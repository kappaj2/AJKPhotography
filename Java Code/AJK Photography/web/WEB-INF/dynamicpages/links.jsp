<%@ taglib prefix="tiles" uri="/WEB-INF/struts-tiles.tld"%>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld"%>
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld"%>
<%@ taglib prefix="logic" uri="/WEB-INF/struts-logic.tld"%>


<table border="0" width="100%" class="TableLinks">
    <tr>
        <th class="th1"><bean:message key="title.page.links.heading"/></th>
    </tr>
    
    <tr>
        <td class="th2" align="left">
            <bean:message key="title.page.links.feedback"/>
        </td>
    </tr>
    <tr>
        <td align="left">
            <li><html:link action="/feedbackAction?exec=showFeedback"><bean:message key="title.page.links.feedback.enter"/></html:link></li>
        </td>
    </tr>   
    <tr>
        <td align="left">
            <li><html:link action="/feedbackAction?exec=showApprovedFeedback"><bean:message key="title.page.links.feedback.browse"/></html:link></li>
        </td>
    </tr>        
</table>

