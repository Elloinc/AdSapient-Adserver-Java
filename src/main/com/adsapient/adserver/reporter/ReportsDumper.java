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

import com.adsapient.adserver.beans.ReporterModel;

import com.adsapient.util.DBOperator;

import org.apache.log4j.Logger;

import java.io.File;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ReportsDumper {
	private static Logger logger = Logger.getLogger(ReportsDumper.class);

	private String pathToDumpFolder;

	private int limit;

	private String eventFilePrefix;

	private String uniquesFilePrefix;

	private ReporterModel reporterModel;

	private FileSorter fileSorter = new FileSorter();

	public void dumpReports() {
		long t = System.currentTimeMillis();

		try {
			String query = "insert into events (Event_Type,Banner_Id,Place_Id,Unique_Id,Timestamp) values";
			String vals = reporterModel.getAdeventBulkInsertQuery();

			if (vals == null) {
				return;
			}

			if (vals.length() <= 0) {
				return;
			}

			query += vals.substring(0, vals.length() - 1);
			reporterModel.setAdeventBulkInsertQuery("");

			DBOperator.executeUpdate(query);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}

		try {
			String query = "insert into uniques (uniqueid,langid,browserid,osid,geolocationid) values";
			String vals = reporterModel.getUniqueBulkIsertQuery();

			if (vals == null) {
				return;
			}

			if (vals.length() <= 0) {
				return;
			}

			query += vals.substring(0, vals.length() - 1);
			reporterModel.setUniqueBulkIsertQuery("");

			DBOperator.executeUpdate(query);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
	}

	private void truncateOldFiles(File[] files) {
		Arrays.sort(files, fileSorter);

		List<File> l = Arrays.asList(files);
		List<File> lr = l.subList(0, l.size() - limit);

		for (File f : lr) {
			f.delete();
		}
	}

	public void sortFilesByTime(File[] files) {
		Arrays.sort(files, fileSorter);
	}

	public String getPathToDumpFolder() {
		return pathToDumpFolder;
	}

	public void setPathToDumpFolder(String pathToDumpFolder) {
		this.pathToDumpFolder = pathToDumpFolder;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getEventFilePrefix() {
		return eventFilePrefix;
	}

	public void setEventFilePrefix(String eventFilePrefix) {
		this.eventFilePrefix = eventFilePrefix;
	}

	public ReporterModel getReporterModel() {
		return reporterModel;
	}

	public void setReporterModel(ReporterModel reporterModel) {
		this.reporterModel = reporterModel;
	}

	public String getUniquesFilePrefix() {
		return uniquesFilePrefix;
	}

	public void setUniquesFilePrefix(String uniquesFilePrefix) {
		this.uniquesFilePrefix = uniquesFilePrefix;
	}

	private class FileSorter implements Comparator {
		public int compare(Object o1, Object o2) {
			File f1 = (File) o1;
			File f2 = (File) o2;
			String ts1 = f1.getName().split("_")[1];
			String ts2 = f2.getName().split("_")[1];
			Long l1 = Long.parseLong(ts1);
			Long l2 = Long.parseLong(ts2);

			return l1.compareTo(l2);
		}
	}
}
