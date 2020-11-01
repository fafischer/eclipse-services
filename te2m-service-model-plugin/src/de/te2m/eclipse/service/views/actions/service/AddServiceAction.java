/*
* AddServiceAction.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.views.actions.service;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import de.te2m.eclipse.service.model.tree.service.SystemNode;
import de.te2m.eclipse.service.views.actions.AbstractTreeAction;
import de.te2m.eclipse.service.wizards.service.NewServiceWizard;

/**
 * The Class AddServiceAction.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class AddServiceAction extends AbstractTreeAction {

	/**
	 * Instantiates a new adds the service action.
	 *
	 * @param v the v
	 */
	public AddServiceAction(TreeViewer v) {
		super();
		myViewer = v;
		setText("Add Service");
		setToolTipText("Add a new Service");
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

		if (obj instanceof SystemNode) {
			SystemNode pn = (SystemNode) obj;
			
			try{
			
			NewServiceWizard wizard = new NewServiceWizard(pn);

			// create wizard dialog & launch wizard dialog
			WizardDialog dialog = new WizardDialog(myViewer.getControl()
					.getShell(), wizard);
			dialog.open();

			myViewer.refresh();
			}
			catch (CoreException ce)
			{
				// TODO extend error handling
			}
		}
		
	}
	
	
}
