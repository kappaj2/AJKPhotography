/*
 * ImageForm.java
 *
 * Created on 13 March 2008, 11:27
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.struts.forms;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;
import za.co.ajk.business.rules.FileValidationRules;
import za.co.ajk.persistance.transferobjects.Image;

/**
 *
 * @author akapp
 */
public class ImageForm extends ActionForm{
    
    private static Logger log = Logger.getLogger(ImageForm.class);
    private FileValidationRules fileValidationRules;
    
    /**
     *  Default constructor
     */
    public ImageForm(){
        fileValidationRules = new FileValidationRules();
    }
    
    private FormFile myFile;
    
    public void setMyFile(FormFile myFile) {
        this.myFile = myFile;
    }
    
    public FormFile getMyFile() {
        return myFile;
    }
    
    private Collection packageList;
    
    private Collection imageList;
    
    public Collection getImageList(){
        return imageList;
    }
    
    public void setImageList(Collection imageList){
        this.imageList = imageList;
    }
    
    private String actionName;
    
    private boolean includeImage = false;
    
    /** identifier field */
    private za.co.ajk.persistance.transferobjects.ImagePK comp_id;
    
    /** persistent field */
    private String imageName;
    
    /** persistent field */
    private String imageUrl;
    
    /** nullable persistent field */
    private String imageDescription;
    
    public za.co.ajk.persistance.transferobjects.ImagePK getComp_id() {
        return this.comp_id;
    }
    
    public void setComp_id(za.co.ajk.persistance.transferobjects.ImagePK comp_id) {
        this.comp_id = comp_id;
    }
    
    public String getImageName() {
        return this.imageName;
    }
    
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
    
    public String getImageUrl() {
        return this.imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public String getImageDescription() {
        return this.imageDescription;
    }
    
    public void setImageDescription(String imageDescription) {
        this.imageDescription = imageDescription;
    }
    
    private long categoryCode;
    
    private long galleryCode;
    
    private long imageCode;
    
    public long getCategoryCode() {
        return categoryCode;
    }
    
    public void setCategoryCode(long categoryCode) {
        this.categoryCode = categoryCode;
    }
    
    public long getGalleryCode() {
        return galleryCode;
    }
    
    public void setGalleryCode(long galleryCode) {
        this.galleryCode = galleryCode;
    }
    
    public void reset(ActionMapping arg0, HttpServletRequest arg1){
        log.debug("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        log.debug(" ~~~~~~~~~~~ reset called in the ImageForm ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        log.debug("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        
        
        setIncludeImage(false);
        setImageList(new ArrayList<Image>());
    }
    
    public boolean isIncludeImage() {
        return includeImage;
    }
    
    public void setIncludeImage(boolean includeImage) {
        this.includeImage = includeImage;
    }
    
    public long getImageCode() {
        return imageCode;
    }
    
    public void setImageCode(long imageCode) {
        this.imageCode = imageCode;
    }
    
    public String getActionName() {
        return actionName;
    }
    
    public void setActionName(String actionName) {
        this.actionName = actionName;
    }
    
    public Collection getPackageList() {
        return packageList;
    }
    
    public void setPackageList(Collection packageList) {
        this.packageList = packageList;
    }
    
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
    
    
    public ActionErrors validate(
            ActionMapping mapping, HttpServletRequest request ) {
        
        ActionErrors errors = new ActionErrors();
        
        if (getMyFile() != null){
            
            String contentType = getMyFile().getContentType();
            String fileName = getMyFile().getFileName();
            /*
             *Check for filesize limit of 5 meg
             */
            if (!fileValidationRules.checkImageFileSize(getMyFile().getFileSize())){
                errors.add("filesize", new ActionMessage("error.file.size"," Max file size is 5 Meg and this one is "+getMyFile().getFileSize()/1024/1024));
            }
            
            log.debug("FileType received is >"+getMyFile().getContentType()+"<");
            log.debug("Filename to validate is >"+getMyFile().getFileName());
            
            /*
             *  Check for file type
             */
            if (!fileValidationRules.checkImageFileContent(contentType)){
                errors.add("filetype", new ActionMessage("error.file.type","Invalid file type received. Type received is ",contentType ));
            }
            
            /*
             *  Check for file name - invalid chars etc...
             */
            if (!fileValidationRules.checkImageFileName(fileName)){
                errors.add("filetype", new ActionMessage("error.file.name","Invalid file name received. Possible invalid character in filename (ex '-')"));
            }
        }
        
        return errors;
    }
    
}
