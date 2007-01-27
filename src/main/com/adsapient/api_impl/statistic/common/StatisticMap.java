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
package com.adsapient.api_impl.statistic.common;

import org.apache.commons.collections.map.LinkedMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class StatisticMap {
	private Map statisticMap = new LinkedMap();

	public Collection getOrderingStatisticCollection() {
		Collection statisticEntitysCollection = statisticMap.values();

		ArrayList orderingStatisticCollection = new ArrayList();
		orderingStatisticCollection.addAll(statisticEntitysCollection);

		int collectionSize = statisticEntitysCollection.size();

		Iterator notOrderingCollectionIterator = statisticEntitysCollection
				.iterator();

		for (int collectionCount = 0; collectionCount < collectionSize; collectionCount++) {
			StatisticEntity statisticEntity = (StatisticEntity) notOrderingCollectionIterator
					.next();

			int statisticEntityPosition = Integer.parseInt(statisticEntity
					.getEntityName());

			orderingStatisticCollection.set(statisticEntityPosition - 1,
					statisticEntity);
		}

		return orderingStatisticCollection;
	}

	public Map getStatisticMap() {
		return this.statisticMap;
	}

	public void addStatisticEntity(StatisticEntity entity) {
		if (statisticMap.containsKey(entity.getEntityName())) {
			StatisticEntity savedEntity = (StatisticEntity) statisticMap
					.get(entity.getEntityName());

			savedEntity.sum(entity);
		} else {
			statisticMap.put(entity.getEntityName(), entity);
		}
	}

	public void addStatisticEntitysColection(Collection statisticCollection) {
		Iterator statisticEntityIterator = statisticCollection.iterator();

		while (statisticEntityIterator.hasNext()) {
			StatisticEntity statisticEntity = (StatisticEntity) statisticEntityIterator
					.next();
			addStatisticEntity(statisticEntity);
		}
	}
}
