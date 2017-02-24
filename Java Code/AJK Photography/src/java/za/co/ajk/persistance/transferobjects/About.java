package za.co.ajk.persistance.transferobjects;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import za.co.ajk.persistance.hibernate.dao.DTO;


/** @author Hibernate CodeGenerator */
public class About implements Serializable, DTO {
    
    /** identifier field */
    private Long id;
    
    /** persistent field */
    private String aboutHeading;
    
    /** persistent field */
    private String aboutDescription;
    
    /** full constructor */
    public About(Long id, String aboutHeading, String aboutDescription) {
        this.id = id;
        this.aboutHeading = aboutHeading;
        this.aboutDescription = aboutDescription;
    }
    
    /** default constructor */
    public About() {
    }
    
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getAboutHeading() {
        return this.aboutHeading;
    }
    
    public void setAboutHeading(String aboutHeading) {
        this.aboutHeading = aboutHeading;
    }
    
    public String getAboutDescription() {
        return this.aboutDescription;
    }
    
    public void setAboutDescription(String aboutDescription) {
        this.aboutDescription = aboutDescription;
    }
    
    public String toString() {
        return new ToStringBuilder(this)
        .append("id", getId())
        .toString();
    }
    
}
