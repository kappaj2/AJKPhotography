/*
 * GalleryMaintenanceService.java
 *
 * Created on 12 March 2008, 08:22
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.service;

import java.io.Serializable;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import za.co.ajk.common.exception.CustomException;
import za.co.ajk.common.exception.ErrorCode;
import za.co.ajk.common.exception.Layer;
import za.co.ajk.persistance.daos.ImageGalleryDAO;
import za.co.ajk.persistance.hibernate.dao.DAOException;
import za.co.ajk.persistance.hibernate.dao.factory.AbstractDAOFactory;
import za.co.ajk.persistance.hibernate.dao.factory.DAOFactory;
import za.co.ajk.persistance.transferobjects.Image;
import za.co.ajk.persistance.transferobjects.ImageGallery;
import za.co.ajk.persistance.transferobjects.ImagePK;

/**
 * This class will be the interface to the relevant DAO's.
 * NOTE: Whenever a list of objects is returned, it will NOT have the image byte[] populated. This only happens on a specific
 * object load.
 *
 * @author akapp
 */
public class GalleryMaintenanceService {
    
    private DAOFactory imageGalleryFactory;
    private ImageGalleryDAO imageGalleryDAO;
    private Session hibSession;
    private ImageMaintenanceService imageMaintenanceService = new ImageMaintenanceService();
    
    private static Logger log = Logger.getLogger(CategoryMaintenanceService.class);
    
    /** Creates a new instance of GalleryMaintenanceService */
    public GalleryMaintenanceService() {
        
        imageGalleryFactory = AbstractDAOFactory.getFactory("ImageGallery");
        imageGalleryDAO = imageGalleryFactory.getDAO("ImageGallery");
        hibSession = imageGalleryDAO.getSession();
    }
    
    /**
     *  This method will return a list of objects by using the QBE feature of Hibernate. This seems to be buggy at best!!!!
     * @param ImageGalery
     * @return List<ImageGallery>
     * @throws CustomException
     */
    public List<ImageGallery> searchByQBE(ImageGallery imageGallery) throws CustomException {
        
        try{
            
            List galleryList = imageGalleryDAO.findByQBE(ImageGallery.class, imageGallery);
            if (log.isDebugEnabled()){
                log.debug("Size for ImageGalleries is >"+galleryList.size());
            }
            return galleryList;
            
        }catch (DAOException dao){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Error getting data from DAO. Error is -"+dao.getLocalizedMessage(), dao);
        }catch (HibernateException he){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Hiberate error getting data from DAO. Error is -"+he.getLocalizedMessage(), he);
        }
    }
    
    /**
     * This method will find all the gallery objects in the database.
     * @return List<ImageGallery>
     * @throws CustomException
     */
    public List<ImageGallery> findAll() throws CustomException {
        
        try{
            
            List<ImageGallery> galleryList = imageGalleryDAO.findAll(ImageGallery.class);
            if (log.isDebugEnabled()){
                log.debug("Size for ImageGalleries is >"+galleryList.size());
            }
            return galleryList;
            
        }catch (DAOException dao){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Error getting data from DAO. Error is -"+dao.getLocalizedMessage(), dao);
        }catch (HibernateException he){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Hiberate error getting data from DAO. Error is -"+he.getLocalizedMessage(), he);
        }
    }
    
    /**
     *  This method will load a specific object by using the PK value in the DB
     *  @param Serializable
     *  @return ImageGallery
     *  @throws CustomException
     */
    public ImageGallery load(Serializable pkValue) throws CustomException{
        
        try{
            
            ImageGallery imageGallery = imageGalleryDAO.load(ImageGallery.class, pkValue, hibSession);
            imageGallery.setGalleryImage(imageGalleryDAO.getGalleryFileData(imageGallery));
            return imageGallery;
            
        }catch (DAOException dao){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Error getting data from DAO. Error is -"+dao.getLocalizedMessage(), dao);
        }catch (HibernateException he){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Hiberate error getting data from DAO. Error is -"+he.getLocalizedMessage(), he);
        }
    }
    
    /**
     * This method will load a specific ImageGallery object from the database. The GalleryCode will be unique - it is generated by a DB sequence.
     * @param Serializable (Long)
     * @return ImageGallery (with image byte[] populated)
     * @throws CustomException
     */
    public ImageGallery loadByGalleryCode(Serializable pkValue) throws CustomException{
        
        try{
            
            List<ImageGallery> imageGalleryList = imageGalleryDAO.findDataByColumn(ImageGallery.class, "comp_id.galleryCode", pkValue);
            /*
             *  This should only return 1 object - the gallery code is generated by sequence in the DB
             */
            ImageGallery imageGallery = imageGalleryList.get(0);
            imageGallery.setGalleryImage(imageGalleryDAO.getGalleryFileData(imageGallery));
            return imageGallery;
            
        }catch (DAOException dao){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Error getting data from DAO. Error is -"+dao.getLocalizedMessage(), dao);
        }catch (HibernateException he){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Hiberate error getting data from DAO. Error is -"+he.getLocalizedMessage(), he);
        }
    }
    
    /**
     * This method will retrieve all the ImageGallery objects for a specific CategoryCode
     * @param Serializable(Long)
     * @return List<ImageGallery>
     * @throws CustomException
     */
    public List<ImageGallery> loadByCategoryCode(Serializable pkValue) throws CustomException{
        
        try{
            
            List<ImageGallery> imageGalleryList = imageGalleryDAO.findDataByColumn(ImageGallery.class, "comp_id.categoryCode", pkValue);
            return imageGalleryList;
            
        }catch (DAOException dao){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Error getting data from DAO. Error is -"+dao.getLocalizedMessage(), dao);
        }catch (HibernateException he){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Hiberate error getting data from DAO. Error is -"+he.getLocalizedMessage(), he);
        }
    }
    
    /**
     * This method will load a specific ImageGallery object by using the PK value in the DB.
     * NOTE: This creates a new session, so don't use for load/update functionality. Rather use the one where the session is passed to the DAO.
     * @param Serializable
     * @return ImageGallery
     * @throws GalleryException
     */
    public ImageGallery loadNoSession(Serializable pkValue) throws CustomException{
        
        try{
            
            ImageGallery imageGallery = imageGalleryDAO.load(pkValue);
            
            return imageGallery;
            
        }catch (DAOException dao){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Error getting data from DAO. Error is -"+dao.getLocalizedMessage(), dao);
        }catch (ObjectNotFoundException nfe){
            log.debug(" ++++++++++++++++++++++++++++++++++ object not found exception !!!!!!!!!!!11");
            throw new CustomException(ErrorCode.OBJECT_NOT_FOUND, Layer.SERVICE, "Hiberate error getting data from DAO. Error is -"+nfe.getLocalizedMessage(), nfe);
        }catch (HibernateException he){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Hiberate error getting data from DAO. Error is -"+he.getLocalizedMessage(), he);
        }
    }
    
    /**
     * This method will load a specific GallerImage object from the database. This will retrieve and clean all the child records as well.
     * @param ImageGallery
     * @throws CustomException
     */
    public void doLoadDelete(ImageGallery imageGallery) throws CustomException{
        
        try{
            /*
             *  Before we can delete the gallery, all the images in the gallery must first be deleted.
             */
            if (log.isDebugEnabled()){
                log.debug("Received a doLoadDelete in galleryMaintenanceService");
                log.debug("CategoryCode >"+imageGallery.getComp_id().getCategoryCode());
                log.debug("Gallerycode >"+imageGallery.getComp_id().getGalleryCode());
            }
            
            Image image = new Image();
            ImagePK imagePK = new ImagePK();
            imagePK.setCategoryCode(imageGallery.getComp_id().getCategoryCode());
            imagePK.setGalleryCode(imageGallery.getComp_id().getGalleryCode());
            
            image.setComp_id(imagePK);
            
            if (log.isDebugEnabled()){
                log.debug("Load the image gallery object");
            }
            
            List<ImageGallery> imageGalleryList =
                    imageGalleryDAO.findByColumn(imageGallery.getComp_id().getCategoryCode(), imageGallery.getComp_id().getGalleryCode());
            
            /*
             *  This should only return one record.
             */
            imageGallery = imageGalleryList.get(0);
            
            imageGalleryDAO.doLoadDelete(imageGallery);
            
        }catch (DAOException dao){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Error getting data from DAO. Error is -"+dao.getLocalizedMessage(), dao);
        }catch (HibernateException he){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Hiberate error getting data from DAO. Error is -"+he.getLocalizedMessage(), he);
        }
    }
    
    /**
     * This method will save a GalleryImage object to the database.
     * It will also save the image byte[] to the filesystem.
     * @param ImageGallery
     * @throws CustomException
     */
    public void save(ImageGallery imageGallery) throws CustomException{
        try{
            imageGalleryDAO.save(imageGallery);
        }catch (DAOException dao){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Error getting data from DAO. Error is -"+dao.getLocalizedMessage(), dao);
        }catch (HibernateException he){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Hiberate error getting data from DAO. Error is -"+he.getLocalizedMessage(), he);
        }
    }
    
    /**
     * This method will update the ImageGallery object int the database.
     * If the file infor is present, it will delete the old image and save the new image on the filesystem.
     * @param GalleryImage
     * @param String
     * @throws CustomException
     */
    public void update(ImageGallery imageGallery, String oldFileName) throws CustomException{
        
        if (log.isDebugEnabled()){
            log.debug("Value for imageGallery in update is >"+imageGallery.toString());
        }
        
        try{
            imageGalleryDAO.update(imageGallery, oldFileName, hibSession);
        }catch (DAOException dao){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Error getting data from DAO. Error is -"+dao.getLocalizedMessage(), dao);
        }catch (HibernateException he){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Hiberate error getting data from DAO. Error is -"+he.getLocalizedMessage(), he);
        }
    }
}
