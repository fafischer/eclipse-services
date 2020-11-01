/*
* Test.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service;

import java.util.Enumeration;
import java.util.Properties;

/**
 * The Class Test.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class Test {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		Properties p = System.getProperties();

		Enumeration<Object> keys = p.keys();
		
		while (keys.hasMoreElements()) {
			Object object = (Object) keys.nextElement();
			
			System.out.println(object +" = "+p.getProperty((String)object));
			
			
			
		}

	}

}
