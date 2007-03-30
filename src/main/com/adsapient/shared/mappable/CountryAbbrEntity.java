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
package com.adsapient.shared.mappable;

import com.adsapient.adserver.geoip.IGeoIpEntry;

public class CountryAbbrEntity implements IGeoIpEntry {
	private Integer id;

	private String countryAbbr2;

	private String countryAbbr3;

	private String countryName;

	public String getCountryAbbr2() {
		return countryAbbr2;
	}

	public void setCountryAbbr2(String countryAbbr2) {
		this.countryAbbr2 = countryAbbr2;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCountryAbbr3() {
		return countryAbbr3;
	}

	public void setCountryAbbr3(String countryAbbr3) {
		this.countryAbbr3 = countryAbbr3;
	}
}
