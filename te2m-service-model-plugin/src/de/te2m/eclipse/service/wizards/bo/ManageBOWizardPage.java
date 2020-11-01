/*
* ManageBOWizardPage.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.wizards.bo;

import java.util.UUID;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import de.te2m.api.ext.project.bo.ClassInfo;



/**
 * The Class ManageBOWizardPage.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class ManageBOWizardPage extends WizardPage {
	
	
	/**
	 * The ci.
	 */
	private ClassInfo ci;
	/**
	 * The nametext.
	 */
	private Text nametext;
	
	
	/**
	 * The pkg text.
	 */
	private Text pkgText;

	/**
	 * The descText.
	 */
	private Text desc;
	
	/**
	 * The generate button.
	 */
	private Button generateButton;

	/**
	 * The selection.
	 */
	private ISelection selection;

	/**
	 * Instantiates a new service2 code wizard page.
	 *
	 * @param info the info
	 * @param selection the selection
	 */
	public ManageBOWizardPage(ClassInfo info, ISelection selection) {
		super("wizardPage");
		setTitle("Manage Business Object");
		setDescription("Manage a master data for a business object");
		this.selection = selection;
		this.ci=info;
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
		Label label = new Label(container, 0);
		label.setText("&Name:");
		nametext = new Text(container, 2052);
		GridData gd = new GridData(768);
		nametext.setLayoutData(gd);
		nametext.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}

		});

		label = new Label(container, 0);
		label.setText("&Package:");
		pkgText = new Text(container, SWT.SINGLE);
		gd = new GridData(768);
		//gd.heightHint=40;
		pkgText.setLayoutData(gd);
		pkgText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}

		});


		
		label = new Label(container, 0);
		label.setText("&Description:");
		desc = new Text(container, SWT.MULTI);
		gd = new GridData(768);
		gd.heightHint=80;
		desc.setLayoutData(gd);
		desc.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}

		});

		generateButton = new Button(container, SWT.CHECK);
		
		generateButton.setText("Generate Code");
		
		initialize();
		dialogChanged();
		setControl(container);
	}

	/**
	 * Dialog changed.
	 */
	private void dialogChanged() {
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
	 * Gets the class info.
	 *
	 * @return the class info
	 */
	public ClassInfo getClassInfo()
	{
		if(null==ci)
		{
			ci = new ClassInfo();
		}
		if(null==ci.getId())
		{
			ci.setId(UUID.randomUUID().toString());
		}
		ci.setName(getName());

		ci.setPkg(getPackage());
		
		ci.setDescription(getDescription());
		
		ci.setGenerate(generateButton.getSelection());
		
		return ci;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.DialogPage#getDescription()
	 */
	public String getDescription() {
		return desc.getText().trim();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.WizardPage#getName()
	 */
	public String getName() {
		return nametext.getText().trim();
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
	 * Initialize.
	 */
	private void initialize() {
		if (null != ci) {
			if (null != ci.getName()) {
				nametext.setText(ci.getName());
			}
			if (null != ci.getDescription()) {
				desc.setText(ci.getDescription());
			}

			if (null != ci.getPkg()) {
				pkgText.setText(ci.getPkg());
			}

			generateButton.setSelection(ci.isGenerate());

		}
	}

	/**
	 * Sets the class info.
	 *
	 * @param info the new class info
	 */
	public void setClassInfo(ClassInfo info)
	{
		ci = info;
	}
	
	/**
	 * Update status.
	 * 
	 * @param message
	 *            the message
	 */
	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}
}
