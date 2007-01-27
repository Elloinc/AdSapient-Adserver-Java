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

import com.adsapient.util.MyHibernateUtil;

import org.apache.struts.action.ActionForm;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class ContextFilter implements FilterInterface {
	private static final String relatedFiltersQueryName = "getRelatedContextFilterIds";

	private Integer bannerId;

	public Integer campainId;

	public String contextFilterId;

	public void setCampainId(Integer campainId) {
		this.campainId = campainId;
	}

	public Integer getCampainId() {
		return campainId;
	}

	public void setContextFilterId(String contextFilterId) {
		this.contextFilterId = contextFilterId;
	}

	public String getContextFilterId() {
		return contextFilterId;
	}

	public void add(FilterInterface filterInterface) {
	}

	public void addNew() {
		MyHibernateUtil.addObject(this);
	}

	public Object copy() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public ContextFilter clone() throws CloneNotSupportedException {
		ContextFilter filter = (ContextFilter) super.clone();

		return filter;
	}

	public boolean doFilter(HttpServletRequest request) {
		return false;
	}

	public void init(HttpServletRequest request, ActionForm actionForm) {
	}

	public void reset() {
	}

	public String save() {
		return null;
	}

	public void update(HttpServletRequest request, ActionForm actionForm,
			boolean enableHibernateUpdate) {
	}

	public void doAfterFilter(Banner banner, PlacesImpl places,
			StatisticImpl statistic, Map requestMap) {
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
