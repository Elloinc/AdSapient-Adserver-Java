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
package com.adsapient.api_impl.usermanagment;

import com.adsapient.util.MyHibernateUtil;

import org.apache.log4j.Logger;

public class Account {
	static Logger logger = Logger.getLogger(Account.class);

	Integer accountId;

	Integer userId;

	Integer money = new Integer(0);

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void update(Integer value) {
		this.money = new Integer(value.intValue() + money.intValue());

		if (this.accountId != null) {
			MyHibernateUtil.updateObject(this);
		}
	}

	public void update(int value) {
		this.money = new Integer(value + money.intValue());

		if (this.accountId != null) {
			MyHibernateUtil.updateObject(this);
		} else {
		}
	}
}
