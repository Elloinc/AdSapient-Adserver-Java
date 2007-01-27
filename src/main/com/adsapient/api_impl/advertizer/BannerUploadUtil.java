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
package com.adsapient.api_impl.advertizer;

import com.adsapient.api_impl.managment.StatisticManager;
import com.adsapient.api_impl.managment.application.ApplicationManagment;
import com.adsapient.api_impl.resource.ResourceImpl;
import com.adsapient.api_impl.share.Size;
import com.adsapient.api_impl.usability.advertizer.BannerRepresentation;
import com.adsapient.api_impl.usermanagment.RateImpl;
import com.adsapient.api_impl.usermanagment.UserImpl;

import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.admin.AdsapientConstants;

import com.adsapient.gui.forms.BannerUploadFormAction;

import org.apache.log4j.Logger;

import org.apache.struts.upload.FormFile;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class BannerUploadUtil {
	private static Logger logger = Logger.getLogger(BannerUploadUtil.class);

	public static File getBannerFile(BannerImpl banner) {
		String resultPath;

		if (banner == null) {
			logger
					.warn("trying to retrive banner with null instead banner object");
			resultPath = ApplicationManagment.application
					+ "/banners/default/default.jpe";
		} else {
			resultPath = banner.getFile();
		}

		return new File(AdsapientConstants.PATH_TO_BANNERS + resultPath);
	}

	public static String readFile(File file) {
		StringBuffer contents = new StringBuffer();

		BufferedReader input = null;

		try {
			input = new BufferedReader(new FileReader(file));

			char[] buffer = new char[2048];

			int length = 0;

			while ((length = input.read(buffer)) != -1) {
				String test = new String(buffer);

				contents.append(buffer);
			}
		} catch (FileNotFoundException ex) {
			logger.error("Cant find file " + file, ex);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (input != null) {
					input.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		return contents.toString();
	}

	public static void removeAllUsersFile(UserImpl user) {
		File userHomeDirectory = new File("banners/" + user.getId());

		if (userHomeDirectory.exists()) {
			File[] filesList = userHomeDirectory.listFiles();

			for (int fileIndex = 0; fileIndex < filesList.length; fileIndex++) {
				filesList[fileIndex].delete();
			}

			userHomeDirectory.delete();
		}
	}

	public static void removeBanner(Integer bannerId) {
		BannerImpl ban = (BannerImpl) MyHibernateUtil.loadObjectWithCriteria(
				BannerImpl.class, "bannerId", bannerId);

		if (ban != null) {
			StatisticManager.remove(ban);

			MyHibernateUtil.removeObject(ban);

			StatisticManager.remove(ban);

			MyHibernateUtil.removeObject(RateImpl.class, ban.getRateId());
		} else {
			logger.error("Error in removeBanner :can't load  banner with id="
					+ bannerId);
		}
	}

	public static void removeFile(String filePath) {
		File file2Remove = new File(filePath);

		if (!file2Remove.exists()) {
			logger.warn("WARNING in remove file : file-" + filePath
					+ "dont exist");
		} else {
			file2Remove.delete();
			logger.debug("file " + filePath + "was removed from system");
		}
	}

	public static String saveDefaultBannerFile(FormFile file) {
		String data = null;

		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			InputStream stream = file.getInputStream();

			StringBuffer sb = new StringBuffer();
			sb.append(ApplicationManagment.application).append("/banners/")
					.append("default");

			File tempFile = new File(sb.toString());

			tempFile.mkdirs();

			OutputStream bos = new FileOutputStream(sb.append("/").append(
					file.getFileName()).toString());

			int bytesRead = 0;
			byte[] buffer = new byte[8192];

			while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}

			bos.close();

			stream.close();

			return sb.toString();
		} catch (FileNotFoundException fnfe) {
			System.err.println(fnfe.getMessage());

			return "";
		} catch (IOException ioe) {
			System.out.println("Exception in save file");
			System.err.println(ioe.getMessage());

			return "";
		}
	}

	public static void saveFile(BannerUploadFormAction form) {
		String data = null;

		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			InputStream stream = form.getTheFile().getInputStream();

			String path = AdsapientConstants.PATH_TO_BANNERS + form.getUserId();
			File tempFile = new File(path);

			tempFile.mkdirs();

			OutputStream bos = new FileOutputStream(path + "/"
					+ form.getBannerId());

			form.setFile(form.getUserId() + "/" + form.getBannerId());

			int bytesRead = 0;
			byte[] buffer = new byte[8192];

			while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}

			bos.close();

			stream.close();
		} catch (FileNotFoundException fnfe) {
			System.err.println(fnfe.getMessage());
		} catch (IOException ioe) {
			System.out.println("Exception in save file");
			System.err.println(ioe.getMessage());
		}
	}

	public static Collection viewCampainBanners(Integer campainId,
			boolean enableTransformation) {
		Collection banners = new ArrayList();

		banners = MyHibernateUtil.viewWithCriteria(BannerImpl.class,
				"campainId", campainId, "bannerId");

		if (!enableTransformation) {
			return banners;
		}

		Collection transformetedBannersCollection = new ArrayList();
		Iterator bannersIterator = banners.iterator();

		while (bannersIterator.hasNext()) {
			BannerImpl userBanner = (BannerImpl) bannersIterator.next();
			userBanner.setSize((Size) MyHibernateUtil.loadObject(Size.class,
					userBanner.getSizeId()));

			transformetedBannersCollection.add(new BannerRepresentation(
					userBanner));
		}

		return transformetedBannersCollection;
	}

	public static Collection viewUserBanners(Class objClass, Integer userId,
			boolean enableTransformation) {
		Collection bannersCollection = new ArrayList();

		bannersCollection = MyHibernateUtil.viewWithCriteria(objClass,
				"userId", userId, "bannerId");

		if (!enableTransformation) {
			return bannersCollection;
		}

		Collection transformetedBannersCollection = new ArrayList();
		Iterator bannersIterator = bannersCollection.iterator();

		while (bannersIterator.hasNext()) {
			Object userObject = bannersIterator.next();

			if (userObject instanceof ResourceImpl) {
				continue;
			}

			BannerImpl userBanner = (BannerImpl) userObject;
			userBanner.setSize((Size) MyHibernateUtil.loadObject(Size.class,
					userBanner.getSizeId()));

			transformetedBannersCollection.add(new BannerRepresentation(
					userBanner));
		}

		return transformetedBannersCollection;
	}
}
