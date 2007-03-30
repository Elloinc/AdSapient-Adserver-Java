package com.adsapient.shared.service;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Expression;
import com.adsapient.shared.mappable.*;
import com.adsapient.shared.exceptions.ConfigurationsException;
import com.adsapient.shared.AdsapientConstants;
import com.adsapient.shared.dao.HibernateEntityDao;
import com.adsapient.gui.forms.EditCampainActionForm;
import com.adsapient.gui.ContextAwareGuiBean;
import com.adsapient.api.AdsapientException;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CampaignService {
    static private Logger logger = Logger.getLogger(CampaignService.class);
    private HibernateEntityDao hibernateEntityDao;
    private BannerManagementService bannerManagementService;
    private RatesService ratesService;

    public static synchronized void campainsStateHandler() {
        logger.info(" begin campaigns StateHandler");

        long start = System.currentTimeMillis();
        Collection allUsersCampainsCollection = new ArrayList();
        Session session = null;
        Transaction tr = null;

        try {
            session = AdSapientHibernateService.openSession();

            tr = session.beginTransaction();

            allUsersCampainsCollection = session.createCriteria(
                    CampainImpl.class).add(
                    Expression.not(Expression.like("stateId", new Integer(
                            AdsapientConstants.DEFAULT_CAMPAIN_STATE_ID))))
                    .list();

            tr.commit();
        } catch (HibernateException ex) {
            logger
                    .error("This exception throw in -- campain state Handler",
                            ex);
        } finally {
            AdSapientHibernateService.closeSession(session, tr);
        }

        if (allUsersCampainsCollection != null) {
            Iterator campainsIterator = allUsersCampainsCollection.iterator();

            while (campainsIterator.hasNext()) {
                CampainImpl campain = (CampainImpl) campainsIterator.next();

                if (AdsapientConstants.DEFAULT_NOT_VERIFY_STATE_ID == campain
                        .getStateId()) {
                    continue;
                }

                if (!TimeService.isCampainPeriodActive(campain.getStartDate(),
                        campain.getEndDate())) {
                    updateCampainState(
                            campain,
                            AdsapientConstants.DEFAULT_STOPPED_CAMPAIN_STATE_ID);

                    continue;
                }

                if (UserDefineCampainStates.PAUSED.equalsIgnoreCase(campain
                        .getUserDefineCampainStateId())
                        || UserDefineCampainStates.COMPLETED
                                .equalsIgnoreCase(campain
                                        .getUserDefineCampainStateId())) {
                    updateCampainState(
                            campain,
                            AdsapientConstants.DEFAULT_STOPPED_CAMPAIN_STATE_ID);

                    continue;
                }

                if (AdsapientConstants.DEFAULT_ACTIVE_CAMPAIN_STATE_ID != campain
                        .getStateId()) {
                    updateCampainState(
                            campain,
                            AdsapientConstants.DEFAULT_ACTIVE_CAMPAIN_STATE_ID);
                }
            }
        }

        logger.info("finish campain state handler.Operation time is "
                + (System.currentTimeMillis() - start));
    }

    public static final void updateCampainState(CampainImpl campain,
                                                int campainStateId) {
        Session session = null;
        Transaction tr = null;

        try {
            session = AdSapientHibernateService.openSession();

            tr = session.beginTransaction();

            CampainImpl editableCampain = (CampainImpl) session.load(campain
                    .getClass(), campain.getCampainId());

            editableCampain.setStateId(campainStateId);

            tr.commit();
        } catch (HibernateException ex) {
            logger
                    .error("This exception throw in -- campain state Handler",
                            ex);
        } finally {
            AdSapientHibernateService.closeSession(session, tr);
        }
    }

       public void removeAllUsersCampain(UserImpl user) {
        Collection campainsCollection = hibernateEntityDao.viewWithCriteria(
                CampainImpl.class, "userId", user.getId(), "campainId");

        if ((campainsCollection != null) && (!campainsCollection.isEmpty())) {
            Iterator campainsIterator = campainsCollection.iterator();

            while (campainsIterator.hasNext()) {
                CampainImpl campain = (CampainImpl) campainsIterator.next();
                campain.delete(null, null);
            }
        }

        hibernateEntityDao.removeWithCriteria(BannerImpl.class, "userId", user
                .getId());

        UploadService.removeAllUsersFile(user);
    }

    public Collection viewCampains(Integer userId) {
        Collection coll = new ArrayList();

        Session session = null;
        Transaction tr = null;

        try {
            session = AdSapientHibernateService.openSession();
            tr = session.beginTransaction();

            coll = session.createCriteria(CampainImpl.class).add(
                    Expression.like("userId", userId)).add(
                    Expression.not(Expression.like("stateId", new Integer(
                            AdsapientConstants.DEFAULT_CAMPAIN_STATE_ID))))
                    .list();

            tr.commit();
        } catch (HibernateException ex) {
            logger.error("This exception throw in -- vew campains ", ex);
            logger.error(ex.getLocalizedMessage());
        } finally {
            AdSapientHibernateService.closeSession(session, tr);
        }

        return coll;
    }

    public Collection checkCampains(String campaignName) {
        Collection coll = new ArrayList();

        Session session = null;
        Transaction tr = null;

        try {
            session = AdSapientHibernateService.openSession();
            tr = session.beginTransaction();

            coll = session.createCriteria(CampainImpl.class).add(
                    Expression.like("name", campaignName)).list();
            tr.commit();
        } catch (HibernateException ex) {
            logger.error("This exception throw in -- vew campains ", ex);
            logger.error(ex.getLocalizedMessage());
        } finally {
            AdSapientHibernateService.closeSession(session, tr);
        }

        return coll;
    }

    public BannerImpl getBannerFromSystemDefaultCampaignBySizeId(
            Integer sizeId) {
        CampainImpl campain = (CampainImpl) hibernateEntityDao.loadObject(
                CampainImpl.class,
                AdsapientConstants.DEFAULT_SYSTEM_CAMPAIN_ID);

        Iterator defaultBannerdIterator = campain.getBanners().iterator();

        while (defaultBannerdIterator.hasNext()) {
            BannerImpl banner = (BannerImpl) defaultBannerdIterator.next();

            if (banner.getSizeId().intValue() == sizeId.intValue()) {
                return banner;
            }
        }

        return null;
    }

    public void addNewDefaultCampain(UserImpl user) {
         RatesService ratesService = (RatesService) ContextAwareGuiBean.getContext().getBean("ratesService");
        CookieManagementService cookieManagementService = (CookieManagementService) ContextAwareGuiBean.getContext().getBean("cookieManagementService");
        Integer campainId;

        CampainImpl camp = new CampainImpl();
        camp.setName("default");
        camp.setUserId(user.getId());
        camp
                .setUserDefineCampainStateId(AdsapientConstants.DEFAULT_USER_DEFAIN_CAMPAIN_STATE);

        camp.setStateId(AdsapientConstants.DEFAULT_CAMPAIN_STATE_ID);
        ratesService.createCampainRate(camp, user);
        campainId = new Integer((String) hibernateEntityDao.save(camp));

        bannerManagementService.addDefaultBanners2DefaultCampain(campainId, user);
    }

    public void checkDefaultSystemCampain() {
        logger.info("Default campaign manager is started");

        Collection sizesCollection = hibernateEntityDao.viewAll(Size.class);
        Collection bannersCollection = new ArrayList();
        Iterator bannerIterator = bannersCollection.iterator();
        boolean saveEnable;

        CampainImpl campainImpl = (CampainImpl) hibernateEntityDao.loadObject(
                CampainImpl.class, new Integer(0));

        bannersCollection = (List<BannerImpl>) hibernateEntityDao
                .viewWithCriteria(BannerImpl.class, "campainId", campainImpl
                        .getId(), "bannerId");

        Iterator sizesIterator = sizesCollection.iterator();

        while (sizesIterator.hasNext()) {
            saveEnable = true;

            Size size = (Size) sizesIterator.next();

            bannerIterator = bannersCollection.iterator();

            while (bannerIterator.hasNext()) {
                BannerImpl bannerFromCollection = (BannerImpl) bannerIterator
                        .next();

                if (bannerFromCollection.getSizeId().equals(size.getSizeId())) {
                    bannerFromCollection.setFile(size.getDefaultFileName());

                    bannerFromCollection.setStatus(BannerImpl.STATUS_DEFAULT);

                    hibernateEntityDao.updateObject(bannerFromCollection);

                    saveEnable = false;
                }
            }

            if (saveEnable) {
                BannerImpl banner = new BannerImpl();
                campainImpl.udateBanner(banner);
                banner.setFile(size.getDefaultFileName());
                banner.setFileName("default");
                banner.setContentType("image/gif");
                banner.setSizeId(size.getSizeId());
                banner.setTypeId(size.getDefaultFileTypeId());
                banner.setStatus(BannerImpl.STATUS_DEFAULT);

                logger
                        .info("bil dobavlen banner dlia default campain with size"
                                + size.getSize());

                ratesService.createBannerRate(banner,
                        AdsapientConstants.DEFAULT_SYSTEM_CAMPAIN_ID);

                hibernateEntityDao.save(banner);
            }
        }
    }

    public void viewDefaultCampain(UserImpl user,
                                          EditCampainActionForm form) throws AdsapientException {
        CampainImpl campain = viewDefaultCampain(user.getId());
        form.init(campain);
        form.setAction("edit");
    }

    public CampainImpl viewDefaultCampain(Integer userId)
            throws AdsapientException {
        CampainImpl campain = null;

        try {
            campain = (CampainImpl) hibernateEntityDao
                    .viewWithCriteria(
                            CampainImpl.class,
                            "userId",
                            userId,
                            "stateId",
                            new Integer(
                                    AdsapientConstants.DEFAULT_CAMPAIN_STATE_ID))
                    .iterator().next();
        } catch (Exception e) {
            logger.error(
                    "Error while trying to access to default campain for user with id="
                            + userId, e);
            throw new ConfigurationsException("Can't access to default campain");
        }

        return campain;
    }

    public HibernateEntityDao getHibernateEntityDao() {
        return hibernateEntityDao;
    }

    public void setHibernateEntityDao(HibernateEntityDao hibernateEntityDao) {
        this.hibernateEntityDao = hibernateEntityDao;
    }

    public BannerManagementService getBannerManagementService() {
        return bannerManagementService;
    }

    public void setBannerManagementService(BannerManagementService bannerManagementService) {
        this.bannerManagementService = bannerManagementService;
    }

    public RatesService getRatesService() {
        return ratesService;
    }

    public void setRatesService(RatesService ratesService) {
        this.ratesService = ratesService;
    }
}
