/*
* ParameterListNode.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.model.tree.service;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import de.te2m.eclipse.service.model.tree.TreeParentNode;
import de.te2m.eclipse.service.views.DefaultTreeViewLabelProvider;

/**
 * The Class ParameterListNode.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class ParameterListNode extends TreeParentNode {

	/**
	 * Instantiates a new parameter list node.
	 *
	 * @param name the name
	 */
	public ParameterListNode(String name) {
		super(name);

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

	/* (non-Javadoc)
     * @see de.te2m.eclipse.service.model.tree.AbstractParentNode#getImageDescriptor()
     */
    public String getImageDescriptor()
    {
        return ISharedImages.IMG_ELCL_STOP;
    }
	
}
