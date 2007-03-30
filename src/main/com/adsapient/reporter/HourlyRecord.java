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
package com.adsapient.reporter;

import com.adsapient.adserver.beans.EventObject;
import com.adsapient.api.IMappable;
import com.adsapient.shared.AdsapientConstants;

public class HourlyRecord implements IMappable {
    private Integer id;
    private int hour;
    private int placeid;
    private int bannerid;
    private int views;
    private int clicks;
    private int leads;
    private int sales;
    private double earned;
    private double spent;

    public HourlyRecord(EventObject eo) {
        this.hour = (int) ((int) eo.getTs() / ReportsPropagator.MILLISECONDS_IN_DAY);
        this.placeid = eo.getPlaceid();
        this.bannerid = eo.getBannerid();
        this.views = (eo.getEventid() == AdsapientConstants.ADVIEW) ? 1 : 0;
        this.clicks = (eo.getEventid() == AdsapientConstants.CLICK) ? 1 : 0;
        this.leads = (eo.getEventid() == AdsapientConstants.LEAD) ? 1 : 0;
        this.sales = (eo.getEventid() == AdsapientConstants.SALE) ? 1 : 0;
        this.earned = eo.getEarned();
        this.spent = eo.getSpent();
    }

    public void merge(EventObject eo) {
        this.views = (eo.getEventid() == AdsapientConstants.ADVIEW) ? (views + 1) : views;
        this.clicks = (eo.getEventid() == AdsapientConstants.CLICK) ? (clicks + 1) : clicks;
        this.leads = (eo.getEventid() == AdsapientConstants.LEAD) ? (leads + 1) : leads;
        this.sales = (eo.getEventid() == AdsapientConstants.SALE) ? (sales + 1) : sales;
        this.earned = earned + eo.getEarned();
        this.spent = spent + eo.getSpent();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getPlaceid() {
        return placeid;
    }

    public void setPlaceid(int placeid) {
        this.placeid = placeid;
    }

    public int getBannerid() {
        return bannerid;
    }

    public void setBannerid(int bannerid) {
        this.bannerid = bannerid;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public int getLeads() {
        return leads;
    }

    public void setLeads(int leads) {
        this.leads = leads;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public double getEarned() {
        return earned;
    }

    public void setEarned(double earned) {
        this.earned = earned;
    }

    public double getSpent() {
        return spent;
    }

    public void setSpent(double spent) {
        this.spent = spent;
    }

}
