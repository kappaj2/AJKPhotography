<%@ page language="java"%>

<%@ taglib prefix="c"     uri="/WEB-INF/c.tld" %>

<%-- create a form --%>
<form>
    <%-- print out the form data --%>
    <table border="0" class="TableAbout">
        <tr>
            <th class="th2" align="center"><c:out value="${aboutForm.aboutHeading}" escapeXml="false"/></th>
        </tr>
        <tr>
            <td class="th2" align="left"><c:out value="${aboutForm.aboutDescription}" escapeXml="false"/></td>
        </tr>
    </table>
</form>
