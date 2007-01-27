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

import com.adsapient.api_impl.admin.SystemCurrency;

import com.adsapient.util.MyHibernateUtil;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.Iterator;

public class MoneyTransformer {
	private static Logger logger = Logger.getLogger(MoneyTransformer.class);

	public static SystemCurrency getSystemCurrency() {
		Collection currencyCollection = MyHibernateUtil
				.viewAll(SystemCurrency.class);
		Iterator currencyIterator = currencyCollection.iterator();

		while (currencyIterator.hasNext()) {
			SystemCurrency currency = (SystemCurrency) currencyIterator.next();

			if (currency.isSystem()) {
				return currency;
			}
		}

		return null;
	}

	public static Integer convert2Money(float money) {
		Integer result;
		result = new Integer((int) (money * 1000));

		return result;
	}

	public static float transformMoney(Integer money) {
		float result = 0;

		if (money != null) {
			result = (float) (money.floatValue() / 1000);

			return result;
		} else {
			logger.error("trying to transform null. Return 0 as result");

			return result;
		}
	}

	public static float transformMoney(int money) {
		float result = 0;

		result = ((float) money) / 1000;

		return result;
	}
}
