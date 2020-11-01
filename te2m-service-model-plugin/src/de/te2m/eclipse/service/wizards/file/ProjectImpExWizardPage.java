/*
* ProjectImpExWizardPage.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.wizards.file;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;

import de.te2m.eclipse.service.wizards.AbstractProjectSelectionHandlingWizardPage;

/**
 * The Class ProjectImpExWizardPage.
 * 
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class ProjectImpExWizardPage extends AbstractProjectSelectionHandlingWizardPage {

	/**
	 * The ctnr hint.
	 */
	private String ctnrHint;

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
	public ProjectImpExWizardPage(ISelection selection) {
		super("wizardPage");
		this.selection = selection;
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
		createProjectCombo(container);
		initialize();
		dialogChanged();
		setControl(container);
	}

	/**
	 * Initialize.
	 */
	protected void initialize() {
		super.initialize();
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
