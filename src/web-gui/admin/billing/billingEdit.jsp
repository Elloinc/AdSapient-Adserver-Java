<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %>
<%@ taglib uri='/WEB-INF/struts-bean.tld' prefix='bean' %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<html:errors/>
<html:messages id="msg" message="true" header="messages.header" footer="messages.footer" >
 <bean:write name="msg" />
</html:messages>

<html:form action="billing">
    <html:hidden property="action"/>
    <html:hidden property="type"/>

    <%--<%BillingActionForm form = (BillingActionForm) request.getAttribute("billingActionForm"); %>--%>
    <tr><td>
        <table width="100%" cellspacing="0" cellpadding="0"><tr>
            <td><img src="images/table1.gif"></td>
            <td width="100%" class="tableheader"><bean:message key="billing.info"/></td>
            <td><img src="images/table2.gif"></td>
        </tr></table>
    </td></tr>
    <tr><td>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">

            
            <tr>
                <td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
                <td height="10" class="maintext">
                    <table border="0" width="100%" cellspacing="5"><tr><td>&nbsp;&nbsp;<bean:message
                            key="paypal.login"/>:</td></tr></table>
                </td>
                <td  class="maintext" style="background-color: #D1DFFF;">
                    <table  class="maintext" border="0" width="100%" cellspacing="5"><tr><td  class="maintext" ><html:text style="width:300"
                                                                                      property="payPalLogin"/></td></tr>
                    </table>
                </td>
                <td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
            </tr>

                <%--<% if ("edit".equals(form.getAction())){ %>--%>
            <!--
    <tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td height="10" class="maintext"><table border="0" width="100%" cellspacing="5"><tr><td>&nbsp;&nbsp;
	<bean :message key="minimum.pub.pay"/>:
	</td></tr></table></td>
	<td style="background-color: #D1DFFF;"><table border="0" width="100%" cellspacing="5"><tr><td>
	<%--<%=form.getMinimumPayout()%>--%>
	</td></tr></table></td>
	<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr> 
	          -->
                <%--<%} else {%>--%>
            <tr>
                <td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
                <td height="10" class="maintext">
                    <table  class="maintext" border="0" width="100%" cellspacing="5"><tr><td>&nbsp;&nbsp;<bean:message
                            key="minimum.pub.pay"/>:</td></tr></table>
                </td>
                <td  class="maintext" style="background-color: #D1DFFF;">
                    <table  class="maintext" border="0" width="100%" cellspacing="5"><tr><td><html:text property="minimumPayout"/></td>
                    </tr></table>
                </td>
                <td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
            </tr>


                <%--<%}%>--%>
            <tr>
                <td  class="maintext" width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
                <td  class="maintext" ></td>
                <td  class="maintext" height="10" style="background-color: #D1DFFF;">
                    <table class="maintext"  border="0" width="100%" cellspacing="5"><tr><td class="maintext" ><input type="submit"
                                                                                  value='<bean:message key="submit"/>'>
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
