/*
 * PackagesMaintenanceService.java
 *
 * Created on 19 March 2008, 12:10
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.service;

import java.io.Serializable;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import za.co.ajk.common.exception.CustomException;
import za.co.ajk.common.exception.ErrorCode;
import za.co.ajk.common.exception.Layer;
import za.co.ajk.persistance.daos.PackageDAO;
import za.co.ajk.persistance.hibernate.dao.DAOException;
import za.co.ajk.persistance.hibernate.dao.factory.AbstractDAOFactory;
import za.co.ajk.persistance.hibernate.dao.factory.DAOFactory;
import za.co.ajk.persistance.transferobjects.Packages;

/**
 *
 * @author akapp
 */
public class PackagesMaintenanceService {
    
    private DAOFactory packageFactory;
    private PackageDAO packageDAO;
    private Session hibSession;
    
    private static Logger log = Logger.getLogger(PackagesMaintenanceService.class);
    
    /** Creates a new instance of PackagesMaintenanceService */
    public PackagesMaintenanceService() {
        packageFactory = AbstractDAOFactory.getFactory("Packages");
        packageDAO = packageFactory.getDAO("Packages");
        hibSession = packageDAO.getSession();
    }
    
    public List<Packages> findAll() throws CustomException{
        try{
            List<Packages> packageList = packageDAO.findAll(Packages.class);
            
            log.debug("Size for packageList is >"+packageList.size());
            
            return packageList;
        }catch (DAOException dao){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Error getting data from DAO. Error is -"+dao.getLocalizedMessage(), dao);
        }catch (HibernateException he){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Hiberate error getting data from DAO. Error is -"+he.getLocalizedMessage(), he);
        }
    }
    
    
    public void doLoadDelete(Packages packages)throws CustomException{
        try{
            
            packageDAO.doLoadDelete(packages);
            
        }catch (DAOException dao){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Error getting data from DAO. Error is -"+dao.getLocalizedMessage(), dao);
        }catch (HibernateException he){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Hiberate error getting data from DAO. Error is -"+he.getLocalizedMessage(), he);
        }
    }
    /**
     *  This method will save the Image object to the database. It will also save the image byte[] to the filesystem
     * @param Packages
     * @throws CustomException
     */
    public void save(Packages packages) throws CustomException{
        try{
            packageDAO.save(packages);
        }catch (DAOException dao){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Error getting data from DAO. Error is -"+dao.getLocalizedMessage(), dao);
        }catch (HibernateException he){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Hiberate error getting data from DAO. Error is -"+he.getLocalizedMessage(), he);
        }
    }
    
    /**
     *  This method will load the Image object for the database using the PK value
     * @param Serializable
     * @return PAckages
     * @throws CustomException
     */
    public Packages load(Serializable pkValue) throws CustomException{
        
        if(log.isDebugEnabled()){
            log.debug("Received a load in PackgeMaintenanceService");
        }
        
        Packages packages = new Packages();
        try{
            
            packages = packageDAO.load(Packages.class, pkValue, hibSession);
            
        }catch (DAOException dao){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Error getting data from DAO. Error is -"+dao.getLocalizedMessage(), dao);
        }catch (HibernateException he){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Hiberate error getting data from DAO. Error is -"+he.getLocalizedMessage(), he);
        }
        return packages;
    }
    
    /**
     * This method will update a specific Image object. It will also remove and write a new filesystem entry if the values are present
     * @param Packages
     * @throws CustomException
     */
    public void update(Packages packages) throws CustomException{
                
        try{
            packageDAO.update(packages, hibSession);
        }catch (DAOException dao){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Error getting data from DAO. Error is -"+dao.getLocalizedMessage(), dao);
        }catch (HibernateException he){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Hiberate error getting data from DAO. Error is -"+he.getLocalizedMessage(), he);
        }
    }
}
