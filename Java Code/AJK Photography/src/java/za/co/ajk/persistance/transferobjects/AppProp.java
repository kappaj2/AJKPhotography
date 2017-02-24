package za.co.ajk.persistance.transferobjects;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import za.co.ajk.persistance.hibernate.dao.DTO;


/** @author Hibernate CodeGenerator */
public class AppProp implements Serializable , DTO{
    
    /** identifier field */
    private String propertyKey;
    
    /** persistent field */
    private String propertyValueDefault;
    
    /** nullable persistent field */
    private String propertyValue;
    
    /** full constructor */
    public AppProp(String propertyKey, String propertyValueDefault, String propertyValue) {
        this.propertyKey = propertyKey;
        this.propertyValueDefault = propertyValueDefault;
        this.propertyValue = propertyValue;
    }
    
    /** default constructor */
    public AppProp() {
    }
    
    /** minimal constructor */
    public AppProp(String propertyKey, String propertyValueDefault) {
        this.propertyKey = propertyKey;
        this.propertyValueDefault = propertyValueDefault;
    }
    
    public String getPropertyKey() {
        return this.propertyKey;
    }
    
    public void setPropertyKey(String propertyKey) {
        this.propertyKey = propertyKey;
    }
    
    public String getPropertyValueDefault() {
        return this.propertyValueDefault;
    }
    
    public void setPropertyValueDefault(String propertyValueDefault) {
        this.propertyValueDefault = propertyValueDefault;
    }
    
    public String getPropertyValue() {
        return this.propertyValue;
    }
    
    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }
    
    public String toString() {
        return new ToStringBuilder(this)
        .append("propertyKey", getPropertyKey())
        .toString();
    }
    
}
