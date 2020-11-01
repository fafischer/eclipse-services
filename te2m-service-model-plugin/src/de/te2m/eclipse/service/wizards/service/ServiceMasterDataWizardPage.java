/*
* ServiceMasterDataWizardPage.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.wizards.service;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * The Class ServiceMasterDataWizardPage.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class ServiceMasterDataWizardPage extends AbstractServiceMasterDataPage {

	/**
	 * Instantiates a new service master data wizard page.
	 *
	 * @param selection the selection
	 */
	public ServiceMasterDataWizardPage(ISelection selection) {
		super(selection);
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets
	 * .Composite)
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, 0);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 2;
		layout.verticalSpacing = 4;

		createNameText(container);
		
		createDescriptionText(container);
		
		createBehaviorButton(container);
		
		createTemplateCombo(container);
		
		initialize();
		dialogChanged();
		setControl(container);
	}


	/* (non-Javadoc)
	 * @see de.te2m.eclipse.service.wizards.service.AbstractServiceMasterDataPage#initialize()
	 */
	@Override
	protected void initialize() {
		super.initialize();

	}
	
	
	
}
