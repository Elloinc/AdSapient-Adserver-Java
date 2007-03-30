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
package com.adsapient.shared.mappable;

import com.adsapient.shared.AdsapientConstants;

import org.apache.log4j.Logger;

public class FiltersTemplate {
	static Logger logger = Logger.getLogger(FiltersTemplate.class);

	private Integer bannerId;

	private Integer campainId;

	private Integer templateId;

	private Integer userId;

	private String behaviorFilterId;

	private String contentFilterId;

	private String dateFilterId;

	private String geoFilterId;

	private String keyWordsFilterId;

	private String parameterFilterId;

	private String systemsFilterId;

	private String templateName;

	private String timeFilterId;

	private String trafficFilterId;

	public void setBehaviorFilterId(String behaviorFilterId) {
		this.behaviorFilterId = behaviorFilterId;
	}

	public String getBehaviorFilterId() {
		return behaviorFilterId;
	}

	public void setCampainId(Integer campainId) {
		this.campainId = campainId;
	}

	public Integer getCampainId() {
		return campainId;
	}

	public void setContentFilterId(String contentFilterId) {
		this.contentFilterId = contentFilterId;
	}

	public String getContentFilterId() {
		return contentFilterId;
	}

	public void setDateFilterId(String dateFilterId) {
		this.dateFilterId = dateFilterId;
	}

	public String getDateFilterId() {
		return dateFilterId;
	}

	public void setFilterId4FilterKey(String key, String id) {
		if (AdsapientConstants.TIME_FILTER.equals(key)) {
			this.timeFilterId = id;
		}

		if (AdsapientConstants.DATE_FILTER.equals(key)) {
			this.dateFilterId = id;
		}

		if (AdsapientConstants.GEO_FILTER.equals(key)) {
			this.geoFilterId = id;
		}

		if (AdsapientConstants.TRAFFIC_FILTER.equals(key)) {
			this.trafficFilterId = id;
		}

		if (AdsapientConstants.CONTENT_FILTER.equals(key)) {
			this.contentFilterId = id;
		}

		if (AdsapientConstants.KEYWORDS_FILTER.equals(key)) {
			this.keyWordsFilterId = id;
		}

		if (AdsapientConstants.BEHAVIOR_FILTER.equalsIgnoreCase(key)) {
			this.behaviorFilterId = id;
		}

		if (AdsapientConstants.PARAMETERS_FILTER.equals(key)) {
			this.parameterFilterId = id;
		}

		if (AdsapientConstants.SYSTEMS_FILTER.equals(key)) {
			this.systemsFilterId = id;
		}
	}

	public String getFilterIdByFilterKey(String key) {
		if (AdsapientConstants.TIME_FILTER.equalsIgnoreCase(key)) {
			return this.timeFilterId;
		}

		if (AdsapientConstants.DATE_FILTER.equalsIgnoreCase(key)) {
			return this.dateFilterId;
		}

		if (AdsapientConstants.GEO_FILTER.equalsIgnoreCase(key)) {
			return this.geoFilterId;
		}

		if (AdsapientConstants.TRAFFIC_FILTER.equalsIgnoreCase(key)) {
			return this.trafficFilterId;
		}

		if (AdsapientConstants.CONTENT_FILTER.equalsIgnoreCase(key)) {
			return this.contentFilterId;
		}

		if (AdsapientConstants.PARAMETERS_FILTER.equalsIgnoreCase(key)) {
			return this.parameterFilterId;
		}

		if (AdsapientConstants.KEYWORDS_FILTER.equals(key)) {
			return this.keyWordsFilterId;
		}

		if (AdsapientConstants.SYSTEMS_FILTER.equals(key)) {
			return this.systemsFilterId;
		}

		if (AdsapientConstants.BEHAVIOR_FILTER.equalsIgnoreCase(key)) {
			return this.behaviorFilterId;
		}

		logger.warn("Cant find filterId in getFlterIdByfilterKey for key="
				+ key);

		return null;
	}

	public void setGeoFilterId(String geoFilterId) {
		this.geoFilterId = geoFilterId;
	}

	public String getGeoFilterId() {
		return geoFilterId;
	}

	public void setKeyWordsFilterId(String keyWordsFilterId) {
		this.keyWordsFilterId = keyWordsFilterId;
	}

	public String getKeyWordsFilterId() {
		return keyWordsFilterId;
	}

	public void setParameterFilterId(String parameterFilterId) {
		this.parameterFilterId = parameterFilterId;
	}

	public String getParameterFilterId() {
		return parameterFilterId;
	}

	public void setSystemsFilterId(String systemsFilterId) {
		this.systemsFilterId = systemsFilterId;
	}

	public String getSystemsFilterId() {
		return systemsFilterId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTimeFilterId(String timeFilterId) {
		this.timeFilterId = timeFilterId;
	}

	public String getTimeFilterId() {
		return timeFilterId;
	}

	public void setTrafficFilterId(String trafficFilterId) {
		this.trafficFilterId = trafficFilterId;
	}

	public String getTrafficFilterId() {
		return trafficFilterId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getUserId() {
		return userId;
	}

	public Integer getBannerId() {
		return bannerId;
	}

	public void setBannerId(Integer bannerId) {
		this.bannerId = bannerId;
	}
}
