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

import com.adsapient.adserver.reporter.ReporterModelBuilder;

import com.adsapient.shared.mappable.BannerImpl;
import com.adsapient.shared.AdsapientConstants;

import org.apache.log4j.Logger;

import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ClickProcessor extends HttpServlet {
	static Logger logger = Logger.getLogger(ClickProcessor.class);

	private ReporterModelBuilder reporterModelBuilder;

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
	}

	public void doGet(Map<String, Object> requestParams,
			HttpServletResponse response) {
		try {
			reporterModelBuilder.registerEvent(requestParams);

			BannerImpl banner = (BannerImpl) requestParams
					.get(AdsapientConstants.BANNER_REQUEST_PARAM_KEY);
			String targetUrl = banner.getUrl();
			response.sendRedirect(targetUrl);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
	}

	public ReporterModelBuilder getReporterModelBuilder() {
		return reporterModelBuilder;
	}

	public void setReporterModelBuilder(
			ReporterModelBuilder reporterModelBuilder) {
		this.reporterModelBuilder = reporterModelBuilder;
	}
}
