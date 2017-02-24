/*
 * AboutMaintenanceService.java
 *
 * Created on 27 March 2008, 09:33
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.service;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import za.co.ajk.common.exception.CustomException;
import za.co.ajk.persistance.daos.AboutDAO;
import za.co.ajk.persistance.hibernate.dao.factory.AbstractDAOFactory;
import za.co.ajk.persistance.hibernate.dao.factory.DAOFactory;
import za.co.ajk.persistance.transferobjects.About;

/**
 *
 * @author akapp
 */
public class AboutMaintenanceService {
    
    private DAOFactory aboutFactory;
    private AboutDAO aboutDAO;
    private Session hibSession;
    
    private static Logger log = Logger.getLogger(AboutMaintenanceService.class);
    
    /** Creates a new instance of AboutMaintenanceService */
    public AboutMaintenanceService() throws CustomException {
        aboutFactory = AbstractDAOFactory.getFactory("About");
        aboutDAO = aboutFactory.getDAO("About");
        hibSession = aboutDAO.getSession();
    }
    
    /**
     *  This method will return the first record in the database. There should only be one.
     * @return About
     * @throws CustomException
     */
    public About getAbout() throws CustomException{
        List<About> aboutList = aboutDAO.findAll(About.class);
        About about;
        
        if (aboutList.size() == 0){
            about = new About();
        }else{
            about = aboutList.get(0);
        }
        
        return about;
    }
    
    /**
     * This method will atttempt to load reccord from the database.
     * If found then update else load new one to database
     * @param About
     * @throws CustomException
     */
    public void saveOrUpdate(About about) throws CustomException{
        About aboutTemp = getAbout();
        
        log.debug("Value for about id is >"+aboutTemp.getId()+"<");
        
        if (aboutTemp == null || aboutTemp.getId() == null){
            aboutTemp = about;
            aboutTemp.setId(1L);
            aboutDAO.save(aboutTemp, true);
        }else{
            aboutTemp.setAboutDescription(about.getAboutDescription());
            aboutTemp.setAboutHeading(about.getAboutHeading());
            aboutDAO.update(aboutTemp, hibSession);
        }
    }
}
