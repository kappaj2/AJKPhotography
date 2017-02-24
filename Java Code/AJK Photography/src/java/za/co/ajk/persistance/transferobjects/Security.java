package za.co.ajk.persistance.transferobjects;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import za.co.ajk.persistance.hibernate.dao.DTO;


/** @author Hibernate CodeGenerator */
public class Security implements Serializable, DTO {
    
    /** identifier field */
    private Long id;
    
    /** persistent field */
    private String username;
    
    /** persistent field */
    private String password;
    
    /** full constructor */
    public Security(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
    
    /** default constructor */
    public Security() {
    }
    
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String toString() {
        return new ToStringBuilder(this)
        .append("id", getId())
        .toString();
    }
    
}
