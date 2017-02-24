<%@ taglib prefix="tiles" uri="/WEB-INF/struts-tiles.tld"%>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld"%>
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld"%>
<%@ taglib prefix="logic" uri="/WEB-INF/struts-logic.tld"%>


<table border="0" class="TableMaintenance">
    
    <tr>
        <td>
            <table border="0" class="TableMaintenance">
                <tr>
                    <th class="th1">Available Maintenance Links</th>
                </tr>
                <tr>
                    <td class="th2" align="left">
                        <bean:message key="maintenance.page.categories"/>
                    </td>
                </tr>
                <tr>
                    <td align="left">
                        <li><html:link action="/admin/categoryMaintenanceAction?exec=showAllCategories"><bean:message key="maintenance.page.categories.show"/></html:link></li>
                    </td>
                </tr>
                <tr>
                    <td class="th2" align="left">
                        <bean:message key="maintenance.page.galleries"/>
                    </td>
                </tr>
                <tr>
                    <td align="left">
                        <li><html:link action="/admin/galleryMaintenanceAction?exec=showAllGalleries"><bean:message key="maintenance.page.galleries.show"/></html:link></li>
                    </td>
                </tr>       
                <tr>
                    <td align="left">
                        <li><html:link action="/admin/imageMaintenanceAction?exec=buildImagesLookup"><bean:message key="maintenance.page.galleries.update"/></html:link></li>
                    </td>
                </tr>                  
                
                <tr>
                    <td class="th2" align="left">
                        <bean:message key="maintenance.page.site"/>
                    </td>
                </tr>
                <tr>
                    <td align="left">
                        <li><html:link action="/admin/feedbackMaintenanceAction?exec=showPendingFeedback"><bean:message key="maintenance.page.visitors.show.pendingfeedback"/></html:link></li>
                    </td>
                </tr>   
                <tr>
                    <td align="left">
                        <li><html:link action="/admin/feedbackMaintenanceAction?exec=showVisitors"><bean:message key="maintenance.page.visitors.show.visitors"/></html:link></li>
                    </td>
                </tr>                  
                <tr>
                    <td class="th2" align="left">
                        <bean:message key="maintenance.page.packages"/>
                    </td>
                </tr>
                <tr>
                    <td align="left">
                        <li><html:link action="/admin/packageMaintenanceAction?exec=showAllPackageList"><bean:message key="maintenance.page.packages.show"/></html:link></li>
                    </td>
                </tr>       
                <tr>
                    <td align="left">
                        <li><html:link action="/admin/packageMaintenanceAction?exec=showAllPackageAdd"><bean:message key="maintenance.page.packages.add"/></html:link></li>
                    </td>
                </tr>
                
                <tr>
                    <td class="th2" align="left">
                        <bean:message key="maintenance.page.about"/>
                    </td>
                </tr>
                <tr>
                    <td align="left">
                        <li><html:link action="/admin/aboutMaintenanceAction?exec=showAbout"><bean:message key="maintenance.page.about.edit"/></html:link></li>
                    </td>
                </tr>          
                
                <tr>
                    <td class="th2" align="left">
                        <bean:message key="maintenance.page.configuration"/>
                    </td>
                </tr>
                <tr>
                    <td align="left">
                        <li><html:link action="/admin/configurationAndSetupAction?exec=createDirectoryStructure"><bean:message key="maintenance.page.setup.createdir"/></html:link></li>
                    </td>
                </tr>                   
                <tr>
                    <td align="left">
                        <li><html:link action="/admin/configurationAndSetupAction?exec=displayAddStylsheet"><bean:message key="title.page.stylesheet.add"/></html:link></li>
                    </td>
                </tr> 
                <tr>
                    <td align="left">
                        <li><html:link action="/admin/configurationAndSetupAction?exec=displayAppParameters"><bean:message key="title.page.property.list"/></html:link></li>
                    </td>
                </tr>                  
            </table>
        </td>
    </tr>
    
</table>

