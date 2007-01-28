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
package com.adsapient.gui;

import com.adsapient.api_impl.advertizer.BannerImpl;
import com.adsapient.api_impl.advertizer.CampainImpl;
import com.adsapient.api_impl.install.InstallItem;
import com.adsapient.api_impl.managment.money.MoneyManager;
import com.adsapient.api_impl.share.ApplicationOptImpl;
import com.adsapient.api_impl.share.Size;

import com.adsapient.shared.api.entity.IMappable;
import com.adsapient.shared.dao.HibernateEntityDao;
import com.adsapient.shared.mappable.CountryAbbrEntity;
import com.adsapient.shared.mappable.SystemSettings;

import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.admin.AdsapientConstants;
import com.adsapient.util.admin.ConfigurationConstants;
import com.adsapient.util.filters.FiltersUtil;
import com.adsapient.util.plugin.ApplicationInstaller;

import org.apache.log4j.Logger;

import org.springframework.beans.BeansException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class ContextAwareGuiBean implements ApplicationContextAware {
	private static Logger logger = Logger.getLogger(ContextAwareGuiBean.class);

	public static ApplicationContext ctx;

	public static String application = null;

	private String pathToBanners;

	private HibernateEntityDao hibernateEntityDao;

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		ctx = applicationContext;
	}

	public static void install() {
		try {
			ApplicationInstaller.createTables();

			ContextAwareGuiBean contextAwareGuiBean = (ContextAwareGuiBean) ContextAwareGuiBean.ctx
					.getBean("contextAwareGuiBean");
			contextAwareGuiBean.setupFiles();
			contextAwareGuiBean.setupDefaultBanners();
		} catch (Exception e) {
			logger.error("Proble fill database", e);
		}
	}

	public void installIfNecessary() {
		try {
			Properties props = new Properties();
			props.load(ContextAwareGuiBean.class.getClassLoader()
					.getResourceAsStream("adsapient.properties"));

			Connection con;
			String createString;
			Statement stmt;
			Class.forName((String) props.get("db.driver"));

			String qweryString = "select * from banner;";

			con = DriverManager.getConnection((String) props.get("db.url"),
					(String) props.get("db.username"), (String) props
							.get("db.password"));
			stmt = con.createStatement();

			stmt.executeQuery(qweryString);
			stmt.close();
			con.close();
		} catch (Exception ex) {
			install();
		}
	}

	@SuppressWarnings("unchecked")
	public void setup() {
		installIfNecessary();

		try {
			System.setProperty("java.awt.headless", "true");

			Iterator applicationOptions = hibernateEntityDao.viewAll(
					ApplicationOptImpl.class).iterator();

			while (applicationOptions.hasNext()) {
				ApplicationOptImpl option = (ApplicationOptImpl) applicationOptions
						.next();

				if ("application".equalsIgnoreCase(option.getName())) {
					application = option.getValue();
				}
			}

			List<IMappable> systemSettings = (List<IMappable>) hibernateEntityDao
					.viewAll(SystemSettings.class);

			for (IMappable im : systemSettings) {
				SystemSettings ss = (SystemSettings) im;
				int ti = ss.getTypeid();

				switch (ti) {
				case AdsapientConstants.BROWSER_TYPE_ID:
					FiltersUtil.BrowsersMap.put(ss.getSskey(), ss.getSsvalue());

					break;

				case AdsapientConstants.OS_TYPE_ID:
					FiltersUtil.SystemsMap.put(ss.getSskey(), ss.getSsvalue());

					break;

				case AdsapientConstants.LANGUAGE_TYPE_ID:
					FiltersUtil.LangsMap.put(ss.getSskey(), ss.getSsvalue());

					break;
				}
			}

			try {
				List<CountryAbbrEntity> countries = (List<CountryAbbrEntity>) hibernateEntityDao
						.viewAllWithOrder(CountryAbbrEntity.class,
								"countryName");
				FiltersUtil.fillCountryMap(countries);
			} catch (Exception e) {
				logger.error("Error getting countryList", e);
			}
		} catch (Exception ex) {
			logger.error("setup error", ex);
		}
	}

	public boolean setupFiles() {
		clean();

		Collection installItems = hibernateEntityDao.viewAll(InstallItem.class);
		logger.info("Begin application installation..");

		Iterator itemsIterator = installItems.iterator();

		while (itemsIterator.hasNext()) {
			InstallItem item = (InstallItem) itemsIterator.next();
			String path2install = pathToBanners + item.getFilePath();

			if (InstallItem.COMMAND_COPY.equalsIgnoreCase(item.getCommand())) {
				try {
					logger.info("Copy file from " + item.getServerURL()
							+ " to " + path2install);

					InputStream in = ContextAwareGuiBean.class.getClassLoader()
							.getResourceAsStream(item.getServerURL());

					if (in == null) {
						logger.error("Couldn't locate on server:"
								+ item.getServerURL());

						continue;
					}

					OutputStream out = new FileOutputStream(path2install);
					byte[] b = new byte[in.available()];
					int bytesRead = in.read(b);

					if (bytesRead != -1) {
						out.write(b);
					}

					in.close();
					out.close();
				} catch (IOException e) {
					logger.error("Error while setup application data", e);
				}
			}

			if (InstallItem.COMMAND_MKDIR.equalsIgnoreCase(item.getCommand())) {
				logger.info("execute mkdir command");
				new File(path2install).mkdirs();
				logger.info("success create directorys " + path2install);
			}
		}

		logger.info("Finish application installer");

		return true;
	}

	public void clean() {
		try {
			File f = new File(pathToBanners);
			deleteDir(f);
			f.mkdirs();
		} catch (Exception e) {
			logger.error("Unable to clean directory "
					+ AdsapientConstants.PATH_TO_BANNERS, e);
		}
	}

	public static boolean deleteDir(File dir) {
		if (dir.exists()) {
			File[] files = dir.listFiles();

			if (files != null) {
				for (int i = 0; i < files.length; i++)
					if (files[i].isDirectory()) {
						deleteDir(files[i]);
					} else {
						files[i].delete();
					}
			}
		}

		return (dir.delete());
	}

	@SuppressWarnings("unchecked")
	public void setupDefaultBanners() {
		CampainImpl campain = (CampainImpl) hibernateEntityDao.loadObject(
				CampainImpl.class,
				ConfigurationConstants.DEFAULT_SYSTEM_CAMPAIN_ID);

		List<Size> sizes = (List<Size>) MyHibernateUtil.viewAll(Size.class);

		List<BannerImpl> banners = (List<BannerImpl>) MyHibernateUtil
				.viewWithCriteria(BannerImpl.class, "campainId",
						ConfigurationConstants.DEFAULT_SYSTEM_CAMPAIN_ID,
						"bannerId");

		for (Size size : sizes) {
			boolean found = false;

			for (BannerImpl banner : banners)
				if (banner.getSizeId().equals(size.getId())) {
					banner.setFile(size.getDefaultFileName());
					banner.setStatus(BannerImpl.STATUS_DEFAULT);
					MyHibernateUtil.updateObject(banner);
					found = true;
				}

			if (!found) {
				try {
					BannerImpl banner = new BannerImpl();
					banner.setFile(size.getDefaultFileName());
					banner.setFileName("default");
					banner.setContentType("image/gif");
					banner.setSizeId(size.getSizeId());
					banner.setTypeId(size.getDefaultFileTypeId());
					banner.setStatus(BannerImpl.STATUS_DEFAULT);
					campain.udateBanner(banner);

					logger.info(new StringBuffer("added new banner for size")
							.append(size.getSize()).toString());

					MoneyManager.createBannerRate(banner,
							ConfigurationConstants.DEFAULT_SYSTEM_CAMPAIN_ID);

					MyHibernateUtil.addObject(banner);
				} catch (Exception e) {
					logger.error("cannot save default banner", e);
				}
			}
		}
	}

	public String getPathToBanners() {
		return pathToBanners;
	}

	public void setPathToBanners(String pathToBanners) {
		this.pathToBanners = pathToBanners;
	}

	public HibernateEntityDao getHibernateEntityDao() {
		return hibernateEntityDao;
	}

	public void setHibernateEntityDao(HibernateEntityDao hibernateEntityDao) {
		this.hibernateEntityDao = hibernateEntityDao;
	}
}
