<%@ page import="com.adsapient.api_impl.statistic.common.StatisticEntity" %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %> 
<%@ taglib uri='/WEB-INF/struts-bean.tld' prefix='bean' %>

	
	<tr><td>
	<table width="100%"cellspacing="0" cellpadding="0"><tr>
	<td><img src="images/table1.gif"></td>
	<td width="100%" class="tableheader"><bean:message key="daily.reports.for.campaign"/></td>
	<td><img src="images/table2.gif"></td>
	</tr></table>
</td></tr>
<tr> <td>
	<table width="100%" cellspacing="0" cellpadding="0">
	
	<tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td><table border="0" style="background-color:#ffffff;" cellspacing="1" width="100%"><tr>
		<td height="10"  width="10%"  class="tabletableheader">&nbsp;<bean:message key="id"/></td>
		<td height="10"  width="20%" class="tabletableheader">&nbsp;<bean:message key="campaign.name"/></td>
		<td height="10"  width="10%" class="tabletableheader">&nbsp;<bean:message key="start.date"/></td>
		<td height="10"  width="10%" class="tabletableheader">&nbsp;<bean:message key="end.date"/></td>
		<td height="10"  width="10%" class="tabletableheader">&nbsp;<bean:message key="rate"/></td>
		<td height="10"  width="10%" class="tabletableheader">&nbsp;<bean:message key="adviews.clicks.booked"/></td>
		<td height="10"  width="10%" class="tabletableheader">&nbsp;<bean:message key="adviews.clicks.displayed"/></td>
		<td height="10"  width="10%" class="tabletableheader">&nbsp;<bean:message key="adleads.sales.booked"/></td>
		<td height="10"  width="10%" class="tabletableheader">&nbsp;<bean:message key="adleads.sales.displayed"/></td>
	</tr>
	
	<logic:iterate  id="statMy"  name="advertizerStatisticActionForm" property="statisticCollection">
	  <%StatisticEntity statistic = (StatisticEntity) statMy;%>
					  <%=statistic.format()%>
					  
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
