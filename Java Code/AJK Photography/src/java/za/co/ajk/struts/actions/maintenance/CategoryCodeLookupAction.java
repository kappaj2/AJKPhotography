/*
 * CategoryCodeLookupAction.java
 *
 * Created on 27 March 2008, 02:30
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.struts.actions.maintenance;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author akapp
 */
public class CategoryCodeLookupAction extends Action{
    
    private static Logger log = Logger.getLogger(CategoryCodeLookupAction.class);
    
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception{
        
        log.debug("Received a lookup call !!!!");
        
        String catCode = request.getParameter("CategoryCode");
        log.debug("Cat code in request is >"+catCode+"<");
        
        StringBuffer strBuff = new StringBuffer();
        strBuff.append("<items>");
        
            strBuff.append("<item>");
                strBuff.append("<display>");
                    strBuff.append("val1");
                strBuff.append("</display>");
                strBuff.append("<value>");
                    strBuff.append("value1");
                strBuff.append("</value>");
            strBuff.append("</item>");
            strBuff.append("<item>");
                strBuff.append("<display>");
                    strBuff.append("val2");
                strBuff.append("</display>");
                strBuff.append("<value>");
                    strBuff.append("value2");
                strBuff.append("</value>");
            strBuff.append("</item>");
        
        strBuff.append("</items>");
        
        String res = strBuff.toString();
        
        PrintWriter out = response.getWriter();
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        
        out.println(res);
     
        out.close();
        
        return null;
    }
    
}









/******************************
 * Possible code generation lookualike
 *
 *package xhrstruts;
 *
 *
 * import java.io.PrintWriter;
 * import java.util.ArrayList;
 * import java.util.Iterator;
 * import javax.servlet.http.HttpServletRequest;
 * import javax.servlet.http.HttpServletResponse;
 * import javax.servlet.http.HttpSession;
 * import org.apache.struts.action.Action;
 * import org.apache.struts.action.ActionForm;
 * import org.apache.struts.action.ActionForward;
 * import org.apache.struts.action.ActionMapping;
 *
 *
 * public class Example3GetCharactersAction extends Action {
 *
 *
 * public ActionForward execute(ActionMapping mapping, ActionForm inForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
 *
 * // Get a list of characters associated with the select TV show
 * String tvShow = (String)request.getParameter("tvShow");
 * if (tvShow == null) {
 * tvShow = "";
 * }
 * ArrayList characters = getCharacters(tvShow);
 *
 * // And yes, I know creating HTML in an Action is generally very bad form,
 * // but I wanted to keep this exampel simple.
 * String html = "<select name=\"CharactersSelect\">";
 * int i = 0;
 * for (Iterator it = characters.iterator(); it.hasNext();) {
 * String name = (String)it.next();
 * i++;
 * html += "<option value=\"" + i + "\">" + name + "</option>";
 * }
 * html += "</select>";
 *
 * // Write the HTML to response
 * response.setContentType("text/html");
 * PrintWriter out = response.getWriter();
 * out.println(html);
 * out.flush();
 *
 * return null; // Not forwarding to anywhere, response is fully-cooked
 *
 * } // End execute()
 *
 *
 * // This method returns a list of characters for a given TV show.  If no TV
 * // show is selected, i.e., initial page view, am empty ArrayList is returned.
 * private ArrayList getCharacters(String tvShow) {
 *
 * ArrayList al = new ArrayList();
 *
 * if (tvShow.equalsIgnoreCase("StarTrekTNG")) {
 * al.add("Jean Luc Picard");
 * al.add("William T. Riker");
 * al.add("Data");
 * al.add("Deanna Troi");
 * al.add("Geordi LaForge");
 * }
 *
 * if (tvShow.equalsIgnoreCase("Babylon5")) {
 * al.add("John Sheridan");
 * al.add("Delenn");
 * al.add("Londo Mollari");
 * al.add("Stephen Franklin");
 * al.add("Vir Cotto");
 * }
 *
 * if (tvShow.equalsIgnoreCase("StargateSG1")) {
 * al.add("Samantha Carter");
 * al.add("Jack O'Neil");
 * al.add("Teal'c");
 * al.add("Daniel Jackson");
 * al.add("Baal");
 * }
 *
 * return al;
 *
 * } // End getCharacters()
 *
 *
 * } // End class
 *
 */

