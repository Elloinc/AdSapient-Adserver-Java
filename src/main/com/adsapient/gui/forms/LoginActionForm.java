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

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;


public class LoginActionForm extends ActionForm {
    private String action = "init";
    private String login = "";
    private String password = "";
    private boolean automaticallyLogin = false;
    private String parentId;

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setAutomaticallyLogin(boolean automaticallyLogin) {
        this.automaticallyLogin = automaticallyLogin;
    }

    public boolean isAutomaticallyLogin() {
        return automaticallyLogin;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public ActionErrors validate(ActionMapping mapping,
        HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();

        if ("init".equalsIgnoreCase(this.action) ||
                "logout".equalsIgnoreCase(this.action)) {
            return null;
        }

        if (login.length() == 0) {
            errors.add(ActionErrors.GLOBAL_ERROR,
                new ActionError("error.login.required"));
        }

        if (password.length() == 0) {
            errors.add(ActionErrors.GLOBAL_MESSAGE,
                new ActionError("error.password.required"));
        }

        return errors;
    }
}
