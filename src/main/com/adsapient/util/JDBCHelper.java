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
package com.adsapient.util;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class JDBCHelper {
	private static Logger logger = Logger.getLogger(DBOperator.class);

	private static Connection conn = null;

	private String url;

	private String driver;

	private String username;

	private String password;

	private String database;

	public void loadConnection() {
		try {
			Class.forName(this.driver).newInstance();
			conn = DriverManager.getConnection(this.url, this.username,
					this.password);
		} catch (Exception ex) {
			logger.info(ex.getMessage());
		}
	}

	public Connection getConnection() {
		if (conn == null) {
			loadConnection();
		}

		return conn;
	}

	public synchronized void executeUpdate(PreparedStatement ps) {
		try {
			ps.executeUpdate();
			ps.close();
		} catch (Exception ex) {
			logger.warn("Error while executing query via JDBC opertor"
					+ ex.toString());
		}
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

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
