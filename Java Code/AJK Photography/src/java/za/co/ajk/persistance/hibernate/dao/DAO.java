/*
 * DAO.java
 *
 * Created on May 22, 2007, 9:32 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.persistance.hibernate.dao;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author andre.kapp
 */
public interface DAO extends Serializable {
    /**
     * Update the object in the database.
     *
     * @param obj The object to update.
     */
    public <T extends DTO> void update( T obj ) throws DAOException;
    /**
     * Delete a given Data Object
     * @param obj The Object to delete
     */
    public <T extends DTO> void delete(T obj) throws DAOException;
    /**
     * Insert the object into the persistent store.
     *
     * @param obj The object to save.
     *
     * @return The primary key of the newly inserted object.
     */
    public <T extends DTO> Serializable save( T obj ) throws DAOException;
    /**
     * Load a given DTO given the DTO Class and the primary key.
     * @param cls  The DTO Class
     * @param pPrimaryKey The primary key for the required DTO
     * @return
     * @throws DAOException
     */
    public <T extends DTO>T load(Class<T> cls, Serializable pPrimaryKey) throws DAOException;
    /**
     * Search For a given DTO.
     * @param pFilterList The list of filters to bring
     * @param pDTOClass The DTO for the resulting class
     * @return A list of results in which the result will be the DTO Class given as the second parameter.
     * @throws DAOException
     */
    public <T extends DTO> List search(List<LookupFilter> pFilterList, Class<T> pDTOClass) throws DAOException;
    public <T extends DTO> List search(List<LookupFilter> pFilterList, Class<T> pDTOClass, String pSortField) throws DAOException;
    public <T extends DTO> List search(List<LookupFilter> pFilterList, Class<T> pDTOClass, String pSortField, int pMaxResults) throws DAOException;
    
    public String getIdentifierPropertyName(Class<DTO> pDTOClass);
}
