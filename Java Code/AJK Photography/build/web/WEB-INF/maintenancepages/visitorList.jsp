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
                <td colspan="4" align="center"  class="th1">
                    <bean:message key="title.page.feedback.visitor.list" /> 
                </td>
            </tr>
            <tr>
                <th class="tr1" ><bean:message key="title.page.feedback.visitor.name" /></th>                
                <th class="tr1" ><bean:message key="title.page.feedback.visitor.surname" /></th>                
                <th class="tr1" ><bean:message key="title.page.feedback.visitor.email" /></th>   
                <th class="tr1" ><bean:message key="title.page.feedback.visitor.comments"/></th>
            </tr>
            
            <logic:empty name="feedbackMaintenanceForm" property="visitorList">
                <tr>
                    <td colspan="4"><bean:message key="title.page.visitor.noentry" /></td>
                </tr>
            </logic:empty>
            <logic:notEmpty name="feedbackMaintenanceForm" property="visitorList">
                <logic:iterate name="feedbackMaintenanceForm" property="visitorList" id="visitorDetail" indexId="index"  >
                    
                    <tr>
                        <td class='tr1' valign="top" align="left"><c:out value="${visitorDetail.visitorName}"/></td>  
                        <td class='tr1' valign="top" align="left"><c:out value="${visitorDetail.visitorSurname}"/></td>  
                        <td class='tr1' valign="top" align="left"><c:out value="${visitorDetail.visitorEmail}"/></td>  
                        
                        <logic:notEmpty name="visitorDetail" property="visitorComments">
                            <td colspan="3" width="100%" class="tr1">
                                <table class="TableMaintenanceDetail" border="0" >
                                    <logic:iterate name="visitorDetail" property="visitorComments" id="visitorComment" indexId="index"  >
                                        <tr>
                                            <td class="tr1" align="left">
                                                <c:out value="${visitorComment.commentId}"/>
                                            </td>
                                       
                                            <td class="tr1" align="left" width="100%">
                                                <c:out value="${visitorComment.commentText}"/>
                                            </td>
                                        </tr>
                                    </logic:iterate>
                                </table>
                            </td>
                        </logic:notEmpty>

                    </tr>
                </logic:iterate>
            </logic:notEmpty>
        </tbody>
    </table>
</html:form>
