<%@ page language="java"%>
<%@ taglib prefix="html"  uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean"  uri="/WEB-INF/struts-bean.tld" %> 
<%@ taglib prefix="c"     uri="/WEB-INF/c.tld" %>
<%@ taglib prefix="logic" uri="/WEB-INF/struts-logic.tld" %>

<jsp:include page="/WEB-INF/errorDisplay.jsp" flush="true" />

<%-- create a html form --%>
<html:form action="/admin/configurationAndSetupAction"  method="post" enctype="multipart/form-data">
    
    <html:hidden property="typeHeading" value="TypeHeading"/>
    
    <%-- print out the form data --%>
    <table border="0"  class="TableMaintenance">
        <tr>
            <th colspan="2" class="th1"><bean:message key="title.page.stylesheet.add" /></th>
        </tr>
        <tr>
            <td class="tr1" align="right"><bean:message key="title.page.stylesheet.select"/></td>
            <td class="tr1" align="left"><html:file size="120" property="myFile" /></td>
        </tr>         
        <tr>
            <td colspan="2"><html:submit>Save</html:submit></td>
        </tr>
    </table>
    
    <%-- set the parameter for the dispatch action --%>
    <html:hidden property="exec" value="saveStylesheet" />
</html:form>
