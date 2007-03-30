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
package com.adsapient.shared.mappable;

import com.adsapient.api.AdsapientCommand;

import com.adsapient.shared.mappable.BehaviorPattern;
import com.adsapient.shared.mappable.UserImpl;
import com.adsapient.shared.service.CookieManagementService;
import com.adsapient.shared.service.UploadService;
import com.adsapient.shared.AdsapientConstants;
import com.adsapient.shared.dao.HibernateEntityDao;

import com.adsapient.gui.forms.BehaviorActionForm;
import com.adsapient.gui.ContextAwareGuiBean;

import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;

public class BehaviorCommand extends AdsapientCommand {
	public boolean addCheck(ActionForm actionForm, HttpServletRequest request) {
		BehaviorActionForm form = (BehaviorActionForm) actionForm;
		form.setAction("view");

		return super.addCheck(actionForm, request);
	}

	public boolean deleteCheck(ActionForm actionForm, HttpServletRequest request) {
		BehaviorActionForm form = (BehaviorActionForm) actionForm;
		form.setAction("view");

		return super.deleteCheck(actionForm, request);
	}

	public boolean editCheck(ActionForm actionForm, HttpServletRequest request) {
		BehaviorActionForm form = (BehaviorActionForm) actionForm;
		form.setAction("view");

		return super.editCheck(actionForm, request);
	}

	public boolean resultAdd(ActionForm actionForm, HttpServletRequest request) {
        HibernateEntityDao hibernateEntityDao =  (HibernateEntityDao) ContextAwareGuiBean.getContext().getBean("hibernateEntityDao");
        UserImpl user = (UserImpl) request.getSession().getAttribute(
				AdsapientConstants.USER);

		int userId = user.getId();

		if (AdsapientConstants.ADMIN.equalsIgnoreCase(user.getRole())) {
			try {
				userId = Integer.parseInt(request.getParameter("userId"));
			} catch (Exception e) {
				try {
					userId = (Integer) request.getAttribute("userId");
				} catch (Exception e1) {
				}
			}
		}

		BehaviorActionForm form = (BehaviorActionForm) actionForm;
		BehaviorPattern pattern = new BehaviorPattern();
		pattern.setUserId(userId);
		form.update(pattern);

		hibernateEntityDao.save(pattern);

		return true;
	}

	public boolean resultDelete(ActionForm actionForm,
			HttpServletRequest request) {
        HibernateEntityDao hibernateEntityDao =  (HibernateEntityDao) ContextAwareGuiBean.getContext().getBean("hibernateEntityDao");
        BehaviorActionForm form = (BehaviorActionForm) actionForm;
		hibernateEntityDao.removeObject(BehaviorPattern.class, new Integer(form
				.getId()));

		return true;
	}

	public boolean resultEdit(ActionForm actionForm, HttpServletRequest request) {
        HibernateEntityDao hibernateEntityDao =  (HibernateEntityDao) ContextAwareGuiBean.getContext().getBean("hibernateEntityDao");
        BehaviorActionForm form = (BehaviorActionForm) actionForm;

		BehaviorPattern pattern = (BehaviorPattern) hibernateEntityDao.loadObject(
				BehaviorPattern.class, new Integer(form.getId()));
		form.update(pattern);
		hibernateEntityDao.updateObject(pattern);

		return false;
	}

	public boolean resultInit(ActionForm actionForm, HttpServletRequest request) {
		BehaviorActionForm form = (BehaviorActionForm) actionForm;
		form.setAction("add");
		form.init(new BehaviorPattern());

		return false;
	}

	public boolean resultView(ActionForm actionForm, HttpServletRequest request) {
        HibernateEntityDao hibernateEntityDao =  (HibernateEntityDao) ContextAwareGuiBean.getContext().getBean("hibernateEntityDao");
        BehaviorActionForm form = (BehaviorActionForm) actionForm;
		BehaviorPattern pattern = (BehaviorPattern) hibernateEntityDao.loadObject(
				BehaviorPattern.class, new Integer(form.getId()));
		form.init(pattern);
		form.setAction("edit");

		return false;
	}
}
