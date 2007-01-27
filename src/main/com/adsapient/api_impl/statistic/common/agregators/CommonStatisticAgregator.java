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
package com.adsapient.api_impl.statistic.common.agregators;

import com.adsapient.api.StatisticInterface;
import com.adsapient.api.NameWorker;
import com.adsapient.api.StatisticFormater;

import com.adsapient.api_impl.statistic.StatisticImpl;
import com.adsapient.api_impl.statistic.common.StatisticEntity;
import com.adsapient.api_impl.statistic.common.StatisticRequestParameter;
import com.adsapient.api_impl.statistic.engine.StatisticBuilder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class CommonStatisticAgregator {
	public static Collection getParametersCollection(Calendar startPeriod,
			Calendar endPeriod, String columnName, String columnValue) {
		Collection requestParameters = new ArrayList();

		StatisticRequestParameter parameter1 = new StatisticRequestParameter(
				StatisticImpl.TIME_COLUMN, StatisticRequestParameter.MORE, Long
						.toString(startPeriod.getTime().getTime()));
		StatisticRequestParameter parameter2 = new StatisticRequestParameter(
				StatisticImpl.TIME_COLUMN, StatisticRequestParameter.LESS, Long
						.toString(endPeriod.getTime().getTime()));
		StatisticRequestParameter parameter3 = new StatisticRequestParameter(
				columnName, StatisticRequestParameter.EQUAL, columnValue);

		requestParameters.add(parameter1);
		requestParameters.add(parameter2);
		requestParameters.add(parameter3);

		return requestParameters;
	}

	public static Collection getReportByName(String columnIdValue,
			String columnName, Calendar startDate, Calendar endDate,
			Class nameSupportClass, String collectionIdColumnName,
			NameWorker nameWorker, String rateColumnName,
			StatisticFormater format) {
		endDate.add(Calendar.DATE, 1);

		Collection requestParameters = CommonStatisticAgregator
				.getParametersCollection(startDate, endDate, columnName,
						columnIdValue);

		return getReportByName(requestParameters, nameSupportClass,
				collectionIdColumnName, nameWorker, rateColumnName, format);
	}

	public static Collection getReportByName(Collection parametersCollection,
			Class nameSupportClass, String collectionIdColumnName,
			NameWorker nameWorker, String rateColumnName,
			StatisticFormater format) {
		Collection resultCollection = new ArrayList();

		Collection nameSupportIdCollection = new ArrayList();

		nameSupportIdCollection = StatisticBuilder.getStatisticCollection(
				StatisticBuilder.IMPRESSION_CLASS,
				StatisticBuilder.DISTINCT_COLLECTION, collectionIdColumnName,
				parametersCollection);

		Iterator nameSupportIditerator = nameSupportIdCollection.iterator();

		while (nameSupportIditerator.hasNext()) {
			Collection concreateRequestParameters = new ArrayList();

			String nameSupportId = (String) nameSupportIditerator.next();

			concreateRequestParameters.addAll(parametersCollection);
			concreateRequestParameters.add(new StatisticRequestParameter(
					collectionIdColumnName, StatisticRequestParameter.EQUAL,
					nameSupportId));

			StatisticEntity entity = CommonStatisticAgregator
					.getStatisticAsEntity(StatisticImpl.TIME_COLUMN,
							rateColumnName, null, concreateRequestParameters);
			entity.setStatisticFormater(format);
			nameWorker.consider(nameSupportId, nameSupportClass, entity);

			resultCollection.add(entity);
		}

		return resultCollection;
	}

	public static StatisticEntity getStatisticAsEntity(
			String statisticEntityId, String columnType, String rateColumnName) {
		int impressionsCount;
		int clicksCount;
		int leadsCount;
		int salesCount;
		int ctr;
		int revenue;
		Collection parametersCollection = new ArrayList();

		StatisticRequestParameter requestParametr = new StatisticRequestParameter(
				columnType, StatisticRequestParameter.EQUAL, statisticEntityId);
		parametersCollection.add(requestParametr);

		impressionsCount = StatisticBuilder.getStatistic(
				StatisticBuilder.IMPRESSION_CLASS, StatisticBuilder.COUNT_TYPE,
				columnType, parametersCollection);

		clicksCount = StatisticBuilder.getStatistic(
				StatisticBuilder.CLICK_CLASS, StatisticBuilder.COUNT_TYPE,
				columnType, parametersCollection);

		leadsCount = StatisticBuilder.getStatistic(
				StatisticBuilder.LEADS_CLASS, StatisticBuilder.COUNT_TYPE,
				columnType, parametersCollection);

		salesCount = StatisticBuilder.getStatistic(
				StatisticBuilder.SALES_CLASS, StatisticBuilder.COUNT_TYPE,
				columnType, parametersCollection);

		revenue = StatisticBuilder
				.getStatistic(null, StatisticBuilder.SUM_TYPE, rateColumnName,
						parametersCollection);

		StatisticEntity entity = new StatisticEntity();
		entity.setImpressions(impressionsCount);
		entity.setClicks(clicksCount);
		entity.setLeads(leadsCount);
		entity.setSales(salesCount);
		entity.setRevenue(revenue);

		return entity;
	}

	public static StatisticEntity getStatisticAsEntity(String columnType,
			String rateColumnName, String uniqueColumnName,
			Collection parametersCollection) {
		int impressionsCount;
		int clicksCount;
		int leadsCount;
		int salesCount;
		int ctr;
		int revenue;
		int uniques = 0;

		impressionsCount = StatisticBuilder.getStatistic(
				StatisticBuilder.IMPRESSION_CLASS, StatisticBuilder.COUNT_TYPE,
				columnType, parametersCollection);

		clicksCount = StatisticBuilder.getStatistic(
				StatisticBuilder.CLICK_CLASS, StatisticBuilder.COUNT_TYPE,
				columnType, parametersCollection);

		leadsCount = StatisticBuilder.getStatistic(
				StatisticBuilder.LEADS_CLASS, StatisticBuilder.COUNT_TYPE,
				columnType, parametersCollection);

		salesCount = StatisticBuilder.getStatistic(
				StatisticBuilder.SALES_CLASS, StatisticBuilder.COUNT_TYPE,
				columnType, parametersCollection);

		revenue = StatisticBuilder
				.getStatistic(null, StatisticBuilder.SUM_TYPE, rateColumnName,
						parametersCollection);

		if (uniqueColumnName != null) {
			uniques = StatisticBuilder.getStatistic(
					StatisticBuilder.IMPRESSION_CLASS,
					StatisticBuilder.DISTINCT_TYPE, uniqueColumnName,
					parametersCollection);
		}

		StatisticEntity entity = new StatisticEntity();
		entity.setImpressions(impressionsCount);
		entity.setClicks(clicksCount);
		entity.setLeads(leadsCount);
		entity.setSales(salesCount);
		entity.setRevenue(revenue);
		entity.setUniques(uniques);

		return entity;
	}

	public static void considerStatisticByTimePeriod(String statisticId,
			String statisticOwnerIdColumn, Map statisticMap,
			Calendar startDate, Calendar endDate, int IncreasePeriod,
			int calendarIdValue) {
		String rateColumn = StatisticInterface.PLACES_RATE_COLUMN;

		if (StatisticInterface.ADVERTISER_ID_COLUMN
				.equalsIgnoreCase(statisticOwnerIdColumn)) {
			rateColumn = StatisticInterface.BANNER_RATE_COLUMN;
		}

		Calendar periodDate = Calendar.getInstance();
		Calendar startDateLocal = Calendar.getInstance();

		startDateLocal.setTime(startDate.getTime());
		periodDate.setTime(startDate.getTime());
		periodDate.add(IncreasePeriod, 1);

		while (startDateLocal.before(endDate)) {
			Collection requestParameters = CommonStatisticAgregator
					.getParametersCollection(startDateLocal, periodDate,
							statisticOwnerIdColumn, statisticId);

			StatisticEntity entity = CommonStatisticAgregator
					.getStatisticAsEntity(StatisticImpl.TIME_COLUMN,
							rateColumn, null, requestParameters);

			StatisticEntity savedEntity = (StatisticEntity) statisticMap
					.get(Integer.toString(startDateLocal.get(calendarIdValue)));

			savedEntity.sum(entity);

			startDateLocal.add(IncreasePeriod, 1);
			periodDate.add(IncreasePeriod, 1);
		}
	}
}
