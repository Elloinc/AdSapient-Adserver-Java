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
package com.adsapient.api_impl.report.entity;

import com.adsapient.api.ReportEntity;

import com.adsapient.api_impl.statistic.common.StatisticEntity;

import org.apache.log4j.Logger;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class BaseReportEntity implements ReportEntity {
	private static Logger logger = Logger.getLogger(BaseReportEntity.class);

	private Collection collection = new ArrayList();

	private String header;

	public BaseReportEntity(Collection collection, String header) {
		this.collection = collection;
		this.header = header;
	}

	public boolean createExelReport(HSSFWorkbook wb) {
		HSSFSheet sheet = wb.getSheetAt(0);
		int beginRowNumber = sheet.getLastRowNum();
		int currentRowNumber = beginRowNumber;
		short cellNumber = 0;

		if (this.collection.isEmpty()) {
			logger.warn("Skip transform BaseReportEntiyt " + header);

			return false;
		}

		HSSFCellStyle cellStyleJUSTIFY = wb.createCellStyle();
		cellStyleJUSTIFY.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		sheet.createRow(++currentRowNumber);

		HSSFRow row = sheet.createRow(++currentRowNumber);
		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue(header);
		cell.setCellStyle(cellStyleJUSTIFY);
		sheet.addMergedRegion(new Region(currentRowNumber, (short) 0,
				currentRowNumber, (short) 8));

		HSSFRow row2 = sheet.createRow(++currentRowNumber);

		Iterator headersIterator = ((StatisticEntity) this.collection
				.iterator().next()).getStatisticFormater().getHeaders()
				.iterator();

		while (headersIterator.hasNext()) {
			String header = (String) headersIterator.next();
			row2.createCell(cellNumber++).setCellValue(header);
		}

		cellNumber = 0;

		Iterator dataIterator = this.collection.iterator();

		while (dataIterator.hasNext()) {
			HSSFRow statisticRow = sheet.createRow(++currentRowNumber);
			StatisticEntity entity = (StatisticEntity) dataIterator.next();
			Iterator formatValuesIterator = entity.getAsFormatedCollection()
					.iterator();

			while (formatValuesIterator.hasNext()) {
				Object value = formatValuesIterator.next();
				statisticRow.createCell(cellNumber++).setCellValue(
						value.toString());
			}

			cellNumber = 0;
		}

		return true;
	}
}
