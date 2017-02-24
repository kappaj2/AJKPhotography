/*
 * J2EEGeekBaseServlet.java
 *
 * Created on 20 March 2008, 10:43
 */

package za.co.ajk.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The <code>J2EEGeekBaseServlet</code> class is the abstract base class for all servlets. It overrides the service() method
 * of the HttpServlet class and makes it final, preventing subclasses from changing the implementation.
 * <p/>
 * The goal of the Superclass is to enforce certain checks before passing control to the doWork() method in this class.
 *
 * @author (<a href="mailto:vscarpenter at gmail.com">Vinny Carpenter</A>
 * @version 1.0
 */
public abstract class J2EEGeekBaseServlet extends HttpServlet {

    /**
     * The <code>init()</code> method is called by the servlet container to indicate to a servlet that the servlet
     * is being placed into service.
     *
     * @throws javax.servlet.ServletException
     */
    public void init() throws ServletException {

    }

    /**
     * The <code>service()</code> method is Called by the servlet container to allow the servlet to respond
     * to any HTTP GET or POST request.
     *
     * @param javax.servlet.http.HttpServletRequest
     *         req
     * @param javax.servlet.http.HttpServletResponse
     *         res
     * @throws javax.servlet.ServletException ServletException
     * @throws java.io.IOException            IOException
     */
    public void service(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        doWork(req, res);

    }

    /**
     * The <code>doWork</code> method must be implemented by all subclasses. The <code>service()</code> method also ends
     * up calling doWork() which will make it possible for this servlet to respond to any GET or POST requests.  The real
     * unit of work for each servlet will go in the doWork() method.
     *
     * @param req
     * @param res
     * @throws ServletException
     * @throws IOException
     */
    public abstract void doWork(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException;


    /**
     * Called by the servlet container to indicate to a servlet that the servlet is being
     * taken out of service.
     */
    public void destroy() {

    }

    /**
     * Returns information about the servlet, such as author, version, and copyright. By
     * default, this method returns an empty string. Override this method to have it return
     * a meaningful value.
     */
    public java.lang.String getServletInfo() {
        return "J2EEGeekBaseServlet v1.0 by Vinny Carpenter <vscarpenter at gmail dot com>";

    }


}
