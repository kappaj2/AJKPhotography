<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@ taglib prefix="html"  uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="logic" uri="/WEB-INF/struts-logic.tld" %>
<%@ taglib prefix="c"     uri="/WEB-INF/c.tld" %>
<%@ taglib prefix="bean"  uri="/WEB-INF/struts-bean.tld" %> 

<script language="JavaScript">
        function displayCategory(var1){

	  	document.imageForm.galleryCode.value = var1;
	  	document.imageForm.submit();          
        }
</script>

<html:form action="imageAction?exec=executeAction">
<html:hidden property="galleryCode" value="0"/>
<html:hidden property="actionName" value="images"/>   

<c:set var="colCount" scope="request" value="0"/>
<c:set var="imagesPerRow" scope="request" value="4"/>
<table border="0" width="100%" class="TableGalleries">
<tr>
    <td>
        <h1 class="th1"><bean:message key="title.page.gallery.heading" /></h1>
    </td>
</tr>

<tr>
    <td>
    <logic:empty name="imageForm" property="imageList"  >
        <table border="0" class="TableGalleries">
            <tr>
                <td>
                    <bean:message key="title.page.gallery.noentry" />
                </td>
            </tr>
        </table>
    </logic:empty>
    
    <logic:notEmpty name="imageForm" property="imageList">
    <table border="0" width="100%" class="TableGalleriesDetail">
        <tr  valign="top">
            <logic:iterate name="imageForm" property="imageList" id="imageDetail" indexId="index">
            
            <c:if test="${colCount == imagesPerRow}">  <!-- ColCount == 0 is the first image for the current row - create new row -->
        </tr>
        <tr>
            </c:if>
            
            <td>
                <table border="0" class="TableGalleriesDetail">
                    <tr>
                    <td  width="250" >
                        <a href="javascript:displayCategory('<c:out value="${imageDetail.comp_id.galleryCode}"/>' )">
                           <img  alt="" src="<html:rewrite page="/ImageServlet?requestType=gallery" paramId="galleryCode" paramName="imageDetail" paramProperty="comp_id.galleryCode"/>" />
                        </a>
                    </td>
                    <tr>
                        <td align="center"  width="250"><c:out value="${imageDetail.galleryDescription}" escapeXml="false"/></td>
                    </tr>
                </table>                                
            </td>
            
            <c:if test="${colCount == imagesPerRow-1}">  
        </tr>
        <tr>
            </c:if>
            
            <c:set var="colCount" value="${colCount +1}"/>      
            
            <c:if test="${colCount == imagesPerRow}">  
                <c:set var="colCount" value="${0}"/>
            </c:if>                
            
            </logic:iterate>
        </tr>
    </table>
    </logic:notEmpty>
    
    </td>
</tr>

</html:form>
