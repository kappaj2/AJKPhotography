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
            <td colspan="5" align="center"  class="th1">
                <bean:message key="title.page.category.list" /> 
            </td>
        </tr>
        <tr>
            
            <th class="tr1" ><bean:message key="title.page.category.code" /></th>
            <th class="tr1" ><bean:message key="title.page.category.description" /></th>
            <th class="tr1" ><bean:message key="title.page.category.imageurl" /></th>            
            <th >&nbsp;</th>
            <th >&nbsp;</th>
        </tr>
        
        <logic:empty name="imageCategoryForm" property="categoryList">
            <tr>
                <td colspan="5"><bean:message key="title.page.category.noentry" /></td>
            </tr>
        </logic:empty>
        <logic:notEmpty name="imageCategoryForm" property="categoryList">
            <logic:iterate name="imageCategoryForm" property="categoryList" id="categoryDetail" indexId="index">
                
                <tr>
                    
                    <td class='tr1'><c:out value="${categoryDetail.categoryCode}"/></td>
                    <td class='tr1'><c:out value="${categoryDetail.categoryDescription}"/></td>
                    <td class='tr1'><c:out value="${categoryDetail.categoryImageUrl}"/></td>
                    <td class='tr1'>
                        <a  
                            href='<c:url value="/admin/categoryMaintenanceAction.do?exec=deleteCategory" >
                            
                            <c:param name="categoryCode" value="${categoryDetail.categoryCode}"/>
                            </c:url>'>
                            <bean:message key="title.page.category.delete" /> 
                        </a>

                    </td>
                    <td class='tr1'>
                        <a  
                            href='<c:url value="/admin/categoryMaintenanceAction.do?exec=showCategoryUpdate" context="/AJK_Photography">
                            
                            <c:param name="categoryCode" value="${categoryDetail.categoryCode}"/>
                            </c:url>'>
                            <bean:message key="title.page.category.edit" /> 
                        </a>
                    </td>
                </tr>
            </logic:iterate>
        </logic:notEmpty>
        <tr>
            <td colspan="5">
                <html:link action="/admin/categoryMaintenanceAction.do?exec=addCategory">
                    <bean:message key="title.page.category.add" /> 
                </html:link>
            </td>
        </tr>
    </tbody>
</table>
