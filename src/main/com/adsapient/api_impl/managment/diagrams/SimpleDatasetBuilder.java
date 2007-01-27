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

import com.adsapient.api.DatasetBuilder;

import com.adsapient.api_impl.statistic.common.StatisticEntity;

import com.adsapient.util.Msg;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpSession;

public class SimpleDatasetBuilder implements DatasetBuilder {
	public Dataset createDataset(Object statisticDataset, HttpSession session,
			String columnType, boolean useLocalization) {
		Collection resultCollection = new ArrayList();
		Collection statisticCollection = (Collection) statisticDataset;

		StringBuffer buffer = new StringBuffer();

		if ((statisticCollection.size() < DiagramFactory2.MIN_NUMBER_OF_DAYS_IN_STATISTIC)) {
			int numberOfEmptyInstances = DiagramFactory2.MIN_NUMBER_OF_DAYS_IN_STATISTIC
					- statisticCollection.size();

			for (int emptyElementsCount = 0; emptyElementsCount < numberOfEmptyInstances; emptyElementsCount++) {
				buffer.append(" ");

				StatisticEntity entity = new StatisticEntity(buffer.toString(),
						0, 0);

				resultCollection.add(entity);
			}
		}

		resultCollection.addAll(statisticCollection);

		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
		Iterator statisticCollectioniterator = resultCollection.iterator();

		while (statisticCollectioniterator.hasNext()) {
			StatisticEntity statistic = (StatisticEntity) statisticCollectioniterator
					.next();

			Comparable rowName;

			if (useLocalization) {
				rowName = Msg.fetch("day" + statistic.getEntityName(), session);
			} else {
				rowName = statistic.getEntityName();
			}

			if (DiagramFactory2.IMPRESSIONS_COLUMN.equalsIgnoreCase(columnType)) {
				defaultcategorydataset.addValue(new Integer(statistic
						.getImpressions()), Msg.fetch("impressions", session),
						rowName);
			}

			if (DiagramFactory2.CLICKS_COLUMN.equalsIgnoreCase(columnType)) {
				defaultcategorydataset.addValue(new Integer(statistic
						.getClicks()), Msg.fetch("clicks", session), rowName);
			}
		}

		return defaultcategorydataset;
	}
}
