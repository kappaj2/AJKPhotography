package za.co.ajk.persistance.transferobjects;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import za.co.ajk.persistance.hibernate.dao.DTO;


/** @author Hibernate CodeGenerator */
public class ImageGallery implements Serializable , DTO{
    
    /** identifier field */
    private za.co.ajk.persistance.transferobjects.ImageGalleryPK comp_id;
    
    /** persistent field */
    private String galleryDescription;
    
    /** persistent field */
    private String galleryImageUrl;
    
    /** nullable persistent field */
    private za.co.ajk.persistance.transferobjects.ImageCategory imageCategory;
    
    /** nullable persistent field */
    private byte[] galleryImage;
    
    /** persistent field */
    private Set images;
    
    /** full constructor */
    public ImageGallery(za.co.ajk.persistance.transferobjects.ImageGalleryPK comp_id, String galleryDescription, za.co.ajk.persistance.transferobjects.ImageCategory imageCategory, Set images) {
        this.comp_id = comp_id;
        this.galleryDescription = galleryDescription;
        this.imageCategory = imageCategory;
        this.images = images;
    }
    
    /** default constructor */
    public ImageGallery() {
    }
    
    /** minimal constructor */
    public ImageGallery(za.co.ajk.persistance.transferobjects.ImageGalleryPK comp_id, String galleryDescription, Set images) {
        this.comp_id = comp_id;
        this.galleryDescription = galleryDescription;
        this.images = images;
    }
    
    public za.co.ajk.persistance.transferobjects.ImageGalleryPK getComp_id() {
        return this.comp_id;
    }
    
    public void setComp_id(za.co.ajk.persistance.transferobjects.ImageGalleryPK comp_id) {
        this.comp_id = comp_id;
    }
    
    public String getGalleryDescription() {
        return this.galleryDescription;
    }
    
    public void setGalleryDescription(String galleryDescription) {
        this.galleryDescription = galleryDescription;
    }
    
    public za.co.ajk.persistance.transferobjects.ImageCategory getImageCategory() {
        return this.imageCategory;
    }
    
    public void setImageCategory(za.co.ajk.persistance.transferobjects.ImageCategory imageCategory) {
        this.imageCategory = imageCategory;
    }
    
    public byte[] getGalleryImage() {
        return this.galleryImage;
    }
    
    public void setGalleryImage(byte[] galleryImage) {
        this.galleryImage = galleryImage;
    }
    
    public Set getImages() {
        return this.images;
    }
    
    public void setImages(Set images) {
        this.images = images;
    }
    
    public String toString() {
        return new ToStringBuilder(this)
        .append("comp_id", getComp_id())
        .toString();
    }
    
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof ImageGallery) ) return false;
        ImageGallery castOther = (ImageGallery) other;
        return new EqualsBuilder()
        .append(this.getComp_id(), castOther.getComp_id())
        .isEquals();
    }
    
    public int hashCode() {
        return new HashCodeBuilder()
        .append(getComp_id())
        .toHashCode();
    }

    public String getGalleryImageUrl() {
        return galleryImageUrl;
    }

    public void setGalleryImageUrl(String galleryImageUrl) {
        this.galleryImageUrl = galleryImageUrl;
    }
    
}
