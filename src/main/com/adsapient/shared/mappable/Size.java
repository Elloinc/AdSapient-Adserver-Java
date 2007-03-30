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
package com.adsapient.shared.mappable;

import com.adsapient.api.IMappable;
import com.adsapient.shared.AdsapientConstants;
import com.adsapient.shared.service.AdSapientHibernateService;

import org.apache.log4j.Logger;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import org.hibernate.criterion.Expression;

import java.util.ArrayList;
import java.util.Collection;

public class Size implements IMappable {
	static private Logger logger = Logger.getLogger(Size.class);

	private Integer sizeId;

	private String defaultFileName = "";

	private String size;

	private Integer defaultFileTypeId = Type.IMAGE_TYPE_ID;

	private int height;

	private int maxBannerSize = AdsapientConstants.MAX_BANNER_SIZE_DEFAULT_VALUE;

	private int width;

	public Size() {
		this.height = 120;
		this.width = 120;
	}

	public void setDefaultFileName(String defaultFileName) {
		this.defaultFileName = defaultFileName;
	}

	public String getDefaultFileName() {
		return defaultFileName;
	}

	public void setDefaultFileTypeId(Integer defaultFileTypeId) {
		this.defaultFileTypeId = defaultFileTypeId;
	}

	public Integer getDefaultFileTypeId() {
		return defaultFileTypeId;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getHeight() {
		return height;
	}

	public void setMaxBannerSize(int maxBannerSize) {
		this.maxBannerSize = maxBannerSize;
	}

	public int getMaxBannerSize() {
		return maxBannerSize;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getSize() {
		return size;
	}

	public void setSizeId(Integer sizeId) {
		this.sizeId = sizeId;
	}

	public Integer getSizeId() {
		return sizeId;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getWidth() {
		return width;
	}

	public Integer getId() {
		return sizeId;
	}

	public Collection checkSize(String size) {
		Collection coll = new ArrayList();
		Session session = null;
		Transaction tr = null;

		try {
			session = AdSapientHibernateService.openSession();
			tr = session.beginTransaction();
			coll = session.createCriteria(Size.class).add(
					Expression.like("size", size)).list();
			tr.commit();
		} catch (HibernateException ex) {
			logger.error("This exception throw in -- add size ", ex);
			logger.error(ex.getLocalizedMessage());
		} finally {
			AdSapientHibernateService.closeSession(session, tr);
		}

		return coll;
	}
}
