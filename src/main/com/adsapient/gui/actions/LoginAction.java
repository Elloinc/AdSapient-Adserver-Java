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

import com.adsapient.api_impl.usermanagment.UserImpl;
import com.adsapient.api_impl.usermanagment.UserManagerImpl;
import com.adsapient.api_impl.usermanagment.role.RoleController;

import com.adsapient.util.Msg;
import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.admin.AdsapientConstants;
import com.adsapient.util.security.AdsapientSecurityManager;
import com.adsapient.gui.forms.LoginActionForm;

import org.apache.log4j.Logger;

import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class LoginAction extends Action {
    private static Logger logger = Logger.getLogger(LoginAction.class);
    private boolean hasTryLogin = false;

    private void invalidatePreviousSessions(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        session = request.getSession(true);

        Enumeration attributeNames = session.getAttributeNames();

        if (attributeNames != null) {
            while (attributeNames.hasMoreElements()) {
                String attName = (String) attributeNames.nextElement();

                try {
                    session.removeAttribute(attName);
                } catch (Exception ex) {
                }
            }
        }
    }

    public ActionForward execute(ActionMapping mapping, ActionForm theForm,
        HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        invalidatePreviousSessions(request);

        Msg.init(getResources(request));

        LoginActionForm form = (LoginActionForm) theForm;
        UserManagerImpl userMang = new UserManagerImpl();

        UserImpl user = null;

        boolean isFreshPage = false;

        Cookie[] cookies = request.getCookies();

        if ((form.getLogin() == null) || "".equals(form.getLogin())) {
            isFreshPage = true;
        }

        if ("logout".equalsIgnoreCase(form.getAction())) {
            user = (UserImpl) request.getSession().getAttribute("user");

            if (user != null) {
                user = null;
                request.getSession().setAttribute(AdsapientConstants.USER, user);
                request.getSession()
                       .setAttribute(AdsapientConstants.PARENT_USER, null);

                request.getSession().invalidate();
            }

            String loginFromCookie = null;
            String passwordFromCookie = null;

            for (int cookieNumber = 0; cookieNumber < cookies.length;
                    cookieNumber++) {
                String cookieParamName = cookies[cookieNumber].getName();

                if ("login".equalsIgnoreCase(cookieParamName)) {
                    if (!"".equalsIgnoreCase(cookies[cookieNumber].getValue())) {
                        loginFromCookie = cookies[cookieNumber].getValue();
                    }
                }

                if ("password".equalsIgnoreCase(cookieParamName)) {
                    if (!"".equalsIgnoreCase(cookies[cookieNumber].getValue())) {
                        passwordFromCookie = cookies[cookieNumber].getValue();
                    }
                }
            }

            if ((loginFromCookie != null) && (passwordFromCookie != null)) {
                user = userMang.viewUser(loginFromCookie, passwordFromCookie);

                if (user != null) {
                    if (!RoleController.ADMIN.equalsIgnoreCase(user.getRole())) {
                        form.setLogin(loginFromCookie);
                        form.setPassword(passwordFromCookie);
                        form.setAutomaticallyLogin(true);
                    }
                }
            }

            form.setAction("login");
        }

        if ((isFreshPage) && (cookies != null) && (cookies.length > 1) &&
                (!"login".equalsIgnoreCase(form.getAction()))) {
            String loginFromCookie = null;
            String passwordFromCookie = null;

            for (int cookieNumber = 0; cookieNumber < cookies.length;
                    cookieNumber++) {
                String cookieParamName = cookies[cookieNumber].getName();

                if ("login".equalsIgnoreCase(cookieParamName)) {
                    if (!"".equalsIgnoreCase(cookies[cookieNumber].getValue())) {
                        loginFromCookie = cookies[cookieNumber].getValue();
                    }
                }

                if ("password".equalsIgnoreCase(cookieParamName)) {
                    if (!"".equalsIgnoreCase(cookies[cookieNumber].getValue())) {
                        passwordFromCookie = cookies[cookieNumber].getValue();
                    }
                }
            }

            if ((loginFromCookie != null) && (passwordFromCookie != null)) {
                user = userMang.viewUser(loginFromCookie, passwordFromCookie);

                if (user != null) {
                    if (!RoleController.ADMIN.equalsIgnoreCase(user.getRole())) {
                        form.setLogin(loginFromCookie);
                        form.setPassword(passwordFromCookie);
                        form.setAutomaticallyLogin(true);
                    }
                }
            }
        }

        if (isFreshPage && form.isAutomaticallyLogin()) {
            return mapping.findForward("login");
        }

        user = userMang.viewUser(form.getLogin(), form.getPassword());

        if (!form.isAutomaticallyLogin()) {
            response.addCookie(new Cookie("login", ""));
            response.addCookie(new Cookie("password", ""));
            request.getSession().invalidate();
        }

        ActionMessages messages = new ActionMessages();

        if (null == user) {
            if ((hasTryLogin) && ("login".equalsIgnoreCase(form.getAction()))) {
                messages.add(Globals.ERROR_KEY,
                    new ActionMessage("login.failed"));
                saveErrors(request, messages);
            } else {
                hasTryLogin = true;
            }

            form.setLogin("");
            form.setPassword("");
            form.setAction("login");

            return mapping.findForward("login");
        } else {
            hasTryLogin = false;

            if (form.getParentId() != null) {
                if (AdsapientSecurityManager.isAccess()) {
                    UserImpl parentUser = (UserImpl) MyHibernateUtil.loadObject(UserImpl.class,
                            new Integer(form.getParentId()));

                    if ((parentUser != null) &&
                            RoleController.ADMIN.equalsIgnoreCase(
                                parentUser.getRole())) {
                        request.getSession()
                               .setAttribute(AdsapientConstants.PARENT_USER,
                            parentUser);

                        AdsapientSecurityManager.successAccess();
                    }
                } else {
                    logger.warn(
                        " Attempt to login into user account but have now access! account login:" +
                        form.getLogin() + " parentId:" + form.getParentId());
                }
            }

            if (form.isAutomaticallyLogin()) {
                Cookie loginCookie = new Cookie("login", form.getLogin());
                Cookie passwordCookie = new Cookie("password",
                        form.getPassword());

                loginCookie.setMaxAge(999999999);
                passwordCookie.setMaxAge(999999999);

                response.addCookie(loginCookie);
                response.addCookie(passwordCookie);
            }

            if (RoleController.GUEST.equalsIgnoreCase(user.getRole())) {
                UserImpl realUser = (UserImpl) MyHibernateUtil.loadObject(UserImpl.class,
                        user.getRealUserId());
                request.getSession()
                       .setAttribute(AdsapientConstants.USER, realUser);
                request.getSession().setAttribute(AdsapientConstants.GUEST, user);
            } else {
                request.getSession().setAttribute(AdsapientConstants.USER, user);
            }

            if (RoleController.ADMIN.equalsIgnoreCase(user.getRole())) {
                return mapping.findForward("admin");
            }

            if (RoleController.ADVERTISER.equalsIgnoreCase(user.getRole())) {
                return mapping.findForward("successAdv");
            } else if (RoleController.PUBLISHER.equalsIgnoreCase(user.getRole())) {
                return mapping.findForward("successPub");
            } else if (RoleController.ADVERTISERPUBLISHER.equalsIgnoreCase(
                        user.getRole())) {
                return mapping.findForward("successAdv");
            } else if (RoleController.ADMIN.equalsIgnoreCase(user.getRole())) {
                return mapping.findForward("successAdmin");
            }

            return mapping.findForward("success");
        }
    }
}
