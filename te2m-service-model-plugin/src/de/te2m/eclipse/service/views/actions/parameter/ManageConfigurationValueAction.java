/*
* ManageConfigurationValueAction.java
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

import de.te2m.eclipse.service.model.tree.service.ConfigurationNode;
import de.te2m.eclipse.service.views.actions.AbstractTreeAction;
import de.te2m.eclipse.service.wizards.parameter.ManageConfigurationValueWizard;

/**
 * The Class ManageConfigurationValueAction.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class ManageConfigurationValueAction extends AbstractTreeAction {


	/**
	 * Instantiates a new manage configuration value action.
	 *
	 * @param v the v
	 */
	public ManageConfigurationValueAction(TreeViewer v) {
		super();
		myViewer = v;
		setText("Edit");
		setToolTipText("Edit Configuration Value");
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

		if (obj instanceof ConfigurationNode) {

			ManageConfigurationValueWizard wizard = new ManageConfigurationValueWizard(null,((ConfigurationNode)obj));

			// create wizard dialog & launch wizard dialog
			WizardDialog dialog = new WizardDialog(myViewer.getControl()
					.getShell(), wizard);
			dialog.open();
			
			myViewer.refresh();

		}
	}
}
