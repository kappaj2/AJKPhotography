/*
 * DAOFactory.java
 *
 * Created on May 22, 2007, 9:47 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.persistance.hibernate.dao.factory;

import za.co.ajk.persistance.hibernate.dao.DAO;
import za.co.ajk.persistance.hibernate.dao.DAOException;
import za.co.ajk.persistance.hibernate.dao.factory.config.Configuration;
import org.hibernate.SessionFactory;

/**
 *
 * @author andre.kapp
 */
public interface DAOFactory {
	public String getName();
        public SessionFactory getSessionFactory();
	public <T extends DAO> T getDAO(String pDTOName) throws DAOException;
	public void init(Configuration configuration) throws DAOException;
}
