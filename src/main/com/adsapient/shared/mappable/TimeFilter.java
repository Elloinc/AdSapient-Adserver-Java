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
package com.adsapient.shared.mappable;

import com.adsapient.api.Banner;
import com.adsapient.api.FilterInterface;

import com.adsapient.shared.mappable.PlacesImpl;
import com.adsapient.shared.mappable.StatisticImpl;

import com.adsapient.api.IMappable;
import com.adsapient.shared.service.CookieManagementService;
import com.adsapient.shared.service.TimeService;
import com.adsapient.shared.dao.HibernateEntityDao;

import com.adsapient.gui.forms.FilterActionForm;
import com.adsapient.gui.ContextAwareGuiBean;

import org.apache.log4j.Logger;

import org.apache.struts.action.ActionForm;

import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class TimeFilter implements FilterInterface, IMappable {
	static Logger logger = Logger.getLogger(TimeFilter.class);

	private static final String relatedFiltersQueryName = "getRelatedTimeFilterIds";

	private Integer bannerId;

	private Integer campainId;

	private String excludeTime = TimeService.DEFAULT_TIME_VALUE;

	private Integer timeFilterId;

	public boolean isApplyFilter(TimeFilter tFilter) {
		for (int i = 1; i < 25; i++) {
			String searchString = ":" + Integer.toString(i) + ":";

			if (excludeTime.indexOf(searchString) > -1) {
				if (tFilter.getExcludeTime().indexOf(searchString) < 0) {
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

	public void setExcludeTime(String excludeTime) {
		this.excludeTime = excludeTime;
	}

	public String getExcludeTime() {
		return excludeTime;
	}

	public Integer getTimeFilterId() {
		return timeFilterId;
	}

	public void setTimeFilterId(Integer timeFilterId) {
		this.timeFilterId = timeFilterId;
	}

	public void add(FilterInterface filter) {
		if (filter instanceof TimeFilter) {
			TimeFilter tFilter = (TimeFilter) filter;

			StringBuffer strBuf = new StringBuffer();

			for (int i = 1; i < 25; i++) {
				if ((excludeTime.indexOf(":" + Integer.toString(i) + ":") > -1)
						&& (tFilter.getExcludeTime().indexOf(
								":" + Integer.toString(i) + ":") > -1)) {
					strBuf.append(":").append(i).append(":");
				}
			}

			this.excludeTime = strBuf.toString();
		} else {
			logger.warn("given class isnt instance of Time filter");
		}
	}

	public void addNew() {
        HibernateEntityDao hibernateEntityDao = (HibernateEntityDao)ContextAwareGuiBean.getContext().getBean("hibernateEntityDao");
        hibernateEntityDao.save(this);
	}

	public Object copy() {
		TimeFilter timeFilter = new TimeFilter();

		timeFilter.setExcludeTime(this.getExcludeTime());

		return timeFilter;
	}

	public TimeFilter clone() throws CloneNotSupportedException {
		TimeFilter filter = (TimeFilter) super.clone();

		return filter;
	}

	public boolean doFilter(HttpServletRequest request) {
		Calendar calendar = Calendar.getInstance();
		int currentHour = calendar.get(Calendar.HOUR_OF_DAY);

		if (this.excludeTime.indexOf(Integer.toString(currentHour + 1)) < 0) {
			return false;
		}

		return true;
	}

	public void init(HttpServletRequest request, ActionForm actionForm) {
		FilterActionForm form = (FilterActionForm) actionForm;

		form.setExcludeHours(this.getExcludeTime());

		if (this.bannerId != null) {
			form.setBannerId(this.bannerId.toString());
		}

		form.setFilterAction("update");
	}

	public void reset() {
		this.excludeTime = TimeService.DEFAULT_TIME_VALUE;
	}

	public String save() {
        HibernateEntityDao hibernateEntityDao = (HibernateEntityDao)ContextAwareGuiBean.getContext().getBean("hibernateEntityDao");
        return String.valueOf(hibernateEntityDao.save(this));
	}

	public void update(HttpServletRequest request, ActionForm actionForm,
			boolean enableHibernateUpdate) {
		FilterActionForm form = (FilterActionForm) actionForm;
		StringBuffer strB = new StringBuffer();

		for (int i = 1; i < 25; i++) {
			if (request.getParameter("hour" + i) != null) {
				strB.append(":").append(i).append(":");
			}
		}

		this.setExcludeTime(strB.toString());

		form.setExcludeHours(strB.toString());

		if (enableHibernateUpdate) {
            HibernateEntityDao hibernateEntityDao = (HibernateEntityDao)ContextAwareGuiBean.getContext().getBean("hibernateEntityDao");
            hibernateEntityDao.updateObject(this);
		}
	}

	public void doAfterFilter(Banner banner, PlacesImpl places,
			StatisticImpl statistic, Map requestMap) {
	}

	public Integer getId() {
		return timeFilterId;
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
