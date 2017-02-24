<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib prefix="html"  uri="/WEB-INF/struts-html.tld" %>


<html:html lang="true">
    <head>
        <title><bean:message key="logon.title" /></title>
        <link rel="stylesheet" type="text/css" href="<html:rewrite page='/css/ajk.css'/>">
    </head>
    
    <body>
        
        <jsp:include page="/WEB-INF/errorDisplay.jsp" flush="true" />
        
        <html:form action="/SubmitLogon?exec=validate" focus="username">
            <table border="0" width="100%">
                
                <tr>
                    <th align="right">
                        Username:
                    </th>
                    <td align="left">
                        <html:text property="username" size="16" maxlength="18"/>
                    </td>
                </tr>
                
                <tr>
                    <th align="right">
                        Password:
                    </th>
                    <td align="left">
                        <html:password property="password" size="16" maxlength="18"/>
                    </td>
                </tr>
                
                <tr>
                    <td align="right">
                        <html:submit property="Submit" value="Submit"/>
                    </td>
                    <td align="left">
                        <html:reset/>
                    </td>
                </tr>
                
            </table>
            
        </html:form>
    </body>
</html:html>


