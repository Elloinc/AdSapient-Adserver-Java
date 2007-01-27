<%@ page import="com.adsapient.api_impl.publisher.*,
                              com.adsapient.api_impl.statistic.publisher.PublisherStatistic,
                              com.adsapient.api_impl.statistic.common.*" %>
<%@ page import="com.adsapient.util.financial.FinancialLinksController"%>
<%@ page import="com.adsapient.util.jsp.JSPHelper"%>
<%@ page import="com.adsapient.util.Msg"%>
<%@ page import="com.adsapient.gui.ContextAwareGuiBean"%>
<%@ page import="com.adsapient.shared.service.TotalsReportService"%>
<%@ page import="org.springframework.context.ApplicationContext"%>
<%@ page import="com.adsapient.util.admin.AdsapientConstants"%>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %>
<%@ taglib uri='/WEB-INF/struts-bean.tld' prefix='bean' %>

<% ApplicationContext ctx = ContextAwareGuiBean.ctx;%>
<% TotalsReportService trs = (TotalsReportService) ctx.getBean("totalsReportsService");%>

<html:messages id="msg" message="true" header="messages.header" footer="messages.footer" >
 <bean:write name="msg" />
</html:messages>


<html:form action="publisherEdit" >
<input type="hidden" name="action" value="init">
<tr><td>
	<table width="100%"cellspacing="0" cellpadding="0"><tr>
	<td><img src="images/table1.gif"></td>
	<td width="100%" class="tableheader"><bean:message key="site.sitelist.title"/></td>
	<td><img src="images/table2.gif"></td>
	</tr></table>
</td></tr>
<tr><td>
	<table width="100%" cellspacing="0" cellpadding="0">
	
	<tr>
	<td width="5" style="background-image: url(images/table4.gif);"></td>
		<td><table border="0" style="background-color:#ffffff;" cellspacing="1" width="100%"><tr>
	<td  width="5%" class="tabletableheader"><nobr><bean:message key="id"/></nobr></td>
	<td  width="20%" class="tabletableheader"><nobr><bean:message key="url"/></nobr></td>
	<td  width="25%" class="tabletableheader"><nobr><bean:message key="description"/></nobr></td>
	<td  width="5%" class="tabletableheader"><nobr><bean:message key="cpm.cpc"/></nobr></td>
	<td  width="5%" class="tabletableheader"><nobr><bean:message key="cpl.cps"/></nobr></td>
	<td  width="10%" class="tabletableheader"><nobr><bean:message key="views_clicks"/></nobr></td>
	<td  width="10%" class="tabletableheader"><nobr><bean:message key="leads.sales"/></nobr></td>
	<td  width="5%" class="tabletableheader"><nobr><bean:message key="ctr"/></nobr></td>
	<td  width="5%" class="tabletableheader"><nobr><bean:message key="revenue"/></nobr></td>
	<td  width="10%" class="tabletableheader" colspan="4"><nobr><bean:message key="actions"/></nobr></td>

		</tr>
		
	<logic:iterate  id="siteMy"  name="publisherActionForm" property="sitesCollection">
	<%SiteImpl site=(SiteImpl) siteMy;%>
	<%StatisticEntity entity=PublisherStatistic.getSiteStatisticAsText(site.getSiteId());%>
	<tr>
		<td  class="tabledata-c"><nobr><%=site.getSiteId()%></nobr></td>
		<td  class="tabledata"><nobr><a class="tabledata" href="<%=response.encodeURL(site.getUrl())%>" target="_blank"><%=site.getUrl()%></a></nobr></td>
		<td  class="tabledata"><%=site.getDescription()%></td>
		<td  class="tabledata-c"><nobr><%=FinancialLinksController.generateRates(session,site,response)%></nobr></td>
		<td  class="tabledata-c"><nobr><%=FinancialLinksController.generateCPLRates(session,site,response)%></nobr></td>
		<td  class="tabledata-c"><nobr><%=trs.getTotalUnitsByEntity(SiteImpl.class,site.getSiteId(), AdsapientConstants.ADVIEW)%> | <%=trs.getTotalUnitsByEntity(SiteImpl.class,site.getSiteId(), AdsapientConstants.CLICK)%></nobr></td>
		<td  class="tabledata-c"><nobr><%=trs.getTotalUnitsByEntity(SiteImpl.class,site.getSiteId(), AdsapientConstants.LEAD)%> | <%=trs.getTotalUnitsByEntity(SiteImpl.class,site.getSiteId(), AdsapientConstants.SALE)%></nobr></td>
		<td  class="tabledata-c"><nobr><%=trs.getTotalUnitsByEntity(SiteImpl.class,site.getSiteId(), AdsapientConstants.CTR) %></nobr></td>
		<td  class="tabledata-c"><nobr><%=trs.getTotalUnitsByEntity(SiteImpl.class,site.getSiteId(), AdsapientConstants.EARNEDSPENT)%></nobr></td>
		<%-- <td  class="tabledata-c"><nobr><a class="tabledata" href="<%=response.encodeURL("publisherStatistic.do?statisticType=Site&siteId="+site.getSiteId()+"&userId="+site.getUserId())%>"><img src="images/icons/reports.png" border="0" title=<bean:message key="reports"/>></a></nobr></td>
		<td  class="tabledata-c"><nobr><%if (JSPHelper.checkResetAccess(request)){%><a class="tabledata" href="<%=response.encodeURL("publisherEdit.do?action=resetStatistic&siteId="+site.getSiteId()+"&userId="+site.getUserId())%>" onClick="return window.confirm('<%=Msg.fetch("reset.confirm",session)%>');">
                                                                                    <%}%><img src="images/icons/resetstats.gif" border="0" title=<bean:message key="reset.statistic"/>></a></nobr></td> --%>

        <td class="tabledata-c"><nobr><a class="tabledata" href="<%=response.encodeURL("totals.do?siteId="+site.getId())%>" onClick="return window.confirm('<%=(Msg.fetch("reset.confirm",session))%>');"><img src="images/icons/resetstats.gif" border="0" title='<bean:message key="reset"/>'/></a></nobr></td>
        <td  class="tabledata-c"><nobr><a class="tabledata" href="<%=response.encodeURL("publisherEdit.do?action=view&siteId="+site.getSiteId()+"&userId="+site.getUserId())%>"><img src="images/icons/edit.png" border="0" title=<bean:message key="edit"/>></a></nobr></td>
		<td  class="tabledata-c"><nobr><a class="tabledata" href="<%=response.encodeURL("publisherEdit.do?action=remove&siteId="+site.getSiteId())%>" onClick="return window.confirm('<%=(Msg.fetch("delete.confirm",session)+" "+site.getSiteId())%>');"><img src="images/icons/remove.png" border="0" title=<bean:message key="remove"/>></a> </nobr></td>


	</tr>
	</logic:iterate>
	

		<tr>
	<td height="10"  colspan="13"  class="tabledata"><table border="0" width="100%" cellspacing="5"><tr><td class="tabledata"><input type="submit" value='<%=Msg.fetch("site.addsite.button",session)%>'></td></tr></table></td>
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


