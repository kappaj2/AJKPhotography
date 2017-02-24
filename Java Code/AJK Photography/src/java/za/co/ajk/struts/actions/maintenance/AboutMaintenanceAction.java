/*
 * AboutMaintenanceAction.java
 *
 * Created on 27 March 2008, 09:44
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.struts.actions.maintenance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import za.co.ajk.persistance.transferobjects.About;
import za.co.ajk.service.AboutMaintenanceService;
import za.co.ajk.struts.actions.BaseDispatchAction;
import za.co.ajk.struts.forms.AboutForm;

/**
 *
 * @author akapp
 */
public class AboutMaintenanceAction extends BaseDispatchAction{
    
    private static Logger log = Logger.getLogger(AboutMaintenanceAction.class);
    
    /*
     *  Define service for dealing with DAO's
     */
    private AboutMaintenanceService aboutMaintenanceService = new AboutMaintenanceService();
    
    /**
     * This method will retrieve the existing about record and forward for edit/update
     */
    public ActionForward showAbout(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        log.debug("Got a showAbout action request!!!");
        
        About about = aboutMaintenanceService.getAbout();
        
        AboutForm aboutForm = (AboutForm)form;
        BeanUtils.copyProperties(aboutForm, about);
        
        return mapping.findForward("showEditUpdateAbout");
    }
    
    /**
     * This method will retrieve the existing about record and forward for edit/update
     */
    public ActionForward saveUpdateAbout(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        log.debug("Got a saveUpdateAbout action request!!!");
        
        About about = new About();
        AboutForm aboutForm = (AboutForm)form;
        
        BeanUtils.copyProperties(about, aboutForm);
        aboutMaintenanceService.saveOrUpdate(about);
        
        ActionMessages errors = new ActionMessages();
        ActionMessage msg;
        
        msg = new ActionMessage("title.page.about.updated");
        
        errors.add(ActionMessages.GLOBAL_MESSAGE, msg);
        saveMessages(request.getSession(true), errors);
        saveAppInfo(request, errors);
        
        return mapping.findForward("showEditUpdateAbout");
    }
}
