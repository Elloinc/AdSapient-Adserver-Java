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
package com.adsapient.adserver.reporter;

import org.apache.log4j.Logger;

import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;

import org.quartz.impl.StdSchedulerFactory;

import org.quartz.xml.JobSchedulingDataProcessor;

import java.util.Map;
import java.util.Properties;

public class ReporterScheduler {
	private static Logger logger = Logger.getLogger(ReporterScheduler.class);

	private Properties quartzProperties;

	private String pathToJobs;

	private Map<String, String> quartzPropertiesMap;

	public void setup() {
		try {
			SchedulerFactory schedFact = new StdSchedulerFactory();
			((StdSchedulerFactory) schedFact).initialize(quartzProperties);

			Scheduler sched = schedFact.getScheduler();

			JobSchedulingDataProcessor xmlProcessor = new JobSchedulingDataProcessor();
			xmlProcessor.processFileAndScheduleJobs(pathToJobs, sched, true);

			sched.start();
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
	}

	public String getPathToJobs() {
		return pathToJobs;
	}

	public void setPathToJobs(String pathToJobs) {
		this.pathToJobs = pathToJobs;
	}

	public Properties getQuartzProperties() {
		return quartzProperties;
	}

	public void setQuartzProperties(Properties quartzProperties) {
		this.quartzProperties = quartzProperties;
	}

	public Map<String, String> getQuartzPropertiesMap() {
		return quartzPropertiesMap;
	}

	public void setQuartzPropertiesMap(Map<String, String> quartzPropertiesMap) {
		this.quartzPropertiesMap = quartzPropertiesMap;
	}
}
