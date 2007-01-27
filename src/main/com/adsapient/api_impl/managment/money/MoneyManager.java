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

import com.adsapient.api_impl.advertizer.CampainImpl;
import com.adsapient.api_impl.publisher.PlacesImpl;
import com.adsapient.api_impl.publisher.SiteImpl;
import com.adsapient.api_impl.usermanagment.Account;
import com.adsapient.api_impl.usermanagment.BillingInfoImpl;
import com.adsapient.api_impl.usermanagment.Financial;
import com.adsapient.api_impl.usermanagment.RateImpl;
import com.adsapient.api_impl.usermanagment.UserImpl;
import com.adsapient.api_impl.usermanagment.role.RoleController;

import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.admin.ConfigurationConstants;

import org.apache.log4j.Logger;

public class MoneyManager {
	static Logger logger = Logger.getLogger(MoneyManager.class);

	public static boolean createBannerRate(Banner banner, Integer campainId) {
		CampainImpl campain = (CampainImpl) MyHibernateUtil.loadObject(
				CampainImpl.class, campainId);

		RateImpl campainRate = (RateImpl) MyHibernateUtil.loadObject(
				RateImpl.class, campain.getRateId());

		if (campainRate == null) {
			logger.error("cant find rate with id=" + campain.getRateId());

			return false;
		}

		RateImpl bannerRates = campainRate.createInstance();

		String bannerRatesId = String.valueOf(MyHibernateUtil
				.addObject(bannerRates));

		updateReporterDB(bannerRates);

		banner.setRateId(new Integer(bannerRatesId));

		return true;
	}

	public static void createCampainRate(CampainImpl campain, UserImpl user) {
		Financial userFinancial = (Financial) MyHibernateUtil
				.loadObjectWithCriteria(Financial.class, "userId", user.getId());

		RateImpl rate = new RateImpl();
		rate.setRateType(userFinancial.getAdvertisingType());
		rate.setCpcRate(userFinancial.getAdvertisingCPCrate());
		rate.setCpmRate(userFinancial.getAdvertisingCPMrate());
		rate.setCplRate(userFinancial.getAdvertisingCPLrate());
		rate.setCpsRate(userFinancial.getAdvertisingCPSrate());

		String rateId = String.valueOf(MyHibernateUtil.addObject(rate));

		updateReporterDB(rate);
		campain.setRateId(new Integer(rateId));
	}

	public static void createPlacesRate(PlacesImpl places, Integer siteId) {
		SiteImpl site = (SiteImpl) MyHibernateUtil.loadObject(SiteImpl.class,
				siteId);

		RateImpl siteRate = (RateImpl) MyHibernateUtil.loadObject(
				RateImpl.class, site.getRateId());

		RateImpl placesRate = siteRate.createInstance();

		String rateId = String.valueOf(MyHibernateUtil.addObject(placesRate));

		updateReporterDB(placesRate);
		places.setRateId(new Integer(rateId));
	}

	public static void createSiteRate(SiteImpl site, UserImpl user) {
		Financial userFinancial = (Financial) MyHibernateUtil
				.loadObjectWithCriteria(Financial.class, "userId", user.getId());

		RateImpl rate = new RateImpl();
		rate.setRateType(userFinancial.getPublishingType());
		rate.setCpcRate(userFinancial.getPublishingCPCrate());
		rate.setCpmRate(userFinancial.getPublishingCPMrate());
		rate.setCplRate(userFinancial.getPublishingCPLrate());
		rate.setCpsRate(userFinancial.getPublishingCPSrate());

		String rateId = String.valueOf(MyHibernateUtil.addObject(rate));

		updateReporterDB(rate);

		site.setRateId(new Integer(rateId));
	}

	public static void createUserFinancial(UserImpl user) {
		Financial financial = (Financial) MyHibernateUtil.loadObject(
				Financial.class, ConfigurationConstants.SYSTEM_FINANCIAL_ID);

		Financial userFinancial = new Financial();
		userFinancial.setUserId(user.getId());

		if (RoleController.ADVERTISER.equals(user.getRole())) {
			userFinancial.setAdvertisingCPCrate(financial
					.getAdvertisingCPCrate());
			userFinancial.setAdvertisingCPMrate(financial
					.getAdvertisingCPMrate());
			userFinancial.setAdvertisingCPSrate(financial
					.getAdvertisingCPSrate());
			userFinancial.setAdvertisingCPLrate(financial
					.getAdvertisingCPLrate());

			userFinancial.setAdvertisingType(financial.getAdvertisingType());
		}

		if (RoleController.PUBLISHER.equals(user.getRole())) {
			userFinancial
					.setPublishingCPCrate(financial.getPublishingCPCrate());
			userFinancial
					.setPublishingCPMrate(financial.getPublishingCPMrate());
			userFinancial
					.setPublishingCPSrate(financial.getPublishingCPSrate());
			userFinancial
					.setPublishingCPLrate(financial.getPublishingCPLrate());

			userFinancial.setPublishingType(financial.getPublishingType());
		}

		if (RoleController.ADVERTISERPUBLISHER.equals(user.getRole())
				|| (RoleController.ADMIN.equalsIgnoreCase(user.getRole()))
				|| (RoleController.HOSTEDSERVICE.equalsIgnoreCase(user
						.getRole()))) {
			userFinancial.setAdvertisingCPCrate(financial
					.getAdvertisingCPCrate());
			userFinancial
					.setPublishingCPCrate(financial.getPublishingCPCrate());

			userFinancial.setAdvertisingCPSrate(financial
					.getAdvertisingCPSrate());
			userFinancial
					.setPublishingCPSrate(financial.getPublishingCPSrate());

			userFinancial.setAdvertisingCPLrate(financial
					.getAdvertisingCPLrate());
			userFinancial
					.setPublishingCPLrate(financial.getPublishingCPLrate());

			userFinancial.setAdvertisingCPMrate(financial
					.getAdvertisingCPMrate());
			userFinancial
					.setPublishingCPMrate(financial.getPublishingCPMrate());
			userFinancial.setCommissionRate(financial.getCommissionRate());

			userFinancial.setAdvertisingType(financial.getAdvertisingType());
			userFinancial.setPublishingType(financial.getPublishingType());
		}

		MyHibernateUtil.addObject(userFinancial);

		Account userAccount = new Account();
		userAccount.setUserId(user.getId());

		MyHibernateUtil.addObject(userAccount);

		BillingInfoImpl systemBilling = (BillingInfoImpl) MyHibernateUtil
				.loadObject(BillingInfoImpl.class,
						ConfigurationConstants.SYSTEM_USER_ID);
		BillingInfoImpl userBilling = systemBilling.getCopy(user.getId());
		MyHibernateUtil.addObject(userBilling);
	}

	public static Account loadUserAccount(Integer userId) {
		Account userAccount = (Account) MyHibernateUtil.loadObjectWithCriteria(
				Account.class, "userId", userId);

		return userAccount;
	}

	public static void removeUserFinance(UserImpl user) {
		MyHibernateUtil.removeWithCriteria(Financial.class, "userId", user
				.getId());

		MyHibernateUtil.removeWithCriteria(Account.class, "userId", user
				.getId());

		MyHibernateUtil.removeWithCriteria(BillingInfoImpl.class, "userId",
				user.getId());
	}

	private static void updateReporterDB(RateImpl rate) {
		try {
		} catch (Exception ex) {
			logger.info("Reporter SQL Exception " + ex.getMessage());
		}
	}
}
