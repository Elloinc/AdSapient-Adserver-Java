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
package com.adsapient.shared.service;

import junit.framework.TestCase;

import java.math.BigDecimal;

public class TotalsReportServiceTest extends TestCase {
	public void testPrecision() {
		TotalsReportService s = new TotalsReportService();
		double testValue = 43454.345000000000000444;
		BigDecimal bd = new BigDecimal(testValue);
		bd.setScale(4, BigDecimal.ROUND_HALF_UP);

		double d = bd.doubleValue();
		System.out.println(round(4354.45554, 2));
	}

	double round(double value, int decimalPlace) {
		double power_of_ten = 1;

		while (decimalPlace-- > 0)
			power_of_ten *= 10.0;

		return Math.round(value * power_of_ten) / power_of_ten;
	}
}
