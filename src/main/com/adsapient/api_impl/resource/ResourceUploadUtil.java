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
package com.adsapient.api_impl.resource;

import com.adsapient.api.AdsapientException;

import com.adsapient.api_impl.exceptions.ConfigurationsException;
import com.adsapient.api_impl.managment.application.ApplicationManagment;

import com.adsapient.util.MyHibernateUtil;

import com.adsapient.gui.forms.ResourceUploadForm;

import org.apache.log4j.Logger;

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

public class ResourceUploadUtil {
	private static Logger logger = Logger.getLogger(ResourceUploadUtil.class);

	public static File getResourceFile(Integer resourceId) {
		ResourceImpl resource = (ResourceImpl) MyHibernateUtil.loadObject(
				ResourceImpl.class, resourceId);

		return getResourceFile(resource);
	}

	public static String getFile(Integer resourceId) {
		ResourceImpl resource = (ResourceImpl) MyHibernateUtil.loadObject(
				ResourceImpl.class, resourceId);

		return resource.getFile();
	}

	public static File getResourceFile(ResourceImpl res) {
		String resultPath = null;

		if (res == null) {
			logger
					.error("trying to retrive resource with null instead resource object");
		} else {
			resultPath = res.getFile();
		}

		return new File(resultPath);
	}

	public static String readFile(File file) {
		StringBuffer contents = new StringBuffer();

		BufferedReader input = null;

		try {
			input = new BufferedReader(new FileReader(file));

			char[] buffer = new char[2048];

			int length = 0;

			while ((length = input.read(buffer)) != -1) {
				contents.append(buffer);
			}
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
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

	public static void removeResource(Integer resourceId)
			throws AdsapientException {
		ResourceImpl res = (ResourceImpl) MyHibernateUtil.loadObject(
				ResourceImpl.class, resourceId);

		if (res != null) {
			ResourceUploadUtil.removeFile(res.getFile());

			MyHibernateUtil.removeObject(res);
		} else {
			logger
					.error("Error in removeresource :can't load  resource with id="
							+ resourceId);
			throw new ConfigurationsException(
					"Error in removeresource :can't load  resource with id="
							+ resourceId);
		}
	}

	public static void saveFile(ResourceUploadForm form) {
		String data = null;

		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			InputStream stream = form.getTheFile().getInputStream();

			StringBuffer sb = new StringBuffer();
			sb.append(ApplicationManagment.application).append("/resources/")
					.append(form.getUserId());

			File tempFile = new File(sb.toString());

			tempFile.mkdirs();

			OutputStream bos = new FileOutputStream(sb.append("/").append(
					form.getResourceId()).toString());

			form.setFile(sb.toString());

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

	public static Collection viewUserResources(Integer userId, String orderField) {
		Collection resourcesCollection = new ArrayList();

		resourcesCollection = MyHibernateUtil.viewWithCriteria(
				ResourceImpl.class, "userId", userId, orderField);

		Collection transformetedResourcesCollection = new ArrayList();
		Iterator resourcesIterator = resourcesCollection.iterator();

		while (resourcesIterator.hasNext()) {
			ResourceImpl res = (ResourceImpl) resourcesIterator.next();
			transformetedResourcesCollection
					.add(new ResourceRepresentation(res));
		}

		return transformetedResourcesCollection;
	}
}
