/*
* CreatePropertyAction.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.views.actions.common;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import de.te2m.eclipse.service.model.tree.AttributedTreeParentNode;
import de.te2m.eclipse.service.views.actions.AbstractTreeAction;
import de.te2m.eclipse.service.wizards.property.CreatePropertyWizard;

/**
 * The Class CreatePropertyAction.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class CreatePropertyAction extends AbstractTreeAction {


	/**
	 * Instantiates a new creates the property action.
	 *
	 * @param v the v
	 */
	public CreatePropertyAction(TreeViewer v) {
		super();
		myViewer = v;
		setText("Create Property");
		setToolTipText("Create a new Property");
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_OBJ_ADD));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		ISelection selection = myViewer.getSelection();
		Object obj = ((IStructuredSelection) selection).getFirstElement();

		if (obj instanceof AttributedTreeParentNode) {

			CreatePropertyWizard wizard = new CreatePropertyWizard(((AttributedTreeParentNode)obj));

			// create wizard dialog & launch wizard dialog
			WizardDialog dialog = new WizardDialog(myViewer.getControl()
					.getShell(), wizard);
			dialog.open();
			
			myViewer.refresh();

		}
	}

}
