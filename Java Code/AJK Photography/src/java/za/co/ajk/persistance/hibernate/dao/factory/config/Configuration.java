/*
 * Configuration.java
 *
 * Created on May 22, 2007, 9:48 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.persistance.hibernate.dao.factory.config;

import java.util.Map;
import java.util.Properties;

/**
 *
 * @author andre.kapp
 */
public interface Configuration {

	public Map<String, Class> getDAOClasses();
	public Properties getProperties();
	public Map<String, Class> getDTOClassList();
	public Map<Class, String> getDAONameMap();
}