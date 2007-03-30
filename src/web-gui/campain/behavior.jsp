<%@ page import="com.adsapient.gui.forms.BehaviorActionForm"%>
<%@ page import="com.adsapient.shared.service.I18nService"%>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %>
<%@ taglib uri='/WEB-INF/struts-bean.tld' prefix='bean' %>
<%@ page contentType="text/html;charset=UTF-8"%>

<%BehaviorActionForm form=(BehaviorActionForm) request.getAttribute("behaviorActionForm");%>


<html:errors/>
<html:messages id="msg" message="true" header="messages.header" footer="messages.footer" >
 <bean:write name="msg" />
</html:messages>

<html:form action="behaviorAction" >
<html:hidden property="id"/>
<html:hidden property="action"/>


<tr><td>
	<table width="100%"cellspacing="0" cellpadding="0"><tr>
	<td><img src="images/table1.gif"></td>
	<td width="100%" class="tableheader"><%=Msg.fetch(form.getAction()+".patter.box",request)%><%="edit".equals(form.getAction())?form.getId():""%></td>
	<td><img src="images/table2.gif"></td>
	</tr></table>
</td></tr>
<tr><td>
	<table width="100%" cellspacing="0" cellpadding="0">
	

	<tr>
	<td width="5"  style="background-image: url(images/table4.gif);"></td>
	<td height="10" class="maintext"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><bean:message key="name"/>:</td></tr></table></td>
	<td height="10"  ><table border="0" width="100%" cellspacing="5"><tr><td ><html:text property="name" styleId="nameId" size="45"/></td></tr></table></td>
	<td width="6"  style="background-image: url(images/table5.gif);"></td>
	</tr>

	


	<tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td height="10"  class="maintext"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><bean:message key="recency"/>:</td></tr> 
	                           														 <tr><td class="smalltext"><bean:message key="recency.description"/></td></tr></table></td>
	<td height="10"  ><table border="0" width="100%" cellspacing="5"><tr><td ><html:text property="recency" styleId="recencyId" size="4"/></td></tr></table></td>
	<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr>
	
	<tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td height="10"  class="maintext"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><bean:message key="frequency"/>:</td></tr>
	                                                  								 <tr><td class="smalltext"><bean:message key="frequency.description"/></td></tr></table></td>
	<td height="10"  ><table border="0" width="100%" cellspacing="5"><tr><td ><html:select property="frequencyNumbers" styleId="frequencyNumbersId" styleClass="defSmall" ><html:optionsCollection property="numbersCollection"/></html:select>
	&nbsp;&nbsp;<html:select property="frequencyDays" styleId="frequencyDaysId" styleClass="defSmall" ><html:optionsCollection property="frequencyCollection"/></html:select></td></tr></table></td>
	<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr>
	
	<tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td height="10"  class="maintext"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><bean:message key="duration"/>:</td></tr>
	                                   												 <tr><td class="smalltext"><bean:message key="duration.description"/></td></tr></table></td>
	<td height="10"  ><table border="0" width="100%" cellspacing="5"><tr><td ><html:select  styleId="durationId" property="duration" styleClass="defSmall" ><html:optionsCollection property="durationCollection"/></html:select>	</td></tr></table></td>
	<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr>  
	
	
	<tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td height="10"  class="maintext"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><bean:message key="keywords"/>:</td></tr>
																					 <tr><td style="width:100px;"class="smalltext"><bean:message key="keywords.description"/></td></tr></table></td>
	<td height="10"  ><table border="0" width="100%" cellspacing="5"><tr><td ><html:textarea property="keyWords"  styleId="keyWordsId" style="width:350px;height:75px" />	</td></tr></table></td>
	<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr>  
	
	<tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td height="10" colspan="2"  class="maintext"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><bean:message key="category.label"/>:</td></tr></table></td>
	<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr>  
	
	
	<tr>
				<td width="5" heigh="10"
					style="background-image: url(images/table4.gif);">
				</td>
				<td class="tabledata" colspan="2">
				<table border="0" width="100%" cellspacing="5">
					<tr>
						<td><bean:message key="possible.choices" /></br>
						<html:select property="selectedCategorys" multiple="true"
							styleId="uiOptionTransfer_source1"
							styleClass="uiOptionTransfer_target">
							<html:optionsCollection property="categorysCollection" />
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
						<html:select property="selectedCategorys"
							styleId="uiOptionTransfer_target1" multiple="true"
							styleClass="uiOptionTransfer_target">
							<html:optionsCollection property="categorysCollection2" />
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
<html:form action="behaviorAction" onsubmit="transform_pattern(uiOptionTransfer_object1);">
<html:hidden property="id"/>
<html:hidden property="action"/>
<html:hidden property="name" styleId="nameIdNew"/>
<html:hidden property="recency" styleId="recencyIdNew"/>
<html:hidden property="frequencyDays" styleId="frequencyDaysIdNew"/>
<html:hidden property="frequencyNumbers" styleId="frequencyNumbersIdNew"/>
<html:hidden property="duration" styleId="durationIdNew"/>
<html:hidden property="keyWords" styleId="keywordsIdNew"/>
<html:hidden property="selectedCategorys" styleId="selectedCategorysIdNew"/>


	
	
	<tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td ></td>
	<td height="10" ><table border="0" width="100%" cellspacing="5"><tr><td ><input type="submit"
	value='<%=Msg.fetch(form.getAction()+".patter.button",request)%>' onsubmit=""></td></tr></table></td>
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
 </html:form>
