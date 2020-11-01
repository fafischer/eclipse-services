/*
* TreeNode.java
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
import java.util.UUID;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;

/**
 * The Class TreeNode.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class TreeNode implements IAdaptable
{
    
    /**
     * The name.
     */
    private String name;
    
    /**
     * The id.
     */
    private  String id;

    
    /**
     * The parent.
     */
    private AbstractParentNode parent;

    /**
     * Instantiates a new tree node.
     *
     * @param name the name
     */
    public TreeNode(String name)
    {
        this.name = name;
        
        this.id= UUID.randomUUID().toString();
        
    }

    /* (non-Javadoc)
     * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
     */
    public Object getAdapter(Class key)
    {
        return null;
    }

    /**
	 * Gets the allowed actions.
	 *
	 * @param viewer the viewer
	 * @return the allowed actions
	 */
	public List<Action> getAllowedActions(TreeViewer viewer)
	{
		return new ArrayList<Action>();
	}
    
    /**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

    /**
	 * Gets the image.
	 *
	 * @return the image
	 */
	public Image getImage() {

		return null;
	}

    /**
     * Gets the image descriptor.
     *
     * @return the image descriptor
     */
    public String getImageDescriptor()
    {
        return ISharedImages.IMG_OBJ_ELEMENT;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Gets the parent.
     *
     * @return the parent
     */
    public AbstractParentNode getParent()
    {
        return parent;
    }

    /**
     * Checks if is read only.
     *
     * @return true, if is read only
     */
    public boolean isReadOnly()
    {
    	return false;
    }

    
    /**
     * Refresh.
     */
    public void refresh()
    {
        getParent().refresh();
    }

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
     * Set the name.
     *
     * @param newName the new name
     */
    public void setName(String newName)
    {
        name=newName;
    }

	/**
     * Sets the parent.
     *
     * @param parent the new parent
     */
    public void setParent(AbstractParentNode parent)
    {
        this.parent = parent;
    }
	
	/* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return getName();
    }
    
    
}