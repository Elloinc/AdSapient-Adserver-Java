<%@ page import="com.adsapient.util.Msg"%>
<%@ page import="com.adsapient.gui.forms.PluginManagerActionForm"%>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %>
<%@ taglib uri='/WEB-INF/struts-bean.tld' prefix='bean' %>
<%@ page contentType="text/html;charset=UTF-8"%>

<%PluginManagerActionForm form=(PluginManagerActionForm) request.getAttribute("pluginManagerActionForm");%>


<html:errors/>
<html:messages id="msg" message="true" header="messages.header" footer="messages.footer" >
 <bean:write name="msg" />
</html:messages>

<html:form action="plugin" >
<html:hidden property="id"/>
<html:hidden property="action"/>


<tr><td>
	<table width="100%"cellspacing="0" cellpadding="0"><tr>
	<td><img src="images/table1.gif"></td>
	<td width="100%" class="tableheader"><%=Msg.fetch(form.getAction()+".plugin.box",request)%><%="edit".equals(form.getAction())?form.getId().toString():""%></td>
	<td><img src="images/table2.gif"></td>
	</tr></table>
</td></tr>
<tr><td>
	<table width="100%" cellspacing="0" cellpadding="0">
	

	<tr>
	<td width="5"  style="background-image: url(images/table4.gif);"></td>
	<td height="10" class="maintext"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><bean:message key="class.name"/>:</td></tr></table></td>
	<td height="10"  ><table border="0" width="100%" cellspacing="5"><tr><td ><html:text property="className" styleId="nameId" size="45"/></td></tr></table></td>
	<td width="6"  style="background-image: url(images/table5.gif);"></td>
	</tr>
	<tr>
	<td width="5"  style="background-image: url(images/table4.gif);"></td>
	<td height="10" class="maintext"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><bean:message key="name"/>:</td></tr></table></td>
	<td height="10"  ><table border="0" width="100%" cellspacing="5"><tr><td ><html:text property="pluginName" styleId="nameId" size="45"/></td></tr></table></td>
	<td width="6"  style="background-image: url(images/table5.gif);"></td>
	</tr>

	


	
	
	
	<tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td ></td>
	<td height="10" ><table border="0" width="100%" cellspacing="5"><tr><td ><input type="submit"
	value='<%=Msg.fetch(form.getAction()+".plugin.button",request)%>' onsubmit=""></td></tr></table></td>
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
