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

import org.apache.commons.validator.EmailValidator;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;


public class PassReminderForm extends ActionForm {
    private String email;
    private String action = "init";

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public ActionErrors validate(ActionMapping mapping,
        HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();

        if (action.equals("init")) {
            return null;
        }

        String emailLower = email.toLowerCase();

        if (email.length() == 0) {
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
