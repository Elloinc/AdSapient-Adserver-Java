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
package com.adsapient.util.hibernate;

import com.adsapient.api_impl.advertizer.BannerImpl;
import com.adsapient.api_impl.publisher.PlaceImpl;
import com.adsapient.api_impl.publisher.PlacesImpl;
import com.adsapient.api_impl.share.Category;
import com.adsapient.api_impl.share.Size;

import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.admin.EntityWrap;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.Iterator;

public class WrapperHelper {
	public static Logger logger = Logger.getLogger(WrapperHelper.class);

	public static boolean addEntity(EntityWrap entity) {
		if ("categorys".equalsIgnoreCase(entity.getEntityType())) {
			Category category = new Category();

			if (category.checkCategoryName(entity.getEntityValue()).size() == 0) {
				category.setName(entity.getEntityValue());
				MyHibernateUtil.addObject(category);

				return true;
			} else {
				return false;
			}
		}

		if ("positions".equalsIgnoreCase(entity.getEntityType())) {
			PlaceImpl place = new PlaceImpl();

			if (place.checkPlaceName(entity.getEntityValue()).size() == 0) {
				place.setName(entity.getEntityValue());
				MyHibernateUtil.addObject(place);

				updateReporterDB(place);

				return true;
			} else {
				return false;
			}
		}

		return false;
	}

	public static boolean deleteEntity(EntityWrap entity) {
		boolean result = true;

		if ("categorys".equalsIgnoreCase(entity.getEntityType())) {
			Category tempCategory = (Category) MyHibernateUtil.loadObject(
					new Category().getClass(),
					new Integer(entity.getEntityId()));

			MyHibernateUtil.removeObject(tempCategory);

			Category newCategory = (Category) MyHibernateUtil.viewAll(
					Category.class).iterator().next();
			Collection placesCollection = MyHibernateUtil
					.viewAll(PlacesImpl.class);

			Iterator placesIterator = placesCollection.iterator();

			while (placesIterator.hasNext()) {
				PlacesImpl places = (PlacesImpl) placesIterator.next();

				if (places.getCategorysAsList().contains(tempCategory)) {
					places.getCategorysAsList().add(newCategory);

					MyHibernateUtil.updateObject(places);
					logger.info("change place category from "
							+ tempCategory.getName() + " to "
							+ newCategory.getName() + " of places="
							+ places.getId());
				}
			}
		}

		if ("positions".equalsIgnoreCase(entity.getEntityType())) {
			PlaceImpl place = (PlaceImpl) MyHibernateUtil.loadObject(
					PlaceImpl.class, new Integer(entity.getEntityId()));

			MyHibernateUtil.removeObject(place);

			PlaceImpl newPlace = (PlaceImpl) MyHibernateUtil.viewAll(
					PlaceImpl.class).iterator().next();

			Collection placesCollection = MyHibernateUtil
					.viewAll(PlacesImpl.class);

			Iterator placesIterator = placesCollection.iterator();

			while (placesIterator.hasNext()) {
				PlacesImpl places = (PlacesImpl) placesIterator.next();

				if (places.getPlaceId().equals(place.getPlaceId())) {
					places.setPlaceId(newPlace.getPlaceId());

					MyHibernateUtil.updateObject(places);
					logger.info("change places position to position="
							+ newPlace.getName() + "  place  ");
				}
			}
		}

		if ("size".equalsIgnoreCase(entity.getEntityType())) {
			Size size = (Size) MyHibernateUtil.loadObject(Size.class,
					new Integer(entity.getEntityId()));

			MyHibernateUtil.removeObject(size);

			Size newSize = (Size) MyHibernateUtil.viewAll(Size.class)
					.iterator().next();

			Collection bannersCollection = MyHibernateUtil
					.viewAll(BannerImpl.class);
			Collection placesCollection = MyHibernateUtil
					.viewAll(PlacesImpl.class);

			Iterator bannersIterator = bannersCollection.iterator();

			while (bannersIterator.hasNext()) {
				BannerImpl banner = (BannerImpl) bannersIterator.next();

				if (banner.getSizeId().equals(size.getSizeId())) {
					banner.setSizeId(newSize.getSizeId());

					MyHibernateUtil.updateObject(banner);
					logger.info("chage size id of banner="
							+ banner.getBannerId() + " to sizeid="
							+ banner.getSizeId());
				}
			}

			Iterator placesIterator = placesCollection.iterator();

			while (placesIterator.hasNext()) {
				PlacesImpl places = (PlacesImpl) placesIterator.next();

				if (places.getSizeId().equals(size.getSizeId())) {
					places.setSizeId(newSize.getSizeId());

					MyHibernateUtil.updateObject(places);

					logger.info("changed size of ad place with id="
							+ places.getId() + " to size id="
							+ places.getSizeId());
				}
			}
		}

		return result;
	}

	private static void updateReporterDB(PlaceImpl place) {
		try {
		} catch (Exception ex) {
			logger.info("Reporter SQL Exception " + ex.getMessage());
		}
	}
}
