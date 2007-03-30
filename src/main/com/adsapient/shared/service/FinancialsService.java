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

import com.adsapient.shared.mappable.*;
import com.adsapient.shared.mappable.SiteImpl;
import com.adsapient.shared.mappable.RateImpl;
import com.adsapient.shared.mappable.UserImpl;
import com.adsapient.shared.AdsapientConstants;
import com.adsapient.shared.dao.HibernateEntityDao;

import com.adsapient.gui.forms.FinancialManagmentActionForm;
import com.adsapient.gui.forms.RateManagmentActionForm;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.Iterator;

public class FinancialsService {
    static Logger logger = Logger.getLogger(FinancialsService.class);
    private HibernateEntityDao hibernateEntityDao;

    public void updateUserRates(FinancialManagmentActionForm form) {
        Financial financialOptions = (Financial) hibernateEntityDao
                .loadObjectWithCriteria(Financial.class, "userId", new Integer(
                        form.getUserId()));

        if (financialOptions != null) {
            updateOptions(financialOptions, form);

            hibernateEntityDao.updateObject(financialOptions);
        } else {
            logger.error("Couldn't find rate object for userId:"
                    + form.getUserId());
        }

        UserImpl user = (UserImpl) hibernateEntityDao.loadObject(UserImpl.class,
                Integer.parseInt(form.getUserId()));

        if (user.getRole().equals(AdsapientConstants.ADVERTISER)) {
            Collection campaigns = hibernateEntityDao.viewWithCriteria(
                    CampainImpl.class, "userId", user.getId(), "userId");

            for (Object obj : campaigns) {
                CampainImpl campaign = (CampainImpl) obj;
                updateCampaignRates(campaign, financialOptions);
            }
        } else if (user.getRole().equals(AdsapientConstants.PUBLISHER)) {
            Collection campaigns = hibernateEntityDao.viewWithCriteria(
                    SiteImpl.class, "userId", user.getId(), "userId");

            for (Object obj : campaigns) {
                SiteImpl site = (SiteImpl) obj;
                updateSiteRates(site, financialOptions);
            }
        }
    }

    private void updateSiteRates(SiteImpl site, Financial financialOptions) {
        RateImpl rate = site.getRate();
        updateRate(rate, financialOptions);
        hibernateEntityDao.updateObject(rate);


        for (Object place : site.getRealPlaces()) {
            PlacesImpl pl = (PlacesImpl) place;
            rate = pl.getRate();
            updateRate(rate, financialOptions);
           hibernateEntityDao.updateObject(rate);

        }
    }

    private void updateCampaignRates(CampainImpl campaign,
                                     Financial financialOptions) {
        RateImpl rate = campaign.getRate();
        updateRate(rate, financialOptions);
        hibernateEntityDao.updateObject(rate);


        for (BannerImpl banner : campaign.getBanners()) {
            rate = banner.getRate();
            updateRate(rate, financialOptions);
            hibernateEntityDao.updateObject(rate);

        }
    }

    private void updateRate(RateImpl rate, Financial financialOptions) {
        rate.setCpmRate(financialOptions.getAdvertisingCPMrate());
        rate.setCpcRate(financialOptions.getAdvertisingCPCrate());
        rate.setCpsRate(financialOptions.getAdvertisingCPSrate());
        rate.setCplRate(financialOptions.getAdvertisingCPLrate());
    }

    public void updateOptions(Financial financialOptions,
                              FinancialManagmentActionForm form) {
        financialOptions.setAdvertisingCPCrate(convert2Money(form.getAdvertisingCPCrate()));
        financialOptions.setAdvertisingCPMrate(convert2Money(form.getAdvertisingCPMrate()));
        financialOptions.setAdvertisingCPLrate(convert2Money(form.getAdvertisingCPLrate()));
        financialOptions.setAdvertisingCPSrate(convert2Money(form.getAdvertisingCPSrate()));

        financialOptions.setPublishingCPCrate(convert2Money(form.getPublishingCPCrate()));
        financialOptions.setPublishingCPMrate(convert2Money(form.getPublishingCPMrate()));
        financialOptions.setPublishingCPLrate(convert2Money(form.getPublishingCPLrate()));
        financialOptions.setPublishingCPSrate(convert2Money(form.getPublishingCPSrate()));

        StringBuffer advBuffer = new StringBuffer();
        StringBuffer pubBuffer = new StringBuffer();

        if (!AdsapientConstants.EMPTY.equalsIgnoreCase(financialOptions
                .getAdvertisingType())) {
            if (form.isAdvCPM()) {
                advBuffer.append("1,");
            } else {
                advBuffer.append("0,");
            }

            if (form.isAdvCPC()) {
                advBuffer.append("1,");
            } else {
                advBuffer.append("0,");
            }

            if (form.isAdvCPL()) {
                advBuffer.append("1,");
            } else {
                advBuffer.append("0,");
            }

            if (form.isAdvCPS()) {
                advBuffer.append("1,");
            } else {
                advBuffer.append("0,");
            }
        }

        if (!AdsapientConstants.EMPTY.equalsIgnoreCase(financialOptions
                .getPublishingType())) {
            if (form.isPubCPM()) {
                pubBuffer.append("1,");
            } else {
                pubBuffer.append("0,");
            }

            if (form.isPubCPC()) {
                pubBuffer.append("1,");
            } else {
                pubBuffer.append("0,");
            }

            if (form.isPubCPL()) {
                pubBuffer.append("1,");
            } else {
                pubBuffer.append("0,");
            }

            if (form.isPubCPS()) {
                pubBuffer.append("1,");
            } else {
                pubBuffer.append("0,");
            }
        }

        if (!AdsapientConstants.EMPTY.equalsIgnoreCase(financialOptions
                .getAdvertisingType())
                && (!AdsapientConstants.EMPTY.equalsIgnoreCase(financialOptions
                        .getPublishingType()))) {
            financialOptions.setCommissionRate(form.getCommissionRate());
        }

        financialOptions.setAdvertisingType(advBuffer.toString());
        financialOptions.setPublishingType(pubBuffer.toString());
    }

    public void updateRate(RateManagmentActionForm form, RateImpl rate) {
        rate.setCpcRate(convert2Money(form.getCpcRate()));
        rate.setCpmRate(convert2Money(form.getCpmRate()));
        rate.setCplRate(convert2Money(form.getCplRate()));
        rate.setCpsRate(convert2Money(form.getCpsRate()));
        rate.setRateId(new Integer(form.getRateId()));

        StringBuffer advBuffer = new StringBuffer();

        if (form.isAdvCPM()) {
            advBuffer.append("1,");
        } else {
            advBuffer.append("0,");
        }

        if (form.isAdvCPC()) {
            advBuffer.append("1,");
        } else {
            advBuffer.append("0,");
        }

        if (form.isAdvCPL()) {
            advBuffer.append("1,");
        } else {
            advBuffer.append("0,");
        }

        if (form.isAdvCPS()) {
            advBuffer.append("1,");
        } else {
            advBuffer.append("0,");
        }

        rate.setRateType(advBuffer.toString());

        hibernateEntityDao.updateObject(rate);

    }

    public SystemCurrency getSystemCurrency() {
        Collection currencyCollection = hibernateEntityDao
                .viewAll(SystemCurrency.class);
        Iterator currencyIterator = currencyCollection.iterator();

        while (currencyIterator.hasNext()) {
            SystemCurrency currency = (SystemCurrency) currencyIterator.next();

            if (currency.isSystem()) {
                return currency;
            }
        }

        return null;
    }

    public static Integer convert2Money(float money) {
        Integer result;
        result = new Integer((int) (money * 1000));

        return result;
    }

    public static float transformMoney(Integer money) {
        float result = 0;

        if (money != null) {
            result = (float) (money.floatValue() / 1000);

            return result;
        } else {
            logger.error("trying to transform null. Return 0 as result");

            return result;
        }
    }

    public static float transformMoney(int money) {
        float result = 0;

        result = ((float) money) / 1000;

        return result;
    }

    public HibernateEntityDao getHibernateEntityDao() {
        return hibernateEntityDao;
    }

    public void setHibernateEntityDao(HibernateEntityDao hibernateEntityDao) {
        this.hibernateEntityDao = hibernateEntityDao;
    }
}
