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
package com.adsapient.util.sql;

import com.adsapient.util.plugin.ApplicationInstaller;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Collection;
import java.util.Iterator;

public class ConnectionManager {
	static Logger logger = Logger.getLogger(ConnectionManager.class);

	public static final String HIBERNATE_URL = "db.url";

	public static final String HIBERNATE_USERNAME = "db.username";

	public static final String HIBERNATE_PASSWORD = "db.password";

	public static final String HIBERNATE_DRIVER = "hibernate.connection.driver_class";

	public static final String HIBERNATE_DIALECT = "hibernate.dialect";

	public static boolean test(Collection sqlQuerry) throws Exception {
		InputStream stream = ApplicationInstaller.class.getClassLoader()
				.getResourceAsStream("adsapient.properties");
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(stream));
		String str;
		String url = "jdbc:mysql:";
		String userName = "banner";
		String password = "banner123";
		String driver = "com.mysql.jdbc.Driver";
		String dialect = "org.hibernate.dialect.MySQLDialect";

		while ((str = reader.readLine()) != null) {
			if (str.startsWith(HIBERNATE_URL)) {
				url = str.substring(HIBERNATE_URL.length() + 1, str.length());

				continue;
			}

			if (str.startsWith(HIBERNATE_DRIVER)) {
				driver = str.substring(HIBERNATE_DRIVER.length() + 1, str
						.length());

				continue;
			}

			if (str.startsWith(HIBERNATE_USERNAME)) {
				userName = str.substring(HIBERNATE_USERNAME.length() + 1, str
						.length());

				continue;
			}

			if (str.startsWith(HIBERNATE_PASSWORD)) {
				password = str.substring(HIBERNATE_PASSWORD.length() + 1, str
						.length());

				continue;
			}

			if (str.startsWith(HIBERNATE_DIALECT)) {
				dialect = str.substring(HIBERNATE_DIALECT.length() + 1, str
						.length());

				continue;
			}
		}

		Iterator querrysIterator = sqlQuerry.iterator();
		Connection con;
		String createString;

		Statement stmt;

		Class.forName(driver);

		String qweryString = "";

		while (querrysIterator.hasNext()) {
			try {
				qweryString = (String) querrysIterator.next();
				con = DriverManager.getConnection(url, userName, password);
				stmt = con.createStatement();

				stmt.executeUpdate(qweryString);
				stmt.close();
				con.close();
			} catch (SQLException ex) {
				logger.error("SQLException: " + ex.getMessage());
				logger.info("the querry is " + qweryString);
			}
		}

		logger.info("finishing sql insert");

		return true;
	}
}
