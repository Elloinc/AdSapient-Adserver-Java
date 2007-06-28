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
package com.adsapient.shared.service;

import com.adsapient.shared.AdsapientConstants;
import com.adsapient.shared.mappable.TotalsReport;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.sql.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

public class JDBCService {
    static Logger logger = Logger.getLogger(JDBCService.class);

    public static final String HIBERNATE_URL = "db.url";

    public static final String HIBERNATE_USERNAME = "db.username";

    public static final String HIBERNATE_PASSWORD = "db.password";

    public static final String HIBERNATE_DRIVER = "db.driver";

    public static final String HIBERNATE_DIALECT = "hibernate.dialect";

    private String url;
    private String driver;
    private String userName;
    private String password;
    private String dialect;

    private String updateTotalsSQL;
    private String saveTotalsSQL;

    private Connection connection;

    private PreparedStatement updateTotalsReportPreparedStatement;
    private PreparedStatement saveTotalsReportPreparedStatement;

    public void setup() {
        try {
            InputStream stream = InstallationService.class.getClassLoader()
                    .getResourceAsStream("adsapient.properties");
            Properties props = new Properties();
            props.load(stream);

            url = props.getProperty(HIBERNATE_URL);
            driver = props.getProperty(HIBERNATE_DRIVER);
            userName = props.getProperty(HIBERNATE_USERNAME);
            password = props.getProperty(HIBERNATE_PASSWORD);
            dialect = props.getProperty(HIBERNATE_DIALECT);

            Class.forName(driver);
            connection = DriverManager.getConnection(url, userName, password);
            updateTotalsReportPreparedStatement = connection.prepareStatement(updateTotalsSQL);
            saveTotalsReportPreparedStatement = connection.prepareStatement(saveTotalsSQL);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    public boolean test(Collection sqlQuerry) throws Exception {

        Iterator querrysIterator = sqlQuerry.iterator();
        String createString;

        Statement stmt;


        String qweryString = "";

        while (querrysIterator.hasNext()) {
            try {
                qweryString = (String) querrysIterator.next();
                if (connection == null || connection.isClosed()) {
                    connection = DriverManager.getConnection(url, userName, password);
                }

                stmt = connection.createStatement();

                if (driver.equals(AdsapientConstants.HSSQLDRIVER)) {
                    qweryString = qweryString.replaceFirst("CREATE TABLE", "CREATE CACHED TABLE");
                }
                stmt.executeUpdate(qweryString);
                stmt.close();
                connection.close();
            } catch (SQLException ex) {
                logger.error("SQLException: " + ex.getMessage());
                logger.info("the querry is " + qweryString);
            }
        }

        logger.info("finishing sql insert");

        return true;
    }

    public synchronized void updateTotalsReport(TotalsReport totalsReport) {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(url, userName, password);
            }
            updateTotalsReportPreparedStatement.setInt(1, totalsReport.getEntityid());
            updateTotalsReportPreparedStatement.setString(2, totalsReport.getEntityclass());
            updateTotalsReportPreparedStatement.setInt(3, totalsReport.getAdviews());
            updateTotalsReportPreparedStatement.setInt(4, totalsReport.getClicks());
            updateTotalsReportPreparedStatement.setInt(5, totalsReport.getLeads());
            updateTotalsReportPreparedStatement.setInt(6, totalsReport.getSales());
            updateTotalsReportPreparedStatement.setDouble(7, totalsReport.getEarnedspent());
            updateTotalsReportPreparedStatement.setInt(8, totalsReport.getUniques());
            updateTotalsReportPreparedStatement.setInt(9, totalsReport.getId());

            updateTotalsReportPreparedStatement.execute();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    public String getUpdateTotalsSQL() {
        return updateTotalsSQL;
    }

    public void setUpdateTotalsSQL(String updateTotalsSQL) {
        this.updateTotalsSQL = updateTotalsSQL;
    }

    public String getSaveTotalsSQL() {
        return saveTotalsSQL;
    }

    public void setSaveTotalsSQL(String saveTotalsSQL) {
        this.saveTotalsSQL = saveTotalsSQL;
    }

    public void saveTotalsReport(TotalsReport totalsReport) {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(url, userName, password);
            }
            saveTotalsReportPreparedStatement.setInt(1, totalsReport.getEntityid());
            saveTotalsReportPreparedStatement.setString(2, totalsReport.getEntityclass());
            saveTotalsReportPreparedStatement.setInt(3, totalsReport.getAdviews());
            saveTotalsReportPreparedStatement.setInt(4, totalsReport.getClicks());
            saveTotalsReportPreparedStatement.setInt(5, totalsReport.getLeads());
            saveTotalsReportPreparedStatement.setInt(6, totalsReport.getSales());
            saveTotalsReportPreparedStatement.setDouble(7, totalsReport.getEarnedspent());
            saveTotalsReportPreparedStatement.setInt(8, totalsReport.getUniques());

            saveTotalsReportPreparedStatement.execute();

            Statement s = connection.createStatement();
            ResultSet rs = s.executeQuery("SELECT LAST_INSERT_ID();");
            if (rs.next()) {
                int id = rs.getInt(1);
                totalsReport.setId(id);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
}
