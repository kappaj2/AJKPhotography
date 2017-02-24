<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld"%>
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld"%>
<%@ taglib prefix="logic" uri="/WEB-INF/struts-logic.tld"%>

<table width="100%"  border="0" class="TableMaintenance">
	<tr>
		<td class="th3" align="center">
			<bean:message key="title.heading" />
		</td>
		<td class="homePage" rowspan="2" valign="top">
			<li><html:link action="/homePage.do?exec=homePage">Home Page</html:link></li>
		</td>
 <!--               
		<logic:notPresent role="administrator,teammember">
			<td class="homePage" rowspan="2" valign="top">
				<li><html:link action="/loginPage">Log In</html:link></li>
			</td>
		</logic:notPresent>
-->
	</tr>

</table>
