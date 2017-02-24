/*
 * FeedbackMaintenanceAction.java
 *
 * Created on 25 March 2008, 03:23
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.struts.actions.maintenance;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import za.co.ajk.common.util.UniversalComparator;
import za.co.ajk.persistance.transferobjects.Visitor;
import za.co.ajk.persistance.transferobjects.VisitorComment;
import za.co.ajk.service.CommentsMaintenanceService;
import za.co.ajk.struts.actions.BaseDispatchAction;
import za.co.ajk.struts.forms.FeedbackMaintenanceForm;

/**
 *
 * @author akapp
 */
public class FeedbackMaintenanceAction extends BaseDispatchAction{
    
    private static Logger log = Logger.getLogger(FeedbackMaintenanceAction.class);
    
    private CommentsMaintenanceService commentsMaintenanceService = new CommentsMaintenanceService();
    
    /**
     * This method will forward to the display of the feedback jsp. No other action required.
     */
    public ActionForward showPendingFeedback(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        log.debug("Got a showPendingFeedback action request!!!");
        
        List<VisitorComment> visitorCommentList = commentsMaintenanceService.findPendingApprovals();
        
        /*
         * Order list by date submitted descending order
         */
        Collections.sort(visitorCommentList, new UniversalComparator(
                "getDateSubmitted", UniversalComparator.DESCENDING));
        
        FeedbackMaintenanceForm feedbackMaintenanceForm = (FeedbackMaintenanceForm)form;
        feedbackMaintenanceForm.setFeedbackList(visitorCommentList);
        
        return mapping.findForward("showPendingFeedback");
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
        
        FeedbackMaintenanceForm feedbackMaintenanceForm = (FeedbackMaintenanceForm)form;
        feedbackMaintenanceForm.setFeedbackList(visitorCommentList);
        
        return mapping.findForward("showApprovedFeedback");
    }
    
    /**
     * This method will forward to the display of the showVisitors jsp. No other action required.
     */
    public ActionForward showVisitors(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        log.debug("Got a showVisitors action request!!!");
        
        List<Visitor> visitorList = commentsMaintenanceService.findAllVisitors();
        
        for (Visitor elem : visitorList) {
            log.debug("Visitor name is >"+elem.getVisitorName()+"< and surname >"+elem.getVisitorSurname()+"<");
            
            Set<VisitorComment> visitorCommentSet = elem.getVisitorComments();
            
            for (VisitorComment elem2 : visitorCommentSet) {
                log.debug("Visitor comment is >"+elem2.getCommentText()+"<");
            }
            
        }
        /*
         * Order list by surname in ascending order
         */
        Collections.sort(visitorList, new UniversalComparator(
                "getVisitorSurname", UniversalComparator.ASCENDING));
        
        FeedbackMaintenanceForm feedbackMaintenanceForm = (FeedbackMaintenanceForm)form;

        feedbackMaintenanceForm.setVisitorList(visitorList);
        
        return mapping.findForward("showVisitors");
    }
    
    /**
     * This method will delete a feedback message
     */
    public ActionForward deleteFeedback(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        log.debug("Got a deleteFeedback action request!!!");
        
        /*
         * Re-populate the imageList with the "root" categories.
         */
        Long commentId = Long.parseLong(request.getParameter("commentId"));
        
        commentsMaintenanceService.loadDeleteComment(commentId);
        
        return showPendingFeedback(mapping, form, request, response);
    }
    
    /**
     * This method will delete a feedback message
     */
    public ActionForward publishFeedback(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        log.debug("Got a publishFeedback action request!!!");
        
        /*
         * Re-populate the imageList with the "root" categories.
         */
        Long commentId = Long.parseLong(request.getParameter("commentId"));
        
        FeedbackMaintenanceForm feedbackMaintenanceForm = (FeedbackMaintenanceForm)form;
        String[] commentIdArr = feedbackMaintenanceForm.getCommentId();
        String[] commentTextArr = feedbackMaintenanceForm.getCommentText();
        
        log.debug("commentIdArr size is >"+commentIdArr.length);
        log.debug("commentTextArr size is >"+commentTextArr.length);
        
        for (int i = 0; i < commentIdArr.length; i++) {
            log.debug("Value for i is >"+i+"<");
            log.debug("Value for id arr is >"+commentIdArr[i]+"<");
            log.debug("Value for text is >"+commentTextArr[i]+"<");
            log.debug("               ----------------            ");
            
        }
        
        commentsMaintenanceService.publishComment(commentId);
        
        return showPendingFeedback(mapping, form, request, response);
    }
    
    
    /**
     * This method will iterate through the arrays created by the form.
     * It will then publish each record and update the comment text with the value received.
     *
     */
    public ActionForward publishFeedbackList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        FeedbackMaintenanceForm feedbackMaintenanceForm = (FeedbackMaintenanceForm)form;
        String[] commentIdArr = feedbackMaintenanceForm.getCommentId();
        String[] commentTextArr = feedbackMaintenanceForm.getCommentText();
        
        log.debug("commentIdArr size is >"+commentIdArr.length);
        log.debug("commentTextArr size is >"+commentTextArr.length);
        
        for (int i = 0; i < commentIdArr.length; i++) {
            log.debug("Value for i is >"+i+"<");
            log.debug("Value for id arr is >"+commentIdArr[i]+"<");
            log.debug("Value for text is >"+commentTextArr[i]+"<");
            log.debug("               ----------------            ");
            
            Long pkValue = Long.parseLong(commentIdArr[i]);
            String comment = commentTextArr[i];
            commentsMaintenanceService.publishComment(pkValue, comment);
        }
        
        return showPendingFeedback(mapping, form, request, response);
    }
    
    
}
