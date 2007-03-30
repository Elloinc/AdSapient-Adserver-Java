<%@ page import="com.adsapient.gui.forms.ProfileEditActionForm"%>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %>
<%@ taglib uri='/WEB-INF/struts-bean.tld' prefix='bean' %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<html:errors/>
<html:messages id="msg" message="true" header="messages.header" footer="messages.footer" >
 <bean:write name="msg" />
</html:messages>

<html:form action="profileEdit" >
<html:hidden property="action"/>
<html:hidden property="id"/>
<%ProfileEditActionForm form = (ProfileEditActionForm) request.getAttribute("profileEditActionForm"); %>
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
	<td height="10"  class="maintext"><table border="0" width="100%" cellspacing="5"><tr><td>&nbsp;&nbsp;<bean:message key="name"/>:&nbsp;&nbsp;<sup>*</sup></td></tr></table></td>
	<td ><table border="0" width="100%" cellspacing="5"><tr><td><html:text style="width:150"  property="name" maxlength="50"/></td></tr></table></td>
	<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr>

	
	<%//if("viewProfile".equalsIgnoreCase(form.getAction())||"edit".equalsIgnoreCase(form.getAction()) )  {%>
	<tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td height="10"  class="maintext"><table border="0" width="100%" cellspacing="5"><tr><td>&nbsp;&nbsp;<bean:message key="login"/>:&nbsp;&nbsp;<sup>*</sup></td></tr></table></td>
	<td ><table border="0" width="100%" cellspacing="5"><tr><td><html:text  style="width:150" property="profileLogin" maxlength="50"/></td></tr></table></td>
	<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr>
	<tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td height="10" class="maintext"><table border="0" width="100%" cellspacing="5"><tr><td>&nbsp;&nbsp;<bean:message key="password"/>:&nbsp;&nbsp;<sup>*</sup></td></tr></table></td>
	<td ><table border="0" width="100%" cellspacing="5"><tr><td><html:password size="10" style="width:150" property="profilePassword" maxlength="30"/></td></tr></table></td>
	<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr>
	<tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td height="10" class="maintext"><table border="0" width="100%" cellspacing="5"><tr><td>&nbsp;&nbsp;<bean:message key="confirmpassword"/>:&nbsp;&nbsp;<sup>*</sup></td></tr></table></td>
	<td ><table border="0" width="100%" cellspacing="5"><tr><td><html:password size="10" style="width:150" property="confirmpassword" maxlength="30"/></td></tr></table></td>
	<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr>

	<tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td height="10" class="maintext"><table border="0" width="100%" cellspacing="5"><tr><td>&nbsp;&nbsp;<bean:message key="email"/>:&nbsp;&nbsp;<sup>*</sup></td></tr></table></td>
	<td ><table border="0" width="100%" cellspacing="5"><tr><td><html:text  style="width:150" property="email" maxlength="40"/></td></tr></table></td>
	<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr>

    
    <%if("admin".equalsIgnoreCase(form.getUserRole())){  %>
    <tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td height="10" class="maintext"><table border="0" width="100%" cellspacing="5"><tr><td>&nbsp;&nbsp;<bean:message key="account.type"/>:</td></tr></table></td>
	<td ><table border="0" width="100%" cellspacing="5"><tr><td><html:text  style="width:150" property="userRole" disabled="true"/></td></tr></table></td>
	<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr>
    <% } %>

    <%if(!"admin".equalsIgnoreCase(form.getUserRole())){%>
    <%if("viewProfile".equalsIgnoreCase(form.getAction())||"init".equalsIgnoreCase(form.getAction())||"edit".equalsIgnoreCase(form.getAction()) ){%>
	<tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td height="10" class="maintext"><table border="0" width="100%" cellspacing="5"><tr><td>&nbsp;&nbsp;<bean:message key="account.type"/>:</td></tr></table></td>
	<td ><table border="0" width="100%" cellspacing="5"><tr><td><html:select  property="userRole" disabled="true">
	<html:optionsCollection  property="userRolesCollection" /></html:select>       </td></tr></table></td>
	<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr>
	<%} else {%>

	<tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td height="10" class="maintext"><table border="0" width="100%" cellspacing="5"><tr><td>&nbsp;&nbsp;<bean:message key="account.type"/>:</td></tr></table></td>
	<td ><table border="0" width="100%" cellspacing="5"><tr><td><html:select  property="userRole" disabled="false">
	<html:optionsCollection  property="userRolesCollection" /></html:select>       </td></tr></table></td>
	<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr>
	<%}%>
    <%}%>


	<tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td ></td>
	<td height="10"><table border="0" width="100%" cellspacing="5"><tr><td><input type="submit" value='<bean:message key="submit"/>'></td></tr></table></td>
	<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr>
    <tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td ><bean:message key="form.msg.mandatory"/></td>
    <td height="10" ><table border="0" width="100%" cellspacing="5"><tr><td></td></tr></table></td>
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
