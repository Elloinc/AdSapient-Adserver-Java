<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='tiles' %>
<%@ taglib uri='/WEB-INF/struts-bean.tld' prefix='bean' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %> 

<html:form action="login" focus="login">
<html:hidden property="action"/>	
<tr><td>
	<table width="100%"cellspacing="0" cellpadding="0"><tr>
	<td><img src="images/table1.gif"></td>
	<td width="100%" class="tableheader"><bean:message key="login.header"/></td>
	<td><a href="#" onclick="helpAction('login');"><img border="0" src="images/icons/thelp.png" title=<bean:message key="context.help"/>></a></td>
	</tr></table>
</td></tr>
<tr><td>

	<table border="0"  width="100%" cellspacing="0" cellpadding="0">
	
	<tr>
	<td width="5" heigh="10" class="leftborder"><img src="images/table4.gif"></td>
	<td width="45%" align="left" class="menuitem">&nbsp;&nbsp;<bean:message key="login.username"/>:</td>
	<td align="left"><table border="0" width="100%" cellspacing="2"><tr><td class="menuitem"><nobr><html:text size="25" maxlength="25"  style="width:100" property="login"/>&nbsp;&nbsp;</nobr></td></tr></table></td>
	<td width="6" heigh="10" class="rightborder"><img src="images/table5.gif"></td>
	</tr>

	<tr>
	<td width="5" heigh="10" class="leftborder"></td>
	<td align="left" class="menuitem">&nbsp;&nbsp;<bean:message key="login.password"/>:</td>
	<td class="menuitem" align="left"><table border="0" width="100%" cellspacing="2"><tr><td><nobr><html:password size="25" style="width:100" maxlength="25" property="password"/>&nbsp;&nbsp;</nobr></td></tr></table></td>
	<td width="6" heigh="10" class="rightborder"></td>
	</tr>

	<tr>
	<td width="5" heigh="10" class="leftborder"></td>
	<td class="menuitem" align="left" colspan="2"><table border="0" width="100%" cellspacing="2"><tr><td><nobr>&nbsp;&nbsp;<bean:message key="login.automatically"/>&nbsp;&nbsp;&nbsp;<html:checkbox property="automaticallyLogin"/></nobr></td></tr></table></td>
	<td width="6" heigh="10" class="rightborder"></td>
	</tr>
	
	<tr>
	<td width="5" heigh="10" class="leftborder"></td>
	<td class="menuitem"></td>
	<td class="menuitem"><table border="0" width="100%" cellspacing="2"><tr><td><input type="submit" value='<bean:message key="login"/>'></td></tr></table></td>
	<td width="6" heigh="10" class="rightborder"></td>
	</tr>	
	
	<tr>
	<td width="5" heigh="10" class="leftborder"></td>
	<td colspan="2" colspan="2" class="menuitem"><a class="menuitem" href="<%=response.encodeURL("passReminder.do")%>">&nbsp;&nbsp;<bean:message key="login.forgotyourpassword"/></a></td>
	<td width="6" heigh="10" class="rightborder"></td>
	</tr>	

 </html:form>	
	</table>
</td></tr>
 <tr><td>
	<table width="100%" cellspacing="0" cellpadding="0"><tr>
	<td><img src="images/table6.gif"></td>
	<td width="100%" heigh="10" style="background-image: url(images/table8.gif);"></td>
	<td><img src="images/table7.gif"></td>
	</tr></table>
</td></tr>


