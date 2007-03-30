package com.adsapient.shared.service;

import com.adsapient.api.IMappable;
import com.adsapient.shared.AdsapientConstants;
import com.adsapient.shared.dao.HibernateEntityDao;
import com.adsapient.shared.mappable.UniqueUser;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.util.*;

public class CookieManagementService {
    static Logger logger = Logger.getLogger(CookieManagementService.class);
    private LinkHelperService linkHelperService;
    private HibernateEntityDao hibernateEntityDao;

    private static final StringBuffer sbUrlUpdate1 = new StringBuffer("sv?")
            .append(AdsapientConstants.EVENTID_REQUEST_PARAM_NAME).append("=")
            .append(AdsapientConstants.UPDATE_ENTITY_ADSERVEREVENT_TYPE)
            .append("&")
            .append(AdsapientConstants.CLASSNAME_REQUEST_PARAM_NAME)
            .append("=");
    private static final StringBuffer sbUrlUpdate2 = new StringBuffer("&")
            .append(AdsapientConstants.ENTITYID_REQUEST_PARAM_NAME).append("=");

//    public static int getCount(String query) {
//        Transaction tx = null;
//        Session session = null;
//
//        try {
//            session = AdSapientHibernateService.openSession();
//            tx = session.beginTransaction();
//
//            Integer count = 0;
//            Object obj = session.createQuery(query).uniqueResult();
//
//            if (obj instanceof Integer) {
//                count = (Integer) obj;
//            } else if (obj instanceof Long) {
//                Long l = (Long) obj;
//                count = l.intValue();
//            }
//
//            tx.commit();
//
//            if (count == null) {
//                return 0;
//            } else {
//                return count.intValue();
//            }
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//        } finally {
//            AdSapientHibernateService.closeSession(session, tx);
//        }
//
//        return 0;
//    }
//
//    public static List getObject(String query) {
//        Transaction tx = null;
//        Session session = null;
//
//        try {
//            session = AdSapientHibernateService.openSession();
//
//            if (session == null) {
//                return null;
//            }
//
//            tx = session.beginTransaction();
//
//            List result = session.createQuery(query).list();
//            tx.commit();
//
//            return result;
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//
//            return new ArrayList();
//        } finally {
//            AdSapientHibernateService.closeSession(session, tx);
//        }
//    }
//
//    public static Collection getSQLCollection(String query) {
//        Transaction tx = null;
//        Session session = null;
//
//        try {
//            session = AdSapientHibernateService.openSession();
//            tx = session.beginTransaction();
//
//
//            tx.commit();
//
//
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//        } finally {
//            AdSapientHibernateService.closeSession(session, tx);
//        }
//
//        return new ArrayList();
//    }
//
//    public static int getSQLCount(String query) {
//        Transaction tx = null;
//        Session session = null;
//
//        try {
//            session = AdSapientHibernateService.openSession();
//            tx = session.beginTransaction();
//
//            tx.commit();
//
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//
//            return 0;
//        } finally {
//            AdSapientHibernateService.closeSession(session, tx);
//        }
//        return 0;
//    }
//
//    public Integer addObject(Object hibernateObject) {
//        Transaction tx = null;
//        Integer resultId = null;
//        Session session = null;
//
//        try {
//            session = AdSapientHibernateService.openSession();
//            tx = session.beginTransaction();
//
//            Object tempObject = session.save(hibernateObject);
//
//            resultId = (Integer) tempObject;
//
//            tx.commit();
//
//            sendUpdateEventToAdserver(hibernateObject.getClass(), resultId,
//                    null);
//        } catch (Exception ex) {
//            logger.error(ex.getMessage());
//        } finally {
//            AdSapientHibernateService.closeSession(session, tx);
//        }
//
//        return resultId;
//    }
//
//    public void sendUpdateEventToAdserver(Class entityClass, Serializable entityId, String params) {
//
//        AdserverSender as = new AdserverSender(entityClass, entityId, params);
//        TimeKiller tk1 = new TimeKiller(as, 60000);
//        as.start();
//    }
//
//    private class AdserverSender extends Thread {
//        private Class entityClass;
//        private Serializable entityId;
//        private String params;
//
//        public AdserverSender(Class entityClass, Serializable entityId, String params) {
//            this.entityClass = entityClass;
//            this.entityId = entityId;
//            this.params = params;
//        }
//
//        public void run() {
//            try {
//
//                URL u = new URL(new StringBuffer(linkHelperService
//                        .getPathToAdServer()).append(sbUrlUpdate1).append(
//                        entityClass.getName()).append(sbUrlUpdate2)
//                        .append(entityId).append((params == null) ? "" : params)
//                        .toString());
//
//                BufferedReader in = new BufferedReader(new InputStreamReader(u
//                        .openStream()));
//
//                String inputLine;
//                StringBuffer sb = new StringBuffer();
//
//                while ((inputLine = in.readLine()) != null) {
//                    sb.append(inputLine);
//                    sb.append("\n");
//                }
//
//                in.close();
//            } catch (Exception ex) {
//                logger.error(ex.getMessage());
//            }
//        }
//    }
//
//    public static void executeNativeSQL(String query) {
//        Transaction tx = null;
//        Session session = null;
//
//        try {
//            session = AdSapientHibernateService.openSession();
//            tx = session.beginTransaction();
//
//            session.createSQLQuery(query).list();
//            tx.commit();
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//        } finally {
//            AdSapientHibernateService.closeSession(session, tx);
//        }
//    }
//
//    public static Object loadObject(Class objClass, String ObjectId) {
//        Transaction tx = null;
//        Session session = null;
//
//        try {
//            session = AdSapientHibernateService.openSession();
//            tx = session.beginTransaction();
//
//            Object result = session.get(objClass, ObjectId);
//            tx.commit();
//
//            if (result != null) {
//                return result;
//            }
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//        } finally {
//            AdSapientHibernateService.closeSession(session, tx);
//        }
//
//        return null;
//    }
//
//    public static Object loadObject(Class objClass, Integer ObjectId) {
//        Transaction tx = null;
//        Session session = null;
//
//        try {
//            session = AdSapientHibernateService.openSession();
//            tx = session.beginTransaction();
//
//            Object result = session.load(objClass, ObjectId);
//            tx.commit();
//
//            if (result != null) {
//                return result;
//            } else {
//                logger.info("cant load object " + objClass + "  with id = "
//                        + ObjectId);
//            }
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//        } finally {
//            AdSapientHibernateService.closeSession(session, tx);
//        }
//
//        return null;
//    }
//
//    public static synchronized Object loadObjectWithCriteria(
//            Class hibernateObjectClass, String criteriaName,
//            Object criteriaValue) {
//        Transaction tx = null;
//        List collect = new ArrayList();
//        Session session = null;
//
//        try {
//            session = AdSapientHibernateService.openSession();
//
//            collect = session.createCriteria(hibernateObjectClass).add(
//                    Expression.like(criteriaName, criteriaValue)).list();
//            session.flush();
//
//            if ((collect != null) && (!collect.isEmpty())) {
//                if (collect.size() != 1) {
//                    logger.warn("Waring there are several instances of class "
//                            + hibernateObjectClass + " with field "
//                            + criteriaName + " and value " + criteriaValue
//                            + ", the count is " + collect.size());
//                }
//
//                return collect.iterator().next();
//            }
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//        } finally {
//            AdSapientHibernateService.closeSession(session, tx);
//        }
//
//        return null;
//    }
//
//    public static synchronized Object loadObjectWithCriteria(
//            Class hibernateObjectClass, String criteriaName1,
//            Object criteriaValue1, String criteriaName2, Object criteriaValue2) {
//        Session session = null;
//
//        try {
//            session = AdSapientHibernateService.openSession();
//
//            Criteria crit = session.createCriteria(hibernateObjectClass);
//
//            if (criteriaValue1 == null) {
//                crit.add(Expression.isNull(criteriaName1));
//            } else {
//                crit.add(Expression.eq(criteriaName1, criteriaValue1));
//            }
//
//            if (criteriaValue2 == null) {
//                crit.add(Expression.isNull(criteriaName2));
//            } else {
//                crit.add(Expression.eq(criteriaName2, criteriaValue2));
//            }
//
//            List collect = crit.list();
//
//            if ((collect != null) && (!collect.isEmpty())) {
//                if (collect.size() != 1) {
//                    logger.warn(new StringBuffer(
//                            "Waring there are several instances of class ")
//                            .append(hibernateObjectClass.getName()).append(
//                            " with  ").append(criteriaName1)
//                            .append("=").append(criteriaValue1).append(" and ")
//                            .append(criteriaName2).append("=").append(
//                            criteriaValue2).append(", the count is ")
//                            .append(collect.size()).toString());
//                }
//
//                return collect.get(0);
//            }
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//        } finally {
//            AdSapientHibernateService.closeSession(session, null);
//        }
//
//        return null;
//    }
//
//    public boolean removeObject(Class objClass, Serializable objectId) {
//        boolean result = false;
//        Transaction tx = null;
//        Session session = null;
//
//        try {
//            session = AdSapientHibernateService.openSession();
//            tx = session.beginTransaction();
//
//            Object temp = session.get(objClass, objectId);
//
//            if (temp != null) {
//                session.delete(temp);
//                result = true;
//            } else {
//                logger.warn("Trying delete instace of  " + objClass
//                        + " but cant find this instance with id=" + objectId);
//            }
//
//            tx.commit();
//
//            sendUpdateEventToAdserver(temp.getClass(), objectId, null);
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//        } finally {
//            AdSapientHibernateService.closeSession(session, tx);
//        }
//
//        return result;
//    }
//
//    public boolean removeObject(Object obj) {
//        boolean result = false;
//        Transaction tx = null;
//        Session session = null;
//
//        if (obj == null) {
//            logger.warn("in removeObject atemp to del null instead object");
//
//            return false;
//        }
//
//        try {
//            session = AdSapientHibernateService.openSession();
//            tx = session.beginTransaction();
//            session.delete(obj);
//            tx.commit();
//
//            sendUpdateEventToAdserver(obj.getClass(),
//                    ((IMappable) obj).getId(), null);
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//        } finally {
//            AdSapientHibernateService.closeSession(session, tx);
//        }
//
//        return result;
//    }
//

//
//    public void updateObject(Object obj) {
//        Transaction tx = null;
//        Session session = null;
//
//        try {
//            session = AdSapientHibernateService.openSession();
//
//            tx = session.beginTransaction();
//            session.update(obj);
//            tx.commit();
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//        } finally {
//            AdSapientHibernateService.closeSession(session, tx);
//        }
//
//        try {
//            sendUpdateEventToAdserver(obj.getClass(),
//                    ((IMappable) obj).getId(), null);
//        } catch (Exception ex) {
//            logger.error(ex.getMessage());
//        }
//    }
//
//    public void saveOrUpdateObject(Object obj) {
//        Transaction tx = null;
//        Session session = null;
//
//        try {
//            session = AdSapientHibernateService.openSession();
//
//            tx = session.beginTransaction();
//            session.saveOrUpdate(obj);
//            tx.commit();
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//        } finally {
//            AdSapientHibernateService.closeSession(session, tx);
//        }
//
//        try {
//            sendUpdateEventToAdserver(obj.getClass(),
//                    ((IMappable) obj).getId(), null);
//        } catch (Exception ex) {
//            logger.error(ex.getMessage());
//        }
//    }
//
//    public static Collection viewAll(Class objClass) {
//        Collection collect = new ArrayList();
//        Transaction tx = null;
//        Session session = null;
//
//        try {
//            session = AdSapientHibernateService.openSession();
//
//            if (session == null) {
//                return collect;
//            }
//
//            tx = session.beginTransaction();
//            collect = session.createCriteria(objClass).list();
//            tx.commit();
//        } catch (Exception ex) {
//            logger.error(ex.getMessage());
//        } finally {
//            AdSapientHibernateService.closeSession(session, tx);
//        }
//
//        if (collect == null) {
//            logger.info("cant find any instance of " + objClass
//                    + " in view all");
//
//            return new ArrayList();
//        }
//
//        return collect;
//    }
//
//    public static Collection viewAllWithOrder(Class className,
//                                              String orderFieldName) {
//        Collection collect = new ArrayList();
//        Transaction tx = null;
//        Session session = null;
//
//        try {
//            session = AdSapientHibernateService.openSession();
//            tx = session.beginTransaction();
//            collect = session.createCriteria(className).addOrder(
//                    Order.asc(orderFieldName)).list();
//            tx.commit();
//        } catch (Exception ex) {
//            logger.error(ex.getMessage());
//        } finally {
//            AdSapientHibernateService.closeSession(session, tx);
//        }
//
//        return collect;
//    }
//
//    public static Collection viewWithCriteria(Class hibernateObjectClass,
//                                              String criteriaName, Object criteriaValue, String orderField) {
//        Transaction tx = null;
//        List collect = new ArrayList();
//        Session session = null;
//
//        try {
//            session = AdSapientHibernateService.openSession();
//            tx = session.beginTransaction();
//            collect = session.createCriteria(hibernateObjectClass).add(
//                    Expression.like(criteriaName, criteriaValue)).addOrder(
//                    Order.asc(orderField)).list();
//            tx.commit();
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//        } finally {
//            AdSapientHibernateService.closeSession(session, tx);
//        }
//
//        return collect;
//    }
//
//    public static Collection viewWithCriteria(Class hibernateObjectClass,
//                                              String criteriaName1, Object criteriaValue1, String criteriaName2,
//                                              Object criteriaValue2) {
//        Transaction tx = null;
//        Collection collect = null;
//        Session session = null;
//
//        try {
//            session = AdSapientHibernateService.openSession();
//            tx = session.beginTransaction();
//            collect = session.createCriteria(hibernateObjectClass).add(
//                    Expression.like(criteriaName1, criteriaValue1)).add(
//                    Expression.like(criteriaName2, criteriaValue2)).list();
//            tx.commit();
//        } catch (Exception ex) {
//            logger.error(ex.getMessage());
//        } finally {
//            AdSapientHibernateService.closeSession(session, tx);
//        }
//
//        return collect;
//    }
//
//    public static int executeQueryUpdate(String queryName) {
//        int res = 0;
//        Transaction tx = null;
//        Session session = null;
//
//        try {
//            session = HibernateEntityDao.openSession();
//            tx = session.beginTransaction();
//
//            Query query = session.getNamedQuery(queryName);
//            res = query.executeUpdate();
//            tx.commit();
//        } catch (Exception e) {
//            logger.error("", e);
//        } finally {
//            try {
//                AdSapientHibernateService.closeSession(session, tx);
//            } catch (Exception e) {
//            }
//
//            ;
//        }
//
//        return res;
//    }
//
//    public Collection executeHQLQuery(String queryName,
//                                             Map<String, Object> params) {
//        Session session = null;
//
//        try {
//            session = hibernateEntityDao.openSession();
//
//            Query query = session.getNamedQuery(queryName);
//
//            if ((params != null) && (params.size() > 0)) {
//                for (String key : params.keySet())
//                    query.setParameter(key, params.get(key));
//            }
//
//            return query.list();
//        } catch (Exception e) {
//            logger.error("Error executing " + queryName, e);
//
//            return new ArrayList();
//        }
//    }
//
//    public static final String UNIQUE_USER_ID = "uniqueUserId";
//
//    public Integer getUniqueUserId(HttpServletRequest request) {
//        Map paramMap = getCookie(request);
//
//        if (paramMap.containsKey(UNIQUE_USER_ID)) {
//            if (CookieManagementService.loadObject(UniqueUser.class, new Integer(
//                    (String) paramMap.get(UNIQUE_USER_ID))) != null) {
//                return new Integer((String) paramMap.get(UNIQUE_USER_ID));
//            }
//        }
//
//        Collection usersCollection = CookieManagementService.viewWithCriteria(
//                UniqueUser.class, "userIp", request.getRemoteAddr(), "userIp");
//
//        if (!usersCollection.isEmpty()) {
//            return ((UniqueUser) usersCollection.iterator().next()).getUserId();
//        }
//
//        UniqueUser user = new UniqueUser();
//        user.setUserIp(request.getRemoteAddr());
//
//        return new Integer(addObject(user));
//    }
//
//    public static boolean isUserUnique(HttpServletRequest request) {
//        Map paramMap = getCookie(request);
//
//        if (paramMap.containsKey(UNIQUE_USER_ID)) {
//            if (CookieManagementService.loadObject(UniqueUser.class, new Integer(
//                    (String) paramMap.get(UNIQUE_USER_ID))) != null) {
//                return false;
//            }
//        }
//
//        Collection usersCollection = CookieManagementService.viewWithCriteria(
//                UniqueUser.class, "userIp", request.getRemoteAddr(), "userIp");
//
//        if (!usersCollection.isEmpty()) {
//            return false;
//        }
//
//        return true;
//    }
//
//    public static boolean saveUniqueUser(HttpServletResponse response,
//                                         Integer userId) {
//        Map cookieParametres = new HashMap();
//        cookieParametres.put(UNIQUE_USER_ID, userId.toString());
//
//        addCookies(response, cookieParametres);
//
//        return true;
//    }
//
//    private static Map getCookie(HttpServletRequest request) {
//        Map cookiesParametersMap = new HashMap();
//
//        Cookie[] cookies = request.getCookies();
//
//        if ((cookies != null) && (cookies.length != 0)) {
//            for (int cookieNumber = 0; cookieNumber < cookies.length; cookieNumber++) {
//                Cookie cookie = cookies[cookieNumber];
//
//                cookiesParametersMap.put(cookie.getName(), cookie.getValue());
//            }
//        }
//
//        return cookiesParametersMap;
//    }
//
//    private static void addCookies(HttpServletResponse response, Map map) {
//        if ((map != null) && (!map.isEmpty())) {
//            Set parametersSet = map.entrySet();
//
//            Iterator setIterator = parametersSet.iterator();
//
//            while (setIterator.hasNext()) {
//                Map.Entry me = (Map.Entry) setIterator.next();
//
//                String parameterName = (String) me.getKey();
//                String parameterValue = (String) me.getValue();
//
//                Cookie cookie = new Cookie(parameterName, parameterValue);
//
//                cookie.setMaxAge(999999999);
//
//                response.addCookie(cookie);
//            }
//        }
//    }
//
//    public LinkHelperService getLinkHelperService() {
//        return linkHelperService;
//    }
//
//    public void setLinkHelperService(LinkHelperService linkHelperService) {
//        this.linkHelperService = linkHelperService;
//    }
//
//    public HibernateEntityDao getHibernateEntityDao() {
//        return hibernateEntityDao;
//    }
//
//    public void setHibernateEntityDao(HibernateEntityDao hibernateEntityDao) {
//        this.hibernateEntityDao = hibernateEntityDao;
//    }
}
