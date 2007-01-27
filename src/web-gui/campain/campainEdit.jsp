<%@ page import="com.adsapient.api_impl.advertizer.*,
                              com.adsapient.api_impl.usermanagment.*,
							  com.adsapient.util.Msg"%>
<%@ page import="com.adsapient.gui.forms.EditCampainActionForm"%>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %>
<%@ taglib uri='/WEB-INF/struts-bean.tld' prefix='bean' %>
<%@ page contentType="text/html;charset=UTF-8"%>

<%EditCampainActionForm form=(EditCampainActionForm) request.getAttribute("editCampainActionForm");%>
<%form.setHsr(request);%>
<%UserImpl user=(UserImpl) session.getAttribute("user");%>
<html:errors/>
<html:messages id="msg" message="true" header="messages.header" footer="messages.footer" >
 <bean:write name="msg" />
</html:messages>

<html:form action="campainEdit" >
<html:hidden property="campainId"/>
<html:hidden property="userId"/>
<html:hidden property="action"/>
<html:hidden property="defaultCampain"/>

<tr><td>
	<table width="100%"cellspacing="0" cellpadding="0"><tr>
	<td><img src="images/table1.gif"></td>
	<td width="100%" class="tableheader"><%if ("add".equals(form.getAction())) {
	 out.println(Msg.fetch(form.getAction()+".campaign.title", request));
	  } else {
	 out.println(Msg.fetch("edit.campaign.title",request)+form.getCampainId()); }%></td>
	<td><img src="images/table2.gif"></td>
	</tr></table>
</td></tr>
<tr><td>
	<table width="100%" cellspacing="0" cellpadding="0">
	

	<tr>
	<td width="5"  style="background-image: url(images/table4.gif);"></td>
	<td height="10" class="maintext"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><bean:message key="campaign.name"/>:&nbsp;&nbsp;<sup>*</sup></td></tr></table></td>
	<td height="10"  ><table border="0" width="100%" cellspacing="5"><tr><td ><html:text property="campainName" size="50" maxlength="40"/></td></tr></table></td>
	<td width="6"  style="background-image: url(images/table5.gif);"></td>
	</tr>
	
	<tr>
	<td width="5"  style="background-image: url(images/table4.gif);"></td>
	<td height="10" class="maintext"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><bean:message key="campaign.budget"/>:</td></tr></table></td>
	<td height="10"  ><table border="0" width="100%" cellspacing="5"><tr><td ><html:text property="budget" size="25" maxlength="7"/></td></tr></table></td>
	<td width="6"  style="background-image: url(images/table5.gif);"></td>
	</tr>
	

	<tr>
	<td width="5"  style="background-image: url(images/table4.gif);"></td>
	<td  class="maintext"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><bean:message key="startdate"/>:</td></tr></table></td>
	<td  ><table border="0" width="100%" cellspacing="5"><tr><td ><html:text readonly="true" property="startDate"/>&nbsp;<script language="JavaScript" type="text/javascript">
    
    </script></td></tr></table></td>
	<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr>

	<tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td height="10"  class="maintext"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><bean:message key="enddate"/>:</td></tr></table></td>
	<td height="10"  ><table border="0" width="100%" cellspacing="5"><tr><td ><html:text readonly="true" property="endDate"/>&nbsp;<script language="JavaScript" type="text/javascript">
    
    </script></td></tr></table></td>
	<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr>

    <%--
    <tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td height="10"  class="maintext"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><bean:message key="alttext"/>:</td></tr></table></td>
	<td height="10"  ><table border="0" width="100%" cellspacing="5"><tr><td ><html:text property="altText" size="50"/></td></tr></table></td>
	<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr>
	<tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td height="10"  class="maintext"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><bean:message key="statusbartext"/>:</td></tr></table></td>
	<td height="10"  ><table border="0" width="100%" cellspacing="5"><tr><td ><html:text property="statusBartext" size="50"/></td></tr></table></td>
	<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr>
	<%if (user.isOwnCampaignsAllow()){%>
	<tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td height="10"  class="maintext"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><bean:message key="own.campaigns.text.advertizer"/>:</td></tr></table></td>
	<td height="10"  ><table border="0" width="100%" cellspacing="5"><tr><td ><html:checkbox property="ownCampaigns"/></td></tr></table></td>
	<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr>
  <%}%>
	<tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td height="10"  class="maintext"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><bean:message key="targeturl"/>:</td></tr></table></td>
	<td height="10"  ><table border="0" width="100%" cellspacing="5"><tr><td ><html:text property="campainUrl" size="50"/></td></tr></table></td>
	<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr>
--%>
	<tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td height="10" class="maintext"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><bean:message key="status"/>:</td></tr></table></td>
	<td height="10"  ><table border="0" width="100%" cellspacing="5"><tr><td ><%=form.generateCampainStates()%></td></tr></table></td>
	<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr>
 <%--   
    
	<tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td height="10"  class="maintext"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><bean:message key="target.window"/></td></tr></table></td>
	<td height="10"  ><table border="0" width="100%" cellspacing="5"><tr><td ><html:select property="targetWindowId" styleClass="defType" onchange="targetSelect(editCampainActionForm,1);"><html:optionsCollection property="targetWindowCollection"/></html:select></td></tr></table></td>
	<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr>
		
	
	<tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td height="10"  class="maintext"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><bean:message key="inventory.type"/></td></tr></table></td>
	<td height="10"  ><table border="0" width="100%" cellspacing="5"><tr><td ><html:select property="placeTypeId" styleClass="defType" onchange="placeSelect(editCampainActionForm,1);"><html:optionsCollection property="placeTypeCollection"/></html:select></td></tr></table></td>
	<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr>
	
	
	<tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td height="10"  class="maintext"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><bean:message key="loading.type"/></td></tr></table></td>
	<td height="10"  ><table border="0" width="100%" cellspacing="5"><tr><td ><html:select property="loadingTypeId" styleClass="defType" onchange="loadingSelect(editCampainActionForm,1);"><html:optionsCollection property="loadingTypeCollection"/></html:select></td></tr></table></td>
	<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr>

	--%>
	
	<tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td ></td>
	<td height="10" ><table border="0" width="100%" cellspacing="5"><tr><td ><input type="submit" value='<%=Msg.fetch(form.getAction()+".campaign.button",request)%>'>
	<%if ("edit".equals(form.getAction())){%>
	&nbsp;&nbsp;&nbsp;<input type="button" value='<bean:message key="remove"/>'
	onclick="if( window.confirm('<%=Msg.fetch("delete.confirm",request)%>')) {document.location='<%=response.encodeURL("campainEdit.do?action=remove&campainId="+form.getCampainId())%>'}"> 
	<%}%>
	</td></tr></table></td>
	<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr>
    <tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td height="10" class="maintext"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><bean:message key="form.msg.mandatory"/></td></tr></table></td>
	<td height="10"  ><table border="0" width="100%" cellspacing="5"><tr><td ></td></tr></table></td>
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
