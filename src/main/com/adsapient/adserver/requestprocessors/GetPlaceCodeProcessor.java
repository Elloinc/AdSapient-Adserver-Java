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
package com.adsapient.adserver.requestprocessors;

import com.adsapient.adserver.AdserverDecisionProvider;
import com.adsapient.adserver.AdserverModelBuilder;
import com.adsapient.adserver.beans.AdcodeTemplate;
import com.adsapient.adserver.beans.AdserverModel;

import com.adsapient.util.admin.AdsapientConstants;

import org.apache.log4j.Logger;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetPlaceCodeProcessor extends AbstractAdsapientProcessor {
	static Logger logger = Logger.getLogger(GetPlaceCodeProcessor.class);

	private AdserverDecisionProvider adserverDecisionProvider;

	private AdserverModel adserverModel;

	private AdserverModelBuilder adserverModelBuilder;

	public AdserverModelBuilder getAdserverModelBuilder() {
		return adserverModelBuilder;
	}

	public void setAdserverModelBuilder(
			AdserverModelBuilder adserverModelBuilder) {
		this.adserverModelBuilder = adserverModelBuilder;
	}

	public AdserverModel getAdserverModel() {
		return adserverModel;
	}

	public void setAdserverModel(AdserverModel adserverModel) {
		this.adserverModel = adserverModel;
	}

	public AdserverDecisionProvider getAdserverDecisionProvider() {
		return adserverDecisionProvider;
	}

	public void setAdserverDecisionProvider(
			AdserverDecisionProvider adserverDecisionProvider) {
		this.adserverDecisionProvider = adserverDecisionProvider;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		logger.debug("getad");
	}

	public void doGet(Map<String, Object> requestParams,
			HttpServletResponse response) {
		String adcode = getAdCode(requestParams);
		writeResponse(adcode, response);
	}

	public String getAdCode(Map<String, Object> requestParams) {
		Map<String, Object> templateParams = adserverModelBuilder
				.buildTemplateParams(requestParams);

		AdcodeTemplate at = templates
				.get(AdsapientConstants.PLACE_TYPE_ORDINARY);
		String adcode = at.getAdCodeFromParameters(templateParams);

		return adcode;
	}
}
