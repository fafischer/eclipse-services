/*
* ManageReturnValueAction.java
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

import de.te2m.eclipse.service.model.tree.service.SimpleParameterNode;
import de.te2m.eclipse.service.views.actions.AbstractTreeAction;
import de.te2m.eclipse.service.wizards.parameter.ManageReturnValueWizard;

/**
 * The Class ManageReturnValueAction.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class ManageReturnValueAction extends AbstractTreeAction {


	/**
	 * Instantiates a new manage return value action.
	 *
	 * @param v the v
	 */
	public ManageReturnValueAction(TreeViewer v) {
		super();
		myViewer = v;
		setText("Edit");
		setToolTipText("Edit Return Value");
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_ELCL_SYNCED));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		ISelection selection = myViewer.getSelection();
		Object obj = ((IStructuredSelection) selection).getFirstElement();

		if (obj instanceof SimpleParameterNode) {

			ManageReturnValueWizard wizard = new ManageReturnValueWizard(null,(SimpleParameterNode)obj);

			// create wizard dialog & launch wizard dialog
			WizardDialog dialog = new WizardDialog(myViewer.getControl()
					.getShell(), wizard);
			dialog.open();
			
			myViewer.refresh();

		}
	}

}
