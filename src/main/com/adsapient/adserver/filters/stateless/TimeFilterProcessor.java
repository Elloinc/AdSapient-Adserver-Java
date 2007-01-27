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
package com.adsapient.adserver.filters.stateless;

import com.adsapient.api.FilterInterface;

import com.adsapient.api_impl.filter.DateFilter;
import com.adsapient.api_impl.filter.TimeFilter;

import org.apache.log4j.Logger;

import java.util.Calendar;
import java.util.Map;

public class TimeFilterProcessor {
	static Logger logger = Logger.getLogger(TimeFilterProcessor.class);

	public boolean doFilter(FilterInterface entity,
			Map<String, Object> requestParams) {
		try {
			if (entity instanceof TimeFilter) {
				TimeFilter timeFilterObj = (TimeFilter) entity;
				String excludeTime = timeFilterObj.getExcludeTime();
				Calendar calendar = Calendar.getInstance();
				int currentHour = calendar.get(Calendar.HOUR_OF_DAY);

				if (excludeTime.indexOf(Integer.toString(currentHour + 1)) < 0) {
					return false;
				}

				return true;
			}

			if (entity instanceof DateFilter) {
				DateFilter dateFilterObj = (DateFilter) entity;
				String excludedays = dateFilterObj.getExcludeDays();

				Calendar calendar = Calendar.getInstance();
				int currentDay = calendar.get(Calendar.DAY_OF_WEEK);

				if (excludedays.indexOf(Integer.toString(currentDay)) < 0) {
					return false;
				}

				return true;
			}

			return true;
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}

		return true;
	}
}
