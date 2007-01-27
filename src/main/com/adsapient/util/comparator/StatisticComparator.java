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
package com.adsapient.util.comparator;

import com.adsapient.api_impl.publisher.PlacesImpl;

import com.adsapient.util.MyHibernateUtil;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class StatisticComparator {
	private static Logger logger = Logger.getLogger(StatisticComparator.class);

	public static Map transformBannersCollectionBySites(
			Collection sitesCollection, Collection placesCollection) {
		Map resultMap = new HashMap();

		Iterator sitesIterator = sitesCollection.iterator();

		while (sitesIterator.hasNext()) {
			String siteId = (String) sitesIterator.next();
			Collection resultCollection = new ArrayList();

			resultMap.put(new Integer(siteId), resultCollection);
		}

		Iterator placesIterator = placesCollection.iterator();

		while (placesIterator.hasNext()) {
			String placesId = (String) placesIterator.next();

			PlacesImpl places = (PlacesImpl) MyHibernateUtil.loadObject(
					PlacesImpl.class, new Integer(placesId));

			if (resultMap.containsKey(places.getSiteId())) {
				((Collection) resultMap.get(places.getSiteId())).add(places);
			} else {
				logger
						.warn("cant find site statistic in statistic table for adplace with id="
								+ placesId);
			}
		}

		return resultMap;
	}
}
