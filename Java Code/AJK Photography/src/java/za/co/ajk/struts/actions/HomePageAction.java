/*
 * HomePageAction.java
 *
 * Created on 10 March 2008, 02:31
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.struts.actions;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import za.co.ajk.persistance.transferobjects.About;
import za.co.ajk.persistance.transferobjects.ImageCategory;
import za.co.ajk.persistance.transferobjects.Packages;
import za.co.ajk.service.AboutMaintenanceService;
import za.co.ajk.service.CategoryMaintenanceService;
import za.co.ajk.service.PackagesMaintenanceService;
import za.co.ajk.struts.forms.AboutForm;
import za.co.ajk.struts.forms.ImageForm;
import za.co.ajk.struts.forms.PackageForm;

/**
 *
 * @author akapp
 */
public class HomePageAction extends BaseDispatchAction{
    
    private static Logger log = Logger.getLogger(HomePageAction.class);
    private CategoryMaintenanceService categoryMaintenanceService = new CategoryMaintenanceService();
    private PackagesMaintenanceService packagesMaintenanceService = new PackagesMaintenanceService();
    private AboutMaintenanceService aboutMaintenanceService = new AboutMaintenanceService();
    private static String app_name;
    
    
    
    /**
     *  This method will retrieve all teh categories from the DB and add them for the display page.
     */
    public ActionForward homePage(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        /*
         *
         */
        log.debug("Got a homePage action request!!!");
        List<ImageCategory> imageCategoryList = categoryMaintenanceService.findAll();
        ImageForm imageForm = new ImageForm();
        imageForm.setImageList(imageCategoryList);
        
        HttpSession session = request.getSession();
        session.setAttribute("imageForm", imageForm);
        
        /*
         * Create a list of packages for this packages jsp to display on the home page
         */
        PackageForm packageForm = new PackageForm();
        List<Packages> packageList = packagesMaintenanceService.findAll();
        log.debug("Size for package list is >"+packageList.size());
        packageForm.setPackageList(packageList);
        session.setAttribute("packageForm", packageForm);
        
        /*
         * Populate the about section
         */
        AboutForm aboutForm = new AboutForm();
        About about = aboutMaintenanceService.getAbout();
        log.debug("VAlue for about heading is >"+about.getAboutHeading()+"<");
        BeanUtils.copyProperties(aboutForm, about);
        session.setAttribute("aboutForm", aboutForm);
        
        return mapping.findForward("homePage");
    }
    
}
