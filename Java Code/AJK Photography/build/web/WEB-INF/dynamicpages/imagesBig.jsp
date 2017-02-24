<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@ taglib prefix="html"  uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="logic" uri="/WEB-INF/struts-logic.tld" %>
<%@ taglib prefix="c"     uri="/WEB-INF/c.tld" %>
<%@ taglib prefix="bean"  uri="/WEB-INF/struts-bean.tld" %> 

<html:form action="imageAction?exec=executeAction">
    <table border="0" class="TableImagesBig">
        <tr>
            <td>
                <h1 class="th1"><bean:message key="title.page.image.big.heading" /></h1>
            </td>
        </tr>
        <tr>
            <td>
                <img height="420" alt="" src="<html:rewrite page="/ImageServlet?requestType=imageBig" paramId="imageCode" paramName="imageForm" paramProperty="imageCode"/>" />                    
            </td>
        </tr>
    </table>                                      
    
</html:form>
