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
package com.adsapient.gui;

import com.adsapient.adserver.AdserverServlet;
import com.adsapient.api.IMappable;
import com.adsapient.shared.AdsapientConstants;
import com.adsapient.shared.dao.HibernateEntityDao;
import com.adsapient.shared.mappable.*;
import com.adsapient.shared.service.CookieManagementService;
import com.adsapient.shared.service.FiltersService;
import com.adsapient.shared.service.RatesService;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Iterator;
import java.util.List;

public class ContextAwareGuiBean implements ApplicationContextAware {
    private static Logger logger = Logger.getLogger(ContextAwareGuiBean.class);

    public static ApplicationContext ctx;

    public static String application = null;

    private HibernateEntityDao hibernateEntityDao;
    private CookieManagementService cookieManagementService;
    private RatesService ratesService;


    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        ctx = applicationContext;
        if (SetupServlet.INSTALLING) {
            setupDefaultBanners();
        }
    }

    @SuppressWarnings("unchecked")
    public void setup() {
        try {
            System.setProperty("java.awt.headless", "true");

            Iterator applicationOptions = hibernateEntityDao.viewAll(
                    ApplicationOptImpl.class).iterator();

            while (applicationOptions.hasNext()) {
                ApplicationOptImpl option = (ApplicationOptImpl) applicationOptions
                        .next();

                if ("application".equalsIgnoreCase(option.getName())) {
                    application = option.getValue();
                }
            }

            List<IMappable> systemSettings = (List<IMappable>) hibernateEntityDao
                    .viewAll(SystemSettings.class);

            for (IMappable im : systemSettings) {
                SystemSettings ss = (SystemSettings) im;
                int ti = ss.getTypeid();

                switch (ti) {
                    case AdsapientConstants.BROWSER_TYPE_ID:
                        FiltersService.BrowsersMap.put(ss.getSskey(), ss.getSsvalue());

                        break;

                    case AdsapientConstants.OS_TYPE_ID:
                        FiltersService.SystemsMap.put(ss.getSskey(), ss.getSsvalue());

                        break;

                    case AdsapientConstants.LANGUAGE_TYPE_ID:
                        FiltersService.LangsMap.put(ss.getSskey(), ss.getSsvalue());

                        break;
                }
            }

            try {
                List<CountryAbbrEntity> countries = (List<CountryAbbrEntity>) hibernateEntityDao
                        .viewAllWithOrder(CountryAbbrEntity.class,
                                "countryName");
                FiltersService.fillCountryMap(countries);
            } catch (Exception e) {
                logger.error("Error getting countryList", e);
            }
        } catch (Exception ex) {
            logger.error("setup error", ex);
        }
    }

    @SuppressWarnings("unchecked")
    public void setupDefaultBanners() {
        CampainImpl campain = (CampainImpl) hibernateEntityDao.loadObject(
                CampainImpl.class,
                AdsapientConstants.DEFAULT_SYSTEM_CAMPAIN_ID);

        List<Size> sizes = (List<Size>) hibernateEntityDao.viewAll(Size.class);

        List<BannerImpl> banners = (List<BannerImpl>) hibernateEntityDao
                .viewWithCriteria(BannerImpl.class, "campainId",
                        AdsapientConstants.DEFAULT_SYSTEM_CAMPAIN_ID,
                        "bannerId");

        for (Size size : sizes) {
            boolean found = false;

            for (BannerImpl banner : banners)
                if (banner.getSizeId().equals(size.getId())) {
                    banner.setFile(size.getDefaultFileName());
                    banner.setStatus(BannerImpl.STATUS_DEFAULT);
                    hibernateEntityDao.updateObject(banner);
                    found = true;
                }

            if (!found) {
                try {
                    BannerImpl banner = new BannerImpl();
                    banner.setFile(size.getDefaultFileName());
                    banner.setFileName("default");
                    banner.setContentType("image/gif");
                    banner.setSizeId(size.getSizeId());
                    banner.setTypeId(size.getDefaultFileTypeId());
                    banner.setStatus(BannerImpl.STATUS_DEFAULT);
                    campain.udateBanner(banner);

                    logger.info(new StringBuffer("added new banner for size")
                            .append(size.getSize()).toString());

                    ratesService.createBannerRate(banner,
                            AdsapientConstants.DEFAULT_SYSTEM_CAMPAIN_ID);

                    hibernateEntityDao.save(banner);
                } catch (Exception e) {
                    logger.error("cannot save default banner", e);
                }
            }
        }
    }

    public static ApplicationContext getContext() {
        if (ctx != null) {
            return ctx;
        } else {
            return AdserverServlet.appContext;
        }
    }


    public HibernateEntityDao getHibernateEntityDao() {
        return hibernateEntityDao;
    }

    public void setHibernateEntityDao(HibernateEntityDao hibernateEntityDao) {
        this.hibernateEntityDao = hibernateEntityDao;
    }

    public CookieManagementService getCookieManagementService() {
        return cookieManagementService;
    }

    public void setCookieManagementService(CookieManagementService cookieManagementService) {
        this.cookieManagementService = cookieManagementService;
    }

    public RatesService getRatesService() {
        return ratesService;
    }

    public void setRatesService(RatesService ratesService) {
        this.ratesService = ratesService;
    }
}
