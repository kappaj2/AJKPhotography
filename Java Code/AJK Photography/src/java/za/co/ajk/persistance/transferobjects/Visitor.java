package za.co.ajk.persistance.transferobjects;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;
import za.co.ajk.persistance.hibernate.dao.DTO;


/** @author Hibernate CodeGenerator */
public class Visitor implements Serializable, DTO {
    
    /** identifier field */
    private Long visitorId;
    
    /** persistent field */
    private String visitorName;
    
    /** persistent field */
    private String visitorSurname;
    
    /** persistent field */
    private String visitorEmail;
    
    /** persistent field */
    private Set visitorComments;
    
    /** full constructor */
    public Visitor(String visitorName, String visitorSurname, String visitorEmail, Set visitorComments) {
        this.visitorName = visitorName;
        this.visitorSurname = visitorSurname;
        this.visitorEmail = visitorEmail;
        this.visitorComments = visitorComments;
    }
    
    /** default constructor */
    public Visitor() {
    }
    
    /**
     * 		       auto_increment
     *
     */
    public Long getVisitorId() {
        return this.visitorId;
    }
    
    public void setVisitorId(Long visitorId) {
        this.visitorId = visitorId;
    }
    
    public String getVisitorName() {
        return this.visitorName;
    }
    
    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }
    
    public String getVisitorSurname() {
        return this.visitorSurname;
    }
    
    public void setVisitorSurname(String visitorSurname) {
        this.visitorSurname = visitorSurname;
    }
    
    public String getVisitorEmail() {
        return this.visitorEmail;
    }
    
    public void setVisitorEmail(String visitorEmail) {
        this.visitorEmail = visitorEmail;
    }
    
    public Set getVisitorComments() {
        return this.visitorComments;
    }
    
    public void setVisitorComments(Set visitorComments) {
        this.visitorComments = visitorComments;
    }
    
    public String toString() {
        return new ToStringBuilder(this)
        .append("visitorId", getVisitorId())
        .toString();
    }
    
}
