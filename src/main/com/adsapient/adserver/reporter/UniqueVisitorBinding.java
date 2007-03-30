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

import com.adsapient.api.IEventObject;
import com.adsapient.api.ISpecificEventsObject;

import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.bind.tuple.TupleInput;
import com.sleepycat.bind.tuple.TupleOutput;

import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UniqueVisitorBinding extends TupleBinding {
	static Logger logger = Logger.getLogger(UniqueVisitorBinding.class);

	private SimpleDateFormat simpleDateFormat;

	public Object entryToObject(TupleInput ti) {
		VisitorObjectImpl visitorObject = new VisitorObjectImpl();

		if (ti.available() == 0) {
			return visitorObject;
		}

		int ipAddressStrLength = ti.readInt();
		String ipAddress = ti.readBytes(ipAddressStrLength);
		visitorObject.setIpAddress(ipAddress);

		if (ti.available() == 0) {
			return visitorObject;
		}

		int bannersToEventsMapSize = ti.readInt();
		Map<String, IEventObject> bannersToEventsMap = new HashMap<String, IEventObject>();

		for (int i = 0; i < bannersToEventsMapSize; i++) {
			int entityId = ti.readInt();
			String bannerClassId = ti.readBytes(1);
			IEventObject eventObject = new EventObjectImpl();
			int adEventTypeToSpecificEventsObjectMapSize = ti.readInt();
			Map<Byte, ISpecificEventsObject> adEventTypeToSpecificEventsObjectMap = new HashMap<Byte, ISpecificEventsObject>();

			for (int j = 0; j < adEventTypeToSpecificEventsObjectMapSize; j++) {
				Byte adEventType = ti.readByte();
				ISpecificEventsObject specificEventsObject = new SpecificEventsObjectImpl();
				int dateToCountMapSize = ti.readInt();
				Map<Date, Integer> dateToCountMap = new HashMap<Date, Integer>();

				for (int k = 0; k < dateToCountMapSize; k++) {
					Date date = new Date(ti.readLong());
					int count = ti.readInt();
					dateToCountMap.put(date, count);
				}

				specificEventsObject.setDateToCountMap(dateToCountMap);
				adEventTypeToSpecificEventsObjectMap.put(adEventType,
						specificEventsObject);
			}

			eventObject
					.setAdEventTypeToSpecificEventsObjectMap(adEventTypeToSpecificEventsObjectMap);
			bannersToEventsMap.put(entityId + bannerClassId, eventObject);
		}

		visitorObject.setEntityCompositeKeyToEventsMap(bannersToEventsMap);

		return visitorObject;
	}

	public void objectToEntry(Object object, TupleOutput to) {
		VisitorObjectImpl visitorObject = (VisitorObjectImpl) object;
		int ipAddressStrLength = visitorObject.getIpAddress().length();
		String ipAddress = visitorObject.getIpAddress();
		to.writeInt(ipAddressStrLength);
		to.writeBytes(ipAddress);

		Map<String, IEventObject> bannersToEventsMap = visitorObject
				.getEntityCompositeKeyToEventsMap();

		if (bannersToEventsMap == null) {
			return;
		}

		to.writeInt(bannersToEventsMap.size());

		for (String entityCompositeKey : bannersToEventsMap.keySet()) {
			int entityId = Integer.parseInt(entityCompositeKey.substring(0,
					entityCompositeKey.length() - 1));
			String entityClassId = entityCompositeKey.substring(
					entityCompositeKey.length() - 1, entityCompositeKey
							.length());
			to.writeInt(entityId);
			to.writeBytes(entityClassId);

			IEventObject eventObject = bannersToEventsMap.get(entityId
					+ entityClassId);
			Map<Byte, ISpecificEventsObject> adEventTypeToSpecificEventsObjectMap = eventObject
					.getAdEventTypeToSpecificEventsObjectMap();
			to.writeInt(adEventTypeToSpecificEventsObjectMap.size());

			for (Byte adEventType : adEventTypeToSpecificEventsObjectMap
					.keySet()) {
				to.writeByte(adEventType);

				ISpecificEventsObject specificEventsObject = adEventTypeToSpecificEventsObjectMap
						.get(adEventType);
				Map<Date, Integer> dateToCountMap = specificEventsObject
						.getDateToCountMap();
				to.writeInt(dateToCountMap.size());

				for (Date date : dateToCountMap.keySet()) {
					to.writeLong(date.getTime());

					Integer count = dateToCountMap.get(date);
					to.writeInt(count);
				}
			}
		}
	}

	public SimpleDateFormat getSimpleDateFormat() {
		return simpleDateFormat;
	}

	public void setSimpleDateFormat(SimpleDateFormat simpleDateFormat) {
		this.simpleDateFormat = simpleDateFormat;
	}
}
