<%@ page import="com.adsapient.api_impl.advertizer.*,
                              com.adsapient.util.Msg ,
                              com.adsapient.util.security.*" %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %>
<%@ taglib uri='/WEB-INF/struts-bean.tld' prefix='bean' %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ page contentType="text/html; charset=UTF-8" %> 

<html:errors/>
<html:messages id="msg" message="true" header="messages.header" footer="messages.footer" >
 <bean:write name="msg" />
</html:messages>

<html:form action="passReminder" >
<html:hidden property="action"/>

<tr><td>
	<table width="100%"cellspacing="0" cellpadding="0"><tr>
	<td><img src="images/table1.gif"></td>
	<td width="100%" class="tableheader"><bean:message key="login.forgotyourpassword"/></td>
	<td><img src="images/table2.gif"></td>
	</tr></table>
</td></tr>
<tr><td>
	<table width="100%"  cellspacing="0" cellpadding="0">

	<tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td height="10" colspan="2" class="maintext"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext">&nbsp;&nbsp;<bean:message key="login.forgotyourpassword.text"/></td></tr></table></td>
	<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr>
	
	<tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td height="10"  class="maintext"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext">&nbsp;&nbsp;<bean:message key="email"/>:&nbsp;&nbsp;<sup>*</sup></td></tr></table></td>
	<td ><html:text property="email"/></td>
	<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr>

	<tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td></td>
	<td height="10" ><table   border="0" width="100%" cellspacing="5"><tr><td><input type="submit" value='<bean:message key="submit"/>'></td></tr></table></td>
	<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr>
    <tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td><bean:message key="form.msg.mandatory"/></td>
	<td height="10" ><table   border="0" width="100%" cellspacing="5"><tr><td></td></tr></table></td>
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
