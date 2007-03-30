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

import com.adsapient.shared.mappable.City;
import com.adsapient.shared.service.CookieManagementService;
import com.adsapient.shared.dao.HibernateEntityDao;
import com.adsapient.gui.ContextAwareGuiBean;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class CitiesService {
	private static SortedMap<String, String> cities = new TreeMap<String, String>();

	private static Collection allCities = null;

	static Logger logger = Logger.getLogger(CitiesService.class);

	public static Collection getAllCities() {
        HibernateEntityDao hibernateEntityDao =  (HibernateEntityDao) ContextAwareGuiBean.getContext().getBean("hibernateEntityDao");
        if (allCities == null) {
			StringBuffer stringBuffer = new StringBuffer();

			stringBuffer
					.append(
							"select airports.airport from com.adsapient.api_impl.ute.AirportImpl")
					.append(" as airports group by airports.airport");

			allCities = hibernateEntityDao.getObject(stringBuffer.toString());
		}

		return allCities;
	}

	public static String getAllCitiesIndexes() {
		StringBuffer resultCitiesCode = new StringBuffer();

		List allCities = (List) getAllCities();

		if (allCities == null) {
			return null;
		}

		for (Iterator iter = allCities.iterator(); iter.hasNext();) {
			String city = (String) iter.next();
			resultCitiesCode.append(city).append(",");
		}

		return resultCitiesCode.toString();
	}

	@SuppressWarnings("unchecked")
	public static String getCityNameByCode(String cityCode) {
        HibernateEntityDao hibernateEntityDao =  (HibernateEntityDao) ContextAwareGuiBean.getContext().getBean("hibernateEntityDao");
        if (cities.isEmpty()) {
			List<City> list = (List<City>) hibernateEntityDao.viewAll(City.class);

			for (City city : list)
				cities.put(city.getAbbr(), city.getName());
		}

		if (cities.containsKey(cityCode)) {
			return (String) cities.get(cityCode);
		} else {
			return "n/a";
		}
	}
}
