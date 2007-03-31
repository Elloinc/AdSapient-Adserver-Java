<%@ page import="com.adsapient.gui.SendMailServlet" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
    <title>AdSapient</title>
    <link rel="STYLESHEET" type="text/css" href="adsapient.css">
    <script type='text/javascript' src='js/validation.js'></script>
</head>

<body>
<table class="main_table">
<tr>
    <td class="main_table_top">
        <table class="table_center" align="center">
            <tr>
                <td></td>
            </tr>
        </table>
    </td>
</tr>
<tr>
    <td class="main_table_top_links">
        <table class="table_center_links" align="center">
            <tr>
                <td>
                    <%@ include file="links.jsp" %>
                </td>
            </tr>
        </table>
    </td>
</tr>
<tr>
<td class="main_table_screen">
<table class="table_center_screen" align="center">
<tr>
<td width="780" height="100%" valign="top">
<!--Page Start-->
<table width="780" height="100%" cellpadding="0">
    <tr>
        <td width="10"></td>
        <td width="205" valign="top" align="left">
            <!-- Menu -->
            <table width="215" cellpadding="0" cellspacing="0">
                <tr>
                    <td class="menu_top"></td>
                </tr>
                <tr>
                    <td class="menu_middle"><br><br>
                        <a href="about.jsp" class="links_text">About AdSapient Inc.</a><br><br>
                        <a href="contact.jsp" class="links_text">Contact us</a><br><br><br>
                    </td>
                </tr>
                <tr>
                    <td class="menu_bottom"></td>
                </tr>
            </table>
        </td>
        <td width="30"></td>
        <td class="products_text" align="center" valign="top">
            <!-- Contact Form -->
            <strong>Contact</strong><br>
            <br>
            <%
                int result = SendMailServlet.READY;
                try {
                    result = Integer.parseInt(request.getParameter("result"));
                } catch (Exception ex) {
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
            <br>
            <br>

            <form action="/sendMail" method="POST" name="formContactUs">
                <table width="500" cellspacing="0" cellpadding="0" align="center"
                       class="forma_main">
                    <tr class="forma1">
                        <td width="200" align="right" class="forma" height="40">First Name<strong
                                class="highlight">*</strong></td>
                        <td width="300" align="center"><input type="Text" size="32" maxlength="50"
                                                              class="input_form" name="rtFirstName">
                        </td>
                    </tr>
                    <tr class="forma2">
                        <td width="200" align="right" class="forma" height="40">Last Name<strong
                                class="highlight">*</strong></td>
                        <td width="300" align="center"><input type="Text" size="32" maxlength="50"
                                                              class="input_form" name="rtLastName">
                        </td>
                    </tr>
                    <tr class="forma1">
                        <td width="200" align="right" class="forma" height="40">Company</td>
                        <td width="300" align="center"><input type="Text" size="32" maxlength="50"
                                                              class="input_form" name="stCompany">
                        </td>
                    </tr>
                    <tr class="forma2">
                        <td width="200" align="right" class="forma" height="40">Email<strong
                                class="highlight">*</strong></td>
                        <td width="300" align="center"><input type="Text" size="32" maxlength="200"
                                                              class="input_form" name="reEmail"></td>
                    </tr>
                    <tr class="forma1">
                        <td width="200" align="right" class="forma" height="200">Comments or
                            Questions<strong class="highlight">*</strong></td>
                        <td width="300" align="center"><textarea class="input_form" name="rtComment"
                                                                 cols="23" rows="10"
                                                                 wrap="virtual"></textarea></td>
                    </tr>
                    <tr class="forma2">
                        <td width="200" align="right" class="forma" height="40"></td>
                        <td width="300" align="center"><input type="submit"
                                                              onClick="return onSubmitCheck(document.forms['formContactUs'])"
                                                              value="Submit"
                                                              class="input_form"></td>
                    </tr>
                </table>
            </form>
            <br>
            <br>

        </td>
    </tr>
</table>


<!--Page End-->
</td>
</tr>
</table>
</td>
</tr>
<tr>
    <td class="main_table_bottom_links">
        <table class="table_center_links" align="center">
            <tr>
                <td>
                    <%@ include file="links.jsp" %>
                </td>
            </tr>
        </table>
    </td>
</tr>
<tr>
    <td class="main_table_bottom">
        <table class="table_center_bottom" align="center">
            <tr>
                <td class="table_center_bottom_text">
                    <%@ include file="copyrights.jsp" %>
                </td>
            </tr>
        </table>
    </td>
</tr>
</table>
</body>
</html>
