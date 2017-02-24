<%@ page language="java"%>
<%@ taglib prefix="html"  uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean"  uri="/WEB-INF/struts-bean.tld" %> 
<%@ taglib prefix="c"     uri="/WEB-INF/c.tld" %>
<%@ taglib prefix="logic" uri="/WEB-INF/struts-logic.tld" %>

<%-- create a html form --%>
<html:form action="/admin/imageMaintenanceAction"  method="post" enctype="multipart/form-data">
    
    <html:hidden property="typeHeading" value="TypeHeading"/>
    
    
    <%-- print out the form data --%>
    <table border="0" class="TableMaintenance">
        <tr>
            <th colspan="2" class="th1"><bean:message key="title.page.gallery.images.update" /></th>
        </tr>
        
        <tr>
            <td colspan="2" class="tderror">
                <html:errors property="filesize"/>
            </td>
        </tr>
        <tr>
            <td colspan="2" class="tderror">
                <html:errors property="filetype"/>
            </td>
        </tr>
        
        <tr>
            <th class="tr1" align="right">Category Code</th>
            <td class="tr1" align="left"><html:text readonly="true" property="categoryCode"/></td>
        </tr>
        <tr>
            <th class="tr1" align="right">Gallery Code</th>
            <td class="tr1" align="left"><html:text readonly="true"  property="galleryCode"/></td>
        </tr>   
        <tr>
            <th class="tr1" align="right">Image Code</th>
            <td class="tr1" align="left"><html:text readonly="true"  property="imageCode"/></td>
        </tr>   
        <tr>
            <th class="tr1" align="right">Image Description</th>
            <td class="tr1" align="left"><html:textarea cols="80" rows="10" property="imageDescription" /></td>
        </tr>
        <tr>
            <th class="tr1" align="right">Update image</th>
            <td class="tr1" align="left"><html:checkbox property="includeImage" /></td>
        </tr>  
        <tr>
            <th class="tr1" align="right"><bean:message key="title.page.image.height" /></th>
            <td class="tr1" align="left"><c:out value="${ImagesHeight}"/></td>
        </tr>         
        <tr>
            <td class="tr1" align="right"><bean:message key="title.page.galleryimage.select"/></td>
            <td class="tr1" align="left"><html:file size="120" property="myFile" /></td>
        </tr> 
        <tr>
            <td colspan="2"><html:submit>Update and save image</html:submit></td>
        </tr>
    </table>
    
    <%-- set the parameter for the dispatch action --%>
    <html:hidden property="exec" value="updateAndSaveImage" />
</html:form>
