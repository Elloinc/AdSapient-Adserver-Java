<%@ page import="com.adsapient.api_impl.filter.KeyWordsFilterElement"%>
<%@ page import="com.adsapient.util.Msg"%>
<html:form action="filter">
    <html:hidden property="filterType" />
<html:hidden property="filterAction" />
<html:hidden property="campainId" />
<html:hidden property="bannerId" />
<html:hidden property="textEngineAction" styleId="textEngineAction" />
<html:hidden property="textEngineId" styleId="textEngineId" />

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
                    <td height="10" width="10%" class="tabletableheader"><bean:message
                        key="id" /></td>
                    <td height="10" width="50%" class="tabletableheader"><bean:message
                        key="keywords.label" /></td>
                    <td height="10" width="10%" class="tabletableheader"><bean:message
                        key="keywords.impressions.day" /></td>
                    <td height="10" width="10%" class="tabletableheader"><bean:message
                        key="keywords.clicks.day" /></td>
                    <td height="10" width="10%" class="tabletableheader"><bean:message
                        key="keywords.average.position"/></td>

                    <td height="10" width="10%" class="tabletableheader"><bean:message
                        key="actions" /></td>
                </tr>
                
                <logic:iterate id="entityMy" name="filterActionForm"
                               property="textEnginesCollection">
                    <%KeyWordsFilterElement entity = (KeyWordsFilterElement) entityMy;%>
						<tr>
							<td height="10" class="tabledata"><nobr><%=entity.getId()%></nobr></td>
							<td height="10" class="tabledata"><nobr><%=entity.getKeyWordSet()%></nobr></td>
							<td height="10" class="tabledata-c"><nobr><%=entity.getImpressions()%></nobr></td>
							<td height="10" class="tabledata-c"><nobr><%=entity.getClicks()%></nobr></td>
							<td height="10" class="tabledata-c"><nobr><%=entity.getAverage()%></nobr></td>
							<td height="10" class="tabledata-c"><nobr><img
								src="images/icons/remove.png" border="0"
								title=<bean:message key="remove"/> style="cursor: pointer;"
								onclick="document.getElementById('textEngineAction').value='remove';document.getElementById('textEngineId').value='<%=entity.getId()%>';filterActionForm.submit();" /></nobr></td>
						</tr>
					</logic:iterate>
					<tr>
							<td height="10" class="tabledata"></td>
							<td height="10" class="tabledata" colspan="4"><html:text property="textEngine"/></td>
							<td height="10" class="tabledata-c"></td>

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
		<table width="100%" cellspacing="0" cellpadding="0" class="tabledata">
			
			<tr>
				<td width="5" heigh="10"
					style="background-image: url(images/table4.gif);"></td>
				<td>

				<hr />
				<table border="0" cellspacing="5" cellpadding="5" class="tabledata">
					<tr>
						<td><img src="images/icons/update.png" style="cursor: pointer;"
							onclick="filterActionForm.submit();"
							title="<%=Msg.fetch("update",request)%>"
							alt="<%=Msg.fetch("update",request)%>" /></td>
						<%--<td><img src="images/icons/reset.png" style="cursor: pointer;"
							onclick="document.location='<%=response.encodeURL("filter.do?filterAction=reset&filterType="+form.getFilterType()+"&campainId="+form.getCampainId())%> '"
							title="<%=Msg.fetch("reset.keywords.filter.options",request)%>"
							alt="<%=Msg.fetch("reset.keywords.filter.options",request)%>" />
						</td>
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
