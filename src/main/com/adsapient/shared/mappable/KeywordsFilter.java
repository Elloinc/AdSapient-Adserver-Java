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
import com.adsapient.api.IMappable;

import com.adsapient.shared.service.CookieManagementService;

import com.adsapient.shared.AdsapientConstants;
import com.adsapient.shared.dao.HibernateEntityDao;

import com.adsapient.gui.actions.FilterAction;
import com.adsapient.gui.forms.FilterActionForm;
import com.adsapient.gui.ContextAwareGuiBean;

import org.apache.log4j.Logger;

import org.apache.struts.action.ActionForm;

import org.hibernate.util.XMLHelper;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

public class KeywordsFilter implements FilterInterface, IMappable {
	public static Logger logger = Logger.getLogger(KeywordsFilter.class);

	private static final String relatedFiltersQueryName = "getRelatedKeywordsFilterIds";

	private Integer bannerId;

	private Integer campainId;

	private Set<KeyWordsFilterElement> keyWordElements = new HashSet<KeyWordsFilterElement>();

	private Integer keyWordFilterId;

	public void setCampainId(Integer campainId) {
		this.campainId = campainId;
	}

	public Integer getCampainId() {
		return campainId;
	}

	public void setKeyWordElements(Set<KeyWordsFilterElement> keyWordElements) {
		this.keyWordElements = keyWordElements;
		new XMLHelper();
	}

	public Set<KeyWordsFilterElement> getKeyWordElements() {
		return keyWordElements;
	}

	public Integer getKeyWordFilterId() {
		return keyWordFilterId;
	}

	public void setKeyWordFilterId(Integer keyWordFilterId) {
		this.keyWordFilterId = keyWordFilterId;
	}

	public void add(FilterInterface filterInterface) {
        HibernateEntityDao hibernateEntityDao = (HibernateEntityDao)ContextAwareGuiBean.getContext().getBean("hibernateEntityDao");
        if (filterInterface instanceof KeywordsFilter) {
			KeywordsFilter keywordFilter = (KeywordsFilter) filterInterface;
			Iterator keyWordsIterator = keywordFilter.getKeyWordElements()
					.iterator();

			while (keyWordsIterator.hasNext()) {
				KeyWordsFilterElement element = (KeyWordsFilterElement) keyWordsIterator
						.next();
				KeyWordsFilterElement clonetedElement = null;

				clonetedElement = (KeyWordsFilterElement) element.clone();

				if (clonetedElement != null) {
					clonetedElement.setKeywordsFilterId(this
							.getKeyWordFilterId());
				}

				hibernateEntityDao.save(clonetedElement);
			}
		} else {
			logger.warn("given class isntn instance of keyword filter");
		}
	}

	public void addNew() {
        HibernateEntityDao hibernateEntityDao = (HibernateEntityDao)ContextAwareGuiBean.getContext().getBean("hibernateEntityDao");
        hibernateEntityDao.save(this);
	}

	public Object copy() throws CloneNotSupportedException {
		return super.clone();
	}

    public KeywordsFilter clone() throws CloneNotSupportedException {
		KeywordsFilter filter = new KeywordsFilter();
		filter.setBannerId(this.bannerId);
		filter.setCampainId(this.campainId);

		if ((this.keyWordElements != null) && (this.keyWordElements.size() > 0)) {
			for (Iterator<KeyWordsFilterElement> iter = this.keyWordElements
					.iterator(); iter.hasNext();) {
				KeyWordsFilterElement old = iter.next();
				KeyWordsFilterElement n = new KeyWordsFilterElement();
				n.setAverage(old.getAverage());
				n.setClicks(old.getClicks());
				n.setImpressions(old.getImpressions());
				n.setKeyWordSet(old.getKeyWordSet());
				filter.keyWordElements.add(n);
			}
		}

		return filter;
	}

	public void doAfterFilter(Banner banner, PlacesImpl places,
			StatisticImpl statistic, Map requestMap) {
	}

	public boolean doFilter(HttpServletRequest request) {
		Integer placesId = new Integer(request
				.getParameter(AdsapientConstants.PLACES_ID));

		PlacesImpl places = null;//Place2SiteMap.getPlaceFromId(placesId);

		if (!places.isKeywords()) {
			if (this.getKeyWordElements().size() > 0) {
				return false;
			} else {
				return true;
			}
		} else {
			if (this.getKeyWordElements().size() == 0) {
				return false;
			}

			String adplacesKeyword = request
					.getParameter(AdsapientConstants.KEYWORD_PARAMETER);

			if ((adplacesKeyword == null) || (adplacesKeyword.length() == 0)) {
				return false;
			}

			adplacesKeyword = adplacesKeyword.toLowerCase();

			Iterator textEnginesIterator = this.getKeyWordElements().iterator();

			while (textEnginesIterator.hasNext()) {
				KeyWordsFilterElement element = (KeyWordsFilterElement) textEnginesIterator
						.next();
				StringTokenizer tokenizer = new StringTokenizer(
						element.keyWordSet, ";");

				while (tokenizer.hasMoreTokens()) {
					String engineKeyword = tokenizer.nextToken().toLowerCase();

					StringTokenizer adplacesKeywordTokenizer = new StringTokenizer(
							adplacesKeyword, " ");

					while (adplacesKeywordTokenizer.hasMoreTokens()) {
						String adplacesTokenizedKeyWord = adplacesKeywordTokenizer
								.nextToken();

						if (adplacesTokenizedKeyWord
								.equalsIgnoreCase(engineKeyword)) {
							logger.info("find campaign with keyword="
									+ engineKeyword
									+ " and adplaces keyword is "
									+ adplacesKeyword);

							return true;
						}
					}
				}
			}

			return false;
		}
	}

	public void init(HttpServletRequest request, ActionForm actionForm) {
		FilterActionForm form = (FilterActionForm) actionForm;
		form.setTextEnginesCollection(this.getKeyWordElements());
		form.setImpressions(0);
		form.setClicks(0);
		form.setAverage(0);

		if (this.bannerId != null) {
			form.setBannerId(this.bannerId.toString());
		}

		form.setFilterAction(FilterAction.UPDATE);
	}

	public void reset() {
        HibernateEntityDao hibernateEntityDao = (HibernateEntityDao)ContextAwareGuiBean.getContext().getBean("hibernateEntityDao");
        hibernateEntityDao.removeWithCriteria(KeyWordsFilterElement.class,
				"KeywordsFilterId", this.getKeyWordFilterId());

		KeywordsFilter filter = (KeywordsFilter) hibernateEntityDao.loadObject(
				KeywordsFilter.class, this.getKeyWordFilterId());

		if (filter != null) {
			this.setKeyWordElements(filter.getKeyWordElements());
		}
	}

	public String save() {
        HibernateEntityDao hibernateEntityDao = (HibernateEntityDao)ContextAwareGuiBean.getContext().getBean("hibernateEntityDao");
        Integer id = (Integer) hibernateEntityDao.save(this);

		Iterator elementsIterator = this.getKeyWordElements().iterator();

		while (elementsIterator.hasNext()) {
			KeyWordsFilterElement element = (KeyWordsFilterElement) elementsIterator
					.next();

			element.setId(null);
			element.setKeywordsFilterId(id);

			hibernateEntityDao.save(element);
		}

		return String.valueOf(id);
	}

	public void update(HttpServletRequest request, ActionForm actionForm,
			boolean enableHibernateupdate) {
        HibernateEntityDao hibernateEntityDao = (HibernateEntityDao)ContextAwareGuiBean.getContext().getBean("hibernateEntityDao");
        FilterActionForm form = (FilterActionForm) actionForm;

		if ("add".equalsIgnoreCase(form.getTextEngineAction())) {
			KeyWordsFilterElement element = new KeyWordsFilterElement();
			element.setKeywordsFilterId(this.getKeyWordFilterId());
			element.setKeyWordSet(form.getTextEngine());
			element.setImpressions(form.getImpressions());
			element.setClicks(form.getClicks());
			element.setAverage(form.getAverage());

			if (enableHibernateupdate) {
				hibernateEntityDao.save(element);
			} else {
				this.getKeyWordElements().add(element);
			}
		}

		if ("remove".equalsIgnoreCase(form.getTextEngineAction())) {
			if ((form.getTextEngineId() != null)) {
				hibernateEntityDao.removeObject(KeyWordsFilterElement.class, form
						.getTextEngineId());
			}

			form.setTextEngineAction("add");
		}

		if (enableHibernateupdate) {
			KeywordsFilter filter = (KeywordsFilter) hibernateEntityDao
					.loadObject(KeywordsFilter.class, this.getKeyWordFilterId());

			if (filter != null) {
				this.setKeyWordElements(filter.getKeyWordElements());
			}
		}

		form.setTextEngine("");
	}

	public Integer getId() {
		return keyWordFilterId;
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
