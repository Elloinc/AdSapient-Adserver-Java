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
package com.adsapient.api_impl.managment.diagrams;

import com.adsapient.api_impl.statistic.common.StatisticEntity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DiagramFactory {
	public static Map getShortStatisticMap(Map statisticMap,
			int allImpressionsCount) {
		Map shortStatisticMap = new HashMap();
		int othersImpressionsCount = 0;

		Iterator campainsStatisticIterator = statisticMap.entrySet().iterator();

		while (campainsStatisticIterator.hasNext()) {
			Map.Entry entry = (Map.Entry) campainsStatisticIterator.next();

			if (((StatisticEntity) entry.getValue()).getImpressions() >= (allImpressionsCount * 0.05)) {
				shortStatisticMap.put((String) entry.getKey(),
						((StatisticEntity) entry.getValue()));
			} else {
				othersImpressionsCount += ((StatisticEntity) entry.getValue())
						.getImpressions();
			}
		}

		if (othersImpressionsCount > 0) {
			StatisticEntity entity = new StatisticEntity();
			entity.setImpressions(othersImpressionsCount);

			shortStatisticMap.put("Other company", entity);
		}

		return shortStatisticMap;
	}
}
