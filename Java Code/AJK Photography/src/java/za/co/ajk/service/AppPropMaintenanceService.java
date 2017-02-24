/*
 * AppPropMaintenanceService.java
 *
 * Created on 01 April 2008, 11:08
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.service;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import za.co.ajk.common.exception.CustomException;
import za.co.ajk.common.exception.ErrorCode;
import za.co.ajk.common.exception.Layer;
import za.co.ajk.common.util.FileUtility;
import za.co.ajk.persistance.daos.AppPropDAO;
import za.co.ajk.persistance.hibernate.dao.DAOException;
import za.co.ajk.persistance.hibernate.dao.factory.AbstractDAOFactory;
import za.co.ajk.persistance.hibernate.dao.factory.DAOFactory;
import za.co.ajk.persistance.transferobjects.AppProp;


/**
 *
 * @author akapp
 */
public class AppPropMaintenanceService {
    
    private DAOFactory appPropFactory;
    private AppPropDAO appPropDAO;
    private Session hibSession;
    private FileUtility fileUtility;
    
    private static Logger log = Logger.getLogger(AppPropMaintenanceService.class);
    
    /**
     * Creates a new instance of AppPropMaintenanceService
     */
    public AppPropMaintenanceService() {
        
        appPropFactory = AbstractDAOFactory.getFactory("AppProp");
        appPropDAO = appPropFactory.getDAO("AppProp");
        hibSession = appPropDAO.getSession();
        fileUtility = new FileUtility();
    }
    
    /**
     * This method will find all the parameter entries in the database
     * @return List
     * @throws CustomExcetpion
     */
    public List<AppProp> findAll() throws CustomException{
        try{
            List<AppProp> appPropList = appPropDAO.findAll(AppProp.class);
            
            log.debug("Size for appPropList is >"+ appPropList.size());
            
            return appPropList;
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
    public AppProp load(Serializable pkValue) throws CustomException{
        
        if(log.isDebugEnabled()){
            log.debug("Received a load in PackgeMaintenanceService");
        }
        
        AppProp appProp = new AppProp();
        try{
            
            appProp = appPropDAO.load(AppProp.class, pkValue, hibSession);
            
            log.debug("Found prop with key >"+appProp.getPropertyKey());
            log.debug("Found prop with def value >"+appProp.getPropertyValueDefault()+"<");
            log.debug("Found prop with value >"+appProp.getPropertyValue()+"<");
            
        }catch (DAOException dao){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Error getting data from DAO. Error is -"+dao.getLocalizedMessage(), dao);
        }catch (HibernateException he){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Hiberate error getting data from DAO. Error is -"+he.getLocalizedMessage(), he);
        }
        return appProp;
    }
    
    
    /**
     * This method will find a specific property using the propKey as lookup
     * @param String propKey to use for searching
     * @return AppProp
     * @throws CustomException
     */
    public AppProp findProp(String propKey) throws CustomException{
        log.debug("Received a findProp call with key >"+propKey+"<");
        try{
            List appPropList = findAll();
            log.debug("Properties list size is >"+appPropList.size()+"<");
            
            for (Iterator it = appPropList.iterator(); it.hasNext();) {
                AppProp elem = (AppProp) it.next();
                
                if(elem.getPropertyKey().equals(propKey)){
                    return elem;
                }
            }
            
            return null;
            
        }catch (DAOException dao){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Error getting data from DAO. Error is -"+dao.getLocalizedMessage(), dao);
        }catch (HibernateException he){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Hiberate error getting data from DAO. Error is -"+he.getLocalizedMessage(), he);
        }
    }
    
    /**
     * This method will search for this AppProp and update if found else add a new one
     * @param AppProp
     * @throws CustomException
     */
    public void loadAndUpdate(AppProp appProp) throws CustomException{
        try{
            
            AppProp appPropTemp = appPropDAO.load(AppProp.class, appProp.getPropertyKey(), hibSession);
            
            BeanUtils.copyProperties(appPropTemp, appProp);
            appPropDAO.update(appPropTemp, hibSession);
            
        }catch (DAOException dao){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Error getting data from DAO. Error is -"+dao.getLocalizedMessage(), dao);
        }catch (HibernateException he){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Hiberate error getting data from DAO. Error is -"+he.getLocalizedMessage(), he);
        }catch (Exception iea){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Error getting data from DAO. Error is -"+iea.getLocalizedMessage(), iea);
        }
    }
    
    
    /**
     *  This method will return a list of the files in the specific fileType directory
     * @param FileUtility.FileType
     * @return String[] of file names
     * @throws CustomExecption
     */
    public String[] getFileListing(FileUtility.FileType fileType) throws CustomException{
        String[] files = fileUtility.getFileListing(fileType);
        
        return files;
    }
    
    public String getCorrectValue(AppProp appProp){
        if (!appProp.getPropertyValue().equals(appProp.getPropertyValueDefault())){
            return appProp.getPropertyValue();
        }else{
            return appProp.getPropertyValueDefault();
        }
        
    }
}
