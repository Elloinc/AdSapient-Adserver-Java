<%@ page import="com.adsapient.api_impl.statistic.common.StatisticEntity,
                              com.adsapient.util.I18nService ,
                              com.adsapient.util.security.*,
                              com.adsapient.shared.service.TimeService,
                              com.adsapient.api_impl.statistic.advertizer.AdditionalAdvertiserStatistic,
                              java.util.*" %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %> 
<%@ taglib uri='/WEB-INF/struts-bean.tld' prefix='bean' %>

<html:form action="generalReport">
	 	  <html:hidden property="type"/>
	
	<tr><td>
	<table width="100%"cellspacing="0" cellpadding="0"><tr>
	<td><img src="images/table1.gif"></td>
	<td width="100%" class="tableheader"><bean:message key="date.range.selector"/></td>
	<td><img src="images/table2.gif"></td>
	</tr></table>
</td></tr>
<tr> <td>
	<table width="100%" cellspacing="0" cellpadding="0">
	
	<tr>
	<td width="5" heigh="10" style="background-image: url(images/table4.gif);"></td>
	<td><table border="0" style="background-color:#ffffff;" cellspacing="1" width="100%"><tr>
		<td height="10"  width="40%"  class="tabletableheader">&nbsp;<bean:message key="startdate"/></td>
		<td height="10"  width="40%" class="tabletableheader">&nbsp;<bean:message key="enddate"/></td>
		<td height="10"  width="20%" class="tabletableheader">&nbsp;</td>
	</tr>
	
	<tr>  
	 
		<td class="tabledata-c"><html:text property="startDate"/>&nbsp;<script language="JavaScript" type="text/javascript">
    
    </script></td>
		<td class="tabledata-c"><html:text property="endDate"/>&nbsp;<script language="JavaScript" type="text/javascript">
    
    </script></td> 
    
		<td class="tabledata-c"><html:submit/></td> 	
   </tr>
	
	</html:form>
		</table></td>	
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
