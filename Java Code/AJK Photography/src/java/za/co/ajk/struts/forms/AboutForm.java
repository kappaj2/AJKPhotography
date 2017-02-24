/*
 * AboutForm.java
 *
 * Created on 27 March 2008, 09:43
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.struts.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author akapp
 */
public class AboutForm extends ActionForm{
    
    private String aboutHeading;
    private String aboutDescription;
    private static Logger log = Logger.getLogger(AboutForm.class);
    
    public String getAboutHeading() {
        return aboutHeading;
    }
    
    public void setAboutHeading(String aboutHeading) {
        this.aboutHeading = aboutHeading;
    }
    
    public String getAboutDescription() {
        return aboutDescription;
    }
    
    public void setAboutDescription(String aboutDescription) {
        this.aboutDescription = aboutDescription;
    }
    
    public void reset(ActionMapping arg0, HttpServletRequest arg1){
        log.debug("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        log.debug(" ~~~~~~~~~~~ reset called in the AvoutForm ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        log.debug("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }
    
    public ActionErrors validate(
            ActionMapping mapping, HttpServletRequest request ) {
        
        ActionErrors errors = new ActionErrors();
        
        if(aboutDescription != null && aboutDescription.length() > 1024){
            errors.add("invalidmaxlength", new ActionMessage("invalid.lengt.max.1024", getAboutDescription().length()));
        }
        return errors;
    }
}
