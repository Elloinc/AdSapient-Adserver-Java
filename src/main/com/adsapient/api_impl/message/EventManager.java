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
package com.adsapient.api_impl.message;

import com.adsapient.api.EmailMessageInterface;

import com.adsapient.api_impl.usermanagment.UserImpl;

import com.adsapient.util.MyHibernateUtil;

import javax.servlet.http.HttpServletRequest;

public class EventManager {
	public static void userRegisterevent(Integer userId,
			HttpServletRequest request) {
		UserImpl user = (UserImpl) MyHibernateUtil.loadObject(UserImpl.class,
				userId);

		StringBuffer buffer = new StringBuffer();
		buffer.append("Hello,").append(user.getName()).append("\n");
		buffer.append("Welcome to AdSapient Banner Management System.").append(
				"\n");
		buffer.append("You can login to AdSapient at: http://").append(
				request.getServerName()).append(":").append(
				request.getServerPort()).append(request.getContextPath())
				.append("\n");
		buffer.append("\n");
		buffer.append("Your account details:").append("\n");
		buffer.append("Name:").append(user.getName()).append("\n");
		buffer.append("Login:").append(user.getLogin()).append("\n");
		buffer.append("Password:").append(user.getPassword()).append("\n");
		buffer.append("Email:").append(user.getEmail()).append("\n");
		buffer.append("User role:").append(user.getRole()).append("\n");
		buffer.append("\n");
		buffer.append("---").append("\n");
		buffer.append("Regards,").append("\n");
		buffer.append("AdSapient");

		EmailMessageInterface message = new EmailMessageImpl(user.getEmail(),
				buffer.toString(), "Welcome to AdSapient",
				"contact@adsapient.com");

		MessagesManager.deliveryMessage(message);
	}
}
