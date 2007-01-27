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
package com.adsapient.util.admin;

import java.text.SimpleDateFormat;

public class AdsapientConstants {
	public static final String USER = "user";

	public static final String PARENT_USER = "parent";

	public static final String DATE_FORMAT = "dd.MM.yyyy";

	public static final String STATISTIC_DATE_FORMAT = "dd/MM";

	public static final String PLACES_ID = "placeId";

	public static final String DEMO = "demo";

	public static final String TIME_FILTER = "timeFilter";

	public static final String DATE_FILTER = "dateFilter";

	public static final String GEO_FILTER = "geoFilter";

	public static final String TRAFFIC_FILTER = "trafficFilter";

	public static final String CONTENT_FILTER = "contentFilter";

	public static final String KEYWORDS_FILTER = "keywordsFilter";

	public static final String PARAMETERS_FILTER = "parametersFilter";

	public static final String SYSTEMS_FILTER = "systemsFilter";

	public static final String KEYWORD_PARAMETER = "keyword";

	public static final String BEHAVIOR_FILTER = "behaviorFilter";

	public static final String BANNER_ID = "bannerId";

	public static final String EMPTY = "";

	public static final String GUEST = "guest";

	public static final String ADSERVER_UNIQUE_VISITOR_ID_COOKIENAME = "ADSERVER_UNIQUE_VISITOR_ID_COOKIENAME";

	public static final SimpleDateFormat SAMPLE_DATE_FORMAT = new SimpleDateFormat(
			AdsapientConstants.DATE_FORMAT);

	public static final byte GETPLACECODE_ADSERVEREVENT_TYPE = 1;

	public static final byte GETADCODE_ADSERVEREVENT_TYPE = 2;

	public static final byte GETAD_ADSERVEREVENT_TYPE = 3;

	public static final byte CLICK_ADSERVEREVENT_TYPE = 4;

	public static final byte LEAD_ADSERVEREVENT_TYPE = 6;

	public static final byte SALE_ADSERVEREVENT_TYPE = 7;

	public static final byte ONUNLOAD_ADSERVEREVENT_TYPE = 8;

	public static final byte VIDEOAD_ADSERVEREVENT_TYPE = 9;

	public static final byte UPDATE_ENTITY_ADSERVEREVENT_TYPE = 10;

	public static final byte IMAGE_BANNER_TYPE = 1;

	public static final byte HTML_BANNER_TYPE = 2;

	public static final byte FLASH_BANNER_TYPE = 3;

	public static final byte VIDEO_BANNER_TYPE = 6;

	public static final byte LANGUAGE_TYPE_ID = 1;

	public static final byte BROWSER_TYPE_ID = 2;

	public static final byte OS_TYPE_ID = 3;

	public static final Integer UNIDENTIFIED_BROWSER_ID = 1000;

	public static final Integer UNIDENTIFIED_LANGUAGE_ID = 0;

	public static final Integer UNIDENTIFIED_OPERATING_SYSTEM_ID = 3000;

	public static final String UNIDENTIFIED_LANGUAGE_SSKEY = "unidentified language";

	public static final String UNIDENTIFIED_BROWSER_SSKEY = "unidentified browser";

	public static final String UNIDENTIFIED_OS_SSKEY = "unidentified operating system";

	public static final String UNIDENTIFIED_GEOLOCATION_ID = "Not found";

	public static final String TARGET_WINDOW_OPTION_KEY = "targetWindow";

	public static final String ADSOURCE_TEMPLATE_PARAM_LABEL = "adsource";

	public static final String CLICKTAG_TEMPLATE_PARAM_LABEL = "clicktag";

	public static final String TARGETWINDOW_TEMPLATE_PARAM_LABEL = "targetwindow";

	public static final String WIDTH_TEMPLATE_PARAM_LABEL = "width";

	public static final String HEIGHT_TEMPLATE_PARAM_LABEL = "height";

	public static final String ONUNLOADHANDLER_TEMPLATE_PARAM_LABEL = "onunloadhandler";

	public static final String ALTTEXT_TEMPLATE_PARAM_LABEL = "alttext";

	public static final String TITLETEXT_TEMPLATE_PARAM_LABEL = "titletext";

	public static final String STATUSBARTEXT_TEMPLATE_PARAM_LABEL = "statusbartext";

	public static final String TARGETURL_TEMPLATE_PARAM_LABEL = "targeturl";

	public static final String POPUPDEF_TEMPLATE_PARAM_LABEL = "popupdef";

	public static final String ADSERVERLOCATION_TEMPLATE_PARAM_LABEL = "adserverlocation";

	public static final String APPCONTEXTPATH_TEMPLATE_PARAM_LABEL = "appcontextpath";

	public static final String ADSERVERSERVLET_TEMPLATE_PARAM_LABEL = "adserverservlet";

	public static final String KEYVALUEPARAMS_TEMPLATE_PARAM_LABEL = "keyvalueparams";

	public static final String REFERER_NAME = "referer";

	public static final String EVENT_TYPE_REQUEST_PARAM_KEY = "EVENT_TYPE_REQUEST_PARAM_KEY";

	public static final String ADPLACE_REQUEST_PARAM_KEY = "ADPLACE_REQUEST_PARAM_KEY";

	public static final String BANNER_REQUEST_PARAM_KEY = "BANNER_REQUEST_PARAM_KEY";

	public static final String TEMPLATEID_REQUEST_PARAM_KEY = "TEMPLATEID_REQUEST_PARAM_KEY";

	public static final String PLACEID_REQUEST_PARAM_KEY = "PLACEID_REQUEST_PARAM_KEY";

	public static final String REQUESTURL_REQUEST_PARAM_KEY = "REQUESTURL_REQUEST_PARAM_KEY";

	public static final String KEYVALUEPARAMS_REQUEST_PARAM_KEY = "KEYVALUEPARAMS_REQUEST_PARAM_KEY";

	public static final String WIDTH_REQUEST_PARAM_KEY = "WIDTH_REQUEST_PARAM_KEY";

	public static final String HEIGHT_REQUEST_PARAM_KEY = "HEIGHT_REQUEST_PARAM_KEY";

	public static final String ALTTEXT_REQUEST_PARAM_KEY = "ALTTEXT_REQUEST_PARAM_KEY";

	public static final String STATUSBARTEXT_REQUEST_PARAM_KEY = "STATUSBARTEXT_REQUEST_PARAM_KEY";

	public static final String TARGETURL_REQUEST_PARAM_KEY = "TARGETURL_REQUEST_PARAM_KEY";

	public static final String IMAGE_ID_REQUEST_PARAM_KEY = "IMAGE_ID_REQUEST_PARAM_KEY";

	public static final String IMAGE_TITLE_REQUEST_PARAM_KEY = "IMAGE_TITLE_REQUEST_PARAM_KEY";

	public static final String ADSOURCE_ID_REQUEST_PARAM_KEY = "ADSOURCE_ID_REQUEST_PARAM_KEY";

	public static final String TARGET_WINDOW_REQUEST_PARAM_KEY = "TARGET_WINDOW_REQUEST_PARAM_KEY";

	public static final String ONUNLOADHANDLER_REQUEST_PARAM_KEY = "ONUNLOADHANDLER_REQUEST_PARAM_KEY";

	public static final String COOKIE_UNIQUE_ID_REQUEST_PARAM_KEY = "COOKIE_UNIQUE_ID_REQUEST_PARAM_KEY";

	public static final String IPADDRESS_UNIQUE_ID_REQUEST_PARAM_KEY = "IPADDRESS_UNIQUE_ID_REQUEST_PARAM_KEY";

	public static final String HEADERSTATICINFO_UNIQUE_ID_REQUEST_PARAM_KEY = "HEADERSTATICINFO_UNIQUE_ID_REQUEST_PARAM_KEY";

	public static final String HTML_SOURCE_REQUEST_PARAM_KEY = "HTML_SOURCE_REQUEST_PARAM_KEY";

	public static final String CONTENT_TYPE_REQUEST_PARAM_KEY = "CONTENT_TYPE_REQUEST_PARAM_KEY";

	public static final String FILE_NAME_REQUEST_PARAM_KEY = "FILE_NAME_REQUEST_PARAM_KEY";

	public static final String BANNER_NAME_REQUEST_PARAM_KEY = "BANNER_NAME_REQUEST_PARAM_KEY";

	public static final String BANNER_ID_REQUEST_PARAM_KEY = "BANNER_ID_REQUEST_PARAM_KEY";

	public static final String CUSTOMREQUESTPARAMS_REQUEST_PARAM_KEY = "CUSTOMREQUESTPARAMS_REQUEST_PARAM_KEY";

	public static final String BROWSER_ID_REQUEST_PARAM_KEY = "BROWSER_ID_REQUEST_PARAM_KEY";

	public static final String LANGUAGE_ID_REQUEST_PARAM_KEY = "LANGUAGE_ID_REQUEST_PARAM_KEY";

	public static final String OS_ID_REQUEST_PARAM_KEY = "OS_ID_REQUEST_PARAM_KEY";

	public static final String BROWSER_SSKEY_REQUEST_PARAM_KEY = "BROWSER_SSKEY_REQUEST_PARAM_KEY";

	public static final String LANGUAGE_SSKEY_REQUEST_PARAM_KEY = "LANGUAGE_SSKEY_REQUEST_PARAM_KEY";

	public static final String OS_SSKEY_REQUEST_PARAM_KEY = "OS_SSKEY_REQUEST_PARAM_KEY";

	public static final String GEOLOCATION_NAME_REQUEST_PARAM_KEY = "GEOLOCATION_NAME_REQUEST_PARAM_KEY";

	public static final String REFERER_PARAM_KEY = "REFERER_PARAM_KEY";

	public static final String KEYWORDS_REQUEST_PARAM_KEY = "KEYWORDS_REQUEST_PARAM_KEY";

	public static final String RESOURCE_TYPE_REQUEST_PARAM_KEY = "RESOURCE_TYPE_REQUEST_PARAM_KEY";

	public static final String SMS_NUMBER_REQUEST_PARAM_KEY = "SMS_NUMBER_REQUEST_PARAM_KEY";

	public static final String SMS_TEXT_REQUEST_PARAM_KEY = "SMS_TEXT_REQUEST_PARAM_KEY";

	public static final String CALL_NUMBER_REQUEST_PARAM_KEY = "CALL_NUMBER_REQUEST_PARAM_KEY";

	public static final String BANNER_TEXT_REQUEST_PARAM_KEY = "BANNER_TEXT_REQUEST_PARAM_KEY";

	public static final String LIST_TEXT_REQUEST_PARAM_KEY = "LIST_TEXT_REQUEST_PARAM_KEY";

	public static final byte ADVIEW = 0;

	public static final byte CLICK = 1;

	public static final byte LEAD = 2;

	public static final byte SALE = 3;

	public static final byte EARNEDSPENT = 4;

	public static final byte CTR = 5;

	public static final byte NOEVENT = -128;

	public static final String HREF_PARAMETER = "href";

	public static final String TARGET_PARAMETER = "target";

	public static final String BANNERID_REQUEST_PARAM_NAME = "bannerId";

	public static final String PLACEID_REQUEST_PARAM_NAME = "placeId";

	public static final String EVENTID_REQUEST_PARAM_NAME = "eventId";

	public static final String CLASSNAME_REQUEST_PARAM_NAME = "className";

	public static final String ENTITYID_REQUEST_PARAM_NAME = "entityId";

	public static final String USERID_REQUEST_PARAM_NAME = "userId";

	public static final String SITEID_REQUEST_PARAM_NAME = "siteId";

	public static final String CAMPAIGNID_REQUEST_PARAM_NAME = "campaignId";

	public static final String RANDOM_REQUEST_PARAM_NAME = "Random";

	public static final String RANDOM2_REQUEST_PARAM_NAME = "rnd";

	public static final String COUNT_REQUEST_PARAM_NAME = "count";

	public static final String KEYWORDS_PARAMETER = "keywords";

	public static final String EVENT_PARAMETER = "eventId";

	public static final int TARGET_WINDOW_TYPE = 1;

	public static final int ANY = 1;

	public static final int TARGET_WINDOW_SELF = 2;

	public static final int TARGET_WINDOW_NEW = 3;

	public static final String TARGET_WINDOW_NEW_LABEL = "_blank";

	public static final String TARGET_WINDOW_SELF_LABEL = "_top";

	public static final int LOADING_TYPE_TYPE = 3;

	public static final int PLACE_TYPE_TYPE = 2;

	public static final int PLACE_TYPE_ORDINARY = 2;

	public static final int PLACE_TYPE_POPUP = 3;

	public static final int PLACE_TYPE_POPUNDER = 4;

	public static final int LOADING_TYPE_IMMEDIATE = 2;

	public static final int LOADING_TYPE_ON_PAGE_LOAD = 3;

	public static final int LOADING_TYPE_ON_PAGE_UNLOAD = 4;

	public static String PATH_TO_BANNERS;

	public static final String BANNER_ENTITY_ID = "B";

	public static final String ADPLACE_ENTITY_ID = "P";

	public static final String COUNTRY_ADDRES_ANONIMOUS = "Anonymous";

	public static final String COUNTRY_ADDRES_NOT_FOUND = "Not found";

	public static final String COUNTRY_ADDRES_REZERVED = "Reserved";

	public static final String COUNTRY_ABBR_ADDRES_ANONIMOUS = "--";

	public static final String COUNTRY_ABBR_ADDRES_NOT_FOUND = "==";

	public static final String COUNTRY_ABBR_ADDRES_RESERVED = "++";

	public static final String COUNTRY_ABBR_ADDRES_ANONIMOUS3 = "---";

	public static final String COUNTRY_ABBR_ADDRES_NOT_FOUND3 = "===";

	public static final String COUNTRY_ABBR_ADDRES_RESERVED3 = "+++";

	public static final int IFRAME_WRAPPER_TEMPLATEID = 7;

	public static final int SUPERSTITIAL_TEMPLATEID = 8;
}
