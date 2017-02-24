<%@ page language="java"%>
<%@ taglib prefix="html"  uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean"  uri="/WEB-INF/struts-bean.tld" %> 
<%@ taglib prefix="c"     uri="/WEB-INF/c.tld" %>
<%@ taglib prefix="logic" uri="/WEB-INF/struts-logic.tld" %>

<jsp:include page="/WEB-INF/errorDisplay.jsp" flush="true" />

<table  border="0" class="TableMaintenance">
    <tbody>
        <%-- set the header --%>
        <tr>
            <td colspan="4" align="center"  class="th1">
                <bean:message key="title.page.property.list" /> 
            </td>
        </tr>
        <tr>
            <th class="tr1" ><bean:message key="title.page.property.key" /></th>
            <th class="tr1" ><bean:message key="title.page.property.default" /></th>
            <th class="tr1" ><bean:message key="title.page.property.value" /></th>            
            <th >&nbsp;</th>
        </tr>
        <logic:empty name="configurationAndSetupForm" property="appPropList">
            <tr>
                <td colspan="4"><bean:message key="title.page.property.noentry" /></td>
            </tr>
        </logic:empty>
        <logic:notEmpty name="configurationAndSetupForm" property="appPropList">
            <logic:iterate name="configurationAndSetupForm" property="appPropList" id="appPropDetail" indexId="index">
                <tr>
                    <td class='tr1'><c:out value="${appPropDetail.propertyKey}"/></td>
                    <td class='tr1'><c:out value="${appPropDetail.propertyValueDefault}"/></td>
                    <td class='tr1'><c:out value="${appPropDetail.propertyValue}"/></td>
                    <td class='tr1'>
                        <a  
                            href='<c:url value="/admin/configurationAndSetupAction.do?exec=displayPropEdit">
                            <c:param name="propertyKey" value="${appPropDetail.propertyKey}"/>
                            </c:url>'>
                            <bean:message key="title.page.property.edit" /> 
                        </a>
                    </td>
                </tr>
            </logic:iterate>
        </logic:notEmpty>
    </tbody>
</table>
