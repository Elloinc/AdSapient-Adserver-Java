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
package com.adsapient.shared.mappable;

import com.adsapient.shared.mappable.Size;
import com.adsapient.shared.service.CookieManagementService;

import com.adsapient.shared.mappable.EntityWrap;
import com.adsapient.shared.dao.HibernateEntityDao;
import com.adsapient.gui.ContextAwareGuiBean;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.Iterator;

public class WrapperHelper {
	public static Logger logger = Logger.getLogger(WrapperHelper.class);

	public static boolean addEntity(EntityWrap entity) {
        HibernateEntityDao hibernateEntityDao = (HibernateEntityDao)ContextAwareGuiBean.getContext().getBean("hibernateEntityDao");
        if ("categorys".equalsIgnoreCase(entity.getEntityType())) {
			Category category = new Category();

			if (category.checkCategoryName(entity.getEntityValue()).size() == 0) {
				category.setName(entity.getEntityValue());
				hibernateEntityDao.save(category);

				return true;
			} else {
				return false;
			}
		}

		if ("positions".equalsIgnoreCase(entity.getEntityType())) {
			PlaceImpl place = new PlaceImpl();

			if (place.checkPlaceName(entity.getEntityValue()).size() == 0) {
				place.setName(entity.getEntityValue());
				hibernateEntityDao.save(place);

				updateReporterDB(place);

				return true;
			} else {
				return false;
			}
		}

		return false;
	}

	public static boolean deleteEntity(EntityWrap entity) {
        HibernateEntityDao hibernateEntityDao = (HibernateEntityDao)ContextAwareGuiBean.getContext().getBean("hibernateEntityDao");
        boolean result = true;

		if ("categorys".equalsIgnoreCase(entity.getEntityType())) {
			Category tempCategory = (Category) hibernateEntityDao.loadObject(
					new Category().getClass(),
					new Integer(entity.getEntityId()));

			hibernateEntityDao.removeObject(tempCategory);

			Category newCategory = (Category) hibernateEntityDao.viewAll(
					Category.class).iterator().next();
			Collection placesCollection = hibernateEntityDao
					.viewAll(PlacesImpl.class);

			Iterator placesIterator = placesCollection.iterator();

			while (placesIterator.hasNext()) {
				PlacesImpl places = (PlacesImpl) placesIterator.next();

				if (places.getCategorysAsList().contains(tempCategory)) {
					places.getCategorysAsList().add(newCategory);

					hibernateEntityDao.updateObject(places);
					logger.info("change place category from "
							+ tempCategory.getName() + " to "
							+ newCategory.getName() + " of places="
							+ places.getId());
				}
			}
		}

		if ("positions".equalsIgnoreCase(entity.getEntityType())) {
			PlaceImpl place = (PlaceImpl) hibernateEntityDao.loadObject(
					PlaceImpl.class, new Integer(entity.getEntityId()));

			hibernateEntityDao.removeObject(place);

			PlaceImpl newPlace = (PlaceImpl) hibernateEntityDao.viewAll(
					PlaceImpl.class).iterator().next();

			Collection placesCollection = hibernateEntityDao
					.viewAll(PlacesImpl.class);

			Iterator placesIterator = placesCollection.iterator();

			while (placesIterator.hasNext()) {
				PlacesImpl places = (PlacesImpl) placesIterator.next();

				if (places.getPlaceId().equals(place.getPlaceId())) {
					places.setPlaceId(newPlace.getPlaceId());

					hibernateEntityDao.updateObject(places);
					logger.info("change places position to position="
							+ newPlace.getName() + "  place  ");
				}
			}
		}

		if ("size".equalsIgnoreCase(entity.getEntityType())) {
			Size size = (Size) hibernateEntityDao.loadObject(Size.class,
					new Integer(entity.getEntityId()));

			hibernateEntityDao.removeObject(size);

			Size newSize = (Size) hibernateEntityDao.viewAll(Size.class)
					.iterator().next();

			Collection bannersCollection = hibernateEntityDao
					.viewAll(BannerImpl.class);
			Collection placesCollection = hibernateEntityDao
					.viewAll(PlacesImpl.class);

			Iterator bannersIterator = bannersCollection.iterator();

			while (bannersIterator.hasNext()) {
				BannerImpl banner = (BannerImpl) bannersIterator.next();

				if (banner.getSizeId().equals(size.getSizeId())) {
					banner.setSizeId(newSize.getSizeId());

					hibernateEntityDao.updateObject(banner);
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

					hibernateEntityDao.updateObject(places);

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
