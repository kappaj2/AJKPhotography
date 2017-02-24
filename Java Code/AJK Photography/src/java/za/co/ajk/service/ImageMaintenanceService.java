/*
 * ImageMaintenanceService.java
 *
 * Created on 12 March 2008, 09:05
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
import za.co.ajk.persistance.daos.ImageDAO;
import za.co.ajk.persistance.hibernate.dao.DAOException;
import za.co.ajk.persistance.hibernate.dao.factory.AbstractDAOFactory;
import za.co.ajk.persistance.hibernate.dao.factory.DAOFactory;
import za.co.ajk.persistance.transferobjects.Image;

/**
 *
 * @author akapp
 */
public class ImageMaintenanceService {
    
    private DAOFactory imageFactory;
    private ImageDAO imageDAO;
    private Session hibSession;
    
    private static Logger log = Logger.getLogger(ImageMaintenanceService.class);
    
    /** Creates a new instance of ImageMaintenanceService */
    public ImageMaintenanceService() {
        imageFactory = AbstractDAOFactory.getFactory("Image");
        imageDAO = imageFactory.getDAO("Image");
        hibSession = imageDAO.getSession();
    }
    
    /**
     *  This method will save the Image object to the database. It will also save the image byte[] to the filesystem
     * @param Image
     * @throws CustomException
     */
    public void save(Image image) throws CustomException{
        try{
            imageDAO.save(image);
        }catch (DAOException dao){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Error getting data from DAO. Error is -"+dao.getLocalizedMessage(), dao);
        }catch (HibernateException he){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Hiberate error getting data from DAO. Error is -"+he.getLocalizedMessage(), he);
        }
    }
    
    /**
     *  This method will load the Image object for the database using the PK value
     * @param Serializable
     * @return Image
     * @throws CustomException
     */
    public Image load(Serializable pkValue) throws CustomException{
        
        if(log.isDebugEnabled()){
            log.debug("Received a load in ImageMaintenanceService");
        }
        
        Image image = new Image();
        try{
            
            image = imageDAO.load(Image.class, pkValue, hibSession);
            
        }catch (DAOException dao){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Error getting data from DAO. Error is -"+dao.getLocalizedMessage(), dao);
        }catch (HibernateException he){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Hiberate error getting data from DAO. Error is -"+he.getLocalizedMessage(), he);
        }
        return image;
    }
    
    /**
     *  This method will delete the Image object passed.
     * @param Image
     * @throws CustomException
     */
    public void doOneKeyDelete(Image image) throws CustomException{
        
        if(log.isDebugEnabled()){
            log.debug("Received a doOneKeyDelete in ImageMaintenanceService");
        }
        
        try{
            
            if(log.isDebugEnabled()){
                log.debug("CategoryCode is >"+image.getComp_id().getCategoryCode());
                log.debug("GaleryCode is >"+image.getComp_id().getGalleryCode());
                log.debug("ImageCode is >"+image.getComp_id().getImageCode());
            }
            
            imageDAO.doOneKeyDelete(image);
            
        }catch (DAOException dao){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Error getting data from DAO. Error is -"+dao.getLocalizedMessage(), dao);
        }catch (HibernateException he){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Hiberate error getting data from DAO. Error is -"+he.getLocalizedMessage(), he);
        }
    }
    
    /**
     *  This method will search for all Image objects by passing it a category and gallery code
     * @param Long categoryCode
     * @param Long galleryCode
     * @return List<Image>
     * @throws CustomException
     */
    public List<Image> findByColumn(Long categoryCode, Long galleryCode) throws CustomException{
        
        if(log.isDebugEnabled()){
            log.debug("Received a findByColumn call for GalleryImage");
        }
        
        List<Image> imageList = null;
        try{
            
            imageList = imageDAO.findByColumn(categoryCode, galleryCode);
            
        }catch (Exception ex){
            ex.printStackTrace();
            log.debug("Error doing findByColumn", ex);
        }
        
        return imageList;
    }
    
    
    /**
     *  This method will retrieve a list of Image object for a specific galleryCode.
     * @param Long galleryCode to search with
     * @return List<Image>
     * @throws CustomException
     */
    public List<Image> findByGalleryCode(Long galleryCode) throws CustomException{
        
        if(log.isDebugEnabled()){
            log.debug("Received a findByGalleryCode call for Image");
        }
        
        List<Image> imageList = null;
        try{
            
            imageList = imageDAO.findDataByColumn(Image.class, "comp_id.galleryCode", galleryCode);
            
        }catch (Exception ex){
            ex.printStackTrace();
            log.debug("Error doing findByColumn", ex);
        }
        
        return imageList;
    }
    
    /**
     *  This method will retrieve an Image object for a specific imageCode.
     * @param Long imageCode to search with
     * @return List<Image>
     * @throws CustomException
     */
    public Image findByImageCode(Long imageCode, boolean largeVersion) throws CustomException{
        
        if(log.isDebugEnabled()){
            log.debug("Received a findByImageCode call for Image");
        }
        
        Image image = new Image();
        List<Image> imageList = null;
        try{
            
            imageList = imageDAO.findDataByColumn(Image.class, "comp_id.imageCode", imageCode);
            image = imageList.get(0);
            image.setImageData(imageDAO.getImageFileData(image, largeVersion));
            
        }catch (Exception ex){
            ex.printStackTrace();
            log.debug("Error doing findByColumn", ex);
        }
        
        return image;
    }
    
    /**
     * This method will delete an Image object It will also clean the filesystem
     * @param Image
     * @throws CustomException
     */
    public void doLoadDelete(Image image) throws CustomException{
        
        try{
            
            imageDAO.doLoadDelete(image);
            
        }catch (DAOException dao){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Error getting data from DAO. Error is -"+dao.getLocalizedMessage(), dao);
        }catch (HibernateException he){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Hiberate error getting data from DAO. Error is -"+he.getLocalizedMessage(), he);
        }
    }
    
    
    /**
     * This method will update a specific Image object. It will also remove and write a new filesystem entry if the values are present
     * @param Image
     * @param String oldFileName
     * @throws CustomException
     */
    public void update(Image image, String oldFileName) throws CustomException{
        
        if(log.isDebugEnabled()){
            log.debug("Value for image in update is >"+image.toString());
        }
        
        try{
            imageDAO.update(image, oldFileName, hibSession);
        }catch (DAOException dao){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Error getting data from DAO. Error is -"+dao.getLocalizedMessage(), dao);
        }catch (HibernateException he){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Hiberate error getting data from DAO. Error is -"+he.getLocalizedMessage(), he);
        }
    }
}
