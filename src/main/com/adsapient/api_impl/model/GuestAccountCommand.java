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
package com.adsapient.api_impl.model;

import com.adsapient.api.AdsapientCommand;

import com.adsapient.api_impl.usermanagment.UserImpl;
import com.adsapient.api_impl.usermanagment.role.RoleController;

import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.admin.AdsapientConstants;
import com.adsapient.util.admin.ConfigurationConstants;

import com.adsapient.gui.forms.GuestAccountActionForm;

import org.apache.log4j.Logger;

import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;

public class GuestAccountCommand extends AdsapientCommand {
	static Logger logger = Logger.getLogger(GuestAccountActionForm.class);

	public boolean resultAdd(ActionForm actionForm, HttpServletRequest request) {
		GuestAccountActionForm form = (GuestAccountActionForm) actionForm;
		UserImpl realUser = (UserImpl) request.getSession().getAttribute(
				AdsapientConstants.USER);
		form.setAction("list");

		UserImpl user = form.getUser();
		user.setRealUserId(realUser.getId());

		user.setStateId(ConfigurationConstants.DEFAULT_VERIFYING_STATE_ID);

		String userId = String.valueOf(MyHibernateUtil.addObject(user));
		logger.info("Create new guest account with guest user id=" + userId);

		return true;
	}

	public boolean resultDelete(ActionForm actionForm,
			HttpServletRequest request) {
		GuestAccountActionForm form = (GuestAccountActionForm) actionForm;
		form.setAction("list");

		UserImpl user = (UserImpl) MyHibernateUtil.loadObject(UserImpl.class,
				form.getId());
		MyHibernateUtil.removeObject(user);
		logger.info("Delete guest account=" + user.getId());

		return true;
	}

	public boolean resultEdit(ActionForm actionForm, HttpServletRequest request) {
		GuestAccountActionForm form = (GuestAccountActionForm) actionForm;
		form.setAction("list");

		UserImpl user = (UserImpl) MyHibernateUtil.loadObject(UserImpl.class,
				form.getId());
		form.getUser(user);
		MyHibernateUtil.updateObject(user);

		return true;
	}

	public boolean resultInit(ActionForm actionForm, HttpServletRequest request) {
		GuestAccountActionForm form = (GuestAccountActionForm) actionForm;
		form.setAction("add");

		UserImpl user = (UserImpl) request.getSession().getAttribute(
				AdsapientConstants.USER);
		form.setUserRole(RoleController.GUEST);

		return true;
	}

	public boolean resultView(ActionForm actionForm, HttpServletRequest request) {
		GuestAccountActionForm form = (GuestAccountActionForm) actionForm;
		form.setAction("edit");

		UserImpl user = (UserImpl) MyHibernateUtil.loadObject(UserImpl.class,
				form.getId());
		form.initForm(user);

		return true;
	}
}
