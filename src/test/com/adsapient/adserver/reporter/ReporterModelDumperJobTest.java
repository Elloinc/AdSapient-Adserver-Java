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

import com.adsapient.adserver.ModelUpdater;
import com.adsapient.adserver.requestprocessors.GetPlaceCodeProcessor;

import com.adsapient.test.SpringTestCase;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.Random;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

public class ReporterModelDumperJobTest extends SpringTestCase {
	GetPlaceCodeProcessor getPlaceCodeProcessor;

	ModelUpdater modelUpdater;

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public static void main(String[] args) {
		new ReporterModelDumperJobTest().testDumpAsFileToExternalLocation();
	}

	public void testDumpAsFileToExternalLocation() {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int numberOfEvents = 30000;

			for (int i = 0; i < numberOfEvents; i++) {
				EventObject eo = new EventObject();
				baos.write(longToByteArray(eo.getTs()));
				baos.write(intToByteArray(eo.getBannerid()));
				baos.write(intToByteArray(eo.getPlaceid()));
				baos.write(eo.getEventid());
				baos.write(intToByteArray(eo.getEventid()));
			}

			System.out.println(baos.size());

			long tt1 = System.currentTimeMillis();
			Deflater compresser = new Deflater();
			compresser.setInput(baos.toByteArray());

			ByteArrayOutputStream outputBaos = new ByteArrayOutputStream();
			DeflaterOutputStream dos = new DeflaterOutputStream(outputBaos,
					compresser);
			dos.finish();
			System.out.println("ARCHIVING TOOK:"
					+ (System.currentTimeMillis() - tt1));
			System.out.println(outputBaos.size());

			long t1 = System.currentTimeMillis();
			FileOutputStream fos = new FileOutputStream(
					"\\\\192.168.1.102\\test\\output_"
							+ System.currentTimeMillis());
			fos.write(baos.toByteArray());
			fos.close();
			System.out.println("WRITING TOOK:"
					+ (System.currentTimeMillis() - t1));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private byte[] intToByteArray(final int integer) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		dos.writeInt(integer);
		dos.flush();

		return bos.toByteArray();
	}

	private byte[] longToByteArray(final long l) throws IOException {
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

	private class EventObject {
		private long ts;

		private int bannerid;

		private int placeid;

		private byte eventid;

		private int uniqueid;

		public EventObject() {
			Random r = new Random();
			ts = r.nextLong();
			bannerid = r.nextInt();
			placeid = r.nextInt();
			eventid = (byte) r.nextInt();
			uniqueid = r.nextInt();
		}

		public long getTs() {
			return ts;
		}

		public void setTs(long ts) {
			this.ts = ts;
		}

		public int getBannerid() {
			return bannerid;
		}

		public void setBannerid(int bannerid) {
			this.bannerid = bannerid;
		}

		public int getPlaceid() {
			return placeid;
		}

		public void setPlaceid(int placeid) {
			this.placeid = placeid;
		}

		public byte getEventid() {
			return eventid;
		}

		public void setEventid(byte eventid) {
			this.eventid = eventid;
		}

		public int getUniqueid() {
			return uniqueid;
		}

		public void setUniqueid(int uniqueid) {
			this.uniqueid = uniqueid;
		}
	}
}
