package za.co.ajk.persistance.transferobjects;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import za.co.ajk.persistance.hibernate.dao.DTO;


/** @author Hibernate CodeGenerator */
public class OnlineDocument implements Serializable , DTO{
    
    /** identifier field */
    private String documentName;
    
    /** persistent field */
    private String documentType;
    
    /** persistent field */
    private String mimeType;
    
    /** persistent field */
    private String fileSize;
    
    /** persistent field */
    private String documentUrl;
    
    /** nullable persistent field */
    private String shortDescription;
    
    /** nullable persistent field */
    private byte[] documentData;
    
    /** full constructor */
    public OnlineDocument(String documentType, String mimeType, String fileSize, String documentUrl, String shortDescription, byte[] documentData) {
        this.documentType = documentType;
        this.mimeType = mimeType;
        this.fileSize = fileSize;
        this.documentUrl = documentUrl;
        this.shortDescription = shortDescription;
        this.documentData = documentData;
    }
    
    /** default constructor */
    public OnlineDocument() {
    }
    
    /** minimal constructor */
    public OnlineDocument(String documentType, String mimeType, String fileSize, String documentUrl) {
        this.documentType = documentType;
        this.mimeType = mimeType;
        this.fileSize = fileSize;
        this.documentUrl = documentUrl;
    }
    
    public String getDocumentName() {
        return this.documentName;
    }
    
    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }
    
    public String getDocumentType() {
        return this.documentType;
    }
    
    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }
    
    public String getMimeType() {
        return this.mimeType;
    }
    
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
    
    public String getFileSize() {
        return this.fileSize;
    }
    
    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }
    
    public String getDocumentUrl() {
        return this.documentUrl;
    }
    
    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }
    
    public String getShortDescription() {
        return this.shortDescription;
    }
    
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }
    
    public byte[] getDocumentData() {
        return this.documentData;
    }
    
    public void setDocumentData(byte[] documentData) {
        this.documentData = documentData;
    }
    
    public String toString() {
        return new ToStringBuilder(this)
        .append("documentName", getDocumentName())
        .toString();
    }
    
}
