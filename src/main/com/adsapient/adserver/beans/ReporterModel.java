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

import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class ReporterModel implements Serializable {
	private static Logger logger = Logger.getLogger(ReporterModel.class);

	private ByteArrayOutputStream adeventsBaos = new ByteArrayOutputStream();

	private ByteArrayOutputStream uniquesBaos = new ByteArrayOutputStream();

	private String adeventBulkInsertQuery = new String();

	private String uniqueBulkIsertQuery = new String();

	private byte[] adeventsByteArray;

	private byte[] uniqueVisitorsByteArray;

	public void registerEvent(EventObject eo) throws IOException {
		try {
			setAdeventBulkInsertQuery(getAdeventBulkInsertQuery()
					+ ("(" + eo.getEventid() + "," + eo.getBannerid() + ","
							+ eo.getPlaceid() + "," + eo.getUniqueid()
							+ ",now()" + "),"));
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
	}

	public void registerNewUniqueVisitor(UniqueVisitorObject uvo)
			throws IOException {
		try {
			setUniqueBulkIsertQuery(getUniqueBulkIsertQuery()
					+ ("(" + uvo.getUniqueid() + "," + uvo.getLangid() + ","
							+ uvo.getBrowserid() + "," + uvo.getOsid() + ","
							+ uvo.getGeolocationid() + "),"));
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
	}

	public static byte[] intToByteArray(final int integer) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		dos.writeInt(integer);
		dos.flush();

		return bos.toByteArray();
	}

	public static byte[] longToByteArray(final long l) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		dos.writeLong(l);
		dos.flush();

		return bos.toByteArray();
	}

	public static double arr2double(byte[] arr, int start) {
		int i = 0;
		int len = 8;
		int cnt = 0;
		byte[] tmp = new byte[len];

		for (i = start; i < (start + len); i++) {
			tmp[cnt] = arr[i];

			cnt++;
		}

		long accum = 0;
		i = 0;

		for (int shiftBy = 0; shiftBy < 64; shiftBy += 8) {
			accum |= (((long) (tmp[i] & 0xff)) << shiftBy);
			i++;
		}

		return Double.longBitsToDouble(accum);
	}

	public static long arr2long(byte[] arr, int start) {
		int i = 0;
		int len = 4;
		int cnt = 0;
		byte[] tmp = new byte[len];

		for (i = start; i < (start + len); i++) {
			tmp[cnt] = arr[i];
			cnt++;
		}

		long accum = 0;
		i = 0;

		for (int shiftBy = 0; shiftBy < 32; shiftBy += 8) {
			accum |= (((long) (tmp[i] & 0xff)) << shiftBy);
			i++;
		}

		return accum;
	}

	public static int arr2int(byte[] arr, int start) {
		int low = arr[start] & 0xff;
		int high = arr[start + 1] & 0xff;

		return (int) ((high << 8) | low);
	}

	public byte[] getAdeventsByteArray() {
		return adeventsByteArray;
	}

	public void setAdeventsByteArray(byte[] adeventsByteArray) {
		this.adeventsByteArray = adeventsByteArray;
	}

	public byte[] getUniqueVisitorsByteArray() {
		return uniqueVisitorsByteArray;
	}

	public void setUniqueVisitorsByteArray(byte[] uniqueVisitorsByteArray) {
		this.uniqueVisitorsByteArray = uniqueVisitorsByteArray;
	}

	public ByteArrayOutputStream getAdeventsBaos() {
		return adeventsBaos;
	}

	public void setAdeventsBaos(ByteArrayOutputStream adeventsBaos) {
		this.adeventsBaos = adeventsBaos;
	}

	public ByteArrayOutputStream getUniquesBaos() {
		return uniquesBaos;
	}

	public void setUniquesBaos(ByteArrayOutputStream uniquesBaos) {
		this.uniquesBaos = uniquesBaos;
	}

	public String getAdeventBulkInsertQuery() {
		return adeventBulkInsertQuery;
	}

	public void setAdeventBulkInsertQuery(String adeventBulkInsertQuery) {
		this.adeventBulkInsertQuery = adeventBulkInsertQuery;
	}

	public String getUniqueBulkIsertQuery() {
		return uniqueBulkIsertQuery;
	}

	public void setUniqueBulkIsertQuery(String uniqueBulkIsertQuery) {
		this.uniqueBulkIsertQuery = uniqueBulkIsertQuery;
	}
}
