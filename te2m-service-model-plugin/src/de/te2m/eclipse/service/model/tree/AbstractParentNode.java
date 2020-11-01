/*
* AbstractParentNode.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.model.tree;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.ISharedImages;

/**
 * The Class AbstractParentNode.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class AbstractParentNode extends TreeNode {

    /**
     * The children.
     */
    List<TreeNode> children;

	
	/**
	 * Instantiates a new abstract parent node.
	 *
	 * @param name the name
	 */
	public AbstractParentNode(String name) {
		super(name);
		children=new ArrayList<TreeNode>();
	}

	/**
     * Adds the child.
     *
     * @param child the child
     */
    public void addChild(TreeNode child)
    {
        children.add(child);
        child.setParent(this);
    }

	/**
	 * Gets the children.
	 *
	 * @return the children
	 */
	public TreeNode[] getChildren() {
	    return (TreeNode[]) getChildrenAsList().toArray(new TreeNode[getChildrenAsList().size()]);
	}

	/**
	 * Gets the children as list.
	 *
	 * @return the children as list
	 */
	public List<TreeNode> getChildrenAsList() {
		
		
		return new ArrayList<TreeNode>(children);
	}

    
    /* (non-Javadoc)
	 * @see de.te2m.eclipse.service.model.tree.TreeNode#getImageDescriptor()
	 */
	public String getImageDescriptor() {
	    return ISharedImages.IMG_OBJ_FILE;
	}

    /**
	 * Force always the child creation.
	 *
	 * @return true, if successful
	 */
	public boolean hasChildren() {
	    return true;
	}

	
	/**
     * Removes the child.
     *
     * @param child the child
     */
    public void removeChild(TreeNode child)
    {
        children.remove(child);
        child.setParent(null);
    }

	/**
	 * Reset.
	 */
	public void reset() {
	    children = new ArrayList<TreeNode>();
	}

}