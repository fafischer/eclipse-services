/*
* AbstractServiceMasterDataPage.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.wizards.service;

import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import de.te2m.api.ext.project.service.Service;
import de.te2m.eclipse.service.wizards.AbstractMasterDataPage;
import de.te2m.eclipse.service.wizards.service.template.ServiceTemplate;
import de.te2m.eclipse.service.wizards.service.template.ServiceTemplateUtils;

/**
 * The Class AbstractServiceMasterDataPage.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractServiceMasterDataPage extends AbstractMasterDataPage {

	/**
	 * The new service.
	 */
	private Service newService;
	/**
	 * The template combo.
	 */
	protected Combo templateCombo;

	/**
	 * The behavior button.
	 */
	protected Button behaviorButton;
	/**
	 * The templates.
	 */
	protected List<ServiceTemplate> templates;

	/**
	 * Instantiates a new new service master data wizard page.
	 *
	 * @param selection the selection
	 */
	public AbstractServiceMasterDataPage(ISelection selection) {
		this("wizardPage");
		this.selection = selection;
		templates = ServiceTemplateUtils.getInstance().getList();
	}

	/**
	 * Instantiates a new abstract service master data page.
	 *
	 * @param pageName the page name
	 */
	public AbstractServiceMasterDataPage(String pageName) {
		super(pageName);
	}

	/**
	 * Instantiates a new abstract service master data page.
	 *
	 * @param pageName   the page name
	 * @param title      the title
	 * @param titleImage the title image
	 */
	public AbstractServiceMasterDataPage(String pageName, String title, ImageDescriptor titleImage) {
		super(pageName, title, titleImage);
	}

	/**
	 * Creates the behavior button.
	 *
	 * @param container the container
	 */
	protected void createBehaviorButton(Composite container) {
		Label label;
		// Templates?
		label = new Label(container, 0);
		label.setText("Service Client: ");
		behaviorButton = new Button(container, SWT.CHECK);
	}

	/**
	 * Creates the template combo.
	 *
	 * @param container the container
	 */
	protected void createTemplateCombo(Composite container) {
		Label label;
		// Templates?
		label = new Label(container, 0);
		label.setText("Template: ");
		templateCombo = new Combo(container, SWT.BORDER | SWT.READ_ONLY);
		templateCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	}

	/**
	 * Gets the object description.
	 *
	 * @return the object description
	 */
	public String getObjectDescription() {
		return descText.getText().trim();
	}

	/**
	 * Gets the object name.
	 *
	 * @return the object name
	 */
	public String getObjectName() {
		return nametext.getText().trim();
	}

	/**
	 * Gets the service.
	 *
	 * @return the service
	 */
	public Service getService() {
		return newService;
	}

	/**
	 * Gets the service template.
	 *
	 * @return the service template
	 */
	public ServiceTemplate getServiceTemplate() {
		String templateName = templateCombo.getText();

		for (ServiceTemplate tmpl : templates) {
			if (templateName.equals(tmpl.getName())) {
				return tmpl;
			}
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.te2m.eclipse.service.wizards.AbstractMasterDataPage#initialize()
	 */
	protected void initialize() {
		super.initialize();
		initializeTemplateCombo();
	}

	/**
	 * Initialize template combo.
	 */
	protected void initializeTemplateCombo() {

		String[] items;

		if (null != templates) {
			items = new String[templates.size() + 1];
		} else {
			items = new String[1];
		}

		int i = 0;

		items[i] = "--- No Service Template ---";

		if (null != templates) {

			for (ServiceTemplate tmpl : templates) {
				i++;
				items[i] = tmpl.getName();
			}
		}
		templateCombo.setItems(items);
	}

	/**
	 * Checks if is service client.
	 *
	 * @return true, if is service client
	 */
	public boolean isServiceClient() {
		return behaviorButton.getSelection();
	}
}