/*
* RefreshAction.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.views.actions.common;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import de.te2m.eclipse.service.views.actions.AbstractTreeAction;

/**
 * The Class RefreshAction.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class RefreshAction extends AbstractTreeAction {

	/**
	 * Instantiates a new refresh action.
	 *
	 * @param v the v
	 */
	public RefreshAction(TreeViewer v) {
		super();
		myViewer = v;
		setText("Refresh");
		setToolTipText("Refresh view");
		
		setImageDescriptor(PlatformUI.getWorkbench()
				.getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_TOOL_REDO));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {

		myViewer.refresh();
	}

}
