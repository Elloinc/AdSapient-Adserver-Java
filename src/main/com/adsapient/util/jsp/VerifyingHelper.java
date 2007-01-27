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
package com.adsapient.util.jsp;

import com.adsapient.api_impl.advertizer.CampainImpl;
import com.adsapient.api_impl.advertizer.CampainManagerImpl;
import com.adsapient.api_impl.publisher.SiteImpl;
import com.adsapient.api_impl.usermanagment.UserImpl;

import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.admin.ConfigurationConstants;
import com.adsapient.util.maps.Place2SiteMap;

import com.adsapient.gui.forms.VerificationActionForm;

import org.apache.log4j.Logger;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

public class VerifyingHelper {
	static Logger logger = Logger.getLogger(VerifyingHelper.class);

	public static final String CAMPAIGN = "campaign";

	public static final String USER = "user";

	public static final String STATE_ID = "stateId";

	public static final String SITE = "site";

	public static String getCount(String type) {
		int count = 0;
		StringBuffer buffer = new StringBuffer("(");

		if (SITE.equalsIgnoreCase(type)) {
			count = loadCollection(SiteImpl.class, "siteId").size();
		}

		if (CAMPAIGN.equalsIgnoreCase(type)) {
			count = loadCollection(CampainImpl.class, "campainId").size();
		}

		if (USER.equalsIgnoreCase(type)) {
			count = loadCollection(UserImpl.class, "id").size();
		}

		buffer.append(count);

		return buffer.append(")").toString();
	}

	public static void initVeriFyingForm(VerificationActionForm form,
			HttpServletRequest request) {
		if (SITE.equalsIgnoreCase(form.getType())) {
			Collection resultCollection = loadCollection(SiteImpl.class,
					"siteId");

			form.setVerifyingCollection(resultCollection);
		}

		if (USER.equalsIgnoreCase(form.getType())) {
			Collection resultCollection = loadCollection(UserImpl.class, "id");

			form.setVerifyingCollection(resultCollection);
		}

		if (CAMPAIGN.equalsIgnoreCase(form.getType())) {
			Collection resultCollection = loadCollection(CampainImpl.class,
					"campainId");

			form.setVerifyingCollection(resultCollection);
		}
	}

	public static void verifyState(VerificationActionForm form) {
		if (CAMPAIGN.equalsIgnoreCase(form.getType())) {
			if (form.getElementId() != null) {
				CampainImpl campain = (CampainImpl) MyHibernateUtil.loadObject(
						CampainImpl.class, new Integer(form.getElementId()));

				if (campain != null) {
					CampainManagerImpl.updateCampainState(campain,
							ConfigurationConstants.DEFAULT_VERIFYING_STATE_ID);
				} else {
					logger
							.warn("trying to verify campain with id that not exist="
									+ form.getElementId());
				}
			} else {
				logger
						.warn("trying to call verify but campainId was not specifik");
			}
		}

		if (SITE.equalsIgnoreCase(form.getType())) {
			if (form.getElementId() != null) {
				SiteImpl site = (SiteImpl) MyHibernateUtil.loadObject(
						SiteImpl.class, new Integer(form.getElementId()));

				if (site != null) {
					site
							.setStateId(ConfigurationConstants.DEFAULT_VERIFYING_STATE_ID);

					MyHibernateUtil.updateObject(site);

					Place2SiteMap.reload();
				}
			}
		}

		if (USER.equalsIgnoreCase(form.getType())) {
			if (form.getElementId() != null) {
				UserImpl user = (UserImpl) MyHibernateUtil.loadObject(
						UserImpl.class, new Integer(form.getElementId()));

				if (user != null) {
					user
							.setStateId(ConfigurationConstants.DEFAULT_VERIFYING_STATE_ID);

					MyHibernateUtil.updateObject(user);
				}
			}
		}
	}

	private static Collection loadCollection(Class verifyongClass,
			String orderField) {
		Collection resultCollection = MyHibernateUtil.viewWithCriteria(
				verifyongClass, STATE_ID, new Integer(
						ConfigurationConstants.DEFAULT_NOT_VERIFY_STATE_ID),
				orderField);

		return resultCollection;
	}
}
