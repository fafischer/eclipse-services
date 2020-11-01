/*
* ErrorNode.java
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
 * The Class ErrorNode.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class ErrorNode extends TreeNode
{

    /**
     * Instantiates a new error node.
     *
     * @param name the name
     */
    public ErrorNode(String name)
    {
        super(name);

    }

    /* (non-Javadoc)
     * @see de.te2m.eclipse.service.model.tree.TreeNode#getImageDescriptor()
     */
    public String getImageDescriptor()
    {
        // TODO Auto-generated method stub
        return ISharedImages.IMG_OBJS_ERROR_TSK;
    }

}
