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
package com.adsapient.util.admin.email;

import com.adsapient.api_impl.usermanagment.BillingInfoImpl;
import com.adsapient.api_impl.usermanagment.UserImpl;

import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.financial.MoneyTransformer;

import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;

import java.net.URLEncoder;

public class EmailFormer {
	private static final Logger logger = Logger.getLogger(EmailFormer.class);

	public static boolean sendPublisherRequest(UserImpl publisher) {
		BillingInfoImpl billingInfor = (BillingInfoImpl) MyHibernateUtil
				.loadObject(BillingInfoImpl.class, new Integer(0));
		BillingInfoImpl publisherInfo = (BillingInfoImpl) MyHibernateUtil
				.loadObjectWithCriteria(BillingInfoImpl.class, "userId",
						publisher.getId());

		String publisherMoney = Float.toString(MoneyTransformer
				.transformMoney(publisher.getAccountMoney()));

		StringBuffer emaillBody = new StringBuffer("");
		emaillBody.append("Hello,\n");
		emaillBody
				.append("Publisher #xxx requests payout. Please visit the following link to make money transaction.\n ");

		try {
			emaillBody
					.append(
							"https://www.paypal.com/cgi-bin/webscr?cmd=_xclick&business=")
					.append(
							URLEncoder.encode(publisherInfo.getPayPalLogin(),
									"UTF-8")).append("&item_name=").append(
							URLEncoder.encode("Publishing payments", "UTF-8"))
					.append("&item_number=0&amount=").append(
							URLEncoder.encode(publisherMoney, "UTF-8")).append(
							"&no_shipping=0&no_note=1&currency_code=").append(
							MoneyTransformer.getSystemCurrency()
									.getCurrencyCode()).append(
							"&bn=PP%2dBuyNowBF&charset=UTF%2d8");
		} catch (UnsupportedEncodingException ex) {
			logger.error("encode problem ", ex);

			return false;
		}

		emaillBody.append("\nRegards, \n").append("Your AdSapient ");
		EmailSender.sendEmail(emaillBody.toString(), "Publisher #xx"
				+ publisher.getId() + " requests payout  ", billingInfor
				.getPayPalLogin());

		return true;
	}
}
