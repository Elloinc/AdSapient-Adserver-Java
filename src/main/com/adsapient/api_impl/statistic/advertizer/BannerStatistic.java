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
package com.adsapient.api_impl.statistic.advertizer;

import com.adsapient.api.StatisticInterface;

import com.adsapient.api_impl.advertizer.BannerImpl;
import com.adsapient.api_impl.statistic.StatisticImpl;
import com.adsapient.api_impl.statistic.common.StatisticEntity;
import com.adsapient.api_impl.statistic.common.StatisticMap;
import com.adsapient.api_impl.statistic.common.agregators.CommonStatisticAgregator;

import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.admin.AdsapientConstants;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

public class BannerStatistic {
	private static SimpleDateFormat statisticDateFormater = new SimpleDateFormat(
			AdsapientConstants.STATISTIC_DATE_FORMAT);

	public static Collection getBannerMonthStatistic(Integer bannerId,
			Calendar startDate) {
		int curentDate = startDate.get(Calendar.DATE);
		int dateMonthAgo;

		Collection resultCollection = new ArrayList();

		Collection dataCollection = new ArrayList();

		Calendar beginDate = Calendar.getInstance();
		Calendar beginPeriodDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();

		beginDate.setTime(startDate.getTime());
		beginPeriodDate.setTime(startDate.getTime());
		endDate.setTime(startDate.getTime());

		beginDate.set(Calendar.HOUR_OF_DAY, 0);
		endDate.set(Calendar.HOUR_OF_DAY, 0);

		beginDate.set(Calendar.MINUTE, 0);
		endDate.set(Calendar.MINUTE, 0);

		beginPeriodDate.add(Calendar.MONTH, -1);
		endDate.add(Calendar.DAY_OF_MONTH, 1);

		while (beginPeriodDate.getTimeInMillis() <= beginDate.getTimeInMillis()) {
			StatisticEntity result = getBannerStatistic(bannerId, beginDate,
					endDate);
			beginDate.add(Calendar.DATE, -1);
			endDate.add(Calendar.DATE, -1);

			result.setEntityName(statisticDateFormater
					.format(endDate.getTime()));
			resultCollection.add(result);
		}

		return resultCollection;
	}

	public static StatisticEntity getBannerStatisticAsText(Integer bannerId) {
		return CommonStatisticAgregator.getStatisticAsEntity(bannerId
				.toString(), StatisticImpl.BANNER_ID_COLUMN,
				StatisticInterface.BANNER_RATE_COLUMN);
	}

	public static Collection getBannerWeekStatistic(Integer bannerId,
			Calendar startDate) {
		int curentDate = startDate.get(Calendar.DATE);
		int dateMonthAgo;

		StatisticMap statisticMap = new StatisticMap();

		Collection resultCollection = new ArrayList();

		Collection dataCollection = new ArrayList();

		Calendar beginDate = Calendar.getInstance();
		Calendar beginPeriodDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();

		beginDate.setTime(startDate.getTime());
		beginPeriodDate.setTime(startDate.getTime());
		endDate.setTime(startDate.getTime());

		beginDate.set(Calendar.HOUR_OF_DAY, 0);
		endDate.set(Calendar.HOUR_OF_DAY, 0);

		beginDate.set(Calendar.MINUTE, 0);
		endDate.set(Calendar.MINUTE, 0);

		beginPeriodDate.add(Calendar.MONTH, -1);
		endDate.add(Calendar.DAY_OF_MONTH, 1);

		while (beginPeriodDate.getTimeInMillis() <= beginDate.getTimeInMillis()) {
			StatisticEntity result = getBannerStatistic(bannerId, beginDate,
					endDate);

			beginDate.add(Calendar.DATE, -1);
			endDate.add(Calendar.DATE, -1);
			result.setEntityName("" + endDate.get(Calendar.DAY_OF_WEEK));
			statisticMap.addStatisticEntity(result);
		}

		return statisticMap.getOrderingStatisticCollection();
	}

	public static int getUserBannersCount(Integer userId) {
		Collection userBannersCollection = MyHibernateUtil.viewWithCriteria(
				BannerImpl.class, "userId", userId, "bannerId");

		if (userBannersCollection == null) {
			return 0;
		}

		return userBannersCollection.size();
	}

	private static StatisticEntity getBannerStatistic(Integer bannerId,
			Calendar beginCalendar, Calendar endCalendar) {
		StatisticEntity result = new StatisticEntity();
		int countImpressions;
		int countClicks;

		StringBuffer stringBuffer = new StringBuffer();
		StringBuffer clicksQuery = new StringBuffer();

		stringBuffer
				.append(
						"select count(statistic.bannerId) from com.adsapient.api_impl.")
				.append(
						"statistic.StatisticImpl as statistic  where "
								+ " statistic.time > ").append(
						beginCalendar.getTime().getTime()).append(
						" and statistic.time < ").append(
						endCalendar.getTime().getTime()).append(
						" and statistic.bannerId='").append(bannerId).append(
						"'");

		clicksQuery
				.append(
						"select count(statistic.bannerId) from com.adsapient.api_impl.")
				.append(
						"statistic.ClickImpl as statistic  where statistic.time > ")
				.append(beginCalendar.getTime().getTime()).append(
						" and statistic.time < ").append(
						endCalendar.getTime().getTime()).append(
						" and statistic.bannerId='").append(bannerId).append(
						"'");

		countImpressions = MyHibernateUtil.getCount(stringBuffer.toString());
		countClicks = MyHibernateUtil.getCount(clicksQuery.toString());

		result.setImpressions(countImpressions);
		result.setClicks(countClicks);

		return result;
	}
}
