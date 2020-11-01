/*
* AbstractProjectSelectionHandlingWizardPage.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.wizards;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * The Class AbstractProjectSelectionHandlingWizardPage.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractProjectSelectionHandlingWizardPage extends
		WizardPage {

    /**
     * The container text.
     */
    protected Combo containerText;

	
	/**
	 * Instantiates a new abstract project selection handling wizard page.
	 *
	 * @param pageName the page name
	 */
	public AbstractProjectSelectionHandlingWizardPage(String pageName) {
		super(pageName);
	}

	/**
	 * Instantiates a new abstract project selection handling wizard page.
	 *
	 * @param pageName the page name
	 * @param title the title
	 * @param titleImage the title image
	 */
	public AbstractProjectSelectionHandlingWizardPage(String pageName,
			String title, ImageDescriptor titleImage) {
		super(pageName, title, titleImage);
	}

	/**
	 * Container changed.
	 */
	protected void containerChanged() {
		String container = getContainerName();

	    if (container.length() == 0)
	    {
	        updateStatus("File container must be specified");
	        return;
	    }
	    updateStatus(null);
	}


	/**
	 * Creates the project combo.
	 *
	 * @param container the container
	 */
	protected void createProjectCombo(Composite container) {
		Label label = new Label(container, 0);
	    label.setText("&Project:");
	    containerText = new Combo(container,  SWT.BORDER | SWT.READ_ONLY);
	    containerText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	    containerText.setItems(getProjectNames());
	    containerText.addModifyListener(new ModifyListener()
	    {
	
	        public void modifyText(ModifyEvent e)
	        {
	            containerChanged();
	        }
	
	    });
	}

	/**
	 * Dialog changed.
	 */
	protected void dialogChanged() {
		
		containerChanged();
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
     * Gets the project names.
     *
     * @return the project names
     */
    protected String[] getProjectNames() {
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
	    
		String[] projectNames = new String[projects.length];
		
		for (int i = 0; i < projects.length; i++) {
	    	projectNames[i] = projects[i].getName();
		}
	    
	    return projectNames; 
	}
    
    /**
     * Initialize.
     */
    protected void initialize()
    {
    	initializeContainer();
    }
    
    /**
     * Initialize container.
     */
    protected void initializeContainer() {
		// Do nothing for now, maybe add some initialization later
		
	}

	/**
     * Update status.
     *
     * @param message the message
     */
    protected void updateStatus(String message)
    {
        setErrorMessage(message);
        setPageComplete(message == null);
    }
    

}