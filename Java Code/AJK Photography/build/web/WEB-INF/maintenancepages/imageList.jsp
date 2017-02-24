<%@ page language="java"%>
<%@ taglib prefix="html"  uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean"  uri="/WEB-INF/struts-bean.tld" %> 
<%@ taglib prefix="c"     uri="/WEB-INF/c.tld" %>
<%@ taglib prefix="logic" uri="/WEB-INF/struts-logic.tld" %>

<jsp:include page="/WEB-INF/errorDisplay.jsp" flush="true" />

<table border="0" class="TableMaintenance">
    <tbody>
        <%-- set the header --%>
        <tr>
            <td colspan="7" align="center"  class="th1">
                <bean:message key="title.page.gallery.image.list" /> 
            </td>
        </tr>
        <tr>
            <th class="tr1" ><bean:message key="title.page.category.description" /></th>              
            <th class="tr1" ><bean:message key="title.page.gallery.description" /></th>
            <th class="tr1" ><bean:message key="title.page.gallery.imageCode" /></th>
            <th class="tr1" ><bean:message key="title.page.image.imageUrl" /></th>
            <th class="tr1" ><bean:message key="title.page.image.imageDescription" /></th>
            <th >&nbsp;</th>
            <th>&nbsp;</th>
        </tr>
        
        <logic:empty name="imageForm" property="imageList">
            <tr>
                <td colspan="7"><bean:message key="title.page.gallery.noentry" /></td>
            </tr>
        </logic:empty>
        <logic:notEmpty name="imageForm" property="imageList">
            <logic:iterate name="imageForm" property="imageList" id="imageDetail" indexId="index">
                
                <tr>
                    <td class='tr1'><c:out value="${imageDetail.imageGallery.imageCategory.categoryDescription}"/></td>                    
                    <td class='tr1'><c:out value="${imageDetail.imageGallery.galleryDescription}"/></td>                    
                    <td class='tr1'><c:out value="${imageDetail.comp_id.imageCode}"/></td>                    
                    <td class='tr1'><c:out value="${imageDetail.imageUrl}"/></td>                    
                    <td class='tr1'><c:out value="${imageDetail.imageDescription}"/></td>  
                    
                    <td class='tr1'>
                        <a  
                            href='<c:url value="/admin/imageMaintenanceAction.do?exec=deleteImage" >
                            <c:param name="categoryCode" value="${imageDetail.comp_id.categoryCode}"/>
                            <c:param name="galleryCode" value="${imageDetail.comp_id.galleryCode}"/>
                            <c:param name="imageCode" value="${imageDetail.comp_id.imageCode}"/>
                            </c:url>'>
                            <bean:message key="title.page.gallery.delete" /> 
                        </a>
                    </td>
                    <td class='tr1'>
                        <a  
                            href='<c:url value="/admin/imageMaintenanceAction.do?exec=showImageUpdate" >
                            <c:param name="categoryCode" value="${imageDetail.comp_id.categoryCode}"/>
                            <c:param name="galleryCode" value="${imageDetail.comp_id.galleryCode}"/>
                            <c:param name="imageCode" value="${imageDetail.comp_id.imageCode}"/>
                            </c:url>'>
                            <bean:message key="title.page.gallery.edit" /> 
                        </a>
                    </td>                    
                </tr>
            </logic:iterate>
        </logic:notEmpty>
        <tr>
            <td colspan="7">
                <html:link action="/admin/imageMaintenanceAction.do?exec=addImage">
                    <bean:message key="title.page.image.add" /> 
                </html:link>
            </td>
        </tr>
    </tbody>
</table>
