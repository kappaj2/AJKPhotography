/*
 * IstayException.java
 *
 * Created on May 22, 2007, 9:40 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.common.exception;

/**
 *
 * @author andre.kapp
 */
public class CustomException extends RuntimeException {

	
	private CustomException() {		
	} 
    

	public CustomException(ErrorCode _errorCode, Layer _layer, String _internalMessage, Throwable _cause) {
		super(_internalMessage, _cause);
	    this.errorCode = _errorCode;
	    this.layer = _layer;
	}

	  	
	public ErrorCode getErrorCode() {
		return errorCode;
	}

	  
	public Layer getLayer() {
		return layer;
	}
	
	
    private ErrorCode errorCode;
    
    private Layer layer;
	
}