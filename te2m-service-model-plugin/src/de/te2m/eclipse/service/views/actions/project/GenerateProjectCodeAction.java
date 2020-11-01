/*
* GenerateProjectCodeAction.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.views.actions.project;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import de.te2m.eclipse.service.views.actions.AbstractTreeAction;
import de.te2m.eclipse.service.wizards.emitter.AbstractReportingWizard;
import de.te2m.eclipse.service.wizards.emitter.Project2CodeWizard;


/**
 * The Class GenerateProjectCodeAction.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class GenerateProjectCodeAction extends AbstractTreeAction {

	/**
	 * Instantiates a new generate service code action.
	 * 
	 * @param v
	 *            the v
	 */
	public GenerateProjectCodeAction(TreeViewer v) {
		super();
		myViewer = v;
		setText("Generate");
		setToolTipText("Generate report for Project");
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


			AbstractReportingWizard wizard = new Project2CodeWizard();

			// create wizard dialog & launch wizard dialog
			WizardDialog dialog = new WizardDialog(myViewer.getControl()
					.getShell(), wizard);
			dialog.open();

			myViewer.refresh();

	}

}
