<%@ page import="com.adsapient.api_impl.advertizer.*,
                                      com.adsapient.api_impl.statistic.common.*,
                                      com.adsapient.util.financial.FinancialLinksController,
                                      com.adsapient.api_impl.usability.advertizer.*,
                                        com.adsapient.util.jsp.JSPHelper,
                                      com.adsapiadsapientMsg"%>
<%@ page import="com.adsapient.util.Msg"%>
<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<html:messages id="msg" message="true" header="messages.header" footer="messages.footer" >
 <bean:write name="msg" />
</html:messages>

<html:form action="uteupload.do" enctype="multipart/form-data"> 
<html:hidden property="action"/>
<html:hidden property="campainId"/>
<tr><td>
	<table width="100%"cellspacing="0" cellpadding="0"><tr>
	<td><img src="images/table1.gif"></td>
	<td width="100%" class="tableheader"><bean:message key="banners.list.title"/></td>
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
	<td height="10"  width="5%" class="tabletableheader"  > <bean:message key="cpm.cpc"/></td>
	<td height="10"  width="5%" class="tabletableheader"><bean:message key="status"/></td>
	<td height="10"  width="5%" class="tabletableheader"><bean:message key="views_clicks"/> </td> 
	<td height="10"  width="15%" class="tabletableheader" colspan="4"><bean:message key="actions"/></td> 
		</tr>
		
 <logic:iterate  id="userBanner"  name="uteBannerUploadForm" property="userBanners">
<%BannerRepresentation banner= (BannerRepresentation) userBanner;%>
<%StatisticEntity entity=banner.getStatistic();%>
		<tr>
	<td height="10" class="tabledata"><nobr><%=banner.getBannerId()%></nobr></td>
	<td height="10" class="tabledata"><nobr><%=banner.getCampainId()%></nobr></td>
	<td height="10" class="tabledata"><nobr><%=banner.getCampainName()%></nobr></td>
	<td height="10" class="tabledata"><nobr><%=banner.getName()%></nobr></td>
	<td height="10" class="tabledata-c"><nobr><%=banner.getBannerSize()%></nobr></td>
	<td height="10" class="tabledata-c"><nobr><%=FinancialLinksController.generateRates(session,banner,response)%></nobr></td>
	<td height="10" class="tabledata-c"><nobr> <%=banner.getStatusText(request)%></nobr></td>  
	<td height="10" class="tabledata-c"> <%=entity.getImpressions()%>/<%=entity.getClicks()%></nobr></td>
	<%--<td height="10" class="tabledata-c"><nobr><a class="tabledata" href="<%=response.encodeURL(banner.getReportsHref())%>"><img src="images/icons/reports.png" border="0" title=<bean:message key="reports"/>></a></nobr></td>
	<td height="10" class="tabledata-c"><nobr><%if (JSPHelper.checkResetAccess(request)){%><a class="tabledata" href="<%=response.encodeURL("uteupload.do?action=resetStatistic&bannerId=" + banner.getBannerId())%>" onClick="return window.confirm('<%=Msg.fetch("reset.confirm",session)%>');">
	                                               <%}%><img src="images/icons/resetstats.gif" border="0" title=<bean:message key="reset.statistic"/>></a></nobr></td>--%> 
	<td height="10" class="tabledata-c"><nobr><a class="tabledata" href="<%=response.encodeURL("uteupload.do?action=view&bannerId=" + banner.getBannerId())%>"><img src="images/icons/edit.png" border="0" title=<bean:message key="edit"/>></a></nobr></td> 
	<td height="10" class="tabledata-c"><nobr><%if (banner.getDeleteHref()!=null){%><a class="tabledata" href="<%=response.encodeURL("uteupload.do?action=removeBanner&bannerId="+banner.getBannerId())%>" onClick="return window.confirm('<%=(Msg.fetch("delete.confirm",session)+" "+banner.getBannerId())%>');"><img src="images/icons/remove.png" border="0" title=<bean:message key="remove"/>></a><%}%></nobr></td>	                      
	
		</tr>
	</logic:iterate>	
	
		<tr>
	<td height="10"  colspan="12"  class="tabledata"><table border="0" width="100%" cellspacing="7">
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
