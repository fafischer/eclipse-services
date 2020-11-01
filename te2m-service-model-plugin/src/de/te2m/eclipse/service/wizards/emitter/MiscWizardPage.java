/*
* MiscWizardPage.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.wizards.emitter;

import java.util.List;

import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import de.te2m.report.api.model.Report;

/**
 * The "New" wizard page allows setting the container for the new file as well
 * as the file name. The page will only accept file name without the extension
 * OR with the extension that matches the expected one (t4p).
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */

public class MiscWizardPage extends AbstractContainerAndPackageProcessingWizardPage {

	/**
	 * The selection.
	 */
	private ISelection selection;

	/**
	 * The nametext.
	 */
	private Text nametext;

	/**
	 * The descText.
	 */
	private Text desc;

	/**
	 * The template combo.
	 */
	private Combo templateCombo;

	/**
	 * Instantiates a new misc wizard page.
	 *
	 * @param selection the selection
	 */
	public MiscWizardPage(ISelection selection) {
		super("wizardPage");
		setTitle("Do Some Magic");
		setDescription("This wizard perfors some magic based on its wizard data.");
		this.selection = selection;
	}

	/**
	 * Creates the control.
	 *
	 * @param parent the parent
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, 0);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 2;
		layout.verticalSpacing = 4;

		createNameText(container);

		createDescriptionText(container);

		createPackageText(container);

		createProjectCombo(container);

		createTemplateCombo(container);
		// createTarget(container);

		initialize();
		dialogChanged();
		setControl(container);

	}

	/**
	 * Creates the description text.
	 *
	 * @param container the container
	 */
	protected void createDescriptionText(Composite container) {
		Label label = new Label(container, 0);
		label.setText("&Description:");
		desc = new Text(container, SWT.MULTI);
		GridData gd = new GridData(768);
		gd.heightHint = 80;
		desc.setLayoutData(gd);
		desc.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				descriptionChanged();
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
				nameChanged();
			}

		});
	}

	/**
	 * Creates the template combo.
	 *
	 * @param container the container
	 */
	protected void createTemplateCombo(Composite container) {

		Label label = new Label(container, 0);
		label.setText("&Template:");
		templateCombo = new Combo(container, SWT.BORDER | SWT.READ_ONLY);
		templateCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		templateCombo.setItems(getTemplateItems());
		templateCombo.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				templateChanged();
			}

		});
	}

	/**
	 * Description changed.
	 */
	protected void descriptionChanged() {
		// Accept everything for now

	}

	/**
	 * Ensures that both text fields are set.
	 */

	protected void dialogChanged() {

		super.dialogChanged();
		nameChanged();
		descriptionChanged();
		templateChanged();
	}

	/**
	 * Gets the description text.
	 *
	 * @return the description text
	 */
	public String getDescriptionText() {

		return desc.getText();
	}

	/**
	 * Gets the name text.
	 *
	 * @return the name text
	 */
	public String getNameText() {

		return nametext.getText();
	}

	/**
	 * Gets the report template.
	 *
	 * @return the report template
	 */
	public Report getReportTemplate() {
		String templateName = templateCombo.getText();

		List<Report> reports = TemplateUtils.getInstance().getList();

		for (Report tmpl : reports) {
			if (templateName.equals(tmpl.getName())) {
				return tmpl;
			}
		}

		return null;
	}

	/**
	 * Initialize template combo.
	 *
	 * @return the template items
	 */
	protected String[] getTemplateItems() {
		List<Report> reports = TemplateUtils.getInstance().getList();

		String[] items;

		if (null != reports) {
			items = new String[reports.size() + 1];
		} else {
			items = new String[1];
		}

		int i = 0;

		items[i] = "--- No Report selected ---";

		if (null != reports) {
			for (Report tmpl : reports) {
				i++;
				items[i] = tmpl.getName();
			}
		}

		return items;
	}

	/**
	 * Gets the template text.
	 *
	 * @return the template text
	 */
	public String getTemplateText() {
		return templateCombo.getText();
	}

	/* (non-Javadoc)
	 * @see de.te2m.eclipse.service.wizards.emitter.AbstractContainerAndPackageProcessingWizardPage#initialize()
	 */
	@Override
	protected void initialize() {

		super.initialize();
		// initializeTemplateCombo();
	}

	/**
	 * Name changed.
	 */
	protected void nameChanged() {
		String name = getNameText();

		if (name.length() == 0) {
			updateStatus("Name must be specified");
			return;
		}
		updateStatus(null);
	}

	/**
	 * Template changed.
	 */
	protected void templateChanged() {
		String name = getTemplateText();

		if (name.length() == 0) {
			updateStatus("Template must be specified");
			return;
		}
		updateStatus(null);

	}

}