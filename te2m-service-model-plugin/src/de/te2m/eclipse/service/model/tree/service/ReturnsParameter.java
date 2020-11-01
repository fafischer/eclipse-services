/*
* ReturnsParameter.java
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
 * The Interface ReturnsParameter.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public interface ReturnsParameter {


	/**
	 * Gets the return value.
	 *
	 * @return the return value
	 */
	public ParameterInfo getReturnValue();

	/**
	 * Sets the return value.
	 *
	 * @param retVal the new return value
	 */
	public void setReturnValue(ParameterInfo retVal);


}