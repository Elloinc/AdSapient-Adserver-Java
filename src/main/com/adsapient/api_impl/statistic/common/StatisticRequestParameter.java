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
package com.adsapient.api_impl.statistic.common;

public class StatisticRequestParameter {
	public static final String LESS = "<";

	public static final String MORE = ">";

	public static final String EQUAL = " like ";

	private String parameterAction = "";

	private String parameterName = "";

	private String parameterValue = "";

	public StatisticRequestParameter(String name, String action, String value) {
		super();

		parameterName = name;
		parameterAction = action;
		parameterValue = value;

		if (EQUAL.equalsIgnoreCase(action)) {
			try {
				new Integer(value);
			} catch (NumberFormatException ex) {
				StringBuffer buffer = new StringBuffer();
				buffer.append("'").append(value).append("'");

				parameterValue = buffer.toString();
			}
		}
	}

	private StatisticRequestParameter() {
		super();
	}

	public void setParameterAction(String parameterAction) {
		this.parameterAction = parameterAction;
	}

	public String getParameterAction() {
		return parameterAction;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}

	public String getParameterValue() {
		return parameterValue;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(parameterName).append(parameterAction).append(
				parameterValue);

		return buffer.toString();
	}
}
