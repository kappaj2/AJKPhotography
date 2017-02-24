/*
 * ConfigurationAndSetupForm.java
 *
 * Created on 28 March 2008, 11:42
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.struts.forms;

import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

/**
 *
 * @author akapp
 */
public class ConfigurationAndSetupForm extends ActionForm{
    
    private FormFile myFile;
    private Collection appPropList;
    
    private String propertyKey;
    private String propertyValueDefault;
    private String propertyValue;
    
    public FormFile getMyFile() {
        return myFile;
    }
    
    public void setMyFile(FormFile myFile) {
        this.myFile = myFile;
    }
    
    public Collection getAppPropList() {
        return appPropList;
    }
    
    public void setAppPropList(Collection appPropList) {
        this.appPropList = appPropList;
    }
    
    public String getPropertyKey() {
        return propertyKey;
    }
    
    public void setPropertyKey(String propertyKey) {
        this.propertyKey = propertyKey;
    }
    
    public String getPropertyValueDefault() {
        return propertyValueDefault;
    }
    
    public void setPropertyValueDefault(String propertyValueDefault) {
        this.propertyValueDefault = propertyValueDefault;
    }
    
    public String getPropertyValue() {
        return propertyValue;
    }
    
    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }
    
    public void reset(ActionMapping arg0, HttpServletRequest arg1){
        setPropertyValue(null);
        setPropertyValueDefault(null);
        setPropertyKey(null);
    }
}


