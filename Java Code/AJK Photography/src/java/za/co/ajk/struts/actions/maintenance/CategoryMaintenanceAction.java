/*
 * CategoryMaintenanceAction.java
 *
 * Created on 11 March 2008, 12:25
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.struts.actions.maintenance;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import za.co.ajk.service.CategoryMaintenanceService;
import za.co.ajk.struts.actions.BaseDispatchAction;
import za.co.ajk.struts.forms.ImageCategoryForm;

/**
 *
 * @author akapp
 */
public class CategoryMaintenanceAction extends BaseDispatchAction{
    
    private static Logger log = Logger.getLogger(CategoryMaintenanceAction.class);
    
    /*
     *  Define service for dealing with DAO's
     */
    private CategoryMaintenanceService categoryMaintenanceService = new CategoryMaintenanceService();
    
    /**
     * This method will retrieve all the categories defined in the database
     */
    public ActionForward showAllCategories(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        log.debug("Got a showAllCategories action request!!!");
        
        List<ImageCategory> categoryList = categoryMaintenanceService.findAll();
        
        ImageCategoryForm imageCategoryForm = (ImageCategoryForm)form;
        imageCategoryForm.setCategoryList(categoryList);
        
        return mapping.findForward("showAllCategories");
    }
    
    /**
     * This method will delete a specific category
     */
    public ActionForward deleteCategory(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        log.debug("Got a deleteCategory action request!!!");
        
        long categoryCode = Long.parseLong(request.getParameter("categoryCode"));
        
        ImageCategory imageCategory = new ImageCategory();
        imageCategory.setCategoryCode(categoryCode);
        
        try{
            
            categoryMaintenanceService.doLoadDelete(imageCategory);
            
        } catch (CustomException ce){
            
            log.debug("!!!!!!!!!!!!! adding to errors !!!!!!!!!!!!!!");
            ActionMessages errors = new ActionMessages();
            ActionMessage msg = new ActionMessage("error.filesystem.delete", ce.getMessage());
            
            errors.add(ActionMessages.GLOBAL_MESSAGE, msg);
            saveMessages(request.getSession(true), errors);
            saveAppErrors(request, errors);
        }
        
        return showAllCategories(mapping, form, request, response);
    }
    
    /**
     * This will just forward to the correct display page/form combination
     */
    public ActionForward addCategory(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        log.debug("Got a addCategory action request!!!");
        
        return mapping.findForward("addCategory");
    }
    
    /**
     * This method will save a new category
     */
    public ActionForward saveCategory(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        log.debug("Got a saveCategory action request!!!");
        try{
            
            ImageCategoryForm imageCategoryForm = (ImageCategoryForm)form;
            FormFile myFile = imageCategoryForm.getMyFile();
            
            ImageCategory imageCategory = new ImageCategory();
            imageCategory.setCategoryDescription(imageCategoryForm.getCategoryDescription());
            imageCategory.setCategoryCode(0L);
            imageCategory.setCategoryImage(myFile.getFileData());
            imageCategory.setCategoryImageUrl(myFile.getFileName());
            categoryMaintenanceService.save(imageCategory);
            
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
        return showAllCategories(mapping, form, request, response);
    }
    
    
    
    public ActionForward showCategoryUpdate(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        log.debug("!!!!!!!!!!!!!! Doing get and forward to show update !!!!!!!!!!");
        ImageCategoryForm imageCategoryForm = (ImageCategoryForm)form;
        
        long categoryCode = Long.parseLong(request.getParameter("categoryCode"));
        ImageCategory imageCategory = new ImageCategory();
        
        imageCategory = categoryMaintenanceService.load(categoryCode);
        
        imageCategoryForm.setCategoryCode(imageCategory.getCategoryCode());
        imageCategoryForm.setCategoryDescription(imageCategory.getCategoryDescription());
        
        //imageCategoryForm.setImageCategory(imageCategory);
        
        log.debug("Category data is now >"+imageCategory.getCategoryCode()+"< and >"+imageCategory.getCategoryDescription()+"<");
        
        log.debug("Done loading - now forwarding to display screen!!!");
        
        return mapping.findForward("displayImageCategoryUpdate");
        
    }
    
    
    
    public ActionForward updateAndSaveCategory(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        log.debug("Got an updateAndSaveCategory action request!!!");
        ImageCategoryForm imageCategoryForm = (ImageCategoryForm)form;
        FormFile myFile = imageCategoryForm.getMyFile();
        
        
        long categoryCode = imageCategoryForm.getCategoryCode();
        
        try{
            
            ImageCategory imageCategory = categoryMaintenanceService.load(categoryCode);
            String oldFileName = null;
            
            log.debug("Category data is now >"+imageCategory.getCategoryCode()+"< and >"+imageCategory.getCategoryDescription()+"<");
            
            /*
             *  If include of the image is selected, then update the image as well as the description field
             */
            imageCategory.setCategoryDescription(imageCategoryForm.getCategoryDescription());
            
            if (imageCategoryForm.isIncludeImage()){
                
                log.debug("===============checkbox set");
                oldFileName = imageCategory.getCategoryImageUrl(); // still old file name value - this must be deleted!!!
                imageCategory.setCategoryImage(myFile.getFileData());
                imageCategory.setCategoryImageUrl(myFile.getFileName());
            }
            
            categoryMaintenanceService.update(imageCategory, oldFileName);
            
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
        
        return showAllCategories(mapping, form, request, response);
        
    }
    
}
