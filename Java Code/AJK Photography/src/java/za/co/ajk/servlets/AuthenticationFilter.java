/*
 * AuthenticationFilter.java
 *
 * Created on 07 April 2008, 01:04
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.servlets;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import za.co.ajk.common.util.User;

/**
 *
 * @author akapp
 */
public class AuthenticationFilter implements Filter {
    
    private String onFailure = "logon.jsp";
    private FilterConfig filterConfig;
    private static Logger log = Logger.getLogger(AuthenticationFilter.class);
    
    public void init(FilterConfig filterConfig) throws ServletException{
        this.filterConfig = filterConfig;
        onFailure = filterConfig.getInitParameter("onFailure");
        log.debug("Doing filter init method!!!");
    }
    
    public void doFilter(ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException{
        
        log.debug("Doing doFilter method in security filter");
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse res = (HttpServletResponse)response;
        
        if(req.getServletPath().equals(onFailure)){
            chain.doFilter(request, response);
            return;
        }
        
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("user");
        
        
        if (user == null) {
            // redirect to the login page
            log.debug("Directing to logon page");
            res.sendRedirect(req.getContextPath()+onFailure);
        } else {
            log.debug("Going to next action");
            chain.doFilter(request, response);
        }
    }
    
    public void destroy() {
    }
}
