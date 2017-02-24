/*
 * GalleryMaintenanceAction.java
 *
 * Created on 11 March 2008, 03:23
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.struts.actions.maintenance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import za.co.ajk.persistance.transferobjects.ImageCategory;
import za.co.ajk.persistance.transferobjects.ImageGallery;
import za.co.ajk.persistance.transferobjects.ImageGalleryPK;
import za.co.ajk.service.CategoryMaintenanceService;
import za.co.ajk.service.GalleryMaintenanceService;
import za.co.ajk.struts.actions.BaseDispatchAction;
import za.co.ajk.struts.forms.ImageGalleryForm;

/**
 *
 * @author akapp
 */
public class GalleryMaintenanceAction extends BaseDispatchAction{
    
    private static Logger log = Logger.getLogger(GalleryMaintenanceAction.class);
    
    private GalleryMaintenanceService galleryMaintenanceService = new GalleryMaintenanceService();
    private CategoryMaintenanceService categoryMaintenanceService = new CategoryMaintenanceService();
    
    /**
     *
     */
    public ActionForward showAllGalleries(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        log.debug("Got a showAllGalleriess action request!!!");
        
        List<ImageGallery> GalleryList = galleryMaintenanceService.findAll();
        
        log.debug("Size for ImageCategories is >"+GalleryList.size());
        
        ImageGalleryForm imageGalleryForm = (ImageGalleryForm)form;
        imageGalleryForm.setGalleryList(GalleryList);
        
        return mapping.findForward("showAllGalleries");
    }
    
    
    public ActionForward updateGallery(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        log.debug("Got an updateGallery action request!!!");
        
        ImageGalleryForm imageGalleryForm = (ImageGalleryForm)form;
        FormFile myFile = imageGalleryForm.getMyFile();
        
        Long categoryCode = imageGalleryForm.getCategoryCode();
        Long galleryCode = imageGalleryForm.getGalleryCode();
        
        log.debug("Got categoryCode>"+categoryCode+"< and galleryCode >"+galleryCode+"<");
        
        ImageGalleryPK imageGalleryPK = new ImageGalleryPK();
        imageGalleryPK.setCategoryCode(categoryCode);
        imageGalleryPK.setGalleryCode(galleryCode);
        
        
        try{
            
            ImageGallery imageGallery = new ImageGallery();
            
            imageGallery = galleryMaintenanceService.load(imageGalleryPK);
            String oldFileName = null;
            imageGallery.setGalleryDescription(imageGalleryForm.getGalleryDescription());
            
            if (imageGalleryForm.isIncludeImage()){
                
                log.debug("===============checkbox set");
                oldFileName= imageGallery.getGalleryImageUrl();
                imageGallery.setGalleryImage(myFile.getFileData());
                imageGallery.setGalleryImageUrl(myFile.getFileName());
            }
            
            galleryMaintenanceService.update(imageGallery, oldFileName);
            
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
        
        return showAllGalleries(mapping, form, request, response);
    }
    
    
    /**
     *  forward to the editGallery jsp
     */
    public ActionForward editGallery(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        log.debug("Got an eddGallery action request!!!");
        
        ImageGalleryForm imageGalleryForm = (ImageGalleryForm)form;
        Long categoryCode = imageGalleryForm.getCategoryCode();
        Long galleryCode = imageGalleryForm.getGalleryCode();
        
        log.debug("Gor categoryCode>"+categoryCode+"< and galleryCode >"+galleryCode+"<");
        
        ImageGalleryPK imageGalleryPK = new ImageGalleryPK();
        imageGalleryPK.setCategoryCode(categoryCode);
        imageGalleryPK.setGalleryCode(galleryCode);
        
        ImageGallery imageGallery = new ImageGallery();
        imageGallery = galleryMaintenanceService.load(imageGalleryPK);
        
        
        BeanUtils.copyProperties(imageGalleryForm, imageGallery);
        
        return mapping.findForward("editGallery");
    }
    /**
     *
     */
    public ActionForward deleteGallery(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        log.debug("Got a deleteGallery action request!!!");
        
        log.debug("catCode >"+request.getParameter("categoryCode"));
        log.debug("galCode >"+request.getParameter("galleryCode"));
        
        long categoryCode = Long.parseLong(request.getParameter("categoryCode"));
        long galleryCode = Long.parseLong(request.getParameter("galleryCode"));
        
        log.debug("Value for categoryCode >"+categoryCode+"< and value for gallery code >"+galleryCode+"<");
        
        ImageGallery imageGallery = new ImageGallery();
        ImageGalleryPK imageGalleryPK = new ImageGalleryPK();
        
        imageGalleryPK.setCategoryCode(categoryCode);
        imageGalleryPK.setGalleryCode(galleryCode);
        
        imageGallery.setComp_id(imageGalleryPK);
        
        try{
            
            galleryMaintenanceService.doLoadDelete(imageGallery);
            
        } catch (CustomException ce){
            
            log.debug("!!!!!!!!!!!!! adding to errors !!!!!!!!!!!!!!");
            ActionMessages errors = new ActionMessages();
            ActionMessage msg = new ActionMessage("error.filesystem.delete", ce.getMessage());
            
            errors.add(ActionMessages.GLOBAL_MESSAGE, msg);
            saveMessages(request.getSession(true), errors);
            saveAppErrors(request, errors);
        }
        return showAllGalleries(mapping, form, request, response);
    }
    
    /**
     *
     */
    public ActionForward addGallery(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        log.debug("Got a addGallery action request!!!");
        /*
         *  Build a list of all available catergories for a lookup list.
         */
        
        List<ImageCategory> categoryList = categoryMaintenanceService.findAll();
        
        HttpSession session = request.getSession();
        /*
         *  Build a map of categories
         */
        Map<Long, ImageCategory> categoryMap = new HashMap<Long, ImageCategory>();
        for (ImageCategory mType : categoryList){
            
            categoryMap.put(mType.getCategoryCode(), mType);
            
        }
        session.setAttribute("categoryMap", categoryMap);
        
        return mapping.findForward("addGallery");
    }
    
    /**
     *
     */
    public ActionForward saveGallery(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        log.debug("Got a saveGallery action request!!!");
        
        
        ImageGalleryForm imageGalleryForm = (ImageGalleryForm)form;
        FormFile myFile = imageGalleryForm.getMyFile();
        
        ImageGallery imageGallery = new ImageGallery();
        imageGallery.setGalleryDescription(imageGalleryForm.getGalleryDescription());
        ImageGalleryPK imageGalleryPK = new ImageGalleryPK();
        
        imageGalleryPK.setCategoryCode(imageGalleryForm.getCategoryCode());
        imageGalleryPK.setGalleryCode(0L);
        
        imageGallery.setComp_id(imageGalleryPK);
        imageGallery.setGalleryImageUrl(myFile.getFileName());
        imageGallery.setGalleryImage(myFile.getFileData());
        
        try{
            
            galleryMaintenanceService.save(imageGallery);
            
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
        
        
        return showAllGalleries(mapping, form, request, response);
    }
    
    
    
    
    
    
    
}