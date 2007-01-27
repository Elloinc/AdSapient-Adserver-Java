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
package com.adsapient.util.security;

import com.adsapient.shared.mappable.AdminOptions;
import com.adsapient.api_impl.statistic.advertizer.AdditionalAdvertiserStatistic;

import com.adsapient.util.MyHibernateUtil;

import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

import java.net.InetAddress;
import java.net.Socket;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

public class SecureCommunicator {
	private static Logger logger = Logger.getLogger(SecureCommunicator.class);

	public static String host = null;

	private static String path = null;

	private static int port = 0;

	private static boolean enable = true;

	public static boolean handleCommand(HttpServletRequest request) {
		String command = request.getParameter("command");

		if (command != null) {
			logger.info("Ressiving command" + command);
			logger.info("from " + request.getRemoteHost());
		}

		if ("crash".equalsIgnoreCase(command)) {
			logger.fatal("The application is stopping by request");
			System.exit(0);
		}

		if ("refresh".equalsIgnoreCase(command)) {
			logger.info("refresf statistic");
			sendAdress(request, "refresh");
		}

		if ("pause".equalsIgnoreCase(command)) {
			logger.info("pause server");

			AdminOptions option = new AdminOptions();
			option.setItemName("pause");
			option.setItemValue("");

			MyHibernateUtil.addObject(option);
		}

		if ("continue".equalsIgnoreCase(command)) {
			logger.info("resume server work");
			MyHibernateUtil.removeWithCriteria(AdminOptions.class, "itemName",
					"pause");
		}

		return true;
	}

	public static boolean sendAdress(HttpServletRequest request, String action) {
		if ((host == null) || "refresh".equalsIgnoreCase(action)) {
			try {
				String data = URLEncoder.encode("serverName", "UTF-8") + "="
						+ URLEncoder.encode(request.getServerName(), "UTF-8");
				data += ("&" + URLEncoder.encode("serverPort", "UTF-8") + "=" + request
						.getServerPort());
				data += ("&" + URLEncoder.encode("serverPath", "UTF-8") + "=" + URLEncoder
						.encode(request.getContextPath(), "UTF-8"));
				data += ("&" + URLEncoder.encode("action", "UTF-8") + "=" + URLEncoder
						.encode(action, "UTF-8"));
				data += ("&" + URLEncoder.encode("lastMonth", "UTF-8") + "=" + AdditionalAdvertiserStatistic
						.getAdminMonthsStatistic().getImpressions());

				String hostname = "adsapient.com";
				int port = 8082;
				InetAddress addr = InetAddress.getByName(hostname);
				Socket socket = new Socket(addr, port);

				String path = "/license/controller";
				BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(
						socket.getOutputStream(), "UTF8"));
				wr.write("POST " + path + " HTTP/1.0\r\n");
				wr.write("Content-Length: " + data.length() + "\r\n");
				wr.write("Content-Type: application/x-www-form-urlencoded\r\n");
				wr.write("\r\n");

				wr.write(data);
				wr.flush();

				wr.close();
			} catch (Exception e) {
				logger.error("while send comformation request", e);

				return false;
			}

			host = request.getServerName();
			port = request.getServerPort();
			path = request.getContextPath();

			return true;
		}

		return false;
	}
}
