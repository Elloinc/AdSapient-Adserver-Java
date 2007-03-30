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

import com.adsapient.shared.mappable.BannerImpl;
import com.adsapient.shared.mappable.CampainImpl;
import com.adsapient.shared.mappable.UserImpl;
import com.adsapient.shared.service.*;
import com.adsapient.shared.exceptions.AdsapientSecurityException;
import com.adsapient.shared.AdsapientConstants;
import com.adsapient.shared.dao.HibernateEntityDao;

import com.adsapient.shared.service.LabelService;
import com.adsapient.shared.service.I18nService;
import com.adsapient.gui.forms.BannerUploadActionForm;

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

    private BannerManagementService bannerManagementService;
    private HibernateEntityDao hibernateEntityDao;
    private PluginService pluginService;
    private LabelService labelService;
    private UploadService uploadService;

    public ActionForward secureExecute(ActionMapping mapping,
        ActionForm actionForm, HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        BannerUploadActionForm form = (BannerUploadActionForm) actionForm;
        UserImpl user = (UserImpl) request.getSession()
                                          .getAttribute(AdsapientConstants.USER);
        form.setHsr(request);

        int userId = user.getId();

        if (AdsapientConstants.ADMIN.equalsIgnoreCase(user.getRole())) {
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

            if (AdsapientConstants.PUBLISHER.equalsIgnoreCase(user.getRole())) {
                banner.setStatus(BannerImpl.STATUS_PUBLISHER_DEFAULT);

                params.put("STATUS",
                    new Integer(BannerImpl.STATUS_PUBLISHER_DEFAULT));
                params.put("USERID", user.getId());
                sizes = hibernateEntityDao.executeHQLQuery("getSize4DefaultBannersPublisher",
                        params);
            } else {
                banner.setStatus(BannerImpl.STATUS_DEFAULT);

                params.put("STATUS", new Integer(BannerImpl.STATUS_DEFAULT));
                sizes = hibernateEntityDao.executeHQLQuery("getSize4DefaultBanners",
                        params);
            }

            form.setSizesCollection(labelService.fillSizeValueLabel(sizes));

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
                if (bannerManagementService.checkBanners(form.getBannerName()).size() == 0) {
                    Banner banner = pluginService.createBanner(form);
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
                            I18nService.fetch("banner.name", request)));
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

            form.setUserBanners(uploadService.viewCampainBanners(
                    form.getCampainId(), true));

            form.setAction("upload");

            return mapping.findForward("campaignbanners_view");
        }

        if ("edit".equalsIgnoreCase(form.getAction())) {
            Banner banner = pluginService.loadBannerById(form.getBannerId());
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
            Banner banner = pluginService.loadBannerById(form.getBannerId());
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
            Banner banner = pluginService.loadBannerById(form.getBannerId());

            if (banner != null) {

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
            form.setUserBanners(uploadService.viewUserBanners(
                    BannerImpl.class, userId, true));

            return mapping.findForward("view");
        }

        if ("view".equalsIgnoreCase(form.getAction())) {
            Banner banner = pluginService.loadBannerById(form.getBannerId());
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
                    sizes = hibernateEntityDao.executeHQLQuery("getSize4DefaultBannersPublisherEdit",
                            params);
                } else {
                    params.put("STATUS", new Integer(BannerImpl.STATUS_DEFAULT));
                    sizes = hibernateEntityDao.executeHQLQuery("getSize4DefaultBannersEdit",
                            params);
                }

                form.setSizesCollection(labelService.fillSizeValueLabel(sizes));
            }

            return mapping.findForward("edit");
        }

        if ("afterupload".equalsIgnoreCase(form.getAction())) {
            form.setAction("add");

            CampainImpl campain = (CampainImpl) hibernateEntityDao.loadObject(CampainImpl.class,
                    form.getCampainId());
            Banner banner;

            banner = pluginService.createBanner(form);

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

    public BannerManagementService getBannerManagementService() {
        return bannerManagementService;
    }

    public void setBannerManagementService(BannerManagementService bannerManagementService) {
        this.bannerManagementService = bannerManagementService;
    }

    public HibernateEntityDao getHibernateEntityDao() {
        return hibernateEntityDao;
    }

    public void setHibernateEntityDao(HibernateEntityDao hibernateEntityDao) {
        this.hibernateEntityDao = hibernateEntityDao;
    }

    public PluginService getPluginService() {
        return pluginService;
    }

    public void setPluginService(PluginService pluginService) {
        this.pluginService = pluginService;
    }

    public LabelService getLabelService() {
        return labelService;
    }

    public void setLabelService(LabelService labelService) {
        this.labelService = labelService;
    }

    public UploadService getUploadService() {
        return uploadService;
    }

    public void setUploadService(UploadService uploadService) {
        this.uploadService = uploadService;
    }
}
