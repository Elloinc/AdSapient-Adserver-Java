<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri='/WEB-INF/struts-bean.tld' prefix='bean' %>
<html:errors/>
<html:messages id="msg" message="true" header="messages.header" footer="messages.footer" >
 <bean:write name="msg" />
</html:messages>


<%
    String exceptionReason = (String)request.getAttribute("exceptionReason");
	if (exceptionReason!=null){
%>
<tr><td><table width="100%" cellspacing="0" cellpadding="0"><tr><td width="5" height="1"><img src="images/table10.gif"></td><td heigh="1" width="100%" style="background-color: #0030AA;"></td><td width="6" height="1" ><img src="images/table9.gif"></td></tr></table></td></tr>
<tr><td><table width="100%" cellspacing="0" cellpadding="0"><tr><td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td><td height="10" ><table  border="0" width="100%" cellspacing="5"><tr><td style="background-color: #e0e0e0;	text-align : left;font-size : 14px;font-family : sans-serif;color : Red;font-weight : normal;text-decoration : none;line-height: 20px;">&nbsp;&nbsp;
<%=exceptionReason%>
</td></tr></table></td><td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td></tr></table></td></tr>
<tr><td><table width="100%" cellspacing="0" cellpadding="0"><tr><td><img src="images/table6.gif"></td><td width="100%" heigh="10" style="background-image: url(images/table8.gif);"></td><td><img src="images/table7.gif"></td></tr></table></td></tr>
<% } %>

<%--
<tr><td>
<img src="images/titleImage.jpg" width="321" height="540">
</td></tr>
--%>
<tr><td>
	<table width="100%"cellspacing="0" cellpadding="0"><tr>
	<td><img src="images/table1.gif"></td>
	<td width="100%" class="tableheader">Welcome to AdSapient</td>
	<td><img src="images/table2.gif"></td>
	</tr></table>
</td></tr>
<tr><td>
	<table width="100%" cellspacing="0" cellpadding="0">
	
	<tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td class="maintext"><table border="0" width="100%" cellspacing="5"><tr><td class="maintext">
        Open source ad serving platform.

</td></tr></table></td>
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
