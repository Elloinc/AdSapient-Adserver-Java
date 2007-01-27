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
import com.adsapient.api.StatisticFormater;

import com.adsapient.api_impl.advertizer.CampainImpl;
import com.adsapient.api_impl.publisher.PlaceImpl;
import com.adsapient.api_impl.publisher.SiteImpl;
import com.adsapient.api_impl.share.Category;
import com.adsapient.api_impl.statistic.StatisticImpl;
import com.adsapient.api_impl.statistic.common.StatisticEntity;
import com.adsapient.api_impl.statistic.common.agregators.CommonStatisticAgregator;
import com.adsapient.api_impl.statistic.engine.ModernSingleton;
import com.adsapient.api_impl.statistic.format.StatisticFormatFactory;

import com.adsapient.util.Msg;
import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.admin.AdsapientConstants;

import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class AdditionalPublisherStatistic {
	private static Logger logger = Logger
			.getLogger(AdditionalPublisherStatistic.class);

	public static Collection getDailyPublishingReport(String parameterId,
			String parameterColumn, Calendar startDate, Calendar endDate,
			HttpServletRequest request) {
		StatisticFormater formater = StatisticFormatFactory.getFormater(
				StatisticFormatFactory.SIMPLE_FORMATER, request);
		SimpleDateFormat sdf = new SimpleDateFormat(
				AdsapientConstants.STATISTIC_DATE_FORMAT);
		Collection resultCollection = new ArrayList();
		Calendar periodDate = Calendar.getInstance();
		Calendar startDateLocal = Calendar.getInstance();

		startDateLocal.setTime(startDate.getTime());
		periodDate.setTime(startDate.getTime());
		endDate.add(Calendar.DATE, 1);

		boolean include = false;

		periodDate.add(Calendar.DATE, 1);

		while (startDateLocal.before(endDate)) {
			Collection stringResult = new ArrayList();

			Collection requestParameters = CommonStatisticAgregator
					.getParametersCollection(startDateLocal, periodDate,
							parameterColumn, parameterId);

			StatisticEntity statisticEntity = CommonStatisticAgregator
					.getStatisticAsEntity(StatisticImpl.PUBLISHER_ID_COLUMN,
							StatisticImpl.PLACES_RATE_COLUMN, null,
							requestParameters);
			statisticEntity.setStatisticFormater(formater);
			statisticEntity.setEntityName(sdf.format(startDateLocal.getTime()));
			resultCollection.add(statisticEntity);

			startDateLocal.add(Calendar.DATE, 1);
			periodDate.add(Calendar.DATE, 1);
		}

		return resultCollection;
	}

	public static Collection getPublisherBySitesRevenueStatistic(
			Integer userId, Calendar startDate, Calendar endDate,
			HttpServletRequest request) {
		StatisticFormater formater = StatisticFormatFactory.getFormater(
				StatisticFormatFactory.ID_WITH_CPM_CPC, request);
		Collection resultCollection = new ArrayList();
		StatisticEntity totalStatistic = new StatisticEntity(formater);
		endDate.add(Calendar.DATE, 1);

		Collection allPublisherSitesCollection;

		if (userId == null) {
			allPublisherSitesCollection = MyHibernateUtil.viewWithCriteria(
					SiteImpl.class, "userId", userId, "siteId");
		} else {
			allPublisherSitesCollection = MyHibernateUtil
					.viewAll(SiteImpl.class);
		}

		Iterator sitesIterator = allPublisherSitesCollection.iterator();

		while (sitesIterator.hasNext()) {
			SiteImpl site = (SiteImpl) sitesIterator.next();

			Collection requestParameters = CommonStatisticAgregator
					.getParametersCollection(startDate, endDate,
							StatisticImpl.SITE_ID_COLUMN, site.getSiteId()
									.toString());

			StatisticEntity statisticEntity = CommonStatisticAgregator
					.getStatisticAsEntity(StatisticImpl.SITE_ID_COLUMN,
							StatisticImpl.PLACES_RATE_COLUMN,
							StatisticImpl.UNIQUE_USER_COLUMN, requestParameters);

			statisticEntity.setStatisticFormater(formater);
			statisticEntity.setLabel(site.getSiteId().toString());
			statisticEntity.setEntityName(site.getUrl());
			statisticEntity.setLabel2(site.getRate().getCPM_CPC());
			statisticEntity.setLabel3(site.getRate().getCPL_CPS());

			totalStatistic.sum(statisticEntity);

			resultCollection.add(statisticEntity);
		}

		totalStatistic.setLabel(Msg.fetch("total", request));

		resultCollection.add(totalStatistic);

		return resultCollection;
	}

	public static Collection getPublishingReportByCampaigns(
			Collection requestParameters, HttpServletRequest requset) {
		StatisticFormater format = StatisticFormatFactory.getFormater(
				StatisticFormatFactory.FORMAT_ID_LABEL, requset);

		return CommonStatisticAgregator
				.getReportByName(
						requestParameters,
						CampainImpl.class,
						StatisticImpl.CAMPAIGN_ID_COLUMN,
						ModernSingleton
								.getInstance(ModernSingleton.PERSISTENT_WITH_ID_WORKER),
						StatisticInterface.PLACES_RATE_COLUMN, format);
	}

	public static Collection getReportByCategory(String columnIdValue,
			String columnIdName, Calendar startDate, Calendar endDate,
			HttpServletRequest request) {
		StatisticFormater formater = StatisticFormatFactory.getFormater(
				StatisticFormatFactory.SIMPLE_FORMATER, request);

		return CommonStatisticAgregator.getReportByName(columnIdValue,
				columnIdName, startDate, endDate, Category.class,
				StatisticImpl.CATEGORY_ID_COLUMN, ModernSingleton
						.getInstance(ModernSingleton.PERSISTENT_WORKER),
				StatisticInterface.PLACES_RATE_COLUMN, formater);
	}

	public static Collection getReportByCountrys(String columnValue,
			String columnName, Calendar startDate, Calendar endDate,
			HttpServletRequest request) {
		StatisticFormater format = StatisticFormatFactory.getFormater(
				StatisticFormatFactory.SIMPLE_FORMATER, request);

		return CommonStatisticAgregator.getReportByName(columnValue,
				columnName, startDate, endDate, null,
				StatisticImpl.COUNTRY_ID_COLUMN, ModernSingleton
						.getInstance(ModernSingleton.COUNTRY_WORKER),
				StatisticInterface.PLACES_RATE_COLUMN, format);
	}

	public static Collection getReportByDayOfTheWeek(String statisticId,
			String statisticColumnId, Calendar startDate, Calendar endDate,
			HttpServletRequest request) {
		StatisticFormater formater = StatisticFormatFactory.getFormater(
				StatisticFormatFactory.SIMPLE_FORMATER, request);
		Map resultMap = new HashMap();
		endDate.add(Calendar.DATE, 1);

		for (int dayOfWeek = 1; dayOfWeek <= 7; dayOfWeek++) {
			resultMap.put(Integer.toString(dayOfWeek), new StatisticEntity(
					formater));
		}

		CommonStatisticAgregator.considerStatisticByTimePeriod(statisticId,
				statisticColumnId, resultMap, startDate, endDate,
				Calendar.DATE, Calendar.DAY_OF_WEEK);

		Collection resultCollection = new ArrayList();

		for (int dayOfWeek = 1; dayOfWeek <= 7; dayOfWeek++) {
			StatisticEntity entity = (StatisticEntity) resultMap.get(Integer
					.toString(dayOfWeek));

			entity.setEntityName(Msg.fetch("day" + dayOfWeek, request));
			resultCollection.add(entity);
		}

		return resultCollection;
	}

	public static Collection getReportByHours(String columnValue,
			String columnName, Calendar startDate, Calendar endDate,
			HttpServletRequest request) {
		StatisticFormater formater = StatisticFormatFactory.getFormater(
				StatisticFormatFactory.SIMPLE_FORMATER, request);
		Map resultMap = new HashMap();

		endDate.add(Calendar.DATE, 1);

		for (int hourOfDay = 0; hourOfDay < 24; hourOfDay++) {
			resultMap.put(Integer.toString(hourOfDay), new StatisticEntity(
					formater));
		}

		CommonStatisticAgregator.considerStatisticByTimePeriod(columnValue,
				columnName, resultMap, startDate, endDate, Calendar.HOUR,
				Calendar.HOUR_OF_DAY);

		Collection resultCollection = new ArrayList();

		for (int hourOfDay = 0; hourOfDay < 24; hourOfDay++) {
			StatisticEntity entity = (StatisticEntity) resultMap.get(Integer
					.toString(hourOfDay));
			entity.setEntityName(Integer.toString(hourOfDay));
			resultCollection.add(entity);
		}

		return resultCollection;
	}

	public static Collection getReportByPositions(String columnIdValue,
			String columnIdName, Calendar startDate, Calendar endDate,
			HttpServletRequest request) {
		StatisticFormater formater = StatisticFormatFactory.getFormater(
				StatisticFormatFactory.SIMPLE_FORMATER, request);

		return CommonStatisticAgregator.getReportByName(columnIdValue,
				columnIdName, startDate, endDate, PlaceImpl.class,
				StatisticImpl.POSITION_ID_COLUMN, ModernSingleton
						.getInstance(ModernSingleton.PERSISTENT_WORKER),
				StatisticInterface.PLACES_RATE_COLUMN, formater);
	}

	public static Collection getReportByUniques(Integer userId,
			Calendar startDate, Calendar endDate, HttpServletRequest request) {
		StatisticFormater formater = StatisticFormatFactory.getFormater(
				StatisticFormatFactory.ID_WITH_UNIQUES, request);
		Collection resultCollection = new ArrayList();
		StatisticEntity totalStatistic = new StatisticEntity(formater);
		totalStatistic.setLabel("TOTAL");
		totalStatistic.setEntityName(AdsapientConstants.EMPTY);

		endDate.add(Calendar.DATE, 1);

		Collection allPublisherSitesCollection;

		if (userId != null) {
			allPublisherSitesCollection = MyHibernateUtil.viewWithCriteria(
					SiteImpl.class, "userId", userId, "siteId");
		} else {
			allPublisherSitesCollection = MyHibernateUtil
					.viewAll(SiteImpl.class);
		}

		Iterator sitesIterator = allPublisherSitesCollection.iterator();

		while (sitesIterator.hasNext()) {
			SiteImpl site = (SiteImpl) sitesIterator.next();

			Collection requestParameters = CommonStatisticAgregator
					.getParametersCollection(startDate, endDate,
							StatisticImpl.SITE_ID_COLUMN, site.getSiteId()
									.toString());

			StatisticEntity statisticEntity = CommonStatisticAgregator
					.getStatisticAsEntity(StatisticImpl.SITE_ID_COLUMN,
							StatisticImpl.PLACES_RATE_COLUMN,
							StatisticImpl.UNIQUE_USER_COLUMN, requestParameters);
			statisticEntity.setEntityName(site.getUrl());
			statisticEntity.setLabel(site.getSiteId().toString());
			statisticEntity.setStatisticFormater(formater);

			totalStatistic.sum(statisticEntity);

			resultCollection.add(statisticEntity);
		}

		totalStatistic.setLabel("TOTAL");
		totalStatistic.setEntityName(AdsapientConstants.EMPTY);
		resultCollection.add(totalStatistic);

		return resultCollection;
	}
}
