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

import com.adsapient.shared.mappable.UserImpl;
import com.adsapient.shared.service.RoleService;

import com.adsapient.shared.service.I18nService;

import org.apache.commons.validator.EmailValidator;

import org.apache.log4j.Logger;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;


public class EditUserForm extends ActionForm {
    private static Logger logger = Logger.getLogger(EditUserForm.class);
    private Collection userRolesCollection = new ArrayList();
    private HttpServletRequest request;
    private String action = "init";
    private String confirmpassword = "";
    private String email = "";
    private String id = "";
    private String login = "";
    private String name = "";
    private String password = "";
    private String userRole;
    private UserImpl user = new UserImpl();

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }

    public String getConfirmpassword() {
        return confirmpassword;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setRequest(HttpServletRequest req) {
        this.request = req;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public UserImpl getUser() {
        user.setName(name);
        user.setEmail(email);
        user.setLogin(login);
        user.setPassword(password);
        user.setRole(this.userRole);

        return user;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRolesCollection(Collection userRolesCollection) {
        this.userRolesCollection = userRolesCollection;
    }

    public Collection getUserRolesCollection() {
        if (this.request != null) {
            userRolesCollection = RoleService.getNotAutoriseRoleLabelsCollection(this.request);
        } else {
            logger.warn("request object == null");
        }

        return userRolesCollection;
    }

    public void initForm(UserImpl userI) {
        this.email = userI.getEmail();
        this.name = userI.getName();
        this.login = userI.getLogin();
        this.password = userI.getPassword();
        this.confirmpassword = userI.getPassword();
        this.id = userI.getId().toString();
        this.userRole = userI.getRole();
    }

    public ActionErrors validate(ActionMapping mapping,
        HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        com.adsapient.shared.service.ValidationService validator = new com.adsapient.shared.service.ValidationService();

        if ("init".equalsIgnoreCase(this.action) ||
                "view".equalsIgnoreCase(this.action) ||
                "remove".equalsIgnoreCase(this.action) ||
                "viewProfile".equalsIgnoreCase(this.action)) {
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
                    I18nService.fetch("form.name", request)));

            return errors;
        }

        if (login.trim().length() == 0) {
            errors.add(ActionErrors.GLOBAL_ERROR,
                new ActionError("error.edituser.loginrequired"));

            return errors;
        }

        if (!validator.isAlphabets(login, " _.,")) {
            errors.add(ActionErrors.GLOBAL_ERROR,
                new ActionError("errors.alphanum",
                    I18nService.fetch("login.username", request)));

            return errors;
        }

        if (password.trim().length() == 0) {
            errors.add(ActionErrors.GLOBAL_MESSAGE,
                new ActionError("error.edituser.passwordrequired"));

            return errors;
        }

        if (!password.equals(confirmpassword) && (password.length() != 0)) {
            errors.add(ActionErrors.GLOBAL_MESSAGE,
                new ActionError("error.edituser.confirmpassword"));

            return errors;
        }

        String emailLower = email.toLowerCase();

        if (email.trim().length() == 0) {
            errors.add(ActionErrors.GLOBAL_MESSAGE,
                new ActionError("error.edituser.emailrequired"));

            return errors;
        }

        if (email != emailLower) {
            errors.add(ActionErrors.GLOBAL_MESSAGE,
                new ActionError("error.edituser.emailrequiredforcase"));

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
}
