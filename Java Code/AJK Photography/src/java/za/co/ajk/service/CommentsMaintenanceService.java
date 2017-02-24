/*
 * CommentsMaintenanceService.java
 *
 * Created on 25 March 2008, 12:57
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.service;

import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import za.co.ajk.common.exception.CustomException;
import za.co.ajk.common.exception.ErrorCode;
import za.co.ajk.common.exception.Layer;
import za.co.ajk.persistance.daos.VisitorCommentDAO;
import za.co.ajk.persistance.daos.VisitorDAO;
import za.co.ajk.persistance.hibernate.dao.DAOException;
import za.co.ajk.persistance.hibernate.dao.factory.AbstractDAOFactory;
import za.co.ajk.persistance.hibernate.dao.factory.DAOFactory;
import za.co.ajk.persistance.transferobjects.Visitor;
import za.co.ajk.persistance.transferobjects.VisitorComment;

/**
 *
 * @author akapp
 */
public class CommentsMaintenanceService {
    
    private DAOFactory visitorFactory;
    private VisitorDAO visitorDAO;
    
    private DAOFactory visitorCommentFactory;
    private VisitorCommentDAO visitorCommentDAO;
    
    private Session hibSession;
    
    /** Creates a new instance of CommentsMaintenanceService */
    public CommentsMaintenanceService() {
        visitorFactory = AbstractDAOFactory.getFactory("Visitor");
        visitorDAO = visitorFactory.getDAO("Visitor");
        
        visitorCommentFactory = AbstractDAOFactory.getFactory("VisitorComment");
        visitorCommentDAO = visitorCommentFactory.getDAO("VisitorComment");
        
        hibSession = visitorDAO.getSession();
    }
    
    
    public Visitor load(Serializable pkValue) throws CustomException{
        try{
            Visitor visitor = visitorDAO.load(Visitor.class, pkValue, hibSession);
            return visitor;
        }catch (DAOException dao){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Error getting data from DAO. Error is -"+dao.getLocalizedMessage(), dao);
        }catch (HibernateException he){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Hiberate error getting data from DAO. Error is -"+he.getLocalizedMessage(), he);
        }
    }
    
    /**
     * This method will return all the visitors in the database.
     */
    public List<Visitor> findAllVisitors() throws CustomException{
        try{
            List<Visitor> visitorList = visitorDAO.findAll(Visitor.class);
            return visitorList;
        }catch (DAOException dao){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Error getting data from DAO. Error is -"+dao.getLocalizedMessage(), dao);
        }catch (HibernateException he){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Hiberate error getting data from DAO. Error is -"+he.getLocalizedMessage(), he);
        }
    }
    
    
    public Serializable save(Visitor visitor) throws CustomException{
        try{
            Long pkValue = (Long)visitorDAO.save(visitor);
            return pkValue;
        }catch (DAOException dao){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Error getting data from DAO. Error is -"+dao.getLocalizedMessage(), dao);
        }catch (HibernateException he){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Hiberate error getting data from DAO. Error is -"+he.getLocalizedMessage(), he);
        }
    }
    
    public List<Visitor> findByQBE(Visitor visitor) throws CustomException{
        try{
            List visitorList = visitorDAO.findByQBE(Visitor.class, visitor);
            return visitorList;
        }catch (DAOException dao){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Error getting data from DAO. Error is -"+dao.getLocalizedMessage(), dao);
        }catch (HibernateException he){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Hiberate error getting data from DAO. Error is -"+he.getLocalizedMessage(), he);
        }
    }
    
    public void update(Visitor visitor) throws CustomException{
        try{
            visitorDAO.update(visitor, hibSession);
            
        }catch (DAOException dao){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Error getting data from DAO. Error is -"+dao.getLocalizedMessage(), dao);
        }catch (HibernateException he){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Hiberate error getting data from DAO. Error is -"+he.getLocalizedMessage(), he);
        }
    }
    
    public void loadDeleteComment(Serializable pkValue) throws CustomException{
        try{
            
            VisitorComment visitorComment = visitorCommentDAO.load(VisitorComment.class, pkValue, hibSession);
            visitorCommentDAO.delete(visitorComment);
            
        }catch (DAOException dao){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Error getting data from DAO. Error is -"+dao.getLocalizedMessage(), dao);
        }catch (HibernateException he){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Hiberate error getting data from DAO. Error is -"+he.getLocalizedMessage(), he);
        }
    }
    
    /**
     * This method will publish a comment by updating the publish status only
     * @param Serializable the pk value of the comment db entry
     * @throws CustomException
     */
    public void publishComment(Serializable pkValue) throws CustomException{
        try{
            
            VisitorComment visitorComment = visitorCommentDAO.load(VisitorComment.class, pkValue, hibSession);
            visitorComment.setPublished("Y");
            visitorCommentDAO.update(visitorComment, hibSession);
            
        }catch (DAOException dao){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Error getting data from DAO. Error is -"+dao.getLocalizedMessage(), dao);
        }catch (HibernateException he){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Hiberate error getting data from DAO. Error is -"+he.getLocalizedMessage(), he);
        }
    }
    
    /**
     * This method will publish a comment by updating the comment text and the publish status
     * @param Serializable the pk value of the comment db entry
     * @param String the comment text
     * @throws CustomException
     */
    public void publishComment(Serializable pkValue, String comment) throws CustomException{
        try{
            
            VisitorComment visitorComment = visitorCommentDAO.load(VisitorComment.class, pkValue, hibSession);
            visitorComment.setPublished("Y");
            visitorComment.setCommentText(comment);
            visitorCommentDAO.update(visitorComment, hibSession);
            
        }catch (DAOException dao){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Error getting data from DAO. Error is -"+dao.getLocalizedMessage(), dao);
        }catch (HibernateException he){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Hiberate error getting data from DAO. Error is -"+he.getLocalizedMessage(), he);
        }
    }
    
    /**
     * This method will return all the comments awaiting approval
     * @return List
     * @throws CustomException
     */
    public List<VisitorComment> findPendingApprovals() throws CustomException{
        try{
            
            List<VisitorComment> visitorCommentList = visitorCommentDAO.findPendingApprovals();
            return visitorCommentList;
            
        }catch (DAOException dao){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Error getting data from DAO. Error is -"+dao.getLocalizedMessage(), dao);
        }catch (HibernateException he){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Hiberate error getting data from DAO. Error is -"+he.getLocalizedMessage(), he);
        }
        
    }
    
    /**
     * This method will return all the comments that have been approved
     * @return List
     * @trhows CustomException
     */
    public List<VisitorComment> findAllApprovedComments() throws CustomException{
        try{
            
            List<VisitorComment> visitorCommentList = visitorCommentDAO.findAllApprovedComments();
            return visitorCommentList;
            
        }catch (DAOException dao){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Error getting data from DAO. Error is -"+dao.getLocalizedMessage(), dao);
        }catch (HibernateException he){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Hiberate error getting data from DAO. Error is -"+he.getLocalizedMessage(), he);
        }
        
    }
}
