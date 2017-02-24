/*
 * PackageForm.java
 *
 * Created on 19 March 2008, 11:02
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.struts.forms;

import java.math.BigDecimal;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author akapp
 */
public class PackageForm extends ActionForm{
    
    private static Logger log = Logger.getLogger(PackageForm.class);
    
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
    
    private Collection packageList;
    
    public Long getPackageId() {
        return packageId;
    }
    
    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }
    
    public String getPackageName() {
        return packageName;
    }
    
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
    
    public String getWhatIDo() {
        return whatIDo;
    }
    
    public void setWhatIDo(String whatIDo) {
        this.whatIDo = whatIDo;
    }
    
    public String getWhatYouDo() {
        return whatYouDo;
    }
    
    public void setWhatYouDo(String whatYouDo) {
        this.whatYouDo = whatYouDo;
    }
    
    public String getWhatYouGet() {
        return whatYouGet;
    }
    
    public void setWhatYouGet(String whatYouGet) {
        this.whatYouGet = whatYouGet;
    }
    
    public BigDecimal getCost() {
        return cost;
    }
    
    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
    
    public Collection getPackageList() {
        try{
            
            
            log.debug("Returning a packageList with size >"+this.packageList.size());
            return packageList;
            
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    public void setPackageList(Collection packageList) {
        
        log.debug("=================================================================================");
        log.debug("Setting package list with a list of size >"+packageList.size());
        log.debug("=================================================================================");
        
        this.packageList = packageList;
        log.debug("Returning a packageList with size >"+this.packageList.size());
    }
    
    public void reset(ActionMapping arg0, HttpServletRequest arg1){
        log.debug("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        log.debug(" ~~~~~~~~~~~ reset called in the PackageForm ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        log.debug("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        
        
    }
}
