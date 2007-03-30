<%@ page import="com.adsapient.shared.service.I18nService" %>
<%@ page import="com.adsapient.gui.forms.FinancialManagmentActionForm"%>
<%@ page import="org.springframework.context.ApplicationContext"%>
<%@ page import="com.adsapient.gui.ContextAwareGuiBean"%>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %>
<%@ taglib uri='/WEB-INF/struts-bean.tld' prefix='bean' %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix="tiles" %>
<%@ page contentType="text/html; charset=UTF-8" %> 
<% ApplicationContext ctx = ContextAwareGuiBean.getContext();%>
<% I18nService i18nService = (I18nService) ctx.getBean("i18nService");%>
 <%FinancialManagmentActionForm form=(FinancialManagmentActionForm)
 request.getAttribute("financialManagmentActionForm");%>
<html:errors/>
<html:messages id="msg" message="true" header="messages.header" footer="messages.footer" >
 <bean:write name="msg" />
</html:messages>

<html:form action="financialManagement">
<html:hidden property="action"/>
<html:hidden property="userId"/>
<html:hidden property="head"/>
	<tr><td>
	<table width="100%"cellspacing="0" cellpadding="0"><tr>
	<td><img src="images/table1.gif"></td>
	<td width="100%" class="tableheader"><%=form.getHead()%></td>
	<td><img src="images/table2.gif"></td>
	</tr></table>
</td></tr>
<tr><td>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">

	
	<tr>
		<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
      	<td height="10" class="maintext" colspan="3">&nbsp;</td>
      <td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
    </tr>
	<tr>
		<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
		<td class="maintext" colspan="3">
		<table>
		<%if((!"".equals(form.getAdvertiserType()))&&("".equals(form.getPublisherType()))){%>
		<tr >
			<td colspan="3" height="30" class="maintext">&nbsp;&nbsp;<bean:message key="note.rates.overwritten" /></td>
		</tr>
        <tr>
			<td height="30"  class="maintext">&nbsp;&nbsp;<bean:message key="defaultCPMrate"/>:&nbsp;&nbsp;<sup>*</sup></td>
			<td class="maintext"> <html:text property="advertisingCPMrate" maxlength="5"/> </td>
			<td class="maintext">&nbsp;</td>
		</tr>
		<tr>
			<td height="30"  class="maintext">&nbsp;&nbsp;<bean:message key="defaultCPCrate"/>:&nbsp;&nbsp;<sup>*</sup></td>
			<td class="maintext"><html:text property="advertisingCPCrate" maxlength="5"/></td>
			<td class="maintext">&nbsp;</td>		
		</tr>
		<tr>		
			<td height="30"  class="maintext">&nbsp;&nbsp;<bean:message key="defaultCPLrate"/>:&nbsp;&nbsp;<sup>*</sup></td>
			<td class="maintext"> <html:text property="advertisingCPLrate" maxlength="5"/> </td>
			<td class="maintext">&nbsp;</td>
		</tr>
		<tr>
			<td height="30"  class="maintext">&nbsp;&nbsp;<bean:message key="defaultCPSrate"/>:&nbsp;&nbsp;<sup>*</sup></td>
			<td class="maintext"><html:text property="advertisingCPSrate" maxlength="5"/></td>
			<td class="maintext">&nbsp;</td>		
		</tr>


      <%}
		if((!"".equals(form.getPublisherType()))&&("".equals(form.getAdvertiserType()))){%>
		<tr>
			<td height="30"  class="maintext">&nbsp;&nbsp;<bean:message key="defaultCPMrate"/>:&nbsp;&nbsp;<sup>*</sup></td>
			<td class="maintext"><html:text property="publishingCPMrate" maxlength="5"/></td>
			<td class="maintext">&nbsp;</td>
		</tr>
		<tr>		
			<td height="30"  class="maintext">&nbsp;&nbsp;<bean:message key="defaultCPCrate"/>:&nbsp;&nbsp;<sup>*</sup></td>
			<td class="maintext"><html:text property="publishingCPCrate" maxlength="5"/></td>
			<td class="maintext">&nbsp;</td>		
		</tr>
		<tr>
			<td height="30"  class="maintext">&nbsp;&nbsp;<bean:message key="defaultCPLrate"/>:&nbsp;&nbsp;<sup>*</sup></td>
			<td class="maintext"><html:text property="publishingCPLrate" maxlength="5"/></td>
			<td class="maintext">&nbsp;</td>
		</tr>
		<tr>		
			<td height="30"  class="maintext">&nbsp;&nbsp;<bean:message key="defaultCPSrate"/>:&nbsp;&nbsp;<sup>*</sup></td>
			<td class="maintext"><html:text property="publishingCPSrate" maxlength="5"/></td>
			<td class="maintext">&nbsp;</td>		
		</tr>
		<%}
	if((!"".equals(form.getAdvertiserType())) && (!"".equals(form.getPublisherType())))
	  {%>
		
		<tr>		
			<td height="30"  class="maintext">&nbsp;&nbsp;<bean:message key="advertisingCPMrate.value"/>:&nbsp;&nbsp;<sup>*</sup></td>
			<td class="maintext"> <html:text property="advertisingCPMrate" maxlength="5"/> </td>
			<td class="maintext">&nbsp;</td>
		</tr>
		<tr>
			<td height="30"  class="maintext">&nbsp;&nbsp;<bean:message key="advertisingCPCrate.value"/>:&nbsp;&nbsp;<sup>*</sup></td>
			<td class="maintext"><html:text property="advertisingCPCrate" maxlength="5"/></td>
			<td class="maintext">&nbsp;</td>		
		</tr>
		<tr>		
			<td height="30"  class="maintext">&nbsp;&nbsp;<bean:message key="advertisingCPLrate.value"/>:&nbsp;&nbsp;<sup>*</sup></td>
			<td class="maintext"> <html:text property="advertisingCPLrate" maxlength="5"/> </td>
			<td class="maintext">&nbsp;</td>
		</tr>
		<tr>
			<td height="30"  class="maintext">&nbsp;&nbsp;<bean:message key="advertisingCPSrate.value"/>:&nbsp;&nbsp;<sup>*</sup></td>
			<td class="maintext"><html:text property="advertisingCPSrate" maxlength="5"/></td>
			<td class="maintext">&nbsp;</td>		
		</tr>   	

		<tr>
			<td height="30"  class="maintext">&nbsp;&nbsp;<bean:message key="publishingCPMrate.value"/>:&nbsp;&nbsp;<sup>*</sup></td>
			<td class="maintext"><html:text property="publishingCPMrate" maxlength="5"/></td>
			<td class="maintext">&nbsp;</td>
		</tr>
		<tr>		
			<td height="30"  class="maintext">&nbsp;&nbsp;<bean:message key="publishingCPCrate.value"/>:&nbsp;&nbsp;<sup>*</sup></td>
			<td class="maintext"><html:text property="publishingCPCrate" maxlength="5"/></td>
			<td class="maintext">&nbsp;</td>		
		</tr>
		<tr>
			<td height="30"  class="maintext">&nbsp;&nbsp;<bean:message key="publishingCPLrate.value"/>:&nbsp;&nbsp;<sup>*</sup></td>
			<td class="maintext"><html:text property="publishingCPLrate" maxlength="5"/></td>
			<td class="maintext">&nbsp;</td>
		</tr>
		<tr>		
			<td height="30"  class="maintext">&nbsp;&nbsp;<bean:message key="publishingCPSrate.value"/>:&nbsp;&nbsp;<sup>*</sup></td>
			<td class="maintext"><html:text property="publishingCPSrate" maxlength="5"/></td>
			<td class="maintext">&nbsp;</td>		
		</tr> 
		 
	<%}%>
		</table></td>
		<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr>
   
  <%if(form.getUserId().length()==0 || form.getUserId().equals("0"))
   {%>
	<tr>
		<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
		<td  colspan="3" ><hr></td>
		<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr>
	<tr>
		<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
		<td class="maintext" colspan="3">
		<table>
		
		<%--<tr>
			<td class="maintext">&nbsp;&nbsp;<html:checkbox  property="advCPM"/></td>
			<td height="30" class="maintext"><bean:message key="advertisingCPMrate"/></td>
			<td class="maintext">&nbsp;</td>
		</tr>
		<tr>
			<td class="maintext">&nbsp;&nbsp;<html:checkbox  property="advCPC" /></td>
			<td height="30" class="maintext"><bean:message key="advertisingCPCrate"/></td>
			<td class="maintext">&nbsp;</td>
		</tr>
		<tr>	
			<td class="maintext">&nbsp;&nbsp;<html:checkbox  property="advCPL" /></td>
			<td height="30" class="maintext"><bean:message key="advertisingCPLrate"/></td>
			<td class="maintext">&nbsp;</td>
		</tr>
		<tr>
			<td class="maintext">&nbsp;&nbsp;<html:checkbox  property="advCPS" /></td>
			<td height="30" class="maintext"><bean:message key="advertisingCPSrate"/></td>
			<td class="maintext">&nbsp;</td>
		</tr>   
			
		<tr>	
			<td class="maintext">&nbsp;&nbsp;<html:checkbox  property="pubCPM" /></td>
			<td height="30" class="maintext"><bean:message key="publishingCPMrate"/></td>		
			<td class="maintext">&nbsp;</td>
		</tr>
		<tr>		
			<td class="maintext">&nbsp;&nbsp;<html:checkbox  property="pubCPC" /> </td> 
			<td height="30" class="maintext"><bean:message key="publishingCPCrate"/></td>			
			<td class="maintext">&nbsp;</td>	
		</tr>
		<tr>	
			<td class="maintext">&nbsp;&nbsp;<html:checkbox  property="pubCPL" /></td>
			<td height="30" class="maintext"><bean:message key="publishingCPLrate"/></td>		
			<td class="maintext">&nbsp;</td>
		</tr>
		<tr>		
			<td class="maintext">&nbsp;&nbsp;<html:checkbox  property="pubCPS" /> </td> 
			<td height="30" class="maintext"><bean:message key="publishingCPSrate"/></td>			
			<td class="maintext">&nbsp;</td>	
		</tr>
        <tr>
			<td class="maintext">&nbsp;&nbsp;<html:checkbox  property="pubCPS" /> </td> 
			<td height="30" class="maintext"><bean:message key="publishingCPSrate"/></td>
			<td class="maintext">&nbsp;</td>
		</tr>--%>
        </table></td>
		<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr>
 <%} else
   {%>
	   <html:hidden property="advCPM"/>
	   <html:hidden property="pubCPM"/>
	   <html:hidden property="advCPC"/>
	   <html:hidden property="pubCPC"/>
	   <html:hidden property="advCPL"/>
	   <html:hidden property="pubCPL"/>
	   <html:hidden property="advCPS"/>
	   <html:hidden property="pubCPS"/>
	<%}%>
	<tr>
		<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
		<td  colspan="2"><table border="0" width="100%" cellspacing="5"><tr><td><input type="submit" value='<bean:message key="submit"/>'></td></tr></table></td>
		<td height="10" ></td>
		<td width="6" heigh="10" style="background-image: url(images/table5.gif);"></td>
	</tr>
    <tr>
        <td width="5" style="background-image: url(images/table4.gif);"></td>
        <td  colspan="2"><table border="0" width="100%" cellspacing="5"><tr><td><bean:message key="form.msg.mandatory"/></td></tr></table></td>
        <td height="10" ></td>
		<td width="6" style="background-image: url(images/table5.gif);"></td>
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
 </html:form>
