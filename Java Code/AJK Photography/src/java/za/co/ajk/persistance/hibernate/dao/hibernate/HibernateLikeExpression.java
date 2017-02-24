/*
 * HibernateLikeExpression.java
 *
 * Created on May 22, 2007, 10:09 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.persistance.hibernate.dao.hibernate;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.engine.TypedValue;
import org.hibernate.util.StringHelper;

/**
 *
 * @author andre.kapp
 */
public class HibernateLikeExpression implements Criterion {
	private final String propertyName;
	private final Object value;
	private boolean ignoreCase;


	public HibernateLikeExpression(String propertyName, Object pValue, boolean pIgnoreCase) {
		this.propertyName=propertyName;
		this.ignoreCase=pIgnoreCase;
		if(pIgnoreCase && pValue!=null) { //Make value Lower case...
			value = pValue.toString().toLowerCase();
		} else {
			this.value = pValue;
		}
	}

	public String toSqlString(Criteria pCriteria, CriteriaQuery pCriteriaQuery) throws HibernateException {
		String[] columns = pCriteriaQuery.getColumnsUsingProjection(pCriteria, propertyName);
		if(ignoreCase) {
			if ( columns.length!=1 ) throw new HibernateException(
					"case insensitive expression may only be applied to single-column properties: " +
					propertyName
				);
			String vLowerCaseFunction = pCriteriaQuery.getFactory().getDialect().getLowercaseFunction();
			return String.format("%s(%s) LIKE to_char('%s')", vLowerCaseFunction, columns[0], value);
		} else {
			String vResultString = StringHelper.join(" and ", StringHelper.suffix(columns, " LIKE to_char('"+value+"')"));
			if(columns.length>1) {
				vResultString = String.format("(%s)", vResultString);
			}
			return vResultString;
		}
	}	
	
	public TypedValue[] getTypedValues(Criteria criteria, CriteriaQuery criteriaQuery)
	throws HibernateException {
		return new TypedValue[0];
	}

	public String toString() {
		return propertyName + " LIKE " + value;
	}
	
}
