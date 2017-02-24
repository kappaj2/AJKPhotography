package za.co.ajk.persistance.transferobjects;

import java.io.Serializable;
import java.math.BigDecimal;
import org.apache.commons.lang.builder.ToStringBuilder;
import za.co.ajk.persistance.hibernate.dao.DTO;


/** @author Hibernate CodeGenerator */
public class Packages implements Serializable, DTO {
    
    /** identifier field */
    private Long packageId;
    
    /** persistent field */
    private String packageName;
    
    /** persistent field */
    private String whatIDo;
    
    /** persistent field */
    private String whatYouDo;
    
    /** persistent field */
    private String whatYouGet;
    
    /** persistent field */
    private BigDecimal cost;
    
    /** full constructor */
    public Packages(Long packageId, String packageName, String whatIDo, String whatYouDo, String whatYouGet, BigDecimal cost) {
        this.packageId = packageId;
        this.packageName = packageName;
        this.whatIDo = whatIDo;
        this.whatYouDo = whatYouDo;
        this.whatYouGet = whatYouGet;
        this.cost = cost;
    }
    
    /** default constructor */
    public Packages() {
    }
    
    /**
     * 		       auto_increment
     *
     */
    public Long getPackageId() {
        return this.packageId;
    }
    
    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }
    
    public String getPackageName() {
        return this.packageName;
    }
    
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
    
    public String getWhatIDo() {
        return this.whatIDo;
    }
    
    public void setWhatIDo(String whatIDo) {
        this.whatIDo = whatIDo;
    }
    
    public String getWhatYouDo() {
        return this.whatYouDo;
    }
    
    public void setWhatYouDo(String whatYouDo) {
        this.whatYouDo = whatYouDo;
    }
    
    public String getWhatYouGet() {
        return this.whatYouGet;
    }
    
    public void setWhatYouGet(String whatYouGet) {
        this.whatYouGet = whatYouGet;
    }
    
    public BigDecimal getCost() {
        return this.cost;
    }
    
    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
    
    public String toString() {
        return new ToStringBuilder(this)
        .append("packageId", getPackageId())
        .toString();
    }
    
}
