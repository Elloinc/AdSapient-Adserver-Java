<%@ page import="com.adsapient.gui.forms.FilterActionForm" %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %>
<%@ taglib uri='/WEB-INF/struts-bean.tld' prefix='bean' %>
<%FilterActionForm form = (FilterActionForm) request.getAttribute("filtersTemplateActionForm");%>

<script language="javascript">
    function allPlacesClick(checked)
    {
        if (checked)
        {
            document.getElementById('uiOptionTransfer_category3').disabled = 'disabled';
            document.getElementById('uiOptionTransfer_source3').disabled = 'disabled';
            document.getElementById('uiOptionTransfer_target3').disabled = 'disabled';
        }
        else
        {
            document.getElementById('uiOptionTransfer_category3').disabled = undefined;
            document.getElementById('uiOptionTransfer_source3').disabled = undefined;
            document.getElementById('uiOptionTransfer_target3').disabled = undefined;
        }
    }
</script>
<tr>
    <td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
    <td class="tabledata">
        <table border="0" width="100%" cellspacing="5">
            <tr>
                <td colspan="3">
                    
                    <%--<html:checkbox property="allPlaces" styleId="oldallPlaces"--%>
                                               <%--onclick="allPlacesClick(checked);"/>--%>
                    <bean:message
                        key="display.creatives.on.all.adplaces"/></td>
            </tr>
        </table>
    </td>
    <td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
</tr>
<tr>
    
<tr>
    <td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
    <td class="tabledata"><bean:message key="select.certain"/>:</td>
    <td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
</tr>
<tr>
    <td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
    <td class="tabledata">
        <table border="0" cellspacing="5">
            <tr>
                <td>
                    <bean:message key="possible.choices"/><br/>
                    <select id="uiOptionTransfer_category3" class="uiOptionTransfer_category" onchange="uiOptionTransfer_fillSrc(uiOptionTransfer_object3),
	 	uiOptionTransfer_fillStates(uiOptionTransfer_object3);">
                    </select>
                    <br/>
                    <select id="uiOptionTransfer_source3" class="uiOptionTransfer_target" multiple="multiple">
                    </select>
                </td>
                <td>
                    <input type="button" value="&raquo;" title='<bean:message key="move.all.options.to.target"/>'
                           onClick="uiOptionTransfer_transferAll(uiOptionTransfer_object3); return false;"/>
                    <br/>
                    <input type="button" value="&gt;" title='<bean:message key="move.selected.options.to.target.list"/>'
                           onClick="uiOptionTransfer_transfer(uiOptionTransfer_object3); return false;"/>
                    <br/>
                    <input type="button" value="&lt;" title='<bean:message key="delete.selected.options.from.target"/>'
                           onClick="uiOptionTransfer_return(uiOptionTransfer_object3); return false;"/>
                    <br/>
                    <input type="button" value="&laquo;" title='<bean:message key="delete.all.options.from.target"/>'
                           onClick="uiOptionTransfer_returnAll(uiOptionTransfer_object3); return false;"/>
                </td>
                <td>
                    <bean:message key="actual.selection"/><br/>
                    <select id="uiOptionTransfer_target3" class="uiOptionTransfer_target" multiple="multiple">
                    </select>
                </td>
            </tr>
        </table>
    </td>
    <td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
</tr>
<tr>
    
    <script language="javascript">
        var uiOptionTransfer_object3;
        allPlacesClick(filterActionForm.allPlaces.checked);



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

