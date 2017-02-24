/*
 * ConfigurationAndSetupService.java
 *
 * Created on 28 March 2008, 11:24
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.service;

import za.co.ajk.common.exception.CustomException;
import za.co.ajk.common.util.FileUtility;

/**
 *
 * @author akapp
 */
public class ConfigurationAndSetupService {
    
    
    public void buildDiretories() throws CustomException{
        
        FileUtility fileUtility = new FileUtility();
        
        fileUtility.buildDirectoryStructure();
        
    }
    
}
