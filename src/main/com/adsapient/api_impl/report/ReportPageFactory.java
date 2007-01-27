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
package com.adsapient.api_impl.report;

import com.adsapient.api.ReportPageBuilderInterface;
import com.adsapient.api.ReportTransformer;

import com.adsapient.api_impl.report.transformer.ExelTransformer;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class ReportPageFactory {
	private static Logger logger = Logger.getLogger(ReportPageFactory.class);

	public static ReportPage createPage(HttpServletRequest request) {
		StringBuffer buffer = new StringBuffer(
				"com.adsapient.api_impl.report.builder.");
		String pageType = request.getParameter("statisticType");

		buffer.append(pageType).append("PageBuilder");

		Class pageBuilderClass = null;
		ReportPageBuilderInterface pageBuilder;

		try {
			pageBuilderClass = Class.forName(buffer.toString());
			pageBuilder = (ReportPageBuilderInterface) pageBuilderClass
					.newInstance();
		} catch (Exception e) {
			logger.error("Cant  create page builder. The class was "
					+ buffer.toString(), e);

			return new ReportPage();
		}

		ReportPage page = pageBuilder.createPage(request);
		ReportTransformer transformer = new ExelTransformer();
		page.setTransformer(transformer);

		return page;
	}
}
