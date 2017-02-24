<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@ taglib prefix="html"  uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="logic" uri="/WEB-INF/struts-logic.tld" %>
<%@ taglib prefix="c"     uri="/WEB-INF/c.tld" %>
<%@ taglib prefix="bean"  uri="/WEB-INF/struts-bean.tld" %> 

<html:form action="packageAction">
<table border="0" width="100%" class="TablePackages" >

<tr>
    <td>
        <h1 class="th1"><bean:message key="title.page.package.heading" /></h1>
    </td>
</tr>
<logic:empty name="packageForm" property="packageList"  >
    <table border="0" class="TablePackages">
        <tr>
            <td>
                <bean:message key="title.page.package.noentry" />
            </td>
        </tr>
    </table>
</logic:empty>

<logic:notEmpty name="packageForm" property="packageList">

<tr valign="top">        
    <td align="left" valign="top">
        <ul>
        <logic:iterate name="packageForm" property="packageList" id="packageDetail" indexId="index">
            <li>
                <html:link page="/packageAction.do?exec=showPackageDetail" 
                           paramId="packageId" paramName="packageDetail" paramProperty="packageId">
                    <c:out value="${packageDetail.packageName}"/>
                </html:link>
            </li>
        </logic:iterate>
    </td>
    </ul>
</tr>
</table>
</logic:notEmpty>
</html:form>