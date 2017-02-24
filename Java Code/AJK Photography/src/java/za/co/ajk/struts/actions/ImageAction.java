/*
 * ImageAction.java
 *
 * Created on 17 March 2008, 06:33
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.struts.actions;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import za.co.ajk.persistance.transferobjects.Image;
import za.co.ajk.persistance.transferobjects.ImageGallery;
import za.co.ajk.service.GalleryMaintenanceService;
import za.co.ajk.service.ImageMaintenanceService;
import za.co.ajk.struts.forms.ImageForm;

/**
 * This action class will only handle the forwarding between the different layouts. The actual image retrieval is done by a servlet.
 * @author akapp
 */
public class ImageAction extends BaseDispatchAction {
    
    private static Logger log = Logger.getLogger(ImageAction.class);
    private GalleryMaintenanceService galleryMaintenanceService = new GalleryMaintenanceService();
    private ImageMaintenanceService imageMaintenanceService = new ImageMaintenanceService();
    
    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        /*
         *
         */
        log.debug("Got an execute action in the ImageAction class");
        
        /*
         *  If the actionName is in the request parameters, its a breadcrumb request.
         */
        boolean isCrumb = true;
        
        String actionName = (String)request.getParameter("actionName");
        ImageForm imageForm = (ImageForm)form;
        
        if (actionName == null){
            actionName = imageForm.getActionName();
            isCrumb = false;
        }
        
        log.debug("Value for actionName is =    =============================== >"+actionName +"< ===================");
        
        /*
         *  Possible values are at the moment
         *  1. galleries
         *  2. images
         */
        
        if (actionName.equals("galleries")){
            
            
            log.debug("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            log.debug(" Received a galleries request in ImageAction  ");
            log.debug("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            
            /*
             *  If code is categories - then retrieve all the galleries for the category value passed.
             */
            long categoryCode =0L;
            if (isCrumb){
                categoryCode = Long.parseLong(request.getParameter("categoryCode"));
            }else{
                categoryCode = imageForm.getCategoryCode();
            }
            super.setPageName("/imageAction.do?exec=executeAction&categoryCode="+categoryCode+"&actionName="+actionName);
            
            log.debug("Received a categoryCode value of >"+categoryCode+"<");
            
            /*
             *  Get all the galleries for this specific categoryCode
             */
            List<ImageGallery> imageGalleryList = galleryMaintenanceService.loadByCategoryCode(categoryCode);
            log.debug("Size for image gallery list is >"+imageGalleryList.size());
            
            for (ImageGallery elem : imageGalleryList) {
                log.debug("Got categoryCode value of >"+elem.getComp_id().getCategoryCode());
                log.debug("Got gallerycode value of >"+elem.getComp_id().getGalleryCode());
                log.debug("Got galleryImageURL value of >"+elem.getGalleryImageUrl());
                log.debug("Got description value of >"+elem.getGalleryDescription());
            }
            
            imageForm.setImageList(imageGalleryList);
            
            return mapping.findForward("showCategoryGalleries");
            
        }else if (actionName.equals("images")){
            
            log.debug("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            log.debug(" Received an images request in ImageAction  ");
            log.debug("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            
            /*
             *  If code is categories - then retrieve all the galleries for the category value passed.
             */
            
            long galleryCode = 0L;
            if (isCrumb){
                galleryCode = Long.parseLong(request.getParameter("galleryCode"));
            }else{
                galleryCode = imageForm.getGalleryCode();
            }
            super.setPageName("/imageAction.do?exec=executeAction&galleryCode="+galleryCode+"&actionName="+actionName);
            
            log.debug("Received a galleryCode value of >"+galleryCode+"<");
            List<Image> imageList = imageMaintenanceService.findByGalleryCode(galleryCode);
            
            if (log.isDebugEnabled()){
                
                for (Image elem : imageList) {
                    log.debug("Value for categoryCode is >"+elem.getComp_id().getCategoryCode());
                    log.debug("Value for galleryCode is >"+elem.getComp_id().getGalleryCode());
                    log.debug("Value for imageCode is >"+elem.getComp_id().getImageCode());
                    log.debug("Image description is >"+elem.getImageDescription());
                }
            }
            
            imageForm.setImageList(imageList);
            
            return mapping.findForward("showGalleryImages");
            
        }else if (actionName.equals("imagesBig")){
            
            log.debug("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            log.debug(" Received an imagesBig request in ImageAction  ");
            log.debug("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            
            /*
             *  If code is categories - then retrieve all the galleries for the category value passed.
             */
           
            
            long imageCode = 0L;
            if (isCrumb){
                imageCode = Long.parseLong(request.getParameter("imageCode"));
            }else{
                imageCode = imageForm.getImageCode();
            }
            super.setPageName("/imageAction.do?exec=executeAction&imageCode="+imageCode+"&actionName="+actionName);
            
            
            log.debug("Received an imageCode value of >"+imageCode+"<");
            Image image = imageMaintenanceService.findByImageCode(imageCode, true);
            
            imageForm.setImageName(image.getImageUrl());
            imageForm.setImageDescription(image.getImageDescription());
            
            return mapping.findForward("showGalleryImageBig");
            
        }else{
            
            return null;
            
        }
    }
    
    public ActionForward displayGalleryImage(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        /*
         *
         */
        log.debug("Got an displayGalleryImage request in the ImageAction class");
        return mapping.findForward("showAllCategories");
    }
    
}
