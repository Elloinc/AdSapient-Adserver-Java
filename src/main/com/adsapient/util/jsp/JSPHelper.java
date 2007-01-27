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

import com.adsapient.api.AdsapientException;

import com.adsapient.api_impl.filter.factory.CampainFilters;
import com.adsapient.api_impl.filter.factory.FiltersFactory;
import com.adsapient.api_impl.usermanagment.UserImpl;
import com.adsapient.api_impl.usermanagment.role.RoleController;

import com.adsapient.util.Msg;
import com.adsapient.util.admin.AdsapientConstants;

import com.adsapient.gui.forms.FilterActionForm;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JSPHelper {
	Logger logger = Logger.getLogger(JSPHelper.class);

	public static boolean checkResetAccess(HttpServletRequest request) {
		UserImpl user = (UserImpl) request.getSession().getAttribute(
				AdsapientConstants.USER);

		if ((user != null)
				&& (RoleController.ADMIN.equalsIgnoreCase(user.getRole()))) {
			return true;
		}

		UserImpl parentUser = (UserImpl) request.getSession().getAttribute(
				AdsapientConstants.PARENT_USER);

		if ((parentUser != null)
				&& (RoleController.ADMIN.equalsIgnoreCase(parentUser.getRole()))) {
			return true;
		}

		return false;
	}

	public static String generateAdsapientOptionsCollection(String fieldName,
			String className, Collection labelsCollection) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<select name=\"").append("campainStateId").append(
				"\" class=\"").append("defType").append("\">");

		Iterator labelsCollectionIterator = labelsCollection.iterator();

		while (labelsCollectionIterator.hasNext()) {
			AdsapientLabelValueBean bean = (AdsapientLabelValueBean) labelsCollectionIterator
					.next();

			buffer.append("<option value=\"").append(bean.getValue()).append(
					"\"");

			if (bean.isSelected()) {
				buffer.append(" selected=\"selected\"");
			}

			buffer.append(" > ").append(bean.getLabel()).append("</option>");
		}

		buffer.append("</select>");

		return buffer.toString();
	}

	public static String generateFiltersHeader(FilterActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws NumberFormatException, AdsapientException {
		StringBuffer resultHref = new StringBuffer();

		CampainFilters filters = FiltersFactory.createFakeFilters();
		Iterator filtersIterator = filters.getFiltersMap().entrySet()
				.iterator();

		while (filtersIterator.hasNext()) {
			Map.Entry filterElement = (Map.Entry) filtersIterator.next();

			String filterType = (String) filterElement.getKey();
			resultHref
					.append("<td><table cellspacing=0 border=0 cellpadding=0><tr>");

			if (!form.getFilterType().equalsIgnoreCase(filterType)) {
				resultHref
						.append("<td width=\"9\" height=\"18\" ")
						.append(
								"style=\"background-image: url(images/tab4.gif);\"></td>")
						.append(
								"<td class=\"tabledata\" style=\"background-image: url(images/tab5.gif);\">");

				StringBuffer sbUrl = new StringBuffer("filter.do?filterType=")
						.append(filterType).append("&campainId=").append(
								form.getCampainId());

				if ((form.getBannerId() != null)
						&& (form.getBannerId().length() > 0)) {
					sbUrl.append("&bannerId=").append(form.getBannerId());
				}

				resultHref
						.append("<nobr>&nbsp;&nbsp;&nbsp;")
						.append("<a class=\"tabdata\" href=\"")
						.append(response.encodeURL(sbUrl.toString()))
						.append("\">")
						.append(Msg.fetch(filterType, request))
						.append("</a>&nbsp;&nbsp;&nbsp;</nobr></td>")
						.append(
								"<td width=1 height=18  style=\"background-image: url(images/tab3.gif);\"></td>")
						.append("</tr></table></td>");
			} else {
				resultHref
						.append("<td width=\"12\" height=\"18\" ")
						.append(
								"style=\"background-image: url(images/tab1.gif);\"></td>")
						.append(
								"<td class=\"tabledata\" style=\"background-image: url(images/tab2.gif);\">");
				resultHref
						.append("<nobr>&nbsp;&nbsp;&nbsp;")
						.append(Msg.fetch(filterType, request))
						.append("&nbsp;&nbsp;&nbsp;</nobr></td>")
						.append(
								"<td width=1 height=18  style=\"background-image: url(images/tab3.gif);\"></td>")
						.append("</tr></table></td>");
			}
		}

		return resultHref.toString();
	}

	public static String generateFiltersTempletHeader(FilterActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws NumberFormatException, AdsapientException {
		StringBuffer resultHref = new StringBuffer();

		CampainFilters filters = FiltersFactory.createFakeFilters();
		Iterator filtersIterator = filters.getFiltersMap().entrySet()
				.iterator();

		while (filtersIterator.hasNext()) {
			Map.Entry filterElement = (Map.Entry) filtersIterator.next();

			String filterType = (String) filterElement.getKey();
			resultHref
					.append("<td><table cellspacing=0 border=0 cellpadding=0><tr>");

			if (!form.getFilterType().equalsIgnoreCase(filterType)) {
				resultHref
						.append("<td width=\"9\" height=\"18\" ")
						.append(
								"style=\"background-image: url(images/tab4.gif);\"></td>")
						.append(
								"<td class=\"tabledata\" style=\"background-image: url(images/tab5.gif);\">");
				resultHref
						.append("<nobr>&nbsp;&nbsp;&nbsp;")
						.append("<a class=\"tabdata\" href=\"")
						.append(
								response
										.encodeURL("filtersTemplate.do?filterType="
												+ filterType))
						.append("\">")
						.append(Msg.fetch(filterType, request))
						.append("</a>&nbsp;&nbsp;&nbsp;</nobr></td>")
						.append(
								"<td width=1 height=18  style=\"background-image: url(images/tab3.gif);\"></td>")
						.append("</tr></table></td>");
			} else {
				resultHref
						.append("<td width=\"12\" height=\"18\" ")
						.append(
								"style=\"background-image: url(images/tab1.gif);\"></td>")
						.append(
								"<td class=\"tabledata\" style=\"background-image: url(images/tab2.gif);\">");
				resultHref
						.append("<nobr>&nbsp;&nbsp;&nbsp;")
						.append(Msg.fetch(filterType, request))
						.append("&nbsp;&nbsp;&nbsp;</nobr></td>")
						.append(
								"<td width=1 height=18  style=\"background-image: url(images/tab3.gif);\"></td>")
						.append("</tr></table></td>");
			}
		}

		return resultHref.toString();
	}
}
