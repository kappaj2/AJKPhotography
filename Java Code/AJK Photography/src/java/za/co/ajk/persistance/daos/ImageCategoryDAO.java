/*
 * ImageCategoryDAO.java
 *
 * Created on 11 March 2008, 12:24
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.persistance.daos;

import java.io.Serializable;
import java.util.Set;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import za.co.ajk.common.exception.CustomException;
import za.co.ajk.common.exception.ErrorCode;
import za.co.ajk.common.exception.Layer;
import za.co.ajk.common.util.FileUtility;
import za.co.ajk.persistance.hibernate.dao.hibernate.AbstractHibernateDAO;
import za.co.ajk.persistance.transferobjects.Image;
import za.co.ajk.persistance.transferobjects.ImageCategory;
import za.co.ajk.persistance.transferobjects.ImageGallery;

/**
 *
 * @author akapp
 */
public class ImageCategoryDAO extends AbstractHibernateDAO {
    
    private static Logger log = Logger.getLogger(ImageCategoryDAO.class);
    
    private FileUtility fileUtility = new FileUtility();
    
    /**
     * This method will load an ImageCategory object from the database.
     * It uses the pk value to retrieve the unique object.
     * NOTE: The base class also enables a similar load method but with some different parameters. So be carefull!
     * @param Serializable
     * @return ImageCategory
     * @throws CustomException
     */
    public ImageCategory load(Serializable primaryKey) throws CustomException{
        
        log.debug("Received a load call in categoryDAO");
        
        ImageCategory imageCategory = (ImageCategory)super.load(ImageCategory.class, primaryKey);
        
        imageCategory.setCategoryImage(fileUtility.getFileData(FileUtility.FileType.CATEGORY, imageCategory.getCategoryImageUrl()));
        return imageCategory;
    }
    
    /**
     * This method will retrieve the file data in a byte[] from the filesystem
     * @param ImageCategory 
     * @return byte[]
     * @thorws CustomException
     */
    public byte[] getCategoryFileData(ImageCategory imageCategory) throws CustomException{
        
        byte[] imageData = fileUtility.getFileData(FileUtility.FileType.CATEGORY, imageCategory.getCategoryImageUrl());
        return imageData;
    }
    
    /**
     * This method will load an ImageCategory object and then delete it. this is done so that al the child objects are also deleted.
     * This method also deletes the files on the filesystem that is linked to objects in the chain.
     * @param ImageCategory
     * @throws CustomException
     */
    public void doLoadDelete(ImageCategory imageCategory) throws CustomException{
        
        log.debug("Received a load and delete action request !!!!");
        
        Session vHibernateSession = getSession();
        
        imageCategory = load(ImageCategory.class, imageCategory.getCategoryCode(), vHibernateSession);
        
        /*
         *  Now delete all the images and galleries where the category code is the same.
         */
        long categoryCode = imageCategory.getCategoryCode();
        
        Set<ImageGallery> imageGallerySet = imageCategory.getImageGalleries();
        for (ImageGallery elem : imageGallerySet) {
            log.debug("Delete gallery image");
            fileUtility.deleteFile(FileUtility.FileType.GALLERY, elem.getGalleryImageUrl());
            
            /*
             * now iterate through all the image associated with this gallery
             */
            Set<Image> images = elem.getImages();
            for (Image elem2 : images) {
                log.debug("Deleting image");
                fileUtility.deleteFile(FileUtility.FileType.IMAGE, elem2.getImageUrl());
            }
        }
        /*
         *  Now iterate through imageGallerySet
         */
        
        boolean success = fileUtility.deleteFile(FileUtility.FileType.CATEGORY, imageCategory.getCategoryImageUrl());
        
        log.debug("Value for file delete success is >"+success+"<");
        
        if (success){
            delete(imageCategory, vHibernateSession);
        }else{
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.PERSISTENCE, "Unable to delete file from the file system belonging to this category", null);
        }
        
        vHibernateSession.flush();
        vHibernateSession.clear();
        
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
    public void save(ImageCategory imageCategory) throws CustomException{
        
        fileUtility.writeFile(imageCategory.getCategoryImage(), FileUtility.FileType.CATEGORY, imageCategory.getCategoryImageUrl());
        
        /*
         *  Clear object data - data stored on FileSystem and not in the DB
         */
        imageCategory.setCategoryImage(new byte[0]);
        
        /*
         *  Persist the database stuff via super method.
         */
        super.save(imageCategory);
    }
    
    /**
     * This method will update an object in the DB as well as the file on the filesystem if included
     * @param ImageCategory
     * @param String oldFileName
     * @param Session 
     * @throws CustomException
     */
    public void update(ImageCategory imageCategory, String oldFileName, Session hibSession) throws CustomException{
        
        /*
         *  Delete old file if name is not null.
         *  The write new file data
         */
        if (oldFileName != null){
            fileUtility.deleteFile(FileUtility.FileType.CATEGORY, oldFileName);
            fileUtility.writeFile(imageCategory.getCategoryImage(), FileUtility.FileType.CATEGORY, imageCategory.getCategoryImageUrl());
        }
        
        /*
         *  Clear object data - data stored on FileSystem and not in the DB
         */
        imageCategory.setCategoryImage(new byte[0]);
        
        /*
         *  Persist the database stuff via super method.
         */
        super.update(imageCategory, hibSession);
    }
}
