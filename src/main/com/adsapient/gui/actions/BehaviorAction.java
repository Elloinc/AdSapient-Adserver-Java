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

import com.adsapient.api.AdsapientCommand;
import com.adsapient.api.User;

import com.adsapient.shared.exceptions.AdsapientSecurityException;
import com.adsapient.shared.mappable.BehaviorPattern;
import com.adsapient.shared.service.CookieManagementService;
import com.adsapient.shared.mappable.BehaviorCommand;
import com.adsapient.shared.AdsapientConstants;
import com.adsapient.shared.dao.HibernateEntityDao;

import com.adsapient.gui.forms.BehaviorActionForm;

import org.apache.log4j.Logger;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class BehaviorAction extends SecureAction {
    Logger logger = Logger.getLogger(BehaviorAction.class);
    private HibernateEntityDao hibernateEntityDao;

    protected void checkAccessRestriction(HttpServletRequest request,
        ActionForm actionForm) throws AdsapientSecurityException {
    }

    public ActionForward secureExecute(ActionMapping mapping,
        ActionForm actionForm, HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        BehaviorActionForm form = (BehaviorActionForm) actionForm;
        AdsapientCommand command = new BehaviorCommand();
        User user = (User) request.getSession()
                                  .getAttribute(AdsapientConstants.USER);
        form.setRequest(request);

        ActionMessages messages = new ActionMessages();

        if ("add".equalsIgnoreCase(form.getAction())) {
            command.add(form, request);
            messages.add(Globals.MESSAGE_KEY,
                new ActionMessage("success.behavior.saved"));
            saveMessages(request, messages);
        }

        if ("del".equalsIgnoreCase(form.getAction())) {
            command.delete(form, request);
            messages.add(Globals.MESSAGE_KEY,
                new ActionMessage("success.behavior.removed"));
            saveMessages(request, messages);
        }

        if ("edit".equalsIgnoreCase(form.getAction())) {
            command.edit(form, request);
            messages.add(Globals.MESSAGE_KEY,
                new ActionMessage("success.behavior.updated"));
            saveMessages(request, messages);
        }

        if ("view".equalsIgnoreCase(form.getAction())) {
            form.setAction("init");
            form.setTemplatesCollection(hibernateEntityDao.viewWithCriteria(
                    BehaviorPattern.class, "userId", user.getId(), "id"));

            return mapping.findForward("list");
        }

        if ("init".equalsIgnoreCase(form.getAction())) {
            command.init(form, request);

            return mapping.findForward("success");
        }

        if ("preview".equalsIgnoreCase(form.getAction())) {
            command.view(form, request);

            return mapping.findForward("success");
        }

        return mapping.findForward("success");
    }

    public HibernateEntityDao getHibernateEntityDao() {
        return hibernateEntityDao;
    }

    public void setHibernateEntityDao(HibernateEntityDao hibernateEntityDao) {
        this.hibernateEntityDao = hibernateEntityDao;
    }
}
