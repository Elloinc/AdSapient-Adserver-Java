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
package com.adsapient.api_impl.filter;

import com.adsapient.api.Banner;
import com.adsapient.api.FilterInterface;

import com.adsapient.api_impl.publisher.PlacesImpl;
import com.adsapient.api_impl.statistic.StatisticImpl;

import com.adsapient.shared.api.entity.IMappable;

import com.adsapient.util.DateUtil;
import com.adsapient.util.MyHibernateUtil;

import com.adsapient.gui.actions.FilterAction;
import com.adsapient.gui.forms.FilterActionForm;

import org.apache.log4j.Logger;

import org.apache.struts.action.ActionForm;

import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class DateFilter implements FilterInterface, IMappable {
	static Logger logger = Logger.getLogger(DateFilter.class);

	private static final String relatedFiltersQueryName = "getRelatedDateFilterIds";

	private Integer bannerId;

	private Integer campainId;

	private Integer dateFilterId;

	private String excludeDays = DateUtil.DEFAULT_DATE_VALUE;

	public boolean isApplyFilter(DateFilter fDateFilter) {
		for (int i = 1; i < 8; i++) {
			if (excludeDays.indexOf(Integer.toString(i)) > -1) {
				if (fDateFilter.getExcludeDays().indexOf(Integer.toString(i)) < 0) {
					return false;
				}
			}
		}

		return true;
	}

	public void setCampainId(Integer campainId) {
		this.campainId = campainId;
	}

	public Integer getCampainId() {
		return campainId;
	}

	public void setExcludeDays(String excludeDays) {
		this.excludeDays = excludeDays;
	}

	public String getExcludeDays() {
		return excludeDays;
	}

	public void add(FilterInterface filterInterface) {
		if (filterInterface instanceof DateFilter) {
			DateFilter dFilter = (DateFilter) filterInterface;
			StringBuffer strBuf = new StringBuffer();

			for (int i = 1; i < 8; i++) {
				if ((excludeDays.indexOf(Integer.toString(i)) > -1)
						& (dFilter.getExcludeDays()
								.indexOf(Integer.toString(i)) > -1)) {
					strBuf.append(i);
				}
			}

			this.excludeDays = strBuf.toString();
		} else {
			logger.warn("given class isntn instance of date filter");
		}
	}

	public void addNew() {
		MyHibernateUtil.addObject(this);
	}

	public Object copy() {
		DateFilter dateFilter = new DateFilter();

		dateFilter.setExcludeDays(this.getExcludeDays());

		return dateFilter;
	}

	@Override
	public DateFilter clone() throws CloneNotSupportedException {
		return (DateFilter) super.clone();
	}

	public void doAfterFilter(Banner banner, PlacesImpl places,
			StatisticImpl statistic, Map requestMap) {
	}

	public boolean doFilter(HttpServletRequest request) {
		Calendar calendar = Calendar.getInstance();
		int currentDay = calendar.get(Calendar.DAY_OF_WEEK);

		if (excludeDays.indexOf(Integer.toString(currentDay)) < 0) {
			return false;
		}

		return true;
	}

	public void init(HttpServletRequest request, ActionForm actionForm) {
		FilterActionForm form = (FilterActionForm) actionForm;

		if (this.bannerId != null) {
			form.setBannerId(this.bannerId.toString());
		}

		form.setValuesOfTheDay(this.getExcludeDays());
		form.setFilterAction(FilterAction.UPDATE);
	}

	public void reset() {
		this.excludeDays = DateUtil.DEFAULT_DATE_VALUE;
	}

	public String save() {
		return String.valueOf(MyHibernateUtil.addObject(this));
	}

	public void update(HttpServletRequest request, ActionForm actionForm,
			boolean enableHibernateUpdate) {
		FilterActionForm form = (FilterActionForm) actionForm;

		StringBuffer strB = new StringBuffer();

		for (int dayNumber = 1; dayNumber < 8; dayNumber++) {
			if (request.getParameter("day" + dayNumber) != null) {
				strB.append(dayNumber);
			}
		}

		this.setExcludeDays(strB.toString());
		form.setValuesOfTheDay(strB.toString());

		if (enableHibernateUpdate) {
			MyHibernateUtil.updateObject(this);
		}
	}

	public Integer getId() {
		return dateFilterId;
	}

	public Integer getDateFilterId() {
		return dateFilterId;
	}

	public void setDateFilterId(Integer dateFilterId) {
		this.dateFilterId = dateFilterId;
	}

	public Integer getBannerId() {
		return bannerId;
	}

	public void setBannerId(Integer bannerId) {
		this.bannerId = bannerId;
	}

	public String getRelatedFiltersQueryName() {
		return relatedFiltersQueryName;
	}
}
