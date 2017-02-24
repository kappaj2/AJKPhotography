<%@ page language="java"%>
<%@ taglib prefix="html"  uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean"  uri="/WEB-INF/struts-bean.tld" %> 

<jsp:include page="/WEB-INF/errorDisplay.jsp" flush="true" />

<%-- create a html form --%>
<html:form action="/admin/configurationAndSetupAction" method="post">
    
    <%-- print out the form data --%>
    <table border="0"  class="TableMaintenance">
        
    </table>
    
</html:form>
