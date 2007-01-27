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
package com.adsapient.util.replication;

import com.adsapient.util.MyHibernateUtil;

import org.apache.log4j.Logger;

import java.util.Timer;
import java.util.TimerTask;

public class ReplicationManager {
	private static Logger logger = Logger.getLogger(ReplicationManager.class);

	public static void start() {
		int delay = 5000;
		int period = 10000000;
		Timer timer = new Timer();

		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				checkStatistic();
				logger.info("replication manager working ");
			}
		}, delay, period);
	}

	private static void checkStatistic() {
		logger.info("begin statistic check");

		int rowNumber = MyHibernateUtil
				.getSQLCount("select count(*) as RESULT from statistic;");
		logger.info("thearis  " + rowNumber + " in statistic table");

		logger.info("finish statisti check");
	}
}
