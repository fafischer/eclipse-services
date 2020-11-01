/*
* Project2CodeWizardPage.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.wizards.emitter;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
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
import org.eclipse.ui.dialogs.ContainerSelectionDialog;

import de.te2m.eclipse.service.ServiceModelPlugin;
import de.te2m.eclipse.service.preferences.GeneratorPreferenceConstants;
import de.te2m.eclipse.service.wizards.AbstractProjectSelectionHandlingWizardPage;

/**
 * The Class Service2CodeWizardPage.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class Project2CodeWizardPage extends AbstractProjectSelectionHandlingWizardPage
{


    /**
     * The package text.
     */
    private Text packageText;

    /**
     * The selection.
     */
    private ISelection selection;
    

    
    
    

    /**
     * Instantiates a new service2 code wizard page.
     *
     * @param selection the selection
     */
    public Project2CodeWizardPage(ISelection selection)
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
        GridData gd = new GridData(768);
        gd.horizontalSpan = 2;
        createProjectCombo(container);
        Label label = new Label(container, 0);
        label.setText("Base Package Name: ");
        packageText = new Text(container, 2052);
        packageText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        packageText.addModifyListener(new ModifyListener()
        {

            public void modifyText(ModifyEvent e)
            {
                packageChanged();
            }

        });

        initialize();
        dialogChanged();
        setControl(container);
    }

    /**
     * Dialog changed.
     */
    protected void dialogChanged()
    {
        super.dialogChanged();
    	
    	packageChanged();
    }

	/**
     * Gets the container name.
     *
     * @return the container name
     */
    public String getContainerName()
    {
        return containerText.getText().trim();
    }


    /**
     * Gets the package name.
     *
     * @return the package name
     */
    public String getPackageName()
    {
        return packageText.getText().trim();
    }

    /**
     * Initialize.
     */
    protected void initialize()
    {
    	super.initialize();
        
        // check last used package name (hidden value from cfg)
        String tmpPkgString=ServiceModelPlugin.getDefault().getPreferenceStore()
                .getString(GeneratorPreferenceConstants.PREFERENCE_RECENTLY_USED_PACKAGE);
        
        // not available?
        if(null==tmpPkgString||tmpPkgString.trim().length()==0)
        {
        	// fallback to default base package
        	tmpPkgString=ServiceModelPlugin.getDefault().getPreferenceStore()
                    .getString(GeneratorPreferenceConstants.PREFERENCE_BASE_PACKAGE);
        }
        
        
        if(null!=tmpPkgString||tmpPkgString.trim().length()>0)
        {
        	packageText.setText(tmpPkgString);
        }
    }

    /**
     * Package changed.
     */
    protected void packageChanged() {
		String packName = getPackageName();

        if (packName.length() == 0)
        {
            updateStatus("Package name must be specified");
            return;
        }
        if (packName.lastIndexOf(".") == packName.length())
        {
            updateStatus("Package name must not end wit '.'");
            return;
        }
        else
        {
            updateStatus(null);
            return;
        }
	}
    
    
    
}
