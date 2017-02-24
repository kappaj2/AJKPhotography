<%@ page language="java"%>
<%@ taglib prefix="html"  uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean"  uri="/WEB-INF/struts-bean.tld" %> 
<%@ taglib prefix="logic" uri="/WEB-INF/struts-logic.tld" %>
<%@ taglib prefix="c"     uri="/WEB-INF/c.tld" %>


<jsp:include page="/WEB-INF/errorDisplay.jsp" flush="true" />

<html:form action="/admin/feedbackMaintenanceAction" method="post">
    <table border="0" class="TableMaintenance">
        <tbody>
            <%-- set the header --%>
            <tr>
                <td colspan="8" align="center"  class="th1">
                    <bean:message key="title.page.feedback.approval.list" /> 
                </td>
            </tr>
            <tr>
                <th class="tr1" ><bean:message key="title.page.feedback.visitor.name" /></th>                
                <th class="tr1" ><bean:message key="title.page.feedback.visitor.surname" /></th>                
                <th class="tr1" ><bean:message key="title.page.feedback.visitor.email" /></th>                
                <th class="tr1" ><bean:message key="title.page.feedback.commentId" /></th>
                <th class="tr1" ><bean:message key="title.page.feedback.text" /></th>
                <th class="tr1" ><bean:message key="title.page.feedback.date.submitted" /></th>            
                <th class="tr1" ><bean:message key="title.page.feedback.approved" /></th>            
                <th >&nbsp;</th>
            </tr>
            
            <logic:empty name="feedbackMaintenanceForm" property="feedbackList">
                <tr>
                    <td colspan="8"><bean:message key="title.page.feedback.pending.noentry" /></td>
                </tr>
            </logic:empty>
            <logic:notEmpty name="feedbackMaintenanceForm" property="feedbackList">
                <logic:iterate name="feedbackMaintenanceForm" property="feedbackList" id="feedbackDetail" indexId="index"  >
                    
                    <tr>
                    	<td class='tr1'><c:out value="${feedbackDetail.visitor.visitorName}"/></td>  
                    	<td class='tr1'><c:out value="${feedbackDetail.visitor.visitorSurname}"/></td>  
                    	<td class='tr1'><c:out value="${feedbackDetail.visitor.visitorEmail}"/></td>  
                        <td class='tr1'><c:out value="${feedbackDetail.commentId}"/></td>    
                        <td class='tr1'><html:textarea cols="30" rows="3" name="feedbackDetail" property="commentText" /></td>  
                        
                        <html:hidden name="feedbackDetail" property="commentId" />
                        <td class='tr1'><c:out value="${feedbackDetail.dateSubmitted}"/></td>
                        <td class='tr1'><c:out value="${feedbackDetail.published}"/></td>
                        <td class='tr1'>
                            <a  
                                href='<c:url value="/admin/feedbackMaintenanceAction.do?exec=deleteFeedback" >
                                <c:param name="commentId" value="${feedbackDetail.commentId}"/>
                            </c:url>'>
                                <bean:message key="title.page.feedback.delete" /> 
                            </a>
                        </td>
                    </tr>
                </logic:iterate>
                <tr>
                    <td colspan="8"><html:submit><bean:message key="title.page.feedback.publish.all"/></html:submit></td>
                </tr>                
            </logic:notEmpty>
            
        </tbody>
    </table>
    <html:hidden property="exec" value="publishFeedbackList" />
</html:form>
