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

import com.adsapient.shared.mappable.CampainImpl;
import com.adsapient.shared.exceptions.AdsapientSecurityException;
import com.adsapient.shared.mappable.UserImpl;
import com.adsapient.shared.service.CampaignService;
import com.adsapient.shared.service.CookieManagementService;

import com.adsapient.shared.service.I18nService;
import com.adsapient.shared.dao.HibernateEntityDao;
import com.adsapient.gui.forms.EditCampainActionForm;

import org.apache.log4j.Logger;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class EditCampainAction extends SecureAction {
    static Logger logger = Logger.getLogger(EditCampainAction.class);
     private CampaignService campaignService;
    private HibernateEntityDao hibernateEntityDao;

    public ActionForward secureExecute(ActionMapping mapping,
        ActionForm theForm, HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        ActionMessages messages = new ActionMessages();

        EditCampainActionForm form = (EditCampainActionForm) theForm;
        UserImpl user = (UserImpl) request.getSession().getAttribute("user");

        if ("remove".equalsIgnoreCase(form.getAction())) {
            CampainImpl camp = (CampainImpl) hibernateEntityDao.loadObject(CampainImpl.class,
                    form.getCampainId());
            camp.delete(form, request);

            messages.add(Globals.MESSAGE_KEY,
                new ActionMessage("success.campaign.removed"));
            saveMessages(request, messages);

            return mapping.findForward("success");
        }

        if ("defaultCampain".equalsIgnoreCase(form.getAction())) {
            campaignService.viewDefaultCampain(user, form);

            return mapping.findForward("edit");
        }

        if ("edit".equalsIgnoreCase(form.getAction())) {
            CampainImpl camp = (CampainImpl) hibernateEntityDao.loadObject(CampainImpl.class,
                    form.getCampainId());
            camp.edit(form, request);

            messages.add(Globals.MESSAGE_KEY,
                new ActionMessage("success.campaign.saved"));
            saveMessages(request, messages);

            return mapping.findForward("success");
        }

        if ("resetStatistic".equalsIgnoreCase(form.getAction())) {
            CampainImpl campaign = (CampainImpl) hibernateEntityDao.loadObject(CampainImpl.class,
                    form.getCampainId());

            if (campaign != null) {

                messages.add(Globals.MESSAGE_KEY,
                    new ActionMessage("success.stat.reset"));
                saveMessages(request, messages);
            } else {
                logger.error(
                    "trying to remove statistic but cant load campaign with id=" +
                    form.getCampainId());
            }

            return mapping.findForward("success");
        }

        if ("init".equalsIgnoreCase(form.getAction())) {
            new CampainImpl().init(form, request);

            return mapping.findForward("init");
        }

        if ("add".equalsIgnoreCase(form.getAction())) {

            if (campaignService.checkCampains(form.getCampainName()).size() == 0) {
                new CampainImpl(request).add(form, request);

                messages.add(Globals.MESSAGE_KEY,
                    new ActionMessage("success.campaign.added"));
                saveMessages(request, messages);
            } else {
                messages.add("errors.exists",
                    new ActionMessage(I18nService.fetch("campaign.name", request), false));
                saveErrors(request, messages);

                return mapping.findForward("edit");
            }

            return mapping.findForward("success");
        }

        if ("view".equalsIgnoreCase(form.getAction())) {
            CampainImpl campain = (CampainImpl) hibernateEntityDao.loadObject(CampainImpl.class,
                    form.getCampainId());
            campain.view(form, request);

            return mapping.findForward("edit");
        }

        return mapping.findForward("init");
    }

    protected void checkAccessRestriction(HttpServletRequest request,
        ActionForm actionForm) throws AdsapientSecurityException {
    }

    public CampaignService getCampaignService() {
        return campaignService;
    }

    public void setCampaignService(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    public HibernateEntityDao getHibernateEntityDao() {
        return hibernateEntityDao;
    }

    public void setHibernateEntityDao(HibernateEntityDao hibernateEntityDao) {
        this.hibernateEntityDao = hibernateEntityDao;
    }
}
