/*
 * FileValidationRules.java
 *
 * Created on 25 March 2008, 11:01
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.business.rules;

import java.util.Properties;
import org.apache.commons.beanutils.PropertyUtils;

/**
 *
 * @author akapp
 * This class will be used to handle file validation rules.
 */
public class FileValidationRules {
    
    public static final int IMAGE_FILE_SIZE_MAX = 5*1024*1024; // 5 meg max
    
    public void test(){
        
        PropertyUtils pp = new PropertyUtils();
       
        Properties prop = new Properties();
    }
    
    /**
     *  This method will check that the file size is not more than the maximum size allowed.
     * @param int filesize in bytes!
     * @return boolean True if file size is within limits
     */
    public boolean checkImageFileSize(int fileSize){
        if (fileSize < IMAGE_FILE_SIZE_MAX){
            return true;
        }
        return false;
        
    }
    
    /**
     *  This method will check the filename for invalid characters, ets.
     * @param String fileName
     * @return boolean
     */
    public boolean checkImageFileName(String fileName) {
        int pos = fileName.indexOf("-");
        if (pos != -1){
            return false;
        }
        return true;
    }
    
    /**
     *  This method wil check for the fileContent that is uploaded.
     * @param String content description
     * @return boolean
     */
    public boolean checkImageFileContent(String contentType){
        if (!contentType.equals("image/pjpeg") &&
                !contentType.equals("image/jpeg") &&
                !contentType.equals("application/octet-stream")){
            return false;
        }
        return true;
    }
}
