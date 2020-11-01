/*
* AbstractMasterDataPage.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.wizards;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * The Class AbstractMasterDataPage.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractMasterDataPage extends WizardPage {

	/**
	 * The initial name.
	 */
	private String initialName;
	
	/**
	 * The initial description.
	 */
	private String initialDescription;
	
	/**
	 * The nametext.
	 */
	protected Text nametext;
	/**
	 * The descText.
	 */
	protected Text descText;
	/**
	 * The selection.
	 */
	protected ISelection selection;

	/**
	 * Instantiates a new new service master data wizard page.
	 *
	 * @param myselection the myselection
	 */
	public AbstractMasterDataPage(ISelection myselection) {
		super("wizardPage");
		selection = myselection;
	}

	/**
	 * Instantiates a new abstract master data page.
	 *
	 * @param pageName the page name
	 */
	public AbstractMasterDataPage(String pageName) {
		super(pageName);
	}
	
	
	/**
	 * Instantiates a new abstract master data page.
	 *
	 * @param pageName the page name
	 * @param title the title
	 * @param titleImage the title image
	 */
	public AbstractMasterDataPage(String pageName, String title,
			ImageDescriptor titleImage) {
		super(pageName, title, titleImage);
	}

	/**
	 * Creates the description text.
	 *
	 * @param container the container
	 */
	protected void createDescriptionText(Composite container) {
		Label label;
		GridData gd= new GridData(768);
		label = new Label(container, 0);
		label.setText("&Description:");
		descText = new Text(container, SWT.MULTI);
		gd.heightHint=100;
		descText.setLayoutData(gd);
		descText.addModifyListener(new ModifyListener() {
	
			public void modifyText(ModifyEvent e) {
				dialogDescriptionChanged();
			}
	
	
	
		});
	}

	/**
	 * Creates the name text.
	 *
	 * @param container the container
	 */
	protected void createNameText(Composite container) {
		Label label = new Label(container, 0);
		label.setText("&Name:");
		nametext = new Text(container, 2052);
		GridData gd = new GridData(768);
		nametext.setLayoutData(gd);
		nametext.addModifyListener(new ModifyListener() {
	
			public void modifyText(ModifyEvent e) {
				dialogNameChanged();
			}
	
		});
	}

	/**
	 * Dialog changed.
	 */
	protected void dialogChanged() {
	
		
	}

	/**
	 * Dialog description changed.
	 */
	protected void dialogDescriptionChanged() {
	
		updateStatus(null);
	}

	/**
	 * Dialog name changed.
	 */
	protected void dialogNameChanged() {
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
	 * Gets the desc text.
	 *
	 * @return the desc text
	 */
	protected Text getDescText() {
		return descText;
	}

	/**
	 * Gets the entered description.
	 *
	 * @return the entered description
	 */
	public String getEnteredDescription()
	{
		return getDescText().getText().trim();
	}

	/**
	 * Gets the entered name.
	 *
	 * @return the entered name
	 */
	public String getEnteredName()
	{
		return getNametext().getText().trim();
	}

	/**
	 * Gets the nametext.
	 *
	 * @return the nametext
	 */
	protected Text getNametext() {
		return nametext;
	}

	/**
	 * Initialize.
	 */
	protected void initialize() {	
		if(null!=nametext&&null!=initialName)
		{
			nametext.setText(initialName);
		}
		if(null!=descText&&null!=initialDescription)
		{
			descText.setText(initialDescription);
		}
	}

	/**
	 * Sets the default description.
	 *
	 * @param desc the new default description
	 */
	public void setDefaultDescription(String desc) {
		initialDescription=desc;
	}

	/**
	 * Sets the default name.
	 *
	 * @param name the new default name
	 */
	public void setDefaultName(String name) {
		initialName=name;
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