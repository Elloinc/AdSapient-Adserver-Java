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
package com.adsapient.adserver.filters.stateless;

import com.adsapient.adserver.AdserverServlet;
import com.adsapient.adserver.beans.AdserverModel;
import com.adsapient.adserver.beans.IpToCountryMappingBean;
import com.adsapient.api.FilterInterface;
import com.adsapient.api_impl.filter.*;
import com.adsapient.api_impl.publisher.PlacesImpl;
import com.adsapient.api_impl.settings.ParameterImpl;
import com.adsapient.util.admin.AdsapientConstants;
import org.apache.log4j.Logger;

import java.util.*;

public class RequestParamsFilterProcessor {
    static Logger logger = Logger.getLogger(RequestParamsFilterProcessor.class);

    private AdserverModel adserverModel;

    private IpToCountryMappingBean ipToCountryMappingBean;

    public void setAdserverModel(AdserverModel adserverModel) {
        this.adserverModel = adserverModel;
    }

    public void setup() {
    }

    public boolean doFilter(FilterInterface entity,
                            Map<String, Object> requestParams) {
        try {
            if (entity instanceof KeywordsFilter) {
                KeywordsFilter keyflObj = (KeywordsFilter) entity;

                Integer placesId = Integer.parseInt(requestParams.get(
                        AdsapientConstants.PLACEID_REQUEST_PARAM_KEY)
                        .toString());

                PlacesImpl places = (PlacesImpl) adserverModel.getPlacesMap()
                        .get(placesId);

                if (keyflObj.getKeyWordElements().size() == 0) {
                    return true;
                }

                String adplacesKeyword = "";

                if (requestParams
                        .get(AdsapientConstants.KEYWORDS_REQUEST_PARAM_KEY) != null) {
                    adplacesKeyword = requestParams.get(
                            AdsapientConstants.KEYWORDS_REQUEST_PARAM_KEY)
                            .toString();
                }

                if ((adplacesKeyword == null)
                        || (adplacesKeyword.length() == 0)) {
                    if (requestParams.get(AdsapientConstants.REFERER_PARAM_KEY) != null) {
                        String publisherSitePageUrl = (requestParams
                                .get(AdsapientConstants.REFERER_PARAM_KEY)
                                .toString());
                        adplacesKeyword = adserverModel
                                .getSitePagesAndKeywords().get(
                                publisherSitePageUrl);
                    }

                    if ((adplacesKeyword == null)
                            || (adplacesKeyword.length() == 0)) {
                        return false;
                    }
                }

                adplacesKeyword = adplacesKeyword.toLowerCase();

                Iterator textEnginesIterator = keyflObj.getKeyWordElements()
                        .iterator();

                while (textEnginesIterator.hasNext()) {
                    KeyWordsFilterElement element = (KeyWordsFilterElement) textEnginesIterator
                            .next();
                    StringTokenizer tokenizer = new StringTokenizer(
                            element.keyWordSet, ";");

                    while (tokenizer.hasMoreTokens()) {
                        String engineKeyword = tokenizer.nextToken()
                                .toLowerCase();

                        StringTokenizer adplacesKeywordTokenizer = new StringTokenizer(
                                adplacesKeyword, ";");

                        while (adplacesKeywordTokenizer.hasMoreTokens()) {
                            String adplacesTokenizedKeyWord = adplacesKeywordTokenizer
                                    .nextToken();

                            if (adplacesTokenizedKeyWord
                                    .equalsIgnoreCase(engineKeyword)) {
                                logger.info("find campaign with keyword="
                                        + engineKeyword
                                        + " and adplaces keyword is "
                                        + adplacesKeyword);

                                return true;
                            }
                        }
                    }
                }
            }

            if (entity instanceof SystemsFilter) {
                return doSystemsFilter(requestParams, (SystemsFilter) entity);
            } else if (entity instanceof GeoFilter) {
                return doGeoFilter(requestParams, (GeoFilter) entity);
            } else if (entity instanceof ParametersFilter) {
                return doParametersFilter(requestParams,
                        (ParametersFilter) entity);
            } else if (entity instanceof ContentFilter) {
                return doContentFilter(requestParams, (ContentFilter) entity);
            }

            return true;
        } catch (NullPointerException ex) {
            logger.error(ex.getMessage(), ex);

            return true;
        }
    }

    private boolean doContentFilter(
            Map<String, Object> requestParams,
            ContentFilter filter) {
        PlacesImpl place = (PlacesImpl) requestParams.get(AdsapientConstants.ADPLACE_REQUEST_PARAM_KEY);
        String placeCategories = place.getCategorys();
        String filterCategories = filter.getCategorys();
        List<String> placeCategoriesList = getCategoriesListFromString(placeCategories);
        List<String> filterCategoriesList = getCategoriesListFromString(filterCategories);
        if (filterCategoriesList == null || filterCategoriesList.size() == 0) return false;
        boolean categoryFound = false;
        for (String filterCatId : filterCategoriesList) {
            if (placeCategoriesList.contains(filterCatId)) {
                categoryFound = true;
                break;
            }
        }
        if (!categoryFound) return false;
        Integer placesPositionId = place.getPlaceId();
        if (filter.getPositions().indexOf(":" + placesPositionId + ":") < 0) {
            return false;
        }
        Integer placeId = place.getId();
        if (filter.getPlaces().indexOf(":" + placeId + ":") < 0) {
            return false;
        }
        return true;
    }

    private List<String> getCategoriesListFromString(String categoriesStr) {
        String[] cats = categoriesStr.split(":");
        List<String> catsList = new ArrayList<String>();
        for (String cat : cats) {
            if (cat.equals("")) continue;
            String catId = cat.split("-")[0];
            catsList.add(catId);
        }
        return catsList;
    }

    private boolean doGeoFilter(Map<String, Object> requestParams,
                                GeoFilter filter) {
        IpToCountryMappingBean ipToCountryMappingBean = (IpToCountryMappingBean) AdserverServlet.appContext
                .getBean("ipToCountryMapping");
        String visitorCountry = ipToCountryMappingBean
                .search((String) requestParams
                        .get(AdsapientConstants.IPADDRESS_UNIQUE_ID_REQUEST_PARAM_KEY));

        if (visitorCountry
                .equals(AdsapientConstants.COUNTRY_ABBR_ADDRES_ANONIMOUS)
                || visitorCountry
                .equals(AdsapientConstants.COUNTRY_ABBR_ADDRES_NOT_FOUND)
                || visitorCountry
                .equals(AdsapientConstants.COUNTRY_ABBR_ADDRES_RESERVED)
                || (filter.getPreferCountrys().indexOf(visitorCountry) >= 0)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean doParametersFilter(Map<String, Object> requestParams,
                                       ParametersFilter filter) {
        Map<String, String> filterKeyValueParametersMap = filter
                .getKeyValueParametersMap();

        if (!hasValues(filterKeyValueParametersMap)) {
            return true;
        }

        Map<String, String> keyValueParametersMap = (Map<String, String>) requestParams
                .get(AdsapientConstants.CUSTOMREQUESTPARAMS_REQUEST_PARAM_KEY);

        for (String key : filterKeyValueParametersMap.keySet()) {
            String value1 = keyValueParametersMap.get(key);
            String value2 = filterKeyValueParametersMap.get(key);

            if (value1 == null) {
                continue;
            }

            List<String> l1 = Arrays.asList(value1.split("(:)|(;)"));
            List<String> l2 = Arrays.asList(value2.split("(:)|(;)"));

            for (String s : l1) {
                if (l2.contains(s)) {
                    return true;
                }
            }
        }

        for (Integer id : adserverModel.getParametersMap().keySet()) {
            ParameterImpl parameter = (ParameterImpl) adserverModel
                    .getParametersMap().get(id);

            if (keyValueParametersMap.containsKey(parameter.getName())) {
                return false;
            }
        }

        if (filterKeyValueParametersMap.size() > 0) {
            return false;
        }

        return true;
    }

    private boolean doSystemsFilter(Map<String, Object> requestParams,
                                    SystemsFilter filter) {
        String user_lang = (String) requestParams
                .get(AdsapientConstants.LANGUAGE_SSKEY_REQUEST_PARAM_KEY);
        String user_browser = Integer.toString((Integer) requestParams
                .get(AdsapientConstants.BROWSER_ID_REQUEST_PARAM_KEY));
        String user_system = Integer.toString((Integer) requestParams
                .get(AdsapientConstants.OS_ID_REQUEST_PARAM_KEY));
        String user_referer = (String) requestParams
                .get(AdsapientConstants.REFERER_PARAM_KEY);

        if (((user_lang != null) && (filter.getUser_lang().indexOf(user_lang) > -1))
                || ((user_system != null) && (filter.getUser_system().indexOf(
                user_system) > -1))
                || ((user_browser != null) && (filter.getUser_browser()
                .indexOf(user_browser) > -1))) {
            return true;
        }

        if (user_referer != null) {
            Iterator elementsIterator = filter.getReferrersElements()
                    .iterator();

            while (elementsIterator.hasNext()) {
                ReferrersElement element = (ReferrersElement) elementsIterator
                        .next();

                if (element.isType()
                        && user_referer.equals(element.getTarget_url())) {
                    return true;
                }

                if (!element.isType()
                        && user_referer.equals(element.getTarget_url())) {
                    return false;
                }
            }
        }

        return false;
    }

    private boolean hasValues(Map<String, String> map) {
        boolean hasValues = false;

        for (String key : map.keySet()) {
            String value = map.get(key);

            if ((value != null) && !value.equals("")) {
                hasValues = true;
            }
        }

        return hasValues;
    }

    public IpToCountryMappingBean getIpToCountryMappingBean() {
        return ipToCountryMappingBean;
    }

    public void setIpToCountryMappingBean(
            IpToCountryMappingBean ipToCountryMappingBean) {
        this.ipToCountryMappingBean = ipToCountryMappingBean;
    }
}
