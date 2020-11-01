/*
* ConfigurationNode.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.model.tree.service;

import java.net.URL;
import java.util.List;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import de.te2m.api.ext.project.bo.ParameterInfo;
import de.te2m.eclipse.service.views.DefaultTreeViewLabelProvider;
import de.te2m.eclipse.service.views.actions.common.CreatePropertyAction;
import de.te2m.eclipse.service.views.actions.parameter.DeleteConfigurationValueAction;
import de.te2m.eclipse.service.views.actions.parameter.ManageConfigurationValueAction;


/**
 * The Class MemberNode.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class ConfigurationNode extends SimpleParameterNode
{
	
    
    

	/**
	 * Instantiates a new configuration node.
	 *
	 * @param ci the ci
	 */
	public ConfigurationNode(ParameterInfo ci) {
		super(ci);
	}




	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.te2m.eclipse.service.model.tree.TreeNode#getAllowedActions(org.eclipse
	 * .jface.viewers.TreeViewer)
	 */
	@Override
	public List<Action> getAllowedActions(TreeViewer viewer) {
		List<Action> allowedActions = super.getAllowedActions(viewer);
		allowedActions.add(new ManageConfigurationValueAction(viewer));
		allowedActions.add(new DeleteConfigurationValueAction(viewer));
		return allowedActions;
	}


	/* (non-Javadoc)
	 * @see de.te2m.eclipse.service.model.tree.TreeNode#getImage()
	 */
	@Override
	public Image getImage() {

		Bundle bundle = FrameworkUtil
				.getBundle(DefaultTreeViewLabelProvider.class);
		URL url = FileLocator.find(bundle, new org.eclipse.core.runtime.Path(
				"/icons/bullet.png"), null);
		ImageDescriptor imageDcr = ImageDescriptor.createFromURL(url);
		return imageDcr.createImage();
	}    
    
    
}
