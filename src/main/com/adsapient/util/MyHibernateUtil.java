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
package com.adsapient.util;

import com.adsapient.api_impl.statistic.engine.StatisticFactoryImpl;

import com.adsapient.gui.ContextAwareGuiBean;

import com.adsapient.shared.api.entity.IMappable;
import com.adsapient.shared.service.LinkHelperService;

import com.adsapient.util.admin.AdsapientConstants;

import org.apache.log4j.Logger;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MyHibernateUtil {
	static Logger logger = Logger.getLogger(MyHibernateUtil.class);

	private static final StringBuffer sbUrlUpdate1 = new StringBuffer("sv?")
			.append(AdsapientConstants.EVENTID_REQUEST_PARAM_NAME).append("=")
			.append(AdsapientConstants.UPDATE_ENTITY_ADSERVEREVENT_TYPE)
			.append("&")
			.append(AdsapientConstants.CLASSNAME_REQUEST_PARAM_NAME)
			.append("=");

	private static final StringBuffer sbUrlUpdate2 = new StringBuffer("&")
			.append(AdsapientConstants.ENTITYID_REQUEST_PARAM_NAME).append("=");

	public static int getCount(String query) {
		Transaction tx = null;
		Session session = null;

		try {
			session = AdSapientHibernateService.openSession();
			tx = session.beginTransaction();

			Integer count = 0;
			Object obj = session.createQuery(query).uniqueResult();

			if (obj instanceof Integer) {
				count = (Integer) obj;
			} else if (obj instanceof Long) {
				Long l = (Long) obj;
				count = l.intValue();
			}

			tx.commit();

			if (count == null) {
				return 0;
			} else {
				return count.intValue();
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			AdSapientHibernateService.closeSession(session, tx);
		}

		return 0;
	}

	public static List getObject(String query) {
		Transaction tx = null;
		Session session = null;

		try {
			session = AdSapientHibernateService.openSession();

			if (session == null) {
				return null;
			}

			tx = session.beginTransaction();

			List result = session.createQuery(query).list();
			tx.commit();

			return result;
		} catch (Exception e) {
			logger.error(e.getMessage());

			return new ArrayList();
		} finally {
			AdSapientHibernateService.closeSession(session, tx);
		}
	}

	public static Collection getSQLCollection(String query) {
		Transaction tx = null;
		Session session = null;

		try {
			session = AdSapientHibernateService.openSession();
			tx = session.beginTransaction();

			Collection result = session.createSQLQuery(query).addScalar(
					StatisticFactoryImpl.RESULT, Hibernate.STRING).list();
			tx.commit();

			if (result == null) {
				return new ArrayList();
			} else {
				return result;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			AdSapientHibernateService.closeSession(session, tx);
		}

		return new ArrayList();
	}

	public static int getSQLCount(String query) {
		Transaction tx = null;
		Session session = null;

		try {
			session = AdSapientHibernateService.openSession();
			tx = session.beginTransaction();

			Integer count = (Integer) session.createSQLQuery(query).addScalar(
					StatisticFactoryImpl.RESULT, Hibernate.INTEGER)
					.uniqueResult();
			tx.commit();

			if (count == null) {
				return 0;
			} else {
				return count.intValue();
			}
		} catch (Exception e) {
			logger.error(e.getMessage());

			return 0;
		} finally {
			AdSapientHibernateService.closeSession(session, tx);
		}
	}

	public static Integer addObject(Object hibernateObject) {
		Transaction tx = null;
		Integer resultId = null;
		Session session = null;

		try {
			session = AdSapientHibernateService.openSession();
			tx = session.beginTransaction();

			Object tempObject = session.save(hibernateObject);

			resultId = (Integer) tempObject;

			tx.commit();

			sendUpdateEventToAdserver(hibernateObject.getClass(), resultId,
					null);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		} finally {
			AdSapientHibernateService.closeSession(session, tx);
		}

		return resultId;
	}

	public static void sendUpdateEventToAdserver(Class entityClass,
			Serializable entityId, String params) {
		try {
			LinkHelperService linkHelperService = (LinkHelperService) ContextAwareGuiBean.ctx
					.getBean("linkHelperService");

			URL u = new URL(new StringBuffer(linkHelperService
					.getPathToAdServer()).append(sbUrlUpdate1).append(
					entityClass.getName()).append(sbUrlUpdate2)
					.append(entityId).append((params == null) ? "" : params)
					.toString());

			BufferedReader in = new BufferedReader(new InputStreamReader(u
					.openStream()));

			String inputLine;
			StringBuffer sb = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				sb.append(inputLine);
				sb.append("\n");
			}

			in.close();
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
	}

	public static void executeNativeSQL(String query) {
		Transaction tx = null;
		Session session = null;

		try {
			session = AdSapientHibernateService.openSession();
			tx = session.beginTransaction();

			session.createSQLQuery(query).list();
			tx.commit();
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			AdSapientHibernateService.closeSession(session, tx);
		}
	}

	public static Object loadObject(Class objClass, String ObjectId) {
		Transaction tx = null;
		Session session = null;

		try {
			session = AdSapientHibernateService.openSession();
			tx = session.beginTransaction();

			Object result = session.get(objClass, ObjectId);
			tx.commit();

			if (result != null) {
				return result;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			AdSapientHibernateService.closeSession(session, tx);
		}

		return null;
	}

	public static Object loadObject(Class objClass, Integer ObjectId) {
		Transaction tx = null;
		Session session = null;

		try {
			session = AdSapientHibernateService.openSession();
			tx = session.beginTransaction();

			Object result = session.load(objClass, ObjectId);
			tx.commit();

			if (result != null) {
				return result;
			} else {
				logger.info("cant load object " + objClass + "  with id = "
						+ ObjectId);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			AdSapientHibernateService.closeSession(session, tx);
		}

		return null;
	}

	public static synchronized Object loadObjectWithCriteria(
			Class hibernateObjectClass, String criteriaName,
			Object criteriaValue) {
		Transaction tx = null;
		List collect = new ArrayList();
		Session session = null;

		try {
			session = AdSapientHibernateService.openSession();

			collect = session.createCriteria(hibernateObjectClass).add(
					Expression.like(criteriaName, criteriaValue)).list();
			session.flush();

			if ((collect != null) && (!collect.isEmpty())) {
				if (collect.size() != 1) {
					logger.warn("Waring there are several instances of class "
							+ hibernateObjectClass + " with field "
							+ criteriaName + " and value " + criteriaValue
							+ ", the count is " + collect.size());
				}

				return collect.iterator().next();
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			AdSapientHibernateService.closeSession(session, tx);
		}

		return null;
	}

	public static synchronized Object loadObjectWithCriteria(
			Class hibernateObjectClass, String criteriaName1,
			Object criteriaValue1, String criteriaName2, Object criteriaValue2) {
		Session session = null;

		try {
			session = AdSapientHibernateService.openSession();

			Criteria crit = session.createCriteria(hibernateObjectClass);

			if (criteriaValue1 == null) {
				crit.add(Expression.isNull(criteriaName1));
			} else {
				crit.add(Expression.eq(criteriaName1, criteriaValue1));
			}

			if (criteriaValue2 == null) {
				crit.add(Expression.isNull(criteriaName2));
			} else {
				crit.add(Expression.eq(criteriaName2, criteriaValue2));
			}

			List collect = crit.list();

			if ((collect != null) && (!collect.isEmpty())) {
				if (collect.size() != 1) {
					logger.warn(new StringBuffer(
							"Waring there are several instances of class ")
							.append(hibernateObjectClass.getName()).append(
									" with  ").append(criteriaName1)
							.append("=").append(criteriaValue1).append(" and ")
							.append(criteriaName2).append("=").append(
									criteriaValue2).append(", the count is ")
							.append(collect.size()).toString());
				}

				return collect.get(0);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return null;
	}

	public static boolean removeObject(Class objClass, Serializable objectId) {
		boolean result = false;
		Transaction tx = null;
		Session session = null;

		try {
			session = AdSapientHibernateService.openSession();
			tx = session.beginTransaction();

			Object temp = session.get(objClass, objectId);

			if (temp != null) {
				session.delete(temp);
				result = true;
			} else {
				logger.warn("Trying delete instace of  " + objClass
						+ " but cant find this instance with id=" + objectId);
			}

			tx.commit();

			sendUpdateEventToAdserver(temp.getClass(), objectId, null);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			AdSapientHibernateService.closeSession(session, tx);
		}

		return result;
	}

	public static boolean removeObject(Object obj) {
		boolean result = false;
		Transaction tx = null;
		Session session = null;

		if (obj == null) {
			logger.warn("in removeObject atemp to del null instead object");

			return false;
		}

		try {
			session = AdSapientHibernateService.openSession();
			tx = session.beginTransaction();
			session.delete(obj);
			tx.commit();

			sendUpdateEventToAdserver(obj.getClass(),
					((IMappable) obj).getId(), null);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			AdSapientHibernateService.closeSession(session, tx);
		}

		return result;
	}

	public static void removeWithCriteria(Class class4Remove,
			String criteriaName, Object criteriaValue) {
		Transaction tx = null;
		List collect = new ArrayList();
		Session session = null;

		try {
			session = AdSapientHibernateService.openSession();
			tx = session.beginTransaction();
			collect = session.createCriteria(class4Remove).add(
					Expression.like(criteriaName, criteriaValue)).list();

			if ((collect != null) && (!collect.isEmpty())) {
				Iterator entityIterator = collect.iterator();

				while (entityIterator.hasNext()) {
					removeObject(entityIterator.next());
				}

				tx.commit();
			} else {
				logger.info("Error cant find any entity with given Id  "
						+ criteriaName + " =" + criteriaValue + "of class "
						+ class4Remove);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			AdSapientHibernateService.closeSession(session, tx);
		}
	}

	public static void updateObject(Object obj) {
		Transaction tx = null;
		Session session = null;

		try {
			session = AdSapientHibernateService.openSession();

			tx = session.beginTransaction();
			session.update(obj);
			tx.commit();
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			AdSapientHibernateService.closeSession(session, tx);
		}

		try {
			sendUpdateEventToAdserver(obj.getClass(),
					((IMappable) obj).getId(), null);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
	}

	public static void saveOrUpdateObject(Object obj) {
		Transaction tx = null;
		Session session = null;

		try {
			session = AdSapientHibernateService.openSession();

			tx = session.beginTransaction();
			session.saveOrUpdate(obj);
			tx.commit();
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			AdSapientHibernateService.closeSession(session, tx);
		}

		try {
			sendUpdateEventToAdserver(obj.getClass(),
					((IMappable) obj).getId(), null);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
	}

	public static Collection viewAll(Class objClass) {
		Collection collect = new ArrayList();
		Transaction tx = null;
		Session session = null;

		try {
			session = AdSapientHibernateService.openSession();

			if (session == null) {
				return collect;
			}

			tx = session.beginTransaction();
			collect = session.createCriteria(objClass).list();
			tx.commit();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		} finally {
			AdSapientHibernateService.closeSession(session, tx);
		}

		if (collect == null) {
			logger.info("cant find any instance of " + objClass
					+ " in view all");

			return new ArrayList();
		}

		return collect;
	}

	public static Collection viewAllWithOrder(Class className,
			String orderFieldName) {
		Collection collect = new ArrayList();
		Transaction tx = null;
		Session session = null;

		try {
			session = AdSapientHibernateService.openSession();
			tx = session.beginTransaction();
			collect = session.createCriteria(className).addOrder(
					Order.asc(orderFieldName)).list();
			tx.commit();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		} finally {
			AdSapientHibernateService.closeSession(session, tx);
		}

		return collect;
	}

	public static Collection viewWithCriteria(Class hibernateObjectClass,
			String criteriaName, Object criteriaValue, String orderField) {
		Transaction tx = null;
		List collect = new ArrayList();
		Session session = null;

		try {
			session = AdSapientHibernateService.openSession();
			tx = session.beginTransaction();
			collect = session.createCriteria(hibernateObjectClass).add(
					Expression.like(criteriaName, criteriaValue)).addOrder(
					Order.asc(orderField)).list();
			tx.commit();
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			AdSapientHibernateService.closeSession(session, tx);
		}

		return collect;
	}

	public static Collection viewWithCriteria(Class hibernateObjectClass,
			String criteriaName1, Object criteriaValue1, String criteriaName2,
			Object criteriaValue2) {
		Transaction tx = null;
		Collection collect = null;
		Session session = null;

		try {
			session = AdSapientHibernateService.openSession();
			tx = session.beginTransaction();
			collect = session.createCriteria(hibernateObjectClass).add(
					Expression.like(criteriaName1, criteriaValue1)).add(
					Expression.like(criteriaName2, criteriaValue2)).list();
			tx.commit();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		} finally {
			AdSapientHibernateService.closeSession(session, tx);
		}

		return collect;
	}

	public static int executeQueryUpdate(String queryName) {
		int res = 0;
		Transaction tx = null;
		Session session = null;

		try {
			session = HibernateUtil.openSession();
			tx = session.beginTransaction();

			Query query = session.getNamedQuery(queryName);
			res = query.executeUpdate();
			tx.commit();
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			try {
				AdSapientHibernateService.closeSession(session, tx);
			} catch (Exception e) {
			}

			;
		}

		return res;
	}

	public static Collection executeHQLQuery(String queryName,
			Map<String, Object> params) {
		Session session = null;

		try {
			session = HibernateUtil.openSession();

			Query query = session.getNamedQuery(queryName);

			if ((params != null) && (params.size() > 0)) {
				for (String key : params.keySet())
					query.setParameter(key, params.get(key));
			}

			return query.list();
		} catch (Exception e) {
			logger.error("Error executing " + queryName, e);

			return new ArrayList();
		}
	}
}
