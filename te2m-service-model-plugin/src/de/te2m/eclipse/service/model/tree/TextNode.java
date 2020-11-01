/*
* TextNode.java
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
public class TextNode extends TreeNode
{

    /**
     * Instantiates a new tree parent node.
     *
     * @param name the name
     */
    public TextNode(String name)
    {
        super(name);
    }


    /* (non-Javadoc)
     * @see de.te2m.eclipse.service.model.tree.TreeNode#getImageDescriptor()
     */
    public String getImageDescriptor()
    {
        return ISharedImages.IMG_OBJ_FILE;
    }


	/* (non-Javadoc)
	 * @see de.te2m.eclipse.service.model.tree.TreeNode#isReadOnly()
	 */
	@Override
	public boolean isReadOnly() {
		return true;
	}
    
    
}
