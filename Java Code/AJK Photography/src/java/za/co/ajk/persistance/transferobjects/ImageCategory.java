package za.co.ajk.persistance.transferobjects;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;
import za.co.ajk.persistance.hibernate.dao.DTO;


/** @author Hibernate CodeGenerator */
public class ImageCategory implements Serializable, DTO {
    
    /** identifier field */
    private Long categoryCode;
    
    /** persistent field */
    private String categoryDescription;
    
    /** persistent field */
    private String categoryImageUrl;    
    
    /** nullable persistent field */
    private byte[] categoryImage;
    
    /** persistent field */
    private Set imageGalleries;
    
    /** full constructor */
    public ImageCategory(String categoryDescription, byte[] categoryImage, Set imageGalleries) {
        this.categoryDescription = categoryDescription;
        this.categoryImage = categoryImage;
        this.imageGalleries = imageGalleries;
    }
    
    /** default constructor */
    public ImageCategory() {
    }
    
    /** minimal constructor */
    public ImageCategory(String categoryDescription, Set imageGalleries) {
        this.categoryDescription = categoryDescription;
        this.imageGalleries = imageGalleries;
    }
    
    /**
     * 		       auto_increment
     *
     */
    public Long getCategoryCode() {
        return this.categoryCode;
    }
    
    public void setCategoryCode(Long categoryCode) {
        this.categoryCode = categoryCode;
    }
    
    public String getCategoryDescription() {
        return this.categoryDescription;
    }
    
    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }
    
    public byte[] getCategoryImage() {
        return this.categoryImage;
    }
    
    public void setCategoryImage(byte[] categoryImage) {
        this.categoryImage = categoryImage;
    }
    
    public Set getImageGalleries() {
        return this.imageGalleries;
    }
    
    public void setImageGalleries(Set imageGalleries) {
        this.imageGalleries = imageGalleries;
    }
    
    public String toString() {
        return new ToStringBuilder(this)
        .append("categoryCode", getCategoryCode())
        .toString();
    }

    public String getCategoryImageUrl() {
        return categoryImageUrl;
    }

    public void setCategoryImageUrl(String categoryImageUrl) {
        this.categoryImageUrl = categoryImageUrl;
    }
    
}
