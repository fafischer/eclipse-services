/*
* PropertySetWizardPage.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.wizards;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;

import de.te2m.eclipse.service.model.cfg.PropertyCtnr;
import de.te2m.eclipse.service.model.cfg.PropertyGroup;

/**
 * The Class ProjectImpExWizardPage.
 * 
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class PropertySetWizardPage extends WizardPage {

	/**
	 * The pg.
	 */
	private PropertyGroup pg;

	/**
	 * The text map.
	 */
	private Map<PropertyCtnr, Widget> textMap;

	/**
	 * The enabled button.
	 */
	private Button enabledButton;

	/**
	 * The selection.
	 */
	private ISelection selection;

	/**
	 * Instantiates a new service2 code wizard page.
	 * 
	 * @param selection
	 *            the selection
	 */
	public PropertySetWizardPage(ISelection selection) {
		super("wizardPage");
		this.selection = selection;
		this.textMap = new HashMap<PropertyCtnr, Widget>();

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

		try {

			if (null != pg) {

				setTitle(pg.getName());
				setDescription(pg.getDescription());
				Label buttonLabel = new Label(container, 0);
				buttonLabel.setText(pg.getName() + " enabled:");

				enabledButton = new Button(container, SWT.CHECK);
				enabledButton.addSelectionListener(new SelectionAdapter() {

					public void widgetSelected(SelectionEvent e) {
						dialogChanged();
					}

				});
				for (Iterator iterator = pg.getProperties().iterator(); iterator
						.hasNext();) {
					PropertyCtnr ctnr = (PropertyCtnr) iterator.next();
					Label label = new Label(container, 0);
					label.setText(ctnr.getName() + ":");

					Widget text2Store;

					if (ctnr.hasValues()) {
						Combo currentText = new Combo(container, SWT.BORDER
								| SWT.READ_ONLY);
						if (null != ctnr.getValue()) {
							currentText.setText(ctnr.getValue());
						}

						for (Iterator<String> values = ctnr.getValueList()
								.iterator(); values.hasNext();) {
							String value = values.next();
							currentText.add(value);

						}
						currentText.setToolTipText(ctnr.getDescription());
						GridData gd = new GridData(768);
						currentText.setLayoutData(gd);
						if (null != ctnr.getValue()) {
							currentText.setText(ctnr.getValue());
						}
						currentText.addModifyListener(new ModifyListener() {

							public void modifyText(ModifyEvent e) {
								dialogChanged();
							}

						});
						text2Store = currentText;
					} else {
						Text currentText = new Text(container, 2052);
						if (null != ctnr.getValue()) {
							currentText.setText(ctnr.getValue());
						}
						currentText.setToolTipText(ctnr.getDescription());
						GridData gd = new GridData(768);
						currentText.setLayoutData(gd);
						currentText.addModifyListener(new ModifyListener() {

							public void modifyText(ModifyEvent e) {
								dialogChanged();
							}

						});
						text2Store = currentText;
					}

					textMap.put(ctnr, text2Store);
				}
			}
			initialize();
			dialogChanged();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		setControl(container);
	}

	/**
	 * Dialog changed.
	 */
	private void dialogChanged() {
		boolean error = false;

		if (enabledButton.getSelection()) {

			for (Iterator<PropertyCtnr> iterator = textMap.keySet().iterator(); iterator
					.hasNext();) {
				PropertyCtnr ctnr = iterator.next();

				if (ctnr.isMandantory()) {
					String txt = null;

					txt = getTextContent(ctnr);
					if (null == txt || txt.trim().length() == 0) {
						updateStatus("Property " + ctnr.getName()
								+ " must not be empty");
						error = true;
					}
				}
			}
		}
		if (!error) {
			updateStatus(null);
		}
	}

	/**
	 * Gets the property group.
	 *
	 * @return the property group
	 */
	public PropertyGroup getPropertyGroup() {
		for (Iterator iterator = textMap.keySet().iterator(); iterator
				.hasNext();) {
			PropertyCtnr ctnr = (PropertyCtnr) iterator.next();

			ctnr.setValue(getTextContent(ctnr));

		}

		return pg;
	}

	/**
	 * Gets the text content.
	 *
	 * @param ctnr the ctnr
	 * @return the text content
	 */
	protected String getTextContent(PropertyCtnr ctnr) {
		String txt;
		if (ctnr.hasValues()) {
			Combo currentText = (Combo) textMap.get(ctnr);

			txt = currentText.getText().trim();
		} else {
			Text currentText = (Text) textMap.get(ctnr);

			txt = currentText.getText().trim();
		}
		return txt;
	}

	/**
	 * Initialize.
	 */
	private void initialize() {
	}

	/**
	 * Sets the property group.
	 *
	 * @param group the new property group
	 */
	public void setPropertyGroup(PropertyGroup group)
	{
		pg=group;
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
