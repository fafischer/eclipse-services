/*
* NewServiceMasterDataWizardPage.java
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

import de.te2m.eclipse.service.wizards.service.template.ServiceTemplateUtils;

/**
 * The Class MasterDataWizardPage.
 * 
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class NewServiceMasterDataWizardPage extends AbstractServiceMasterDataPage {

	/**
	 * Instantiates a new new service master data wizard page.
	 *
	 * @param selection the selection
	 */
	public NewServiceMasterDataWizardPage(ISelection selection) {
		super("wizardPage");
		setTitle("Create a new Service");
		setDescription("Create a new Service");
		this.selection = selection;
		templates= ServiceTemplateUtils.getInstance().getList();
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

		//
		
		initialize();
		dialogChanged();
		setControl(container);
	}
}
