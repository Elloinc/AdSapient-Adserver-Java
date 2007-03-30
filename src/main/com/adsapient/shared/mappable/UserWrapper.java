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

import com.adsapient.shared.service.I18nService;
import com.adsapient.shared.AdsapientConstants;
import com.adsapient.shared.dao.HibernateEntityDao;
import com.adsapient.shared.service.LinkHelperService;
import com.adsapient.shared.service.FinancialsService;
import com.adsapient.gui.ContextAwareGuiBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserWrapper {
	private UserImpl user;

	public UserWrapper(UserImpl user) {
		this.user = user;
	}

	public Integer getId() {
		return user.getId();
	}

	public String getName() {
		return user.getName();
	}

	public String getRole(HttpServletRequest request) {
		return I18nService.fetch(user.getShortRole(), request);
	}

	public String generateFinancialLink(HttpServletResponse response) {
		LinkHelperService linkHelperService = (LinkHelperService) ContextAwareGuiBean.getContext().getBean("linkHelperService");
        return linkHelperService.generateFinancialLink(user, response);
	}

	public String getBalance(HttpServletResponse response) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<a class=\"tabledata\" href=\"").append(
				response.encodeURL("viewAccount.do?action=accountView&userId="
						+ user.getId())).append("\">").append(
				FinancialsService.transformMoney(user.getAccountMoney()))
				.append("</a>");

		return buffer.toString();
	}

	public String getLoginHref(HttpServletRequest request,
			HttpServletResponse response) {
		StringBuffer buffer = new StringBuffer();
		buffer
				.append("<a target=\"_blank\" class=\"tabledata\" href=\"")
				.append(
						response.encodeURL("relogin?login=" + user.getLogin()
								+ "&password=" + user.getPassword()))
				.append(
						"\"><img src=\"images/icons/login.png\" border=\"0\" title=")
				.append(I18nService.fetch("login", request)).append("></a>");

		return buffer.toString();
	}

	public String generateAdvStatistic() {
		StringBuffer buffer = new StringBuffer();

		if (AdsapientConstants.PUBLISHER.equals(user.getRole())) {
			buffer.append("<td class=\"tabledata-c\" colspan=2></td>");
		} else {
		}

		return buffer.toString();
	}

	public UserImpl getUser() {
		return user;
	}

	public void setUser(UserImpl user) {
		this.user = user;
	}

	public String generatePubStatistic() {
		StringBuffer buffer = new StringBuffer();

		if (AdsapientConstants.ADVERTISER.equalsIgnoreCase(user.getRole())) {
			buffer.append("<td colspan=2 class=\"tabledata-c\"></td>");
		} else {
		}

		return buffer.toString();
	}

	private int getStatistic(String statisticColumn, String value,
			Class statisticClass) {
		return 0;
	}
}
