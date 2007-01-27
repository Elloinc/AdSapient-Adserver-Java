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

import com.adsapient.api_impl.usermanagment.Account;
import com.adsapient.api_impl.usermanagment.BillingInfoImpl;
import com.adsapient.api_impl.usermanagment.UserImpl;
import com.adsapient.api_impl.usermanagment.role.RoleController;

import com.adsapient.util.Msg;
import com.adsapient.util.MyHibernateUtil;
import com.adsapient.util.admin.AdsapientConstants;
import com.adsapient.util.financial.MoneyTransformer;

import org.apache.log4j.Logger;

import org.apache.struts.action.ActionForm;

import java.io.UnsupportedEncodingException;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ViewAccountActionForm extends ActionForm {
    private static final long serialVersionUID = 1L;
    static final Logger logger = Logger.getLogger(ViewAccountActionForm.class);
    String action = "view";
    String header = "";
    private boolean publisherPayments = false;
    private boolean advertiserPayments = false;
    private HttpServletRequest request;
    private HttpServletResponse responce;
    String userId;
    float money = 0;
    private String currentBalanceName = "";

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setCurrentBalanceName(String currentBalanceName) {
        this.currentBalanceName = currentBalanceName;
    }

    public String getCurrentBalanceName() {
        return currentBalanceName;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getHeader() {
        return header;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public float getMoney() {
        return money;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void initForm(Account account) {
        this.money = MoneyTransformer.transformMoney(account.getMoney());
        this.userId = account.getUserId().toString();
    }

    public void init(UserImpl user, Account account) {
        BillingInfoImpl billing = (BillingInfoImpl) MyHibernateUtil.loadObjectWithCriteria(BillingInfoImpl.class,
                "userId", user.getId());

        if (RoleController.ADMIN.equals(user.getRole()) ||
                RoleController.PUBLISHER.equals(user.getRole()) ||
                RoleController.ADVERTISERPUBLISHER.equals(user.getRole())) {
            if (user.getAccountMoney().intValue() >= billing.getMinimumPublisherPayout()
                                                                .intValue()) {
                this.publisherPayments = true;
            }
        }

        if (RoleController.ADMIN.equals(user.getRole()) ||
                RoleController.ADVERTISER.equals(user.getRole()) ||
                RoleController.ADVERTISERPUBLISHER.equals(user.getRole())) {
            this.setAdvertiserPayments(true);
        }
    }

    public String getPublisherLink() {
        StringBuffer buffer = new StringBuffer();

        if (request.getSession().getAttribute(AdsapientConstants.GUEST) != null) {
            return buffer.toString();
        }

        buffer.append("<a   href=\"")
              .append(responce.encodeURL("billingTransaction.do?action=" +
                RoleController.PUBLISHER)).append(" \">")
              .append(Msg.fetch("request.publisher.payout", request))
              .append("</a>");

        return buffer.toString();
    }

    public String getAdvertiserLink() {
        BillingInfoImpl billingInfor = (BillingInfoImpl) MyHibernateUtil.loadObject(BillingInfoImpl.class,
                new Integer(0));

        StringBuffer buffer = new StringBuffer();

        if (request.getSession().getAttribute(AdsapientConstants.GUEST) != null) {
            return buffer.toString();
        }

        try {
            buffer.append("<a target=\"_blank\" href=\"")
                  .append("https://www.paypal.com/cgi-bin/webscr?cmd=_xclick&business=")
                  .append(URLEncoder.encode(billingInfor.getPayPalLogin(),
                    "UTF-8"))
                  .append("&item_name=Adsapient%20money%20transfer&item_number=0&no_shipping=0&no_note=1&tax=0&currency_code=")
                  .append(MoneyTransformer.getSystemCurrency().getCurrencyCode())
                  .append("&bn=PP%2dDonationsBF&charset=UTF%2d8").append(" \">")
                  .append(Msg.fetch("request.money.transfer", request))
                  .append("</a>");
        } catch (UnsupportedEncodingException e) {
            logger.error("cant Encode email adress");

            return "N/A";
        }

        return buffer.toString();
    }

    public boolean isAdvertiserPayments() {
        return advertiserPayments;
    }

    public void setAdvertiserPayments(boolean advertiserPayments) {
        this.advertiserPayments = advertiserPayments;
    }

    public boolean isPublisherPayments() {
        return publisherPayments;
    }

    public void setPublisherPayments(boolean publisherPayments) {
        this.publisherPayments = publisherPayments;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponce() {
        return responce;
    }

    public void setResponce(HttpServletResponse responce) {
        this.responce = responce;
    }
}
