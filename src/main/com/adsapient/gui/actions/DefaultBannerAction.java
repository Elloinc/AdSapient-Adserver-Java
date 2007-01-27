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

import com.adsapient.api_impl.advertizer.BannerImpl;
import com.adsapient.api_impl.advertizer.BannerUploadUtil;
import com.adsapient.api_impl.exceptions.AdsapientSecurityException;
import com.adsapient.api_impl.managment.BannerManager;
import com.adsapient.api_impl.managment.DefaultCampainManager;
import com.adsapient.api_impl.share.Size;
import com.adsapient.api_impl.usermanagment.UserImpl;
import com.adsapient.api_impl.usermanagment.role.RoleController;

import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.admin.AdsapientConstants;
import com.adsapient.util.security.SecureAction;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class DefaultBannerAction extends SecureAction {
    protected void checkAccessRestriction(HttpServletRequest request,
        ActionForm actionForm) throws AdsapientSecurityException {
    }

    public ActionForward secureExecute(ActionMapping mapping,
        ActionForm actionForm, HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        DefaultBannerActionForm form = (DefaultBannerActionForm) actionForm;

        ActionMessages messages = new ActionMessages();

        UserImpl user = (UserImpl) request.getSession()
                                          .getAttribute(AdsapientConstants.USER);

        if ("edit".equalsIgnoreCase(form.getAction())) {
            BannerImpl banner = DefaultCampainManager.getBannerFromSystemDefaultCampaignBySizeId(new Integer(
                        form.getId()));

            banner.setUrl(form.getURL());
            banner.setAltText(form.getAltText());
            banner.setStatusBartext(form.getStatusBartext());
            banner.setTypeId(form.getTypeId());

            if (user.getRole().equalsIgnoreCase(RoleController.ADMIN)) {
                banner.setStatus(BannerImpl.STATUS_DEFAULT);
            } else if (user.getRole().equalsIgnoreCase(RoleController.PUBLISHER)) {
                banner.setStatus(BannerImpl.STATUS_PUBLISHER_DEFAULT);
            }

            banner.setSizeId(form.getSizeId());
            banner.setSize((Size) MyHibernateUtil.loadObject(Size.class,
                    banner.getSizeId()));

            MyHibernateUtil.updateObject(banner);

            if (!"".equalsIgnoreCase(form.getFile().getFileName())) {
                String filePath = BannerUploadUtil.saveDefaultBannerFile(form.getFile());

                if (!"".equalsIgnoreCase(filePath)) {
                    banner.setFile(filePath);

                    MyHibernateUtil.updateObject(banner);
                }
            }

            DefaultCampainManager.checkDefaultSystemCampain();


            messages.add(Globals.MESSAGE_KEY,
                new ActionMessage("success.updated"));
            saveMessages(request, messages);

            form.setAction("init");
        }

        if ("init".equalsIgnoreCase(form.getAction()) ||
                "removeBanner".equalsIgnoreCase(form.getAction())) {
            if (user.getRole().equalsIgnoreCase(RoleController.ADMIN)) {
                form.setBanners(BannerManager.getBanners(
                        BannerImpl.STATUS_DEFAULT));

                Map<String, Object> params = new HashMap<String, Object>();
                params.put("STATUS", new Integer(BannerImpl.STATUS_DEFAULT));

                List count = (List) MyHibernateUtil.executeHQLQuery("countSizes4DefaultBanners",
                        params);

                if ((count != null) && (count.size() > 0)) {
                    Long num = (Long) count.get(0);

                    if (num.longValue() != 0) {
                        form.setCreateAllowed(true);
                    } else {
                        form.setCreateAllowed(false);
                    }
                }
            } else if (user.getRole().equalsIgnoreCase(RoleController.PUBLISHER)) {
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("STATUS",
                    new Integer(BannerImpl.STATUS_PUBLISHER_DEFAULT));
                params.put("USERID", user.getId());

                List count = (List) MyHibernateUtil.executeHQLQuery("countSizes4DefaultBannersPublisher",
                        params);

                if ((count != null) && (count.size() > 0)) {
                    Long num = (Long) count.get(0);

                    if (num.longValue() != 0) {
                        form.setCreateAllowed(true);
                    } else {
                        form.setCreateAllowed(false);
                    }
                }

                form.setBanners(BannerManager.getBanners4User(user.getId(),
                        BannerImpl.STATUS_PUBLISHER_DEFAULT));
            }

            return mapping.findForward("list");
        }

        if ("view".equalsIgnoreCase(form.getAction())) {
            form.setAction("edit");

            Integer bannerId = Integer.parseInt(form.getId(), 10);
            BannerImpl banner = (BannerImpl) MyHibernateUtil.loadObject(BannerImpl.class,
                    bannerId);

            form.setAltText(banner.getAltText());
            form.setStatusBartext(banner.getStatusBartext());
            form.setURL(banner.getUrl());
            form.setFileName(banner.getFileName());
            form.setTypeId(banner.getTypeId());

            form.setSizeId(banner.getSizeId());

            return mapping.findForward("viewBanner");
        }

        return null;
    }
}
