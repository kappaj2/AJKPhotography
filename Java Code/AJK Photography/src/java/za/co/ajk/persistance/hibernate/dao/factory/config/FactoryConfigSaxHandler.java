/*
 * FactoryConfigSaxHandler.java
 *
 * Created on May 22, 2007, 9:58 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.persistance.hibernate.dao.factory.config;

import za.co.ajk.persistance.hibernate.dao.DAOException;
import za.co.ajk.persistance.hibernate.dao.factory.DAOFactory;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


/**
 *
 * @author andre.kapp
 */
public class FactoryConfigSaxHandler extends DefaultHandler {
    
    private static Logger log = Logger.getLogger(FactoryConfigSaxHandler.class);
    
    private Map<String, DAOFactory> mFactoryMap = new HashMap<String, DAOFactory>();
    private Map<String, DAOFactory> mFailSafeFactoryMap = new HashMap<String, DAOFactory>();
    
    private DAOFactory mCurrentFactory = null;
    private FactoryConfiguration mTempConfig = null;
    private String mTempPropertyName = null;
    private String mTempPropertValue = null;
    private String mCurrentDAOName = null;
    private StringBuffer mTempBuffer = new StringBuffer();
    private boolean mFailSafeFactory = false;
    
    public void startDocument(){
        System.out.println("Start Document event handler");
    }
    
    public void endDocument(){
        System.out.println("End document event handler");
    }
    
    public void startElement(String pURI, String pLocalName, String pQName, Attributes pAttributes) throws SAXException {
        
        log.debug("Getting startElement  pURIName >"+pURI+"< and localName >"+pLocalName+"< and qName >"+pQName+"<");
        
        if("factory".equals(pLocalName) || "factory".equals(pQName)) {
            
            mTempConfig = new FactoryConfiguration();
            mTempConfig.setName(pAttributes.getValue("name"));
            String vFactoryClassName = pAttributes.getValue("class");
            if(vFactoryClassName == null) {
                System.out.println("Factory Class name is >"+vFactoryClassName);
                log.warn("Unable to create Factory "+pAttributes.getValue("name")+", no class attribute set");
            }
            try {
                mCurrentFactory = (DAOFactory) Class.forName(vFactoryClassName).newInstance();
                if(Boolean.parseBoolean(pAttributes.getValue("failSafe"))) { //will only catch failSafe='true' otherwise false
                    mFailSafeFactory = true;
                }
            } catch (Exception e) {
                log.error("FATAL: unable to create Factory "+pAttributes.getValue("name"), e);
                throw new SAXException(e);
            }
        }
        
        if("property-name".equals(pLocalName) || "property-name".equals(pQName)) {
            //Make sure temp StringBuffer is empty
            mTempBuffer.setLength(0);
        }
        
        if("property-value".equals(pLocalName) || "property-value".equals(pQName)) {
            //Make sure temp StringBuffer is empty
            mTempBuffer.setLength(0);
        }
        
        if("dao".equals(pLocalName) || "dao".equals(pQName)) {//This a DAO definition
            String vDAOClassName = pAttributes.getValue("class");
            mCurrentDAOName = pAttributes.getValue("name");
            if(vDAOClassName != null) {
                try {
                    System.out.println("Addin DAO with name >"+mCurrentDAOName+"< to map");
                    mTempConfig.addDAO(mCurrentDAOName, Class.forName(vDAOClassName));
                } catch (ClassNotFoundException e) {
                    log.warn("Unable to start DAO "+mCurrentDAOName, e);
                    throw new SAXException(e);
                }
            }
        }
        
        if("dto".equals(pLocalName) || "dto".equals(pQName)) {//This a DTO definition
            String vDTOName = pAttributes.getValue("name");
            String vDTOClassName = pAttributes.getValue("class");
            if(vDTOName == null || "".equals(vDTOName)) {
                throw new IllegalStateException("Element dto MUST have name attribute set to a non-empty string");
            }
            if(vDTOClassName == null || "".equals(vDTOClassName)) {
                throw new IllegalStateException("Element dto MUST have class attribute set to a non-empty string");
            }
            try {
                
                Class vDTOClass = Class.forName(vDTOClassName);
                mTempConfig.addDTOClass(vDTOName, vDTOClass);
                mTempConfig.addDAONameMapping(vDTOClass,mCurrentDAOName);
                
                if(mFailSafeFactory) {
                    mFailSafeFactoryMap.put(vDTOName, mCurrentFactory);
                }else {
                    System.out.println("Adding DTOname >"+vDTOName+"< to mFactoryMap");
                    mFactoryMap.put(vDTOName, mCurrentFactory);
                }
                
            } catch (ClassNotFoundException e) {
                throw new SAXException("Unable configure DTO "+vDTOName, e);
            }
        }
    }
    
    public void characters(char[] ch, int start, int length) throws SAXException {
        mTempBuffer.append(ch, start, length);
    }
    
    public void endElement(String pURI, String pLocalName, String pQName) throws SAXException {

        
        if("factory".equals(pLocalName) || "factory".equals(pQName)) {
            //Initialise the current Factory using the config.
            try {
                mCurrentFactory.init(mTempConfig);
                mTempConfig = null;
                mFailSafeFactory = false;
            } catch (DAOException e) {
                String msg="Unable to initialise factory "+mTempConfig.getName();
                log.error(msg,e);
                throw new SAXException(msg,e);
            }
        }
        if("property-name".equals(pLocalName) || "property-name".equals(pQName)) {
            mTempPropertyName = mTempBuffer.toString();
            mTempBuffer.setLength(0);
        }
        if("property-value".equals(pLocalName) || "property-value".equals(pQName)) {
            mTempPropertValue = mTempBuffer.toString();
            mTempBuffer.setLength(0);
        }
        if("property".equals(pLocalName) || "property".equals(pQName)) {//Set the property
            mTempConfig.setProperty(mTempPropertyName, mTempPropertValue);
            mTempPropertValue = null;
            mTempPropertyName = null;
        }
        if("dao".equals(pLocalName) || "dao".equals(pQName)) {
            mCurrentDAOName = null;
        }
    }
    
    
    public Map<String, DAOFactory> getFactoryMap() {
        return this.mFactoryMap;
    }
    
    
    public Map<String, DAOFactory> getFailSafeFactoryMap() {
        return mFailSafeFactoryMap;
    }
    
    
}
