/*
* LoadProjectWizard.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.wizards.file;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.INewWizard;

import de.te2m.eclipse.service.model.tree.service.ServiceViewRootNode;
import de.te2m.eclipse.service.views.ProjectModelProvider;

/**
 * The Class LoadProjectWizard.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class LoadProjectWizard extends AbstractImpExProcessingWizard implements INewWizard {

	/**
	 * The root node.
	 */
	private ServiceViewRootNode serviceViewRootNode;

	/**
	 * Instantiates a new load project wizard.
	 */
	public LoadProjectWizard() {
		super("");
		setNeedsProgressMonitor(true);
	}


	/**
	 * Instantiates a new load project wizard.
	 *
	 * @param root the root
	 */
	public LoadProjectWizard(ServiceViewRootNode root) {
		super("");
		setNeedsProgressMonitor(true);
		serviceViewRootNode=root;
	}

	/* (non-Javadoc)
	 * @see de.te2m.eclipse.service.wizards.file.AbstractImpExProcessingWizard#determineTargetName()
	 */
	@Override
	protected String determineTargetName() {
		return "Import Project";
	}

	/* (non-Javadoc)
	 * @see de.te2m.eclipse.service.wizards.file.AbstractImpExProcessingWizard#getDescription()
	 */
	@Override
	protected String getDescription() {

		return "Load a project file from disc and open it in the service modelling view";
	}


	/* (non-Javadoc)
	 * @see de.te2m.eclipse.service.wizards.file.AbstractImpExProcessingWizard#getTitle()
	 */
	@Override
	protected String getTitle() {
		return "Import project file";
	}


	/* (non-Javadoc)
	 * @see de.te2m.eclipse.service.wizards.file.AbstractImpExProcessingWizard#processTask(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.resources.IContainer)
	 */
	@Override
	protected void processTask(IProgressMonitor monitor, IContainer container)
			throws CoreException {
		
		
			ProjectModelProvider.getInstance().loadFromContainer(container);
		
	}

	

}