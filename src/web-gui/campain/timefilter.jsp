<%@ page import="com.adsapient.shared.service.I18nService"%>
<tr>
    <td>
<table width="100%" cellspacing="0" cellpadding="0">
    <tr>
        <td width="5" style="background-image: url(images/table4.gif);"></td>
        <td class="tabledata">&nbsp;<bean:message key="select.hours" />:</td>
        <td width="6" style="background-image: url(images/table5.gif);"></td>
    </tr>
    <html:form action="filter">
        <html:hidden property="filterType" />
        <html:hidden property="filterAction" />
        <html:hidden property="campainId" />
        <html:hidden property="excludeHours" />
        <html:hidden property="bannerId" />

        <tr>
            <td width="5" heigh="10"
                style="background-image: url(images/table4.gif);"></td>
            <td class="tabledata">
            <table border="0">
                <tr>
                    <logic:iterate id="valueMy" name="filterActionForm"
                                   property="excludeHoursCollection">
                        <%String value = (String) valueMy;%>
							<td><input type="checkbox" name="<%="hour"+i%>"
								<%="y".equalsIgnoreCase(value)?"checked":""%>><%=(i-1)%> - <%=i%><%i++;%><%if ((i - 1) % 12 == 0) {%></td>
					</tr>
					<tr>
						<%}%>
				</td>
				</logic:iterate>
			</tr>
	</table>
	</td>
	<td width="6" heigh="10"
		style="background-image: url(images/table5.gif);"></td>
</tr>
<tr>
	<td width="5" heigh="10"
		style="background-image: url(images/table4.gif);"></td>
	<td class="tabledata">
	<hr />
	<table border="0" cellspacing="5" cellpadding="5">
		<tr>
            <td><img src="images/icons/update.png" style="cursor: pointer;"
				onclick="filterActionForm.filterAction.value='update';filterActionForm.submit();"
				title="<%=i18nService.fetch("update",request)%>"
				alt="<%=i18nService.fetch("update",request)%>" /></td>
			<%--<td><img src="images/icons/reset.png" style="cursor: pointer;"
				onclick="document.location='<%=response.encodeURL("filter.do?filterAction=reset&filterType="+form.getFilterType()+"&campainId="+form.getCampainId())%> '"
				title="<%=Msg.fetch("reset.time.filter.options",request)%>"
				alt="<%=Msg.fetch("reset.time.filter.options",request)%>" /></td>
			<td><img src="images/icons/reset.png" style="cursor: pointer;"
				onclick="document.location='<%=response.encodeURL("filter.do?filterAction=resetAll&filterType="+form.getFilterType()+"&campainId="+form.getCampainId())%> '"
				title="<%=Msg.fetch("reset.all.filters.options",request)%>"
				alt="<%=Msg.fetch("reset.all.filters.options",request)%>" /></td>
			<td><img src="images/icons/save.png" style="cursor: pointer;"
				onclick="document.location='<%=response.encodeURL("filter.do?dataSource=template&templateAction=import&campainId="+form.getCampainId())%> '"
				title="<%=Msg.fetch("save.all.filters.options.as.template",request)%>"
				alt="<%=Msg.fetch("save.all.filters.options.as.template",request)%>" />
			</td> --%>
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
</html:form>
