<%@ page import="com.adsapient.api_impl.usermanagment.UserImpl" %>
<%@ page import="com.adsapient.util.link.LinkHelper" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri='/WEB-INF/struts-bean.tld' prefix='bean' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %>

<%UserImpl user = (UserImpl) request.getSession().getAttribute("user");%>
<tr><td>
    <table width="100%" cellspacing="0" cellpadding="0"><tr>
        <td><img src="images/table1.gif"></td>
        <td width="100%" class="tableheader"><nobr><bean:message key="account.management"/></nobr></td>
        <%--() commented and added below for help link--%>
        <%if("admin".equalsIgnoreCase(user.getRole())){%>
             <td><a target="_blank" href="" onclick="helpAction('account.management');"><img border="0" src="images/icons/thelp.png" title=
        <bean:message key="context.help"/>></a></td>
        <%}%>
        <%if("advertiser".equalsIgnoreCase(user.getRole())){%>
            <td><a target="_blank" href="" onclick="helpAction('account.management');"><img border="0" src="images/icons/thelp.png" title=
        <bean:message key="context.help"/>></a></td>
        <%}%>
        <%if("publisher".equalsIgnoreCase(user.getRole())){%>
            <td><a target="_blank" href="" onclick="helpAction('account.management');"><img border="0" src="images/icons/thelp.png" title=
        <bean:message key="context.help"/>></a></td>
        <%}%>

        <%--<td><a href="#" onclick="helpAction('account.management');"><img border="0" src="images/icons/thelp.png" title=
        <bean:message key="context.help"/>></a></td>--%>
    </tr></table>
</td></tr>
<tr><td>
    <table  width="100%" cellspacing="0" cellpadding="0">
        <%if (!"admin".equalsIgnoreCase(user.getRole())) {%>
        <tr>
            <td width="5" heigh="10" class="leftborder"></td>
            <td height="10" colspan="2"><a class="menuitem" href="
	<%=response.encodeURL("viewAccount.do")%>
	">&nbsp;&nbsp;
                <bean:message key="money.managment.settings"/>
            </a></td>
            <td width="6" heigh="10" class="rightborder"></td>
        </tr>
        <%}%>

        <tr>
            <td width="5" heigh="10" class="leftborder"></td>
            <td height="10" colspan="2"><a class="menuitem"
                                           href="<%=response.encodeURL("profileEdit.do?action=viewProfile")%>">&nbsp;&nbsp;
                <bean:message key="account.profile"/>
                <%=LinkHelper.getUserName(session)%></a></td>
            <td width="6" heigh="10" class="rightborder"></td>
        </tr>

        
        
        
        <%--<%=response.encodeURL("guest.do?action=list")%>--%>
        
        
        
        
        

        
        
        
        <%--<%=response.encodeURL("billing.do")%>--%>
        
        
        
        
        

        
        
        
        <%--<%=response.encodeURL("accountSettings.do")%>--%>
        
        
        
        
        

        <tr>
            <td width="5" heigh="10" class="leftborder"></td>
            <%--<td height="10" colspan="2"><a class="menuitem" href="<%=response.encodeURL("login.do?action=logout")%>">--%>
            <td height="10" colspan="2"><a class="menuitem" href="<%=response.encodeURL("logout.do")%>">
                &nbsp;&nbsp;&nbsp;<bean:message key="account.logoff"/></a></td>
            <td width="6" heigh="10" class="rightborder"></td>
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


