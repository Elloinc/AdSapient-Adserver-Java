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
package com.adsapient.util.financial;

import com.adsapient.api.RateEnableInterface;

import com.adsapient.api_impl.usermanagment.Financial;
import com.adsapient.api_impl.usermanagment.RateImpl;
import com.adsapient.api_impl.usermanagment.UserImpl;
import com.adsapient.api_impl.usermanagment.role.RoleController;

import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.admin.AdsapientConstants;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FinancialLinksController {
	static Logger logger = Logger.getLogger(Financial.class);

	public static String generateCPLRates(HttpSession session,
			RateEnableInterface rateSupportObject, HttpServletResponse response) {
		StringBuffer result = new StringBuffer();
		RateImpl rate = (RateImpl) MyHibernateUtil.loadObject(RateImpl.class,
				rateSupportObject.getRateId());

		if (rate == null) {
			logger.error("Cant find rate with id="
					+ rateSupportObject.getRateId());

			return "N/A";
		}

		UserImpl parentUser = (UserImpl) session.getAttribute("parent");
		UserImpl user = (UserImpl) session.getAttribute("user");

		if ((parentUser == null)
				&& (!RoleController.ADMIN.equalsIgnoreCase(user.getRole()))) {
			result.append(MoneyTransformer.transformMoney(rate.getCplRate()))
					.append(" | ").append(
							MoneyTransformer.transformMoney(rate.getCpsRate()));

			return result.toString();
		}

		result.append("<a class=\"tabledata\" href=\"").append(
				response.encodeURL("rateManagement.do?rateId="
						+ rate.getRateId())).append("\"  >").append(
				MoneyTransformer.transformMoney(rate.getCplRate())).append(
				" | ").append(
				MoneyTransformer.transformMoney(rate.getCpsRate())).append(
				"</a>");

		return result.toString();
	}

	public static String generateFinancialLink(UserImpl user,
			HttpServletResponse response) {
		StringBuffer resultBuffer = new StringBuffer();

		Financial userFinancial = (Financial) MyHibernateUtil
				.loadObjectWithCriteria(Financial.class, "userId", user.getId());

		if (userFinancial == null) {
			resultBuffer
					.append("<td colspan=\"4\" class=\"tabledata-c\"></td>");
			resultBuffer
					.append("<td colspan=\"4\" class=\"tabledata-c\"></td>");

			return resultBuffer.toString();
		}

		if (RoleController.ADMIN.equalsIgnoreCase(user.getRole())
				|| RoleController.ADVERTISERPUBLISHER.equalsIgnoreCase(user
						.getRole())
				|| RoleController.HOSTEDSERVICE
						.equalsIgnoreCase(user.getRole())) {
			append(resultBuffer, MoneyTransformer.transformMoney(userFinancial
					.getAdvertisingCPMrate()), user, response);
			append(resultBuffer, MoneyTransformer.transformMoney(userFinancial
					.getAdvertisingCPCrate()), user, response);
			append(resultBuffer, MoneyTransformer.transformMoney(userFinancial
					.getAdvertisingCPLrate()), user, response);
			append(resultBuffer, MoneyTransformer.transformMoney(userFinancial
					.getAdvertisingCPSrate()), user, response);
			append(resultBuffer, MoneyTransformer.transformMoney(userFinancial
					.getPublishingCPMrate()), user, response);
			append(resultBuffer, MoneyTransformer.transformMoney(userFinancial
					.getPublishingCPCrate()), user, response);
			append(resultBuffer, MoneyTransformer.transformMoney(userFinancial
					.getPublishingCPLrate()), user, response);
			append(resultBuffer, MoneyTransformer.transformMoney(userFinancial
					.getPublishingCPSrate()), user, response);
		} else if (RoleController.ADVERTISER.equalsIgnoreCase(user.getRole())) {
			append(resultBuffer, MoneyTransformer.transformMoney(userFinancial
					.getAdvertisingCPMrate()), user, response);
			append(resultBuffer, MoneyTransformer.transformMoney(userFinancial
					.getAdvertisingCPCrate()), user, response);
			append(resultBuffer, MoneyTransformer.transformMoney(userFinancial
					.getAdvertisingCPLrate()), user, response);
			append(resultBuffer, MoneyTransformer.transformMoney(userFinancial
					.getAdvertisingCPSrate()), user, response);
			resultBuffer
					.append("<td colspan=\"4\" class=\"tabledata-c\"></td>");
		} else {
			resultBuffer
					.append("<td colspan=\"4\" class=\"tabledata-c\"></td>");
			append(resultBuffer, MoneyTransformer.transformMoney(userFinancial
					.getPublishingCPMrate()), user, response);
			append(resultBuffer, MoneyTransformer.transformMoney(userFinancial
					.getPublishingCPCrate()), user, response);
			append(resultBuffer, MoneyTransformer.transformMoney(userFinancial
					.getPublishingCPLrate()), user, response);
			append(resultBuffer, MoneyTransformer.transformMoney(userFinancial
					.getPublishingCPSrate()), user, response);
		}

		return resultBuffer.toString();
	}

	public static String generateRates(HttpSession session,
			RateEnableInterface rateSupportObject, HttpServletResponse response) {
		StringBuffer result = new StringBuffer();
		RateImpl rate = (RateImpl) MyHibernateUtil.loadObject(RateImpl.class,
				rateSupportObject.getRateId());

		if (rate == null) {
			logger.error("Cant find rate with id="
					+ rateSupportObject.getRateId());

			return "n/a";
		}

		UserImpl parentUser = (UserImpl) session
				.getAttribute(AdsapientConstants.PARENT_USER);
		UserImpl user = (UserImpl) session
				.getAttribute(AdsapientConstants.USER);

		if ((parentUser == null)
				&& (!RoleController.ADMIN.equalsIgnoreCase(user.getRole()))) {
			result.append(MoneyTransformer.transformMoney(rate.getCpmRate()))
					.append(" | ").append(
							MoneyTransformer.transformMoney(rate.getCpcRate()));

			return result.toString();
		}

		result.append("<a class=\"tabledata\" href=\"").append(
				response.encodeURL("rateManagement.do?rateId="
						+ rate.getRateId())).append("\"  >").append(
				MoneyTransformer.transformMoney(rate.getCpmRate())).append(
				" | ").append(
				MoneyTransformer.transformMoney(rate.getCpcRate())).append(
				"</a>");

		return result.toString();
	}

	private static final void append(StringBuffer buffer, float data,
			UserImpl user, HttpServletResponse response) {
		buffer.append("<td class=\"tabledata-c\">").append(
				"<a class=\"tabledata\" href=\"").append(
				response.encodeURL("financialManagement.do?action=view&userId="
						+ user.getId())).append("\" >").append(data).append(
				"</a></td>");
	}
}
