<%@ page import="com.adsapient.api_impl.usermanagment.*,
                                      com.adsapient.util.link.*,
                                     com.adsapient.web.campain.upload.*" %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %> 
<%@ taglib uri='/WEB-INF/struts-bean.tld' prefix='bean' %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%BannerUploadFormAction form= (BannerUploadFormAction) request.getAttribute("bannerUploadActionForm");%>

<html:messages id="msg" message="true" header="messages.header" footer="messages.footer" >
 <bean:write name="msg" />
</html:messages>

 <html:form action="upload"   enctype="multipart/form-data">  
<html:hidden property="action"/> 
<html:hidden property="id"/> 
<tr><td>
	<table width="100%"cellspacing="0" cellpadding="0"><tr>
	<td><img src="images/table1.gif"></td>
	<td width="100%" class="tableheader"><bean:message key="banner.edit.title"/></td>
	<td><img src="images/table2.gif"></td>
	</tr></table>
</td></tr>

<tr><td>
	<table width="100%" cellspacing="0" cellpadding="0"><tr>
		
		<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
		<td><table width="100%" cellspacing="0" cellpadding="0">
		<tr>
	<td height="10" colspan="2" class="tabledata"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"> 
	
   
	<%=LinkHelper.getHrefByBannerId(form.getBannerId())%>

	</td></tr></table></td>	</tr>
	<tr>
		<td height="10" class="tabledata"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><nobr><bean:message key="size.name.text"/></nobr></td></tr></table></td>
		<td height="10" class="tabledata"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><html:select  property="sizeId" styleClass="defType"><html:optionsCollection property="sizesCollection" /></html:select></td></tr></table></td>
	</tr>
	<tr>
		<td height="10" class="tabledata"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><nobr><bean:message key="banner.active"/></nobr></td></tr></table></td>
		<td height="10" class="tabledata"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><html:select property="status"styleClass="defType"><html:optionsCollection property="statesCollection"/></html:select> </td></tr></table></td>
	</tr>
	<tr>
		<td height="10" class="tabledata"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><nobr><bean:message key="banner.url"/></nobr></td></tr></table></td> 
		<td height="10" class="tabledata"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext">
		<html:text  property="URL" style="width: 370px;"/></td></tr></table></td> 
	</tr>
	<tr>
		<td height="10" class="tabledata"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><nobr><bean:message key="banner.type.text"/></nobr></td></tr></table></td> 
		<td height="10" class="tabledata"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><html:select  property="typeId" styleClass="defType"><html:optionsCollection property="typesCollection" /></html:select></td></tr></table></td> 
	</tr>
	<tr>
		<td height="10" class="tabledata"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><nobr><bean:message key="startdate"/></nobr></td></tr></table></td> 
		<td height="10" class="tabledata"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><html:text property="startDate"/>&nbsp;<script language="JavaScript" type="text/javascript">
    
    </script></td></tr></table></td> 
	</tr>
	<tr>
		<td height="10" class="tabledata"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><nobr><bean:message key="enddate"/></nobr></td></tr></table></td> 
		<td height="10" class="tabledata"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><html:text property="endDate"/>&nbsp;<script language="JavaScript" type="text/javascript">
    
    </script></td></tr></table></td> 
	</tr>
	<tr>
		<td height="10"  class="maintext"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><bean:message key="banner.name"/>:</td></tr></table></td>
		<td height="10"  ><table border="0" width="100%" cellspacing="5"><tr><td ><html:text property="bannerName" size="50"/></td></tr></table></td>
	</tr>
	<tr>
		<td height="10"  class="maintext"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><bean:message key="alttext"/>:</td></tr></table></td>
		<td height="10"  ><table border="0" width="100%" cellspacing="5"><tr><td ><html:text property="altText" size="50"/></td></tr></table></td>
	</tr>
	<tr>
		<td height="10"  class="maintext"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><bean:message key="statusbartext"/>:</td></tr></table></td>
		<td height="10"  ><table border="0" width="100%" cellspacing="5"><tr><td ><html:text property="statusBartext" size="50"/></td></tr></table></td>
	</tr>
		
	<tr>
		<td height="10" class="tabledata"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><nobr><bean:message key="campaign"/></nobr></td></tr></table></td> 
		<td height="10" class="tabledata"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><html:select  property="campainId" styleClass="defType"><html:optionsCollection property="userCampainsCollection" /></html:select></td></tr></table></td> 
	</tr> 
	
	<tr>
	<td height="10" class="tabledata"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><nobr> <bean:message key="target.window"/></nobr></td></tr></table></td> 
	<td height="10" class="tabledata"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><html:select property="targetWindowId" styleClass="defType" onchange="targetSelect(bannerUploadActionForm,1);"><html:optionsCollection property="targetWindowCollection"/></html:select></td></tr></table></td>
	</tr>
		
	
	<tr>
	<td height="10" class="tabledata"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><nobr><bean:message key="inventory.type"/></nobr></td></tr></table></td> 
	<td height="10" class="tabledata"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><html:select property="placeTypeId" styleClass="defType" onchange="placeSelect(bannerUploadActionForm,1);"><html:optionsCollection property="placeTypeCollection"/></html:select></td></tr></table></td>
	
	</tr>
	
	
	<tr>
	<td height="10" class="tabledata"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><nobr><bean:message key="loading.type"/></nobr></td></tr></table></td> 
	<td height="10" class="tabledata"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><html:select property="loadingTypeId" styleClass="defType" onchange="loadingSelect(bannerUploadActionForm,1);"><html:optionsCollection property="loadingTypeCollection"/></html:select></td></tr></table></td>	
	</tr>
	<tr>
		<td height="10" class="tabledata"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><nobr><bean:message key="banner.weight"/></nobr></td></tr></table></td>
		<td height="10" class="tabledata"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><html:select property="bannerWeightId" styleClass="defType"><html:optionsCollection property="bannerWeightCollection"/></html:select></td></tr></table></td>	
	</tr>
	<tr>
		<td height="10" class="tabledata"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><nobr><bean:message key="banner.file"/></nobr></td></tr></table></td> 
		<td height="10" class="tabledata"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><html:file property="theFile" size="50"/></td></tr></table></td>
	</tr>		
	<tr>
		<td height="10" colspan="2" class="tabledata"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><html:submit /></td></tr></table></td>
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


