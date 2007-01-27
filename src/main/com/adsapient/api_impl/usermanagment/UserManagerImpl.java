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
package com.adsapient.api_impl.usermanagment;

import com.adsapient.api.AdsapientException;
import com.adsapient.api.User;

import com.adsapient.api_impl.advertizer.CampainManagerImpl;
import com.adsapient.api_impl.managment.DefaultCampainManager;
import com.adsapient.api_impl.managment.money.MoneyManager;
import com.adsapient.api_impl.message.EventManager;
import com.adsapient.api_impl.publisher.PublishManagerImpl;
import com.adsapient.api_impl.usermanagment.role.RoleController;

import com.adsapient.util.AdSapientHibernateService;
import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.admin.ConfigurationConstants;

import org.apache.log4j.Logger;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class UserManagerImpl {
	Logger logger = Logger.getLogger(UserManagerImpl.class);

	public void editUser(UserImpl user) {
		MyHibernateUtil.updateObject(user);

		updateReporterDB(user);
	}

	public void rejectUser(User user) {
	}

	public void removeUser(Integer userId) throws AdsapientException {
		UserImpl user = (UserImpl) MyHibernateUtil.loadObject(UserImpl.class,
				userId);

		PublishManagerImpl publishManag = new PublishManagerImpl();
		publishManag.removePublisher(user);

		new CampainManagerImpl().removeAllUsersCampain(user);

		MoneyManager.removeUserFinance(user);

		MyHibernateUtil.removeObject(user);
	}

	public boolean signInUser(UserImpl user, HttpServletRequest request) {
		Integer userId = null;

		Collection coll = MyHibernateUtil.viewWithCriteria(UserImpl.class,
				"login", user.getLogin(), "id");

		if (!coll.isEmpty()) {
			return false;
		}

		userId = new Integer(MyHibernateUtil.addObject(user));

		updateReporterDB(user);

		MoneyManager.createUserFinancial(user);

		if (RoleController.ADVERTISERPUBLISHER.equalsIgnoreCase(user.getRole())) {
			DefaultCampainManager.addNewDefaultCampain(user);
		}

		AccountSetting settings = new AccountSetting();
		settings.setUserId(userId);
		MyHibernateUtil.addObject(settings);

		logger.info("user was registered . His id is " + userId);
		EventManager.userRegisterevent(userId, request);

		return true;
	}

	public UserImpl viewUser(Integer userId) {
		UserImpl user = (UserImpl) MyHibernateUtil.loadObject(UserImpl.class,
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
					ConfigurationConstants.DEFAULT_VERIFYING_STATE_ID));

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
									RoleController.ADMIN)))
					.add(
							Expression
									.like(
											"stateId",
											new Integer(
													ConfigurationConstants.DEFAULT_VERIFYING_STATE_ID)))
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
}
