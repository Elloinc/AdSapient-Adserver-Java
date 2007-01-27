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
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;

public class DBOperator {
	private static int MAXLISTSIZE;

	private static int DBJOBSCHEDULE;

	private static Logger logger = Logger.getLogger(DBOperator.class);

	private static DBOperator instance;

	Connection conn = null;

	ExecutorService executorService;

	Runnable dbInsertTask;

	List queryList = new ArrayList();

	private DBOperator() {
		Timer timer;
		TimerTask task;
		timer = new Timer();
		task = new TimeDbInsert();

		try {
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
	}

	private static DBOperator getInstance() {
		if (instance == null) {
			instance = new DBOperator();
		}

		return instance;
	}

	public static void executeUpdate(String query) {
		getInstance().executeUpdateInternal(query);
	}

	private synchronized void executeUpdateInternal(String query) {
		queryList.add(query);
	}

	private synchronized void dbInsert() {
		try {
			Statement s = conn.createStatement();

			for (int i = queryList.size() - 1; i >= 0; i--) {
				s.executeUpdate(queryList.get(i).toString());
				queryList.remove(i);
			}

			s.close();
		} catch (Exception ex) {
			logger.warn("Error while executing query via JDBC opertor"
					+ ex.toString());
		}
	}

	class TimeDbInsert extends TimerTask {
		public void run() {
			if (queryList.size() > 0) {
				executorService.execute(dbInsertTask);
			}
		}
	}
}
