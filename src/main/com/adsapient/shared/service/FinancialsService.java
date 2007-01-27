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
package com.adsapient.shared.service;

import com.adsapient.api_impl.advertizer.BannerImpl;
import com.adsapient.api_impl.advertizer.CampainImpl;
import com.adsapient.api_impl.publisher.PlacesImpl;
import com.adsapient.api_impl.publisher.SiteImpl;
import com.adsapient.api_impl.usermanagment.Financial;
import com.adsapient.api_impl.usermanagment.RateImpl;
import com.adsapient.api_impl.usermanagment.UserImpl;
import com.adsapient.api_impl.usermanagment.role.RoleController;

import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.admin.AdsapientConstants;
import com.adsapient.util.financial.MoneyTransformer;

import com.adsapient.gui.forms.FinancialManagmentActionForm;
import com.adsapient.gui.forms.RateManagmentActionForm;

import org.apache.log4j.Logger;

import java.util.Collection;

public class FinancialsService {
	static Logger logger = Logger.getLogger(FinancialsService.class);

	public void updateUserRates(FinancialManagmentActionForm form) {
		Financial financialOptions = (Financial) MyHibernateUtil
				.loadObjectWithCriteria(Financial.class, "userId", new Integer(
						form.getUserId()));

		if (financialOptions != null) {
			updateOptions(financialOptions, form);

			MyHibernateUtil.updateObject(financialOptions);
		} else {
			logger.error("Couldn't find rate object for userId:"
					+ form.getUserId());
		}

		UserImpl user = (UserImpl) MyHibernateUtil.loadObject(UserImpl.class,
				Integer.parseInt(form.getUserId()));

		if (user.getRole().equals(RoleController.ADVERTISER)) {
			Collection campaigns = MyHibernateUtil.viewWithCriteria(
					CampainImpl.class, "userId", user.getId(), "userId");

			for (Object obj : campaigns) {
				CampainImpl campaign = (CampainImpl) obj;
				updateCampaignRates(campaign, financialOptions);
			}
		} else if (user.getRole().equals(RoleController.PUBLISHER)) {
			Collection campaigns = MyHibernateUtil.viewWithCriteria(
					SiteImpl.class, "userId", user.getId(), "userId");

			for (Object obj : campaigns) {
				SiteImpl site = (SiteImpl) obj;
				updateSiteRates(site, financialOptions);
			}
		}
	}

	private void updateSiteRates(SiteImpl site, Financial financialOptions) {
		RateImpl rate = site.getRate();
		updateRate(rate, financialOptions);
		MyHibernateUtil.updateObject(rate);

		updateReporterDB(rate);

		for (Object place : site.getRealPlaces()) {
			PlacesImpl pl = (PlacesImpl) place;
			rate = pl.getRate();
			updateRate(rate, financialOptions);
			MyHibernateUtil.updateObject(rate);

			updateReporterDB(rate);
		}
	}

	private void updateCampaignRates(CampainImpl campaign,
			Financial financialOptions) {
		RateImpl rate = campaign.getRate();
		updateRate(rate, financialOptions);
		MyHibernateUtil.updateObject(rate);

		updateReporterDB(rate);

		for (BannerImpl banner : campaign.getBanners()) {
			rate = banner.getRate();
			updateRate(rate, financialOptions);
			MyHibernateUtil.updateObject(rate);

			updateReporterDB(rate);
		}
	}

	private void updateRate(RateImpl rate, Financial financialOptions) {
		rate.setCpmRate(financialOptions.getAdvertisingCPMrate());
		rate.setCpcRate(financialOptions.getAdvertisingCPCrate());
		rate.setCpsRate(financialOptions.getAdvertisingCPSrate());
		rate.setCplRate(financialOptions.getAdvertisingCPLrate());
	}

	public void updateOptions(Financial financialOptions,
			FinancialManagmentActionForm form) {
		financialOptions.setAdvertisingCPCrate(MoneyTransformer
				.convert2Money(form.getAdvertisingCPCrate()));
		financialOptions.setAdvertisingCPMrate(MoneyTransformer
				.convert2Money(form.getAdvertisingCPMrate()));
		financialOptions.setAdvertisingCPLrate(MoneyTransformer
				.convert2Money(form.getAdvertisingCPLrate()));
		financialOptions.setAdvertisingCPSrate(MoneyTransformer
				.convert2Money(form.getAdvertisingCPSrate()));

		financialOptions.setPublishingCPCrate(MoneyTransformer
				.convert2Money(form.getPublishingCPCrate()));
		financialOptions.setPublishingCPMrate(MoneyTransformer
				.convert2Money(form.getPublishingCPMrate()));
		financialOptions.setPublishingCPLrate(MoneyTransformer
				.convert2Money(form.getPublishingCPLrate()));
		financialOptions.setPublishingCPSrate(MoneyTransformer
				.convert2Money(form.getPublishingCPSrate()));

		StringBuffer advBuffer = new StringBuffer();
		StringBuffer pubBuffer = new StringBuffer();

		if (!AdsapientConstants.EMPTY.equalsIgnoreCase(financialOptions
				.getAdvertisingType())) {
			if (form.isAdvCPM()) {
				advBuffer.append("1,");
			} else {
				advBuffer.append("0,");
			}

			if (form.isAdvCPC()) {
				advBuffer.append("1,");
			} else {
				advBuffer.append("0,");
			}

			if (form.isAdvCPL()) {
				advBuffer.append("1,");
			} else {
				advBuffer.append("0,");
			}

			if (form.isAdvCPS()) {
				advBuffer.append("1,");
			} else {
				advBuffer.append("0,");
			}
		}

		if (!AdsapientConstants.EMPTY.equalsIgnoreCase(financialOptions
				.getPublishingType())) {
			if (form.isPubCPM()) {
				pubBuffer.append("1,");
			} else {
				pubBuffer.append("0,");
			}

			if (form.isPubCPC()) {
				pubBuffer.append("1,");
			} else {
				pubBuffer.append("0,");
			}

			if (form.isPubCPL()) {
				pubBuffer.append("1,");
			} else {
				pubBuffer.append("0,");
			}

			if (form.isPubCPS()) {
				pubBuffer.append("1,");
			} else {
				pubBuffer.append("0,");
			}
		}

		if (!AdsapientConstants.EMPTY.equalsIgnoreCase(financialOptions
				.getAdvertisingType())
				&& (!AdsapientConstants.EMPTY.equalsIgnoreCase(financialOptions
						.getPublishingType()))) {
			financialOptions.setCommissionRate(form.getCommissionRate());
		}

		financialOptions.setAdvertisingType(advBuffer.toString());
		financialOptions.setPublishingType(pubBuffer.toString());
	}

	public void updateRate(RateManagmentActionForm form, RateImpl rate) {
		rate.setCpcRate(MoneyTransformer.convert2Money(form.getCpcRate()));
		rate.setCpmRate(MoneyTransformer.convert2Money(form.getCpmRate()));
		rate.setCplRate(MoneyTransformer.convert2Money(form.getCplRate()));
		rate.setCpsRate(MoneyTransformer.convert2Money(form.getCpsRate()));
		rate.setRateId(new Integer(form.getRateId()));

		StringBuffer advBuffer = new StringBuffer();

		if (form.isAdvCPM()) {
			advBuffer.append("1,");
		} else {
			advBuffer.append("0,");
		}

		if (form.isAdvCPC()) {
			advBuffer.append("1,");
		} else {
			advBuffer.append("0,");
		}

		if (form.isAdvCPL()) {
			advBuffer.append("1,");
		} else {
			advBuffer.append("0,");
		}

		if (form.isAdvCPS()) {
			advBuffer.append("1,");
		} else {
			advBuffer.append("0,");
		}

		rate.setRateType(advBuffer.toString());

		MyHibernateUtil.updateObject(rate);

		updateReporterDB(rate);
	}

	private void updateReporterDB(RateImpl rate) {
		try {
		} catch (Exception ex) {
			logger.info("Reporter SQL Exception " + ex.getMessage());
		}
	}
}
