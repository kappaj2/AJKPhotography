<%@ page language="java"%>
<%@ taglib prefix="html"  uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean"  uri="/WEB-INF/struts-bean.tld" %> 

<jsp:include page="/WEB-INF/errorDisplay.jsp" flush="true" />

<html:form action="feedbackAction">
    
    <html:hidden property="exec" value="saveUserInput"/>
    
    <table cellspacing="5"
           cellpadding="0"
           border="0"
           class="TableCaptcha">
        
        <tr>
            <td colspan="2" class="tderror">
                <html:errors property="invalidcaptcha"/>
            </td>
        </tr>        
        <tr>
            <td colspan="2" class="tderror">
                <html:errors property="invalidcomments"/>
            </td>
        </tr>
        
        
        <tr>
            <td class="th1" align="right"><bean:message key="title.page.feedback.name"/></td>
            <td class="th2" align="left"><html:text property="name"/></td>
        </tr>
        <tr>
            <td class="th1" align="right"><bean:message key="title.page.feedback.surname"/></td>
            <td class="th2" align="left"><html:text property="surname"/></td>
        </tr>        
        <tr>
            <td class="th1" align="right"><bean:message key="title.page.feedback.emailaddress"/></td>
            <td class="th2" align="left"><html:text property="email"/></td>
        </tr>    
        <tr>
            <td class="th1" align="right"><bean:message key="title.page.feedback.comments"/></td>
            <td class="th2" align="left"><html:textarea cols="80" rows="10" property="comments" /></td>
        </tr>    
        <tr>
            <td colspan="2">
                <hr width="80%"/>
            </td>
        </tr>   
        <tr>
            <td colspan="2" class="th2"><bean:message key="title.page.feedback.captchaheading"/></td>
        </tr>
        <tr>
        <td><img src="<html:rewrite page="/jcaptcha"/>"/></td>
        <td><html:text property="jcaptchaResponse"/>
        <tr>
            <td colspan="2" class="th2"><bean:message key="title.page.feedback.captchaexplain"/></td>
        </tr>
        <tr>
            <td colspan="2">
                <hr width="80%"/>
            </td>
        </tr>   
        <tr>
            <td colspan="2"><html:submit>Submit feedback</html:submit></td>
        </tr>    
    </table>
    
</html:form>
