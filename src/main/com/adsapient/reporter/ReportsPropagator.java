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
package com.adsapient.reporter;

import com.adsapient.adserver.beans.EventObject;
import com.adsapient.adserver.beans.UniqueVisitorObject;
import com.adsapient.shared.dao.HibernateEntityDao;
import org.apache.log4j.Logger;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReportsPropagator {
    static Logger logger = Logger.getLogger(ReportsPropagator.class);
    //key=hour+placeid+bannerid
    private Map<String, HourlyRecord> hourlyRecordsMap = new HashMap<String, HourlyRecord>();
    //key=entityid+className+startDate+endDate+timeType+outofType+systemType
    private Map<String, UniquesRecord> uniquesRecordsMap = new HashMap<String, UniquesRecord>();
    private HibernateEntityDao hibernateEntityDao;
    public static final long MILLISECONDS_IN_DAY = 1000 * 60 * 60 * 24;

    public void dump() {
        long t1 = System.currentTimeMillis();
        int previousHour = getHourByDate(new Date()) - 1;
        //saving new values to the database
        //cleaning records for the previous hour, if found
        for (String key : hourlyRecordsMap.keySet()) {
            HourlyRecord hr = hourlyRecordsMap.get(key);
            hibernateEntityDao.saveOrUpdate(hr);
            if (hr.getHour() == previousHour) hourlyRecordsMap.remove(key);
        }
        for (String key : uniquesRecordsMap.keySet()) {
            UniquesRecord ur = uniquesRecordsMap.get(key);
            hibernateEntityDao.saveOrUpdate(ur);
        }
        logger.debug("Dumping reports took:" + (System.currentTimeMillis() - t1));
    }

    public void processAdeventsFile(File file) throws Exception {
        FileInputStream fis = new FileInputStream(file);
        DataInputStream dis = new DataInputStream(fis);
        while (dis.available() > 0) {
            EventObject eventObject = new EventObject();
            eventObject.setTs(dis.readLong());
            eventObject.setBannerid(dis.readInt());
            eventObject.setPlaceid(dis.readInt());
            eventObject.setEventid(dis.readByte());
            eventObject.setEarned(dis.readDouble());
            eventObject.setSpent(dis.readDouble());
            //
            updateHourlyRecordsMap(eventObject);
        }
    }

    public void processUniquesFile(File file) throws Exception {
        FileInputStream fis = new FileInputStream(file);
        DataInputStream dis = new DataInputStream(fis);
        while (dis.available() > 0) {
            UniqueVisitorObject uniqueVisitorObject = new UniqueVisitorObject();
            uniqueVisitorObject.setUniqueid(dis.readInt());
            uniqueVisitorObject.setBrowserid(dis.readInt());
            uniqueVisitorObject.setLangid(dis.readInt());
            uniqueVisitorObject.setOsid(dis.readInt());
            uniqueVisitorObject.setGeolocationid(dis.readInt());
            //
            updateUniquesRecordsMap(uniqueVisitorObject);
        }
    }

    private void updateUniquesRecordsMap(UniqueVisitorObject uniqueVisitorObject) {

    }

    private int getHourByDate(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return (int) ((int) c.getTimeInMillis() / MILLISECONDS_IN_DAY);
    }

    private void updateHourlyRecordsMap(EventObject eventObject) {
        int hour = (int) ((int) eventObject.getTs() / MILLISECONDS_IN_DAY);
        //hourlyRecordsMap
        String key = String.valueOf(hour) + String.valueOf(eventObject.getPlaceid()) + String.valueOf(eventObject.getBannerid());
        if (!hourlyRecordsMap.containsKey(key)) {
            hourlyRecordsMap.put(key, new HourlyRecord(eventObject));
        } else {
            HourlyRecord hr = hourlyRecordsMap.get(key);
            hr.merge(eventObject);
        }
    }

    public HibernateEntityDao getHibernateEntityDao() {
        return hibernateEntityDao;
    }

    public void setHibernateEntityDao(HibernateEntityDao hibernateEntityDao) {
        this.hibernateEntityDao = hibernateEntityDao;
    }
}
