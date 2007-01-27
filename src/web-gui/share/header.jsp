<%@ page import="com.adsapient.util.admin.AdsapientConstants"%>
<%@ page import="com.adsapient.api_impl.usermanagment.UserImpl"%>

<%@ taglib uri='/WEB-INF/struts-bean.tld' prefix='bean' %>

<table cellspacing="0" cellpadding="0" width="94%" border="0">
    <tr><td style="background-color:#ffffff;" align="center"><img alt="<bean:message key="productname"/>" src="images/logo.gif"></td></tr>
    <%
        if (session.getAttribute(AdsapientConstants.USER) != null) {
            UserImpl user = (UserImpl) session.getAttribute(AdsapientConstants.USER);
    %>
    <tr><td style="background-color:#ffffff;font-family:arial; font-size:13px;" >Welcome, <b><%= " " + user.getLogin()%></b></td></tr>
    <%
        }
    %>
</table>
