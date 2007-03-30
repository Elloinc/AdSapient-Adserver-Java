<%@ page import="com.adsapient.shared.mappable.EntityWrap"%>
<%@ page import="com.adsapient.gui.forms.AdminsTuningActionForm"%>
<%@ page import="com.adsapient.shared.service.I18nService"%>
<%@ page import="com.adsapient.gui.ContextAwareGuiBean"%>
<%@ page import="org.springframework.context.ApplicationContext"%>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %>
<%@ taglib uri='/WEB-INF/struts-bean.tld' prefix='bean' %>
<%@ page contentType="text/html; charset=UTF-8" %> 

<html:errors/>
<html:messages id="msg" message="true" header="messages.header" footer="messages.footer" >
 <bean:write name="msg" />
</html:messages>

<html:form action="adminTuning" enctype="www-form-urlencoded">
<html:hidden property="action"/>
<html:hidden property="entity"/> 

<% ApplicationContext ctx = ContextAwareGuiBean.getContext();%>
<% I18nService i18nService = (I18nService) ctx.getBean("i18nService");%>
<% AdminsTuningActionForm form= (AdminsTuningActionForm) request.getAttribute("adminsTuningActionForm");%>
<% String ent = form.getEntity(); %>
<tr><td>
	<table width="100%"cellspacing="0" cellpadding="0"><tr>
	<td><img src="images/table1.gif"></td>
	<td width="100%" class="tableheader"><%=i18nService.fetch(ent+".list.title", session)%></td>
	<td><img src="images/table2.gif"></td>
	</tr></table>
</td></tr>
<tr><td>
	<table width="100%" cellspacing="0" cellpadding="0">
	
	<tr>
		<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
		<td><table border="0" style="background-color:#ffffff;" cellspacing="1" width="100%"><tr>
		<td height="10"  width="1%" class="tabletableheader"><bean:message key="id"/></td>
		<td height="10"  width="74%" class="tabletableheader"><%=i18nService.fetch(ent+".name", session)%></td>
		<td height="10"  width="5%" class="tabletableheader" colspan="2"><nobr><bean:message key="actions"/></nobr></td>
	</tr>
	
	<logic:iterate  id="entityMy" name="adminsTuningActionForm" property="entitysCollection">
	<% EntityWrap entity = (EntityWrap) entityMy;%>
	<tr>
		<td height="10" class="tabledata"><nobr><%=entity.getEntityId()%></nobr></td>
		<td height="10" class="tabledata"><nobr><%=entity.getEntityValue()%></nobr></td>
		<% if("size".equalsIgnoreCase(ent)){%>
			<td height="10" class="tabledata-c"><nobr><a class="tabledata" href='<%=response.encodeURL("defaultBanners.do?action=view&id="+entity.getEntityId())%>'><img src="images/icons/edit.png" border="0" title=<bean:message key="edit"/>></a></nobr></td> 
		<%}%>
	<td height="10" class="tabledata-c"><nobr><a class="tabledata" href="<%=response.encodeURL("adminTuning.do?action=remove&entity="+entity.getEntityType()+"&entityId="+entity.getEntityId())%>" onClick="return window.confirm('<%=(i18nService.fetch("delete.confirm",session)+" "+entity.getEntityId())%>');"><img src="images/icons/remove.png" border="0" title=<bean:message key="remove"/>></a></nobr></td> 
	</tr>
	</logic:iterate>	
		<tr>
	<td height="10"  colspan="8"  class="tabledata">
        <table border="0" width="100%" cellspacing="5">
		    <tr>
	            <td class="tabledata"><%=i18nService.fetch(ent+".new", session)%>&nbsp;&nbsp;<sup>*</sup></td>
	            <td class="tabledata"><html:text property="entityValue" maxlength="50"/></td>
	            <td class="tabledata"><html:submit/> </td>
		    </tr>
        </table>
     </td>
		</tr>
         <tr>
             <td colspan="10" class="tabledata">
                 <table border="0" width="100%" cellspacing="5"
                     <tr>
                         <td><bean:message key="form.msg.mandatory"/></td>
                     </tr>
                 </table>
             </td>
         </tr>
    </table>
    </td>
	<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr>
	</table>
</td></tr>
</html:form>

<tr><td>
	<table width="100%" cellspacing="0" cellpadding="0"><tr>
	<td><img src="images/table6.gif"></td>
	<td width="100%" heigh="10" style="background-image: url(images/table8.gif);"></td>
	<td><img src="images/table7.gif"></td>
	</tr></table>
</td></tr>
