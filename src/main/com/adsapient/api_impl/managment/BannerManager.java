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

import com.adsapient.api_impl.advertizer.BannerImpl;
import com.adsapient.api_impl.advertizer.CampainImpl;
import com.adsapient.api_impl.managment.money.MoneyManager;
import com.adsapient.api_impl.share.Size;
import com.adsapient.api_impl.share.Type;
import com.adsapient.api_impl.usability.advertizer.BannerRepresentation;
import com.adsapient.api_impl.usermanagment.UserImpl;

import com.adsapient.util.AdSapientHibernateService;
import com.adsapient.util.MyHibernateUtil;

import org.apache.log4j.Logger;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import org.hibernate.criterion.Expression;

import java.io.File;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BannerManager {
	static Logger logger = Logger.getLogger(BannerManager.class);

	public static Collection getBanners(int status) {
		Collection<BannerRepresentation> resultCollection = new ArrayList<BannerRepresentation>();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("STATUS", new Integer(status));

		Collection<BannerImpl> banners = (Collection<BannerImpl>) MyHibernateUtil
				.executeHQLQuery("loadBanners", params);

		for (BannerImpl banner : banners) {
			if ((banner.getSizeId() != null)
					&& (banner.getSizeId().intValue() == 0)) {
				continue;
			}

			BannerRepresentation represent = new BannerRepresentation(banner);
			resultCollection.add(represent);
		}

		return resultCollection;
	}

	public static Collection getBanners4User(Integer userId, int status) {
		Collection<BannerRepresentation> resultCollection = new ArrayList<BannerRepresentation>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("USERID", userId);
		params.put("STATUS", new Integer(status));

		Collection<BannerImpl> banners = (Collection<BannerImpl>) MyHibernateUtil
				.executeHQLQuery("loadBanners4User", params);

		for (BannerImpl banner : banners) {
			if ((banner.getSizeId() != null)
					&& (banner.getSizeId().intValue() == 0)) {
				continue;
			}

			BannerRepresentation represent = new BannerRepresentation(banner);
			resultCollection.add(represent);
		}

		return resultCollection;
	}

	public static void addDefaultBanners2DefaultCampain(
			Integer defaultCampainId, UserImpl user) {
		fillDefaultBannersCollection(defaultCampainId);
	}

	public static boolean checkBannerState() {
		Collection allBannersCollection = MyHibernateUtil
				.viewAll(BannerImpl.class);

		if ((allBannersCollection != null) && (!allBannersCollection.isEmpty())) {
			Iterator bannersIterator = allBannersCollection.iterator();

			while (bannersIterator.hasNext()) {
				String filePath;
				BannerImpl banner = (BannerImpl) bannersIterator.next();
				filePath = banner.getFile();

				if (filePath != null) {
					if (!new File(filePath).exists()) {
						return false;
					}
				}
			}
		}

		return true;
	}

	public Collection checkBanners(String bannerName) {
		Collection coll = new ArrayList();

		Session session = null;
		Transaction tr = null;

		try {
			session = AdSapientHibernateService.openSession();
			tr = session.beginTransaction();

			coll = session.createCriteria(BannerImpl.class).add(
					Expression.like("name", bannerName)).list();
			tr.commit();
		} catch (HibernateException ex) {
			logger.error("This exception throw in -- vew banners ", ex);
			logger.error(ex.getLocalizedMessage());
		} finally {
			AdSapientHibernateService.closeSession(session, tr);
		}

		return coll;
	}

	private static void fillDefaultBannersCollection(Integer defaultCampainId) {
		CampainImpl campainImpl = (CampainImpl) MyHibernateUtil.loadObject(
				CampainImpl.class, defaultCampainId);

		Collection sizesCollection = MyHibernateUtil.viewAll(Size.class);
		Iterator sizesIterator = sizesCollection.iterator();

		while (sizesIterator.hasNext()) {
			Size size = (Size) sizesIterator.next();
			BannerImpl banner = new BannerImpl();
			campainImpl.udateBanner(banner);
			banner.setContentType("image/gif");
			banner.setFile(size.getDefaultFileName());
			banner.setSizeId(size.getSizeId());
			banner.setFileName("default banner");
			banner.setTypeId(Type.IMAGE_TYPE_ID);

			MoneyManager.createBannerRate(banner, defaultCampainId);

			MyHibernateUtil.addObject(banner);
		}
	}
}
