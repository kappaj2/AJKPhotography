/*
 * VisitorDAO.java
 *
 * Created on 25 March 2008, 12:59
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.persistance.daos;

import java.io.Serializable;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import za.co.ajk.common.exception.CustomException;
import za.co.ajk.persistance.hibernate.dao.hibernate.AbstractHibernateDAO;
import za.co.ajk.persistance.transferobjects.Visitor;

/**
 *
 * @author akapp
 */
public class VisitorDAO extends AbstractHibernateDAO{
    
    private static Logger log = Logger.getLogger(VisitorDAO.class);
    
    /**
     * This method will load and return a spcific visitor object.
     * @param pkvalye
     * @return visitor
     * @throws CustomException
     */
    public Visitor load(Serializable primaryKey) throws CustomException{
        return (Visitor)super.load(Visitor.class, primaryKey);
    }
    
    
    /**
     * This method will save the visitor response to the database
     * @param visitor
     * @throws CustomException
     */
    public Serializable save(Visitor visitor) throws CustomException{
        Long pkValue = (Long)super.save(visitor);
        return pkValue;
    }
    
    
    public void update(Visitor visitor, Session hibSession) throws CustomException{
        
        /*
         *  Persist the database stuff via super method.
         */
        super.update(visitor, hibSession);
    }
    

}
