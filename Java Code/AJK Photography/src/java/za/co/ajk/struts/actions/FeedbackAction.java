/*
 * FeedbackAction.java
 *
 * Created on 20 March 2008, 10:59
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.struts.actions;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import za.co.ajk.common.util.UniversalComparator;
import za.co.ajk.persistance.transferobjects.ImageCategory;
import za.co.ajk.persistance.transferobjects.Visitor;
import za.co.ajk.persistance.transferobjects.VisitorComment;
import za.co.ajk.service.CategoryMaintenanceService;
import za.co.ajk.service.CommentsMaintenanceService;
import za.co.ajk.struts.forms.FeedbackForm;
import za.co.ajk.struts.forms.ImageForm;

/**
 *
 * @author akapp
 */
public class FeedbackAction extends BaseDispatchAction{
    
    private static Logger log = Logger.getLogger(FeedbackAction.class);
    private CommentsMaintenanceService commentsMaintenanceService = new CommentsMaintenanceService();
    private CategoryMaintenanceService categoryMaintenanceService = new CategoryMaintenanceService();
    
    /**
     * This method will forward to the display of the feedback jsp. No other action required.
     */
    public ActionForward showFeedback(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        log.debug("Got a showFeedback action request!!!");
        
        super.setPageName("showFeedback");
        /*
         * Re-populate the imageList with the "root" categories.
         */
        HttpSession session = request.getSession();
        ImageForm imageForm = (ImageForm)session.getAttribute("imageForm");
        
        List<ImageCategory> imageCategoryList = categoryMaintenanceService.findAll();
        imageForm.setImageList(imageCategoryList);
        session.setAttribute("imageForm", imageForm);
        
        return mapping.findForward("showFeedback");
    }
    
    
    /**
     * UserResponse to the catcha challenge has already been validated in the forms validate method.
     * Just save the response and forward to the thank-you page.
     */
    public ActionForward saveUserInput(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        log.debug("Got a validateUserInput action request!!!");
        FeedbackForm feedbackForm = (FeedbackForm)form;
        
        /*
         * Create a visitor object with the information provided.
         * Then use QBE to see if this is a new visitor or a previously registered one.
         * The add a set of VisitorComments to store the comment.
         * NOTE: Only use the name/surname as lookup values.
         * Trim leading/trailing spaces !!!
         */
        Visitor visitor = new Visitor();
        
        visitor.setVisitorName(feedbackForm.getName().trim());
        visitor.setVisitorSurname(feedbackForm.getSurname().trim());
        
        VisitorComment visitorComment = new VisitorComment();
        visitorComment.setCommentText(feedbackForm.getComments());
        visitorComment.setPublished("N");
        
        List<Visitor> visitorList = commentsMaintenanceService.findByQBE(visitor);
        
        log.debug("VisitorList size is >"+visitorList.size());
        
        if (visitorList.size() == 0){ /* new visitor */
            
            visitor.setVisitorId(0L);
            visitor.setVisitorEmail(feedbackForm.getEmail().trim());
            
            Long pkValue = (Long)commentsMaintenanceService.save(visitor);
            
            visitor = commentsMaintenanceService.load(pkValue);
            
            visitorComment.setVisitor(visitor);
            
            Set<VisitorComment> commentsSet = visitor.getVisitorComments();
            
            commentsSet.add(visitorComment);
            
            commentsMaintenanceService.update(visitor);
            
            
        }else if (visitorList.size() == 1){  /* existing visitor */
            
            visitor = visitorList.get(0);
            visitor = commentsMaintenanceService.load(visitor.getVisitorId());
            visitorComment.setVisitor(visitor);
            
            Set<VisitorComment> commentsSet = visitor.getVisitorComments();
            
            commentsSet.add(visitorComment);
            
            commentsMaintenanceService.update(visitor);
            
            
        }else if (visitorList.size() > 1){ /* --- error cannot have more than 1 !!!!! */
            
            ActionMessages errors = new ActionMessages();
            ActionMessage msg = new ActionMessage("error.hibernate.duplicate");
            
            errors.add(ActionMessages.GLOBAL_MESSAGE, msg);
            saveMessages(request.getSession(true), errors);
            saveAppErrors(request, errors);
            return mapping.findForward("showFeedback");
        }
        
        feedbackForm.setThankYou("Thank you "+visitor.getVisitorName()+". <br>Your comments have been recorded and will be made available once it has been read and published. <br>We appreciate your time and effort to complete this feedback form.");
        
        return mapping.findForward("showFeedbackThankyou");
    }
    
    public ActionForward showApprovedFeedback(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        log.debug("Got a showApprovedFeedback action request!!!");
        
        List<VisitorComment> visitorCommentList = commentsMaintenanceService.findAllApprovedComments();
        
        /*
         * Order list by date submitted descending order
         */
        Collections.sort(visitorCommentList, new UniversalComparator(
                "getDateSubmitted", UniversalComparator.DESCENDING));
        
        FeedbackForm feedbackForm = (FeedbackForm)form;
        feedbackForm.setFeedbackList(visitorCommentList);
        
        return mapping.findForward("showApprovedFeedback");
    }
    
}
