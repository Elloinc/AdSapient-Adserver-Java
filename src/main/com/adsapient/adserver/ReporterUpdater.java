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

import com.adsapient.shared.api.entity.IMappable;
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

	private String tablePrefix = "hourlyreports";

	private BeanService beanService;

	private Connection connection;

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

	private void restoreTotalsReporterModel() {
		try {
			Class.forName(dbDriver).newInstance();
			connection = DriverManager.getConnection(guiDbUrl, guiUsername,
					guiPassword);

			Statement st = connection.createStatement();

			String query = "SELECT * FROM totalsreports;";
			logger.debug(query);

			ResultSet rs = st.executeQuery(query);

			if (totalsReporterModel.getEntityObjects() == null) {
				totalsReporterModel
						.setEntityObjects(new HashMap<String, TotalsReport>());
			}

			while (rs.next()) {
				TotalsReport totalsReport = readTotalsReport(rs);
				String key = totalsReport.getEntityid()
						+ totalsReport.getEntityclass();
				totalsReporterModel.getEntityObjects().put(key, totalsReport);
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.error(e.getMessage());
				}
			}
		}
	}

	private PreparedStatement fillTotalsInsertPreparedStatement(
			TotalsReport totalsReport, PreparedStatement ps) {
		try {
			ps.setInt(1, totalsReport.getEntityid());
			ps.setString(2, totalsReport.getEntityclass());
			ps.setInt(3, totalsReport.getAdviews());
			ps.setInt(4, totalsReport.getClicks());
			ps.setInt(5, totalsReport.getLeads());
			ps.setInt(6, totalsReport.getSales());
			ps.setDouble(7, totalsReport.getEarnedspent());

			byte[] uniquesBytes = beanService.serialize(totalsReport
					.getUniques());
			ps.setBinaryStream(8, new ByteArrayInputStream(uniquesBytes),
					uniquesBytes.length);
		} catch (SQLException ex) {
			logger.error(ex.getMessage());
		}

		return ps;
	}

	private PreparedStatement fillTotalsUpdatePreparedStatement(
			TotalsReport totalsReport, PreparedStatement ps) {
		try {
			ps.setInt(1, totalsReport.getEntityid());
			ps.setString(2, totalsReport.getEntityclass());
			ps.setInt(3, totalsReport.getAdviews());
			ps.setInt(4, totalsReport.getClicks());
			ps.setInt(5, totalsReport.getLeads());
			ps.setInt(6, totalsReport.getSales());
			ps.setDouble(7, totalsReport.getEarnedspent());

			byte[] uniquesBytes = beanService.serialize(totalsReport
					.getUniques());
			ps.setBinaryStream(8, new ByteArrayInputStream(uniquesBytes),
					uniquesBytes.length);
		} catch (SQLException ex) {
			logger.error(ex.getMessage());
		}

		return ps;
	}

	private PreparedStatement fillInsertPreparedStatement(
			ReporterEntityObject reporterEntityObject, PreparedStatement ps) {
		try {
			ps.setInt(1, reporterEntityObject.getEntityid());
			ps.setInt(2, reporterEntityObject.getHourOfMonth());
			ps.setString(3, reporterEntityObject.getEntityclass());
			ps.setInt(4, reporterEntityObject.getAdviews());
			ps.setInt(5, reporterEntityObject.getClicks());
			ps.setInt(6, reporterEntityObject.getLeads());
			ps.setInt(7, reporterEntityObject.getSales());
			ps.setDouble(8, reporterEntityObject.getEarnedspent());

			byte[] uniquesBytes = beanService.serialize(reporterEntityObject
					.getUniques());
			ps.setBinaryStream(9, new ByteArrayInputStream(uniquesBytes),
					uniquesBytes.length);

			byte[] whatwheredisplayedBytes = beanService
					.serialize(reporterEntityObject.getWhatwheredisplayed());
			ps.setBinaryStream(10, new ByteArrayInputStream(
					whatwheredisplayedBytes), whatwheredisplayedBytes.length);

			byte[] geolocationsBytes = beanService
					.serialize(reporterEntityObject.getGeolocations());
			ps.setBinaryStream(11, new ByteArrayInputStream(geolocationsBytes),
					geolocationsBytes.length);

			byte[] systempropertiesBytes = beanService
					.serialize(reporterEntityObject.getSystemproperties());
			ps.setBinaryStream(12, new ByteArrayInputStream(
					systempropertiesBytes), systempropertiesBytes.length);
		} catch (SQLException ex) {
			logger.error(ex.getMessage());
		}

		return ps;
	}

	private PreparedStatement fillUpdatePreparedStatement(
			ReporterEntityObject reporterEntityObject, PreparedStatement ps) {
		try {
			ps.setInt(1, reporterEntityObject.getAdviews());
			ps.setInt(2, reporterEntityObject.getClicks());
			ps.setInt(3, reporterEntityObject.getLeads());
			ps.setInt(4, reporterEntityObject.getSales());
			ps.setDouble(5, reporterEntityObject.getEarnedspent());

			byte[] uniquesBytes = beanService.serialize(reporterEntityObject
					.getUniques());
			ps.setBinaryStream(6, new ByteArrayInputStream(uniquesBytes),
					uniquesBytes.length);

			byte[] whatwheredisplayedBytes = beanService
					.serialize(reporterEntityObject.getWhatwheredisplayed());
			ps.setBinaryStream(7, new ByteArrayInputStream(
					whatwheredisplayedBytes), whatwheredisplayedBytes.length);

			byte[] geolocationsBytes = beanService
					.serialize(reporterEntityObject.getGeolocations());
			ps.setBinaryStream(8, new ByteArrayInputStream(geolocationsBytes),
					geolocationsBytes.length);

			byte[] systempropertiesBytes = beanService
					.serialize(reporterEntityObject.getSystemproperties());
			ps.setBinaryStream(9, new ByteArrayInputStream(
					systempropertiesBytes), systempropertiesBytes.length);
			ps.setInt(10, reporterEntityObject.getEntityid());
			ps.setInt(11, reporterEntityObject.getHourOfMonth());
			ps.setString(12, reporterEntityObject.getEntityclass());
		} catch (SQLException ex) {
			logger.error(ex.getMessage());
		}

		return ps;
	}

	private String getTableName() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());

		String tableName = tablePrefix + "_" + c.get(Calendar.MONTH) + "_"
				+ c.get(Calendar.YEAR);

		return tableName;
	}

	private String getHourOfMonth() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());

		String hourOfMonth = String.valueOf(((c.get(Calendar.DATE) - 1) * 24)
				+ c.get(Calendar.HOUR_OF_DAY));

		return hourOfMonth;
	}

	private ReporterEntityObject readReporterEntityObject(ResultSet rs)
			throws SQLException {
		ReporterEntityObject reporterEntityObject = new ReporterEntityObject();
		reporterEntityObject.setEntityid(rs.getInt("entityid"));
		reporterEntityObject.setEntityclass(rs.getString("entityclass"));
		reporterEntityObject.setHourOfMonth(rs.getInt("hourofthemonth"));
		reporterEntityObject.setAdviews(rs.getInt("adviews"));
		reporterEntityObject.setClicks(rs.getInt("clicks"));
		reporterEntityObject.setLeads(rs.getInt("leads"));
		reporterEntityObject.setSales(rs.getInt("sales"));
		reporterEntityObject.setEarnedspent(rs.getDouble("earnedspent"));

		reporterEntityObject
				.setWhatwheredisplayed((Map<Integer, ReporterEventsObject>) beanService
						.deserialize(rs.getBlob("whatwheredisplayed")
								.getBytes(
										1,
										(int) rs.getBlob("whatwheredisplayed")
												.length())));
		reporterEntityObject
				.setGeolocations((Map<Integer, ReporterEventsObject>) beanService
						.deserialize(rs.getBlob("geolocations").getBytes(1,
								(int) rs.getBlob("geolocations").length())));
		reporterEntityObject
				.setSystemproperties((Map<Integer, ReporterEventsObject>) beanService
						.deserialize(rs.getBlob("systemproperties").getBytes(1,
								(int) rs.getBlob("geolocations").length())));

		return reporterEntityObject;
	}

	private TotalsReport readTotalsReport(ResultSet rs) throws SQLException {
		TotalsReport totalsReport = new TotalsReport();
		totalsReport.setId(rs.getInt("id"));
		totalsReport.setEntityid(rs.getInt("entityid"));
		totalsReport.setEntityclass(rs.getString("entityclass"));
		totalsReport.setAdviews(rs.getInt("adviews"));
		totalsReport.setClicks(rs.getInt("clicks"));
		totalsReport.setLeads(rs.getInt("leads"));
		totalsReport.setSales(rs.getInt("sales"));
		totalsReport.setEarnedspent(rs.getDouble("earnedspent"));

		return totalsReport;
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
