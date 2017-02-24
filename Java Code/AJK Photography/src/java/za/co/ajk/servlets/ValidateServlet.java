/*
 * ValidateServlet.java
 *
 * Created on 20 March 2008, 10:42
 */

package za.co.ajk.servlets;



import com.octo.captcha.service.CaptchaServiceException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import za.co.ajk.common.util.CaptchaServiceSingleton;

/**
 * Created by IntelliJ IDEA.
 * User: vcarpent
 * Date: May 13, 2005
 * Time: 2:30:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class ValidateServlet extends J2EEGeekBaseServlet {
    
    private static final Log log = LogFactory.getLog(ValidateServlet.class);
    
    public void doWork(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        
        res.setContentType("text/html");
        ServletOutputStream out = res.getOutputStream();
        out.println("<html><head><title>JCaptcha Sample</title></head><body>");
        Boolean isResponseCorrect = Boolean.FALSE;
        
        String captchaId = req.getSession().getId();
        String response = req.getParameter("j_captcha_response");
        try {
            isResponseCorrect = CaptchaServiceSingleton.getInstance().validateResponseForID(captchaId, response);
        } catch (CaptchaServiceException e) {
            log.error("Exception - " + e.getCause().getMessage());
        }
        
        if (isResponseCorrect.booleanValue()) {
            out.println("<h1>Success -- <a href=\"/jcaptcha/\">Try again?</a></h1>");
            
        } else {
            out.println("<h1>Failure -- <a href=\"/jcaptcha/\">Try again</a>");
        }
    }
}
