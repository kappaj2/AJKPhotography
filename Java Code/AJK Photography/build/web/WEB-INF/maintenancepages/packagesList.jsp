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
            <td colspan="8" align="center"  class="th1">
                <bean:message key="title.page.packages.list" /> 
            </td>
        </tr>
        <tr>
            <th class="tr1" ><bean:message key="title.page.package.code" /></th>        
            <th class="tr1" ><bean:message key="title.page.package.name" /></th>              
            <th class="tr1" ><bean:message key="title.page.package.whatido" /></th>
            <th class="tr1" ><bean:message key="title.page.package.whatyoudo" /></th>
            <th class="tr1" ><bean:message key="title.page.package.whatyouget" /></th>            
            <th class="tr1" ><bean:message key="title.page.package.cost" /></th>            
            <th >&nbsp;</th>
            <th >&nbsp;</th>
        </tr>
        
        <logic:empty name="packageForm" property="packageList">
            <tr>
                <td colspan="8"><bean:message key="title.page.package.noentry" /></td>
            </tr>
        </logic:empty>
        <logic:notEmpty name="packageForm" property="packageList">
            <logic:iterate name="packageForm" property="packageList" id="packageDetail" indexId="index">
                
                <tr>
                    <td class='tr1'><c:out value="${packageDetail.packageId}"/></td>                    
                    <td class='tr1'><c:out value="${packageDetail.packageName}"/></td>                    
                    <td class='tr1'><c:out value="${packageDetail.whatIDo}"/></td>                    
                    <td class='tr1'><c:out value="${packageDetail.whatYouDo}"/></td>                    
                    <td class='tr1'><c:out value="${packageDetail.whatYouGet}"/></td>                    
                    <td class='tr1'><c:out value="${packageDetail.cost}"/></td>                    
                    
                    <td class='tr1'>
                        <a  
                            href='<c:url value="/admin/packageMaintenanceAction.do?exec=packageDelete">
                            <c:param name="packageId" value="${packageDetail.packageId}"/>
                            </c:url>'>
                            <bean:message key="title.page.package.delete" /> 
                        </a>
                    </td>
                    <td class='tr1'>
                        <a  
                            href='<c:url value="/admin/packageMaintenanceAction.do?exec=showPackageEdit" >
                            <c:param name="packageId" value="${packageDetail.packageId}"/>
                            </c:url>'>
                            <bean:message key="title.page.package.edit" /> 
                        </a>
                    </td>
                </tr>
            </logic:iterate>
        </logic:notEmpty>
        <tr>
            <td colspan="8">
                <html:link action="/admin/packageMaintenanceAction.do?exec=showAllPackageAdd">
                    <bean:message key="title.page.package.add" /> 
                </html:link>
            </td>
        </tr>
    </tbody>
</table>
