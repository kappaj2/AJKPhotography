/*
 * CodeLookupForm.java
 *
 * Created on 27 March 2008, 02:31
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.struts.forms;

import java.util.ArrayList;
import java.util.List;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author akapp
 */
public class CodeLookupForm extends ActionForm{
    
    private String[] galleryCodes = new String[]{};
    private String[] categoryCodes = new String[]{};
    
    private List categories = new ArrayList();
    
    public String[] getGalleryCodes() {
        return galleryCodes;
    }
    
    public void setGalleryCodes(String[] galleryCodes) {
        this.galleryCodes = galleryCodes;
    }
    
    public String[] getCategoryCodes() {
        return categoryCodes;
    }
    
    public void setCategoryCodes(String[] categoryCodes) {
        this.categoryCodes = categoryCodes;
    }
    
    public List getCategories() {
        return categories;
    }
    
    public void setCategories(List categories) {
        this.categories = categories;
    }
    
}
