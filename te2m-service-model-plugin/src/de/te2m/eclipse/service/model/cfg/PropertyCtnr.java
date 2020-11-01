/*
* PropertyCtnr.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.model.cfg;

import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * The Class PropertyCtnr.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
@XmlType
public class PropertyCtnr {
	
	/**
	 * The name.
	 */
	private String name;
	
	/**
	 * The value.
	 */
	private String value;
	
	/**
	 * The description.
	 */
	private String description;
	
	/**
	 * The mandantory.
	 */
	private boolean mandantory;
	
	/**
	 * The value list.
	 */
	private List<String> valueList;
	
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
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Gets the value list.
	 *
	 * @return the value list
	 */
	public List<String> getValueList() {
		return valueList;
	}

	/**
	 * Checks for values.
	 *
	 * @return true, if successful
	 */
	public boolean hasValues()
	{
		return null!=valueList&&!valueList.isEmpty();
	}

	/**
	 * Checks if is mandantory.
	 *
	 * @return true, if is mandantory
	 */
	public boolean isMandantory() {
		return mandantory;
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
	 * Sets the mandantory.
	 *
	 * @param mandantory the new mandantory
	 */
	public void setMandantory(boolean mandantory) {
		this.mandantory = mandantory;
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
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	/**
	 * Sets the value list.
	 *
	 * @param valueList the new value list
	 */
	public void setValueList(List<String> valueList) {
		this.valueList = valueList;
	}

	/**
	 * Sets the value list values.
	 *
	 * @param values the new value list values
	 */
	@XmlTransient
	public void setValueListValues(String[] values)
	{
		valueList=Arrays.asList(values);
	}
	
	
	
}
