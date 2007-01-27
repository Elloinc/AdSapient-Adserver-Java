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
package com.adsapient.api_impl.managment.plugin;

import com.adsapient.api.Banner;
import com.adsapient.api.AdsapientException;
import com.adsapient.api.AbstractPluginBannerClass;

import com.adsapient.api_impl.advertizer.BannerImpl;
import com.adsapient.api_impl.advertizer.PluginsImpl;
import com.adsapient.api_impl.share.Type;

import com.adsapient.util.MyHibernateUtil;

import com.adsapient.gui.forms.BannerUploadFormAction;

import org.apache.log4j.Logger;

import java.lang.reflect.Constructor;

public class BannerPluginFactory {
	private static Logger logger = Logger.getLogger(BannerPluginFactory.class);

	public static Banner createBanner(BannerUploadFormAction form)
			throws AdsapientException {
		if (Type.IMAGE_TYPE_ID.equals(form.getTypeId())
				|| Type.FLESH_TYPE_ID.equals(form.getTypeId())
				|| Type.HTML_TYPE_ID.equals(form.getTypeId())
				|| Type.SUPERSTITIAL_BANNER.equals(form.getTypeId())) {
			Banner banner = new BannerImpl(form);

			return banner;
		}

		Type bannerType = (Type) MyHibernateUtil.loadObject(Type.class, form
				.getTypeId());
		PluginsImpl plugin = (PluginsImpl) MyHibernateUtil
				.loadObjectWithCriteria(PluginsImpl.class, "typeId", bannerType
						.getTypeId());
		Class bannerClass = null;
		AbstractPluginBannerClass banner = null;

		try {
			bannerClass = Class.forName(plugin.getClassName());
		} catch (ClassNotFoundException e) {
			logger.error("cant create plugin banner. Reason class not found:"
					+ plugin.getClassName());
			throw new AdsapientException(
					"cant find class from external plugin ");
		}

		try {
			Constructor con = bannerClass.getConstructor(new Class[] { form
					.getClass() });
			banner = (AbstractPluginBannerClass) con
					.newInstance(new Object[] { form });
		} catch (InstantiationException e) {
			logger.warn(" while instance plugin", e);
		} catch (IllegalAccessException e) {
			logger.warn(" while instance plugin", e);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return banner;
	}

	public static Banner loadBannerById(Integer bannerId) {
		Banner banner = (Banner) MyHibernateUtil.loadObjectWithCriteria(
				BannerImpl.class, "bannerId", bannerId);

		if (banner == null) {
			logger.warn("cant find banner with id:" + bannerId);
		}

		return banner;
	}

	public static void removeBanner(Integer bannerId) {
		logger.warn("remove banner with id:" + bannerId);
		MyHibernateUtil.removeWithCriteria(BannerImpl.class, "bannerId",
				bannerId);
	}
}
