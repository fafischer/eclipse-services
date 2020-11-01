/*
* PropertyUtils.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.model.cfg;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


/**
 * The Class PropertyUtils.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class PropertyUtils {

	
	/**
	 * The instance.
	 */
	private static PropertyUtils instance = new PropertyUtils();

	
	/**
	 * Gets the single instance of PropertyUtils.
	 *
	 * @return single instance of PropertyUtils
	 */
	public static PropertyUtils getInstance()
	{
		return instance;
	}

	/**
	 * Instantiates a new property utils.
	 */
	private PropertyUtils()
	{
		// do nothing;
	}
	
	/**
	 * Load.
	 *
	 * @param path the path
	 * @return the property group
	 */
	public PropertyGroup load(String path)
	{
		PropertyGroup p = null;
		JAXBContext context;
		
		try {
			
			
			
			InputStream propsStream = this.getClass().getResourceAsStream(path);
			
			context = JAXBContext.newInstance(PropertyGroup.class);

			Unmarshaller um = context.createUnmarshaller();

			p = (PropertyGroup)um.unmarshal(propsStream);

		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return p;
	}

	/**
	 * Save.
	 *
	 * @param p the p
	 * @param path the path
	 * @return the property group
	 */
	public PropertyGroup save(PropertyGroup p, String path)
	{
		JAXBContext context;
		
		FileOutputStream fos =null;
		try {
			
			fos = new FileOutputStream(path);
			
			context = JAXBContext.newInstance(PropertyGroup.class);

			Marshaller um = context.createMarshaller();

			um.marshal(p,fos);

		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			if(null!=fos)
			{
				try {
					fos.close();
				} catch (IOException e) {
					// Nothing to do...
					e.printStackTrace();
				}
			}
		}
		
		return p;
	}


	
	
}
