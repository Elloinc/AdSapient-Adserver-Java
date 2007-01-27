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
package com.adsapient.api_impl.usability.common;

import com.adsapient.api.StatisticInterface;

import com.adsapient.api_impl.statistic.common.StatisticRequestParameter;
import com.adsapient.api_impl.statistic.engine.StatisticBuilder;
import com.adsapient.api_impl.usermanagment.UserImpl;
import com.adsapient.api_impl.usermanagment.role.RoleController;

import com.adsapient.util.Msg;
import com.adsapient.util.financial.FinancialLinksController;
import com.adsapient.util.financial.MoneyTransformer;

import java.util.ArrayList;
import java.util.Collection;

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
		return Msg.fetch(user.getShortRole(), request);
	}

	public String generateFinancialLink(HttpServletResponse response) {
		return FinancialLinksController.generateFinancialLink(user, response);
	}

	public String getBalance(HttpServletResponse response) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<a class=\"tabledata\" href=\"").append(
				response.encodeURL("viewAccount.do?action=accountView&userId="
						+ user.getId())).append("\">").append(
				MoneyTransformer.transformMoney(user.getAccountMoney()))
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
				.append(Msg.fetch("login", request)).append("></a>");

		return buffer.toString();
	}

	public String generateAdvStatistic() {
		StringBuffer buffer = new StringBuffer();

		if (RoleController.PUBLISHER.equals(user.getRole())) {
			buffer.append("<td class=\"tabledata-c\" colspan=2></td>");
		} else {
			buffer.append("<td class=\"tabledata-c\">").append(
					getStatistic(StatisticInterface.ADVERTISER_ID_COLUMN, user
							.getId().toString(),
							StatisticBuilder.IMPRESSION_CLASS)).append("</td>");
			buffer.append("<td class=\"tabledata-c\">").append(
					getStatistic(StatisticInterface.ADVERTISER_ID_COLUMN, user
							.getId().toString(), StatisticBuilder.CLICK_CLASS))
					.append("</td>");
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

		if (RoleController.ADVERTISER.equalsIgnoreCase(user.getRole())) {
			buffer.append("<td colspan=2 class=\"tabledata-c\"></td>");
		} else {
			buffer.append("<td class=\"tabledata-c\">").append(
					getStatistic(StatisticInterface.PUBLISHER_ID_COLUMN, user
							.getId().toString(),
							StatisticBuilder.IMPRESSION_CLASS)).append("</td>");
			buffer.append("<td class=\"tabledata-c\">").append(
					getStatistic(StatisticInterface.PUBLISHER_ID_COLUMN, user
							.getId().toString(), StatisticBuilder.CLICK_CLASS))
					.append("</td>");
		}

		return buffer.toString();
	}

	private int getStatistic(String statisticColumn, String value,
			Class statisticClass) {
		Collection parameters = new ArrayList();
		parameters.add(new StatisticRequestParameter(statisticColumn,
				StatisticRequestParameter.EQUAL, value));

		int result = StatisticBuilder.getStatistic(statisticClass,
				StatisticBuilder.COUNT_TYPE, statisticColumn, parameters);

		return result;
	}
}
