/*
* ManageBOWizard.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.wizards.bo;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IWorkbench;

import de.te2m.api.ext.project.bo.ClassInfo;
import de.te2m.eclipse.service.views.ProjectModelProvider;
import de.te2m.project.util.ProjectUtils;

/**
 * The Class ManageBOWizard.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class ManageBOWizard extends Wizard  {
	
	/**
	 * The ci.
	 */
	private ClassInfo ci;
	
	/**
	 * The selection.
	 */
	private ISelection selection;


	
	/**
	 * The main page.
	 */
	private ManageBOWizardPage boMainPage;

	/**
	 * Instantiates a new import wizard.
	 *
	 * @param info the info
	 */
	public ManageBOWizard(ClassInfo info) {
		super();
		ci=info;
	}

	/* (non-Javadoc)
     * @see org.eclipse.jface.wizard.IWizard#addPages()
     */
    public void addPages() {
        super.addPages(); 
		this.boMainPage = new ManageBOWizardPage(ci, selection); 
		//boMainPage.setClassInfo(ci);
        addPage(boMainPage);        
    }

	/**
	 * Inits the.
	 *
	 * @param workbench the workbench
	 * @param selection the selection
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	public boolean performFinish() {

		ProjectModelProvider pmp = ProjectModelProvider.getInstance();
		
		ClassInfo ci = boMainPage.getClassInfo();
		
		ClassInfo existing = ProjectUtils.determineObjectByID(pmp.getProject(),ci.getId());
		
		if(null==existing)
		{
			pmp.getProject().getObjects().add(ci);
		}
		
		return true;
	}

}
