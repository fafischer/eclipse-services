/*
* WSDLImportWizard.java
*   
* Copyright 2009 - 2013 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-axis2-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.tools.plugin.wizard;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;

/**
 * The Class WSDLImportWizard.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class WSDLImportWizard extends Wizard implements IImportWizard {
	
	/**
	 * The main page.
	 */
	WSDLImportWizardPage mainPage;

	/**
	 * Instantiates a new wSDL import wizard.
	 */
	public WSDLImportWizard() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	public boolean performFinish() {
		IFile file = mainPage.createNewFile();
        if (file == null)
            return false;
        return true;
	}
	 
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle("File Import Wizard"); //NON-NLS-1
		setNeedsProgressMonitor(true);
		mainPage = new WSDLImportWizardPage("Create mvn Service Project (AXIS2)",selection); //NON-NLS-1
	}
	
	/* (non-Javadoc)
     * @see org.eclipse.jface.wizard.IWizard#addPages()
     */
    public void addPages() {
        super.addPages(); 
        addPage(mainPage);        
    }

}
