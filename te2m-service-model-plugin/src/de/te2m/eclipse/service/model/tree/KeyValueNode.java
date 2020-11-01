/*
* KeyValueNode.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.model.tree;

import org.eclipse.ui.ISharedImages;

/**
 * The Class TreeParentNode.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class KeyValueNode extends TreeNode
{

    /**
     * The value.
     */
    private String value;
	
	/**
	 * Instantiates a new tree parent node.
	 *
	 * @param name the name
	 * @param value the value
	 */
    public KeyValueNode(String name, String value)
    {
        super(name);
        this.value=value;
    }


    /* (non-Javadoc)
     * @see de.te2m.eclipse.service.model.tree.TreeNode#getImageDescriptor()
     */
    public String getImageDescriptor()
    {
        return ISharedImages.IMG_OBJ_FILE;
    }


	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue()
	{
		return value;
	}


	/* (non-Javadoc)
	 * @see de.te2m.eclipse.service.model.tree.TreeNode#isReadOnly()
	 */
	@Override
	public boolean isReadOnly() {
		return true;
	}
    
	
	/**
	 * Sets the value.
	 *
	 * @param string the new value
	 */
	public void setValue(String string) {
		value=string;		
	}
    
	/* (non-Javadoc)
	 * @see de.te2m.eclipse.service.model.tree.TreeNode#toString()
	 */
	public String toString()
	{
		return getName()+"="+value;
	}
}


