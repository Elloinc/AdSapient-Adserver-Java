<%@ page
        import="com.adsapient.api_impl.publisher.PlacesImpl,
                com.adsapient.api_impl.publisher.SiteImpl,
                com.adsapient.api_impl.statistic.common.StatisticEntity,
                com.adsapient.api_impl.statistic.publisher.PlaceStatistic,
                com.adsapient.util.Msg,
                com.adsapient.util.financial.FinancialLinksController,
                com.adsapient.util.jsp.JSPHelper,
                java.util.Iterator" %>
<%@ page import="com.adsapient.shared.service.TotalsReportService" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.adsapient.gui.ContextAwareGuiBean" %>
<%@ page import="com.adsapient.util.admin.AdsapientConstants" %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %>
<%@ taglib uri='/WEB-INF/struts-bean.tld' prefix='bean' %>


<% ApplicationContext ctx = ContextAwareGuiBean.ctx;%>
<% TotalsReportService trs = (TotalsReportService) ctx.getBean("totalsReportsService");%>

<html:messages id="msg" message="true" header="messages.header" footer="messages.footer" >
 <bean:write name="msg" />
</html:messages>


<html:form action="publisherEdit">
<input type="hidden" name="action" value="init">
<tr>
    <td>
        <table width="100%" cellspacing="0" cellpadding="0">
            <tr>
                <td><img src="images/table1.gif"></td>
                <td width="100%" class="tableheader"><bean:message
                        key="site.adplaceslist.title"/></td>
                <td><img src="images/table2.gif"></td>
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
    <td height="10" width="1%" class="tabletableheader"><nobr>
        <bean:message
                key="site.id"/><br/>
        <bean:message
                key="place.id"/></nobr></td>
    <td height="10" width="5%" class="tabletableheader"><nobr><bean:message
            key="position"/> /<br/><bean:message
            key="category"/></nobr></td>
    <td height="10" width="5%" class="tabletableheader"><nobr><bean:message
            key="size"/></nobr></td>
    <td height="10" width="25%" class="tabletableheader"><nobr><bean:message
            key="site.url"/></nobr></td>
    <td height="10" width="5%" class="tabletableheader"><nobr><bean:message
            key="cpm.cpc"/><br/><bean:message
            key="cpl.cps"/></nobr></td>
    <td height="10" width="5%" class="tabletableheader"><nobr><bean:message
            key="views_clicks"/><br/><bean:message
            key="leads.sales"/></nobr></td>
    <td height="10" width="5%" class="tabletableheader"><nobr><bean:message
            key="revenue"/></nobr></td>
    <td height="10" width="10%" class="tabletableheader" colspan="2"><nobr><bean:message
            key="actions"/></nobr></td>

</tr>

<logic:iterate id="siteMy" name="publisherActionForm"
               property="sitesCollection">
    <%SiteImpl site = (SiteImpl) siteMy;%>


    <%if ((site.getRealPlaces() != null)
            && (!site.getRealPlaces().isEmpty())) {%>
    <%Iterator placesIterator = site.getRealPlaces().iterator();%>
    <%while (placesIterator.hasNext()) {%>
    <%PlacesImpl places = (PlacesImpl) placesIterator.next();%>
    <%StatisticEntity entity = PlaceStatistic
            .getPlaceStatisticAsText(places.getId());%>

    <tr>
        <td class="tabledata-c"><nobr><%=site.getSiteId()%><br/><%=places.getId()%></nobr></td>
        <td class="tabledata"><nobr><%=places.getPlace().getName()%>
            <br/><%=places.getCategoryName()%></nobr></td>
        <td class="tabledata-c"><nobr><%=places.getSizeName()%></nobr></td>
        <td class="tabledata-c"><a class="tabledata"
                                 href="<%=response.encodeURL(site.getUrl())%>" target="_blank">
            <nobr><%=site.getUrl()%></nobr></a></td>
        <td height="10" class="tabledata-c"><nobr><%=FinancialLinksController.generateRates(session,
                places, response)%><br/><%=FinancialLinksController.generateCPLRates(session,
                places, response)%></nobr></td>
        <td class="tabledata-c">
            <nobr><%=trs.getTotalUnitsByEntity(PlacesImpl.class, places.getId(), AdsapientConstants.ADVIEW)%>
                | <%=trs.getTotalUnitsByEntity(PlacesImpl.class, places.getId(), AdsapientConstants.CLICK)%><br/>
                <%=trs.getTotalUnitsByEntity(PlacesImpl.class, places.getId(), AdsapientConstants.LEAD)%>
                | <%=trs.getTotalUnitsByEntity(PlacesImpl.class, places.getId(), AdsapientConstants.SALE)%></nobr></td>
        <td class="tabledata-c">
            <nobr><%=trs.getTotalUnitsByEntity(PlacesImpl.class, places.getId(), AdsapientConstants.EARNEDSPENT)%></nobr>
        </td>
        
        <td height="10" class="tabledata-c"><nobr>
                <%-- <a class="tabledata"
                                                       href="<%=response.encodeURL("publisherStatistic.do?statisticType=Places&placeId="+places.getId())%>"><img
                  src="images/icons/reports.png" border="0"
                  title=
              <bean:message key="reports"/>></a> --%>
            <%if (JSPHelper.checkResetAccess(request)) {%>
                <%-- <a class="tabledata"
             href="<%=response.encodeURL("placeEdit.do?action=resetStatistic&placesId="+places.getId())%>"
             onClick="return window.confirm('<%=Msg.fetch("reset.confirm",session)%>');">
              <%}%><img src="images/icons/resetstats.gif" border="0"
                        title=
              <bean:message key="reset.statistic"/>></a> --%>
                <%}%>
            <br/>
            <a class="tabledata" href="<%=response.encodeURL("totals.do?placeId="+places.getId())%>" 
               onClick="return window.confirm('<%=(Msg.fetch("reset.confirm",session))%>');">
                <img src="images/icons/resetstats.gif" border="0" title='<bean:message key="reset"/>'/></a>

            <a class="tabledata"
               href="<%=response.encodeURL("placeEdit.do?placeAction=view&placesId="+places.getId())+"&userId="+places.getUserId() %>"><img
                    src="images/icons/edit.png" border="0"
                    title=
                        <bean:message key="edit"/></a>
                <a class="tabledata"
                   href="<%=response.encodeURL("placeEdit.do?placeAction=remove&placesId="+places.getId())%>"
                   onClick="return window.confirm('<%=(Msg.fetch("delete.confirm",session)+" "+places.getId())%>');"><img
                        src="images/icons/remove.png" border="0"
                        title=
                            <bean:message key="remove"/></a></nobr>

                            </td>


    </tr>

    <%}%>
    <%}%>
</logic:iterate>
</html:form>

<%--
<tr>
    <html:form action="placeEdit.do?action=init">
        <td height="10" colspan="15" class="tabledata">
            <table border="0" width="100%" cellspacing="5">
                <tr>
                    <td class="tabledata"><input type="submit"
                                                 value='<%=Msg.fetch("adplace.add.button",session)%>'></td>
                </tr>
            </table>
        </td>
    </html:form>
</tr>
--%>
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




