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

import com.adsapient.api_impl.statistic.ClickImpl;
import com.adsapient.api_impl.statistic.LeadImpl;
import com.adsapient.api_impl.statistic.SaleImpl;
import com.adsapient.api_impl.statistic.StatisticImpl;

import java.util.Collection;

public class StatisticBuilder {
	public static final Class CLICK_CLASS = ClickImpl.class;

	public static final Class IMPRESSION_CLASS = StatisticImpl.class;

	public static final Class LEADS_CLASS = LeadImpl.class;

	public static final Class SALES_CLASS = SaleImpl.class;

	public static final String SUM_TYPE = "sum";

	public static final String COUNT_TYPE = "count";

	public static final String DISTINCT_TYPE = " count(distinct ";

	public static final String DISTINCT_COLLECTION = " distinct ";

	public static int getStatistic(Class statisticClass, String statisticType,
			String statisticIdName, Collection parametersCollection) {
		StatisticFactoryInterface factory = new StatisticFactoryImpl();

		return factory.getStatistic(statisticClass, statisticType,
				statisticIdName, parametersCollection);
	}

	public static Collection getStatisticCollection(Class statisticClass,
			String statisticType, String statisticIdName,
			Collection parametersCollection) {
		StatisticFactoryInterface factory = new StatisticFactoryImpl();

		return factory.getStatisticCollection(statisticClass, statisticType,
				statisticIdName, parametersCollection);
	}
}
