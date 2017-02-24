<%@ page language="java"%>
<%@ taglib prefix="html"  uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean"  uri="/WEB-INF/struts-bean.tld" %> 
<%@ taglib prefix="c"     uri="/WEB-INF/c.tld" %>
<%@ taglib prefix="logic" uri="/WEB-INF/struts-logic.tld" %>


<%-- create a html form --%>
<html:form action="/admin/configurationAndSetupAction">
        
    <%-- print out the form data --%>
    <table border="0"  class="TableMaintenance">
        <tr>
            <th colspan="2" class="tr1"><bean:message key="title.page.property.edit"/></th>
        </tr>
        
        <tr>
            <th class="tr1" align="right"><bean:message key="title.page.property.key"/></th>
            <td class="tr1" align="left"><html:text readonly="true" property="propertyKey"/></td>
        </tr>
        <tr>
            <th class="tr1" align="right"><bean:message key="title.page.property.default"/></th>
            <td class="tr1" align="left"><html:text readonly="true" property="propertyValueDefault" /></td>
        </tr>
        <tr>
            <th class="tr1" align="right"><bean:message key="title.page.property.value"/></th>
            <td class="tr1"><html:text property="propertyValue" /></td>
        </tr>  
        <tr>
            <td colspan="2"><html:submit>Save</html:submit></td>
        </tr>
    </table>
    
    <%-- set the parameter for the dispatch action --%>
    <html:hidden property="exec" value="updateProperty" />
</html:form>
