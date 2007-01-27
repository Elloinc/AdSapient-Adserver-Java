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

import com.adsapient.util.admin.AdsapientConstants;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestServlet extends HttpServlet {
	static Logger logger = Logger.getLogger(TestServlet.class);

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Integer placeId = null;

		try {
			placeId = new Integer(
					request
							.getParameter(AdsapientConstants.PLACEID_REQUEST_PARAM_NAME));
		} catch (NumberFormatException e) {
			logger
					.warn(
							"cant transform placeId to Integer. The source parameter was"
									+ request
											.getParameter(AdsapientConstants.PLACEID_REQUEST_PARAM_NAME),
							e);
		}

		int testCount = Integer.parseInt(request.getParameter("count"));
		logger.info("begin testing request. Count is:" + testCount
				+ "  Placeid is" + placeId);

		long beginTime = System.currentTimeMillis();

		for (int i = 0; i < testCount; i++) {
			Collection banners = null;
		}

		long endTime = System.currentTimeMillis();

		logger.info("com.adsapient.adserver.test zakonchen za:"
				+ (endTime - beginTime));

		PrintWriter out = response.getWriter();

		out.println("Count is:" + testCount + "  Placeid is" + placeId);
		out.println("com.adsapient.adserver.test zakonchen za:"
				+ (endTime - beginTime));
		out.flush();
		out.close();
	}
}
