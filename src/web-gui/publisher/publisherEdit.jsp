<%@ page import="com.adsapient.api_impl.publisher.*,
                              com.adsapient.web.publisher.edit.*,
                              com.adsapient.shared.service.I18nService" %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %> 
<%@ taglib uri='/WEB-INF/struts-bean.tld' prefix='bean' %>
<%@ page contentType="text/html; charset=UTF-8" %>


<% EditPublisherSiteActionForm form=(EditPublisherSiteActionForm) request.getAttribute("editPublisherSiteActionForm");
   %>
 


  
 <html:form action="publisherEdit" >  <br>
<html:hidden property="siteId"/> <br>
<html:hidden property="action"/>
<html:hidden property="userId"/>
  <bean:message key="allow.clicks.campain"/><html:checkbox property="clicksCampainAllow" /> <br>
 <bean:message key="url"/> <html:text property="url"/>    <br>
 <bean:message key="description"/><html:textarea property="description"/>     <br>
 <bean:message key="category"/><html:select  property="categoryId" ><html:optionsCollection property="categoryCollection" /></html:select> <br> 
 
<input type="submit" value='<%=Msg.fetch(form.getAction(),session)%>'>    
</html:form>  
<% if (!"add".equalsIgnoreCase(form.getAction())) {%>
<br>
<%if (!form.getSitePlacesCollection().isEmpty()) {%>
<h1><bean:message key="registered.site.places"/> </h1>
<table onload="checkIsPlaceSelected()">
<tr><td> <bean:message key="registered.place"/></td> <td><bean:message key="source.view"/> </td> <td><bean:message key="unregister.place"/> <td></tr>

<logic:iterate  id="placeMy"  name="editPublisherSiteActionForm" property="sitePlacesCollection">
<%PlaceImpl places= (PlaceImpl) placeMy;%>
<tr><td><%=places.getPlace()%></td>
      <td><a href='placeEdit.do?placeAction=viewsource&placeId=<%=places.getPlaceId()%>&siteId=<%=form.getSiteId()%>'><bean:message key="source.view"/></a> <td>
      <td><a href='placeEdit.do?placeAction=remove&placeId=<%=places.getPlaceId()%>&siteId=<%=form.getSiteId()%>'><bean:message key="unregister.place"/> </a><td>  
	 	
</logic:iterate> 

</table>
<br>
<%}%>
<html:form action="placeEdit"> 
<input type="hidden" name="action" value="view">
<input type="hidden" name="siteId" value="<%=form.getSiteId()%>">
                                                                   
<html:select  property="placeId"  onchange="checkIsPlaceSelected()"><html:optionsCollection property="placeCollection" /></html:select> <br>
size <html:select  property="sizeId" ><html:optionsCollection property="sizesCollection" /></html:select>   <br> 
<input type="submit" value='<bean:message key="add"/>'> 
<input type="button" value="<bean:message key="back"/>"  onclick="document.location='<%=response.encodeURL("publisherView.do")%>'"/>
 </html:form>   
     <%}%>
 
