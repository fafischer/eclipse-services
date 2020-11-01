/*
* ServiceTemplate.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.wizards.service.template;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import de.te2m.api.ext.core.IdObject;
import de.te2m.api.ext.project.bo.ClassInfo;
import de.te2m.api.ext.project.service.Service;
import de.te2m.project.util.ProjectUtils;



/**
 * The Class ServiceTemplate.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
@XmlRootElement
public class ServiceTemplate extends IdObject{

	/**
	 * The Constant TEMPLATE_EXTENSION.
	 */
	public static final String TEMPLATE_EXTENSION=".service.te2m";
	
	/**
	 * The name.
	 */
	private String name;
	
	/**
	 * The description.
	 */
	private String description;
	
	/**
	 * The license info.
	 */
	private String licenseInfo;
	
	/**
	 * The organization.
	 */
	private String organization;
	
	/**
	 * The service.
	 */
	private Service service;
	
	/**
	 * The objects.
	 */
	private List<ClassInfo> objects;

	/**
     * Adds the object.
     *
     * @param newObject the new object
     */
    public void addObject(ClassInfo newObject)
    {
    	getObjects().add(newObject);
    }

	/**
     * Determines the object by id.
     *
     * @param id the id
     * @return the class info
     */
    public ClassInfo determineObjectByID(String id)
    {
    	if(null==id||id.trim().length()==0)return null;
    	for (Iterator iterator = getObjects().iterator(); iterator.hasNext();) {
			ClassInfo type = (ClassInfo) iterator.next();
			if(null!=type.getId()&&id.equals(type.getId()))
			{
				return type;
			}
		}
    	
    	return null;
    }

	/**
     * Determines the object by signature.
     *
     * @param signature the signature
     * @return the class info
     */
    public ClassInfo determineObjectBySignature(String signature)
    {
    	if(null==signature||signature.trim().length()==0)return null;
    	for (ClassInfo type: getObjects()) {
			if(signature.equals(ProjectUtils.getSignature(type)))
			{
				return type;
			}
		}
    	
    	return null;
    }

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Gets the license info.
	 *
	 * @return the license info
	 */
	public String getLicenseInfo() {
		return licenseInfo;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * Gets the objects.
	 *
	 * @return the objects
	 */
	public List<ClassInfo> getObjects() {
		
		if(null==objects)
		{
			objects=new ArrayList<ClassInfo>();
		}
		return objects;
	}

	/**
	 * Gets the organization.
	 *
	 * @return the organization
	 */
	public String getOrganization() {
		return organization;
	}
	

	
	

	/**
	 * Gets the service.
	 *
	 * @return the service
	 */
	public Service getService() {
		return service;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Sets the license info.
	 *
	 * @param licenseInfo the new license info
	 */
	public void setLicenseInfo(String licenseInfo) {
		this.licenseInfo = licenseInfo;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
    
    /**
	 * Sets the objects.
	 *
	 * @param objects the new objects
	 */
	public void setObjects(List<ClassInfo> objects) {
		this.objects = objects;
	}
    
    /**
	 * Sets the organization.
	 *
	 * @param organization the new organization
	 */
	public void setOrganization(String organization) {
		this.organization = organization;
	}

    /**
	 * Sets the service.
	 *
	 * @param service the new service
	 */
	public void setService(Service service) {
		this.service = service;
	}
    
}
