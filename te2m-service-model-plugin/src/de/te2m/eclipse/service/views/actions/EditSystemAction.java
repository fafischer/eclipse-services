/*
* EditSystemAction.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.views.actions;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import de.te2m.eclipse.service.model.tree.service.SystemNode;
import de.te2m.eclipse.service.views.ProjectModelProvider;
import de.te2m.eclipse.service.wizards.AbstractPropertyMgmtWizard;
import de.te2m.eclipse.service.wizards.system.ManageSystemWizard;

/**
 * The Class EditSystemAction.
 * 
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class EditSystemAction extends AbstractTreeAction {

	/**
	 * Instantiates a new edits the system action.
	 * 
	 * @param v
	 *            the v
	 */
	public EditSystemAction(TreeViewer v) {
		super();
		myViewer = v;
		setText("Edit System");
		setToolTipText("Edit the currently selected system");
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_OBJ_ELEMENT));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		ISelection selection = myViewer.getSelection();
		Object obj = ((IStructuredSelection) selection).getFirstElement();

		if (obj instanceof SystemNode) {
			SystemNode pn = (SystemNode) obj;

			try {

				AbstractPropertyMgmtWizard wizard = new ManageSystemWizard(pn);

				// create wizard dialog & launch wizard dialog
				WizardDialog dialog = new WizardDialog(myViewer.getControl()
						.getShell(), wizard);
				dialog.open();

				ProjectModelProvider.getInstance().distributeRefresh();
			} catch (CoreException ce) {
				// TODO extend error handling
			}
		}
	}

}
