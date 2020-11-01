/*
* ParameterNode.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.model.tree.service;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.ISharedImages;

import de.te2m.api.ext.project.bo.ParameterInfo;
import de.te2m.eclipse.service.views.actions.common.DeleteAction;

/**
 * The Class ParameterNode.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class ParameterNode extends SimpleParameterNode
{
	
    
    /**
     * Instantiates a new parameter node.
     *
     * @param parameterInfo the parameter info
     */
    public ParameterNode(ParameterInfo parameterInfo) {
		super(parameterInfo);
	}


	/* (non-Javadoc)
	 * @see de.te2m.eclipse.service.model.tree.TreeParentNode#getAllowedActions(org.eclipse.jface.viewers.TreeViewer)
	 */
	@Override
	public List<Action> getAllowedActions(TreeViewer viewer) {
		List<Action> allowedActions = new ArrayList<Action>();
		allowedActions.add(new DeleteAction(viewer));
		allowedActions.addAll(super.getAllowedActions(viewer));
		return allowedActions;
	}

	/* (non-Javadoc)
     * @see de.te2m.eclipse.service.model.tree.TreeNode#getImageDescriptor()
     */
    public String getImageDescriptor()
    {
        // TODO Auto-generated method stub
        return ISharedImages.IMG_TOOL_FORWARD;
    }
	    
    
    
}
