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
package com.adsapient.api_impl.advertizer;

import com.adsapient.api.Campain;
import com.adsapient.api.FilterInterface;
import com.adsapient.api.VerifyingTargetSupportElement;
import com.adsapient.api.*;

import com.adsapient.api_impl.filter.factory.FiltersFactory;
import com.adsapient.api_impl.managment.StatisticManager;
import com.adsapient.api_impl.managment.money.MoneyManager;
import com.adsapient.api_impl.publisher.PlacesImpl;
import com.adsapient.api_impl.share.UserDefineCampainStates;
import com.adsapient.api_impl.statistic.common.StatisticRequestParameter;
import com.adsapient.api_impl.statistic.engine.StatisticBuilder;
import com.adsapient.api_impl.usermanagment.Account;
import com.adsapient.api_impl.usermanagment.RateImpl;
import com.adsapient.api_impl.usermanagment.UserImpl;
import com.adsapient.api_impl.usermanagment.role.RoleController;

import com.adsapient.shared.api.entity.IMappable;

import com.adsapient.util.Msg;
import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.admin.AdsapientConstants;
import com.adsapient.util.filters.FiltersUtil;

import com.adsapient.gui.forms.EditCampainActionForm;

import org.apache.log4j.Logger;

import org.apache.struts.action.ActionForm;

import org.hibernate.HibernateException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

public class CampainImpl extends VerifyingTargetSupportElement implements
		RateEnableInterface, NameSupportInterface, Campain,
		AdsapientInterceptedSupportEntity, IMappable {
	protected static final long serialVersionUID = 1L;

	protected static Logger logger = Logger.getLogger(CampainImpl.class);

	protected Integer budget = new Integer(0);

	protected List<FilterInterface> filters;

	protected Integer campainId;

	protected Integer rateId;

	protected Integer userId = new Integer(0);

	protected RateImpl rate = null;

	protected Set<BannerImpl> banners = new HashSet<BannerImpl>();

	protected String altText = "";

	protected String endDate;

	protected String name;

	protected String startDate;

	protected String statusBartext = "";

	protected String url = "http://";

	protected String userDefineCampainStateId;

	protected boolean ownCampaigns = false;

	protected int prioritet = 10;

	public CampainImpl() {
		super();

		SimpleDateFormat sdf = new SimpleDateFormat(
				AdsapientConstants.DATE_FORMAT);

		this.startDate = sdf.format(Calendar.getInstance().getTime());

		Calendar endDateCalendar = Calendar.getInstance();
		endDateCalendar.add(Calendar.WEEK_OF_MONTH, 4);
		this.endDate = sdf.format(endDateCalendar.getTime());

		this.userDefineCampainStateId = UserDefineCampainStates.LIVE;

		this.setCampainId(new Integer(0));
	}

	public CampainImpl(HttpServletRequest request) {
		super(request);

		SimpleDateFormat sdf = new SimpleDateFormat(
				AdsapientConstants.DATE_FORMAT);

		this.startDate = sdf.format(Calendar.getInstance().getTime());

		Calendar endDateCalendar = Calendar.getInstance();
		endDateCalendar.add(Calendar.WEEK_OF_MONTH, 4);
		this.endDate = sdf.format(endDateCalendar.getTime());

		this.userDefineCampainStateId = UserDefineCampainStates.LIVE;

		this.setCampainId(new Integer(0));
	}

	public Integer getActualCampaignCost() {
		Collection<StatisticRequestParameter> requestParameters = new ArrayList<StatisticRequestParameter>();
		requestParameters
				.add(new StatisticRequestParameter(
						StatisticInterface.CAMPAIGN_ID_COLUMN,
						StatisticRequestParameter.EQUAL, this.getCampainId()
								.toString()));

		int cost = StatisticBuilder.getStatistic(null,
				StatisticBuilder.COUNT_TYPE,
				StatisticInterface.BANNER_RATE_COLUMN, requestParameters);
		logger.info("cost camapign:" + campainId + " is " + cost
				+ " and the budget is " + this.budget);

		return new Integer(cost);
	}

	public void setAltText(String altText) {
		this.altText = altText;
	}

	public String getAltText() {
		return altText;
	}

	public Set<BannerImpl> getBanners() {
		return banners;
	}

	public void setBanners(Set<BannerImpl> banners) {
		this.banners = banners;
	}

	public void setBudget(Integer budget) {
		this.budget = budget;
	}

	public Integer getBudget() {
		return budget;
	}

	public void setCampainId(Integer campainId) {
		this.campainId = campainId;
	}

	public Integer getCampainId() {
		return this.campainId;
	}

	public String getCampainState(HttpServletRequest request) {
		UserDefineCampainStates state = (UserDefineCampainStates) MyHibernateUtil
				.loadObject(UserDefineCampainStates.class, this
						.getUserDefineCampainStateId());

		return Msg.fetch(state.getUserDefineCampainState(), request);
	}

	public boolean isContainRelevantSizeBanner(PlacesImpl places,
			Collection usedBannersCollection) {
		boolean findBanner = false;

		if (this.budget.intValue() != 0) {
			if (this.getActualCampaignCost().intValue() >= this.getBudget()
					.intValue()) {
				return false;
			}
		}

		if ((banners != null) && (!banners.isEmpty())) {
			Iterator bannersIterator = banners.iterator();

			while (bannersIterator.hasNext()) {
				BannerImpl banner = (BannerImpl) bannersIterator.next();

				if (banner.isSuitable(places, usedBannersCollection)) {
					findBanner = true;
				}
			}
		}

		if (findBanner) {
			Account advertiserAccount = (Account) MyHibernateUtil
					.loadObjectWithCriteria(Account.class, "userId", this
							.getUserId());

			if (advertiserAccount.getMoney().intValue() <= 0) {
				UserImpl user = (UserImpl) MyHibernateUtil.loadObject(
						UserImpl.class, this.getUserId());

				if (RoleController.HOSTEDSERVICE.equalsIgnoreCase(user
						.getRole())) {
					return true;
				}

				return false;
			}

			return true;
		}

		return false;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setOwnCampaigns(boolean ownCampaigns) {
		this.ownCampaigns = ownCampaigns;
	}

	public boolean isOwnCampaigns() {
		return ownCampaigns;
	}

	public void setPrioritet(int prioritet) {
		this.prioritet = prioritet;
	}

	public int getPrioritet() {
		return prioritet;
	}

	public void setRate(RateImpl rate) {
		this.rate = rate;
	}

	public RateImpl getRate() {
		if (this.getRateId() == null) {
			return null;
		}

		if (this.rate == null) {
			this.rate = (RateImpl) MyHibernateUtil.loadObject(RateImpl.class,
					rateId);
		}

		return this.rate;
	}

	public void setRateId(Integer rateId) {
		this.rateId = rateId;
	}

	public Integer getRateId() {
		return rateId;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStatusBartext(String statusBartext) {
		this.statusBartext = statusBartext;
	}

	public String getStatusBartext() {
		return statusBartext;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUserDefineCampainStateId(String userDefineCampainStateId) {
		this.userDefineCampainStateId = userDefineCampainStateId;
	}

	public String getUserDefineCampainStateId() {
		return userDefineCampainStateId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getUserId() {
		return userId;
	}

	public boolean addCheck(ActionForm actionForm, HttpServletRequest request) {
		EditCampainActionForm form = (EditCampainActionForm) actionForm;

		if (form != null) {
			form.setAction("init");
		}

		return super.addCheck(actionForm, request);
	}

	public boolean deleteCheck(ActionForm actionForm, HttpServletRequest request) {
		EditCampainActionForm form = (EditCampainActionForm) actionForm;

		if (form != null) {
			form.setAction("init");
		}

		return super.deleteCheck(actionForm, request);
	}

	public boolean editCheck(ActionForm actionForm, HttpServletRequest request) {
		EditCampainActionForm form = (EditCampainActionForm) actionForm;

		if (form != null) {
			form.setAction("init");
		}

		return super.editCheck(actionForm, request);
	}

	public boolean resultAdd(ActionForm actionForm, HttpServletRequest request) {
		EditCampainActionForm form = (EditCampainActionForm) actionForm;
		UserImpl user = (UserImpl) request.getSession().getAttribute(
				AdsapientConstants.USER);
		Integer campainId;

		form.getCampain(this);
		MoneyManager.createCampainRate(this, user);

		campainId = new Integer(MyHibernateUtil.addObject(this));

		updateReporterDB(this);

		Iterator campainIterator = FiltersFactory.createFakeFilters()
				.getFiltersMap().entrySet().iterator();

		while (campainIterator.hasNext()) {
			Map.Entry filterEntry = (Map.Entry) campainIterator.next();

			FilterInterface filter = (FilterInterface) filterEntry.getValue();

			filter.setCampainId(campainId);

			filter.addNew();
		}

		return true;
	}

	public boolean resultDelete(ActionForm actionForm,
			HttpServletRequest request) {
		FiltersUtil.removeAllFiltersForGivenCampain(campainId);

		Iterator bannersIterator = this.getBanners().iterator();

		while (bannersIterator.hasNext()) {
			BannerImpl banner = (BannerImpl) bannersIterator.next();

			BannerUploadUtil.removeBanner(banner.getBannerId());
		}

		StatisticManager.remove(this);

		MyHibernateUtil.removeObject(CampainImpl.class, campainId);

		MyHibernateUtil.removeObject(RateImpl.class, this.getRateId());

		StatisticManager.remove(this);

		return true;
	}

	public boolean resultEdit(ActionForm actionForm, HttpServletRequest request) {
		EditCampainActionForm form = (EditCampainActionForm) actionForm;

		try {
			CampainImpl campain = (CampainImpl) MyHibernateUtil.loadObject(
					CampainImpl.class, this.getCampainId());
			form.getCampain(campain);
			MyHibernateUtil.updateObject(campain);

			updateReporterDB(campain);
		} catch (HibernateException ex) {
			logger.error("Exeption in edit campain ", ex);
		}

		return true;
	}

	public boolean resultInit(ActionForm actionForm, HttpServletRequest request) {
		EditCampainActionForm form = (EditCampainActionForm) actionForm;
		UserImpl user = (UserImpl) request.getSession().getAttribute(
				AdsapientConstants.USER);

		form.setAction("add");
		form.init(this);
		form.setUserId(user.getId());

		return true;
	}

	public boolean resultView(ActionForm actionForm, HttpServletRequest request) {
		EditCampainActionForm form = (EditCampainActionForm) actionForm;
		form.init(this);
		form.setAction("edit");

		return false;
	}

	public void udateBanner(Banner banner) {
		banner.setCampainId(this.campainId);

		if ((banner.getUserId() != null)
				&& ((banner.getStatus() != BannerImpl.STATUS_DEFAULT) && (banner
						.getStatus() != BannerImpl.STATUS_PUBLISHER_DEFAULT))) {
			banner.setUserId(this.userId);
		}

		banner.setStartDate(this.startDate);
		banner.setEndDate(this.endDate);
	}

	public Integer getId() {
		return campainId;
	}

	public List<FilterInterface> getFilters() {
		return filters;
	}

	public void setFilters(List<FilterInterface> filters) {
		this.filters = filters;
	}

	private void updateReporterDB(CampainImpl campain) {
		try {
		} catch (Exception ex) {
			logger.info("Reporter SQL Exception " + ex.getMessage());
		}
	}
}
