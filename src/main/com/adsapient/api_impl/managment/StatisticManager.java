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
package com.adsapient.api_impl.managment;

import com.adsapient.api.Banner;

import com.adsapient.api_impl.advertizer.CampainImpl;
import com.adsapient.api_impl.managment.money.MoneyManager;
import com.adsapient.api_impl.publisher.PlacesImpl;
import com.adsapient.api_impl.publisher.SiteImpl;
import com.adsapient.api_impl.statistic.ClickImpl;
import com.adsapient.api_impl.statistic.StatisticImpl;
import com.adsapient.api_impl.usermanagment.Account;
import com.adsapient.api_impl.usermanagment.UserImpl;
import com.adsapient.api_impl.usermanagment.role.RoleController;

import com.adsapient.util.MyHibernateUtil;

import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Collection;
import java.util.Iterator;

public class StatisticManager {
	static Logger logger = Logger.getLogger(StatisticManager.class);

	public static boolean remove(Object adsapientObject) {
		if (adsapientObject instanceof Banner) {
			Banner banner = (Banner) adsapientObject;
			removeFromStatistic("bannerId", banner.getBannerId(), "setBannerId");

			return true;
		}

		if (adsapientObject instanceof CampainImpl) {
			CampainImpl campain = (CampainImpl) adsapientObject;
			removeFromStatistic("campainId", campain.getCampainId(),
					"setCampainId");

			Iterator bannersIterator = campain.getBanners().iterator();

			while (bannersIterator.hasNext()) {
				CampainImpl banner = (CampainImpl) bannersIterator.next();

				StatisticManager.remove(banner);
			}

			return true;
		}

		if (adsapientObject instanceof SiteImpl) {
			SiteImpl site = (SiteImpl) adsapientObject;
			removeFromStatistic("siteId", site.getSiteId(), "setSiteId");

			Iterator placesIterator = site.getRealPlaces().iterator();

			while (placesIterator.hasNext()) {
				PlacesImpl places = (PlacesImpl) placesIterator.next();

				StatisticManager.remove(places);
			}

			return true;
		}

		if (adsapientObject instanceof PlacesImpl) {
			PlacesImpl places = (PlacesImpl) adsapientObject;
			removeFromStatistic("placeId", places.getId(), "setPlaceId");

			return true;
		}

		if (adsapientObject instanceof UserImpl) {
			UserImpl user = (UserImpl) adsapientObject;

			if (RoleController.ADVERTISER.equals(user.getRole())) {
				Iterator campaingnsCollectionIterator = MyHibernateUtil
						.viewWithCriteria(CampainImpl.class, "userId",
								user.getId(), "userId").iterator();

				while (campaingnsCollectionIterator.hasNext()) {
					StatisticManager
							.remove(campaingnsCollectionIterator.next());
				}

				removeFromStatistic("advertizerId", user.getId(),
						"setAdvertizerId");
			}

			if (RoleController.ADVERTISERPUBLISHER.equals(user.getRole())) {
				Iterator campaingnsCollectionIterator = MyHibernateUtil
						.viewWithCriteria(CampainImpl.class, "userId",
								user.getId(), "userId").iterator();

				while (campaingnsCollectionIterator.hasNext()) {
					StatisticManager
							.remove(campaingnsCollectionIterator.next());
				}

				Iterator sitesIterator = MyHibernateUtil.viewWithCriteria(
						SiteImpl.class, "userId", user.getId(), "userId")
						.iterator();

				while (sitesIterator.hasNext()) {
					StatisticManager.remove(sitesIterator.next());
				}

				removeFromStatistic("advertizerId", user.getId(),
						"setAdvertizerId");

				removeFromStatistic("publisherId", user.getId(),
						"setPublisherId");
			}

			if (RoleController.PUBLISHER.equals(user.getRole())) {
				Iterator sitesIterator = MyHibernateUtil.viewWithCriteria(
						SiteImpl.class, "userId", user.getId(), "userId")
						.iterator();

				while (sitesIterator.hasNext()) {
					StatisticManager.remove(sitesIterator.next());
				}

				removeFromStatistic("publisherId", user.getId(),
						"setPublisherId");
			}

			Account account = MoneyManager.loadUserAccount(user.getId());
			account.setMoney(new Integer(0));
			MyHibernateUtil.updateObject(account);

			return true;
		}

		logger.warn("can't  find instance of given class");

		return false;
	}

	private static synchronized boolean removeFromStatistic(
			String criterianame, Object criteriaValue, String metodName) {
		Class statisticClass = StatisticImpl.class;
		Class clickClass = ClickImpl.class;

		Method method;
		Method methodOfClickClass;

		try {
			method = statisticClass.getMethod(metodName,
					new Class[] { Integer.class });
			methodOfClickClass = clickClass.getMethod(metodName,
					new Class[] { Integer.class });
		} catch (SecurityException e) {
			logger.error(" find metod -- secure exeption +" + metodName, e);

			return false;
		} catch (NoSuchMethodException e) {
			logger.error("cant find metod " + metodName, e);

			return false;
		}

		Iterator impressionsCollectionIterator = MyHibernateUtil
				.viewWithCriteria(statisticClass, criterianame, criteriaValue,
						"statisticId").iterator();

		while (impressionsCollectionIterator.hasNext()) {
			StatisticImpl impression = (StatisticImpl) impressionsCollectionIterator
					.next();

			try {
				method.invoke(impression, new Object[] { null });
			} catch (IllegalArgumentException e1) {
				logger.error("error while invoke metod ", e1);

				return false;
			} catch (IllegalAccessException e1) {
				logger.error("error while invoke metod ", e1);

				return false;
			} catch (InvocationTargetException e1) {
				logger.error("error while invoke metod ", e1);

				return false;
			}

			MyHibernateUtil.updateObject(impression);
		}

		Collection clicksCollection = MyHibernateUtil.viewWithCriteria(
				clickClass, criterianame, criteriaValue, "clickId");
		logger.info("preparing for remove clicks " + clicksCollection.size());

		Iterator clicksCollectionIterator = clicksCollection.iterator();

		while (clicksCollectionIterator.hasNext()) {
			ClickImpl click = (ClickImpl) clicksCollectionIterator.next();

			try {
				methodOfClickClass.invoke(click, new Object[] { null });
			} catch (IllegalArgumentException e1) {
				logger.error("error while invoke impression metod ", e1);

				return false;
			} catch (IllegalAccessException e1) {
				logger.error("error while invoke metod ", e1);

				return false;
			} catch (InvocationTargetException e1) {
				logger.error("error while invoke metod ", e1);

				return false;
			}

			MyHibernateUtil.updateObject(click);
		}

		return true;
	}
}
