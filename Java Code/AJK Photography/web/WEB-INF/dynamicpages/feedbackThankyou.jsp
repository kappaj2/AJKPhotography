<%@ page language="java"%>
<%@ taglib prefix="html"  uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="c"     uri="/WEB-INF/c.tld" %>

<jsp:include page="/WEB-INF/errorDisplay.jsp" flush="true" />

<html:form action="feedbackAction">
    
    <table cellspacing="5"
           cellpadding="0"
           border="0"
           class="TableCaptcha">
        
        <tr>            
            <td class='th2'><c:out value="${feedbackForm.thankYou}" escapeXml="false"/></td>   
        </tr> 
    </table>
    
</html:form>
