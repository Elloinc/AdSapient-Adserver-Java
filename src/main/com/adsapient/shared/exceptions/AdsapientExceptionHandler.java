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
package com.adsapient.shared.exceptions;

import com.adsapient.shared.exceptions.AdsapientSecurityException;
import com.adsapient.shared.exceptions.ConfigurationsException;
import com.adsapient.shared.exceptions.HibernateException;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ExceptionHandler;
import org.apache.struts.config.ExceptionConfig;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AdsapientExceptionHandler extends ExceptionHandler {
    public ActionForward execute(Exception e, ExceptionConfig arg1,
        ActionMapping mapping, ActionForm arg3, HttpServletRequest request,
        HttpServletResponse response) throws ServletException {
        if (e instanceof ConfigurationsException) {
            request.setAttribute("exceptionReason",
                "Configurations Exception !" + e.getMessage());

            return mapping.findForward("exception");
        }

        if (e instanceof HibernateException) {
            request.setAttribute("exceptionReason",
                "Hibernate Exception !" + e.getMessage());

            return mapping.findForward("exception");
        }

        if (e instanceof AdsapientSecurityException) {
            request.setAttribute("exceptionReason", e.getMessage());

            return mapping.findForward("exception");
        }

        request.setAttribute("exceptionReason", e.getMessage());

        return mapping.findForward("exception");
    }
}
