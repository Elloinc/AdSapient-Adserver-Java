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

import com.adsapient.api.FilterInterface;

import com.adsapient.api_impl.filter.FiltersTemplate;
import com.adsapient.api_impl.filter.SystemFilter;

import com.adsapient.util.MyHibernateUtil;

import org.apache.commons.collections.map.LinkedMap;

import org.apache.log4j.Logger;

import org.apache.struts.action.ActionForm;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class CampainFilters {
	static Logger logger = Logger.getLogger(CampainFilters.class);

	Map filtersMap = new LinkedMap();

	public CampainFilters() {
		Collection filters = MyHibernateUtil.viewAllWithOrder(
				SystemFilter.class, "id");

		Iterator filtersIterator = filters.iterator();

		while (filtersIterator.hasNext()) {
			SystemFilter filterElement = (SystemFilter) filtersIterator.next();
			Class filterClass = null;

			try {
				filterClass = Class.forName(filterElement.getFilterClass());
			} catch (ClassNotFoundException e) {
				logger.warn("could not find class " + filterElement.getClass());

				continue;
			}

			if (filterClass != null) {
				try {
					filtersMap.put(filterElement.getFilterName(), filterClass
							.newInstance());
				} catch (InstantiationException e1) {
					logger.warn(e1);

					continue;
				} catch (IllegalAccessException e1) {
					logger.warn(e1);

					continue;
				}
			}
		}
	}

	public CampainFilters(Integer campaignId, Integer bannerId) {
		Collection filters = MyHibernateUtil.viewAllWithOrder(
				SystemFilter.class, "id");

		Iterator filtersIterator = filters.iterator();

		while (filtersIterator.hasNext()) {
			SystemFilter filterElement = (SystemFilter) filtersIterator.next();
			Class filterClass = null;

			try {
				filterClass = Class.forName(filterElement.getFilterClass());
			} catch (ClassNotFoundException e) {
				logger.error("error in campain filters constructor:", e);
			}

			FilterInterface filter = null;

			if (bannerId != null) {
				filter = (FilterInterface) MyHibernateUtil
						.loadObjectWithCriteria(filterClass, "bannerId",
								bannerId);
			}

			if (filter == null) {
				filter = (FilterInterface) MyHibernateUtil
						.loadObjectWithCriteria(filterClass, "campainId",
								campaignId, "bannerId", null);
			}

			if ((bannerId != null) && (filter != null)
					&& (filter.getBannerId() == null)) {
				try {
					FilterInterface clonedFilter = (FilterInterface) filter
							.clone();
					clonedFilter.setBannerId(bannerId);
					clonedFilter.save();
					filtersMap.put(filterElement.getFilterName(), clonedFilter);
				} catch (CloneNotSupportedException e) {
					logger.error("Error while cloning filter "
							+ filterClass.getName(), e);
				}
			} else {
				filtersMap.put(filterElement.getFilterName(), filter);
			}
		}
	}

	public CampainFilters(String templateIdString) {
		Integer templateId = new Integer(templateIdString);
		FiltersTemplate template = (FiltersTemplate) MyHibernateUtil
				.loadObject(FiltersTemplate.class, templateId);

		Iterator filtersIterator = MyHibernateUtil.viewAll(SystemFilter.class)
				.iterator();

		while (filtersIterator.hasNext()) {
			SystemFilter systemFilter = (SystemFilter) filtersIterator.next();
			Class filterClass = null;

			try {
				filterClass = Class.forName(systemFilter.getFilterClass());
			} catch (ClassNotFoundException e) {
				logger.warn("class not found" + systemFilter.getFilterClass(),
						e);

				continue;
			}

			if (template.getFilterIdByFilterKey(systemFilter.getFilterName()) != null) {
				Object filter = MyHibernateUtil.loadObject(filterClass,
						template.getFilterIdByFilterKey(systemFilter
								.getFilterName()));

				if (filter != null) {
					this.filtersMap.put(systemFilter.getFilterClass(), filter);
				} else {
					logger.error("can't load filter with id ="
							+ template.getFilterIdByFilterKey(systemFilter
									.getFilterName())
							+ " for template with id=" + templateIdString);
				}
			}
		}
	}

	public Collection getFiltersKeysCollection() {
		return this.filtersMap.keySet();
	}

	public Map getFiltersMap() {
		return filtersMap;
	}

	public void init(Object filterKey, HttpServletRequest request,
			ActionForm form) {
		if (this.filtersMap.containsKey(filterKey)) {
			FilterInterface filter = (FilterInterface) filtersMap
					.get(filterKey);

			filter.init(request, form);
		} else {
			logger.warn("error in CampainFilters init : cant find  key"
					+ filterKey);
		}
	}

	public void reset(Object filterKey, boolean enableHibernateUpdate) {
		if (this.filtersMap.containsKey(filterKey)) {
			FilterInterface filter = (FilterInterface) filtersMap
					.get(filterKey);

			if (filter != null) {
				filter.reset();

				if (enableHibernateUpdate) {
					MyHibernateUtil.updateObject(filter);
				}
			} else {
				logger
						.warn("in CampainFilters metod reset: filter = null his  key id "
								+ filterKey);
			}
		} else {
			logger.warn(" in reset :cant find filter with key" + filterKey);
		}
	}

	public void resetAll(boolean enableHibernateUpdate) {
		Iterator entryIterator = this.filtersMap.entrySet().iterator();

		while (entryIterator.hasNext()) {
			Map.Entry entry = (Map.Entry) entryIterator.next();

			this.reset(entry.getKey(), enableHibernateUpdate);
		}
	}

	public boolean update(Object filterKey, HttpServletRequest request,
			ActionForm form, boolean enableHibernateUpdate) {
		if (this.filtersMap.containsKey(filterKey)) {
			FilterInterface filter = (FilterInterface) filtersMap
					.get(filterKey);

			filter.update(request, form, enableHibernateUpdate);

			if ((filter != null) && (filter.getBannerId() == null)) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("CAMP", filter.getCampainId());

				Collection list = MyHibernateUtil.executeHQLQuery(filter
						.getRelatedFiltersQueryName(), params);

				if (list != null) {
					for (Iterator iter = list.iterator(); iter.hasNext();) {
						Object[] ind = (Object[]) iter.next();
						MyHibernateUtil.removeObject(filter.getClass(),
								(Integer) ind[1]);

						try {
							FilterInterface clonedFilter = (FilterInterface) filter
									.clone();
							clonedFilter.setBannerId((Integer) ind[0]);
							clonedFilter.save();
						} catch (Exception e) {
							logger.error("unable to clone filter", e);
						}
					}
				}
			}

			if (filter != null) {
				return (filter.getBannerId() == null) ? true : false;
			}
		} else {
			logger.warn("error in CampainFilters update : cant find  key"
					+ filterKey);
		}

		return false;
	}
}
