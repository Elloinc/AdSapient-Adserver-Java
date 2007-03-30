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

public class UniqueVisitorObject {
	private int uniqueid;

	private int langid;

	private int browserid;

	private int osid;

	private int geolocationid;

	public int getGeolocationid() {
		return geolocationid;
	}

	public void setGeolocationid(int geolocationid) {
		this.geolocationid = geolocationid;
	}

	public int getUniqueid() {
		return uniqueid;
	}

	public void setUniqueid(int uniqueid) {
		this.uniqueid = uniqueid;
	}

	public int getLangid() {
		return langid;
	}

	public void setLangid(int langid) {
		this.langid = langid;
	}

	public int getBrowserid() {
		return browserid;
	}

	public void setBrowserid(int browserid) {
		this.browserid = browserid;
	}

	public int getOsid() {
		return osid;
	}

	public void setOsid(int osid) {
		this.osid = osid;
	}
}
