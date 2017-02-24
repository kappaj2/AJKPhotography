/*
 * MaintenanceAction.java
 *
 * Created on 11 March 2008, 10:08
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.struts.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author akapp
 */
public class MaintenanceAction extends BaseDispatchAction  {
    
    private static Logger log = Logger.getLogger(MaintenanceAction.class);
    
    /**
     * This will force the before action to be executed which set the difference env variables, etc....
     */
    public ActionForward showMaintenanceScreens(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        log.debug("Got a execute action request!!!");
        return mapping.findForward("maintenancePage");
    }
    
}
