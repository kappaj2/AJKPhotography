package za.co.ajk.persistance.transferobjects;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import za.co.ajk.persistance.hibernate.dao.DTO;


/** @author Hibernate CodeGenerator */
public class ImageGalleryPK implements Serializable, DTO {
    
    /** identifier field */
    private Long galleryCode;
    
    /** identifier field */
    private Long categoryCode;
    
    /** full constructor */
    public ImageGalleryPK(Long galleryCode, Long categoryCode) {
        this.galleryCode = galleryCode;
        this.categoryCode = categoryCode;
    }
    
    /** default constructor */
    public ImageGalleryPK() {
    }
    
    public Long getGalleryCode() {
        return this.galleryCode;
    }
    
    public void setGalleryCode(Long galleryCode) {
        this.galleryCode = galleryCode;
    }
    
    public Long getCategoryCode() {
        return this.categoryCode;
    }
    
    public void setCategoryCode(Long categoryCode) {
        this.categoryCode = categoryCode;
    }
    
    public String toString() {
        return new ToStringBuilder(this)
        .append("galleryCode", getGalleryCode())
        .append("categoryCode", getCategoryCode())
        .toString();
    }
    
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof ImageGalleryPK) ) return false;
        ImageGalleryPK castOther = (ImageGalleryPK) other;
        return new EqualsBuilder()
        .append(this.getGalleryCode(), castOther.getGalleryCode())
        .append(this.getCategoryCode(), castOther.getCategoryCode())
        .isEquals();
    }
    
    public int hashCode() {
        return new HashCodeBuilder()
        .append(getGalleryCode())
        .append(getCategoryCode())
        .toHashCode();
    }
    
}
