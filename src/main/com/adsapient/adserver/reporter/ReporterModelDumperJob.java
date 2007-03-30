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

import com.adsapient.adserver.AdserverServlet;
import com.adsapient.adserver.ReporterUpdater;
import com.adsapient.adserver.beans.ReporterModel;
import com.adsapient.adserver.beans.TotalsReporterModel;

import com.adsapient.shared.dao.HibernateEntityDao;
import com.adsapient.shared.mappable.TotalsReport;

import org.apache.log4j.Logger;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class ReporterModelDumperJob implements Job {
	private static Logger logger = Logger
			.getLogger(ReporterModelDumperJob.class);

	private ReporterModel reporterModel;

	private TotalsReporterModel totalsReporterModel;

	private HibernateEntityDao hibernateEntityDao;

	private ReporterUpdater reporterUpdater;

	private ReportsDumper reportsDumper;

	private void init() {
		if ((reporterModel == null) && (AdserverServlet.appContext != null)) {
			reporterModel = (ReporterModel) AdserverServlet.appContext
					.getBean("reporterModel");
			totalsReporterModel = (TotalsReporterModel) AdserverServlet.appContext
					.getBean("totalsReporterModel");
			hibernateEntityDao = (HibernateEntityDao) AdserverServlet.appContext
					.getBean("hibernateEntityDao");
			reporterUpdater = (ReporterUpdater) AdserverServlet.appContext
					.getBean("reporterUpdater");
			reportsDumper = (ReportsDumper) AdserverServlet.appContext
					.getBean("reportsDumper");
		}
	}

	public void execute(JobExecutionContext jobExecutionContext)
			throws JobExecutionException {
		long t1 = System.currentTimeMillis();
		init();

		if ((totalsReporterModel == null)
				|| (totalsReporterModel.getEntityObjects() == null)) {
			return;
		}

		try {
			for (String key : totalsReporterModel.getEntityObjects().keySet()) {
				TotalsReport totalsReport = totalsReporterModel
						.getEntityObjects().get(key);
				totalsReport.adjustValues();
				hibernateEntityDao.saveOrUpdate(totalsReport);
			}

			reportsDumper.dumpReports();
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
	}
}
