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

import com.adsapient.api.AdsapientInterceptedSupportEntity;

import com.adsapient.api_impl.exceptions.AdsapientSecurityException;
import com.adsapient.api_impl.exceptions.ConfigurationsException;
import com.adsapient.api_impl.managment.StatisticManager;
import com.adsapient.api_impl.publisher.SiteImpl;
import com.adsapient.api_impl.share.Category;
import com.adsapient.api_impl.usermanagment.UserImpl;
import com.adsapient.api_impl.usermanagment.role.RoleController;

import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.security.SecureAction;
import com.adsapient.gui.forms.EditPublisherSiteActionForm;

import org.apache.log4j.Logger;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.LabelValueBean;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class EditPublisherSiteAction extends SecureAction {
    private static Logger logger = Logger.getLogger(EditPublisherSiteAction.class);

    public ActionForward secureExecute(ActionMapping mapping,
        ActionForm theForm, HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        EditPublisherSiteActionForm form = (EditPublisherSiteActionForm) theForm;

        UserImpl user = (UserImpl) request.getSession().getAttribute("user");

        ActionMessages messages = new ActionMessages();
        propagateCategorys(form);

        if ("init".equalsIgnoreCase(form.getAction())) {
            form.setAction("add");

            return mapping.findForward("init");
        }
        else if ("view".equalsIgnoreCase(form.getAction())) {
            AdsapientInterceptedSupportEntity site = (AdsapientInterceptedSupportEntity) MyHibernateUtil.loadObject(SiteImpl.class,
                    form.getSiteId());

            if (site == null) {
                throw new ConfigurationsException("can't load site for user " +
                    form.getUserId() + "and siteId" + form.getSiteId());
            }

            site.view(form, request);

            return mapping.findForward("edit");
        }

        if ("edit".equalsIgnoreCase(form.getAction())) {
            AdsapientInterceptedSupportEntity site = (SiteImpl) MyHibernateUtil.loadObject(SiteImpl.class,
                    form.getSiteId());

            if (site == null) {
                throw new ConfigurationsException("can't load site for user " +
                    form.getUserId() + "and siteId" + form.getSiteId());
            }

            site.edit(form, request);
            request.setAttribute("userId", ((SiteImpl) site).getUserId());

            messages.add(Globals.MESSAGE_KEY,
                new ActionMessage("success.resource.updated"));
            saveMessages(request, messages);

            return mapping.findForward("success");
        }

        if ("add".equalsIgnoreCase(form.getAction())) {
            AdsapientInterceptedSupportEntity site = new SiteImpl(request);
            site.add(form, request);

            messages.add(Globals.MESSAGE_KEY,
                new ActionMessage("success.resource.saved"));
            saveMessages(request, messages);

            return mapping.findForward("success");
        }

        if ("resetStatistic".equalsIgnoreCase(form.getAction())) {
            SiteImpl site = (SiteImpl) MyHibernateUtil.loadObject(SiteImpl.class,
                    form.getSiteId());

            if (site != null) {
                StatisticManager.remove(site);
            } else {
                logger.error(
                    "trying to remove statistic but cant load site with id=" +
                    form.getSiteId());
            }

            return mapping.findForward("success");
        }

        if ("remove".equalsIgnoreCase(form.getAction())) {
            AdsapientInterceptedSupportEntity site = (AdsapientInterceptedSupportEntity) MyHibernateUtil.loadObject(SiteImpl.class,
                    form.getSiteId());

            if (site == null) {
                return mapping.findForward("success");
            }

            site.delete(form, request);

            messages.add(Globals.MESSAGE_KEY,
                new ActionMessage("success.resource.removed"));
            saveMessages(request, messages);

            return mapping.findForward("success");
        }

        return mapping.findForward("edit");
    }

    protected void checkAccessRestriction(HttpServletRequest request,
        ActionForm actionForm) throws AdsapientSecurityException {
        UserImpl user = (UserImpl) request.getSession().getAttribute("user");

        if (RoleController.ADVERTISER.equals(user.getRole())) {
            throw new AdsapientSecurityException("acsess.denyide");
        }
    }

    private void propagateCategorys(EditPublisherSiteActionForm form) {
        Collection categoryCollection = MyHibernateUtil.viewAll(Category.class);

        Iterator iter = categoryCollection.iterator();

        while (iter.hasNext()) {
            Category category = (Category) iter.next();
            LabelValueBean bean = new LabelValueBean(category.getName(),
                    category.getId().toString());

            StringBuffer categoryStringBufer = new StringBuffer();

            categoryStringBufer.append(":").append(category.getId()).append(":");

            form.getCategorys().add(bean);
        }
    }
}
