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
package com.adsapient.util.filters;

import com.adsapient.api.StatisticInterface;

import com.adsapient.api_impl.statistic.common.StatisticRequestParameter;
import com.adsapient.api_impl.statistic.engine.StatisticBuilder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TrafficFilterUtil {
	private static Map campainImpressions = Collections
			.synchronizedMap(new HashMap());

	private static Map campainClicks = Collections
			.synchronizedMap(new HashMap());

	public static int getClicksForCampain(Integer campainId,
			boolean increaseCache, boolean isImpression) {
		int campainClicksLocal = 0;

		if (campainClicks.containsKey(campainId)) {
			campainClicksLocal = ((Integer) campainClicks.get(campainId))
					.intValue();
		} else {
			Collection parametersCollection = new ArrayList();
			parametersCollection.add(new StatisticRequestParameter(
					StatisticInterface.CAMPAIGN_ID_COLUMN,
					StatisticRequestParameter.EQUAL, campainId.toString()));

			campainClicksLocal = StatisticBuilder
					.getStatistic(StatisticBuilder.CLICK_CLASS,
							StatisticBuilder.COUNT_TYPE,
							StatisticInterface.CAMPAIGN_ID_COLUMN,
							parametersCollection);

			campainClicks.put(campainId, new Integer(campainClicksLocal));
		}

		if (increaseCache && !isImpression) {
			campainClicksLocal++;
			campainClicks.put(campainId, new Integer(campainClicksLocal));
		}

		return campainClicksLocal;
	}

	public static int getClicksForCampainInDay(Integer campainId,
			boolean increaseCache, boolean isImpression) {
		StringBuffer stringBuffer = new StringBuffer();

		Calendar beginDate = Calendar.getInstance();

		int campainImpressionsLocal = 0;
		String key = new StringBuffer("campain").append(campainId)
				.append("day").append(beginDate.get(Calendar.DAY_OF_YEAR))
				.toString();

		if (campainClicks.containsKey(key)) {
			campainImpressionsLocal = ((Integer) campainClicks.get(key))
					.intValue();
		} else {
			beginDate.add(Calendar.DAY_OF_YEAR, -1);

			Collection parametersCollection = new ArrayList();
			parametersCollection.add(new StatisticRequestParameter(
					StatisticInterface.CAMPAIGN_ID_COLUMN,
					StatisticRequestParameter.EQUAL, campainId.toString()));
			parametersCollection.add(new StatisticRequestParameter(
					StatisticInterface.TIME_COLUMN,
					StatisticRequestParameter.MORE, Long.toString(beginDate
							.getTimeInMillis())));

			campainImpressionsLocal = StatisticBuilder
					.getStatistic(StatisticBuilder.CLICK_CLASS,
							StatisticBuilder.COUNT_TYPE,
							StatisticInterface.CAMPAIGN_ID_COLUMN,
							parametersCollection);

			campainClicks.put(key, new Integer(campainImpressionsLocal));
		}

		if (increaseCache && !isImpression) {
			campainImpressionsLocal++;
			campainClicks.put(key, new Integer(campainImpressionsLocal));
		}

		return campainImpressionsLocal;
	}

	public static int getCustomPeriodClick(Map requestMap, boolean unique,
			Integer campainId, int hour, int day, boolean increaseCache,
			boolean isImpression) {
		return 0;
	}

	public static int getCustomPeriodImpressions(Map requestMap,
			boolean unique, Integer campainId, int hour, int day,
			boolean increaseCache, boolean isImpression) {
		return 0;
	}

	public static int getImpressionsForCampain(Integer campainId,
			boolean increaseCache, boolean isImpression) {
		int campainImpressionsLocal = 0;

		if (campainImpressions.containsKey(campainId)) {
			campainImpressionsLocal = ((Integer) campainImpressions
					.get(campainId)).intValue();
		} else {
			Collection parametersCollection = new ArrayList();
			parametersCollection.add(new StatisticRequestParameter(
					StatisticInterface.CAMPAIGN_ID_COLUMN,
					StatisticRequestParameter.EQUAL, campainId.toString()));

			campainImpressionsLocal = StatisticBuilder
					.getStatistic(StatisticBuilder.IMPRESSION_CLASS,
							StatisticBuilder.COUNT_TYPE,
							StatisticInterface.CAMPAIGN_ID_COLUMN,
							parametersCollection);

			campainImpressions.put(campainId, new Integer(
					campainImpressionsLocal));
		}

		if (increaseCache && isImpression) {
			campainImpressionsLocal++;
			campainImpressions.put(campainId, new Integer(
					campainImpressionsLocal));
		}

		return campainImpressionsLocal;
	}

	public static int getImpressionsForCampainInDay(Integer campainId,
			boolean increaseCache, boolean isImpression) {
		StringBuffer stringBuffer = new StringBuffer();

		Calendar beginDate = Calendar.getInstance();

		int campainImpressionsLocal = 0;
		String key = new StringBuffer("campain").append(campainId)
				.append("day").append(beginDate.get(Calendar.DAY_OF_YEAR))
				.toString();

		if (campainImpressions.containsKey(key)) {
			campainImpressionsLocal = ((Integer) campainImpressions.get(key))
					.intValue();
		} else {
			beginDate.add(Calendar.DAY_OF_YEAR, -1);

			Collection parametersCollection = new ArrayList();
			parametersCollection.add(new StatisticRequestParameter(
					StatisticInterface.CAMPAIGN_ID_COLUMN,
					StatisticRequestParameter.EQUAL, campainId.toString()));
			parametersCollection.add(new StatisticRequestParameter(
					StatisticInterface.TIME_COLUMN,
					StatisticRequestParameter.MORE, Long.toString(beginDate
							.getTimeInMillis())));

			campainImpressionsLocal = StatisticBuilder
					.getStatistic(StatisticBuilder.IMPRESSION_CLASS,
							StatisticBuilder.COUNT_TYPE,
							StatisticInterface.CAMPAIGN_ID_COLUMN,
							parametersCollection);

			campainImpressions.put(key, new Integer(campainImpressionsLocal));
		}

		if (increaseCache && isImpression) {
			campainImpressionsLocal++;
			campainImpressions.put(key, new Integer(campainImpressionsLocal));
		}

		return campainImpressionsLocal;
	}

	public static int getUniqueClicksForCampain(Integer campainId,
			Map requestMap, boolean increaseCache, boolean isImpression) {
		return 0;
	}

	public static int getUniqueClicksForCampainInDay(Integer campainId,
			Map requestMap, boolean increaseCache, boolean isImpression) {
		return 0;
	}

	public static int getUniqueClicksForCampainInMonth(Integer campainId,
			Map requestMap, boolean increaseCache, boolean isImpression) {
		return 0;
	}

	public static int getUniqueImpressionsForCampain(Integer campainId,
			Map requestMap, boolean increaseCache, boolean isImpression) {
		return 0;
	}

	public static int getUniqueImpressionsForCampainInDay(Integer campainId,
			Map requestMap, boolean increaseCache, boolean isImpression) {
		return 0;
	}

	public static int getUniqueImpressionsForCampainInMonth(Integer campainId,
			Map requestMap, boolean increaseCache, boolean isImpression) {
		return 0;
	}

	public static void reload() {
		campainImpressions.clear();
		campainClicks.clear();
	}
}
