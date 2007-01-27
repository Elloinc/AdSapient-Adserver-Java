<%@ page import="com.adsapient.api_impl.usermanagment.UserImpl"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri='/WEB-INF/struts-bean.tld' prefix='bean' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %>
<%UserImpl user1 = (UserImpl) request.getSession().getAttribute("user");%>

<tr><td>
	<table width="100%"cellspacing="0" cellpadding="0"><tr>
	<td><img src="images/table1.gif"></td>
	<td width="100%" class="tableheader"><nobr><bean:message key="miscellaneous.title"/></nobr></td>
    <%--()added below for help link--%>
    <%if("admin".equalsIgnoreCase(user1.getRole())){%>
             <td><a target="_blank" href="" onclick="helpAction('miscellaneous');"><img border="0" src="images/icons/thelp.png" title=<bean:message key="context.help"/>></a></td>
        <%}%>
        <%if("advertiser".equalsIgnoreCase(user1.getRole())){%>
            <td><a target="_blank" href="" onclick="helpAction('miscellaneous');"><img border="0" src="images/icons/thelp.png" title=<bean:message key="context.help"/>></a></td>
        <%}%>
        <%if("publisher".equalsIgnoreCase(user1.getRole())){%>
            <td><a target="_blank" href="" onclick="helpAction('miscellaneous');"><img border="0" src="images/icons/thelp.png" title=<bean:message key="context.help"/>></a></td>
        <%}%>
    <%--<td><a href="<bean:message key="miscellaneous.help"/>" onclick="helpAction('miscellaneous');"><img border="0" src="images/icons/thelp.png" title=<bean:message key="context.help"/>></a></td>--%>
	</tr></table>
</td></tr>
<tr><td>
	<table  width="100%" cellspacing="0" cellpadding="0">
	
	<tr>
	<td width="5" heigh="10" class="leftborder"></td>
        <%--()added below for help link--%>
        <%if("admin".equalsIgnoreCase(user1.getRole())){%>
             <td height="10" colspan="2" ><a class="menuitem" target="_blank" href="">&nbsp;&nbsp;<bean:message key="newuser.onlinemanual"/></a></td>
        <%}%>
        <%if("advertiser".equalsIgnoreCase(user1.getRole())){%>
            <td height="10" colspan="2" ><a class="menuitem" target="_blank" href="">&nbsp;&nbsp;<bean:message key="newuser.onlinemanual"/></a></td>
        <%}%>
        <%if("publisher".equalsIgnoreCase(user1.getRole())){%>
            <td height="10" colspan="2" ><a class="menuitem" target="_blank" href="">&nbsp;&nbsp;<bean:message key="newuser.onlinemanual"/></a></td>
        <%}%>

    <%--<td height="10" colspan="2" ><a class="menuitem" target="_blank" href="http://adsapient.com">&nbsp;&nbsp;<bean:message key="newuser.onlinemanual"/></a></td>--%>
	<td width="6" heigh="10" class="rightborder"></td>
	</tr>	
	
	<tr>
	<td width="5" heigh="10" class="leftborder"></td>
	<td height="10" colspan="2" ><a class="menuitem" href="<%=response.encodeURL("contact.do")%>">&nbsp;&nbsp;<bean:message key="newuser.contact"/></a></td>
	<td width="6" heigh="10" class="rightborder"></td>
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




