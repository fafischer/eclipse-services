/*
* AddParameterAction.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.views.actions.parameter;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import de.te2m.eclipse.service.model.tree.service.OperationNode;
import de.te2m.eclipse.service.views.actions.AbstractTreeAction;
import de.te2m.eclipse.service.wizards.parameter.ManageParameterWizard;

/**
 * The Class AddParameterAction.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class AddParameterAction extends AbstractTreeAction {

	/**
	 * Instantiates a new adds the parameter action.
	 *
	 * @param v the v
	 */
	public AddParameterAction(TreeViewer v) {
		super();
		myViewer = v;
		setText("Add Parameter");
		setToolTipText("Add a new parameter");
		setImageDescriptor(PlatformUI.getWorkbench()
				.getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_OBJ_ADD));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {

		ISelection selection = myViewer.getSelection();
		Object obj = ((IStructuredSelection) selection).getFirstElement();

		if (obj instanceof OperationNode) {
			OperationNode pn = (OperationNode) obj;
			

			ManageParameterWizard wizard = new ManageParameterWizard((pn));

			// create wizard dialog & launch wizard dialog
			WizardDialog dialog = new WizardDialog(myViewer.getControl()
					.getShell(), wizard);
			dialog.open();
			
			myViewer.refresh();

		}

	}
	
	
}
