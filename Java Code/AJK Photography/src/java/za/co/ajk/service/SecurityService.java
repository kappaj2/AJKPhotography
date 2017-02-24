/*
 * SecurityService.java
 *
 * Created on 07 April 2008, 02:19
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.service;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import za.co.ajk.common.exception.CustomException;
import za.co.ajk.common.exception.ErrorCode;
import za.co.ajk.common.exception.Layer;
import za.co.ajk.common.util.DesEncrypter;

import za.co.ajk.persistance.daos.SecurityDAO;
import za.co.ajk.persistance.hibernate.dao.DTO;
import za.co.ajk.persistance.hibernate.dao.factory.AbstractDAOFactory;
import za.co.ajk.persistance.hibernate.dao.factory.DAOFactory;
import za.co.ajk.persistance.transferobjects.Security;

/**
 *
 * @author akapp
 */
public class SecurityService {
    
    private DAOFactory securityFactory;
    private SecurityDAO securityDAO;
    private Session hibSession;
    private final static String KEY = "testing encryption and decryption to save password to DB";
    private DesEncrypter encrypter;
    
    private static Logger log = Logger.getLogger(SecurityService.class);
    
    /** Creates a new instance of SecurityService */
    public SecurityService() {
        securityFactory = AbstractDAOFactory.getFactory("Security");
        securityDAO = securityFactory.getDAO("Security");
        hibSession = securityDAO.getSession();
        
        encrypter = new DesEncrypter(KEY);
    }
    
    
    /**
     * This method will check against the db if the password received is valid.
     * If not, it will return false;
     * If no record in database found, insert the username, password as the new record.
     * @param String username
     * @param String password
     * @return boolean
     * @throws CustomException
     */
    public boolean isPasswordValid(String username, String password) throws CustomException{

        log.debug("Received a isPasswordValid request");
        
        if (!username.equals("admin")){
            throw new CustomException(ErrorCode.SECURITY_USERNAME_ERROR, Layer.PERSISTENCE, "Invalid username entered", null);
        }
        
        Security security = new Security();
        security.setUsername(username);

        log.debug("Getting list of defined security entries");
        
        List<? extends DTO> securityList = securityDAO.findByQBE(Security.class, security);
        
        log.debug("Size for security list is >"+securityList.size()+"<");
        
        if (securityList.size() == 0){
            /*
             *  Create new entry
             */
            try{
                
                security.setId(0l);
                security.setPassword(encryptPassword(password));
                securityDAO.save(security);
                return true;
                
            }catch (Exception ex){
                throw new CustomException(ErrorCode.SECURITY_SAVE_ERROR, Layer.PERSISTENCE, "Error saving security object to database.", ex);
            }
        }else if (securityList.size() == 1){
            
            try{
                String encryptedPassword = ((Security)securityList.get(0)).getPassword();
                String decodedPassword = decryptPassword(encryptedPassword);
                
                log.debug("Decoded password is >"+decodedPassword+"< and password received is >"+password+"<");
                
                if (decodedPassword.equals(password)){
                    return true;
                }else{
                    return false;
                }
                
            }catch (Exception ex){
                throw new CustomException(ErrorCode.SECURITY_DECODE_ERROR, Layer.PERSISTENCE, "Error decoding security object from the database. ", ex);
            }
        }else{
            throw new CustomException(ErrorCode.SECURITY_RETRIEVE_ERROR, Layer.PERSISTENCE, "Error saving security object to database. Found more than one record.", null);
        }
        
    }
    
    /**
     *  Encrypt the string to be stored in the database
     * @param String String to encrypt
     * @return String encrypted String
     * @throws Exception
     */
    private String encryptPassword(String password) throws Exception {
        String encryptedString = encrypter.encrypt( password );
        return encryptedString;
    }
    
    /**
     * Decrypt a string
     * @param String
     * @return String
     * @throws Exception
     */
    private String decryptPassword(String password) throws Exception {
        
        String decryptedString = encrypter.decrypt( password );
        return decryptedString;
    }
    
}



