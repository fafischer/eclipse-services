/*
* DefaultTreeViewLabelProvider.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.views;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import de.te2m.eclipse.service.model.tree.TreeNode;

/**
 * The Class DefaultTreeViewLabelProvider.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class DefaultTreeViewLabelProvider extends LabelProvider
{
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
	 */
	public Image getImage(Object obj) {
		String imageKey = ISharedImages.IMG_OBJ_ELEMENT;
		if (obj instanceof TreeNode)
		{
			
			TreeNode tn = (TreeNode)obj;
			
			if(null!=tn.getImage())
			{
				return tn.getImage();
			}
			
		   imageKey = tn.getImageDescriptor();
		
		}
		
		return PlatformUI.getWorkbench().getSharedImages().getImage(imageKey);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
	 */
	public String getText(Object obj) {
		return obj.toString();
	}
}
