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
package com.adsapient.api_impl.managment.money;

import com.adsapient.api.Banner;

import com.adsapient.api_impl.publisher.PlacesImpl;
import com.adsapient.api_impl.publisher.SiteImpl;
import com.adsapient.api_impl.statistic.ClickImpl;
import com.adsapient.api_impl.statistic.StatisticImpl;
import com.adsapient.api_impl.usermanagment.Account;
import com.adsapient.api_impl.usermanagment.RateImpl;

import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.financial.FinancialConstants;
import com.adsapient.util.maps.Place2SiteMap;

import org.apache.log4j.Logger;

public class MoneyCalculator {
	static Logger logger = Logger.getLogger(MoneyCalculator.class);

	public static boolean considerClick(Banner banner, PlacesImpl places,
			ClickImpl statistic) {
		int profit = 0;

		SiteImpl site = Place2SiteMap.getSiteByPlaceId(places.getId());

		if (site == null) {
			logger.error("cant load site for given placeId=" + places.getId());

			return false;
		}

		Account advertiserAccount = (Account) MyHibernateUtil
				.loadObjectWithCriteria(Account.class, "userId", banner
						.getUserId());

		if (advertiserAccount == null) {
			logger.error("cant load advertiser account for user with id="
					+ banner.getUserId());

			return false;
		}

		Account publisherAcount = (Account) MyHibernateUtil
				.loadObjectWithCriteria(Account.class, "userId", site
						.getUserId());

		if (publisherAcount == null) {
			logger.error("cant load publisher account for user with id="
					+ site.getUserId());

			return false;
		}

		Account systemAccount = (Account) MyHibernateUtil.loadObject(
				Account.class, FinancialConstants.SYSTEM_ACCOUNT_ID);

		if (systemAccount == null) {
			logger.error("Cant load system account");

			return false;
		}

		RateImpl bannerRate = banner.getRate();
		RateImpl placesRate = places.getRate();

		if (!RateImpl.parseTypeValue(bannerRate.getRateType(), RateImpl.CPC)) {
			return true;
		}

		if (!RateImpl.parseTypeValue(placesRate.getRateType(), RateImpl.CPC)) {
			placesRate.setCpcRate(new Integer(0));
		}

		statistic.setBannerRate(bannerRate.getCpcRate());
		statistic.setPlacesRate(placesRate.getCpcRate());

		advertiserAccount.update(bannerRate.getCpcRate().intValue() * (-1));

		publisherAcount.update(placesRate.getCpcRate());

		systemAccount.update(bannerRate.getCpcRate().intValue()
				- placesRate.getCpcRate().intValue());

		return true;
	}

	public static boolean considerImpressions(Banner banner, PlacesImpl places,
			StatisticImpl statistic) {
		int profit = 0;

		SiteImpl site = Place2SiteMap.getSiteByPlaceId(places.getId());

		if (site == null) {
			logger.error("cant load site for given placeId=" + places.getId());

			return false;
		}

		Account advertiserAccount = (Account) MyHibernateUtil
				.loadObjectWithCriteria(Account.class, "userId", banner
						.getUserId());

		if (advertiserAccount == null) {
			logger.error("cant load advertiser account for user with id="
					+ banner.getUserId());

			return false;
		}

		Account publisherAcount = (Account) MyHibernateUtil
				.loadObjectWithCriteria(Account.class, "userId", site
						.getUserId());

		if (publisherAcount == null) {
			logger.error("cant load publisher account for user with id="
					+ site.getUserId());

			return false;
		}

		Account systemAccount = (Account) MyHibernateUtil.loadObject(
				Account.class, FinancialConstants.SYSTEM_ACCOUNT_ID);

		if (systemAccount == null) {
			logger.error("Cant load system account");

			return false;
		}

		RateImpl bannerRate = banner.getRate();
		RateImpl placesRate = places.getRate();

		if (!bannerRate.isMatching(placesRate, 0)) {
			statistic.setBannerRate(new Integer(0));
			statistic.setPlacesRate(new Integer(0));

			return true;
		}

		statistic.setBannerRate(new Integer(
				bannerRate.getCpmRate().intValue() / 1000));
		statistic.setPlacesRate(new Integer(
				placesRate.getCpmRate().intValue() / 1000));

		advertiserAccount
				.update((bannerRate.getCpmRate().intValue() * (-1)) / 1000);

		publisherAcount.update(placesRate.getCpmRate().intValue() / 1000);

		systemAccount.update((bannerRate.getCpmRate().intValue() / 1000)
				- (placesRate.getCpmRate().intValue() / 1000));

		return true;
	}
}
