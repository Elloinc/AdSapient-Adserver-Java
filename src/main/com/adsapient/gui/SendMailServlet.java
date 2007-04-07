package com.adsapient.gui;

import org.apache.log4j.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

public class SendMailServlet extends HttpServlet {
    private static Logger logger = Logger.getLogger(SendMailServlet.class);
    public static Properties MAIL_SERVER_CONFIG;
    public static Properties APP_RES = new Properties();
    public static final byte SUCCESS = 0;
    public static final byte FAILURE = 1;
    public static final byte READY = 2;

    public void init() throws ServletException {
        MAIL_SERVER_CONFIG = new Properties();
        APP_RES = new Properties();
        try {
            MAIL_SERVER_CONFIG.load(this.getServletContext().getResourceAsStream("WEB-INF/adsapient.properties"));
            APP_RES.load(this.getServletContext().getResourceAsStream("WEB-INF/ApplicationResource.properties"));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }


    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws ServletException, IOException {
        String firstName = request.getParameter("rtFirstName") == null ? "" : request.getParameter("rtFirstName");
        String lastName = request.getParameter("rtLastName") == null ? "" : request.getParameter("rtLastName");
        String company = request.getParameter("stCompany") == null ? "" : "from " + request.getParameter("stCompany");
        String email = request.getParameter("reEmail") == null ? "" : request.getParameter("reEmail");
        String comment = request.getParameter("rtComment") == null ? "" : request.getParameter("rtComment");
        //
        String from = firstName.trim() + " " + lastName.trim() + company.trim();
        String message = compileMessage(from, email, comment);
        //
        byte result = sendEmail(email, MAIL_SERVER_CONFIG.getProperty("recepient.email"),
                APP_RES.getProperty("email.subject"), message);
        request.setAttribute("__result", result);
        //
        String[] path = request.getHeader("referer").split("/");
        String input = "contact.jsp";
        if (path != null && path.length > 0) {
            input = path[path.length - 1];
            if (input.contains("?")) {
                input = input.split("\\?")[0];
            }
        }
//        RequestDispatcher rd = request.getRequestDispatcher(input);
//        rd.forward(request, response);
//        response.sendRedirect(input);
        request.getRequestDispatcher("contact.jsp").forward(request, response);
    }

    private String compileMessage(String from, String email, String comment) {
        StringBuffer sb = new StringBuffer();
        sb.append("FROM: " + from);
        sb.append("\n");
        sb.append("EMAIL: " + email);
        sb.append("\n");
        sb.append("COMMENT: " + comment);
        return sb.toString();
    }


    private byte sendEmail(
            String aFromEmailAddr, String aToEmailAddr,
            String aSubject, String aBody
    ) {

        Session session = Session.getDefaultInstance(MAIL_SERVER_CONFIG, null);
        session.getProperties().put("mail.smtp.localhost", "localhost");
        session.setDebug(true);
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(aFromEmailAddr));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(aToEmailAddr));
            message.setSubject(aSubject);
            message.setText(aBody);
            Transport.send(message);
            return SUCCESS;
        }
        catch (MessagingException ex) {
            logger.error(ex.getMessage(), ex);
            return FAILURE;
        }
    }
}
