/*
* LoadProjectAction.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.views.actions.project;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import de.te2m.eclipse.service.model.tree.service.ServiceViewRootNode;
import de.te2m.eclipse.service.views.ProjectModelProvider;
import de.te2m.eclipse.service.views.actions.AbstractTreeAction;
import de.te2m.eclipse.service.wizards.file.AbstractImpExProcessingWizard;
import de.te2m.eclipse.service.wizards.file.LoadProjectWizard;

/**
 * The Class SaveProjectAction.
 * 
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class LoadProjectAction extends AbstractTreeAction {

	/**
	 * The root.
	 */
	private ServiceViewRootNode root;

	/**
	 * Instantiates a new save project action.
	 *
	 * @param v the v
	 * @param hiddenRoot the hidden root
	 */
	public LoadProjectAction(TreeViewer v, ServiceViewRootNode hiddenRoot) {
		super();
		myViewer = v;
		root = hiddenRoot;
		setText("Load");
		setToolTipText("Load Project");
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_ETOOL_SAVEALL_EDIT));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {

		AbstractImpExProcessingWizard wizard = new LoadProjectWizard(root);

		// create wizard dialog & launch wizard dialog
		WizardDialog dialog = new WizardDialog(
				myViewer.getControl().getShell(), wizard);
		dialog.open();

		//myViewer.refresh();

		ProjectModelProvider.getInstance().distributeRefresh();

	}
	
}
