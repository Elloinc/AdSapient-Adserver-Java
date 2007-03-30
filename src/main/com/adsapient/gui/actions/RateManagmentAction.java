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

import com.adsapient.shared.exceptions.AdsapientSecurityException;
import com.adsapient.shared.mappable.RateImpl;
import com.adsapient.shared.mappable.UserImpl;

import com.adsapient.shared.service.FinancialsService;
import com.adsapient.shared.service.CookieManagementService;
import com.adsapient.shared.AdsapientConstants;
import com.adsapient.shared.dao.HibernateEntityDao;

import com.adsapient.gui.forms.RateManagmentActionForm;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class RateManagmentAction extends SecureAction {
    private FinancialsService financialsService;
    private HibernateEntityDao hibernateEntityDao;

    public ActionForward secureExecute(ActionMapping mapping,
        ActionForm actionForm, HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        RateManagmentActionForm form = (RateManagmentActionForm) actionForm;

        UserImpl user = (UserImpl) request.getSession().getAttribute("user");

        if ("view".equalsIgnoreCase(form.getAction())) {
            RateImpl rate = (RateImpl) hibernateEntityDao.loadObject(RateImpl.class,
                    new Integer(form.getRateId()));

            form.initForm(rate);

            form.setAction("edit");

            return mapping.findForward("success");
        }

        if ("edit".equalsIgnoreCase(form.getAction())) {
            RateImpl rate = new RateImpl();
            financialsService.updateRate(form, rate);

            ActionMessages messages = new ActionMessages();
            messages.add(Globals.MESSAGE_KEY,
                new ActionMessage("success.updated"));
            saveMessages(request, messages);

            return mapping.findForward("success");
        }

        return mapping.findForward("success");
    }

    protected void checkAccessRestriction(HttpServletRequest request,
        ActionForm actionForm) throws AdsapientSecurityException {
        UserImpl parentUser = (UserImpl) request.getSession()
                                                .getAttribute("parent");
        UserImpl user = (UserImpl) request.getSession().getAttribute("user");

        if ((parentUser == null) &&
                (!AdsapientConstants.ADMIN.equalsIgnoreCase(user.getRole()))) {
            throw new AdsapientSecurityException("access.deny!!");
        }
    }

    public FinancialsService getFinancialsService() {
        return financialsService;
    }

    public void setFinancialsService(FinancialsService financialsService) {
        this.financialsService = financialsService;
    }
}
