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
import com.adsapient.api_impl.exceptions.ConfigurationsException;
import com.adsapient.api_impl.resource.ResourceImpl;
import com.adsapient.api_impl.resource.ResourceUploadUtil;
import com.adsapient.api_impl.usermanagment.UserImpl;

import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.security.SecureAction;
import com.adsapient.gui.forms.ResourceUploadForm;

import org.apache.log4j.Logger;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ResourceUploadAction extends SecureAction {
    static Logger logger = Logger.getLogger(ResourceUploadAction.class);

    public ActionForward secureExecute(ActionMapping mapping,
        ActionForm actionForm, HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        ResourceUploadForm theForm = (ResourceUploadForm) actionForm;
        UserImpl user = (UserImpl) request.getSession().getAttribute("user");

        ActionMessages messages = new ActionMessages();

        if ("add".equalsIgnoreCase(theForm.getAction())) {
            theForm.setContentType(theForm.getTheFile().getContentType());

            ResourceImpl res = theForm.getResource(new ResourceImpl());

            if (theForm.getResourceName().length() == 0) {
                res.setResourceName(theForm.getTheFile().getFileName());
                theForm.setResourceName(res.getResourceName());
            }

            res.setResSize(theForm.getTheFile().getFileSize());
            theForm.setResSize(res.getResSize());

            String resId = String.valueOf(MyHibernateUtil.addObject(res));
            theForm.setResourceId(resId);

            ResourceUploadUtil.saveFile(theForm);
            theForm.getResource(res);

            MyHibernateUtil.updateObject(res);

            theForm.setAction("init");
        }

        if ("edit".equalsIgnoreCase(theForm.getAction())) {
            ResourceImpl res = theForm.getResource((ResourceImpl) MyHibernateUtil.loadObject(
                        ResourceImpl.class, new Integer(theForm.getResourceId())));

            if (!"".equalsIgnoreCase(theForm.getTheFile().getFileName())) {
                ResourceUploadUtil.removeFile(theForm.getFile());

                ResourceUploadUtil.saveFile(theForm);

                res.setFile(theForm.getFile());
                res.setResSize(theForm.getTheFile().getFileSize());

                res.setContentType(theForm.getTheFile().getContentType());
            }

            MyHibernateUtil.updateObject(res);
            theForm.setAction("init");

            messages.add(Globals.MESSAGE_KEY,
                new ActionMessage("success.resource.updated"));
            saveMessages(request, messages);
        }

        if ("removeResource".equalsIgnoreCase(theForm.getAction())) {
            ResourceUploadUtil.removeResource(new Integer(
                    theForm.getResourceId()));

            theForm.setAction("init");

            messages.add(Globals.MESSAGE_KEY,
                new ActionMessage("Publishing resource removed"));
            saveMessages(request, messages);
        }

        if ("init".equalsIgnoreCase(theForm.getAction())) {
            theForm.setAction("upload");

            theForm.setUserResources(ResourceUploadUtil.viewUserResources(
                    user.getId(), theForm.getOrderId()));

            return mapping.findForward("view");
        }

        if ("view".equalsIgnoreCase(theForm.getAction())) {
            theForm.setAction("edit");

            ResourceImpl res = (ResourceImpl) MyHibernateUtil.loadObject(ResourceImpl.class,
                    new Integer(theForm.getResourceId()));

            if (res == null) {
                throw new ConfigurationsException(
                    "Exeption in view resource: resource wit id=" +
                    theForm.getResourceId() + "not found");
            }

            theForm.init(res);

            return mapping.findForward("edit");
        }

        if ("upload".equalsIgnoreCase(theForm.getAction())) {
            theForm.setAction("add");
            theForm.setUserId(user.getId().toString());

            return mapping.findForward("select");
        }

        return mapping.findForward("view");
    }

    protected void checkAccessRestriction(HttpServletRequest request,
        ActionForm actionForm) throws AdsapientSecurityException {
    }
}
