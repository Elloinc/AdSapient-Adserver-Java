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
import com.adsapient.api.User;

import com.adsapient.api_impl.exceptions.AdsapientSecurityException;
import com.adsapient.api_impl.statistic.advertizer.AdditionalAdvertiserStatistic;

import com.adsapient.util.Msg;
import com.adsapient.util.admin.AdsapientConstants;
import com.adsapient.util.security.SecureAction;
import com.adsapient.gui.forms.AdvertizerStatisticActionForm;

import org.apache.log4j.Logger;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.text.SimpleDateFormat;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AdvertizerStatisticAction extends SecureAction {
    private static Logger logger = Logger.getLogger(AdvertizerStatisticAction.class);
    public static final String CAMPAIGN = "Campaign";
    public static final String BANNER = "Banner";
    public static final String ADVERTISERS = "Advertisers";
    public static final String ADVERTISER = "Advertiser";

    public ActionForward secureExecute(ActionMapping mapping,
        ActionForm actionForm, HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        User user = (User) request.getSession()
                                  .getAttribute(AdsapientConstants.USER);

        AdvertizerStatisticActionForm form = (AdvertizerStatisticActionForm) actionForm;

        SimpleDateFormat sdf = new SimpleDateFormat(AdsapientConstants.DATE_FORMAT);
        Calendar startPeriod = Calendar.getInstance();
        Calendar endPeriod = Calendar.getInstance();

        if (form.getReport() != null) {
            return mapping.findForward("report");
        }

        if ("dateSelector".equalsIgnoreCase(form.getAction())) {
            return mapping.findForward("dateSelector");
        }

        if (CAMPAIGN.equalsIgnoreCase(form.getStatisticType()) &&
                (form.getAction() == null)) {
            return mapping.findForward("allCampainStatistic");
        }

        if (BANNER.equalsIgnoreCase(form.getStatisticType()) &&
                (form.getAction() == null)) {
            return mapping.findForward("allBannerStatistic");
        }

        if (ADVERTISER.equalsIgnoreCase(form.getStatisticType()) &&
                (form.getAction() == null)) {
            return mapping.findForward("viewAdvertizerStatistic");
        }

        if (ADVERTISERS.equalsIgnoreCase(form.getStatisticType()) &&
                (form.getAction() == null)) {
            return mapping.findForward("allAdvertisersStatistic");
        }

        try {
            startPeriod.setTime(sdf.parse(form.getStartDate()));
            endPeriod.setTime(sdf.parse(form.getEndDate()));
        } catch (java.text.ParseException e) {
            logger.error("while parse calendar value ", e);
        }

        if ("advetiserByCampaigns".equalsIgnoreCase(form.getType())) {
            if (ADVERTISERS.equalsIgnoreCase(form.getAction())) {
                form.setStatisticCollection(AdditionalAdvertiserStatistic.getAdvertisingReportByCampaigns(
                        null, startPeriod, endPeriod, request));

                return mapping.findForward("byCampaignsTail");
            }

            form.setStatisticCollection(AdditionalAdvertiserStatistic.getAdvertisingReportByCampaigns(
                    user.getId(), startPeriod, endPeriod, request));

            return mapping.findForward("byCampaignsTail");
        }

        if ("advertiserDailyReport".equalsIgnoreCase(form.getType())) {
            if (ADVERTISERS.equalsIgnoreCase(form.getAction())) {
                form.setTableHeader(Msg.fetch("daily.advertising.report",
                        request));
                form.setStatisticCollection(AdditionalAdvertiserStatistic.getAdvertisingReportByDates(
                        "%", StatisticInterface.ADVERTISER_ID_COLUMN,
                        startPeriod, endPeriod, request));

                return mapping.findForward("byDayTail");
            }

            if (CAMPAIGN.equalsIgnoreCase(form.getAction())) {
                form.setTableHeader(Msg.fetch(
                        "daily.advertising.report.campaign", request) +
                    form.getCampainId());
                form.setStatisticCollection(AdditionalAdvertiserStatistic.getAdvertisingReportByDates(
                        form.getCampainId(),
                        StatisticInterface.CAMPAIGN_ID_COLUMN, startPeriod,
                        endPeriod, request));

                return mapping.findForward("byDayTail");
            }

            if (BANNER.equalsIgnoreCase(form.getAction())) {
                form.setTableHeader(Msg.fetch(
                        "daily.advertising.report.banner", request) +
                    form.getBannerId());
                form.setStatisticCollection(AdditionalAdvertiserStatistic.getAdvertisingReportByDates(
                        form.getBannerId(),
                        StatisticInterface.BANNER_ID_COLUMN, startPeriod,
                        endPeriod, request));

                return mapping.findForward("byDayTail");
            }

            form.setTableHeader(Msg.fetch("daily.advertising.report", request));
            form.setStatisticCollection(AdditionalAdvertiserStatistic.getAdvertisingReportByDates(
                    user.getId().toString(),
                    StatisticInterface.ADVERTISER_ID_COLUMN, startPeriod,
                    endPeriod, request));

            return mapping.findForward("byDayTail");
        }

        if ("advertiserBySitesReport".equalsIgnoreCase(form.getType())) {
            if (ADVERTISERS.equalsIgnoreCase(form.getAction())) {
                form.setTableHeader(Msg.fetch("where.displayed.report", request));
                form.setStatisticCollection(AdditionalAdvertiserStatistic.getAdvertisingReportBySites(
                        "%", StatisticInterface.ADVERTISER_ID_COLUMN,
                        startPeriod, endPeriod, request));

                return mapping.findForward("bySitesTail");
            }

            if (CAMPAIGN.equalsIgnoreCase(form.getAction())) {
                form.setTableHeader(Msg.fetch(
                        "where.displayed.report.campaign", request) +
                    form.getCampainId());
                form.setStatisticCollection(AdditionalAdvertiserStatistic.getAdvertisingReportBySites(
                        form.getCampainId(),
                        StatisticInterface.CAMPAIGN_ID_COLUMN, startPeriod,
                        endPeriod, request));

                return mapping.findForward("bySitesTail");
            }

            if (BANNER.equalsIgnoreCase(form.getAction())) {
                form.setTableHeader(Msg.fetch("where.displayed.report.banner",
                        request) + form.getBannerId());
                form.setStatisticCollection(AdditionalAdvertiserStatistic.getAdvertisingReportBySites(
                        form.getBannerId(),
                        StatisticInterface.BANNER_ID_COLUMN, startPeriod,
                        endPeriod, request));

                return mapping.findForward("bySitesTail");
            }

            form.setTableHeader(Msg.fetch("where.displayed.report", request));
            form.setStatisticCollection(AdditionalAdvertiserStatistic.getAdvertisingReportBySites(
                    user.getId().toString(),
                    StatisticInterface.ADVERTISER_ID_COLUMN, startPeriod,
                    endPeriod, request));

            return mapping.findForward("bySitesTail");
        }

        if ("advertiserByBannersReport".equalsIgnoreCase(form.getType())) {
            if (ADVERTISERS.equalsIgnoreCase(form.getAction())) {
                form.setTableHeader(Msg.fetch("advertising.report.by.banners",
                        request));
                form.setStatisticCollection(AdditionalAdvertiserStatistic.getAdvertisingReportByBanner(
                        "%", StatisticInterface.ADVERTISER_ID_COLUMN,
                        startPeriod, endPeriod, request));

                return mapping.findForward("byBannersTail");
            }

            if (CAMPAIGN.equalsIgnoreCase(form.getAction())) {
                form.setTableHeader(Msg.fetch(
                        "advertising.report.by.banners.campaign", request) +
                    form.getCampainId());
                form.setStatisticCollection(AdditionalAdvertiserStatistic.getAdvertisingReportByBanner(
                        form.getCampainId(),
                        StatisticInterface.CAMPAIGN_ID_COLUMN, startPeriod,
                        endPeriod, request));

                return mapping.findForward("byBannersTail");
            }

            form.setTableHeader(Msg.fetch("advertising.report.by.banners",
                    request));
            form.setStatisticCollection(AdditionalAdvertiserStatistic.getAdvertisingReportByBanner(
                    user.getId().toString(),
                    StatisticInterface.ADVERTISER_ID_COLUMN, startPeriod,
                    endPeriod, request));

            return mapping.findForward("byBannersTail");
        }

        if ("advertiserByCategorysReport".equalsIgnoreCase(form.getType())) {
            if (ADVERTISERS.equalsIgnoreCase(form.getAction())) {
                form.setTableHeader(Msg.fetch(
                        "advertising.report.by.categorys", request));
                form.setStatisticCollection(AdditionalAdvertiserStatistic.getAdvertisingReportByCategorys(
                        "%", StatisticInterface.ADVERTISER_ID_COLUMN,
                        startPeriod, endPeriod, request));

                return mapping.findForward("byCategorysTail");
            }

            if (CAMPAIGN.equalsIgnoreCase(form.getAction())) {
                form.setTableHeader(Msg.fetch(
                        "advertising.report.by.categorys.campaign", request) +
                    form.getCampainId());
                form.setStatisticCollection(AdditionalAdvertiserStatistic.getAdvertisingReportByCategorys(
                        form.getCampainId(),
                        StatisticInterface.CAMPAIGN_ID_COLUMN, startPeriod,
                        endPeriod, request));

                return mapping.findForward("byCategorysTail");
            }

            if (BANNER.equalsIgnoreCase(form.getAction())) {
                form.setTableHeader(Msg.fetch(
                        "advertising.report.by.categorys.banner", request) +
                    form.getBannerId());
                form.setStatisticCollection(AdditionalAdvertiserStatistic.getAdvertisingReportByCategorys(
                        form.getBannerId(),
                        StatisticInterface.BANNER_ID_COLUMN, startPeriod,
                        endPeriod, request));

                return mapping.findForward("byCategorysTail");
            }

            form.setTableHeader(Msg.fetch("advertising.report.by.categorys",
                    request));
            form.setStatisticCollection(AdditionalAdvertiserStatistic.getAdvertisingReportByCategorys(
                    user.getId().toString(),
                    StatisticInterface.ADVERTISER_ID_COLUMN, startPeriod,
                    endPeriod, request));

            return mapping.findForward("byCategorysTail");
        }

        if ("advertiserByDayOfTheWeekReport".equalsIgnoreCase(form.getType())) {
            if (ADVERTISERS.equalsIgnoreCase(form.getAction())) {
                form.setTableHeader(Msg.fetch(
                        "advertising.report.by.dayofweek", request));
                form.setStatisticCollection(AdditionalAdvertiserStatistic.getAdvertisingReportByDayOfTheWeek(
                        "%", StatisticInterface.ADVERTISER_ID_COLUMN,
                        startPeriod, endPeriod, request));

                return mapping.findForward("byDayOfTheWeekTail");
            }

            if (CAMPAIGN.equalsIgnoreCase(form.getAction())) {
                form.setTableHeader(Msg.fetch(
                        "advertising.report.by.dayofweek.campaign", request) +
                    form.getCampainId());
                form.setStatisticCollection(AdditionalAdvertiserStatistic.getAdvertisingReportByDayOfTheWeek(
                        form.getCampainId(),
                        StatisticInterface.CAMPAIGN_ID_COLUMN, startPeriod,
                        endPeriod, request));

                return mapping.findForward("byDayOfTheWeekTail");
            }

            if (BANNER.equalsIgnoreCase(form.getAction())) {
                form.setTableHeader(Msg.fetch(
                        "advertising.report.by.dayofweek.banner", request) +
                    form.getBannerId());
                form.setStatisticCollection(AdditionalAdvertiserStatistic.getAdvertisingReportByDayOfTheWeek(
                        form.getBannerId(),
                        StatisticInterface.BANNER_ID_COLUMN, startPeriod,
                        endPeriod, request));

                return mapping.findForward("byDayOfTheWeekTail");
            }

            form.setTableHeader(Msg.fetch("advertising.report.by.dayofweek",
                    request));
            form.setStatisticCollection(AdditionalAdvertiserStatistic.getAdvertisingReportByDayOfTheWeek(
                    user.getId().toString(),
                    StatisticInterface.ADVERTISER_ID_COLUMN, startPeriod,
                    endPeriod, request));

            return mapping.findForward("byDayOfTheWeekTail");
        }

        if ("advertiserByHourReport".equalsIgnoreCase(form.getType())) {
            if (ADVERTISERS.equalsIgnoreCase(form.getAction())) {
                form.setTableHeader(Msg.fetch("advertising.report.by.hour",
                        request));
                form.setStatisticCollection(AdditionalAdvertiserStatistic.getAdvertisingReportByHour(
                        "%", StatisticInterface.ADVERTISER_ID_COLUMN,
                        startPeriod, endPeriod, request));

                return mapping.findForward("byHourTail");
            }

            if (CAMPAIGN.equalsIgnoreCase(form.getAction())) {
                form.setTableHeader(Msg.fetch(
                        "advertising.report.by.hour.campaign", request) +
                    form.getCampainId());
                form.setStatisticCollection(AdditionalAdvertiserStatistic.getAdvertisingReportByHour(
                        form.getCampainId(),
                        StatisticInterface.CAMPAIGN_ID_COLUMN, startPeriod,
                        endPeriod, request));

                return mapping.findForward("byHourTail");
            }

            if (BANNER.equalsIgnoreCase(form.getAction())) {
                form.setTableHeader(Msg.fetch(
                        "advertising.report.by.hour.bannerr", request) +
                    form.getBannerId());
                form.setStatisticCollection(AdditionalAdvertiserStatistic.getAdvertisingReportByHour(
                        form.getBannerId(),
                        StatisticInterface.BANNER_ID_COLUMN, startPeriod,
                        endPeriod, request));

                return mapping.findForward("byHourTail");
            }

            form.setTableHeader(Msg.fetch("advertising.report.by.hour", request));
            form.setStatisticCollection(AdditionalAdvertiserStatistic.getAdvertisingReportByHour(
                    user.getId().toString(),
                    StatisticInterface.ADVERTISER_ID_COLUMN, startPeriod,
                    endPeriod, request));

            return mapping.findForward("byHourTail");
        }

        if ("advertiserByCountryReport".equalsIgnoreCase(form.getType())) {
            if (ADVERTISERS.equalsIgnoreCase(form.getAction())) {
                form.setTableHeader(Msg.fetch("advertising.report.by.country",
                        request));
                form.setStatisticCollection(AdditionalAdvertiserStatistic.getAdvertisingReportByCountry(
                        "%", StatisticInterface.ADVERTISER_ID_COLUMN,
                        startPeriod, endPeriod, request));

                return mapping.findForward("byCoutryTail");
            }

            if (CAMPAIGN.equalsIgnoreCase(form.getAction())) {
                form.setTableHeader(Msg.fetch(
                        "advertising.report.by.country.campaign", request) +
                    form.getCampainId());
                form.setStatisticCollection(AdditionalAdvertiserStatistic.getAdvertisingReportByCountry(
                        form.getCampainId(),
                        StatisticInterface.CAMPAIGN_ID_COLUMN, startPeriod,
                        endPeriod, request));

                return mapping.findForward("byCoutryTail");
            }

            if (BANNER.equalsIgnoreCase(form.getAction())) {
                form.setTableHeader(Msg.fetch(
                        "advertising.report.by.country.banner", request) +
                    form.getBannerId());
                form.setStatisticCollection(AdditionalAdvertiserStatistic.getAdvertisingReportByCountry(
                        form.getBannerId(),
                        StatisticInterface.BANNER_ID_COLUMN, startPeriod,
                        endPeriod, request));

                return mapping.findForward("byCoutryTail");
            }

            form.setTableHeader(Msg.fetch("advertising.report.by.country",
                    request));
            form.setStatisticCollection(AdditionalAdvertiserStatistic.getAdvertisingReportByCountry(
                    user.getId().toString(),
                    StatisticInterface.ADVERTISER_ID_COLUMN, startPeriod,
                    endPeriod, request));

            return mapping.findForward("byCoutryTail");
        }

        return mapping.findForward("view");
    }

    protected void checkAccessRestriction(HttpServletRequest request,
        ActionForm actionForm) throws AdsapientSecurityException {
    }
}
