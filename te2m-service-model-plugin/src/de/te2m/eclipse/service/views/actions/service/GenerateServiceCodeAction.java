/*
* GenerateServiceCodeAction.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.views.actions.service;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import de.te2m.eclipse.service.model.tree.service.ServiceNode;
import de.te2m.eclipse.service.views.actions.AbstractTreeAction;
import de.te2m.eclipse.service.wizards.emitter.Service2CodeWizard;

/**
 * The Class GenerateServiceCodeAction.
 * 
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class GenerateServiceCodeAction extends AbstractTreeAction {

	/**
	 * Instantiates a new generate service code action.
	 * 
	 * @param v
	 *            the v
	 */
	public GenerateServiceCodeAction(TreeViewer v) {
		super();
		myViewer = v;
		setText("Generate");
		setToolTipText("Generate Code for Service");
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_ETOOL_SAVEALL_EDIT));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		ISelection selection = myViewer.getSelection();
		Object obj = ((IStructuredSelection) selection).getFirstElement();

		if (obj instanceof ServiceNode) {
			ServiceNode sn = (ServiceNode) obj;

			Service2CodeWizard wizard = new Service2CodeWizard(sn);

			// create wizard dialog & launch wizard dialog
			WizardDialog dialog = new WizardDialog(myViewer.getControl()
					.getShell(), wizard);
			dialog.open();

			myViewer.refresh();

		}
	}



}
