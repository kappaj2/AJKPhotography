/*
 * PackageAction.java
 *
 * Created on 14 March 2008, 02:07
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
import za.co.ajk.persistance.transferobjects.ImageCategory;
import za.co.ajk.persistance.transferobjects.Packages;
import za.co.ajk.service.CategoryMaintenanceService;
import za.co.ajk.service.PackagesMaintenanceService;
import za.co.ajk.struts.forms.ImageForm;
import za.co.ajk.struts.forms.PackageForm;

/**
 *
 * @author akapp
 */
public class PackageAction extends BaseDispatchAction {
    
    private static Logger log = Logger.getLogger(PackageAction.class);
    
    private PackagesMaintenanceService packagesMaintenanceService = new PackagesMaintenanceService();
    private CategoryMaintenanceService categoryMaintenanceService = new CategoryMaintenanceService();
    
    /**
     * This method will retrieve a master list of all the packages defined.
     */
    public ActionForward showAllPackageList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        PackageForm packageForm = (PackageForm)form;
        List<Packages> packageList = packagesMaintenanceService.findAll();
        
        packageForm.setPackageList(packageList);
        
        log.debug("Got a execute action request!!!");
        return mapping.findForward("showPackageList");
    }
    
    /**
     * This method will display the package detail recieved.
     * Because the categories are listed on top by default, it is possible that they have been cleared due to a navigation right down to
     * the last image display. An imageform reset would have cleared them.
     * This will cause a problem if the package detail is activated. Normal navigation, however, would not cause this. It is just
     * because of the back button on the browser.
     *
     */
    public ActionForward showPackageDetail(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        log.debug("Got a showPackageDetail action request!!!");
        PackageForm packageForm = (PackageForm)form;
        
        long packageId = Long.parseLong(request.getParameter("packageId"));
        log.debug("recieved packageId value of >"+packageId+"<");
        
        Packages packages = packagesMaintenanceService.load(packageId);
        
        log.debug("Got package >"+packages.getPackageName());
        
        BeanUtils.copyProperties(packageForm, packages);
        
        HttpSession session = request.getSession();
        session.setAttribute("packageForm", packageForm);
        
        /*
         * Re-populate the imageList with the "root" categories.
         */
        ImageForm imageForm = (ImageForm)session.getAttribute("imageForm");
        
        List<ImageCategory> imageCategoryList = categoryMaintenanceService.findAll();
        imageForm.setImageList(imageCategoryList);
        session.setAttribute("imageForm", imageForm);
        
        return mapping.findForward("showPackageDetail");
    }
    
}
