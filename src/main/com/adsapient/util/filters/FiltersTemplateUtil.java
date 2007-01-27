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
import com.adsapient.api.AdsapientException;

import com.adsapient.api_impl.filter.FiltersTemplate;
import com.adsapient.api_impl.filter.SystemFilter;
import com.adsapient.api_impl.filter.factory.CampainFilters;
import com.adsapient.api_impl.filter.factory.FiltersFactory;
import com.adsapient.api_impl.usermanagment.UserImpl;

import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.admin.ConfigurationConstants;

import com.adsapient.gui.forms.FilterActionForm;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class FiltersTemplateUtil {
	static Logger logger = Logger.getLogger(FiltersTemplateUtil.class);

	public static void addTemplate(Map filtersMap, String templateName,
			Integer userId) {
		FiltersTemplate fTemplate = new FiltersTemplate();
		Iterator filtersIterator = filtersMap.entrySet().iterator();

		while (filtersIterator.hasNext()) {
			Map.Entry entry = (Map.Entry) filtersIterator.next();

			if (entry.getValue() instanceof FilterInterface) {
				FilterInterface filter = (FilterInterface) entry.getValue();

				String id = filter.save();
				fTemplate.setFilterId4FilterKey((String) entry.getKey(), id);
			} else {
				logger
						.error("trying to save object for filter template but it isnt Filter");
			}
		}

		fTemplate.setUserId(userId);
		fTemplate.setTemplateName(templateName);
		MyHibernateUtil.addObject(fTemplate);
	}

	public static void perform(FilterActionForm form, HttpServletRequest request)
			throws AdsapientException {
		Integer campainId = new Integer(form.getCampainId());

		CampainFilters currentCampainFilters = FiltersFactory
				.createCampinFilters(campainId, null);

		UserImpl user = (UserImpl) request.getSession().getAttribute("user");

		Collection userTemplatesCollection = MyHibernateUtil.viewWithCriteria(
				FiltersTemplate.class, "userId", user.getId(), "templateId");

		Iterator templatesIterator = userTemplatesCollection.iterator();

		while (templatesIterator.hasNext()) {
			FiltersTemplate fTempl = (FiltersTemplate) templatesIterator.next();

			if (request.getParameter(fTempl.getTemplateName()) != null) {
				CampainFilters campainsFilter = FiltersFactory
						.createTemplateFilters(fTempl.getTemplateId()
								.toString());

				Iterator filtersIterator = campainsFilter.getFiltersMap()
						.entrySet().iterator();

				while (filtersIterator.hasNext()) {
					Map.Entry entry = (Map.Entry) filtersIterator.next();

					FilterInterface filter = (FilterInterface) entry.getValue();

					FilterInterface campFilter = (FilterInterface) currentCampainFilters
							.getFiltersMap().get(entry.getKey());
					campFilter.add(filter);

					MyHibernateUtil.updateObject(campFilter);
				}
			}
		}
	}

	public static void removeTemplate(FiltersTemplate fTemplate) {
		Collection filters = MyHibernateUtil.viewAll(SystemFilter.class);
		Iterator filtersIterator = filters.iterator();

		while (filtersIterator.hasNext()) {
			SystemFilter filter = (SystemFilter) filtersIterator.next();

			Class filterClass = null;

			try {
				filterClass = Class.forName(filter.getFilterClass());
			} catch (ClassNotFoundException e) {
				logger.error("cant find class in remove Template"
						+ filter.getClass() + " " + e);
			}

			if ((filterClass != null)
					&& ((fTemplate.getFilterIdByFilterKey(filter
							.getFilterName())) != null)) {
				MyHibernateUtil.removeObject(filterClass, fTemplate
						.getFilterIdByFilterKey(filter.getFilterName()));
			}
		}

		MyHibernateUtil.removeObject(fTemplate);
	}

	public static void viewTemplateOptions(FilterActionForm form,
			HttpServletRequest request) throws AdsapientException {
		CampainFilters filters = FiltersFactory.createTemplateFilters(form
				.getTemplateId());

		FiltersTemplate fTempl = (FiltersTemplate) MyHibernateUtil.loadObject(
				FiltersTemplate.class, new Integer(form.getTemplateId()));

		request.getSession().setAttribute("filterTemplate", fTempl);

		form.setTemplateName(fTempl.getTemplateName());
		request.getSession().setAttribute("templateName",
				fTempl.getTemplateName());

		Iterator filtersIterator = filters.getFiltersMap().entrySet()
				.iterator();

		while (filtersIterator.hasNext()) {
			Map.Entry entry = (Map.Entry) filtersIterator.next();

			request.getSession().setAttribute(
					ConfigurationConstants.TEMP_FILTER_PREFIX + entry.getKey(),
					entry.getValue());
		}
	}
}
