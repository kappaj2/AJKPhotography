package za.co.ajk.persistance.transferobjects;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import za.co.ajk.persistance.hibernate.dao.DTO;


/** @author Hibernate CodeGenerator */
public class Image implements Serializable, DTO {

    /** identifier field */
    private za.co.ajk.persistance.transferobjects.ImagePK comp_id;

    /** nullable persistent field */
    private byte[] imageData;

    /** persistent field */
    private String imageUrl;

    /** nullable persistent field */
    private String imageDescription;

    /** nullable persistent field */
    private za.co.ajk.persistance.transferobjects.ImageGallery imageGallery;

    /** full constructor */
    public Image(za.co.ajk.persistance.transferobjects.ImagePK comp_id,byte[] imageData, String imageUrl, String imageDescription, za.co.ajk.persistance.transferobjects.ImageGallery imageGallery) {
        this.comp_id = comp_id;
        this.imageUrl = imageUrl;
        this.imageDescription = imageDescription;
        this.imageGallery = imageGallery;
        this.setImageData(imageData);
    }

    /** default constructor */
    public Image() {
    }

    /** minimal constructor */
    public Image(za.co.ajk.persistance.transferobjects.ImagePK comp_id, byte[] imageData, String imageUrl) {
        this.comp_id = comp_id;
        this.imageData = imageData;
        this.imageUrl = imageUrl;
    }

    public za.co.ajk.persistance.transferobjects.ImagePK getComp_id() {
        return this.comp_id;
    }

    public void setComp_id(za.co.ajk.persistance.transferobjects.ImagePK comp_id) {
        this.comp_id = comp_id;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageDescription() {
        return this.imageDescription;
    }

    public void setImageDescription(String imageDescription) {
        this.imageDescription = imageDescription;
    }

    public za.co.ajk.persistance.transferobjects.ImageGallery getImageGallery() {
        return this.imageGallery;
    }

    public void setImageGallery(za.co.ajk.persistance.transferobjects.ImageGallery imageGallery) {
        this.imageGallery = imageGallery;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("comp_id", getComp_id())
            .toString();
    }

    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof Image) ) return false;
        Image castOther = (Image) other;
        return new EqualsBuilder()
            .append(this.getComp_id(), castOther.getComp_id())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getComp_id())
            .toHashCode();
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

}
