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
package com.adsapient.gui.actions;

import com.adsapient.api.StatisticInterface;

import com.adsapient.api_impl.exceptions.AdsapientSecurityException;
import com.adsapient.api_impl.statistic.common.agregators.CommonStatisticAgregator;
import com.adsapient.api_impl.statistic.publisher.AdditionalPublisherStatistic;
import com.adsapient.api_impl.usermanagment.UserImpl;
import com.adsapient.api_impl.usermanagment.role.RoleController;

import com.adsapient.util.Msg;
import com.adsapient.util.admin.AdsapientConstants;
import com.adsapient.util.security.SecureAction;
import com.adsapient.gui.forms.PublisherStatisticActionForm;

import org.apache.log4j.Logger;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.text.SimpleDateFormat;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class PublisherStatisticAction extends SecureAction {
    public static final String SITE = "Site";
    public static final String PLACES = "Places";
    public static final String PUBLISHERS = "Publishers";
    public static final String PUBLISHER = "Publisher";
    private static Logger logger = Logger.getLogger(PublisherStatisticAction.class);

    public ActionForward secureExecute(ActionMapping mapping,
        ActionForm actionForm, HttpServletRequest request,
        HttpServletResponse httpservletresponse) throws Exception {
        PublisherStatisticActionForm form = (PublisherStatisticActionForm) actionForm;

        UserImpl user = (UserImpl) request.getSession()
                                          .getAttribute(AdsapientConstants.USER);

        int userId = user.getId();

        if (RoleController.ADMIN.equalsIgnoreCase(user.getRole())) {
            try {
                userId = Integer.parseInt(request.getParameter("userId"));
            } catch (Exception e) {
                try {
                    userId = (Integer) request.getAttribute("userId");
                } catch (Exception e1) {
                }
            }
        }

        SimpleDateFormat sdf = new SimpleDateFormat(AdsapientConstants.DATE_FORMAT);
        Calendar startPeriod = Calendar.getInstance();
        Calendar endPeriod = Calendar.getInstance();

        try {
            startPeriod.setTime(sdf.parse(form.getStartDate()));
            endPeriod.setTime(sdf.parse(form.getEndDate()));
        } catch (java.text.ParseException e) {
            logger.error("in revenue by sites", e);
        }

        if (form.getReport() != null) {
            return mapping.findForward("report");
        }

        if ("daySelector".equalsIgnoreCase(form.getAction())) {
            return mapping.findForward("daySelector");
        }

        if (SITE.equalsIgnoreCase(form.getStatisticType()) &&
                (form.getAction() == null)) {
            return mapping.findForward("allSiteStats");
        }

        if (PLACES.equalsIgnoreCase(form.getStatisticType()) &&
                (form.getAction() == null)) {
            return mapping.findForward("allPlacesStats");
        }

        if (PUBLISHER.equalsIgnoreCase(form.getStatisticType()) &&
                (form.getAction() == null)) {
            return mapping.findForward("viewPublisherStatistic");
        }

        if (PUBLISHERS.equalsIgnoreCase(form.getStatisticType()) &&
                (form.getAction() == null)) {
            return mapping.findForward("allPublisherStats");
        }

        if ("revenueBySites".equalsIgnoreCase(form.getType())) {
            if (PUBLISHERS.equalsIgnoreCase(form.getAction())) {
                form.setRevenueBySitesCollection(AdditionalPublisherStatistic.getPublisherBySitesRevenueStatistic(
                        null, startPeriod, endPeriod, request));

                return mapping.findForward("viewRevenueBySites");
            }

            form.setRevenueBySitesCollection(AdditionalPublisherStatistic.getPublisherBySitesRevenueStatistic(
                    userId, startPeriod, endPeriod, request));

            return mapping.findForward("viewRevenueBySites");
        }

        if ("publisherDailyRevenue".equalsIgnoreCase(form.getType())) {
            if (PUBLISHERS.equals(form.getAction())) {
                form.setTableHeader(Msg.fetch("daily.publishing.report", request));
                form.setStatisticCollection(AdditionalPublisherStatistic.getDailyPublishingReport(
                        "%", StatisticInterface.PUBLISHER_ID_COLUMN,
                        startPeriod, endPeriod, request));

                return mapping.findForward("publisherDailyRevenue");
            }

            if (SITE.equals(form.getAction())) {
                form.setTableHeader(Msg.fetch("daily.reports.for.site", request));
                form.setStatisticCollection(AdditionalPublisherStatistic.getDailyPublishingReport(
                        form.getSiteId(), StatisticInterface.SITE_ID_COLUMN,
                        startPeriod, endPeriod, request));

                return mapping.findForward("publisherDailyRevenue");
            }

            if (PLACES.equalsIgnoreCase(form.getAction())) {
                form.setTableHeader(Msg.fetch("daily.reports.for.places",
                        request) + form.getPlaceId());
                form.setStatisticCollection(AdditionalPublisherStatistic.getDailyPublishingReport(
                        form.getPlaceId(), StatisticInterface.PLACE_ID_COLUMN,
                        startPeriod, endPeriod, request));

                return mapping.findForward("publisherDailyRevenue");
            }

            form.setStatisticCollection(AdditionalPublisherStatistic.getDailyPublishingReport(
                    "" + userId, StatisticInterface.PUBLISHER_ID_COLUMN,
                    startPeriod, endPeriod, request));
            form.setTableHeader(Msg.fetch("daily.publishing.report", request));

            return mapping.findForward("publisherDailyRevenue");
        }

        if ("statisticByCampaigns".equalsIgnoreCase(form.getType())) {
            if (PUBLISHERS.equals(form.getAction())) {
                form.setTableHeader(Msg.fetch("what.displayed.report", request));

                form.setStatisticCollection(AdditionalPublisherStatistic.getPublishingReportByCampaigns(
                        CommonStatisticAgregator.getParametersCollection(
                            startPeriod, endPeriod,
                            StatisticInterface.PUBLISHER_ID_COLUMN, "%"),
                        request));

                return mapping.findForward("activityByCampaigns");
            }

            if (SITE.equals(form.getAction())) {
                form.setTableHeader(Msg.fetch("what.displayed.report.site",
                        request) + form.getSiteId());

                form.setStatisticCollection(AdditionalPublisherStatistic.getPublishingReportByCampaigns(
                        CommonStatisticAgregator.getParametersCollection(
                            startPeriod, endPeriod,
                            StatisticInterface.SITE_ID_COLUMN, form.getSiteId()),
                        request));

                return mapping.findForward("activityByCampaigns");
            }

            if (PLACES.equals(form.getAction())) {
                form.setTableHeader(Msg.fetch("what.displayed.report.places",
                        request) + form.getPlaceId());

                form.setStatisticCollection(AdditionalPublisherStatistic.getPublishingReportByCampaigns(
                        CommonStatisticAgregator.getParametersCollection(
                            startPeriod, endPeriod,
                            StatisticInterface.PLACE_ID_COLUMN,
                            form.getPlaceId()), request));

                return mapping.findForward("activityByCampaigns");
            }

            form.setStatisticCollection(AdditionalPublisherStatistic.getPublishingReportByCampaigns(
                    CommonStatisticAgregator.getParametersCollection(
                        startPeriod, endPeriod,
                        StatisticInterface.PUBLISHER_ID_COLUMN, "" + userId),
                    request));

            form.setTableHeader(Msg.fetch("what.displayed.report", request));

            return mapping.findForward("activityByCampaigns");
        }

        if ("dayoftheweek".equalsIgnoreCase(form.getType())) {
            if (SITE.equalsIgnoreCase(form.getAction())) {
                form.setTableHeader(Msg.fetch("activity.by.day.week.for.site",
                        request) + form.getSiteId());
                form.setStatisticCollection(AdditionalPublisherStatistic.getReportByDayOfTheWeek(
                        form.getSiteId(), StatisticInterface.SITE_ID_COLUMN,
                        startPeriod, endPeriod, request));

                return mapping.findForward("activityDayOfTheWeek");
            }

            if (PLACES.equalsIgnoreCase(form.getAction())) {
                form.setTableHeader(Msg.fetch(
                        "activity.by.day.week.for.places", request) +
                    form.getPlaceId());
                form.setStatisticCollection(AdditionalPublisherStatistic.getReportByDayOfTheWeek(
                        form.getPlaceId(), StatisticInterface.PLACE_ID_COLUMN,
                        startPeriod, endPeriod, request));

                return mapping.findForward("activityDayOfTheWeek");
            }

            if (PUBLISHERS.equalsIgnoreCase(form.getAction())) {
                form.setTableHeader(Msg.fetch("activity.by.day.week.report",
                        request));
                form.setStatisticCollection(AdditionalPublisherStatistic.getReportByDayOfTheWeek(
                        "%", StatisticInterface.PUBLISHER_ID_COLUMN,
                        startPeriod, endPeriod, request));

                return mapping.findForward("activityDayOfTheWeek");
            }

            form.setTableHeader(Msg.fetch("activity.by.day.week.report", request));

            form.setStatisticCollection(AdditionalPublisherStatistic.getReportByDayOfTheWeek(
                    "" + userId, StatisticInterface.PUBLISHER_ID_COLUMN,
                    startPeriod, endPeriod, request));

            return mapping.findForward("activityDayOfTheWeek");
        }

        if ("statisticByCategory".equalsIgnoreCase(form.getType())) {
            if (SITE.equalsIgnoreCase(form.getAction())) {
                form.setTableHeader(Msg.fetch(
                        "reports.by.place.Category.site", request) +
                    form.getSiteId());
                form.setStatisticCollection(AdditionalPublisherStatistic.getReportByCategory(
                        form.getSiteId(), StatisticInterface.SITE_ID_COLUMN,
                        startPeriod, endPeriod, request));

                return mapping.findForward("viewStatisticByCategory");
            }

            if (PUBLISHERS.equalsIgnoreCase(form.getAction())) {
                form.setTableHeader(Msg.fetch("reports.by.place.Category",
                        request));
                form.setStatisticCollection(AdditionalPublisherStatistic.getReportByCategory(
                        "%", StatisticInterface.PUBLISHER_ID_COLUMN,
                        startPeriod, endPeriod, request));

                return mapping.findForward("viewStatisticByCategory");
            }

            form.setTableHeader(Msg.fetch("reports.by.place.Category", request));
            form.setStatisticCollection(AdditionalPublisherStatistic.getReportByCategory(
                    "" + userId, StatisticInterface.PUBLISHER_ID_COLUMN,
                    startPeriod, endPeriod, request));

            return mapping.findForward("viewStatisticByCategory");
        }

        if ("statisticByPositions".equalsIgnoreCase(form.getType())) {
            if (SITE.equalsIgnoreCase(form.getAction())) {
                form.setTableHeader(Msg.fetch(
                        "reports.publishing.report.by.positions.site", request) +
                    form.getSiteId());
                form.setStatisticCollection(AdditionalPublisherStatistic.getReportByPositions(
                        form.getSiteId(), StatisticInterface.SITE_ID_COLUMN,
                        startPeriod, endPeriod, request));

                return mapping.findForward("activityByPositions");
            }

            if (PUBLISHERS.equalsIgnoreCase(form.getAction())) {
                form.setTableHeader(Msg.fetch(
                        "reports.publishing.report.by.positions", request));
                form.setStatisticCollection(AdditionalPublisherStatistic.getReportByPositions(
                        "%", StatisticInterface.PUBLISHER_ID_COLUMN,
                        startPeriod, endPeriod, request));

                return mapping.findForward("activityByPositions");
            }

            form.setTableHeader(Msg.fetch(
                    "reports.publishing.report.by.positions", request));
            form.setStatisticCollection(AdditionalPublisherStatistic.getReportByPositions(
                    "" + userId, StatisticInterface.PUBLISHER_ID_COLUMN,
                    startPeriod, endPeriod, request));

            return mapping.findForward("activityByPositions");
        }

        if ("statisticByHours".equalsIgnoreCase(form.getType())) {
            if (SITE.equalsIgnoreCase(form.getAction())) {
                form.setTableHeader(Msg.fetch(
                        "reports.activity.hour.of.the.day.site", request) +
                    form.getSiteId());
                form.setStatisticCollection(AdditionalPublisherStatistic.getReportByHours(
                        form.getSiteId(), StatisticInterface.SITE_ID_COLUMN,
                        startPeriod, endPeriod, request));

                return mapping.findForward("activityByHours");
            }

            if (PLACES.equalsIgnoreCase(form.getAction())) {
                form.setTableHeader(Msg.fetch(
                        "reports.activity.hour.of.the.day.places", request) +
                    form.getPlaceId());
                form.setStatisticCollection(AdditionalPublisherStatistic.getReportByHours(
                        form.getPlaceId(), StatisticInterface.PLACE_ID_COLUMN,
                        startPeriod, endPeriod, request));

                return mapping.findForward("activityByHours");
            }

            if (PUBLISHERS.equalsIgnoreCase(form.getAction())) {
                form.setTableHeader(Msg.fetch(
                        "reports.activity.hour.of.the.day", request));
                form.setStatisticCollection(AdditionalPublisherStatistic.getReportByHours(
                        "%", StatisticInterface.PUBLISHER_ID_COLUMN,
                        startPeriod, endPeriod, request));

                return mapping.findForward("activityByHours");
            }

            form.setTableHeader(Msg.fetch("reports.activity.hour.of.the.day",
                    request));
            form.setStatisticCollection(AdditionalPublisherStatistic.getReportByHours(
                    "" + userId, StatisticInterface.PUBLISHER_ID_COLUMN,
                    startPeriod, endPeriod, request));

            return mapping.findForward("activityByHours");
        }

        if ("statisticByCoutrys".equalsIgnoreCase(form.getType())) {
            if (SITE.equalsIgnoreCase(form.getAction())) {
                form.setTableHeader(Msg.fetch("reports.bycountry.site", request) +
                    form.getSiteId());

                form.setStatisticCollection(AdditionalPublisherStatistic.getReportByCountrys(
                        form.getSiteId(), StatisticInterface.SITE_ID_COLUMN,
                        startPeriod, endPeriod, request));

                return mapping.findForward("activityByCountrys");
            }

            if (PLACES.equalsIgnoreCase(form.getAction())) {
                form.setTableHeader(Msg.fetch("reports.bycountry.places",
                        request) + form.getPlaceId());

                form.setStatisticCollection(AdditionalPublisherStatistic.getReportByCountrys(
                        form.getPlaceId(), StatisticInterface.PLACE_ID_COLUMN,
                        startPeriod, endPeriod, request));

                return mapping.findForward("activityByCountrys");
            }

            if (PUBLISHERS.equalsIgnoreCase(form.getAction())) {
                form.setTableHeader(Msg.fetch("reports.bycountry", request));
                form.setStatisticCollection(AdditionalPublisherStatistic.getReportByCountrys(
                        "%", StatisticInterface.PUBLISHER_ID_COLUMN,
                        startPeriod, endPeriod, request));

                return mapping.findForward("activityByCountrys");
            }

            form.setTableHeader(Msg.fetch("reports.bycountry", request));
            form.setStatisticCollection(AdditionalPublisherStatistic.getReportByCountrys(
                    "" + userId, StatisticInterface.PUBLISHER_ID_COLUMN,
                    startPeriod, endPeriod, request));

            return mapping.findForward("activityByCountrys");
        }

        if ("statisticByUniques".equalsIgnoreCase(form.getType())) {
            if (!PUBLISHERS.equalsIgnoreCase(form.getAction())) {
            }

            form.setStatisticCollection(AdditionalPublisherStatistic.getReportByUniques(
                    userId, startPeriod, endPeriod, request));

            return mapping.findForward("activityByUniques");
        }

        return mapping.findForward("empty");
    }

    protected void checkAccessRestriction(
        HttpServletRequest httpservletrequest, ActionForm actionform)
        throws AdsapientSecurityException {
    }
}
