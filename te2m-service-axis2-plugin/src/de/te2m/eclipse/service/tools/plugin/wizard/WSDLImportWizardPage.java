/*
* WSDLImportWizardPage.java
*   
* Copyright 2009 - 2013 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-axis2-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.tools.plugin.wizard;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;


/**
 * The Class WSDLImportWizardPage.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class WSDLImportWizardPage extends WizardNewFileCreationPage {
	
	/**
	 * The editor.
	 */
	protected FileFieldEditor editor;

	/**
	 * Instantiates a new wSDL import wizard page.
	 *
	 * @param pageName the page name
	 * @param selection the selection
	 */
	public WSDLImportWizardPage(String pageName, IStructuredSelection selection) {
		super(pageName, selection);
		setTitle(pageName); //NON-NLS-1
		setDescription("Import a file from the local file system into the workspace"); //NON-NLS-1
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.dialogs.WizardNewFileCreationPage#createAdvancedControls(org.eclipse.swt.widgets.Composite)
	 */	
	protected void createAdvancedControls(Composite parent) {
		Composite fileSelectionArea = new Composite(parent, SWT.NONE);
		GridData fileSelectionData = new GridData(GridData.GRAB_HORIZONTAL
				| GridData.FILL_HORIZONTAL);
		fileSelectionArea.setLayoutData(fileSelectionData);

		GridLayout fileSelectionLayout = new GridLayout();
		fileSelectionLayout.numColumns = 3;
		fileSelectionLayout.makeColumnsEqualWidth = false;
		fileSelectionLayout.marginWidth = 0;
		fileSelectionLayout.marginHeight = 0;
		fileSelectionArea.setLayout(fileSelectionLayout);
		
		editor = new FileFieldEditor("fileSelect","Select File: ",fileSelectionArea); //NON-NLS-1 //NON-NLS-2
		editor.getTextControl(fileSelectionArea).addModifyListener(new ModifyListener(){
			public void modifyText(ModifyEvent e) {
				IPath path = new Path(WSDLImportWizardPage.this.editor.getStringValue());
				setFileName(path.lastSegment());
			}
		});
		String[] extensions = new String[] { "*.*" }; //NON-NLS-1
		editor.setFileExtensions(extensions);
		fileSelectionArea.moveAbove(null);

	}
	
	 /* (non-Javadoc)
	 * @see org.eclipse.ui.dialogs.WizardNewFileCreationPage#createLinkTarget()
	 */
	protected void createLinkTarget() {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.dialogs.WizardNewFileCreationPage#getInitialContents()
	 */
	protected InputStream getInitialContents() {
		try {
			return new FileInputStream(new File(editor.getStringValue()));
		} catch (FileNotFoundException e) {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.dialogs.WizardNewFileCreationPage#getNewFileLabel()
	 */
	protected String getNewFileLabel() {
		return "New File Name:"; //NON-NLS-1
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.dialogs.WizardNewFileCreationPage#validateLinkedResource()
	 */
	protected IStatus validateLinkedResource() {
		return new Status(IStatus.OK, "te2m-service-tools-plugin", IStatus.OK, "", null); //NON-NLS-1 //NON-NLS-2
	}
}
