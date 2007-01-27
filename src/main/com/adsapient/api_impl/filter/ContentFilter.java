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

import com.adsapient.api_impl.publisher.PlaceImpl;
import com.adsapient.api_impl.publisher.PlacesImpl;
import com.adsapient.api_impl.share.Category;
import com.adsapient.api_impl.statistic.StatisticImpl;

import com.adsapient.shared.api.entity.IMappable;

import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.admin.AdsapientConstants;
import com.adsapient.util.filters.ContentFilterAddon;
import com.adsapient.util.maps.Place2SiteMap;

import com.adsapient.gui.forms.FilterActionForm;

import org.apache.log4j.Logger;

import org.apache.struts.action.ActionForm;
import org.apache.struts.util.LabelValueBean;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class ContentFilter implements FilterInterface, IMappable {
	static Logger logger = Logger.getLogger(ContentFilter.class);

	private static final String relatedFiltersQueryName = "getRelatedContentFilterIds";

	private Integer bannerId;

	private Integer campainId;

	private String categorys = ContentFilterAddon.getCategorysAllKeys();

	private Integer contentFilterId;

	private String places = ContentFilterAddon.getPlacesAllKeys();

	private String positions = ContentFilterAddon.getPositionsAllKeys();

	private boolean useAllPlaces = true;

	public boolean isApplyFilter(ContentFilter contFilter) {

		return true;
	}

	public void setCampainId(Integer campainId) {
		this.campainId = campainId;
	}

	public Integer getCampainId() {
		return campainId;
	}

	public void setCategorys(String categorys) {
		this.categorys = categorys;
	}

	public String getCategorys() {
		return categorys;
	}

	public Integer getContentFilterId() {
		return contentFilterId;
	}

	public void setContentFilterId(Integer contentFilterId) {
		this.contentFilterId = contentFilterId;
	}

	public void setPlaces(String places) {
		this.places = places;
	}

	public String getPlaces() {
		return places;
	}

	public void setPositions(String positions) {
		this.positions = positions;
	}

	public String getPositions() {
		return positions;
	}

	public void setUseAllPlaces(boolean useAllPlaces) {
		this.useAllPlaces = useAllPlaces;
	}

	public boolean isUseAllPlaces() {
		return useAllPlaces;
	}

	public void add(FilterInterface filter) {
		if (filter instanceof ContentFilter) {
			ContentFilter contFilterTemplate = (ContentFilter) filter;

			StringBuffer resultCategorysBuffer = new StringBuffer();
			StringBuffer resultPositionsBuffer = new StringBuffer();
			StringBuffer resultPlacesBuffer = new StringBuffer();

			Collection categorysCollection = MyHibernateUtil
					.viewAll(Category.class);

			if (categorysCollection != null) {
				Iterator categorysIter = categorysCollection.iterator();

				while (categorysIter.hasNext()) {
					Category category = (Category) categorysIter.next();
					StringBuffer strBuffer = new StringBuffer();
					strBuffer.append(":").append(category.getId().toString())
							.append(":");

					if ((contFilterTemplate.getCategorys().indexOf(
							strBuffer.toString()) > -1)
							| (this.categorys.indexOf(strBuffer.toString()) > -1)) {
						resultCategorysBuffer.append(strBuffer.toString());
					}
				}
			}

			this.categorys = resultCategorysBuffer.toString();

			Collection positionsCollection = MyHibernateUtil
					.viewAll(PlaceImpl.class);

			for (Iterator positionsIterator = positionsCollection.iterator(); positionsIterator
					.hasNext();) {
				PlaceImpl currentPlace = (PlaceImpl) positionsIterator.next();

				StringBuffer placeIndex = new StringBuffer();
				placeIndex.append(":").append(currentPlace.getPlaceId())
						.append(":");

				if ((this.positions.indexOf(placeIndex.toString()) > -1)
						|| ((contFilterTemplate.getPositions()
								.indexOf(placeIndex.toString())) > -1)) {
					resultPositionsBuffer.append(placeIndex);
				}
			}

			this.positions = resultPositionsBuffer.toString();

			Collection placesCollection = (MyHibernateUtil
					.viewAll(PlacesImpl.class));

			for (Iterator placesIterator = placesCollection.iterator(); placesIterator
					.hasNext();) {
				PlacesImpl currentPlace = (PlacesImpl) placesIterator.next();

				StringBuffer placeIndex = new StringBuffer();
				placeIndex.append(":").append(currentPlace.getId()).append(":");

				if ((this.places.indexOf(placeIndex.toString()) > -1)
						|| ((contFilterTemplate.getPlaces().indexOf(placeIndex
								.toString())) > -1)) {
					resultPlacesBuffer.append(currentPlace);
				}
			}

			this.places = resultPlacesBuffer.toString();
		} else {
			logger.warn("given object isnt instance of contentfilter");
		}
	}

	public void addNew() {
		MyHibernateUtil.addObject(this);
	}

	public Object copy() {
		ContentFilter newContentFilter = new ContentFilter();

		newContentFilter.setCategorys(this.getCategorys());
		newContentFilter.setPlaces(this.getPlaces());
		newContentFilter.setPositions(this.getPositions());
		newContentFilter.setUseAllPlaces(this.useAllPlaces);

		return newContentFilter;
	}

	public ContentFilter clone() throws CloneNotSupportedException {
		ContentFilter filter = (ContentFilter) super.clone();

		return filter;
	}

	public void doAfterFilter(Banner banner, PlacesImpl places,
			StatisticImpl statistic, Map requestMap) {
	}

	public boolean doFilter(HttpServletRequest request) {
		Integer placeId = new Integer(request
				.getParameter(AdsapientConstants.PLACES_ID));
		List<Integer> categoryIds;
		Integer positionId = null;

		if (placeId != null) {
			categoryIds = Place2SiteMap.getCategoryFromPlace(placeId);
			positionId = Place2SiteMap.getPositionIdFromPlaceId(placeId);

			for (Integer categoryId : categoryIds) {
				StringBuffer categoryKey = new StringBuffer().append(":")
						.append(categoryId).append(":");

				if (this.getCategorys().indexOf(categoryKey.toString()) < 0) {
					return false;
				}
			}
		}

		StringBuffer positionKey = new StringBuffer().append(":").append(
				positionId).append(":");
		StringBuffer placeKey = new StringBuffer(":").append(placeId).append(
				":");

		if (this.getPositions().indexOf(positionKey.toString()) < 0) {
			return false;
		}

		if ((this.getPlaces().indexOf(placeKey.toString()) < 0)
				&& (!this.useAllPlaces)) {
			return false;
		}

		return true;
	}

	public void init(HttpServletRequest request, ActionForm actionForm) {
		FilterActionForm form = (FilterActionForm) actionForm;

		Collection categoryCollection = MyHibernateUtil.viewAll(Category.class);

		Iterator iter = categoryCollection.iterator();

		while (iter.hasNext()) {
			Category category = (Category) iter.next();
			LabelValueBean bean = new LabelValueBean(category.getName(),
					category.getId().toString());

			StringBuffer categoryStringBufer = new StringBuffer();

			categoryStringBufer.append(":").append(category.getId()).append(
					"-0").append(":");

			if (this.getCategorys().indexOf(":" + category.getId() + "-") > -1) {
				form.getCategorys2().add(bean);
			}

			form.getCategorys().add(bean);
		}

		form.setCategoriesPriorities(this.getCategorys());

		Collection pagePositionsCollection = MyHibernateUtil
				.viewAll(PlaceImpl.class);

		Iterator pagePositionIterator = pagePositionsCollection.iterator();

		while (pagePositionIterator.hasNext()) {
			PlaceImpl currentPlace = (PlaceImpl) pagePositionIterator.next();

			LabelValueBean placeBean = new LabelValueBean(currentPlace
					.getName(), currentPlace.getPlaceId().toString());

			StringBuffer positionsStringBufer = new StringBuffer();
			positionsStringBufer.append(":").append(currentPlace.getPlaceId())
					.append(":");

			if (this.getPositions().indexOf(positionsStringBufer.toString()) > -1) {
				form.getPagePositionsCollection2().add(placeBean);
			}

			form.getPagePositionsCollection1().add(placeBean);
		}

		form.setRegisteredPlaces(this.getPlaces());

		if (this.bannerId != null) {
			form.setBannerId(this.bannerId.toString());
		}

		form.setAllPlaces(this.useAllPlaces);
		form.setFilterAction("update");
	}

	public void reset() {
		this.categorys = ContentFilterAddon.getCategorysAllKeys();
		this.positions = ContentFilterAddon.getPositionsAllKeys();
		this.useAllPlaces = true;
	}

	public String save() {
		return String.valueOf(MyHibernateUtil.addObject(this));
	}

	public void update(HttpServletRequest request, ActionForm actionForm,
			boolean enableHibernateUpdate) {
		FilterActionForm form = (FilterActionForm) actionForm;

		this.setCategorys(form.getSelectedCategorysName());

		this.setPositions(form.getSelectedPositions());

		this.setPlaces(form.getRegisteredPlaces());

		this.setUseAllPlaces(form.isAllPlaces());

		if (enableHibernateUpdate) {
			MyHibernateUtil.updateObject(this);
		}
	}

	public Integer getId() {
		return contentFilterId;
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
