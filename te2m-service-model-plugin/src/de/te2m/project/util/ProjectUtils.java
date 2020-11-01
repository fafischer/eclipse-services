/*
* ProjectUtils.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.project.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlTransient;

import de.te2m.api.ext.core.BasicDocumentedObject;
import de.te2m.api.ext.core.IdObject;
import de.te2m.api.ext.core.IdObject.Attributes;
import de.te2m.api.ext.core.IdObject.Attributes.Entry;
import de.te2m.api.ext.project.Project;
import de.te2m.api.ext.project.bo.ClassInfo;

/**
 * The Class ProjectUtils.
 * 
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class ProjectUtils {

	/**
	 * The Constant ATTRIBUTE_NAME_Q_NAME_LOCAL_PART.
	 */
	public static final String ATTRIBUTE_NAME_Q_NAME_LOCAL_PART = "QName.LocalPart";

	/**
	 * The Constant ATTRIBUTE_NAME_Q_NAME_NAME_SPACE.
	 */
	public static final String ATTRIBUTE_NAME_Q_NAME_NAME_SPACE = "QName.NameSpace";
	/**
	 * The instance.
	 */
	private static ProjectUtils instance = new ProjectUtils();

	/**
	 * Determines the object by id.
	 *
	 * @param proj the proj
	 * @param id the id
	 * @return the class info
	 */
	public static ClassInfo determineObjectByID(Project proj, String id) {
		if (null == id || id.trim().length() == 0)
			return null;
		for (ClassInfo type : proj.getObjects()) {

			if (null != type.getId() && id.equals(type.getId())) {
				return type;
			}
		}

		return null;
	}

	/**
	 * Determines the object by signature.
	 *
	 * @param proj the proj
	 * @param signature the signature
	 * @return the class info
	 */
	public static ClassInfo determineObjectBySignature(Project proj,
			String signature) {
		if (null == signature || signature.trim().length() == 0)
			return null;
		for (ClassInfo type : proj.getObjects()) {
			if (null != getSignature(type)
					&& signature.equals(getSignature(type))) {
				return type;
			}
		}

		return null;
	}

	/**
	 * Gets the attribute.
	 *
	 * @param ido the ido
	 * @param key the key
	 * @return the attribute
	 */
	public static String getAttribute(IdObject ido, String key) {
		if (null == ido) {
			return null;
		}
		
		if(null==ido.getAttributes())
		{
			ido.setAttributes(new Attributes());
		}
		
		for (Entry entry : ido.getAttributes().getEntries()) {
			if (entry.getKey().equals(key)) {
				return entry.getValue();
			}
		}

		return null;
	}

	/**
	 * Gets the single instance of ProjectUtils.
	 * 
	 * @return single instance of ProjectUtils
	 */
	public static ProjectUtils getInstance() {
		return instance;
	}

	/**
	 * Gets the signature.
	 *
	 * @param ci the ci
	 * @return the signature
	 */
	public static String getSignature(ClassInfo ci) {
		if(null==ci){
			return null;
		}
		
		StringBuffer sb = new StringBuffer();
		if (null != ci.getPkg()) {
			sb.append(ci.getPkg()).append(".");
		}
		// QName defined? --> fallback
		else if (null != getAttribute(ci, ATTRIBUTE_NAME_Q_NAME_NAME_SPACE)) {
			sb.append(getAttribute(ci, ATTRIBUTE_NAME_Q_NAME_NAME_SPACE))
					.append(":");
		}

		if (null != ci.getName()) {
			sb.append(ci.getName());
		}
		if (sb.length() != 0) {
			return sb.toString();
		} else {
			return null;
		}
	}

	/**
	 * Map attributes.
	 * 
	 * @param attributes
	 *            the attributes
	 * @return the properties
	 */
	public static Map<String, String> mapAttributesToMap(
			IdObject.Attributes attributes) {
		Map<String, String> props = new HashMap<String, String>();
		if (null != attributes) {
			for (Entry entry : attributes.getEntries()) {
				props.put(entry.getKey(), entry.getValue());
			}
		}

		return props;
	}

	/**
	 * Map attributes.
	 * 
	 * @param attributes
	 *            the attributes
	 * @return the properties
	 */
	public static Properties mapAttributesToProperties(
			IdObject.Attributes attributes) {
		Properties props = new Properties();
		if (null != attributes) {
			for (Entry entry : attributes.getEntries()) {
				if(null!=entry.getKey()&&null!=entry.getValue())
				{
					props.put(entry.getKey(), entry.getValue());
				}
			}
		}

		return props;
	}

	/**
	 * Reset id.
	 *
	 * @param info the info
	 */
	public static void resetId(IdObject info) {
		if (null != info) {
			info.setId(UUID.randomUUID().toString());
		}

	}

	/**
	 * Sets the attribute.
	 *
	 * @param ido the ido
	 * @param key the key
	 * @param value the value
	 */
	public static void setAttribute(IdObject ido, String key, String value) {
		if (null != ido) {

			boolean found = false;

			if(null==ido.getAttributes())
			{
				ido.setAttributes(new Attributes());
			}
			
			for (Entry entry : ido.getAttributes().getEntries()) {
				if (entry.getKey().equals(key)) {
					entry.setValue(value);
					found = true;
				}
			}

			if (!found) {
				Entry entry = new Entry();
				entry.setValue(value);
				entry.setKey(key);
				ido.getAttributes().getEntries().add(entry);
			}

		}

	}

	/**
	 * Instantiates a new project utils.
	 */
	private ProjectUtils() {
		// do nothing;
	}

	/**
	 * Load.
	 * 
	 * @param propsStream
	 *            the props stream
	 * @return the project
	 */
	public Project load(InputStream propsStream) {

		if (null == propsStream) {
			return null;
		}

		Project p = null;
		JAXBContext context;

		try {

			context = JAXBContext.newInstance(Project.class);

			Unmarshaller um = context.createUnmarshaller();

			p = (Project) um.unmarshal(propsStream);

		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return p;
	}

	/**
	 * Load as resource.
	 * 
	 * @param path
	 *            the path
	 * @return the project
	 */
	public BasicDocumentedObject loadAsResource(String path) {
		InputStream propsStream = this.getClass().getResourceAsStream(path);

		return load(propsStream);
	}

	/**
	 * Load from file.
	 * 
	 * @param fileName
	 *            the file name
	 * @return the project
	 */
	public BasicDocumentedObject loadFromFile(String fileName) {
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
	 * @param projectToSave
	 *            the project to save
	 * @param path
	 *            the path
	 * @return the project
	 */
	public BasicDocumentedObject save(BasicDocumentedObject projectToSave,
			String path) {

		FileOutputStream fos = null;
		try {

			fos = new FileOutputStream(path);

			String output = toString(projectToSave);

			if (null != output) {
				fos.write(output.getBytes());
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
	 * @param p
	 *            the p
	 * @return the string
	 */
	public String toString(BasicDocumentedObject p) {

		try {
			JAXBContext ctx = JAXBContext.newInstance(Project.class);
			java.io.StringWriter sw = new StringWriter();

			Marshaller marshaller = ctx.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
					Boolean.TRUE);
			marshaller.marshal(p, sw);

			return sw.toString();
		} catch (JAXBException jbex) {
			jbex.printStackTrace();
			return null;
		}
	}

}
