/*
 * DAOException.java
 *
 * Created on May 22, 2007, 9:32 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.persistance.hibernate.dao;

import za.co.ajk.common.exception.ErrorCode;
import za.co.ajk.common.exception.CustomException;
import za.co.ajk.common.exception.Layer;


/**
 *
 * @author andre.kapp
 */
public class DAOException extends CustomException{
    
	private DAOException(ErrorCode pErrorCode, Layer pLayer, String pMessage, Throwable pCause) {
		super(pErrorCode, pLayer, pMessage, pCause);
	}
        
	public DAOException(String pMessage, Throwable pCause) {
		this(null, Layer.PERSISTENCE, pMessage, pCause);
	}
	
	public DAOException(String pMessage) {
            this(null, Layer.PERSISTENCE, pMessage, null);
	}
	
	public DAOException(Throwable pCause) {
            this(null, Layer.PERSISTENCE, null, pCause);
	}
}
