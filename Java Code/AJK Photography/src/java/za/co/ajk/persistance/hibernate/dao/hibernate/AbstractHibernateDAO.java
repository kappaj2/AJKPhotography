/*
 * AbstractHibernateDAO.java
 *
 * Created on May 22, 2007, 10:00 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.persistance.hibernate.dao.hibernate;

import za.co.ajk.common.util.UniversalComparator;
import za.co.ajk.persistance.hibernate.dao.DAOException;
import za.co.ajk.persistance.hibernate.dao.DTO;
import za.co.ajk.persistance.hibernate.dao.HibernateDAO;
import za.co.ajk.persistance.hibernate.dao.LookupFilter;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import za.co.ajk.common.exception.CustomException;
import za.co.ajk.common.exception.ErrorCode;
import za.co.ajk.common.exception.Layer;

/**
 *
 * @author andre.kapp
 */
public abstract class AbstractHibernateDAO implements HibernateDAO {
    
    private static Logger log = Logger.getLogger(AbstractHibernateDAO.class);
    
    protected SessionFactory mSessionFactory;
    
    private List<DTO> mDTOClasses;
    
    /**
     * Set the DTO Classes which this DAO will handle
     *
     * @param pClasses
     *            The list of DTO Classes for this DAO.
     */
    public void setDTOClasses(List<DTO> pClasses) {
        this.mDTOClasses = pClasses;
    }
    
    /**
     * Set the Session Factory for this DAO
     *
     * @param pSessionFactory
     *            The new Session Factory
     *
     */
    public void setSessionFactory(SessionFactory pSessionFactory) throws DAOException {
        mSessionFactory = pSessionFactory;
    }
    
    public SessionFactory getSessionFactory(){
        return mSessionFactory;
    }
    
    /**
     * Get a list of DTO Classes handled by this DAO
     *
     * @return The list of DTO Classes
     *
     */
    public List<DTO> getDTOClasses() throws DAOException {
        return mDTOClasses;
    }
    
    /**
     * Load an object from the database
     *
     * @param pPrimaryKey
     *            The primary key of the object
     * @return the DTO object with the given primary key
     * @throws HibernateException
     */
    public <T extends DTO>T load(Class<T> cls, Serializable pPrimaryKey) throws HibernateException {
        if (pPrimaryKey == null) {
            throw new HibernateException("Primary key cannot be null.");
        }
        log.debug("Received a load command in AbstractHibernateDAO");
        Session vHibernateSession = getSession();
        return (T) vHibernateSession.load(cls, pPrimaryKey);
    }
    
    /**
     * Load an object from the database
     *
     * @param pPrimaryKey
     *            The primary key of the object
     * @return the DTO object with the given primary key
     * @throws HibernateException
     */
    public <T extends DTO>T load(Class<T> cls, Serializable pPrimaryKey, Session session) throws HibernateException {
        if (pPrimaryKey == null) {
            throw new HibernateException("Primary key cannot be null.");
        }
        log.debug("Received a load command in AbstractHibernateDAO");
        return (T) session.load(cls, pPrimaryKey);
    }
    
    
    /**
     * Save an Object to persistent store.  if required also flush the session.
     *
     */
    public <T extends DTO>Serializable save(Object obj, boolean flush) throws HibernateException {
        
        Session hibSession = getSession();
        Transaction tx = hibSession.beginTransaction();
        
        Serializable retval;
        
        if(flush) {
            hibSession.setCacheMode(CacheMode.IGNORE);
            retval=hibSession.save(obj);
            hibSession.flush();
            hibSession.clear();
        } else {
            retval = hibSession.save(obj);
        }
        tx.commit();
        
        return retval;
    }
    
    /**
     * Insert the object into the database.
     *
     * @param obj
     *            The object to save.
     *
     * @return The primary key of the newly inserted object.
     * CHANGE LOG:
     * Changed to true to get NON-DB generated PK values to save - else if false Hibernate
     * will not save but will also not through any exceptions
     */
    public <T extends DTO> Serializable save(T obj) throws HibernateException {
        log.debug("Received a save command in AbstractHibertnateDAO");
        return save(obj, true);
    }
    
    /**
     * Update the object in the database.
     *
     * @param obj
     *            The object to update.
     */
    public <T extends DTO> void update(T obj) throws HibernateException {
        log.debug("Received an update call in AbstractHibernateDAO");
        Session vHibernateSession = getSession();
        
        Transaction tx = vHibernateSession.beginTransaction();
        
        vHibernateSession.update(obj);
        tx.commit();
        
        vHibernateSession.flush();
        vHibernateSession.clear();
        
    }
    
    
    /**
     * Update the object in the database.
     *
     * @param obj
     *            The object to update.
     */
    public <T extends DTO> void update(T obj, Session vHibernateSession) throws HibernateException {
        log.debug("Received an update call in AbstractHibernateDAO with session");
        
        Transaction tx = vHibernateSession.beginTransaction();
        vHibernateSession.update(obj);
        tx.commit();
        vHibernateSession.flush();
        vHibernateSession.clear();
    }
    
    /**
     * Find all available elements
     *
     * @return
     * @throws DAOException
     */
    public List findAll(Class<? extends DTO> pDTOClass) throws DAOException {
        log.debug("Received a findAll call in AbstractHibernateDAO");
        String hql ="from "+pDTOClass.getSimpleName();
        Query query = getSession().createQuery(hql);
        return query.list();
    }
    
    /**
     * Find data by column name using Hibernate QBE functionality
     * @PARAM Class DTO class of object to be returned
     * @PARAM String columnName to be used in the where clause
     * @PARAM String columnValue to be used in the where clause
     * @RETURN List of Class objects found
     * @THROWS DAOException
     * @DEPRECATED Rather use the the findByQBE method
     */
    public List findDataByColumn(Class<? extends DTO> pDTOClass,
            String columnName,
            Object columnValue) throws DAOException {
        
        log.debug("Received a findDataByColumn call in AbstractHibernateDAO");
        log.debug("pDTOClass is >"+pDTOClass.getSimpleName()+"< and column >"+columnName+"< and value >"+columnValue);
        
        Session vHibernateSession = getSession();
        Criteria criteria = vHibernateSession.createCriteria(pDTOClass);
        criteria.add(Expression.eq(columnName, columnValue));
        List queryList = criteria.list();
        
        log.debug("List size in DAO is >"+queryList.size()+"<");
        
        return queryList;
        
    }
    
    /**
     * Find data by column name using Hibernate QBE functionality
     * @PARAM Class DTO class of object to be returned
     * @PARAM String columnName to be used in the where clause
     * @PARAM String columnValue to be used in the where clause
     * @RETURN List of Class objects found
     * @THROWS DAOException
     * @DEPRECATED Rather use the the findByQBE method
     */
    public List findDataByColumn(Class<? extends DTO> pDTOClass,
            String columnName,
            Object columnValue,
            Session session) throws DAOException {
        
        log.debug("Received a findDataByColumn call in AbstractHibernateDAO");
        log.debug("pDTOClass is >"+pDTOClass.getSimpleName()+"< and column >"+columnName+"< and value >"+columnValue);
        
        Criteria criteria = session.createCriteria(pDTOClass);
        criteria.add(Expression.eq(columnName, columnValue));
        List queryList = criteria.list();
        
        log.debug("List size in DAO is >"+queryList.size()+"<");
        
        return queryList;
        
    }
    
    
    /**
     * Find data by column name using Hibernate QBE functionality
     * @PARAM Class DTO class of object to be returned
     * @PARAM String columnName to be used in the where clause
     * @PARAM String columnValue to be used in the where clause
     * @RETURN List of Class objects found
     * @THROWS DAOException
     * @DEPRECATED Rather use the the findByQBE method
     */
    public List findDataByColumn(Class<? extends DTO> pDTOClass,
            String columnName,
            Object columnValue,
            String columnName2,
            Object columnValue2,
            Session session) throws DAOException {
        
        log.debug("Received a findDataByColumn call in AbstractHibernateDAO");
        log.debug("pDTOClass is >"+pDTOClass.getSimpleName()+"< and column >"+columnName+"< and value >"+columnValue);
        log.debug("pDTOClass is >"+pDTOClass.getSimpleName()+"< and column2 >"+columnName2+"< and value2 >"+columnValue2);
        
        Criteria criteria = session.createCriteria(pDTOClass);
        criteria.add(Expression.eq(columnName, columnValue));
        criteria.add(Expression.eq(columnName2, columnValue2));
        List queryList = criteria.list();
        
        log.debug("List size in DAO is >"+queryList.size()+"<");
        
        return queryList;
        
    }
    /**
     *  @PARAM Class type to query with
     *  @PARAM Object of type DTO to query with
     *  @RETURN List of objects found
     *  @THROWS DAOException
     */
    public List<? extends DTO> findByQBE(Class<? extends DTO> _parDTOClass, Object obj) throws DAOException{
        
        Example dto = Example.create(obj).ignoreCase().enableLike(MatchMode.EXACT);
        dto.excludeZeroes();
        
        Session session = getSession();
        Criteria criteria = session.createCriteria(_parDTOClass);
        criteria.add(dto);
        
        List<? extends DTO> queryList = criteria.list();
        return queryList;
    }
    
    /**
     *  @PARAM Class type to query with
     *  @PARAM Object of type DTO to query with
     *  @RETURN List of objects found
     *  @THROWS DAOException
     */
    public List<? extends DTO> findByQBE(Class<? extends DTO> _parDTOClass, Object obj, Session session) throws DAOException{
        
        Example dto = Example.create(obj).ignoreCase().enableLike(MatchMode.EXACT);
        dto.excludeZeroes();
        
        Criteria criteria = session.createCriteria(_parDTOClass);
        criteria.add(dto);
        
        List<? extends DTO> queryList = criteria.list();
        return queryList;
    }
    /*=========================================================================================================
     *
     * NOTE:
     * For delete we have to do a bit more than just delete. For each of the cascade="delete" options we first
     * need to load the master object before deleting it. This will ensure that the child records are also
     * deleted that are marked with the cascade option.
     * Just deleting the master object will throw a ForeignKey exception as Hibernate will NOT attempt to delete
     * the child records first. This is a bit stupid but ja, what more can I say.
     *
     * Design philosophy:
     * This delete method will NOT be responsible for the load due to above.
     * The respective DAO's/Services will be required to handle it due to PK values differing, etc.....
     *
     *=========================================================================================================
     */
    
    /**
     * Delete a given Data Object
     *
     * @param obj
     *            The Object to delete
     */
    public <T extends DTO> void delete(T obj) {
        log.debug("Received a delete call in AbstractHibernateDAO");
        Session vHibernateSession = getSession();
        Transaction tx = vHibernateSession.beginTransaction();
        
        vHibernateSession.delete(obj);
        tx.commit();
        vHibernateSession.flush();
    }
    
    /**
     * Delete a given Data Object
     *
     * @param obj
     *            The Object to delete
     */
    public <T extends DTO> void delete(T obj, Session vHibernateSession) {
        log.debug("Received a delete call in AbstractHibernateDAO");
        Transaction tx = vHibernateSession.beginTransaction();
        vHibernateSession.delete(obj);
        tx.commit();
        vHibernateSession.flush();
        
    }
    
    /**
     * Return a list of Objects matching the Search criteria. If the search criteria is set to null, the all objects of
     * class pDTOClass are returned.
     *
     * @param pFilterList
     *            A list of FilterDTO objecst used to filter the objects.
     * @param pDTOClass
     *            A Class implementing the DTO marker interface. Type checking is just done for sanity's sake.
     * @return The Search Results
     */
    public <T extends DTO>List search(List<LookupFilter> pFilterList, Class<T> pDTOClass) throws DAOException {
        return search(pFilterList, pDTOClass, null);
    }
    
    public <T extends DTO>List search(List<LookupFilter> pFilterList, Class<T> pDTOClass, String pSortField) throws DAOException {
        return search(pFilterList, pDTOClass, pSortField, Integer.MAX_VALUE);
    }
    
    public <T extends DTO>List search(List<LookupFilter> pFilterList, Class<T> pDTOClass, String pSortField, int pMaxResults) throws DAOException {
        Criteria vCriteria = getCriteria(pFilterList, pDTOClass);
        if(pMaxResults<Integer.MAX_VALUE) {
            vCriteria.setMaxResults(pMaxResults);
        }
        if(pSortField != null) {
            Order vSortOrder = Order.asc(pSortField);
            vCriteria.addOrder(vSortOrder);
        }
        List vResult = search(vCriteria);
        if(pSortField !=null ) {//Sort the result and return
            Comparator vComparator;
            try {
                vComparator = new UniversalComparator(this.getAccessorMethodName(pSortField, pDTOClass), UniversalComparator.ASCENDING);
            } catch (IntrospectionException e) {
                throw new DAOException(e);
            }
            Collections.sort(vResult, vComparator);
        }
        return vResult;
        
    }
    
    private List search(Criteria pCriteria) {
        List vResults = pCriteria.list();
        return new ArrayList(new HashSet(vResults)); // Ugly but required to bury dupes
    }
    
    /**
     * Get a set of Criteria given a FilterList Object.
     *
     * @param pFilterList
     *            The list of FilterList Object
     * @param pDTOClass
     *            The JavaBean in which the result will be held
     * @return The Search Criteria
     */
    protected <T extends DTO>Criteria getCriteria(List<LookupFilter> pFilterList, Class<T> pDTOClass) {
        
        Session vHibernateSession = getSession();
        
        Criteria vCriteria = vHibernateSession.createCriteria(pDTOClass);
        for (LookupFilter vFilter : pFilterList) {
            Criterion vRestriction = null;
            switch (vFilter.getOperator()) {
                case LookupFilter.OPERATION_EQUALS:
                    vRestriction = Restrictions.eq(vFilter.getPropertyName(), vFilter.getObjectValue());
                    break;
                case LookupFilter.OPERATION_GREATER:
                    vRestriction = Restrictions.gt(vFilter.getPropertyName(), vFilter.getObjectValue());
                    break;
                case LookupFilter.OPERATION_LESS:
                    vRestriction = Restrictions.lt(vFilter.getPropertyName(), vFilter.getObjectValue());
                    break;
                case LookupFilter.OPERATION_LIKE:
                    vRestriction = new HibernateLikeExpression(vFilter.getPropertyName(), vFilter.getObjectValue(), false);
                    break;
                case LookupFilter.OPERATION_LIKE_CASE_INSENSITIVE:
                    vRestriction = new HibernateLikeExpression(vFilter.getPropertyName(), vFilter.getObjectValue(), true);
                    break;
                case LookupFilter.OPERATION_NOT_EQUALS:
                    vRestriction = Restrictions.ne(vFilter.getPropertyName(), vFilter.getObjectValue());
                    break;
                default: // Ignore, don't do nada...
                    break;
            }
            if (vRestriction != null) { // We have a valid Filter
                vCriteria.add(vRestriction);
            }
        }
        return vCriteria;
    }
    
    /**
     * Get the current Hibernate Session
     *
     * @return The active Hibernate Session.
     */
    public Session getSession() {
        Session vHibernateSession = null;
        vHibernateSession = mSessionFactory.openSession();
        
//        System.out.println("isOpen "+vHibernateSession.isOpen());
//        System.out.println("Is dirty >"+vHibernateSession.isDirty());
//        System.out.println("Connected is >"+vHibernateSession.isConnected());
        
        
        return vHibernateSession;
        
    }
    
    /**
     * Get the name of the Identifier Class Name
     *
     * @param pDTOClass
     *            The DTO Class
     * @return The name of the identifier property as a String.
     * @throws HibernateException
     */
    public String getIdentifierPropertyName(Class<DTO> pDTOClass) throws HibernateException {
        ClassMetadata vClassMetadata = this.mSessionFactory.getClassMetadata(pDTOClass);
        return vClassMetadata.getIdentifierPropertyName();
    }
    
    
    protected String getAccessorMethodName(String field, Class javaBeanClass) throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(javaBeanClass);
        //Extract the method to get the given property...
        String methodName = null;
        PropertyDescriptor [] descriptorList = beanInfo.getPropertyDescriptors();
        if(field!=null) {
            for (PropertyDescriptor vDescriptor : descriptorList) {
                if(field.equals(vDescriptor.getName())) {
                    Method method = vDescriptor.getReadMethod();
                    methodName = method.getName();
                    break;
                }
            }
        }
        
        return methodName;
    }
    
    /**
     *  This method will load the file data in byte[] format from the file system. This will be used to send the data
     *  to the client.
     *  @param String fileURL The file URL where the file is located on the file system
     *  @return byte[] Returns a byte[] representation of the data on the file system
     *  @throws HFException
     */
    public byte[] getByteArrayForFile(String fileURL) throws CustomException{
        
        log.debug("Doing byte[] fetch for file >"+fileURL);
        
        byte[] bArr = null;
        try{
            File file = new File(fileURL);
            
            long fileByteSize = file.length();
            log.debug("Size for file to load is >"+fileByteSize);
            
            FileChannel fc =
                    new FileInputStream(file).getChannel();
            
            ByteBuffer buff = ByteBuffer.allocate((int)fileByteSize);
            fc.read(buff);
            
            
            bArr = buff.array();
            fc.close();
            
        }catch(FileNotFoundException fne){
            fne.printStackTrace();
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.PERSISTENCE, "Unable to read file "+fileURL+" from the file system", fne);
        }catch (IOException ie){
            ie.printStackTrace();
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.PERSISTENCE, "Unable to read file "+fileURL+" from the file system", ie);
        }
        
        return bArr;
    }
}
