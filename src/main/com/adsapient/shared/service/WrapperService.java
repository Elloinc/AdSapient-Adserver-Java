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

import com.adsapient.shared.mappable.PlaceImpl;
import com.adsapient.shared.mappable.Category;
import com.adsapient.shared.mappable.Size;
import com.adsapient.shared.mappable.EntityWrap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class WrapperService {
	public static Collection wrapCollection(Collection objectsCollection) {
		Collection resultCollection = new ArrayList();

		if (!objectsCollection.isEmpty()) {
			Iterator objectsCollectionIterator = objectsCollection.iterator();

			while (objectsCollectionIterator.hasNext()) {
				EntityWrap entity = wrapEntity(objectsCollectionIterator.next());

				resultCollection.add(entity);
			}
		}

		return resultCollection;
	}

	public static EntityWrap wrapEntity(Object someObject) {
		EntityWrap wrap = new EntityWrap();

		if (someObject instanceof Category) {
			Category categoryObject = (Category) someObject;
			wrap.setEntityId(categoryObject.getId().toString());
			wrap.setEntityValue(categoryObject.getName());
			wrap.setEntityType("categorys");

			return wrap;
		}

		if (someObject instanceof PlaceImpl) {
			PlaceImpl place = (PlaceImpl) someObject;
			wrap.setEntityId(place.getPlaceId().toString());
			wrap.setEntityValue(place.getName());
			wrap.setEntityType("positions");

			return wrap;
		}

		if (someObject instanceof Size) {
			Size size = (Size) someObject;
			wrap.setEntityId(size.getSizeId().toString());
			wrap.setEntityValue(size.getSize());
			wrap.setEntityType("size");

			return wrap;
		}

		return null;
	}
}
