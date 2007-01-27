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
package com.adsapient.api_impl.report.builder;

import com.adsapient.api.ReportPageBuilderInterface;
import com.adsapient.api.StatisticInterface;
import com.adsapient.api.User;

import com.adsapient.api_impl.report.ReportPage;
import com.adsapient.api_impl.report.entity.BaseReportEntity;
import com.adsapient.api_impl.report.entity.DateReportEntity;
import com.adsapient.api_impl.statistic.common.agregators.CommonStatisticAgregator;
import com.adsapient.api_impl.statistic.publisher.AdditionalPublisherStatistic;

import com.adsapient.util.Msg;
import com.adsapient.util.admin.AdsapientConstants;

import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

public class PublisherPageBuilder implements ReportPageBuilderInterface {
	private static Logger logger = Logger.getLogger(PublisherPageBuilder.class);

	public ReportPage createPage(HttpServletRequest request) {
		String start = request.getParameter("startDate");
		String end = request.getParameter("endDate");
		User user = (User) request.getSession().getAttribute(
				AdsapientConstants.USER);
		String publisherId = user.getId().toString();

		SimpleDateFormat sdf = new SimpleDateFormat(
				AdsapientConstants.DATE_FORMAT);
		Calendar startPeriod = Calendar.getInstance();
		Calendar endPeriod = Calendar.getInstance();

		try {
			startPeriod.setTime(sdf.parse(start));
			endPeriod.setTime(sdf.parse(end));
		} catch (java.text.ParseException e) {
			logger.error("in revenue by sites", e);
		}

		ReportPage page = new ReportPage();

		page.add(new DateReportEntity(request));

		page.add(new BaseReportEntity(AdditionalPublisherStatistic
				.getPublisherBySitesRevenueStatistic(user.getId(), startPeriod,
						endPeriod, request), "publishing.report.by.sites"));

		page.add(new BaseReportEntity(AdditionalPublisherStatistic
				.getDailyPublishingReport(publisherId,
						StatisticInterface.PUBLISHER_ID_COLUMN, startPeriod,
						endPeriod, request), Msg.fetch(
				"daily.publishing.report", request)));

		page.add(new BaseReportEntity(AdditionalPublisherStatistic
				.getPublishingReportByCampaigns(CommonStatisticAgregator
						.getParametersCollection(startPeriod, endPeriod,
								StatisticInterface.PUBLISHER_ID_COLUMN,
								publisherId), request), Msg.fetch(
				"what.displayed.report", request)));

		page.add(new BaseReportEntity(AdditionalPublisherStatistic
				.getReportByDayOfTheWeek(publisherId,
						StatisticInterface.PUBLISHER_ID_COLUMN, startPeriod,
						endPeriod, request), Msg.fetch(
				"activity.by.day.week.report", request)));

		page.add(new BaseReportEntity(AdditionalPublisherStatistic
				.getReportByCategory(publisherId,
						StatisticInterface.PUBLISHER_ID_COLUMN, startPeriod,
						endPeriod, request), Msg.fetch(
				"reports.by.place.Category", request)));

		page.add(new BaseReportEntity(AdditionalPublisherStatistic
				.getReportByPositions(publisherId,
						StatisticInterface.PUBLISHER_ID_COLUMN, startPeriod,
						endPeriod, request), Msg.fetch(
				"reports.publishing.report.by.positions", request)));

		page.add(new BaseReportEntity(AdditionalPublisherStatistic
				.getReportByHours(publisherId,
						StatisticInterface.PUBLISHER_ID_COLUMN, startPeriod,
						endPeriod, request), Msg.fetch(
				"reports.activity.hour.of.the.day", request)));

		page.add(new BaseReportEntity(AdditionalPublisherStatistic
				.getReportByCountrys(publisherId,
						StatisticInterface.PUBLISHER_ID_COLUMN, startPeriod,
						endPeriod, request), Msg.fetch("reports.bycountry",
				request)));

		page.add(new BaseReportEntity(AdditionalPublisherStatistic
				.getReportByUniques(user.getId(), startPeriod, endPeriod,
						request), Msg.fetch("reports.daily.uniques", request)));

		return page;
	}
}
