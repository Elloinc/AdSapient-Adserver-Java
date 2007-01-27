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
package com.adsapient.util.filters;

import com.adsapient.api_impl.publisher.PlaceImpl;
import com.adsapient.api_impl.publisher.PlacesImpl;
import com.adsapient.api_impl.share.Category;

import com.adsapient.util.MyHibernateUtil;

import java.util.Collection;
import java.util.Iterator;

public class ContentFilterAddon {
	public static String getCategorysAllKeys() {
		Collection categoryCollection = MyHibernateUtil.viewAll(Category.class);

		Iterator categorysIterator = categoryCollection.iterator();

		StringBuffer resultString = new StringBuffer();

		while (categorysIterator.hasNext()) {
			Category category = (Category) categorysIterator.next();
			resultString.append(":").append(category.getId()).append("-0")
					.append(":");
		}

		return resultString.toString();
	}

	public static String getPositionsAllKeys() {
		Collection categoryCollection = MyHibernateUtil
				.viewAll(PlaceImpl.class);

		Iterator categorysIterator = categoryCollection.iterator();

		StringBuffer resultString = new StringBuffer();

		while (categorysIterator.hasNext()) {
			PlaceImpl place = (PlaceImpl) categorysIterator.next();
			resultString.append(":").append(place.getPlaceId()).append(":");
		}

		return resultString.toString();
	}

	public static String getPlacesAllKeys() {
		Collection placesCollection = MyHibernateUtil.viewAll(PlacesImpl.class);
		StringBuffer resultBuffer = new StringBuffer();

		for (Iterator placesIterator = placesCollection.iterator(); placesIterator
				.hasNext();) {
			PlacesImpl currentPlaces = (PlacesImpl) placesIterator.next();
			resultBuffer.append(":").append(currentPlaces.getId()).append(":");
		}

		return resultBuffer.toString();
	}
}
