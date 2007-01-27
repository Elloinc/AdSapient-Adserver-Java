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
package com.adsapient.test;

import com.adsapient.api_impl.publisher.PlaceImpl;
import com.adsapient.api_impl.publisher.SiteImpl;
import com.adsapient.api_impl.usermanagment.UserImpl;

import org.apache.cactus.ServletTestCase;

import org.hibernate.Session;
import org.hibernate.Transaction;

public abstract class BaseServeTest extends ServletTestCase {
	protected UserImpl user;

	protected SiteImpl site;

	protected PlaceImpl place;

	Transaction transaction;

	Session session;

	public void setUp() throws Exception {
		reopenTestSession();
		transaction = session.beginTransaction();

		super.setUp();
	}

	private void reopenTestSession() {
	}

	public void tearDown() throws Exception {
		rollback();
		super.tearDown();
	}

	protected void rollback() {
		if (session.isOpen()) {
			transaction.rollback();
			session.close();
		}
	}

	public void doSetup() {
		user = BaseTestHelper.createUser();
		session.saveOrUpdate(user);
		session.flush();

		assertNotNull(user);

		session.saveOrUpdate(site);
		session.flush();

		assertNotNull(site);

		session.saveOrUpdate(place);
		session.flush();

		assertNotNull(place);
	}
}
