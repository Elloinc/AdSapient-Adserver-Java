<%@ page import="com.adsapient.gui.forms.CampainActionForm"%>
<%@ page import="com.adsapient.api_impl.advertizer.CampainImpl"%>
<%@ page import="com.adsapient.util.financial.FinancialLinksController"%>
<%@ page import="com.adsapient.api_impl.statistic.common.StatisticEntity"%>
<%@ page import="com.adsapient.api_impl.statistic.advertizer.AdvertizerStatistic"%>
<%@ page import="com.adsapient.util.jsp.JSPHelper"%>
<%@ page import="com.adsapient.util.Msg"%>
<%@ page import="com.adsapient.gui.ContextAwareGuiBean"%>
<%@ page import="com.adsapient.shared.service.TotalsReportService"%>
<%@ page import="org.springframework.context.ApplicationContext"%>
<%@ page import="com.adsapient.util.admin.AdsapientConstants"%>
<%@ page import="com.adsapient.api_impl.publisher.SiteImpl"%>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %>
<%@ taglib uri='/WEB-INF/struts-bean.tld' prefix='bean' %>

<% ApplicationContext ctx = ContextAwareGuiBean.ctx;%>
<% TotalsReportService trs = (TotalsReportService) ctx.getBean("totalsReportsService");%>

<%CampainActionForm form =(CampainActionForm) request.getAttribute("campainActionForm");%>

<html:messages id="msg" message="true" header="messages.header" footer="messages.footer" >
 <bean:write name="msg" />
</html:messages>

<html:form action="campainEdit" >
<html:hidden property="action"/>
<tr><td>
	<table width="100%"cellspacing="0" cellpadding="0"><tr>
	<td><img src="images/table1.gif"></td>
	<td width="100%" class="tableheader"><bean:message key="campaigns.list.title"/></td>
	<td><img src="images/table2.gif" alt=""></td>
	</tr></table>
</td></tr>
<tr><td>
	<table width="100%" cellspacing="0" cellpadding="0">
	
	<tr>
	<td width="5" style="background-image: url(images/table4.gif);"></td>
		<td><table border="0" style="background-color:#ffffff;" cellspacing="1" width="100%"><tr>
	<td width="5%" class="tabletableheader"><nobr><bean:message key="id"/></nobr></td>
	<td width="25%" class="tabletableheader"><nobr><bean:message key="campaign.name"/></nobr></td>
	<td width="5%" class="tabletableheader"><nobr><bean:message key="cpm.cpc"/></nobr></td>
	<td width="5%" class="tabletableheader"><nobr><bean:message	key="cpl.cps" /></nobr></td>
	<td width="10%" class="tabletableheader"><nobr><bean:message key="views_clicks"/></nobr></td>
	<td width="10%" class="tabletableheader"><nobr><bean:message key="leads.sales"/></nobr></td>
	<td width="5%" class="tabletableheader"><nobr><bean:message key="ctr"/></nobr></td>
	<td width="10%" class="tabletableheader"><nobr><bean:message key="status"/></nobr></td>
	<td width="5%" class="tabletableheader"><nobr><bean:message	key="spendings" /></nobr></td>
	<td width="20%" class="tabletableheader" colspan="4"><nobr><bean:message key="actions"/></nobr></td>
		</tr>
		
<logic:iterate  id="campainsMy"  name="campainActionForm" property="campains">
<%CampainImpl campain= (CampainImpl) campainsMy;%>
<%StatisticEntity entity= AdvertizerStatistic.getCampainStatisticAsText(campain.getCampainId());%>
		<tr>
	<td height="10" class="tabledata-c"><nobr><%=campain.getCampainId()%></nobr></td>
	<td height="10" class="tabledata-c"><nobr><%=campain.getName()%></nobr></td>
	<td height="10" class="tabledata-c"><nobr><%=FinancialLinksController.generateRates(session,campain,response)%></nobr></td>
	<td height="10" class="tabledata-c"><nobr><%=FinancialLinksController.generateCPLRates(session,campain,response)%></nobr></td>
	<td height="10" class="tabledata-c"><nobr><%=trs.getTotalUnitsByEntity(CampainImpl.class,campain.getCampainId(), AdsapientConstants.ADVIEW)%> | <%=trs.getTotalUnitsByEntity(CampainImpl.class,campain.getCampainId(), AdsapientConstants.CLICK)%></nobr></td>
	<td height="10" class="tabledata-c"><nobr><%=trs.getTotalUnitsByEntity(CampainImpl.class,campain.getCampainId(), AdsapientConstants.LEAD)%> | <%=trs.getTotalUnitsByEntity(CampainImpl.class,campain.getCampainId(), AdsapientConstants.SALE) %></nobr></td>
	<td height="10" class="tabledata-c"><nobr><%=trs.getTotalUnitsByEntity(CampainImpl.class,campain.getCampainId(), AdsapientConstants.CTR)%></nobr></td>
	<td height="10" class="tabledata-c"><nobr><%=campain.getCampainState(request)%></nobr></td>
	<td height="10" class="tabledata-c"><nobr><%=trs.getTotalUnitsByEntity(CampainImpl.class,campain.getCampainId(), AdsapientConstants.EARNEDSPENT)%></nobr></td>
	<td height="10" class="tabledata-c"><nobr><a class="tabledata" href="<%=response.encodeURL("filter.do?campainId="+campain.getCampainId()+"&userId="+campain.getUserId())%>"><img src="images/icons/targeting.png" border="0" title=<bean:message key="targeting"/>></a></td>
	<td height="10" class="tabledata-c"><nobr><a class="tabledata" href='<%=response.encodeURL("campainEdit.do?action=view&campainId="+campain.getCampainId()+"&userId="+campain.getUserId())%>'><img src="images/icons/edit.png" border="0" title=<bean:message key="edit"/>></a></nobr></td>
	<%--<td height="10" class="tabledata-c"><nobr><a class="tabledata" href="<%=response.encodeURL("advertizerStatistic.do?type=campainAll&campainId="+campain.getCampainId()+"&userId="+campain.getUserId())%>"><img src="images/icons/reports.png" border="0" title=<bean:message key="reports"/>></a></nobr></td>
	<td height="10" class="tabledata-c"><nobr><%if (JSPHelper.checkResetAccess(request)){%><a class="tabledata" href="<%=response.encodeURL("campainEdit.do?action=resetStatistic&campainId="+campain.getCampainId()+"&userId="+campain.getUserId())%>" onClick="return window.confirm('<%=Msg.fetch("reset.confirm",session)%>');">
	                                                                             <%}%><img src="images/icons/resetstats.gif" border="0" title=<bean:message key="reset.statistic"/>></a></nobr></td>--%>
    <td height="10" class="tabledata-c"><nobr><a class="tabledata" href="<%=response.encodeURL("totals.do?campaignId="+campain.getCampainId())%>" onClick="return window.confirm('<%=(Msg.fetch("reset.confirm",session))%>');"><img src="images/icons/resetstats.gif" border="0" title='<bean:message key="reset"/>'/></a></nobr></td>
	<td height="10" class="tabledata-c"><nobr><a class="tabledata" href='<%=response.encodeURL("campainEdit.do?action=remove&campainId="+campain.getCampainId())%>' onClick="return window.confirm('<%=(Msg.fetch("delete.confirm",session)+" "+campain.getCampainId())%>');"><img src="images/icons/remove.png" border="0" title=<bean:message key="remove"/>></a></nobr></td>

		</tr>
	</logic:iterate>
		<tr>
	<td height="10"  colspan="13"  class="tabledata"><table border="0" width="100%" cellspacing="5">
		<tr><td class="tabledata"><input type="submit" value='<bean:message key="add"/>'></td>
		</tr>

        </table></td>
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
