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
import com.adsapient.api_impl.managment.DefaultCampainManager;
import com.adsapient.api_impl.share.Size;

import com.adsapient.util.Msg;
import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.admin.EntityWrap;
import com.adsapient.util.admin.EntityWrapper;
import com.adsapient.util.hibernate.WrapperHelper;
import com.adsapient.util.security.SecureAction;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class BannerSizeAction extends SecureAction {
    protected void checkAccessRestriction(HttpServletRequest request,
        ActionForm actionForm) throws AdsapientSecurityException {
    }

    public ActionForward secureExecute(ActionMapping mapping,
        ActionForm actionForm, HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        BannerSizeActionForm form = (BannerSizeActionForm) actionForm;
        ActionMessages messages = new ActionMessages();
        ActionErrors errors = new ActionErrors();

        if ("add".equalsIgnoreCase(form.getAction())) {
            Size newSize = form.getSize(new Size());

            if (MyHibernateUtil.loadObjectWithCriteria(Size.class, "size",
                        newSize.getSize()) == null) {
                String sizeId = String.valueOf(MyHibernateUtil.addObject(
                            newSize));
                form.setId(sizeId);

                DefaultCampainManager.checkDefaultSystemCampain();

                messages.add(Globals.MESSAGE_KEY,
                    new ActionMessage("success.bannersize.added"));
                saveMessages(request, messages);
            } else {
                errors.add(ActionErrors.GLOBAL_ERROR,
                    new ActionError("errors.exists",
                        Msg.fetch("size.name", request)));
                saveErrors(request, errors);

                return mapping.findForward("select");
            }

            form.setAction("init");
        }

        if ("edit".equalsIgnoreCase(form.getAction())) {
            Size size = (Size) MyHibernateUtil.loadObject(Size.class,
                    new Integer(form.getId()));

            size = form.getSize(size);
            MyHibernateUtil.updateObject(size);

            DefaultCampainManager.checkDefaultSystemCampain();

            messages.add(Globals.MESSAGE_KEY,
                new ActionMessage("success.bannersize.saved"));
            saveMessages(request, messages);

            form.setAction("init");
        }

        if ("remove".equalsIgnoreCase(form.getAction())) {
            EntityWrap entity = new EntityWrap();

            entity.setEntityId(form.getId());
            entity.setEntityType("size");

            WrapperHelper.deleteEntity(entity);

            messages.add(Globals.MESSAGE_KEY,
                new ActionMessage("success.bannersize.removed"));
            saveMessages(request, messages);

            form.setAction("init");
        }

        if ("init".equalsIgnoreCase(form.getAction())) {
            form.setAction("upload");

            form.setBannerSizeCollection(EntityWrapper.wrapCollection(
                    MyHibernateUtil.viewAll(Size.class)));

            return mapping.findForward("view");
        }

        if ("view".equalsIgnoreCase(form.getAction())) {
            form.setAction("edit");

            Size size = (Size) MyHibernateUtil.loadObject(Size.class,
                    new Integer(form.getId()));

            if (size == null) {
                throw new ConfigurationsException(
                    "Exeption in view size: size with id=" + form.getId() +
                    "not found");
            }

            form.init(size);

            return mapping.findForward("select");
        }

        if ("upload".equalsIgnoreCase(form.getAction())) {
            form.setAction("add");

            return mapping.findForward("select");
        }

        return mapping.findForward("view");
    }
}
