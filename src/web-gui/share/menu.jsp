<%@ page import="com.adsapient.api_impl.usermanagment.UserImpl" %>
<%@ page import="com.adsapient.api_impl.managment.application.ApplicationManagment" %>

<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%UserImpl user= (UserImpl) request.getSession().getAttribute("user");%>

<%--<%if  (ApplicationManagment.check(ApplicationManagment.AdNetwork)) {%>--%>
<%if("admin".equalsIgnoreCase(user.getRole())){%>
<tiles:insert attribute='menu1'/>
<tiles:insert attribute='menu2'/>
<tiles:insert attribute='menu3'/>
<tiles:insert attribute='menu4'/>
<tiles:insert attribute='menu5'/>
<%--<tiles:insert attribute='menu6'/>--%>
<%--<tiles:insert attribute='menu7'/>--%>
<tiles:insert attribute='menu11'/>
<tiles:insert attribute='menu12'/>
<tiles:insert attribute='menu13'/>
<tiles:insert attribute='menu14'/>
<%}%>

<%if("advertiserpublisher".equalsIgnoreCase(user.getRole())||"hostedservice".equalsIgnoreCase(user.getRole())){%>
<tiles:insert attribute='menu3'/>
<tiles:insert attribute='menu4'/>
<tiles:insert attribute='menu5'/>
<tiles:insert attribute='menu6'/>
<tiles:insert attribute='menu9'/>
<tiles:insert attribute='menu11'/>

<%}%>

<%if("advertiser".equalsIgnoreCase(user.getRole())){%>
<%--<tiles:insert attribute='menu4'/>--%>
<tiles:insert attribute='menu5'/>
<tiles:insert attribute='menu7'/>
<%--<tiles:insert attribute='menu8'/>--%>
<tiles:insert attribute='menu11'/>
<%}%>

<%if("publisher".equalsIgnoreCase(user.getRole())){%>
<tiles:insert attribute='menu5'/>
<tiles:insert attribute='menu6'/>
<tiles:insert attribute='menu10'/>
<tiles:insert attribute='menu11'/>
<%}%>
<%--<%}%>--%>














