/*
* AbstractContainerAndPackageProcessingWizardPage.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.wizards.emitter;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import de.te2m.eclipse.service.ServiceModelPlugin;
import de.te2m.eclipse.service.preferences.GeneratorPreferenceConstants;
import de.te2m.eclipse.service.wizards.AbstractProjectSelectionHandlingWizardPage;

/**
 * The Class AbstractContainerAndPackageProcessingWizardPage.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractContainerAndPackageProcessingWizardPage
		extends AbstractProjectSelectionHandlingWizardPage {

	/**
	 * The package text.
	 */
	private Text packageText;

	/**
	 * Instantiates a new abstract container and package processing wizard page.
	 *
	 * @param pageName the page name
	 */
	public AbstractContainerAndPackageProcessingWizardPage(String pageName) {
		super(pageName);
	}

	/**
	 * Instantiates a new abstract container and package processing wizard page.
	 *
	 * @param pageName the page name
	 * @param title the title
	 * @param titleImage the title image
	 */
	public AbstractContainerAndPackageProcessingWizardPage(String pageName, String title, ImageDescriptor titleImage) {
		super(pageName, title, titleImage);
	}

	/**
	 * Creates the package text.
	 *
	 * @param container the container
	 */
	protected void createPackageText(Composite container) {
		Label label = new Label(container, 0);
	    label.setText("Base Package Name: ");
	    packageText = new Text(container, 2052);
	
	    // gd
	    packageText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	    packageText.addModifyListener(new ModifyListener()
	    {
	
	        public void modifyText(ModifyEvent e)
	        {
	            packageChanged();
	        }
	
	    });
	}

	/* (non-Javadoc)
	 * @see de.te2m.eclipse.service.wizards.AbstractProjectSelectionHandlingWizardPage#dialogChanged()
	 */
	@Override
	protected void dialogChanged() {
	    super.dialogChanged();
		packageChanged();
	}

    /**
	 * Gets the package name.
	 *
	 * @return the package name
	 */
	public String getPackageName() {
	    return packageText.getText().trim();
	}


	/* (non-Javadoc)
	 * @see de.te2m.eclipse.service.wizards.AbstractProjectSelectionHandlingWizardPage#initialize()
	 */
	protected void initialize()
    {
    	super.initialize();

        initializePackage();
    }

	/**
	 * Initialize package.
	 */
	protected void initializePackage() {
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
	    
	    
	    if(null!=tmpPkgString&&tmpPkgString.trim().length()>0)
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