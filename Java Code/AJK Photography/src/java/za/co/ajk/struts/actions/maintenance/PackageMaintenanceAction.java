/*
 * PackageMaintenanceAction.java
 *
 * Created on 19 March 2008, 11:21
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.struts.actions.maintenance;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import za.co.ajk.persistance.transferobjects.Packages;
import za.co.ajk.service.PackagesMaintenanceService;
import za.co.ajk.struts.actions.*;
import za.co.ajk.struts.forms.PackageForm;

/**
 *
 * @author akapp
 */
public class PackageMaintenanceAction extends BaseDispatchAction {
    
    private static Logger log = Logger.getLogger(PackageMaintenanceAction.class);
    private PackagesMaintenanceService packagesMaintenanceService = new PackagesMaintenanceService();
    
    /**
     *
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
     * Form mapping action only!
     */
    public ActionForward showAllPackageAdd(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        log.debug("Got a execute action request!!!");
        return mapping.findForward("showPackageAdd");
    }
    
    /**
     *
     */
    public ActionForward showPackageEdit(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        log.debug("Got a showPackageEdit action request!!!");
        
        PackageForm packageForm = (PackageForm)form;
        
        long packageId = Long.parseLong(request.getParameter("packageId"));
        
        Packages packages = new Packages();
        
        packages = packagesMaintenanceService.load(packageId);
        BeanUtils.copyProperties(packageForm, packages);
        
        return mapping.findForward("showPackageEdit");
    }
    
    /**
     *
     */
    public ActionForward packageDelete(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        log.debug("Got a packageDelete action request!!!");
        
        long packageId = Long.parseLong(request.getParameter("packageId"));
        
        Packages packages = new Packages();
        packages.setPackageId(packageId);
        
        packagesMaintenanceService.doLoadDelete(packages);
        
        return showAllPackageList(mapping, form, request, response);
    }
    
    /**
     *
     */
    public ActionForward packageAdd(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        log.debug("Got a packageAdd action request!!!");
        
        PackageForm packageForm = (PackageForm)form;
        
        Packages packages = new Packages();
        BeanUtils.copyProperties(packages, packageForm);
        
        packagesMaintenanceService.save(packages);
        
        return showAllPackageList(mapping, form, request, response);
    }
    
    /**
     *
     */
    public ActionForward packageUpdate(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        log.debug("Got a packageUpdate action request!!!");        
        PackageForm packageForm = (PackageForm)form;
        
        Packages packages = new Packages();
        packages = packagesMaintenanceService.load(packageForm.getPackageId());
        
        BeanUtils.copyProperties(packages, packageForm);
        packagesMaintenanceService.update(packages);

        return showAllPackageList(mapping, form, request, response);
    }
}
