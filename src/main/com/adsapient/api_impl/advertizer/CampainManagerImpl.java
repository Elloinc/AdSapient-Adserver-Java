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
package com.adsapient.api_impl.advertizer;

import com.adsapient.api_impl.share.UserDefineCampainStates;
import com.adsapient.api_impl.usermanagment.UserImpl;

import com.adsapient.util.AdSapientHibernateService;
import com.adsapient.util.DateUtil;
import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.admin.ConfigurationConstants;

import org.apache.log4j.Logger;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import org.hibernate.criterion.Expression;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class CampainManagerImpl {
	static private Logger logger = Logger.getLogger(CampainManagerImpl.class);

	public static synchronized void campainsStateHandler() {
		logger.info(" begin campaigns StateHandler");

		long start = System.currentTimeMillis();
		Collection allUsersCampainsCollection = new ArrayList();
		Session session = null;
		Transaction tr = null;

		try {
			session = AdSapientHibernateService.openSession();

			tr = session.beginTransaction();

			allUsersCampainsCollection = session.createCriteria(
					CampainImpl.class).add(
					Expression.not(Expression.like("stateId", new Integer(
							ConfigurationConstants.DEFAULT_CAMPAIN_STATE_ID))))
					.list();

			tr.commit();
		} catch (HibernateException ex) {
			logger
					.error("This exception throw in -- campain state Handler",
							ex);
		} finally {
			AdSapientHibernateService.closeSession(session, tr);
		}

		if (allUsersCampainsCollection != null) {
			Iterator campainsIterator = allUsersCampainsCollection.iterator();

			while (campainsIterator.hasNext()) {
				CampainImpl campain = (CampainImpl) campainsIterator.next();

				if (ConfigurationConstants.DEFAULT_NOT_VERIFY_STATE_ID == campain
						.getStateId()) {
					continue;
				}

				if (!DateUtil.isCampainPeriodActive(campain.getStartDate(),
						campain.getEndDate())) {
					updateCampainState(
							campain,
							ConfigurationConstants.DEFAULT_STOPPED_CAMPAIN_STATE_ID);

					continue;
				}

				if (UserDefineCampainStates.PAUSED.equalsIgnoreCase(campain
						.getUserDefineCampainStateId())
						|| UserDefineCampainStates.COMPLETED
								.equalsIgnoreCase(campain
										.getUserDefineCampainStateId())) {
					updateCampainState(
							campain,
							ConfigurationConstants.DEFAULT_STOPPED_CAMPAIN_STATE_ID);

					continue;
				}

				if (ConfigurationConstants.DEFAULT_ACTIVE_CAMPAIN_STATE_ID != campain
						.getStateId()) {
					updateCampainState(
							campain,
							ConfigurationConstants.DEFAULT_ACTIVE_CAMPAIN_STATE_ID);
				}
			}
		}

		logger.info("finish campain state handler.Operation time is "
				+ (System.currentTimeMillis() - start));
	}

	public void removeAllUsersCampain(UserImpl user) {
		Collection campainsCollection = MyHibernateUtil.viewWithCriteria(
				CampainImpl.class, "userId", user.getId(), "campainId");

		if ((campainsCollection != null) && (!campainsCollection.isEmpty())) {
			Iterator campainsIterator = campainsCollection.iterator();

			while (campainsIterator.hasNext()) {
				CampainImpl campain = (CampainImpl) campainsIterator.next();
				campain.delete(null, null);
			}
		}

		MyHibernateUtil.removeWithCriteria(BannerImpl.class, "userId", user
				.getId());

		BannerUploadUtil.removeAllUsersFile(user);
	}

	public Collection viewCampains(Integer userId) {
		Collection coll = new ArrayList();

		Session session = null;
		Transaction tr = null;

		try {
			session = AdSapientHibernateService.openSession();
			tr = session.beginTransaction();

			coll = session.createCriteria(CampainImpl.class).add(
					Expression.like("userId", userId)).add(
					Expression.not(Expression.like("stateId", new Integer(
							ConfigurationConstants.DEFAULT_CAMPAIN_STATE_ID))))
					.list();

			tr.commit();
		} catch (HibernateException ex) {
			logger.error("This exception throw in -- vew campains ", ex);
			logger.error(ex.getLocalizedMessage());
		} finally {
			AdSapientHibernateService.closeSession(session, tr);
		}

		return coll;
	}

	public Collection checkCampains(String campaignName) {
		Collection coll = new ArrayList();

		Session session = null;
		Transaction tr = null;

		try {
			session = AdSapientHibernateService.openSession();
			tr = session.beginTransaction();

			coll = session.createCriteria(CampainImpl.class).add(
					Expression.like("name", campaignName)).list();
			tr.commit();
		} catch (HibernateException ex) {
			logger.error("This exception throw in -- vew campains ", ex);
			logger.error(ex.getLocalizedMessage());
		} finally {
			AdSapientHibernateService.closeSession(session, tr);
		}

		return coll;
	}

	public static final void updateCampainState(CampainImpl campain,
			int campainStateId) {
		Session session = null;
		Transaction tr = null;

		try {
			session = AdSapientHibernateService.openSession();

			tr = session.beginTransaction();

			CampainImpl editableCampain = (CampainImpl) session.load(campain
					.getClass(), campain.getCampainId());

			editableCampain.setStateId(campainStateId);

			tr.commit();
		} catch (HibernateException ex) {
			logger
					.error("This exception throw in -- campain state Handler",
							ex);
		} finally {
			AdSapientHibernateService.closeSession(session, tr);
		}
	}
}
