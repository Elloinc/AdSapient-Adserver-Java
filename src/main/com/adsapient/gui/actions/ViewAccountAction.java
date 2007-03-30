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
package com.adsapient.gui.actions;

import com.adsapient.api.AdsapientException;

import com.adsapient.shared.exceptions.AdsapientSecurityException;
import com.adsapient.shared.mappable.Account;
import com.adsapient.shared.mappable.UserImpl;

import com.adsapient.shared.dao.HibernateEntityDao;
import com.adsapient.shared.service.TotalsReportService;
import com.adsapient.shared.service.RatesService;
import com.adsapient.shared.service.CookieManagementService;
import com.adsapient.shared.AdsapientConstants;

import com.adsapient.shared.service.I18nService;
import com.adsapient.shared.service.SecurityManagementService;
import com.adsapient.gui.forms.ViewAccountActionForm;
import com.adsapient.gui.ContextAwareGuiBean;

import org.apache.log4j.Logger;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ViewAccountAction extends Action {
    private Logger logger = Logger.getLogger(ViewAccountAction.class);
    private TotalsReportService totalsReportsService;
    private HibernateEntityDao hibernateEntityDao;
    private CookieManagementService cookieManagementService;
    private RatesService ratesService;

    public ActionForward execute(ActionMapping mapping, ActionForm actionForm,
        HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        if (!SecurityManagementService.isUserLogIn(request)) {
            return mapping.findForward("login");
        }

        checkAccessRestriction(request, actionForm);

        ViewAccountActionForm form = (ViewAccountActionForm) actionForm;
        UserImpl user = (UserImpl) request.getSession()
                                          .getAttribute(AdsapientConstants.USER);
        UserImpl guest = (UserImpl) request.getSession()
                                           .getAttribute(AdsapientConstants.GUEST);
        form.setRequest(request);
        form.setResponce(response);

        if ("view".equalsIgnoreCase(form.getAction())) {
            Account account = ratesService.loadUserAccount(user.getId());
            form.setCurrentBalanceName("current.balance");

            if (account != null) {
                form.setHeader(I18nService.fetch("account.view", request));

                form.setMoney(new Float(totalsReportsService.getUserBalance(
                            user.getId())));
                form.init(user, account);
            } else {
                logger.error("trying to view account for user with id " +
                    user.getId() + " but account ==null");
            }

            if (AdsapientConstants.ADMIN.equalsIgnoreCase(user.getRole())) {
                form.setAction("edit");

                form.setHeader(I18nService.fetch("account.view", request));
                form.setUserId(user.getId().toString());

                return mapping.findForward("edit");
            }

            return mapping.findForward("view");
        }

        if ("system".equalsIgnoreCase(form.getAction())) {
            if (!AdsapientConstants.ADMIN.equals(user.getRole())) {
                throw new AdsapientException("access.deny");
            }

            Account systemAccount = (Account) hibernateEntityDao.loadObject(Account.class,
                    AdsapientConstants.SYSTEM_ACCOUNT_ID);

            if (systemAccount != null) {
                form.setCurrentBalanceName("system.account.balance");
                form.setHeader(I18nService.fetch("system.account", request));
                form.setMoney(new Float(totalsReportsService.getUserBalance(
                            AdsapientConstants.ADMIN_ID)));
            } else {
                logger.error("Cant load money account for " +
                    AdsapientConstants.SYSTEM_ACCOUNT_ID);
            }

            return mapping.findForward("system");
        }

        if ("accountView".equalsIgnoreCase(form.getAction())) {
            if (!AdsapientConstants.ADMIN.equals(user.getRole())) {
                throw new AdsapientException("access.deny");
            }

            Account account = ratesService.loadUserAccount(new Integer(
                        form.getUserId()));

            if (account != null) {
                form.setHeader(I18nService.fetch("account.view", request));
                form.setCurrentBalanceName("current.balance");
                form.setAction("edit");
                form.initForm(account);
            }

            return mapping.findForward("edit");
        }

        if ("edit".equalsIgnoreCase(form.getAction())) {
            if (!AdsapientConstants.ADMIN.equals(user.getRole())) {
                throw new AdsapientException("access.deny");
            }

            Account account = ratesService.loadUserAccount(new Integer(
                        form.getUserId()));

            if (guest == null) {
                form.setMoney(new Float(totalsReportsService.getUserBalance(
                            user.getId())));

                hibernateEntityDao.updateObject(account);
            }

            form.initForm(account);

            return mapping.findForward("edit");
        }

        return mapping.findForward("empty");
    }

    protected void checkAccessRestriction(HttpServletRequest request,
        ActionForm actionForm) throws AdsapientSecurityException {
    }

    public TotalsReportService getTotalsReportsService() {
        return totalsReportsService;
    }

    public void setTotalsReportsService(
        TotalsReportService totalsReportsService) {
        this.totalsReportsService = totalsReportsService;
    }

    public HibernateEntityDao getHibernateEntityDao() {
        return hibernateEntityDao;
    }

    public void setHibernateEntityDao(HibernateEntityDao hibernateEntityDao) {
        this.hibernateEntityDao = hibernateEntityDao;
    }

    public CookieManagementService getCookieManagementService() {
        return cookieManagementService;
    }

    public void setCookieManagementService(CookieManagementService cookieManagementService) {
        this.cookieManagementService = cookieManagementService;
    }

    public RatesService getRatesService() {
        return ratesService;
    }

    public void setRatesService(RatesService ratesService) {
        this.ratesService = ratesService;
    }
}
