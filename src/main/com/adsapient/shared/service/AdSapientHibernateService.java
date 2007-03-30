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
package com.adsapient.shared.service;

import org.apache.log4j.Logger;

import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import org.hibernate.cfg.Configuration;

import org.springframework.beans.BeansException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class AdSapientHibernateService implements ApplicationContextAware {
	static Logger logger = Logger.getLogger(AdSapientHibernateService.class);

	public static ApplicationContext ctx;

	public static String application = null;

	public static SessionFactory sessionFactory;

	private static Session testSession;

	private static String pathToHibernateConfigFile = "/hibernate.cfg.xml";

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		ctx = applicationContext;
		sessionFactory = (SessionFactory) ctx.getBean("sessionFactory");
	}

	public static void setTestSession(Session testSession) {
		AdSapientHibernateService.testSession = testSession;
	}

	public static void rebuildSessionFactory() {
		try {
		} catch (Exception ex) {
			logger.fatal(ex.getMessage(), ex);
		}
	}

	public static synchronized void rebuildSessionFactory(
			Configuration configuration) {
		try {
			rebuildSessionFactory();
		} catch (Exception ex) {
			throw new RuntimeException("problem with rebuild  session", ex);
		}
	}

	public static synchronized void closeSession(Session session,
			Transaction transaction) {
		if ((session != null) && session.equals(testSession)) {
			return;
		}

		try {
			if ((transaction != null) && !transaction.wasCommitted()) {
				try {
					transaction.rollback();
				} catch (HibernateException e) {
					logger.error(
							"error while trying to rollback wrong transaction",
							e);
				}
			}
		} catch (HibernateException e) {
			logger.error("error while trying to find out transaktion state", e);
		}

		if (session != null) {
			try {
				session.close();
			} catch (HibernateException eh) {
				logger.error("Exeption in close sesion:" + eh.getMessage());
			}
		}
	}

	public static synchronized Session openSession() throws HibernateException {
		if (sessionFactory == null) {
			return null;
		}

		if (testSession != null) {
			return testSession;
		}

		Session session = sessionFactory.openSession();
		session.setFlushMode(FlushMode.COMMIT);

		return session;
	}
}
