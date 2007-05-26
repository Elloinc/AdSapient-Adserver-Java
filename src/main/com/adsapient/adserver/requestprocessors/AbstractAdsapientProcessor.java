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

import com.adsapient.adserver.beans.AdcodeTemplate;

import org.apache.log4j.Logger;

import java.io.InputStream;
import java.io.PrintWriter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractAdsapientProcessor extends HttpServlet {
	static Logger logger = Logger.getLogger(AbstractAdsapientProcessor.class);

	protected Map<String, String> pathsToTemplates;

	protected Map<Integer, AdcodeTemplate> templates;

	public Map<String, String> getPathsToTemplates() {
		return pathsToTemplates;
	}

	public void setPathsToTemplates(Map<String, String> pathsToTemplates) {
		this.pathsToTemplates = pathsToTemplates;
	}

	public Map<Integer, AdcodeTemplate> getTemplates() {
		return templates;
	}

	public void setTemplates(Map<Integer, AdcodeTemplate> templates) {
		this.templates = templates;
	}

	public void setup() {
		try {
			templates = new HashMap<Integer, AdcodeTemplate>();

			for (String templateId : pathsToTemplates.keySet()) {
				String pathToTemplate = pathsToTemplates.get(templateId);
				AdcodeTemplate t = new AdcodeTemplate();
				t.setTemplateStr(readTemplateFromFile(pathToTemplate));

				templates.put(Integer.parseInt(templateId), t);
			}
		} catch (Exception ex) {
			logger.fatal(ex.getMessage(), ex);
		}
	}

	private String readTemplateFromFile(String pathToTemplate) throws Exception {
		InputStream is = getClass().getClassLoader().getResourceAsStream(
				pathToTemplate);
		byte[] bbs = new byte[is.available()];
		is.read(bbs);
		is.close();

		return new String(bbs);
	}

	protected void writeResponse(String str, HttpServletResponse response) {
		try {
			PrintWriter pw = response.getWriter();
            pw.write(str);
			pw.flush();
			pw.close();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
	}
}
