/*
* PropertiesNode.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.model.tree;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import org.eclipse.ui.ISharedImages;

/**
 * The Class PropertiesNode.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class PropertiesNode extends AbstractParentNode
{

    /**
     * The properties.
     */
    private Hashtable<String, String> properties;
	
	/**
     * Instantiates a new PropertiesNode.
     *
     * @param name the name
     */
    public PropertiesNode(String name)
    {
        super(name);
        properties=new Hashtable<String, String>();
    }

    /* (non-Javadoc)
	 * @see de.te2m.eclipse.service.model.tree.TreeParentNode#getChildrenAsList()
	 */
	public List<TreeNode> getChildrenAsList() {
		
		List<TreeNode> newList = new ArrayList<TreeNode>(super.getChildrenAsList());
		
		if(null!=properties&&properties.size()>0)
		{
			Enumeration<String> keys = properties.keys();
			while (keys.hasMoreElements()) {
				String key = keys.nextElement();
				KeyValueNode kvn = new KeyValueNode(key, properties.get(key));
				newList.add(kvn)
;			}
		}
		else
		{
			newList.add(new InfoNode("No properties defined"));
		}
		
		
		return newList;
	}

	
	/* (non-Javadoc)
     * @see de.te2m.eclipse.service.model.tree.TreeParentNode#getImageDescriptor()
     */
    public String getImageDescriptor()
    {

            return ISharedImages.IMG_OBJS_TASK_TSK;
    }

	/* (non-Javadoc)
	 * @see de.te2m.eclipse.service.model.tree.TreeParentNode#hasChildren()
	 */
	@Override
	public boolean hasChildren() {
		
		return super.hasChildren()||(null!=properties&&!properties.isEmpty());
	}

	/**
	 * Sets the property.
	 *
	 * @param key the key
	 * @param value the value
	 */
	public void setProperty(String key, String value) {
		
		if(null==properties)
		{
			properties= new Hashtable<String, String>();
	
		}
		
		properties.put(key, value);
		
	}


}
