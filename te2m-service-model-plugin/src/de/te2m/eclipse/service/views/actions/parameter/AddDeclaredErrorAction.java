/*
* AddDeclaredErrorAction.java
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
import de.te2m.eclipse.service.model.tree.service.ReturnsErrors;
import de.te2m.eclipse.service.views.actions.AbstractTreeAction;
import de.te2m.eclipse.service.wizards.parameter.ManageDeclaredErrorsWizard;
import de.te2m.project.util.ProjectUtils;

/**
 * The Class AddDeclaredErrorAction.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class AddDeclaredErrorAction extends AbstractTreeAction {


	/**
	 * Instantiates a new adds the declared error action.
	 *
	 * @param v the v
	 */
	public AddDeclaredErrorAction(TreeViewer v) {
		super();
		myViewer = v;
		setText("Add Error");
		setToolTipText("Add a new error that my be thrown by the operation");
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

		/*
		 ITreeContentProvider tcp =tree.getContentProvider();
Object parent = tcp.getParent(selectObject);
		 */
		
		if (obj instanceof OperationNode) {
			ReturnsErrors pn = (ReturnsErrors) obj;
			

			ManageDeclaredErrorsWizard wizard = new ManageDeclaredErrorsWizard(pn, null);

			// create wizard dialog & launch wizard dialog
			WizardDialog dialog = new WizardDialog(myViewer.getControl()
					.getShell(), wizard);
			dialog.open();
			
			myViewer.refresh();
			
		}

	}
	
	
}
