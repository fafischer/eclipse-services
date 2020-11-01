/*
* CreateProjectAction.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.views.actions.project;

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import de.te2m.api.ext.project.Project;
import de.te2m.eclipse.service.views.ProjectModelProvider;
import de.te2m.eclipse.service.views.actions.AbstractTreeAction;

/**
 * The Class CreateProjectAction.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class CreateProjectAction extends AbstractTreeAction {


	/**
	 * Instantiates a new creates the project action.
	 *
	 * @param v the v
	 */
	public CreateProjectAction(TreeViewer v) {
		super();
		myViewer = v;
		setText("Create");
		setToolTipText("Create Project");
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_OBJ_ADD));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		
		InputDialog inpD = new InputDialog(
				myViewer.getControl().getShell(), "Add",
				"Add Project", "", null);

		inpD.open();

		if (inpD.getReturnCode() == Window.OK) {
			
			Project p = new Project();
			
			p.setName(inpD.getValue());
			
			ProjectModelProvider.getInstance().createProjectNode(p);

			ProjectModelProvider.getInstance().distributeRefresh();
		
		}

	}

}
