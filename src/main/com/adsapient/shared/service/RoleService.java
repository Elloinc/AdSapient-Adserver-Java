package com.adsapient.shared.service;

import com.adsapient.shared.mappable.RoleImpl;
import com.adsapient.shared.mappable.UserImpl;
import com.adsapient.shared.AdsapientConstants;
import com.adsapient.shared.dao.HibernateEntityDao;
import com.adsapient.gui.ContextAwareGuiBean;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.struts.util.LabelValueBean;

public class RoleService {
    public static Collection getRoleLabelsCollection(HttpServletRequest request) {
        HibernateEntityDao hibernateEntityDao = (HibernateEntityDao) ContextAwareGuiBean.getContext().getBean("hibernateEntityDao");
        Collection userRolesCollection = new ArrayList();

        Collection registeredRoles = hibernateEntityDao.viewAll(RoleImpl.class);

        UserImpl user = (UserImpl) request.getSession().getAttribute(
                AdsapientConstants.USER);

        Iterator rolesIterator = registeredRoles.iterator();

        while (rolesIterator.hasNext()) {
            RoleImpl role = (RoleImpl) rolesIterator.next();

            if (!role.isEnabled()) {
                continue;
            }

            if (AdsapientConstants.ADMIN.equalsIgnoreCase(role.getRoleName())) {
                if (AdsapientConstants.ADMIN.equalsIgnoreCase(user.getRole())) {
                    userRolesCollection.add(new LabelValueBean(I18nService.fetch(role
                            .getRoleName(), request), role.getRoleName()));
                }
            } else {
                userRolesCollection.add(new LabelValueBean(I18nService.fetch(role
                        .getRoleName(), request), role.getRoleName()));
            }
        }

        return userRolesCollection;
    }

    public static Collection getNotAutoriseRoleLabelsCollection(
            HttpServletRequest request) {
        HibernateEntityDao hibernateEntityDao = (HibernateEntityDao)ContextAwareGuiBean.getContext().getBean("hibernateEntityDao");
        Collection userRolesCollection = new ArrayList();

        Collection registeredRoles = hibernateEntityDao.viewAll(RoleImpl.class);

        UserImpl user = (UserImpl) request.getSession().getAttribute("user");

        Iterator rolesIterator = registeredRoles.iterator();

        while (rolesIterator.hasNext()) {
            RoleImpl role = (RoleImpl) rolesIterator.next();

            if (!role.isEnabled()) {
                continue;
            }

            if (!AdsapientConstants.ADMIN.equalsIgnoreCase(role.getRoleName())
                    || !AdsapientConstants.HOSTEDSERVICE.equalsIgnoreCase(role
                            .getRoleName())) {
                userRolesCollection.add(new LabelValueBean(I18nService.fetch(role
                        .getRoleName(), request), role.getRoleName()));
            }
        }

        return userRolesCollection;
    }
}
