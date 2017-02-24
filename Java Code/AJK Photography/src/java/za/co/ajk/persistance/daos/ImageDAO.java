/*
 * ImageDAO.java
 *
 * Created on 12 March 2008, 08:38
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.persistance.daos;

import java.io.Serializable;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import za.co.ajk.common.exception.CustomException;
import za.co.ajk.common.exception.ErrorCode;
import za.co.ajk.common.exception.Layer;
import za.co.ajk.common.util.FileUtility;
import za.co.ajk.persistance.hibernate.dao.hibernate.AbstractHibernateDAO;
import za.co.ajk.persistance.transferobjects.Image;


/**
 *
 * @author akapp
 */
public class ImageDAO extends AbstractHibernateDAO {
    
    private static Logger log = Logger.getLogger(ImageDAO.class);
    
    private FileUtility fileUtility = new FileUtility();
    
    public Image load(Serializable primaryKey) throws CustomException{
        
        log.debug("Received a load call in imageDAO");
        
        Image image = (Image)super.load(Image.class, primaryKey);
        
        image.setImageData(fileUtility.getFileData(FileUtility.FileType.IMAGE, image.getImageUrl()));
        return image;
        
    }
    
    
    /**
     * This method will retrieve the file data in a byte[] from the filesystem
     * @param ImageGallery
     * @return byte[]
     * @thorws CustomException
     */
    public byte[] getImageFileData(Image image, boolean largeVersion) throws CustomException{
        
        String imageName = image.getImageUrl();
        if (largeVersion){
            imageName = imageName.replace(".", "_large.");
        }
        log.debug("Loading image data for filename >"+imageName+"<");
        
        byte[] imageData = fileUtility.getFileData(FileUtility.FileType.IMAGE, imageName);
        return imageData;
    }
    
    
    public void doLoadDelete(Image image) throws CustomException{
        
        
        Session vHibernateSession = getSession();
        
        image = load(Image.class, image.getComp_id(), vHibernateSession);
        
        boolean success = fileUtility.deleteFile(FileUtility.FileType.IMAGE, image.getImageUrl());
        
        if (success){
            delete(image, vHibernateSession);
        }else{
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.PERSISTENCE, "Unable to delete file from the file system", null);
        }
        
        vHibernateSession.flush();
        vHibernateSession.clear();
        
    }
    
    /**
     *  @PARAM GalleryImage The object to delete
     *  @THROWS CustomException
     */
    public void doOneKeyDelete(Image image) throws CustomException{
        
        log.debug("Received a doOneKeyDelete call for Image");
        
        try{
            
            log.debug("CategoryCode >"+image.getComp_id().getCategoryCode());
            log.debug("Gallerycode >"+image.getComp_id().getGalleryCode());
            log.debug("ImageCode >"+image.getComp_id().getImageCode());
            
            Session vHibernateSession = getSession();
            
            log.debug("Value for pk to string is >"+image.getComp_id().toString());
            
            List<Image> imageList = findDataByColumn(Image.class,
                    "comp_id.categoryCode", image.getComp_id().getCategoryCode(),
                    "comp_id.galleryCode", image.getComp_id().getGalleryCode(),
                    vHibernateSession);
            
//                    (List<GalleryImage>)findByQBE(GalleryImage.class,
//                    galleryImage,
//                    vHibernateSession);
//
            log.debug("Size for imageList is >"+imageList.size()+"<");
            
            for (Image elem : imageList) {
                //log.debug("Deleting with id >"+elem.getId());
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
     *  @PARAM GalleryImage The object to delete
     *  @THROWS IstayException
     */
    public List<Image> findByColumn(Long categoryCode, Long galleryCode) throws CustomException{
        
        log.debug("Received a findByColumn call for Image");
        List<Image> imageList = null;
        try{
            
            Session vHibernateSession = getSession();
            
            imageList = findDataByColumn(Image.class,
                    "comp_id.categoryCode", categoryCode,
                    "comp_id.galleryCode", galleryCode,
                    vHibernateSession);
            
            log.debug("Size for imageList is >"+imageList.size()+"<");
            
            vHibernateSession.flush();
            vHibernateSession.clear();
            
        }catch (Exception ex){
            ex.printStackTrace();
            log.debug("Error doing findByColumn", ex);
        }
        
        return imageList;
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
    public void save(Image image) throws CustomException{
        
        fileUtility.writeFile(image.getImageData(), FileUtility.FileType.IMAGE, image.getImageUrl());
        
        /*
         *  Clear object data - data stored on FileSystem and not in the DB
         */
        image.setImageData(new byte[0]);
        /*
         *  Persist the database stuff via super method.
         */
        super.save(image);
    }
    
    public void update(Image image, String oldFileName, Session hibSession) throws CustomException{
        
        /*
         *  Delete old file if name is not null.
         *  The write new file data
         */
        if (oldFileName != null){
            fileUtility.deleteFile(FileUtility.FileType.IMAGE, oldFileName);
            fileUtility.writeFile(image.getImageData(), FileUtility.FileType.IMAGE, image.getImageUrl());
        }
        
        /*
         *  Clear object data - data stored on FileSystem and not in the DB
         */
        image.setImageData(new byte[0]);
        
        /*
         *  Persist the database stuff via super method.
         */
        super.update(image, hibSession);
    }
}
