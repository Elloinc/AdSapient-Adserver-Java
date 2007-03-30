<%@ page import="com.adsapient.gui.forms.FilterActionForm"%>
<%@ page import="com.adsapient.shared.AdsapientConstants"%>
<%@ page import="com.adsapient.shared.service.I18nService"%>
<%@ page import="com.adsapient.shared.mappable.KeyWordsFilterElement"%>
<%@ page import="com.adsapient.shared.mappable.ReferrersElement"%>
<%@ page import="com.adsapient.gui.ContextAwareGuiBean"%>
<%@ page import="org.springframework.context.ApplicationContext"%>
<%@ page import="com.adsapient.shared.service.LinkHelperService"%>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %>
<%@ taglib uri='/WEB-INF/struts-bean.tld' prefix='bean' %>
<%@ page contentType="text/html; charset=UTF-8" %>


<% ApplicationContext ctx = ContextAwareGuiBean.getContext();%>
<% LinkHelperService lhs = (LinkHelperService) ctx.getBean("linkHelperService");%>

<%FilterActionForm form = (FilterActionForm) request
        .getAttribute("filtersTemplateActionForm");
    int i = 1;

%>

<html:errors/>
<html:messages id="msg" message="true" header="messages.header" footer="messages.footer" >
 <bean:write name="msg" />
</html:messages>

<tr>
    <td>
        <table width="100%" cellspacing="0" cellpadding="0">
            <tr>
                <td><img src="images/table1.gif"></td>
                <td width="100%" class="tableheader"><bean:message
                        key="targetingoptions"/></td>
                <td><img src="images/table2.gif"></td>
            </tr>
        </table>
    </td>
</tr>

<tr>
    <td>
        <table style="background-image: url(images/tab6.gif);" width="100%"
               cellspacing="0" cellpadding="0">
            <tr>
                <td width="5" style="background-image: url(images/table4.gif);"></td>
                <td height="5" style="background-color:E8EFFF;"></td>
                <td width="6" style="background-image: url(images/table5.gif);"></td>
            </tr>
            <tr>
                <td width="5" style="background-image: url(images/table4.gif);"></td>

                <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                        <%=lhs.generateFiltersTempletHeader(form, request,
                                response)%>
                    </table>
                </td>

                <td width="6" style="background-image: url(images/table5.gif);"></td>
            </tr>
        </table>

    </td>
</tr>


<%if (AdsapientConstants.TIME_FILTER.equalsIgnoreCase(form
        .getFilterType())) {%>

<tr>
<td>
<table width="100%" cellspacing="0" cellpadding="0">
<html:form action="filtersTemplate">
    <html:hidden property="filterType"/>
    <html:hidden property="dataSource"/>
    <html:hidden property="templateAction"/>
    <html:hidden property="enableAutoUpdate"/>
    <html:hidden property="excludeHours"/>

    <tr>
        <td width="5" style="background-image: url(images/table4.gif);"></td>
        <td class="tabledata">
            <table border="0" width="100%" cellspacing="5">
                <tr>
                    <td class="tabledata"><bean:message key="input.template.name"/>:&nbsp;
                        <html:text property="templateName"/></td>
                </tr>
            </table>
        </td>
        <td width="6" style="background-image: url(images/table5.gif);"></td>
    </tr>
    <tr>
        <td width="5" style="background-image: url(images/table4.gif);"></td>
        <td class="tabledata">
            <table border="0" width="100%" cellspacing="5">
                <tr>
                    <td class="tabledata"><bean:message key="select.hours"/>:</td>
                </tr>
            </table>
        </td>
        <td width="6" style="background-image: url(images/table5.gif);"></td>
    </tr>
    <tr>
        <td width="5" style="background-image: url(images/table4.gif);"></td>
        <td class="tabledata">
            <table border="0">
                <tr>
                    <logic:iterate id="valueMy" name="filtersTemplateActionForm"
                                   property="excludeHoursCollection">
                    <%String value = (String) valueMy;%>
                    <td><input type="checkbox" name="<%="hour"+i%>"
                        <%="y".equalsIgnoreCase(value)?"checked":""%>><%=i%><%i++;%><%if ((i - 1) % 12 == 0) {%></td>
                </tr>
                <tr>
                        <%}%>
        </td>
        </logic:iterate>
    </tr>
    </table>
    </td>
    <td width="6" style="background-image: url(images/table5.gif);"></td>
    </tr>
    <tr>
        <td width="5" style="background-image: url(images/table4.gif);"></td>
        <td class="tabledata">
            <hr/>
            <table border="0" cellspacing="5" cellpadding="5">
                <tr>
                    <td><img src="images/icons/update.png" style="cursor: pointer;"
                             onclick="filtersTemplateActionForm.submit();"
                             title="<%=i18nService.fetch("update",request)%>"
                             alt="<%=i18nService.fetch("update",request)%>"/></td>
                    <td><img src="images/icons/save.png" style="cursor: pointer;"
                             onclick="filtersTemplateActionForm.templateAction.value='save'; filtersTemplateActionForm.submit();"
                             title="<%=i18nService.fetch("template.save",request)%>"
                             alt="<%=i18nService.fetch("template.save",request)%>"/></td>
                    <td><img src="images/icons/reset.png" style="cursor: pointer;"
                             onclick="filtersTemplateActionForm.reset();"
                             title="<%=i18nService.fetch("reset",request)%>"
                             alt="<%=i18nService.fetch("reset",request)%>"/></td>
                </tr>
            </table>

        </td>
        <td width="6" style="background-image: url(images/table5.gif);"></td>
    </tr>
    </table>
    </td>
    </tr>
    <tr>
        <td>
            <table width="100%" cellspacing="0" cellpadding="0">
                <tr>
                    <td><img src="images/table6.gif"></td>
                    <td width="100%" style="background-image: url(images/table8.gif);"></td>
                    <td><img src="images/table7.gif"></td>
                </tr>
            </table>
        </td>
    </tr>
</html:form>
<%}%>





<%if (AdsapientConstants.DATE_FILTER.equalsIgnoreCase(form
        .getFilterType())) {%>

<tr>
<td>
<table width="100%" cellspacing="0" cellpadding="0">
<html:form action="filtersTemplate">
    <html:hidden property="filterType"/>
    <html:hidden property="dataSource"/>
    <html:hidden property="templateAction"/>
    <html:hidden property="enableAutoUpdate"/>
    <html:hidden property="valuesOfTheDay"/>


    <tr>
        <td width="5" style="background-image: url(images/table4.gif);"></td>
        <td class="tabledata">
            <table border="0" width="100%" cellspacing="5">
                <tr>
                    <td class="tabledata"><bean:message key="input.template.name"/>:&nbsp;
                        <html:text property="templateName"/></td>
                </tr>
            </table>
        </td>
        <td width="6" style="background-image: url(images/table5.gif);"></td>
    </tr>
    <tr>
        <td width="5" style="background-image: url(images/table4.gif);"></td>
        <td class="tabledata">&nbsp;<bean:message key="select.days"/>:</td>
        <td width="6" style="background-image: url(images/table5.gif);"></td>
    </tr>

    <logic:iterate id="valueMy" name="filtersTemplateActionForm"
                   property="valuesOfTheDayCollection">
        <%String value = (String) valueMy;%>
        <tr>
            <td width="5" style="background-image: url(images/table4.gif);"></td>
            <td class="tabledata">&nbsp;<input type="checkbox"
                                               name="<%="day"+i%>" <%="y".equalsIgnoreCase(value)?"checked":""%>>
                <%=i18nService.fetch("day" + i, session)%><%i++;%></td>
            <td width="6" style="background-image: url(images/table5.gif);"></td>
        </tr>
    </logic:iterate>
    <tr>
        <td width="5" style="background-image: url(images/table4.gif);"></td>
        <td class="tabledata">
            <hr/>
            <table border="0" cellspacing="5" cellpadding="5">
                <tr>
                    <td><img src="images/icons/update.png" style="cursor: pointer;"
                             onclick="filtersTemplateActionForm.submit();"
                             title="<%=i18nService.fetch("update",request)%>"
                             alt="<%=i18nService.fetch("update",request)%>"/></td>
                    <td><img src="images/icons/save.png" style="cursor: pointer;"
                             onclick="filtersTemplateActionForm.templateAction.value='save'; filtersTemplateActionForm.submit();"
                             title="<%=i18nService.fetch("template.save",request)%>"
                             alt="<%=i18nService.fetch("template.save",request)%>"/></td>
                    <td><img src="images/icons/reset.png" style="cursor: pointer;"
                             onclick="filtersTemplateActionForm.reset();"
                             title="<%=i18nService.fetch("reset",request)%>"
                             alt="<%=i18nService.fetch("reset",request)%>"/></td>
                </tr>
            </table>

        </td>
        <td width="6" style="background-image: url(images/table5.gif);"></td>
    </tr>
    </table>
    </td>
    </tr>
    <tr>
        <td>
            <table width="100%" cellspacing="0" cellpadding="0">
                <tr>
                    <td><img src="images/table6.gif"></td>
                    <td width="100%" style="background-image: url(images/table8.gif);"></td>
                    <td><img src="images/table7.gif"></td>
                </tr>
            </table>
        </td>
    </tr>
</html:form>
<%}%>





<%if (AdsapientConstants.GEO_FILTER.equalsIgnoreCase(form
        .getFilterType())) {%>

<tr>
<td>
<table width="100%" cellspacing="0" cellpadding="0">
<html:form action="filtersTemplate">
<tr>
    <td width="5" style="background-image: url(images/table4.gif);"></td>
    <td class="tabledata">
        <table border="0" width="100%" cellspacing="5">
            <tr>
                <td class="tabledata"><bean:message key="input.template.name"/>:&nbsp;
                    <html:text property="templateName" styleId="oldTemplateName"/></td>
            </tr>
        </table>
    </td>
    <td width="6" style="background-image: url(images/table5.gif);"></td>
</tr>
<tr>
    <td width="5" heigh="10"
        style="background-image: url(images/table4.gif);"></td>
    <td class="tabledata">&nbsp;<bean:message key="select.countries"/></td>
    <td width="6" style="background-image: url(images/table5.gif);"></td>
</tr>

<tr>
<td width="5" heigh="10"
    style="background-image: url(images/table4.gif);"></td>
<td class="tabledata">
    <table border="0" width="100%" cellspacing="5">
        <tr>
            <td><bean:message key="possible.choices"/></br>
            <html:select property="countries" multiple="true"
                         styleId="uiOptionTransfer_source1"
                         styleClass="uiOptionTransfer_target">
                <html:optionsCollection property="countryNameToCodeCollection"/>
    </html:select></td>
<td><input type="button" value="&raquo;"
           title='<bean:message key="move.all.options.to.target"/>'
           onClick="uiOptionTransfer_transferAll(uiOptionTransfer_object1); return false;"/>
    <br/>
    <input type="button" value="&gt;"
           title='<bean:message key="move.selected.options.to.target.list"/>'
           onClick="uiOptionTransfer_transfer(uiOptionTransfer_object1); return false;"/>
    <br/>
    <input type="button" value="&lt;"
           title='<bean:message key="delete.selected.options.from.target"/>'
           onClick="uiOptionTransfer_return(uiOptionTransfer_object1); return false;"/>
    <br/>
    <input type="button" value="&laquo;"
           title='<bean:message key="delete.all.options.from.target"/>'
           onClick="uiOptionTransfer_returnAll(uiOptionTransfer_object1); return false;"/>
</td>
<td><bean:message key="actual.selection"/></br>
<html:select property="countries2"
             styleId="uiOptionTransfer_target1" multiple="true"
             styleClass="uiOptionTransfer_target">
    <html:optionsCollection property="containedCountryNames"/>
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



    uiOptionTransfer_init1();
</script>
<tr>
    <td width="5" heigh="10"
        style="background-image: url(images/table4.gif);"></td>
    <td class="tabledata">&nbsp;<bean:message key="select.cities"/></td>
    <td width="6" style="background-image: url(images/table5.gif);"></td>
</tr>
<tr>
    <td width="5" heigh="10"
        style="background-image: url(images/table4.gif);"></td>
    <td class="tabledata">
        <table border="0" width="100%" cellspacing="5">
            <tr>
                <td><bean:message key="possible.choices"/></br>
                <html:select property="cities" multiple="true"
                             styleId="uiOptionTransfer_source2"
                             styleClass="uiOptionTransfer_target">
                    <html:optionsCollection property="cityNameToCodeCollection"/>
        </html:select></td>
    <td><input type="button" value="&raquo;"
               title='<bean:message key="move.all.options.to.target"/>'
               onClick="uiOptionTransfer_transferAll(uiOptionTransfer_object2); return false;"/>
        <br/>
        <input type="button" value="&gt;"
               title='<bean:message key="move.selected.options.to.target.list"/>'
               onClick="uiOptionTransfer_transfer(uiOptionTransfer_object2); return false;"/>
        <br/>
        <input type="button" value="&lt;"
               title='<bean:message key="delete.selected.options.from.target"/>'
               onClick="uiOptionTransfer_return(uiOptionTransfer_object2); return false;"/>
        <br/>
        <input type="button" value="&laquo;"
               title='<bean:message key="delete.all.options.from.target"/>'
               onClick="uiOptionTransfer_returnAll(uiOptionTransfer_object2); return false;"/>
    </td>
    <td><bean:message key="actual.selection"/></br>
    <html:select property="cities2" styleId="uiOptionTransfer_target2"
                 multiple="true" styleClass="uiOptionTransfer_target">
        <html:optionsCollection property="containedCityNames"/>
    </html:select></td>
</tr>
</table>
</td>
<td width="6" heigh="10"
    style="background-image: url(images/table5.gif);"></td>
</tr>
<script language="javascript">
    var uiOptionTransfer_object2;


    function uiOptionTransfer_init2() {
        var src = document.getElementById('uiOptionTransfer_source2');

        uiOptionTransfer_object2 = new uiOptionTransfer_Globals(null, null, 'uiOptionTransfer_source2', 'uiOptionTransfer_target2', 'uiOptionTransfer_distance2');
        uiOptionTransfer_fillStates(uiOptionTransfer_object2);
        uiOptionTransfer_onSubmit(uiOptionTransfer_object2);
    }

    uiOptionTransfer_init2();
</script>

</html:form>
<html:form action="filtersTemplate">
    <html:hidden property="filterType"/>
    <html:hidden property="dataSource"/>
    <html:hidden property="templateAction"/>
    <html:hidden property="enableAutoUpdate"/>
    <html:hidden property="templateName" styleId="newTemplateName"/>
    <html:hidden property="selectedCountrys" styleId="selectedCountrysId"/>
    <html:hidden property="selectedCities" styleId="selectedCitiesId"/>

    
    <tr>
        <td width="5" heigh="10"
            style="background-image: url(images/table4.gif);"></td>
        <td class="tabledata">
            <hr/>
            <table border="0" cellspacing="5" cellpadding="5">
                <tr>
                    <td><img src="images/icons/update.png" style="cursor: pointer;"
                             onclick="transform_data(uiOptionTransfer_object1,true);filtersTemplateActionForm[1].submit();"
                             title="<%=Msg.fetch("update",request)%>"
                             alt="<%=Msg.fetch("update",request)%>"/></td>
                    <td><img src="images/icons/save.png" style="cursor: pointer;"
                             onclick="filtersTemplateActionForm[1].templateAction.value='save'; filtersTemplateActionForm[1].submit();"
                             title="<%=Msg.fetch("template.save",request)%>"
                             alt="<%=Msg.fetch("template.save",request)%>"/></td>
                    <td><img src="images/icons/reset.png" style="cursor: pointer;"
                             onclick="filtersTemplateActionForm[1].reset();"
                             title="<%=Msg.fetch("reset",request)%>"
                             alt="<%=Msg.fetch("reset",request)%>"/></td>
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
<%}%>




<%if (AdsapientConstants.BEHAVIOR_FILTER.equalsIgnoreCase(form
        .getFilterType())) {%>

<tr>
<td>
<table width="100%" cellspacing="0" cellpadding="0">
<html:form action="filtersTemplate">
    <tr>
        <td width="5" style="background-image: url(images/table4.gif);"></td>
        <td class="tabledata">
            <table border="0" width="100%" cellspacing="5">
                <tr>
                    <td class="tabledata"><bean:message key="input.template.name"/>:&nbsp;
                        <html:text property="templateName" styleId="oldTemplateName"/></td>
                </tr>
            </table>
        </td>
        <td width="6" style="background-image: url(images/table5.gif);"></td>
    </tr>
    <tr>
        <td width="5" heigh="10"
            style="background-image: url(images/table4.gif);"></td>
        <td class="tabledata">&nbsp;<bean:message key="select.behavior"/></td>
        <td width="6" style="background-image: url(images/table5.gif);"></td>
    </tr>
    
    <tr>
    <td width="5" heigh="10"
        style="background-image: url(images/table4.gif);"></td>
    <td class="tabledata">
        <table border="0" width="100%" cellspacing="5">
            <tr>
                <td><bean:message key="possible.choices"/></br>
                <html:select property="countries" multiple="true"
                             styleId="uiOptionTransfer_source1"
                             styleClass="uiOptionTransfer_target">
                    <html:optionsCollection property="elements"/>
        </html:select></td>
    <td><input type="button" value="&raquo;"
               title='<bean:message key="move.all.options.to.target"/>'
               onClick="uiOptionTransfer_transferAll(uiOptionTransfer_object1); return false;"/>
        <br/>
        <input type="button" value="&gt;"
               title='<bean:message key="move.selected.options.to.target.list"/>'
               onClick="uiOptionTransfer_transfer(uiOptionTransfer_object1); return false;"/>
        <br/>
        <input type="button" value="&lt;"
               title='<bean:message key="delete.selected.options.from.target"/>'
               onClick="uiOptionTransfer_return(uiOptionTransfer_object1); return false;"/>
        <br/>
        <input type="button" value="&laquo;"
               title='<bean:message key="delete.all.options.from.target"/>'
               onClick="uiOptionTransfer_returnAll(uiOptionTransfer_object1); return false;"/>
    </td>
    <td><bean:message key="actual.selection"/></br>
    <html:select property="selectedElementsValue"
                 styleId="uiOptionTransfer_target1" multiple="true"
                 styleClass="uiOptionTransfer_target">
        <html:optionsCollection property="selectedElements"/>
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



        uiOptionTransfer_init1();
    </script>

</html:form>
<html:form action="filtersTemplate">
    <html:hidden property="filterType"/>
    <html:hidden property="dataSource"/>
    <html:hidden property="templateAction"/>
    <html:hidden property="enableAutoUpdate"/>
    <html:hidden property="templateName" styleId="newTemplateName"/>
    <html:hidden property="selectedElementsValue" styleId="selectedElementsValueId"/>


    
    <tr>
        <td width="5" heigh="10"
            style="background-image: url(images/table4.gif);"></td>
        <td class="tabledata">
            <hr/>
            <table border="0" cellspacing="5" cellpadding="5">
                <tr>
                    <td><img src="images/icons/update.png" style="cursor: pointer;"
                             onclick="transform_patterns(uiOptionTransfer_object1,true);filtersTemplateActionForm[1].submit();"
                             title="<%=Msg.fetch("update",request)%>"
                             alt="<%=Msg.fetch("update",request)%>"/></td>
                    <td><img src="images/icons/save.png" style="cursor: pointer;"
                             onclick="filtersTemplateActionForm[1].templateAction.value='save'; filtersTemplateActionForm[1].submit();"
                             title="<%=Msg.fetch("template.save",request)%>"
                             alt="<%=Msg.fetch("template.save",request)%>"/></td>
                    <td><img src="images/icons/reset.png" style="cursor: pointer;"
                             onclick="filtersTemplateActionForm[1].reset();"
                             title="<%=Msg.fetch("reset",request)%>"
                             alt="<%=Msg.fetch("reset",request)%>"/></td>
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
<%}%>






<%if (AdsapientConstants.TRAFFIC_FILTER.equalsIgnoreCase(form
        .getFilterType())) {%>

<table width="100%" cellspacing="0" cellpadding="0">
<tr>
<td>
<table width="100%" cellspacing="0" cellpadding="0">
<html:form action="filtersTemplate">
<html:hidden property="filterType"/>
<html:hidden property="dataSource"/>
<html:hidden property="templateAction"/>
<html:hidden property="enableAutoUpdate"/>


<tr>
    <td width="5" style="background-image: url(images/table4.gif);"></td>
    <td class="tabledata">
        <table border="0" width="100%" cellspacing="5">
            <tr>
                <td class="tabledata"><bean:message key="input.template.name"/>:&nbsp;
                    <html:text property="templateName"/></td>
            </tr>
        </table>
    </td>
    <td width="6" style="background-image: url(images/table5.gif);"></td>
</tr>

<tr>
    <td width="5" heigh="10"
        style="background-image: url(images/table4.gif);"></td>
    <td class="tabledata">
        <table border="0" width="100%" cellspacing="5">
            <tr>
                <td>
                    <hr>
                </td>
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
        <table border="0" width="100%" cellspacing="5">
            <tr>
                <td><html:text property="maxImpressionsInCampain"/>&nbsp;&nbsp;&nbsp;
                    <bean:message key="maxImpressionsInCampain"/></td>
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
        <table border="0" width="100%" cellspacing="5">
            <tr>
                <td><html:text property="maxClicksInCampain"/>&nbsp;&nbsp;&nbsp;
                    <bean:message key="maxClicksInCampain"/></td>
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
        <table border="0" width="100%" cellspacing="5">
            <tr>
                <td>
                    <hr>
                </td>
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
        <table border="0" width="100%" cellspacing="5">
            <tr>
                <td><html:text property="maxImpressionsInDay"/>&nbsp;&nbsp;&nbsp;
                    <bean:message key="maxImpressionsInDay"/></td>
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
        <table border="0" width="100%" cellspacing="5">
            <tr>
                <td><html:text property="maxClicksInDay"/>&nbsp;&nbsp;&nbsp; <bean:message
                        key="maxClicksInDay"/></td>
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
        <table border="0" width="100%" cellspacing="5">
            <tr>
                <td>
                    <hr>
                </td>
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
        <table border="0" cellspacing="5">
            <tr>
                <td><html:text property="customPeriodValue"/></td>
                <td><bean:message key="maxImpressionsInCustomPeriod"/></td>
                <td><html:select property="customPeriodDayValue">
                    <html:optionsCollection property="customPeriodDay"/>
                </html:select></td>
                <td><bean:message key="days.small"/></td>
                <td><html:select property="customPeriodHourValue">
                    <html:optionsCollection property="customPeriodHour"/>
                </html:select></td>
                <td><bean:message key="hours"/></td>
            </tr>
            <tr>
                <td><html:text property="customPeriodInClickValue"/></td>
                <td><bean:message key="maxClicksInCustomPeriod"/></td>
                <td><html:select property="customPeriodClickDayValue">
                    <html:optionsCollection property="customPeriodDay"/>
                </html:select></td>
                <td><bean:message key="days.small"/></td>
                <td><html:select property="customPeriodClickHourValue">
                    <html:optionsCollection property="customPeriodHour"/>
                </html:select></td>
                <td><bean:message key="hours"/></td>
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
        <table border="0" width="100%" cellspacing="5">
            <tr>
                <td><html:text property="maxImpresionsInDayForUniqueUser"/>&nbsp;&nbsp;&nbsp;
                    <bean:message key="maxImpresionsInDayForUniqueUser"/></td>
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
        <table border="0" width="100%" cellspacing="5">
            <tr>
                <td><html:text property="maxClicksInDayForUniqueUser"/>&nbsp;&nbsp;&nbsp;
                    <bean:message key="maxClicksInDayForUniqueUser"/></td>
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
        <table border="0" width="100%" cellspacing="5">
            <tr>
                <td>
                    <hr>
                </td>
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
        <table border="0" width="100%" cellspacing="5">
            <tr>
                <td><html:text property="maxImpressionsInCampainForUniqueUser"/>&nbsp;&nbsp;&nbsp;
                    <bean:message key="maxImpressionsInCampainForUniqueUser"/></td>
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
        <table border="0" width="100%" cellspacing="5">
            <tr>
                <td><html:text property="maxClicksInCampainForUniqueUser"/>&nbsp;&nbsp;&nbsp;
                    <bean:message key="maxClicksInCampainForUniqueUser"/></td>
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
        <table border="0" width="100%" cellspacing="5">
            <tr>
                <td>
                    <hr>
                </td>
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
        <table border="0" width="100%" cellspacing="5">
            <tr>
                <td><html:text property="maxImpressionsInMonthForUniqueUser"/>&nbsp;&nbsp;&nbsp;
                    <bean:message key="maxImpressionsInMonthForUniqueUser"/></td>
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
        <table border="0" width="100%" cellspacing="5">
            <tr>
                <td><html:text property="maxClicksInMonthForUniqueUser"/>&nbsp;&nbsp;&nbsp;
                    <bean:message key="maxClicksInMonthForUniqueUser"/></td>
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
        <table border="0" width="100%" cellspacing="5">
            <tr>
                <td>
                    <hr>
                </td>
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
        <table border="0" cellspacing="5">
            <tr>
                <td><html:text property="customPeriodValueUnique"/></td>
                <td><bean:message key="maxImpressionsInCustomPeriodUnique"/></td>
                <td><html:select property="customPeriodHourValueUnique">
                    <html:optionsCollection property="customPeriodHour"/>
                </html:select></td>
                <td><bean:message key="hours"/></td>
                <td><html:select property="customPeriodDayValueUnique">
                    <html:optionsCollection property="customPeriodDay"/>
                </html:select></td>
                <td><bean:message key="days.small"/></td>
            </tr>
            <tr>
                <td><html:text property="customPeriodInClickValueUnique"/></td>
                <td><bean:message key="maxClicksInCustomPeriodUnique"/></td>
                <td><html:select property="customPeriodClickHourValueUnique">
                    <html:optionsCollection property="customPeriodHour"/>
                </html:select></td>
                <td><bean:message key="hours"/></td>
                <td><html:select property="customPeriodClickDayValueUnique">
                    <html:optionsCollection property="customPeriodDay"/>
                </html:select></td>
                <td><bean:message key="days.small"/></td>
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
        <table border="0" cellspacing="5" width="100%">
            <tr>
                <td>
                    <hr>
                </td>
            </tr>
        </table>
    </td>
    <td width="6" heigh="10"
        style="background-image: url(images/table5.gif);"></td>
</tr>

<tr>
    <td width="5" heigh="10"
        style="background-image: url(images/table4.gif);"></td>
    <td class="tabledata" cellspacing="5">
        <table border="0" cellspacing="5" cellpadding="5">
            <tr>
                <td><img src="images/icons/update.png" style="cursor: pointer;"
                         onclick="filtersTemplateActionForm.submit();"
                         title="<%=Msg.fetch("update",request)%>"
                         alt="<%=Msg.fetch("update",request)%>"/></td>
                <td><img src="images/icons/save.png" style="cursor: pointer;"
                         onclick="filtersTemplateActionForm.templateAction.value='save'; filtersTemplateActionForm.submit();"
                         title="<%=Msg.fetch("template.save",request)%>"
                         alt="<%=Msg.fetch("template.save",request)%>"/></td>
                <td><img src="images/icons/reset.png" style="cursor: pointer;"
                         onclick="filtersTemplateActionForm.reset();"
                         title="<%=Msg.fetch("reset",request)%>"
                         alt="<%=Msg.fetch("reset",request)%>"/></td>
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
<%}%>







<%if (AdsapientConstants.CONTENT_FILTER.equalsIgnoreCase(form
        .getFilterType())) {%>

<tr>
<td>
<table width="100%" cellspacing="0" cellpadding="0">
<html:form action="filtersTemplate">


<tr>
    <td width="5" style="background-image: url(images/table4.gif);"></td>
    <td class="tabledata">
        <table border="0" width="100%" cellspacing="5">
            <tr>
                <td class="tabledata"><bean:message key="input.template.name"/>:&nbsp;
                    <html:text property="templateName" styleId="oldTemplateName"/></td>
                <td></td>
                <td></td>
            </tr>
        </table>
    </td>
    <td width="6" style="background-image: url(images/table5.gif);"></td>
</tr>


<tr>
    <td width="5" heigh="10"
        style="background-image: url(images/table4.gif);"></td>
    <td class="tabledata">&nbsp;<bean:message key="select.categories"/>:</td>
    <td width="6" heigh="10"
        style="background-image: url(images/table5.gif);"></td>
</tr>
<tr>
    <td width="5" heigh="10"
        style="background-image: url(images/table4.gif);"></td>

    <td class="tabledata">
        <table border="0" cellspacing="5">
            <tr>
                <td><bean:message key="possible.choices"/><br/>
                    <html:select property="selectedCategorys" multiple="true"
                                 styleId="uiOptionTransfer_source1"
                                 styleClass="uiOptionTransfer_target">
                        <html:optionsCollection property="categorys"/>
                    </html:select></td>
                <td><input type="button" value="&raquo;"
                           title='<bean:message key="move.all.options.to.target"/>'
                           onClick="uiOptionTransfer_transferAll(uiOptionTransfer_object1); return false;"/>
                    <br/>
                    <input type="button" value="&gt;"
                           title='<bean:message key="move.selected.options.to.target.list"/>'
                           onClick="uiOptionTransfer_transfer(uiOptionTransfer_object1); return false;"/>
                    <br/>
                    <input type="button" value="&lt;"
                           title='<bean:message key="delete.selected.options.from.target"/>'
                           onClick="uiOptionTransfer_return(uiOptionTransfer_object1); return false;"/>
                    <br/>
                    <input type="button" value="&laquo;"
                           title='<bean:message key="delete.all.options.from.target"/>'
                           onClick="uiOptionTransfer_returnAll(uiOptionTransfer_object1); return false;"/>
                </td>
                <td><bean:message key="actual.selection"/><br/>
                    <html:select property="selectedCategorys"
                                 styleId="uiOptionTransfer_target1" multiple="true"
                                 styleClass="uiOptionTransfer_target">
                        <html:optionsCollection property="categorys2"/>
                    </html:select></td>
            </tr>
        </table>
    </td>
    <td width="6" heigh="10"
        style="background-image: url(images/table5.gif);"></td>
</tr>
<tr>


    <!-- Script for this menu--->
    <script language="javascript">
        var uiOptionTransfer_object1;


        function uiOptionTransfer_init1() {
            var src = document.getElementById('uiOptionTransfer_source1');

            uiOptionTransfer_object1 = new uiOptionTransfer_Globals(null, null, 'uiOptionTransfer_source1', 'uiOptionTransfer_target1', 'uiOptionTransfer_distance1');
            uiOptionTransfer_fillStates(uiOptionTransfer_object1);
            uiOptionTransfer_onSubmit(uiOptionTransfer_object1);
        }



        uiOptionTransfer_init1();
    </script>
    <tr>
        <td width="5" heigh="10"
            style="background-image: url(images/table4.gif);"></td>
        <td class="tabledata">&nbsp;<bean:message key="select.positions"/>:</td>
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
                    <td><bean:message key="possible.choices"/><br/>
                        <html:select property="selectedCategorys" multiple="true"
                                     styleId="uiOptionTransfer_source2"
                                     styleClass="uiOptionTransfer_target">
                            <html:optionsCollection property="pagePositionsCollection1"/>
                        </html:select></td>
                    <td><input type="button" value="&raquo;"
                               title='<bean:message key="move.all.options.to.target"/>'
                               onClick="uiOptionTransfer_transferAll(uiOptionTransfer_object2); return false;"/>
                        <br/>
                        <input type="button" value="&gt;"
                               title='<bean:message key="move.selected.options.to.target.list"/>'
                               onClick="uiOptionTransfer_transfer(uiOptionTransfer_object2); return false;"/>
                        <br/>
                        <input type="button" value="&lt;"
                               title='<bean:message key="delete.selected.options.from.target"/>'
                               onClick="uiOptionTransfer_return(uiOptionTransfer_object2); return false;"/>
                        <br/>
                        <input type="button" value="&laquo;"
                               title='<bean:message key="delete.all.options.from.target"/>'
                               onClick="uiOptionTransfer_returnAll(uiOptionTransfer_object2); return false;"/>
                    </td>
                    <td><bean:message key="actual.selection"/><br/>
                        <html:select property="selectedCategorys"
                                     styleId="uiOptionTransfer_target2" multiple="true"
                                     styleClass="uiOptionTransfer_target">
                            <html:optionsCollection property="pagePositionsCollection2"/>
                        </html:select></td>
                </tr>
            </table>
        </td>
        <td width="6" heigh="10"
            style="background-image: url(images/table5.gif);"></td>
    </tr>
<tr>


    <!-- Script for this menu--->
    <script language="javascript">
        var uiOptionTransfer_object2;


        function uiOptionTransfer_init2() {
            var src = document.getElementById('uiOptionTransfer_source2');

            uiOptionTransfer_object2 = new uiOptionTransfer_Globals(null, null, 'uiOptionTransfer_source2', 'uiOptionTransfer_target2', 'uiOptionTransfer_distance2');
            uiOptionTransfer_fillStates(uiOptionTransfer_object2);
            uiOptionTransfer_onSubmit(uiOptionTransfer_object2);
        }



        uiOptionTransfer_init2();

        function allPlacesClick(checked)
        {
            if (checked)
            {
                document.getElementById('uiOptionTransfer_category3').disabled = 'disabled';
                document.getElementById('uiOptionTransfer_source3').disabled = 'disabled';
                document.getElementById('uiOptionTransfer_target3').disabled = 'disabled';
                document.getElementById('allPlaces').value = true;
            }
            else
            {
                document.getElementById('uiOptionTransfer_category3').disabled = undefined;
                document.getElementById('uiOptionTransfer_source3').disabled = undefined;
                document.getElementById('uiOptionTransfer_target3').disabled = undefined;
                document.getElementById('allPlaces').value = false;
            }
        }
    </script>
    <tr>
        <td width="5" heigh="10"
            style="background-image: url(images/table4.gif);"></td>
        <td class="tabledata">
            <table border="0" width="100%" cellspacing="5">
                <tr>
                    <td colspan="3"><html:checkbox property="allPlaces"
                                                   styleId="oldallPlaces" onclick="allPlacesClick(checked);"/>
                        <bean:message
                                key="display.creatives.on.all.adplaces"/></td>
                </tr>
            </table>
        </td>
        <td width="6" heigh="10"
            style="background-image: url(images/table5.gif);"></td>
    </tr>
<tr>
    
    <tr>
        <td width="5" heigh="10"
            style="background-image: url(images/table4.gif);"></td>
        <td class="tabledata">&nbsp;<bean:message key="select.certain"/>:</td>
        <td width="6" heigh="10"
            style="background-image: url(images/table5.gif);"></td>
    </tr>
    <tr>
        <td width="5" heigh="10"
            style="background-image: url(images/table4.gif);"></td>
        <td class="tabledata">
            <table border="0" cellspacing="5">
                <tr>
                    <td><bean:message key="possible.choices"/><br/>
                        <select id="uiOptionTransfer_category3"
                                class="uiOptionTransfer_category"
                                onchange="uiOptionTransfer_fillSrc(uiOptionTransfer_object3),
	 	uiOptionTransfer_fillStates(uiOptionTransfer_object3);">
                        </select> <br/>
                        <select id="uiOptionTransfer_source3"
                                class="uiOptionTransfer_target" multiple="multiple">
                        </select></td>
                    <td><input type="button" value="&raquo;"
                               title='<bean:message key="move.all.options.to.target"/>'
                               onClick="uiOptionTransfer_transferAll(uiOptionTransfer_object3); return false;"/>
                        <br/>
                        <input type="button" value="&gt;"
                               title='<bean:message key="move.selected.options.to.target.list"/>'
                               onClick="uiOptionTransfer_transfer(uiOptionTransfer_object3); return false;"/>
                        <br/>
                        <input type="button" value="&lt;"
                               title='<bean:message key="delete.selected.options.from.target"/>'
                               onClick="uiOptionTransfer_return(uiOptionTransfer_object3); return false;"/>
                        <br/>
                        <input type="button" value="&laquo;"
                               title='<bean:message key="delete.all.options.from.target"/>'
                               onClick="uiOptionTransfer_returnAll(uiOptionTransfer_object3); return false;"/>
                    </td>
                    <td><bean:message key="actual.selection"/><br/>
                        <select id="uiOptionTransfer_target3"
                                class="uiOptionTransfer_target" multiple="multiple">
                        </select></td>
                </tr>
            </table>
        </td>
        <td width="6" heigh="10"
            style="background-image: url(images/table5.gif);"></td>
    </tr>
<tr>

<script language="javascript">
    var uiOptionTransfer_object3;


    function uiOptionTransfer_init3() {
        cats = new Array();
        var arr;

    <%=form.generateSourceStructure()%>

        uiOptionTransfer_object3 = new uiOptionTransfer_Globals(cats, 'uiOptionTransfer_category3', 'uiOptionTransfer_source3', 'uiOptionTransfer_target3', 'uiOptionTransfer_distance3');
        uiOptionTransfer_fillCat(uiOptionTransfer_object3);
        uiOptionTransfer_fillSrc(uiOptionTransfer_object3);
        uiOptionTransfer_fillTgt3(uiOptionTransfer_object3);
        uiOptionTransfer_onSubmit(uiOptionTransfer_object3);
        uiOptionTransfer_fillStates(uiOptionTransfer_object3);
    }

    function uiOptionTransfer_fillTgt3(obj) {
        var src = document.getElementById(obj.sourceId);
        var tgt = document.getElementById(obj.targetId);
        var item;
        var opt;
    <%=form.generateTargetStructure()%>
    }

    uiOptionTransfer_init3();
</script>
</html:form>
<html:form action="filtersTemplate">
    <html:hidden property="campainId"/>
    <html:hidden property="selectedCategorysName"
                 styleId="selectedCategorysId"/>
    <html:hidden property="selectedPositions"
                 styleId="selectedPositionsId"/>
    <html:hidden property="registeredPlaces" styleId="selectedPlacesId"/>
    <html:hidden property="filterType"/>
    <html:hidden property="dataSource"/>
    <html:hidden property="templateAction"/>
    <html:hidden property="enableAutoUpdate"/>
    <html:hidden property="templateName" styleId="newTemplateName"/>
    <html:hidden property="allPlaces" styleId="allPlaces"/>
    <tr>
        <td width="5" heigh="10"
            style="background-image: url(images/table4.gif);"></td>
        <td class="tabledata">
            <hr>
            <table border="0" cellspacing="5" cellpadding="5">
                <tr>
                    <td><img src="images/icons/update.png" style="cursor: pointer;"
                             onclick="transform_categorys(uiOptionTransfer_object1,true);filtersTemplateActionForm[1].submit();"
                             title="<%=Msg.fetch("update",request)%>"
                             alt="<%=Msg.fetch("update",request)%>"/></td>

                    <td><img src="images/icons/save.png" style="cursor: pointer;"
                             onclick="document.location='<%=response.encodeURL("filtersTemplate.do?templateAction=save&templateName="+form.getTemplateName())%> '"
                             title="<%=Msg.fetch("template.save",request)%>"
                             alt="<%=Msg.fetch("template.save",request)%>"/></td>
                    <td><img src="images/icons/reset.png" style="cursor: pointer;"
                             onclick="filtersTemplateActionForm[1].reset();"
                             title="<%=Msg.fetch("reset",request)%>"
                             alt="<%=Msg.fetch("reset",request)%>"/></td>
                    <td>
                </tr>
            </table>
        </td>
        <td width="6" heigh="10"
            style="background-image: url(images/table5.gif);"></td>
    </tr>
    <tr>
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
<script language="javascript">
    allPlacesClick(document.getElementById('oldallPlaces').checked);
</script>

<%}%>




<%if (AdsapientConstants.KEYWORDS_FILTER.equalsIgnoreCase(form
        .getFilterType())) {%>


<html:form action="filtersTemplate">
<html:hidden property="filterType"/>
<html:hidden property="dataSource"/>
<html:hidden property="templateAction"/>
<html:hidden property="enableAutoUpdate"/>
<html:hidden property="textEngineAction" styleId="textEngineAction"/>
<html:hidden property="textEngineId" styleId="textEngineId"/>

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

                        <td class="tabledata" colspan="3"><bean:message
                                key="input.template.name"/>:&nbsp; <html:text
                                property="templateName" styleId="oldTemplateName"/></td>
                    </tr>
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
                <td width="5" heigh="10"
                    style="background-image: url(images/table4.gif);"></td>
                <td>
                    <table border="0" style="background-color:#ffffff;" cellspacing="1"
                           width="100%">
                        <tr>
                            <td height="10" width="10%" class="tabletableheader"><bean:message
                                    key="id"/></td>
                            <td height="10" width="50%" class="tabletableheader"><bean:message
                                    key="keywords.label"/></td>
                            <td height="10" width="10%" class="tabletableheader"><bean:message
                                    key="keywords.impressions.day"/></td>
                            <td height="10" width="10%" class="tabletableheader"><bean:message
                                    key="keywords.clicks.day"/></td>
                            <td height="10" width="10%" class="tabletableheader"><bean:message
                                    key="keywords.average.position"/></td>

                            <td height="10" width="10%" class="tabletableheader"><bean:message
                                    key="actions"/></td>
                        </tr>
                        
                        <logic:iterate id="entityMy" name="filtersTemplateActionForm"
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
                                        title=
                                            <bean:message key="remove"/> style="cursor: pointer;"
                                                onclick="document.getElementById('textEngineAction').value='remove';document.getElementById('textEngineId').value='<%=entity.getId()%>';filtersTemplateActionForm.submit();" /></
                                        nobr></td>
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

                    <hr/>
                    <table border="0" cellspacing="5" cellpadding="5" class="tabledata">
                        <tr>
                            <td><img src="images/icons/update.png" style="cursor: pointer;"
                                     onclick="filtersTemplateActionForm.submit();"
                                     title="<%=Msg.fetch("update",request)%>"
                                     alt="<%=Msg.fetch("update",request)%>"/></td>
                            <td><img src="images/icons/save.png" style="cursor: pointer;"
                                     onclick="filtersTemplateActionForm.templateAction.value='save'; filtersTemplateActionForm.submit();"
                                     title="<%=Msg.fetch("template.save",request)%>"
                                     alt="<%=Msg.fetch("template.save",request)%>"/></td>
                            <td><img src="images/icons/reset.png" style="cursor: pointer;"
                                     onclick="filtersTemplateActionForm.reset();"
                                     title="<%=Msg.fetch("reset",request)%>"
                                     alt="<%=Msg.fetch("reset",request)%>"/></td>
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
<%}%>





<%if (AdsapientConstants.PARAMETERS_FILTER.equalsIgnoreCase(form
        .getFilterType())) {%>


<html:form action="filtersTemplate" method="get">
<html:hidden property="filterType"/>
<html:hidden property="dataSource"/>
<html:hidden property="templateAction"/>
<html:hidden property="enableAutoUpdate"/>

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

                        <td class="tabledata" colspan="3"><bean:message
                                key="input.template.name"/>:&nbsp; <html:text
                                property="templateName" styleId="oldTemplateName"/></td>
                    </tr>
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
                <td width="5" heigh="10"
                    style="background-image: url(images/table4.gif);"></td>
                <td>
                    <table border="0" style="background-color:#ffffff;" cellspacing="1"
                           width="100%">
                        <%=form.getParametersSource()%>
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

                    <hr/>
                    <table border="0" cellspacing="5" cellpadding="5" class="tabledata">
                        <tr>
                            <td><img src="images/icons/update.png" style="cursor: pointer;"
                                     onclick="transform();filtersTemplateActionForm.submit();"
                                     title="<%=Msg.fetch("update",request)%>"
                                     alt="<%=Msg.fetch("update",request)%>"/></td>
                            <td><img src="images/icons/save.png" style="cursor: pointer;"
                                     onclick="filtersTemplateActionForm.templateAction.value='save'; filtersTemplateActionForm.submit();"
                                     title="<%=Msg.fetch("template.save",request)%>"
                                     alt="<%=Msg.fetch("template.save",request)%>"/></td>
                            <td><img src="images/icons/reset.png" style="cursor: pointer;"
                                     onclick="filtersTemplateActionForm.reset();"
                                     title="<%=Msg.fetch("reset",request)%>"
                                     alt="<%=Msg.fetch("reset",request)%>"/></td>
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
<%}%>





<%if (AdsapientConstants.SYSTEMS_FILTER.equalsIgnoreCase(form.getFilterType())) {%>
<tr>
<td>
<table style="background-image: url(images/tab6.gif);" width="100%" cellspacing="0" cellpadding="0">
<html:form action="filtersTemplate" method="get">
<html:hidden property="campainId"/>
<html:hidden property="selected_browsers" styleId="selected_browsers_target"/>
<html:hidden property="selected_systems" styleId="selected_systems_target"/>
<html:hidden property="selected_langs" styleId="selected_langs_target"/>
<html:hidden property="filterType"/>
<html:hidden property="dataSource"/>
<html:hidden property="templateAction"/>
<html:hidden property="enableAutoUpdate"/>
<html:hidden property="allPlaces" styleId="allPlaces"/>

<tr>
    <td width="5" style="background-image: url(images/table4.gif);"></td>
    <td class="tabledata">
        <table border="0" width="100%" cellspacing="5">
            <tr>
                <td class="tabledata"><bean:message key="input.template.name"/>:&nbsp;
                    <html:text property="templateName" styleId="oldTemplateName"/></td>
                <td></td>
                <td></td>
            </tr>
        </table>
    </td>
    <td width="6" style="background-image: url(images/table5.gif);"></td>
</tr>


<tr>
    <td width="5" heigh="10"
        style="background-image: url(images/table4.gif);"></td>
    <td class="tabledata">&nbsp;<bean:message key="select.browser"/>:</td>
    <td width="6" heigh="10"
        style="background-image: url(images/table5.gif);"></td>
</tr>
<tr>
    <td width="5" heigh="10"
        style="background-image: url(images/table4.gif);"></td>

    <td class="tabledata">
        <table border="0" cellspacing="5">
            <tr>
                <td><bean:message key="possible.choices"/><br/>
                    <html:select property="selectedBrowsers" multiple="true"
                                 styleId="browsers_source"
                                 styleClass="uiOptionTransfer_target">
                        <html:optionsCollection property="user_browsers"/>
                    </html:select></td>
                <td><input type="button" value="&raquo;"
                           title='<bean:message key="move.all.options.to.target"/>'
                           onClick="uiOptionTransfer_transferAll(browsers_object); return false;"/>
                    <br/>
                    <input type="button" value="&gt;"
                           title='<bean:message key="move.selected.options.to.target.list"/>'
                           onClick="uiOptionTransfer_transfer(browsers_object); return false;"/>
                    <br/>
                    <input type="button" value="&lt;"
                           title='<bean:message key="delete.selected.options.from.target"/>'
                           onClick="uiOptionTransfer_return(browsers_object); return false;"/>
                    <br/>
                    <input type="button" value="&laquo;"
                           title='<bean:message key="delete.all.options.from.target"/>'
                           onClick="uiOptionTransfer_returnAll(browsers_object); return false;"/>
                </td>
                <td><bean:message key="actual.selection"/><br/>
                    <html:select property="selectedBrowsers"
                                 styleId="browsers_target" multiple="true"
                                 styleClass="uiOptionTransfer_target">
                        <html:optionsCollection property="user_browsers2"/>
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
    <td class="tabledata">&nbsp;<bean:message key="select.systems"/>:</td>
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
                <td><bean:message key="possible.choices"/><br/>
                    <html:select property="selectedSystems" multiple="true"
                                 styleId="systems_source"
                                 styleClass="uiOptionTransfer_target">
                        <html:optionsCollection property="user_systems"/>
                    </html:select></td>
                <td><input type="button" value="&raquo;"
                           title='<bean:message key="move.all.options.to.target"/>'
                           onClick="uiOptionTransfer_transferAll(systems_object); return false;"/>
                    <br/>
                    <input type="button" value="&gt;"
                           title='<bean:message key="move.selected.options.to.target.list"/>'
                           onClick="uiOptionTransfer_transfer(systems_object); return false;"/>
                    <br/>
                    <input type="button" value="&lt;"
                           title='<bean:message key="delete.selected.options.from.target"/>'
                           onClick="uiOptionTransfer_return(systems_object); return false;"/>
                    <br/>
                    <input type="button" value="&laquo;"
                           title='<bean:message key="delete.all.options.from.target"/>'
                           onClick="uiOptionTransfer_returnAll(systems_object); return false;"/>
                </td>
                <td><bean:message key="actual.selection"/><br/>
                    <html:select property="selectedBrowsers"
                                 styleId="systems_target" multiple="true"
                                 styleClass="uiOptionTransfer_target">
                        <html:optionsCollection property="user_systems2"/>
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
    <td class="tabledata">&nbsp;<bean:message key="select.systems"/>:</td>
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
                <td><bean:message key="possible.choices"/><br/>
                    <html:select property="selectedSystems" multiple="true"
                                 styleId="systems_source"
                                 styleClass="uiOptionTransfer_target">
                        <html:optionsCollection property="user_systems"/>
                    </html:select></td>
                <td><input type="button" value="&raquo;"
                           title='<bean:message key="move.all.options.to.target"/>'
                           onClick="uiOptionTransfer_transferAll(systems_object); return false;"/>
                    <br/>
                    <input type="button" value="&gt;"
                           title='<bean:message key="move.selected.options.to.target.list"/>'
                           onClick="uiOptionTransfer_transfer(systems_object); return false;"/>
                    <br/>
                    <input type="button" value="&lt;"
                           title='<bean:message key="delete.selected.options.from.target"/>'
                           onClick="uiOptionTransfer_return(systems_object); return false;"/>
                    <br/>
                    <input type="button" value="&laquo;"
                           title='<bean:message key="delete.all.options.from.target"/>'
                           onClick="uiOptionTransfer_returnAll(systems_object); return false;"/>
                </td>
                <td><bean:message key="actual.selection"/><br/>
                    <html:select property="selectedSystems"
                                 styleId="systems_target" multiple="true"
                                 styleClass="uiOptionTransfer_target">
                        <html:optionsCollection property="user_systems2"/>
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
<!-- Page positions ------>
<tr>
    <td width="5" heigh="10"
        style="background-image: url(images/table4.gif);"></td>
    <td class="tabledata">
        <table border="0" cellspacing="5">
            <tr>
                <td><bean:message key="possible.choices"/><br/>
                    <html:select property="selectedLangs" multiple="true"
                                 styleId="langs_source"
                                 styleClass="uiOptionTransfer_target">
                        <html:optionsCollection property="user_langs"/>
                    </html:select></td>
                <td><input type="button" value="&raquo;"
                           title='<bean:message key="move.all.options.to.target"/>'
                           onClick="uiOptionTransfer_transferAll(langs_object); return false;"/>
                    <br/>
                    <input type="button" value="&gt;"
                           title='<bean:message key="move.selected.options.to.target.list"/>'
                           onClick="uiOptionTransfer_transfer(langs_object); return false;"/>
                    <br/>
                    <input type="button" value="&lt;"
                           title='<bean:message key="delete.selected.options.from.target"/>'
                           onClick="uiOptionTransfer_return(langs_object); return false;"/>
                    <br/>
                    <input type="button" value="&laquo;"
                           title='<bean:message key="delete.all.options.from.target"/>'
                           onClick="uiOptionTransfer_returnAll(langs_object); return false;"/>
                </td>
                <td><bean:message key="actual.selection"/><br/>
                    <html:select property="selectedLangs"
                                 styleId="langs_target" multiple="true"
                                 styleClass="uiOptionTransfer_target">
                        <html:optionsCollection property="user_langs2"/>
                    </html:select></td>
            </tr>
        </table>
    </td>
    <td width="6" heigh="10"
        style="background-image: url(images/table5.gif);"></td>
</tr>
<!-- Script for this menu--->
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
    function transform() {
        lists = new Array(browsers_object, systems_object,langs_object);
        transform_any_categorys(lists);
    }
</script>

<tr>
    <td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
    <td class="tabledata">
        <table border="0" style="background-color:#ffffff;" cellspacing="1" width="100%">
            <tr>
                <td height="10" width="10%" class="tabletableheader"><bean:message key="id"/></td>
                <td height="10" width="60%" class="tabletableheader"><bean:message key="referrer.value"/></td>
                <td height="10" width="20%" class="tabletableheader"><bean:message key="referrer.type"/></td>
                <td height="10" width="10%" class="tabletableheader"><bean:message key="actions"/></td>
            </tr>
            
            <logic:iterate id="entityMy" name="filtersTemplateActionForm"
                           property="referrersCollection">
                <%ReferrersElement entity = (ReferrersElement) entityMy;%>
                <tr>
                    <td height="10" class="tabledata"><nobr><%=entity.getId()%></nobr></td>
                    <td height="10" class="tabledata"><nobr><%=entity.getTarget_url()%></nobr></td>
                    <td height="10" class="tabledata"><nobr><%=(entity.isType() ? "Target" : "Block")%></nobr></td>
                    <td height="10" class="tabledata-c"><nobr><img
                            src="images/icons/remove.png" border="0"
                            title=
                                <bean:message key="remove"/> style="cursor: pointer;"
                                    onclick="document.getElementById('referrerEngineAction').value='remove';document.getElementById('referrerId').value='<%=entity.getId()%>';filterActionForm.submit();" /></
                            nobr></td>
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
                         onclick="transform();filtersTemplateActionForm.submit();"
                         title="<%=Msg.fetch("update",request)%>"
                         alt="<%=Msg.fetch("update",request)%>"/></td>

                <td><img src="images/icons/save.png" style="cursor: pointer;"
                         onclick="filtersTemplateActionForm.templateAction.value='save'; filtersTemplateActionForm.submit();"
                         title="<%=Msg.fetch("template.save",request)%>"
                         alt="<%=Msg.fetch("template.save",request)%>"/></td>
                <td><img src="images/icons/reset.png" style="cursor: pointer;"
                         onclick="filtersTemplateActionForm.reset();"
                         title="<%=Msg.fetch("reset",request)%>"
                         alt="<%=Msg.fetch("reset",request)%>"/></td>
                <td>
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
<%}%>

