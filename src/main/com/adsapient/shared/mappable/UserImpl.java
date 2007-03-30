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

import com.adsapient.api.AbstractVerifyingElement;
import com.adsapient.api.User;
import com.adsapient.api.IMappable;

import com.adsapient.shared.mappable.Account;
import com.adsapient.shared.service.RatesService;
import com.adsapient.shared.AdsapientConstants;
import com.adsapient.gui.ContextAwareGuiBean;

import org.apache.log4j.Logger;

import org.apache.struts.action.ActionForm;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

public class UserImpl extends AbstractVerifyingElement implements Serializable,
		User, IMappable {
	private static final long serialVersionUID = 1L;

	static Logger logger = Logger.getLogger(UserImpl.class);

	private Integer id;

	private Integer realUserId = null;

	private String City;

	private String company;

	private String country;

	private String email;

	private String login;

	private String name;

	private String password;

	private String phone;

	private String role;

	public UserImpl() {
		super();
	}

	public void setCity(String city) {
		City = city;
	}

	public String getShortRole() {
		if (AdsapientConstants.ADMIN.equalsIgnoreCase(this.role)) {
			return "role.adm";
		}

		if (AdsapientConstants.PUBLISHER.equalsIgnoreCase(this.role)) {
			return "role.pub";
		}

		if (AdsapientConstants.ADVERTISER.equalsIgnoreCase(this.role)) {
			return "role.adv";
		}

		if (AdsapientConstants.ADVERTISERPUBLISHER.equalsIgnoreCase(this.role)) {
			return "role.advpub";
		}

		if (AdsapientConstants.HOSTEDSERVICE.equalsIgnoreCase(this.role)) {
			return "role.hosted";
		}

		if (AdsapientConstants.GUEST.equalsIgnoreCase(this.role)) {
			return "role.guest";
		}

		return "";
	}

	public String getCity() {
		return City;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCompany() {
		return company;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountry() {
		return country;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public Integer getId() {
		return id;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getLogin() {
		return login;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone() {
		return phone;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}

	public boolean isOwnCampaignsAllow() {
		if (AdsapientConstants.ADVERTISERPUBLISHER.equalsIgnoreCase(this.role)) {
			return true;
		}

		return false;
	}

	private void setId(Integer id) {
		this.id = id;
	}

	public Integer getAccountMoney() {
        RatesService ratesService = (RatesService) ContextAwareGuiBean.getContext().getBean("ratesService");
        Account account = ratesService.loadUserAccount(this.getId());

		if (account != null) {
			return account.getMoney();
		} else {
			logger.error("cant load account for user with id=" + this.getId());

			return new Integer(0);
		}
	}

	public Integer getRealUserId() {
		return realUserId;
	}

	public void setRealUserId(Integer realUserId) {
		this.realUserId = realUserId;
	}

	public boolean resultAdd(ActionForm actionForm, HttpServletRequest request) {
		return false;
	}

	public boolean resultDelete(ActionForm actionForm,
			HttpServletRequest request) {
		return false;
	}

	public boolean resultEdit(ActionForm actionForm, HttpServletRequest request) {
		return false;
	}

	public boolean resultInit(ActionForm actionForm, HttpServletRequest request) {
		return false;
	}

	public boolean resultView(ActionForm actionForm, HttpServletRequest request) {
		return false;
	}
}
