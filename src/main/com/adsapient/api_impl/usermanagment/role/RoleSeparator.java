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
package com.adsapient.api_impl.usermanagment.role;

import com.adsapient.api_impl.usermanagment.UserImpl;

import com.adsapient.util.Msg;
import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.admin.AdsapientConstants;

import org.apache.struts.util.LabelValueBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

public class RoleSeparator {
	public static Collection getRoleLabelsCollection(HttpServletRequest request) {
		Collection userRolesCollection = new ArrayList();

		Collection registeredRoles = MyHibernateUtil.viewAll(RoleImpl.class);

		UserImpl user = (UserImpl) request.getSession().getAttribute(
				AdsapientConstants.USER);

		Iterator rolesIterator = registeredRoles.iterator();

		while (rolesIterator.hasNext()) {
			RoleImpl role = (RoleImpl) rolesIterator.next();

			if (!role.isEnable()) {
				continue;
			}

			if (RoleController.ADMIN.equalsIgnoreCase(role.getRoleName())) {
				if (RoleController.ADMIN.equalsIgnoreCase(user.getRole())) {
					userRolesCollection.add(new LabelValueBean(Msg.fetch(role
							.getRoleName(), request), role.getRoleName()));
				}
			} else {
				userRolesCollection.add(new LabelValueBean(Msg.fetch(role
						.getRoleName(), request), role.getRoleName()));
			}
		}

		return userRolesCollection;
	}

	public static Collection getNotAutoriseRoleLabelsCollection(
			HttpServletRequest request) {
		Collection userRolesCollection = new ArrayList();

		Collection registeredRoles = MyHibernateUtil.viewAll(RoleImpl.class);

		UserImpl user = (UserImpl) request.getSession().getAttribute("user");

		Iterator rolesIterator = registeredRoles.iterator();

		while (rolesIterator.hasNext()) {
			RoleImpl role = (RoleImpl) rolesIterator.next();

			if (!role.isEnable()) {
				continue;
			}

			if (!RoleController.ADMIN.equalsIgnoreCase(role.getRoleName())
					|| !RoleController.HOSTEDSERVICE.equalsIgnoreCase(role
							.getRoleName())) {
				userRolesCollection.add(new LabelValueBean(Msg.fetch(role
						.getRoleName(), request), role.getRoleName()));
			}
		}

		return userRolesCollection;
	}
}
