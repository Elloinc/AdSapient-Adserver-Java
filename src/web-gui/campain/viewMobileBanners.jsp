<%@ page import="com.adsapient.api_impl.statistic.common.StatisticEntity"%>
<%@ page import="com.adsapient.gui.forms.BannerUploadFormAction"%>
<%@ page import="com.adsapient.api_impl.usability.advertizer.BannerRepresentation"%>
<%@ page import="com.adsapient.util.financial.FinancialLinksController"%>
<%@ page import="com.adsapient.util.jsp.JSPHelper"%>
<%@ page import="com.adsapient.util.Msg"%>
<%@ page import="com.adsapient.web.campain.upload.MobileBannerUploadActionForm"%>
<%@ page import="com.adsapient.api_impl.usability.advertizer.MobileBannerRepresentation"%>
<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%MobileBannerUploadActionForm form=(MobileBannerUploadActionForm) request.getAttribute("mobileBannerUploadActionForm");%>

<html:messages id="msg" message="true" header="messages.header" footer="messages.footer" >
 <bean:write name="msg" />
</html:messages>

<html:form action="mobileUpload.do" enctype="multipart/form-data">
<html:hidden property="action"/>
<html:hidden property="campainId"/>
<tr><td>
	<table width="100%"cellspacing="0" cellpadding="0"><tr>
	<td><img src="images/table1.gif"></td>
	<td width="100%" class="tableheader">
	<%if(form.getCampainId()==null){%> <bean:message key="banners.list.title"/> <%}else{%>
						<bean:message key="campaignbanners.list.title"/><%=form.getCampainId()%><%}%>
	</td>
	<td><img src="images/table2.gif"></td>
	</tr></table>
</td></tr>
<tr><td>
	<table width="100%" cellspacing="0" cellpadding="0">
	
	<tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
		<td><table border="0" style="background-color:#ffffff;" cellspacing="1" width="100%"><tr>
	<td height="10"  width="1%" class="tabletableheader"><bean:message key="id"/></td>
	<td height="10"  width="10%" class="tabletableheader"><bean:message key="campaign.id"/></td>
	<td height="10"  width="10%" class="tabletableheader"><bean:message key="campaign.name"/></td>
	<td height="10"  width="10%" class="tabletableheader"><bean:message key="banner.name"/></td>
	<td height="10"  width="10%" class="tabletableheader"><bean:message key="banner.size"/></td>
	<td height="10"  width="5%" class="tabletableheader" > <bean:message key="cpm.cpc"/></td>
	<td height="10"  width="5%" class="tabletableheader"><nobr><bean:message	key="cpl.cps" /></nobr></td>
	<td height="10"  width="5%" class="tabletableheader"><bean:message key="status"/></td>
	<td height="10"  width="5%" class="tabletableheader"><bean:message key="views_clicks"/> </td>
	<td height="10"  width="5%" class="tabletableheader"><bean:message key="leads.sales"/> </td>
	<td width="5%" class="tabletableheader"><nobr><bean:message	key="spendings" /></nobr></td>
	<td height="10"  width="15%" class="tabletableheader" colspan="4"><bean:message key="actions"/></td>
		</tr>
		
 <logic:iterate  id="userBanner"  name="mobileBannerUploadActionForm" property="userBanners">
<%BannerRepresentation banner= (BannerRepresentation) userBanner;%>
<%StatisticEntity entity=banner.getStatistic();%>
		<tr>
	<td height="10" class="tabledata"><nobr><%=banner.getBannerId()%></nobr></td>
	<td height="10" class="tabledata"><nobr><%=banner.getCampainId()%></nobr></td>
	<td height="10" class="tabledata"><nobr><%=banner.getCampainName()%></nobr></td>
	<td height="10" class="tabledata"><nobr><%=banner.getName()%></nobr></td>
	<td height="10" class="tabledata-c"><nobr><%=banner.getBannerSize()%></nobr></td>
	<td height="10" class="tabledata-c"><nobr><%=FinancialLinksController.generateRates(session,banner,response)%></nobr></td>
	<td height="10" class="tabledata-c"><nobr><%=FinancialLinksController.generateCPLRates(session,banner,response)%></nobr></td>
	<td height="10" class="tabledata-c"><nobr><%=banner.getStatusText(request)%></nobr></td>
	<td height="10" class="tabledata-c"><nobr><%=entity.getImpressions()%>/<%=entity.getClicks()%></nobr></td>
	<td height="10" class="tabledata-c"><nobr><%=entity.getLeads()%>/<%=entity.getSales() %></nobr></td>
	<td height="10" class="tabledata-c"><nobr><%=entity.getRevenueAsFloat()%></nobr></td>
	<%-- <td height="10" class="tabledata-c"><nobr><a class="tabledata" href="<%=response.encodeURL(banner.getReportsHref())%>"><img src="images/icons/reports.png" border="0" title=<bean:message key="reports"/>></a></nobr></td>
	<td height="10" class="tabledata-c"><nobr><%if (JSPHelper.checkResetAccess(request)){%><a class="tabledata" href="<%=response.encodeURL(banner.getResetStatisticHref())%>" onClick="return window.confirm('<%=Msg.fetch("reset.confirm",session)%>');">
	                                               <%}%><img src="images/icons/resetstats.gif" border="0" title=<bean:message key="reset.statistic"/>></a></nobr></td> --%>
	<td height="10" class="tabledata-c"><nobr><a class="tabledata" href="<%=response.encodeURL("filter.do?bannerId="+banner.getBannerId()+"&campainId="+banner.getCampainId()+"&userId="+banner.getUserId())%>"><img src="images/icons/targeting.png" border="0" title=<bean:message key="targeting"/>></a></td>
	<td height="10" class="tabledata-c"><nobr><a class="tabledata" href="<%=response.encodeURL(banner.getSettingsHref())%>"><img src="images/icons/edit.png" border="0" title=<bean:message key="edit"/>></a></nobr></td>
	<td height="10" class="tabledata-c"><nobr><%if (banner.getDeleteMobileHref()!=null){%><a class="tabledata" href="<%=response.encodeURL(banner.getDeleteMobileHref())%>" onClick="return window.confirm('<%=(Msg.fetch("delete.confirm",session)+" "+banner.getName())%>');"><img src="images/icons/remove.png" border="0" title=<bean:message key="remove"/>></a><%}%></nobr></td>

		</tr>
	</logic:iterate>
	
		<tr>
	<td height="10"  colspan="15"  class="tabledata"><table border="0" width="100%" cellspacing="7">
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
