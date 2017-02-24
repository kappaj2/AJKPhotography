/*
 * PackageDAO.java
 *
 * Created on 19 March 2008, 11:34
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.persistance.daos;

import java.io.Serializable;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import za.co.ajk.common.exception.CustomException;
import za.co.ajk.common.exception.ErrorCode;
import za.co.ajk.common.exception.Layer;
import za.co.ajk.persistance.hibernate.dao.hibernate.AbstractHibernateDAO;
import za.co.ajk.persistance.transferobjects.Packages;

/**
 *
 * @author akapp
 */
public class PackageDAO extends AbstractHibernateDAO{
    
    private static Logger log = Logger.getLogger(PackageDAO.class);
    
    public Packages load(Serializable primaryKey) throws CustomException{
        return (Packages)super.load(Packages.class, primaryKey);
    }
    
    
    public void doLoadDelete(Packages packages) throws CustomException{
        
        log.debug("Received a loadDelete call for Package");
        try{
            
            Session vHibernateSession = getSession();
            
            packages = load(Packages.class, packages.getPackageId(), vHibernateSession);
            
            delete(packages, vHibernateSession);
            
            vHibernateSession.flush();
            vHibernateSession.clear();
            
        }catch (Exception ex){
            ex.printStackTrace();
            log.debug("Error doing LoadDelete", ex);
            
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.PERSISTENCE, "Unable to delete file from the file system", ex);
            
        }
    }
    
    public void update(Packages packages, Session hibSession) throws CustomException{

        /*
         *  Persist the database stuff via super method.
         */
        super.update(packages, hibSession);
    }    
}
