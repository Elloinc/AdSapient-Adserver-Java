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
package com.adsapient.api_impl.settings;

import com.adsapient.shared.api.entity.IMappable;

public class DynamicParameter extends ParameterImpl implements IMappable {
	private Integer parameterFilterId;

	public DynamicParameter(ParameterImpl param) {
		setName(param.getName());
		setType(param.getType());
		setValue(param.getValue());
		setRegex(false);
	}

	public DynamicParameter() {
		super();
	}

	public Integer getParameterFilterId() {
		return parameterFilterId;
	}

	public void setParameterFilterId(Integer parameterFilterId) {
		this.parameterFilterId = parameterFilterId;
	}

	public String toString() {
		return "DynamicParameter id:" + getParameterId() + " {type:"
				+ getType() + " name:" + getName() + " value:" + getValue()
				+ (isRegex() ? " isRegex" : "") + "} ";
	}

	public Integer getId() {
		return getParameterId();
	}
}
