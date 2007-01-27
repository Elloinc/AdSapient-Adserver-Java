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
import com.adsapient.api.StatisticFormater;

import com.adsapient.api_impl.advertizer.BannerImpl;
import com.adsapient.api_impl.advertizer.CampainImpl;
import com.adsapient.api_impl.publisher.PlacesImpl;
import com.adsapient.api_impl.publisher.SiteImpl;
import com.adsapient.api_impl.share.Category;
import com.adsapient.api_impl.statistic.StatisticImpl;
import com.adsapient.api_impl.statistic.common.StatisticEntity;
import com.adsapient.api_impl.statistic.common.StatisticRequestParameter;
import com.adsapient.api_impl.statistic.common.agregators.CommonStatisticAgregator;
import com.adsapient.api_impl.statistic.engine.ModernSingleton;
import com.adsapient.api_impl.statistic.engine.StatisticBuilder;
import com.adsapient.api_impl.statistic.format.StatisticFormatFactory;

import com.adsapient.util.Msg;
import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.admin.AdsapientConstants;
import com.adsapient.util.comparator.StatisticComparator;

import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AdditionalAdvertiserStatistic {
	public static final Logger logger = Logger
			.getLogger(AdditionalAdvertiserStatistic.class);

	public static final String N_A = "n/a";

	public static final String NBS = "&nbsp;";

	public static Collection getAdvertisingReportByCampaigns(Integer userId,
			Calendar startDate, Calendar endDate, HttpServletRequest request) {
		endDate.add(Calendar.DATE, 1);

		Collection resultCollection = new ArrayList();
		StatisticFormater formate = (StatisticFormater) StatisticFormatFactory
				.getFormater(StatisticFormatFactory.FORMAT_ID_LABEL, request);

		HttpSession session = request.getSession();
		Collection campaignsCollection;

		if (userId != null) {
			campaignsCollection = MyHibernateUtil.viewWithCriteria(
					CampainImpl.class, "userId", userId, "userId");
		} else {
			campaignsCollection = MyHibernateUtil.viewAll(CampainImpl.class);
		}

		Iterator campaignsIterator = campaignsCollection.iterator();

		while (campaignsIterator.hasNext()) {
			StringBuffer buffer = new StringBuffer();
			StringBuffer leadsSalesBuffer = new StringBuffer();
			CampainImpl campain = (CampainImpl) campaignsIterator.next();

			Collection commonRequestParameters = CommonStatisticAgregator
					.getParametersCollection(startDate, endDate,
							StatisticInterface.CAMPAIGN_ID_COLUMN, campain
									.getCampainId().toString());

			StatisticEntity entity = CommonStatisticAgregator
					.getStatisticAsEntity(
							StatisticInterface.CAMPAIGN_ID_COLUMN,
							StatisticInterface.BANNER_ID_COLUMN, null,
							commonRequestParameters);
			entity.setStatisticFormater(StatisticFormatFactory.getFormater(
					StatisticFormatFactory.ADVERTISER_CAMPAIGNS, request,
					campain));

			resultCollection.add(entity);
		}

		return resultCollection;
	}

	public static Collection getAdvertisingReportByDates(String columnValue,
			String columnName, Calendar startDate, Calendar endDate,
			HttpServletRequest request) {
		Collection resultCollection = new ArrayList();
		Calendar periodDate = (Calendar) startDate.clone();
		Calendar startDateLocal = (Calendar) startDate.clone();
		Calendar endDateLocal = (Calendar) endDate.clone();
		StatisticFormater format = StatisticFormatFactory.getFormater(
				StatisticFormatFactory.ADVERTISER_DATE_WITH_UNIQUES, request);
		Map resultMap = new HashMap();

		boolean include = false;
		SimpleDateFormat sdf = new SimpleDateFormat(
				AdsapientConstants.STATISTIC_DATE_FORMAT);
		NumberFormat formater = new DecimalFormat("0.00");

		periodDate.add(Calendar.DATE, 1);
		endDateLocal.add(Calendar.DATE, 1);

		while (startDateLocal.before(endDateLocal)) {
			Map previeMap = new HashMap();

			Collection parametersCollection = CommonStatisticAgregator
					.getParametersCollection(startDateLocal, periodDate,
							columnName, columnValue);

			StatisticEntity entity = CommonStatisticAgregator
					.getStatisticAsEntity(
							StatisticInterface.ADVERTISER_ID_COLUMN,
							StatisticInterface.BANNER_RATE_COLUMN,
							StatisticInterface.UNIQUE_USER_COLUMN,
							parametersCollection);

			entity.setStatisticFormater(format);
			entity.setEntityName(sdf.format(startDateLocal.getTime()));
			resultCollection.add(entity);

			startDateLocal.add(Calendar.DATE, 1);
			periodDate.add(Calendar.DATE, 1);
		}

		return resultCollection;
	}

	public static Collection getAdvertisingReportBySites(String valueId,
			String columnName, Calendar startDate, Calendar endDate,
			HttpServletRequest request) {
		Calendar endDateLocal = Calendar.getInstance();
		endDateLocal.setTime(endDate.getTime());

		Collection resultCollection = new ArrayList();
		StatisticFormater formater = StatisticFormatFactory.getFormater(
				StatisticFormatFactory.SIMPLE_FORMATER, request);
		StatisticRequestParameter advertiserParameter = new StatisticRequestParameter(
				columnName, StatisticRequestParameter.EQUAL, valueId);
		endDateLocal.add(Calendar.DATE, 1);

		Collection parametersCollection = CommonStatisticAgregator
				.getParametersCollection(startDate, endDateLocal, columnName,
						valueId);

		Collection displayedSitesCollection = StatisticBuilder
				.getStatisticCollection(StatisticBuilder.IMPRESSION_CLASS,
						StatisticBuilder.DISTINCT_COLLECTION,
						StatisticInterface.SITE_ID_COLUMN, parametersCollection);

		Collection displayedPlacesCollection = StatisticBuilder
				.getStatisticCollection(StatisticBuilder.IMPRESSION_CLASS,
						StatisticBuilder.DISTINCT_COLLECTION,
						StatisticInterface.PLACE_ID_COLUMN,
						parametersCollection);

		Map sitesWithPlacesMap = StatisticComparator
				.transformBannersCollectionBySites(displayedSitesCollection,
						displayedPlacesCollection);
		Iterator statisticIterator = sitesWithPlacesMap.entrySet().iterator();

		while (statisticIterator.hasNext()) {
			Map.Entry entry = (Map.Entry) statisticIterator.next();

			SiteImpl site = (SiteImpl) MyHibernateUtil.loadObject(
					SiteImpl.class, (Integer) entry.getKey());

			Collection placesCollection = (Collection) entry.getValue();
			Collection requestParameters = CommonStatisticAgregator
					.getParametersCollection(startDate, endDateLocal,
							StatisticInterface.SITE_ID_COLUMN, site.getSiteId()
									.toString());

			requestParameters.add(advertiserParameter);

			StatisticEntity entity = CommonStatisticAgregator
					.getStatisticAsEntity(StatisticInterface.SITE_ID_COLUMN,
							StatisticInterface.BANNER_RATE_COLUMN, null,
							requestParameters);

			entity.setStatisticFormater(formater);
			entity.setEntityName(site.getUrl());

			resultCollection.add(entity);

			Iterator placesIterator = placesCollection.iterator();

			while (placesIterator.hasNext()) {
				PlacesImpl places = (PlacesImpl) placesIterator.next();

				StringBuffer buffer = new StringBuffer();
				buffer.append(NBS).append(site.getUrl()).append(":").append(
						places.getPlace().getName());

				Collection placesParametersCollection = CommonStatisticAgregator
						.getParametersCollection(startDate, endDate,
								StatisticInterface.PLACE_ID_COLUMN, places
										.getId().toString());

				placesParametersCollection.add(advertiserParameter);

				StatisticEntity placesEntity = CommonStatisticAgregator
						.getStatisticAsEntity(
								StatisticInterface.PLACE_ID_COLUMN,
								StatisticImpl.BANNER_RATE_COLUMN, null,
								placesParametersCollection);

				placesEntity.setStatisticFormater(formater);
				placesEntity.setEntityName(buffer.toString());
				resultCollection.add(placesEntity);
			}
		}

		return resultCollection;
	}

	public static Collection getAdvertisingReportByBanner(String columnValue,
			String columnName, Calendar startDate, Calendar endDate,
			HttpServletRequest request) {
		Calendar endDateLocal = Calendar.getInstance();
		endDateLocal.setTime(endDate.getTime());

		endDateLocal.add(Calendar.DATE, 1);

		StatisticFormater format = StatisticFormatFactory.getFormater(
				StatisticFormatFactory.FORMAT_ID_LABEL, request);

		Collection requestParametersCollection = CommonStatisticAgregator
				.getParametersCollection(startDate, endDateLocal, columnName,
						columnValue);

		return CommonStatisticAgregator
				.getReportByName(
						requestParametersCollection,
						BannerImpl.class,
						StatisticInterface.BANNER_ID_COLUMN,
						ModernSingleton
								.getInstance(ModernSingleton.PERSISTENT_WITH_ID_WORKER),
						StatisticInterface.BANNER_RATE_COLUMN, format);
	}

	public static Collection getAdvertisingReportByCategorys(
			String columnValue, String columnName, Calendar startDate,
			Calendar endDate, HttpServletRequest request) {
		StatisticFormater format = StatisticFormatFactory.getFormater(
				StatisticFormatFactory.SIMPLE_FORMATER, request);
		Calendar endDateLocal = Calendar.getInstance();
		endDateLocal.setTime(endDate.getTime());
		endDateLocal.add(Calendar.DATE, 1);

		Collection requestParametersCollection = CommonStatisticAgregator
				.getParametersCollection(startDate, endDateLocal, columnName,
						columnValue);

		return CommonStatisticAgregator.getReportByName(
				requestParametersCollection, Category.class,
				StatisticInterface.CATEGORY_ID_COLUMN, ModernSingleton
						.getInstance(ModernSingleton.PERSISTENT_WORKER),
				StatisticInterface.BANNER_RATE_COLUMN, format);
	}

	public static Collection getAdvertisingReportByDayOfTheWeek(
			String columnValue, String columnName, Calendar startDate,
			Calendar endDate, HttpServletRequest request) {
		StatisticFormater formater = StatisticFormatFactory.getFormater(
				StatisticFormatFactory.SIMPLE_FORMATER, request);
		Map resultMap = new HashMap();
		Calendar endDateLocal = (Calendar) endDate.clone();
		endDateLocal.add(Calendar.DATE, 1);

		for (int dayOfWeek = 1; dayOfWeek <= 7; dayOfWeek++) {
			resultMap.put(Integer.toString(dayOfWeek), new StatisticEntity(
					formater));
		}

		CommonStatisticAgregator.considerStatisticByTimePeriod(columnValue,
				columnName, resultMap, startDate, endDateLocal, Calendar.DATE,
				Calendar.DAY_OF_WEEK);

		Collection resultCollection = new ArrayList();

		for (int dayOfWeek = 1; dayOfWeek <= 7; dayOfWeek++) {
			StatisticEntity entity = (StatisticEntity) resultMap.get(Integer
					.toString(dayOfWeek));

			entity.setEntityName(Msg.fetch("day" + dayOfWeek, request));

			resultCollection.add(entity);
		}

		return resultCollection;
	}

	public static Collection getAdvertisingReportByHour(String columnValue,
			String columnName, Calendar startDate, Calendar endDate,
			HttpServletRequest request) {
		StatisticFormater formater = StatisticFormatFactory.getFormater(
				StatisticFormatFactory.SIMPLE_FORMATER, request);
		Map resultMap = new HashMap();
		Calendar endDateLocal = (Calendar) endDate.clone();
		endDateLocal.add(Calendar.DATE, 1);

		for (int hourOfDay = 0; hourOfDay < 24; hourOfDay++) {
			resultMap.put(Integer.toString(hourOfDay), new StatisticEntity(
					formater));
		}

		CommonStatisticAgregator.considerStatisticByTimePeriod(columnValue,
				columnName, resultMap, startDate, endDateLocal, Calendar.HOUR,
				Calendar.HOUR_OF_DAY);

		Collection resultCollection = new ArrayList();

		for (int hourOfDay = 0; hourOfDay <= 23; hourOfDay++) {
			StatisticEntity entity = (StatisticEntity) resultMap.get(Integer
					.toString(hourOfDay));
			entity.setEntityName(Integer.toString(hourOfDay));
			resultCollection.add(entity);
		}

		return resultCollection;
	}

	public static Collection getAdvertisingReportByCountry(String columnValue,
			String columnName, Calendar startDate, Calendar endDate,
			HttpServletRequest request) {
		StatisticFormater formater = StatisticFormatFactory.getFormater(
				StatisticFormatFactory.SIMPLE_FORMATER, request);

		Calendar endDateLocal = (Calendar) endDate.clone();
		endDateLocal.add(Calendar.DATE, 1);

		Collection requestParametersCollection = CommonStatisticAgregator
				.getParametersCollection(startDate, endDateLocal, columnName,
						columnValue);

		return CommonStatisticAgregator.getReportByName(
				requestParametersCollection, null,
				StatisticInterface.COUNTRY_ID_COLUMN, ModernSingleton
						.getInstance(ModernSingleton.COUNTRY_WORKER),
				StatisticInterface.BANNER_RATE_COLUMN, formater);
	}

	public static StatisticEntity getAdminMonthsStatistic() {
		Calendar startDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();
		startDate.add(Calendar.MONTH, -1);

		Collection requestParameters = CommonStatisticAgregator
				.getParametersCollection(startDate, endDate,
						StatisticInterface.PLACE_ID_COLUMN, Integer.toString(1));

		return CommonStatisticAgregator.getStatisticAsEntity(
				StatisticInterface.PLACE_ID_COLUMN,
				StatisticInterface.PLACES_RATE_COLUMN, null, requestParameters);
	}
}
