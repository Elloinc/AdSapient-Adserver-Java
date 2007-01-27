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

import com.adsapient.api_impl.exceptions.AdsapientSecurityException;
import com.adsapient.api_impl.exceptions.ConfigurationsException;
import com.adsapient.api_impl.usermanagment.Financial;
import com.adsapient.api_impl.usermanagment.UserImpl;
import com.adsapient.api_impl.usermanagment.role.RoleController;

import com.adsapient.shared.service.FinancialsService;

import com.adsapient.util.Msg;
import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.admin.ConfigurationConstants;
import com.adsapient.util.security.SecureAction;
import com.adsapient.gui.forms.FinancialManagmentActionForm;

import org.apache.log4j.Logger;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class FinancialManagmentAction extends SecureAction {
    private static Logger logger = Logger.getLogger(FinancialManagmentAction.class);
    private FinancialsService financialsService;

    protected void checkAccessRestriction(HttpServletRequest request,
        ActionForm actionForm) throws AdsapientSecurityException {
    }

    public ActionForward secureExecute(ActionMapping mapping,
        ActionForm actionForm, HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        FinancialManagmentActionForm form = (FinancialManagmentActionForm) actionForm;

        ActionMessages messages = new ActionMessages();

        UserImpl user = (UserImpl) request.getSession().getAttribute("user");

        if ("default".equalsIgnoreCase(form.getAction())) {
            if (!RoleController.ADMIN.equalsIgnoreCase(user.getRole())) {
                throw new ConfigurationsException("permissions.deny");
            }

            Financial financialOptions = (Financial) MyHibernateUtil.loadObject(Financial.class,
                    ConfigurationConstants.SYSTEM_FINANCIAL_ID);

            form.setHead(Msg.fetch("default.system.rates", request));
            form.initForm(financialOptions);

            form.setAction("defaultEdit");

            return mapping.findForward("success");
        }

        if ("defaultEdit".equalsIgnoreCase(form.getAction())) {
            if (!RoleController.ADMIN.equalsIgnoreCase(user.getRole())) {
                throw new ConfigurationsException("permissions.deny");
            }

            Financial financialOptions = (Financial) MyHibernateUtil.loadObject(Financial.class,
                    ConfigurationConstants.SYSTEM_FINANCIAL_ID);

            financialsService.updateOptions(financialOptions, form);

            MyHibernateUtil.updateObject(financialOptions);
            messages.add(Globals.MESSAGE_KEY,
                new ActionMessage("success.updated"));
            saveMessages(request, messages);

            return mapping.findForward("success");
        }

        if ("edit".equalsIgnoreCase(form.getAction())) {
            financialsService.updateUserRates(form);

            messages.add(Globals.MESSAGE_KEY,
                new ActionMessage("success.updated"));
            saveMessages(request, messages);

            form.setAction("view");
        }

        if ("view".equalsIgnoreCase(form.getAction())) {
            if (!RoleController.ADMIN.equalsIgnoreCase(user.getRole())) {
                throw new ConfigurationsException("permissions.deny");
            }

            if (form.getUserId() == null) {
                throw new ConfigurationsException("userid.must.present");
            }

            Financial financialOptions = (Financial) MyHibernateUtil.loadObjectWithCriteria(Financial.class,
                    "userId", new Integer(form.getUserId()));

            form.setHead(Msg.fetch("default.rates.for.user", request) +
                form.getUserId());
            form.initForm(financialOptions);

            form.setAction("edit");

            return mapping.findForward("success");
        }

        return mapping.findForward("empty");
    }

    public FinancialsService getFinancialsService() {
        return financialsService;
    }

    public void setFinancialsService(FinancialsService financialsService) {
        this.financialsService = financialsService;
    }
}
