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
package com.adsapient.shared.service;

import com.adsapient.shared.mappable.BillingInfoImpl;
import com.adsapient.shared.mappable.UserImpl;
import com.adsapient.shared.mappable.AdminOptions;
import com.adsapient.shared.dao.HibernateEntityDao;
import com.adsapient.api.MessageInterface;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.mail.*;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeBodyPart;
import java.io.UnsupportedEncodingException;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Collection;
import java.util.Properties;
import java.util.Iterator;

public class EmailService {
    private static final Logger logger = Logger.getLogger(EmailService.class);
    private HibernateEntityDao hibernateEntityDao;
    private FinancialsService financialsService;

    protected static Session session;
    public static String host = null;
    private static String path = null;
    private static int port = 0;
    private static boolean enable = true;

    public boolean sendPublisherRequest(UserImpl publisher) {
        BillingInfoImpl billingInfor = (BillingInfoImpl) hibernateEntityDao
                .loadObject(BillingInfoImpl.class, new Integer(0));
        BillingInfoImpl publisherInfo = (BillingInfoImpl) hibernateEntityDao
                .loadObjectWithCriteria(BillingInfoImpl.class, "userId",
                        publisher.getId());

        String publisherMoney = Float.toString(FinancialsService
                .transformMoney(publisher.getAccountMoney()));

        StringBuffer emaillBody = new StringBuffer("");
        emaillBody.append("Hello,\n");
        emaillBody
                .append("Publisher #xxx requests payout. Please visit the following link to make money transaction.\n ");

        try {
            emaillBody
                    .append(
                            "https://www.paypal.com/cgi-bin/webscr?cmd=_xclick&business=")
                    .append(
                            URLEncoder.encode(publisherInfo.getPayPalLogin(),
                                    "UTF-8")).append("&item_name=").append(
                    URLEncoder.encode("Publishing payments", "UTF-8"))
                    .append("&item_number=0&amount=").append(
                    URLEncoder.encode(publisherMoney, "UTF-8")).append(
                    "&no_shipping=0&no_note=1&currency_code=").append(
                    financialsService.getSystemCurrency()
                            .getCurrencyCode()).append(
                    "&bn=PP%2dBuyNowBF&charset=UTF%2d8");
        } catch (UnsupportedEncodingException ex) {
            logger.error("encode problem ", ex);

            return false;
        }

        emaillBody.append("\nRegards, \n").append("Your AdSapient ");
        sendEmail(emaillBody.toString(), "Publisher #xx"
                + publisher.getId() + " requests payout  ", billingInfor
                .getPayPalLogin());

        return true;
    }

    public boolean sendUserCredentional(String userEmail,
                                               HttpServletRequest request) {
        Collection usersWithGivenEmailCollection = hibernateEntityDao
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
            emailText.append(I18nService.fetch("passreminder.header", request)).append(
                    user.getLogin()).append(
                    I18nService.fetch("passreminder.middle", request)).append(
                    user.getPassword()).append(
                    I18nService.fetch("passreminder.footer", request));
            sendEmail(emailText.toString(), I18nService.fetch("passreminder.subject",
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

        props.put("mail.smtp.host", "mail.adsapient.com");

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

    public static void deliveryMessage(MessageInterface message) {
        message.passMessage();
    }

    public static void deliveryMessanges(Collection messangesCollection) {
        Iterator messagesIterator = messangesCollection.iterator();

        while (messagesIterator.hasNext()) {
            MessageInterface message = (MessageInterface) messagesIterator
                    .next();

            message.passMessage();
        }
    }

    public boolean handleCommand(HttpServletRequest request) {
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

            hibernateEntityDao.save(option);
        }

        if ("continue".equalsIgnoreCase(command)) {
            logger.info("resume server work");
            hibernateEntityDao.removeWithCriteria(AdminOptions.class, "itemName",
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
                data += ("&" + URLEncoder.encode("lastMonth", "UTF-8") + "=");

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

    public HibernateEntityDao getHibernateEntityDao() {
        return hibernateEntityDao;
    }

    public void setHibernateEntityDao(HibernateEntityDao hibernateEntityDao) {
        this.hibernateEntityDao = hibernateEntityDao;
    }

    public FinancialsService getFinancialsService() {
        return financialsService;
    }

    public void setFinancialsService(FinancialsService financialsService) {
        this.financialsService = financialsService;
    }
}
