<%@ page import="java.util.*" %>
<%@ page import = "java.io.PrintWriter" %>
<%@ page import="com.adsapient.util.Msg"%>
<%@ page import="com.adsapient.api_impl.facade.AdsapientSystemFasade"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<%  String filename = AdsapientSystemFasade.generateDiagram(request,new PrintWriter(out));
	String graphURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + filename;
%>

<tr><td>
	<table width="100%"cellspacing="0" cellpadding="0"><tr>
	<td><img src="images/table1.gif"></td>
	<td width="100%" class="tableheader"><%=Msg.fetch(request.getParameter("headerMsg"),request)%><%=((request.getParameter("siteId")==null)?"":" #"+request.getParameter("siteId"))%></td>
	<td><img src="images/table2.gif"></td>
	</tr></table>
</td></tr>
<tr><td>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td  class="maintext"><table border="0" width="100%" cellspacing="5"><tr><td align="center"><img src="<%= graphURL %>" width=500 height=300 border=0 usemap="#<%= filename %>"></td></tr></table></td>
	<td ><table border="0" width="100%" cellspacing="5"><tr><td></td></tr></table></td>
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
