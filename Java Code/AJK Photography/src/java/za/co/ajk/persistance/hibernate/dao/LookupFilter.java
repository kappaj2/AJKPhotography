/*
 * LookupFilter.java
 *
 * Created on May 22, 2007, 10:08 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.persistance.hibernate.dao;

import java.io.Serializable;

/**
 *
 * @author andre.kapp
 */
public class LookupFilter implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//How am I related to the previous filter
	public static final int JOIN_OR=0;
	public static final int JOIN_AND=1;
	
	//What operations do I support
	public static final int OPERATION_EQUALS=0;
	public static final int OPERATION_GREATER=1;
	public static final int OPERATION_LESS=2;
	public static final int OPERATION_LIKE=3;
	public static final int OPERATION_LIKE_CASE_INSENSITIVE=4;
	public static final int OPERATION_NOT_EQUALS=5;
	
	private static final String[] mOperatorStrings = {"EQUALS", "GREATER THAN", "LESS THAN",
													  "LIKE", "CASE_INSENSITIVE_LIKE", "NOT EQUALS"}; 
	//Okay my beanny stuff
	private String mPropertyName;
	private Object mObjectValue;
	private int mOperator;
	
	public Object getObjectValue() {
		return this.mObjectValue;
	}
	public void setObjectValue(Object pObjectValue) {
		this.mObjectValue = pObjectValue;
	}
	public int getOperator() {
		return this.mOperator;
	}
	public void setOperator(int pOperator) {
		this.mOperator = pOperator;
	}
	public String getPropertyName() {
		return this.mPropertyName;
	}
	public void setPropertyName(String pPropertyName) {
		this.mPropertyName = pPropertyName;
	}
	
	public void setOperator(String pOperator) {
		boolean done=false;
		for(int i=0; i<mOperatorStrings.length;i++) {
			if(mOperatorStrings[i].equalsIgnoreCase(pOperator)) {
				setOperator(i);
				done = true;
				break;
			}
		}
		if(!done) {
			throw new IllegalArgumentException("Comparison Operator no supported.  Use one of: \n"+mOperatorStrings);
		}
	}
	
	public String getOperatorString() {
		return mOperatorStrings[getOperator()];
	}
}
