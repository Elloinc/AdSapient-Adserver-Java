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

import com.adsapient.shared.AdsapientConstants;

import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;


public abstract class AdsapientCommand
    implements AdsapientInterceptedSupportEntity {
    public final boolean add(ActionForm actionForm, HttpServletRequest request) {
        if (addCheck(actionForm, request)) {
            return resultAdd(actionForm, request);
        }

        return true;
    }

    public abstract boolean resultAdd(ActionForm actionForm,
        HttpServletRequest request);

    public boolean addCheck(ActionForm actionForm, HttpServletRequest request) {
        if (request.getSession().getAttribute(AdsapientConstants.GUEST) != null) {
            return false;
        }

        return true;
    }

    public final boolean view(ActionForm actionForm, HttpServletRequest request) {
        if (viewCheck(actionForm, request)) {
            return resultView(actionForm, request);
        }

        return true;
    }

    public abstract boolean resultEdit(ActionForm actionForm,
        HttpServletRequest request);

    public abstract boolean resultView(ActionForm actionForm,
        HttpServletRequest request);

    public final boolean delete(ActionForm actionForm,
        HttpServletRequest request) {
        if (deleteCheck(actionForm, request)) {
            return resultDelete(actionForm, request);
        }

        return true;
    }

    public final boolean edit(ActionForm actionForm, HttpServletRequest request) {
        if (editCheck(actionForm, request)) {
            return resultEdit(actionForm, request);
        }

        return true;
    }

    public boolean editCheck(ActionForm actionForm, HttpServletRequest request) {
        if (request.getSession().getAttribute(AdsapientConstants.GUEST) != null) {
            return false;
        }

        return true;
    }

    public abstract boolean resultDelete(ActionForm actionForm,
        HttpServletRequest request);

    public boolean deleteCheck(ActionForm actionForm, HttpServletRequest request) {
        if ((request != null) &&
                (request.getSession().getAttribute(AdsapientConstants.GUEST) != null)) {
            return false;
        }

        return true;
    }

    public final boolean init(ActionForm actionForm, HttpServletRequest request) {
        if (initCheck(actionForm, request)) {
            return resultInit(actionForm, request);
        }

        return true;
    }

    public abstract boolean resultInit(ActionForm actionForm,
        HttpServletRequest request);

    public boolean initCheck(ActionForm actionForm, HttpServletRequest request) {
        return true;
    }

    public boolean viewCheck(ActionForm actionForm, HttpServletRequest request) {
        return true;
    }
}
