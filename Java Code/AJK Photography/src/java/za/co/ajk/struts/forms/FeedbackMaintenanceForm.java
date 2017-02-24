/*
 * FeedbackMaintenanceForm.java
 *
 * Created on 25 March 2008, 03:24
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.struts.forms;

import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import za.co.ajk.persistance.transferobjects.VisitorComment;

/**
 *
 * @author akapp
 */
public class FeedbackMaintenanceForm extends ActionForm{
    
    private static Logger log = Logger.getLogger(FeedbackMaintenanceForm.class);
    
    private String[] commentText = null;
    private String[] commentId = null;
    
    private Collection feedbackList;
    private Collection visitorList;
    
    public Collection getFeedbackList() {
        return feedbackList;
    }
    
    public void setFeedbackList(Collection feedbackList) {
        this.feedbackList = feedbackList;
    }
    
    public void reset(ActionMapping arg0, HttpServletRequest arg1){
        
        feedbackList = new ArrayList<VisitorComment>();
        
        log.debug("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        log.debug(" ~~~~~~~~~~~ reset called in the FeedbackMaintenanceForm ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        log.debug("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        
        
    }
    
    public String[] getCommentText() {
        return commentText;
    }
    
    public void setCommentText(String[] commentText) {
        this.commentText = commentText;
    }
    
    public String[] getCommentId() {
        return commentId;
    }
    
    public void setCommentId(String[] commentId) {
        this.commentId = commentId;
    }
    
    public Collection getVisitorList() {
        return visitorList;
    }
    
    public void setVisitorList(Collection visitorList) {
        this.visitorList = visitorList;
    }
}
