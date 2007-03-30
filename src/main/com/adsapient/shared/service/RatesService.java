package com.adsapient.shared.service;

import org.apache.log4j.Logger;
import com.adsapient.api.Banner;
import com.adsapient.shared.mappable.*;
import com.adsapient.shared.AdsapientConstants;
import com.adsapient.shared.dao.HibernateEntityDao;

public class RatesService {
    static Logger logger = Logger.getLogger(RatesService.class);
    private HibernateEntityDao hibernateEntityDao;

    public boolean createBannerRate(Banner banner, Integer campainId) {
        CampainImpl campain = (CampainImpl) hibernateEntityDao.loadObject(
                CampainImpl.class, campainId);

        RateImpl campainRate = (RateImpl) hibernateEntityDao.loadObject(
                RateImpl.class, campain.getRateId());

        if (campainRate == null) {
            logger.error("cant find rate with id=" + campain.getRateId());

            return false;
        }

        RateImpl bannerRates = campainRate.createInstance();

        String bannerRatesId = String.valueOf(hibernateEntityDao
                .save(bannerRates));

        updateReporterDB(bannerRates);

        banner.setRateId(new Integer(bannerRatesId));

        return true;
    }

    public void createCampainRate(CampainImpl campain, UserImpl user) {
        Financial userFinancial = (Financial) hibernateEntityDao
                .loadObjectWithCriteria(Financial.class, "userId", user.getId());

        RateImpl rate = new RateImpl();
        rate.setRateType(userFinancial.getAdvertisingType());
        rate.setCpcRate(userFinancial.getAdvertisingCPCrate());
        rate.setCpmRate(userFinancial.getAdvertisingCPMrate());
        rate.setCplRate(userFinancial.getAdvertisingCPLrate());
        rate.setCpsRate(userFinancial.getAdvertisingCPSrate());

        String rateId = String.valueOf(hibernateEntityDao.save(rate));

        updateReporterDB(rate);
        campain.setRateId(new Integer(rateId));
    }

    public void createPlacesRate(PlacesImpl places, Integer siteId) {
        SiteImpl site = (SiteImpl) hibernateEntityDao.loadObject(SiteImpl.class,
                siteId);

        RateImpl siteRate = (RateImpl) hibernateEntityDao.loadObject(
                RateImpl.class, site.getRateId());

        RateImpl placesRate = siteRate.createInstance();

        String rateId = String.valueOf(hibernateEntityDao.save(placesRate));

        updateReporterDB(placesRate);
        places.setRateId(new Integer(rateId));
    }

    public void createSiteRate(SiteImpl site, UserImpl user) {
        Financial userFinancial = (Financial) hibernateEntityDao
                .loadObjectWithCriteria(Financial.class, "userId", user.getId());

        RateImpl rate = new RateImpl();
        rate.setRateType(userFinancial.getPublishingType());
        rate.setCpcRate(userFinancial.getPublishingCPCrate());
        rate.setCpmRate(userFinancial.getPublishingCPMrate());
        rate.setCplRate(userFinancial.getPublishingCPLrate());
        rate.setCpsRate(userFinancial.getPublishingCPSrate());

        String rateId = String.valueOf(hibernateEntityDao.save(rate));

        updateReporterDB(rate);

        site.setRateId(new Integer(rateId));
    }

    public void createUserFinancial(UserImpl user) {
        Financial financial = (Financial) hibernateEntityDao.loadObject(
                Financial.class, AdsapientConstants.SYSTEM_FINANCIAL_ID);

        Financial userFinancial = new Financial();
        userFinancial.setUserId(user.getId());

        if (AdsapientConstants.ADVERTISER.equals(user.getRole())) {
            userFinancial.setAdvertisingCPCrate(financial
                    .getAdvertisingCPCrate());
            userFinancial.setAdvertisingCPMrate(financial
                    .getAdvertisingCPMrate());
            userFinancial.setAdvertisingCPSrate(financial
                    .getAdvertisingCPSrate());
            userFinancial.setAdvertisingCPLrate(financial
                    .getAdvertisingCPLrate());

            userFinancial.setAdvertisingType(financial.getAdvertisingType());
        }

        if (AdsapientConstants.PUBLISHER.equals(user.getRole())) {
            userFinancial
                    .setPublishingCPCrate(financial.getPublishingCPCrate());
            userFinancial
                    .setPublishingCPMrate(financial.getPublishingCPMrate());
            userFinancial
                    .setPublishingCPSrate(financial.getPublishingCPSrate());
            userFinancial
                    .setPublishingCPLrate(financial.getPublishingCPLrate());

            userFinancial.setPublishingType(financial.getPublishingType());
        }

        if (AdsapientConstants.ADVERTISERPUBLISHER.equals(user.getRole())
                || (AdsapientConstants.ADMIN.equalsIgnoreCase(user.getRole()))
                || (AdsapientConstants.HOSTEDSERVICE.equalsIgnoreCase(user
                        .getRole()))) {
            userFinancial.setAdvertisingCPCrate(financial
                    .getAdvertisingCPCrate());
            userFinancial
                    .setPublishingCPCrate(financial.getPublishingCPCrate());

            userFinancial.setAdvertisingCPSrate(financial
                    .getAdvertisingCPSrate());
            userFinancial
                    .setPublishingCPSrate(financial.getPublishingCPSrate());

            userFinancial.setAdvertisingCPLrate(financial
                    .getAdvertisingCPLrate());
            userFinancial
                    .setPublishingCPLrate(financial.getPublishingCPLrate());

            userFinancial.setAdvertisingCPMrate(financial
                    .getAdvertisingCPMrate());
            userFinancial
                    .setPublishingCPMrate(financial.getPublishingCPMrate());
            userFinancial.setCommissionRate(financial.getCommissionRate());

            userFinancial.setAdvertisingType(financial.getAdvertisingType());
            userFinancial.setPublishingType(financial.getPublishingType());
        }

        hibernateEntityDao.save(userFinancial);

        Account userAccount = new Account();
        userAccount.setUserId(user.getId());

        hibernateEntityDao.save(userAccount);

        BillingInfoImpl systemBilling = (BillingInfoImpl) hibernateEntityDao
                .loadObject(BillingInfoImpl.class,
                        AdsapientConstants.SYSTEM_USER_ID);
        BillingInfoImpl userBilling = systemBilling.getCopy(user.getId());
        hibernateEntityDao.save(userBilling);
    }

    public Account loadUserAccount(Integer userId) {
        Account userAccount = (Account) hibernateEntityDao.loadObjectWithCriteria(
                Account.class, "userId", userId);

        return userAccount;
    }

    public void removeUserFinance(UserImpl user) {
        hibernateEntityDao.removeWithCriteria(Financial.class, "userId", user
                .getId());

        hibernateEntityDao.removeWithCriteria(Account.class, "userId", user
                .getId());

        hibernateEntityDao.removeWithCriteria(BillingInfoImpl.class, "userId",
                user.getId());
    }

    private static void updateReporterDB(RateImpl rate) {
        try {
        } catch (Exception ex) {
            logger.info("Reporter SQL Exception " + ex.getMessage());
        }
    }

    public HibernateEntityDao getHibernateEntityDao() {
        return hibernateEntityDao;
    }

    public void setHibernateEntityDao(HibernateEntityDao hibernateEntityDao) {
        this.hibernateEntityDao = hibernateEntityDao;
    }
}
