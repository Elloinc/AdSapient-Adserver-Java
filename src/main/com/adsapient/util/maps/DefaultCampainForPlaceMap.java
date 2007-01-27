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
package com.adsapient.util.maps;

import com.adsapient.api_impl.advertizer.CampainImpl;
import com.adsapient.api_impl.publisher.SiteImpl;

import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.admin.ConfigurationConstants;

import org.apache.log4j.Logger;

public class DefaultCampainForPlaceMap {
	static Logger logger = Logger.getLogger(DefaultCampainForPlaceMap.class);

	public static CampainImpl getDefaultCampainByPlaceId(Integer placeId) {
		SiteImpl site = Place2SiteMap.getSiteByPlaceId(placeId);

		if (site != null) {
			return getDefaultCampainByUserId(site.getUserId());
		}

		logger.error("can't load site by place ID -- " + placeId);

		return null;
	}

	public static CampainImpl getDefaultCampainByUserId(Integer userId) {
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
		}

		if (campain != null) {
			return campain;
		} else {
			return (CampainImpl) MyHibernateUtil.loadObject(CampainImpl.class,
					ConfigurationConstants.DEFAULT_SYSTEM_CAMPAIN_ID);
		}
	}
}
