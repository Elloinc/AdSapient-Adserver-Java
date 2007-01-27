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
package com.adsapient.api_impl.managment.plugin;

import com.adsapient.api.PluginInstaller;

import com.adsapient.api_impl.exceptions.PluginInstallerExeption;

import com.adsapient.util.MyHibernateUtil;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PluginInstallerImpl implements PluginInstaller {
	private static Logger logger = Logger.getLogger(PluginInstallerImpl.class);

	public void executeSQL(String filePath, Class pluginClass)
			throws PluginInstallerExeption {
		logger.info("Begin install for SQL resource:" + filePath);

		InputStream stream = pluginClass.getResourceAsStream(filePath);

		if (stream == null) {
			stream = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(filePath);
		}

		if (stream == null) {
			logger.warn(filePath + " not found");
			throw new PluginInstallerExeption(filePath + " not found");
		}

		try {
			BufferedReader in = new BufferedReader(
					new InputStreamReader(stream));
			StringBuffer buffer = new StringBuffer();
			String str;

			while ((str = in.readLine()) != null) {
				buffer.append(str);
			}

			in.close();

			MyHibernateUtil.executeNativeSQL(buffer.toString());
			logger.info("execute sql script while setup:" + buffer.toString());
		} catch (IOException e) {
			logger.warn("while read resource:" + filePath);
		}
	}
}
