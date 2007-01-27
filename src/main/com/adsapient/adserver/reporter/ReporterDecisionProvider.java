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
package com.adsapient.adserver.reporter;

import com.adsapient.adserver.beans.VisitorObjectImpl;

import com.adsapient.api_impl.advertizer.BannerImpl;

import com.adsapient.shared.api.entity.IEventObject;
import com.adsapient.shared.api.entity.ISpecificEventsObject;

import com.adsapient.util.admin.AdsapientConstants;

import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class ReporterDecisionProvider {
	static Logger logger = Logger.getLogger(ReporterDecisionProvider.class);

	private AdsapientReporter adsapientReporter;

	private String datePattern = "yyMMddHHmmss";

	private SimpleDateFormat dateFormat;

	private UniqueVisitorBinding binding;

	public void setup() {
		dateFormat = new SimpleDateFormat(datePattern);
		binding = new UniqueVisitorBinding();
		binding.setSimpleDateFormat(dateFormat);
	}

	public boolean canDisplayToUniqueWithPerDayFrequency(long timeServed,
			long period, int unitsFrequencyPerDayPerUniqueUser,
			Map<String, Object> requestParams, byte eventType) {
		try {
			ISpecificEventsObject specificEventObject = findSpecificEventsObject(
					requestParams, eventType);
			int numberOfUnitsServed = specificEventObject.getDateToCountMap()
					.get(new Date());

			if (numberOfUnitsServed >= unitsFrequencyPerDayPerUniqueUser) {
				return false;
			}

			long numberOfPeriods = ((timeServed / period) == 0) ? 1
					: (timeServed / period);
			long currentFrequencyPerPeriod = numberOfUnitsServed
					/ numberOfPeriods;

			if (unitsFrequencyPerDayPerUniqueUser < currentFrequencyPerPeriod) {
				return true;
			} else {
				return false;
			}
		} catch (NullPointerException ex) {
			return true;
		}
	}

	public boolean canDisplayToUniqueWithPerCampaignFrequency(long timeServed,
			long period, int adviewsFrequencyPerCampaignPerUniqueUser,
			Map<String, Object> requestParams, byte eventType) {
		try {
			ISpecificEventsObject specificEventObject = findSpecificEventsObject(
					requestParams, eventType);
			int numberOfUnitsServed = 0;

			for (Date d : specificEventObject.getDateToCountMap().keySet()) {
				int eventsCount = specificEventObject.getDateToCountMap()
						.get(d);
				numberOfUnitsServed += eventsCount;
			}

			if (numberOfUnitsServed >= adviewsFrequencyPerCampaignPerUniqueUser) {
				return false;
			}

			long numberOfPeriods = ((timeServed / period) == 0) ? 1
					: (timeServed / period);
			long currentFrequencyPerPeriod = numberOfUnitsServed
					/ numberOfPeriods;

			if (adviewsFrequencyPerCampaignPerUniqueUser < currentFrequencyPerPeriod) {
				return true;
			} else {
				return false;
			}
		} catch (NullPointerException ex) {
			return true;
		}
	}

	public boolean canDisplayToUniqueWithPerMonthFrequency(long timeServed,
			long period, int adviewsFrequencyPerMonthPerUniqueUser,
			Map<String, Object> requestParams, byte eventType) {
		try {
			ISpecificEventsObject specificEventObject = findSpecificEventsObject(
					requestParams, eventType);
			Date today = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(today);

			int todaysMonth = c.get(Calendar.MONTH);

			int numberOfUnitsServed = 0;

			for (Date d : specificEventObject.getDateToCountMap().keySet()) {
				c.setTime(d);

				int month = c.get(Calendar.MONTH);

				if (todaysMonth == month) {
					int eventsCount = specificEventObject.getDateToCountMap()
							.get(d);
					numberOfUnitsServed += eventsCount;
				}
			}

			if (numberOfUnitsServed >= adviewsFrequencyPerMonthPerUniqueUser) {
				return false;
			}

			long numberOfPeriods = ((timeServed / period) == 0) ? 1
					: (timeServed / period);
			long currentFrequencyPerPeriod = numberOfUnitsServed
					/ numberOfPeriods;

			if (adviewsFrequencyPerMonthPerUniqueUser < currentFrequencyPerPeriod) {
				return true;
			} else {
				return false;
			}
		} catch (NullPointerException ex) {
			return true;
		}
	}

	public boolean canDisplayToUniqueWithPerCustomPeriodFrequency(
			long timeServed, long period,
			int adviewsFrequencyPerCustomPeriodPerUniqueUser,
			Map<String, Object> requestParams, byte eventType,
			long numberOfMillisecondsInCustomPeriod) {
		try {
			ISpecificEventsObject specificEventObject = findSpecificEventsObject(
					requestParams, eventType);
			Date rightNow = new Date();
			long before = rightNow.getTime()
					- numberOfMillisecondsInCustomPeriod;
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(before);

			int numberOfUnitsServed = 0;

			for (Date d : specificEventObject.getDateToCountMap().keySet()) {
				if (c.before(d)) {
					int eventsCount = specificEventObject.getDateToCountMap()
							.get(d);
					numberOfUnitsServed += eventsCount;
				}
			}

			if (numberOfUnitsServed >= adviewsFrequencyPerCustomPeriodPerUniqueUser) {
				return false;
			}

			long numberOfPeriods = ((timeServed / period) == 0) ? 1
					: (timeServed / period);
			long currentFrequencyPerPeriod = numberOfUnitsServed
					/ numberOfPeriods;

			if (adviewsFrequencyPerCustomPeriodPerUniqueUser < currentFrequencyPerPeriod) {
				return true;
			} else {
				return false;
			}
		} catch (NullPointerException ex) {
			return true;
		}
	}

	public AdsapientReporter getAdsapientReporter() {
		return adsapientReporter;
	}

	public void setAdsapientReporter(AdsapientReporter adsapientReporter) {
		this.adsapientReporter = adsapientReporter;
	}

	private ISpecificEventsObject findSpecificEventsObject(
			Map<String, Object> requestParams, byte eventType) {
		try {
			VisitorObjectImpl foundVisitor = adsapientReporter
					.getVisitorObject(requestParams);
			BannerImpl banner = (BannerImpl) requestParams
					.get(AdsapientConstants.BANNER_REQUEST_PARAM_KEY);
			IEventObject eventObject = foundVisitor
					.getEntityCompositeKeyToEventsMap().get(
							banner.getId()
									+ AdsapientConstants.BANNER_ENTITY_ID);
			ISpecificEventsObject specificEventObject = eventObject
					.getAdEventTypeToSpecificEventsObjectMap().get(eventType);

			return specificEventObject;
		} catch (NullPointerException ex) {
			return null;
		}
	}
}
