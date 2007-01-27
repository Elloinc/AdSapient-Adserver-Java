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

import com.adsapient.api_impl.admin.SystemCurrency;
import com.adsapient.api_impl.exceptions.AdsapientSecurityException;
import com.adsapient.api_impl.usermanagment.UserImpl;
import com.adsapient.api_impl.usermanagment.role.RoleController;

import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.admin.AdsapientConstants;
import com.adsapient.util.admin.email.EmailFormer;
import com.adsapient.util.financial.MoneyTransformer;
import com.adsapient.util.security.SecureAction;
import com.adsapient.gui.forms.TransactionRequestActionForm;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class TransactionRequestAction extends SecureAction {
    public ActionForward secureExecute(ActionMapping mapping,
        ActionForm actionForm, HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        TransactionRequestActionForm form = (TransactionRequestActionForm) actionForm;
        UserImpl user = (UserImpl) request.getSession()
                                          .getAttribute(AdsapientConstants.USER);

        ActionMessages messages = new ActionMessages();

        form.setRequest(request);

        if (RoleController.PUBLISHER.equalsIgnoreCase(form.getAction())) {
            EmailFormer.sendPublisherRequest(user);

            return mapping.findForward("success");
        }

        if ("system".equalsIgnoreCase(form.getType())) {
            form.setSelectedCurrency(MoneyTransformer.getSystemCurrency()
                                                     .getCurrencyCode());
            form.setType("edit");
            form.setAction("system");

            return mapping.findForward("system");
        }

        if ("edit".equalsIgnoreCase(form.getType())) {
            Collection currencyCollection = MyHibernateUtil.viewAll(SystemCurrency.class);
            Iterator currencyIterator = currencyCollection.iterator();

            while (currencyIterator.hasNext()) {
                SystemCurrency currency = (SystemCurrency) currencyIterator.next();

                if (currency.getCurrencyCode()
                                .equalsIgnoreCase(form.getSelectedCurrency())) {
                    currency.setSystem(true);
                } else {
                    currency.setSystem(false);
                }

                MyHibernateUtil.updateObject(currency);
            }

            messages.add(Globals.MESSAGE_KEY,
                new ActionMessage("success.updated"));
            saveMessages(request, messages);

            return mapping.findForward("billing");
        }

        return mapping.findForward("nothink");
    }

    protected void checkAccessRestriction(HttpServletRequest request,
        ActionForm actionForm) throws AdsapientSecurityException {
    }
}
