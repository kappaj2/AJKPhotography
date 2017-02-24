/*
 * Layer.java
 *
 * Created on May 22, 2007, 9:38 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.common.exception;

import java.io.Serializable;


/**
 * The Layer class is used to create a Object to indicate on which Layer of the application an
 * exception occurred. The constructor of the class is private and only the static variables of the
 * class can create and store references to the Layer objects.
 *
 *
 * @author andre.kapp
 */
public class Layer implements Serializable {
    
    
    /**
     * The static variable PERSISTENCE stores a reference to a Layer object that is constructed with
     * the name "PERSISTENCE".
     */
    public static Layer PERSISTENCE  = new Layer("PERSISTENCE");
    
    
    /**
     * The static variable SERVICE stores a reference to a Layer object that is constructed with
     * the name "SERVICE".
     */
    public static Layer SERVICE = new Layer("SERVICE");
    
    
    /**
     * The static variable EJB stores a reference to a Layer object that is constructed with
     * the name "EJB".
     */
    public static Layer EJB = new Layer("EJB");
    
    
    /**
     * The static variable WEBAPP stores a reference to a Layer object that is constructed with
     * the name "WEBAPP".
     */
    public static Layer WEBAPP = new Layer("WEBAPP");
    
    
    /**
     * The static variable COMMON stores a reference to a Layer object that is constructed with
     * the name "COMMON".
     */
    public static Layer COMMON = new Layer("COMMON");
    
    
   
    /**
     * The constructor creates a newly allocated Layer object. The constructor is private and only
     * the static variables can therefor create instances of the class.
     *
     * @param 	_layerValue. The name of the layer where the Exception occurred.
     */
    private Layer(String _layerValue) {
        this.layerValue = _layerValue;
    }
    
    
    /**
     * The accessor method for the String property layerValue.
     *
     * @return	String. The current value of the String property layerValue.
     */
    public String getValue() {
        return layerValue;
    }
    
    
    /**
     * The layerValue variable stores a reference to a String object which contain the name-value of
     * the Layer where the exception occurred.
     */
    private String layerValue;
    
    
} 

