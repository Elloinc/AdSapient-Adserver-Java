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
package com.adsapient.adserver.beans;

import java.util.Map;

public class AdcodeTemplate {
	private String templateStr;

	public String getTemplateStr() {
		return templateStr;
	}

	public void setTemplateStr(String templateStr) {
		this.templateStr = templateStr;
	}

	public String getAdCodeFromParameters(Map<String, Object> params) {
		String ret = new String(templateStr);

		for (String paramLabel : params.keySet()) {
			Object obj = params.get(paramLabel);
			String paramValue = "";

			if (obj instanceof String) {
				paramValue = (String) obj;
			} else if (obj instanceof Integer) {
				paramValue = String.valueOf(obj);
			}

			ret = ret.replaceAll("\\{" + paramLabel + "\\}", paramValue);
		}

		return ret;
	}
}
