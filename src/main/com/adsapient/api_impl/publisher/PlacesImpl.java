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
package com.adsapient.api_impl.publisher;

import com.adsapient.api.SimpleTargetSupportElement;
import com.adsapient.api.*;

import com.adsapient.api_impl.advertizer.CampainImpl;
import com.adsapient.api_impl.managment.StatisticManager;
import com.adsapient.api_impl.managment.money.MoneyManager;
import com.adsapient.api_impl.share.Category;
import com.adsapient.api_impl.share.Size;
import com.adsapient.api_impl.statistic.StatisticImpl;
import com.adsapient.api_impl.statistic.common.StatisticRequestParameter;
import com.adsapient.api_impl.statistic.engine.StatisticBuilder;
import com.adsapient.api_impl.usermanagment.Financial;
import com.adsapient.api_impl.usermanagment.RateImpl;
import com.adsapient.api_impl.usermanagment.UserImpl;
import com.adsapient.api_impl.usermanagment.role.RoleController;

import com.adsapient.shared.api.entity.IMappable;

import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.admin.AdsapientConstants;
import com.adsapient.util.maps.Place2SiteMap;

import com.adsapient.gui.forms.PlacesEditActionForm;

import org.apache.log4j.Logger;

import org.apache.struts.action.ActionForm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class PlacesImpl extends SimpleTargetSupportElement implements
		RateEnableInterface, PlacesInterface,
		AdsapientInterceptedSupportEntity, IMappable {
	static Logger logger = Logger.getLogger(PlacesImpl.class);

	private String categorys;

	private Date creationDate;

	private Integer id;

	private Integer placeId;

	private Integer rateId;

	private Integer siteId;

	private Integer sizeId;

	private Integer userId;

	private boolean keywords = false;

	private boolean ownCampaign;

	private boolean sorting = false;

	private RateImpl rate = null;

	private int columnCount = 1;

	private int rowCount = 1;

	public PlacesImpl() {
		super();
	}

	public boolean isBatch() {
		if ((this.getRowCount() != 1) || (this.getColumnCount() != 1)) {
			return true;
		} else {
			return false;
		}
	}

	public String getCategoryName() {
		String delim = ", ";
		StringBuffer ret = new StringBuffer();

		return ret.toString();
	}

	public void setColumnCount(int columnCount) {
		this.columnCount = columnCount;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setKeywords(boolean keywords) {
		this.keywords = keywords;
	}

	public boolean isKeywords() {
		return keywords;
	}

	public void setOwnCampaign(boolean ownCampaign) {
		this.ownCampaign = ownCampaign;
	}

	public boolean isOwnCampaign() {
		return ownCampaign;
	}

	public PlaceImpl getPlace() {
		return (PlaceImpl) MyHibernateUtil.loadObject(PlaceImpl.class, placeId);
	}

	public void setPlaceId(Integer placeId) {
		this.placeId = placeId;
	}

	public Integer getPlaceId() {
		return placeId;
	}

	public RateImpl getRate() {
		if (this.rate == null) {
			this.rate = (RateImpl) MyHibernateUtil.loadObject(RateImpl.class,
					this.getRateId());
		}

		return this.rate;
	}

	public void setRateId(Integer rateId) {
		this.rateId = rateId;
	}

	public Integer getRateId() {
		return rateId;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSizeId(Integer sizeId) {
		this.sizeId = sizeId;
	}

	public Integer getSizeId() {
		return sizeId;
	}

	public String getSizeName() {
		Size size = (Size) MyHibernateUtil.loadObject(Size.class, sizeId);

		if ((size != null) && (size.getSize() != null)) {
			return size.getSize();
		} else {
			return "";
		}
	}

	public void setSorting(boolean sorting) {
		this.sorting = sorting;
	}

	public boolean isSorting() {
		return sorting;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getUserId() {
		return userId;
	}

	public Integer checkForCommission(Integer campaignId) {
		if (!this.ownCampaign) {
			return campaignId;
		}

		CampainImpl campain = (CampainImpl) MyHibernateUtil.loadObject(
				CampainImpl.class, campaignId);

		UserImpl user = (UserImpl) MyHibernateUtil.loadObject(UserImpl.class,
				campain.getUserId());

		if ((user == null)
				|| RoleController.HOSTEDSERVICE
						.equalsIgnoreCase(user.getRole())) {
			return campaignId;
		}

		Collection parametersCollection = new ArrayList();
		parametersCollection.add(new StatisticRequestParameter(
				StatisticInterface.PLACE_ID_COLUMN,
				StatisticRequestParameter.EQUAL, this.getId().toString()));

		Collection nameSupportIdCollection = new ArrayList();

		nameSupportIdCollection = StatisticBuilder.getStatisticCollection(
				StatisticBuilder.IMPRESSION_CLASS,
				StatisticBuilder.DISTINCT_COLLECTION,
				StatisticImpl.CAMPAIGN_ID_COLUMN, parametersCollection);

		Iterator nameSupportIditerator = nameSupportIdCollection.iterator();
		float ownImpressions = 0;
		float commissionsImpressions = 0;

		while (nameSupportIditerator.hasNext()) {
			Collection concreateRequestParameters = new ArrayList();
			String nameSupportId = (String) nameSupportIditerator.next();

			if (nameSupportId == null) {
				continue;
			}

			concreateRequestParameters.addAll(parametersCollection);
			concreateRequestParameters.add(new StatisticRequestParameter(
					StatisticImpl.CAMPAIGN_ID_COLUMN,
					StatisticRequestParameter.EQUAL, nameSupportId));

			int impressionsCount = StatisticBuilder.getStatistic(
					StatisticBuilder.IMPRESSION_CLASS,
					StatisticBuilder.COUNT_TYPE,
					StatisticInterface.PLACE_ID_COLUMN,
					concreateRequestParameters);

			CampainImpl loadedCampain = (CampainImpl) MyHibernateUtil
					.loadObject(CampainImpl.class, new Integer(nameSupportId));

			if (loadedCampain == null) {
				continue;
			}

			if (loadedCampain.getUserId().equals(campain.getUserId())) {
				ownImpressions += impressionsCount;
			} else {
				commissionsImpressions += impressionsCount;
			}
		}

		Financial userFinancial = (Financial) MyHibernateUtil
				.loadObjectWithCriteria(Financial.class, "userId", this
						.getUserId());
		int commission = userFinancial.getCommissionRate().intValue();

		if (((ownImpressions / 100) * commission) < commissionsImpressions) {
			return new Integer(0);
		} else {
			return campaignId;
		}
	}

	public void setRate(RateImpl rate) {
		this.rate = rate;
	}

	public boolean add(ActionForm actionForm, HttpServletRequest request) {
		if (request.getSession().getAttribute(AdsapientConstants.GUEST) != null) {
			return false;
		}

		PlacesEditActionForm form = (PlacesEditActionForm) actionForm;
		UserImpl user = (UserImpl) request.getSession().getAttribute(
				AdsapientConstants.USER);

		int userId = user.getId();

		if (RoleController.ADMIN.equalsIgnoreCase(user.getRole())) {
			try {
				userId = Integer.parseInt(request.getParameter("userId"));
			} catch (Exception e) {
				try {
					userId = (Integer) request.getAttribute("userId");
				} catch (Exception e1) {
				}
			}
		}

		form.updatPlace(this);
		this.setUserId(userId);

		MoneyManager.createPlacesRate(this, form.getSiteId());

		MyHibernateUtil.addObject(this);

		updateReporterDB();

		return true;
	}

	public boolean view(ActionForm actionForm, HttpServletRequest request) {
		PlacesEditActionForm form = (PlacesEditActionForm) actionForm;
		form.initForm(this);
		form.setAction("edit");

		return true;
	}

	public boolean edit(ActionForm actionForm, HttpServletRequest request) {
		if (request.getSession().getAttribute(AdsapientConstants.GUEST) != null) {
			return false;
		}

		PlacesEditActionForm form = (PlacesEditActionForm) actionForm;
		form.updatPlace(this);
		MyHibernateUtil.updateObject(this);

		return true;
	}

	public boolean delete(ActionForm actionForm, HttpServletRequest request) {
		if ((request == null)
				|| (request.getSession().getAttribute(AdsapientConstants.GUEST) != null)) {
			return false;
		}

		PublishManagerImpl manag = new PublishManagerImpl();

		MyHibernateUtil.removeObject(RateImpl.class, this.getRateId());

		StatisticManager.remove(this);

		MyHibernateUtil.removeObject(PlacesImpl.class, this.id);

		Place2SiteMap.reload();

		return true;
	}

	public boolean init(ActionForm actionForm, HttpServletRequest request) {
		PlacesEditActionForm form = (PlacesEditActionForm) actionForm;
		form.setAction("add");

		SiteImpl site = null;

		if (form.getSiteId() != null) {
			site = (SiteImpl) MyHibernateUtil.loadObject(SiteImpl.class, form
					.getSiteId());
			site.updatePlaces(this);
		}

		form.initForm(this);
		form.setTypeId(site.getTypeId());
		form.setUrl(site.getUrl());
		form.setSiteId(site.getSiteId());

		return false;
	}

	public String getCategorys() {
		return categorys;
	}

	public void setCategorys(String categorys) {
		this.categorys = categorys;
	}

	public List<Category> getCategorysAsList() {
		if (categorys == null) {
			return new ArrayList();
		}

		String[] ids = categorys.split(":");
		List cats = new ArrayList();

		for (String id : ids) {
			try {
				int idN = Integer.parseInt(id);
				Category cat = (Category) MyHibernateUtil.loadObject(
						Category.class, idN);
				cats.add(cat);
			} catch (Exception ex) {
			}
		}

		return cats;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	private void updateReporterDB() {
		try {
		} catch (Exception ex) {
			logger.info("Reporter SQL Exception " + ex.getMessage());
		}
	}
}
