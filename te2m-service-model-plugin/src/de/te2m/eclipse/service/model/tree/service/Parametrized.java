/*
* Parametrized.java
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
 * Interface for enabling any kind of parameter related features.
 *
 * @author ffischer
 */
public interface Parametrized {

	/**
	 * Adds a single parameter.
	 *
	 * @param param the param
	 */
	public void addParameter(ParameterInfo param);

	/**
	 * Gets all parameters.
	 *
	 * @return the parameter
	 */
	public List<ParameterInfo> getParameter() ;


	/**
	 * Sets all parameter.
	 *
	 * @param params the new parameter
	 */
	public void setParameter(List<ParameterInfo> params) ;

}
