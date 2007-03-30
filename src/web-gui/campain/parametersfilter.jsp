<html:form action="filter" method="get">
		<html:hidden property="filterType" />
		<html:hidden property="filterAction" />
		<html:hidden property="campainId" />
		<html:hidden property="bannerId" />
<tr>
	<td>
	<table width="100%" cellspacing="0" cellpadding="0" border="0">
		
		<tr>
			<td width="5" heigh="10"
				style="background-image: url(images/table4.gif);"></td>
			<td>
			<table border="0" width="100%" class="tabledata" cellspacing="0" cellpadding="0">
			<tr><td>
			  <table border="0" class="tabledata" cellspacing="2" cellpadding="2">
				<%=form.getParametersSource()%>
			  </table>
			</td></tr>
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
	<table width="100%" cellspacing="0" cellpadding="0" class="tabledata">
		
		<tr>
			<td width="5" heigh="10"
				style="background-image: url(images/table4.gif);"></td>
			<td>

			<hr />
			<table border="0" cellspacing="5" cellpadding="5" class="tabledata">
				<tr>
					<td><img src="images/icons/update.png" style="cursor: pointer;"
						onclick="transform();filterActionForm.submit();"
						title="<%=i18nService.fetch("update",request)%>"
						alt="<%=i18nService.fetch("update",request)%>" /></td>
					<%--<td><img src="images/icons/reset.png" style="cursor: pointer;"
						onclick="document.location='<%=response.encodeURL("filter.do?filterAction=reset&filterType="+form.getFilterType()+"&campainId="+form.getCampainId())%> '"
						title="<%=Msg.fetch("reset.parameter.filter.options",request)%>"
						alt="<%=Msg.fetch("reset.parameter.filter.options",request)%>" /></td>
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
				style="background-image: url(images/table8.gif);" colspan="3"></td>
			<td><img src="images/table7.gif"></td>
		</tr>
	</table>
	</td>
</tr>
</html:form>
