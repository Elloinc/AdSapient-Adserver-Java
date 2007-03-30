<%@ page import="com.adsapient.gui.forms.GuestAccountActionForm"%>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %>
<%@ taglib uri='/WEB-INF/struts-bean.tld' prefix='bean' %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ page contentType="text/html; charset=UTF-8" %> 

<html:errors/>
<html:messages id="msg" message="true" header="messages.header" footer="messages.footer" >
 <bean:write name="msg" />
</html:messages>

<html:form action="guest" >
<html:hidden property="action"/>
<html:hidden property="id"/>
<html:hidden property="userRole"/>
<% GuestAccountActionForm form = (GuestAccountActionForm) request.getAttribute("guestAccountActionForm"); %>
<% form.setRequest(request); %>
<tr><td>
	<table width="100%"cellspacing="0" cellpadding="0"><tr>
	<td><img src="images/table1.gif"></td>
	<td width="100%" class="tableheader"><bean:message key="user.information"/></td>
	<td><img src="images/table2.gif"></td>
	</tr></table>
</td></tr>
<tr><td>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">

	
	<tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td height="10"  class="maintext"><table border="0" width="100%" cellspacing="5"><tr><td>&nbsp;&nbsp;<bean:message key="name"/>:</td></tr></table></td>
	<td ><table border="0" width="100%" cellspacing="5"><tr><td><html:text  style="width:150" property="name"/></td></tr></table></td>
	<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr>

	     
	<tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td height="10"  class="maintext"><table border="0" width="100%" cellspacing="5"><tr><td>&nbsp;&nbsp;<bean:message key="login"/>:</td></tr></table></td>
	<td ><table border="0" width="100%" cellspacing="5"><tr><td><html:text  style="width:150" property="profileLogin"/></td></tr></table></td>
	<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr>
	<tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td height="10" class="maintext"><table border="0" width="100%" cellspacing="5"><tr><td>&nbsp;&nbsp;<bean:message key="password"/>:</td></tr></table></td>
	<td ><table border="0" width="100%" cellspacing="5"><tr><td><html:password size="10" style="width:150" property="profilePassword"/></td></tr></table></td>
	<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr>
	<tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td height="10" class="maintext"><table border="0" width="100%" cellspacing="5"><tr><td>&nbsp;&nbsp;<bean:message key="confirmpassword"/>:</td></tr></table></td>
	<td ><table border="0" width="100%" cellspacing="5"><tr><td><html:password size="10" style="width:150" property="confirmpassword"/></td></tr></table></td>
	<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr> 
	
	<tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td height="10" class="maintext"><table border="0" width="100%" cellspacing="5"><tr><td>&nbsp;&nbsp;<bean:message key="email"/>:</td></tr></table></td>
	<td ><table border="0" width="100%" cellspacing="5"><tr><td><html:text  style="width:150" property="email"/></td></tr></table></td>
	<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr> 
	
	
	<tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td height="10" class="maintext"><table border="0" width="100%" cellspacing="5"><tr><td>&nbsp;&nbsp;<bean:message key="account.type"/>:</td></tr></table></td>
	<td ><table border="0" width="100%" cellspacing="5"><tr><td><html:select  property="userRole" disabled="true">
	<html:optionsCollection  property="userRolesCollection" /></html:select>       </td></tr></table></td>
	<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr>
	
	<tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td ></td>
	<td height="10" ><table border="0" width="100%" cellspacing="5"><tr><td><input type="submit" value='<bean:message key="submit"/>'></td></tr></table></td>
	<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr>
	
	</table>
</td></tr>
<tr><td>
	<table width="100%" cellspacing="0" cellpadding="0"><tr>
	<td><img src="images/table6.gif"></td>
	<td width="100%" heigh="10" style="background-image: url(images/table8.gif);"></td>
	<td><img src="images/table7.gif"></td>
	</tr></table>
</td></tr>
 </html:form>
