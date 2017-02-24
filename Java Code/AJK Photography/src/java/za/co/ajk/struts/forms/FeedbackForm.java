/*
 * FeedbackForm.java
 *
 * Created on 20 March 2008, 10:55
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.struts.forms;

import com.octo.captcha.service.CaptchaServiceException;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import za.co.ajk.common.util.CaptchaServiceSingleton;

/**
 *
 * @author akapp
 */
public class FeedbackForm extends ActionForm{
    
    private static Logger log = Logger.getLogger(FeedbackForm.class);
    
    private String jcaptchaResponse;
    private String name;
    private String surname;
    private String email;
    private String comments;
    
    private String result;
    private String thankYou;
    
    private Collection feedbackList;
    
    public String getJcaptchaResponse() {
        return jcaptchaResponse;
    }
    
    public void setJcaptchaResponse(String jcaptchaResponse) {
        this.jcaptchaResponse = jcaptchaResponse;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getComments() {
        return comments;
    }
    
    public void setComments(String comments) {
        this.comments = comments;
    }
    
    public String getResult() {
        return result;
    }
    
    public void setResult(String result) {
        this.result = result;
    }
    
    public String getThankYou() {
        return thankYou;
    }
    
    public void setThankYou(String thankYou) {
        this.thankYou = thankYou;
    }
    
    public void reset(ActionMapping arg0, HttpServletRequest arg1){
        log.debug("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        log.debug(" ~~~~~~~~~~~ reset called in the FeedbackForm ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        log.debug("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        
        setJcaptchaResponse(null);
    }
    
    public ActionErrors validate(
            ActionMapping mapping, HttpServletRequest request ) {
        
        ActionErrors errors = new ActionErrors();
        
        if (getJcaptchaResponse() != null){
            String captchaId = request.getSession().getId();
            Boolean isResponseCorrect = Boolean.FALSE;
            
            try {
                isResponseCorrect = CaptchaServiceSingleton.getInstance().validateResponseForID(captchaId, getJcaptchaResponse());
            } catch (CaptchaServiceException e) {
                log.error("Exception - " + e.getCause().getMessage());
            }
            
            log.debug("Captcha response validation result is >"+isResponseCorrect+"<");
            
            if (!isResponseCorrect){
                errors.add("invalidcaptcha", new ActionMessage("title.page.feedback.captchainvalid"));
            }
            
            if (getComments() != null && getComments().length() > 1024){
                errors.add("invalidcomments", new ActionMessage("title.page.feedback.commentstolong", getComments().length()));
            }
        }
        setJcaptchaResponse(null);
        return errors;
    }
    
    public String getSurname() {
        return surname;
    }
    
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Collection getFeedbackList() {
        return feedbackList;
    }

    public void setFeedbackList(Collection feedbackList) {
        this.feedbackList = feedbackList;
    }
    
}
