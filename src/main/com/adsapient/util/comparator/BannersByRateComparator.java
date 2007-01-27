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
package com.adsapient.util.comparator;

import com.adsapient.api.RateEnableInterface;

import com.adsapient.api_impl.usermanagment.RateImpl;

import org.apache.log4j.Logger;

import java.util.Comparator;

public class BannersByRateComparator implements Comparator {
	static Logger logger = Logger.getLogger(BannersByRateComparator.class);

	public int compare(Object object1, Object object2) {
		if ((object1 instanceof RateEnableInterface)
				&& (object2 instanceof RateEnableInterface)) {
			RateImpl rate1 = (RateImpl) ((RateEnableInterface) object1)
					.getRate();
			RateImpl rate2 = (RateImpl) ((RateEnableInterface) object2)
					.getRate();

			if (rate1.getCpcRate().intValue() > rate2.getCpcRate().intValue()) {
				return -1;
			}

			if (rate1.getCpcRate().intValue() < rate2.getCpcRate().intValue()) {
				return 1;
			}

			return 0;
		} else {
			logger.warn("Given objects is not instance of RateEnableInterface");

			return 0;
		}
	}
}
