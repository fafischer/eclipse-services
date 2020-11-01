/*
* MasterDataWizardPage.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.wizards;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import de.te2m.eclipse.service.model.tree.AttributedTreeParentNode;

/**
 * The Class MasterDataWizardPage.
 * 
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class MasterDataWizardPage extends AbstractMasterDataPage {

	/**
	 * The spn.
	 * @deprecated
	 */
	protected AttributedTreeParentNode spn;


	/**
	 * Instantiates a new master data wizard page.
	 *
	 * @param selection the selection
	 */
	public MasterDataWizardPage(ISelection selection) {
		super(selection);
		setTitle("Manage Parameter");
		setDescription("Manage a parameter.");
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

		initialize();
		dialogChanged();
		setControl(container);
	}

	/**
	 * Dialog changed.
	 */
	protected void dialogChanged() {
		String key = getName();
		if (key.length() == 0) {
			updateStatus("Name must be specified");
			return;
		} else {
			updateStatus(null);
			return;
		}
	}

	/**
	 * Initialize.
	 */
	protected void initialize() {
		
		// using tree nodes directly for accessing the existing
		// name and description is deprecated
		// ToDo Rework
		
		if (null != spn) {
			if (null != spn.getName()) {
				nametext.setText(spn.getName());
			}
			if (null != spn.getDescription()) {
				descText.setText(spn.getDescription());
			}

		}
		
		super.initialize();

	}

	/**
	 * Sets the spn.
	 *
	 * @param spn the new spn
	 * @deprecated
	 */
	public void setSpn(AttributedTreeParentNode spn) {
		this.spn = spn;
	}

	/**
	 * Update status.
	 * 
	 * @param message
	 *            the message
	 */
	protected void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

}
