package za.co.ajk.struts.actions;

/*
 * BaseDispatchAction.java
 *
 * Created on 10 March 2008, 02:30
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
import fr.improve.struts.taglib.layout.crumb.CrumbImpl;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import za.co.ajk.persistance.transferobjects.AppProp;
import za.co.ajk.service.AppPropMaintenanceService;


/**
 *
 * @author akapp
 */
public class BaseDispatchAction extends DispatchAction{
    
    private static final String SUBMIT = "submit";
    private static final String CANCEL = "cancel";
    private static final String SESSION = "session";
    private static final String LOGIN = "login";
    private static final String DEFAULT_PARAMETER = "exec";
    private static final String DEFAULT_METHOD = "homePage";
    
    public static final String APP_WARNING_KEY = "APP_WARNING_KEY";
    public static final String APP_ERROR_KEY = "APP_ERROR_KEY";
    public static final String APP_INFO_KEY = "APP_INFO_KEY";
    
    private static Logger log = Logger.getLogger(BaseDispatchAction.class);
    
    private static int cc = 1;
    private String pageName = "";
    private String appName = null;
    
    private void setAppName(){
        /*
         * set the application name
         */
        AppPropMaintenanceService appPropMaintenanceService = new AppPropMaintenanceService();
        AppProp appProp = appPropMaintenanceService.findProp("app_name");
        appName = appProp.getPropertyValue();
    }
    protected void saveAppInfo(HttpServletRequest request, ActionMessages messages) {
        saveAppMessages(request, messages, APP_INFO_KEY);
    }
    
    protected void saveAppWarnings(HttpServletRequest request, ActionMessages messages) {
        saveAppMessages(request, messages, APP_WARNING_KEY);
    }
    
    protected void saveAppErrors(HttpServletRequest request, ActionMessages messages) {
        saveAppMessages(request, messages, APP_ERROR_KEY);
    }
    
    private void saveAppMessages(HttpServletRequest request, ActionMessages messages, String key) {
        // Remove any messages attribute if none are required
        if ((messages == null) || messages.isEmpty()) {
            request.removeAttribute(key);
            return;
        }
        
        // Save the messages we need
        request.setAttribute(key, messages);
    }
    
    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an <code>ActionForward</code> instance describing where and how
     * control should be forwarded, or <code>null</code> if the response has
     * already been completed.
     *
     * @param actionMapping The ActionMapping used to select this instance
     * @param actionForm The optional ActionForm bean for this request (if any)
     * @param httpServletRequest The HTTP request we are processing
     * @param httpServletResponse The HTTP response we are creating
     *
     * @return ActionForward The action forward of returned by the relected method.
     *   If an exception occurs the ActionFoward mapped to "exception" or if this is null the input forward.
     *
     */
    public ActionForward execute(ActionMapping actionMapping,
            ActionForm actionForm,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        
        ActionForward actionForward = null;
        try {
            log.debug("Doing basedispatchaction execute");
            
            String parameter = actionMapping.getParameter();
            if(parameter == null) {
                parameter = DEFAULT_PARAMETER;
            }
            
            String name = httpServletRequest.getParameter(parameter);
            
            if(name == null || name.trim().equals("")) {
                name = DEFAULT_METHOD;
            }
            
            // Invoke the named method, and return the result
            
            log.debug("Doing forward for parameter >"+parameter+"<");
            log.debug("Doing forward for method >"+name+"<");
            
            executeBefore(httpServletRequest);
            
            actionForward = dispatchMethod(actionMapping, actionForm, httpServletRequest, httpServletResponse, name);
            
            executeAfter(httpServletRequest);
            
        } catch(Throwable ex) {
            log.error("Error doing lookup", ex);
            return (actionMapping.findForward("error"));
        }
        return actionForward;
    }
    
    
    
    private void executeBefore(HttpServletRequest request){
        
        if (log.isDebugEnabled()){
            log.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            log.debug("@@  Doing before                                                  @");
            log.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        }
        
        HttpSession session = request.getSession();
        
        List crumbs = (List) session.getAttribute("crumbs");
        if (crumbs == null){
            crumbs = new ArrayList();
            /*
             * Add deault homepage
             */
            CrumbImpl crumb1 = new CrumbImpl();
            
            crumb1.setKey("crumbs.homepage");
            crumb1.setLink("/homePage.do");
            crumbs.add(crumb1);
            session.setAttribute("crumbs", crumbs);
        }
        
        /*
         * Set the application/site name for the browser title
         */
        if (appName == null){
            setAppName();
            session.setAttribute("appName", appName);
            log.debug("++++++++++++++++++ appName is homeAction is >"+appName+" ++++++++++++++++++");
        }
    }
    
    
    private void checkBackFlag(List crumbs, String pageName){
        
        /*
         * clean out sub-levels if needed
         * Check for the Code to determine level
         * categoryCode => level == 1
         * galleryCode  => level == 2
         * imageCode    => level == 3
         */
        int level = 0;
        String key = "";
        
        if (pageName.indexOf("categoryCode") != -1){
            level = 1;
            key = "Gallery";
        }else if (pageName.indexOf("galleryCode") != -1){
            level = 2;
            key = "Image";
        }else if (pageName.indexOf("imageCode") != -1){
            level = 3;
            key = "Full size";
        }
        
        log.debug("Checking for level >"+level+"<");
        
        ArrayList col1 = new ArrayList(crumbs);
        
        //((ArrayList)crumbs).clone();
        
        
        switch (level){
            case 1: // delete everything with categoryCode/galleryCode or imageCode entry
                for (Iterator it = col1.iterator(); it.hasNext();) {
                    CrumbImpl elem = (CrumbImpl) it.next();
                    boolean remove = false;
                    if (elem.getKey() .equals("Gallery") ||
                            elem.getKey().equals("Image") ||
                            elem.getKey().equals("Full size")){
                        crumbs.remove(elem);
                    } // end if
                } // end for loop
                break;
                
            case 2: // delete only gallery or imageCode
                for (Iterator it = col1.iterator(); it.hasNext();) {
                    CrumbImpl elem = (CrumbImpl) it.next();
                    boolean remove = false;
                    if (    elem.getKey().equals("Image") ||
                            elem.getKey().equals("Full size")){
                        crumbs.remove(elem);
                    } // end if
                } // end for loop
                break;
                
            case 3:  // delete only image code
                for (Iterator it = col1.iterator(); it.hasNext();) {
                    CrumbImpl elem = (CrumbImpl) it.next();
                    boolean remove = false;
                    if (    elem.getKey().equals("Full size")){
                        crumbs.remove(elem);
                    } // end if
                } // end for loop
                break;
                
            default:
        }
//        Iterator crumbIterator = crumbs.iterator();
        
//        boolean tempBackFlag=false;
//        while(crumbIterator.hasNext()){
//
//            crumbIterator.equals()
//            if (crumbIterator.equals(pageName)){
//                tempBackFlag = true;
//                crumbIterator.next();
//            }
//            if(tempBackFlag){
//                crumbs.remove(crumbIterator);
//            }
//        }
//
        
        
        CrumbImpl submitPage1 = new CrumbImpl();
        
        addCrumbAttributes(submitPage1, key, pageName);
        crumbs.add(submitPage1);
        
//
//        return tempBackFlag;
    }
    
    private CrumbImpl addCrumbAttributes(CrumbImpl crumb, String key, String link){
        
        log.debug("Adding key >"+key+"< to crumbs list");
        
        crumb.setKey(key);
        crumb.setLink(link);
        return crumb;
    }
    
    private void executeAfter(HttpServletRequest request){
        log.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        log.debug("@@  Doing after                                                   @");
        log.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        log.debug("Value for pageName is >"+pageName+"<");
        
        HttpSession session = request.getSession();
        
        List crumbs = (List) session.getAttribute("crumbs");
        if (crumbs == null || pageName == null || pageName.equals("")){
            
            crumbs = new ArrayList();
            /*
             * Add deault homepage
             */
            CrumbImpl crumb1 = new CrumbImpl();
            crumb1.setKey("crumbs.homepage");
            crumb1.setLink("/homePage.do");
            crumbs.add(crumb1);
            
        }else{
            
            checkBackFlag(crumbs, pageName);
            
        }
        session.setAttribute("crumbs", crumbs);
        
    }
    
    public String getPageName() {
        return pageName;
    }
    
    public void setPageName(String pageName) {
        this.pageName = pageName;
    }
}
