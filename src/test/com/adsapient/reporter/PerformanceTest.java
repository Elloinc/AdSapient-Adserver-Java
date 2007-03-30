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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PerformanceTest {
	private String dbDriver = "com.mysql.jdbc.Driver";

	private String reporterDbUrl = "jdbc:mysql://localhost:3306/reports?relaxAutoCommit=true;autoReconnect=true";

	private String username = "reporter";

	private String password = "reporter";

	private Connection connection;

	private String tableDefinition = "create table hourlyreports_24_12_2006(hour date not null, placeid int not null, bannerid int not null, uniqueid int not null, adviews int, clicks int, leads int, sales int, primary key(hour,placeid,bannerid,uniqueid),index(hour,placeid,bannerid));";

	private int maxNumberOfPlaces = 200;

	private int maxNumberOfBanners = 1000;

	private int numberOfRecords = 100000000;

	private int numberOfUniques = 10000000;

	public static void main(String[] args) {
		PerformanceTest pt = new PerformanceTest();

		pt.select();
	}

	private void select() {
		try {
			Class.forName(dbDriver).newInstance();
			connection = DriverManager.getConnection(reporterDbUrl, username,
					password);

			Statement st = connection.createStatement();
			Random random = new Random();
			int numberOfSelects = 10000;

			for (int i = 0; i < numberOfSelects; i++) {
				long t2 = System.currentTimeMillis();
				String h = "2006-12-01";

				int pid = 12;
				int bid = random.nextInt(maxNumberOfBanners);
				int uniqueid = random.nextInt(numberOfUniques);

				String query = "SELECT * FROM hourlyreports_24_12_2006 WHERE hour='"
						+ h
						+ "' AND placeid="
						+ pid
						+ " AND bannerid="
						+ bid
						+ ";";
				ResultSet rs = st.executeQuery(query);
				long time = (System.currentTimeMillis() - t2);

				if (rs.next()) {
					System.out.println("found result for:" + bid);
				}

				System.out.println(time);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void insert() {
		try {
			Class.forName(dbDriver).newInstance();
			connection = DriverManager.getConnection(reporterDbUrl, username,
					password);

			Statement st = connection.createStatement();
			long t2 = System.currentTimeMillis();
			int count = 0;

			String cq = "SELECT COUNT(*) FROM hourlyreports_24_12_2006;";
			ResultSet rs = st.executeQuery(cq);
			rs.next();
			count = rs.getInt("count(*)");
			System.out.println(count);

			for (int i = 0; i <= numberOfRecords; i += 50000) {
				long t1 = System.currentTimeMillis();
				String testValues = getTestValues(i, i + 50000);
				System.out.println("TEST VALUES TOOK:"
						+ (System.currentTimeMillis() - t1));

				String query = "insert into hourlyreports_24_12_2006 (hour,placeid,bannerid,uniqueid,adviews,clicks,leads,sales) values "
						+ testValues + ";";

				try {
					st.execute(query);
				} catch (SQLException sqlEx) {
					System.out.println(sqlEx.getMessage());
				}

				System.out.println("GETTESTVALUES TOOK:"
						+ (System.currentTimeMillis() - t2));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private String getTestValues(int start, int nofrec) {
		StringBuffer testValue = new StringBuffer();
		Random random = new Random();
		List<String> recordsList = new ArrayList<String>();

		for (int i = start; i < nofrec; i++) {
			testValue.append("(");
			testValue.append("'2006-12-");
			testValue.append(random.nextInt(24) + 1);
			testValue.append("',");
			testValue.append(random.nextInt(maxNumberOfPlaces));
			testValue.append(",");
			testValue.append(random.nextInt(maxNumberOfBanners));
			testValue.append(",");
			testValue.append(i);
			testValue.append(",");
			testValue.append(random.nextInt(3) + 1);
			testValue.append(",");
			testValue.append(random.nextInt(3) + 1);
			testValue.append(",");
			testValue.append(random.nextInt(3) + 1);
			testValue.append(",");
			testValue.append(random.nextInt(3) + 1);
			testValue.append("),");
		}

		String s = testValue.toString();

		return s.substring(0, s.length() - 1);
	}

	private String getNextTestValue() {
		StringBuffer testValue = new StringBuffer();
		Random random = new Random();
		testValue.append("(");
		testValue.append("'2006-12-");
		testValue.append(random.nextInt(31) + 1);
		testValue.append("'");
		testValue.append(",");
		testValue.append(random.nextInt(maxNumberOfPlaces));
		testValue.append(",");
		testValue.append(random.nextInt(maxNumberOfBanners));
		testValue.append(",");
		testValue.append(random.nextInt(numberOfUniques));
		testValue.append(",");
		testValue.append(random.nextInt(3) + 1);
		testValue.append(",");
		testValue.append(random.nextInt(3) + 1);
		testValue.append(",");
		testValue.append(random.nextInt(3) + 1);
		testValue.append(",");
		testValue.append(random.nextInt(3) + 1);
		testValue.append(")");

		return testValue.toString();
	}
}
