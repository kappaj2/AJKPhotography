/*
 * ImageCategoryForm.java
 *
 * Created on 11 March 2008, 01:52
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.struts.forms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import za.co.ajk.persistance.transferobjects.ImageCategory;

/**
 *
 * @author akapp
 */
public class ImageCategoryForm extends ActionForm{
    
    private static Logger log = Logger.getLogger(ImageCategoryForm.class);
    
    private FormFile myFile;
    
    public void setMyFile(FormFile myFile) {
        this.myFile = myFile;
    }
    
    public FormFile getMyFile() {
        return myFile;
    }
    
    private Collection categoryList;
    
    public Collection getCategoryList() {
        return categoryList;
    }
    
    public void setCategoryList(Collection categoryList) {
        this.categoryList = categoryList;
    }
    
    private ImageCategory imageCategory;
    
    /** identifier field */
    private Long categoryCode;
    
    /** persistent field */
    private String categoryDescription;
    
    /** nullable persistent field */
    private byte[] categoryImage;
    
    private boolean includeImage = false;
    
    /** persistent field */
    private Set imageGalleries;
    
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
    
    public void reset(ActionMapping arg0, HttpServletRequest arg1){
        
        log.debug("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        log.debug(" ~~~~~~~~~~~ reset called in the ImageCategoryForm ~~~~~~~~~~~~~~~~~~~~~~~~~~");
        log.debug("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        categoryList = new ArrayList<ImageCategory>();
        includeImage = false;
        imageCategory = new ImageCategory();
    }
    
    public boolean isIncludeImage() {
        return includeImage;
    }
    
    public void setIncludeImage(boolean includeImage) {
        this.includeImage = includeImage;
    }
    
    public ImageCategory getImageCategory() {
        return imageCategory;
    }
    
    public void setImageCategory(ImageCategory imageCategory) {
        this.imageCategory = imageCategory;
    }
}
