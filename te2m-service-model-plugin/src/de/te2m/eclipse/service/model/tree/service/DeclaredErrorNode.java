/*
* DeclaredErrorNode.java
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
import de.te2m.eclipse.service.views.actions.parameter.DeleteDeclaredErrorAction;
import de.te2m.eclipse.service.views.actions.parameter.EditDeclaredErrorAction;

/**
 * The Class DeclaredErrorNode.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class DeclaredErrorNode extends SimpleParameterNode
{
	/**
	 * Instantiates a new configuration node.
	 *
	 * @param ci the ci
	 */
	public DeclaredErrorNode(ParameterInfo ci) {
		super(ci);
	}


    /* (non-Javadoc)
     * @see de.te2m.eclipse.service.model.tree.AttributedTreeParentNode#getAllowedActions(org.eclipse.jface.viewers.TreeViewer)
     */
    @Override
	public List<Action> getAllowedActions(TreeViewer viewer) {
		List<Action> allowedActions = new ArrayList<Action>();
		allowedActions.add(new EditDeclaredErrorAction(viewer));
		allowedActions.add(new DeleteDeclaredErrorAction(viewer));
		allowedActions.addAll(super.getAllowedActions(viewer));
		return allowedActions;
	}

	/* (non-Javadoc)
	 * @see de.te2m.eclipse.service.model.tree.TreeNode#getImage()
	 */
	/*
	@Override
	public Image getImage() {

		Bundle bundle = FrameworkUtil
				.getBundle(DefaultTreeViewLabelProvider.class);
		URL url = FileLocator.find(bundle, new org.eclipse.core.runtime.Path(
				"/icons/bullet.png"), null);
		ImageDescriptor imageDcr = ImageDescriptor.createFromURL(url);
		return imageDcr.createImage();
	}
*/

	/* (non-Javadoc)
     * @see de.te2m.eclipse.service.model.tree.AbstractParentNode#getImageDescriptor()
     */
    public String getImageDescriptor()
    {
        return ISharedImages.IMG_DEC_FIELD_WARNING;
    }    
    
    
}
