/*
* ServiceTemplateUtils.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.wizards.service.template;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import de.te2m.eclipse.service.ServiceModelPlugin;
import de.te2m.eclipse.service.preferences.MainPreferenceConstants;


/**
 * The Class ServiceTemplateUtils.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class ServiceTemplateUtils {


		/**
		 * The instance.
		 */
		private static ServiceTemplateUtils instance = new ServiceTemplateUtils();

		/**
		 * Gets the single instance of ProjectUtils.
		 *
		 * @return single instance of ProjectUtils
		 */
		public static ServiceTemplateUtils getInstance() {
			return instance;
		}

		/**
		 * Instantiates a new project utils.
		 */
		private ServiceTemplateUtils() {
		
			// do nothing;
		}

		/**
		 * Gets the list.
		 *
		 * @return the list
		 */
		public List<ServiceTemplate> getList()
		{
			List<ServiceTemplate> templates = new ArrayList<ServiceTemplate>();
			String dirName = ServiceModelPlugin
					.getDefault()
					.getPreferenceStore()
					.getString(
							MainPreferenceConstants.PREFERENCE_TEMPLATE_PATH);

			if(null==dirName)
			{
				return templates;
			}
			
			File baseDir = new File(dirName);
			
			if(null==baseDir||!baseDir.exists()||!baseDir.isDirectory()||!baseDir.canRead())
			{
				return templates;
			}
			
			File[] files = baseDir.listFiles();
			
			for(File file:files)
			{
				if(file.isFile()&&file.canRead()&&file.getName().indexOf(ServiceTemplate.TEMPLATE_EXTENSION)!=-1)
				{
					try {
						ServiceTemplate template = load(new FileInputStream(file));
						if(null!=template)
						{
							templates.add(template);
						}
					} catch (FileNotFoundException e) {

						e.printStackTrace();
					}
					
							
				}
			}
			
			return templates;

		}

		/**
		 * Load.
		 *
		 * @param propsStream the props stream
		 * @return the project
		 */
		public ServiceTemplate load(InputStream propsStream) {

			if (null == propsStream) {
				return null;
			}

			ServiceTemplate p = null;
			JAXBContext context;

			try {

				context = JAXBContext.newInstance(ServiceTemplate.class);

				Unmarshaller um = context.createUnmarshaller();

				p = (ServiceTemplate) um.unmarshal(propsStream);

			} catch (JAXBException e) {

				e.printStackTrace();
				return null;
			}
			return p;
		}

		/**
		 * Load as resource.
		 *
		 * @param path the path
		 * @return the project
		 */
		public ServiceTemplate loadAsResource(String path) {
			InputStream propsStream = this.getClass().getResourceAsStream(path);

			return load(propsStream);
		}

		/**
		 * Load from file.
		 *
		 * @param fileName the file name
		 * @return the project
		 */
		public ServiceTemplate loadFromFile(String fileName) {
			if (null == fileName) {
				return null;
			}
			InputStream propsStream;
			try {
				propsStream = new FileInputStream(fileName);
			} catch (FileNotFoundException e) {
				return null;
			}

			return load(propsStream);
		}

		/**
		 * Save.
		 *
		 * @param projectToSave the project to save
		 * @param path the path
		 * @return the project
		 */
		public ServiceTemplate save(ServiceTemplate projectToSave, String path) {

			FileOutputStream fos = null;
			try {

				fos = new FileOutputStream(path);

				String output = toString(projectToSave);
				
				if(null!=output)
				{
					fos.write(output.getBytes("UTF-8"));
				}

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (null != fos) {
					try {
						fos.close();
					} catch (IOException e) {
						// Nothing to do...
						e.printStackTrace();
					}
				}
			}

			return projectToSave;
		}

		/**
		 * To string.
		 *
		 * @param p the p
		 * @return the string
		 */
		public String toString(ServiceTemplate p) {

			try {
				JAXBContext ctx = JAXBContext.newInstance(ServiceTemplate.class);
				java.io.StringWriter sw = new StringWriter();

				Marshaller marshaller = ctx.createMarshaller();
				marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
				marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
				marshaller.marshal(p, sw);

				return sw.toString();
			} catch (JAXBException jbex) {
				jbex.printStackTrace();
				return null;
			}
		}
	}

