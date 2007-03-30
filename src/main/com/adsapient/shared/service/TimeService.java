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

import com.adsapient.shared.exceptions.IncorrectDateException;

import org.apache.commons.lang.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

public class TimeService {
	public static final int MAX_YEAR_VALUE = 2100;

	public static final int MIN_YEAR_VALUE = 1900;

	public static final int MAX_MONTH_VALUE = 12;

	public static final int MIN_MONTH_VALUE = 1;

	public static final int MAX_DAY_VALUE = 31;

	public static final int MIN_DAY_VALUE = 1;

	public static final int MAX_HOUR_VALUE = 23;

	public static final int MAX_MINUTE_VALUE = 59;

	public static final int MIN_MINUTE_VALUE = 0;

	public static final int MIN_HOUR_VALUE = 0;

	public static final String DEFAULT_DATE_VALUE = "1234567";

	public static final String DEFAULT_TIME_VALUE = ":1::2::3::4::5::6::7::8::9::10::11::12::13::14::15::16::17::18::19::20::21::22::23::24:";

	public static final boolean isCampainPeriodActive(String beginDate,
			String endDate) {
		try {
			Date startDate = new Date(parseDateDDMMYYYY(beginDate, false));
			Date completeDate = new Date(parseDateDDMMYYYY(endDate, false));
			Date currentDate = Calendar.getInstance().getTime();

			if (currentDate.compareTo(startDate) < 0) {
				return false;
			}

			if (currentDate.compareTo(completeDate) > 0) {
				return false;
			}

			return true;
		} catch (IncorrectDateException ex) {
			System.err.println(ex.getCause());
		}

		return false;
	}

	public static final long getCurrentTime() {
		return Calendar.getInstance().getTime().getTime();
	}

	public static final boolean isInBlockTime(String beginTime, String endTime)
			throws IncorrectDateException {
		int beginHour;
		int endHour;
		int beginMinute;
		int endMinute;

		Calendar calendar = Calendar.getInstance();
		int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
		int currentMinute = calendar.get(Calendar.MINUTE);

		if (StringUtils.isEmpty(beginTime) | StringUtils.isEmpty(endTime)) {
			throw new IncorrectDateException("Date string is empty.");
		}

		StringTokenizer begin = new StringTokenizer(beginTime, ":");
		StringTokenizer end = new StringTokenizer(endTime, ":");

		if (!begin.hasMoreTokens()) {
			throw new IncorrectDateException("Begin time was not specified.");
		}

		if (!end.hasMoreTokens()) {
			throw new IncorrectDateException("End time was not specified.");
		}

		String tempBH = begin.nextToken();
		String tempEH = end.nextToken();

		try {
			beginHour = Integer.parseInt(tempBH);
			endHour = Integer.parseInt(tempEH);
		} catch (NumberFormatException e) {
			throw new IncorrectDateException(
					"Year parameter should be numeric.");
		}

		String tempBM = begin.nextToken();
		String tempEM = end.nextToken();

		try {
			beginMinute = Integer.parseInt(tempBM);
			endMinute = Integer.parseInt(tempEM);
		} catch (NumberFormatException e) {
			throw new IncorrectDateException(
					"Year parameter should be numeric.");
		}

		System.out.println("cur hour" + currentHour + "  curr min"
				+ currentMinute);
		System.out.println("begin time" + beginHour + "  begin min"
				+ beginMinute);

		if ((currentHour > beginHour)
				| ((currentHour == beginHour) & (currentMinute > beginMinute))) {
			if ((currentHour < endHour)
					| ((currentHour == endHour) & (currentMinute < endMinute))) {
				return true;
			}
		}

		return false;
	}

	public static final String getNormalizedDateString(long date) {
		StringBuffer result = new StringBuffer();
		Calendar cal = Calendar.getInstance();
		cal.setTime(new java.util.Date(date));

		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);

		if (year < 1000) {
			if (year < 10) {
				result.append("000");
			} else if (year < 100) {
				result.append("00");
			} else {
				result.append('0');
			}
		}

		result.append(year).append('-');

		if (month < 10) {
			result.append('0');
		}

		result.append(month).append('-');

		if (day < 10) {
			result.append('0');
		}

		result.append(day);

		return result.toString();
	}

	public static final String getNormalizedDateString() {
		StringBuffer result = new StringBuffer();
		Calendar cal = Calendar.getInstance();

		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);

		if (year < 1000) {
			if (year < 10) {
				result.append("000");
			} else if (year < 100) {
				result.append("00");
			} else {
				result.append('0');
			}
		}

		result.append(year).append('-');

		if (month < 10) {
			result.append('0');
		}

		result.append(month).append('-');

		if (day < 10) {
			result.append('0');
		}

		result.append(day);

		return result.toString();
	}

	public static final long parseDateDDMMYYYY(String str)
			throws IncorrectDateException {
		return parseDateDDMMYYYY(str, false);
	}

	public static final long parseDateDDMMYYYY(String str, boolean checkPast)
			throws IncorrectDateException {
		int year;
		int month;
		int day;

		if (StringUtils.isEmpty(str)) {
			throw new IncorrectDateException("Date string is empty.");
		}

		StringTokenizer st = new StringTokenizer(str, ".");

		if (!st.hasMoreTokens()) {
			throw new IncorrectDateException("Day was not specified.");
		}

		String tempEl = st.nextToken();

		try {
			day = Integer.parseInt(tempEl);
		} catch (NumberFormatException e) {
			throw new IncorrectDateException("Day parameter should be numeric.");
		}

		if ((day < MIN_DAY_VALUE) || (day > MAX_DAY_VALUE)) {
			throw new IncorrectDateException(
					"Incorrect ranges for day (day should be >= "
							+ MIN_DAY_VALUE + " and <= " + MAX_DAY_VALUE + ")");
		}

		if (!st.hasMoreTokens()) {
			throw new IncorrectDateException("Month was not specified.");
		}

		tempEl = st.nextToken();

		try {
			month = Integer.parseInt(tempEl);
		} catch (NumberFormatException e) {
			throw new IncorrectDateException(
					"Month parameter should be numeric.");
		}

		if ((month < MIN_MONTH_VALUE) || (month > MAX_MONTH_VALUE)) {
			throw new IncorrectDateException(
					"Incorrect ranges for month (month should be >= "
							+ MIN_MONTH_VALUE + " and <= " + MAX_MONTH_VALUE
							+ ")");
		}

		if (!st.hasMoreTokens()) {
			throw new IncorrectDateException("Year was not specified.");
		}

		tempEl = st.nextToken();

		try {
			year = Integer.parseInt(tempEl);
		} catch (NumberFormatException e) {
			throw new IncorrectDateException(
					"Year parameter should be numeric.");
		}

		if ((year < MIN_YEAR_VALUE) || (year > MAX_YEAR_VALUE)) {
			throw new IncorrectDateException(
					"Incorrect ranges for year (year should be >= "
							+ MIN_YEAR_VALUE + " and <= " + MAX_YEAR_VALUE
							+ ")");
		}

		Calendar cal = Calendar.getInstance();

		if (checkPast) {
			if (year < cal.get(Calendar.YEAR)) {
				throw new IncorrectDateException(
						"Date should not refer to the past.");
			} else if (year == cal.get(Calendar.YEAR)) {
				if (month < (cal.get(Calendar.MONTH) + 1)) {
					throw new IncorrectDateException(
							"Date should not refer to the past.");
				} else if (month == (cal.get(Calendar.MONTH) + 1)) {
					if (day < cal.get(Calendar.DAY_OF_MONTH)) {
						throw new IncorrectDateException(
								"Date should not refer to the past.");
					}
				}
			}
		}

		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, day);

		return cal.getTime().getTime();
	}

	public static final long parseDateYYYYMMDD(String str)
			throws IncorrectDateException {
		return parseDateYYYYMMDD(str, true);
	}

	public static final long parseDateYYYYMMDD(String str, boolean checkPast)
			throws IncorrectDateException {
		int year;
		int month;
		int day;

		if (StringUtils.isEmpty(str)) {
			throw new IncorrectDateException("Date string is empty.");
		}

		StringTokenizer st = new StringTokenizer(str, " -");

		if (!st.hasMoreTokens()) {
			throw new IncorrectDateException("Year was not specified.");
		}

		String tempEl = st.nextToken();

		try {
			year = Integer.parseInt(tempEl);
		} catch (NumberFormatException e) {
			throw new IncorrectDateException(
					"Year parameter should be numeric.");
		}

		if ((year < MIN_YEAR_VALUE) || (year > MAX_YEAR_VALUE)) {
			throw new IncorrectDateException(
					"Incorrect ranges for year (year should be >= "
							+ MIN_YEAR_VALUE + " and <= " + MAX_YEAR_VALUE
							+ ")");
		}

		if (!st.hasMoreTokens()) {
			throw new IncorrectDateException("Month was not specified.");
		}

		tempEl = st.nextToken();

		try {
			month = Integer.parseInt(tempEl);
		} catch (NumberFormatException e) {
			throw new IncorrectDateException(
					"Month parameter should be numeric.");
		}

		if ((month < MIN_MONTH_VALUE) || (month > MAX_MONTH_VALUE)) {
			throw new IncorrectDateException(
					"Incorrect ranges for month (month should be >= "
							+ MIN_MONTH_VALUE + " and <= " + MAX_MONTH_VALUE
							+ ")");
		}

		if (!st.hasMoreTokens()) {
			throw new IncorrectDateException("Day was not specified.");
		}

		tempEl = st.nextToken();

		try {
			day = Integer.parseInt(tempEl);
		} catch (NumberFormatException e) {
			throw new IncorrectDateException("Day parameter should be numeric.");
		}

		if ((day < MIN_DAY_VALUE) || (day > MAX_DAY_VALUE)) {
			throw new IncorrectDateException(
					"Incorrect ranges for day (day should be >= "
							+ MIN_DAY_VALUE + " and <= " + MAX_DAY_VALUE + ")");
		}

		Calendar cal = Calendar.getInstance();

		if (checkPast) {
			if (year < cal.get(Calendar.YEAR)) {
				throw new IncorrectDateException(
						"Date should not refer to the past.");
			} else if (year == cal.get(Calendar.YEAR)) {
				if (month < (cal.get(Calendar.MONTH) + 1)) {
					throw new IncorrectDateException(
							"Date should not refer to the past.");
				} else if (month == (cal.get(Calendar.MONTH) + 1)) {
					if (day < cal.get(Calendar.DAY_OF_MONTH)) {
						throw new IncorrectDateException(
								"Date should not refer to the past.");
					}
				}
			}
		}

		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, day);

		return cal.getTime().getTime();
	}
}
