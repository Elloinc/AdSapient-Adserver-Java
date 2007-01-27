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

import com.adsapient.api_impl.advertizer.CampainImpl;
import com.adsapient.api_impl.statistic.StatisticImpl;
import com.adsapient.api_impl.statistic.common.StatisticEntity;
import com.adsapient.api_impl.statistic.common.StatisticMap;
import com.adsapient.api_impl.statistic.common.agregators.CommonStatisticAgregator;

import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.admin.AdsapientConstants;

import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

public class AdvertizerStatistic {
	static Logger logger = Logger.getLogger(AdvertizerStatistic.class);

	private static SimpleDateFormat statisticDateFormater = new SimpleDateFormat(
			AdsapientConstants.STATISTIC_DATE_FORMAT);

	public static Collection getAdvertiserWeekStatistic(Integer userId,
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
			StatisticEntity result = getAdvertiserStatistic(userId, beginDate,
					endDate);

			beginDate.add(Calendar.DATE, -1);
			endDate.add(Calendar.DATE, -1);

			result.setEntityName("" + endDate.get(Calendar.DAY_OF_WEEK));
			statisticMap.addStatisticEntity(result);
		}

		return statisticMap.getOrderingStatisticCollection();
	}

	public static int getAdvertizerCampainsCount(Integer userId) {
		Collection advertizerCampainsCollection = MyHibernateUtil
				.viewWithCriteria(CampainImpl.class, "userId", userId,
						"campainId");

		if (advertizerCampainsCollection == null) {
			return 0;
		}

		return advertizerCampainsCollection.size();
	}

	public static StatisticEntity getAdvertizerStatistic(Integer userId) {
		StatisticEntity result = new StatisticEntity();
		int countImpressions;
		int countClicks;

		StringBuffer stringBuffer = new StringBuffer();
		StringBuffer clicksQuery = new StringBuffer();

		stringBuffer
				.append(
						"select count(statistic.advertizerId) from com.adsapient.api_impl.")
				.append(
						"statistic.StatisticImpl as statistic  where statistic.advertizerId='")
				.append(userId).append("'");

		clicksQuery
				.append(
						"select count(statistic.advertizerId) from com.adsapient.api_impl.")
				.append(
						"statistic.ClickImpl as statistic where statistic.advertizerId='")
				.append(userId).append("'");

		countImpressions = MyHibernateUtil.getCount(stringBuffer.toString());
		countClicks = MyHibernateUtil.getCount(clicksQuery.toString());

		result.setImpressions(countImpressions);
		result.setClicks(countClicks);

		return result;
	}

	public static Collection getCampainMonthStatistic(Integer campainId,
			Calendar startDate) {
		int curentDate = startDate.get(Calendar.DATE);
		int dateMonthAgo;

		Date campainBegiDate = null;

		CampainImpl campain = (CampainImpl) MyHibernateUtil.loadObject(
				CampainImpl.class, campainId);

		SimpleDateFormat sdf = new SimpleDateFormat(
				AdsapientConstants.DATE_FORMAT);

		try {
			campainBegiDate = sdf.parse(campain.getStartDate());
		} catch (java.text.ParseException e) {
			logger.error("in getCampainMonthStatistic", e);
		}

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

		if (campainBegiDate != null) {
			if (beginPeriodDate.getTime().compareTo(campainBegiDate) < 0) {
				beginPeriodDate.setTime(campainBegiDate);
				beginPeriodDate.set(Calendar.HOUR_OF_DAY, 0);
				beginPeriodDate.set(Calendar.MINUTE, 0);
			}
		}

		while (beginPeriodDate.getTimeInMillis() <= beginDate.getTimeInMillis()) {
			StatisticEntity result = getCampainStatistic(campainId, beginDate,
					endDate);

			beginDate.add(Calendar.DATE, -1);
			endDate.add(Calendar.DATE, -1);

			result.setEntityName(statisticDateFormater
					.format(endDate.getTime()));
			resultCollection.add(result);
		}

		return resultCollection;
	}

	public static StatisticEntity getCampainStatisticAsText(Integer campainId) {
		return CommonStatisticAgregator.getStatisticAsEntity(campainId
				.toString(), StatisticImpl.CAMPAIGN_ID_COLUMN,
				StatisticInterface.BANNER_RATE_COLUMN);
	}

	public static Collection getCampainWeekStatistic(Integer campainId,
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
			StatisticEntity result = getCampainStatistic(campainId, beginDate,
					endDate);

			beginDate.add(Calendar.DATE, -1);
			endDate.add(Calendar.DATE, -1);

			result.setEntityName("" + endDate.get(Calendar.DAY_OF_WEEK));
			statisticMap.addStatisticEntity(result);
		}

		return statisticMap.getOrderingStatisticCollection();
	}

	public static Collection getCampaignsMonthStatistic(Integer userId,
			Calendar startDate) {
		StatisticMap statisticMap = new StatisticMap();

		Collection userCampainsCollection = MyHibernateUtil.viewWithCriteria(
				CampainImpl.class, "userId", userId, "campainId");

		if (!userCampainsCollection.isEmpty()) {
			Iterator userCampainsIterator = userCampainsCollection.iterator();

			while (userCampainsIterator.hasNext()) {
				CampainImpl userCampain = (CampainImpl) userCampainsIterator
						.next();

				statisticMap
						.addStatisticEntitysColection(getCampainMonthStatistic(
								userCampain.getCampainId(), startDate));
			}

			return statisticMap.getStatisticMap().values();
		}

		return new ArrayList();
	}

	private static StatisticEntity getAbstractStatistic(String abstraxtType,
			Integer abstructId, Calendar beginCalendar, Calendar endCalendar) {
		StatisticEntity result = new StatisticEntity();
		int countImpressions;
		int countClicks;

		StringBuffer stringBuffer = new StringBuffer();
		StringBuffer clicksQuery = new StringBuffer();

		stringBuffer
				.append(
						"select count(statistic.campainId) from com.adsapient.api_impl.")
				.append(
						"statistic.StatisticImpl as statistic  where "
								+ " statistic.time > ").append(
						beginCalendar.getTime().getTime()).append(
						" and statistic.time < ").append(
						endCalendar.getTime().getTime()).append(" and ");

		if ("campain".equalsIgnoreCase(abstraxtType)) {
			stringBuffer.append("statistic.campainId='").append(abstructId)
					.append("'");
		}

		if ("advertiser".equalsIgnoreCase(abstraxtType)) {
			stringBuffer.append("statistic.advertizerId='").append(abstructId)
					.append("'");
		}

		clicksQuery
				.append(
						"select count(statistic.campainId) from com.adsapient.api_impl.")
				.append(
						"statistic.ClickImpl as statistic  where statistic.time > ")
				.append(beginCalendar.getTime().getTime()).append(
						" and statistic.time < ").append(
						endCalendar.getTime().getTime()).append(" and ");

		if ("campain".equalsIgnoreCase(abstraxtType)) {
			clicksQuery.append("statistic.campainId='").append(abstructId)
					.append("'");
		}

		if ("advertiser".equalsIgnoreCase(abstraxtType)) {
			clicksQuery.append("statistic.advertizerId='").append(abstructId)
					.append("'");
		}

		countImpressions = MyHibernateUtil.getCount(stringBuffer.toString());
		countClicks = MyHibernateUtil.getCount(clicksQuery.toString());

		result.setImpressions(countImpressions);
		result.setClicks(countClicks);

		return result;
	}

	private static StatisticEntity getAdvertiserStatistic(Integer advertiserId,
			Calendar beginCalendar, Calendar endCalendar) {
		StatisticEntity result = new StatisticEntity();
		result = getAbstractStatistic("advertiser", advertiserId,
				beginCalendar, endCalendar);

		return result;
	}

	private static StatisticEntity getCampainStatistic(Integer campainId,
			Calendar beginCalendar, Calendar endCalendar) {
		StatisticEntity result = new StatisticEntity();
		result = getAbstractStatistic("campain", campainId, beginCalendar,
				endCalendar);

		return result;
	}
}
