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
package com.adsapient.util.admin.email;

import com.adsapient.api_impl.usermanagment.UserImpl;

import com.adsapient.util.Msg;
import com.adsapient.util.MyHibernateUtil;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import javax.servlet.http.HttpServletRequest;

public class EmailSender {
	static Logger logger = Logger.getLogger(EmailSender.class);

	protected static Session session;

	public static boolean sendUserCredentional(String userEmail,
			HttpServletRequest request) {
		Collection usersWithGivenEmailCollection = MyHibernateUtil
				.viewWithCriteria(UserImpl.class, "email", userEmail, "id");

		if (usersWithGivenEmailCollection.isEmpty()) {
			logger.info("User with email=" + userEmail
					+ "not registeres in system");

			return false;
		} else {
			java.util.Iterator iter = usersWithGivenEmailCollection.iterator();
			UserImpl user = null;

			while (iter.hasNext()) {
				user = (UserImpl) iter.next();
			}

			StringBuffer emailText = new StringBuffer();
			emailText.append(Msg.fetch("passreminder.header", request)).append(
					user.getLogin()).append(
					Msg.fetch("passreminder.middle", request)).append(
					user.getPassword()).append(
					Msg.fetch("passreminder.footer", request));
			sendEmail(emailText.toString(), Msg.fetch("passreminder.subject",
					request), user.getEmail());

			return true;
		}
	}

	public static void sendEmail(String emailText, String emailSubject,
			String emailAdress) {
		sendEmail(emailText, emailSubject, emailAdress, "admin@adsapient.com");
	}

	public static void sendEmail(String emailText, String emailSubject,
			String emailAdress, String from) {
		Properties props = System.getProperties();
		Message mesg;

		props.put("mail.smtp.host", "mail.crickem.com");

		session = Session.getDefaultInstance(props, null);
		session.setDebug(true);

		try {
			mesg = new MimeMessage(session);

			mesg.setFrom(new InternetAddress(from));

			InternetAddress toAddress = new InternetAddress(emailAdress);
			mesg.setRecipient(Message.RecipientType.TO, toAddress);

			mesg.setSubject(emailSubject);

			Multipart mp = new MimeMultipart();

			BodyPart textPart = new MimeBodyPart();
			textPart.setText(emailText);

			mesg.setContent(mp);

			Transport.send(mesg);
		} catch (MessagingException ex) {
			System.err.println(ex);
			ex.printStackTrace(System.err);
		}
	}
}
