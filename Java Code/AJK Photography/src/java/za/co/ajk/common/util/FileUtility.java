/*
 * FileUtility.java
 *
 * Created on 14 March 2008, 09:50
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import org.apache.log4j.Logger;
import za.co.ajk.common.exception.CustomException;
import za.co.ajk.common.exception.ErrorCode;
import za.co.ajk.common.exception.Layer;
import za.co.ajk.persistance.transferobjects.AppProp;
import za.co.ajk.service.AppPropMaintenanceService;

/**
 *
 * @author akapp
 * This class will handle all the file related actions.
 *
 * When an image is saved to the filesystem, it is sized after the save to the required size.
 * For category and gallery images, no "big" version will be saved.
 * For images - there will be two files - one thumbnail and one full-size.
 *
 */
public class FileUtility {
    
    private static final String CATEGORY_IMAGE_PATH  = "/opt/appdocs/images/category/";
    private static final String GALLERY_IMAGE_PATH   = "/opt/appdocs/images/gallery/";
    private static final String IMAGE_PATH           = "/opt/appdocs/images/image/";
    private static final String LOGO_PATH            = "/opt/appdocs/images/logo/";
    private static final String STYLESHEET_PATH      = "/opt/appdocs/css/";
    
    public enum FileType{CATEGORY, GALLERY, IMAGE, LOGO, CSS};
    
    private static Logger log = Logger.getLogger(FileUtility.class);
    
    /**
     *  This method will build all the required directories for storing the images
     *  There are currently three directories that are needed.
     */
    public void buildDirectoryStructure() throws CustomException{
        try{
            
            File f = new File(CATEGORY_IMAGE_PATH);
            if (!f.exists()){
                f.mkdirs();
            }
            
            f = new File(GALLERY_IMAGE_PATH);
            if (!f.exists()){
                f.mkdirs();
            }
            
            f = new File(IMAGE_PATH);
            if (!f.exists()){
                f.mkdirs();
            }
            
            f = new File(LOGO_PATH);
            if (!f.exists()){
                f.mkdirs();
            }
            
            f = new File(STYLESHEET_PATH);
            if (!f.exists()){
                f.mkdirs();
            }
            
        }catch (SecurityException se){
            throw new CustomException(ErrorCode.FILE_CREATE_ERROR, Layer.PERSISTENCE, "Unable to save file to the file system. "+se.getMessage(), se);
        }
    }
    /**
     *  This method will determine the appropriate directory and will then write a file to that directoy with the data provided.
     *  It will throw and exception of the file cannot be written.
     *
     * @param byte[] The file data to write to the file system
     * @param FileType Determine the file directory where it must reside
     * @param String fileName of the file to create.
     */
    public void writeFile(byte[] fileData, FileType fileType, String fileName) throws CustomException{
        
        log.debug("received a fileWrite call with filename >"+fileName+"< and fileType >"+fileType.toString());
        
        String fileURL = buildFileName(fileType, fileName);
        
        try{
            
            File file = new File(fileURL);
            log.debug("Writing file >"+file.getAbsolutePath()+"<");
            
            if (file.exists()){
                log.debug("++++++++++++++ File already exists!!!!");
                throw new CustomException(ErrorCode.FILE_EXISTS_ERROR, Layer.PERSISTENCE, "Unable to save file to the file system. File already exists on the filesystem. ", null);
            }
            
            FileChannel fc =
                    new FileOutputStream(file).getChannel();
            
            fc.write(ByteBuffer.wrap(fileData));
            
            fc.close();

            
            /*
             * If it is an image file - write a large one as well and size appropriately
             */
            if(fileType == FileType.IMAGE){

                String targetURL = fileURL.replace(".", "_large.");
                            
                File largeFile = new File(targetURL);
                
                log.debug("Writing large file >"+largeFile.getAbsolutePath()+"<");

//                if (largeFile.exists()){
//                    log.debug("++++++++++++++ File already exists!!!!");
//                    throw new CustomException(ErrorCode.FILE_EXISTS_ERROR, Layer.PERSISTENCE, "Unable to save file to the file system. File already exists on the filesystem. ", null);
//                }

                FileChannel fcLarge =
                        new FileOutputStream(largeFile).getChannel();

                fcLarge.write(ByteBuffer.wrap(fileData));

                fcLarge.close();
            }

            /*
             * Now resize
             */
            sizeImages(fileType, file, fileURL);

            
        }catch (FileNotFoundException fnf){
            fnf.printStackTrace();
            throw new CustomException(ErrorCode.FILE_CREATE_ERROR, Layer.PERSISTENCE, "Unable to save file to the file system. "+fnf.getMessage(), fnf);
        }catch (IOException ioe){
            ioe.printStackTrace();
            throw new CustomException(ErrorCode.FILE_CREATE_ERROR, Layer.PERSISTENCE, "Unable to save file to the file system. "+ioe.getMessage(), ioe);
        }
    }
    
    /**
     * The method will delete the file from the filesystem
     * @param FileType fileType
     * @param String fileName
     * @throws CustomException
     */
    public boolean deleteFile(FileType fileType, String fileName) throws CustomException{
        
        String fileURL = buildFileName(fileType, fileName);
        
        try{
            
            File file = new File(fileURL);
            
            if (!file.exists()){
                log.debug("++++++++++++++ File already exists!!!!");
                throw new CustomException(ErrorCode.FILE_EXISTS_ERROR, Layer.PERSISTENCE, "Unable to delete file >"+fileURL+" on the file system. File does not exists.", null);
            }
            
            if (!file.canWrite()){
                throw new CustomException(ErrorCode.FILE_DELETE_ERROR, Layer.PERSISTENCE, "Unable to delete file >"+fileURL+" on the file system", null);
            }
            boolean success = file.delete();
            
            if (!success){
                throw new CustomException(ErrorCode.FILE_DELETE_ERROR, Layer.PERSISTENCE, "Unable to delete file >"+fileURL+" on the file system", null);
            }
            
            return true;
            
        }catch (Exception ex){
            throw new CustomException(ErrorCode.FILE_DELETE_ERROR, Layer.PERSISTENCE, "Unable to delete file >"+fileURL+" on the file system", ex);
        }
        
    }
    
    /**
     * This method will retrieve the file from the filesystem and return it as a byte array. The fileType determines the
     * appropriate folder to be used.
     *
     * @param FileType fileType
     * @param String fileName
     * @return byte[]
     * @throws CustomException
     */
    public byte[] getFileData(FileType fileType, String fileName) throws CustomException{
        
        String fileURL = buildFileName(fileType, fileName);
        log.debug("Getting file data for file >"+fileURL+"<");
        
        try{
            FileChannel fc = new FileInputStream(fileURL).getChannel();
            long fileSize = fc.size();
            log.debug("Size for file is >"+fileSize+"<");
            
            ByteBuffer buff = ByteBuffer.allocate((int)fileSize);
            int numBytes = fc.read(buff);
            
            fc.close();
            log.debug("Returning byte[] of size >"+buff.array().length+"< and numByte >"+numBytes+"<");
            return buff.array();
            
        }catch (Exception ex){
            log.debug("Error getting fileData ", ex);
            throw new CustomException(ErrorCode.FILE_LOAD_ERROR, Layer.PERSISTENCE, "Unable to load file >"+fileURL+" from the file system", ex);
        }
    }
    
    /**
     *  Thios method will return a list of files  found in the specific directory
     * @param FileType Determine where to look fir the files
     * @return String[]
     * @throws CustomException
     */
    public String[] getFileListing(FileType fileType) throws CustomException{
        String fileURL = buildFileName(fileType, null);
        File file = new File(fileURL);
        String[] files = file.list();
        return files;
    }
    
    /**
     * This method will read the system properties table and extract the value for the CSS attribute.
     * It will then open that source file and write the contents into the ajk.css (target) file.
     * The ajk.css is the stylesheet used by the applications, so overwriting it with new data gives you the
     * ability to change the look and feel of the site.
     * @throws CustomException
     */
    public void copyStylesheetToActive() throws CustomException{
        try{
            log.debug("Doing copying of stylesheets to active");
            
            AppProp appProp = getNewStylesheetName();
            
            log.debug("Value for default is >"+appProp.getPropertyValueDefault()+"< and new value =>"+appProp.getPropertyValue()+"<");
            
            if (!appProp.getPropertyValue().equals(appProp.getPropertyValueDefault())){
                
                String sourceFile = buildFileName(FileType.CSS, appProp.getPropertyValue());
                
                java.net.URL targetURL = getClass().getResource("/css/ajk.css");
                
                log.debug("Value for targetURL is >"+targetURL.toString());
                
                FileChannel
                        in = new FileInputStream(sourceFile).getChannel(),
                        out = new FileOutputStream(targetURL.getFile()).getChannel();
                in.transferTo(0, in.size(), out);
                in.close();
                out.close();
            }
            
        }catch (FileNotFoundException fne){
            log.debug("Error copying data between the two stylesheets. File not found error. ", fne);
            throw new CustomException(ErrorCode.FILE_LOAD_ERROR, Layer.PERSISTENCE, "Unable to copy file new stylesheet from the file system. ", fne);
        }catch (IOException ioe){
            log.debug("Error getting fileData ", ioe);
            throw new CustomException(ErrorCode.FILE_LOAD_ERROR, Layer.PERSISTENCE, "Unable to copy file new stylesheet from the file system. ", ioe);
        }
    }
    
    /**
     * This method will look for the new stylesheet value in the application properties table.
     * If the default and the new value is the same, don't do anything.
     * @return AppProp
     *
     */
    private AppProp getNewStylesheetName(){
        AppPropMaintenanceService appPropMaintenanceService = new AppPropMaintenanceService();
        AppProp appProp = appPropMaintenanceService.findProp("css");
        return appProp;
    }
    
    /**
     *  This method will build up a string representation of the filename
     * @param FileType fileType
     * @param String fileName
     * @return String
     */
    private String buildFileName(FileType fileType, String fileName){
        String fileURL = new String();
        switch (fileType){
            case CATEGORY:
                fileURL = CATEGORY_IMAGE_PATH + fileName;
                break;
            case GALLERY:
                fileURL = GALLERY_IMAGE_PATH + fileName;
                break;
            case IMAGE:
                fileURL = IMAGE_PATH + fileName;
                break;
            case LOGO:
                fileURL = LOGO_PATH + fileName;
                break;
            case CSS:
                fileURL = STYLESHEET_PATH + fileName;
                break;
            default:
                break;
        }
        return fileURL;
    }


    /**
     *
     * Resize the file that was written to the FS.
     * @param fileType
     * @param file
     * @param fileURL
     * @throws za.co.ajk.common.exception.CustomException
     */
    private void sizeImages(FileType fileType, File file, String fileURL) throws CustomException{
        AppPropMaintenanceService appPropMaintenanceService = new AppPropMaintenanceService();
        
        if (fileType == FileType.CATEGORY || fileType == FileType.GALLERY){
            log.debug("Doing filesize pos for category and gallery types");
            
            AppProp appProp = new AppProp();
            String targetSize = "";
            
            if (fileType == FileType.CATEGORY){
                appProp = appPropMaintenanceService.load("categories_height_size");
            }else{
                appProp = appPropMaintenanceService.load("galleries_height_size");
            }
            targetSize = appPropMaintenanceService.getCorrectValue(appProp);
            
            /*
             *  Build thumbnail image object handler
             */
            MetadataExtractor metadataExtractor = new MetadataExtractor();
            metadataExtractor.setFileName(fileURL);
            
            String imageHeight = metadataExtractor.getImageHeight();
            String imageWidth  = metadataExtractor.getImageWidth();
            
            ThumbNail thumbNail = new ThumbNail();
            
            /*
             * Set manipulation parameters to control behaviour
             */
            thumbNail.setSourceFileName(fileURL);
            thumbNail.setTargetFileName(fileURL);
            thumbNail.setImageHeight(imageHeight);
            thumbNail.setImageWidth(imageWidth);
            thumbNail.setMaxSize(targetSize);
            
            /*
             * now resize source into target file
             */
            log.debug("Doing fileSize pos 7");
            thumbNail.resize();
            log.debug("Doing fileSize pos 8");
            
        }else if (fileType == FileType.IMAGE){
            
            AppProp appProp = new AppProp();
            String targetSize = "";
            appProp = appPropMaintenanceService.load("images_height_size");
            targetSize = appPropMaintenanceService.getCorrectValue(appProp);
            
            String largeTargetSize = "";
            appProp = appPropMaintenanceService.load("images_large_height_size");
            largeTargetSize = appPropMaintenanceService.getCorrectValue(appProp);
            
            log.debug("Retrieved props from db with targetSize >"+targetSize+"< and largetTargetSize >"+largeTargetSize+"<");
            /*
             * build thumbnail and full-size
             */
            log.debug("Doing filesize pos for image types");
            
            /*
             *  Build thumbnail image object handler
             */
            MetadataExtractor metadataExtractor = new MetadataExtractor();
            metadataExtractor.setFileName(fileURL);
            
            String imageHeight = metadataExtractor.getImageHeight();
            String imageWidth  = metadataExtractor.getImageWidth();
            
            ThumbNail thumbNail = new ThumbNail();
            
            /*
             * Set manipulation parameters to control behaviour
             */
            thumbNail.setSourceFileName(fileURL);
            thumbNail.setTargetFileName(fileURL);
            thumbNail.setImageHeight(imageHeight);
            thumbNail.setImageWidth(imageWidth);
            thumbNail.setMaxSize(targetSize);
            /*
             * now resize source into target file
             */
            thumbNail.resize();
            
            /*
             * Now also size one for the big images display
             * The file name now appends _large to the image name
             */
            
            String targetURL = fileURL.replace(".", "_large.");
            
            log.debug("Building image for targetsource name >"+targetURL+"<");
            thumbNail.setSourceFileName(fileURL);
            thumbNail.setTargetFileName(targetURL);
            thumbNail.setImageHeight(imageHeight);
            thumbNail.setImageWidth(imageWidth);
            thumbNail.setMaxSize(largeTargetSize);
            /*
             * now resize source into target file
             */
            thumbNail.resize();
            

            log.debug("Finished resizing for large image version");
        }
    }
}
