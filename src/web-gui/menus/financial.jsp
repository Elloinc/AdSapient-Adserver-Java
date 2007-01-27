<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri='/WEB-INF/struts-bean.tld' prefix='bean' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %>

<tr><td>
	<table width="100%"cellspacing="0" cellpadding="0"><tr>
	<td><img src="images/table1.gif"></td>
	<td width="100%" class="tableheader"><nobr><bean:message key="financials.managment"/></nobr></td>
    <%--() commented and added below for help link--%>
    <td><a target="_blank" href="" onclick="helpAction('financials.management');"><img border="0" src="images/icons/thelp.png" title=<bean:message key="context.help"/>></a></td>
        <%--<td><a href="#" onclick="helpAction('financials.management');"><img border="0" src="images/icons/thelp.png" title=<bean:message key="context.help"/>></a></td>--%>
	</tr></table>
</td></tr>
<tr><td>
	<table  width="100%" cellspacing="0" cellpadding="0">

	<tr>
	<td width="5" heigh="10" class="leftborder"></td>
	<td height="10" colspan="2" ><a class="menuitem" href="<%=response.encodeURL("financialManagement.do?action=default")%>">&nbsp;&nbsp;<bean:message key="default.system.rates"/></a></td>
	<td width="6" heigh="10" class="rightborder"></td>
	</tr>


	<tr>
	<td width="5" heigh="10" class="leftborder"></td>
	<td height="10" colspan="2" ><a class="menuitem" href="<%=response.encodeURL("viewAccount.do?action=system")%>">&nbsp;&nbsp;<bean:message key="system.account"/></a></td>
	<td width="6" heigh="10" class="rightborder"></td>
	</tr>

	
	
	
	<%--<%=response.encodeURL("billing.do?action=system&type=system")%>--%>
	
        
    
	
	


	</table>
</td></tr>

 <tr><td>
	<table width="100%" cellspacing="0" cellpadding="0"><tr>
	<td><img src="images/table6.gif"></td>
	<td width="100%" heigh="10" style="background-image: url(images/table8.gif);"></td>
	<td><img src="images/table7.gif"></td>
	</tr></table>
</td></tr>



