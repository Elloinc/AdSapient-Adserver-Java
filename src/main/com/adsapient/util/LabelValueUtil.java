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
package com.adsapient.util;

import com.adsapient.api_impl.admin.SystemCurrency;
import com.adsapient.api_impl.advertizer.BannerImpl;
import com.adsapient.api_impl.advertizer.CampainImpl;
import com.adsapient.api_impl.filter.FiltersTemplate;
import com.adsapient.api_impl.publisher.PlaceImpl;
import com.adsapient.api_impl.publisher.ResourceType;
import com.adsapient.api_impl.publisher.SiteImpl;
import com.adsapient.api_impl.resource.ResourceImpl;
import com.adsapient.api_impl.settings.TargetingSettings;
import com.adsapient.api_impl.share.CampainState;
import com.adsapient.api_impl.share.Category;
import com.adsapient.api_impl.share.Size;
import com.adsapient.api_impl.share.Type;
import com.adsapient.api_impl.share.UserDefineCampainStates;
import com.adsapient.api_impl.usermanagment.UserImpl;
import com.adsapient.api_impl.usermanagment.role.RoleController;

import com.adsapient.util.admin.AdsapientConstants;
import com.adsapient.util.jsp.AdsapientLabelValueBean;

import com.adsapient.gui.forms.EditCampainActionForm;

import org.apache.log4j.Logger;

import org.apache.struts.util.LabelValueBean;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

public class LabelValueUtil {
	private static Logger logger = Logger.getLogger(LabelValueUtil.class);

	public static Collection getCurrencyValueLabels(HttpServletRequest request) {
		Collection<LabelValueBean> resultCollection = new ArrayList<LabelValueBean>();

		Collection coll = MyHibernateUtil.viewAll(SystemCurrency.class);

		if (coll != null) {
			Iterator iter = coll.iterator();

			while (iter.hasNext()) {
				SystemCurrency currency = (SystemCurrency) iter.next();
				resultCollection.add(new LabelValueBean(Msg.fetch(currency
						.getCurrencyName(), request), currency
						.getCurrencyCode()));
			}
		}

		return resultCollection;
	}

	public static Collection getSitesCollection(HttpServletRequest request) {
		Collection<LabelValueBean> resultSitesCollection = new ArrayList<LabelValueBean>();

		UserImpl user = (UserImpl) request.getSession().getAttribute("user");

		Collection sitesCollection = MyHibernateUtil.viewWithCriteria(
				SiteImpl.class, "userId", user.getId(), "siteId");

		if ((sitesCollection != null) && (!sitesCollection.isEmpty())) {
			Iterator siteIterator = sitesCollection.iterator();

			while (siteIterator.hasNext()) {
				SiteImpl site = (SiteImpl) siteIterator.next();
				resultSitesCollection.add(new LabelValueBean(site.getUrl(),
						site.getSiteId().toString()));
			}
		} else {
		}

		return resultSitesCollection;
	}

	public static Collection fillBannerStatesValueLabel(
			HttpServletRequest request) {
		Collection<LabelValueBean> resultCollection = new ArrayList<LabelValueBean>();

		resultCollection.add(new LabelValueBean(Msg.fetch("enabled.label",
				request), "1"));
		resultCollection.add(new LabelValueBean(Msg.fetch("disabled", request),
				"0"));

		return resultCollection;
	}

	public static Collection fillCampainBannersValueLabel(Integer userId) {
		Collection<LabelValueBean> resultCollection = new ArrayList<LabelValueBean>();

		try {
			Collection coll = MyHibernateUtil.viewWithCriteria(
					BannerImpl.class, "userId", userId, "stateId", "1");

			if (coll != null) {
				Iterator iter = coll.iterator();

				while (iter.hasNext()) {
					BannerImpl banner = (BannerImpl) iter.next();
					resultCollection.add(new LabelValueBean(banner.getFile(),
							banner.getBannerId().toString()));
				}
			}
		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
		}

		return resultCollection;
	}

	public static Collection fillCampainsValueLabel(HttpServletRequest request) {
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

		Collection userCampainsCollection = new ArrayList();
		Collection<LabelValueBean> resultCollection = new ArrayList<LabelValueBean>();
		userCampainsCollection = MyHibernateUtil.viewWithCriteria(
				CampainImpl.class, "userId", userId, "campainId");

		Iterator userCampainsIterator = userCampainsCollection.iterator();

		while (userCampainsIterator.hasNext()) {
			CampainImpl userCampain = (CampainImpl) userCampainsIterator.next();

			resultCollection.add(new LabelValueBean(userCampain.getName(),
					userCampain.getCampainId().toString()));
		}

		return resultCollection;
	}

	public static Collection fillCategorysValueLabel() {
		Collection<LabelValueBean> resultCollection = new ArrayList<LabelValueBean>();

		try {
			Collection coll = MyHibernateUtil.viewAll(Category.class);

			if (coll != null) {
				Iterator iter = coll.iterator();

				while (iter.hasNext()) {
					Category category = (Category) iter.next();
					resultCollection.add(new LabelValueBean(category.getName(),
							category.getId().toString()));
				}
			}
		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
		}

		return resultCollection;
	}

	public static Collection fillCustomWeightCollection(int beginValue,
			int endValue) {
		Collection<LabelValueBean> resultCollection = new ArrayList<LabelValueBean>();

		for (int count = beginValue; count < endValue; count++) {
			LabelValueBean bean = new LabelValueBean(Integer.toString(count),
					Integer.toString(count));

			resultCollection.add(bean);
		}

		return resultCollection;
	}

	public static Collection fillResourcesValueLabel(Integer userId,
			Integer typeId, String orderField) {
		Collection<LabelValueBean> resultCollection = new ArrayList<LabelValueBean>();

		try {
			Collection coll = null;

			if (typeId.equals(new Integer(0))) {
				coll = MyHibernateUtil.viewWithCriteria(ResourceImpl.class,
						"userId", userId, orderField);
			} else {
				coll = MyHibernateUtil.viewWithCriteria(ResourceImpl.class,
						"userId", userId, "typeId", typeId);
			}

			resultCollection.add(new LabelValueBean("No resource", "0"));

			if (coll != null) {
				Iterator iter = coll.iterator();

				while (iter.hasNext()) {
					ResourceImpl res = (ResourceImpl) iter.next();
					resultCollection
							.add(new LabelValueBean(res.getResourceName(), res
									.getResourceId().toString()));
				}
			}
		} catch (Exception e) {
			System.err.println(e.getStackTrace());
		}

		return resultCollection;
	}

	public static Collection fillSitePlacesValueLabel() {
		Collection<LabelValueBean> resultCollection = new ArrayList<LabelValueBean>();

		try {
			Collection coll = MyHibernateUtil.viewAll(PlaceImpl.class);

			if (coll != null) {
				Iterator iter = coll.iterator();

				while (iter.hasNext()) {
					PlaceImpl place = (PlaceImpl) iter.next();
					resultCollection.add(new LabelValueBean(place.getName(),
							place.getPlaceId().toString()));
				}
			}
		} catch (Exception e) {
			System.err.println(e.getStackTrace());
		}

		return resultCollection;
	}

	public static Collection fillSizeValueLabel() {
		return fillSizeValueLabel(null);
	}

	public static Collection fillSizeValueLabel(Collection sizes) {
		Collection<LabelValueBean> resultCollection = new ArrayList<LabelValueBean>();

		try {
			if (sizes == null) {
				sizes = MyHibernateUtil.viewAll(Size.class);
			}

			Iterator iter = sizes.iterator();

			while (iter.hasNext()) {
				Size size = (Size) iter.next();
				resultCollection.add(new LabelValueBean(size.getSize(), size
						.getSizeId().toString()));
			}
		} catch (Exception e) {
			logger.error(e.getStackTrace());
		}

		return resultCollection;
	}

	public static Collection fillStatesValueLabel() {
		Collection<LabelValueBean> resultCollection = new ArrayList<LabelValueBean>();

		Collection coll = MyHibernateUtil.viewAll(CampainState.class);

		if (coll != null) {
			Iterator iter = coll.iterator();

			while (iter.hasNext()) {
				CampainState state = (CampainState) iter.next();
				resultCollection.add(new LabelValueBean(state.getState(), state
						.getCampainStateId()));
			}
		}

		return resultCollection;
	}

	public static Collection fillTargetingCollection(int type,
			boolean anyEnable, HttpServletRequest request) {
		Collection<LabelValueBean> resultCollection = new ArrayList<LabelValueBean>();
		Iterator hibernateCollectioniterator = MyHibernateUtil
				.viewWithCriteria(TargetingSettings.class, "typeId",
						new Integer(type), "id").iterator();

		while (hibernateCollectioniterator.hasNext()) {
			TargetingSettings setting = (TargetingSettings) hibernateCollectioniterator
					.next();

			if (anyEnable) {
				resultCollection.add(new LabelValueBean(Msg.fetch(setting
						.getSettingKey(), request), new Integer(setting
						.getSettingValue()).toString()));
			} else {
				if (!"any".equalsIgnoreCase(setting.getSettingKey())) {
					resultCollection.add(new LabelValueBean(Msg.fetch(setting
							.getSettingKey(), request), new Integer(setting
							.getSettingValue()).toString()));
				}
			}
		}

		return resultCollection;
	}

	public static Collection fillTemplatesValueLabel(String userId) {
		Collection<LabelValueBean> resultCollection = new ArrayList<LabelValueBean>();

		try {
			Collection coll = MyHibernateUtil.viewWithCriteria(
					FiltersTemplate.class, "userId", userId, "templateId");

			if (coll != null) {
				Iterator iter = coll.iterator();

				while (iter.hasNext()) {
					FiltersTemplate fTemplate = (FiltersTemplate) iter.next();
					resultCollection.add(new LabelValueBean(fTemplate
							.getTemplateName(), fTemplate.getTemplateId()
							.toString()));
				}
			}
		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
		}

		return resultCollection;
	}

	public static Collection fillTypeValueLabel() {
		Collection<LabelValueBean> resultCollection = new ArrayList<LabelValueBean>();

		try {
			Collection coll = MyHibernateUtil.viewAll(Type.class);

			Iterator iter = coll.iterator();

			while (iter.hasNext()) {
				Type type = (Type) iter.next();
				resultCollection.add(new LabelValueBean(type.getType(), type
						.getTypeId().toString()));
			}
		} catch (Exception e) {
			System.err.println(e.getStackTrace());
		}

		return resultCollection;
	}

	public static Collection fillUserDefineCampainStatesValueLabel(
			EditCampainActionForm form) {
		SimpleDateFormat sdf = new SimpleDateFormat(
				AdsapientConstants.DATE_FORMAT);
		Date campainBegiDate = null;
		Date campainEndDate = null;
		Date today = Calendar.getInstance().getTime();

		try {
			campainBegiDate = sdf.parse(form.getStartDate());
			campainEndDate = sdf.parse(form.getEndDate());
		} catch (java.text.ParseException e) {
			logger.error("infillUserDefineCampainStatesValueLebel", e);
		}

		Collection<AdsapientLabelValueBean> resultCollection = new ArrayList<AdsapientLabelValueBean>();

		Collection coll = MyHibernateUtil
				.viewAll(UserDefineCampainStates.class);

		if (coll != null) {
			Iterator campainStatesIterator = coll.iterator();

			while (campainStatesIterator.hasNext()) {
				UserDefineCampainStates state = (UserDefineCampainStates) campainStatesIterator
						.next();

				AdsapientLabelValueBean bean = new AdsapientLabelValueBean(
						Msg.fetch(state.getUserDefineCampainState(), form
								.getHsr()), state.getUserDefineCampainStateId());

				if ((form.getCampainStateId() != null)
						&& form.getCampainStateId().equalsIgnoreCase(
								bean.getValue())) {
					bean.setSelected(true);
				}

				if (UserDefineCampainStates.BOOKED.equalsIgnoreCase(bean
						.getValue())) {
					if (today.before(campainBegiDate)) {
						bean.setDisable(false);
					}
				}

				if (UserDefineCampainStates.LIVE.equalsIgnoreCase(bean
						.getValue())
						|| UserDefineCampainStates.PAUSED.equalsIgnoreCase(bean
								.getValue())
						|| UserDefineCampainStates.CANCELLED
								.equalsIgnoreCase(bean.getValue())) {
					if (today.after(campainBegiDate)
							&& (today.before(campainEndDate))) {
						bean.setDisable(false);
					}
				}

				if (UserDefineCampainStates.COMPLETED.equalsIgnoreCase(bean
						.getValue())) {
					if (today.after(campainEndDate)) {
						bean.setDisable(false);
					}
				}

				resultCollection.add(bean);
			}
		}

		return resultCollection;
	}

	public static Collection getResourcesTypes() {
		Collection<LabelValueBean> resultCollection = new ArrayList<LabelValueBean>();

		try {
			Collection coll = MyHibernateUtil.viewAll(ResourceType.class);

			if (coll != null) {
				Iterator iter = coll.iterator();

				while (iter.hasNext()) {
					ResourceType type = (ResourceType) iter.next();
					resultCollection.add(new LabelValueBean(type.getType(),
							type.getId().toString()));
				}
			}
		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
		}

		return resultCollection;
	}
}
