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
import com.adsapient.adserver.beans.IpToCountryMappingBean;
import com.adsapient.api.FilterInterface;
import com.adsapient.shared.AdsapientConstants;
import com.adsapient.shared.mappable.*;
import com.adsapient.shared.service.ParametersService;
import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdserverModelBuilder {
    static Logger logger = Logger.getLogger(AdserverModelBuilder.class);

    private AdserverModel adserverModel;

    private ParametersService parametersService;

    private IpToCountryMappingBean ipToCountryMappingBean;

    public Map<String, Object> buildParamsFromRequest(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> m = new HashMap<String, Object>();
        Map<String, String> requestParametersMap = getParametersFromRequest(request);

        Byte eventType = null;

        try {
            String eventTypeStr = requestParametersMap
                    .get(AdsapientConstants.EVENTID_REQUEST_PARAM_NAME);
            eventType = Byte.parseByte(eventTypeStr);
        } catch (Exception ex) {
            logger.debug(ex.getMessage());

            eventType = AdsapientConstants.GETADCODE_ADSERVEREVENT_TYPE;
        }

        m.put(AdsapientConstants.EVENT_TYPE_REQUEST_PARAM_KEY, eventType);

        m.put(AdsapientConstants.REFERER_PARAM_KEY, request
                .getParameter(AdsapientConstants.REFERER_NAME));

        Integer placeId = null;

        try {
            String placeIdStr = requestParametersMap
                    .get(AdsapientConstants.PLACEID_REQUEST_PARAM_NAME);
            placeId = Integer.parseInt(placeIdStr);

            if (adserverModel.getPlacesMap().containsKey(placeId)) {
                PlacesImpl place = (PlacesImpl) adserverModel.getPlacesMap()
                        .get(placeId);
                m.put(AdsapientConstants.PLACEID_REQUEST_PARAM_KEY, placeId);
                m.put(AdsapientConstants.ADPLACE_REQUEST_PARAM_KEY, place);

                if (request.getParameter(AdsapientConstants.KEYWORDS_PARAMETER) != null) {
                    m.put(AdsapientConstants.KEYWORDS_REQUEST_PARAM_KEY,
                            request.getParameter(
                                    AdsapientConstants.KEYWORDS_PARAMETER)
                                    .toString());
                }
            } else {
                m.put(AdsapientConstants.PLACEID_REQUEST_PARAM_KEY, null);
                m.put(AdsapientConstants.ADPLACE_REQUEST_PARAM_KEY, null);
            }
        } catch (Exception ex) {
            logger.debug(ex.getMessage());
            m.put(AdsapientConstants.ADPLACE_REQUEST_PARAM_KEY, null);
            m.put(AdsapientConstants.PLACEID_REQUEST_PARAM_KEY, null);
            m.put(AdsapientConstants.TEMPLATEID_REQUEST_PARAM_KEY, null);
        }

        Integer bannerId = null;

        try {
            String bannerIdStr = requestParametersMap
                    .get(AdsapientConstants.BANNERID_REQUEST_PARAM_NAME);
            bannerId = Integer.parseInt(bannerIdStr);

            BannerImpl banner = null;

            if (adserverModel.getBannersMap().containsKey(bannerId)) {
                banner = (BannerImpl) adserverModel.getBannersMap().get(
                        bannerId);
            } else if (adserverModel.getDefaultBannersPool().containsKey(
                    bannerId)) {
                banner = (BannerImpl) adserverModel.getDefaultBannersPool()
                        .get(bannerId);
            } else if (adserverModel.getDefaultPublisherBannerPool()
                    .containsKey(bannerId)) {
                banner = (BannerImpl) adserverModel
                        .getDefaultPublisherBannerPool().get(bannerId);
            }

            m.put(AdsapientConstants.BANNER_REQUEST_PARAM_KEY, banner);
        } catch (Exception ex) {
            m.put(AdsapientConstants.BANNER_REQUEST_PARAM_KEY, null);
        }

        try {
            m.put(AdsapientConstants.REQUESTURL_REQUEST_PARAM_KEY, request
                    .getRequestURL().toString());
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }

        try {
            Cookie[] ccs = request.getCookies();

            for (Cookie c : ccs) {
                String cookieName = c.getName();
                String cookieValue = c.getValue();

                if (cookieName
                        .equals(AdsapientConstants.ADSERVER_UNIQUE_VISITOR_ID_COOKIENAME)) {
                    Integer i = Integer.parseInt(cookieValue);
                    m
                            .put(
                                    AdsapientConstants.COOKIE_UNIQUE_ID_REQUEST_PARAM_KEY,
                                    i);
                }
            }
        } catch (Exception ex) {
            m.put(AdsapientConstants.COOKIE_UNIQUE_ID_REQUEST_PARAM_KEY, null);
        }

        try {
            String ipAddress = null;
            if (request.getHeader("X-Forwarded-For") == null)
                ipAddress = request.getRemoteAddr();
            else
                ipAddress = request.getHeader("X-Forwarded-For");
            StringBuffer buffer = new StringBuffer(ipAddress);
            int index = buffer.indexOf(",");
            if (index > 0) {
                int length = buffer.length();
                buffer = buffer.delete(index, length);
            }
            m.put(AdsapientConstants.IPADDRESS_UNIQUE_ID_REQUEST_PARAM_KEY, buffer.toString());
        } catch (Exception ex) {
            m.put(AdsapientConstants.IPADDRESS_UNIQUE_ID_REQUEST_PARAM_KEY, null);
        }

        try {
            m.put(AdsapientConstants.HEADERSTATICINFO_UNIQUE_ID_REQUEST_PARAM_KEY, request.getHeader(""));
        } catch (Exception ex) {
            m.put(AdsapientConstants.HEADERSTATICINFO_UNIQUE_ID_REQUEST_PARAM_KEY, null);
        }

        try {
            Map<String, String> parametersMap = new HashMap<String, String>();

            for (Enumeration en = request.getParameterNames(); en
                    .hasMoreElements();) {
                String paramName = (String) en.nextElement();
                String paramValue = (String) request.getParameter(paramName);
                parametersMap.put(paramName, paramValue);
            }

            m.put(AdsapientConstants.CUSTOMREQUESTPARAMS_REQUEST_PARAM_KEY,
                    parametersMap);
        } catch (Exception ex) {
            m.put(AdsapientConstants.CUSTOMREQUESTPARAMS_REQUEST_PARAM_KEY,
                    new HashMap<String, String>());
        }

        try {
            PlacesImpl pl = (PlacesImpl) adserverModel.getPlacesMap().get(
                    placeId);
            SiteImpl si = (SiteImpl) adserverModel.getSitesMap().get(
                    pl.getSiteId());
            int type = si.getTypeId();
            m.put(AdsapientConstants.RESOURCE_TYPE_REQUEST_PARAM_KEY, type);
        } catch (Exception ex) {
            m.put(AdsapientConstants.RESOURCE_TYPE_REQUEST_PARAM_KEY, null);
        }

        String userAgent = request.getHeader("User-Agent");

        try {
            m.put(AdsapientConstants.BROWSER_ID_REQUEST_PARAM_KEY,
                    AdsapientConstants.UNIDENTIFIED_BROWSER_ID);
            m.put(AdsapientConstants.BROWSER_SSKEY_REQUEST_PARAM_KEY,
                    AdsapientConstants.UNIDENTIFIED_BROWSER_SSKEY);

            for (String key : adserverModel.getBrowsersMap().keySet()) {
                if (userAgent.contains(key)) {
                    SystemSettings ss = adserverModel.getBrowsersMap().get(key);
                    m.put(AdsapientConstants.BROWSER_ID_REQUEST_PARAM_KEY, ss
                            .getId());
                    m.put(AdsapientConstants.BROWSER_SSKEY_REQUEST_PARAM_KEY,
                            ss.getSskey());

                    break;
                }
            }
        } catch (Exception ex) {
            m.put(AdsapientConstants.BROWSER_ID_REQUEST_PARAM_KEY,
                    AdsapientConstants.UNIDENTIFIED_BROWSER_ID);
            m.put(AdsapientConstants.BROWSER_SSKEY_REQUEST_PARAM_KEY,
                    AdsapientConstants.UNIDENTIFIED_BROWSER_SSKEY);
        }

        String acceptLanguage = request.getLocale().getLanguage();

        try {
            m.put(AdsapientConstants.LANGUAGE_SSKEY_REQUEST_PARAM_KEY,
                    AdsapientConstants.UNIDENTIFIED_LANGUAGE_SSKEY);
            m.put(AdsapientConstants.LANGUAGE_ID_REQUEST_PARAM_KEY,
                    AdsapientConstants.UNIDENTIFIED_LANGUAGE_ID);

            for (SystemSettings ss : adserverModel.getLanguagesMap().values())
                if (acceptLanguage.startsWith(ss.getSskey())) {
                    m.put(AdsapientConstants.LANGUAGE_SSKEY_REQUEST_PARAM_KEY,
                            ss.getSskey());
                    m.put(AdsapientConstants.LANGUAGE_ID_REQUEST_PARAM_KEY, ss
                            .getId());

                    break;
                }
        } catch (Exception ex) {
            m.put(AdsapientConstants.LANGUAGE_SSKEY_REQUEST_PARAM_KEY,
                    AdsapientConstants.UNIDENTIFIED_LANGUAGE_SSKEY);
            m.put(AdsapientConstants.LANGUAGE_ID_REQUEST_PARAM_KEY,
                    AdsapientConstants.UNIDENTIFIED_LANGUAGE_ID);
        }

        try {
            m.put(AdsapientConstants.OS_ID_REQUEST_PARAM_KEY,
                    AdsapientConstants.UNIDENTIFIED_OPERATING_SYSTEM_ID);
            m.put(AdsapientConstants.OS_SSKEY_REQUEST_PARAM_KEY,
                    AdsapientConstants.UNIDENTIFIED_OS_SSKEY);

            for (String key : adserverModel.getOssMap().keySet()) {
                if (userAgent.contains(key)) {
                    SystemSettings ss = adserverModel.getOssMap().get(key);
                    m.put(AdsapientConstants.OS_ID_REQUEST_PARAM_KEY, ss
                            .getId());
                    m.put(AdsapientConstants.OS_SSKEY_REQUEST_PARAM_KEY, ss
                            .getSskey());

                    break;
                }
            }
        } catch (Exception ex) {
            m.put(AdsapientConstants.OS_ID_REQUEST_PARAM_KEY,
                    AdsapientConstants.UNIDENTIFIED_OPERATING_SYSTEM_ID);
            m.put(AdsapientConstants.OS_SSKEY_REQUEST_PARAM_KEY,
                    AdsapientConstants.UNIDENTIFIED_OS_SSKEY);
        }

        try {
            m.put(AdsapientConstants.GEOLOCATION_NAME_REQUEST_PARAM_KEY,
                    AdsapientConstants.UNIDENTIFIED_GEOLOCATION_ID);

            String countryName = ipToCountryMappingBean
                    .search((String) m
                            .get(AdsapientConstants.IPADDRESS_UNIQUE_ID_REQUEST_PARAM_KEY));
            m.put(AdsapientConstants.GEOLOCATION_NAME_REQUEST_PARAM_KEY,
                    countryName);
        } catch (Exception ex) {
            m.put(AdsapientConstants.GEOLOCATION_NAME_REQUEST_PARAM_KEY,
                    AdsapientConstants.UNIDENTIFIED_GEOLOCATION_ID);
        }

        String referer = request.getHeader("referer");

        try {
            if (referer == null) {
                referer = request.getParameter("referer");
            }

            if (referer == null) {
                m.put(AdsapientConstants.REFERER_PARAM_KEY, "");
            } else {
                m.put(AdsapientConstants.REFERER_PARAM_KEY, referer);
            }
        } catch (Exception ex) {
            m.put(AdsapientConstants.REFERER_PARAM_KEY, "");
        }
        m.put(AdsapientConstants.RESPONSE_PARAM_KEY, response);

        m.put(AdsapientConstants.REASON_FOR_DEFAULT_REQUEST_PARAM_KEY, "");

        return m;
    }

    public AdserverModel getAdserverModel() {
        return adserverModel;
    }

    public void setAdserverModel(AdserverModel adserverModel) {
        this.adserverModel = adserverModel;
    }

    public List<FilterInterface> getCampaignFilters(BannerImpl banner) {
        if ((banner.getFilters() != null) && (banner.getFilters().size() != 0)) {
            return banner.getFilters();
        }

        CampainImpl campaign = (CampainImpl) adserverModel.getCampaignsMap()
                .get(banner.getCampainId());

        return campaign.getFilters();
    }

    public Map<String, Object> buildTemplateParams(
            Map<String, Object> requestParams) {
        PlacesImpl place = (PlacesImpl) adserverModel.getPlacesMap().get(requestParams.get(AdsapientConstants.PLACEID_REQUEST_PARAM_KEY));
        BannerImpl banner = (BannerImpl) requestParams.get(AdsapientConstants.BANNER_REQUEST_PARAM_KEY);

        int sizeId = 1;
        int placeTypeId = AdsapientConstants.PLACE_TYPE_ORDINARY;
        String bannerIdPair = "";
        String placeIdPair = "";

        if (place != null) {
            placeTypeId = place.getPlaceTypeId();
            sizeId = place.getSizeId();
            placeIdPair = "&" + AdsapientConstants.PLACEID_REQUEST_PARAM_NAME
                    + "=" + place.getId();
        }

        if (banner != null) {
            sizeId = banner.getSizeId();

            bannerIdPair = "&" + AdsapientConstants.BANNERID_REQUEST_PARAM_NAME
                    + "=" + banner.getBannerId();
        }

        String randomPair = "&" + AdsapientConstants.RANDOM2_REQUEST_PARAM_NAME
                + "=" + System.currentTimeMillis();

        Map<String, String> customParams = (Map<String, String>) requestParams.get(AdsapientConstants.CUSTOMREQUESTPARAMS_REQUEST_PARAM_KEY);
        String countPair = "&" + AdsapientConstants.COUNT_REQUEST_PARAM_NAME + "=" + (customParams.get(AdsapientConstants.COUNT_REQUEST_PARAM_NAME) == null ? "true" : customParams.get(AdsapientConstants.COUNT_REQUEST_PARAM_NAME));

        StringBuffer keyValueStrBuffer = new StringBuffer();
        keyValueStrBuffer.append(AdsapientConstants.EVENTID_REQUEST_PARAM_NAME);
        keyValueStrBuffer.append("=");
        keyValueStrBuffer
                .append(AdsapientConstants.GETADCODE_ADSERVEREVENT_TYPE);
        keyValueStrBuffer.append(placeIdPair);
        keyValueStrBuffer.append(bannerIdPair);
        keyValueStrBuffer.append(randomPair);
        keyValueStrBuffer.append(countPair);

        requestParams.put(AdsapientConstants.KEYVALUEPARAMS_REQUEST_PARAM_KEY,
                keyValueStrBuffer.toString());

        Size size = (Size) adserverModel.getSizesMap().get(sizeId);
        requestParams.put(AdsapientConstants.WIDTH_REQUEST_PARAM_KEY, size.getWidth());
        requestParams.put(AdsapientConstants.HEIGHT_REQUEST_PARAM_KEY, size.getHeight());

        requestParams.put(AdsapientConstants.TEMPLATEID_REQUEST_PARAM_KEY,
                placeTypeId);

        return requestParams;
    }

    public Map<String, Object> buildTemplateParams(
            Map<String, Object> requestParams, BannerImpl banner) {
        requestParams.put(AdsapientConstants.TEMPLATEID_REQUEST_PARAM_KEY,
                banner.getTypeId());
        requestParams.put(AdsapientConstants.ALTTEXT_REQUEST_PARAM_KEY, banner
                .getAltText());
        requestParams.put(AdsapientConstants.CONTENT_TYPE_REQUEST_PARAM_KEY,
                banner.getContentType());
        requestParams.put(AdsapientConstants.FILE_NAME_REQUEST_PARAM_KEY,
                banner.getFileName());
        requestParams.put(AdsapientConstants.BANNER_NAME_REQUEST_PARAM_KEY,
                banner.getName());
        requestParams.put(AdsapientConstants.STATUSBARTEXT_REQUEST_PARAM_KEY,
                banner.getStatusBartext());
        requestParams.put(AdsapientConstants.BANNER_ID_REQUEST_PARAM_KEY,
                banner.getBannerId());
        requestParams.put(AdsapientConstants.SMS_NUMBER_REQUEST_PARAM_KEY,
                banner.getSMSNumber());
        requestParams.put(AdsapientConstants.CALL_NUMBER_REQUEST_PARAM_KEY,
                banner.getCallNumber());
        requestParams.put(AdsapientConstants.BANNER_TEXT_REQUEST_PARAM_KEY,
                banner.getBannerText());
        requestParams.put(AdsapientConstants.LIST_TEXT_REQUEST_PARAM_KEY,
                banner.getListText());
        requestParams.put(AdsapientConstants.SMS_TEXT_REQUEST_PARAM_KEY, banner
                .getSmsText());

        Map<String, String> customParams = (Map<String, String>) requestParams
                .get(AdsapientConstants.CUSTOMREQUESTPARAMS_REQUEST_PARAM_KEY);
        StringBuffer countAdUnits = new StringBuffer();

        if ((customParams != null)
                && customParams
                .containsKey(AdsapientConstants.COUNT_REQUEST_PARAM_NAME)) {
            countAdUnits.append("&");
            countAdUnits.append(AdsapientConstants.COUNT_REQUEST_PARAM_NAME);
            countAdUnits.append("=");
            countAdUnits.append(customParams.get(AdsapientConstants.COUNT_REQUEST_PARAM_NAME));
        }

        int sizeId = 1;
        String placeIdStr = "";
        PlacesImpl place = (PlacesImpl) adserverModel
                .getPlacesMap()
                .get(
                        requestParams
                                .get(AdsapientConstants.PLACEID_REQUEST_PARAM_KEY));

        if (banner != null) {
            sizeId = banner.getSizeId();
        } else {
            sizeId = place.getSizeId();
        }

        if (place != null) {
            placeIdStr = new StringBuffer("|")
                    .append(AdsapientConstants.PLACEID_REQUEST_PARAM_NAME)
                    .append("=")
                    .append(
                            Integer
                                    .toString((Integer) requestParams
                                    .get(AdsapientConstants.PLACEID_REQUEST_PARAM_KEY)))
                    .toString();
        }

        String targetUrl = banner.getUrl();

        if (place != null) {
            targetUrl = new StringBuffer((String) requestParams
                    .get(AdsapientConstants.REQUESTURL_REQUEST_PARAM_KEY))
                    .append("?").append(
                    AdsapientConstants.EVENTID_REQUEST_PARAM_NAME)
                    .append("=").append(
                    AdsapientConstants.CLICK_ADSERVEREVENT_TYPE)
                    .append("|").append(
                    AdsapientConstants.BANNERID_REQUEST_PARAM_NAME)
                    .append("=").append(banner.getId()).append(placeIdStr)
                    .append(countAdUnits).toString();
        }

        requestParams.put(AdsapientConstants.TARGETURL_REQUEST_PARAM_KEY,
                targetUrl);

        Size size = (Size) adserverModel.getSizesMap().get(sizeId);
        requestParams.put(AdsapientConstants.WIDTH_REQUEST_PARAM_KEY, size
                .getWidth());
        requestParams.put(AdsapientConstants.HEIGHT_REQUEST_PARAM_KEY, size
                .getHeight());

        requestParams.put(AdsapientConstants.IMAGE_ID_REQUEST_PARAM_KEY, "");
        requestParams.put(AdsapientConstants.IMAGE_TITLE_REQUEST_PARAM_KEY, "");
        requestParams.put(AdsapientConstants.ONUNLOADHANDLER_REQUEST_PARAM_KEY,
                "");

        if (place != null) {
            placeIdStr = "&"
                    + AdsapientConstants.PLACEID_REQUEST_PARAM_NAME
                    + "="
                    + requestParams
                    .get(AdsapientConstants.PLACEID_REQUEST_PARAM_KEY);
        }

        String adSource = new StringBuffer((String) requestParams
                .get(AdsapientConstants.REQUESTURL_REQUEST_PARAM_KEY)).append(
                "?").append(AdsapientConstants.EVENTID_REQUEST_PARAM_NAME)
                .append("=")
                .append(AdsapientConstants.GETAD_ADSERVEREVENT_TYPE)
                .append("&").append(
                AdsapientConstants.BANNERID_REQUEST_PARAM_NAME).append(
                "=").append(banner.getId()).append(placeIdStr).append(
                countAdUnits).toString();

        int adminsDefaultTargetWindowId = Integer.valueOf(adserverModel
                .getOptionsItemNameToItemValueMap().get(
                AdsapientConstants.TARGET_WINDOW_OPTION_KEY));
        String defaultTargetWindow = ((adminsDefaultTargetWindowId == AdsapientConstants.TARGET_WINDOW_NEW) ? AdsapientConstants.TARGET_WINDOW_NEW_LABEL
                : AdsapientConstants.TARGET_WINDOW_SELF_LABEL);

        requestParams.put(AdsapientConstants.ADSOURCE_ID_REQUEST_PARAM_KEY,
                adSource);

        if (place != null) {
            if ((place.getTargetWindowId() == AdsapientConstants.ANY)
                    && (banner.getTargetWindowId() == AdsapientConstants.ANY)) {
                requestParams.put(
                        AdsapientConstants.TARGET_WINDOW_REQUEST_PARAM_KEY,
                        defaultTargetWindow);
            } else if ((place.getTargetWindowId() == AdsapientConstants.TARGET_WINDOW_NEW)
                    && (banner.getTargetWindowId() == AdsapientConstants.ANY)) {
                requestParams.put(
                        AdsapientConstants.TARGET_WINDOW_REQUEST_PARAM_KEY,
                        AdsapientConstants.TARGET_WINDOW_NEW_LABEL);
            } else if ((place.getTargetWindowId() == AdsapientConstants.ANY)
                    && (banner.getTargetWindowId() == AdsapientConstants.TARGET_WINDOW_NEW)) {
                requestParams.put(
                        AdsapientConstants.TARGET_WINDOW_REQUEST_PARAM_KEY,
                        AdsapientConstants.TARGET_WINDOW_NEW_LABEL);
            } else if ((place.getTargetWindowId() == AdsapientConstants.TARGET_WINDOW_NEW)
                    && (banner.getTargetWindowId() == AdsapientConstants.TARGET_WINDOW_SELF)) {
                requestParams.put(
                        AdsapientConstants.TARGET_WINDOW_REQUEST_PARAM_KEY,
                        defaultTargetWindow);
            } else if ((place.getTargetWindowId() == AdsapientConstants.TARGET_WINDOW_SELF)
                    && (banner.getTargetWindowId() == AdsapientConstants.TARGET_WINDOW_NEW)) {
                requestParams.put(
                        AdsapientConstants.TARGET_WINDOW_REQUEST_PARAM_KEY,
                        defaultTargetWindow);
            } else if ((place.getTargetWindowId() == AdsapientConstants.TARGET_WINDOW_SELF)
                    && (banner.getTargetWindowId() == AdsapientConstants.TARGET_WINDOW_SELF)) {
                requestParams.put(
                        AdsapientConstants.TARGET_WINDOW_REQUEST_PARAM_KEY,
                        AdsapientConstants.TARGET_WINDOW_SELF_LABEL);
            } else if ((place.getTargetWindowId() == AdsapientConstants.TARGET_WINDOW_NEW)
                    && (banner.getTargetWindowId() == AdsapientConstants.TARGET_WINDOW_NEW)) {
                requestParams.put(
                        AdsapientConstants.TARGET_WINDOW_REQUEST_PARAM_KEY,
                        AdsapientConstants.TARGET_WINDOW_NEW_LABEL);
            } else {
                requestParams.put(
                        AdsapientConstants.TARGET_WINDOW_REQUEST_PARAM_KEY,
                        AdsapientConstants.TARGET_WINDOW_NEW_LABEL);
            }
        } else {
            requestParams.put(
                    AdsapientConstants.TARGET_WINDOW_REQUEST_PARAM_KEY,
                    AdsapientConstants.TARGET_WINDOW_NEW_LABEL);
        }

        if (banner.getTypeId() == AdsapientConstants.HTML_BANNER_TYPE) {
            String parsedHtmlBannerContent = parseHtmlBannerContent(
                    readHtmlBannerContent(banner), requestParams);
            requestParams.put(AdsapientConstants.HTML_SOURCE_REQUEST_PARAM_KEY,
                    parsedHtmlBannerContent);
        }

        return requestParams;
    }

    private String readHtmlBannerContent(BannerImpl banner) {
        try {
            InputStream is = new FileInputStream(
                    AdsapientConstants.PATH_TO_BANNERS + banner.getFile());
            byte[] bbs = new byte[is.available()];
            is.read(bbs);
            is.close();

            return new String(bbs);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);

            return "";
        }
    }

    protected String parseHtmlBannerContent(String htmlBannerContent, Map<String, Object> requestParams) {
        String replacement = "<a href=\"" + requestParams.get(AdsapientConstants.TARGETURL_REQUEST_PARAM_KEY) + "\" target=\"" + requestParams.get(AdsapientConstants.TARGET_WINDOW_REQUEST_PARAM_KEY) + "\"";
        String parsedHtmlBannerContent = htmlBannerContent.replaceAll("target=\"[^\"]*\"", "");
        parsedHtmlBannerContent = parsedHtmlBannerContent.replaceAll("<\\s*a\\s+[^>]*href\\s*=\\s*[\\\"']?([^\\\"' >]+)[\\\"' >]", replacement);
        parsedHtmlBannerContent = parsedHtmlBannerContent.replaceAll("\\n", "");
        parsedHtmlBannerContent = parsedHtmlBannerContent.replaceAll("\\r", "");
        parsedHtmlBannerContent = parsedHtmlBannerContent.replaceAll("\\'", "\\\\\\\\'");
        String clickTAGPair = "clickTAG=" + requestParams.get(AdsapientConstants.TARGETURL_REQUEST_PARAM_KEY);
        String targetWindowPair = "targetWindow=" + requestParams.get(AdsapientConstants.TARGET_WINDOW_REQUEST_PARAM_KEY);
        parsedHtmlBannerContent = parsedHtmlBannerContent.replaceAll("<\\s*embed\\s+[^>]*src\\s*=(\\s*[\\\"']?([^\\\"' >]+))[\\\"' >]", "<embed src=$1?" + clickTAGPair + "&" + targetWindowPair + "\"");
        parsedHtmlBannerContent = parsedHtmlBannerContent.replaceAll("<\\s*param\\s+[^>]*name=\\\"movie\\\"\\s+[^>]*value\\s*=(\\s*[\\\"']?([^\\\"' >]+))[\\\"' >]", "<param name=\"movie\" value=$1?" + clickTAGPair + "&" + targetWindowPair + "\"");
        return parsedHtmlBannerContent;
    }

    private Map<String, String> getParametersFromRequest(HttpServletRequest req) {
        Map<String, String> parametersMap = new HashMap<String, String>();

        for (Object key : req.getParameterMap().keySet()) {
            String value = req.getParameter((String) key);
            parametersMap.put((String) key, value);
        }

        String eventIdStr = req
                .getParameter(AdsapientConstants.EVENTID_REQUEST_PARAM_NAME);
        String[] keyValuePairs = eventIdStr.split("\\|");

        if (keyValuePairs.length > 0) {
            parametersMap.put(AdsapientConstants.EVENTID_REQUEST_PARAM_NAME,
                    keyValuePairs[0]);

            for (String keyValuePairStr : keyValuePairs) {
                String[] keyValuePair = keyValuePairStr.split("=");

                if (keyValuePair.length == 2) {
                    parametersMap.put(keyValuePair[0], keyValuePair[1]);
                }
            }
        }

        return parametersMap;
    }

    public ParametersService getParametersService() {
        return parametersService;
    }

    public void setParametersService(ParametersService parametersService) {
        this.parametersService = parametersService;
    }

    public IpToCountryMappingBean getIpToCountryMappingBean() {
        return ipToCountryMappingBean;
    }

    public void setIpToCountryMappingBean(
            IpToCountryMappingBean ipToCountryMappingBean) {
        this.ipToCountryMappingBean = ipToCountryMappingBean;
    }
}
