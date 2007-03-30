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
package com.adsapient.adserver.filters.stateless;

import com.maxmind.geoip.LookupService;

import java.io.File;

import java.net.URL;

public class RequestParamsFilterProcessorTest {
	public static LookupService cl = null;

	public static void main(String[] args) {
		try {
			URL url = RequestParamsFilterProcessor.class.getClassLoader()
					.getResource("setup/GeoIP.dat");
			File f = new File(url.toURI());
			String durgasIp = "125.22.42.194";
			cl = new LookupService(f, LookupService.GEOIP_STANDARD);

			String visitorCountry = cl.getCountry(durgasIp).getCode();
			System.out.println(visitorCountry);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
