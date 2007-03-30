
<%@ taglib uri='/WEB-INF/struts-bean.tld' prefix='bean' %>
<%
    String exceptionReason = (String)request.getAttribute("exceptionReason");
%>

<html>
<head>
<title></title>
    <link rel="stylesheet" href="css/main.css" type="text/css" />
</head>
<body background="images/bg_body.gif" leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0" marginwidth="0" marginheight="0">

            <bean:message key="security.exception" /> : <%=exceptionReason%>
		</body>
</html>
