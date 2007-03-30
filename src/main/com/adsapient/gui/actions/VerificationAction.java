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

import com.adsapient.gui.forms.VerificationActionForm;
import com.adsapient.shared.AdsapientConstants;
import com.adsapient.shared.exceptions.AdsapientSecurityException;
import com.adsapient.shared.mappable.UserImpl;
import com.adsapient.shared.service.UserManagementService;
import com.adsapient.shared.service.ValidationService;
import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class VerificationAction extends SecureAction {
    static Logger logger = Logger.getLogger(VerificationAction.class);
    private UserManagementService userManagementService;
    private ValidationService validationService;

    public ActionForward secureExecute(ActionMapping mapping,
                                       ActionForm actionForm, HttpServletRequest request,
                                       HttpServletResponse response) throws Exception {
        VerificationActionForm form = (VerificationActionForm) actionForm;

        UserImpl user = (UserImpl) request.getSession().getAttribute("user");

        if ("verify".equalsIgnoreCase(form.getAction())) {
            validationService.verifyState(form);

            form.setAction("view");
        }

        if ("view".equalsIgnoreCase(form.getAction())) {
            ValidationService.initVeriFyingForm(form, request);
        }
        if ("remove".equalsIgnoreCase(form.getAction())) {
            userManagementService.removeUser(Integer.valueOf(form.getElementId()));
            ActionMessages messages = new ActionMessages();
            messages.add(Globals.MESSAGE_KEY,
                    new ActionMessage("success.user.removed"));
            saveMessages(request, messages);
            form.setType("user");
            ValidationService.initVeriFyingForm(form, request);
            return mapping.findForward("success");
        }

        return mapping.findForward("success");
    }

    protected void checkAccessRestriction(HttpServletRequest request,
                                          ActionForm actionForm) throws AdsapientSecurityException {
        UserImpl user = (UserImpl) request.getSession()
                .getAttribute(AdsapientConstants.USER);

        if (!AdsapientConstants.ADMIN.equalsIgnoreCase(user.getRole())) {
            throw new AdsapientSecurityException("acceess deny");
        }
    }

    public UserManagementService getUserManagementService() {
        return userManagementService;
    }

    public void setUserManagementService(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    public ValidationService getValidationService() {
        return validationService;
    }

    public void setValidationService(ValidationService validationService) {
        this.validationService = validationService;
    }
}
