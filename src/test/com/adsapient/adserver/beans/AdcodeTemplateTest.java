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

import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

public class AdcodeTemplateTest extends TestCase {
	public static String testStr = "{adserverlocation}/{appcontextpath}/{adserverservlet}?{keyvalueparams}','_blank','toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=\"{width}\",height=\"{height}\",left=200,top=200";

	public static String resultStr = "http://localhost:8080/adsapient/sv?placeId=4','_blank','toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=\"468\",height=\"60\",left=200,top=200";

	AdcodeTemplate at;

	Map<String, Object> params;

	public void setUp() throws Exception {
		super.setUp();
		at = new AdcodeTemplate();
		at.setTemplateStr(testStr);

		params = new HashMap<String, Object>();
		params.put("adserverlocation", "http://localhost:8080");
		params.put("appcontextpath", "adsapient");
		params.put("adserverservlet", "sv");
		params.put("keyvalueparams", "placeId=4");
		params.put("width", "468");
		params.put("height", "60");

		params.put("targeturl", "campainRedirect?&bannerId=20&placeId=4");
		params.put("id", "ads5463971f0d0ec58e010d98d3a77f03a9");
		params.put("adsource", "image?bannerId=20");
		params.put("alttext", "adsapient.com");
		params.put("titletext", "adsapient.com");
		params.put("statusbartext", "adsapient.com");
		params.put("onunloadhandler",
				"timeRegister?statisticId=5463971f0d0ec58e010d98d3a77f03a9");
	}

	public void testTemplateCompile() {
		assertEquals(resultStr, at.getAdCodeFromParameters(params));
	}

	public void testAdcodeCompile() {
	}
}
