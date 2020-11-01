/*
* ManageParameterWizardPage.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.wizards.parameter;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import de.te2m.eclipse.service.model.tree.service.SimpleParameterNode;
import de.te2m.eclipse.service.wizards.AbstractMasterDataPage;

/**
 * The Class ManageParameterWizardPage.
 * 
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class ManageParameterWizardPage extends AbstractMasterDataPage {

	/**
	 * The typeext.
	 */
	private Text typeext;

	
	/**
	 * The pkg text.
	 */
	private Text pkgText;

	/**
	 * The spn.
	 */
	private SimpleParameterNode spn;


	/**
	 * Instantiates a new service2 code wizard page.
	 * 
	 * @param selection
	 *            the selection
	 */
	public ManageParameterWizardPage(ISelection selection) {
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

		Label label = new Label(container, 0);
		label.setText("&Type:");
		typeext = new Text(container, 2052);
		GridData gd = new GridData(768);
		typeext.setLayoutData(gd);
		typeext.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}

		});

		label = new Label(container, 0);
		label.setText("&Package:");
		pkgText = new Text(container, 2052);
		gd = new GridData(768);
		pkgText.setLayoutData(gd);
		pkgText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}

		});

		initialize();
		dialogChanged();
		setControl(container);
	}

	/**
	 * Dialog changed.
	 */
	protected void dialogChanged() {

	}

	/**
	 * Gets the package.
	 *
	 * @return the package
	 */
	public String getPackage() {
		return pkgText.getText().trim();
	}


	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return typeext.getText().trim();
	}

	/**
	 * Initialize.
	 */
	protected void initialize() {
		if (null != spn) {
			if (null != spn.getName()) {
				nametext.setText(spn.getName());
			}
			if(null!=spn.getDescription())
			{
				descText.setText(spn.getDescription());
			}
			if (null != spn.getParamClass()&& null!=spn.getParamClass().getName()) {
				typeext.setText(spn.getParamClass().getName());
			}

			if (null != spn.getParamClass()&& null!=spn.getParamClass().getPkg()) {
				pkgText.setText(spn.getParamClass().getPkg());
			}

		}
	}
	
	/**
	 * Sets the spn.
	 *
	 * @param spn the new spn
	 */
	public void setSpn(SimpleParameterNode spn) {
		this.spn = spn;
	}

}
