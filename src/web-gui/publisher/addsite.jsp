<%@ page import="com.adsapient.api_impl.usermanagment.UserImpl,
                 com.adsapient.util.Msg,
                 com.adsapient.gui.forms.EditPublisherSiteActionForm" %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %>
<%@ taglib uri='/WEB-INF/struts-bean.tld' prefix='bean' %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html:errors/>
<html:messages id="msg" message="true" header="messages.header" footer="messages.footer" >
 <bean:write name="msg" />
</html:messages>

<% EditPublisherSiteActionForm form = (EditPublisherSiteActionForm) request.getAttribute("editPublisherSiteActionForm");%>
<%UserImpl user = (UserImpl) request.getSession().getAttribute("user");%>
<html:form action="publisherEdit">
<tr><td>
    <table width="100%" cellspacing="0" cellpadding="0"><tr>
        <td><img src="images/table1.gif"></td>
        <td width="100%"
            class="tableheader"><%=Msg.fetch(form.getAction() + ".site.title", session)%> <%=(form.getSiteId() != null ? "#" + form.getSiteId().toString() : "")%></td>
        <td><img src="images/table2.gif"></td>
    </tr></table>
</td></tr>
<tr><td>
<table width="100%" cellspacing="0" cellpadding="0">



<tr>
    <td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
    <td height="10" class="maintext">
        <table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><bean:message key="url"/>
            :&nbsp;&nbsp; 
            <% if(form.getTypeId()!= null){
                 if(form.getTypeId() != 2)
                    out.print("<sup id='test'>*</sup>");
                 else
                    out.print("<sup id='test' style='visibility:hidden'>*</sup>");
            }else
                out.print("<sup id='test'>*</sup>");
                %></td></tr></table>
    </td>
    <td height="10" >
        <table border="0" width="100%" cellspacing="5"><tr><td ><html:text
                style="width:200" styleId="urlField" property="url" maxlength="50"/></td></tr></table>
    </td>
    <td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
</tr>

<tr>
    <td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
    <td height="10" class="maintext">
        <table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><bean:message
                key="publish.type"/></td></tr></table>
    </td>
    <td height="10" >
        <table border="0" width="100%" cellspacing="5"><tr><td ><html:select
                property="typeId" styleId="typeField" onchange="javascript:hideValidator(this)" styleClass="defType" value="<%=((form.getTypeId()==null)?"1":form.getTypeId().toString())%>"><html:optionsCollection property="typeCollection"/>
        </html:select></td></tr></table>
    </td>
    <td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
</tr>

<tr>
    <td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
    <td height="10" class="maintext">
        <table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><bean:message
                key="description"/>:&nbsp;&nbsp;<sup>*</sup></td></tr></table>
    </td>
    <td height="10" >
        <table border="0" width="100%" cellspacing="5"><tr><td >
            <html:textarea style="width:200; height:50" styleId="descriptionField" property="description"/></td></tr>
        </table>
    </td>
    <td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
</tr>

<tr>
    <td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
    <td height="10" class="maintext">
        <table border="0" width="100%" cellspacing="5"><tr><td class="maintext"><bean:message
                key="category"/>:</td></tr></table>
    </td>
    
    
        <%--<html:select property="categoryId"><html:optionsCollection property="categoryCollection"/>--%>
        <%--</html:select></td></tr></table>--%>
    
    <td class="tabledata">
        <table border="0" cellspacing="5">
            <tr>
                <td><bean:message key="possible.choices"/><br/>
                    <html:select property="selectedCategorys" multiple="true"
                                 styleId="uiOptionTransfer_source1"
                                 styleClass="uiOptionTransfer_target_small">
                        <html:optionsCollection property="categorys"/>
                    </html:select></td>
                <td><input type="button" value="&raquo;"
                           title='<bean:message key="move.all.options.to.target"/>'
                           onClick="uiOptionTransfer_transferAll(uiOptionTransfer_object1); selectionChanged();return false;"/>
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
                           onClick="uiOptionTransfer_returnAll(uiOptionTransfer_object1); selectionChanged();return false;"/>
                </td>
                <td><bean:message key="actual.selection"/><br/>
                    <html:select property="selectedCategorys"
                                 styleId="uiOptionTransfer_target1"
                                 multiple="true"
                                 onchange="selectionChanged();"
                                 styleClass="uiOptionTransfer_target_small">
                        <html:optionsCollection property="categorys2"/>
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


<!-- Script for this menu--->
<script language="javascript">
    var uiOptionTransfer_object1;
                                                                                                              

    function uiOptionTransfer_init1() {
        var src = document.getElementById('uiOptionTransfer_source1');

        uiOptionTransfer_object1 = new uiOptionTransfer_Globals(null, null, 'uiOptionTransfer_source1', 'uiOptionTransfer_target1', 'uiOptionTransfer_distance1');
        uiOptionTransfer_fillStates(uiOptionTransfer_object1);
        uiOptionTransfer_onSubmit(uiOptionTransfer_object1);
    }


    sliderValuesArray = new Object();
    currentlySelectedCategoryIndex = 0;


    uiOptionTransfer_init1();
</script>

<script language="javascript">
    function hideValidator(form){
        var selindex = form.value;
        if(selindex == 2)
            document.getElementById("test").style.visibility="hidden";
        else
            document.getElementById("test").style.visibility="visible";

    }
</script>


<% if (user.isOwnCampaignsAllow()) {%>

<tr>
    <td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
    <td height="10" class="maintext">&nbsp;<bean:message key="own.campaigns.text"/></td>
    <td height="10" ><html:checkbox property="ownCampaigns"/></td>
    <td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
</tr>
<%} %>

</html:form>

<tr>
    <td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
    <td ></td>
    <td height="10" >
        <html:form action="publisherEdit">
            <html:hidden property="siteId"/>
            <html:hidden property="action"/>
            <html:hidden property="userId"/>
            <html:hidden property="typeId"
                         styleId="type"/>
            <html:hidden property="url"
                         styleId="urlId"/>
            <html:hidden property="description"
                         styleId="descriptionId"/>
            <html:hidden property="selectedCategorysName"
                         styleId="selectedCategorysId"/>
            <table border="0" width="100%" cellspacing="5"><tr><td >
                <input type="submit" value='<%=Msg.fetch(form.getAction()+".site.button",request)%>'
                       onclick="selectionChanged();transform_site_categorys(uiOptionTransfer_object1,true);editPublisherSiteActionForm.submit();">
                <%if ("edit".equals(form.getAction())) {%>
                &nbsp;&nbsp;&nbsp;<input type="button" value='<bean:message key="remove"/>'
                                         onclick="selectionChanged();if( window.confirm('<%=Msg.fetch("delete.confirm",request)%>')) {document.location='<%=response.encodeURL("publisherEdit.do?action=remove&siteId="+form.getSiteId())%>'}">
                <%}%>
            </td></tr></table>
        </html:form>
    </td>
    <td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
</tr>
<tr>
    <td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
    <td height="10" colspan="2" class="maintext">
            <bean:message key="form.msg.mandatory"/>
    </td>
    <td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
</tr>


</table>
</td></tr>


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

