<%@ page import="com.adsapient.gui.forms.FilterActionForm"%>
<%@ page import="com.adsapient.api_impl.filter.FiltersTemplate"%>
<%@ page import="com.adsapient.util.jsp.JSPHelper"%>
<%@ page import="com.adsapient.util.admin.AdsapientConstants"%>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %>
<%@ taglib uri='/WEB-INF/struts-bean.tld' prefix='bean' %>
<%@ page contentType="text/html; charset=UTF-8" %>


<%FilterActionForm form = (FilterActionForm) request
        .getAttribute("filterActionForm");
    int i = 1;

%>
<script language="javascript" type="text/javascript"
        src="javascript/ui.js"></script>

<html:errors/>
<html:messages id="msg" message="true" header="messages.header" footer="messages.footer" >
 <bean:write name="msg" />
</html:messages>

<%if (!form.getTemplateCollection().isEmpty()) {%>
<html:form action="filtersTemplate">
    <input type="hidden" name="campainId" value="<%=form.getCampainId()%>">
    <input type="hidden" name="filterType"
           value="<%=form.getFilterType()%>"> <input type="hidden"
                                                     name="templateAction" value="perform">
    <tr>
        <td>
            <table width="100%" cellspacing="0" cellpadding="0">
                <tr>
                    <td><img src="images/table1.gif"></td>
                    <td width="100%" class="tableheader"><bean:message
                            key="advertising.templates.list"/></td>
                    <td><img src="images/table2.gif"></td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <table width="100%" cellspacing="0" cellpadding="0">
                <logic:iterate id="valueMy" name="filterActionForm"
                               property="templateCollection">
                    <%FiltersTemplate fTemplate = (FiltersTemplate) valueMy;%>
                    <tr>
                        <td width="5" heigh="10"
                            style="background-image: url(images/table4.gif);"></td>
                        <td height="10" ><input
                                type="checkbox" name="<%=fTemplate.getTemplateName()%>"> <a
                                class="tabledata"
                                href="<%=response.encodeURL("filtersTemplate.do?templateAction=view&templateId="+fTemplate.getTemplateId())%>">
                            <%=fTemplate.getTemplateName()%></a></td>
                        <td width="6" heigh="10"
                            style="background-image: url(images/table5.gif);"></td>
                    </tr>
                </logic:iterate>
                <tr>
                    <td width="5" heigh="10"
                        style="background-image: url(images/table4.gif);"></td>
                    <td >
                        <table border="0" width="100%" cellspacing="5">
                            <tr>
                                <td><input type="submit"
                                           value="<bean:message key="button.applytemplate"/>"/></td>
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
                        <%=JSPHelper.generateFiltersHeader(form, request, response)%>
                    </table>
                </td>

                <td width="6" style="background-image: url(images/table5.gif);"></td>
            </tr>
        </table>

    </td>
</tr>


<%if (AdsapientConstants.TIME_FILTER.equalsIgnoreCase(form
        .getFilterType())) {%>
<%@ include file="timefilter.jsp" %>

<%}%>





<%if (AdsapientConstants.DATE_FILTER.equalsIgnoreCase(form
        .getFilterType())) {%>
<%@ include file="dayfilter.jsp" %>
<%}%>





<%if (AdsapientConstants.GEO_FILTER.equalsIgnoreCase(form
        .getFilterType())) {%>
<%@ include file="geofilter.jsp" %>
<%}%>





<%if (AdsapientConstants.BEHAVIOR_FILTER.equalsIgnoreCase(form
        .getFilterType())) {%>
<%--<%@ include file="behaviorfilter.jsp" %>--%>
<%}%>





<%if (AdsapientConstants.TRAFFIC_FILTER.equalsIgnoreCase(form
        .getFilterType())) {%>
<%@ include file="trafficfilter.jsp" %>
<%}%>






<%if (AdsapientConstants.CONTENT_FILTER.equalsIgnoreCase(form
        .getFilterType())) {%>
<%@ include file="contentfilter.jsp" %>
<%}%>




<%if (AdsapientConstants.KEYWORDS_FILTER.equalsIgnoreCase(form
        .getFilterType())) {%>

<%@ include file="keywordsfilter.jsp" %>
<%}%>



<%if (AdsapientConstants.PARAMETERS_FILTER.equalsIgnoreCase(form
        .getFilterType())) {%>
<%@ include file="parametersfilter.jsp" %>
<%}%>





<%if (AdsapientConstants.SYSTEMS_FILTER.equalsIgnoreCase(form.getFilterType())) {%>
<%@ include file="systemsfilter.jsp" %>
<%}%>


