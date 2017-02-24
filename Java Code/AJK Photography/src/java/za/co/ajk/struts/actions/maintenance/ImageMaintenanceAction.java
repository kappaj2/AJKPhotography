/*
 * ImageMaintenanceAction.java
 *
 * Created on 13 March 2008, 11:33
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.struts.actions.maintenance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;
import org.hibernate.ObjectNotFoundException;
import za.co.ajk.common.exception.CustomException;
import za.co.ajk.common.exception.ErrorCode;
import za.co.ajk.persistance.transferobjects.Image;
import za.co.ajk.persistance.transferobjects.ImageCategory;
import za.co.ajk.persistance.transferobjects.ImageGallery;
import za.co.ajk.persistance.transferobjects.ImageGalleryPK;
import za.co.ajk.persistance.transferobjects.ImagePK;
import za.co.ajk.service.CategoryMaintenanceService;
import za.co.ajk.service.GalleryMaintenanceService;
import za.co.ajk.service.ImageMaintenanceService;
import za.co.ajk.struts.actions.BaseDispatchAction;
import za.co.ajk.struts.forms.ImageForm;

/**
 *
 * @author akapp
 */
public class ImageMaintenanceAction extends BaseDispatchAction{
    
    private GalleryMaintenanceService galleryMaintenanceService = new GalleryMaintenanceService();
    private CategoryMaintenanceService categoryMaintenanceService = new CategoryMaintenanceService();
    private ImageMaintenanceService imageMaintenanceService = new ImageMaintenanceService();
    
    
    private static Logger log = Logger.getLogger(ImageMaintenanceAction.class);
    
    
    private void buildCategoryMap(HttpServletRequest request){
        HttpSession session = request.getSession();
        List<ImageCategory> categoryList = categoryMaintenanceService.findAll();
        
        /*
         *  Now build a list of categories
         */
        Map<Long, ImageCategory> categoryMap = new HashMap<Long, ImageCategory>();
        /*
         *  Add empty value to force user to select one first
         */
        categoryMap.put(0L, new ImageCategory("Make selection", null));
        
        for (ImageCategory mType : categoryList){
            categoryMap.put(mType.getCategoryCode(), mType);
        }
        session.setAttribute("categoryMap", categoryMap);
    }
    
    public ActionForward addImage(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        buildCategoryMap(request);
        
        return mapping.findForward("imageAdd");
    }
    
    public ActionForward buildImagesLookup(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        buildCategoryMap(request);
        
        return mapping.findForward("galleryImageLookup");
    }
    
    
    public ActionForward imageList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        log.debug("Doing image list action");
        
        ImageForm imageForm = (ImageForm)form;
        long categoryCode = imageForm.getCategoryCode();
        long galleryCode = imageForm.getGalleryCode();
        
        log.debug("Doing display of gallery images with categoryCode >"+categoryCode+"< and galleryCode >"+galleryCode+"<");
        
        ImageGalleryPK imageGalleryPK = new ImageGalleryPK();
        imageGalleryPK.setCategoryCode(categoryCode);
        imageGalleryPK.setGalleryCode(galleryCode);
        
        try{
            ImageGallery imageGallery = new ImageGallery();
            imageGallery = galleryMaintenanceService.loadNoSession(imageGalleryPK);
            
            log.debug("Found imageGallery object with galleryDescription >"+imageGallery.getGalleryDescription()+"<");
            
            Set<Image> imageSet = imageGallery.getImages();
            log.debug("Size for images set is >"+imageSet.size());
            
            imageForm.setImageList(imageSet);
            
            for (Image elem : imageSet) {
                log.debug("Image name is >"+elem.getImageUrl());
                elem.getComp_id().getCategoryCode();
                elem.getComp_id().getGalleryCode();
                elem.getComp_id().getImageCode();
            }
            
            
        }catch (CustomException ocus){
            log.debug("!!!!!!!!!!!!! ocus adding to errors !!!!!!!!!!!!!!");
            ActionMessages errors = new ActionMessages();
            ActionMessage msg;
            
            if (ocus.getErrorCode() == ErrorCode.OBJECT_NOT_FOUND){
                msg = new ActionMessage("error.hibernate.gallery");
            }else{
                msg = new ActionMessage("error.hibernate.generic");
            }
            
            errors.add(ActionMessages.GLOBAL_MESSAGE, msg);
            saveMessages(request.getSession(true), errors);
            saveAppErrors(request, errors);
        }
        
        return mapping.findForward("imageList");
    }
    
    
    
    public ActionForward uploadAndSaveImage(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        ImageForm imageForm = (ImageForm)form;
        FormFile myFile = imageForm.getMyFile();
        
        long categoryCode = imageForm.getCategoryCode();
        long galleryCode = imageForm.getGalleryCode();
        
        ImagePK imagePK = new ImagePK();
        imagePK.setCategoryCode(categoryCode);
        imagePK.setGalleryCode(galleryCode);
        
        try{
            
            Image image = new Image();
            image.setComp_id(imagePK);
            image.setImageDescription(imageForm.getImageDescription());
            image.setImageUrl(myFile.getFileName());
            
            image.setImageData(myFile.getFileData());
            
            imageMaintenanceService.save(image);
            
        }catch (ObjectNotFoundException onf){
            onf.printStackTrace();
            log.debug("!!!!!!!!!!!!!onf -  adding to errors !!!!!!!!!!!!!!");
            ActionMessages errors = new ActionMessages();
            ActionMessage msg = new ActionMessage("error.hibernate.generic", onf.getMessage());
            
            errors.add(ActionMessages.GLOBAL_MESSAGE, msg);
            saveMessages(request.getSession(true), errors);
            saveAppErrors(request, errors);
            
        }catch (CustomException ocus){
            ocus.printStackTrace();
            log.debug("!!!!!!!!!!!!!ocus -  adding to errors !!!!!!!!!!!!!!");
            
            ActionMessages errors = new ActionMessages();
            
            if (ocus.getErrorCode() == ErrorCode.FILE_CREATE_ERROR){
                ActionMessage msg = new ActionMessage("error.file.create", ocus.getMessage());
                errors.add(ActionMessages.GLOBAL_MESSAGE, msg);
            }else if (ocus.getErrorCode() == ErrorCode.FILE_EXISTS_ERROR){
                ActionMessage msg = new ActionMessage("error.file.exists", ocus.getMessage());
                errors.add(ActionMessages.GLOBAL_MESSAGE, msg);
            }else if (ocus.getErrorCode() == ErrorCode.FILE_DELETE_ERROR){
                ActionMessage msg = new ActionMessage("error.file.delete", ocus.getMessage());
                errors.add(ActionMessages.GLOBAL_MESSAGE, msg);
            }else{
                ActionMessage msg = new ActionMessage("error.file.generic", ocus.getMessage());
                errors.add(ActionMessages.GLOBAL_MESSAGE, msg);
            }
            
            saveMessages(request.getSession(true), errors);
            saveAppErrors(request, errors);
        }
        
        return imageList(mapping, form, request, response);
    }
    
    /**
     *
     */
    public ActionForward deleteImage(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        ImageForm imageForm = (ImageForm)form;
        
        long categoryCode = Long.parseLong(request.getParameter("categoryCode"));
        long galleryCode = Long.parseLong(request.getParameter("galleryCode"));
        long imageCode = Long.parseLong(request.getParameter("imageCode"));
        
        log.debug("Doing delete of image with categoryCode >"+categoryCode+"< and gallerycode >"+galleryCode+"< and imagecode >"+imageCode+"<");
        
        ImagePK imagePK = new ImagePK();
        imagePK.setCategoryCode(categoryCode);
        imagePK.setGalleryCode(galleryCode);
        imagePK.setImageCode(imageCode);
        
        try{
            
            Image image = new Image();
            image.setComp_id(imagePK);
            
            imageMaintenanceService.doLoadDelete(image);
            
        }catch (ObjectNotFoundException onf){
            onf.printStackTrace();
            log.debug("!!!!!!!!!!!!!onf -  adding to errors !!!!!!!!!!!!!!");
            ActionMessages errors = new ActionMessages();
            ActionMessage msg = new ActionMessage("error.hibernate.notfound");
            
            errors.add(ActionMessages.GLOBAL_MESSAGE, msg);
            saveMessages(request.getSession(true), errors);
            saveAppErrors(request, errors);
        }catch (CustomException ocus){
            ocus.printStackTrace();
            log.debug("!!!!!!!!!!!!!ocus -  adding to errors !!!!!!!!!!!!!!");
            ActionMessages errors = new ActionMessages();
            ActionMessage msg = new ActionMessage("error.hibernate.notfound");
            
            errors.add(ActionMessages.GLOBAL_MESSAGE, msg);
            saveMessages(request.getSession(true), errors);
            saveAppErrors(request, errors);
        }
        
        return imageList(mapping, form, request, response);
    }
    
    public ActionForward showImageUpdate(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        
        ImageForm imageForm = (ImageForm)form;
        
        long categoryCode = Long.parseLong(request.getParameter("categoryCode"));
        long galleryCode = Long.parseLong(request.getParameter("galleryCode"));
        long imageCode = Long.parseLong(request.getParameter("imageCode"));
        
        ImagePK imagePK = new ImagePK();
        imagePK.setCategoryCode(categoryCode);
        imagePK.setGalleryCode(galleryCode);
        imagePK.setImageCode(imageCode);
        
        Image image = new Image();
        image.setComp_id(imagePK);
        
        image = imageMaintenanceService.load(imagePK);
        
        BeanUtils.copyProperties(imageForm, image);
        imageForm.setComp_id(image.getComp_id());
        
        return mapping.findForward("imageEdit");
        
    }
    
    
    public ActionForward updateAndSaveImage(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        log.debug("Got an updateAndSaveImage action request!!!");
        ImageForm imageForm = (ImageForm)form;
        FormFile myFile = imageForm.getMyFile();
        
        ImagePK imagePK = new ImagePK();
        imagePK.setCategoryCode(imageForm.getCategoryCode());
        imagePK.setGalleryCode(imageForm.getGalleryCode());
        imagePK.setImageCode(imageForm.getImageCode());
        
        try{
            
            Image image = new Image();
            
            image = imageMaintenanceService.load(imagePK);
            String oldFileName = null;
            /*
             *  If include of the image is selected, then update the image as well as the description field
             */
            image.setImageDescription(imageForm.getImageDescription());
            
            if (imageForm.isIncludeImage()){
                log.debug("===============checkbox set");
                oldFileName = image.getImageUrl();
                image.setImageData(myFile.getFileData());
                image.setImageUrl(myFile.getFileName());
            }
            
            imageMaintenanceService.update(image, oldFileName);
            
        }catch (ObjectNotFoundException onf){
            onf.printStackTrace();
            log.debug("!!!!!!!!!!!!!onf -  adding to errors !!!!!!!!!!!!!!");
            ActionMessages errors = new ActionMessages();
            ActionMessage msg = new ActionMessage("error.hibernate.generic", onf.getMessage());
            
            errors.add(ActionMessages.GLOBAL_MESSAGE, msg);
            saveMessages(request.getSession(true), errors);
            saveAppErrors(request, errors);
            
        }catch (CustomException ocus){
            ocus.printStackTrace();
            log.debug("!!!!!!!!!!!!!ocus -  adding to errors !!!!!!!!!!!!!!");
            
            ActionMessages errors = new ActionMessages();
            
            if (ocus.getErrorCode() == ErrorCode.FILE_CREATE_ERROR){
                ActionMessage msg = new ActionMessage("error.file.create", ocus.getMessage());
                errors.add(ActionMessages.GLOBAL_MESSAGE, msg);
            }else if (ocus.getErrorCode() == ErrorCode.FILE_DELETE_ERROR){
                ActionMessage msg = new ActionMessage("error.file.delete", ocus.getMessage());
                errors.add(ActionMessages.GLOBAL_MESSAGE, msg);
            }else{
                ActionMessage msg = new ActionMessage("error.file.generic", ocus.getMessage());
                errors.add(ActionMessages.GLOBAL_MESSAGE, msg);
            }
            
            saveMessages(request.getSession(true), errors);
            saveAppErrors(request, errors);
        }
        
        return imageList(mapping, form, request, response);
        
    }
}
