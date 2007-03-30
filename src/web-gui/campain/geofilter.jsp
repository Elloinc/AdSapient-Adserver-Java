<tr>
	<td>
	<table width="100%" cellspacing="0" cellpadding="0">
		<html:form action="filter">
			<tr>
				<td width="5" heigh="10"
					style="background-image: url(images/table4.gif);"></td>
				<td class="tabledata">&nbsp;<bean:message key="select.countries" /></td>
				<td width="6" style="background-image: url(images/table5.gif);"></td>
			</tr>
			<tr>
				<td width="5" heigh="10"
					style="background-image: url(images/table4.gif);"></td>
				<td class="tabledata">
				<table border="0" width="100%" cellspacing="5">
					<tr>
						<td><bean:message key="possible.choices" /></br>
						<html:select property="countries" multiple="true"
							styleId="uiOptionTransfer_source1"
							styleClass="uiOptionTransfer_target">
							<html:optionsCollection property="countryNameToCodeCollection" />
						</html:select></td>
						<td><input type="button" value="&raquo;"
							title='<bean:message key="move.all.options.to.target"/>'
							onClick="uiOptionTransfer_transferAll(uiOptionTransfer_object1); return false;" />
						<br />
						<input type="button" value="&gt;"
							title='<bean:message key="move.selected.options.to.target.list"/>'
							onClick="uiOptionTransfer_transfer(uiOptionTransfer_object1); return false;" />
						<br />
						<input type="button" value="&lt;"
							title='<bean:message key="delete.selected.options.from.target"/>'
							onClick="uiOptionTransfer_return(uiOptionTransfer_object1); return false;" />
						<br />
						<input type="button" value="&laquo;"
							title='<bean:message key="delete.all.options.from.target"/>'
							onClick="uiOptionTransfer_returnAll(uiOptionTransfer_object1); return false;" />
						</td>
						<td><bean:message key="actual.selection" /></br>
						<html:select property="countries2"
							styleId="uiOptionTransfer_target1" multiple="true"
							styleClass="uiOptionTransfer_target">
							<html:optionsCollection property="containedCountryNames" />
						</html:select></td>
					</tr>
				</table>
				</td>
				<td width="6" heigh="10"
					style="background-image: url(images/table5.gif);"></td>
			</tr>
			<!-- Script for this menu--->
			<script language="javascript">
var uiOptionTransfer_object1;

function uiOptionTransfer_init1() {
  var src = document.getElementById('uiOptionTransfer_source1');

  uiOptionTransfer_object1 = new uiOptionTransfer_Globals(null, null, 'uiOptionTransfer_source1', 'uiOptionTransfer_target1', 'uiOptionTransfer_distance1');
  uiOptionTransfer_fillStates(uiOptionTransfer_object1);
  uiOptionTransfer_onSubmit(uiOptionTransfer_object1);
}

function uiOptionTransfer_fillStates(obj) {
   var src = document.getElementById(obj.sourceId).options;
   var tgt = document.getElementById(obj.targetId).options;
    for (var i = 0; i < src.length; ++i) {
    for (var k = 0; k < tgt.length; ++k) {
    if(src[i].value==tgt[k].value) {
    src[i].disabled=true;
    break;
    }
  }
}
}
uiOptionTransfer_init1();
</script>

       

		</html:form>

		<html:form action="filter">
			<html:hidden property="filterType" />
			<html:hidden property="filterAction" />
			<html:hidden property="campainId" />
			<html:hidden property="selectedCountrys" styleId="selectedCountrysId" />
			<html:hidden property="selectedCities" styleId="selectedCitiesId" />
			<html:hidden property="bannerId" />
			
			<tr>
				<td width="5" heigh="10"
					style="background-image: url(images/table4.gif);"></td>
				<td class="tabledata" cellspacing="5">
				<hr />
				<table border="0" cellspacing="5" cellpadding="5">
					<tr>
						<td><img src="images/icons/update.png" style="cursor: pointer;"
							onclick="transform_data(uiOptionTransfer_object1);filterActionForm[1].submit();"
							title="<%=i18nService.fetch("update",request)%>"
							alt="<%=i18nService.fetch("update",request)%>" /></td>
						<%-- <td><img src="images/icons/reset.png" style="cursor: pointer;"
							onclick="document.location='<%=response.encodeURL("filter.do?filterAction=reset&filterType="+form.getFilterType()+"&campainId="+form.getCampainId())%> '"
							title="<%=Msg.fetch("reset.geo.filter.options",request)%>"
							alt="<%=Msg.fetch("reset.geo.filter.options",request)%>" /></td>
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
