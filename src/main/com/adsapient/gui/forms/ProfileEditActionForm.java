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
package com.adsapient.gui.forms;

import com.adsapient.api_impl.usermanagment.UserImpl;
import com.adsapient.api_impl.usermanagment.role.RoleSeparator;

import com.adsapient.util.Msg;
import com.adsapient.util.jsp.InputValidator;

import org.apache.commons.validator.EmailValidator;

import org.apache.log4j.Logger;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;


public class ProfileEditActionForm extends ActionForm {
    private static Logger logger = Logger.getLogger(ProfileEditActionForm.class);
    private String action = "init";
    private String userRole;
    private Collection userRolesCollection = new ArrayList();
    private String email = "";
    private Integer id;
    private boolean isRolesChangeEnable = false;
    private String profileLogin = "";
    private String name = "";
    private String profilePassword = "";
    private String confirmpassword = "";
    private HttpServletRequest request;
    private UserImpl user = new UserImpl();

    public boolean isRolesChangeEnable() {
        return isRolesChangeEnable;
    }

    public void setRolesChangeEnable(boolean isRolesChangeEnable) {
        this.isRolesChangeEnable = isRolesChangeEnable;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest req) {
        this.request = req;
    }

    public String getAction() {
        return action;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }

    public String getConfirmpassword() {
        return confirmpassword;
    }

    public UserImpl getUser() {
        user.setName(name);
        user.setEmail(email);
        user.setLogin(profileLogin);
        user.setPassword(profilePassword);
        user.setRole(this.userRole);

        return user;
    }

    public void initForm(UserImpl userI) {
        this.email = userI.getEmail();
        this.name = userI.getName();
        this.profileLogin = userI.getLogin();
        this.profilePassword = userI.getPassword();
        this.confirmpassword = userI.getPassword();
        this.id = userI.getId();
        this.userRole = userI.getRole();
    }

    public ActionErrors validate(ActionMapping mapping,
        HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        InputValidator validator = new InputValidator();

        if ("init".equalsIgnoreCase(this.action) ||
                "view".equalsIgnoreCase(this.action) ||
                "remove".equalsIgnoreCase(this.action) ||
                "viewProfile".equalsIgnoreCase(this.action) ||
                "resetStatistic".equalsIgnoreCase(this.action)) {
            return null;
        }

        if (name.trim().length() == 0) {
            errors.add(ActionErrors.GLOBAL_ERROR,
                new ActionError("error.edituser.namerequired"));

            return errors;
        }

        if (!validator.isAlphabets(name, " _.,")) {
            errors.add(ActionErrors.GLOBAL_ERROR,
                new ActionError("errors.alphabets",
                    Msg.fetch("form.name", request)));

            return errors;
        }

        if (profileLogin.trim().length() == 0) {
            errors.add(ActionErrors.GLOBAL_ERROR,
                new ActionError("error.edituser.loginrequired"));

            return errors;
        }

        if (!validator.isAlphanumeric(profileLogin, "_., /:@&")) {
            errors.add(ActionErrors.GLOBAL_ERROR,
                new ActionError("errors.alphanum", Msg.fetch("login", request)));

            return errors;
        }

        if (profilePassword.trim().length() == 0) {
            errors.add(ActionErrors.GLOBAL_MESSAGE,
                new ActionError("error.edituser.passwordrequired"));

            return errors;
        }

        if (!profilePassword.equals(confirmpassword) &&
                (profilePassword.length() != 0)) {
            errors.add(ActionErrors.GLOBAL_MESSAGE,
                new ActionError("error.edituser.confirmpassword"));

            return errors;
        }

        if (email.trim().length() == 0) {
            errors.add(ActionErrors.GLOBAL_MESSAGE,
                new ActionError("error.edituser.emailrequired"));

            return errors;
        }

        if (email != email.toLowerCase()) {
            errors.add(ActionErrors.GLOBAL_MESSAGE,
                new ActionError("error.email.requiredforcase"));

            return errors;
        }

        EmailValidator ev = EmailValidator.getInstance();

        if (!ev.isValid(email)) {
            errors.add(ActionErrors.GLOBAL_MESSAGE,
                new ActionError("error.edituser.wrongemail"));

            return errors;
        }

        return errors;
    }

    public String getUserRole() {
        return userRole;
    }

    public Collection getUserRolesCollection() {
        if (this.request != null) {
            userRolesCollection = RoleSeparator.getRoleLabelsCollection(request);
        } else {
            logger.warn("request == null");
        }

        return userRolesCollection;
    }

    public void setUserRolesCollection(Collection userRolesCollection) {
        this.userRolesCollection = userRolesCollection;
    }

    public String getProfileLogin() {
        return profileLogin;
    }

    public void setProfileLogin(String profileLogin) {
        this.profileLogin = profileLogin;
    }

    public String getProfilePassword() {
        return profilePassword;
    }

    public void setProfilePassword(String profilePassword) {
        this.profilePassword = profilePassword;
    }
}
