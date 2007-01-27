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

import com.adsapient.api_impl.exceptions.AdsapientSecurityException;
import com.adsapient.api_impl.filter.FiltersTemplate;
import com.adsapient.api_impl.filter.factory.CampainFilters;
import com.adsapient.api_impl.filter.factory.FiltersFactory;
import com.adsapient.api_impl.usermanagment.UserImpl;
import com.adsapient.api_impl.usermanagment.role.RoleController;

import com.adsapient.shared.service.FiltersService;

import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.admin.AdsapientConstants;
import com.adsapient.util.security.SecureAction;
import com.adsapient.gui.forms.FilterActionForm;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class FilterAction extends SecureAction {
    public static final String UPDATE = "update";
    public static final String TEMPLATE = "template";
    public static final String RESET = "reset";
    private FiltersService filtersService;

    public ActionForward secureExecute(ActionMapping mapping,
        ActionForm actionForm, HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        ActionMessages messages = new ActionMessages();
        FilterActionForm form = (FilterActionForm) actionForm;
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

        form.setTemplateCollection(MyHibernateUtil.viewWithCriteria(
                FiltersTemplate.class, "userId", userId, "templateId"));

        CampainFilters campainFilters = null;

        if ((form.getCampainId() == null) &&
                (request.getParameter("campainId") != null)) {
            form.setCampainId(request.getParameter("campainId"));
        }

        if ((form.getCampainId() != null) &&
                (form.getCampainId().length() > 0)) {
            Integer bannerId = null;

            if ((form.getBannerId() != null) &&
                    (form.getBannerId().length() > 0)) {
                bannerId = new Integer(form.getBannerId());
            }

            campainFilters = FiltersFactory.createCampinFilters(new Integer(
                        form.getCampainId()), bannerId);
        }

        if ("resetall".equalsIgnoreCase(form.getFilterAction()) &&
                (guest == null)) {
            campainFilters.resetAll(true);
            messages.add(Globals.MESSAGE_KEY,
                new ActionMessage("success.filter.resetAll"));
            saveMessages(request, messages);
        }

        if (FilterAction.TEMPLATE.equalsIgnoreCase(form.getDataSource())) {
            return mapping.findForward("template");
        }

        if (FilterAction.RESET.equalsIgnoreCase(form.getFilterAction()) &&
                (guest == null)) {
            messages.add(Globals.MESSAGE_KEY,
                new ActionMessage("success.filter.reset"));
            saveMessages(request, messages);
            campainFilters.reset(form.getFilterType(), true);
        }

        if (FilterAction.UPDATE.equalsIgnoreCase(form.getFilterAction()) &&
                (guest == null)) {
            int excess = filtersService.getExcess(form);

            if (excess > 0) {
                ActionErrors errors = new ActionErrors();
                errors.add(ActionErrors.GLOBAL_ERROR,
                    new ActionError("totalshares.exceed.by", excess));

                saveErrors(request, errors);

                return mapping.findForward("success");
            }

            boolean result = campainFilters.update(form.getFilterType(),
                    request, actionForm, true);

            if (result) {
                messages.add(Globals.MESSAGE_KEY,
                    new ActionMessage("success.filter.campaign.saved"));
            } else {
                messages.add(Globals.MESSAGE_KEY,
                    new ActionMessage("success.filter.saved"));
            }

            saveMessages(request, messages);
        }

        campainFilters.init(form.getFilterType(), request, form);

        return mapping.findForward("success");
    }

    protected void checkAccessRestriction(HttpServletRequest request,
        ActionForm actionForm) throws AdsapientSecurityException {
    }

    public FiltersService getFiltersService() {
        return filtersService;
    }

    public void setFiltersService(FiltersService filtersService) {
        this.filtersService = filtersService;
    }
}
