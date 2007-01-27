<%@ page import="com.adsapient.util.Msg"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri='/WEB-INF/struts-bean.tld' prefix='bean' %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
							  
<HTML>
  <HEAD>
	<link rel=stylesheet href="css/adsapient.css" type="text/css">
    <title>
		<tiles:useAttribute id="titleStr" name="title" classname="java.lang.String" /><%=Msg.fetch(titleStr,request)%>
	</title>
	<meta HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
	 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"  />
  </HEAD>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<br>

<center>
<tiles:insert attribute="header" />
</center>


<center>
<table width="98%" cellspacing="0" cellpadding="0" >
<tr>
<td width="3" height="3"><img src="images/angle1.gif"></td>
<td width="100%" colspan="5" height="3"style="background-image: url(images/angle2.gif);"></td>
<td width="3" height="3"><img src="images/angle3.gif"></td>
</tr>
<tr>
<td width="3" style="background-image: url(images/angle6.gif);"></td>
<td height="10" colspan="5" style="background-color: #FFFFFF;"></td>
<td width="3" style="background-image: url(images/angle6.gif);"></td>
</tr>

<tr>
<td width="3" style="background-image: url(images/angle6.gif);"></td>
<td width="1%" style="background-color: #FFFFFF;"></td>

<td width="20%">
<table width="100%"cellspacing="0" cellpadding="0">
    <tiles:insert attribute='menu'/>
</table>
</td>

<td width="1%" style="background-color: #FFFFFF;"></td>

<td width="77%" valign="top" style="background-color: #FFFFFF;">
<table width="100%" cellspacing="0" cellpadding="0">
    <tiles:insert attribute='body' />
</table>
</td>
<td width="10" style="background-color: #FFFFFF;"></td>
<td width="1%" style="background-image: url(images/angle6.gif);"></td>
</tr>
<tr>
<td width="3" style="background-image: url(images/angle6.gif);"></td>
<td height="10" colspan="5" style="background-color: #FFFFFF;"></td>
<td width="3" style="background-image: url(images/angle6.gif);"></td>
</tr>
<table width="98%" cellspacing="0" cellpadding="0" >
<tr>
<td width="3" height="3"><img src="images/angle5.gif"></td>
<td width="100%" colspan="5" height="3"style="background-image: url(images/angle2.gif);"></td>
<td width="3" height="3"><img src="images/angle4.gif"></td>
</tr>

</table>
</center>


<tiles:insert attribute="footer" />
<br>
</body>
</html>
  
  






    


