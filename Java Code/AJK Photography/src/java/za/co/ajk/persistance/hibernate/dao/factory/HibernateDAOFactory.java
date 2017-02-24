/*
 * HibernateDAOFactory.java
 *
 * Created on May 22, 2007, 9:47 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.persistance.hibernate.dao.factory;

import za.co.ajk.persistance.hibernate.dao.DAO;
import za.co.ajk.persistance.hibernate.dao.DAOException;
import za.co.ajk.persistance.hibernate.dao.HibernateDAO;

import java.util.Map;

import org.apache.log4j.Logger;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author andre.kapp
 */
public class HibernateDAOFactory implements DAOFactory {

    private Map<String, Class> mapDAOMap = null;
    private Map<String, Class> mapDTOClassMap = null;
    private Map<Class, String> mapDAONameMap = null;
    private SessionFactory sessionFactory;
    private String mapName;

    public void init(Configuration pConfiguration) throws DAOException {
    }
    
    private static Logger log = Logger.getLogger(HibernateDAOFactory.class);
    
    public  HibernateDAOFactory() {
    }
    
    public SessionFactory getSessionFactory(){
        return sessionFactory;
    }
    
    public <T extends DAO> T getDAO(String pDTOName) throws DAOException {
        if(mapDTOClassMap == null) {//Not initialised, error condition
            throw new IllegalStateException("DAO Factory "+this.getClass().getName()+" has not been initialized. No DTOs defined");
        }
        Class<T> vDTOClass = mapDTOClassMap.get(pDTOName);
        
        if(vDTOClass==null) {
            throw new DAOException("Unable to get DAO for DTO "+pDTOName+".  None has been defined");
        }
        
        try {
            return getDAO(vDTOClass);
        } catch (Exception e) {
            //Need to do this to make sure messages from getDAO(Class do not confuse debugger
            throw new DAOException("Unable to get DAO for DTO "+pDTOName, e);
        }
    }

    
    private <T extends DAO> T getDAO(Class<T> pDTOClass) throws DAOException {
        if(mapDAOMap == null) {//Not initialised, error condition
            throw new IllegalStateException("DAO Factory "+this.getClass().getName()+" has not been initialized. No DAOs defined");
        }
        if(mapDAONameMap == null) {//Not initialised, error condition
            throw new IllegalStateException("DAO Factory "+this.getClass().getName()+" has not been initialized. No DTOs defined");
        }
        log.debug("DTOMap: "+mapDAONameMap);
        log.debug("DAOMap: "+mapDAOMap);
        
        String vDAOName = mapDAONameMap.get(pDTOClass);
        Class vDAOClass = mapDAOMap.get(vDAOName);
        if(vDAOClass == null) {
            throw new DAOException("No DAO with name "+vDAOName+" has been defined");
        }
        
        try {
            HibernateDAO vDAOInstance = (HibernateDAO) vDAOClass.newInstance();
            vDAOInstance.setSessionFactory(sessionFactory);
            return (T) vDAOInstance;
        } catch (Exception e) {
            throw new DAOException("Unable to instantiate DAO Instance for "+vDAOName,e);
        }
    }
    
    /**
     * Configure the HibernateDAOFactory.  This will do the following:
     * <li>Create a Hibernate SessioFactory to be used by the DAOs.
     * <li>Configure the session factory with the appropriate Hibernate mappings
     * @param The configuration for this DAOFactory.  Obtained from the xml configuartion
     *
     * @see DAOFactory#init(Configuration)
     */
    public void init(za.co.ajk.persistance.hibernate.dao.factory.config.Configuration configuration) throws DAOException {
        System.out.println("Doing getDAOClass map");
        mapDAOMap = configuration.getDAOClasses();
        
        System.out.println("Doing getDTOClasses map");
        mapDTOClassMap = configuration.getDTOClassList();
        
        System.out.println("Doing getDAOName map");
        mapDAONameMap = configuration.getDAONameMap();
        
        /*
         * create a Hibernate Configuration object and configure it. Use the hibernate.cfg.xml file.
         */
        org.hibernate.cfg.Configuration vHibernateConfiguration = new Configuration().configure();
        
        // Register the base class used for in memory persistence.
        // All classes using the custom in memory persistence must extend from this class.
        // It is however never referenced directly so it needs to be custom loaded for hibernate.
        // Loop through DTO and Add to the configuration.
        
        for (Class vDTOClass: configuration.getDTOClassList().values()) {
            
            vHibernateConfiguration.addClass(vDTOClass);
        }
        //Create me a sessionFactory..
        sessionFactory = vHibernateConfiguration.buildSessionFactory();
    }
    
    public String getName() {
        return mapName;
    }
    
    public void setName(String pName) {
        mapName=pName;
    }
    
    
}
