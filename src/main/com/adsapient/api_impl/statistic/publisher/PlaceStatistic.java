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
package com.adsapient.api_impl.statistic.publisher;

import com.adsapient.api.StatisticInterface;

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

import javax.servlet.http.HttpServletRequest;

public class PlaceStatistic {
	private static SimpleDateFormat statisticDateFormater = new SimpleDateFormat(
			AdsapientConstants.STATISTIC_DATE_FORMAT);

	public static Collection getPlaceMonthStatistic(Integer placeId,
			Calendar startDate) {
		int curentDate = startDate.get(Calendar.DATE);
		int dateMonthAgo;

		Collection resultCollection = new ArrayList();

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

		endDate.add(Calendar.DATE, 1);

		beginPeriodDate.add(Calendar.MONTH, -1);

		while ((beginPeriodDate.getTime().getTime()) <= (beginDate.getTime()
				.getTime())) {
			StatisticEntity result = getPlaceStatistic(placeId, beginDate,
					endDate);

			beginDate.add(Calendar.DATE, -1);
			endDate.add(Calendar.DATE, -1);

			result.setEntityName(statisticDateFormater
					.format(endDate.getTime()));
			resultCollection.add(result);
		}

		return resultCollection;
	}

	public static StatisticEntity getPlaceStatisticAsText(Integer placeId) {
		return CommonStatisticAgregator.getStatisticAsEntity(
				placeId.toString(), StatisticImpl.PLACE_ID_COLUMN,
				StatisticInterface.PLACES_RATE_COLUMN);
	}

	public static Collection getPublisherPlaceWeekStatistic(Integer placeId,
			Calendar startDate, HttpServletRequest request) {
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

		endDate.add(Calendar.DATE, 1);
		beginPeriodDate.add(Calendar.MONTH, -1);

		while (beginPeriodDate.getTimeInMillis() <= beginDate.getTimeInMillis()) {
			StatisticEntity result = getPlaceStatistic(placeId, beginDate,
					endDate);

			beginDate.add(Calendar.DATE, -1);
			endDate.add(Calendar.DATE, -1);

			result.setEntityName("" + endDate.get(Calendar.DAY_OF_WEEK));
			statisticMap.addStatisticEntity(result);
		}

		return statisticMap.getOrderingStatisticCollection();
	}

	private static StatisticEntity getPlaceStatistic(Integer placeId,
			Calendar beginCalendar, Calendar endCalendar) {
		StatisticEntity result = new StatisticEntity();
		int countImpressions;
		int countClicks;

		StringBuffer stringBuffer = new StringBuffer();
		StringBuffer clicksQuery = new StringBuffer();

		stringBuffer
				.append(
						"select count(statistic.placeId) from com.adsapient.api_impl.")
				.append(
						"statistic.StatisticImpl as statistic  where  statistic.time > ")
				.append(beginCalendar.getTime().getTime()).append(
						" and statistic.time < ").append(
						endCalendar.getTime().getTime()).append(
						" and statistic.placeId='").append(placeId).append("'");

		clicksQuery
				.append(
						"select count(statistic.placeId) from com.adsapient.api_impl.")
				.append(
						"statistic.ClickImpl as statistic  where statistic.time > ")
				.append(beginCalendar.getTime().getTime()).append(
						" and statistic.time < ").append(
						endCalendar.getTime().getTime()).append(
						" and statistic.placeId='").append(placeId).append("'");

		countImpressions = MyHibernateUtil.getCount(stringBuffer.toString());
		countClicks = MyHibernateUtil.getCount(clicksQuery.toString());

		result.setImpressions(countImpressions);
		result.setClicks(countClicks);

		return result;
	}
}
