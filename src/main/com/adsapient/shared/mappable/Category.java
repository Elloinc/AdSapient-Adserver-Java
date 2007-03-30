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

import com.adsapient.api.NameSupportInterface;
import com.adsapient.api.IMappable;

import com.adsapient.shared.service.AdSapientHibernateService;

import org.apache.log4j.Logger;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import org.hibernate.criterion.Expression;

import java.util.ArrayList;
import java.util.Collection;

public class Category implements NameSupportInterface, IMappable  {
	static private Logger logger = Logger.getLogger(Category.class);

	private Integer id;

	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return name;
	}

	public String getLabel() {
		return name;
	}

	public Collection checkCategoryName(String catName) {
		Collection coll = new ArrayList();
		Session session = null;
		Transaction tr = null;

		try {
			session = AdSapientHibernateService.openSession();
			tr = session.beginTransaction();
			coll = session.createCriteria(Category.class).add(
					Expression.like("name", catName)).list();
			tr.commit();
		} catch (HibernateException ex) {
			logger.error("This exception throw in -- add category ", ex);
			logger.error(ex.getLocalizedMessage());
		} finally {
			AdSapientHibernateService.closeSession(session, tr);
		}

		return coll;
	}
}
