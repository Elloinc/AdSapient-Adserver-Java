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
package com.adsapient.adserver.filters.stateful;

import com.adsapient.adserver.beans.AdserverModel;
import com.adsapient.adserver.beans.ReporterModel;
import com.adsapient.adserver.beans.TotalsReporterModel;
import com.adsapient.adserver.reporter.ReporterDecisionProvider;

import com.adsapient.api.FilterInterface;

import com.adsapient.shared.mappable.BannerImpl;
import com.adsapient.shared.mappable.CampainImpl;
import com.adsapient.shared.mappable.TrafficFilter;

import com.adsapient.shared.mappable.TotalsReport;
import com.adsapient.shared.AdsapientConstants;

import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Map;

public class TrafficFilterProcessor {
	static Logger logger = Logger.getLogger(TrafficFilterProcessor.class);

	private AdserverModel adserverModel;

	private ReporterModel reporterModel;

	private TotalsReporterModel totalsReporterModel;

	private ReporterDecisionProvider reporterDecisionProvider;

	private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

	public boolean doFilter(FilterInterface entity,
			Map<String, Object> requestParams) {
		try {
			TrafficFilter trafficFilter = (TrafficFilter) entity;
			CampainImpl campaign = (CampainImpl) adserverModel
					.getCampaignsMap().get(trafficFilter.getCampainId());
			BannerImpl banner = (BannerImpl) requestParams
					.get(AdsapientConstants.BANNER_REQUEST_PARAM_KEY);

			int scheduledNumberOfAdviews = trafficFilter
					.getMaxImpressionsInCampain();
			TotalsReport tr = totalsReporterModel.getEntityObjects().get(
					campaign.getId() + CampainImpl.class.getName());

			if (tr == null) {
				tr = new TotalsReport();
			}

			Integer adviewsDisplayed = (tr.getAdviews() == null) ? 0 : tr
					.getAdviews();
			tr = totalsReporterModel.getEntityObjects().get(
					campaign.getId() + CampainImpl.class.getName());

			if (tr == null) {
				tr = new TotalsReport();
			}

			Integer clicksGenerated = (tr.getClicks() == null) ? 0 : tr
					.getClicks();
			long timeServed = new Date().getTime()
					- sdf.parse(banner.getStartDate()).getTime();
			long totalTime = sdf.parse(banner.getEndDate()).getTime()
					- sdf.parse(banner.getStartDate()).getTime();
			long numberOfMillisecondsInDay = 1000 * 60 * 60 * 24;
			long numberOfMillisecondsInMonth = numberOfMillisecondsInDay * 30;

			if (scheduledNumberOfAdviews != 0) {
				if (adviewsDisplayed >= scheduledNumberOfAdviews) {
					return false;
				}

				int adviewsDelta = scheduledNumberOfAdviews - adviewsDisplayed;

				long timeDelta = sdf.parse(banner.getEndDate()).getTime()
						- new Date().getTime();

				long serveSpeed = (adviewsDisplayed == 0) ? timeServed
						: (timeServed / adviewsDisplayed);
				long distributionSpeed = timeDelta / adviewsDelta;

				if (distributionSpeed > serveSpeed) {
					return false;
				}
			}

			int scheduledNumberOfClicks = trafficFilter.getMaxClicksInCampain();

			if (scheduledNumberOfClicks != 0) {
				if (clicksGenerated >= scheduledNumberOfClicks) {
					return false;
				}

				int clicksDelta = scheduledNumberOfClicks - clicksGenerated;

				long timeDelta = sdf.parse(banner.getEndDate()).getTime()
						- new Date().getTime();

				long serveSpeed = (clicksGenerated == 0) ? timeServed
						: (timeServed / clicksGenerated);
				long distributionSpeed = timeDelta / clicksDelta;

				if (distributionSpeed > serveSpeed) {
					return false;
				}
			}

			int adviewsFrequencyPerDay = trafficFilter.getMaxImpressionsInDay();

			if (adviewsFrequencyPerDay != 0) {
				if (isFrequencyCapped(adviewsDisplayed,
						numberOfMillisecondsInDay, adviewsFrequencyPerDay,
						timeServed)) {
					return false;
				}
			}

			int clicksFrequencyPerDay = trafficFilter.getMaxClicksInDay();

			if (clicksFrequencyPerDay != 0) {
				if (isFrequencyCapped(clicksGenerated,
						numberOfMillisecondsInDay, clicksFrequencyPerDay,
						timeServed)) {
					return false;
				}
			}

			int adviewsFrequencyPerCustomPeriod = trafficFilter
					.getCustomPeriodValue();

			if (adviewsFrequencyPerCustomPeriod != 0) {
				int customPeriodDay = trafficFilter.getCustomPeriodDay();
				int customPeriodHour = trafficFilter.getCustomPeriodHour();
				long numberOfMillisecondsInCustomPeriod = (1000 * 60 * 60 * 24 * customPeriodDay)
						+ (1000 * 60 * 60 * customPeriodHour);

				if ((numberOfMillisecondsInCustomPeriod != 0)
						&& isFrequencyCapped(adviewsDisplayed,
								numberOfMillisecondsInCustomPeriod,
								adviewsFrequencyPerCustomPeriod, timeServed)) {
					return false;
				}
			}

			int clicksFrequencyPerCustomPeriod = trafficFilter
					.getCustomPeriodClickInValue();

			if (clicksFrequencyPerCustomPeriod != 0) {
				int customPeriodDay = trafficFilter.getCustomPeriodClickInDay();
				int customPeriodHour = trafficFilter
						.getCustomPeriodClickInHour();
				long numberOfMillisecondsInCustomPeriod = (1000 * 60 * 60 * 24 * customPeriodDay)
						+ (1000 * 60 * 60 * customPeriodHour);

				if ((numberOfMillisecondsInCustomPeriod != 0)
						&& isFrequencyCapped(clicksGenerated,
								numberOfMillisecondsInCustomPeriod,
								clicksFrequencyPerCustomPeriod, timeServed)) {
					return false;
				}
			}

			int adviewsFrequencyPerDayPerUniqueUser = trafficFilter
					.getMaxImpresionsInDayForUniqueUser();

			if (adviewsFrequencyPerDayPerUniqueUser != 0) {
				if (!reporterDecisionProvider
						.canDisplayToUniqueWithPerDayFrequency(timeServed,
								numberOfMillisecondsInDay,
								adviewsFrequencyPerDayPerUniqueUser,
								requestParams, AdsapientConstants.ADVIEW)) {
					return false;
				}
			}

			int clicksFrequencyPerDayPerUniqueUser = trafficFilter
					.getMaxClicksInDayForUniqueUser();

			if (clicksFrequencyPerDayPerUniqueUser != 0) {
				if (!reporterDecisionProvider
						.canDisplayToUniqueWithPerDayFrequency(timeServed,
								numberOfMillisecondsInDay,
								clicksFrequencyPerDayPerUniqueUser,
								requestParams, AdsapientConstants.CLICK)) {
					return false;
				}
			}

			int adviewsFrequencyPerCampaignPerUniqueUser = trafficFilter
					.getMaxImpressionsInCampainForUniqueUser();

			if (adviewsFrequencyPerCampaignPerUniqueUser != 0) {
				if (!reporterDecisionProvider
						.canDisplayToUniqueWithPerCampaignFrequency(timeServed,
								totalTime,
								adviewsFrequencyPerCampaignPerUniqueUser,
								requestParams, AdsapientConstants.ADVIEW)) {
					return false;
				}
			}

			int clicksFrequencyPerCampaignPerUniqueUser = trafficFilter
					.getMaxClicksInCampainForUniqueUser();

			if (clicksFrequencyPerCampaignPerUniqueUser != 0) {
				if (!reporterDecisionProvider
						.canDisplayToUniqueWithPerCampaignFrequency(timeServed,
								totalTime,
								clicksFrequencyPerCampaignPerUniqueUser,
								requestParams, AdsapientConstants.CLICK)) {
					return false;
				}
			}

			int adviewsFrequencyPerMonthPerUniqueUser = trafficFilter
					.getMaxImpressionsInMonthForUniqueUser();

			if (adviewsFrequencyPerMonthPerUniqueUser != 0) {
				if (!reporterDecisionProvider
						.canDisplayToUniqueWithPerMonthFrequency(timeServed,
								numberOfMillisecondsInMonth,
								adviewsFrequencyPerMonthPerUniqueUser,
								requestParams, AdsapientConstants.ADVIEW)) {
					return false;
				}
			}

			int clicksFrequencyPerMonthPerUniqueUser = trafficFilter
					.getMaxClicksInMonthForUniqueUser();

			if (clicksFrequencyPerMonthPerUniqueUser != 0) {
				if (!reporterDecisionProvider
						.canDisplayToUniqueWithPerMonthFrequency(timeServed,
								numberOfMillisecondsInMonth,
								clicksFrequencyPerMonthPerUniqueUser,
								requestParams, AdsapientConstants.CLICK)) {
					return false;
				}
			}

			int adviewsFrequencyPerCustomPeriodPerUniqueUser = trafficFilter
					.getCustomPeriodValueUnique();

			if (adviewsFrequencyPerCustomPeriodPerUniqueUser != 0) {
				int customPeriodDay = trafficFilter.getCustomPeriodDayUnique();
				int customPeriodHour = trafficFilter
						.getCustomPeriodHourUnique();
				long numberOfMillisecondsInCustomPeriod = (1000 * 60 * 60 * 24 * customPeriodDay)
						+ (1000 * 60 * 60 * customPeriodHour);

				if ((numberOfMillisecondsInCustomPeriod != 0)
						&& !reporterDecisionProvider
								.canDisplayToUniqueWithPerCustomPeriodFrequency(
										timeServed,
										numberOfMillisecondsInCustomPeriod,
										adviewsFrequencyPerCustomPeriodPerUniqueUser,
										requestParams,
										AdsapientConstants.ADVIEW,
										numberOfMillisecondsInCustomPeriod)) {
					return false;
				}
			}

			int clicksFrequencyPerCustomPeriodPerUniqueUser = trafficFilter
					.getCustomPeriodClickInValueUnique();

			if (clicksFrequencyPerCustomPeriodPerUniqueUser != 0) {
				int customPeriodDay = trafficFilter
						.getCustomPeriodClickInDayUnique();
				int customPeriodHour = trafficFilter
						.getCustomPeriodClickInHourUnique();
				long numberOfMillisecondsInCustomPeriod = (1000 * 60 * 60 * 24 * customPeriodDay)
						+ (1000 * 60 * 60 * customPeriodHour);

				if ((numberOfMillisecondsInCustomPeriod != 0)
						&& !reporterDecisionProvider
								.canDisplayToUniqueWithPerCustomPeriodFrequency(
										timeServed,
										numberOfMillisecondsInCustomPeriod,
										clicksFrequencyPerCustomPeriodPerUniqueUser,
										requestParams,
										AdsapientConstants.CLICK,
										numberOfMillisecondsInCustomPeriod)) {
					return false;
				}
			}
		} catch (ParseException ex) {
			logger.error(ex.getMessage(), ex);
		}

		return true;
	}

	private boolean isFrequencyCapped(int unitsGenerated, long period,
			int frequencyCapping, long timeServed) {
		long numberOfPeriods = ((timeServed / period) == 0) ? 1
				: (timeServed / period);
		long currentFrequencyPerPeriod = unitsGenerated / numberOfPeriods;

		if (frequencyCapping < currentFrequencyPerPeriod) {
			return true;
		} else {
			return false;
		}
	}

	public AdserverModel getAdserverModel() {
		return adserverModel;
	}

	public void setAdserverModel(AdserverModel adserverModel) {
		this.adserverModel = adserverModel;
	}

	public ReporterModel getReporterModel() {
		return reporterModel;
	}

	public void setReporterModel(ReporterModel reporterModel) {
		this.reporterModel = reporterModel;
	}

	public TotalsReporterModel getTotalsReporterModel() {
		return totalsReporterModel;
	}

	public void setTotalsReporterModel(TotalsReporterModel totalsReporterModel) {
		this.totalsReporterModel = totalsReporterModel;
	}

	public ReporterDecisionProvider getReporterDecisionProvider() {
		return reporterDecisionProvider;
	}

	public void setReporterDecisionProvider(
			ReporterDecisionProvider reporterDecisionProvider) {
		this.reporterDecisionProvider = reporterDecisionProvider;
	}
}
