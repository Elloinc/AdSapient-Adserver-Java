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

import com.adsapient.test.BaseServeTest;

import com.adsapient.shared.AdsapientConstants;

import org.apache.cactus.WebRequest;
import org.apache.cactus.WebResponse;

import java.io.IOException;

import javax.servlet.ServletException;

public class AdserverServletTest extends BaseServeTest {
	public void setUp() {
	}

	public void tearDown() {
	}

	public void beginReadServletOutputStream(WebRequest theRequest) {
		theRequest.setURL("localhost:8080", "/adsapient", "/sv", null, null);
		theRequest.addParameter(AdsapientConstants.EVENT_PARAMETER, String
				.valueOf(AdsapientConstants.GETADCODE_ADSERVEREVENT_TYPE));
	}

	public void testReadServletOutputStream() throws IOException,
			ServletException {
		AdserverServlet servlet = new AdserverServlet();
		servlet.init();
		servlet.doGet(request, response);
	}

	public void endReadServletOutputStream(WebResponse theResponse)
			throws IOException {
		String result = theResponse.getText();
		assertNotNull(result);
		System.out.println(result);
	}
}
