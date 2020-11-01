/*
* AttributedTreeParentNode.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.model.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.TreeViewer;

import de.te2m.api.ext.core.BasicDocumentedObject;
import de.te2m.api.ext.core.IdObject.Attributes.Entry;
import de.te2m.eclipse.service.views.actions.common.CreatePropertyAction;
import de.te2m.project.util.ProjectUtils;


/**
 * The Class AttributedTreeParentNode.
 *
 * @author ffischer
 * @version 1.0
 * @param <T>            the generic type
 * @since 1.0
 */
public class AttributedTreeParentNode<T extends BasicDocumentedObject> extends
		AbstractParentNode {

	/**
	 * The delegated object.
	 */
	private T delegatedObject;

	/**
	 * Instantiates a new attributed tree parent node.
	 * 
	 * @param idObject
	 *            the id object
	 */
	public AttributedTreeParentNode(T idObject) {
		super("");
		delegatedObject = idObject;

	}

	/**
	 * Adds the attribute.
	 * 
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 */
	public void addAttribute(String key, String value) {
		//getAttributes().put(key, value);
		ProjectUtils.setAttribute(getDelegatedObject(), key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.te2m.eclipse.service.model.tree.TreeNode#getAllowedActions(org.eclipse
	 * .jface.viewers.TreeViewer)
	 */
	@Override
	public List<Action> getAllowedActions(TreeViewer viewer) {
		List<Action> allowedActions = super.getAllowedActions(viewer);
		allowedActions.add(new CreatePropertyAction(viewer));
		return allowedActions;
	}

	/**
	 * Gets the attribute.
	 * 
	 * @param key
	 *            the key
	 * @return the attribute
	 */
	public String getAttribute(String key) {
		return getAttributes().get(key);
	}

	/**
	 * Gets the attributes.
	 * 
	 * @return the attributes
	 */
	public Map<String, String> getAttributes() {

		return ProjectUtils.mapAttributesToMap(delegatedObject.getAttributes());
	}

	/**
	 * Gets the children.
	 * 
	 * @return the children
	 */
	public List<TreeNode> getChildrenAsList() {
		List<TreeNode> newList = new ArrayList<TreeNode>(
				super.getChildrenAsList());

		if (null != getAttributes()&&!getAttributes().isEmpty()) {
			TreeParentNode tpn = new TreeParentNode("Properties");
			
			for(String key:getAttributes().keySet())
			{
				KeyValueNode kvn = new KeyValueNode(key,getAttribute(key));
				tpn.addChild(kvn);
			}
			newList.add(tpn);
		} else {
			newList.add(new InfoNode("No properties available"));

		}

		return newList;

	}


	/**
	 * Gets the delegated object.
	 * 
	 * @return the delegated object
	 */
	public T getDelegatedObject() {
		return delegatedObject;
	}

	/**
	 * Gets the description.
	 * 
	 * @return the description
	 */
	public String getDescription() {
		if(null==delegatedObject)
		{
			return null;
		}
		return delegatedObject.getDescription();
	}

	/* (non-Javadoc)
	 * @see de.te2m.eclipse.service.model.tree.TreeNode#getId()
	 */
	public String getId()
	{
		return getDelegatedObject().getId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.te2m.eclipse.service.model.tree.TreeNode#getName()
	 */
	@Override
	public String getName() {
		
		if(null==delegatedObject)
		{
			return null;
		}
		
		return delegatedObject.getName();
	}

	/**
	 * Removes the attribute.
	 * 
	 * @param key
	 *            the key
	 */
	public void removeAttribute(String key) {
		getAttributes().remove(key);
	}

	/**
	 * Reset id.
	 */
	public void resetId()
	{
		getDelegatedObject().setId(UUID.randomUUID().toString());
	}

	/**
	 * Sets the attributes.
	 *
	 * @param newAttrs the new attrs
	 */
	public void setAttributes(Map<String, String> newAttrs) {
		
		for(String key:newAttrs.keySet())
		{
			Entry entry = new Entry();
			
			entry.setKey(key);
			
			entry.setValue(newAttrs.get(key));
		
			delegatedObject.getAttributes().getEntries().add(entry);
		}
	}

	/**
	 * Sets the delegated object.
	 * 
	 * @param delegatedObject
	 *            the new delegated object
	 */
	public void setDelegatedObject(T delegatedObject) {
		this.delegatedObject = delegatedObject;
	}
	
	/**
	 * Sets the description.
	 * 
	 * @param stext
	 *            the new description
	 */
	public void setDescription(String stext) {
		delegatedObject.setDescription(stext);

	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setID(String id)
	
	{
		getDelegatedObject().setId(id);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.te2m.eclipse.service.model.tree.TreeNode#setName(java.lang.String)
	 */
	@Override
	public void setName(String newName) {
		delegatedObject.setName(newName);
	}
	
}
