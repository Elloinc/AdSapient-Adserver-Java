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

import com.adsapient.adserver.beans.ReporterEventsObject;

import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

public class BeanServiceTest extends TestCase {
	BeanService beanService;

	public BeanServiceTest(String testName) {
		super(testName);
	}

	protected void setUp() throws Exception {
		beanService = new BeanService();
	}

	protected void tearDown() throws Exception {
	}

	public void testSerializeDeserialize() {
		Map<Integer, ReporterEventsObject> m1 = new HashMap<Integer, ReporterEventsObject>();
		ReporterEventsObject reo1 = createReo1();
		ReporterEventsObject reo2 = createReo2();
		m1.put(1, reo1);
		m1.put(2, reo2);

		byte[] bbs = beanService.serialize(m1);
		Map<Integer, ReporterEventsObject> m2 = (Map<Integer, ReporterEventsObject>) beanService
				.deserialize(bbs);

		compareMaps(m1, m2);
	}

	private ReporterEventsObject createReo1() {
		ReporterEventsObject reo = new ReporterEventsObject();
		reo.adviews = 10;
		reo.clicks = 11;
		reo.leads = 12;
		reo.sales = 13;
		reo.earnedspent = 20.22;

		return reo;
	}

	private ReporterEventsObject createReo2() {
		ReporterEventsObject reo = new ReporterEventsObject();
		reo.adviews = 10;
		reo.clicks = 11;
		reo.leads = 12;
		reo.sales = 13;
		reo.earnedspent = 20.22;

		return reo;
	}

	private void compareMaps(Map<Integer, ReporterEventsObject> m1,
			Map<Integer, ReporterEventsObject> m2) {
		for (Integer key : m1.keySet()) {
			ReporterEventsObject reo1 = m1.get(key);
			ReporterEventsObject reo2 = m2.get(key);
			compareReporterEventsObjects(reo1, reo2);
		}
	}

	private void compareReporterEventsObjects(ReporterEventsObject reo1,
			ReporterEventsObject reo2) {
		assertEquals(reo1.adviews, reo2.adviews);
		assertEquals(reo1.clicks, reo2.clicks);
		assertEquals(reo1.leads, reo2.leads);
		assertEquals(reo1.sales, reo2.sales);
		assertEquals(reo1.earnedspent, reo2.earnedspent);
	}
}
