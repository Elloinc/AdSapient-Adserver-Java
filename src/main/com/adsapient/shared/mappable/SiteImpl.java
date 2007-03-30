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

import com.adsapient.api.AbstractVerifyingElement;
import com.adsapient.api.RateEnableInterface;
import com.adsapient.api.SiteInterface;
import com.adsapient.api.AdsapientInterceptedSupportEntity;

import com.adsapient.api.IMappable;
import com.adsapient.shared.service.RatesService;
import com.adsapient.shared.service.CookieManagementService;

import com.adsapient.shared.AdsapientConstants;
import com.adsapient.shared.dao.HibernateEntityDao;

import com.adsapient.gui.forms.EditPublisherSiteActionForm;
import com.adsapient.gui.ContextAwareGuiBean;

import org.apache.log4j.Logger;

import org.apache.struts.action.ActionForm;
import org.apache.struts.util.LabelValueBean;

import org.hibernate.HibernateException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

public class SiteImpl extends AbstractVerifyingElement implements
		RateEnableInterface, AdsapientInterceptedSupportEntity, SiteInterface,
		IMappable {
	static Logger logger = Logger.getLogger(SiteImpl.class);

	private String categorys;

	private Integer rateId;

	private Integer siteId;

	private Integer userId;

	private Set places;

	private Set realPlaces;

	private String Description;

	private String Url;

	private String startDate;

	private boolean clicksCampainAllow = false;

	private boolean ownCampaigns = false;

	private Integer typeId;

	public SiteImpl(HttpServletRequest request) {
		super(request);

		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

		this.startDate = sdf.format(Calendar.getInstance().getTime());
	}

	public SiteImpl() {
		super();

		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

		this.startDate = sdf.format(Calendar.getInstance().getTime());
	}

	public void setClicksCampainAllow(boolean clicksCampainAllow) {
		this.clicksCampainAllow = clicksCampainAllow;
	}

	public boolean isClicksCampainAllow() {
		return clicksCampainAllow;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getDescription() {
		return Description;
	}

	public void setOwnCampaigns(boolean ownCampaigns) {
		this.ownCampaigns = ownCampaigns;
	}

	public boolean isOwnCampaigns() {
		return ownCampaigns;
	}

	public void setPlaces(Set places) {
		this.places = places;
	}

	public Set<PlacesImpl> getPlaces() {
		return places;
	}

	public RateImpl getRate() {
        HibernateEntityDao hibernateEntityDao = (HibernateEntityDao) ContextAwareGuiBean.getContext().getBean("hibernateEntityDao");
        return (RateImpl) hibernateEntityDao.loadObject(RateImpl.class, this
				.getRateId());
	}

	public void setRateId(Integer rateId) {
		this.rateId = rateId;
	}

	public Integer getRateId() {
		return rateId;
	}

	public void setRealPlaces(Set realPlaces) {
		this.realPlaces = realPlaces;
	}

	public Set<PlacesImpl> getRealPlaces() {
		return realPlaces;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setUrl(String url) {
		Url = url;
	}

	public String getUrl() {
		return Url;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getUserId() {
		return userId;
	}

	public boolean resultAdd(ActionForm actionForm, HttpServletRequest request) {
		EditPublisherSiteActionForm form = (EditPublisherSiteActionForm) actionForm;
		UserImpl user = (UserImpl) request.getSession().getAttribute(
				AdsapientConstants.USER);

		int userId = user.getId();

		if (AdsapientConstants.ADMIN.equalsIgnoreCase(user.getRole())) {
			try {
				userId = Integer.parseInt(request.getParameter("userId"));
			} catch (Exception e) {
				try {
					userId = (Integer) request.getAttribute("userId");
				} catch (Exception e1) {
				}
			}
		}

		if (form.getDescription() != null) {
			this.setDescription(form.getDescription());
		} else {
			this.setDescription("");
		}

		this.setUrl(form.getUrl());
		this.setUserId(userId);
		this.setTypeId(form.getTypeId());

		this.setCategorys(form.getSelectedCategorysName());
		this.setClicksCampainAllow(this.clicksCampainAllow);

        HibernateEntityDao hibernateEntityDao = (HibernateEntityDao) ContextAwareGuiBean.getContext().getBean("hibernateEntityDao");
        RatesService ratesService = (RatesService) ContextAwareGuiBean.getContext().getBean("ratesService");
        ratesService.createSiteRate(this, user);
		hibernateEntityDao.save(this);

		updateReporterDB();

		return true;
	}

	public boolean resultDelete(ActionForm actionForm,
			HttpServletRequest request) {
		if (this.getRealPlaces() != null) {
			Iterator placesIterator = this.getRealPlaces().iterator();

			while (placesIterator.hasNext()) {
				AdsapientInterceptedSupportEntity sitePlaces = (PlacesImpl) placesIterator
						.next();

				sitePlaces.delete(null, request);
			}
		}
        HibernateEntityDao hibernateEntityDao = (HibernateEntityDao) ContextAwareGuiBean.getContext().getBean("hibernateEntityDao");
		hibernateEntityDao.removeObject(RateImpl.class, this.getRateId());
		hibernateEntityDao.removeObject(SiteImpl.class, this.getSiteId());

		return true;
	}

	public boolean resultEdit(ActionForm actionForm, HttpServletRequest request) {
		EditPublisherSiteActionForm form = (EditPublisherSiteActionForm) actionForm;
        HibernateEntityDao hibernateEntityDao = (HibernateEntityDao) ContextAwareGuiBean.getContext().getBean("hibernateEntityDao");
		try {
			SiteImpl site = (SiteImpl) hibernateEntityDao.loadObject(
					SiteImpl.class, this.getSiteId());

			form.getSite(site);

			hibernateEntityDao.updateObject(site);
		} catch (HibernateException ex) {
			logger.error("Exception in updatePublisher site", ex);
		}

		return true;
	}

	public boolean resultInit(ActionForm actionForm, HttpServletRequest request) {
		return true;
	}

	public boolean resultView(ActionForm actionForm, HttpServletRequest request) {
		EditPublisherSiteActionForm form = (EditPublisherSiteActionForm) actionForm;

		form.setSiteId(this.getSiteId());
		form.setUrl(this.getUrl());
		form.setTypeId(this.getTypeId());
		form.setDescription(this.getDescription());

		form.setCategorys2(extractCategorysFromString());
		form.setCategoriesPriorities(categorys);
		form.setSitePlacesCollection(this.getPlaces());
		form.setClicksCampainAllow(this.isClicksCampainAllow());
		form.setOwnCampaigns(this.isOwnCampaigns());

		form.setAction("edit");

		return true;
	}

	public void updatePlaces(PlacesImpl places) {
		places.setCategorys(this.getCategorys());
		places.setOwnCampaign(this.isOwnCampaigns());
	}

	private void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getCategorys() {
		return categorys;
	}

	public void setCategorys(String categorys) {
		this.categorys = categorys;
	}

	private ArrayList extractCategorysFromString() {
		String[] catIds = categorys.split(":");
		ArrayList l = new ArrayList();
        HibernateEntityDao hibernateEntityDao = (HibernateEntityDao) ContextAwareGuiBean.getContext().getBean("hibernateEntityDao");
		for (String catId : catIds) {
			try {
				int catIdInt = Integer.parseInt(catId.split("-")[0]);
				Category category = (Category) hibernateEntityDao.loadObject(
						Category.class, catIdInt);
				LabelValueBean bean = new LabelValueBean(category.getName(),
						category.getId().toString());
				l.add(bean);
			} catch (Exception ex) {
			}
		}

		return l;
	}

	public Integer getId() {
		return siteId;
	}

	private void updateReporterDB() {
		try {
		} catch (Exception ex) {
			logger.info("Reporter SQL Exception " + ex.getMessage());
		}
	}
}
