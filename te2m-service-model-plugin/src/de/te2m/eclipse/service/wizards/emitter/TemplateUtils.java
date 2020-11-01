/*
* TemplateUtils.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.wizards.emitter;

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
import de.te2m.report.api.model.Report;


/**
 * The Class TemplateUtils.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class TemplateUtils {


		/**
		 * The instance.
		 */
		private static TemplateUtils instance = new TemplateUtils();

		/**
		 * Gets the single instance of ProjectUtils.
		 *
		 * @return single instance of ProjectUtils
		 */
		public static TemplateUtils getInstance() {
			return instance;
		}

		/**
		 * Instantiates a new project utils.
		 */
		private TemplateUtils() {
		
			// do nothing;
		}

		/**
		 * Gets the list.
		 *
		 * @return the list
		 */
		public List<Report> getList()
		{
			List<Report> templates = new ArrayList<Report>();
			String dirName = ServiceModelPlugin
					.getDefault()
					.getPreferenceStore()
					.getString(
							MainPreferenceConstants.PREFERENCE_REPORT_PATH);

			if(null==dirName)
			{
				return  null;
			}
			
			File baseDir = new File(dirName);
			
			if(null==baseDir||!baseDir.exists()||!baseDir.isDirectory()||!baseDir.canRead())
			{
				return null;
			}
			
			File[] files = baseDir.listFiles();
			
			for(File file:files)
			{
				if(file.isFile()&&file.canRead()&&file.getName().indexOf(".xml")!=-1)
				{
					try {
						Report template = load(new FileInputStream(file));
						if(null!=template)
						{
							templates.add(template);
						}
					} catch (FileNotFoundException e) {

						e.printStackTrace();
					}
					
							
				}
			}
			
			if(!templates.isEmpty())
			{
				return templates;
			}
			
			return null;
		}

		/**
		 * Load.
		 *
		 * @param propsStream the props stream
		 * @return the project
		 */
		public Report load(InputStream propsStream) {

			if (null == propsStream) {
				return null;
			}

			Report p = null;
			JAXBContext context;

			try {

				context = JAXBContext.newInstance(Report.class);

				Unmarshaller um = context.createUnmarshaller();

				p = (Report) um.unmarshal(propsStream);

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
		public Report loadAsResource(String path) {
			InputStream propsStream = this.getClass().getResourceAsStream(path);

			return load(propsStream);
		}

		/**
		 * Load from file.
		 *
		 * @param fileName the file name
		 * @return the project
		 */
		public Report loadFromFile(String fileName) {
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
		 * To string.
		 *
		 * @param p the p
		 * @return the string
		 */
		public String toString(Report p) {

			try {
				JAXBContext ctx = JAXBContext.newInstance(Report.class);
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

