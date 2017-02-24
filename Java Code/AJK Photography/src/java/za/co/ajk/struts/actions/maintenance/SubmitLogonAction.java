/*
 * SubmitLogonAction.java
 *
 * Created on 07 April 2008, 01:18
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.struts.actions.maintenance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import za.co.ajk.common.exception.CustomException;
import za.co.ajk.common.exception.ErrorCode;
import za.co.ajk.common.util.User;
import za.co.ajk.service.SecurityService;
import za.co.ajk.struts.actions.BaseDispatchAction;

/**
 *
 * @author akapp
 */
public class SubmitLogonAction extends BaseDispatchAction {
    
    private static Logger log = Logger.getLogger(SubmitLogonAction.class);
    private SecurityService securityService = new SecurityService();
    
    public ActionForward validate( ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {
        
        log.debug("Doing execute in SubmitLogonAction");
        
        HttpSession session = request.getSession();
        
        ActionErrors errors = new ActionErrors();
        
        String username = (String) PropertyUtils.getSimpleProperty(form, "username");
        String password = (String) PropertyUtils.getSimpleProperty(form, "password");
        
        log.debug("Validating with username >"+username+"<");
        
        boolean isValidUser = false;
        try{
            isValidUser = securityService.isPasswordValid(username, password);
            
            log.debug("Value for isValidUSer is >"+isValidUser+"<");
            
            if (!isValidUser){
                
                ActionMessage msg;
                msg = new ActionMessage("error.security.invalid.password");
                errors.add(ActionMessages.GLOBAL_MESSAGE, msg);
                saveMessages(request.getSession(true), errors);
                saveAppInfo(request, errors);
            }
            
        }catch (CustomException ce){
            
            if (ce.getErrorCode().equals(ErrorCode.SECURITY_USERNAME_ERROR)){
                log.debug("Error - username problem");
                ActionMessage msg;
                msg = new ActionMessage("error.security.invalid.username");
                errors.add(ActionMessages.GLOBAL_MESSAGE, msg);
                saveMessages(request.getSession(true), errors);
                saveAppInfo(request, errors);
                
            }else{
                log.debug("Error - general security error", ce);
                ActionMessage msg;
                msg = new ActionMessage("error.security.general", ce.getMessage());
                errors.add(ActionMessages.GLOBAL_MESSAGE, msg);
                saveMessages(request.getSession(true), errors);
                saveAppInfo(request, errors);
            }
        }
        
        if (!errors.isEmpty()){
            log.debug("security errors is not empty - redirect to login page");
            return (mapping.getInputForward());
        }
        
        User user = new User(username);
        user.setUsername(username);
        
        session.setAttribute("user", user);
        
        System.out.println("Inside validation for logon!!!");
        return mapping.findForward("success");
    }
}
