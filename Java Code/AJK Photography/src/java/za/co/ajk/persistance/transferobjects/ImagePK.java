package za.co.ajk.persistance.transferobjects;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import za.co.ajk.persistance.hibernate.dao.DTO;


/** @author Hibernate CodeGenerator */
public class ImagePK implements Serializable , DTO{
    
    /** identifier field */
    private Long imageCode;
    
    /** identifier field */
    private Long categoryCode;
    
    /** identifier field */
    private Long galleryCode;
    
    /** full constructor */
    public ImagePK(Long imageCode, Long categoryCode, Long galleryCode) {
        this.imageCode = imageCode;
        this.categoryCode = categoryCode;
        this.galleryCode = galleryCode;
    }
    
    /** default constructor */
    public ImagePK() {
    }
    
    public Long getImageCode() {
        return this.imageCode;
    }
    
    public void setImageCode(Long imageCode) {
        this.imageCode = imageCode;
    }
    
    public Long getCategoryCode() {
        return this.categoryCode;
    }
    
    public void setCategoryCode(Long categoryCode) {
        this.categoryCode = categoryCode;
    }
    
    public Long getGalleryCode() {
        return this.galleryCode;
    }
    
    public void setGalleryCode(Long galleryCode) {
        this.galleryCode = galleryCode;
    }
    
    public String toString() {
        return new ToStringBuilder(this)
        .append("imageCode", getImageCode())
        .append("categoryCode", getCategoryCode())
        .append("galleryCode", getGalleryCode())
        .toString();
    }
    
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof ImagePK) ) return false;
        ImagePK castOther = (ImagePK) other;
        return new EqualsBuilder()
        .append(this.getImageCode(), castOther.getImageCode())
        .append(this.getCategoryCode(), castOther.getCategoryCode())
        .append(this.getGalleryCode(), castOther.getGalleryCode())
        .isEquals();
    }
    
    public int hashCode() {
        return new HashCodeBuilder()
        .append(getImageCode())
        .append(getCategoryCode())
        .append(getGalleryCode())
        .toHashCode();
    }
    
}
