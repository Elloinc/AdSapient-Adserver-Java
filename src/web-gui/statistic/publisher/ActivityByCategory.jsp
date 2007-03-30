<%@ page import="com.adsapient.api_impl.statistic.common.StatisticEntity"%>

<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html'%>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic'%>
<%@ taglib uri='/WEB-INF/struts-bean.tld' prefix='bean'%>
<%com.adsapient.gui.forms.PublisherStatisticActionFormm.adsapient.gui.forms.PublisherStatisticActionFormtribute("publisherStatisticActionForm");%>
<tr>
	<td>
	<table width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td><img src="images/table1.gif"></td>
			<td width="100%" class="tableheader"><%=form.getTableHeader()%></td>
			<td><img src="images/table2.gif"></td>
		</tr>
	</table>
	</td>
</tr>
<tr>
	<td>
	<table width="100%" cellspacing="0" cellpadding="0">

		
		<tr>
			<td width="5" heigh="10"
				style="background-image: url(images/table4.gif);"></td>
			<td>
			<table border="0" style="background-color:#ffffff;" cellspacing="1"
				width="100%">
				<tr>
					<td height="10" width="20%" class="tabletableheader">&nbsp;<bean:message
						key="category" /></td>
					<td height="10" width="10%" class="tabletableheader">&nbsp;<bean:message
						key="adviews" /></td>
					<td height="10" width="10%" class="tabletableheader">&nbsp;<bean:message
						key="clicks" /></td>
					<td height="10" width="10%" class="tabletableheader">&nbsp;<bean:message
						key="leads" /></td>
					<td height="10" width="10%" class="tabletableheader">&nbsp;<bean:message
						key="sales" /></td>
					<td height="10" width="10%" class="tabletableheader">&nbsp;<bean:message
						key="ctr" /></td>
					<td height="10" width="10%" class="tabletableheader">&nbsp;<bean:message
						key="ltr" /></td>
					<td height="10" width="10%" class="tabletableheader">&nbsp;<bean:message
						key="str" /></td>
					<td height="10" width="10%" class="tabletableheader">&nbsp;<bean:message
						key="revenue" /></td>
				</tr>
				<logic:iterate id="statMy" name="publisherStatisticActionForm"
					property="statisticCollection">
					<%StatisticEntity statistic = (StatisticEntity) statMy;%>
					  <%=statistic.format()%>
				</logic:iterate>
		</tr>
	</table>
	</td>
	<td width="6" heigh="10"
		style="background-image: url(images/table5.gif);"></td>
</tr>
</table>
</td>
</tr>

<tr>
	<td>
	<table width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td><img src="images/table6.gif"></td>
			<td width="100%" heigh="10"
				style="background-image: url(images/table8.gif);"></td>
			<td><img src="images/table7.gif"></td>
		</tr>
	</table>
	</td>
</tr>
