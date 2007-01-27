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
package com.adsapient.util.security;

import com.adsapient.api_impl.usermanagment.UserImpl;

import com.adsapient.util.admin.AdsapientConstants;

import org.apache.log4j.Logger;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpServletRequest;

public class AdsapientSecurityManager {
	private static boolean access = true;

	private static Logger logger = Logger
			.getLogger(AdsapientSecurityManager.class);

	public static synchronized boolean isAccess() {
		return access;
	}

	public static boolean isUserAdmin(HttpServletRequest request) {
		UserImpl user = null;
		user = (UserImpl) request.getSession().getAttribute(
				AdsapientConstants.USER);

		if (user == null) {
			return false;
		} else if ("admin".equalsIgnoreCase(user.getLogin())) {
			return true;
		}

		return false;
	}

	public static boolean isUserLogIn(HttpServletRequest request) {
		UserImpl user = null;
		user = (UserImpl) request.getSession().getAttribute(
				AdsapientConstants.USER);

		if (user == null) {
			return false;
		} else {
			return true;
		}
	}

	public static synchronized void allowAccess() {
		access = true;

		logger.info("Grant acess to login as admin at:"
				+ Calendar.getInstance().toString());

		int numberOfMillisecondsInTheFuture = 10000;
		Date timeToRun = new Date(System.currentTimeMillis()
				+ numberOfMillisecondsInTheFuture);
		Timer timer = new Timer();

		timer.schedule(new TimerTask() {
			public void run() {
				clotheAccess();
			}
		}, timeToRun);
	}

	public static synchronized void successAccess() {
		logger.info("Success login as admin");
		access = false;
	}

	private static synchronized void clotheAccess() {
		if (access == true) {
			logger.warn("Clothe access for login with admin rights by timer!");
			access = false;
		} else {
			logger.info("Check by timer completer user success login as admin");
		}
	}
}
