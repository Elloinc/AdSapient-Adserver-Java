<%@ page import="com.adsapient.util.jsp.*" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri='/WEB-INF/struts-bean.tld' prefix='bean' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %> 

<tr><td>
	<table width="100%"cellspacing="0" cellpadding="0"><tr>
	<td><img src="images/table1.gif"></td>
	<td width="100%" class="tableheader"><nobr><bean:message key="authorization"/></nobr></td>
     <%--() commented and added below for help link--%>
    <td><a target="_blank" href="" onclick="helpAction('authorization');"><img border="0" src="images/icons/thelp.png" title=<bean:message key="context.help"/>></a></td>    
    <%--<td><a href="#" onclick="helpAction('authorization');"><img border="0" src="images/icons/thelp.png" title=<bean:message key="context.help"/>></a></td>--%>
	</tr></table>
</td></tr>
<tr><td>
	<table  width="100%" cellspacing="0" cellpadding="0">
	
	<tr>
	<td width="5" heigh="10" class="leftborder"></td>
	<td  class="menuitem" height="10" colspan="2" ><a class="menuitem" href="<%=response.encodeURL("verification.do?type=user")%>">&nbsp;&nbsp;<bean:message key="user.verify"/>
	<%=VerifyingHelper.getCount(VerifyingHelper.USER)%></a></td>
	<td width="6" heigh="10" class="rightborder"></td>
	</tr>	  
	<%--<tr>
	<td width="5" heigh="10" class="leftborder"></td>
	<td height="10" colspan="2" ><a class="menuitem" href="<%=response.encodeURL("verification.do?type=campaign")%>">&nbsp;&nbsp;<bean:message key="campain.verify"/>
	<%=VerifyingHelper.getCount(VerifyingHelper.CAMPAIGN)%></a></td>
	<td width="6" heigh="10" class="rightborder"></td>
	</tr>
	<tr>
	<td width="5" heigh="10" class="leftborder"></td>
	<td height="10" colspan="2" ><a class="menuitem" href="<%=response.encodeURL("verification.do?type=site")%>">&nbsp;&nbsp;<bean:message key="site.verify"/>
	<%=VerifyingHelper.getCount(VerifyingHelper.SITE)%></a></td>
	<td width="6" heigh="10" class="rightborder"></td>
	</tr>--%>	 
	
		
	
	
	</table>
</td></tr>

 <tr><td>
	<table width="100%" cellspacing="0" cellpadding="0"><tr>
	<td><img src="images/table6.gif"></td>
	<td width="100%" heigh="10" style="background-image: url(images/table8.gif);"></td>
	<td><img src="images/table7.gif"></td>
	</tr></table>
</td></tr>



