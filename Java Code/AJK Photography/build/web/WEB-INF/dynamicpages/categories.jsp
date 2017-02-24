<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@ taglib prefix="html"  uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="logic" uri="/WEB-INF/struts-logic.tld" %>
<%@ taglib prefix="c"     uri="/WEB-INF/c.tld" %>
<%@ taglib prefix="bean"  uri="/WEB-INF/struts-bean.tld" %> 

<script language="JavaScript">
        function displayCategory(var1){
          
	  	document.imageForm.categoryCode.value = var1;
	  	document.imageForm.submit();          
        }
</script>

<html:form action="imageAction?exec=executeAction">
<html:hidden property="categoryCode" value="0"/>
<html:hidden property="actionName" value="galleries"/>    

<c:set var="colCount" scope="request" value="0"/>
<c:set var="imagesPerRow" scope="request" value="4"/>    

<table border="0" width="100%" class="TableCategories">
<tr>
    <td align="middle">
        
        <h1 class="th1"><bean:message key="title.page.category.heading" /></h1>
    </td>
</tr>
<tr>
    <td>
    <logic:empty name="imageForm" property="imageList"  >
        <table border="0" class="TableCategories">
            <tr>
                <td>
                    <bean:message key="title.page.category.noentry" />
                </td>
            </tr>
        </table>
    </logic:empty>
    
    
    <logic:notEmpty name="imageForm" property="imageList">
    <table border="0" width="100%" class="TableCategoriesDetail">
        
        <tr valign="top">
            <logic:iterate name="imageForm" property="imageList" id="imageDetail" indexId="index">
            
            <c:if test="${colCount == imagesPerRow}">  <!-- ColCount == 0 is the first image for the current row - create new row -->
        </tr>
        <tr>
            </c:if>
            
            <td>
                <table border="0" class="TableCategoriesDetail">
                    <tr>
                        <td width="250">
                            <a href="javascript:displayCategory('<c:out value="${imageDetail.categoryCode}"/>' )">
                               <img alt="" src="<html:rewrite page="/ImageServlet?requestType=category" paramId="categoryCode" paramName="imageDetail" paramProperty="categoryCode"/>" />
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td align="center" width="250" ><c:out value="${imageDetail.categoryDescription}" escapeXml="false"/></td>
                    </tr>
                </table>                                
            </td>
            
            <c:if test="${colCount == imagesPerRow-1}">  
        </tr>
        <br/>
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
</table>
</html:form>

