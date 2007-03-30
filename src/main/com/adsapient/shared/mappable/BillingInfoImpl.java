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

import com.adsapient.api.IMappable;

public class BillingInfoImpl  implements IMappable {
    private Integer userId;

    private Integer id;

    private String payPalLogin = "";

    private Integer minimumPublisherPayout;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMinimumPublisherPayout() {
        return minimumPublisherPayout;
    }

    public void setMinimumPublisherPayout(Integer minimumPublisherPayout) {
        this.minimumPublisherPayout = minimumPublisherPayout;
    }

    public String getPayPalLogin() {
        return payPalLogin;
    }

    public void setPayPalLogin(String payPalLogin) {
        this.payPalLogin = payPalLogin;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BillingInfoImpl getCopy(Integer newUserId) {
        BillingInfoImpl billing = new BillingInfoImpl();
        billing.setUserId(newUserId);
        billing.setMinimumPublisherPayout(this.getMinimumPublisherPayout());

        return billing;
    }
}
