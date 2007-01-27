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
package com.adsapient.shared.dao;

import com.adsapient.shared.api.entity.IMappable;

import com.adsapient.util.MyHibernateUtil;

import org.apache.log4j.Logger;

import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class HibernateEntityDao extends HibernateDaoSupport {
	static Logger logger = Logger.getLogger(HibernateEntityDao.class);

	public int getCount(final String query) {
		Object obj = (List) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						session.setCacheMode(CacheMode.IGNORE);

						return session.createQuery(query).uniqueResult();
					}
				});

		Integer count = null;

		if (obj instanceof Integer) {
			count = (Integer) obj;
		} else if (obj instanceof Long) {
			Long l = (Long) obj;
			count = l.intValue();
		}

		if (count == null) {
			return 0;
		} else {
			return count.intValue();
		}
	}

	public List getObject(final String query) {
		List result = (List) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						session.setCacheMode(CacheMode.IGNORE);

						return session.createQuery(query).list();
					}
				});

		return result;
	}

	public Serializable save(Object hibernateObject) {
		try {
			Serializable generatedId = getHibernateTemplate().save(
					hibernateObject);
			getHibernateTemplate().flush();

			return generatedId;
		} catch (Exception ex) {
			logger.error("cannot save object", ex);

			return null;
		}
	}

	synchronized public void saveOrUpdate(final IMappable hibernateObject) {
		try {
			getHibernateTemplate().saveOrUpdate(hibernateObject);

			getHibernateTemplate().flush();
			getHibernateTemplate().clear();
		} catch (Exception ex) {
			getHibernateTemplate().save(hibernateObject);
			getHibernateTemplate().flush();
			getHibernateTemplate().clear();
		}
	}

	public void executeNativeSQL(final String query) {
		logger.info("executing query : " + query);
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				return session.createSQLQuery(query).list();
			}
		});
		getHibernateTemplate().flush();
	}

	public Object loadObject(final Class objClass, final String ObjectId) {
		return getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				session.setCacheMode(CacheMode.IGNORE);

				return session.load(objClass, ObjectId);
			}
		});
	}

	public Object loadObject(final Class objClass, final Integer ObjectId) {
		return getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				session.setCacheMode(CacheMode.IGNORE);

				return session.get(objClass, ObjectId);
			}
		});
	}

	public synchronized Object loadObjectWithCriteria(
			final Class hibernateObjectClass, final String criteriaName,
			final Object criteriaValue) {
		List collect = (List) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						session.setCacheMode(CacheMode.IGNORE);

						return session.createCriteria(hibernateObjectClass)
								.add(
										Expression.like(criteriaName,
												criteriaValue)).list();
					}
				});

		getHibernateTemplate().flush();
		getHibernateTemplate().clear();

		if ((collect != null) && (!collect.isEmpty())) {
			if (collect.size() != 1) {
				logger.warn(new StringBuffer(
						"Waring thearis few instance of class ").append(
						hibernateObjectClass).append("with field ").append(
						criteriaName).append("and value ")
						.append(criteriaValue).append(
								"the count of instance is ").append(
								collect.size()).toString());
			}

			return collect.iterator().next();
		} else {
			return null;
		}
	}

	public boolean removeObject(Class objClass, Serializable objectId) {
		boolean result = false;
		Object temp = getHibernateTemplate().get(objClass, objectId);

		if (temp != null) {
			getHibernateTemplate().getSessionFactory().getCurrentSession()
					.delete(temp);
			result = true;
			getHibernateTemplate().flush();
			getHibernateTemplate().clear();
		} else {
			logger.warn(new StringBuffer("Trying delete instace of  ").append(
					objClass).append(" but cant find this instance with id=")
					.append(objectId).toString());
		}

		return result;
	}

	public boolean removeObject(Object obj) {
		boolean result = false;
		getHibernateTemplate().delete(obj);
		getHibernateTemplate().flush();
		getHibernateTemplate().clear();

		return result;
	}

	public void removeWithCriteria(final Class class4Remove,
			final String criteriaName, final Object criteriaValue) {
		List collect = (List) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						return session.createCriteria(class4Remove).add(
								Expression.like(criteriaName, criteriaValue))
								.list();
					}
				});

		if ((collect != null) && (!collect.isEmpty())) {
			for (Iterator entityIterator = collect.iterator(); entityIterator
					.hasNext(); removeObject(entityIterator.next())) {
				;
			}

			getHibernateTemplate().flush();
			getHibernateTemplate().clear();
		} else {
			logger.info(new StringBuffer(
					"Error cant find any entity with given Id  ").append(
					criteriaName).append(" =").append(criteriaValue).append(
					"of class ").append(class4Remove).toString());
		}
	}

	public void updateObject(final Object obj) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				session.setCacheMode(CacheMode.IGNORE);
				session.update(obj);
				MyHibernateUtil.sendUpdateEventToAdserver(obj.getClass(),
						((IMappable) obj).getId(), null);

				return obj;
			}
		});
	}

	public Collection viewAll(final Class objClass) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				session.setCacheMode(CacheMode.IGNORE);

				return session.createCriteria(objClass).list();
			}
		});
	}

	public Collection viewAllWithOrder(final Class className,
			final String orderFieldName) {
		return (Collection) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						session.setCacheMode(CacheMode.IGNORE);

						return session.createCriteria(className).addOrder(
								Order.asc(orderFieldName)).list();
					}
				});
	}

	public Collection viewWithCriteria(final Class hibernateObjectClass,
			final String criteriaName, final Object criteriaValue,
			final String orderField) {
		Collection coll = (Collection) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						session.setCacheMode(CacheMode.IGNORE);

						return session.createCriteria(hibernateObjectClass)
								.add(
										Expression.like(criteriaName,
												criteriaValue)).addOrder(
										Order.asc(orderField)).list();
					}
				});

		if (coll == null) {
			coll = new ArrayList();
		}

		return coll;
	}

	public Collection viewWithCriteriaNot(final Class hibernateObjectClass,
			final String criteriaName, final Object criteriaValue) {
		Collection coll = (Collection) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						session.setCacheMode(CacheMode.IGNORE);

						return session
								.createCriteria(hibernateObjectClass)
								.add(Expression.ne(criteriaName, criteriaValue))
								.list();
					}
				});

		if (coll == null) {
			coll = new ArrayList();
		}

		return coll;
	}

	public Collection viewWithCriteria(final Class hibernateObjectClass,
			final String criteriaName1, final Object criteriaValue1,
			final String criteriaName2, final Object criteriaValue2) {
		return (Collection) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						session.setCacheMode(CacheMode.IGNORE);

						return session.createCriteria(hibernateObjectClass)
								.add(
										Expression.like(criteriaName1,
												criteriaValue1)).add(
										Expression.like(criteriaName2,
												criteriaValue2)).list();
					}
				});
	}

	public Collection viewWithCriteria(final Class hibernateObjectClass,
			final Object[] criteriaParams) {
		return (Collection) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						session.setCacheMode(CacheMode.IGNORE);

						Criteria critiria = session
								.createCriteria(hibernateObjectClass);

						for (int i = 0; i < criteriaParams.length; i += 2) {
							critiria.add(Expression.eq(
									(String) criteriaParams[i],
									criteriaParams[i + 1]));
						}

						return critiria.list();
					}
				});
	}

	public Collection viewWithNullCriteria(final Class hibernateObjectClass,
			final String criteriaName, final boolean isNull) {
		return (Collection) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						session.setCacheMode(CacheMode.IGNORE);

						Criteria critiria = session
								.createCriteria(hibernateObjectClass);

						if (isNull) {
							critiria.add(Expression.isNull(criteriaName));
						} else {
							critiria.add(Expression.isNotNull(criteriaName));
						}

						return critiria.list();
					}
				});
	}

	public List getBannerList(final Integer status) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				session.setCacheMode(CacheMode.IGNORE);

				Query query = session.getNamedQuery("loadBanners").setInteger(
						"STATUS", status);

				return query.list();
			}
		});
	}

	public Integer executeQueryUpdate(final String queryName) {
		return (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						session.setCacheMode(CacheMode.IGNORE);

						Transaction tx = session.beginTransaction();
						Query query = session.getNamedQuery(queryName);
						Integer res = query.executeUpdate();
						tx.commit();

						return res;
					}
				});
	}

	public Integer executeQueryInsert(final String queryName) {
		return (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						session.setCacheMode(CacheMode.IGNORE);

						Transaction tx = session.beginTransaction();
						Query query = session.createSQLQuery(queryName);
						Integer res = query.executeUpdate();
						tx.commit();

						return res;
					}
				});
	}
}
