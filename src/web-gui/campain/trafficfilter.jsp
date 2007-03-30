<html:form action="filter">
<html:hidden property="filterType"/>
<html:hidden property="filterAction"/>
<html:hidden property="campainId"/>
<html:hidden property="bannerId"/>
<tr>
<td>
<table width="100%" cellspacing="0" cellpadding="0">



<tr>
    <td width="5" heigh="10"
        style="background-image: url(images/table4.gif);"></td>
    <td class="tabledata">
        <table border="0" width="100%" cellspacing="5">
            <tr>
                <td>
                    <hr>
                </td>
            </tr>
        </table>
    </td>
    <td width="6" heigh="10"
        style="background-image: url(images/table5.gif);"></td>
</tr>
<tr>
    <td width="5" heigh="10"
        style="background-image: url(images/table4.gif);"></td>
    <td class="tabledata">
        <table border="0" width="100%" cellspacing="5">
            <tr>
                <td><html:text property="maxImpressionsInCampain"/> <bean:message
                        key="maxImpressionsInCampain"/></td>
            </tr>
        </table>
    </td>
    <td width="6" heigh="10"
        style="background-image: url(images/table5.gif);"></td>
</tr>
<tr>
    <td width="5" heigh="10"
        style="background-image: url(images/table4.gif);"></td>
    <td class="tabledata">
        <table border="0" width="100%" cellspacing="5">
            <tr>
                <td><html:text property="maxClicksInCampain"/> <bean:message
                        key="maxClicksInCampain"/></td>
            </tr>
        </table>
    </td>
    <td width="6" heigh="10"
        style="background-image: url(images/table5.gif);"></td>
</tr>
<tr>
    <td width="5" heigh="10"
        style="background-image: url(images/table4.gif);"></td>
    <td class="tabledata">
        <table border="0" width="100%" cellspacing="5">
            <tr>
                <td>
                    <hr>
                </td>
            </tr>
        </table>
    </td>
    <td width="6" heigh="10"
        style="background-image: url(images/table5.gif);"></td>
</tr>

<tr>
    <td width="5" heigh="10"
        style="background-image: url(images/table4.gif);"></td>
    <td class="tabledata">
        <table border="0" width="100%" cellspacing="5">
            <tr>
                <td><html:text property="maxImpressionsInDay"/> <bean:message
                        key="maxImpressionsInDay"/></td>
            </tr>
        </table>
    </td>
    <td width="6" heigh="10"
        style="background-image: url(images/table5.gif);"></td>
</tr>
<tr>
    <td width="5" heigh="10"
        style="background-image: url(images/table4.gif);"></td>
    <td class="tabledata">
        <table border="0" width="100%" cellspacing="5">
            <tr>
                <td><html:text property="maxClicksInDay"/> <bean:message
                        key="maxClicksInDay"/></td>
            </tr>
        </table>
    </td>
    <td width="6" heigh="10"
        style="background-image: url(images/table5.gif);"></td>
</tr>
<tr>
    <td width="5" heigh="10"
        style="background-image: url(images/table4.gif);"></td>
    <td class="tabledata">
        <table border="0" width="100%" cellspacing="5">
            <tr>
                <td>
                    <hr>
                </td>
            </tr>
        </table>
    </td>
    <td width="6" heigh="10"
        style="background-image: url(images/table5.gif);"></td>
</tr>


<tr>
    <td width="5" heigh="10"
        style="background-image: url(images/table4.gif);"></td>
    <td class="tabledata">
        <table border="0" cellspacing="5">
            <tr>
                <td><html:text property="customPeriodValue"/></td>
                <td><bean:message key="maxImpressionsInCustomPeriod"/></td>
                <td><html:select property="customPeriodDayValue">
                    <html:optionsCollection property="customPeriodDay"/>
                </html:select></td>
                <td><bean:message key="days.small"/></td>
                <td><html:select property="customPeriodHourValue">
                    <html:optionsCollection property="customPeriodHour"/>
                </html:select></td>
                <td><bean:message key="hours"/></td>
            </tr>
            <tr>
                <td><html:text property="customPeriodInClickValue"/></td>
                <td><bean:message key="maxClicksInCustomPeriod"/></td>
                <td><html:select property="customPeriodClickDayValue">
                    <html:optionsCollection property="customPeriodDay"/>
                </html:select></td>
                <td><bean:message key="days.small"/></td>
                <td><html:select property="customPeriodClickHourValue">
                    <html:optionsCollection property="customPeriodHour"/>
                </html:select></td>
                <td><bean:message key="hours"/></td>
            </tr>
        </table>

      </td>
    <td width="6" heigh="10"
        style="background-image: url(images/table5.gif);"></td>
</tr>
<tr>
    <td width="5" heigh="10"
        style="background-image: url(images/table4.gif);"></td>
    <td class="tabledata">
        <table border="0" width="100%" cellspacing="5">
            <tr>
                <td>
                    <hr>
                </td>
            </tr>
        </table>
    </td>
    <td width="6" heigh="10"
        style="background-image: url(images/table5.gif);"></td>
</tr>

<tr>
    <td width="5" heigh="10"
        style="background-image: url(images/table4.gif);"></td>
    <td class="tabledata">
        <table border="0" width="100%" cellspacing="5">
            <tr>
                <td><html:text property="maxImpresionsInDayForUniqueUser"/> <bean:message
                        key="maxImpresionsInDayForUniqueUser"/></td>
            </tr>
        </table>
    </td>
    <td width="6" heigh="10"
        style="background-image: url(images/table5.gif);"></td>
</tr>
<tr>
    <td width="5" heigh="10"
        style="background-image: url(images/table4.gif);"></td>
    <td class="tabledata">
        <table border="0" width="100%" cellspacing="5">
            <tr>
                <td><html:text property="maxClicksInDayForUniqueUser"/> <bean:message
                        key="maxClicksInDayForUniqueUser"/></td>
            </tr>
        </table>
    </td>
    <td width="6" heigh="10"
        style="background-image: url(images/table5.gif);"></td>
</tr>
<tr>
    <td width="5" heigh="10"
        style="background-image: url(images/table4.gif);"></td>
    <td class="tabledata">
        <table border="0" width="100%" cellspacing="5">
            <tr>
                <td>
                    <hr>
                </td>
            </tr>
        </table>
    </td>
    <td width="6" heigh="10"
        style="background-image: url(images/table5.gif);"></td>
</tr>

<tr>
    <td width="5" heigh="10"
        style="background-image: url(images/table4.gif);"></td>
    <td class="tabledata">
        <table border="0" width="100%" cellspacing="5">
            <tr>
                <td><html:text property="maxImpressionsInCampainForUniqueUser"/>
                    <bean:message key="maxImpressionsInCampainForUniqueUser"/></td>
            </tr>
        </table>
    </td>
    <td width="6" heigh="10"
        style="background-image: url(images/table5.gif);"></td>
</tr>
<tr>
    <td width="5" heigh="10"
        style="background-image: url(images/table4.gif);"></td>
    <td class="tabledata">
        <table border="0" width="100%" cellspacing="5">
            <tr>
                <td><html:text property="maxClicksInCampainForUniqueUser"/> <bean:message
                        key="maxClicksInCampainForUniqueUser"/></td>
            </tr>
        </table>
    </td>
    <td width="6" heigh="10"
        style="background-image: url(images/table5.gif);"></td>
</tr>
<tr>
    <td width="5" heigh="10"
        style="background-image: url(images/table4.gif);"></td>
    <td class="tabledata">
        <table border="0" width="100%" cellspacing="5">
            <tr>
                <td>
                    <hr>
                </td>
            </tr>
        </table>
    </td>
    <td width="6" heigh="10"
        style="background-image: url(images/table5.gif);"></td>
</tr>


<tr>
    <td width="5" heigh="10"
        style="background-image: url(images/table4.gif);"></td>
    <td class="tabledata">
        <table border="0" width="100%" cellspacing="5">
            <tr>
                <td><html:text property="maxImpressionsInMonthForUniqueUser"/> <bean:message
                        key="maxImpressionsInMonthForUniqueUser"/></td>
            </tr>
        </table>
    </td>
    <td width="6" heigh="10"
        style="background-image: url(images/table5.gif);"></td>
</tr>
<tr>
    <td width="5" heigh="10"
        style="background-image: url(images/table4.gif);"></td>
    <td class="tabledata">
        <table border="0" width="100%" cellspacing="5">
            <tr>
                <td><html:text property="maxClicksInMonthForUniqueUser"/> <bean:message
                        key="maxClicksInMonthForUniqueUser"/></td>
            </tr>
        </table>
    </td>
    <td width="6" heigh="10"
        style="background-image: url(images/table5.gif);"></td>
</tr>
<tr>
    <td width="5" heigh="10"
        style="background-image: url(images/table4.gif);"></td>
    <td class="tabledata">
        <table border="0" width="100%" cellspacing="5">
            <tr>
                <td>
                    <hr>
                </td>
            </tr>
        </table>
    </td>
    <td width="6" heigh="10"
        style="background-image: url(images/table5.gif);"></td>
</tr>


<tr>
    <td width="5" heigh="10"
        style="background-image: url(images/table4.gif);"></td>
    <td class="tabledata">
        <table border="0" cellspacing="5">
            <tr>
                <td><html:text property="customPeriodValueUnique"/></td>
                <td><bean:message key="maxImpressionsInCustomPeriodUnique"/></td>
                <td><html:select property="customPeriodHourValueUnique">
                    <html:optionsCollection property="customPeriodHour"/>
                </html:select></td>
                <td><bean:message key="hours"/></td>
                <td><html:select property="customPeriodDayValueUnique">
                    <html:optionsCollection property="customPeriodDay"/>
                </html:select></td>
                <td><bean:message key="days.small"/></td>
            </tr>
            <tr>
                <td><html:text property="customPeriodInClickValueUnique"/></td>
                <td><bean:message key="maxClicksInCustomPeriodUnique"/></td>
                <td><html:select property="customPeriodClickHourValueUnique">
                    <html:optionsCollection property="customPeriodHour"/>
                </html:select></td>
                <td><bean:message key="hours"/></td>
                <td><html:select property="customPeriodClickDayValueUnique">
                    <html:optionsCollection property="customPeriodDay"/>
                </html:select></td>
                <td><bean:message key="days.small"/></td>
            </tr>
        </table>
    </td>
    <td width="6" heigh="10"
        style="background-image: url(images/table5.gif);"></td>
</tr>


<tr>
    <td width="5" heigh="10"
        style="background-image: url(images/table4.gif);"></td>
    <td class="tabledata">
        <table border="0" width="100%" cellspacing="5">
            <tr>
                <td>
                    <hr>
                </td>
            </tr>
        </table>
    </td>
    <td width="6" heigh="10"
        style="background-image: url(images/table5.gif);"></td>
</tr>


<tr>
    <td width="5" heigh="10"
        style="background-image: url(images/table4.gif);"></td>
    <td class="tabledata" cellspacing="5">
        <table border="0" cellspacing="5" cellpadding="5">
            <tr>
                <td><img src="images/icons/update.png" style="cursor: pointer;"
                         onclick="filterActionForm.submit();"
                         title="<%=i18nService.fetch("update",request)%>"
                         alt="<%=i18nService.fetch("update",request)%>"/></td>
                
            </tr>
        </table>
    </td>
    <td width="6" heigh="10"
        style="background-image: url(images/table5.gif);"></td>
</tr>
</table>
</td>
</tr>
<tr>
    <td>
        <table width="100%" cellspacing="0" cellpadding="0">
            <tr>
                <td><img src="images/table6.gif"></td>
                <td width="100%" heigh="10"
                    style="background-image: url(images/table8.gif);"></td>
                <td><img src="images/table7.gif"></td>
            </tr>
        </table>
    </td>
</tr>
</html:form>
