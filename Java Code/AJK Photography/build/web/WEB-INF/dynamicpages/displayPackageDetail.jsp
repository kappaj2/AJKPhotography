<%@ page language="java"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="html"  uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean"  uri="/WEB-INF/struts-bean.tld" %> 
<%@ taglib prefix="c"     uri="/WEB-INF/c.tld" %>

<%-- create a html form --%>
<html:form action="packageAction">

    
    <%-- print out the form data --%>
    <table border="0"  class="TablePackages">
        <tr>
            <th colspan="2" class="th1"><bean:message key="title.page.package.detail" /></th>
        </tr>
        <tr>
            <th class="tr1" align="right"><bean:message key="title.page.package.name"/></th>
            <td class="tr1" align="left"><c:out value="${packageForm.packageName}" escapeXml="false"/></td>
        </tr>
        <tr>
            <th class="tr1" align="right"><bean:message key="title.page.package.whatido"/></th>
            <td class="tr1" align="left"><c:out value="${packageForm.whatIDo}" escapeXml="false"/></td>
        </tr>
        <tr>
            <th class="tr1" align="right"><bean:message key="title.page.package.whatyoudo"/></th>
            <td class="tr1" align="left"><c:out value="${packageForm.whatYouDo}" escapeXml="false"/></td>
        </tr>
        <tr>
            <th class="tr1" align="right"><bean:message key="title.page.package.whatyouget"/></th>
            <td class="tr1" align="left"><c:out value="${packageForm.whatYouGet}" escapeXml="false"/></td>
        </tr>
        <tr>
            <th class="tr1" align="right"><bean:message key="title.page.package.cost"/></th>
            <td class="tr1" align="left"><c:out value="${packageForm.cost}" escapeXml="false"/></td>
        </tr>
       
    </table>
    

</html:form>
