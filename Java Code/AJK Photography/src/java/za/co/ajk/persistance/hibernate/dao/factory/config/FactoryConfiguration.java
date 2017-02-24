/*
 * FactoryConfiguration.java
 *
 * Created on May 22, 2007, 9:49 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.persistance.hibernate.dao.factory.config;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author andre.kapp
 */
public class FactoryConfiguration implements Serializable, Configuration {
	
	private String name;
	private Map<String, Class> mDAOClassMap = new HashMap<String, Class>();
	private Properties mProperties = new Properties();
	private Map<String, Class> mDTOList = new HashMap<String, Class>();
	private Map<Class, String> mDAONameMap = new HashMap<Class, String>();
	
	public Map<String, Class> getDAOClassMap() {
		return this.mDAOClassMap;
	}
	public void setDAOClassMap(Map<String, Class> pClassMap) {
		this.mDAOClassMap = pClassMap;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String pName) {
		this.name = pName;
	}
	public void addDAO(String pName, Class pDAO) {
		this.mDAOClassMap.put(pName, pDAO);
	}

        public Map<String, Class> getDAOClasses() {
		return this.mDAOClassMap;
	}
	public Properties getProperties() {
		return this.mProperties;
	}
	public Object setProperty(String pPropertyName, String pPropValue) {
		return this.mProperties.setProperty(pPropertyName, pPropValue);
	}
	public Map<String, Class> getDTOClassList() {
		return this.mDTOList;
	}
	public Class addDTOClass(String name, Class pDTOClass) {
		return this.mDTOList.put(name, pDTOClass);
	}
	public Map<Class, String> getDAONameMap() {
		return this.mDAONameMap;
	}
	public void setDAONameMap(Map<Class, String> pNameMap) {
		this.mDAONameMap = pNameMap;
	}
	public String addDAONameMapping(Class pArg0, String pArg1) {
		return this.mDAONameMap.put(pArg0, pArg1);
	}
}
