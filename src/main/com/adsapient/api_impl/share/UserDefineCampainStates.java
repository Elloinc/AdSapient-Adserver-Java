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
package com.adsapient.api_impl.share;

public class UserDefineCampainStates {
	public static final String LIVE = "1";

	public static final String BOOKED = "2";

	public static final String COMPLETED = "3";

	public static final String PAUSED = "4";

	public static final String CANCELLED = "5";

	private String userDefineCampainStateId;

	private String userDefineCampainState;

	public String getUserDefineCampainState() {
		return userDefineCampainState;
	}

	public void setUserDefineCampainState(String userDefineCampainState) {
		this.userDefineCampainState = userDefineCampainState;
	}

	public String getUserDefineCampainStateId() {
		return userDefineCampainStateId;
	}

	public void setUserDefineCampainStateId(String userDefineCampainStateId) {
		this.userDefineCampainStateId = userDefineCampainStateId;
	}
}
