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
package com.adsapient.adserver.reporter;

import com.adsapient.adserver.beans.EventObjectImpl;
import com.adsapient.adserver.beans.SpecificEventsObjectImpl;
import com.adsapient.adserver.beans.VisitorObjectImpl;

import com.adsapient.shared.api.entity.IEventObject;
import com.adsapient.shared.api.entity.ISpecificEventsObject;
import com.adsapient.shared.api.entity.IVisitorObject;

import com.adsapient.test.SpringTestCase;

import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AdsapientReporterTest extends SpringTestCase {
	String datePattern = "yyMMddHHmmss";

	SimpleDateFormat dateFormat;

	AdsapientReporter reporter;

	int uniquesCount = 1000;

	UniqueVisitorBinding binding;

	public void setUp() throws Exception {
		if (appContext == null) {
			appContext = new ClassPathXmlApplicationContext(getFileList());
		}

		reporter = (AdsapientReporter) appContext.getBean("adsapientReporter");
		dateFormat = new SimpleDateFormat(datePattern);
		binding = new UniqueVisitorBinding();
		binding.setSimpleDateFormat(dateFormat);
		reporter.setup();
	}

	public void tearDown() throws Exception {
		reporter.close();
	}

	public void testUniqueVisitorIOPerformance() {
		try {
			Database db = reporter.getUniqueVisitorsDb();
			long t1 = System.currentTimeMillis();

			for (int i = 0; i < uniquesCount; i++) {
				DatabaseEntry key = new DatabaseEntry(generateKey(i));
				DatabaseEntry value = new DatabaseEntry();
				IVisitorObject visitor = generateUniqueVisitor(0);

				db.put(null, key, value);
			}

			long duration = System.currentTimeMillis() - t1;
			System.out.println("Insertion done in:" + duration);

			t1 = System.currentTimeMillis();

			for (int i = 0; i < uniquesCount; i++) {
				DatabaseEntry searchKey = new DatabaseEntry(generateKey(i));
				DatabaseEntry foundValue = new DatabaseEntry();

				if (reporter.getUniqueVisitorsDb().get(null, searchKey,
						foundValue, LockMode.DEFAULT) != OperationStatus.SUCCESS) {
					System.out.println("Couldn't find value for key:"
							+ new String(searchKey.getData()));
				} else {
				}
			}

			duration = System.currentTimeMillis() - t1;
			System.out.println("Reading done in:" + duration);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testUniqueVisitorBinding() {
		try {
			System.out.println("testUniqueVisitorBinding");

			Database db = reporter.getUniqueVisitorsDb();
			long t1 = System.currentTimeMillis();
			DatabaseEntry key = new DatabaseEntry(generateKey(0));
			DatabaseEntry value = new DatabaseEntry();
			IVisitorObject visitor = generateUniqueVisitor(0);

			db.put(null, key, value);

			long duration = System.currentTimeMillis() - t1;
			System.out.println("Insertion done in:" + duration);

			t1 = System.currentTimeMillis();

			DatabaseEntry foundValue = new DatabaseEntry();

			if (reporter.getUniqueVisitorsDb().get(null, key, foundValue,
					LockMode.DEFAULT) != OperationStatus.SUCCESS) {
				System.out.println("Couldn't find value for key:"
						+ new String(key.getData()));
			} else {
			}

			duration = System.currentTimeMillis() - t1;
			System.out.println("Reading done in:" + duration);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void visitorsAreEqual(IVisitorObject visitor1,
			IVisitorObject visitor2) {
		Map<Integer, IEventObject> m1 = visitor1.getBannerIdToEventsMap();
		Map<Integer, IEventObject> m2 = visitor2.getBannerIdToEventsMap();

		if (m1.size() != m2.size()) {
			fail();
		}

		for (Integer id : m1.keySet()) {
			if (!m2.containsKey(id)) {
				fail();
			}

			if (m2.get(id) == null) {
				fail();
			}

			IEventObject eventObject1 = m1.get(id);
			IEventObject eventObject2 = m2.get(id);
			Map<Byte, ISpecificEventsObject> map1 = eventObject1
					.getAdEventTypeToSpecificEventsObjectMap();
			Map<Byte, ISpecificEventsObject> map2 = eventObject2
					.getAdEventTypeToSpecificEventsObjectMap();

			for (Byte eventType : map1.keySet()) {
				if (!map2.containsKey(eventType)) {
					fail();
				}

				if (map2.get(eventType) == null) {
					fail();
				}

				ISpecificEventsObject specificObject1 = map1.get(eventType);
				ISpecificEventsObject specificObject2 = map2.get(eventType);

				if (specificObject1.getEventType() != specificObject2
						.getEventType()) {
					fail();
				}

				Map<Date, Integer> ma1 = specificObject1.getDateToCountMap();
				Map<Date, Integer> ma2 = specificObject2.getDateToCountMap();

				if (ma1.size() != ma2.size()) {
					fail();
				}

				for (Date d : ma1.keySet()) {
					if (!ma2.containsKey(d)) {
						fail();
					}

					if (ma2.get(d) == null) {
						fail();
					}

					Integer count1 = ma1.get(d);
					Integer count2 = ma2.get(d);

					if (count1 != count2) {
						fail();
					}
				}
			}
		}
	}

	private byte[] generateKey(int id) {
		String prefix = "abcdefghijklmnopqrstuvwxyz";

		return (prefix + String.valueOf(id)).getBytes();
	}

	private byte[] generateValue(int id) {
		String prefix = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz";

		return (prefix + String.valueOf(id)).getBytes();
	}

	private IVisitorObject generateUniqueVisitor(int id) throws ParseException {
		VisitorObjectImpl visitorObject = new VisitorObjectImpl();
		Map<Integer, IEventObject> map = new HashMap<Integer, IEventObject>();
		IEventObject eventObject = new EventObjectImpl();
		Map<Byte, ISpecificEventsObject> map2 = new HashMap<Byte, ISpecificEventsObject>();

		ISpecificEventsObject specificEventsObject = new SpecificEventsObjectImpl();
		specificEventsObject.setEventType((byte) Math.random());

		Map<Date, Integer> map3 = new HashMap<Date, Integer>();
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
		map3.put(dateFormat.parse("010704120856"), (int) Math.random());
		map3.put(dateFormat.parse("010704120857"), (int) Math.random());
		map3.put(dateFormat.parse("010704120858"), (int) Math.random());
		specificEventsObject.setDateToCountMap(map3);

		ISpecificEventsObject specificEventsObject2 = new SpecificEventsObjectImpl();
		specificEventsObject2.setEventType((byte) Math.random());

		Map<Date, Integer> map4 = new HashMap<Date, Integer>();
		map4.put(dateFormat.parse("010704120856"), (int) Math.random());
		map4.put(dateFormat.parse("010704120857"), (int) Math.random());
		map4.put(dateFormat.parse("010704120858"), (int) Math.random());
		specificEventsObject2.setDateToCountMap(map4);

		map2.put((byte) Math.random(), specificEventsObject);
		map2.put((byte) Math.random(), specificEventsObject2);

		map.put((int) Math.random(), eventObject);
		eventObject.setAdEventTypeToSpecificEventsObjectMap(map2);

		return null;
	}
}
