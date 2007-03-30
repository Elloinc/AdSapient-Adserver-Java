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

import com.adsapient.shared.service.I18nService;
import com.adsapient.shared.service.ValidationService;

import org.apache.commons.validator.EmailValidator;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import javax.servlet.http.HttpServletRequest;


public class ContactForm extends ActionForm {
    private String name;
    private String email;
    private String message;
    private String action = "init";
    private String input = "";

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

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public ActionErrors validate(ActionMapping mapping,
        HttpServletRequest request) {
        ValidationService validator = new ValidationService();
        ActionErrors errors = new ActionErrors();
        String errorMsg = "";

        if (action.equals("init")) {
            return null;
        }

        if (name.trim().length() == 0) {
            errors.add("errors.required",
                new ActionMessage("error.name.required"));

            return errors;
        }

        if (!validator.isAlphanumeric(name, "_., /:@&")) {
            errors.add(ActionErrors.GLOBAL_ERROR,
                new ActionError("errors.alphanum",
                    I18nService.fetch("form.name", request)));

            return errors;
        }

        String emailLower = email.toLowerCase();

        if (email.trim().length() == 0) {
            errors.add(ActionErrors.GLOBAL_MESSAGE,
                new ActionError("error.email.required"));

            return errors;
        }

        if (email != emailLower) {
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

        if (message.trim().length() == 0) {
            errors.add(ActionErrors.GLOBAL_MESSAGE,
                new ActionError("error.message.required"));

            return errors;
        }

        if (!validator.isAlphanumeric(message, "_., /:@&")) {
            errors.add(ActionErrors.GLOBAL_ERROR,
                new ActionError("errors.alphanum", I18nService.fetch("message", request)));

            return errors;
        }

        if (message.trim().length() > 500) {
            errors.add(ActionErrors.GLOBAL_ERROR,
                new ActionError("", "Message should not exceed 500 charecters"));

            return errors;
        }

        return errors;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }
}
