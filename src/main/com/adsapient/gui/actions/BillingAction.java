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
import com.adsapient.api_impl.usermanagment.BillingInfoImpl;
import com.adsapient.api_impl.usermanagment.UserImpl;
import com.adsapient.api_impl.usermanagment.role.RoleController;

import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.admin.AdsapientConstants;
import com.adsapient.util.admin.ConfigurationConstants;
import com.adsapient.util.security.SecureAction;
import com.adsapient.gui.forms.BillingActionForm;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class BillingAction extends SecureAction {
    public ActionForward secureExecute(ActionMapping mapping,
        ActionForm actionForm, HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        ActionMessages messages = new ActionMessages();

        BillingActionForm form = (BillingActionForm) actionForm;
        UserImpl user = (UserImpl) request.getSession()
                                          .getAttribute(AdsapientConstants.USER);
        UserImpl guest = (UserImpl) request.getSession()
                                           .getAttribute(AdsapientConstants.GUEST);

        if ("view".equalsIgnoreCase(form.getAction())) {
            BillingInfoImpl billing = (BillingInfoImpl) MyHibernateUtil.loadObjectWithCriteria(BillingInfoImpl.class,
                    "userId", user.getId());
            form.init(billing);
            form.setAction("edit");

            return mapping.findForward("success");
        }

        if ("edit".equalsIgnoreCase(form.getAction())) {
            BillingInfoImpl billing = (BillingInfoImpl) MyHibernateUtil.loadObjectWithCriteria(BillingInfoImpl.class,
                    "userId", user.getId());

            if (guest == null) {
                MyHibernateUtil.updateObject(billing);
                form.updateBilling(billing, false);
            }

            form.init(billing);

            messages.add(Globals.MESSAGE_KEY,
                new ActionMessage("success.updated"));
            saveMessages(request, messages);

            return mapping.findForward("success");
        }

        if ("system".equalsIgnoreCase(form.getAction())) {
            if (!RoleController.ADMIN.equalsIgnoreCase(user.getRole())) {
                throw new SecurityException("permissions.deny");
            }

            BillingInfoImpl billing = (BillingInfoImpl) MyHibernateUtil.loadObjectWithCriteria(BillingInfoImpl.class,
                    "userId", ConfigurationConstants.SYSTEM_USER_ID);

            form.init(billing);
            form.setAction("systemEdit");

            return mapping.findForward("success");
        }

        if ("systemEdit".equalsIgnoreCase(form.getAction())) {
            if (!RoleController.ADMIN.equalsIgnoreCase(user.getRole())) {
                throw new SecurityException("permissions.deny");
            }

            BillingInfoImpl billing = (BillingInfoImpl) MyHibernateUtil.loadObjectWithCriteria(BillingInfoImpl.class,
                    "userId", ConfigurationConstants.SYSTEM_USER_ID);

            if (guest == null) {
                form.updateBilling(billing, true);
                MyHibernateUtil.updateObject(billing);
            }

            form.init(billing);

            return mapping.findForward("success");
        }

        return mapping.findForward("success");
    }

    protected void checkAccessRestriction(HttpServletRequest request,
        ActionForm actionForm) throws AdsapientSecurityException {
    }
}
