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

import com.adsapient.api.NameWorker;
import com.adsapient.api.NameSupportInterface;

import com.adsapient.api_impl.statistic.common.StatisticEntity;

import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.filters.FiltersUtil;

import org.apache.log4j.Logger;

public class ModernSingleton {
	private static CountryNameWorker countryNameWorker = null;

	private static PersistentNameWorker persistentNameWorker = null;

	private static PersistentWithId persistentWithId = null;

	public static final String COUNTRY_WORKER = "countryWorker";

	public static final String PERSISTENT_WORKER = "persistentWorker";

	public static final String PERSISTENT_WITH_ID_WORKER = "persistentWithIdWorker";

	private static Logger logger = Logger.getLogger(ModernSingleton.class);

	private ModernSingleton() {
	}

	public static NameWorker getInstance(String workerName) {
		if (COUNTRY_WORKER.equalsIgnoreCase(workerName)) {
			if (countryNameWorker == null) {
				countryNameWorker = new CountryNameWorker();
			}

			return countryNameWorker;
		}

		if (PERSISTENT_WORKER.equalsIgnoreCase(workerName)) {
			if (persistentNameWorker == null) {
				persistentNameWorker = new PersistentNameWorker();
			}

			return persistentNameWorker;
		}

		if (PERSISTENT_WITH_ID_WORKER.equals(workerName)) {
			if (persistentWithId == null) {
				persistentWithId = new PersistentWithId();
			}

			return persistentWithId;
		}

		logger.error("Cant find key for instanting worker. The given key is "
				+ workerName);

		return null;
	}
}

class PersistentNameWorker implements NameWorker {
	public void consider(String nameId, Class persistentClass,
			StatisticEntity entity) {
		String name = "n/a";

		if (nameId != null) {
			NameSupportInterface nameSupportObject = (NameSupportInterface) MyHibernateUtil
					.loadObject(persistentClass, new Integer(nameId));

			if (nameSupportObject != null) {
				name = nameSupportObject.getName();
			}
		}

		entity.setEntityName(name);
	}
}

class CountryNameWorker implements NameWorker {
	public void consider(String nameId, Class persistentClass,
			StatisticEntity entity) {
		String name = FiltersUtil.getCountryNameByCode(nameId);

		if (name == null) {
			name = "n/a";
		}

		entity.setEntityName(name);
	}
}

class PersistentWithId implements NameWorker {
	public void consider(String nameId, Class persistentClass,
			StatisticEntity entity) {
		entity.setLabel(nameId);

		NameWorker worker = ModernSingleton
				.getInstance(ModernSingleton.PERSISTENT_WORKER);
		worker.consider(nameId, persistentClass, entity);
	}
}
