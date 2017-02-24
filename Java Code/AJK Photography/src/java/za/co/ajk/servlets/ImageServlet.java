/*
 * ImageServlet.java
 *
 * Created on 17 March 2008, 12:06
 */

package za.co.ajk.servlets;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.log4j.Logger;
import za.co.ajk.common.util.FileUtility;
import za.co.ajk.persistance.transferobjects.AppProp;
import za.co.ajk.persistance.transferobjects.Image;
import za.co.ajk.persistance.transferobjects.ImageCategory;
import za.co.ajk.persistance.transferobjects.ImageGallery;
import za.co.ajk.persistance.transferobjects.ImageGalleryPK;
import za.co.ajk.persistance.transferobjects.ImagePK;
import za.co.ajk.service.AppPropMaintenanceService;
import za.co.ajk.service.CategoryMaintenanceService;
import za.co.ajk.service.GalleryMaintenanceService;
import za.co.ajk.service.ImageMaintenanceService;

/**
 *
 * @author akapp
 * @version
 */
public class ImageServlet extends HttpServlet {
    
    private static Logger log = Logger.getLogger(ImageServlet.class);
    private static final int BSIZE = 1024*1024;
    
    private CategoryMaintenanceService  categoryMaintenanceService = new   CategoryMaintenanceService();
    private GalleryMaintenanceService galleryMaintenanceService = new GalleryMaintenanceService();
    private ImageMaintenanceService imageMaintenanceService = new ImageMaintenanceService();
    
    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * This servlet will handle the incoming request and get the byte[] of the image requested.
     * This byte[] is then written to the output stream and displayed ion the JSP
     * The CategoryCode will be used for lookup of the image.
     *
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
//        if(log.isDebugEnabled()){
//            log.debug("---------------------------Received a servlet call ---------------------------------");
//        }
        
        response.setContentType("image/jpg");
        response.setHeader("Content-disposition","attachement");
        
        HttpSession session = request.getSession();
        String requestType = (String)request.getParameter("requestType");
        
        /*
         *  RequestType can be:
         *  1. category
         *  2. gallery
         *  3. image
         */
        
        byte[] imageData = null;
        if (requestType.equals("category")){
            
//            if(log.isDebugEnabled()){
//                log.debug("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//                log.debug(" Received a category request in image servlet  ");
//                log.debug("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//            }
            
            long categoryCode = Long.parseLong(request.getParameter("categoryCode"));
            session.setAttribute("categoryCode", categoryCode);
            
            if(log.isDebugEnabled()){
                log.debug("CategoryCode is >"+categoryCode+"<");
            }
            
            imageData = getCategoryImage(categoryCode);
            
            if(log.isDebugEnabled()){
                log.debug("Size for categorydata is >"+imageData.length);
            }
            
        }else if (requestType.equals("gallery")){
            
//            if(log.isDebugEnabled()){
//                log.debug("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//                log.debug(" Received a gallery request in image servlet  ");
//                log.debug("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//            }
            
            long galleryCode = Long.parseLong(request.getParameter("galleryCode"));
            
            if(log.isDebugEnabled()){
                log.debug("GalleryCode is >"+galleryCode+"<");
            }
            
            ImageGalleryPK imageGalleryPK = new ImageGalleryPK();
            imageGalleryPK.setGalleryCode(galleryCode);
            
            imageData = getGalleryImage(imageGalleryPK);
            
            if(log.isDebugEnabled()){
                log.debug("Size for gallerydata is >"+imageData.length);
            }
            
        }else if (requestType.equals("image")){
            
//            if(log.isDebugEnabled()){
//                log.debug("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//                log.debug(" Received a image request in image servlet  ");
//                log.debug("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//            }
            
            long imageCode = Long.parseLong(request.getParameter("imageCode"));
            
            if(log.isDebugEnabled()){
                log.debug("Value for imageCode is >"+imageCode+"<");
            }
            
            ImagePK imagePK = new ImagePK();
            imagePK.setImageCode(imageCode);
            
            imageData = getImage(imagePK, false);
            
            
        }else if (requestType.equals("imageBig")){
            
//            if(log.isDebugEnabled()){
//                log.debug("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//                log.debug(" Received a image request in image servlet  ");
//                log.debug("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//            }
            
            long imageCode = Long.parseLong(request.getParameter("imageCode"));
            
            if(log.isDebugEnabled()){
                log.debug("Value for imageCode is >"+imageCode+"<");
            }
            
            ImagePK imagePK = new ImagePK();
            imagePK.setImageCode(imageCode);
            
            imageData = getImage(imagePK, true);
            
        }else if (requestType.equals("logo")){
            
//            if(log.isDebugEnabled()){
//                log.debug("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//                log.debug(" Received a logo request in image servlet  ");
//                log.debug("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//            }
            
            AppPropMaintenanceService   appPropMaintenanceService = new AppPropMaintenanceService();
            AppProp appProp = new AppProp();
            appProp = appPropMaintenanceService.findProp("logo_name");
            
            imageData = getLogoData(appProp.getPropertyValue());
        }
        
        /*
         *  Now open an outputstream to the response and send the byte[] to the output stream.
         */
        OutputStream os = response.getOutputStream();
        
        os.write(imageData);
        os.close();
        
    }
    
    private byte[] getCategoryImage(long categoryCode){
        
        ImageCategory imageCategory = categoryMaintenanceService.load(categoryCode);
        byte[] categoryData =imageCategory.getCategoryImage();
        return categoryData;
        
    }
    
    private byte[] getGalleryImage(ImageGalleryPK imageGalleryPK){
        
        ImageGallery imageGallery = galleryMaintenanceService.loadByGalleryCode(imageGalleryPK.getGalleryCode());
        byte[] galleryData = imageGallery.getGalleryImage();
        return galleryData;
    }
    
    private byte[] getImage(ImagePK imagePK, boolean largeVersion){
        
        Image image = imageMaintenanceService.findByImageCode(imagePK.getImageCode(), largeVersion);
        byte[] imageData = image.getImageData();
        return imageData;
    }
    
    /**
     * This method will find the logo specified and then load and return the byte[]
     * @param String logoName
     * @return byte[]
     */
    private byte[] getLogoData(String logoName){
        FileUtility fileUtility = new FileUtility();
        byte[] logoData = fileUtility.getFileData(FileUtility.FileType.LOGO, logoName);
        return logoData;
    }
    
    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }
    
}
