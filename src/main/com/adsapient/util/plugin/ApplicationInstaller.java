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
package com.adsapient.util.plugin;

import com.adsapient.api_impl.exceptions.PluginInstallerExeption;
import com.adsapient.api_impl.install.InstallItem;

import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.admin.AdsapientConstants;
import com.adsapient.util.sql.ConnectionManager;

import org.apache.log4j.Logger;

import org.apache.struts.action.ActionServlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ApplicationInstaller {
	private static Logger logger = Logger.getLogger(ApplicationInstaller.class);

	public static boolean createTables() throws PluginInstallerExeption {
		Collection sqlRequests = new ArrayList();
		String pathToSqlFiles = "/setup/sql";
		String extension = ".sql";
		String[] sqlFiles = new String[] {};
		List<String> fileNamesList = new ArrayList<String>();

		try {
			File f = new File(ApplicationInstaller.class.getResource(
					pathToSqlFiles).getFile());

			if (f.getPath().indexOf(".jar") != -1) {
				String pathToJarFile = f.getPath().substring(6,
						f.getPath().indexOf(".jar") + 4);
				fileNamesList = listFileNamesInJar(pathToJarFile,
						pathToSqlFiles, extension);
			} else {
				f.toURI();

				File[] ffs = f.listFiles();

				for (File file : ffs) {
					if (file.getName().endsWith(".sql")) {
						fileNamesList.add(file.getName());
					}
				}
			}

			sqlFiles = fileNamesList.toArray(new String[fileNamesList.size()]);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		for (int fileCount = 0; fileCount < sqlFiles.length; fileCount++) {
			logger.info("parsing ..." + sqlFiles[fileCount]);

			InputStream stream = ApplicationInstaller.class.getClassLoader()
					.getResourceAsStream(
							pathToSqlFiles + "/" + sqlFiles[fileCount]);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					stream));
			String str;

			try {
				while ((str = reader.readLine()) != null) {
					if (str.startsWith("#")
							|| (AdsapientConstants.EMPTY.equalsIgnoreCase(str))) {
						continue;
					}

					StringBuffer buffer = new StringBuffer();
					buffer.append(str);

					if (str.indexOf(";") > -1) {
						sqlRequests.add(str);

						continue;
					}

					String str2;

					while ((str2 = reader.readLine()) != null) {
						if (str2.startsWith("#")) {
							continue;
						}

						buffer.append(str2);

						if (str2.indexOf(";") > -1) {
							break;
						}
					}

					sqlRequests.add(buffer.toString());
				}

				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			ConnectionManager.test(sqlRequests);
		} catch (Exception e) {
			logger.info("Problem with database acces" + e.getMessage(), e);
			throw new PluginInstallerExeption("Problem with database acces"
					+ e.getMessage());
		}

		return true;
	}

	private static List<String> listFileNamesInJar(String pathToJar,
			String pathToFolderInJar, String extension) {
		if (pathToFolderInJar.startsWith("/")) {
			pathToFolderInJar = pathToFolderInJar.substring(1,
					pathToFolderInJar.length());
		}

		List<String> l = new ArrayList<String>();

		try {
			JarFile zf = new JarFile(pathToJar);
			Enumeration entries = zf.entries();

			while (entries.hasMoreElements()) {
				JarEntry ze = (JarEntry) entries.nextElement();

				if (ze.getName().startsWith(pathToFolderInJar)
						&& ze.getName().endsWith(extension)) {
					l.add(ze.getName().substring(pathToFolderInJar.length(),
							ze.getName().length()));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return l;
	}

	public static boolean setup(ActionServlet actionServlet) {
		Collection setupCommands = new ArrayList();

		if (new File("banners/resource/GeoIP.dat").exists()) {
			logger.info("The application files is correct. Skip installation ");

			return false;
		}

		Collection installItems = MyHibernateUtil.viewAll(InstallItem.class);
		logger.info("Begin application installetion..");

		Iterator itemsIterator = installItems.iterator();

		while (itemsIterator.hasNext()) {
			InstallItem item = (InstallItem) itemsIterator.next();

			if (InstallItem.COMMAND_COPY.equalsIgnoreCase(item.getCommand())) {
				try {
					InputStream in = actionServlet.getServletContext()
							.getResourceAsStream(item.getServerURL());

					if (in == null) {
						logger.error("Couldn't locate on server:"
								+ item.getServerURL());

						continue;
					}

					OutputStream out = new FileOutputStream(item.getFilePath());

					int bytesRead = 0;

					while ((bytesRead = in.read()) != -1) {
						out.write(bytesRead);
					}

					in.close();
					out.close();
				} catch (IOException e) {
					logger.error("Error while setup application data", e);
				}

				logger.info("Copy file from " + item.getServerURL() + " to "
						+ item.getFilePath());
			}

			if (InstallItem.COMMAND_MKDIR.equalsIgnoreCase(item.getCommand())) {
				logger.info("execute mkdir command");
				new File(item.getFilePath()).mkdirs();
				logger.info("success create directorys " + item.getFilePath());
			}
		}

		logger.info("Finish application installer");

		return true;
	}
}
