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

import com.adsapient.api.AdsapientException;

import com.adsapient.shared.exceptions.AdsapientSecurityException;
import com.adsapient.shared.mappable.AccountSetting;
import com.adsapient.shared.mappable.UserImpl;
import com.adsapient.shared.service.CookieManagementService;
import com.adsapient.shared.dao.HibernateEntityDao;

import com.adsapient.gui.forms.AccountProfileActionForm;
import com.adsapient.gui.ContextAwareGuiBean;

import org.apache.log4j.Logger;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AccountProfileAction extends SecureAction {
    static Logger logger = Logger.getLogger(AccountProfileAction.class);
    private HibernateEntityDao hibernateEntityDao;

    public ActionForward secureExecute(ActionMapping mapping,
        ActionForm actionForm, HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        AccountProfileActionForm form = (AccountProfileActionForm) actionForm;

        UserImpl user = (UserImpl) request.getSession().getAttribute("user");

        AccountSetting settings = (AccountSetting) hibernateEntityDao.loadObjectWithCriteria(AccountSetting.class,
                "userId", user.getId());

        if (settings == null) {
            logger.error("Error load account settings for user " +
                user.getId());
            throw new AdsapientException(
                "Error load account settings for user " + user.getId());
        }

        if ("init".equalsIgnoreCase(form.getAction())) {
            if (settings != null) {
                form.setAxis(settings.getAxis().intValue());
                form.setDeph(settings.getDepth().intValue());
                form.setAction("edit");
            }
        }

        if ("edit".equalsIgnoreCase(form.getAction())) {
            form.getValues(settings);

            hibernateEntityDao.updateObject(settings);

            ActionMessages messages = new ActionMessages();
            messages.add(Globals.MESSAGE_KEY,
                new ActionMessage("success.updated"));
            saveMessages(request, messages);
        }

        return mapping.findForward("success");
    }

    protected void checkAccessRestriction(HttpServletRequest request,
        ActionForm actionForm) throws AdsapientSecurityException {
    }

    public HibernateEntityDao getHibernateEntityDao() {
        return hibernateEntityDao;
    }

    public void setHibernateEntityDao(HibernateEntityDao hibernateEntityDao) {
        this.hibernateEntityDao = hibernateEntityDao;
    }
}

