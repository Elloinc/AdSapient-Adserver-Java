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
package com.adsapient.adserver.beans;

import java.util.Date;
import java.util.Map;

public class ReporterEntityObject {
	private int entityid;

	private int adviews;

	private int clicks;

	private int leads;

	private int sales;

	private double earnedspent;

	private int hourOfMonth;

	private String entityclass;

	private Map<Date, Integer[]> uniques;

	private Map<Integer, ReporterEventsObject> whatwheredisplayed;

	private Map<Integer, ReporterEventsObject> geolocations;

	private Map<Integer, ReporterEventsObject> systemproperties;

	public String getEntityclass() {
		return entityclass;
	}

	public void setEntityclass(String entityclass) {
		this.entityclass = entityclass;
	}

	public int getEntityid() {
		return entityid;
	}

	public void setEntityid(int entityid) {
		this.entityid = entityid;
	}

	public int getAdviews() {
		return adviews;
	}

	public void setAdviews(int adviews) {
		this.adviews = adviews;
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

	public double getEarnedspent() {
		return earnedspent;
	}

	public void setEarnedspent(double earnedspent) {
		this.earnedspent = earnedspent;
	}

	public Map<Date, Integer[]> getUniques() {
		return uniques;
	}

	public void setUniques(Map<Date, Integer[]> uniques) {
		this.uniques = uniques;
	}

	public Map<Integer, ReporterEventsObject> getWhatwheredisplayed() {
		return whatwheredisplayed;
	}

	public void setWhatwheredisplayed(
			Map<Integer, ReporterEventsObject> whatwheredisplayed) {
		this.whatwheredisplayed = whatwheredisplayed;
	}

	public Map<Integer, ReporterEventsObject> getGeolocations() {
		return geolocations;
	}

	public void setGeolocations(Map<Integer, ReporterEventsObject> geolocations) {
		this.geolocations = geolocations;
	}

	public Map<Integer, ReporterEventsObject> getSystemproperties() {
		return systemproperties;
	}

	public void setSystemproperties(
			Map<Integer, ReporterEventsObject> systemproperties) {
		this.systemproperties = systemproperties;
	}

	public int getHourOfMonth() {
		return hourOfMonth;
	}

	public void setHourOfMonth(int hourOfMonth) {
		this.hourOfMonth = hourOfMonth;
	}
}
