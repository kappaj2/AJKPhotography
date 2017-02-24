/*
 * ImageGalleryForm.java
 *
 * Created on 11 March 2008, 03:30
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.struts.forms;

import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import za.co.ajk.persistance.transferobjects.Image;
import za.co.ajk.persistance.transferobjects.ImageGallery;

/**
 *
 * @author akapp
 */
public class ImageGalleryForm extends ActionForm{
    
    private static Logger log = Logger.getLogger(ImageGalleryForm.class);
    
    private FormFile myFile;
    
    public void setMyFile(FormFile myFile) {
        this.myFile = myFile;
    }
    
    public FormFile getMyFile() {
        return myFile;
    }
    
    private Collection galleryList;
    
    public Collection getGalleryList() {
        return galleryList;
    }
    
    public void setGalleryList(Collection galleryList) {
        this.galleryList = galleryList;
    }
    
    private Collection imageList;
    
    public Collection getImageList(){
        return imageList;
    }
    
    public void setImageList(Collection imageList){
        this.imageList = imageList;
    }
    
    public void reset(ActionMapping arg0, HttpServletRequest arg1){
        
        log.debug("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        log.debug(" ~~~~~~~~~~~ reset called in the ImageGalleryForm ~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        log.debug("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        
        setGalleryList(new ArrayList<ImageGallery>());
        setImageList(new ArrayList<Image>());
        includeImage = false;
    }
    
    private boolean includeImage = false;
    
    /** persistent field */
    private String galleryDescription;
    
    public String getGalleryDescription() {
        return this.galleryDescription;
    }
    
    public void setGalleryDescription(String galleryDescription) {
        this.galleryDescription = galleryDescription;
    }
    
    private long galleryCode;
    
    private long categoryCode;
    
    public long getGalleryCode() {
        return galleryCode;
    }
    
    public void setGalleryCode(long galleryCode) {
        this.galleryCode = galleryCode;
    }
    
    public long getCategoryCode() {
        return categoryCode;
    }
    
    public void setCategoryCode(long categoryCode) {
        this.categoryCode = categoryCode;
    }
    
    private String galleryImageUrl;
    
    public String getGalleryImageUrl() {
        return galleryImageUrl;
    }
    
    public void setGalleryImageUrl(String galleryImageUrl) {
        this.galleryImageUrl = galleryImageUrl;
    }
    
    public boolean isIncludeImage() {
        return includeImage;
    }
    
    public void setIncludeImage(boolean includeImage) {
        this.includeImage = includeImage;
    }
}
