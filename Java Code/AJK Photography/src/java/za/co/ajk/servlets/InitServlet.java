/*
 * InitServlet.java
 *
 * Created on 02 April 2008, 08:59
 */

package za.co.ajk.servlets;

import java.io.*;

import javax.servlet.*;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import za.co.ajk.common.util.FileUtility;
import za.co.ajk.persistance.transferobjects.AppProp;
import za.co.ajk.service.AppPropMaintenanceService;

/**
 *
 * @author akapp
 * @version
 */
public class InitServlet  extends GenericServlet {
    
    private ServletContext context = null;
    private static Logger log = Logger.getLogger(InitServlet.class);
    
    public void init(ServletConfig config) throws ServletException {
        try {
            AppPropMaintenanceService appPropMaintenanceService = new AppPropMaintenanceService();
            
            String sc = config.getServletContext().getRealPath("/");
            System.out.println("Value for sc >" + sc + "<");
            
            String initF = config.getServletContext().getInitParameter(
                    "log4j-init-file");
            String comp = sc + initF;
            
            System.out.println("Value for compiled file >" + comp);
            
            DOMConfigurator.configureAndWatch(comp, 1000 * 60*5); // check every 5 minutes for changes
            
            /*
             * Log default entry to all categories to start file writing
             * process. This will force logfile switching and clearing of any
             * caches.
             */
            
            log.debug("Starting up log4j");
            log.info("Starting up log4j");
            log.warn("Starting up log4j");
            log.error("Starting up log4j");
            log.fatal("Starting up log4j");
            
            /*
             * Configure the stylesheet for this application
             */
            FileUtility fileUtility = new FileUtility();
            fileUtility.copyStylesheetToActive();
            
            /*
             *  Load system properties into session.
             */
            context = config.getServletContext();
            
            /*
             *  Set the images size attribute.
             *  This will be the height the images are sized to.
             */
            AppProp appProp = appPropMaintenanceService.load("categories_height_size");
            String categoriesHeight = appPropMaintenanceService.getCorrectValue(appProp);
            
            appProp = appPropMaintenanceService.load("galleries_height_size");
            String galleriesHeight = appPropMaintenanceService.getCorrectValue(appProp);

            appProp = appPropMaintenanceService.load("images_height_size");
            String imagesHeight = appPropMaintenanceService.getCorrectValue(appProp);

            context.setAttribute("CategoriesHeight", categoriesHeight);
            context.setAttribute("GalleriesHeight", galleriesHeight);
            context.setAttribute("ImagesHeight", imagesHeight);
            
        } catch (Exception ex) {
                        /*
                         * print the stacktrace to the console log as no logger is active
                         * yet to handle any error logging
                         */
            ex.printStackTrace();
            throw new RuntimeException("Error initializing log4j");
        }
    }
    
    public void service(ServletRequest req, ServletResponse res)
    throws ServletException, IOException {
        PrintWriter out = res.getWriter();
        out.println(System.getenv().toString());
        out.close();
    }
    
}
