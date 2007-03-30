
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %> 
<%@ taglib uri='/WEB-INF/struts-bean.tld' prefix='bean' %>
<%@ page contentType="text/html; charset=UTF-8" %>

<html:form action="htmlBanner" >

<html:hidden property="action"/> 
<html:hidden property="bannerId"/> 
<html:hidden property="campainId"/>  
<html:hidden property="stateId"/>

<html:textarea style="width:200;height:100" property ="HTMLBannerSource"/> <br>
<html:text property="prioritet"/> <br>
size <html:select  property="sizeId" ><html:optionsCollection property="sizesCollection" /></html:select> <br>
type <html:select  property="typeId" ><html:optionsCollection property="typesCollection" /></html:select> <br> 
</html:form>
