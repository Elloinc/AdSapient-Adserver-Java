<%@	page import="com.adsapient.shared.service.I18nService"%>
<%@ page import="com.adsapient.gui.forms.ViewAccountActionForm"%>
<%@ page import="org.springframework.context.ApplicationContext"%>
<%@ page import="com.adsapient.gui.ContextAwareGuiBean"%>
<%@ taglib uri='/WEB-INF/struts-bean.tld' prefix='bean' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %> 

<html:errors/>
<html:messages id="msg" message="true" header="messages.header" footer="messages.footer" >
 <bean:write name="msg" />
</html:messages>
<% ApplicationContext ctx = ContextAwareGuiBean.getContext();%>
<% I18nService i18nService = (I18nService) ctx.getBean("i18nService");%>

<html:form action="viewAccount">
<html:hidden property="action"/>
<html:hidden property="userId"/>
<html:hidden property="header"/>
<html:hidden property="currentBalanceName"/>
	<%ViewAccountActionForm form=(ViewAccountActionForm) request.getAttribute("viewAccountActionForm");%>

<tr><td>
	<table width="100%"cellspacing="0" cellpadding="0"><tr>
	<td><img src="images/table1.gif"></td>
	<td width="100%" class="tableheader"><%=form.getHeader()%></td>
	<td><img src="images/table2.gif"></td>
	</tr></table>
</td></tr>
<tr><td>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">

	
	<tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td height="10"  class="maintext"><table border="0" width="100%" cellspacing="5"><tr><td>&nbsp;&nbsp;<%=i18nService.fetch(form.getCurrentBalanceName(),request)%>:</td></tr></table></td>
	<td ><table border="0" width="100%" cellspacing="5"><tr><td> <html:text property="money"/> </td></tr></table></td>
	<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr>
	 
	
	<tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td ></td>
	<td height="10" ><table border="0" width="100%" cellspacing="5"><tr><td><input type="submit" value='<bean:message key="submit"/>'> </td></tr></table></td>
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
