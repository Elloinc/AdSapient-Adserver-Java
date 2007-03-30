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

import com.adsapient.shared.mappable.ParameterImpl;

import com.adsapient.shared.service.I18nService;
import com.adsapient.shared.service.ValidationService;

import org.apache.log4j.Logger;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;


public class ParameterForm extends ActionForm {
    static Logger logger = Logger.getLogger(ParameterForm.class);
    private Collection parametersCollection = null;
    private String action = "init";
    private String label = "";
    private String name = "";
    private String parameterId;
    private String value = "";
    private int type;

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ParameterImpl getParameter(ParameterImpl param) {
        param.setLabel(label);
        param.setName(name);
        param.setType(type);

        if (value != null) {
            param.setValue(value);
        }

        return param;
    }

    public void setParameterId(String parameterId) {
        this.parameterId = parameterId;
    }

    public String getParameterId() {
        return parameterId;
    }

    public void setParametersCollection(Collection parametersCollection) {
        this.parametersCollection = parametersCollection;
    }

    public Collection getParametersCollection() {
        return parametersCollection;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void init(ParameterImpl param) {
        this.label = param.getLabel();
        this.name = param.getName();
        this.type = param.getType();
        this.value = param.getValue();
    }

    public ActionErrors validate(ActionMapping arg0, HttpServletRequest arg1) {
        ActionErrors errors = new ActionErrors();

        ValidationService validator = new ValidationService();

        if ("add".equalsIgnoreCase(this.action) ||
                "edit".equalsIgnoreCase(this.action)) {
            if ((this.label == null) || (this.label.trim().length() == 0)) {
                errors.add(ActionErrors.GLOBAL_MESSAGE,
                    new ActionError("parameter.label.required"));

                return errors;
            }

            if (!validator.isAlphabets(this.label, " _.,")) {
                errors.add(ActionErrors.GLOBAL_ERROR,
                    new ActionError("errors.alphabets",
                        I18nService.fetch("parameter.label", arg1)));

                return errors;
            }

            if ((this.name == null) || (this.name.trim().length() == 0)) {
                errors.add(ActionErrors.GLOBAL_MESSAGE,
                    new ActionError("parameter.name.required"));

                return errors;
            }

            if (!validator.isAlphabets(this.name, " _.,")) {
                errors.add(ActionErrors.GLOBAL_ERROR,
                    new ActionError("errors.alphabets",
                        I18nService.fetch("parameter.name", arg1)));

                return errors;
            }

            if (this.type < 1) {
                errors.add(ActionErrors.GLOBAL_MESSAGE,
                    new ActionError("parameter.type.required"));

                return errors;
            }

            if ((this.value == null) || (this.value.trim().length() == 0)) {
                errors.add(ActionErrors.GLOBAL_MESSAGE,
                    new ActionError("parameter.predifined.value"));

                return errors;
            }

            if (!validator.isAlphabets(this.value, " _.,")) {
                errors.add(ActionErrors.GLOBAL_ERROR,
                    new ActionError("errors.alphabets",
                        I18nService.fetch("parameter.predefined.values", arg1)));

                return errors;
            }
        }

        return errors;
    }
}
