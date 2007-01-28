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
package com.adsapient.adserver.beans;

import com.adsapient.adserver.geoip.GeoIpEntry;
import com.adsapient.adserver.geoip.IGeoIpEntry;

import com.adsapient.shared.dao.HibernateEntityDao;
import com.adsapient.shared.mappable.CountryAbbrEntity;

import com.adsapient.util.admin.AdsapientConstants;

import com.csvreader.CsvReader;

import com.sleepycat.bind.tuple.TupleInput;
import com.sleepycat.bind.tuple.TupleOutput;

import com.sleepycat.je.Cursor;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.InputStream;

import java.net.URL;

import java.nio.charset.Charset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class IpToCountryMappingBean {
	private static final String ABBR_ADDRES_RESERVED_BASE = "ZZ";

	private static final String databaseName = "countryIp";

	private static Database ipDatabase = null;

	private static Long[] ips;

	private static Logger logger = Logger
			.getLogger(IpToCountryMappingBean.class);

	private String databaseHome = null;

	private Environment ipDBEnvironment = null;

	private HibernateEntityDao hibernateEntityDao;

	private static synchronized InputStream readZipFile(URL path) {
		try {
			ZipFile file = new ZipFile(path.getFile());
			Enumeration entries = file.entries();
			ZipEntry entry = (ZipEntry) entries.nextElement();

			return file.getInputStream(entry);
		} catch (Exception e) {
			logger.error(new StringBuffer("Unable to read ").append(path)
					.toString(), e);
		}

		return null;
	}

	public void close() {
		try {
			if (ipDatabase != null) {
				ipDatabase.close();
			}

			if (ipDBEnvironment != null) {
				ipDBEnvironment.close();
			}
		} catch (DatabaseException dbe) {
		}
	}

	private IGeoIpEntry entryToObject(DatabaseEntry dbEntry) {
		TupleInput ti = new TupleInput(dbEntry.getData());
		GeoIpEntry entry = new GeoIpEntry();
		entry.setIpFrom(ti.readLong());
		entry.setIpTo(ti.readLong());

		int len = ti.readInt();
		entry.setCountryAbbr2(ti.readBytes(len));
		len = ti.readInt();
		entry.setCountryAbbr3(ti.readBytes(len));
		len = ti.readInt();
		entry.setCountryName(ti.readBytes(len));

		return entry;
	}

	@SuppressWarnings("unchecked")
	private void importCSV() {
		CsvReader reader = null;

		try {
			String pathToIPdbFile = "setup/IpToCountry.csv";
			InputStream stream = IpToCountryMappingBean.class.getClassLoader()
					.getResourceAsStream("/" + pathToIPdbFile);
			reader = new CsvReader(stream, Charset.defaultCharset());
		} catch (Exception e) {
			logger.error("Cannot import IpToCountry.zip", e);

			return;
		}

		try {
			ipDBEnvironment.truncateDatabase(null, databaseName, false);
		} catch (Exception e) {
		}

		List<Long> ipFromList = new ArrayList<Long>();

		HashMap<String, IGeoIpEntry> map = new HashMap<String, IGeoIpEntry>();

		try {
			while (reader.readRecord()) {
				String ipFrom = reader.get(0);
				String ipTo = reader.get(1);
				String ctry2 = reader.get(4);
				String ctry3 = reader.get(5);
				String ctryName = reader.get(6);

				if (ctry2.equals(ABBR_ADDRES_RESERVED_BASE)) {
					ctry2 = AdsapientConstants.COUNTRY_ABBR_ADDRES_RESERVED;
					ctry3 = AdsapientConstants.COUNTRY_ABBR_ADDRES_RESERVED3;
					ctryName = AdsapientConstants.COUNTRY_ADDRES_REZERVED;
				}

				ctryName = ctryName.toUpperCase();

				long ipFromLong = Long.parseLong(ipFrom);

				GeoIpEntry entry = new GeoIpEntry();
				entry.setIpFrom(ipFromLong);
				entry.setIpTo(Long.parseLong(ipTo));
				entry.setCountryAbbr2(ctry2);
				entry.setCountryAbbr3(ctry3);
				entry.setCountryName(ctryName);

				DatabaseEntry key = new DatabaseEntry(ipFrom.getBytes());
				DatabaseEntry value = objectToEntry(entry);
				ipDatabase.put(null, key, value);

				ipFromList.add(ipFromLong);

				if (!map.containsKey(ctry2)) {
					map.put(ctry2, entry);
				}
			}

			ipDBEnvironment.sync();
		} catch (Exception e) {
			logger.error("Error importing csv record", e);
		}

		List<CountryAbbrEntity> countries = new ArrayList<CountryAbbrEntity>();

		try {
			countries = (List<CountryAbbrEntity>) hibernateEntityDao
					.viewAll(CountryAbbrEntity.class);
		} catch (Exception e) {
			logger.error("Cannot get LIst of countries", e);
		}

		IGeoIpEntry an = new GeoIpEntry();
		an.setCountryAbbr2(AdsapientConstants.COUNTRY_ABBR_ADDRES_ANONIMOUS);
		an.setCountryAbbr3(AdsapientConstants.COUNTRY_ABBR_ADDRES_ANONIMOUS3);
		an.setCountryName(AdsapientConstants.COUNTRY_ADDRES_ANONIMOUS);
		map.put(AdsapientConstants.COUNTRY_ABBR_ADDRES_ANONIMOUS, an);

		IGeoIpEntry nf = new GeoIpEntry();
		nf.setCountryAbbr2(AdsapientConstants.COUNTRY_ABBR_ADDRES_NOT_FOUND);
		nf.setCountryAbbr3(AdsapientConstants.COUNTRY_ABBR_ADDRES_NOT_FOUND3);
		nf.setCountryName(AdsapientConstants.COUNTRY_ADDRES_NOT_FOUND);
		map.put(AdsapientConstants.COUNTRY_ABBR_ADDRES_NOT_FOUND, nf);

		for (CountryAbbrEntity country : countries) {
			IGeoIpEntry entry = map.get(country.getCountryAbbr2());

			if (entry == null) {
				continue;
			} else {
				if (!entry.getCountryAbbr3().equals(country.getCountryAbbr3())
						|| !entry.getCountryName().equals(
								country.getCountryName())) {
					country.setCountryName(entry.getCountryName());
					country.setCountryAbbr3(entry.getCountryAbbr3());

					try {
						hibernateEntityDao.updateObject(country);
					} catch (Exception e) {
						logger.error("cannot update country", e);
					}
				}

				map.remove(country.getCountryAbbr2());
			}
		}

		for (String abbr2 : map.keySet()) {
			try {
				IGeoIpEntry entry = map.get(abbr2);
				CountryAbbrEntity country = new CountryAbbrEntity();
				country.setCountryAbbr2(entry.getCountryAbbr2());
				country.setCountryAbbr3(entry.getCountryAbbr3());
				country.setCountryName(entry.getCountryName());
				hibernateEntityDao.save(country);
			} catch (Exception e) {
				logger.error("Unable to save entry to mysql ", e);
			}
		}

		ips = new Long[ipFromList.size()];
		ipFromList.toArray(ips);
		Arrays.sort(ips);

		logger.info(new StringBuffer("Imported ").append(ips.length).append(
				" nodes to ipDatabase").toString());
	}

	public void setup() {
		EnvironmentConfig envConfig = new EnvironmentConfig();
		envConfig.setAllowCreate(true);
		envConfig.setTransactional(false);

		File f = new File(databaseHome);
		String databaseHomePath = f.getAbsolutePath();

		if (!f.exists()) {
			f.mkdirs();
		}

		try {
			ipDBEnvironment = new Environment(f, envConfig);
		} catch (Exception e) {
			logger.error(new StringBuffer("Error getting ipDBEnvironment at ")
					.append(databaseHomePath).toString());

			return;
		}

		DatabaseConfig dbConfig = new DatabaseConfig();
		dbConfig.setAllowCreate(true);
		dbConfig.setTransactional(false);

		try {
			ipDatabase = ipDBEnvironment.openDatabase(null, databaseName,
					dbConfig);

			Cursor cursor = ipDatabase.openCursor(null, null);

			DatabaseEntry key = new DatabaseEntry();
			DatabaseEntry value = new DatabaseEntry();
			List<Long> ipFromList = new ArrayList<Long>();

			while (cursor.getNextNoDup(key, value, LockMode.DEFAULT).equals(
					OperationStatus.SUCCESS)) {
				GeoIpEntry entry = (GeoIpEntry) entryToObject(value);
				ipFromList.add(entry.getIpFrom());
			}

			if (ipFromList.size() == 0) {
				importCSV();
			} else {
				ips = new Long[ipFromList.size()];
				ipFromList.toArray(ips);

				Arrays.sort(ips);
			}
		} catch (DatabaseException e) {
			logger.warn("Setup error", e);
		}
	}

	private DatabaseEntry objectToEntry(GeoIpEntry entry) {
		TupleOutput to = new TupleOutput();
		to.writeLong(entry.getIpFrom());
		to.writeLong(entry.getIpTo());
		to.writeInt(entry.getCountryAbbr2().length());
		to.writeBytes(entry.getCountryAbbr2());
		to.writeInt(entry.getCountryAbbr3().length());
		to.writeBytes(entry.getCountryAbbr3());
		to.writeInt(entry.getCountryName().length());
		to.writeBytes(entry.getCountryName());

		DatabaseEntry value = new DatabaseEntry(to.getBufferBytes());

		return value;
	}

	public String search(String key) {
		String searchKey = null;
		StringTokenizer stringTokenizer = new StringTokenizer(key, ".");

		if (stringTokenizer.countTokens() != 4) {
			return AdsapientConstants.COUNTRY_ABBR_ADDRES_ANONIMOUS;
		}

		String ip1 = stringTokenizer.nextToken();
		String ip2 = stringTokenizer.nextToken();
		String ip3 = stringTokenizer.nextToken();
		String ip4 = stringTokenizer.nextToken();

		try {
			long ip = Integer.parseInt(ip4) + (Integer.parseInt(ip3) << 8)
					+ (Integer.parseInt(ip2) << 16)
					+ (Integer.parseInt(ip1) << 24);

			if ((ip >= 0) && (ip < ips[ips.length - 1])) {
				searchKey = String.valueOf(searchIp(0, ips.length - 1, ip));
			} else {
				return AdsapientConstants.COUNTRY_ABBR_ADDRES_ANONIMOUS;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		try {
			Cursor cursor = null;
			cursor = ipDatabase.openCursor(null, null);

			DatabaseEntry theKey = new DatabaseEntry(searchKey.getBytes());
			DatabaseEntry theData = new DatabaseEntry();
			OperationStatus retVal = cursor.getSearchKey(theKey, theData,
					LockMode.DEFAULT);

			if (retVal.equals(OperationStatus.SUCCESS)) {
				IGeoIpEntry found = entryToObject(theData);

				return found.getCountryAbbr2();
			} else {
				return AdsapientConstants.COUNTRY_ABBR_ADDRES_NOT_FOUND;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return AdsapientConstants.COUNTRY_ABBR_ADDRES_NOT_FOUND;
	}

	private long searchIp(int start, int end, long ip) {
		if (ips[start] == ip) {
			return ips[start];
		}

		if (ips[end] == ip) {
			return ips[end];
		}

		int index = (end + start) / 2;

		if ((ips[index] <= ip) && (index < end) && (ips[index + 1] > ip)) {
			return ips[index];
		} else if (ips[index] < ip) {
			return searchIp(index, end, ip);
		} else if (ips[index] > ip) {
			return searchIp(start, index, ip);
		}

		return -1;
	}

	public String getDatabaseHome() {
		return databaseHome;
	}

	public void setDatabaseHome(String databaseHome) {
		this.databaseHome = databaseHome;
	}

	public HibernateEntityDao getHibernateEntityDao() {
		return hibernateEntityDao;
	}

	public void setHibernateEntityDao(HibernateEntityDao hibernateEntityDao) {
		this.hibernateEntityDao = hibernateEntityDao;
	}
}
