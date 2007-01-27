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

import com.adsapient.api.AdsapientException;

import com.adsapient.api_impl.advertizer.BannerImpl;
import com.adsapient.api_impl.advertizer.CampainImpl;
import com.adsapient.api_impl.exceptions.ConfigurationsException;
import com.adsapient.api_impl.managment.money.MoneyManager;
import com.adsapient.api_impl.share.Size;
import com.adsapient.api_impl.usermanagment.UserImpl;

import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.admin.ConfigurationConstants;

import com.adsapient.gui.forms.EditCampainActionForm;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class DefaultCampainManager {
	private static Logger logger = Logger
			.getLogger(DefaultCampainManager.class);

	public static BannerImpl getBannerFromSystemDefaultCampaignBySizeId(
			Integer sizeId) {
		CampainImpl campain = (CampainImpl) MyHibernateUtil.loadObject(
				CampainImpl.class,
				ConfigurationConstants.DEFAULT_SYSTEM_CAMPAIN_ID);

		Iterator defaultBannerdIterator = campain.getBanners().iterator();

		while (defaultBannerdIterator.hasNext()) {
			BannerImpl banner = (BannerImpl) defaultBannerdIterator.next();

			if (banner.getSizeId().intValue() == sizeId.intValue()) {
				return banner;
			}
		}

		return null;
	}

	public static void addNewDefaultCampain(UserImpl user) {
		Integer campainId;

		CampainImpl camp = new CampainImpl();
		camp.setName("default");
		camp.setUserId(user.getId());
		camp
				.setUserDefineCampainStateId(ConfigurationConstants.DEFAULT_USER_DEFAIN_CAMPAIN_STATE);

		camp.setStateId(ConfigurationConstants.DEFAULT_CAMPAIN_STATE_ID);
		MoneyManager.createCampainRate(camp, user);
		campainId = new Integer(MyHibernateUtil.addObject(camp));

		BannerManager.addDefaultBanners2DefaultCampain(campainId, user);
	}

	public static void checkDefaultSystemCampain() {
		logger.info("Default campaign manager is started");

		Collection sizesCollection = MyHibernateUtil.viewAll(Size.class);
		Collection bannersCollection = new ArrayList();
		Iterator bannerIterator = bannersCollection.iterator();
		boolean saveEnable;

		CampainImpl campainImpl = (CampainImpl) MyHibernateUtil.loadObject(
				CampainImpl.class, new Integer(0));

		bannersCollection = (List<BannerImpl>) MyHibernateUtil
				.viewWithCriteria(BannerImpl.class, "campainId", campainImpl
						.getId(), "bannerId");

		Iterator sizesIterator = sizesCollection.iterator();

		while (sizesIterator.hasNext()) {
			saveEnable = true;

			Size size = (Size) sizesIterator.next();

			bannerIterator = bannersCollection.iterator();

			while (bannerIterator.hasNext()) {
				BannerImpl bannerFromCollection = (BannerImpl) bannerIterator
						.next();

				if (bannerFromCollection.getSizeId().equals(size.getSizeId())) {
					bannerFromCollection.setFile(size.getDefaultFileName());

					bannerFromCollection.setStatus(BannerImpl.STATUS_DEFAULT);

					MyHibernateUtil.updateObject(bannerFromCollection);

					saveEnable = false;
				}
			}

			if (saveEnable) {
				BannerImpl banner = new BannerImpl();
				campainImpl.udateBanner(banner);
				banner.setFile(size.getDefaultFileName());
				banner.setFileName("default");
				banner.setContentType("image/gif");
				banner.setSizeId(size.getSizeId());
				banner.setTypeId(size.getDefaultFileTypeId());
				banner.setStatus(BannerImpl.STATUS_DEFAULT);

				logger
						.info("bil dobavlen banner dlia default campain with size"
								+ size.getSize());

				MoneyManager.createBannerRate(banner,
						ConfigurationConstants.DEFAULT_SYSTEM_CAMPAIN_ID);

				MyHibernateUtil.addObject(banner);
			}
		}
	}

	public static void viewDefaultCampain(UserImpl user,
			EditCampainActionForm form) throws AdsapientException {
		CampainImpl campain = viewDefaultCampain(user.getId());
		form.init(campain);
		form.setAction("edit");
	}

	public static CampainImpl viewDefaultCampain(Integer userId)
			throws AdsapientException {
		CampainImpl campain = null;

		try {
			campain = (CampainImpl) MyHibernateUtil
					.viewWithCriteria(
							CampainImpl.class,
							"userId",
							userId,
							"stateId",
							new Integer(
									ConfigurationConstants.DEFAULT_CAMPAIN_STATE_ID))
					.iterator().next();
		} catch (Exception e) {
			logger.error(
					"Error while trying to access to default campain for user with id="
							+ userId, e);
			throw new ConfigurationsException("Can't access to default campain");
		}

		return campain;
	}
}
