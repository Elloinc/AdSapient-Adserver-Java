<%@ page import="com.adsapient.shared.mappable.ReferrersElement"%>
<%@ page import="com.adsapient.shared.service.I18nService"%>
<tr>
  <td>
    <table style="background-image: url(images/tab6.gif);" width="100%"	cellspacing="0" cellpadding="0">
        <html:form action="filter" method="get">
            <html:hidden property="campainId" />
            <html:hidden property="bannerId" />
            <html:hidden property="selected_browsers" styleId="selected_browsers_target" />
            <html:hidden property="selected_systems" styleId="selected_systems_target" />
            <html:hidden property="selected_langs" styleId="selected_langs_target" />
            <html:hidden property="filterType" />
            <html:hidden property="filterAction" />
            <html:hidden property="referrerEngineAction" styleId="referrerEngineAction" />
            <html:hidden property="referrerId" styleId="referrerId" />

                <tr>
                    <td width="5" heigh="10"
                        style="background-image: url(images/table4.gif);"></td>
                    <td class="tabledata">&nbsp;<bean:message key="select.browser" />:</td>
                    <td width="6" heigh="10"
                        style="background-image: url(images/table5.gif);"></td>
                </tr>
                <tr>
                    <td width="5" heigh="10"
                        style="background-image: url(images/table4.gif);"></td>

                    <td class="tabledata">
                    <table border="0" cellspacing="5">
                        <tr>
                            <td><bean:message key="possible.choices" /><br />
                            <html:select property="selectedBrowsers" multiple="true"
                                         styleId="browsers_source"
                                         styleClass="uiOptionTransfer_target">
                                <html:optionsCollection property="user_browsers" />
                            </html:select></td>
                            <td><input type="button" value="&raquo;"
                                       title='<bean:message key="move.all.options.to.target"/>'
                                       onClick="uiOptionTransfer_transferAll(browsers_object); return false;" />
                            <br />
                            <input type="button" value="&gt;"
                                   title='<bean:message key="move.selected.options.to.target.list"/>'
                                   onClick="uiOptionTransfer_transfer(browsers_object); return false;" />
                            <br />
                            <input type="button" value="&lt;"
                                   title='<bean:message key="delete.selected.options.from.target"/>'
                                   onClick="uiOptionTransfer_return(browsers_object); return false;" />
                            <br />
                            <input type="button" value="&laquo;"
                                   title='<bean:message key="delete.all.options.from.target"/>'
                                   onClick="uiOptionTransfer_returnAll(browsers_object); return false;" />
                            </td>
                            <td><bean:message key="actual.selection" /><br />
                            <html:select property="selectedBrowsers"
                                         styleId="browsers_target" multiple="true"
                                         styleClass="uiOptionTransfer_target">
                                <html:optionsCollection property="user_browsers2" />
                            </html:select></td>
                        </tr>
                    </table>
                    </td>
                    <td width="6" heigh="10"
                        style="background-image: url(images/table5.gif);"></td>
                </tr>
                    <!-- Script for this menu--->
                    <script language="javascript">
var browsers_object;


function uiOptionTransfer_init1() {
  var src = document.getElementById('browsers_source');

  browsers_object = new uiOptionTransfer_Globals(null, null, 'browsers_source', 'browsers_target', 'browsers_distance');
  uiOptionTransfer_fillStates(browsers_object);
  uiOptionTransfer_onSubmit(browsers_object);
}



uiOptionTransfer_init1();
</script>
    <tr>
                        <td width="5" heigh="10"
                            style="background-image: url(images/table4.gif);"></td>
                        <td class="tabledata">&nbsp;<bean:message key="select.systems" />:</td>
                        <td width="6" heigh="10"
                            style="background-image: url(images/table5.gif);"></td>
                    </tr>
                    <!-- Page positions ------>
                    <tr>
                        <td width="5" heigh="10"
                            style="background-image: url(images/table4.gif);"></td>
                        <td class="tabledata">
                        <table border="0" cellspacing="5">
                            <tr>
                                <td><bean:message key="possible.choices" /><br />
                                <html:select property="selectedSystems" multiple="true"
                                             styleId="systems_source"
                                             styleClass="uiOptionTransfer_target">
                                    <html:optionsCollection property="user_systems" />
                                </html:select></td>
                                <td><input type="button" value="&raquo;"
                                           title='<bean:message key="move.all.options.to.target"/>'
                                           onClick="uiOptionTransfer_transferAll(systems_object); return false;" />
                                <br />
                                <input type="button" value="&gt;"
                                       title='<bean:message key="move.selected.options.to.target.list"/>'
                                       onClick="uiOptionTransfer_transfer(systems_object); return false;" />
                                <br />
                                <input type="button" value="&lt;"
                                       title='<bean:message key="delete.selected.options.from.target"/>'
                                       onClick="uiOptionTransfer_return(systems_object); return false;" />
                                <br />
                                <input type="button" value="&laquo;"
                                       title='<bean:message key="delete.all.options.from.target"/>'
                                       onClick="uiOptionTransfer_returnAll(systems_object); return false;" />
                                </td>
                                <td><bean:message key="actual.selection" /><br />
                                <html:select property="selectedBrowsers"
                                             styleId="systems_target" multiple="true"
                                             styleClass="uiOptionTransfer_target">
                                    <html:optionsCollection property="user_systems2" />
                                </html:select></td>
                            </tr>
                        </table>
                        </td>
                        <td width="6" heigh="10"
                            style="background-image: url(images/table5.gif);"></td>
                    </tr>
<!-- Script for this menu--->
<script language="javascript">
var systems_object;


function uiOptionTransfer_init2() {
  var src = document.getElementById('systems_source');

    systems_object = new uiOptionTransfer_Globals(null, null, 'systems_source', 'systems_target', 'systems_distance');
    uiOptionTransfer_fillStates(systems_object);
      uiOptionTransfer_onSubmit(systems_object);
}



uiOptionTransfer_init2();
</script>

    				<tr>
                        <td width="5" heigh="10"
                            style="background-image: url(images/table4.gif);"></td>
                        <td class="tabledata">&nbsp;<bean:message key="select.languages" />:</td>
                        <td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
                    </tr>
                    <!-- Page positions ------>
                    <tr>
                        <td width="5" heigh="10"
                            style="background-image: url(images/table4.gif);"></td>
                        <td class="tabledata">
                        <table border="0" cellspacing="5">
                            <tr>
                                <td><bean:message key="possible.choices" /><br />
                                <html:select property="selectedLangs" multiple="true"
                                             styleId="langs_source"
                                             styleClass="uiOptionTransfer_target">
                                    <html:optionsCollection property="user_langs" />
                                </html:select></td>
                                <td><input type="button" value="&raquo;"
                                           title='<bean:message key="move.all.options.to.target"/>'
                                           onClick="uiOptionTransfer_transferAll(langs_object); return false;" />
                                <br />
                                <input type="button" value="&gt;"
                                       title='<bean:message key="move.selected.options.to.target.list"/>'
                                       onClick="uiOptionTransfer_transfer(langs_object); return false;" />
                                <br />
                                <input type="button" value="&lt;"
                                       title='<bean:message key="delete.selected.options.from.target"/>'
                                       onClick="uiOptionTransfer_return(langs_object); return false;" />
                                <br />
                                <input type="button" value="&laquo;"
                                       title='<bean:message key="delete.all.options.from.target"/>'
                                       onClick="uiOptionTransfer_returnAll(langs_object); return false;" />
                                </td>
                                <td><bean:message key="actual.selection" /><br />
                                <html:select property="selectedLangs"
                                             styleId="langs_target" multiple="true"
                                             styleClass="uiOptionTransfer_target">
                                    <html:optionsCollection property="user_langs2" />
                                </html:select></td>
                            </tr>
                        </table>
                        </td>
                        <td width="6" heigh="10"
                            style="background-image: url(images/table5.gif);"></td>
                    </tr>
<!-- Script for lang menu--->
<script language="javascript">
var langs_object;
function uiOptionTransfer_init3() {
  var src = document.getElementById('langs_source');
  langs_object = new uiOptionTransfer_Globals(null, null, 'langs_source', 'langs_target', 'langs_distance');
  uiOptionTransfer_fillStates(langs_object);
  uiOptionTransfer_onSubmit(langs_object);
}
uiOptionTransfer_init3();
</script>



<script language="javascript">
  function transform(){
    lists = new Array(browsers_object,systems_object,langs_object);
    transform_any_categorys(lists);
  }
</script>

<tr>
    <td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
    <td class="tabledata">
        <table border="0" style="background-color:#ffffff;" cellspacing="1"	width="100%">
            <tr>
                <td height="10" width="10%" class="tabletableheader"><bean:message	key="id" /></td>
                <td height="10" width="60%" class="tabletableheader"><bean:message	key="referrer.value" /></td>
                <td height="10" width="20%" class="tabletableheader"><bean:message	key="referrer.type" /></td>
                <td height="10" width="10%" class="tabletableheader"><bean:message	key="actions" /></td>
            </tr>
            
            <logic:iterate id="entityMy" name="filterActionForm"
                           property="referrersCollection">
                <%ReferrersElement entity = (ReferrersElement) entityMy;%>
				<tr>
					<td height="10" class="tabledata"><nobr><%=entity.getId()%></nobr></td>
					<td height="10" class="tabledata"><nobr><%=entity.getTarget_url()%></nobr></td>
					<td height="10" class="tabledata"><nobr><%=(entity.isType()?"Block":"Target")%></nobr></td>
					<td height="10" class="tabledata-c"><nobr><img
						src="images/icons/remove.png" border="0"
						title=<bean:message key="remove"/> style="cursor: pointer;"
						onclick="document.getElementById('referrerEngineAction').value='remove';document.getElementById('referrerId').value='<%=entity.getId()%>';filterActionForm.submit();" /></nobr></td>
				</tr>
			</logic:iterate>
			<tr>
				<td height="10" class="tabledata" colspan="4">
					<html:text property="referrerUrl" size="60"/>&nbsp;
					<input type="checkbox" name="referrerType"><bean:message key="referrer.type.selection"/></input>
			    </td>
			</tr>
		</table>
	</td>
	<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
</tr>

				<tr>
					<td width="5" heigh="10"
						style="background-image: url(images/table4.gif);"></td>
					<td class="tabledata">
					<hr>
					<table border="0" cellspacing="5" cellpadding="5">
						<tr>
							<td><img src="images/icons/update.png" style="cursor: pointer;"
								onclick="transform();filterActionForm.submit();"
								title="<%=i18nService.fetch("update",request)%>"
								alt="<%=i18nService.fetch("update",request)%>" /></td>

							<%-- <td><img src="images/icons/save.png" style="cursor: pointer;"
								onclick="filterActionForm.templateAction.value='save'; filtersTemplateActionForm.submit();"
								title="<%=Msg.fetch("template.save",request)%>"
								alt="<%=Msg.fetch("template.save",request)%>" /></td>
							<td><img src="images/icons/reset.png" style="cursor: pointer;"
								onclick="filterActionForm.reset();"
								title="<%=Msg.fetch("reset",request)%>"
								alt="<%=Msg.fetch("reset",request)%>" /></td>
							<td> --%>
						</tr>
					</table>
					</td>
					<td width="6" heigh="10"
						style="background-image: url(images/table5.gif);"></td>
				</tr>
	<tr>
		<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
		<td heigh="10" style="background-image: url(images/table8.gif);"></td>
		<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr>
	</html:form>
	</table>
  </td>
</tr>
