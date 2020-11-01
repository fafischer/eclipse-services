/*
* ObjectViewRootNode.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.model.tree.bo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.ui.ISharedImages;

import de.te2m.api.ext.project.bo.ClassInfo;
import de.te2m.eclipse.service.model.tree.AbstractParentNode;
import de.te2m.eclipse.service.model.tree.TreeNode;
import de.te2m.eclipse.service.model.tree.service.ProjectNode;
import de.te2m.eclipse.service.views.ProjectModelProvider;

/**
 * The Class ObjectViewRootNode.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class ObjectViewRootNode extends AbstractParentNode
{

    /**
     * The description.
     */
    private String description;

    /**
     * Instantiates a new root node.
     *
     * @param name the name
     */
    public ObjectViewRootNode(String name)
    {
        super(name);
    }

    /* (non-Javadoc)
	 * @see de.te2m.eclipse.service.model.tree.AbstractParentNode#getChildrenAsList()
	 */
	@Override
	public List<TreeNode> getChildrenAsList() {
		
		List<TreeNode> newList = new ArrayList<>();
		ProjectNode pn = ProjectModelProvider.getInstance().getProjectNode();
		if(null!=pn)
		{
			if(null!=pn.getClassInfoList())
			{
				for (Iterator iterator = pn.getClassInfoList().iterator(); iterator.hasNext();) {

					newList.add(new ClassInfoNode((ClassInfo)iterator.next()));
				}
				
			}
		}
		return newList;
	}

    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }

    /* (non-Javadoc)
     * @see de.te2m.eclipse.service.model.tree.TreeParentNode#getImageDescriptor()
     */
    public String getImageDescriptor()
    {

            return ISharedImages.IMG_OBJ_ELEMENT;
    }

    /**
     * Sets the description.
     *
     * @param stext the new description
     */
    public void setDescription(String stext)
    {
        description = stext;

    }

	/* (non-Javadoc)
     * @see de.te2m.eclipse.service.model.tree.TreeNode#toString()
     */
    public String toString()
    {
        if (getDescription() == null || getDescription().trim().length() == 0) return getName();
        else

            return getName() + "(" + getDescription() + ")";
    }
    
    

}
