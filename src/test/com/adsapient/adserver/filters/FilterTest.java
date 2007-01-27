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
package com.adsapient.adserver.filters;

import com.adsapient.adserver.AdserverDecisionProvider;
import com.adsapient.adserver.ModelUpdater;

import com.adsapient.api.FilterInterface;

import com.adsapient.api_impl.advertizer.BannerImpl;
import com.adsapient.api_impl.advertizer.CampainImpl;
import com.adsapient.api_impl.filter.KeyWordsFilterElement;
import com.adsapient.api_impl.filter.ParametersFilter;
import com.adsapient.api_impl.settings.DynamicParameter;

import com.adsapient.shared.api.entity.IMappable;
import com.adsapient.shared.dao.HibernateEntityDao;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import org.hibernate.Transaction;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import org.springframework.orm.hibernate3.HibernateTemplate;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;


public class FilterTest extends TestCase {
    private static Logger logger = Logger.getLogger(FilterTest.class);
    protected static ApplicationContext appContext;
    protected TransactionStatus currentTransaction;
    protected HibernateTemplate hibernateTemplate;
    protected PlatformTransactionManager txManager;
    protected AdserverDecisionProvider adserverDecisionProvider;
    protected ModelUpdater modelUpdater;
    protected HibernateEntityDao hibernateEntityDao;
    Transaction transaction;

    @Override
    protected void runTest() throws Throwable {
        super.runTest();
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        try {
            if (appContext == null) {
                appContext = new ClassPathXmlApplicationContext(getFileList());
            }
        } catch (Exception e) {
            logger.error("", e);
        }

        adserverDecisionProvider = (AdserverDecisionProvider) appContext.getBean(
                "adserverDecisionProvider");
        modelUpdater = (ModelUpdater) appContext.getBean("modelUpdater");
        hibernateEntityDao = (HibernateEntityDao) appContext.getBean(
                "hibernateEntityDao");
        hibernateTemplate = (HibernateTemplate) appContext.getBean(
                "hibernateTemplate");
        txManager = (PlatformTransactionManager) appContext.getBean(
                "transactionManager");

        currentTransaction = txManager.getTransaction(new TransactionDefinition() {
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
    }

    protected void tearDown() throws Exception {
        txManager = (PlatformTransactionManager) appContext.getBean(
                "transactionManager");
        txManager.rollback(currentTransaction);
    }

    protected String[] getFileList() {
        return new String[] { "applicationContext-adserver.xml" };
    }

    public void testFilter5() {
        modelUpdater.update(DynamicParameter.class, new Integer(1), null);
        assertTrue(hasCampaignFilter(ParametersFilter.class.getName(),
                new Integer(1)));
        assertFalse(hasBannerFilter(ParametersFilter.class.getName(),
                new Integer(1)));
    }

    public void testFilter6() {
        modelUpdater.update(DynamicParameter.class, new Integer(3), null);
        assertTrue(hasCampaignFilter(ParametersFilter.class.getName(),
                new Integer(1)));
        assertFalse(hasBannerFilter(ParametersFilter.class.getName(),
                new Integer(3)));
    }

    public void testFilter7() {
        modelUpdater.update(KeyWordsFilterElement.class, new Integer(10), null);
        assertTrue(hasCampaignFilter(KeyWordsFilterElement.class.getName(),
                new Integer(1)));
        assertFalse(hasBannerFilter(KeyWordsFilterElement.class.getName(),
                new Integer(10)));
    }

    public void testFilter8() {
        modelUpdater.update(KeyWordsFilterElement.class, new Integer(2), null);
        assertTrue(hasCampaignFilter(KeyWordsFilterElement.class.getName(),
                new Integer(1)));
        assertFalse(hasBannerFilter(KeyWordsFilterElement.class.getName(),
                new Integer(1)));
    }

    private boolean hasBannerFilter(String className, Integer id) {
        for (BannerImpl banner : modelUpdater.getAdserverModel().getBannersPool()) {
            if (banner.getFilters() != null) {
                for (FilterInterface filter : banner.getFilters()) {
                    if (filter.getClass().getName().equals(className) &&
                            ((IMappable) filter).getId().equals(id)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean hasCampaignFilter(String className, Integer id) {
        for (IMappable im : modelUpdater.getAdserverModel().getCampaignsMap()
                                        .values()) {
            CampainImpl camp = (CampainImpl) im;

            if (camp.getFilters() != null) {
                for (FilterInterface filter : camp.getFilters()) {
                    if (filter.getClass().getName().equals(className) &&
                            ((IMappable) filter).getId().equals(id)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
