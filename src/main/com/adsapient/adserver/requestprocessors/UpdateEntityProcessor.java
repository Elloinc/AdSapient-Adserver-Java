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

import com.adsapient.adserver.ModelUpdater;

import com.adsapient.util.admin.AdsapientConstants;

import org.apache.log4j.Logger;

import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateEntityProcessor extends HttpServlet {
	static Logger logger = Logger.getLogger(UpdateEntityProcessor.class);

	private ModelUpdater modelUpdater;

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
	}

	public void doGet(Map<String, Object> requestParams,
			HttpServletResponse response) {
		try {
			Map<String, String> m = (Map<String, String>) requestParams
					.get(AdsapientConstants.CUSTOMREQUESTPARAMS_REQUEST_PARAM_KEY);
			String className = m
					.get(AdsapientConstants.CLASSNAME_REQUEST_PARAM_NAME);
			Integer entityId = Integer.parseInt(m
					.get(AdsapientConstants.ENTITYID_REQUEST_PARAM_NAME));
			modelUpdater.update(Class.forName(className), entityId,
					requestParams);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public ModelUpdater getModelUpdater() {
		return modelUpdater;
	}

	public void setModelUpdater(ModelUpdater modelUpdater) {
		this.modelUpdater = modelUpdater;
	}
}
