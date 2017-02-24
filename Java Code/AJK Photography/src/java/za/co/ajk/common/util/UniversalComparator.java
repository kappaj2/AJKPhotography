/*
 * UniversalComparator.java
 *
 * Created on May 22, 2007, 10:03 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.common.util;

import za.co.ajk.common.util.StringAlphaNumericComparator;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;
import org.apache.log4j.Logger;

/**
 *
 * @author andre.kapp
 */
public class UniversalComparator extends Object implements Comparator <Object> {
    
    private static Logger logger = Logger.getLogger(UniversalComparator.class);
    
    private StringAlphaNumericComparator stringComparator = new StringAlphaNumericComparator();
    
    private String methodName = "toString";
    
    private int descAscIndicator = 1;
    
    public static final int ASCENDING = 1;
    
    public static final int DESCENDING = -1;
    
    public UniversalComparator(int descAscIndicator) {
        this.descAscIndicator = descAscIndicator;
    }
    
    public UniversalComparator(String methodName, int descAscIndicator) {
        this(descAscIndicator);
        this.methodName = methodName;
    }
    
    public int compare(Object o1, Object o2) {
        Object comp1 = null;
        Object comp2 = null;
        
        try {
            Method o1_Method = o1.getClass().getMethod(methodName, new Class[] {});
            Method o2_Method = o2.getClass().getMethod(methodName, new Class[] {});
            
            comp1 = o1_Method.invoke(o1, ((Object[])null));
            comp2 = o2_Method.invoke(o2, ((Object[])null));
        } catch (NoSuchMethodException e) {
            logger.debug("Exception doing UniversalComparator", e);
        } catch (IllegalAccessException e) {
            logger.debug("Exception doing UniversalComparator", e);
        } catch (InvocationTargetException e) {
            logger.debug("Exception doing UniversalComparator", e);
        }
        Comparable c1 = (Comparable) comp1;
        Comparable c2 = (Comparable) comp2;
        if(comp1 instanceof String && comp2 instanceof String ) { //Comparing 2 Strings.
            return stringComparator.compare(comp1, comp2);
        }
        return c1.compareTo(c2) * descAscIndicator;
        
    }
    
    public boolean equals(Object obj) {
        return this.equals(obj);
    }
}
