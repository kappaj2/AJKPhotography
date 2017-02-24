/*
 * GalleryCodeLookupAction.java
 *
 * Created on 28 March 2008, 09:29
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.struts.actions.maintenance;

import java.io.PrintWriter;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import za.co.ajk.persistance.transferobjects.ImageCategory;
import za.co.ajk.persistance.transferobjects.ImageGallery;
import za.co.ajk.service.CategoryMaintenanceService;
import za.co.ajk.service.GalleryMaintenanceService;

/**
 *
 * @author akapp
 */
public class GalleryCodeLookupAction extends Action{
    
    private static Logger log = Logger.getLogger(GalleryCodeLookupAction.class);
    private GalleryMaintenanceService galleryMaintenanceService = new GalleryMaintenanceService();
    private CategoryMaintenanceService categoryMaintenanceService = new CategoryMaintenanceService();
    
    
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception{
        
        log.debug("Received a lookup call !!!!");
        
        String categoryCode = request.getParameter("CategoryCode");
        
        ImageCategory imageCategory = categoryMaintenanceService.load(Long.parseLong(categoryCode));
        
        StringBuffer strBuff = new StringBuffer();
        strBuff.append("<items>");
        
        Set<ImageGallery> imageGallerySet = imageCategory.getImageGalleries();
        
        if (imageGallerySet.size() > 0){
            for (ImageGallery elem : imageGallerySet) {
                
                long galleryCode = elem.getComp_id().getGalleryCode();
                String galleryDesc = elem.getGalleryDescription();
                
                log.debug("Value for galleryCode in this gallery is >"+galleryCode+"< and description >"+galleryDesc+"<");
                strBuff.append("<item>");
                strBuff.append("<display>");
                strBuff.append(galleryDesc);
                strBuff.append("</display>");
                strBuff.append("<value>");
                strBuff.append(galleryCode);
                strBuff.append("</value>");
                strBuff.append("</item>");
            }
            
        }else{ // no gallery for this category yet
            
            strBuff.append("<item>");
            strBuff.append("<display>");
            strBuff.append("No gallery for this category found");
            strBuff.append("</display>");
            strBuff.append("<value>");
            strBuff.append("0");
            strBuff.append("</value>");
            strBuff.append("</item>");
        }
        
        log.debug("CategoryCode in request is >"+categoryCode+"<");
        
        strBuff.append("</items>");
        
        String res = strBuff.toString();
        
        PrintWriter out = response.getWriter();
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        
        out.println(res);
        
        out.close();
        
        return null;
    }
}