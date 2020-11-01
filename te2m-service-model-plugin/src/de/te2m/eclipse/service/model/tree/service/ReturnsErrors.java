/*
* ReturnsErrors.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.model.tree.service;

import java.util.List;

import de.te2m.api.ext.project.bo.ParameterInfo;

/**
 * The Interface ReturnsErrors.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public interface ReturnsErrors {

	/**
	 * Adds the declared error.
	 * 
	 * @param cfgVal
	 *            the cfg val
	 */
	public abstract void addDeclaredError(ParameterInfo cfgVal);

	/**
	 * Gets the declared errors.
	 * 
	 * @return the declared errors
	 */
	public abstract List<ParameterInfo> getDeclaredErrors();

	/**
	 * Sets the declared errors.
	 * 
	 * @param declaredErrors
	 *            the new declared errors
	 */
	public abstract void setDeclaredErrors(List<ParameterInfo> declaredErrors);
 
}