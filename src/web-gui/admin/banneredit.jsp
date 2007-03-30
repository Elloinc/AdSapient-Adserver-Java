<%@ page import="com.adsapient.gui.forms.DefaultBannerActionForm"%>
<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<html:errors/>
<html:messages id="msg" message="true" header="messages.header" footer="messages.footer" >
 <bean:write name="msg" />
</html:messages>
<%DefaultBannerActionForm form =(DefaultBannerActionForm) request.getAttribute("defaultBannerActionForm");%>
<html:form action="defaultBanners.do"  enctype="multipart/form-data"> 
<html:hidden property="action"/>
<html:hidden property="id"/>
<tr><td>
	<table width="100%"cellspacing="0" cellpadding="0"><tr>
	<td><img src="images/table1.gif"></td>
	<td width="100%" class="tableheader"><bean:message key="admin.editbanner" /></td>
	<td><img src="images/table2.gif"></td>
	</tr></table>
</td></tr>
<tr><td>
	<table width="100%" cellspacing="0" cellpadding="0">
	
<%if(!"".equalsIgnoreCase(form.getFileName())) {%>
	<tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td height="10"  ><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><%=LinkHelper.getHrefBySizeId(form.getId())%></td></tr></table></td>
	<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr> 
	<%}%>
	
	<tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td height="10"  ><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"  width=150><bean:message key="alttext"/></td><td><html:text property="altText" size="50" maxlength="50"/></td></tr></table></td>
	<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr>
	<tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td height="10"  ><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"  width=150><bean:message key="statusbartext"/></td><td><html:text property="statusBartext" size="50" maxlength="50"/></td></tr></table></td>
	<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr>
	<tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td height="10"  ><table border="0" width="100%" cellspacing="5"><tr><td class="maintext" width=150><bean:message key="banner.url"/>&nbsp;&nbsp;<sup>*</sup></td><td><html:text property="URL" size="50" maxlength="50"/></td></tr></table></td>
	<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr>

	<tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td height="10"  ><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"  width=150><bean:message key="size"/></td><td><html:select  property="sizeId"  styleClass="defSize"><html:optionsCollection property="sizesCollection" /></html:select></td></tr></table></td>
	<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr>
	
	<tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td height="10"  ><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"  width=150><bean:message key="banner.type.text"/></td><td><html:select  property="typeId"  styleClass="defType"><html:optionsCollection property="typesCollection" /></html:select></td></tr></table></td>
	<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr>
	
	<tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td height="10"  ><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><bean:message key="uploadfile"/>&nbsp;&nbsp;<sup>*</sup></td></tr></table></td>
	<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr>
	<tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td height="10"  ><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><html:file property="file" />&nbsp;&nbsp;&nbsp;<html:submit /></td></tr></table></td>
	<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr>
    <tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td height="10"  ><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><bean:message key="form.msg.mandatory"/></td></tr></table></td>
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
