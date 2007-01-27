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

import com.adsapient.adserver.AdserverDecisionProvider;
import com.adsapient.adserver.ModelUpdater;

import com.adsapient.api_impl.advertizer.BannerImpl;

import com.adsapient.shared.dao.HibernateEntityDao;

import junit.framework.TestCase;

import org.hibernate.Transaction;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import org.springframework.orm.hibernate3.HibernateTemplate;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

abstract public class SpringTestCase extends TestCase {
	protected static ApplicationContext appContext;

	public String MODULE = "GUI";

	protected TransactionStatus currentTransaction;

	protected HibernateTemplate hibernateTemplate;

	protected PlatformTransactionManager txManager;

	protected AdserverDecisionProvider adserverDecisionProvider;

	protected ModelUpdater modelUpdater;

	protected HibernateEntityDao hibernateEntityDao;

	Transaction transaction;

	public SpringTestCase() {
		super();
	}

	public SpringTestCase(String s) {
		super(s);
	}

	protected void setUp() throws Exception {
		super.setUp();

		if (appContext == null) {
			appContext = new ClassPathXmlApplicationContext(getFileList());
		}

		adserverDecisionProvider = (AdserverDecisionProvider) appContext
				.getBean("adserverDecisionProvider");
		modelUpdater = (ModelUpdater) appContext.getBean("modelUpdater");
		hibernateEntityDao = (HibernateEntityDao) appContext
				.getBean("hibernateEntityDao");

		hibernateTemplate = (HibernateTemplate) appContext
				.getBean("hibernateTemplate");

		txManager = (PlatformTransactionManager) appContext
				.getBean("transactionManager");

		currentTransaction = txManager
				.getTransaction(new TransactionDefinition() {
					public int getPropagationBehavior() {
						return PROPAGATION_REQUIRED;
					}

					public int getIsolationLevel() {
						return ISOLATION_DEFAULT;
					}

					public int getTimeout() {
						return 1000;
					}

					public boolean isReadOnly() {
						return false;
					}

					public String getName() {
						return null;
					}
				});

		currentTransaction.setRollbackOnly();

		doSetUp();
	}

	protected String[] getFileList() {
		return new String[] { "applicationContext-adserver.xml" };
	}

	protected void doSetUp() throws Exception {
	}

	protected void doTearDown() throws Exception {
	}

	protected void tearDown() throws Exception {
		doTearDown();

		txManager = (PlatformTransactionManager) appContext
				.getBean("transactionManager");
		txManager.rollback(currentTransaction);
	}

	public void assertBannersAreEqual(BannerImpl banner1, BannerImpl banner2) {
		assertEquals(banner1.getBannerId(), banner2.getBannerId());
	}
}
