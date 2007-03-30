<%@ page import="com.adsapient.api_impl.advertizer.*,
                              com.adsapient.Msg ,
                              com.adsapient.util.security.*" %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %> 
<%@ taglib uri='/WEB-INF/struts-bean.tld' prefix='bean' %>



<html>
<head>
<title><bean:message key="welcome"/></title> </head>
 <link rel="stylesheet" href="css/WinterICE.css" type="text/css" />


<body >
<h1> <%=Msg.fetch("welcome",session)%></h1>
<table border=1> 
  <tr><td><html:link href="publisherView.do"><bean:message key="publisher"/> </html:link></td>
  <td><html:link href="campainView.do"><bean:message key="advertizer"/> </html:link></td> 
  <td><html:link href="upload.do"> test upload file </html:link> </td> 
  <td><html:link href="viewStatistic.do">view Statistic</html:link></td>
  <%--<td><html:link href="login.do?action=logout"><bean:message key="logout"/></html:link></td>--%>
  <td><html:link href="logout.do"><bean:message key="logout"/></html:link></td>
  <%if(AdsapientSecurityManager.isUserAdmin(request)){%> <td><h1>Admin</h1></td><%}%> 
  
   <tr>
</table>  

<a href="filter.do?campainId=1">Filters</a>
<a href="campainEdit.do?action=DefaultCampain">deffault campain</a><br>
<a href="adminAction.do">admin main </a><br>
<a href="adminTuning.do">admin tuning</a><br>
</body>
</html>
