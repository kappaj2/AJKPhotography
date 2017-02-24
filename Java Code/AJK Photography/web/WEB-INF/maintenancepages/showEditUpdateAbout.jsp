<%@ page language="java"%>
<%@ taglib prefix="html"  uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean"  uri="/WEB-INF/struts-bean.tld" %> 

<jsp:include page="/WEB-INF/errorDisplay.jsp" flush="true" />

<%-- create a html form --%>
<html:form action="/admin/aboutMaintenanceAction" method="post">
    
    <%-- print out the form data --%>
    <table border="0"  class="TableMaintenance">
        <tr>
            <th colspan="2" class="th1"><bean:message key="title.page.about.edit" /></th>
        </tr>
        
        <tr>
            <td colspan="2" class="tderror">
                <html:errors property="invalidmaxlength"/>
            </td>
        </tr>
        
        <tr>
            <th class="tr1" align="right"><bean:message key="title.page.about.heading"/></th>
            <td class="tr1" align="left"><html:text  property="aboutHeading"/></td>
        </tr>
        <tr>
            <th class="tr1" align="right"><bean:message key="title.page.about.description"/></th>
            <td class="tr1" align="left"><html:textarea cols="60" rows="15"  property="aboutDescription"/></td>
        </tr>
        <tr>
            <td colspan="2"><html:submit><bean:message key="save"/></html:submit></td>
        </tr>
    </table>
    
    <%-- set the parameter for the dispatch action --%>
    <html:hidden property="exec" value="saveUpdateAbout" />
</html:form>
