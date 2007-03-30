<%@ page import="com.adsapient.shared.mappable.UserImpl" %>

<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%UserImpl user= (UserImpl) request.getSession().getAttribute("user");%>




<%if("advertiserpublisher".equalsIgnoreCase(user.getRole())
		||"admin".equalsIgnoreCase(user.getRole())
			||"hostedservice".equalsIgnoreCase(user.getRole())){%>
<tiles:insert attribute='content1'/>
<tiles:insert attribute='content2'/>
<tiles:insert attribute='content3'/>
<%}%>

<%if("advertiser".equalsIgnoreCase(user.getRole())){%>
<tiles:insert attribute='content1'/>
<tiles:insert attribute='content2'/>
<%}%>

<%if("publisher".equalsIgnoreCase(user.getRole())){%>
<tiles:insert attribute='content1'/>
<tiles:insert attribute='content3'/>
<%}%>







