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

import com.adsapient.api_impl.managment.CookieManager;
import com.adsapient.api_impl.publisher.PlacesImpl;
import com.adsapient.api_impl.statistic.StatisticImpl;
import com.adsapient.api_impl.usermanagment.UserImpl;
import com.adsapient.api_impl.usermanagment.role.RoleController;

import com.adsapient.shared.api.entity.IMappable;

import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.admin.AdsapientConstants;

import com.adsapient.gui.forms.FilterActionForm;

import org.apache.log4j.Logger;

import org.apache.struts.action.ActionForm;
import org.apache.struts.util.LabelValueBean;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

public class BehaviorFilter implements FilterInterface, IMappable {
	static Logger logger = Logger.getLogger(BehaviorFilter.class);

	private static final String relatedFiltersQueryName = "getRelatedBehaviorFilterIds";

	private Integer bannerId;

	private Integer campainId;

	private String behaviorPatterns = "";

	private Integer id;

	public void setBehaviorPatterns(String behaviorPatterns) {
		this.behaviorPatterns = behaviorPatterns;
	}

	public String getBehaviorPatterns() {
		return behaviorPatterns;
	}

	public void setCampainId(Integer campainId) {
		this.campainId = campainId;
	}

	public Integer getCampainId() {
		return campainId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void add(FilterInterface filterInterface) {
		if (filterInterface instanceof BehaviorFilter) {
			BehaviorFilter anotherFilter = (BehaviorFilter) filterInterface;
			this.behaviorPatterns += anotherFilter.getBehaviorPatterns();
		} else {
			logger
					.warn("Trying to add filter that not instanceof behavior filter");
		}
	}

	public void addNew() {
		MyHibernateUtil.addObject(this);
	}

	public Object copy() {
		BehaviorFilter filter = new BehaviorFilter();
		filter.setBehaviorPatterns(this.getBehaviorPatterns());

		return filter;
	}

	@Override
	public BehaviorFilter clone() throws CloneNotSupportedException {
		BehaviorFilter filter = new BehaviorFilter();
		filter.setBannerId(this.bannerId);
		filter.setBehaviorPatterns(this.behaviorPatterns);
		filter.setCampainId(this.campainId);
		filter.setId(this.id);

		return filter;
	}

	public void doAfterFilter(Banner banner, PlacesImpl places,
			StatisticImpl statistic, Map requestMap) {
	}

	public boolean doFilter(HttpServletRequest request) {
		StringTokenizer tokenizer = new StringTokenizer(this
				.getBehaviorPatterns(), ":");

		if (!tokenizer.hasMoreElements()) {
			return true;
		}

		if (CookieManager.isUserUnique(request)) {
			return false;
		}

		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			BehaviorPattern pattern = (BehaviorPattern) MyHibernateUtil
					.loadObject(BehaviorPattern.class, new Integer(token));

			if (!pattern.doFilter(request)) {
				return false;
			}
		}

		return true;
	}

	public void init(HttpServletRequest request, ActionForm actionForm) {
		FilterActionForm form = (FilterActionForm) actionForm;
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

		Collection userBehaviorPatternsCollection = MyHibernateUtil
				.viewWithCriteria(BehaviorPattern.class, "userId", userId,
						"userId");

		Iterator userBehaviorPatternsIterator = userBehaviorPatternsCollection
				.iterator();

		while (userBehaviorPatternsIterator.hasNext()) {
			BehaviorPattern pattern = (BehaviorPattern) userBehaviorPatternsIterator
					.next();

			form.getElements().add(
					new LabelValueBean(pattern.getName(), pattern.getId()
							.toString()));

			StringBuffer stringBuffer = new StringBuffer();

			stringBuffer.append(":").append(pattern.getId()).append(":");

			if (this.behaviorPatterns.indexOf(stringBuffer.toString()) > -1) {
				form.getSelectedElements().add(
						new LabelValueBean(pattern.getName(), pattern.getId()
								.toString()));
			}
		}

		if (this.bannerId != null) {
			form.setBannerId(this.bannerId.toString());
		}

		form.setFilterAction("update");
	}

	public void reset() {
		this.behaviorPatterns = "";
	}

	public String save() {
		return String.valueOf(MyHibernateUtil.addObject(this));
	}

	public void update(HttpServletRequest request, ActionForm actionForm,
			boolean enableHibernateupdate) {
		FilterActionForm form = (FilterActionForm) actionForm;
		this.setBehaviorPatterns(form.getSelectedElementsValue());

		if (enableHibernateupdate) {
			MyHibernateUtil.updateObject(this);
		}
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
