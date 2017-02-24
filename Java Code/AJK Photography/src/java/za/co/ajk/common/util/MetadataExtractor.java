/*
 * MetadataExtractor.java
 *
 * Created on 08 April 2008, 03:05
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.common.util;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifDirectory;
import java.io.File;
import java.util.Iterator;
import org.apache.log4j.Logger;
import za.co.ajk.common.exception.CustomException;
import za.co.ajk.common.exception.ErrorCode;
import za.co.ajk.common.exception.Layer;

/**
 *
 * @author akapp
 */
public class MetadataExtractor {
    
    private File jpegFile;
    private String fileName;
    
    private String imageHeight = "";
    private String imageWidth = "";
    private String cameraMake = "";
    private String cameraModel = "";
    private String cameraSerialNumber = "";
    
    public MetadataExtractor(){
    }
    
    public void setFileName(String fileName){
        this.fileName = fileName;
        extractData();
    }
    
    public void setJpegFile(File jpegfile) throws CustomException{
        this.jpegFile = jpegFile;
        extractData();
    }
    
    private static Logger log = Logger.getLogger(MetadataExtractor.class);
    
    /**
     *  This method will extract the exif data from the file and populate the relevant data fields
     * @throws CustomException
     */
    private void extractData() throws CustomException{
        
        try{
            log.debug("Trying to get the file information for file >"+fileName+"<");
            File file = new File(fileName);
            log.debug("Extract pos 1");
            
            Metadata metadata = JpegMetadataReader.readMetadata(file);
            
            log.debug("Extract pos 2");
            Iterator directories = metadata.getDirectoryIterator();
            
            while (directories.hasNext()) {
                log.debug("Extract pos 3");
                
                Directory directory = (Directory)directories.next();
                Iterator tags = directory.getTagIterator();
                while (tags.hasNext()) {
                    //log.debug("Extract pos 4");
                    
                    Tag tag = (Tag)tags.next();
                    
                    if (tag.getTagType() == 12){ // camera serial
                        setCameraSerialNumber(tag.getDescription());
                    }
                    if (tag.getTagType() == 272){ // camera model
                        setCameraModel(tag.getDescription());
                    }
                    if (tag.getTagType() == 271){ // camera make
                        setCameraMake(tag.getDescription());
                    }
                }
            }
            
            Directory exifDirectory = metadata.getDirectory(ExifDirectory.class);
            setImageHeight(exifDirectory.getString(ExifDirectory.TAG_EXIF_IMAGE_HEIGHT));
            setImageWidth(exifDirectory.getString(ExifDirectory.TAG_EXIF_IMAGE_WIDTH));
            
        }catch (Exception ex){
            throw new CustomException(ErrorCode.EXIF_ERROR, Layer.COMMON, "Error extracting exif data ."+ex.getMessage(), ex);
        }
    }
    
//    public static void main(String[] args){
//        try{
//            File jpegFile = new File("c://BackgroundImages/0006.jpg");
//            Metadata metadata = JpegMetadataReader.readMetadata(jpegFile);
//
//            // iterate through metadata directories
//            Iterator directories = metadata.getDirectoryIterator();
//
//            String imageWidth = "";
//            String imageHeight = "";
//            String lens = "";
//            String serial = "";
//
//
//            while (directories.hasNext()) {
//                Directory directory = (Directory)directories.next();
//                Iterator tags = directory.getTagIterator();
//                while (tags.hasNext()) {
//                    Tag tag = (Tag)tags.next();
//                    System.out.println("============");
//                    System.out.println("Tag description is >"+tag.getDescription());
//                    System.out.println("Tag name >"+tag.getTagName());
//                    System.out.println("Tag type >"+tag.getTagType());
//                }
//            }
//
//            // tag type
//            // 271 camera make
//            // 272 camera model
//            // 3 imageWidth
//            // 1 imageHeight
//            // 149 lens used
//            // 12 Camera Serial number
//            // 9 owner name
//
////            Directory exifDirectory = metadata.getDirectory(ExifDirectory.class);
////
////            String imageHeight = exifDirectory.getString(ExifDirectory.TAG_EXIF_IMAGE_HEIGHT);
////            String imageWidth = exifDirectory.getString(ExifDirectory.TAG_EXIF_IMAGE_WIDTH);
////            String cameraSerial = exifDirectory.getString(ExifDirectory.TAG_IMAGE_NUMBER);
////
////            System.out.println(ExifDirectory.TAG_EXIF_IMAGE_WIDTH);
////            System.out.println("Size for declared fields is >"+ExifDirectory.class.getDeclaredFields().length);
////            Field[] fields = ExifDirectory.class.getDeclaredFields();
////            for (int i = 0; i < fields.length; i++) {
////                Field f = fields[i];
////                System.out.println("Field name is >"+f.getName());
////
////            }
//
//            System.out.println("Value for imageHeight is >"+imageHeight+"<");
//            System.out.println("Value for imageWidth is >"+imageWidth+"<");
//            System.out.println("Value for cameraSerial is >"+serial+"<");
//            System.out.println("Value for lens is >"+lens+"<");
//
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//
//    }
    
    public File getJpegFile() {
        return jpegFile;
    }
    
    public String getImageHeight() {
        return imageHeight;
    }
    
    private void setImageHeight(String imageHeight) {
        this.imageHeight = imageHeight;
    }
    
    public String getImageWidth() {
        return imageWidth;
    }
    
    private void setImageWidth(String imageWidth) {
        this.imageWidth = imageWidth;
    }
    
    public String getCameraMake() {
        return cameraMake;
    }
    
    private void setCameraMake(String cameraMake) {
        this.cameraMake = cameraMake;
    }
    
    public String getCameraModel() {
        return cameraModel;
    }
    
    private void setCameraModel(String cameraModel) {
        this.cameraModel = cameraModel;
    }
    
    public String getCameraSerialNumber() {
        return cameraSerialNumber;
    }
    
    private void setCameraSerialNumber(String cameraSerialNumber) {
        this.cameraSerialNumber = cameraSerialNumber;
    }
}
