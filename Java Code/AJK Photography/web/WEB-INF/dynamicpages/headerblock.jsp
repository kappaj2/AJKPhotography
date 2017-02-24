<%@ page language="java"%>
<%@ taglib prefix="html"  uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="layout" uri="/WEB-INF/struts-layout.tld" %>
<%@ taglib prefix="c"     uri="/WEB-INF/c.tld" %>

<table border="0" width="100%">
    <tr  >
        <td width="100%" class="tr1">
            <table width="100%" class="headertext">
                <tr>
                    <td  align="center"><!--       <c:out value="${appName}"/> -->
                    AJK Photography
                    </td>
                </tr>
                <tr>
                    <td align="left" valign="bottom">
                        <layout:crumbs  separator=" > " styleClass="breadcrumbs" crumbsName="crumbs"/>            
                    </td> 
                </tr>
            </table>
        </td>
        <td >
            <img class="applogo" height="200" class="applogo" alt="" src="<html:rewrite page="/ImageServlet?requestType=logo"/>" />
        </td>
    </tr>
</table>

