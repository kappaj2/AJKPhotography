<%@ page language="java"%>
<%@ taglib prefix="html"  uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean"  uri="/WEB-INF/struts-bean.tld" %> 
<%@ taglib prefix="logic" uri="/WEB-INF/struts-logic.tld" %>
<%@ taglib prefix="c"     uri="/WEB-INF/c.tld" %>


<jsp:include page="/WEB-INF/errorDisplay.jsp" flush="true" />

<html:form action="/feedbackAction">
    <table border="0" class="TableFeedback">
       
            <%-- set the header --%>
            <tr>
                <td colspan="4" align="center"  class="th1">
                    <bean:message key="title.page.feedback.approved.list" /> 
                </td>
            </tr>
            <tr>
                <th class="th2" ><bean:message key="title.page.feedback.visitor.name" /></th>                
                <th class="th2" ><bean:message key="title.page.feedback.visitor.surname" /></th>                
                <th class="th2" ><bean:message key="title.page.feedback.text" /></th>
                <th class="th2" ><bean:message key="title.page.feedback.date.submitted" /></th>            
            </tr>
            
            <logic:empty name="feedbackForm" property="feedbackList">
                <tr>
                    <td colspan="4"><bean:message key="title.page.feedback.pending.noentry" /></td>
                </tr>
            </logic:empty>
            <logic:notEmpty name="feedbackForm" property="feedbackList">
                <logic:iterate name="feedbackForm" property="feedbackList" id="feedbackDetail" indexId="index"  >
                    <tr>
                    	<td class='th2'><c:out value="${feedbackDetail.visitor.visitorName}"/></td>  
                    	<td class='th2'><c:out value="${feedbackDetail.visitor.visitorSurname}"/></td>  
                        <td class='th2' width="400"><c:out value="${feedbackDetail.commentText}"/></td>
                        <td class='th2'><c:out value="${feedbackDetail.dateSubmitted}"/></td>
                    </tr>
                </logic:iterate>
            </logic:notEmpty>
        
    </table>
</html:form>
