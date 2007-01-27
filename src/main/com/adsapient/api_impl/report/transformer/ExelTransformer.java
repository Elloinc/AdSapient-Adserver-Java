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
package com.adsapient.api_impl.report.transformer;

import com.adsapient.api.ReportEntity;
import com.adsapient.api.ReportTransformer;

import com.adsapient.api_impl.report.ReportPage;

import org.apache.log4j.Logger;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.IOException;

import java.util.Iterator;

import javax.servlet.http.HttpServletResponse;

public class ExelTransformer implements ReportTransformer {
	private static Logger logger = Logger.getLogger(ExelTransformer.class);

	public void transform(ReportPage page, HttpServletResponse response)
			throws IOException {
		logger.info("begin exel transformation");

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("Adsapient Report");

		Iterator contentIterator = page.getContent().iterator();

		while (contentIterator.hasNext()) {
			ReportEntity entity = (ReportEntity) contentIterator.next();
			entity.createExelReport(wb);
		}

		response.setContentType("application/vnd.ms-excel");
		wb.write(response.getOutputStream());
		logger.info("finish exel transformer");
	}
}
