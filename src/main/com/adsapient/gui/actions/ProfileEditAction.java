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

import com.adsapient.shared.exceptions.AdsapientSecurityException;
import com.adsapient.shared.mappable.UserImpl;
import com.adsapient.shared.service.CookieManagementService;
import com.adsapient.shared.service.UserManagementService;
import com.adsapient.shared.AdsapientConstants;
import com.adsapient.shared.dao.HibernateEntityDao;

import com.adsapient.gui.forms.ProfileEditActionForm;

import org.apache.log4j.Logger;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ProfileEditAction extends SecureAction {
    private static Logger logger = Logger.getLogger(ProfileEditAction.class);
    private UserManagementService userManagementService;
    private HibernateEntityDao hibernateEntityDao;

    protected void checkAccessRestriction(HttpServletRequest request,
        ActionForm actionForm) throws AdsapientSecurityException {
    }

    public ActionForward secureExecute(ActionMapping mapping,
        ActionForm actionForm, HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        ProfileEditActionForm form = (ProfileEditActionForm) actionForm;
        form.setRequest(request);

        UserImpl userFromSession = (UserImpl) request.getSession()
                                                     .getAttribute(AdsapientConstants.USER);
        UserImpl guest = (UserImpl) request.getSession()
                                           .getAttribute(AdsapientConstants.GUEST);
        ActionMessages messages = new ActionMessages();

        if ("add".equalsIgnoreCase(form.getAction())) {
            if (guest == null) {
                form.setAction("init");

                UserImpl user = form.getUser();

                if (userManagementService.signInUser(user, request)) {
                    messages.add(Globals.MESSAGE_KEY,
                        new ActionMessage("success.user.added"));
                    saveMessages(request, messages);

                    return mapping.findForward("successAdd");
                } else {
                    messages.add(Globals.ERROR_KEY,
                        new ActionMessage(
                            "user.with.the.same.name.is.registered"));
                    saveErrors(request, messages);
                }
            }

            form.setAction("add");

            return mapping.findForward("init");
        }

        if ("init".equalsIgnoreCase(form.getAction())) {
            form.setAction("add");
            form.setProfileLogin("");
            form.setProfilePassword("");

            return mapping.findForward("init");
        }

        if ("remove".equalsIgnoreCase(form.getAction())) {
            if (guest == null) {
                userManagementService.removeUser(form.getId());
            }

            messages.add(Globals.MESSAGE_KEY,
                new ActionMessage("success.user.removed"));
            saveMessages(request, messages);

            return mapping.findForward("successAdd");
        }

        if ("resetStatistic".equalsIgnoreCase(form.getAction())) {
            if (!AdsapientConstants.ADMIN.equalsIgnoreCase(
                        userFromSession.getRole())) {
                throw new AdsapientSecurityException("access.deny");
            }

            UserImpl user = (UserImpl) hibernateEntityDao.loadObject(UserImpl.class,
                    form.getId());

            return mapping.findForward("successAdd");
        }

        if ("view".equalsIgnoreCase(form.getAction())) {
            UserImpl user = (UserImpl) userManagementService.viewUser(form.getId());

            form.initForm(user);
            form.setAction("edit");

            return mapping.findForward("success");
        }

        if ("viewProfile".equalsIgnoreCase(form.getAction())) {
            form.initForm(userFromSession);
            form.setAction("edit");

            return mapping.findForward("success");
        }

        if ("edit".equalsIgnoreCase(form.getAction())) {
            if (guest == null) {
                UserImpl user = userManagementService.viewUser(form.getId());
                user.setEmail(form.getEmail());
                user.setName(form.getName());
                user.setLogin(form.getProfileLogin());
                user.setPassword(form.getProfilePassword());

                userManagementService.editUser(user);
//                request.getSession().setAttribute(AdsapientConstants.USER, user);
                form.setUserRole(user.getRole());

                messages.add(Globals.MESSAGE_KEY,
                    new ActionMessage("success.updated"));
                saveMessages(request, messages);
            }

            return mapping.findForward("init");
        }

        return null;
    }

    public UserManagementService getUserManagementService() {
        return userManagementService;
    }

    public void setUserManagementService(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    public HibernateEntityDao getHibernateEntityDao() {
        return hibernateEntityDao;
    }

    public void setHibernateEntityDao(HibernateEntityDao hibernateEntityDao) {
        this.hibernateEntityDao = hibernateEntityDao;
    }
}
