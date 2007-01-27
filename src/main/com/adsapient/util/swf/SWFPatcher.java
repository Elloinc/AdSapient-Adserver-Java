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

import org.infozone.tools.xml.queries.XUpdateQuery;
import org.infozone.tools.xml.queries.XUpdateQueryFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class SWFPatcher {
	public void applyAdsapientPatch() {
	}

	protected Node updateFile(String queryFile, String inputFile)
			throws Exception {
		String query = parseUpdateFile(queryFile);
		Document result = parseInputFile(inputFile);

		XUpdateQuery xupdate = XUpdateQueryFactory.newInstance()
				.newXUpdateQuery();
		xupdate.setQString(query);
		xupdate.execute(result);

		return result;
	}

	private String prepareXMLFile(String inputFileName) throws Exception {
		if (inputFileName == null) {
			throw new IllegalArgumentException(
					"name of update file must not be null !");
		}

		File file = new File(inputFileName);
		BufferedReader br = new BufferedReader(new FileReader(file));
		char[] characters = new char[new Long(file.length()).intValue()];
		br.read(characters, 0, new Long(file.length()).intValue());
		br.close();

		// create file as string
		String xmlFileString = new String(characters);
		xmlFileString = xmlFileString.replaceFirst(
				"xmlns='http://www.kineticfusion.org/RVML/1.0'", "");

		return xmlFileString;
	}

	protected String parseUpdateFile(String filename) throws Exception {
		if (filename == null) {
			throw new IllegalArgumentException(
					"name of update file must not be null !");
		}

		File file = new File(filename);
		BufferedReader br = new BufferedReader(new FileReader(file));
		char[] characters = new char[new Long(file.length()).intValue()];
		br.read(characters, 0, new Long(file.length()).intValue());
		br.close();

		return new String(characters);
	}

	protected Document parseInputFile(String filename) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			factory.setValidating(false);
			factory.setIgnoringComments(true);
			factory.setNamespaceAware(true);
			factory.setCoalescing(false);

			Document doc = factory.newDocumentBuilder().parse(
					new ByteArrayInputStream(prepareXMLFile(filename)
							.getBytes()));

			return doc;
		} catch (SAXException e) {
		} catch (ParserConfigurationException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
