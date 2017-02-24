/*
 * CaptchaServiceSingleton.java
 *
 * Created on 20 March 2008, 10:46
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.common.util;

import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;
import com.octo.captcha.service.image.ImageCaptchaService;

/**
 * The <code>CaptchaServiceSingleton</code> implements the Singleton patterns and returns an instance of the
 * ImageCaptchaService.
 */
public class CaptchaServiceSingleton {
    
    private static ImageCaptchaService instance = new DefaultManageableImageCaptchaService();
    
    public static ImageCaptchaService getInstance() {
        return instance;
    }
}
