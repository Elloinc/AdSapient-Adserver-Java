<%@ page import="com.adsapient.util.Msg" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri='/WEB-INF/struts-bean.tld' prefix='bean' %>

<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %>

<%@ page contentType="text/html; charset=UTF-8" %>

<HTML>
<HEAD>
    <link rel=stylesheet href="css/adsapient.css" type="text/css">
    <link rel="stylesheet" media="screen" href="css/dynCalendar.css"/>
    <title>
        <tiles:useAttribute id="titleStr" name="title" classname="java.lang.String"/>
        <%=Msg.fetch(titleStr, request)%>
    </title>
    <script language="javascript" type="text/javascript" src="javascript/browserSniffer.js"></script>
    <script language="javascript" type="text/javascript" src="javascript/dynCalendar.js"></script>
    <script language="javascript" type="text/javascript" src="javascript/ui.js"></script>
    <script language="javascript" type="text/javascript" src="javascript/form.js"></script>
    <script type="text/javascript" src="javascript/LibCrossBrowser.js"></script>
    <script type="text/javascript" src="javascript/EventHandler.js"></script>
    <script type="text/javascript" src="javascript/Bs_FormUtil.js"></script>
    <script type="text/javascript" src="javascript/Bs_Slider.js"></script>
    <script type="text/javascript" src="javascript/ads.js"></script>

</HEAD>

<body onLoad="javascript:initSlider();initPriorities();selectionChanged();" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<center>
    <tiles:insert attribute="header"/>
</center>


<center>
    <table width="98%" cellspacing="0" cellpadding="0">
        <tr>
            <td width="3" height="3"><img src="images/angle1.gif"></td>
            <td width="100%" colspan="5" height="3" style="background-image: url(images/angle2.gif);"></td>
            <td width="3" height="3"><img src="images/angle3.gif"></td>
        </tr>
        <tr>
            <td width="3" style="background-image: url(images/angle6.gif);"></td>
            <td height="10" colspan="5" style="background-color: #FFE8D0;"></td>
            <td width="3" style="background-image: url(images/angle6.gif);"></td>
        </tr>

        <tr>
            <td width="3" style="background-image: url(images/angle6.gif);"></td>
            <td width="1%" style="background-color: #FFE8D0;"></td>
            
            <td width="20%" valign="top" style="background-color: #FFE8D0;">
                <table width="100%" cellspacing="0" cellpadding="0">
                    <tiles:insert attribute='menu'/>
                </table>
            </td>
            
            <td width="1%" style="background-color: #FFE8D0;"></td>
            
            <td width="77%" valign="top" style="background-color: #FFE8D0;">
                <table width="100%" cellspacing="0" cellpadding="0">
                    <tiles:insert attribute='body'/>
                </table>
            </td>
            <td width="10" style="background-color: #FFE8D0;"></td>
            <td width="3" style="background-image: url(images/angle6.gif);"></td>
        </tr>
        <tr>
            <td width="3" style="background-image: url(images/angle6.gif);"></td>
            <td height="10" colspan="5" style="background-color: #FFE8D0;"></td>
            <td width="3" style="background-image: url(images/angle6.gif);"></td>
        </tr>
        <table width="98%" cellspacing="0" cellpadding="0">
            <tr>
                <td width="3" height="3"><img src="images/angle5.gif"></td>
                <td width="100%" colspan="5" height="3" style="background-image: url(images/angle2.gif);"></td>
                <td width="3" height="3"><img src="images/angle4.gif"></td>
            </tr>

        </table>
</center>


<tiles:insert attribute="footer"/>
<br>
</body>
</html>
  
  






    


