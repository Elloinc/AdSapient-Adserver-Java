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
package com.adsapient.api_impl.statistic.engine;

import com.adsapient.api.StatisticFactoryInterface;

import com.adsapient.api_impl.statistic.common.StatisticRequestParameter;
import com.adsapient.api_impl.statistic.common.StatisticSaver;

import com.adsapient.util.MyHibernateUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class StatisticFactoryImpl implements StatisticFactoryInterface {
	public static final String RESULT = "result";

	public int getStatistic(Class statisticClass, String statisticType,
			String statisticIdName, Collection parametersCollection) {
		int result = 0;
		Collection statisticTables = new ArrayList();

		if (statisticClass != null) {
			statisticTables = MyHibernateUtil.viewWithCriteria(
					StatisticSaver.class, StatisticSaver.STATISTIC_CLASS,
					statisticClass.getName(), StatisticSaver.ID);
		} else {
			statisticTables = MyHibernateUtil.viewAll(StatisticSaver.class);
		}

		Iterator statisticTablesIterator = statisticTables.iterator();

		while (statisticTablesIterator.hasNext()) {
			StatisticSaver statisticSaver = (StatisticSaver) statisticTablesIterator
					.next();

			StringBuffer querryBuffer = new StringBuffer();
			querryBuffer.append("select ").append(statisticType).append("(")
					.append(statisticIdName).append(")");

			if (StatisticBuilder.DISTINCT_TYPE.equalsIgnoreCase(statisticType)) {
				querryBuffer.append(")");
			}

			querryBuffer.append(" as ").append(RESULT).append(" from ").append(
					statisticSaver.getTableName());

			if (parametersCollection.size() > 0) {
				querryBuffer.append(" where ");
			}

			Iterator parametersIterator = parametersCollection.iterator();

			while (parametersIterator.hasNext()) {
				StatisticRequestParameter parameter = (StatisticRequestParameter) parametersIterator
						.next();
				querryBuffer.append(parameter.toString());

				if (parametersIterator.hasNext()) {
					querryBuffer.append(" and ");
				} else {
					querryBuffer.append(";");
				}
			}

			result += MyHibernateUtil.getSQLCount(querryBuffer.toString());
		}

		return result;
	}

	public Collection getStatisticCollection(Class statisticClass,
			String statisticType, String statisticIdName,
			Collection parametersCollection) {
		Collection result = new ArrayList();
		Collection statisticTables = new ArrayList();

		if (statisticClass != null) {
			statisticTables = MyHibernateUtil.viewWithCriteria(
					StatisticSaver.class, StatisticSaver.STATISTIC_CLASS,
					statisticClass.getName(), StatisticSaver.ID);
		} else {
			statisticTables = MyHibernateUtil.viewAll(StatisticSaver.class);
		}

		Iterator statisticTablesIterator = statisticTables.iterator();

		while (statisticTablesIterator.hasNext()) {
			StatisticSaver statisticSaver = (StatisticSaver) statisticTablesIterator
					.next();

			StringBuffer querryBuffer = new StringBuffer();
			querryBuffer.append("select ").append(statisticType).append("(")
					.append(statisticIdName).append(")");
			querryBuffer.append(" as ").append(RESULT).append(" from ").append(
					statisticSaver.getTableName());

			if (parametersCollection.size() > 0) {
				querryBuffer.append(" where ");
			}

			Iterator parametersIterator = parametersCollection.iterator();

			while (parametersIterator.hasNext()) {
				StatisticRequestParameter parameter = (StatisticRequestParameter) parametersIterator
						.next();
				querryBuffer.append(parameter.toString());

				if (parametersIterator.hasNext()) {
					querryBuffer.append(" and ");
				} else {
					querryBuffer.append(";");
				}
			}

			result.addAll(MyHibernateUtil.getSQLCollection(querryBuffer
					.toString()));
		}

		return result;
	}
}
