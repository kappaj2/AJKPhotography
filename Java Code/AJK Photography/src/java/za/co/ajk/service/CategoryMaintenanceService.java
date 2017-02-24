/*
 * CategoryMaintenanceService.java
 *
 * Created on 12 March 2008, 08:07
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
import za.co.ajk.common.util.FileUtility;
import za.co.ajk.persistance.daos.ImageCategoryDAO;
import za.co.ajk.persistance.daos.ImageGalleryDAO;
import za.co.ajk.persistance.hibernate.dao.DAOException;
import za.co.ajk.persistance.hibernate.dao.factory.AbstractDAOFactory;
import za.co.ajk.persistance.hibernate.dao.factory.DAOFactory;
import za.co.ajk.persistance.transferobjects.ImageCategory;

/**
 *
 * @author akapp
 */
public class CategoryMaintenanceService {
    
    private DAOFactory imageCategoryFactory;
    private ImageCategoryDAO imageCategoryDAO;
    
    private DAOFactory imageGalleryFactory;
    private ImageGalleryDAO imageGalleryDAO;
    private Session hibSession;
    
    private FileUtility fileUtility;
    private static Logger log = Logger.getLogger(CategoryMaintenanceService.class);
    
    /**
     * Creates a new instance of CategoryMaintenanceService
     */
    public CategoryMaintenanceService() throws CustomException{
        
        imageCategoryFactory = AbstractDAOFactory.getFactory("ImageCategory");
        imageCategoryDAO = imageCategoryFactory.getDAO("ImageCategory");
        
        imageGalleryFactory = AbstractDAOFactory.getFactory("ImageGallery");
        imageGalleryDAO = imageGalleryFactory.getDAO("ImageGallery");
        fileUtility = new FileUtility();
        
        hibSession = imageCategoryDAO.getSession();
        
    }
    
    public List<ImageCategory> findAll() throws CustomException{
        try{
            List<ImageCategory> categoryList = imageCategoryDAO.findAll(ImageCategory.class);
            
            log.debug("Size for ImageCategories is >"+categoryList.size());
            
            return categoryList;
        }catch (DAOException dao){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Error getting data from DAO. Error is -"+dao.getLocalizedMessage(), dao);
        }catch (HibernateException he){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Hiberate error getting data from DAO. Error is -"+he.getLocalizedMessage(), he);
        }
    }
    
    public ImageCategory load(Serializable pkValue) throws CustomException{
        log.debug("Received a category load call in CategoryMaintenanceService");
        try{
            
            ImageCategory imageCategory = imageCategoryDAO.load(ImageCategory.class, pkValue, hibSession);
            imageCategory.setCategoryImage(imageCategoryDAO.getCategoryFileData(imageCategory));
            return imageCategory;
            
        }catch (DAOException dao){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Error getting data from DAO. Error is -"+dao.getLocalizedMessage(), dao);
        }catch (HibernateException he){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Hiberate error getting data from DAO. Error is -"+he.getLocalizedMessage(), he);
        }
    }
    
    public void doLoadDelete(ImageCategory imageCategory)throws CustomException{
        try{
            
            imageCategoryDAO.doLoadDelete(imageCategory);
            
        }catch (DAOException dao){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Error getting data from DAO. Error is -"+dao.getLocalizedMessage(), dao);
        }catch (HibernateException he){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Hiberate error getting data from DAO. Error is -"+he.getLocalizedMessage(), he);
        }
    }
    
    /**
     *  This method will save the category object to the database and the file to the filesystem via the DAO
     */
    public void save(ImageCategory imageCategory) throws CustomException{
        try{
            imageCategoryDAO.save(imageCategory);
        }catch (DAOException dao){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Error getting data from DAO. Error is -"+dao.getLocalizedMessage(), dao);
        }catch (HibernateException he){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Hiberate error getting data from DAO. Error is -"+he.getLocalizedMessage(), he);
        }
    }
    
    public void update(ImageCategory imageCategory, String oldFileName) throws CustomException{
        
        log.debug("Value for imageCategory in update is >"+imageCategory.toString());
        
        try{
            
            imageCategoryDAO.update(imageCategory, oldFileName, hibSession);
            
        }catch (DAOException dao){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Error getting data from DAO. Error is -"+dao.getLocalizedMessage(), dao);
        }catch (HibernateException he){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Hiberate error getting data from DAO. Error is -"+he.getLocalizedMessage(), he);
        }
    }
}
