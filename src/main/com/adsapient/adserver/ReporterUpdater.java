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
package com.adsapient.adserver;

import com.adsapient.adserver.beans.ReporterEntityObject;
import com.adsapient.adserver.beans.ReporterEventsObject;
import com.adsapient.adserver.beans.ReporterModel;
import com.adsapient.adserver.beans.TotalsReporterModel;

import com.adsapient.api.IMappable;
import com.adsapient.shared.dao.HibernateEntityDao;
import com.adsapient.shared.mappable.TotalsReport;
import com.adsapient.shared.service.BeanService;

import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReporterUpdater {
	static Logger logger = Logger.getLogger(ReporterUpdater.class);

	private HibernateEntityDao hibernateEntityDao;

	private TotalsReporterModel totalsReporterModel;

	private ReporterModel reporterModel;

	private String reporterDbUrl;

	private String username;

	private String password;

	private String dbDriver;

	private String guiDbUrl;

	private String guiUsername;

	private String guiPassword;

	private String guiDbDriver;

	private BeanService beanService;

	public void setup() {
		List<IMappable> totalsReports = (List<IMappable>) hibernateEntityDao
				.viewAll(TotalsReport.class);
		Map<String, TotalsReport> m = totalsReporterModel.getEntityObjects();

		if (m == null) {
			m = new HashMap<String, TotalsReport>();
		}

		for (IMappable totalsReport : totalsReports) {
			TotalsReport tr = (TotalsReport) totalsReport;
			m.put(tr.getEntityid().toString() + tr.getEntityclass(), tr);
		}

		totalsReporterModel.setEntityObjects(m);
	}

	public HibernateEntityDao getHibernateEntityDao() {
		return hibernateEntityDao;
	}

	public void setHibernateEntityDao(HibernateEntityDao hibernateEntityDao) {
		this.hibernateEntityDao = hibernateEntityDao;
	}

	public TotalsReporterModel getTotalsReporterModel() {
		return totalsReporterModel;
	}

	public void setTotalsReporterModel(TotalsReporterModel totalsReporterModel) {
		this.totalsReporterModel = totalsReporterModel;
	}

	public ReporterModel getReporterModel() {
		return reporterModel;
	}

	public void setReporterModel(ReporterModel reporterModel) {
		this.reporterModel = reporterModel;
	}

	public String getReporterDbUrl() {
		return reporterDbUrl;
	}

	public void setReporterDbUrl(String reporterDbUrl) {
		this.reporterDbUrl = reporterDbUrl;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDbDriver() {
		return dbDriver;
	}

	public void setDbDriver(String dbDriver) {
		this.dbDriver = dbDriver;
	}

	public BeanService getBeanService() {
		return beanService;
	}

	public void setBeanService(BeanService beanService) {
		this.beanService = beanService;
	}

	public String getGuiDbUrl() {
		return guiDbUrl;
	}

	public void setGuiDbUrl(String guiDbUrl) {
		this.guiDbUrl = guiDbUrl;
	}

	public String getGuiUsername() {
		return guiUsername;
	}

	public void setGuiUsername(String guiUsername) {
		this.guiUsername = guiUsername;
	}

	public String getGuiPassword() {
		return guiPassword;
	}

	public void setGuiPassword(String guiPassword) {
		this.guiPassword = guiPassword;
	}

	public String getGuiDbDriver() {
		return guiDbDriver;
	}

	public void setGuiDbDriver(String guiDbDriver) {
		this.guiDbDriver = guiDbDriver;
	}
}
