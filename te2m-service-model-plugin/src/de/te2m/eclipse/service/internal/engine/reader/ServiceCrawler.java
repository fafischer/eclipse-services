/*
* ServiceCrawler.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.internal.engine.reader;

import java.util.List;


/**
 * The Interface ServiceCrawler.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public interface ServiceCrawler {

	/**
	 * Parses the services.
	 *
	 * @return the list
	 */
	public List<de.te2m.api.ext.project.service.Service> parseServices();

}