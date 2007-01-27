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
import com.adsapient.api_impl.publisher.PlaceImpl;
import com.adsapient.api_impl.share.Category;

import com.adsapient.util.Msg;
import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.admin.EntityWrap;
import com.adsapient.util.admin.EntityWrapper;
import com.adsapient.util.hibernate.WrapperHelper;
import com.adsapient.util.security.SecureAction;
import com.adsapient.gui.forms.AdminsTuningActionForm;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AdminsTuningAction extends SecureAction {
    public ActionForward secureExecute(ActionMapping mapping,
        ActionForm actionForm, HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        AdminsTuningActionForm form = (AdminsTuningActionForm) actionForm;
        ActionMessages messages = new ActionMessages();

        if ("remove".equalsIgnoreCase(form.getAction())) {
            EntityWrap entity = new EntityWrap();

            entity.setEntityId(form.getEntityId());
            entity.setEntityType(form.getEntity());

            WrapperHelper.deleteEntity(entity);

            messages.add(Globals.MESSAGE_KEY,
                new ActionMessage("success.removed"));
            saveMessages(request, messages);
            form.setAction("view");
        }

        if ("add".equalsIgnoreCase(form.getAction())) {
            EntityWrap entity = new EntityWrap();
            entity.setEntityType(form.getEntity());
            entity.setEntityValue(form.getEntityValue());

            if (WrapperHelper.addEntity(entity) == true) {
                messages.add(Globals.MESSAGE_KEY,
                    new ActionMessage("success.updated"));
                saveMessages(request, messages);
            } else {
                if ("categorys".equalsIgnoreCase(form.getEntity())) {
                    messages.add(Globals.MESSAGE_KEY,
                        new ActionMessage("errors.cat.exists"));
                    saveMessages(request, messages);
                } else if ("positions".equalsIgnoreCase(form.getEntity())) {
                    messages.add(Globals.MESSAGE_KEY,
                        new ActionMessage("errors.pos.exists"));
                    saveMessages(request, messages);
                } else {
                    messages.add(Globals.MESSAGE_KEY,
                        new ActionMessage("errors.exists",
                            Msg.fetch(entity.getEntityType(), request)));
                    saveMessages(request, messages);
                }
            }

            form.setAction("view");
            form.setEntityValue("");
        }

        if ("view".equalsIgnoreCase(form.getAction())) {
            if ("categorys".equalsIgnoreCase(form.getEntity())) {
                form.setEntitysCollection(EntityWrapper.wrapCollection(
                        MyHibernateUtil.viewAll(Category.class)));
            }

            if ("positions".equalsIgnoreCase(form.getEntity())) {
                form.setEntitysCollection(EntityWrapper.wrapCollection(
                        MyHibernateUtil.viewAll(PlaceImpl.class)));
            }

            form.setAction("add");

            return mapping.findForward("view");
        }

        return mapping.findForward("view");
    }

    protected void checkAccessRestriction(HttpServletRequest request,
        ActionForm actionForm) throws AdsapientSecurityException {
    }
}
