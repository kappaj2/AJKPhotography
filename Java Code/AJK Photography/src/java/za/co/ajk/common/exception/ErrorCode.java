/*
 * ErrorCode.java
 *
 * Created on May 22, 2007, 9:41 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.common.exception;

import java.io.Serializable;

/**
 *
 * @author andre.kapp
 */
public class ErrorCode  implements Serializable {
    
    public static ErrorCode DATABASE_EXCEPTION = new ErrorCode("DATABASE_EXCEPTION");
    public static ErrorCode DATA_ACCESS_ERROR = new ErrorCode("Error access data from DAO");
    
    public static ErrorCode FILE_DELETE_ERROR = new ErrorCode("Error deleting file from the file system");
    public static ErrorCode FILE_CREATE_ERROR = new ErrorCode("Error creating file from the file system");
    public static ErrorCode FILE_EXISTS_ERROR = new ErrorCode("Error creating file from the file system. File already exists.");
    public static ErrorCode FILE_LOAD_ERROR = new ErrorCode("Error loading file from the file system.");
    public static ErrorCode FILE_LLOCK_ERROR = new ErrorCode("Error manipulating file from the file system. File is currently write locked");
    
    public static ErrorCode OBJECT_NOT_FOUND = new ErrorCode("Object not found in the database.");
    public static ErrorCode SECURITY_SAVE_ERROR = new ErrorCode("Error saving security object to database.");
    
    public static ErrorCode SECURITY_RETRIEVE_ERROR = new ErrorCode("Error retrieving security object to database.");
    public static ErrorCode SECURITY_DECODE_ERROR = new ErrorCode("Error decoding password.");
    public static ErrorCode SECURITY_USERNAME_ERROR = new ErrorCode("Invalid username entered.");
    
    public static ErrorCode EXIF_ERROR = new ErrorCode("Error extracting Exif data from file.");    
    public static ErrorCode THUMBNAIL_ERROR = new ErrorCode("Error creating thumbnail image fir file.");    
    
    private String errorCode;
    
    private ErrorCode(String _errorCode) {
        this.errorCode = _errorCode;
    } // constructor
    
    
    /**
     * The accessor method for the String property errorCode.
     *
     * @return	String. the current value of the String property errorCode.
     */
    public String getErrorCode() {
        return errorCode;
    } // method getErrorCode
    
    /** Error codes are equal if they have the same message. */
    public boolean equals(Object obj) {
        if (!(obj instanceof ErrorCode)) return false;
        if (errorCode.compareTo(((ErrorCode)obj).getErrorCode())==0) return true;
        return false;
    }
    
} // class ErrorCode
