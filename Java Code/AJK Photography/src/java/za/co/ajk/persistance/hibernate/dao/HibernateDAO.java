/*
 * HibernateDAO.java
 *
 * Created on May 22, 2007, 9:33 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.persistance.hibernate.dao;

import java.util.List;
import org.hibernate.SessionFactory;

/**
 *
 * @author andre.kapp
 */
public interface HibernateDAO extends DAO {
    
	public void setSessionFactory(SessionFactory pSessionFactory) throws DAOException;

        public List<DTO> getDTOClasses() throws DAOException;
        
}
