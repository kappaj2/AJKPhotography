/*
 * ImageGalleryDAO.java
 *
 * Created on 11 March 2008, 03:25
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.persistance.daos;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import za.co.ajk.common.exception.CustomException;
import za.co.ajk.common.exception.ErrorCode;
import za.co.ajk.common.exception.Layer;
import za.co.ajk.common.util.FileUtility;
import za.co.ajk.persistance.hibernate.dao.hibernate.AbstractHibernateDAO;
import za.co.ajk.persistance.transferobjects.Image;
import za.co.ajk.persistance.transferobjects.ImageGallery;

/**
 *
 * @author akapp
 */
public class ImageGalleryDAO extends AbstractHibernateDAO {
    
    private static Logger log = Logger.getLogger(ImageGalleryDAO.class);
    private FileUtility fileUtility = new FileUtility();
    
    public ImageGallery load(Serializable primaryKey) throws CustomException{
        
        log.debug("Received a load call in categoryDAO");
        
        ImageGallery imageGallery = (ImageGallery)super.load(ImageGallery.class, primaryKey);
        
        imageGallery.setGalleryImage(fileUtility.getFileData(FileUtility.FileType.GALLERY, imageGallery.getGalleryImageUrl()));
        return imageGallery;
    }
    
    /**
     * This method will retrieve the file data in a byte[] from the filesystem
     * @param ImageGallery
     * @return byte[]
     * @thorws CustomException
     */
    public byte[] getGalleryFileData(ImageGallery imageGallery) throws CustomException{
        
        byte[] imageData = fileUtility.getFileData(FileUtility.FileType.GALLERY, imageGallery.getGalleryImageUrl());
        return imageData;
    }
    
    
    public void doLoadDelete(ImageGallery imageGallery) throws CustomException{
        
        
        Session vHibernateSession = getSession();
        
        imageGallery = load(ImageGallery.class, imageGallery.getComp_id(), vHibernateSession);
        
        /*
         *  Now iterate through the set of associated images and delete them first.
         *  Hibernate will delete them, but then the images on the file system is left with no link to them.
         */
        log.debug("Size for image set is >"+imageGallery.getImages().size());
        
        //String fileURL = "";
        //File file = null;
        boolean masterSuccess = true;
        boolean success = true;
        
        Set<Image> imageSet = imageGallery.getImages();
        for (Image elem : imageSet) {
            
            log.debug("----> Found image with url >"+elem.getImageUrl()+"<--------");
            /*
             *  Now just delete the file of the image - the rest will be handled by hibernate
             */
            success = fileUtility.deleteFile(FileUtility.FileType.IMAGE, elem.getImageUrl());
            
            log.debug("Result for deleting file "+success+"<");
            
            if (!success) masterSuccess = false;
        }
        
        /*
         *  Now remove the image for the gallery
         */
        log.debug("Now deleting imageGallery file");
        
        success = fileUtility.deleteFile(FileUtility.FileType.GALLERY,imageGallery.getGalleryImageUrl());
        
        if (!success) masterSuccess = false;
        
        if (masterSuccess){
            delete(imageGallery, vHibernateSession);
        }else{
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.PERSISTENCE, "Unable to delete file(s) from the file system that belongs to this Gallery", null);
        }
        
        vHibernateSession.flush();
        vHibernateSession.clear();
        
    }
    
    /**
     *  @PARAM IstayAdmin The object to delete
     *  @THROWS IstayException
     */
    public void doOneKeyDelete(ImageGallery imageGallery) throws CustomException{
        
        log.debug("Received a doOneKeyDelete call for ImageGallery");
        
        try{
            
            Session vHibernateSession = getSession();
            
            List<ImageGallery> imageGalleryList =
                    (List<ImageGallery>)findByQBE(ImageGallery.class,
                    imageGallery,
                    vHibernateSession);
            log.debug("Size for ImageGalleryList is >"+imageGalleryList.size()+"<");
            
            for (ImageGallery elem : imageGalleryList) {
                log.debug("Deleting with galleryCode >"+elem.getComp_id().getGalleryCode());
                delete(elem, vHibernateSession);
            }
            
            vHibernateSession.flush();
            vHibernateSession.clear();
            
        }catch (Exception ex){
            ex.printStackTrace();
            log.debug("Error doing oneKeyDelete", ex);
        }
    }
    
    /**
     *  @PARAM ImageGallery The object to delete
     *  @THROWS CustomException
     */
    public List<ImageGallery> findByColumn(Long categoryCode, Long galleryCode) throws CustomException{
        
        log.debug("Received a doOneKeyDelete call for ImageGallery");
        List<ImageGallery> imageGalleryList = null;
        try{
            
            Session vHibernateSession = getSession();
            
            imageGalleryList = findDataByColumn(ImageGallery.class,
                    "comp_id.categoryCode", categoryCode,
                    "comp_id.galleryCode", galleryCode,
                    vHibernateSession);
            
            log.debug("Size for ImageGalleryList is >"+imageGalleryList.size()+"<");
            
            vHibernateSession.flush();
            vHibernateSession.clear();
            
        }catch (Exception ex){
            ex.printStackTrace();
            log.debug("Error doing oneKeyDelete", ex);
        }
        
        return imageGalleryList;
    }
    
    /**
     * This method will do the following:
     * 1. Save the byte[] as a file on the file system
     * 2. Clean the byte[] in the DTO
     * 3. Save the database related data
     * It can be changed to save the byte[] in the DB, but proved to be a bit of a performance problem.
     * Will see if this works a bit better.
     * @param ImageCategory The imageCategory to save
     * @throws CustomException
     */
    public void save(ImageGallery imageGallery) throws CustomException{
        
        fileUtility.writeFile(imageGallery.getGalleryImage(), FileUtility.FileType.GALLERY, imageGallery.getGalleryImageUrl());
        
        /*
         *  Clear object data - data stored on FileSystem and not in the DB
         */
        imageGallery.setGalleryImage(new byte[0]);
        
        /*
         *  Persist the database stuff via super method.
         */
        super.save(imageGallery);
    }
    
    public void update(ImageGallery imageGallery, String oldFileName, Session hibSession) throws CustomException{
        
        /*
         *  Delete old file if name is not null.
         *  The write new file data
         */
        if (oldFileName != null){
            fileUtility.deleteFile(FileUtility.FileType.GALLERY, oldFileName);
            fileUtility.writeFile(imageGallery.getGalleryImage(), FileUtility.FileType.GALLERY, imageGallery.getGalleryImageUrl());
        }
        
        /*
         *  Clear object data - data stored on FileSystem and not in the DB
         */
        imageGallery.setGalleryImage(new byte[0]);
        
        /*
         *  Persist the database stuff via super method.
         */
        super.update(imageGallery, hibSession);
    }
}