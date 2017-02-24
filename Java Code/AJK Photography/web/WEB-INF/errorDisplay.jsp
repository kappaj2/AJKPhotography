<%@ taglib prefix="logic" uri="/WEB-INF/struts-logic.tld" %>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld"%>
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld"%>

<logic:messagesPresent name="APP_INFO_KEY">
    <table border="1"  width="100%" align="center" class="TableInfo">
        <tr>
            <td align="left">
                <p>
                    <html:img border="0" page="/images/icon-success.gif" border="0" vspace="2" hspace="10" align="center"/>
                    <bean:message key="success.heading"/>
                </p>   
                <ul>                
                    <html:messages id="message" name="APP_INFO_KEY">
                        <li></li><bean:write name="message"/>
                    </html:messages>           
                </ul>
            </td>
        </tr>
    </table>
</logic:messagesPresent>

<logic:messagesPresent name="APP_WARNING_KEY">
    <table border="1"  width="100%" align="center" class="TableError">
        <tr>
            <td align="left">
                <p>
                    <html:img border="0" page="/images/icon-alert.gif" border="0" vspace="2" hspace="10" align="center"/>
                    <bean:message key="warnings.heading"/>
                </p>
                <ul>
                    <html:messages id="warning" name="APP_WARNING_KEY">
                        <li><bean:write name="warning"/></li>
                    </html:messages>           
                </ul>
            </td>
        </tr>
    </table>
</logic:messagesPresent>

<logic:messagesPresent name="APP_ERROR_KEY">
    <table border="1" width="100%" align="center" class="TableError">
        <tr>
            <td align="left">
                <p>
                    <html:img border="0" page="/images/icon-warning.gif" border="0" vspace="2" hspace="10" align="center"/>
                    <bean:message key="errors.heading"/>
                </p>
                <ul>               
                    <html:messages id="error" name="APP_ERROR_KEY">
                        <li><bean:write name="error"/></li>
                    </html:messages>                   
                </ul>
            </td>
        </tr>
    </table>
</logic:messagesPresent>