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

import org.apache.log4j.Logger;

import org.apache.struts.Globals;
import org.apache.struts.util.MessageResources;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class I18nService {
	static Logger logger = Logger.getLogger(I18nService.class);

	private static MessageResources messageRes = null;

	public static String fetch(String key, HttpSession session) {
		Locale locale = (Locale) session.getAttribute(Globals.LOCALE_KEY);
		String value = messageRes.getMessage(locale, key);

		if (value == null) {
			logger.error("No message found for locale: " + locale + ", key: "
					+ key + ". Returning \"\"");

			return "";
		}

		return value;
	}

	public static String fetch(String key, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Locale l = (Locale) session.getAttribute(Globals.LOCALE_KEY);

		if (I18nService.messageRes == null) {
			return "welcome";
		}

		String value = messageRes.getMessage(l, key);

		if (value == null) {
			logger.error("No message found for locale: " + l + ", key: " + key
					+ ". Returning \"\"");

			return "";
		}

		return value;
	}

	public static void init(MessageResources msgRes) {
		messageRes = msgRes;
	}

	private static void check(HttpServletRequest request) {
		if (I18nService.messageRes == null) {
		}
	}
}
