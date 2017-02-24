package za.co.ajk.persistance.transferobjects;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;
import za.co.ajk.persistance.hibernate.dao.DTO;


/** @author Hibernate CodeGenerator */
public class HitCounter implements Serializable , DTO{
    
    /** identifier field */
    private Long id;
    
    /** nullable persistent field */
    private Date hitTime;
    
    /** persistent field */
    private String hitAction;
    
    /** nullable persistent field */
    private String hitLangCode;
    
    /** nullable persistent field */
    private String hitTypeCode;
    
    /** full constructor */
    public HitCounter(Long id, Date hitTime, String hitAction, String hitLangCode, String hitTypeCode) {
        this.id = id;
        this.hitTime = hitTime;
        this.hitAction = hitAction;
        this.hitLangCode = hitLangCode;
        this.hitTypeCode = hitTypeCode;
    }
    
    /** default constructor */
    public HitCounter() {
    }
    
    /** minimal constructor */
    public HitCounter(Long id, String hitAction) {
        this.id = id;
        this.hitAction = hitAction;
    }
    
    /**
     * 		       auto_increment
     *
     */
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Date getHitTime() {
        return this.hitTime;
    }
    
    public void setHitTime(Date hitTime) {
        this.hitTime = hitTime;
    }
    
    public String getHitAction() {
        return this.hitAction;
    }
    
    public void setHitAction(String hitAction) {
        this.hitAction = hitAction;
    }
    
    public String getHitLangCode() {
        return this.hitLangCode;
    }
    
    public void setHitLangCode(String hitLangCode) {
        this.hitLangCode = hitLangCode;
    }
    
    public String getHitTypeCode() {
        return this.hitTypeCode;
    }
    
    public void setHitTypeCode(String hitTypeCode) {
        this.hitTypeCode = hitTypeCode;
    }
    
    public String toString() {
        return new ToStringBuilder(this)
        .append("id", getId())
        .toString();
    }
    
}
