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
import com.adsapient.shared.exceptions.ConfigurationsException;
import com.adsapient.shared.mappable.ParameterImpl;
import com.adsapient.shared.service.CookieManagementService;
import com.adsapient.shared.dao.HibernateEntityDao;

import com.adsapient.gui.forms.ParameterForm;
import com.adsapient.gui.ContextAwareGuiBean;

import org.apache.log4j.Logger;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ParameterAction extends SecureAction {
    static Logger logger = Logger.getLogger(ParameterAction.class);
    private HibernateEntityDao hibernateEntityDao;

    public ActionForward secureExecute(ActionMapping mapping,
        ActionForm actionForm, HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        ParameterForm theForm = (ParameterForm) actionForm;
        ActionMessages messages = new ActionMessages();

        if ("add".equalsIgnoreCase(theForm.getAction())) {
            ParameterImpl param = theForm.getParameter(new ParameterImpl());

            String paramId = String.valueOf(hibernateEntityDao.save(param));
            theForm.setParameterId(paramId);

            messages.add(Globals.MESSAGE_KEY,
                new ActionMessage("success.param.added"));
            saveMessages(request, messages);

            theForm.setAction("init");
        }

        if ("edit".equalsIgnoreCase(theForm.getAction())) {
            ParameterImpl param = (ParameterImpl) hibernateEntityDao.loadObject(ParameterImpl.class,
                    new Integer(theForm.getParameterId()));
            param = theForm.getParameter(param);

            hibernateEntityDao.updateObject(param);

            messages.add(Globals.MESSAGE_KEY,
                new ActionMessage("success.param.saved"));
            saveMessages(request, messages);

            theForm.setAction("init");
        }

        if ("removeParameter".equalsIgnoreCase(theForm.getAction())) {
            ParameterImpl param = (ParameterImpl) hibernateEntityDao.loadObject(ParameterImpl.class,
                    new Integer(theForm.getParameterId()));

            hibernateEntityDao.removeObject(param);

            messages.add(Globals.MESSAGE_KEY,
                new ActionMessage("success.param.removed"));
            saveMessages(request, messages);

            theForm.setAction("init");
        }

        if ("init".equalsIgnoreCase(theForm.getAction())) {
            theForm.setAction("upload");

            theForm.setParametersCollection(hibernateEntityDao.viewAll(
                    ParameterImpl.class));

            return mapping.findForward("view");
        }

        if ("view".equalsIgnoreCase(theForm.getAction())) {
            theForm.setAction("edit");

            ParameterImpl param = (ParameterImpl) hibernateEntityDao.loadObject(ParameterImpl.class,
                    new Integer(theForm.getParameterId()));

            if (param == null) {
                throw new ConfigurationsException(
                    "Exeption in view parameter: parameter with id=" +
                    theForm.getParameterId() + "not found");
            }

            theForm.init(param);

            return mapping.findForward("edit");
        }

        if ("upload".equalsIgnoreCase(theForm.getAction())) {
            theForm.setAction("add");

            return mapping.findForward("select");
        }

        return mapping.findForward("view");
    }

    protected void checkAccessRestriction(HttpServletRequest request,
        ActionForm actionForm) throws AdsapientSecurityException {
    }

    public HibernateEntityDao getHibernateEntityDao() {
        return hibernateEntityDao;
    }

    public void setHibernateEntityDao(HibernateEntityDao hibernateEntityDao) {
        this.hibernateEntityDao = hibernateEntityDao;
    }
}
