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
            <td colspan="7" align="center"  class="th1">
                <bean:message key="title.page.gallery.list" /> 
            </td>
        </tr>
        <tr>
   
            <th class="tr1" ><bean:message key="title.page.category.code" /></th>        
            <th class="tr1" ><bean:message key="title.page.category.description" /></th>              
            <th class="tr1" ><bean:message key="title.page.gallery.code" /></th>
            <th class="tr1" ><bean:message key="title.page.gallery.description" /></th>
            <th class="tr1" ><bean:message key="title.page.gallery.imageurl" /></th>            
            <th >&nbsp;</th>
            <th>&nbsp;</th>
        </tr>
        
        <logic:empty name="imageGalleryForm" property="galleryList">
            <tr>
                <td colspan="7"><bean:message key="title.page.gallery.noentry" /></td>
            </tr>
        </logic:empty>
        <logic:notEmpty name="imageGalleryForm" property="galleryList">
            <logic:iterate name="imageGalleryForm" property="galleryList" id="galleryDetail" indexId="index">
                
                <tr>
                    <td class='tr1'><c:out value="${galleryDetail.comp_id.categoryCode}"/></td>                    
                    <td class='tr1'><c:out value="${galleryDetail.imageCategory.categoryDescription}"/></td>                    
                    <td class='tr1'><c:out value="${galleryDetail.comp_id.galleryCode}"/></td>
                    <td class='tr1'><c:out value="${galleryDetail.galleryDescription}"/></td>
                    <td class='tr1'><c:out value="${galleryDetail.galleryImageUrl}"/></td>
                    
                    <td class='tr1'>
                        <a  
                            href='<c:url value="/admin/galleryMaintenanceAction.do?exec=deleteGallery" >
                            <c:param name="categoryCode" value="${galleryDetail.comp_id.categoryCode}"/>
                            <c:param name="galleryCode" value="${galleryDetail.comp_id.galleryCode}"/>
                            </c:url>'>
                            <bean:message key="title.page.gallery.delete" /> 
                        </a>
                    </td>
                    <td class='tr1'>
                        <a  
                            href='<c:url value="/admin/galleryMaintenanceAction.do?exec=editGallery" >
                            <c:param name="categoryCode" value="${galleryDetail.comp_id.categoryCode}"/>
                            <c:param name="galleryCode" value="${galleryDetail.comp_id.galleryCode}"/>
                            </c:url>'>
                            <bean:message key="title.page.gallery.edit" /> 
                        </a>
                    </td>
                </tr>
            </logic:iterate>
        </logic:notEmpty>
        <tr>
            <td colspan="7">
                <html:link action="/admin/galleryMaintenanceAction.do?exec=addGallery">
                    <bean:message key="title.page.gallery.add" /> 
                </html:link>
            </td>
        </tr>
    </tbody>
</table>
