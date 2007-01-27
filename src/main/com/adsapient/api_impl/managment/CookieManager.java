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
package com.adsapient.api_impl.managment;

import com.adsapient.api_impl.statistic.UniqueUser;

import com.adsapient.util.MyHibernateUtil;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieManager {
	static Logger logger = Logger.getLogger(CookieManager.class);

	public static final String UNIQUE_USER_ID = "uniqueUserId";

	public static Integer getUniqueUserId(HttpServletRequest request) {
		Map paramMap = getCookie(request);

		if (paramMap.containsKey(UNIQUE_USER_ID)) {
			if (MyHibernateUtil.loadObject(UniqueUser.class, new Integer(
					(String) paramMap.get(UNIQUE_USER_ID))) != null) {
				return new Integer((String) paramMap.get(UNIQUE_USER_ID));
			}
		}

		Collection usersCollection = MyHibernateUtil.viewWithCriteria(
				UniqueUser.class, "userIp", request.getRemoteAddr(), "userIp");

		if (!usersCollection.isEmpty()) {
			return ((UniqueUser) usersCollection.iterator().next()).getUserId();
		}

		UniqueUser user = new UniqueUser();
		user.setUserIp(request.getRemoteAddr());

		return new Integer(MyHibernateUtil.addObject(user));
	}

	public static boolean isUserUnique(HttpServletRequest request) {
		Map paramMap = getCookie(request);

		if (paramMap.containsKey(UNIQUE_USER_ID)) {
			if (MyHibernateUtil.loadObject(UniqueUser.class, new Integer(
					(String) paramMap.get(UNIQUE_USER_ID))) != null) {
				return false;
			}
		}

		Collection usersCollection = MyHibernateUtil.viewWithCriteria(
				UniqueUser.class, "userIp", request.getRemoteAddr(), "userIp");

		if (!usersCollection.isEmpty()) {
			return false;
		}

		return true;
	}

	public static boolean saveUniqueUser(HttpServletResponse response,
			Integer userId) {
		Map cookieParametres = new HashMap();
		cookieParametres.put(UNIQUE_USER_ID, userId.toString());

		addCookies(response, cookieParametres);

		return true;
	}

	private static Map getCookie(HttpServletRequest request) {
		Map cookiesParametersMap = new HashMap();

		Cookie[] cookies = request.getCookies();

		if ((cookies != null) && (cookies.length != 0)) {
			for (int cookieNumber = 0; cookieNumber < cookies.length; cookieNumber++) {
				Cookie cookie = cookies[cookieNumber];

				cookiesParametersMap.put(cookie.getName(), cookie.getValue());
			}
		}

		return cookiesParametersMap;
	}

	private static void addCookies(HttpServletResponse response, Map map) {
		if ((map != null) && (!map.isEmpty())) {
			Set parametersSet = map.entrySet();

			Iterator setIterator = parametersSet.iterator();

			while (setIterator.hasNext()) {
				Map.Entry me = (Map.Entry) setIterator.next();

				String parameterName = (String) me.getKey();
				String parameterValue = (String) me.getValue();

				Cookie cookie = new Cookie(parameterName, parameterValue);

				cookie.setMaxAge(999999999);

				response.addCookie(cookie);
			}
		}
	}
}
