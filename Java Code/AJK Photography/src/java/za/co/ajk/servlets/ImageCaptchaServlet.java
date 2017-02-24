/*
 * ImageCaptchaServlet.java
 *
 * Created on 20 March 2008, 10:42
 */

package za.co.ajk.servlets;


import com.octo.captcha.service.CaptchaServiceException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import za.co.ajk.common.util.CaptchaServiceSingleton;

/**
 * The <code>ImageCaptchaServlet</code> class creates the actual image that's displayed to the user for validation.
 * The servlet ends up calling the singelton to get an instance of the CaptchaService Singleton and calling its
 * getChallenge method.
 */
public class ImageCaptchaServlet extends J2EEGeekBaseServlet {
    
    private static final Log log = LogFactory.getLog(ImageCaptchaServlet.class);
    
    public void doWork(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        
        byte[] captchaChallengeAsJpeg = null;
        // the output stream to render the captcha image as jpeg into
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            
            // get the session id that will identify the generated captcha.
            String captchaId = req.getSession().getId();
            
            // call the ImageCaptchaService getChallenge method
            BufferedImage challenge = CaptchaServiceSingleton.getInstance().getImageChallengeForID(captchaId, req.getLocale());
            
            // a jpeg encoder
            JPEGImageEncoder jpegEncoder = JPEGCodec.createJPEGEncoder(jpegOutputStream);
            jpegEncoder.encode(challenge);
            
        } catch (IllegalArgumentException e) {
            log.error("IllegalArgumentException exception - " + e.getCause().getMessage());
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        } catch (CaptchaServiceException e) {
            log.error("CaptchaServiceException exception - " + e.getCause().getMessage());
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        
        // flush it in the response
        res.setHeader("Cache-Control", "no-store");
        res.setHeader("Pragma", "no-cache");
        res.setDateHeader("Expires", 0);
        res.setContentType("image/jpeg");
        ServletOutputStream out = res.getOutputStream();
        out.write(captchaChallengeAsJpeg);
        out.flush();
        out.close();
    }
}
