/*
 * AbstractDAOFactory.java
 *
 * Created on May 22, 2007, 9:46 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.persistance.hibernate.dao.factory;

import za.co.ajk.persistance.hibernate.dao.DAOException;
import za.co.ajk.persistance.hibernate.dao.factory.config.FactoryConfigSaxHandler;

import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import org.apache.log4j.Logger;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 *
 * @author andre.kapp
 */
public class AbstractDAOFactory {
    
    private static Logger log = Logger.getLogger(AbstractDAOFactory.class);
    
    private static int[] mAccessLock = new int[0];
    private static AbstractDAOFactory mInstance = null;
    
    //Map DTO Classes to DAOFactory Classes.
    private Map<String, DAOFactory> mDAOMap;
    //Map Failsafe DAO's
    private Map<String, DAOFactory> mFailSafeDAOMap;
    
    /**
     *  Private constructor so no class can instantiate an instance of this.
     */
    private AbstractDAOFactory() throws DAOException {
        init();
    }
    
    private static AbstractDAOFactory getAbstractFactory() throws DAOException {
        synchronized (mAccessLock) {
            if(mInstance == null) {
                log.debug("creating new mInstance");
                mInstance = new AbstractDAOFactory();
            }
        }
        return mInstance;
    }
    /**
     *
     * @param pDTOName
     * @param pFailSafe
     * @return
     * @throws DAOException
     */
    public static DAOFactory getFactory(String pDAOName, boolean pFailSafe) throws DAOException {
        log.debug("Getting DAOFactory for DAOName >"+pDAOName+"<");
        //System.out.println("Getting DAOFactory for DAOName >"+pDAOName+"<");
        
        // This will create a new instance if null and populate the maps
        AbstractDAOFactory vAbstractDAOFactory = getAbstractFactory();
        
        return vAbstractDAOFactory.getDAOFactory(pDAOName, pFailSafe);
    }
    
    /**
     * Return a DAOFactory
     *
     * @param pDTOName
     * @return
     * @throws DAOException
     */
    public static DAOFactory getFactory(String pDTOName) throws DAOException {
        return getFactory(pDTOName, false);
    }
    
    /**
     * Configure this DAO Factory.  This method is called in the constructor
     * @throws DAOException
     */
    private void init() throws DAOException {
        
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("factories.xml");
        //URL test = this.getClass().getClassLoader().getResource("factories.xml");
        //System.out.println("Value for url is >"+test.toString());
              
        InputSource inputSource = new InputSource(inputStream);
        
        // contentHandler for sax events
        FactoryConfigSaxHandler contentHandler = new FactoryConfigSaxHandler();
        XMLReader vSAXParser;
        try {
            vSAXParser = XMLReaderFactory.createXMLReader();
        } catch (SAXException e) {
            throw new DAOException("Unable to create XMLReader", e);
        }
        
        vSAXParser.setContentHandler(contentHandler);
        
        // maps populated by content handler
        mDAOMap = contentHandler.getFactoryMap();
        mFailSafeDAOMap = contentHandler.getFailSafeFactoryMap();
        
        try {
            vSAXParser.parse(inputSource);
        } catch (Exception e) {
            throw new DAOException("Unable to parse XML", e);
        }
        
    }
    
    /**
     *  Return the AbstractDAOFactory name
     */
    public String getName() {
        return "AbstractDAOFactory";
    }
    
    /**
     *  Get a DAOFacoty from the map that was build up from the factories.xml file
     *  @RETURN DAOFactory
     *  @PARAM factoryName required
     *  @PARAM failSafe
     */
    
    protected DAOFactory getDAOFactory(String pName, boolean pFailSafe) {
        
        DAOFactory temp = mDAOMap.get(pName);
        
        if(pFailSafe) {
            return mFailSafeDAOMap.get(pName);
        } else {
            return mDAOMap.get(pName);
        }
    }
    
}
