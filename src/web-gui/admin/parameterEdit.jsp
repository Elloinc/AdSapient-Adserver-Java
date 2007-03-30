<%@ page import="com.adsapient.gui.forms.ParameterForm"%>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %>
<%@ taglib uri='/WEB-INF/struts-bean.tld' prefix='bean' %>
<%@ page contentType="text/html; charset=UTF-8" %>

<html:errors/>
<html:messages id="msg" message="true" header="messages.header" footer="messages.footer" >
 <bean:write name="msg" />
</html:messages>
<%ParameterForm form= (ParameterForm) request.getAttribute("parameterForm");
%>
<html:form action="parameter.do"   enctype="multipart/form-data">
<html:hidden property="action"/>
<html:hidden property="parameterId"/>
    <%if("add".equalsIgnoreCase(form.getAction())){%>
        <tr><td>
	    <table width="100%"cellspacing="0" cellpadding="0"><tr>
	    <td><img src="images/table1.gif"></td>
	    <td width="100%" class="tableheader"><bean:message key="parameter.add.title"/></td>
	    <td><img src="images/table2.gif"></td>
	    </tr></table>
        </td></tr>
   <%}else{%>
        <tr><td>
	    <table width="100%"cellspacing="0" cellpadding="0"><tr>
	    <td><img src="images/table1.gif"></td>
	    <td width="100%" class="tableheader"><bean:message key="parameter.edit.title"/></td>
	    <td><img src="images/table2.gif"></td>
	    </tr></table>
        </td></tr>
    <%}%>


<tr><td>
	<table width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
		<td>
	<table width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td height="10" class="tabledata"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><nobr><bean:message key="parameter.label"/>:</nobr>&nbsp;&nbsp;<sup>*</sup></td></tr></table></td>
		<td height="10" class="tabledata"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><html:text property="label" size="50" maxlength="50"/></td></tr></table></td>
	</tr>
	<tr>
		<td height="10" class="tabledata"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><nobr><bean:message key="parameter.name"/>:</nobr>&nbsp;&nbsp;<sup>*</sup></td></tr></table></td>
		<td height="10" class="tabledata"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><html:text property="name" size="50" maxlength="50"/></td></tr></table></td>
	</tr>
	<tr>
		<td height="10" class="tabledata" valign="top"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><nobr><bean:message key="parameter.type"/>:</nobr>&nbsp;&nbsp;<sup>*</sup></td></tr></table></td>
		<td height="10" class="tabledata"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><html:radio  property="type" value="1"/>&nbsp;&nbsp;<bean:message key="parameter.string.type"/></td></tr>
			<tr><td class="maintext"><html:radio  property="type" value="2"/>&nbsp;&nbsp;<bean:message key="parameter.predefined.type"/></td></tr>
		</table></td>
	</tr>

	<tr>
		<td height="10" class="tabledata"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><nobr><bean:message key="parameter.predefined.values"/>:</nobr>&nbsp;&nbsp;<sup>*</sup></td></tr></table></td>
		<td height="10" class="tabledata"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><html:text property="value" size="75" maxlength="20"/></td></tr></table></td>
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


