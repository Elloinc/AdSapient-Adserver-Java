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
package com.adsapient.util.xml;

import org.apache.log4j.Logger;

import org.w3c.dom.Document;

import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XMLHelper {
	private static Logger logger = Logger.getLogger(XMLHelper.class);

	public static Document parseXmlFile(String filename, boolean validating,
			Class classLoaderSource) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			factory.setValidating(validating);

			Document doc = factory.newDocumentBuilder().parse(
					classLoaderSource.getClassLoader().getResourceAsStream(
							filename));

			return doc;
		} catch (SAXException e) {
			logger.warn("while trying open document" + filename, e);
		} catch (ParserConfigurationException e) {
			logger.warn("while trying open document" + filename, e);
		} catch (IOException e) {
			logger.warn("while trying open document" + filename, e);
		}

		return null;
	}
}
