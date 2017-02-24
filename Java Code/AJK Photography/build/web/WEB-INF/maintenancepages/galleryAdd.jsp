<%@ page language="java"%>
<%@ taglib prefix="html"  uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean"  uri="/WEB-INF/struts-bean.tld" %> 
<%@ taglib prefix="c"     uri="/WEB-INF/c.tld" %>
<%@ taglib prefix="logic" uri="/WEB-INF/struts-logic.tld" %>

<%-- create a html form --%>
<html:form action="/admin/galleryMaintenanceAction"  method="post" enctype="multipart/form-data">
    
    <html:hidden property="typeHeading" value="TypeHeading"/>
    
    <%-- print out the form data --%>
    <table border="0"  class="TableMaintenance">
        <tr>
            <th colspan="2" class="th1"><bean:message key="title.page.gallery.add" /></th>
        </tr>
        
        <tr>
            <th class="tr1" align="right">Category</th>
            <td class="tr1" align="left">
                <html:select property="categoryCode">	
                    <html:options collection="categoryMap" 
                                  property="key" 
                                  labelProperty="value.categoryDescription"/>;
                </html:select>
            </td>
        </tr>
        
        <tr>
            <th class="tr1" align="right">Gallery Description</th>
            <td class="tr1" align="left"><html:textarea cols="80" rows="10" property="galleryDescription" /></td>
        </tr>
        <tr>
            <th class="tr1" align="right"><bean:message key="title.page.image.height" /></th>
            <td class="tr1" align="left"><c:out value="${GalleriesHeight}"/></td>
        </tr>        
        <tr>
            <td class="tr1" align="right"><bean:message key="title.page.galleryimage.select"/></td>
            <td class="tr1" align="left"><html:file size="120" property="myFile" /></td>
        </tr>         
        <tr>
            <td colspan="2"><html:submit>Save</html:submit></td>
        </tr>
    </table>
    
    <%-- set the parameter for the dispatch action --%>
    <html:hidden property="exec" value="saveGallery" />
</html:form>
