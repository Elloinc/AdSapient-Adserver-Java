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
package com.adsapient.shared.jobs;

import com.adsapient.adserver.AdserverServlet;
import com.adsapient.adserver.ModelUpdater;

import com.adsapient.api_impl.advertizer.CampainImpl;

import com.adsapient.shared.api.entity.IMappable;

import com.adsapient.util.DateUtil;

import org.apache.log4j.Logger;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;
import java.util.Map;
import java.util.Set;

public class ModelUpdaterJob implements Job {
	private static Logger logger = Logger.getLogger(ModelUpdaterJob.class);

	private ModelUpdater modelUpdater;

	public void init() {
		if ((modelUpdater == null) && (AdserverServlet.appContext != null)) {
			modelUpdater = (ModelUpdater) AdserverServlet.appContext
					.getBean("modelUpdater");
		}
	}

	public void execute(JobExecutionContext jobExecutionContext)
			throws JobExecutionException {
		long t1 = System.currentTimeMillis();
		logger.debug("Running ModelUpdaterJob");
		init();

		Date today = new Date();

		if (modelUpdater == null) {
			return;
		}

		Map<Integer, IMappable> campaigns = modelUpdater.getAdserverModel()
				.getCampaignsMap();
		Date endDate = null;
		Set<Integer> keys = campaigns.keySet();

		for (Integer i : keys) {
			CampainImpl camp = (CampainImpl) campaigns.get(i);

			try {
				endDate = new Date(DateUtil.parseDateDDMMYYYY(
						camp.getEndDate(), false));
			} catch (Exception e) {
				logger.error("No end date or incorrect end date of campain "
						+ camp.getId());

				continue;
			}

			if (today.compareTo(endDate) > 0) {
				camp.setStateId(2);
				modelUpdater.getHibernateEntityDao().saveOrUpdate(camp);
				modelUpdater.update(camp.getClass(), i, null);
			}
		}

		logger.debug("Running ModelupdaterJob took:"
				+ (System.currentTimeMillis() - t1));
	}
}
