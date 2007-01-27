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
package com.adsapient.api_impl.managment.diagrams;

import com.adsapient.api.StatisticInterface;
import com.adsapient.api.DatasetBuilder;
import com.adsapient.api.*;

import com.adsapient.api_impl.statistic.advertizer.AdditionalAdvertiserStatistic;
import com.adsapient.api_impl.statistic.publisher.AdditionalPublisherStatistic;
import com.adsapient.api_impl.usermanagment.AccountSetting;
import com.adsapient.api_impl.usermanagment.UserImpl;

import com.adsapient.util.Msg;
import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.admin.AdsapientConstants;

import com.adsapient.gui.forms.AdvertizerStatisticActionForm;

import org.apache.log4j.Logger;

import org.jfree.data.category.CategoryDataset;

import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class DiagramFactory2 implements com.adsapient.api.DiagramFactory {
	private static float color = 0;

	static final int BACKGROUND_COLOR = 14737632;

	static final int MIN_NUMBER_OF_DAYS_IN_STATISTIC = 7;

	private static final String PUBLISHER = "publisher";

	private static final String ADVERTISER = "advertiser";

	public static final String IMPRESSIONS_COLUMN = "impressions";

	public static final String CLICKS_COLUMN = "clicks";

	Logger logger = Logger.getLogger(DiagramFactory2.class);

	public String generateDiagram(HttpServletRequest request, PrintWriter pw)
			throws Exception {
		String type = request.getParameter("type");
		String startDateString = request.getParameter("startDate");
		String endDateString = request.getParameter("endDate");
		String resultfileName;
		Calendar startDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();

		if ((startDateString == null) || (endDateString == null)) {
			endDateString = AdsapientConstants.SAMPLE_DATE_FORMAT
					.format(Calendar.getInstance().getTime());
			startDateString = AdsapientConstants.SAMPLE_DATE_FORMAT
					.format(AdvertizerStatisticActionForm.getDateMonthEgo());
		}

		startDate.setTime(AdsapientConstants.SAMPLE_DATE_FORMAT
				.parse(startDateString));
		endDate.setTime(AdsapientConstants.SAMPLE_DATE_FORMAT
				.parse(endDateString));

		HttpSession session = request.getSession();

		UserImpl user = (UserImpl) session
				.getAttribute(AdsapientConstants.USER);
		String action = request.getParameter("action");
		String header = request.getParameter("header");

		AccountSetting settings = (AccountSetting) MyHibernateUtil
				.loadObjectWithCriteria(AccountSetting.class, "userId", user
						.getId());

		if (settings == null) {
			logger
					.error("Cant drow diagram Cause is cant load setting for user "
							+ user.getId());

			return null;
		}

		if (PUBLISHER.equalsIgnoreCase(type)) {
			DatasetBuilder bilder = new SimpleDatasetBuilder();
			DiagramBuilder diagramBuilder = new BarcharDiagramBuilder();
			Collection statisticCollection = new ArrayList();

			if ("site".equalsIgnoreCase(action)) {
				String siteId = request.getParameter("siteId");
				statisticCollection = AdditionalPublisherStatistic
						.getReportByDayOfTheWeek(siteId,
								StatisticInterface.SITE_ID_COLUMN, startDate,
								endDate, request);
			}

			if ("sites".equalsIgnoreCase(action)) {
				statisticCollection = AdditionalPublisherStatistic
						.getDailyPublishingReport(user.getId().toString(),
								StatisticInterface.PUBLISHER_ID_COLUMN,
								startDate, endDate, request);
			}

			if ("allSites".equalsIgnoreCase(action)) {
				statisticCollection = AdditionalPublisherStatistic
						.getDailyPublishingReport("%",
								StatisticInterface.PUBLISHER_ID_COLUMN,
								startDate, endDate, request);
			}

			if ("places".equalsIgnoreCase(action)) {
				String placesId = request.getParameter("placeId");
				statisticCollection = AdditionalPublisherStatistic
						.getReportByDayOfTheWeek(placesId,
								StatisticInterface.PLACE_ID_COLUMN, startDate,
								endDate, request);
			}

			CategoryDataset categorydataset = (CategoryDataset) bilder
					.createDataset(statisticCollection, session,
							IMPRESSIONS_COLUMN, false);
			CategoryDataset secondCategorydataset = (CategoryDataset) bilder
					.createDataset(statisticCollection, session, CLICKS_COLUMN,
							false);

			return diagramBuilder.createDiagram(session, pw, categorydataset,
					secondCategorydataset, settings, Msg.fetch("dayofthemonth",
							request), header);
		}

		if (ADVERTISER.equalsIgnoreCase(type)) {
			DatasetBuilder bilder = new SimpleDatasetBuilder();
			DiagramBuilder diagramBuilder = new BarcharDiagramBuilder();
			Collection statisticCollection = new ArrayList();

			if ("campaigns".equalsIgnoreCase(action)) {
				statisticCollection = AdditionalAdvertiserStatistic
						.getAdvertisingReportByDates(user.getId().toString(),
								StatisticInterface.ADVERTISER_ID_COLUMN,
								startDate, endDate, request);
			}

			if ("campaign".equalsIgnoreCase(action)) {
				String campaignId = request.getParameter("campainId");
				statisticCollection = AdditionalAdvertiserStatistic
						.getAdvertisingReportByDates(campaignId,
								StatisticInterface.CAMPAIGN_ID_COLUMN,
								startDate, endDate, request);
			}

			if ("allCampaigns".equalsIgnoreCase(action)) {
				statisticCollection = AdditionalAdvertiserStatistic
						.getAdvertisingReportByDates("%",
								StatisticInterface.ADVERTISER_ID_COLUMN,
								startDate, endDate, request);
			}

			if ("banner".equalsIgnoreCase(action)) {
				String bannerId = request.getParameter("bannerId");
				statisticCollection = AdditionalAdvertiserStatistic
						.getAdvertisingReportByDates(bannerId,
								StatisticInterface.BANNER_ID_COLUMN, startDate,
								endDate, request);
			}

			CategoryDataset categorydataset = (CategoryDataset) bilder
					.createDataset(statisticCollection, session,
							IMPRESSIONS_COLUMN, false);
			CategoryDataset secondCategorydataset = (CategoryDataset) bilder
					.createDataset(statisticCollection, session, CLICKS_COLUMN,
							false);

			return diagramBuilder.createDiagram(session, pw, categorydataset,
					secondCategorydataset, settings, Msg.fetch("dayofthemonth",
							request), header);
		}

		return "";
	}
}
