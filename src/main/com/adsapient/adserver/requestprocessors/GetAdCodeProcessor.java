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
import com.adsapient.adserver.reporter.ReporterModelBuilder;

import com.adsapient.api_impl.advertizer.BannerImpl;
import com.adsapient.api_impl.share.Type;

import com.adsapient.util.admin.AdsapientConstants;

import org.apache.log4j.Logger;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetAdCodeProcessor extends AbstractAdsapientProcessor {
	static Logger logger = Logger.getLogger(GetAdCodeProcessor.class);

	private AdserverDecisionProvider adserverDecisionProvider;

	private AdserverModelBuilder adserverModelBuilder;

	private ReporterModelBuilder reporterModelBuilder;

	private AdserverModel adserverModel;

	private GetPlaceCodeProcessor getPlaceCodeProcessor;

	public GetPlaceCodeProcessor getGetPlaceCodeProcessor() {
		return getPlaceCodeProcessor;
	}

	public void setGetPlaceCodeProcessor(
			GetPlaceCodeProcessor getPlaceCodeProcessor) {
		this.getPlaceCodeProcessor = getPlaceCodeProcessor;
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

	public void setAdserverModelBuilder(
			AdserverModelBuilder adserverModelBuilder) {
		this.adserverModelBuilder = adserverModelBuilder;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
	}

	public AdserverDecisionProvider getAdserverDecisionProvider() {
		return adserverDecisionProvider;
	}

	public void setAdserverDecisionProvider(
			AdserverDecisionProvider adserverDecisionProvider) {
		this.adserverDecisionProvider = adserverDecisionProvider;
	}

	public void doGet(Map<String, Object> requestParams,
			HttpServletResponse response) {
		try {
			long t1 = System.currentTimeMillis();

			Integer uniqueId = (Integer) requestParams
					.get(AdsapientConstants.COOKIE_UNIQUE_ID_REQUEST_PARAM_KEY);
			Cookie uniqueIdCookie = new Cookie(
					AdsapientConstants.ADSERVER_UNIQUE_VISITOR_ID_COOKIENAME,
					(uniqueId != null) ? Integer.toString(uniqueId) : null);
			response.addCookie(uniqueIdCookie);
			uniqueIdCookie.setMaxAge(Integer.MAX_VALUE);

			BannerImpl banner = (BannerImpl) requestParams
					.get(AdsapientConstants.BANNER_REQUEST_PARAM_KEY);

			if (banner == null) {
				banner = getAd(requestParams);
			}

			if (banner.getTypeId().equals(Type.SUPERSTITIAL_BANNER)) {
				Map<String, Object> templateParams = adserverModelBuilder
						.buildTemplateParams(requestParams, banner);
				requestParams.put(
						AdsapientConstants.TEMPLATEID_REQUEST_PARAM_KEY,
						AdsapientConstants.SUPERSTITIAL_TEMPLATEID);

				AdcodeTemplate at = templates.get(requestParams
						.get(AdsapientConstants.TEMPLATEID_REQUEST_PARAM_KEY));
				String adcode = at.getAdCodeFromParameters(templateParams);

				writeResponse(adcode, response);

				return;
			}

			requestParams.put(AdsapientConstants.BANNER_REQUEST_PARAM_KEY,
					banner);

			Map<String, Object> templateParams = adserverModelBuilder
					.buildTemplateParams(requestParams, banner);
			AdcodeTemplate at = getAdcodeTemplate(templateParams);
			String adcode = at.getAdCodeFromParameters(templateParams);

			logger.debug("Processing getAdCode request took:"
					+ (System.currentTimeMillis() - t1) + " milliseconds");

			writeResponse(adcode, response);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			writeResponse("", response);
		}
	}

	public BannerImpl getAd(Map<String, Object> requestParams) {
		try {
			BannerImpl banner = null;

			if (requestParams.get(AdsapientConstants.BANNER_REQUEST_PARAM_KEY) != null) {
				banner = (BannerImpl) requestParams
						.get(AdsapientConstants.BANNER_REQUEST_PARAM_KEY);
			} else {
				banner = adserverDecisionProvider.getAd(requestParams);
			}

			return banner;
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);

			return null;
		}
	}

	private AdcodeTemplate getAdcodeTemplate(Map<String, Object> templateParams) {
		AdcodeTemplate at = templates.get((Integer) templateParams
				.get(AdsapientConstants.TEMPLATEID_REQUEST_PARAM_KEY) + 100);

		return at;
	}

	public ReporterModelBuilder getReporterModelBuilder() {
		return reporterModelBuilder;
	}

	public void setReporterModelBuilder(
			ReporterModelBuilder reporterModelBuilder) {
		this.reporterModelBuilder = reporterModelBuilder;
	}
}
