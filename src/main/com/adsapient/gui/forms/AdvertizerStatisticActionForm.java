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
package com.adsapient.gui.forms;

import com.adsapient.util.admin.AdsapientConstants;

import org.apache.struts.action.ActionForm;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;


public class AdvertizerStatisticActionForm extends ActionForm {
    SimpleDateFormat sdf = new SimpleDateFormat(AdsapientConstants.DATE_FORMAT);
    private Collection bannerStatisticCollection = new ArrayList();
    private Collection compaignStatisticCollection = new ArrayList();
    private Collection statisticCollection = new ArrayList();
    private String action = null;
    private String report = null;
    private String statisticType = null;
    private String tableHeader;
    private String bannerId;
    private String campainId;
    private String endDate = sdf.format(Calendar.getInstance().getTime());
    private String period;
    private String startDate = sdf.format(getDateMonthEgo());
    private String type;
    private int bannersCount;
    private int campainsCount;
    private int clicks;
    private int impressions;

    public String getTableHeader() {
        return tableHeader;
    }

    public void setTableHeader(String tableHeader) {
        this.tableHeader = tableHeader;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setBannerId(String bannerId) {
        this.bannerId = bannerId;
    }

    public String getBannerId() {
        return bannerId;
    }

    public void setBannerStatisticCollection(
        Collection bannerStatisticCollection) {
        this.bannerStatisticCollection = bannerStatisticCollection;
    }

    public Collection getBannerStatisticCollection() {
        return bannerStatisticCollection;
    }

    public void setBannersCount(int bannersCount) {
        this.bannersCount = bannersCount;
    }

    public int getBannersCount() {
        return bannersCount;
    }

    public void setCampainId(String campainId) {
        this.campainId = campainId;
    }

    public String getCampainId() {
        return campainId;
    }

    public void setCampainsCount(int campainsCount) {
        this.campainsCount = campainsCount;
    }

    public int getCampainsCount() {
        return campainsCount;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public int getClicks() {
        return clicks;
    }

    public void setCompaignStatisticCollection(
        Collection compaignStatisticCollection) {
        this.compaignStatisticCollection = compaignStatisticCollection;
    }

    public Collection getCompaignStatisticCollection() {
        return compaignStatisticCollection;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setImpressions(int impressions) {
        this.impressions = impressions;
    }

    public int getImpressions() {
        return impressions;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPeriod() {
        return period;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStatisticCollection(Collection statisticCollection) {
        this.statisticCollection = statisticCollection;
    }

    public Collection getStatisticCollection() {
        return statisticCollection;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static Date getDateMonthEgo() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);

        return calendar.getTime();
    }

    public String getStatisticType() {
        return statisticType;
    }

    public void setStatisticType(String statisticType) {
        this.statisticType = statisticType;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }
}
