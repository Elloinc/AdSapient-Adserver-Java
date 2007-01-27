<%@ page import="com.adsapient.api_impl.usermanagment.UserImpl,
                 com.adsapient.gui.ContextAwareGuiBean" %>
<%@ page import="com.adsapient.shared.service.LinkHelperService" %>
<%@ page import="com.adsapient.util.Msg" %>
<%@ page import="com.adsapient.gui.forms.PlacesEditActionForm" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %>
<%@ taglib uri='/WEB-INF/struts-bean.tld' prefix='bean' %>
<%@ page contentType="text/html; charset=UTF-8" %>



<% ApplicationContext ctx = ContextAwareGuiBean.ctx;
 LinkHelperService lhs = (LinkHelperService) ctx.getBean("linkHelperService");
    PlacesEditActionForm form = (PlacesEditActionForm) request.getAttribute("placesEditActionForm");
    UserImpl user = (UserImpl) session.getAttribute(com.adsapient.util.admin.AdsapientConstants.USER);
// set sites Collection
//    form.getSitesCollection(request);
// request
    form.setRequest(request);%>
<html:errors/>
<html:messages id="msg" message="true" header="messages.header" footer="messages.footer" >
 <bean:write name="msg" />
</html:messages>
<html:form action="placeEdit">
<html:hidden property="placesId"/>
<html:hidden property="action"/>
<html:hidden property="selectedCategorysName"
             styleId="selectedCategorysId"/>

<tr><td>
    <table width="100%" cellspacing="0" cellpadding="0"><tr>
        <td><img src="images/table1.gif"></td>
        <td width="100%"
            class="tableheader"><%=Msg.fetch(form.getAction() + ".adplace.title", session)%> <%=(form.getPlacesId() != null ? "#" + form.getPlacesId() : "")%></td>
        <td><img src="images/table2.gif"></td>
    </tr></table>
</td></tr>
<tr><td>
<table width="100%" cellspacing="0" cellpadding="0">



<tr>
    <td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
    <td height="10" class="maintext">
        <table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><bean:message key="url.place"/>:</td>
        </tr></table>
    </td>
    <td height="10" class="maintext">
        <table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><%=form.getUrl()%></td>
        </tr></table>
    </td>

    <html:hidden property="siteId" value="<%=form.getSiteId().toString()%>"/>

        <%--
        <td height="10" >
            <table border="0" width="100%" cellspacing="5"><tr><td ><html:select
                    property="siteId" styleClass="defType"><html:optionsCollection property="sitesCollection"/>
            </html:select></td></tr></table>
        </td>
        --%>
    <td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
</tr>

<tr>
    <td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
    <td height="10" class="maintext">
        <table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><bean:message key="position"/>:</td>
        </tr></table>
    </td>
    <td height="10" >
        <table border="0" width="100%" cellspacing="5"><tr><td ><html:select
                property="placeId" styleClass="defType"><html:optionsCollection property="placeCollection"/>
        </html:select></td></tr></table>
    </td>
    <td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
</tr>

<tr>
    <td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
    <td height="10" class="maintext">
        <table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><bean:message key="size"/>:</td></tr>
        </table>
    </td>
    <td height="10" >
        <table border="0" width="100%" cellspacing="5"><tr><td ><html:select
                property="sizeId" styleClass="defType"><html:optionsCollection property="sizesCollection"/>
        </html:select></td></tr></table>
    </td>
    <td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
</tr>


<tr>
    <td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
    <td height="10" class="maintext">
        <table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><bean:message key="category"/>:</td>
        </tr></table>
    </td>
    <td class="tabledata">
        <table border="0" cellspacing="5">
            <tr>
                <td><bean:message key="possible.choices"/><br/>
                    <html:select
                            property="categoryCollection"
                            multiple="true"
                            styleId="uiOptionTransfer_source1"
                            styleClass="uiOptionTransfer_target_small">
                        <html:optionsCollection property="categoryCollection"/>
                    </html:select></td>
                <td><input type="button" value="&raquo;"
                           title='<bean:message key="move.all.options.to.target"/>'
                           onClick="uiOptionTransfer_transferAll(uiOptionTransfer_object1);selectionChanged(); return false;"/>
                    <br/>
                    <input type="button" value="&gt;"
                           title='<bean:message key="move.selected.options.to.target.list"/>'
                           onClick="uiOptionTransfer_transfer(uiOptionTransfer_object1); selectionChanged();return false;"/>
                    <br/>
                    <input type="button" value="&lt;"
                           title='<bean:message key="delete.selected.options.from.target"/>'
                           onClick="uiOptionTransfer_return(uiOptionTransfer_object1); selectionChanged();return false;"/>
                    <br/>
                    <input type="button" value="&laquo;"
                           title='<bean:message key="delete.all.options.from.target"/>'
                           onClick="uiOptionTransfer_returnAll(uiOptionTransfer_object1);selectionChanged(); return false;"/>
                </td>
                <td><bean:message key="actual.selection"/><br/>
                    <html:select property="selectedCategorysName"
                                 styleId="uiOptionTransfer_target1"
                                 multiple="true"
                                 onchange="selectionChanged();"
                                 styleClass="uiOptionTransfer_target_small">
                        <html:optionsCollection property="categorys"/>
                    </html:select>
                    <html:hidden styleId="categoriesPriorities" property="categoriesPriorities"/>
                </td>
                
                <%--<td><div id="slider" /></td>--%>
                <td><div id="slider" style="visibility:hidden;" /></td>
            </tr>
        </table>
    </td>
    <td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
</tr>
<script language="javascript">
    var uiOptionTransfer_object1;


    function uiOptionTransfer_init1() {
        var src = document.getElementById('uiOptionTransfer_source1');

        uiOptionTransfer_object1 = new uiOptionTransfer_Globals(null, null, 'uiOptionTransfer_source1', 'uiOptionTransfer_target1', 'uiOptionTransfer_distance1');
        uiOptionTransfer_fillStates(uiOptionTransfer_object1);
        uiOptionTransfer_onSubmit(uiOptionTransfer_object1);
    }

    uiOptionTransfer_init1();

    sliderValuesArray = new Object();
    currentlySelectedCategoryIndex = 0;


</script>
<% if(form.getTypeId()==1) { %>


<tr>
    <td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
    <td height="10" class="maintext">
        <table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><bean:message key="target.window"/></td>
        </tr></table>
    </td>
    <td height="10" >
        <table border="0" width="100%" cellspacing="5"><tr><td ><html:select
                property="targetWindowId" styleClass="defType" onchange="targetSelect(placesEditActionForm,0);">
            <html:optionsCollection property="targetWindowCollection"/></html:select></td></tr></table>
    </td>
    <td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
</tr>


<tr>
    <td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
    <td height="10" class="maintext">
        <table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><bean:message
                key="inventory.type"/></td></tr></table>
    </td>
    <td height="10" >
        <table border="0" width="100%" cellspacing="5"><tr><td ><html:select
                property="placeTypeId" styleClass="defType" onchange="placeSelect(placesEditActionForm,0);">
            <html:optionsCollection property="placeTypeCollection"/></html:select></td></tr></table>
    </td>
    <td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
</tr>


<tr>
    <td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
    <td height="10" valign="top" class="maintext">
        <table valign="top" border="0" width="100%" cellspacing="5"><tr><td valign="top" class="maintext"><bean:message
                key="htmlsource"/>:</td></tr></table>
    </td>
    <td height="10" >
        <table border="0" width="100%" cellspacing="5"><tr><td ><tr><td>
            
            <textarea name="placeSrc" readonly="readonly" style="width:400px;height:170px"><%=lhs.getPlaceCodeByPlaceId(form.getPlacesId(), request)%></textarea>
        </td></tr></table>
    </td>
    <td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
</tr>
<% } %>

<tr>
    <td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
    <td ></td>
    <td height="10" >
        <table border="0" width="100%" cellspacing="5"><tr><td >
            <input type="submit"
                   value='<%=Msg.fetch(form.getAction()+".adplace.button",session)%>'
                   onclick="undisableComboBoxes(placesEditActionForm);selectionChanged();transform_place_categorys(uiOptionTransfer_object1,true);placesEditActionForm.submit();">
            &nbsp;&nbsp;

            <%--<%if (form.getPlacesId() != null) {%>--%>
            
            <%--<%=Msg.fetch("adplace.demo",request)%>--%>
            
                   
                   <%--<%=form.getDemoHref()%>--%>
                   
            <%--<%}%>--%>
            <%if ("edit".equals(form.getAction())) {%>
            &nbsp;&nbsp;&nbsp;<input type="button" value='<bean:message key="remove"/>'
                                     onclick="selectionChanged();if( window.confirm('<%=Msg.fetch("delete.confirm",request)%>')) {document.location='<%=response.encodeURL("placeEdit.do?placeAction=remove&placesId="+form.getPlacesId())%>'}">
            <%}%>
        </td></tr></table>
    </td>
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
