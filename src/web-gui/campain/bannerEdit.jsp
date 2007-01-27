<%@ page import="com.adsapient.api_impl.usermanagment.UserImpl,
                 com.adsapient.util.Msg,
                 com.adsapient.util.link.LinkHelper,
                 com.adsapient.gui.forms.BannerUploadFormAction" %>
<%@ page import="org.springframework.context.ApplicationContext"%>
<%@ page import="com.adsapient.gui.ContextAwareGuiBean"%>
<%@ page import="com.adsapient.shared.service.LinkHelperService"%>
<%@ page import="com.adsapient.api_impl.share.Type"%>
<%@ page import="com.adsapient.api_impl.advertizer.BannerImpl"%>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %>
<%@ taglib uri='/WEB-INF/struts-bean.tld' prefix='bean' %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html:errors/>
<html:messages id="msg" message="true" header="messages.header" footer="messages.footer" >
 <bean:write name="msg" />
</html:messages>
<% ApplicationContext ctx = ContextAwareGuiBean.ctx;%>
<% LinkHelperService lhs = (LinkHelperService) ctx.getBean("linkHelperService");%>
<%BannerUploadFormAction form = (BannerUploadFormAction) request.getAttribute("bannerUploadActionForm");%>
<%form.setHsr(request);%>
<%UserImpl user = (UserImpl) session.getAttribute("user"); %>
<html:form action="upload" enctype="multipart/form-data">
<html:hidden property="action"/>
<html:hidden property="bannerId"/>
<html:hidden property="userId"/>
<html:hidden property="file"/>
<html:hidden property="contentType"/>


<html:hidden property="campainId"/>
<tr><td>
    <table width="100%" cellspacing="0" cellpadding="0"><tr>
        <td><img src="images/table1.gif"></td>
        <td width="100%" class="tableheader"><%if (form.getBannerId() != null) {
            if(form.getBannerId() != 0){
            	if (form.getStatus() == BannerImpl.STATUS_DEFAULT || form.getStatus() == BannerImpl.STATUS_PUBLISHER_DEFAULT) 
            		out.println(Msg.fetch("banner.edit.default.title", request) + form.getBannerId());
            	else
                	out.println(Msg.fetch("banner.edit.title", request) + form.getBannerId());
            }
            else
                out.println(Msg.fetch("banner.add.title", request));
        } else {
            out.println(Msg.fetch("banner.add.title", request));
        }%></td>
        <td><img src="images/table2.gif"></td>
    </tr></table>
</td></tr>

<tr><td>
<table width="100%" cellspacing="0" cellpadding="0"><tr>

<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
<td>
<table width="100%" cellspacing="0" cellpadding="0">
<tr>
    <td height="10" colspan="2" class="tabledata">
        <table border="0" width="100%" cellspacing="5"><tr><td class="maintext">


            <%--<%=LinkHelper.getHrefByBannerId(form.getBannerId())%>--%>
             <%
                if (form.getBannerId() != null) {
                    if (form.getBannerId() != 0) {
                            out.println(lhs.getPlaceWithBannerByBannerId(form.getBannerId(), request));

                    }
                }
            %>


        </td></tr></table>
    </td></tr>
<tr>
    <td height="10" class="tabledata">
        <table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><nobr><bean:message
                key="size.name.text"/></nobr></td></tr></table>
    </td>
    <td height="10" class="tabledata">
        <table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><html:select property="sizeId"
                                                                                             styleClass="defType">
            <html:optionsCollection property="sizesCollection"/></html:select></td></tr></table>
    </td>
</tr>
	<tr>
	    <td height="10" class="tabledata">
	        <table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><nobr><bean:message
	                key="status"/></nobr></td></tr></table>
	    </td>
	    <td height="10" class="tabledata">
	        <table border="0" width="100%" cellspacing="5"><tr><td class="maintext">
	        <html:select property="status"  styleClass="defType">
		        <%  if (form.getStatus() != BannerImpl.STATUS_DEFAULT && form.getStatus() != BannerImpl.STATUS_PUBLISHER_DEFAULT) {%>	        
		            <html:optionsCollection property="statesCollection"/>
	            <% } else {%>
	            	<option value=<%if (form.getStatus() == BannerImpl.STATUS_DEFAULT) out.println(BannerImpl.STATUS_DEFAULT);
	            		else out.println(BannerImpl.STATUS_PUBLISHER_DEFAULT);%> selected="selected">
						<%out.println(Msg.fetch("default", request)); %>	            		
	            	</option>
	            <% } %>
            </html:select>
	        </td></tr></table>
	    </td>
	</tr>

<tr>
    <td height="10" class="tabledata">
        <table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><nobr><bean:message
                key="banner.type.text"/></nobr></td></tr></table>
    </td>
    <td height="10" class="tabledata">
        <table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><html:select property="typeId"
                                                                                             
                                                                                             styleClass="defType">
            <html:optionsCollection property="typesCollection"/></html:select></td></tr></table>
    </td>
</tr>
<%  if (form.getStatus() != BannerImpl.STATUS_DEFAULT && form.getStatus() != BannerImpl.STATUS_PUBLISHER_DEFAULT) {%>
	<tr>
	    <td height="10" class="tabledata">
	        <table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><nobr><bean:message
	                key="startdate"/></nobr></td></tr></table>
	    </td>
	    <td height="10" class="tabledata">
	        <table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><html:text readonly="true" property="startDate"/>&nbsp;
	            <script language="JavaScript" type="text/javascript">
	                
	            </script></td></tr></table>
	    </td>
	</tr>
	<tr>
	    <td height="10" class="tabledata">
	        <table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><nobr><bean:message
	                key="enddate"/></nobr></td></tr></table>
	    </td>
	    <td height="10" class="tabledata">
	        <table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><html:text readonly="true" property="endDate"/>&nbsp;
	            <script language="JavaScript" type="text/javascript">
	                
	            </script></td></tr></table>
	    </td>
	</tr>
<% } %>
<tr>
    <td height="10" class="maintext">
        <table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><bean:message key="banner.name"/>:&nbsp;&nbsp;<sup>*</sup></td>
        </tr></table>
    </td>
    <td height="10" >
        <table border="0" width="100%" cellspacing="5"><tr><td ><html:text
                property="bannerName" size="50" maxlength="50"/></td></tr></table>
    </td>
</tr>
<%  if (form.getStatus() != BannerImpl.STATUS_DEFAULT && form.getStatus() != BannerImpl.STATUS_PUBLISHER_DEFAULT) {%>
	<tr>
	    <td height="10" class="tabledata">
	        <table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><nobr><bean:message
	                key="campaign"/></nobr></td></tr></table>
	    </td>
	    <td height="10" class="tabledata">
	        <table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><html:select property="campainId"
	                                                                                             styleClass="defType">
	            <html:optionsCollection property="userCampainsCollection"/></html:select></td></tr></table>
	    </td>
	</tr>
<% } %>
<tr>
    <td height="10" class="tabledata">
        <table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><nobr><bean:message
                key="banner.url"/></nobr>&nbsp;&nbsp;<sup>*</sup></td></tr></table>
    </td>
    <td height="10" class="tabledata">
        <table border="0" width="100%" cellspacing="5"><tr><td class="maintext">
            <html:text property="url" style="width: 370px;" maxlength="255"/></td></tr></table>
    </td>
</tr>

<tr>
    <td height="10" class="maintext">
        <table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><bean:message key="alttext"/>:</td></tr>
        </table>
    </td>
    <td height="10" >
        <table border="0" width="100%" cellspacing="5"><tr><td ><html:text
                property="altText" size="50" maxlength="50"/></td></tr></table>
    </td>
</tr>
<tr>
    <td height="10" class="maintext">
        <table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><bean:message key="statusbartext"/>
            </td></tr></table>
    </td>
    <td height="10" >
        <table border="0" width="100%" cellspacing="5"><tr><td ><html:text
                property="statusBartext" size="50" maxlength="50"/></td></tr></table>
    </td>
</tr>
<%if (user.isOwnCampaignsAllow()) { %>
<tr>
    <td height="10" class="maintext">
        <table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><bean:message
                key="own.campaigns.text.advertizer"/>:</td></tr></table>
    </td>
    <td height="10" >
        <table border="0" width="100%" cellspacing="5"><tr><td ><html:checkbox
                property="ownCampaigns"/></td></tr></table>
    </td>
</tr>
<% }%>

<%--<%  if (form.getStatus() != BannerImpl.STATUS_DEFAULT && form.getStatus() != BannerImpl.STATUS_PUBLISHER_DEFAULT) {%>--%>
	
	<tr>
	    <td height="10" class="tabledata">
	        <table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><nobr><bean:message
	                key="target.window"/></nobr></td></tr></table>
	    </td>
	    <td height="10" class="tabledata">
	        <table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><html:select property="targetWindowId"
	                                                                                             styleClass="defType"
	                                                                                             onchange="targetSelect(bannerUploadActionForm,1);">
	            <html:optionsCollection property="targetWindowCollection"/></html:select></td></tr></table>
	    </td>
	</tr>








	        
                
	            
                
	    
	

	
	
	    
	        
                
            
	    
	    
	        
                
	            
                
	    
	
<%--<% }%>--%>



    
        
            
        
    
    
        
            
            
            
    

<tr>
    <td class="tabledata" height="10">
        <table border="0" cellspacing="5" width="100%">
            <tbody><tr><td class="maintext"><nobr>
                <bean:message key="banner.external.url"/>
            </nobr></td></tr></tbody></table>
    </td>
    <td class="tabledata" height="10">
        <table border="0" cellspacing="5" width="100%">
            <tbody><tr><td class="maintext">
                <html:text property="externalURL" size="50" maxlength="50"/></td></tr></tbody></table>
    </td>
</tr>

<tr><td class="tabledata" height="10">
    <table border="0" cellspacing="5" width="100%">
        <tbody><tr><td class="maintext"><nobr>Banner file</nobr>&nbsp;&nbsp;<sup>*</sup></td></tr></tbody>
    </table>
</td>
    <td class="tabledata" height="10">
        <table border="0" cellspacing="5" width="100%"><tbody><tr>
            <td class="maintext">
                <input name="theFile" size="50" value="" type="file"></td></tr></tbody>
        </table>
    </td>
</tr>
<tr>
    <td height="10" colspan="2" class="tabledata">
        <table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><html:submit/>
            <%if ("edit".equals(form.getAction())) {%>
            &nbsp;&nbsp;&nbsp;<input type="button" value='<bean:message key="remove"/>'
                                     onclick="if( window.confirm('<%=Msg.fetch("delete.confirm",request)%>')) {document.location='<%=response.encodeURL("upload.do?action=removeBanner&bannerId="+form.getBannerId())%>'}">
            <%}%>
        </td></tr></table>
    </td>
</tr>
 <tr>
    <td height="10" colspan="2" class="tabledata">
        <table border="0" width="100%" cellspacing="5"><tr><td class="maintext">
            <bean:message key="form.msg.mandatory"/>
        </td></tr></table>
    </td>
</tr>
</table>
</td>
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


