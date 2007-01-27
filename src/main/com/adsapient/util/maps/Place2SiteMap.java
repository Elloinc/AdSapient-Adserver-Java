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

import com.adsapient.api_impl.publisher.PlacesImpl;
import com.adsapient.api_impl.publisher.SiteImpl;
import com.adsapient.api_impl.share.Category;

import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.admin.ConfigurationConstants;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Place2SiteMap {
	static Logger logger = Logger.getLogger(Place2SiteMap.class);

	private static HashMap place2Site = new HashMap();

	private static HashMap placesMap = new HashMap();

	public static synchronized List<Integer> getCategoryFromPlace(
			Integer placeId) {
		List<Integer> resultCategoryIds = new ArrayList();

		if (placesMap.containsKey(placeId)) {
			PlacesImpl place = (PlacesImpl) placesMap.get(placeId);

			for (Category cat : place.getCategorysAsList())
				resultCategoryIds.add(cat.getId());
		}

		return resultCategoryIds;
	}

	public static synchronized PlacesImpl getPlaceFromId(Integer placeId) {
		if (placesMap.containsKey(placeId)) {
			return (PlacesImpl) placesMap.get(placeId);
		}

		logger.info(" in place2sitemap cant find place with id=" + placeId
				+ " in metod getPlaceFromId ");

		return null;
	}

	public static synchronized Integer getPositionIdFromPlaceId(Integer placeId) {
		Integer resultPlaceId = null;

		if (placesMap.containsKey(placeId)) {
			PlacesImpl place = (PlacesImpl) placesMap.get(placeId);
			resultPlaceId = place.getPlaceId();
		}

		return resultPlaceId;
	}

	public static synchronized SiteImpl getSiteByPlaceId(Integer placeId) {
		if (place2Site.containsKey(placeId)) {
			return (SiteImpl) place2Site.get(placeId);
		}

		System.err.println("Error can't find key for place id " + placeId);

		return null;
	}

	public static synchronized Integer getSizeIdFromPlaceId(Integer placeId) {
		if (placesMap.containsKey(placeId)) {
			PlacesImpl place = (PlacesImpl) placesMap.get(placeId);

			return place.getSizeId();
		}

		return null;
	}

	public static synchronized boolean isThisPlaceRegistered(Integer placeId) {
		if (placeId == null) {
			return false;
		}

		if (place2Site.containsKey(placeId)) {
			return true;
		}

		return false;
	}

	public static void reload() {
		fill();
	}

	private static synchronized void fill() {
		Collection sitesCollection = new ArrayList();
		PlacesImpl realPlace;

		place2Site = new HashMap();
		placesMap = new HashMap();

		sitesCollection = MyHibernateUtil.viewWithCriteria(SiteImpl.class,
				"stateId", new Integer(
						ConfigurationConstants.DEFAULT_VERIFYING_STATE_ID),
				"siteId");

		Iterator sitesIterator = sitesCollection.iterator();

		while (sitesIterator.hasNext()) {
			SiteImpl site = (SiteImpl) sitesIterator.next();

			if ((site.getRealPlaces() != null)
					&& (!site.getRealPlaces().isEmpty())) {
				Iterator placesIterator = site.getRealPlaces().iterator();

				while (placesIterator.hasNext()) {
					PlacesImpl place = (PlacesImpl) placesIterator.next();

					placesMap.put(place.getId(), place);

					place2Site.put(place.getId(), site);
				}
			}
		}
	}
}
