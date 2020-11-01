/*
* WSDLImportWizardPage.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.wizards.service;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * The Class WSDLImportWizardPage.
 * A simple page for selecting an existing WSDL file.
 * 
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class WSDLImportWizardPage extends AbstractServiceMasterDataPage {

	/**
	 * The ctnr hint.
	 */
	private String ctnrHint;

	/**
	 * The container text.
	 */
	private Text fileNameText;
	
	/**
	 * Instantiates a new WSDL import wizard page.
	 * 
	 * @param selection
	 *            the selection
	 */
	public WSDLImportWizardPage(ISelection selection) {
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
		Label label = new Label(container, 0);
		label.setText("&WSDL:");
		fileNameText = new Text(container, 2052);
		GridData gd = new GridData(768);
		fileNameText.setLayoutData(gd);
		fileNameText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				dialogWSDLChanged();
			}

		});
		Button button = new Button(container, 8);
		button.setText("Browse...");
		button.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				handleBrowse();
			}

		});
		
		createBehaviorButton(container);
		
		createTemplateCombo(container);
		
		initialize();
		dialogChanged();
		setControl(container);
	}



	/**
	 * Dialog changed.
	 */
	private void dialogWSDLChanged() {
		String container = getWSDLFileName();
		if (container.length() == 0) {
			updateStatus("WSDL file must be specified");
			return;
		} else {
			updateStatus(null);
			return;
		}
	}

	/**
	 * Gets the container name.
	 * 
	 * @return the container name
	 */
	public String getWSDLFileName() {
		return fileNameText.getText().trim();
	}

	/**
	 * Uses the standard file selection dialog to choose the new value for
	 * the WSDL file name field.
	 */

	private void handleBrowse() {
		FileDialog dialog = new FileDialog(getShell(),SWT.SINGLE);
		String result = dialog.open();
		//dialog.getFileName();
		if (null!=result) {
			fileNameText.setText(result);
		}	
	}


	/**
	 * Initialize.
	 */
	protected void initialize() {
		
		super.initialize();
		try {
			if (null != ctnrHint) {
				fileNameText.setText(ctnrHint);
			} else {
				if (selection != null && !selection.isEmpty()
						&& (selection instanceof IStructuredSelection)) {
					IStructuredSelection ssel = (IStructuredSelection) selection;
					if (ssel.size() > 1)
						return;
					Object obj = ssel.getFirstElement();
					if (obj instanceof IResource) {
						IContainer container;
						if (obj instanceof IContainer)
							container = (IContainer) obj;
						else
							container = ((IResource) obj).getParent();
						String rawContainerString = container.getFullPath()
								.toString().substring(1);
						fileNameText.setText(rawContainerString.substring(0,
								rawContainerString.indexOf("/")));
					}
				}
			}
		}

		catch (Exception exception) {
		}
		
		initializeTemplateCombo();
	}

	/**
	 * Sets the container name hint.
	 *
	 * @param cName the new container name hint
	 */
	public void setContainerNameHint(String cName) {
		ctnrHint = cName;
	}

}
