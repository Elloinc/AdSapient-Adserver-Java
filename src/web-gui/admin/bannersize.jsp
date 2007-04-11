<%@ page import="com.adsapient.gui.forms.BannerSizeActionForm"%>
<%@ page import="org.springframework.context.ApplicationContext"%>
<%@ page import="com.adsapient.gui.ContextAwareGuiBean"%>
<%@ page import="com.adsapient.shared.service.I18nService"%>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %>
<%@ taglib uri='/WEB-INF/struts-bean.tld' prefix='bean' %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%BannerSizeActionForm form =(BannerSizeActionForm) request.getAttribute("bannerSizeActionForm");%>
<html:errors/>
<html:messages id="msg" message="true" header="messages.header" footer="messages.footer" >
 <bean:write name="msg" />
</html:messages>

<html:form action="bannerSize">
<html:hidden property="action"/>
<html:hidden property="id"/>    
<tr><td>
	<table width="100%"cellspacing="0" cellpadding="0"><tr>
	<td><img src="images/table1.gif"></td>
	<td width="100%" class="tableheader">
    <%if("add".equalsIgnoreCase(form.getAction())){%>
        <bean:message key="bannerSize.add.title"/>
    <%}else{%>
        <bean:message key="bannerSize.edit.title"/>
    <%}%>

    </td>
	<td><img src="images/table2.gif"></td>
	</tr></table>
</td></tr>

<tr><td>
	<table width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
		<td>
	<table width="100%" cellspacing="0" cellpadding="0">
    <tr>
		<td height="10" class="tabledata"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><nobr><bean:message key="width"/></nobr>&nbsp;&nbsp;<sup>*</sup></td></tr></table></td>
		<td height="10" class="tabledata"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><html:text property="width" maxlength="10"/></td></tr></table></td>
	</tr>
    <tr>
		<td height="10" class="tabledata"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><nobr><bean:message key="height"/></nobr>&nbsp;&nbsp;<sup>*</sup></td></tr></table></td>
		<td height="10" class="tabledata"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><html:text property="height" maxlength="10"/></td></tr></table></td>
	</tr>
	<tr>
		<td height="10" class="tabledata"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><nobr><bean:message key="max.banner.size"/></nobr>&nbsp;&nbsp;<sup>*</sup></td></tr></table></td>
		<td height="10" class="tabledata"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><html:text property="maxsize" maxlength="10"/></td></tr></table></td>
	</tr>
	<tr>
		<td height="10" colspan="2" class="tabledata"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><html:submit /></td></tr></table></td>
	</tr>
    <tr>
        <td  height="10" colspan="2" class="tabledata"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><bean:message key="form.msg.mandatory"/></td></tr></table></td>
    </tr>

    </table></td>
	<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>

	</tr></table>
</td></tr>

<tr><td>
	<table width="100%" cellspacing="0" cellpadding="0"><tr>
	<td><img src="images/table6.gif"></td>
	<td width="100%" heigh="10" style="background-image: url(images/table8.gif);"></td>
	<td><img src="images/table7.gif"></td>
	</tr></table>
</td></tr>
</html:form>
