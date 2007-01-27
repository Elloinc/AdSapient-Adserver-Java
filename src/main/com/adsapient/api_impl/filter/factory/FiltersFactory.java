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
package com.adsapient.api_impl.filter.factory;

import org.apache.log4j.Logger;

public class FiltersFactory {
	private Logger logger = Logger.getLogger(FiltersFactory.class);

	public static synchronized CampainFilters createCampinFilters(
			Integer campainId, Integer bannerId) {
		return new CampainFilters(campainId, bannerId);
	}

	public static synchronized CampainFilters createFakeFilters() {
		return new CampainFilters();
	}

	public static synchronized CampainFilters createTemplateFilters(
			String templateId) {
		return new CampainFilters(templateId);
	}
}
