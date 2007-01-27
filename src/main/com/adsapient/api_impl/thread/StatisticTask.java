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
package com.adsapient.api_impl.thread;

import com.adsapient.api.Banner;
import com.adsapient.api.FilterInterface;
import com.adsapient.api.RunnableTask;

import com.adsapient.api_impl.managment.money.MoneyCalculator;
import com.adsapient.api_impl.publisher.PlacesImpl;
import com.adsapient.api_impl.statistic.StatisticImpl;

import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.admin.ConfigurationConstants;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class StatisticTask implements RunnableTask {
	Banner banner;

	Map requestMap;

	PlacesImpl places;

	StatisticImpl statisticImpl;

	public StatisticTask(Banner banner, PlacesImpl places,
			HttpServletRequest request, StatisticImpl impl) {
		this.banner = banner;
		this.places = places;
		statisticImpl = impl;
	}

	private StatisticTask() {
	}

	public void run() {
		MoneyCalculator.considerImpressions(banner, places, statisticImpl);

		MyHibernateUtil.updateObject(statisticImpl);

		if (!banner.getCampainId().equals(
				ConfigurationConstants.DEFAULT_SYSTEM_CAMPAIN_ID)) {

		}
	}
}
