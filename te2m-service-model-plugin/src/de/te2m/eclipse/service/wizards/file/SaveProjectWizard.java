/*
* SaveProjectWizard.java
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

import de.te2m.eclipse.service.model.tree.service.ProjectNode;
import de.te2m.eclipse.service.views.ProjectModelProvider;

/**
 * The Class SaveProjectWizard.
 * 
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class SaveProjectWizard extends AbstractImpExProcessingWizard implements INewWizard {

	/**
	 * The model.
	 */
	ProjectNode model;

	/**
	 * Constructor for SaveProjectWizard.
	 */
	public SaveProjectWizard() {
		this(null);
	}

	
	/**
	 * Instantiates a new save project wizard.
	 *
	 * @param projectToSave the project to save
	 */
	public SaveProjectWizard(ProjectNode projectToSave) {
		super(null!=projectToSave&&null!=projectToSave.getStorageLocationHint()?projectToSave.getStorageLocationHint():"");
		setNeedsProgressMonitor(true);
		model = projectToSave;
	}


	/* (non-Javadoc)
	 * @see de.te2m.eclipse.service.wizards.file.AbstractImpExProcessingWizard#determineTargetName()
	 */
	@Override
	protected String determineTargetName() {
		return model.getName();
	}


	/* (non-Javadoc)
	 * @see de.te2m.eclipse.service.wizards.file.AbstractImpExProcessingWizard#getDescription()
	 */
	@Override
	protected String getDescription() {

		return "Export the current project into a project file.";
	}


	/* (non-Javadoc)
	 * @see de.te2m.eclipse.service.wizards.file.AbstractImpExProcessingWizard#getTitle()
	 */
	@Override
	protected String getTitle() {
		return "Export project file";
	}


	/* (non-Javadoc)
	 * @see de.te2m.eclipse.service.wizards.file.AbstractImpExProcessingWizard#processTask(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.resources.IContainer)
	 */
	protected void processTask(IProgressMonitor monitor, IContainer container)
			throws CoreException {
			
			
			ProjectModelProvider.getInstance().saveProjectToContainer(container);
		
			container.refreshLocal(2, monitor);
	}

	

}