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
package com.adsapient.api_impl.filter;

public class ReferrersElement implements Cloneable {
	public Integer id;

	public Integer systemsFilterId;

	public String target_url;

	public boolean type;

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public Integer getSystemsFilterId() {
		return systemsFilterId;
	}

	public void setSystemsFilterId(Integer systemsFilterId) {
		this.systemsFilterId = systemsFilterId;
	}

	public void setTarget_url(String target_url) {
		this.target_url = target_url;
	}

	public String getTarget_url() {
		return target_url;
	}

	public void setType(boolean type) {
		this.type = type;
	}

	public boolean isType() {
		return type;
	}

	public Object clone() throws CloneNotSupportedException {
		ReferrersElement element = new ReferrersElement();

		element.setTarget_url(target_url);
		element.setType(type);

		return element;
	}
}
