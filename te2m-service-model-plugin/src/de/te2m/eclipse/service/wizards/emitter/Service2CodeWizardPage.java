/*
* Service2CodeWizardPage.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.wizards.emitter;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import de.te2m.eclipse.service.ServiceModelPlugin;
import de.te2m.eclipse.service.preferences.GeneratorPreferenceConstants;

/**
 * The Class Service2CodeWizardPage.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class Service2CodeWizardPage extends AbstractContainerAndPackageProcessingWizardPage
{


    /**
     * The selection.
     */
    private ISelection selection;
    
    /**
     * The target.
     */
    Combo target;
    
    
    

    /**
     * Instantiates a new service2 code wizard page.
     *
     * @param selection the selection
     */
    public Service2CodeWizardPage(ISelection selection)
    {
        super("wizardPage");
        setTitle("Convert Service Models to Java Classes");
        setDescription("This wizard creates Java code from service models");
        this.selection = selection;
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    public void createControl(Composite parent)
    {
        Composite container = new Composite(parent, 0);
        GridLayout layout = new GridLayout();
        container.setLayout(layout);
        layout.numColumns = 2;
        layout.verticalSpacing = 4;


		createProjectCombo(container);

		createPackageText(container);
		createTarget(container);

        initialize();
        dialogChanged();
        setControl(container);
        

    }

	/**
	 * Creates the target.
	 *
	 * @param container the container
	 */
	protected void createTarget(Composite container) {
		Label label = new Label(container, 0);
        label.setText("Code Generation Target: ");
        target = new Combo(container,  SWT.BORDER | SWT.READ_ONLY);
        target.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        target.setItems(GeneratorPreferenceConstants.GENERATOR_TARGET_VALUES_WIZARD);
        target.addModifyListener(new ModifyListener()
        {

            public void modifyText(ModifyEvent e)
            {
                targetChanged();
            }

        });
	}

	/**
	 * Dialog changed.
	 */
	protected void dialogChanged() {

	    super.dialogChanged();
		targetChanged();
	}

	/**
     * Gets the target.
     *
     * @return the target
     */
    public String getTarget()
    {
    	return target.getText().trim();
    }

	/**
     * Initialize.
     */
    protected void initialize()
    {
    	super.initialize();
        initializeTarget();
    }

	/**
	 * Initialize target.
	 */
	protected void initializeTarget() {
		target.setText(ServiceModelPlugin.getDefault().getPreferenceStore()
                .getString(GeneratorPreferenceConstants.PREFERENCE_TARGET));
	}
	
    /**
     * Target changed.
     */
    protected void targetChanged() {
		String target = getTarget();

	    if (target.length() == 0)
	    {
	        updateStatus("Code generation target must be specified");
	        return;
	    }
	    updateStatus(null);
	}

}
