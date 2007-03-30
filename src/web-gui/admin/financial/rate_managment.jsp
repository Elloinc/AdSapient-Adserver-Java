
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %>
<%@ taglib uri='/WEB-INF/struts-bean.tld' prefix='bean' %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix="tiles" %>
<%@ page contentType="text/html; charset=UTF-8" %> 


<html:errors/>
<html:messages id="msg" message="true" header="messages.header" footer="messages.footer" >
 <bean:write name="msg" />
</html:messages>

<html:form action="rateManagement">
<html:hidden property="action"/>
<html:hidden property="rateId"/>
	<%// AccountProfileActionForm form = (AccountProfileActionForm) request.getAttribute("accountProfileActionForm");%>

<tr><td>
	<table width="100%"cellspacing="0" cellpadding="0"><tr>
	<td><img src="images/table1.gif"></td>
	<td width="100%" class="tableheader"><bean:message key="rate.managment"/></td>
	<td><img src="images/table2.gif"></td>
	</tr></table>
</td></tr>
<tr><td>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">

	
   <tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td height="10"  class="maintext"><table border="0" width="100%" cellspacing="5"><tr><td>&nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="campain.cpm"/>:</td></tr></table></td>
	<td ><table border="0" width="100%" cellspacing="5"><tr><td><html:text property="cpmRate"/></td></tr></table></td>
	<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr>
	<tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td height="10"  class="maintext"><table border="0" width="100%" cellspacing="5"><tr><td>&nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="campain.cpc"/>:</td></tr></table></td>
	<td ><table border="0" width="100%" cellspacing="5"><tr><td><html:text property="cpcRate"/></td></tr></table></td>
	<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr>
	 
	 <tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td height="10"  class="maintext"><table border="0" width="100%" cellspacing="5"><tr><td>&nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="campain.cpl"/>:</td></tr></table></td>
	<td ><table border="0" width="100%" cellspacing="5"><tr><td><html:text property="cplRate"/></td></tr></table></td>
	<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr>
	<tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td height="10"  class="maintext"><table border="0" width="100%" cellspacing="5"><tr><td>&nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="campain.cps"/>:</td></tr></table></td>
	<td ><table border="0" width="100%" cellspacing="5"><tr><td><html:text property="cpsRate"/></td></tr></table></td>
	<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr>

	<tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td ><table border="0" width="100%" cellspacing="5"><tr><td> <input type="submit" value='<bean:message key="submit"/>'></td></tr></table></td>
	<td height="10" ></td>
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
