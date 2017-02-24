/*
 * HFDocumentManagementDAO.java
 *
 * Created on 09 January 2008, 03:15
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.persistance.daos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import za.co.ajk.common.exception.ErrorCode;
import za.co.ajk.common.exception.CustomException;
import za.co.ajk.common.exception.Layer;
import za.co.ajk.persistance.hibernate.dao.DAOException;
import za.co.ajk.persistance.hibernate.dao.DTO;
import za.co.ajk.persistance.hibernate.dao.hibernate.AbstractHibernateDAO;
import za.co.ajk.persistance.transferobjects.OnlineDocument;

/**
 *
 * @author akapp
 *
 * OnlineDocumentData will NOT be loaded via the super.findAll() method.
 * The findAll method will only return the list of valid objects in the DB. This containt a URL which will be used to actually load
 * the data to be send to the users interface.
 */
public class OnlineDocumentDAO extends AbstractHibernateDAO {
    
    private static Logger log = Logger.getLogger(OnlineDocumentDAO.class);
    private static final int BSIZE = 1024;
    
    private static final String ARTICLES_PATH    = "/opt/appdocs/documents/";
    private static final String DOCUMENTS_PATH   = "/opt/appdocs/documents/";
    private static final String NEWSLETTERS_PATH = "/opt/appdocs/documents/";
    private static final String PREACHES_PATH    = "/opt/appdocs/preaches/";
    
    public OnlineDocument load(Serializable primaryKey) throws CustomException{
        return (OnlineDocument)super.load(OnlineDocument.class, primaryKey);
    }
    
    
    /**
     * 
     * 
     * @PARAM Class<? extends DTO> Class type to used for delete query
     * @PARAM String columnName The column name to use in the where clause
     * @PARAM String columnValue The value for the where clause to be used
     * @RETURN Integer Number of records deleted
     * @THROWS HFExCustomException
     */
    public Integer deleteDataByColumn(Class<? extends DTO> _parDTOClass, String columnName, String columnValue) throws CustomException{
        
        Integer numRecsDeleted = 0;
        
        try{
            
            Session vHibernateSession = getSession();
            
            List<OnlineDocument> onlineDocumentList =
                    findDataByColumn(_parDTOClass, columnName, columnValue, vHibernateSession);
            for (OnlineDocument elem : onlineDocumentList) {
                delete(elem, vHibernateSession);
                numRecsDeleted++;
            }
            vHibernateSession.flush();
            vHibernateSession.clear();
            
        }catch (DAOException dao){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Error getting data from DAO. Error is -"+dao.getLocalizedMessage(), dao);
        }catch (HibernateException he){
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.SERVICE, "Hiberate error getting data from DAO. Error is -"+he.getLocalizedMessage(), he);
        }
        
        return numRecsDeleted;
    }
    
    
    /**
     * 
     * 
     * @PARAM OnlineDocument The object to delete
     * @THROWS HCustomException
     */
    public void doOneKeyDelete(OnlineDocument onlineDocument) throws CustomException{
        
        log.debug("Received a doOneKeyDelete call for OnlineDocument");
        
        try{
            
            Session vHibernateSession = getSession();
            
            List<OnlineDocument> onlineDocumentList =
                    (List<OnlineDocument>)findByQBE(OnlineDocument.class,
                    onlineDocument,
                    vHibernateSession);
            log.debug("Size for OnlineDocumentList is >"+onlineDocumentList.size()+"<");
            
            for (OnlineDocument elem : onlineDocumentList) {
                log.debug("Deleting with id >"+elem.getDocumentName());
                delete(elem, vHibernateSession);
            }
            
            vHibernateSession.flush();
            vHibernateSession.clear();
            
        }catch (Exception ex){
            ex.printStackTrace();
            log.debug("Error doing oneKeyDelete", ex);
        }
    }
    
    /**
     *  This method will first load the object from the db to get all the related information such as the URL.
     *  IT will then remove the file from the file system and then delete the object from the dB.
     * 
     * @PARAM OnlineDocument The object to delete
     * @THROWS HFCustomException
     */
    public void doLoadDelete(OnlineDocument onlineDocument) throws CustomException{
        
        log.debug("Received a loadDelete call for OnlineDocument with documentName >"+onlineDocument.getDocumentName());
        try{
            
            Session vHibernateSession = getSession();
            
            onlineDocument = load(OnlineDocument.class, onlineDocument.getDocumentName(), vHibernateSession);
            String fileURL = onlineDocument.getDocumentUrl();
            
            File file = new File(fileURL);
            boolean success = file.delete();
            
            if (success){
                delete(onlineDocument, vHibernateSession);
            }else{
                throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.PERSISTENCE, "Unable to delete file >"+fileURL+" from the file system", null);
            }
            
            vHibernateSession.flush();
            vHibernateSession.clear();
            
        }catch (Exception ex){
            ex.printStackTrace();
            log.debug("Error doing LoadDelete", ex);
            
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.PERSISTENCE, "Unable to delete file from the file system", ex);
            
        }
    }
    
    /**
     *  This method will load the file data in byte[] format from the file system. This will be used to send the data
     *  to the client.
     * 
     * @param String fileURL The file URL where the file is located on the file system
     * @return byte[] Returns a byte[] representation of the data on the file system
     * @throws HFECustomException
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
    
    /**
     * This method will do the following:
     * 1. Save the byte[] as a file on the file system
     * 2. Clean the byte[] in the DTO
     * 3. Save the database related data
     * It can be changed to save the byte[] in the DB, but proved to be a bit of a performance problem.
     * Will see if this works a bit better.
     * 
     * @param OnlineDocument The document to save
     * @throws CustomException
     */
    public void save(OnlineDocument onlineDocument) throws CustomException{
        
        /*
         *  Write to file system
         */
        String fileName = onlineDocument.getDocumentName();
        
        String compFileName = "";
        
        try{
            String documentType = onlineDocument.getDocumentType();
            
            if (documentType.equals("PREACHES")){
                compFileName = PREACHES_PATH+fileName;
            }else if (documentType.equals("ARTICLES")){
                compFileName = ARTICLES_PATH+fileName;
            }else if (documentType.equals("NEWSLETTERS")){
                compFileName = NEWSLETTERS_PATH+fileName;
            }else if (documentType.equals("DOCUMENTS")){
                compFileName = DOCUMENTS_PATH+fileName;
            }
            log.debug("Saving file name >"+compFileName+"<");
            
            File file = new File(compFileName);
            
            FileChannel fc =
                    new FileOutputStream(file).getChannel();
            
            fc.write(ByteBuffer.wrap(onlineDocument.getDocumentData()));
            fc.close();
            
            /*
             *  Clear object data
             */
            onlineDocument.setDocumentData(new byte[0]);
            /*
             *  Build document URL for saving to the DB.
             */
            onlineDocument.setDocumentUrl(compFileName);
            
        }catch (FileNotFoundException fnf){
            fnf.printStackTrace();
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.PERSISTENCE, "Unable to save file to the file system", fnf);
        }catch (IOException ioe){
            ioe.printStackTrace();
            throw new CustomException(ErrorCode.DATA_ACCESS_ERROR, Layer.PERSISTENCE, "Unable tosave file to the file system", ioe);
        }
        /*
         *  Persist the database stuff via super method.
         */
        super.save(onlineDocument);
    }
    
    
}
