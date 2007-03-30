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
package com.adsapient.adserver.beans;

import com.adsapient.api.FilterInterface;
import com.adsapient.api.IMappable;
import com.adsapient.shared.mappable.*;
import org.apache.log4j.Logger;

import java.util.*;

public class AdserverModel {
    static Logger logger = Logger.getLogger(AdserverModel.class);

    private Map<Integer, IMappable> usersMap;

    private Map<Integer, IMappable> bannersMap;

    private Map<Integer, IMappable> campaignsMap;

    private Map<Integer, IMappable> placesMap;

    private Map<Integer, IMappable> sitesMap;

    private Map<Integer, IMappable> sizesMap;

    private Map<Integer, IMappable> filtersMap;

    private Map<Integer, IMappable> ratesMap;

    private Map<Integer, IMappable> parametersMap;

    private Map<Integer, IMappable> geoLocationsMap;

    private Map<String, IMappable> geoLocationsReverseMap;

    private Map<String, String> sitePagesAndKeywords;

    private Map<String, SystemSettings> languagesMap;

    private Map<String, SystemSettings> browsersMap;

    private Map<String, SystemSettings> ossMap;

    private Map<Integer, IMappable> optionsMap;

    private Map<String, String> optionsItemNameToItemValueMap;

    private Map<Integer, IMappable> placeIdToBestMatchCampaignMap;

    private List<BannerImpl> bannersPool;

    private Map<Integer, IMappable> defaultBannersPool;

    private Map<Integer, IMappable> defaultPublisherBannerPool;

    private BannerImpl defaultBanner;

    public Map<String, String> getOptionsItemNameToItemValueMap() {
        return optionsItemNameToItemValueMap;
    }

    public void setOptionsItemNameToItemValueMap(
            Map<String, String> optionsItemNameToItemValueMap) {
        this.optionsItemNameToItemValueMap = optionsItemNameToItemValueMap;
    }

    public Map<Integer, IMappable> getOptionsMap() {
        return optionsMap;
    }

    public void setOptionsMap(Map<Integer, IMappable> optionsMap) {
        this.optionsMap = optionsMap;
    }

    public Map<Integer, IMappable> getDefaultBannersPool() {
        return defaultBannersPool;
    }

    public void setDefaultBannersPool(Map<Integer, IMappable> defaultBannersPool) {
        this.defaultBannersPool = defaultBannersPool;
    }

    public Map<Integer, IMappable> getFiltersMap() {
        return filtersMap;
    }

    public void setFiltersMap(Map<Integer, IMappable> filtersMap) {
        this.filtersMap = filtersMap;
    }

    public Map<Integer, IMappable> getUsersMap() {
        return usersMap;
    }

    public void setUsersMap(Map<Integer, IMappable> usersMap) {
        this.usersMap = usersMap;
    }

    public Map<Integer, IMappable> getBannersMap() {
        return bannersMap;
    }

    public void setBannersMap(Map<Integer, IMappable> bannersMap) {
        this.bannersMap = bannersMap;
    }

    public Map<Integer, IMappable> getCampaignsMap() {
        return campaignsMap;
    }

    public void setCampaignsMap(Map<Integer, IMappable> campaignsMap) {
        this.campaignsMap = campaignsMap;
    }

    public Map<Integer, IMappable> getPlacesMap() {
        return placesMap;
    }

    public void setPlacesMap(Map<Integer, IMappable> placesMap) {
        this.placesMap = placesMap;
    }

    public Map<Integer, IMappable> getSitesMap() {
        return sitesMap;
    }

    public void setSitesMap(Map<Integer, IMappable> sitesMap) {
        this.sitesMap = sitesMap;
    }

    public Map<Integer, IMappable> getSizesMap() {
        return sizesMap;
    }

    public void setSizesMap(Map<Integer, IMappable> sizesMap) {
        this.sizesMap = sizesMap;
    }

    public List<BannerImpl> getBannersPool() {
        return bannersPool;
    }

    public void setBannersPool(List<BannerImpl> bannersPool) {
        this.bannersPool = bannersPool;
    }

    public Map<Integer, IMappable> getRatesMap() {
        return ratesMap;
    }

    public void setRatesMap(Map<Integer, IMappable> ratesMap) {
        this.ratesMap = ratesMap;
    }

    public void setFiltersToCampaigns(List<FilterInterface> filters) {
        for (FilterInterface filter : filters) {
            CampainImpl campaign = (CampainImpl) campaignsMap.get(filter
                    .getCampainId());

            if (campaign == null) {
                break;
            }

            List<FilterInterface> campaignFilters = campaign.getFilters();

            if (campaignFilters == null) {
                campaignFilters = new ArrayList<FilterInterface>();
            }

            campaignFilters.add(filter);
            campaign.setFilters(campaignFilters);
        }
    }

    private final void cleanFiltersInBannerPool(String className, Integer campId) {
        for (BannerImpl banner : getBannersPool())
            if ((banner.getFilters() != null)
                    && banner.getCampainId().equals(campId)) {
                for (ListIterator<FilterInterface> iter = banner.getFilters()
                        .listIterator(); iter.hasNext();) {
                    FilterInterface filter = iter.next();

                    if (filter.getClass().getName().equals(className)) {
                        iter.remove();

                        break;
                    }
                }
            }
    }

    public void removeDynamicParameter(Integer id) {
        for (Integer campaignId : campaignsMap.keySet()) {
            CampainImpl campaign = (CampainImpl) campaignsMap.get(campaignId);

            if (removeDynamicParameter(campaign.getFilters(), id)) {
                cleanFiltersInBannerPool(ParametersFilter.class.getName(),
                        campaign.getId());

                return;
            }
        }

        for (BannerImpl banner : getBannersPool())
            if (removeDynamicParameter(banner.getFilters(), id)) {
                return;
            }

        logger
                .error(new StringBuffer(
                        "Couldn't remove DynamicParameter of id:").append(id)
                        .toString());
    }

    private boolean removeDynamicParameter(List<FilterInterface> filters,
                                           Integer id) {
        if ((filters == null) || (filters.size() == 0)) {
            return false;
        }

        for (FilterInterface filter : filters)
            if (filter.getClass().getName().equals(
                    ParametersFilter.class.getName())) {
                ParametersFilter pf = (ParametersFilter) filter;

                if (pf.getParameters() == null) {
                    continue;
                }

                for (Iterator<DynamicParameter> iter = pf.getParameters()
                        .iterator(); iter.hasNext();) {
                    DynamicParameter dp = iter.next();

                    if (dp.getId().equals(id)) {
                        iter.remove();

                        return true;
                    }
                }

                return false;
            }

        return false;
    }

    public void updateDynamicParameter(DynamicParameter dynamicParameter) {
        for (Integer campaignId : campaignsMap.keySet()) {
            CampainImpl camp = (CampainImpl) campaignsMap.get(campaignId);

            if (updateDynamicParameter(camp.getFilters(), dynamicParameter)) {
                cleanFiltersInBannerPool(ParametersFilter.class.getName(), camp
                        .getId());

                return;
            }
        }

        for (BannerImpl banner : getBannersPool())
            if (updateDynamicParameter(banner.getFilters(), dynamicParameter)) {
                return;
            }

        logger
                .error(new StringBuffer(
                        "Couldn't update DynamicParameter of id:").append(
                        dynamicParameter.getId()).toString());
    }

    private boolean updateDynamicParameter(List<FilterInterface> filters,
                                           DynamicParameter dynamicParameter) {
        if ((filters == null) || (filters.size() == 0)) {
            return false;
        }

        for (FilterInterface filter : filters) {
            if (filter.getClass().getName().equals(
                    ParametersFilter.class.getName())) {
                ParametersFilter pf = (ParametersFilter) filter;

                if (!pf.getId().equals(dynamicParameter.getParameterFilterId())) {
                    return false;
                }

                if (pf.getParameters() == null) {
                    Set<DynamicParameter> set = new HashSet<DynamicParameter>();
                    set.add(dynamicParameter);
                    pf.setParameters(set);

                    return true;
                }

                Iterator it = pf.getParameters().iterator();

                while (it.hasNext()) {
                    DynamicParameter dp = (DynamicParameter) it.next();

                    if (dp.getId().equals(dynamicParameter.getId())) {
                        pf.getParameters().remove(dp);
                        pf.getParameters().add(dynamicParameter);

                        return true;
                    }
                }

                pf.getParameters().add(dynamicParameter);

                return true;
            }
        }

        return false;
    }

    public void removeFilter(String className, Integer id) {
        for (Integer campaignId : campaignsMap.keySet()) {
            CampainImpl campaign = (CampainImpl) campaignsMap.get(campaignId);

            if (campaign.getFilters() != null) {

                for (FilterInterface filter : campaign.getFilters()) {
                    if (filter.getClass().getName().equals(className)
                            && ((IMappable) filter).getId().equals(id)) {
                        campaign.getFilters().remove(filter);
                        cleanFiltersInBannerPool(className, campaignId);

                        return;
                    }
                }
            }
        }

        for (BannerImpl banner : getBannersPool()) {
            if (banner.getFilters() != null) {
                for (FilterInterface filter : banner.getFilters()) {
                    if (filter.getClass().getName().equals(className)
                            && ((IMappable) filter).getId().equals(id)) {
                        banner.getFilters().remove(filter);

                        return;
                    }
                }
            }
        }
    }

    public void updateFilter(String className, IMappable entity) {
        FilterInterface filterEntity = (FilterInterface) entity;
        if (filterEntity.getBannerId() != null) {
            for (BannerImpl banner : getBannersPool())
                if (banner.getId().equals(filterEntity.getBannerId())) {
                    if (banner.getFilters() != null) {
                        for (FilterInterface filter : banner.getFilters()) {
                            if (((IMappable) filter).getId().equals(entity.getId()) &&
                                    filter.getClass().getName().equals(className)) {
                                banner.getFilters().remove(filter);
                                banner.getFilters().add((FilterInterface) entity);
                                getBannersMap().put(banner.getId(), banner);
                                return;
                            }
                        }
                        banner.getFilters().add(filterEntity);
                    } else {
                        List<FilterInterface> list = new ArrayList<FilterInterface>();
                        list.add(filterEntity);
                        banner.setFilters(list);
                    }
                    getBannersMap().put(banner.getId(), banner);
                    return;
                }
        } else {
            CampainImpl campaign = (CampainImpl) campaignsMap.get(filterEntity.getCampainId());
            if (campaign.getFilters() == null) {
                List<FilterInterface>l = new ArrayList<FilterInterface>();
                l.add(filterEntity);
                campaign.setFilters(l);
                cleanFiltersInBannerPool(className, campaign.getId());
                return;
            } else {
                for (FilterInterface filter : campaign.getFilters()) {
                    if (filter.getClass().getName().equals(className) && ((IMappable) filter).getId().equals(entity.getId())) {
                        campaign.getFilters().remove(filter);
                        campaign.getFilters().add(filterEntity);
                        cleanFiltersInBannerPool(className, campaign.getId());
                        return;
                    }
                }
                campaign.getFilters().add(filterEntity);
                cleanFiltersInBannerPool(className, campaign.getId());
            }
        }
    }


    public Map<String, String> getSitePagesAndKeywords() {
        return sitePagesAndKeywords;
    }

    public void setSitePagesAndKeywords(Map<String, String> sitePagesAndKeywords) {
        this.sitePagesAndKeywords = sitePagesAndKeywords;
    }

    public BannerImpl getDefaultBanner() {
        return defaultBanner;
    }

    public void setDefaultBanner(BannerImpl defaultBanner) {
        this.defaultBanner = defaultBanner;
    }

    public Map<String, SystemSettings> getLanguagesMap() {
        return languagesMap;
    }

    public void setLanguagesMap(Map<String, SystemSettings> languagesMap) {
        this.languagesMap = languagesMap;
    }

    public Map<String, SystemSettings> getBrowsersMap() {
        return browsersMap;
    }

    public void setBrowsersMap(Map<String, SystemSettings> browsersMap) {
        this.browsersMap = browsersMap;
    }

    public Map<String, SystemSettings> getOssMap() {
        return ossMap;
    }

    public void setOssMap(Map<String, SystemSettings> ossMap) {
        this.ossMap = ossMap;
    }

    public Map<Integer, IMappable> getParametersMap() {
        return parametersMap;
    }

    public void setParametersMap(Map<Integer, IMappable> parametersMap) {
        this.parametersMap = parametersMap;
    }

    public Map<Integer, IMappable> getGeoLocationsMap() {
        return geoLocationsMap;
    }

    public void setGeoLocationsMap(Map<Integer, IMappable> geoLocationsMap) {
        this.geoLocationsMap = geoLocationsMap;
    }

    public Map<String, IMappable> getGeoLocationsReverseMap() {
        return geoLocationsReverseMap;
    }

    public void setGeoLocationsReverseMap(
            Map<String, IMappable> geoLocationsReverseMap) {
        this.geoLocationsReverseMap = geoLocationsReverseMap;
    }

    public void updateKeyWordsFilterElement(
            KeyWordsFilterElement keyWordsFilterElement) {
        for (Integer campaignId : campaignsMap.keySet()) {
            CampainImpl campaign = (CampainImpl) campaignsMap.get(campaignId);

            if (updateKeyWordsFilterElement(campaign.getFilters(),
                    keyWordsFilterElement)) {
                cleanFiltersInBannerPool(KeywordsFilter.class.getName(),
                        campaign.getId());

                return;
            }

            for (BannerImpl banner : getBannersPool())
                if (updateKeyWordsFilterElement(banner.getFilters(),
                        keyWordsFilterElement)) {
                    return;
                }
        }

        logger.error("Couldn't update DynamicParameter of id:"
                + keyWordsFilterElement.getId());
    }

    private boolean updateKeyWordsFilterElement(List<FilterInterface> filters,
                                                KeyWordsFilterElement keyWordsFilterElement) {
        if ((filters == null) || (filters.size() == 0)) {
            return false;
        }

        for (FilterInterface filter : filters) {
            if (filter.getClass().getName().equals(
                    KeywordsFilter.class.getName())) {
                KeywordsFilter kf = (KeywordsFilter) filter;

                if (!kf.getId().equals(
                        keyWordsFilterElement.getKeywordsFilterId())) {
                    return false;
                }

                if (kf.getKeyWordElements() == null) {
                    Set<KeyWordsFilterElement> set = new HashSet<KeyWordsFilterElement>();
                    set.add(keyWordsFilterElement);
                    kf.setKeyWordElements(set);

                    return true;
                }

                for (KeyWordsFilterElement elem : kf.getKeyWordElements())
                    if (elem.getId().equals(keyWordsFilterElement.getId())) {
                        kf.getKeyWordElements().remove(elem);
                        kf.getKeyWordElements().add(keyWordsFilterElement);

                        return true;
                    }

                kf.getKeyWordElements().add(keyWordsFilterElement);

                return true;
            }
        }

        return false;
    }

    public void removeKeyWordsFilterElement(Integer id) {
        for (Integer campaignId : campaignsMap.keySet()) {
            CampainImpl campaign = (CampainImpl) campaignsMap.get(campaignId);

            if (removeKeyWordsFilterElement(campaign.getFilters(), id)) {
                cleanFiltersInBannerPool(KeywordsFilter.class.getName(),
                        campaign.getId());

                return;
            }
        }

        for (BannerImpl banner : getBannersPool())
            if (removeKeyWordsFilterElement(banner.getFilters(), id)) {
                return;
            }

        logger.error("Couldn't remove DynamicParameter of id:" + id);
    }

    private boolean removeKeyWordsFilterElement(List<FilterInterface> filters,
                                                Integer id) {
        if ((filters == null) || (filters.size() == 0)) {
            return false;
        }

        String className = KeywordsFilter.class.getName();

        for (FilterInterface filter : filters)
            if (filter.getClass().getName().equals(className)) {
                KeywordsFilter pf = (KeywordsFilter) filter;
                Iterator it = pf.getKeyWordElements().iterator();

                while (it.hasNext()) {
                    KeyWordsFilterElement dp = (KeyWordsFilterElement) it
                            .next();

                    if (dp.getId().equals(id)) {
                        pf.getKeyWordElements().remove(dp);

                        return true;
                    }
                }

                return false;
            }

        return false;
    }

    public Map<Integer, IMappable> getDefaultPublisherBannerPool() {
        return defaultPublisherBannerPool;
    }

    public void setDefaultPublisherBannerPool(
            Map<Integer, IMappable> defaultPublisherBannerPool) {
        this.defaultPublisherBannerPool = defaultPublisherBannerPool;
    }

    public void rebuildOptionsItemNameToItemValueMap() {
        if (optionsItemNameToItemValueMap == null) {
            optionsItemNameToItemValueMap = new HashMap<String, String>();
        }

        for (Integer id : optionsMap.keySet()) {
            IMappable im = optionsMap.get(id);
            AdminOptions am = (AdminOptions) im;
            optionsItemNameToItemValueMap.put(am.getItemName(), am
                    .getItemValue());
        }
    }

    public void setFiltersToCampaignsAndBanners(List<FilterInterface> filters) {
        for (FilterInterface filter : filters) {
            if (filter.getBannerId() != null) {
                BannerImpl banner = (BannerImpl) bannersMap.get(filter.getBannerId());
                if (banner != null) {
                    List<FilterInterface>bannerFilters = banner.getFilters();
                    if (bannerFilters == null) bannerFilters = new ArrayList<FilterInterface>();
                    bannerFilters.add(filter);
                    banner.setFilters(bannerFilters);
                    continue;
                }
            }
            CampainImpl campaign = (CampainImpl) campaignsMap.get(filter.getCampainId());
            if (campaign == null)
                continue;
            List<FilterInterface>campaignFilters = campaign.getFilters();
            if (campaignFilters == null)
                campaignFilters = new ArrayList<FilterInterface>();
            campaignFilters.add(filter);
            campaign.setFilters(campaignFilters);
        }
    }
}
