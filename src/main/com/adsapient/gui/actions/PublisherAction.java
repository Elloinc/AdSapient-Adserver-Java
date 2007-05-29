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
import com.adsapient.shared.mappable.UserImpl;
import com.adsapient.shared.service.UserManagementService;

import com.adsapient.shared.AdsapientConstants;
import com.adsapient.gui.forms.PublisherActionForm;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class PublisherAction extends SecureAction {
    private Collection sitesCollection = new ArrayList();
    private UserManagementService userManagementService;
    private UserImpl user;

    public ActionForward secureExecute(ActionMapping mapping,
        ActionForm actionForm, HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        PublisherActionForm form = (PublisherActionForm) actionForm;

        user = (UserImpl) request.getSession().getAttribute("user");

        int userId = user.getId();

        if (AdsapientConstants.ADMIN.equalsIgnoreCase(user.getRole())) {
            try {
                userId = Integer.parseInt(request.getParameter("userId"));
            } catch (Exception e) {
                try {
                    userId = (Integer) request.getAttribute("userId");
                } catch (Exception e1) {
                }
            }
        }

        sitesCollection = userManagementService.viewPublisherSites(userId);

        form.setSitesCollection(sitesCollection);

        if ("viewPlaces".equalsIgnoreCase(form.getAction())) {
            return mapping.findForward("viewPlaces");
        }

        return mapping.findForward("success");
    }

    protected void checkAccessRestriction(HttpServletRequest request,
        ActionForm actionForm) throws AdsapientSecurityException {
        UserImpl user = (UserImpl) request.getSession().getAttribute("user");

        if (!AdsapientConstants.ADMIN.equals(user.getRole()) &&
                !AdsapientConstants.PUBLISHER.equals(user.getRole())) {
            throw new AdsapientSecurityException("acsess.denyide");
        }
    }

    public UserManagementService getUserManagementService() {
        return userManagementService;
    }

    public void setUserManagementService(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }
}
