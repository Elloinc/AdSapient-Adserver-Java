/*
 * AdSapient - Open Source Ad Server
 * http://www.sourceforge.net/projects/adsapient
 * http://www.adsapient.com
 *
 * Copyright (C) 2001-06 Vitaly Sazanovich
 * Vitaly.Sazanovich@gmail.com
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Library General Public License  as published by the
 * Free Software Foundation; either version 2 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *
 */
package com.adsapient.gui.actions;

import com.adsapient.api.FilterInterface;

import com.adsapient.api_impl.exceptions.AdsapientSecurityException;
import com.adsapient.api_impl.filter.FiltersTemplate;
import com.adsapient.api_impl.filter.factory.CampainFilters;
import com.adsapient.api_impl.filter.factory.FiltersFactory;
import com.adsapient.api_impl.usermanagment.UserImpl;
import com.adsapient.api_impl.usermanagment.role.RoleController;

import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.admin.AdsapientConstants;
import com.adsapient.util.admin.ConfigurationConstants;
import com.adsapient.util.filters.FiltersTemplateUtil;
import com.adsapient.util.security.SecureAction;
import com.adsapient.gui.forms.FiltersTemplateActionForm;

import org.apache.log4j.Logger;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class FiltersTemplateAction extends SecureAction {
    private static Logger logger = Logger.getLogger(FiltersTemplateAction.class);

    public ActionForward secureExecute(ActionMapping mapping,
        ActionForm actionForm, HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        FiltersTemplateActionForm form = (FiltersTemplateActionForm) actionForm;
        UserImpl user = (UserImpl) request.getSession()
                                          .getAttribute(AdsapientConstants.USER);
        UserImpl guest = (UserImpl) request.getSession()
                                           .getAttribute(AdsapientConstants.GUEST);

        int userId = user.getId();

        if (RoleController.ADMIN.equalsIgnoreCase(user.getRole())) {
            try {
                userId = Integer.parseInt(request.getParameter("userId"));
            } catch (Exception e) {
                try {
                    userId = (Integer) request.getAttribute("userId");
                } catch (Exception e1) {
                }
            }
        }

        ActionMessages messages = new ActionMessages();

        if ("resetall".equalsIgnoreCase(form.getFilterAction())) {
            FiltersTemplate filtersTemplate = (FiltersTemplate) request.getSession()
                                                                       .getAttribute("filterTemplate");

            if ((filtersTemplate != null) && (guest == null)) {
                CampainFilters campainFilters = FiltersFactory.createTemplateFilters((filtersTemplate.getTemplateId()
                                                                                                     .toString()));
                campainFilters.resetAll(true);
                form.setTemplateId(filtersTemplate.getTemplateId().toString());
                form.setTemplateAction("view");
            } else {
                form.setTemplateAction("init");
            }

            messages.add(Globals.MESSAGE_KEY,
                new ActionMessage("success.filter.resetAll"));
            saveMessages(request, messages);
        }

        if ("save".equalsIgnoreCase(form.getTemplateAction())) {
            String filterKeyUpd = ConfigurationConstants.TEMP_FILTER_PREFIX +
                form.getFilterType();

            FilterInterface filter = (FilterInterface) request.getSession()
                                                              .getAttribute(filterKeyUpd);

            request.getSession()
                   .setAttribute("templateName", form.getTemplateName());

            CampainFilters campainFilters = FiltersFactory.createFakeFilters();
            Map filtersMap = new HashMap();
            Iterator keysIterator = campainFilters.getFiltersKeysCollection()
                                                  .iterator();

            while (keysIterator.hasNext()) {
                String filterKey = (String) keysIterator.next();
                Object filterFromSession = request.getSession()
                                                  .getAttribute(ConfigurationConstants.TEMP_FILTER_PREFIX +
                        filterKey);

                if (filterFromSession != null) {
                    filtersMap.put(filterKey, filterFromSession);
                } else {
                    logger.warn("Warning in  filter with key=  temp" +
                        filterKey + "can not be found in session");
                }
            }

            if (guest == null) {
                FiltersTemplateUtil.addTemplate(filtersMap,
                    form.getTemplateName(), userId);
            }

            form.setTemplateAction("viewUserTemplates");

            messages.add(Globals.MESSAGE_KEY,
                new ActionMessage("success.updated"));
            saveMessages(request, messages);
        }

        if ("remove".equalsIgnoreCase(form.getTemplateAction())) {
            if ((form.getTemplateId() != null) && (guest == null)) {
                FiltersTemplate template = (FiltersTemplate) MyHibernateUtil.loadObject(FiltersTemplate.class,
                        new Integer(form.getTemplateId()));

                FiltersTemplateUtil.removeTemplate(template);
            }

            form.setTemplateAction("viewUserTemplates");

            messages.add(Globals.MESSAGE_KEY,
                new ActionMessage("success.filter.removed"));
            saveMessages(request, messages);
        }

        if ("perform".equalsIgnoreCase(form.getTemplateAction())) {
            if (guest == null) {
                FiltersTemplateUtil.perform(form, request);
            }

            messages.add(Globals.MESSAGE_KEY,
                new ActionMessage("success.filter.applied"));
            saveMessages(request, messages);

            return mapping.findForward("filters");
        }

        if ("viewUserTemplates".equalsIgnoreCase(form.getTemplateAction())) {
            form.setTemplateAction("init");

            Collection templates = MyHibernateUtil.viewWithCriteria(FiltersTemplate.class,
                    "userId", userId, "templateId");

            form.setTemplateCollection(templates);

            return mapping.findForward("viewTemplates");
        }

        if ("view".equalsIgnoreCase(form.getTemplateAction())) {
            FiltersTemplateUtil.viewTemplateOptions(form, request);

            form.setEnableAutoUpdate(true);
        }

        if ("init".equalsIgnoreCase(form.getTemplateAction())) {
            CampainFilters campainFilters = FiltersFactory.createFakeFilters();
            Iterator filterKeysIterator = campainFilters.getFiltersMap()
                                                        .entrySet().iterator();

            while (filterKeysIterator.hasNext()) {
                Map.Entry filterEntry = (Map.Entry) filterKeysIterator.next();

                String tempFilterName = ConfigurationConstants.TEMP_FILTER_PREFIX +
                    filterEntry.getKey();
                Object tempFilter = filterEntry.getValue();
                request.getSession().setAttribute(tempFilterName, tempFilter);
            }

            request.getSession().setAttribute("templateName", "");
        }

        if ("import".equalsIgnoreCase(form.getTemplateAction())) {
            if (form.getCampainId() != null) {
                CampainFilters campainFilters = FiltersFactory.createCampinFilters(new Integer(
                            form.getCampainId()), null);

                Iterator campainFiltersIterator = campainFilters.getFiltersMap()
                                                                .entrySet()
                                                                .iterator();

                while (campainFiltersIterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) campainFiltersIterator.next();

                    FilterInterface filter = (FilterInterface) entry.getValue();

                    if (filter != null) {
                        FilterInterface tempFilter = (FilterInterface) filter.copy();

                        request.getSession()
                               .setAttribute(ConfigurationConstants.TEMP_FILTER_PREFIX +
                            entry.getKey(), tempFilter);
                    } else {
                        logger.warn(
                            " in import filter template filter is null  hie key=" +
                            entry.getKey());
                    }
                }

                request.getSession().setAttribute("templateName", "");

                messages.add(Globals.MESSAGE_KEY,
                    new ActionMessage("success.filter.imported"));
                saveMessages(request, messages);
            } else {
                logger.warn(
                    "trying to import campain but campain id was not specify");
            }
        }

        if ("update".equalsIgnoreCase(form.getTemplateAction())) {
            String filterKey = ConfigurationConstants.TEMP_FILTER_PREFIX +
                form.getFilterType();

            FilterInterface filter = (FilterInterface) request.getSession()
                                                              .getAttribute(filterKey);

            if ((filter != null) && (guest == null)) {
                filter.update(request, form, form.isEnableAutoUpdate());

                request.getSession()
                       .setAttribute(filterKey + form.getFilterType(), filter);
            } else {
                logger.warn("Warn.filter with key=" +
                    ConfigurationConstants.TEMP_FILTER_PREFIX +
                    form.getFilterType() + " could not be found in session");
            }

            request.getSession()
                   .setAttribute("templateName", form.getTemplateName());

            if (form.isEnableAutoUpdate() && (guest == null)) {
                FiltersTemplate template = (FiltersTemplate) request.getSession()
                                                                    .getAttribute("filterTemplate");
                template.setTemplateName(form.getTemplateName());
                MyHibernateUtil.updateObject(template);
            }

            messages.add(Globals.MESSAGE_KEY,
                new ActionMessage("success.filter.updated"));
            saveMessages(request, messages);
        }

        FilterInterface filter = (FilterInterface) request.getSession()
                                                          .getAttribute(ConfigurationConstants.TEMP_FILTER_PREFIX +
                form.getFilterType());

        if (filter != null) {
            filter.init(request, form);
        }

        form.setTemplateAction("update");
        form.setTemplateName((String) request.getSession()
                                             .getAttribute("templateName"));

        return mapping.findForward("template");
    }

    protected void checkAccessRestriction(HttpServletRequest request,
        ActionForm actionForm) throws AdsapientSecurityException {
    }
}
