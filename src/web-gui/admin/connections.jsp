<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %>
<%@ taglib uri='/WEB-INF/struts-bean.tld' prefix='bean' %>
<%@ page contentType="text/html; charset=UTF-8" %>

<html:messages id="msg" message="true" header="messages.header" footer="messages.footer" >
 <bean:write name="msg" />
</html:messages>

<html:form action="adminOptions">
    <html:hidden property="action"/>
    <input type="hidden" name="type" value="mail">
    <tr><td>
        <table width="100%" cellspacing="0" cellpadding="0"><tr>
            <td><img src="images/table1.gif"></td>
            <td width="100%" class="tableheader"><bean:message key="syssettings.networkconnections"/></td>
            <td><img src="images/table2.gif"></td>
        </tr></table>
    </td></tr>
    <tr><td>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">

            
            <tr>
                <td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
                <td height="10" class="maintext">
                    <table border="0" width="100%" cellspacing="5"><tr><td>&nbsp;&nbsp;<bean:message key="mail.server"/>
                        :</td></tr></table>
                </td>
                <td >
                    <table border="0" width="100%" cellspacing="5"><tr><td><html:text style="width:150"
                                                                                      property="mailServer"/></td></tr>
                    </table>
                </td>
                <td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
            </tr>

            
            <tr>
                <td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
                <td height="10" class="maintext">
                    <table border="0" width="100%" cellspacing="5"><tr><td>&nbsp;&nbsp;<bean:message
                            key="mail.server.login"/>:</td></tr></table>
                </td>
                <td >
                    <table border="0" width="100%" cellspacing="5"><tr><td><html:text style="width:150"
                                                                                      property="mailServerLogin"/></td>
                    </tr></table>
                </td>
                <td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
            </tr>
            <tr>
                <td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
                <td height="10" class="maintext">
                    <table border="0" width="100%" cellspacing="5"><tr><td>&nbsp;&nbsp;<bean:message
                            key="mail.server.password"/>:</td></tr></table>
                </td>
                <td >
                    <table border="0" width="100%" cellspacing="5"><tr><td><html:text style="width:150"
                                                                                      property="mailServerPassword"/></td>
                    </tr></table>
                </td>
                <td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
            </tr>

            <tr>
                <td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
                <td ></td>
                <td height="10" >
                    <table border="0" width="100%" cellspacing="5"><tr><td><html:submit/></td></tr></table>
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
