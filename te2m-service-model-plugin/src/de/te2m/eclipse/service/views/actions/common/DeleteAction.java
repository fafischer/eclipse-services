/*
* DeleteAction.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.views.actions.common;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import de.te2m.eclipse.service.model.tree.TreeNode;
import de.te2m.eclipse.service.views.actions.AbstractTreeAction;

/**
 * The Class DeleteAction.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class DeleteAction extends AbstractTreeAction {

	/**
	 * Instantiates a new delete action.
	 *
	 * @param v the v
	 */
	public DeleteAction(TreeViewer v) {
		super();
		myViewer = v;
		setText("Delete");
		setToolTipText("Delete this element");
		setImageDescriptor(PlatformUI.getWorkbench()
				.getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_ETOOL_DELETE));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		ISelection selection = myViewer.getSelection();
		Object obj = ((IStructuredSelection) selection).getFirstElement();

		if (obj instanceof TreeNode) {
			TreeNode tn = (TreeNode) obj;
			
			boolean confirmed = MessageDialog.openQuestion(
					myViewer.getControl().getShell(),
					"Confirm deletion",
					"Delete this element?");

			if (confirmed) {
				tn.getParent().removeChild(tn);
				//tn.refresh();
				myViewer.refresh();
			}
		}
	}
	
	
}
