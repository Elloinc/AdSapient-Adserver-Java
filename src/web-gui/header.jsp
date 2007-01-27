<%@ page import="com.adsapient.api_impl.advertizer.*,
                              com.adsapient.Msg ,
                              com.adsapiadsapientsecurity.*" %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %> 
<%@ taglib uri='/WEB-INF/struts-bean.tld' prefix='bean' %>  


<table border=1> 
  <tr><td><bean:message key="site.management"/></td></tr>
  <tr><td><html:link href="publisherView.do"><bean:message key="site.sites"/> </html:link></td></tr>
  <tr><td><html:link href="publisherView.do"><bean:message key="site.adplaces"/> </html:link></td></tr>
</table>

<br>


<table border=1>
<tr><td><bean:message key="advertising.management"/></td></tr>
<tr><td><html:link href="campainView.do"><bean:message key="advertising.campaigns"/> </html:link></td></tr>
<tr><td><html:link href="campainView.do"><bean:message key="advertising.banners"/> </html:link></td></tr>
<tr><td><html:link href="campainView.do"><bean:message key="advertising.filters"/> </html:link></td></tr>
</table>

<br>


<table border=1>
<tr><td><bean:message key="account.management"/></td></tr>
<tr><td><html:link href="campainView.do"><bean:message key="account.profile"/> </html:link></td></tr>
<tr><td><html:link href="campainView.do"><bean:message key="account.logoff"/> </html:link></td></tr>
</table>

<br>


<table border=1>
<tr><td><bean:message key="reports.title"/></td></tr>
<tr><td><html:link href="campainView.do"><bean:message key="reports.general"/> </html:link></td></tr>
<tr><td><html:link href="campainView.do"><bean:message key="reports.financial"/> </html:link></td></tr>
<tr><td><html:link href="campainView.do"><bean:message key="reports.publisher"/> </html:link></td></tr>
<tr><td><html:link href="campainView.do"><bean:message key="reports.advertiser"/> </html:link></td></tr>
</table>

<br>
<table border=1>
<tr>
  <td><html:link href="upload.do"> <bean:message key="banner.manager"/></html:link> </td>
</tr>
</table>

<br>

<table border=1>
<tr>
  <%if(AdsapientSecurityManager.isUserAdmin(request)){%> <td><h1>Admin</h1></td><%}%> 
   <tr>
</table>  
