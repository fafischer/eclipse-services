/*
* DeleteConfigurationValueAction.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/

package de.te2m.eclipse.service.views.actions.parameter;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import de.te2m.eclipse.service.model.tree.service.ConfigurationNode;
import de.te2m.eclipse.service.model.tree.service.ConfigurationSetNode;
import de.te2m.eclipse.service.model.tree.service.ServiceNode;
import de.te2m.eclipse.service.views.actions.AbstractTreeAction;

/**
 * The Class DeleteConfigurationValueAction.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class DeleteConfigurationValueAction extends AbstractTreeAction {

	/**
	 * Instantiates a new delete configuration value action.
	 *
	 * @param v the v
	 */
	public DeleteConfigurationValueAction(TreeViewer v) {
		super();
		myViewer = v;
		setText("Delete Config");
		setToolTipText("Delete configuration value");
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_ETOOL_DELETE));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {

		ISelection selection = myViewer.getSelection();
		Object obj = ((IStructuredSelection) selection).getFirstElement();

		ITreeContentProvider tcp = (ITreeContentProvider) myViewer
				.getContentProvider();
		Object parentList = tcp.getParent(obj);
		if (parentList instanceof ConfigurationSetNode) {
			Object parent = tcp.getParent(parentList);
			if (parent instanceof ServiceNode) {
				ServiceNode pn = (ServiceNode) parent;
				ConfigurationNode cfg = (ConfigurationNode)obj;
				
				if(MessageDialog.openConfirm(myViewer.getControl().getShell(), "Delete" , "Do your really want to delete "+cfg.getName()+"?"))
				{
					pn.getConfiguration().remove(cfg.getParameterInfo());
					myViewer.refresh();
				
				}
			}

		}
	}

}
