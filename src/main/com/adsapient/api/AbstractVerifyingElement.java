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
package com.adsapient.api;

import com.adsapient.api.AdsapientCommand;

import com.adsapient.api_impl.managment.application.ApplicationManagment;
import com.adsapient.api_impl.usermanagment.UserImpl;
import com.adsapient.api_impl.usermanagment.role.RoleController;

import com.adsapient.util.admin.AdsapientConstants;
import com.adsapient.util.admin.ConfigurationConstants;

import javax.servlet.http.HttpServletRequest;


public abstract class AbstractVerifyingElement extends AdsapientCommand {
    private int stateId = ConfigurationConstants.DEFAULT_VERIFYING_STATE_ID;

    public AbstractVerifyingElement() {
        if (ApplicationManagment.check(ApplicationManagment.AdNetwork)) {
            this.stateId = ConfigurationConstants.DEFAULT_NOT_VERIFY_STATE_ID;
        }
    }

    public AbstractVerifyingElement(HttpServletRequest request) {
        super();

        UserImpl user = (UserImpl) request.getSession()
                                          .getAttribute(AdsapientConstants.USER);

        if (RoleController.HOSTEDSERVICE.equalsIgnoreCase(user.getRole())) {
            this.stateId = ConfigurationConstants.DEFAULT_VERIFYING_STATE_ID;
        }
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public int getStateId() {
        return stateId;
    }
}
