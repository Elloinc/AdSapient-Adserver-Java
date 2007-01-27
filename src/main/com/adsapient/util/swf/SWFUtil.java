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
package com.adsapient.util.swf;

import com.kinesis.KineticFusion;

import org.w3c.dom.Node;

import java.io.File;
import java.io.IOException;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class SWFUtil {
	private void transform2SWF(String swfFileName) throws IOException {
		String[] args = { "processFile", "toSWF", "/banner/temp/result.xml",
				swfFileName };

		KineticFusion.main(args);
	}

	private void transform2XML(String swfFileName) throws IOException {
		String[] args = { "processFile", "toXML", swfFileName,
				"banner/temp/temp.xml" };

		KineticFusion.main(args);
	}

	private void transformXML() throws Exception {
		String inputFile = "banners/temp/temp.xml";
		String updateFile = "banners/temp/xupdate.xml";

		String resultFile = "banners/temp/result.xml";

		SWFPatcher patcher = new SWFPatcher();
		Node result = null;
		result = patcher.updateFile(updateFile, inputFile);

		Source source = new DOMSource(result);

		File file = new File(resultFile);
		Result resultStream = new StreamResult(file);

		Transformer xformer = TransformerFactory.newInstance().newTransformer();
		xformer.transform(source, resultStream);
	}

	public synchronized boolean patchSWFFile(String fileName) throws Exception {
		this.transform2XML(fileName);

		this.transformXML();

		this.transform2SWF("banners/temp/upd.swf");

		return true;
	}
}
