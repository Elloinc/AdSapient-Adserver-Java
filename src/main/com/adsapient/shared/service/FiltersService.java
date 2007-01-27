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
import com.adsapient.api_impl.filter.TrafficFilter;

import com.adsapient.shared.dao.HibernateEntityDao;

import com.adsapient.gui.forms.FilterActionForm;

import org.apache.log4j.Logger;

import java.util.Collection;

public class FiltersService {
	static Logger logger = Logger.getLogger(FiltersService.class);

	private HibernateEntityDao hibernateEntityDao;

	public int getExcess(FilterActionForm form) {
		BannerImpl banner = (BannerImpl) hibernateEntityDao.loadObject(
				BannerImpl.class, Integer.valueOf(form.getBannerId()));
		Object[] criteria = new Object[] { "campainId", banner.getCampainId() };
		BannerImpl[] banners = (BannerImpl[]) hibernateEntityDao
				.viewWithCriteria(BannerImpl.class, criteria).toArray(
						new BannerImpl[] {});
		int sum = 0;

		for (BannerImpl b : banners) {
			if (b.getId().equals(banner.getId())) {
				continue;
			}

			criteria = new Object[] { "bannerId", b.getId() };

			Collection c = hibernateEntityDao.viewWithCriteria(
					TrafficFilter.class, criteria);

			if ((c == null) || (c.size() == 0)) {
				continue;
			}

			TrafficFilter trafficFilter = (TrafficFilter) c.toArray()[0];
			sum += ((trafficFilter.getTrafficShare() == null) ? 0
					: trafficFilter.getTrafficShare());
		}

		sum += Integer.valueOf(form.getTrafficShare());

		return sum - 100;
	}

	public HibernateEntityDao getHibernateEntityDao() {
		return hibernateEntityDao;
	}

	public void setHibernateEntityDao(HibernateEntityDao hibernateEntityDao) {
		this.hibernateEntityDao = hibernateEntityDao;
	}
}
