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

import com.adsapient.api.MultiformRequest;

import com.adsapient.shared.mappable.BillingInfoImpl;
import com.adsapient.shared.service.FinancialsService;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;


public class BillingActionForm extends ActionForm implements MultiformRequest {
    private static final long serialVersionUID = 1L;
    private String action = "view";
    private String type = "user";
    private String payPalLogin;
    private float minimumPayout;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public float getMinimumPayout() {
        return minimumPayout;
    }

    public void setMinimumPayout(float minimumPayout) {
        this.minimumPayout = minimumPayout;
    }

    public String getPayPalLogin() {
        return payPalLogin;
    }

    public void setPayPalLogin(String payPalLogin) {
        this.payPalLogin = payPalLogin;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void init(BillingInfoImpl billing) {
        this.payPalLogin = billing.getPayPalLogin();
        this.minimumPayout = FinancialsService.transformMoney(billing.getMinimumPublisherPayout());
    }

    public void updateBilling(BillingInfoImpl billing, boolean useMinimum) {
        billing.setPayPalLogin(this.getPayPalLogin());

        if (useMinimum) {
            billing.setMinimumPublisherPayout(FinancialsService.convert2Money(
                    this.minimumPayout));
        }
    }

    public ActionErrors validate(ActionMapping arg0, HttpServletRequest request) {
        if (!this.action.equals("edit")) {
            return null;
        }

        ActionErrors errors = new ActionErrors();

        if ((payPalLogin == null) || (payPalLogin.length() == 0)) {
            errors.add(ActionErrors.GLOBAL_ERROR,
                new ActionError("error.paypal.login.incorrect"));
        }

        return errors;
    }
}
