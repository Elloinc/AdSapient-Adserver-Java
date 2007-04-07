
<%@ page import="com.adsapient.gui.SendMailServlet" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
    <link rel=stylesheet href="adsapient.css" type="text/css">
    <title>AdSapient Ad Serving Solutions - Contact</title>
    <meta name="description"
          content="Ad serving solutions: Banner ad management software and ad network software available both a hosted solution and as a standalone program. Smart demographic, psychographic, context and content targeting. ">
    <meta name="keywords"
          content="ad network software, advertising agency software, ad management, adserver, ad server, ad serve, ad serving, ad delivery, banner software, desktop advertising, desktop advertising software, advertising delivery, banner exchange software, ad banners, ad server, ad server solutions, ad-server, banner ad management, online ad software, ad delivery software, ad management, ad targetting, admanager, ad manager, adserve, advertising software, tracking software, ad tracking software, banner server, banner utility, banner serving, banner management software, ad serving technology, rotating banner ads, affiliate management, affiliate software, banner exchange, affiliate tracking, ad network, ad networks">
    <meta HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
    <script language="javascript" type="text/javascript" src="javascript/menu.js"></script>
    <script language="javascript" type="text/javascript" src='javascript/validation.js'></script>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="preloadImages();">
<center>
    <table cellspacing="0" cellpadding="0" width="865" border="0">
        <tr><td align="center"><img alt="AdSapient Ad Serving Solutions" src="images/logo.gif"></td>
            <td align="right" valign="bottom"><map NAME="bg_Map">
                <area SHAPE="poly" ALT="" COORDS="288,20, 392,20, 381,0, 299,0" HREF="contact.jsp"
                      ONMOUSEOVER="changeImages('bg', 'images/bg-contact_over.gif'); return true;"
                      ONMOUSEOUT="changeImages('bg', 'images/bg.gif'); return true;">
                <area SHAPE="poly" ALT="" COORDS="207,20, 196,0, 299,0, 288,20" HREF="support.jsp"
                      ONMOUSEOVER="changeImages('bg', 'images/bg-support_over.gif'); return true;"
                      ONMOUSEOUT="changeImages('bg', 'images/bg.gif'); return true;">
                <area SHAPE="poly" ALT="" COORDS="93,20, 82,0, 195,0, 184,20" HREF="products.jsp"
                      ONMOUSEOVER="changeImages('bg', 'images/bg-products_over.gif'); return true;"
                      ONMOUSEOUT="changeImages('bg', 'images/bg.gif'); return true;">
                <area SHAPE="poly" ALT="" COORDS="1,20, 12,0, 82,0, 93,20" HREF="index.jsp"
                      ONMOUSEOVER="changeImages('bg', 'images/bg-home_over.gif'); return true;"
                      ONMOUSEOUT="changeImages('bg', 'images/bg.gif'); return true;">
            </map>
                <img NAME="bg" SRC="images/bg.gif" WIDTH=392 HEIGHT=20 BORDER=0 USEMAP="#bg_Map"></td></tr>
    </table>
</center>

<center>
<table cellspacing="0" cellpadding="0">
<tr>
    <td width="3"><img src="images/angle1.gif"></td>
    <td width="200" height="3" style="background-image: url(images/vert.gif);"></td>
    <td><img src="images/angle7.gif"></td>
    <td width="656" height="3" style="background-image: url(images/vert.gif);"></td>
    <td width="3"><img src="images/angle2.gif"></td>
</tr>

<tr>
    <td height="174" width="3" style="background-image: url(images/hor.gif);"></td>
    <td width="200" height="174" valign="top" style="background-color:#D1DFFF;">
        <table border="0" width="100%">
            <br><br>
            <tr><td><nobr><a class="promotext2" href="about.jsp">&nbsp;&nbsp;&nbsp;About AdSapient</a></nobr></td>
            </tr>
            <tr><td><nobr><a class="promotext2" href="contact.jsp">&nbsp;&nbsp;&nbsp;Contact us</a></nobr></td></tr>
            <tr><td align="center">
                <table cellpadding="0" cellspacing="0" border="0">
                    <tr><br><td align="center">
                        <!--<a href="http://chat.boldcenter.com/aid/1383567893338894994/bc.chat" target="_blank" onClick="this.newWindow = window.open('http://chat.boldcenter.com/aid/1383567893338894994/bc.chat?url=' + document.location, 'Chat', 'toolbar=0,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=1,width=640,height=480');this.newWindow.focus();this.newWindow.opener=window;return false;"><img alt="Live Customer Support" src="images/live.gif" width="120" height="70" border="0"></a>--></td>
                    </tr>
                </table>
            </td></tr>
        </table>
    </td>

    <td height="174" width="3" style="background-image: url(images/hor.gif);"></td>
    <td width="656" height="174" valign="top" style="background-color: #FFE8D0;">
        <center>
            <table border="0" cellspacing="10">
                <tr><td colspan="2" class="title2">Contact</td></tr>
                <tr><td colspan="2" width="250" class="promotext">
                    <%
                        int result = SendMailServlet.READY;
                        try {
                            result = Integer.valueOf((Byte) request.getAttribute("__result"));
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                        }
                        if (SendMailServlet.APP_RES == null) {
                            result = SendMailServlet.READY;
                        }
                        if (result == SendMailServlet.SUCCESS) {
                            out.println("<font style=\"color:green;\">" + SendMailServlet.APP_RES.getProperty("message.success") + "</font>");
                        } else if (result == SendMailServlet.FAILURE) {
                            out.println("<font style=\"color:red;\">" + SendMailServlet.APP_RES.getProperty("message.failure") + "</font>");
                        } else {
                            out.println("Contact us using this form:");
                        }
                    %>

                </td></tr>


                <tr><td colspan="2" class="promotext">&nbsp;&nbsp;&nbsp; </td></tr>

                <form name="formContactUs" action="/sendMail" method="POST">
                    <input type="hidden" name="__result">
                    <tr>
                        <td class="promotext">First Name<span class="ffRequiredFlag">*</span></td>
                        <td width="250" class="promotext"><font face="Verdana,Arial,helvetica" size="1">
                            <input class="ffField" name="rtFirstName" type="text"
                                   value="<%=request.getParameter("rtFirstName") == null ? "" : request.getParameter("rtFirstName")%>"
                                   size="22" maxlength="50"></font>
                        </td>
                    </tr>
                    <tr>
                        <td class="promotext">Last Name<span class="ffRequiredFlag">*</span></td>
                        <td class="promotext"><font face="Verdana,Arial,helvetica" size="1">
                            <input class="ffField"
                                   value="<%=request.getParameter("rtLastName") == null ? "" : request.getParameter("rtLastName")%>"
                                   name="rtLastName" type="text" value="" size="22" maxlength="50">
                        </font>
                        </td>
                    </tr>
                    <tr>
                        <td class="promotext">Company</td>
                        <td class="promotext"><font face="Verdana,Arial,helvetica" size="1">
                            <input class="ffField"
                                   value="<%=request.getParameter("stCompany") == null ? "" : request.getParameter("stCompany")%>"
                                   name="stCompany" type="text" size="30" maxlength="50">
                        </font>
                        </td>
                    </tr>
                    <tr>
                        <td class="promotext">Email<span class="ffRequiredFlag">*</span></td>
                        <td class="promotext"><font face="Verdana,Arial,helvetica" size="1">
                            <input class="ffField" name="reEmail" type="text"
                                   value="<%=request.getParameter("reEmail") == null ? "" : request.getParameter("reEmail")%>"
                                   size="30" maxlength="200"></font>
                        </td>
                    </tr>
                    <tr><td valign="top" class="promotext">Comments or Questions<span class="ffRequiredFlag">*</span>
                    </td>
                        <td><font face="Verdana,Arial,helvetica" size="1">
                            <textarea class="ffField" name="rtComment"
                                      cols="41" rows="8" wrap="virtual"><%=request.getParameter("rtComment") == null ? "" : request.getParameter("rtComment")%></textarea>
                        </font></td>
                    </tr>
                    <tr><td></td><td><input type="submit"
                                            onClick="return onSubmitCheck(document.forms['formContactUs'])"
                                            value="Submit"></td></tr>
                    <tr><td></td></tr>
                </form>
            </table>
        </center>
    </td>
    <td height="174" width="3" style="background-image: url(images/hor.gif);"></td>
</tr>
<tr>
    <td width="3"><img src="images/angle4.gif"></td>
    <td height="3" style="background-image: url(images/vert.gif);"></td>
    <td width="3"><img src="images/angle5.gif"></td>
    <td height="3" style="background-image: url(images/vert.gif);"></td>
    <td width="3"><img src="images/angle3.gif"></td>
</tr>
</table>
</center>

<%@ include file="tiles/footnote.jsp" %>
<br>


</body>
</html>

