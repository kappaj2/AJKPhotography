<%@ taglib prefix="tiles" uri="/WEB-INF/struts-tiles.tld"%>
<%@ taglib prefix="html"  uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean"  uri="/WEB-INF/struts-bean.tld" %> 
<%@ taglib prefix="c"     uri="/WEB-INF/c.tld" %>
<%@ taglib prefix="logic" uri="/WEB-INF/struts-logic.tld" %>

<html:html lang="true">
    
    <link rel="stylesheet" type="text/css" href="<html:rewrite page='/css/ajk.css'/>">
    
    <head>
        <title><c:out value="${appName}"/></title>
        <html:base/>
    </head>
    <body>
        <c:set var="contextPath" scope="request">${pageContext.request.contextPath}</c:set>
        <table border="0" width="100%" hight="100%">
            <tr>
                <td align="center" colspan="2" height="10%">
                    <tiles:insert attribute="headerTile"/>
                </td>
            </tr>
            <tr >
                <td width="20%" height="25%" valign="top">
                    <tiles:insert attribute="maintMenuTile"/>
                </td>
                <td width="80%" height="55%" valign="top">
                    <tiles:insert attribute="maintBodyTile"/>
                </td>
            </tr>
        </table>
    </body>
</html:html>
