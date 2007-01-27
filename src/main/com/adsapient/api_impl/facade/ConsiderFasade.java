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
package com.adsapient.api_impl.facade;

import com.adsapient.api.Banner;
import com.adsapient.api.RunnableTask;

import com.adsapient.api_impl.publisher.PlacesImpl;
import com.adsapient.api_impl.statistic.StatisticImpl;
import com.adsapient.api_impl.thread.StatisticTask;

import com.adsapient.util.MyHibernateUtil;

import javax.servlet.http.HttpServletRequest;

public class ConsiderFasade {
	public static void considerImpression(Banner banner, PlacesImpl places,
			StatisticImpl statistic, HttpServletRequest request) {
		banner.setStatisticId(String.valueOf(MyHibernateUtil
				.addObject(statistic)));

		RunnableTask task = new StatisticTask(banner, places, request,
				statistic);

		AdsapientSystemFasade.addTask(task);
	}
}
