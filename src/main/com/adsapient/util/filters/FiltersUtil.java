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

import com.adsapient.api.FilterInterface;

import com.adsapient.api_impl.filter.factory.CampainFilters;
import com.adsapient.api_impl.filter.factory.FiltersFactory;

import com.adsapient.shared.mappable.CountryAbbrEntity;

import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.admin.AdsapientConstants;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class FiltersUtil {
	static Logger logger = Logger.getLogger(FiltersUtil.class);

	private static TreeMap<String, String> mapCountries = new TreeMap<String, String>();

	public static String[] countryIndexArray = null;

	public static Map<String, String> BrowsersMap = new TreeMap<String, String>();

	public static Map<String, String> SystemsMap = new TreeMap<String, String>();

	public static Map<String, String> LangsMap = new TreeMap<String, String>();

	public static String getAllCoutryIndexes() {
		StringBuffer sb = new StringBuffer();

		if (countryIndexArray != null) {
			for (int i = 0; i < countryIndexArray.length; i++)
				sb.append(countryIndexArray[i]).append(",");
		}

		return sb.toString();
	}

	public static String getCountryNameByCode(String countryCode) {
		return mapCountries.get(countryCode);
	}

	public static void removeAllFiltersForGivenCampain(Integer campainId) {
		CampainFilters campainFilters = FiltersFactory.createCampinFilters(
				campainId, null);

		Iterator campainFiltersIterator = campainFilters.getFiltersMap()
				.entrySet().iterator();

		while (campainFiltersIterator.hasNext()) {
			Map.Entry element = (Map.Entry) campainFiltersIterator.next();
			FilterInterface filter = (FilterInterface) element.getValue();

			if (filter != null) {
				MyHibernateUtil.removeObject(element.getValue());
			} else {
				logger.warn("in remove campain filters filter is null  it key="
						+ element.getKey());
			}
		}
	}

	public static void fillCountryMap(List<CountryAbbrEntity> countries) {
		if ((countries == null) || (countries.size() == 0)) {
			logger.warn("Country list is empty");
			mapCountries = new TreeMap<String, String>();
			countryIndexArray = new String[0];

			return;
		}

		ArrayList<String> list = new ArrayList<String>();

		try {
			TreeMap<String, String> map = new TreeMap<String, String>();
			int i = 0;

			for (CountryAbbrEntity country : countries) {
				String key = country.getCountryAbbr2();
				map.put(key, country.getCountryName());

				if (key
						.equals(AdsapientConstants.COUNTRY_ABBR_ADDRES_ANONIMOUS)
						|| key
								.equals(AdsapientConstants.COUNTRY_ABBR_ADDRES_RESERVED)
						|| key
								.equals(AdsapientConstants.COUNTRY_ABBR_ADDRES_NOT_FOUND)) {
					list.add(0, key);
				} else {
					list.add(key);
				}
			}

			mapCountries = map;

			countryIndexArray = new String[list.size()];
			countryIndexArray = list.toArray(countryIndexArray);
		} catch (Exception e) {
			logger.error("Error getting Countries", e);
		}
	}
}
