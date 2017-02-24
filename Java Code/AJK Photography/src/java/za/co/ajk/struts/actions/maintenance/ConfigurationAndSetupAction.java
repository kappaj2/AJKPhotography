/*
 * ConfigurationAndSetupAction.java
 *
 * Created on 28 March 2008, 11:28
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.struts.actions.maintenance;

import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;
import za.co.ajk.common.exception.CustomException;
import za.co.ajk.common.util.FileUtility;
import za.co.ajk.persistance.transferobjects.AppProp;
import za.co.ajk.service.AppPropMaintenanceService;
import za.co.ajk.service.ConfigurationAndSetupService;
import za.co.ajk.struts.actions.BaseDispatchAction;
import za.co.ajk.struts.forms.ConfigurationAndSetupForm;

/**
 *
 * @author akapp
 */
public class ConfigurationAndSetupAction extends BaseDispatchAction{
    
    private static Logger log = Logger.getLogger(ConfigurationAndSetupAction.class);
    
    /*
     *  Define service for dealing with DAO's
     */
    private ConfigurationAndSetupService configurationAndSetupService = new ConfigurationAndSetupService();
    private AppPropMaintenanceService appPropMaintenanceService = new AppPropMaintenanceService();
    private FileUtility fileUtility = new FileUtility();
    
    /**
     * This method will create the required directory structure to setup the application
     */
    public ActionForward createDirectoryStructure(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        log.debug("Got a createDirectoryStructure action request!!!");
        
        configurationAndSetupService.buildDiretories();
        
        ActionMessages errors = new ActionMessages();
        ActionMessage msg;
        
        msg = new ActionMessage("title.page.configuration.createdirs");
        
        errors.add(ActionMessages.GLOBAL_MESSAGE, msg);
        saveMessages(request.getSession(true), errors);
        saveAppInfo(request, errors);
        
        return mapping.findForward("showConfigurationAndSetup");
    }
    
    /**
     *  This method will forward to the dadd a new stylesheet screen.
     */
    public ActionForward displayAddStylsheet(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        log.debug("Got a displayAddStylesheet action request!!!");
        return mapping.findForward("showAddStylesheet");
    }
    
    
    /**
     * This method will save the new stylesheet to the file system
     */
    public ActionForward saveStylesheet(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        log.debug("Got a saveStylesheet action request!!!");
        
        ConfigurationAndSetupForm configurationAndSetupForm = (ConfigurationAndSetupForm)form;
        FormFile myFile = configurationAndSetupForm.getMyFile();
        
        String fileName = myFile.getFileName().toLowerCase();
        byte[] fileData = myFile.getFileData();
        
        try{
            
            fileUtility.writeFile(fileData, FileUtility.FileType.CSS, fileName);
            
        }catch (CustomException ce){
            
            ActionMessages errors = new ActionMessages();
            ActionMessage msg;
            
            msg = new ActionMessage("title.page.stylesheet.file.error", ce.getLocalizedMessage());
            
            errors.add(ActionMessages.GLOBAL_MESSAGE, msg);
            saveMessages(request.getSession(true), errors);
            saveAppErrors(request, errors);
            return mapping.findForward("showAddStylesheet");
        }
        
        ActionMessages errors = new ActionMessages();
        ActionMessage msg;
        
        msg = new ActionMessage("title.page.stylesheet.file.saved");
        
        errors.add(ActionMessages.GLOBAL_MESSAGE, msg);
        saveMessages(request.getSession(true), errors);
        saveAppInfo(request, errors);
        
        return mapping.findForward("showAddStylesheet");
    }
    
    
    
    
    
    /**
     * This method will retrieve and display all the application parameters.
     */
    public ActionForward displayAppParameters(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        log.debug("Got a saveStylesheet action request!!!");
        
        ConfigurationAndSetupForm configurationAndSetupForm = (ConfigurationAndSetupForm)form;
        
        List<AppProp> appPropList = appPropMaintenanceService.findAll();
        configurationAndSetupForm.setAppPropList(appPropList);
        
        return mapping.findForward("displayPropList");
    }
    
    /**
     * This method will retrieve and display all the application parameters.
     */
    public ActionForward displayPropEdit(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        log.debug("Got a displayEditProperty action request!!!");
        
        ConfigurationAndSetupForm configurationAndSetupForm = (ConfigurationAndSetupForm)form;
        
        String propertyKey = request.getParameter("propertyKey");
        AppProp appProp = appPropMaintenanceService.load(propertyKey);
        BeanUtils.copyProperties(configurationAndSetupForm, appProp);
        
        return mapping.findForward("displayPropEdit");
    }
    
    /**
     *  This method will update the application property. 
     *  NOTE: Stylesheet (css) must be lowercase.
     */
    public ActionForward updateProperty(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        log.debug("Got a updateProperty action request!!!");
        
        ConfigurationAndSetupForm configurationAndSetupForm = (ConfigurationAndSetupForm)form;
        
        AppProp appProp = new AppProp();
        BeanUtils.copyProperties(appProp, configurationAndSetupForm);
        /*
         *  Stylesheet name must lowercase - the rest can be upper/lower mixed
         */
        if (appProp.getPropertyKey().equals("css")){
            appProp.setPropertyValue(appProp.getPropertyValue().toLowerCase());
        }else{
            appProp.setPropertyValue(appProp.getPropertyValue());
        }
        
        appPropMaintenanceService.loadAndUpdate(appProp);
        
        return displayAppParameters(mapping, form, request, response);
    }
    
    
    public ActionForward getCSSFileListing(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        log.debug("Got a getCSSFileListing action request!!!");
        String[] files = appPropMaintenanceService.getFileListing(FileUtility.FileType.CSS);
        
        StringBuffer strBuff = new StringBuffer();
        strBuff.append("<items>");
        for (int i = 0; i < files.length; i++) {
            
            strBuff.append("<item>");
            strBuff.append("<display>");
            strBuff.append(files[i]);
            strBuff.append("</display>");
            strBuff.append("<value>");
            strBuff.append(files[i]);
            strBuff.append("</value>");
            strBuff.append("</item>");
            
        }
        strBuff.append("</items>");
        
        String res = strBuff.toString();
        
        PrintWriter out = response.getWriter();
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        
        out.println(res);
        
        out.close();
        
        return null;
        
    }
    
}
