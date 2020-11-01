/*
* DeleteDeclaredErrorAction.java
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

import de.te2m.eclipse.service.model.tree.service.DeclaredErrorListNode;
import de.te2m.eclipse.service.model.tree.service.DeclaredErrorNode;
import de.te2m.eclipse.service.model.tree.service.OperationNode;
import de.te2m.eclipse.service.model.tree.service.ReturnsErrors;
import de.te2m.eclipse.service.views.actions.AbstractTreeAction;

/**
 * The Class DeleteDeclaredErrorAction.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class DeleteDeclaredErrorAction extends AbstractTreeAction {

	/**
	 * Instantiates a new delete declared error action.
	 *
	 * @param v the v
	 */
	public DeleteDeclaredErrorAction(TreeViewer v) {
		super();
		myViewer = v;
		setText("Delete");
		setToolTipText("Delete a declared error that my be thrown by the operation");
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
		Object parentErrorList = tcp.getParent(obj);
		if (parentErrorList instanceof DeclaredErrorListNode) {
			Object parent = tcp.getParent(parentErrorList);
			if (parent instanceof OperationNode) {
				ReturnsErrors pn = (ReturnsErrors) parent;
				DeclaredErrorNode error = (DeclaredErrorNode)obj;
				
				if(MessageDialog.openConfirm(myViewer.getControl().getShell(), "Delete Declared Error" , "Do your really want to delete "+error.getName()+"?"))
				{
					pn.getDeclaredErrors().remove(error.getParameterInfo());
					myViewer.refresh();
				
				}
			}

		}
	}

}
