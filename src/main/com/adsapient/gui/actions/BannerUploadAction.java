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

import com.adsapient.api.Banner;

import com.adsapient.api_impl.advertizer.BannerImpl;
import com.adsapient.api_impl.advertizer.BannerUploadUtil;
import com.adsapient.api_impl.advertizer.CampainImpl;
import com.adsapient.api_impl.exceptions.AdsapientSecurityException;
import com.adsapient.api_impl.managment.BannerManager;
import com.adsapient.api_impl.managment.StatisticManager;
import com.adsapient.api_impl.managment.plugin.BannerPluginFactory;
import com.adsapient.api_impl.usermanagment.UserImpl;
import com.adsapient.api_impl.usermanagment.role.RoleController;

import com.adsapient.util.LabelValueUtil;
import com.adsapient.util.Msg;
import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.admin.AdsapientConstants;
import com.adsapient.util.security.SecureAction;
import com.adsapient.gui.forms.BannerUploadFormAction;

import org.apache.log4j.Logger;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class BannerUploadAction extends SecureAction {
    static Logger logger = Logger.getLogger(BannerUploadAction.class);

    public ActionForward secureExecute(ActionMapping mapping,
        ActionForm actionForm, HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        BannerUploadFormAction form = (BannerUploadFormAction) actionForm;
        UserImpl user = (UserImpl) request.getSession()
                                          .getAttribute(AdsapientConstants.USER);
        form.setHsr(request);

        int userId = user.getId();

        if (RoleController.ADMIN.equalsIgnoreCase(user.getRole())) {
            boolean flag = false;
            String strUserId = request.getParameter("userId");

            if (strUserId != null) {
                try {
                    userId = Integer.parseInt(strUserId);
                    flag = true;
                } catch (NumberFormatException e) {
                    logger.error("", e);
                }
            }

            if (!flag) {
                Object objUserId = request.getAttribute("userId");

                if (objUserId != null) {
                    try {
                        userId = (Integer) objUserId;
                    } catch (Exception e) {
                        logger.error("", e);
                    }
                }
            }
        }

        ActionMessages messages = new ActionMessages();

        if ("error".equalsIgnoreCase(form.getAction())) {
            if ((form.getCampainName() == null) ||
                    (form.getCampainName().trim().length() == 0)) {
                return mapping.findForward("empty");
            } else {
                form.setAction("campainBanners");
            }
        }

        if ("addDefault".equalsIgnoreCase(form.getAction())) {
            BannerImpl banner = new BannerImpl();

            Collection sizes = null;
            Map<String, Object> params = new HashMap<String, Object>();

            if (RoleController.PUBLISHER.equalsIgnoreCase(user.getRole())) {
                banner.setStatus(BannerImpl.STATUS_PUBLISHER_DEFAULT);

                params.put("STATUS",
                    new Integer(BannerImpl.STATUS_PUBLISHER_DEFAULT));
                params.put("USERID", user.getId());
                sizes = MyHibernateUtil.executeHQLQuery("getSize4DefaultBannersPublisher",
                        params);
            } else {
                banner.setStatus(BannerImpl.STATUS_DEFAULT);

                params.put("STATUS", new Integer(BannerImpl.STATUS_DEFAULT));
                sizes = MyHibernateUtil.executeHQLQuery("getSize4DefaultBanners",
                        params);
            }

            form.setSizesCollection(LabelValueUtil.fillSizeValueLabel(sizes));

            banner.setUserId(user.getId());
            banner.initForm(form);

            form.setAction("add");

            return mapping.findForward("edit");
        }

        if ("add".equalsIgnoreCase(form.getAction())) {
            if ((form.getBannerName() == null) ||
                    (form.getBannerName().length() == 0)) {
                return mapping.findForward("empty");
            } else {
                BannerManager bannerManager = new BannerManager();

                if (bannerManager.checkBanners(form.getBannerName()).size() == 0) {
                    Banner banner = BannerPluginFactory.createBanner(form);
                    banner.setUserId(userId);
                    banner.add(form, request);
                    messages.add(Globals.MESSAGE_KEY,
                        new ActionMessage("success.banner.saved"));
                    saveMessages(request, messages);

                    if ((banner.getStatus() == BannerImpl.STATUS_DEFAULT) ||
                            (banner.getStatus() == BannerImpl.STATUS_PUBLISHER_DEFAULT)) {
                        form.setAction("init");

                        return mapping.findForward("list_default");
                    }
                } else {
                    messages.add(Globals.ERROR_KEY,
                        new ActionMessage("errors.exists",
                            Msg.fetch("banner.name", request)));
                    saveErrors(request, messages);

                    return mapping.findForward("edit");
                }
            }
        }

        if ("campainBanners".equalsIgnoreCase(form.getAction())) {
            if ("error".equalsIgnoreCase(form.getAction())) {
                return mapping.findForward("empty");
            }

            if (form.getCampainId() == 0) {
                return mapping.findForward("empty");
            }

            form.setUserBanners(BannerUploadUtil.viewCampainBanners(
                    form.getCampainId(), true));

            form.setAction("upload");

            return mapping.findForward("campaignbanners_view");
        }

        if ("edit".equalsIgnoreCase(form.getAction())) {
            Banner banner = BannerPluginFactory.loadBannerById(form.getBannerId());
            banner.edit(form, request);

            messages.add(Globals.MESSAGE_KEY,
                new ActionMessage("success.updated"));
            saveMessages(request, messages);

            if ((banner.getStatus() == BannerImpl.STATUS_DEFAULT) ||
                    (banner.getStatus() == BannerImpl.STATUS_PUBLISHER_DEFAULT)) {
                form.setAction("init");

                return mapping.findForward("list_default");
            }
        }

        if ("removeBanner".equalsIgnoreCase(form.getAction())) {
            Banner banner = BannerPluginFactory.loadBannerById(form.getBannerId());
            boolean isDefault = ((banner.getStatus() == BannerImpl.STATUS_DEFAULT) ||
                (banner.getStatus() == BannerImpl.STATUS_PUBLISHER_DEFAULT))
                ? true : false;

            banner.delete(form, request);

            messages.add(Globals.MESSAGE_KEY,
                new ActionMessage("success.banner.removed"));
            saveMessages(request, messages);

            if (isDefault) {
                form.setAction("init");

                return mapping.findForward("list_default");
            }
        }

        if ("resetStatistic".equalsIgnoreCase(form.getAction())) {
            Banner banner = BannerPluginFactory.loadBannerById(form.getBannerId());

            if (banner != null) {
                StatisticManager.remove(banner);

                messages.add(Globals.MESSAGE_KEY,
                    new ActionMessage("success.stat.reset"));
                saveMessages(request, messages);
            } else {
                logger.error(
                    "trying to remove statistic but cant load banner with id=" +
                    form.getBannerId());
            }

            form.setAction("init");
        }

        if ("init".equalsIgnoreCase(form.getAction())) {
            form.setAction("upload");
            form.setUserBanners(BannerUploadUtil.viewUserBanners(
                    BannerImpl.class, userId, true));

            return mapping.findForward("view");
        }

        if ("view".equalsIgnoreCase(form.getAction())) {
            Banner banner = BannerPluginFactory.loadBannerById(form.getBannerId());
            banner.view(form, request);

            if ((banner.getStatus() == BannerImpl.STATUS_DEFAULT) ||
                    (banner.getStatus() == BannerImpl.STATUS_PUBLISHER_DEFAULT)) {
                Collection sizes = null;
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("SIZEID", banner.getSizeId());

                if (banner.getStatus() == BannerImpl.STATUS_PUBLISHER_DEFAULT) {
                    params.put("STATUS",
                        new Integer(BannerImpl.STATUS_PUBLISHER_DEFAULT));
                    params.put("USERID", banner.getUserId());
                    sizes = MyHibernateUtil.executeHQLQuery("getSize4DefaultBannersPublisherEdit",
                            params);
                } else {
                    params.put("STATUS", new Integer(BannerImpl.STATUS_DEFAULT));
                    sizes = MyHibernateUtil.executeHQLQuery("getSize4DefaultBannersEdit",
                            params);
                }

                form.setSizesCollection(LabelValueUtil.fillSizeValueLabel(sizes));
            }

            return mapping.findForward("edit");
        }

        if ("afterupload".equalsIgnoreCase(form.getAction())) {
            form.setAction("add");

            CampainImpl campain = (CampainImpl) MyHibernateUtil.loadObject(CampainImpl.class,
                    form.getCampainId());
            Banner banner;

            banner = BannerPluginFactory.createBanner(form);

            campain.udateBanner(banner);

            banner.initForm(form);

            return mapping.findForward("edit");
        }

        if ("upload".equalsIgnoreCase(form.getAction())) {
            new BannerImpl().init(actionForm, request);

            return mapping.findForward("select");
        }

        return mapping.findForward("view");
    }

    protected void checkAccessRestriction(HttpServletRequest request,
        ActionForm actionForm) throws AdsapientSecurityException {
    }
}
