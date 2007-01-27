<%@ page import="com.adsapient.api_impl.filter.FiltersTemplate"%>
<%@ page import="com.adsapient.gui.forms.FiltersTemplateActionForm"%>
<%@ page import="com.adsapient.util.Msg"%>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %>
<%@ taglib uri='/WEB-INF/struts-bean.tld' prefix='bean' %>

<%FiltersTemplateActionForm form=(FiltersTemplateActionForm) request.getAttribute("filtersTemplateActionForm");%>

<html:messages id="msg" message="true" header="messages.header" footer="messages.footer" >
 <bean:write name="msg" />
</html:messages>

<html:form action="filtersTemplate">

<html:hidden property="templateAction"/>

<tr><td>
	<table width="100%"cellspacing="0" cellpadding="0"><tr>
	<td><img src="images/table1.gif"></td>
	<td width="100%" class="tableheader"><bean:message key="advertising.templates.list"/></td>
	<td><img src="images/table2.gif" alt=""></td>
	</tr></table>
</td></tr>
<tr><td>
	<table width="100%" cellspacing="0" cellpadding="0">
	
	<tr>
	<td width="5" style="background-image: url(images/table4.gif);"></td>
		<td><table border="0" style="background-color:#ffffff;" cellspacing="1" width="100%"><tr>
	<td  width="1%" class="tabletableheader"><bean:message key="id"/></td>
	<td   width="74%" class="tabletableheader"><bean:message key="template.name"/></td>
	<td  width="5%" class="tabletableheader" colspan="2"><bean:message key="actions"/> 
		</tr>
		
<logic:iterate  id="valueMy"  name="filtersTemplateActionForm" property="templateCollection">
<% FiltersTemplate fTemplate=(FiltersTemplate) valueMy;%>
		<tr>
	<td height="10" class="tabledata"><nobr><%=fTemplate.getTemplateId()%></nobr></td>
	<td height="10" class="tabledata"><nobr><%=fTemplate.getTemplateName()%></nobr></td>
	<td height="10" class="tabledata-c"><nobr><a class="tabledata" href="<%=response.encodeURL("filtersTemplate.do?dataSource=template&templateAction=view&templateId="+fTemplate.getTemplateId())%>"><img src="images/icons/edit.png" border="0" title=<bean:message key="edit"/>></a></nobr></td>
	<td height="10" class="tabledata-c"><nobr><a class="tabledata" href="<%=response.encodeURL("filtersTemplate.do?dataSource=template&templateAction=remove&templateId="+fTemplate.getTemplateId())%>" onClick="return window.confirm('<%=(Msg.fetch("delete.confirm",session)+" "+fTemplate.getTemplateId())%>');"><img src="images/icons/remove.png" border="0" title=<bean:message key="remove"/>></a></nobr></td>
		</tr>
	</logic:iterate>
		<tr>
	<td height="10"  colspan="8"  class="tabledata"><table border="0" width="100%" cellspacing="5">
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
	<td width="100%" style="background-image: url(images/table8.gif);"></td>
	<td><img src="images/table7.gif"></td>
	</tr></table>
</td></tr>
