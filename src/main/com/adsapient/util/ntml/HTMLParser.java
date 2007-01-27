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
package com.adsapient.util.ntml;

import com.adsapient.util.admin.AdsapientConstants;

import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class HTMLParser {
	private static Logger logger = Logger.getLogger(HTMLParser.class);

	public static String parseHTMLFile(String htmlFileContent,
			HttpServletRequest request) {
		String href = request.getParameter(AdsapientConstants.HREF_PARAMETER);
		String target = request
				.getParameter(AdsapientConstants.TARGET_PARAMETER);
		href = href.replaceFirst("_", "&");

		CharSequence inputStr = htmlFileContent;
		String patternStr = "(<a [^>]+>)";

		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(inputStr);

		StringBuffer buf = new StringBuffer();
		boolean found = false;

		while ((found = matcher.find())) {
			StringBuffer link = new StringBuffer();
			link.append("<a href=\"").append(href).append("\" target=\"")
					.append(target).append("\" >");

			String replaceStr = matcher.group();

			matcher.appendReplacement(buf, link.toString());
		}

		matcher.appendTail(buf);

		return buf.toString();
	}
}
