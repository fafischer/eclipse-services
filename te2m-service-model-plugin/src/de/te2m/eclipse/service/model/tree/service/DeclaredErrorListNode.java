/*
* DeclaredErrorListNode.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.model.tree.service;

import org.eclipse.ui.ISharedImages;

import de.te2m.eclipse.service.model.tree.TreeParentNode;

/**
 * The Class DeclaredErrorListNode.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class DeclaredErrorListNode extends TreeParentNode {

	/**
	 * Instantiates a new DeclaredErrorListNode.
	 *
	 * @param name the name
	 */
	public DeclaredErrorListNode(String name) {
		super(name);
	}

    /* (non-Javadoc)
     * @see de.te2m.eclipse.service.model.tree.AbstractParentNode#getImageDescriptor()
     */
    public String getImageDescriptor()
    {
        return ISharedImages.IMG_ELCL_STOP;
    }


	
}
