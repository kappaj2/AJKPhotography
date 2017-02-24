/*
 * VisitorCommentDAO.java
 *
 * Created on 25 March 2008, 01:18
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.persistance.daos;

import java.util.List;
import org.apache.log4j.Logger;
import za.co.ajk.common.exception.CustomException;
import za.co.ajk.persistance.hibernate.dao.hibernate.AbstractHibernateDAO;
import za.co.ajk.persistance.transferobjects.VisitorComment;

/**
 *
 * @author akapp
 */
public class VisitorCommentDAO extends AbstractHibernateDAO{
    
    private static Logger log = Logger.getLogger(VisitorCommentDAO.class);
    
    /**
     *  This method will return all the comments that are still pending approval.
     * @return List
     * @throws CustomException
     */
    public List<VisitorComment> findPendingApprovals() throws CustomException{
        List<VisitorComment> visitorCommentList = super.findDataByColumn(VisitorComment.class, "published", "N");
        return visitorCommentList;
    }

    /**
     *  This method will return all the comments that have already been approved.
     * @return List
     * @throws CustomException
     */
    public List<VisitorComment> findAllApprovedComments() throws CustomException{
        List<VisitorComment> visitorCommentList = super.findDataByColumn(VisitorComment.class, "published", "Y");
        return visitorCommentList;
    }
}
