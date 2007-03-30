<%@ page import="com.adsapient.web.campain.buy.actions.*" %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %> 
<%@ taglib uri='/WEB-INF/struts-bean.tld' prefix='bean' %>
<%@ page contentType="text/html; charset=UTF-8" %>

 <% BuyActionsActionForm form = (BuyActionsActionForm) request.getAttribute("buyActionsActionForm");%>
<html>
<head>
<title><bean:message key="campain.edit"/></title> </head>
 <link rel="stylesheet" href="css/WinterICE.css" type="text/css" />


<body >

<jsp:include page="/header.jsp" />


<table>  

<tr> <td>                  
<html:form action="buyActions">
<html:hidden property="campainId"/>  
<html:hidden property="action"/>  
<html:text property="actions"/>
<html:select property="actionName"><html:optionsCollection property="namesCollection"/></html:select>
<input type="submit" value='<bean:message key="buy.impressions"/>'> 
</html:form>  
</td>             
<td>


 </tr>



</body>
</head>
