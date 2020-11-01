/*
* MemberNode.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.model.tree.bo;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import de.te2m.api.ext.project.bo.ParameterInfo;
import de.te2m.eclipse.service.model.tree.service.SimpleParameterNode;
import de.te2m.eclipse.service.views.DefaultTreeViewLabelProvider;


/**
 * The Class MemberNode.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class MemberNode extends SimpleParameterNode
{

	/**
	 * Instantiates a new configuration node.
	 *
	 * @param ci the ci
	 */
	public MemberNode(ParameterInfo ci) {
		super(ci);
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
