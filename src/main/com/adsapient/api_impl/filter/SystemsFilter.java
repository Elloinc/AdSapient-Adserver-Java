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

import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.filters.FiltersUtil;

import com.adsapient.gui.actions.FilterAction;
import com.adsapient.gui.forms.FilterActionForm;

import org.apache.log4j.Logger;

import org.apache.struts.action.ActionForm;
import org.apache.struts.util.LabelValueBean;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

public class SystemsFilter implements FilterInterface, IMappable {
	public static Logger logger = Logger.getLogger(SystemsFilter.class);

	private static final String relatedFiltersQueryName = "getRelatedSystemsFilterIds";

	private Integer bannerId;

	private Integer campainId;

	private Set referrersElements = new HashSet();

	private Integer systemsFilterId;

	private String user_browser;

	private String user_system;

	private String user_lang;

	private LabelBeanSorter labelBeanSorter = new LabelBeanSorter();

	public void setCampainId(Integer campainId) {
		this.campainId = campainId;
	}

	public Integer getCampainId() {
		return campainId;
	}

	public void setReferrersElements(Set referrersElements) {
		this.referrersElements = referrersElements;
	}

	public Set getReferrersElements() {
		return referrersElements;
	}

	public Integer getSystemsFilterId() {
		return systemsFilterId;
	}

	public void setSystemsFilterId(Integer systemsFilterId) {
		this.systemsFilterId = systemsFilterId;
	}

	public void setUser_browser(String user_browser) {
		this.user_browser = user_browser;
	}

	public String getUser_browser() {
		return user_browser;
	}

	public void setUser_system(String user_system) {
		this.user_system = user_system;
	}

	public String getUser_system() {
		return user_system;
	}

	public void add(FilterInterface filter) {
		if (filter instanceof SystemsFilter) {
			StringBuffer sb;
			Set<String> set;

			SystemsFilter systemsFilterTemplate = (SystemsFilter) filter;

			StringTokenizer tokenizer = new StringTokenizer(
					systemsFilterTemplate.getUser_browser(), ";");
			set = new HashSet<String>();

			while (tokenizer.hasMoreTokens()) {
				String paramValue = tokenizer.nextToken();

				if (!hasValue(user_browser, paramValue)) {
					set.add(paramValue);
				}
			}

			sb = new StringBuffer(user_browser);

			for (String param : set)
				sb.append(param).append(";");

			user_browser = sb.toString();

			set.clear();
			tokenizer = new StringTokenizer(systemsFilterTemplate
					.getUser_system(), ";");

			while (tokenizer.hasMoreTokens()) {
				String paramValue = tokenizer.nextToken();

				if (!hasValue(user_system, paramValue)) {
					set.add(paramValue);
				}
			}

			sb = new StringBuffer(user_system);

			for (String param : set)
				sb.append(param).append(";");

			user_system = sb.toString();

			set.clear();
			tokenizer = new StringTokenizer(systemsFilterTemplate
					.getUser_lang(), ";");

			while (tokenizer.hasMoreTokens()) {
				String paramValue = tokenizer.nextToken();

				if (!hasValue(user_lang, paramValue)) {
					set.add(paramValue);
				}
			}

			sb = new StringBuffer(user_lang);

			for (String param : set)
				sb.append(param).append(";");

			user_lang = sb.toString();

			Iterator referrersIterator = systemsFilterTemplate
					.getReferrersElements().iterator();

			while (referrersIterator.hasNext()) {
				ReferrersElement element = (ReferrersElement) referrersIterator
						.next();
				ReferrersElement clonetedElement = null;

				try {
					clonetedElement = (ReferrersElement) element.clone();
				} catch (CloneNotSupportedException e) {
					logger.warn("cant clonead Referrers element", e);
				}

				if (clonetedElement != null) {
					clonetedElement.setSystemsFilterId(this
							.getSystemsFilterId());
				}

				MyHibernateUtil.addObject(clonetedElement);
			}
		} else {
			logger.warn("given object isnt instance of SystemsFilter");
		}
	}

	public void addNew() {
		StringBuffer sbUserBrowser = new StringBuffer("");
		StringBuffer sbUserSystem = new StringBuffer("");
		StringBuffer sbUserLangs = new StringBuffer("");

		for (Iterator iter = FiltersUtil.BrowsersMap.entrySet().iterator(); iter
				.hasNext();) {
			Map.Entry entry = (Map.Entry) iter.next();
			sbUserBrowser.append((String) entry.getKey());

			if (iter.hasNext()) {
				sbUserBrowser.append(";");
			}
		}

		for (Iterator iter = FiltersUtil.SystemsMap.entrySet().iterator(); iter
				.hasNext();) {
			Map.Entry entry = (Map.Entry) iter.next();
			sbUserSystem.append((String) entry.getKey());

			if (iter.hasNext()) {
				sbUserSystem.append(";");
			}
		}

		for (Iterator iter = FiltersUtil.LangsMap.entrySet().iterator(); iter
				.hasNext();) {
			Map.Entry entry = (Map.Entry) iter.next();
			sbUserLangs.append((String) entry.getKey());

			if (iter.hasNext()) {
				sbUserLangs.append(";");
			}
		}

		user_browser = sbUserBrowser.toString();
		user_system = sbUserSystem.toString();
		user_lang = sbUserLangs.toString();

		systemsFilterId = MyHibernateUtil.addObject(this);

		Iterator elementsIterator = this.getReferrersElements().iterator();

		while (elementsIterator.hasNext()) {
			ReferrersElement element = (ReferrersElement) elementsIterator
					.next();

			element.setId(null);
			element.setSystemsFilterId(systemsFilterId);

			MyHibernateUtil.addObject(element);
		}
	}

	public Object copy() {
		SystemsFilter newSystemsFilter = new SystemsFilter();

		newSystemsFilter.setCampainId(this.campainId);
		newSystemsFilter.setSystemsFilterId(this.systemsFilterId);

		return newSystemsFilter;
	}

	@Override
	public SystemsFilter clone() throws CloneNotSupportedException {
		SystemsFilter filter = (SystemsFilter) super.clone();
		filter.setReferrersElements(new HashSet());

		if (this.referrersElements != null) {
			for (Iterator iter = this.referrersElements.iterator(); iter
					.hasNext();) {
				ReferrersElement rf = (ReferrersElement) iter.next();
				ReferrersElement n = new ReferrersElement();
				n.setId(rf.getId());
				n.setSystemsFilterId(rf.getSystemsFilterId());
				n.setTarget_url(n.getTarget_url());
				n.setType(rf.type);
				filter.referrersElements.add(n);
			}
		}

		return filter;
	}

	public boolean doFilter(HttpServletRequest request) {
		String user_agent = ((HttpServletRequest) request)
				.getHeader("user-agent");

		if (user_agent == null) {
			return true;
		}

		boolean result = false;
		StringTokenizer tokenizer = new StringTokenizer(user_browser, ";");

		while (tokenizer.hasMoreTokens()) {
			String paramValue = tokenizer.nextToken();

			Pattern pattern = Pattern.compile(paramValue);
			Matcher matcher = pattern.matcher(user_agent);

			if (matcher.find()) {
				result = true;
			}
		}

		if (result) {
			result = false;
			tokenizer = new StringTokenizer(user_system, ";");

			while (tokenizer.hasMoreTokens()) {
				String paramValue = tokenizer.nextToken();

				Pattern pattern = Pattern.compile(paramValue);
				Matcher matcher = pattern.matcher(user_agent);

				if (matcher.find()) {
					result = true;
				}
			}
		}

		if (result) {
			result = false;
			tokenizer = new StringTokenizer(user_lang, ";");

			while (tokenizer.hasMoreTokens()) {
				String paramValue = tokenizer.nextToken();
				Pattern pattern = Pattern.compile(paramValue);
				Matcher matcher = pattern.matcher(user_agent);

				if (matcher.find()) {
					result = true;
				}
			}
		}

		String referer = request.getHeader("referer");

		if (referer == null) {
			referer = request.getParameter("referer");
		}

		if (result && (referer != null)) {
			logger.info("referer:" + referer);

			Iterator elementsIterator = this.getReferrersElements().iterator();

			while (elementsIterator.hasNext()) {
				ReferrersElement element = (ReferrersElement) elementsIterator
						.next();

				if (element.isType() && referer.equals(element.getTarget_url())) {
					result = true;
				}

				if (!element.isType()
						&& referer.equals(element.getTarget_url())) {
					result = false;
				}
			}
		}

		return true;
	}

	public boolean hasValue(String source, String value) {
		if (source != null) {
			StringTokenizer tokenizer = new StringTokenizer(source, ";");

			while (tokenizer.hasMoreTokens()) {
				String paramValue = tokenizer.nextToken();

				if (value.equals(paramValue)) {
					return true;
				}
			}

			return false;
		} else {
			return true;
		}
	}

	public void init(HttpServletRequest request, ActionForm actionForm) {
		init((ServletRequest) request, actionForm);
	}

	public void init(ServletRequest request, ActionForm actionForm) {
		FilterActionForm form = (FilterActionForm) actionForm;

		for (Iterator iter = FiltersUtil.BrowsersMap.entrySet().iterator(); iter
				.hasNext();) {
			Map.Entry entry = (Map.Entry) iter.next();

			LabelValueBean bean = new LabelValueBean((String) entry.getValue(),
					(String) entry.getKey());

			if (hasValue(user_browser, (String) entry.getKey())) {
				form.getUser_browsers2().add(bean);
			}

			form.getUser_browsers().add(bean);
		}

		form.setUser_browsers(sortBeansByValue(form.getUser_browsers()));
		form.setUser_browsers2(sortBeansByValue(form.getUser_browsers2()));

		for (Iterator iter = FiltersUtil.SystemsMap.entrySet().iterator(); iter
				.hasNext();) {
			Map.Entry entry = (Map.Entry) iter.next();

			LabelValueBean bean = new LabelValueBean((String) entry.getValue(),
					(String) entry.getKey());

			if (hasValue(user_system, (String) entry.getKey())) {
				form.getUser_systems2().add(bean);
			}

			form.getUser_systems().add(bean);
		}

		form.setUser_systems(sortBeansByValue(form.getUser_systems()));
		form.setUser_systems2(sortBeansByValue(form.getUser_systems2()));

		for (Iterator iter = FiltersUtil.LangsMap.entrySet().iterator(); iter
				.hasNext();) {
			Map.Entry entry = (Map.Entry) iter.next();

			LabelValueBean bean = new LabelValueBean((String) entry.getValue(),
					(String) entry.getKey());

			if (hasValue(user_lang, (String) entry.getKey())) {
				form.getUser_langs2().add(bean);
			}

			form.getUser_langs().add(bean);
		}

		form.setUser_langs(sortBeansByValue(form.getUser_langs()));
		form.setUser_langs2(sortBeansByValue(form.getUser_langs2()));

		form.setReferrersCollection(this.getReferrersElements());

		form.setFilterAction(FilterAction.UPDATE);
	}

	private Collection sortBeansByValue(Collection beans) {
		LabelValueBean[] lvbb = (LabelValueBean[]) beans
				.toArray(new LabelValueBean[beans.size()]);
		Arrays.sort(lvbb, labelBeanSorter);

		return Arrays.asList(lvbb);
	}

	public void reset() {
		MyHibernateUtil.removeWithCriteria(ReferrersElement.class,
				"systemsFilterId", this.getSystemsFilterId());

		SystemsFilter filter = (SystemsFilter) MyHibernateUtil.loadObject(
				SystemsFilter.class, this.getSystemsFilterId());

		if (filter != null) {
			this.setReferrersElements(filter.getReferrersElements());
		}
	}

	public String save() {
		Integer id = MyHibernateUtil.addObject(this);

		Iterator elementsIterator = this.getReferrersElements().iterator();

		while (elementsIterator.hasNext()) {
			ReferrersElement element = (ReferrersElement) elementsIterator
					.next();

			element.setId(null);
			element.setSystemsFilterId(id);

			MyHibernateUtil.addObject(element);
		}

		return String.valueOf(id);
	}

	public String toString() {
		return new StringBuffer().append("Systems filter id:").append(
				systemsFilterId).append(" campainId:").append(campainId)
				.append(" referers size:")
				.append(getReferrersElements().size()).append(" user browser:")
				.append(user_browser).append(" systems:").append(user_system)
				.append(" user_lang:").append(user_lang).toString();
	}

	public void update(HttpServletRequest request, ActionForm actionForm,
			boolean enableHibernateupdate) {
		FilterActionForm form = (FilterActionForm) actionForm;

		user_browser = form.getSelected_browsers();
		user_system = form.getSelected_systems();
		user_lang = form.getSelected_langs();

		if ("add".equalsIgnoreCase(form.getReferrerEngineAction())) {
			if ((form.getReferrerUrl() != null)
					&& (form.getReferrerUrl().length() > 0)) {
				ReferrersElement element = new ReferrersElement();
				element.setSystemsFilterId(this.getSystemsFilterId());
				element.setTarget_url(form.getReferrerUrl());
				element.setType(form.getReferrerType());

				if (enableHibernateupdate) {
					MyHibernateUtil.addObject(element);
				}

				this.getReferrersElements().add(element);
			}
		}

		if ("remove".equalsIgnoreCase(form.getReferrerEngineAction())) {
			logger.info("remove action:" + form.getReferrerEngineAction()
					+ " ReferrerId:" + form.getReferrerId());

			if ((form.getReferrerId() != null)
					&& (!"".equalsIgnoreCase(form.getReferrerId()))) {
				MyHibernateUtil.removeObject(ReferrersElement.class,
						new Integer(form.getReferrerId()));

				ReferrersElement elementToDelete = getReferrersElement(new Integer(
						form.getReferrerId()));

				if (elementToDelete != null) {
					referrersElements.remove(elementToDelete);
				}
			}
		}

		if (enableHibernateupdate) {
			MyHibernateUtil.updateObject(this);
		}

		form.setReferrerUrl("");
	}

	private ReferrersElement getReferrersElement(Integer referrerId) {
		ReferrersElement result = null;
		Iterator elementsIterator = this.getReferrersElements().iterator();

		while (elementsIterator.hasNext()) {
			ReferrersElement element = (ReferrersElement) elementsIterator
					.next();

			if (element.getId().intValue() == referrerId.intValue()) {
				result = element;
			}
		}

		return result;
	}

	public void doAfterFilter(Banner banner, PlacesImpl places,
			StatisticImpl statistic, Map requestMap) {
	}

	public Integer getId() {
		return systemsFilterId;
	}

	public String getUser_lang() {
		return user_lang;
	}

	public void setUser_lang(String user_lang) {
		this.user_lang = user_lang;
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

	private class LabelBeanSorter implements Comparator {
		public int compare(Object o1, Object o2) {
			LabelValueBean bean1 = (LabelValueBean) o1;
			LabelValueBean bean2 = (LabelValueBean) o2;

			return bean1.getLabel().compareTo(bean2.getLabel());
		}
	}
}
