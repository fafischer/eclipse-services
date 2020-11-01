/*
* EditDescriptionAction.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.views.actions.common;

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;

import de.te2m.eclipse.service.model.tree.AttributedTreeParentNode;
import de.te2m.eclipse.service.views.actions.AbstractTreeAction;

/**
 * The Class RenameAction.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class EditDescriptionAction extends AbstractTreeAction {

	/**
	 * Instantiates a new rename action.
	 *
	 * @param v the v
	 */
	public EditDescriptionAction(TreeViewer v) {
		super();
		myViewer = v;
		setText("Edit description");
		setToolTipText("Edit the description");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		ISelection selection = myViewer.getSelection();
		Object obj = ((IStructuredSelection) selection).getFirstElement();

		if (obj instanceof AttributedTreeParentNode) {
			AttributedTreeParentNode tn = (AttributedTreeParentNode) obj;

			InputDialog inpD = new InputDialog(
					myViewer.getControl().getShell(), "Edit Description",
					"Edit", tn.getDescription(), null);

			inpD.open();

			if (inpD.getReturnCode() == Window.OK) {
				tn.setDescription(inpD.getValue());
				//tn.refresh();
				myViewer.refresh();
			}
		}
	}
	
	
}
