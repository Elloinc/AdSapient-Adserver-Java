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
package com.adsapient.shared.service;

import com.adsapient.adserver.beans.AdserverModel;
import com.adsapient.api.FilterInterface;
import com.adsapient.api.IMappable;
import com.adsapient.shared.mappable.BannerRepresentation;
import com.adsapient.shared.dao.HibernateEntityDao;
import com.adsapient.shared.mappable.*;
import com.adsapient.shared.AdsapientConstants;
import com.adsapient.gui.ContextAwareGuiBean;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;

import java.io.File;
import java.util.*;

public class BannerManagementService {
    static Logger logger = Logger.getLogger(BannerManagementService.class);
    private String pathToBanners;
    private HibernateEntityDao hibernateEntityDao;
    private CookieManagementService cookieManagementService;


    public void setup() {
        AdsapientConstants.PATH_TO_BANNERS = pathToBanners;
    }

    public void getBannersFromActiveCampaigns(AdserverModel adserverModel) {
        List<IMappable>activeCampaigns = (List<IMappable>) hibernateEntityDao.viewAll(CampainImpl.class);
        List<BannerImpl>activeBanners = new ArrayList<BannerImpl>();
        Map<Integer, List<FilterInterface>> mapFilters = getBannerFilters();
        for (IMappable campaign : activeCampaigns) {
            CampainImpl camp = (CampainImpl) campaign;
            if (camp.getStateId() == AdsapientConstants.DEFAULT_CAMPAIN_STATE_ID) continue;
            if (!camp.getUserDefineCampainStateId().equals(AdsapientConstants.DEFAULT_USER_DEFAIN_CAMPAIN_STATE)) {
                continue;
            }
            for (BannerImpl banner : camp.getBanners()) {
                if (banner.getStatus() == AdsapientConstants.DEFAULT_ACTIVE_CAMPAIN_STATE_ID) {
                    banner.setFilters(mapFilters.get(banner.getBannerId()));
                    activeBanners.add(banner);
                }
            }
        }
        adserverModel.setBannersPool(activeBanners);
    }

    public void getDefaultBanners(AdserverModel adserverModel) {
        Map<Integer, IMappable> mapDefault = new HashMap<Integer, IMappable>();

        List<BannerImpl> banners = hibernateEntityDao
                .getBannerList(BannerImpl.STATUS_DEFAULT);

        if ((banners == null) || (banners.size() == 0)) {
            return;
        }

        for (BannerImpl im : banners) {
            BannerImpl banner = (BannerImpl) im;

            if ((banner.getSizeId() != null)
                    && (banner.getSizeId().intValue() == 0)) {
                adserverModel.setDefaultBanner(banner);

                break;
            } else {
                mapDefault.put(im.getId(), banner);
            }
        }

        adserverModel.setDefaultBannersPool(mapDefault);
    }

    public void getDefaultPublisherBanners(AdserverModel adserverModel) {
        Map<Integer, IMappable> res = new HashMap<Integer, IMappable>();
        Map<Integer, IMappable> resMob = new HashMap<Integer, IMappable>();
        List<BannerImpl> banners = hibernateEntityDao
                .getBannerList(BannerImpl.STATUS_PUBLISHER_DEFAULT);

        for (BannerImpl banner : banners) {
            res.put(banner.getBannerId(), banner);
        }

        adserverModel.setDefaultPublisherBannerPool(res);
    }

    public HibernateEntityDao getHibernateEntityDao() {
        return hibernateEntityDao;
    }

    public void setHibernateEntityDao(HibernateEntityDao hibernateEntityDao) {
        this.hibernateEntityDao = hibernateEntityDao;
    }

    public String getPathToBanners() {
        return pathToBanners;
    }

    public void setPathToBanners(String pathToBanners) {
        this.pathToBanners = pathToBanners;
    }

    @SuppressWarnings("unchecked")
    public Map<Integer, List<FilterInterface>> getBannerFilters() {
        List<FilterInterface> timeFilters = (List<FilterInterface>) hibernateEntityDao
                .viewWithNullCriteria(TimeFilter.class, "bannerId", false);
        List<FilterInterface> dateFilters = (List<FilterInterface>) hibernateEntityDao
                .viewWithNullCriteria(DateFilter.class, "bannerId", false);
        List<FilterInterface> systemsFilters = (List<FilterInterface>) hibernateEntityDao
                .viewWithNullCriteria(SystemsFilter.class, "bannerId", false);
        List<FilterInterface> parametersFilters = (List<FilterInterface>) hibernateEntityDao
                .viewWithNullCriteria(ParametersFilter.class, "bannerId", false);
        List<FilterInterface> keywordsFilters = (List<FilterInterface>) hibernateEntityDao
                .viewWithNullCriteria(KeywordsFilter.class, "bannerId", false);
        List<FilterInterface> behaviorFilters = (List<FilterInterface>) hibernateEntityDao
                .viewWithNullCriteria(BehaviorFilter.class, "bannerId", false);
        List<FilterInterface> geoFilters = (List<FilterInterface>) hibernateEntityDao
                .viewWithNullCriteria(GeoFilter.class, "bannerId", false);
        List<FilterInterface> trafficFilters = (List<FilterInterface>) hibernateEntityDao
                .viewWithNullCriteria(TrafficFilter.class, "bannerId", false);
        List<FilterInterface> contentFilters = (List<FilterInterface>) hibernateEntityDao
                .viewWithNullCriteria(ContentFilter.class, "bannerId", false);

        Map<Integer, List<FilterInterface>> map = new HashMap<Integer, List<FilterInterface>>();

        allocateFilters2BannerId(map, timeFilters);
        allocateFilters2BannerId(map, dateFilters);
        allocateFilters2BannerId(map, systemsFilters);
        allocateFilters2BannerId(map, parametersFilters);
        allocateFilters2BannerId(map, keywordsFilters);
        allocateFilters2BannerId(map, behaviorFilters);
        allocateFilters2BannerId(map, geoFilters);
        allocateFilters2BannerId(map, trafficFilters);
        allocateFilters2BannerId(map, contentFilters);

        return map;
    }

    private void allocateFilters2BannerId(
            Map<Integer, List<FilterInterface>> map,
            List<FilterInterface> filters) {
        if ((filters == null) || (filters.size() == 0)) {
            return;
        }

        for (FilterInterface filter : filters) {
            List<FilterInterface> list = map.get(filter.getBannerId());

            if (list == null) {
                list = new ArrayList<FilterInterface>();
            }

            list.add(filter);
            map.put(filter.getBannerId(), list);
        }
    }

    public Collection getBanners(int status) {
        Collection<BannerRepresentation> resultCollection = new ArrayList<BannerRepresentation>();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("STATUS", new Integer(status));

        Collection<BannerImpl> banners = (Collection<BannerImpl>) hibernateEntityDao
                .executeHQLQuery("loadBanners", params);

        for (BannerImpl banner : banners) {
            if ((banner.getSizeId() != null)
                    && (banner.getSizeId().intValue() == 0)) {
                continue;
            }

            BannerRepresentation represent = new BannerRepresentation(banner);
            resultCollection.add(represent);
        }

        return resultCollection;
    }

    public Collection getBanners4User(Integer userId, int status) {
        Collection<BannerRepresentation> resultCollection = new ArrayList<BannerRepresentation>();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("USERID", userId);
        params.put("STATUS", new Integer(status));

        Collection<BannerImpl> banners = (Collection<BannerImpl>) hibernateEntityDao
                .executeHQLQuery("loadBanners4User", params);

        for (BannerImpl banner : banners) {
            if ((banner.getSizeId() != null)
                    && (banner.getSizeId().intValue() == 0)) {
                continue;
            }

            BannerRepresentation represent = new BannerRepresentation(banner);
            resultCollection.add(represent);
        }

        return resultCollection;
    }

    public void addDefaultBanners2DefaultCampain(
            Integer defaultCampainId, UserImpl user) {
        fillDefaultBannersCollection(defaultCampainId);
    }

    public boolean checkBannerState() {
        Collection allBannersCollection = hibernateEntityDao
                .viewAll(BannerImpl.class);

        if ((allBannersCollection != null) && (!allBannersCollection.isEmpty())) {
            Iterator bannersIterator = allBannersCollection.iterator();

            while (bannersIterator.hasNext()) {
                String filePath;
                BannerImpl banner = (BannerImpl) bannersIterator.next();
                filePath = banner.getFile();

                if (filePath != null) {
                    if (!new File(filePath).exists()) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private void fillDefaultBannersCollection(Integer defaultCampainId) {
        CampainImpl campainImpl = (CampainImpl) hibernateEntityDao.loadObject(
                CampainImpl.class, defaultCampainId);

        Collection sizesCollection = hibernateEntityDao.viewAll(Size.class);
        Iterator sizesIterator = sizesCollection.iterator();

        while (sizesIterator.hasNext()) {
            Size size = (Size) sizesIterator.next();
            BannerImpl banner = new BannerImpl();
            campainImpl.udateBanner(banner);
            banner.setContentType("image/gif");
            banner.setFile(size.getDefaultFileName());
            banner.setSizeId(size.getSizeId());
            banner.setFileName("default banner");
            banner.setTypeId(Type.IMAGE_TYPE_ID);

            RatesService ratesService = (RatesService) ContextAwareGuiBean.getContext().getBean("ratesService");
            ratesService.createBannerRate(banner, defaultCampainId);
            hibernateEntityDao.save(banner);
        }
    }

    public Collection checkBanners(String bannerName) {
        Collection coll = new ArrayList();

        Session session = null;
        Transaction tr = null;

        try {
            session = AdSapientHibernateService.openSession();
            tr = session.beginTransaction();

            coll = session.createCriteria(BannerImpl.class).add(
                    Expression.like("name", bannerName)).list();
            tr.commit();
        } catch (HibernateException ex) {
            logger.error("This exception throw in -- vew banners ", ex);
            logger.error(ex.getLocalizedMessage());
        } finally {
            AdSapientHibernateService.closeSession(session, tr);
        }

        return coll;
    }

    public CookieManagementService getCookieManagementService() {
        return cookieManagementService;
    }

    public void setCookieManagementService(CookieManagementService cookieManagementService) {
        this.cookieManagementService = cookieManagementService;
    }
}
