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
package com.adsapient.gui.forms;

import com.adsapient.shared.mappable.UserImpl;
import com.adsapient.shared.service.CookieManagementService;
import com.adsapient.shared.dao.HibernateEntityDao;
import com.adsapient.gui.ContextAwareGuiBean;

import org.apache.commons.validator.EmailValidator;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;


public class GuestAccountActionForm extends ProfileEditActionForm {
    private static final long serialVersionUID = 1L;
    private String action = "init";
    private Collection users = new ArrayList();

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Collection getUsers() {
        return users;
    }

    public void setUsers(Collection users) {
        this.users = users;
    }

    public ActionErrors validate(ActionMapping mapping,
        HttpServletRequest request) {
        HibernateEntityDao hibernateEntityDao = (HibernateEntityDao) ContextAwareGuiBean.getContext().getBean("hibernateEntityDao");
        ActionErrors errors = new ActionErrors();

        if ("list".equalsIgnoreCase(this.action) ||
                "init".equalsIgnoreCase(this.action) ||
                "view".equalsIgnoreCase(this.action) ||
                "remove".equalsIgnoreCase(this.action) ||
                "resetStatistic".equalsIgnoreCase(this.action)) {
            return null;
        }

        if (getName().trim().length() == 0) {
            errors.add(ActionErrors.GLOBAL_ERROR,
                new ActionError("error.edituser.namerequired"));
        }

        if (getProfileLogin().trim().length() == 0) {
            errors.add(ActionErrors.GLOBAL_ERROR,
                new ActionError("error.edituser.loginrequired"));
        }

        if (getProfilePassword().trim().length() == 0) {
            errors.add(ActionErrors.GLOBAL_MESSAGE,
                new ActionError("error.edituser.passwordrequired"));
        }

        if (!this.getProfilePassword().equals(this.getConfirmpassword()) &&
                (this.getProfilePassword().length() != 0)) {
            errors.add(ActionErrors.GLOBAL_MESSAGE,
                new ActionError("error.edituser.confirmpassword"));
        }

        if (getEmail().trim().length() == 0) {
            errors.add(ActionErrors.GLOBAL_MESSAGE,
                new ActionError("error.edituser.emailrequired"));
        }

        EmailValidator ev = EmailValidator.getInstance();

        if (!ev.isValid(getEmail())) {
            errors.add(ActionErrors.GLOBAL_MESSAGE,
                new ActionError("error.edituser.wrongemail"));
        }

        UserImpl sameUser = (UserImpl) hibernateEntityDao.loadObjectWithCriteria(UserImpl.class,
                "login", getProfileLogin());

        if ((sameUser != null) && (!sameUser.getId().equals(this.getId()))) {
            errors.add(ActionErrors.GLOBAL_MESSAGE,
                new ActionError("error.login.exist"));
        }

        return errors;
    }

    public UserImpl getUser(UserImpl user) {
        user.setLogin(this.getProfileLogin());
        user.setPassword(this.getProfilePassword());
        user.setName(this.getName());
        user.setRole(this.getUserRole());
        user.setEmail(this.getEmail());

        return user;
    }
}
