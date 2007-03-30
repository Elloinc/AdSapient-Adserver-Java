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
package com.adsapient.adserver;

import com.adsapient.adserver.beans.AdserverModel;
import com.adsapient.adserver.beans.TotalsReporterModel;
import com.adsapient.adserver.filters.stateful.BehaviorFilterProcessor;
import com.adsapient.adserver.filters.stateful.TrafficFilterProcessor;
import com.adsapient.adserver.filters.stateless.RequestParamsFilterProcessor;
import com.adsapient.adserver.filters.stateless.SystemFilterProcessor;
import com.adsapient.adserver.filters.stateless.TimeFilterProcessor;
import com.adsapient.api.FilterInterface;
import com.adsapient.api.IMappable;
import com.adsapient.shared.AdsapientConstants;
import com.adsapient.shared.mappable.*;
import org.apache.log4j.Logger;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class AdserverDecisionProvider {
    static Logger logger = Logger.getLogger(AdserverDecisionProvider.class);
    private AdserverModel adserverModel;
    private AdserverModelBuilder adserverModelBuilder;
    private TimeFilterProcessor timeFilterProcessor;
    private RequestParamsFilterProcessor requestParamsFilterProcessor;
    private TrafficFilterProcessor trafficFilterProcessor;
    private BehaviorFilterProcessor behaviorFilterProcessor;
    private SystemFilterProcessor systemFilterProcessor;
    private TotalsReporterModel totalsReporterModel;
    private ModelUpdater modelUpdater;
    private Iterator webiterator;

    public BannerImpl getAd(Map<String, Object> requestParams) throws Exception {
        int type = (Integer) requestParams.get(AdsapientConstants.RESOURCE_TYPE_REQUEST_PARAM_KEY);
        BannerImpl firstAnalyzedBanner = getNextBanner(type);
        if (firstAnalyzedBanner == null) {
            requestParams.put(AdsapientConstants.REASON_FOR_DEFAULT_REQUEST_PARAM_KEY,
                    (requestParams.get(AdsapientConstants.REASON_FOR_DEFAULT_REQUEST_PARAM_KEY)
                            + " doesn't match current request:"
                            + ":firstAnalyzedBanner:" + firstAnalyzedBanner
                    ));
            logger.info("REASON FOR DISPLAYING DEFAULT AD:" + requestParams.get(AdsapientConstants.REASON_FOR_DEFAULT_REQUEST_PARAM_KEY));
            return getDefaultAd(requestParams);
        }
        BannerImpl currentlyAnalyzedBanner = firstAnalyzedBanner;
        requestParams.put(AdsapientConstants.BANNER_REQUEST_PARAM_KEY, currentlyAnalyzedBanner);
        while (!isMatch(requestParams, currentlyAnalyzedBanner)) {
            currentlyAnalyzedBanner = getNextBanner(type);
            if (currentlyAnalyzedBanner.equals(firstAnalyzedBanner)) {
                requestParams.put(AdsapientConstants.REASON_FOR_DEFAULT_REQUEST_PARAM_KEY,
                        requestParams.get(AdsapientConstants.REASON_FOR_DEFAULT_REQUEST_PARAM_KEY)
                                + ("banner:"
                                + ((BannerImpl) requestParams.get(AdsapientConstants.BANNER_REQUEST_PARAM_KEY)).getId()
                                + " doesn't match current request:"
                                + ":currentlyAnalyzedBanner.equals(firstAnalyzedBanner)"
                        ));
                requestParams.put(AdsapientConstants.REASON_FOR_DEFAULT_REQUEST_PARAM_KEY,
                        requestParams.get(AdsapientConstants.REASON_FOR_DEFAULT_REQUEST_PARAM_KEY)
                                + (":ip:"
                                + requestParams.get(AdsapientConstants.IPADDRESS_UNIQUE_ID_REQUEST_PARAM_KEY)
                        ));
                logger.info("REASON FOR DISPLAYING DEFAULT AD:" + requestParams.get(AdsapientConstants.REASON_FOR_DEFAULT_REQUEST_PARAM_KEY));
                return getDefaultAd(requestParams);
            }
        }
        return currentlyAnalyzedBanner;
    }


    private boolean isMatch(Map<String, Object> requestParams, BannerImpl currentlyAnalyzedBanner) {
        requestParams.put(AdsapientConstants.BANNER_REQUEST_PARAM_KEY, currentlyAnalyzedBanner);
        if (!systemFilterProcessor.doFilter(currentlyAnalyzedBanner, requestParams)) {
            return false;
        }
        List<FilterInterface> campaignFilters = adserverModelBuilder.getCampaignFilters(currentlyAnalyzedBanner);
        if (campaignFilters == null || campaignFilters.size() == 0) {
            return true;
        }
        for (FilterInterface entity : campaignFilters) {
            if (entity instanceof TimeFilter ||
                    entity instanceof DateFilter) {
                if (!timeFilterProcessor.doFilter(entity, requestParams)) {
                    return false;
                } else {
                    continue;
                }
            } else if (entity instanceof SystemsFilter ||
                    entity instanceof ParametersFilter ||
                    entity instanceof KeywordsFilter ||
                    entity instanceof GeoFilter ||
                    entity instanceof ContentFilter) {
                if (!requestParamsFilterProcessor.doFilter(entity, requestParams)) {
                    return false;
                } else {
                    continue;
                }
            } else if (entity instanceof TrafficFilter) {
                if (!trafficFilterProcessor.doFilter(entity, requestParams)) {
                    return false;
                } else {
                    continue;
                }
            }
        }
        return true;
    }

    private synchronized BannerImpl getDefaultAd(Map<String, Object> requestParams) {
        PlacesImpl place = (PlacesImpl) adserverModel.getPlacesMap().get(
                requestParams.get(AdsapientConstants.PLACEID_REQUEST_PARAM_KEY)
        );
        Size size = (Size) adserverModel.getSizesMap().get(place.getSizeId());
        Integer placeId = place.getPlaceId();
        for (IMappable im : adserverModel.getDefaultPublisherBannerPool().values()) {
            BannerImpl banner = (BannerImpl) im;
            if (banner.getUserId().equals(place.getUserId()) && banner.getSizeId().equals(size.getId()))
                return banner;
        }
        for (IMappable im : adserverModel.getDefaultBannersPool().values()) {
            BannerImpl banner = (BannerImpl) im;

            if (place.getSizeId().equals(banner.getSizeId())) {
                return banner;
            }
        }
        BannerImpl banner = adserverModel.getDefaultBanner().clone();
        banner.setSizeId(place.getSizeId());
        banner.setSize(size);
        banner.setPlaceId(String.valueOf(placeId));
        banner.setPlaceTypeId(place.getPlaceTypeId());
        return banner;
    }

    private synchronized BannerImpl getNextBanner(int type) {
        List<BannerImpl> pool = null;
        Iterator iterator = null;
        switch (type) {
            case Type.WEB_RESOURCE:
                pool = adserverModel.getBannersPool();
                if (webiterator == null || !webiterator.hasNext()) {
                    webiterator = pool.iterator();
                }
                iterator = webiterator;
                break;
        }
        if (pool == null
                || pool.size() == 0) {
            return null;
        }

        try {
            return (BannerImpl) iterator.next();
        } catch (NoSuchElementException nsee) {
            webiterator = null;
            modelUpdater.repool();
            return getNextBanner(type);
        }
    }

    public AdserverModel getAdserverModel() {
        return adserverModel;
    }

    public void setAdserverModel(AdserverModel adserverModel) {
        this.adserverModel = adserverModel;
    }

    public AdserverModelBuilder getAdserverModelBuilder() {
        return adserverModelBuilder;
    }

    public void setAdserverModelBuilder(AdserverModelBuilder adserverModelBuilder) {
        this.adserverModelBuilder = adserverModelBuilder;
    }

    public TimeFilterProcessor getTimeFilterProcessor() {
        return timeFilterProcessor;
    }

    public void setTimeFilterProcessor(TimeFilterProcessor timeFilterProcessor) {
        this.timeFilterProcessor = timeFilterProcessor;
    }

    public RequestParamsFilterProcessor getRequestParamsFilterProcessor() {
        return requestParamsFilterProcessor;
    }

    public void setRequestParamsFilterProcessor(RequestParamsFilterProcessor requestParamsFilterProcessor) {
        this.requestParamsFilterProcessor = requestParamsFilterProcessor;
    }

    public TrafficFilterProcessor getTrafficFilterProcessor() {
        return trafficFilterProcessor;
    }

    public void setTrafficFilterProcessor(TrafficFilterProcessor trafficFilterProcessor) {
        this.trafficFilterProcessor = trafficFilterProcessor;
    }

    public BehaviorFilterProcessor getBehaviorFilterProcessor() {
        return behaviorFilterProcessor;
    }

    public void setBehaviorFilterProcessor(BehaviorFilterProcessor behaviorFilterProcessor) {
        this.behaviorFilterProcessor = behaviorFilterProcessor;
    }

    public SystemFilterProcessor getSystemFilterProcessor() {
        return systemFilterProcessor;
    }

    public void setSystemFilterProcessor(SystemFilterProcessor systemFilterProcessor) {
        this.systemFilterProcessor = systemFilterProcessor;
    }

    public TotalsReporterModel getTotalsReporterModel() {
        return totalsReporterModel;
    }

    public void setTotalsReporterModel(TotalsReporterModel totalsReporterModel) {
        this.totalsReporterModel = totalsReporterModel;
    }

    public ModelUpdater getModelUpdater() {
        return modelUpdater;
    }

    public void setModelUpdater(ModelUpdater modelUpdater) {
        this.modelUpdater = modelUpdater;
    }
}
