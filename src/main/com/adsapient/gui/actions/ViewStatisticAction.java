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
import com.adsapient.api_impl.statistic.StatisticImpl;
import com.adsapient.api_impl.usermanagment.UserImpl;
import com.adsapient.api_impl.usermanagment.role.RoleController;

import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.security.SecureAction;
import com.adsapient.gui.forms.ViewStatisticActionForm;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ViewStatisticAction extends SecureAction {
    Collection coll = new ArrayList();

    public ActionForward secureExecute(ActionMapping mapping,
        ActionForm actionForm, HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        UserImpl user = (UserImpl) request.getSession().getAttribute("user");
        ViewStatisticActionForm form = (ViewStatisticActionForm) actionForm;

        coll = MyHibernateUtil.viewWithCriteria(StatisticImpl.class,
                "advertizerId", user.getId(), "statisticId");

        if (coll != null) {
            form.setStatisticCollection(coll);
        }

        return mapping.findForward("success");
    }

    protected void checkAccessRestriction(HttpServletRequest request,
        ActionForm actionForm) throws AdsapientSecurityException {
        UserImpl user = (UserImpl) request.getSession().getAttribute("user");

        if (!RoleController.ADMIN.equalsIgnoreCase(user.getRole())) {
            throw new AdsapientSecurityException("bla bal bal ");
        }
    }
}
