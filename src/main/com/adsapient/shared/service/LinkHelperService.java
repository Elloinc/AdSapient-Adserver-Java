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
package com.adsapient.shared.service;

import com.adsapient.util.Msg;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.URL;

import javax.servlet.http.HttpServletRequest;

public class LinkHelperService {
	static Logger logger = Logger.getLogger(LinkHelperService.class);

	private String pathToAdServer;

	public String getAdCodeByBannerId(Integer bannerId) {
		return null;
	}

	public String getPlaceWithBannerByBannerId(Integer bannerId,
			HttpServletRequest request) {
		try {
			if (bannerId == null) {
				return "";
			}

			URL u = new URL(pathToAdServer
					+ "sv?count=false&eventId=1&bannerId=" + bannerId);
			BufferedReader in = new BufferedReader(new InputStreamReader(u
					.openStream()));

			String inputLine;
			StringBuffer sb = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				sb.append(inputLine);
			}

			in.close();

			return new String(sb.toString().getBytes(), "UTF-8");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		return "";
	}

	public String getPlaceCodeByPlaceId(Integer placeId,
			HttpServletRequest request) {
		try {
			if (placeId == null) {
				return Msg.fetch("htmlsource.notavailable", request);
			}

			URL u = new URL(pathToAdServer + "sv?eventId=1&placeId=" + placeId);
			BufferedReader in = new BufferedReader(new InputStreamReader(u
					.openStream()));

			String inputLine;
			StringBuffer sb = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				sb.append(inputLine);
				sb.append("\n");
			}

			in.close();

			return new String(sb.toString().getBytes(), "UTF-8").replaceAll(
					"\\s\\s", " ");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		return "";
	}

	public String getPathToAdServer() {
		return pathToAdServer;
	}

	public void setPathToAdServer(String pathToAdServer) {
		this.pathToAdServer = pathToAdServer;
	}
}
