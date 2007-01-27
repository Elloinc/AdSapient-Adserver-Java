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

import com.adsapient.util.Msg;
import com.adsapient.util.admin.AdsapientConstants;

import org.apache.log4j.Logger;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

public class DateReportEntity implements ReportEntity {
	private static Logger logger = Logger.getLogger(DateReportEntity.class);

	private Date endDate;

	private Date startDate;

	private String title;

	public DateReportEntity(HttpServletRequest request) {
		String start = request.getParameter("startDate");
		String end = request.getParameter("endDate");
		this.title = Msg.fetch("date.range.selector", request);

		try {
			endDate = AdsapientConstants.SAMPLE_DATE_FORMAT.parse(end);
			startDate = AdsapientConstants.SAMPLE_DATE_FORMAT.parse(start);
		} catch (Exception e) {
			logger.error("cant create dateReportEntity. Start date=" + start
					+ " and endDate=" + end, e);
		}
	}

	public boolean createExelReport(HSSFWorkbook wb) {
		HSSFSheet sheet = wb.getSheetAt(0);
		int beginRowNumber = sheet.getLastRowNum();
		int currentRowNumber = beginRowNumber;

		HSSFCellStyle cellStyleJUSTIFY = wb.createCellStyle();
		cellStyleJUSTIFY.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		HSSFRow row = sheet.createRow(++currentRowNumber);
		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue(title);
		cell.setCellStyle(cellStyleJUSTIFY);
		sheet.addMergedRegion(new Region(currentRowNumber, (short) 0,
				currentRowNumber, (short) 8));

		HSSFRow row2 = sheet.createRow(++currentRowNumber);
		HSSFCell cell2 = row2.createCell((short) 0);
		cell2.setCellValue(" Start Date (DD-MM-YYYY)");
		cell2.setCellStyle(cellStyleJUSTIFY);

		HSSFCell cell3 = row2.createCell((short) 4);
		cell3.setCellValue(" End Date (DD-MM-YYYY)");
		cell3.setCellStyle(cellStyleJUSTIFY);

		sheet.addMergedRegion(new Region(currentRowNumber, (short) 0,
				currentRowNumber, (short) 3));
		sheet.addMergedRegion(new Region(currentRowNumber, (short) 4,
				currentRowNumber, (short) 8));

		HSSFRow row3 = sheet.createRow(++currentRowNumber);
		HSSFCell cell4 = row3.createCell((short) 0);
		cell4.setCellValue(AdsapientConstants.SAMPLE_DATE_FORMAT
				.format(startDate));
		cell4.setCellStyle(cellStyleJUSTIFY);

		HSSFCell cell5 = row3.createCell((short) 4);
		cell5.setCellValue(AdsapientConstants.SAMPLE_DATE_FORMAT
				.format(startDate));
		cell5.setCellStyle(cellStyleJUSTIFY);

		sheet.addMergedRegion(new Region(currentRowNumber, (short) 0,
				currentRowNumber, (short) 3));
		sheet.addMergedRegion(new Region(currentRowNumber, (short) 4,
				currentRowNumber, (short) 8));

		return true;
	}
}
