<%@ page import="com.adsapient.shared.mappable.UserWrapper" %>
<%@ page import="com.adsapient.shared.mappable.UserImpl" %>
<%@ page import="com.adsapient.gui.ContextAwareGuiBean" %>
<%@ page import="com.adsapient.shared.service.I18nService" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.adsapient.shared.service.TotalsReportService"%>
<%@ page import="com.adsapient.shared.AdsapientConstants"%>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %>
<%@ taglib uri='/WEB-INF/struts-bean.tld' prefix='bean' %>
<%@ page contentType="text/html; charset=UTF-8" %>

<% ApplicationContext ctx = ContextAwareGuiBean.getContext();%>
<% TotalsReportService trs = (TotalsReportService) ctx.getBean("totalsReportsService");%>
<% I18nService i18nService = (I18nService) ctx.getBean("i18nService");%>

<%UserImpl privilegUser = (UserImpl) request.getSession().getAttribute("user");%>

<html:messages id="msg" message="true" header="messages.header" footer="messages.footer" >
 <bean:write name="msg" />
</html:messages>

<html:form action="profileEdit">
    <tr><td>
        <table width="100%" cellspacing="0" cellpadding="0"><tr>
            <td><img src="images/table1.gif"></td>
            <td width="100%" class="tableheader"><bean:message key="usermanagement.allusers"/></td>
            <td><img src="images/table2.gif"></td>
        </tr></table>
    </td></tr>
    <tr><td>
        <table width="100%" cellspacing="0" cellpadding="0">
            
            <tr>
                <td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
                <td>
                    <table border="0" style="background-color:#ffffff;" cellspacing="1" width="100%" cellpadding="0"
                           heigh="100%"><tr>
                        <td width="5%" rowspan="2" class="tabletableheader"><bean:message key="id"/></td>
                        <td width="10%" rowspan="2" class="tabletableheader"><bean:message
                                key="usermanagement.username"/></td>
                        <td width="10%" rowspan="2" class="tabletableheader"><bean:message key="userrole"/></td>
                        <td width="10%" class="tabletableheader" colspan="4"><bean:message key="rate.adv"/></td>
                        <td width="10%" class="tabletableheader" colspan="4"><bean:message key="rate.pub"/></td>
                        <td width="5%" rowspan="2" class="tabletableheader"><bean:message key="account.money"/></td>
                        <td width="10%" class="tabletableheader" colspan="2"><bean:message
                                key="advertiser.report"/></td>
                        <td width="10%" class="tabletableheader" colspan="2"><bean:message key="publisher.report"/></td>
                        <td width="5%" rowspan="2" class="tabletableheader" colspan="3"><bean:message
                                key="actions"/></td>
                    </tr>

                        <tr>
                            <td class="tabletableheader">CPM</td><td class="tabletableheader">CPC</td><td
                                class="tabletableheader">CPL</td><td class="tabletableheader">CPS</td>
                            <td class="tabletableheader">CPM</td><td class="tabletableheader">CPC</td><td
                                class="tabletableheader">CPL</td><td class="tabletableheader">CPS</td>
                            <td class="tabletableheader">View</td><td class="tabletableheader">Clicks</td>
                            <td class="tabletableheader">View</td><td class="tabletableheader">Clicks</td>
                        </tr>
                        
                        <logic:iterate id="user" name="viewUsersForm" property="users">

                            <%UserWrapper iterUser = new UserWrapper((UserImpl) user);%>
                            <%UserImpl u  = (UserImpl) user;%>
                            <tr>
                                <td class="tabledata-c"><nobr><%=iterUser.getId()%></nobr></td>
                                <td class="tabledata-c"><nobr><%=iterUser.getName()%></nobr></td>
                                <td class="tabledata-c"><nobr><%=iterUser.getRole(request)%></nobr></td>
                                <%=iterUser.generateFinancialLink(response)%>

                                <td class="tabledata-c"><nobr><%=trs.getUserBalance(u.getId())%></nobr></td>
                                <td class="tabledata-c"><nobr><%=trs.getTotalUnitsByUser(u, AdsapientConstants.ADVIEW, AdsapientConstants.ADVERTISER)%></nobr></td>
                                <td class="tabledata-c"><nobr><%=trs.getTotalUnitsByUser(u, AdsapientConstants.CLICK, AdsapientConstants.ADVERTISER)%></nobr></td>
                                <td class="tabledata-c"><nobr><%=trs.getTotalUnitsByUser(u, AdsapientConstants.ADVIEW, AdsapientConstants.PUBLISHER)%></nobr></td>
                                <td class="tabledata-c"><nobr><%=trs.getTotalUnitsByUser(u, AdsapientConstants.CLICK, AdsapientConstants.PUBLISHER)%></nobr></td>

                                <td class="tabledata-c"><nobr><a class="tabledata"
                                                                 href="<%=response.encodeURL("profileEdit.do?action=view&id="+iterUser.getId())%>"><img
                                        src="images/icons/edit.png" border="0" title='<bean:message key="edit"/>'/></a></nobr></td>
                                <td class="tabledata-c"><nobr><a class="tabledata"
                                                                 href="<%=response.encodeURL("totals.do?userId="+iterUser.getId())%>"
                                                                 onClick="return window.confirm('<%=(i18nService.fetch("reset.confirm",session))%>');"><img
                                        src="images/icons/resetstats.gif" border="0" title='<bean:message key="reset"/>'/></a></nobr></td>
                                 
                                <%----%>
                                  
                               <%-- <td class="tabledata-c"><nobr><%=iterUser.getLoginHref(request, response)%></nobr></td> --%>
                                <td class="tabledata-c"><nobr><a class="tabledata"
                                                                 href="<%=response.encodeURL("profileEdit.do?action=remove&id="+iterUser.getId())%>"
                                                                 onClick="return window.confirm('<%=(i18nService.fetch("delete.confirm",session)+" User "+iterUser.getId())%>');"><img
                                        src="images/icons/remove.png" border="0" title=<bean:message key="remove"/>></a></nobr></td>
                            </tr>
                        </logic:iterate>
                        
                        <tr>
                            <td height="10" colspan="19" class="tabledata">
                                <table border="0" width="100%" cellspacing="5">
                                    <tr><td class="tabledata"><input type="submit" value='<bean:message key="add"/>'>
                                    </td>
                                    </tr></table>
                            </td>
                        </tr>
                    </table>
                </td>
                <td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
            </tr>
        </table>
    </td></tr>
</html:form>

<tr><td>
    <table width="100%" cellspacing="0" cellpadding="0"><tr>
        <td><img src="images/table6.gif"></td>
        <td width="100%" heigh="10" style="background-image: url(images/table8.gif);"></td>
        <td><img src="images/table7.gif"></td>
    </tr></table>
</td></tr>           



