package com.adsapient.shared.service;

import com.adsapient.api.AdsapientException;
import com.adsapient.api.EmailMessageInterface;
import com.adsapient.api.User;
import com.adsapient.shared.mappable.AccountSetting;
import com.adsapient.shared.mappable.EmailMessageImpl;
import com.adsapient.shared.mappable.SiteImpl;
import com.adsapient.shared.mappable.UserImpl;
import com.adsapient.shared.AdsapientConstants;
import com.adsapient.shared.dao.HibernateEntityDao;
import com.adsapient.gui.ContextAwareGuiBean;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class UserManagementService {
    Logger logger = Logger.getLogger(UserManagementService.class);
    private CampaignService campaignService;
    private HibernateEntityDao hibernateEntityDao;

    public void removePublisher(UserImpl user) {
        Collection sitesCollection = hibernateEntityDao.viewWithCriteria(
                SiteImpl.class, "userId", user.getId(), "siteId");

        if ((sitesCollection != null) && (!sitesCollection.isEmpty())) {
            Iterator sitesIterator = sitesCollection.iterator();

            while (sitesIterator.hasNext()) {
                SiteImpl site = (SiteImpl) sitesIterator.next();
                site.delete(null, null);
            }
        }
    }

    public Collection viewPublisherSites(Integer publisherId) {
        Collection sites = null;
        sites = hibernateEntityDao.viewWithCriteria(SiteImpl.class, "userId",
                publisherId, "siteId");

        return sites;
    }

    public void userRegisterevent(Integer userId,
                                         HttpServletRequest request) {
        UserImpl user = (UserImpl) hibernateEntityDao.loadObject(UserImpl.class,
                userId);

        StringBuffer buffer = new StringBuffer();
        buffer.append("Hello,").append(user.getName()).append("\n");
        buffer.append("Welcome to AdSapient Banner Management System.").append(
                "\n");
        buffer.append("You can login to AdSapient at: http://").append(
                request.getServerName()).append(":").append(
                request.getServerPort()).append(request.getContextPath())
                .append("\n");
        buffer.append("\n");
        buffer.append("Your account details:").append("\n");
        buffer.append("Name:").append(user.getName()).append("\n");
        buffer.append("Login:").append(user.getLogin()).append("\n");
        buffer.append("Password:").append(user.getPassword()).append("\n");
        buffer.append("Email:").append(user.getEmail()).append("\n");
        buffer.append("User role:").append(user.getRole()).append("\n");
        buffer.append("\n");
        buffer.append("---").append("\n");
        buffer.append("Regards,").append("\n");
        buffer.append("AdSapient");

        EmailMessageInterface message = new EmailMessageImpl(user.getEmail(),
                buffer.toString(), "Welcome to AdSapient",
                "contact@adsapient.com");

        EmailService.deliveryMessage(message);
    }

    public void editUser(UserImpl user) {
        hibernateEntityDao.updateObject(user);
        updateReporterDB(user);
    }

    public void rejectUser(User user) {
    }

    public void removeUser(Integer userId) throws AdsapientException {
        RatesService ratesService = (RatesService) ContextAwareGuiBean.getContext().getBean("ratesService");
        UserImpl user = (UserImpl) hibernateEntityDao.loadObject(UserImpl.class,
                userId);

        removePublisher(user);

        campaignService.removeAllUsersCampain(user);

        ratesService.removeUserFinance(user);
        hibernateEntityDao.removeObject(user);
    }

    public boolean signInUser(UserImpl user, HttpServletRequest request) {
        RatesService ratesService = (RatesService) ContextAwareGuiBean.getContext().getBean("ratesService");
        Integer userId = null;

        Collection coll = hibernateEntityDao.viewWithCriteria(UserImpl.class,
                "login", user.getLogin(), "id");

        if (!coll.isEmpty()) {
            return false;
        }

        userId = new Integer((Integer) hibernateEntityDao.save(user));

        updateReporterDB(user);

       ratesService.createUserFinancial(user);

        if (AdsapientConstants.ADVERTISERPUBLISHER.equalsIgnoreCase(user.getRole())) {
            campaignService.addNewDefaultCampain(user);
        }

        AccountSetting settings = new AccountSetting();
        settings.setUserId(userId);
        hibernateEntityDao.save(settings);

        logger.info("user was registered . His id is " + userId);
        userRegisterevent(userId, request);

        return true;
    }

    public UserImpl viewUser(Integer userId) {
        UserImpl user = (UserImpl) hibernateEntityDao.loadObject(UserImpl.class,
                userId);

        return user;
    }

    public UserImpl viewUser(String login, String password)
            throws AdsapientException {
        UserImpl user = null;

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

        Session session = null;
        Transaction tx = null;

        try {
            session = AdSapientHibernateService.openSession();
            tx = session.beginTransaction();

            org.hibernate.Query query = session
                    .createQuery("select user from UserImpl user where user.stateId =:stateId");
            query.setInteger("stateId", new Integer(
                    AdsapientConstants.DEFAULT_VERIFYING_STATE_ID));

            List usersList = query.list();
            List users = new ArrayList();
            Iterator itr = usersList.iterator();

            while (itr.hasNext()) {
                UserImpl usr = (UserImpl) itr.next();

                if (usr.getLogin().equals(login)
                        && usr.getPassword().equals(password)) {
                    users.add(usr);
                }
            }

            if (!users.isEmpty()) {
                user = (UserImpl) users.iterator().next();
            }

            tx.commit();
        } catch (HibernateException ex) {
            logger.error("exeption in view User ", ex);
        } finally {
            AdSapientHibernateService.closeSession(session, tx);
        }

        return user;
    }

    public Collection viewUsersList() {
        Collection coll = new ArrayList();
        Session session = null;
        Transaction tr = null;

        try {
            session = AdSapientHibernateService.openSession();
            tr = session.beginTransaction();

            coll = session
                    .createCriteria(UserImpl.class)
                    .add(
                            Expression.not(Expression.like("role",
                                    AdsapientConstants.ADMIN)))
                    .add(
                            Expression
                                    .like(
                                    "stateId",
                                    new Integer(
                                            AdsapientConstants.DEFAULT_VERIFYING_STATE_ID)))
                    .addOrder(Order.asc("id")).list();
            tr.commit();
        } catch (HibernateException ex) {
            System.err.println(ex.getLocalizedMessage());
        } finally {
            AdSapientHibernateService.closeSession(session, tr);
        }

        return coll;
    }

    private void updateReporterDB(UserImpl user) {
        try {
        } catch (Exception ex) {
            logger.info("Reporter SQL Exception " + ex.getMessage());
        }
    }

    public CampaignService getCampaignService() {
        return campaignService;
    }

    public void setCampaignService(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    public HibernateEntityDao getHibernateEntityDao() {
        return hibernateEntityDao;
    }

    public void setHibernateEntityDao(HibernateEntityDao hibernateEntityDao) {
        this.hibernateEntityDao = hibernateEntityDao;
    }

}
