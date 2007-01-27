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

import com.adsapient.api.AbstractPluginBannerClass;
import com.adsapient.api.PluginInstaller;

import com.adsapient.api_impl.advertizer.PluginsImpl;
import com.adsapient.api_impl.exceptions.PluginInstallerExeption;
import com.adsapient.api_impl.share.Type;

import com.adsapient.util.AdSapientHibernateService;
import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.admin.AdsapientConstants;
import com.adsapient.util.xml.XMLHelper;

import com.adsapient.gui.forms.PluginManagerActionForm;

import org.apache.log4j.Logger;

import org.hibernate.cfg.Configuration;

import org.w3c.dom.Document;

import java.util.Iterator;
import java.util.StringTokenizer;

public class PluginManager {
	static Logger logger = Logger.getLogger(PluginManager.class);

	private static PluginInstaller installer = null;

	public static PluginInstaller getInstallerInstance() {
		if (installer == null) {
			installer = new PluginInstallerImpl();
		}

		return installer;
	}

	public static void init() {
		Configuration configuration = new Configuration();
		Iterator pluginsIterator = MyHibernateUtil.viewAll(PluginsImpl.class)
				.iterator();
		logger.info("begin setup additional banners");

		while (pluginsIterator.hasNext()) {
			PluginsImpl plugin = (PluginsImpl) pluginsIterator.next();
			logger.info("begin setup banner:" + plugin.getClassName());

			Class pluginClass = null;

			try {
				pluginClass = Class.forName(plugin.getClassName());

				AbstractPluginBannerClass pluginObject = (AbstractPluginBannerClass) pluginClass
						.newInstance();

				logger.info("add new mapping documents:"
						+ plugin.getMappingData());
			} catch (Exception e) {
				logger.info("Cant find plugin:" + plugin.getClassName());
				plugin.setPresent(false);
				MyHibernateUtil.updateObject(plugin);

				continue;
			}

			String configurationMetedata = plugin.getMappingData();

			if ((configurationMetedata == null)
					|| AdsapientConstants.EMPTY
							.equalsIgnoreCase(configurationMetedata)) {
				logger
						.info("plugin "
								+ plugin.getClassName()
								+ " have now configuration file or theris error while trying to add them");

				continue;
			}

			StringTokenizer tokenizer = new StringTokenizer(
					configurationMetedata, ";");

			if (tokenizer.hasMoreTokens()) {
				while (tokenizer.hasMoreTokens()) {
					String resourceName = tokenizer.nextToken();
					Document mappingDocument = XMLHelper.parseXmlFile(
							resourceName, false, pluginClass);
					configuration.configure(mappingDocument);

					logger.info("add mapping document:" + resourceName);
				}
			}
		}

		configuration.configure("/hibernate.cfg.xml");

		AdSapientHibernateService.rebuildSessionFactory(configuration);
	}

	public static void pluginAddon(PluginManagerActionForm form)
			throws PluginInstallerExeption {
		String className = form.getClassName();

		try {
			Class test = PluginManager.class.getClassLoader().loadClass(
					className);
			Class pluginClass = Class.forName(className);
			logger.info("success find class:" + pluginClass);

			Object pluginObject = pluginClass.newInstance();
			logger.info("create object from class");

			if (pluginObject instanceof AbstractPluginBannerClass) {
				AbstractPluginBannerClass pluginBanner = (AbstractPluginBannerClass) pluginObject;
				logger.info("cast object to :" + pluginBanner.getClass());

				if (pluginBanner.plugin(PluginManager.getInstallerInstance())) {
					Type bannerType = new Type();
					bannerType.setType(form.getPluginName());

					Integer pluginTypeId = new Integer(MyHibernateUtil
							.addObject(bannerType));
					logger.info("add new banner type:" + bannerType.getType());

					PluginsImpl plugin = new PluginsImpl();
					plugin.setClassName(className);
					plugin.setMappingData(pluginBanner.getMappingDocuments());
					plugin.setPresent(true);
					plugin.setPluginName(form.getPluginName());
					plugin.setTypeId(pluginTypeId);
					MyHibernateUtil.addObject(plugin);

					PluginManager.init();
				}

				logger.info("install plugin " + className);
			} else {
				logger.warn("cant plugin object  that is not extend:"
						+ AbstractPluginBannerClass.class);
				throw new PluginInstallerExeption("");
			}
		} catch (Exception ex) {
			logger.warn("while try to add plugin", ex);
			throw new PluginInstallerExeption("error.while.install.plugin");
		}
	}

	public static void unpluginAddon(PluginManagerActionForm form) {
		PluginsImpl plugin = (PluginsImpl) MyHibernateUtil.loadObject(
				PluginsImpl.class, form.getId());

		MyHibernateUtil.removeObject(Type.class, plugin.getTypeId());

		MyHibernateUtil.removeObject(plugin);
	}
}
