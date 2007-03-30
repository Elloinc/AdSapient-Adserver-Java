<%@ page import="com.adsapient.shared.service.I18nService" %>
<%@ page import="com.adsapient.gui.forms.VerificationActionForm"%>
<%@ page import="com.adsapient.shared.mappable.CampainImpl"%>
<%@ page import="com.adsapient.shared.service.FinancialsService"%>
<%@ page import="com.adsapient.gui.ContextAwareGuiBean"%>
<%@ page import="org.springframework.context.ApplicationContext"%>
<%@ page import="com.adsapient.shared.service.LinkHelperService"%>
<%@ page import="com.adsapient.shared.mappable.UserImpl"%>
<%@ page import="com.adsapient.shared.mappable.SiteImpl"%>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %> 
<%@ taglib uri='/WEB-INF/struts-bean.tld' prefix='bean' %>

<% ApplicationContext ctx = ContextAwareGuiBean.getContext();%>
<% LinkHelperService lhs = (LinkHelperService) ctx.getBean("linkHelperService");%>
<% I18nService i18nService = (I18nService) ctx.getBean("i18nService");%>
<% FinancialsService financialsService = (FinancialsService) ctx.getBean("financialsService");%>
<%VerificationActionForm form =(VerificationActionForm) request.getAttribute("verificationActionForm");%>

<html:messages id="msg" message="true" header="messages.header" footer="messages.footer" >
 <bean:write name="msg" />
</html:messages>

 <%if ("campaign".equals(form.getType())) {%>
<tr><td>
	<table width="100%"cellspacing="0" cellpadding="0"><tr>
	<td><img src="images/table1.gif"></td>
	<td width="100%" class="tableheader"><bean:message key="campaigns.4verification.list.title"/></td>
	<td><img src="images/table2.gif" alt=""></td>
	</tr></table>
</td></tr>
<tr><td>
	<table width="100%" cellspacing="0" cellpadding="0">
	
	<tr>
	<td width="5" style="background-image: url(images/table4.gif);"></td>
		<td><table border="0" style="background-color:#ffffff;" cellspacing="1" width="100%"><tr>
	<td 	width="5%" class="tabletableheader"><bean:message key="id"/></td>
	<td width="60%" class="tabletableheader"><bean:message key="campaign.name"/></td>
	<td 	width="5%" class="tabletableheader"> <bean:message key="cpm.cpc"/></td>
	<td 	width="10%" class="tabletableheader" colspan="6"><bean:message key="actions"/></td>
		</tr>
		
<logic:iterate  id="campainsMy"  name="verificationActionForm" property="verifyingCollection">
<%CampainImpl campain= (CampainImpl) campainsMy;%>
		<tr>
	<td height="10" class="tabledata"><nobr><%=campain.getCampainId()%></nobr></td>
	<td height="10" class="tabledata"><nobr><%=campain.getName()%></nobr></td>
	<td height="10" class="tabledata-c"><nobr><%=lhs.generateRates(session,campain,response)%></nobr></td>
	
	<td height="10" class="tabledata-c"><nobr><a class="tabledata" href="<%=response.encodeURL("verification.do?action=verify&type=campaign&elementId="+campain.getCampainId())%>"><img src="images/icons/verify.png" border="0" title=<bean:message key="verify"/>></a></nobr></td>
	<td height="10" class="tabledata-c"><nobr><a class="tabledata" href='<%=response.encodeURL("campainEdit.do?action=view&campainId="+campain.getCampainId())%>'><img src="images/icons/edit.png" border="0" title=<bean:message key="edit"/>></a></nobr></td> 
	<td height="10" class="tabledata-c"><nobr><a class="tabledata" href="<%=response.encodeURL("filter.do?campainId="+campain.getCampainId())%>"><img src="images/icons/targeting.png" border="0" title=<bean:message key="targeting"/>	></a></td>
	<%-- <td height="10" class="tabledata-c"><nobr><a class="tabledata" href="<%=response.encodeURL("advertizerStatistic.do?type=campainAll&campainId="+campain.getCampainId())%>"><img src="images/icons/reports.png" border="0" title=<bean:message key="reports"/>></a></nobr></td>
    <td height="10" class="tabledata-c"><nobr><%if (JSPHelper.checkResetAccess(request)){%><a class="tabledata" href="<%=response.encodeURL("campainEdit.do?action=resetStatistic&campainId="+campain.getCampainId())%>" onClick="return window.confirm('<%=Msg.fetch("reset.confirm",session)%>');">
	                                                                             <%}%><img src="images/icons/resetstats.gif" border="0" title=<bean:message key="reset.statistic"/>></a></nobr></td> --%>
	<td height="10" class="tabledata-c"><nobr><a class="tabledata" href='<%=response.encodeURL("campainEdit.do?action=remove&campainId="+campain.getCampainId())%>'><img src="images/icons/remove.png" border="0" title=<bean:message key="remove"/>></a></nobr></td>	                      
		</tr>
	</logic:iterate>	
				</table></td>
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
<%}%>


<%if ("user".equals(form.getType())) { %>

<%UserImpl privilegUser =(UserImpl) request.getSession().getAttribute("user");%>
<tr><td>
	<table width="100%"cellspacing="0" cellpadding="0"><tr>
	<td><img src="images/table1.gif"></td>
	<td width="100%" class="tableheader"><bean:message key="not.verify.users"/></td>
	<td><img src="images/table2.gif"></td>
	</tr></table>
</td></tr>
<tr><td>
	<table width="100%" cellspacing="0" cellpadding="0">
	
		<tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
		<td><table border="0" style="background-color:#ffffff;" cellspacing="1" width="100%" cellpadding="0" heigh="100%"><tr>
	<td width="5%"  rowspan="2"  class="tabletableheader"><bean:message key="id"/></td>
	<td width="10%" rowspan="2" class="tabletableheader"><bean:message key="usermanagement.username"/></td>
	<td width="10%" rowspan="2" class="tabletableheader"><bean:message key="userrole"/></td>
	<td width="10%" class="tabletableheader" colspan="4"><bean:message key="rate.adv"/></td>
	<td width="10%" class="tabletableheader" colspan="4"><bean:message key="rate.pub"/></td>	
	<td  width="5%" rowspan="2" class="tabletableheader"><bean:message key="account.money"/></td>
	<td  width="15%" rowspan="2" class="tabletableheader"><bean:message key="email"/> </td>
	<td  width="5%" rowspan="2" class="tabletableheader"><bean:message key="login.header"/> </td>
	<td  width="5%" rowspan="2" class="tabletableheader"><bean:message key="password"/> </td>
	<td width="5%" rowspan="2" class="tabletableheader" colspan="4"><bean:message key="actions"/></td>
	</tr>
	
	<tr>
	<td class="tabletableheader">CPM</td><td class="tabletableheader">CPC</td><td class="tabletableheader">CPL</td><td class="tabletableheader">CPS</td> 
	<td class="tabletableheader">CPM</td><td class="tabletableheader">CPC</td><td class="tabletableheader" >CPL</td><td class="tabletableheader">CPS</td>
	</tr>
		
<logic:iterate  id="user"  name="verificationActionForm" property="verifyingCollection"> 

<%UserImpl iterUser= (UserImpl) user;%>
		<tr>
	<td class="tabledata-c"><nobr><%=iterUser.getId()%></nobr></td>
	<td class="tabledata-c"><nobr><%=iterUser.getName()%></nobr></td>
   	<td class="tabledata-c"><nobr><%=i18nService.fetch(iterUser.getShortRole(),request)%></nobr></td>
    <%=lhs.generateFinancialLink(iterUser,response)%>
    <td class="tabledata-c"><nobr><a class="tabledata" href="<%=response.encodeURL("viewAccount.do?action=accountView&userId="+iterUser.getId())%>"><%=financialsService.transformMoney(iterUser.getAccountMoney())%></a></nobr></td>
	<td class="tabledata-c"><nobr><%=iterUser.getEmail()%></nobr></td>
	<td class="tabledata-c"><nobr><%=iterUser.getLogin()%></nobr></td>
	<td class="tabledata-c"><nobr><%=iterUser.getPassword()%></nobr></td>
	<td class="tabledata-c"><nobr><a class="tabledata" href="<%=response.encodeURL("verification.do?action=verify&type=user&elementId="+iterUser.getId())%>"><img src="images/icons/verify.png" border="0" title=<bean:message key="verify.user"/>></a></nobr></td>
	<td class="tabledata-c"><nobr><a class="tabledata" href="<%=response.encodeURL("profileEdit.do?action=view&id="+iterUser.getId())%>"><img src="images/icons/edit.png" border="0" title=<bean:message key="edit"/>></a></nobr></td>
    
    <%--<td class="tabledata-c"><nobr><a target="_blank" class="tabledata" href="login.do?action=login&login=<%=iterUser.getLogin()%>&password=<%=iterUser.getPassword()%>&parentId=<%=privilegUser.getId()%> "><img src="images/icons/login.png" border="0" title=<bean:message key="login"/>></a></nobr></td>--%>
	<td class="tabledata-c"><nobr><a class="tabledata" href="<%=response.encodeURL("verification.do?action=remove&elementId="+iterUser.getId())%>"><img src="images/icons/remove.png" border="0" title=<bean:message key="remove"/>></a></nobr></td>
		</tr>
	</logic:iterate>
		</table></td>
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




<%}%>

<%if ("site".equals(form.getType())) {%>


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
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
		<td><table border="0" style="background-color:#ffffff;" cellspacing="1" width="100%"><tr>
	<td height="10"  width="1%" class="tabletableheader"><bean:message key="id"/></td>
	<td height="10"  width="20%" class="tabletableheader"><bean:message key="url"/></td>
	<td height="10"  width="59%" class="tabletableheader"><bean:message key="description"/></td>
	<td height="10"  width="5%" class="tabletableheader"><bean:message key="cpm.cpc"/> </td>
	<td height="10"  width="5%" class="tabletableheader"><bean:message key="ctr"/> </td> 
	<td height="10"  width="5%" class="tabletableheader" colspan="5"><bean:message key="actions"/></td>
		</tr>
		
	<logic:iterate  id="siteMy"  name="verificationActionForm" property="verifyingCollection">
	<%SiteImpl site=(SiteImpl) siteMy;%>
		<tr>
	<td height="10" class="tabledata"><nobr><%=site.getSiteId()%></nobr></td>
	<td height="10" class="tabledata"><nobr><a class="tabledata" href="<%=response.encodeURL(site.getUrl())%>" target="_blank"><%=site.getUrl()%></a></nobr></td>
	<td height="10" class="tabledata"><%=site.getDescription()%></td>
	<td height="10" class="tabledata-c"><nobr><%=lhs.generateRates(session,site,response)%></nobr></td>
	<td height="10" class="tabledata-c"><nobr></nobr></td>
	<td height="10" class="tabledata-c"><nobr> <a class="tabledata" href="<%=response.encodeURL("verification.do?action=verify&type=site&elementId="+site.getSiteId())%>"><img src="images/icons/verify.png" border="0" title=<bean:message key="verify"/>></a></nobr></td>  
	<td height="10" class="tabledata-c"><nobr><a class="tabledata" href="<%=response.encodeURL("publisherEdit.do?action=view&siteId="+site.getSiteId())%>"><img src="images/icons/edit.png" border="0" title=<bean:message key="edit"/>></a></nobr></td> 
	<%-- <td height="10" class="tabledata-c"><nobr><a class="tabledata" href="<%=response.encodeURL("publisherStatistic.do?type=allSiteStats&siteId="+site.getSiteId())%>"><img src="images/icons/reports.png" border="0" title=<bean:message key="reports"/>></a></nobr></td>
	<td  class="tabledata-c"><nobr><%if (JSPHelper.checkResetAccess(request)){%><a class="tabledata" href="<%=response.encodeURL("publisherEdit.do?action=resetStatistic&siteId="+site.getSiteId())%>" onClick="return window.confirm('<%=Msg.fetch("reset.confirm",session)%>');">
                                                                                    <%}%><img src="images/icons/resetstats.gif" border="0" title=<bean:message key="reset.statistic"/>></a></nobr></td> --%>
		
	<td height="10" class="tabledata-c"><nobr><a class="tabledata" href="<%=response.encodeURL("publisherEdit.do?action=remove&siteId="+site.getSiteId())%>"><img src="images/icons/remove.png" border="0" title=<bean:message key="remove"/>></a> </nobr></td>	                      
	
		</tr>
	</logic:iterate>	
	 
	
		</table></td>
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









<%}%>
