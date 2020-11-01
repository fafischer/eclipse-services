/*
* PropertyGroup.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.model.cfg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Class PropertyGroup.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
@XmlRootElement
public class PropertyGroup {
	
	/**
	 * The name.
	 */
	private String name;
	
	/**
	 * The master key for the property group.
	 * The master key is part of the property group itself and will be used in order
	 * to determine if the property groups is enabled or disabled 
	 */
	@XmlElement(required = true)
	private String pgMasterKey;
	
	/**
	 * The description.
	 */
	private String description;
	


	/**
	 * The properties.
	 */
	private List<PropertyCtnr> properties;

	/**
	 * Instantiates a new property group.
	 */
	public PropertyGroup() {
		super();
		pgMasterKey=getClass().getCanonicalName();
	}	
	
	/**
	 * Instantiates a new property group.
	 *
	 * @param name the name
	 */
	public PropertyGroup(String name) {
		this();
		this.name = name;
	}

	/**
	 * Instantiates a new property group.
	 *
	 * @param name the name
	 * @param pgMasterKey the pg master key
	 */
	public PropertyGroup(String name, String pgMasterKey) {
		this();
		this.name = name;
		this.pgMasterKey = pgMasterKey;
	}

	/**
	 * Adds the property.
	 *
	 * @param property the property
	 */
	public void addProperty(PropertyCtnr property)
	{
		
		getProperties().add(property);
		
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
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the master key for the property group.
	 * The master key is part of the property group itself and will be used in order
	 * to determine if the property groups is enabled or disabled 
	 *
	 * @return the pg master key
	 */
	public String getPgMasterKey() {
		return pgMasterKey;
	}

	/**
	 * Gets the properties.
	 *
	 * @return the properties
	 */
	public List<PropertyCtnr> getProperties() {
		if(null==properties)
		{
			properties = new ArrayList<PropertyCtnr>();
		}

		return properties;
	}

	/**
	 * Merges the provided properties with the stored values.
	 *
	 * @param properties2 the properties2
	 */
	public void initValues(Map<String, String> properties2) {
		if(null!=properties2&&!properties2.isEmpty()&&null!=getProperties()&&!getProperties().isEmpty())
		{
			for (PropertyCtnr ctnr:getProperties()) {
				ctnr.setValue((String)properties2.get(ctnr.getName()));
			}
		}
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
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the properties.
	 *
	 * @param properties the new properties
	 */
	public void setProperties(List<PropertyCtnr> properties) {
		this.properties = properties;
	}	
}
