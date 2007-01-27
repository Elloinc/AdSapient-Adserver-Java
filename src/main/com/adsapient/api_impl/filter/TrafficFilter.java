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
import com.adsapient.api_impl.statistic.ClickImpl;
import com.adsapient.api_impl.statistic.StatisticImpl;

import com.adsapient.shared.api.entity.IMappable;

import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.filters.TrafficFilterUtil;

import com.adsapient.gui.forms.FilterActionForm;

import org.apache.log4j.Logger;

import org.apache.struts.action.ActionForm;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class TrafficFilter implements FilterInterface, IMappable {
	private static final String relatedFiltersQueryName = "getRelatedTrafficFilterIds";

	Logger logger = Logger.getLogger(TrafficFilter.class);

	private Integer trafficShare = 0;

	private Integer bannerId;

	private Integer campainId;

	private Integer trafficFilterId;

	private int CustomPeriodClickInValue = 0;

	private int CustomPeriodClickInValueUnique = 0;

	private int CustomPeriodValue = 0;

	private int CustomPeriodValueUnique = 0;

	private int customPeriodClickInDay = 0;

	private int customPeriodClickInDayUnique = 0;

	private int customPeriodClickInHour = 0;

	private int customPeriodClickInHourUnique = 0;

	private int customPeriodDay = 0;

	private int customPeriodDayUnique = 0;

	private int customPeriodHour = 0;

	private int customPeriodHourUnique = 0;

	private int maxClicksInCampain = 0;

	private int maxClicksInCampainForUniqueUser = 0;

	private int maxClicksInDay = 0;

	private int maxClicksInDayForUniqueUser = 0;

	private int maxClicksInMonthForUniqueUser = 0;

	private int maxImpresionsInDayForUniqueUser = 0;

	private int maxImpressionsInCampain = 0;

	private int maxImpressionsInCampainForUniqueUser = 0;

	private int maxImpressionsInDay = 0;

	private int maxImpressionsInMonthForUniqueUser = 0;

	public void setCampainId(Integer campainId) {
		this.campainId = campainId;
	}

	public Integer getCampainId() {
		return campainId;
	}

	public void setCustomPeriodClickInDay(int customPeriodClickInDay) {
		this.customPeriodClickInDay = customPeriodClickInDay;
	}

	public int getCustomPeriodClickInDay() {
		return customPeriodClickInDay;
	}

	public void setCustomPeriodClickInDayUnique(int customPeriodClickInDayUnique) {
		this.customPeriodClickInDayUnique = customPeriodClickInDayUnique;
	}

	public int getCustomPeriodClickInDayUnique() {
		return customPeriodClickInDayUnique;
	}

	public void setCustomPeriodClickInHour(int customPeriodClickInHour) {
		this.customPeriodClickInHour = customPeriodClickInHour;
	}

	public int getCustomPeriodClickInHour() {
		return customPeriodClickInHour;
	}

	public void setCustomPeriodClickInHourUnique(
			int customPeriodClickInHourUnique) {
		this.customPeriodClickInHourUnique = customPeriodClickInHourUnique;
	}

	public int getCustomPeriodClickInHourUnique() {
		return customPeriodClickInHourUnique;
	}

	public void setCustomPeriodClickInValue(int customPeriodClickInValue) {
		CustomPeriodClickInValue = customPeriodClickInValue;
	}

	public int getCustomPeriodClickInValue() {
		return CustomPeriodClickInValue;
	}

	public void setCustomPeriodClickInValueUnique(
			int customPeriodClickInValueUnique) {
		CustomPeriodClickInValueUnique = customPeriodClickInValueUnique;
	}

	public int getCustomPeriodClickInValueUnique() {
		return CustomPeriodClickInValueUnique;
	}

	public void setCustomPeriodDay(int customPeriodDay) {
		this.customPeriodDay = customPeriodDay;
	}

	public int getCustomPeriodDay() {
		return customPeriodDay;
	}

	public void setCustomPeriodDayUnique(int customPeriodDayUnique) {
		this.customPeriodDayUnique = customPeriodDayUnique;
	}

	public int getCustomPeriodDayUnique() {
		return customPeriodDayUnique;
	}

	public void setCustomPeriodHour(int customPeriodHour) {
		this.customPeriodHour = customPeriodHour;
	}

	public int getCustomPeriodHour() {
		return customPeriodHour;
	}

	public void setCustomPeriodHourUnique(int customPeriodHourUnique) {
		this.customPeriodHourUnique = customPeriodHourUnique;
	}

	public int getCustomPeriodHourUnique() {
		return customPeriodHourUnique;
	}

	public void setCustomPeriodValue(int customPeriodValue) {
		CustomPeriodValue = customPeriodValue;
	}

	public int getCustomPeriodValue() {
		return CustomPeriodValue;
	}

	public void setCustomPeriodValueUnique(int customPeriodValueUnique) {
		CustomPeriodValueUnique = customPeriodValueUnique;
	}

	public int getCustomPeriodValueUnique() {
		return CustomPeriodValueUnique;
	}

	public void setMaxClicksInCampain(int maxClicksInCampain) {
		this.maxClicksInCampain = maxClicksInCampain;
	}

	public int getMaxClicksInCampain() {
		return maxClicksInCampain;
	}

	public void setMaxClicksInCampainForUniqueUser(
			int maxClicksInCampainForUniqueUser) {
		this.maxClicksInCampainForUniqueUser = maxClicksInCampainForUniqueUser;
	}

	public int getMaxClicksInCampainForUniqueUser() {
		return maxClicksInCampainForUniqueUser;
	}

	public void setMaxClicksInDay(int maxClicksInDay) {
		this.maxClicksInDay = maxClicksInDay;
	}

	public int getMaxClicksInDay() {
		return maxClicksInDay;
	}

	public void setMaxClicksInDayForUniqueUser(int maxClicksInDayForUniqueUser) {
		this.maxClicksInDayForUniqueUser = maxClicksInDayForUniqueUser;
	}

	public int getMaxClicksInDayForUniqueUser() {
		return maxClicksInDayForUniqueUser;
	}

	public void setMaxClicksInMonthForUniqueUser(
			int maxClicksInMonthForUniqueUser) {
		this.maxClicksInMonthForUniqueUser = maxClicksInMonthForUniqueUser;
	}

	public int getMaxClicksInMonthForUniqueUser() {
		return maxClicksInMonthForUniqueUser;
	}

	public void setMaxImpresionsInDayForUniqueUser(
			int maxImpresionsInDayForUniqueUser) {
		this.maxImpresionsInDayForUniqueUser = maxImpresionsInDayForUniqueUser;
	}

	public int getMaxImpresionsInDayForUniqueUser() {
		return maxImpresionsInDayForUniqueUser;
	}

	public void setMaxImpressionsInCampain(int maxImpressionsInCampain) {
		this.maxImpressionsInCampain = maxImpressionsInCampain;
	}

	public int getMaxImpressionsInCampain() {
		return maxImpressionsInCampain;
	}

	public void setMaxImpressionsInCampainForUniqueUser(
			int maxImpressionsInCampainForUniqueUser) {
		this.maxImpressionsInCampainForUniqueUser = maxImpressionsInCampainForUniqueUser;
	}

	public int getMaxImpressionsInCampainForUniqueUser() {
		return maxImpressionsInCampainForUniqueUser;
	}

	public void setMaxImpressionsInDay(int maxImpressionsInDay) {
		this.maxImpressionsInDay = maxImpressionsInDay;
	}

	public int getMaxImpressionsInDay() {
		return maxImpressionsInDay;
	}

	public void setMaxImpressionsInMonthForUniqueUser(
			int maxImpressionsInMonthForUniqueUser) {
		this.maxImpressionsInMonthForUniqueUser = maxImpressionsInMonthForUniqueUser;
	}

	public int getMaxImpressionsInMonthForUniqueUser() {
		return maxImpressionsInMonthForUniqueUser;
	}

	public Integer getTrafficFilterId() {
		return trafficFilterId;
	}

	public void setTrafficFilterId(Integer trafficFilterId) {
		this.trafficFilterId = trafficFilterId;
	}

	public void add(FilterInterface filter) {
		if (filter instanceof TrafficFilter) {
			TrafficFilter trafficFilterTemplate = (TrafficFilter) filter;

			this.maxClicksInCampain = this.perfomCompare(
					this.maxClicksInCampain,
					trafficFilterTemplate.maxClicksInCampain);
			this.maxClicksInCampainForUniqueUser = this.perfomCompare(
					this.maxClicksInCampainForUniqueUser,
					trafficFilterTemplate.maxClicksInCampainForUniqueUser);
			this.maxClicksInDay = this.perfomCompare(this.maxClicksInDay,
					trafficFilterTemplate.maxClicksInDay);
			this.maxClicksInDayForUniqueUser = this.perfomCompare(
					this.maxClicksInDayForUniqueUser,
					trafficFilterTemplate.maxClicksInDayForUniqueUser);
			this.maxClicksInMonthForUniqueUser = this.perfomCompare(
					this.maxClicksInMonthForUniqueUser,
					trafficFilterTemplate.maxClicksInMonthForUniqueUser);
			this.maxImpresionsInDayForUniqueUser = this.perfomCompare(
					this.maxImpresionsInDayForUniqueUser,
					trafficFilterTemplate.maxImpresionsInDayForUniqueUser);
			this.maxImpressionsInCampain = this.perfomCompare(
					this.maxImpressionsInCampain,
					trafficFilterTemplate.maxImpressionsInCampain);
			this.maxImpressionsInCampainForUniqueUser = this.perfomCompare(
					this.maxImpressionsInCampainForUniqueUser,
					trafficFilterTemplate.maxImpressionsInCampainForUniqueUser);
			this.maxImpressionsInDay = this.perfomCompare(
					this.maxImpressionsInDay,
					trafficFilterTemplate.maxImpressionsInDay);
			this.maxImpressionsInMonthForUniqueUser = this.perfomCompare(
					this.maxImpressionsInMonthForUniqueUser,
					trafficFilterTemplate.maxImpressionsInMonthForUniqueUser);
		} else {
			logger.warn("given object isnt instance of trafficFilter");
		}
	}

	public void addNew() {
		MyHibernateUtil.addObject(this);
	}

	public Object copy() {
		TrafficFilter trafficFilter = new TrafficFilter();

		trafficFilter.setMaxClicksInCampain(this.getMaxClicksInCampain());
		trafficFilter.setMaxClicksInCampainForUniqueUser(this
				.getMaxClicksInCampainForUniqueUser());
		trafficFilter.setMaxClicksInDay(this.getMaxClicksInDay());
		trafficFilter.setMaxClicksInDayForUniqueUser(this
				.getMaxClicksInDayForUniqueUser());
		trafficFilter.setMaxClicksInMonthForUniqueUser(this
				.getMaxClicksInMonthForUniqueUser());
		trafficFilter.setMaxImpresionsInDayForUniqueUser(this
				.getMaxImpresionsInDayForUniqueUser());
		trafficFilter.setMaxImpressionsInCampain(this
				.getMaxImpressionsInCampain());
		trafficFilter.setMaxImpressionsInCampainForUniqueUser(this
				.getMaxImpressionsInCampainForUniqueUser());
		trafficFilter.setMaxImpressionsInDay(this.getMaxImpressionsInDay());
		trafficFilter.setMaxImpressionsInMonthForUniqueUser(this
				.getMaxImpressionsInMonthForUniqueUser());

		return trafficFilter;
	}

	public boolean doFilter(Map requestMap, boolean increaseCache,
			boolean isImpression) {
		if ((maxImpressionsInCampain != 0)
				&& (maxImpressionsInCampain <= TrafficFilterUtil
						.getImpressionsForCampain(campainId, increaseCache,
								isImpression))) {
			return false;
		}

		if ((maxClicksInCampain != 0)
				&& (maxClicksInCampain <= TrafficFilterUtil
						.getClicksForCampain(campainId, increaseCache,
								isImpression))) {
			return false;
		}

		if ((maxImpressionsInDay != 0)
				&& (maxImpressionsInDay <= TrafficFilterUtil
						.getImpressionsForCampainInDay(campainId,
								increaseCache, isImpression))) {
			return false;
		}

		if ((maxClicksInDay != 0)
				&& (maxClicksInDay <= TrafficFilterUtil
						.getClicksForCampainInDay(campainId, increaseCache,
								isImpression))) {
			return false;
		}

		if ((maxImpressionsInCampainForUniqueUser != 0)
				&& (maxImpressionsInCampainForUniqueUser <= TrafficFilterUtil
						.getUniqueImpressionsForCampain(campainId, requestMap,
								increaseCache, isImpression))) {
			return false;
		}

		if ((maxClicksInCampainForUniqueUser != 0)
				&& (maxClicksInCampainForUniqueUser <= TrafficFilterUtil
						.getUniqueClicksForCampain(campainId, requestMap,
								increaseCache, isImpression))) {
			return false;
		}

		if ((maxImpresionsInDayForUniqueUser != 0)
				&& (maxImpresionsInDayForUniqueUser <= TrafficFilterUtil
						.getUniqueImpressionsForCampainInDay(campainId,
								requestMap, increaseCache, isImpression))) {
			return false;
		}

		if ((maxClicksInDayForUniqueUser != 0)
				&& (maxClicksInDayForUniqueUser <= TrafficFilterUtil
						.getUniqueClicksForCampainInDay(campainId, requestMap,
								increaseCache, isImpression))) {
			return false;
		}

		if ((maxImpressionsInMonthForUniqueUser != 0)
				&& (maxImpressionsInMonthForUniqueUser <= TrafficFilterUtil
						.getUniqueImpressionsForCampainInMonth(campainId,
								requestMap, increaseCache, isImpression))) {
			return false;
		}

		if ((maxClicksInMonthForUniqueUser != 0)
				&& (maxClicksInMonthForUniqueUser <= TrafficFilterUtil
						.getUniqueClicksForCampainInMonth(campainId,
								requestMap, increaseCache, isImpression))) {
			return false;
		}

		if ((CustomPeriodValue != 0)
				&& (CustomPeriodValue <= TrafficFilterUtil
						.getCustomPeriodImpressions(requestMap, false,
								campainId, customPeriodHour, customPeriodDay,
								increaseCache, isImpression))) {
			return false;
		}

		if ((CustomPeriodValueUnique != 0)
				&& (CustomPeriodValue <= TrafficFilterUtil
						.getCustomPeriodImpressions(requestMap, true,
								campainId, customPeriodHourUnique,
								customPeriodDayUnique, increaseCache,
								isImpression))) {
			return false;
		}

		if ((CustomPeriodClickInValue != 0)
				&& (CustomPeriodClickInValue <= TrafficFilterUtil
						.getCustomPeriodClick(requestMap, false, campainId,
								customPeriodClickInHour,
								customPeriodClickInDay, increaseCache,
								isImpression))) {
			return false;
		}

		if ((CustomPeriodClickInValueUnique != 0)
				&& (CustomPeriodClickInValueUnique <= TrafficFilterUtil
						.getCustomPeriodClick(requestMap, true, campainId,
								customPeriodClickInHourUnique,
								customPeriodClickInDayUnique, increaseCache,
								isImpression))) {
			return false;
		}

		return true;
	}

	public void init(HttpServletRequest request, ActionForm actionForm) {
		FilterActionForm form = (FilterActionForm) actionForm;
		form.initTrafficFilter(this);

		if (this.bannerId != null) {
			form.setBannerId(this.bannerId.toString());
		}
	}

	public void reset() {
		this.maxClicksInCampain = 0;
		this.maxClicksInCampainForUniqueUser = 0;
		this.maxClicksInDay = 0;
		this.maxClicksInDayForUniqueUser = 0;
		this.maxImpresionsInDayForUniqueUser = 0;
		this.maxImpressionsInCampain = 0;
		this.maxImpressionsInCampainForUniqueUser = 0;
		this.maxImpressionsInDay = 0;
		this.maxImpressionsInMonthForUniqueUser = 0;
		this.customPeriodClickInDay = 0;
		this.customPeriodClickInDayUnique = 0;
		this.customPeriodClickInHour = 0;
		this.customPeriodClickInHourUnique = 0;
		this.CustomPeriodClickInValue = 0;
		this.CustomPeriodClickInValueUnique = 0;
		this.CustomPeriodValue = 0;
		this.CustomPeriodValueUnique = 0;
	}

	public String save() {
		return String.valueOf(MyHibernateUtil.addObject(this));
	}

	public void update(HttpServletRequest request, ActionForm actionForm,
			boolean enableHibernateUpdate) {
		FilterActionForm form = (FilterActionForm) actionForm;

		this.setMaxClicksInCampain(form.getMaxClicksInCampain());
		this.setMaxClicksInCampainForUniqueUser(form
				.getMaxClicksInCampainForUniqueUser());
		this.setMaxClicksInDay(form.getMaxClicksInDay());
		this.setMaxClicksInDayForUniqueUser(form
				.getMaxClicksInDayForUniqueUser());
		this.setMaxImpresionsInDayForUniqueUser(form
				.getMaxImpresionsInDayForUniqueUser());
		this.setMaxImpressionsInCampain(form.getMaxImpressionsInCampain());
		this.setMaxImpressionsInCampainForUniqueUser(form
				.getMaxImpressionsInCampainForUniqueUser());
		this.setMaxImpressionsInDay(form.getMaxImpressionsInDay());
		this.setMaxImpressionsInMonthForUniqueUser(form
				.getMaxImpressionsInMonthForUniqueUser());
		this.setMaxClicksInMonthForUniqueUser(form
				.getMaxClicksInMonthForUniqueUser());

		this.setCustomPeriodValue(form.getCustomPeriodValue());
		this.setCustomPeriodHour(form.getCustomPeriodHourValue());
		this.setCustomPeriodDay(form.getCustomPeriodDayValue());

		this.setCustomPeriodClickInValue(form.getCustomPeriodInClickValue());
		this.setCustomPeriodClickInHour(form.getCustomPeriodClickHourValue());
		this.setCustomPeriodClickInDay(form.getCustomPeriodClickDayValue());

		this.setCustomPeriodValueUnique(form.getCustomPeriodValueUnique());
		this.setCustomPeriodHourUnique(form.getCustomPeriodHourValueUnique());
		this.setCustomPeriodDayUnique(form.getCustomPeriodDayValueUnique());

		this.setCustomPeriodClickInValueUnique(form
				.getCustomPeriodInClickValueUnique());
		this.setCustomPeriodClickInHourUnique(form
				.getCustomPeriodClickHourValueUnique());
		this.setCustomPeriodClickInDayUnique(form
				.getCustomPeriodClickDayValueUnique());

		this.setTrafficShare(Integer.valueOf(form.getTrafficShare()));

		if (enableHibernateUpdate) {
			MyHibernateUtil.updateObject(this);
		}
	}

	private int perfomCompare(int fromFilter, int fromTemplate) {
		if (fromFilter == 0) {
			return fromTemplate;
		}

		if (fromTemplate == 0) {
			return fromFilter;
		}

		if (fromFilter < fromTemplate) {
			return fromFilter;
		} else {
			return fromTemplate;
		}
	}

	public void doAfterFilter(Banner banner, PlacesImpl places,
			StatisticImpl statistic, Map requestMap) {
		if (statistic instanceof ClickImpl) {
			doFilter(requestMap, true, false);
		} else {
			doFilter(requestMap, true, true);
		}
	}

	public boolean doFilter(HttpServletRequest request) {
		return false;
	}

	public Integer getId() {
		return trafficFilterId;
	}

	public Integer getBannerId() {
		return bannerId;
	}

	public void setBannerId(Integer bannerId) {
		this.bannerId = bannerId;
	}

	public TrafficFilter clone() throws CloneNotSupportedException {
		TrafficFilter filter = (TrafficFilter) super.clone();

		return filter;
	}

	public String getRelatedFiltersQueryName() {
		return relatedFiltersQueryName;
	}

	public Integer getTrafficShare() {
		return trafficShare;
	}

	public void setTrafficShare(Integer trafficShare) {
		this.trafficShare = trafficShare;
	}
}
