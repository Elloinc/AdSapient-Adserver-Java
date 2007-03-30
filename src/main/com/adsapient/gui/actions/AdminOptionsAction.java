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
import com.adsapient.gui.forms.AdminOptionsActionForm;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AdminOptionsAction extends SecureAction {
    protected void checkAccessRestriction(HttpServletRequest request,
        ActionForm actionForm) throws AdsapientSecurityException {
    }

    public ActionForward secureExecute(ActionMapping mapping,
        ActionForm actionForm, HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        AdminOptionsActionForm form = (AdminOptionsActionForm) actionForm;

        if ("edit".equalsIgnoreCase(form.getAction())) {
            form.updateAdminOptions();
            form.setAction("init");
        }

        if ("init".equalsIgnoreCase(form.getAction())) {
            form.initConnections();
            form.setAction("edit");

            return mapping.findForward("view");
        }

        if ("default".equalsIgnoreCase(form.getAction())) {
            form.initDefaultOptions();
            form.setAction("defEdit");

            return mapping.findForward("default");
        }

        if ("defEdit".equalsIgnoreCase(form.getAction())) {
            form.updateSysSettings();

            return mapping.findForward("default");
        }

        return mapping.findForward("view");
    }
}
