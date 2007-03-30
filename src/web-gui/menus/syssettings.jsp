<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri='/WEB-INF/struts-bean.tld' prefix='bean' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %> 

<tr><td>
	<table width="100%"cellspacing="0" cellpadding="0"><tr>
	<td><img src="images/table1.gif"></td>
	<td width="100%" class="tableheader"><nobr><bean:message key="syssettings.title"/></nobr></td>
    <%--() commented and added below for help link--%>
    <%--<td><a href="#" onclick="helpAction('syssettings');"><img border="0" src="images/icons/thelp.png" title=<bean:message key="context.help"/>></a></td>--%>
    <td><a target="_blank" href="" onclick="helpAction('syssettings');"><img border="0" src="images/icons/thelp.png" title=<bean:message key="context.help"/>></a></td>
	</tr></table>
</td></tr>
<tr><td>
	<table  width="100%" cellspacing="0" cellpadding="0">

	<tr>
	<td width="5" heigh="10" class="leftborder"></td>
	<td  class="menuitem" height="10" colspan="2" ><a class="menuitem" href="<%=response.encodeURL("adminTuning.do?entity=categorys")%>">&nbsp;&nbsp;<bean:message key="syssettings.categories"/></a></td>
	<td width="6" heigh="10" class="rightborder"></td>
	</tr>	

	<tr>
	<td width="5" heigh="10" class="leftborder"></td>
	<td  class="menuitem" height="10" colspan="2" ><a class="menuitem" href="<%=response.encodeURL("adminTuning.do?entity=positions")%>">&nbsp;&nbsp;<bean:message key="syssettings.positions"/></a></td>
	<td width="6" heigh="10" class="rightborder"></td>
	</tr>	
	
	<tr>
	<td width="5" heigh="10" class="leftborder"></td>
	<td height="10" colspan="2" ><a class="menuitem" href="<%=response.encodeURL("bannerSize.do")%>">&nbsp;&nbsp;<bean:message key="syssettings.bannersizes"/></a></td>
	<td width="6" heigh="10" class="rightborder"></td>
	</tr>	
	
	<tr>
	<td width="5" heigh="10" class="leftborder"></td>
	<td height="10" colspan="2" ><a class="menuitem" href="<%=response.encodeURL("defaultBanners.do")%>">&nbsp;&nbsp;<bean:message key="syssettings.defaultbanners"/></a></td>
	<td width="6" heigh="10" class="rightborder"></td>
	</tr>	
	
	
	
	
	<%--<%=response.encodeURL("adminOptions.do")%>--%>
	
        
    
	
	
	<tr>
	<td width="5" heigh="10" class="leftborder"></td>
	<td height="10" colspan="2" ><a class="menuitem" href="<%=response.encodeURL("adminOptions.do?action=default")%>">
        &nbsp;&nbsp;<bean:message key="admin.defaultsyssettings"/>
    </a></td>
	<td width="6" heigh="10" class="rightborder"></td>
	</tr>
	
	
	
	<%--<%=response.encodeURL("plugin.do")%>--%>
	
        
    
	
	
	
	<tr>
		<td width="5" heigh="10" class="leftborder"></td>
		<td height="10" colspan="2" ><a class="menuitem" href="<%=response.encodeURL("parameter.do")%>">&nbsp;&nbsp;<bean:message key="syssettings.parameters"/></a></td>
		<td width="6" heigh="10" class="rightborder"></td>
	</tr>
	
		
		
		<%--<%=response.encodeURL("resource.do")%>--%>
		
            
        
		
	
	</table>
</td></tr>

 <tr><td>
	<table width="100%" cellspacing="0" cellpadding="0"><tr>
	<td><img src="images/table6.gif"></td>
	<td width="100%" heigh="10" style="background-image: url(images/table8.gif);"></td>
	<td><img src="images/table7.gif"></td>
	</tr></table>
</td></tr>




