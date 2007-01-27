<%@ page import="com.adsapient.api_impl.resource.ResourceRepresentation"%>
<%@ page import="com.adsapient.util.Msg"%>
<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<html:form action="resource.do" enctype="multipart/form-data"> 
<html:hidden property="action"/>
<html:hidden property="userId"/>
<html:hidden property="orderId"/>
<tr><td>
	<table width="100%"cellspacing="0" cellpadding="0"><tr>
	<td><img src="images/table1.gif"></td>
	<td width="100%" class="tableheader"><bean:message key="resources.list.title"/></td>
	<td><img src="images/table2.gif"></td>
	</tr></table>
</td></tr>
<tr><td>
	<table width="100%" cellspacing="0" cellpadding="0">
	
	<tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
		<td><table border="0" style="background-color:#ffffff;" cellspacing="1" width="100%"><tr>
		<td height="10"  width="1%" class="tabletableheader"><a class="tabletableheader" href="<%=response.encodeURL("resource.do?orderId=resourceId")%>"><bean:message key="id"/></a></td>
		<td height="10"  width="40%" class="tabletableheader"><a class="tabletableheader" href="<%=response.encodeURL("resource.do?orderId=resourceName")%>"><bean:message key="resource.name"/></a></td>
		<td height="10"  width="10%" class="tabletableheader"><a class="tabletableheader" href="<%=response.encodeURL("resource.do?orderId=resSize")%>"><bean:message key="resource.size"/></a></td>  
		<td height="10"  width="10%" class="tabletableheader"><a class="tabletableheader" href="<%=response.encodeURL("resource.do?orderId=typeId")%>"><bean:message key="resource.type"/></a></td>  
		<td height="10"  width="5%" class="tabletableheader" colspan="2"><bean:message key="actions"/></td> 
	</tr>
	
	 <logic:iterate  id="userResource"  name="resourceUploadForm" property="userResources">
	 <%ResourceRepresentation res= (ResourceRepresentation) userResource;%>
	<tr>
		<td height="10" class="tabledata"><nobr><%=res.getResourceId()%></nobr></td>
		<td height="10" class="tabledata"><nobr><%=res.getResourceName()%></nobr></td>
		<td height="10" class="tabledata-c"><nobr><%=res.getResSize()%></nobr></td>
		<td height="10" class="tabledata-c"><nobr><%=res.getContentType()%></nobr></td>
		<td height="10" class="tabledata-c"><nobr><a class="tabledata" href="<%=response.encodeURL(res.getSettingsHref())%>"><img src="images/icons/edit.png" border="0" title=<bean:message key="edit"/>></a></nobr></td> 
		<td height="10" class="tabledata-c"><nobr><%if (res.getDeleteHref()!=null){%><a class="tabledata" href="<%=response.encodeURL(res.getDeleteHref())%>" onClick="return window.confirm('<%=(Msg.fetch("delete.confirm",session)+" "+res.getResourceName())%>');"><img src="images/icons/remove.png" border="0" title=<bean:message key="remove"/>></a><%}%></nobr></td>
	</tr>
	</logic:iterate>	
	
		<tr>
	<td height="10"  colspan="12"  class="tabledata"><table border="0" width="100%" cellspacing="7">
		<tr><td class="tabledata"><input type="submit" value='<bean:message key="add"/>'></td>
		</tr></table></td>
		</tr>		
		</table></td>
	<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr>
	</table>
</td></tr>
</html:form>

<tr><td>
	<table width="100%" cellspacing="0" cellpadding="0"><tr>
	<td><img src="images/table6.gif"></td>
	<td width="100%" heigh="10" style="background-image: url(images/table8.gif);"></td>
	<td><img src="images/table7.gif"></td>
	</tr></table>
</td></tr>
