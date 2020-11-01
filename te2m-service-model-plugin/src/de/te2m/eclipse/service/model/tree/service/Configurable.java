/*
* Configurable.java
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
 * The Interface Configurable.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public interface Configurable {


	/**
	 * Adds the config value.
	 *
	 * @param cfgVal the cfg val
	 */
	public abstract void addConfigValue(ParameterInfo cfgVal);

	/**
	 * Gets the configuration.
	 *
	 * @return the configuration
	 */
	public abstract List<ParameterInfo> getConfiguration();


	/**
	 * Sets the configuration.
	 *
	 * @param declaredErrors the new configuration
	 */
	public abstract void setConfiguration(List<ParameterInfo> declaredErrors);

}