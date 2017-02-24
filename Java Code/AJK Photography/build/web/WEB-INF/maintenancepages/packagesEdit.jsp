<%@ page language="java"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="html"  uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean"  uri="/WEB-INF/struts-bean.tld" %> 

<%-- create a html form --%>
<html:form action="/admin/packageMaintenanceAction">
   
    <html:hidden property="packageId" />
    
    <%-- print out the form data --%>
    <table border="0"  class="TableMaintenance">
        <tr>
            <th colspan="2" class="th1"><bean:message key="title.page.package.edit" /></th>
        </tr>
        <tr>
            <th class="tr1" align="right"><bean:message key="title.page.package.name"/></th>
            <td class="tr1" align="left"><html:text property="packageName" /></td>
        </tr>
        <tr>
            <th class="tr1" align="right"><bean:message key="title.page.package.whatido"/></th>
            <td class="tr1" align="left"><html:textarea cols="80" rows="10" property="whatIDo" /></td>
        </tr>
        <tr>
            <th class="tr1" align="right"><bean:message key="title.page.package.whatyoudo"/></th>
            <td class="tr1" align="left"><html:textarea cols="80" rows="10" property="whatYouDo" /></td>
        </tr>
        <tr>
            <th class="tr1" align="right"><bean:message key="title.page.package.whatyouget"/></th>
            <td class="tr1" align="left"><html:textarea cols="80" rows="10" property="whatYouGet" /></td>
        </tr>
        <tr>
            <th class="tr1" align="right"><bean:message key="title.page.package.cost"/></th>
            <td class="tr1" align="left"><html:text property="cost" /></td>
        </tr>
       
        <tr>
            <td colspan="2"><html:submit>Save</html:submit></td>
        </tr>
    </table>
    
    <%-- set the parameter for the dispatch action --%>
    <html:hidden property="exec" value="packageUpdate" />
</html:form>
