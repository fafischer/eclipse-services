/*
* CreatePropertyWizardPage.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.wizards.property;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import de.te2m.eclipse.service.preferences.GeneratorPreferenceConstants;
import de.te2m.eclipse.service.preferences.MiscPreferenceConstants;
import de.te2m.eclipse.service.preferences.PreferenceUtils;

/**
 * The Class ProjectImpExWizardPage.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class CreatePropertyWizardPage extends WizardPage
{

    /**
     * The key combo.
     */
    private Combo keyCombo;
    
    /**
     * The container text.
     */
    private Text valueText;

    
    /**
     * The selection.
     */
    private ISelection selection;

    /**
     * Instantiates a new service2 code wizard page.
     *
     * @param selection the selection
     */
    public CreatePropertyWizardPage(ISelection selection)
    {
        super("wizardPage");
        setTitle("Create Property");
        setDescription("Create a new Property");
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
        Label label = new Label(container, 0);
        label.setText("&Key:");
        GridData gd = new GridData(768);
        keyCombo = new Combo(container,  SWT.BORDER);
        keyCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        keyCombo.setItems(PreferenceUtils.getStringArrayPreference(MiscPreferenceConstants.KNOWN_PROPERTIES_PREFERENCE));
        keyCombo.addModifyListener(new ModifyListener()
        {

            public void modifyText(ModifyEvent e)
            {
                dialogChanged();
            }

        });

        
        label = new Label(container, 0);
        label.setText("&Value:");
        valueText = new Text(container, 2052);
        gd = new GridData(768);
        valueText.setLayoutData(gd);
        valueText.addModifyListener(new ModifyListener()
        {

            public void modifyText(ModifyEvent e)
            {
                dialogChanged();
            }

        });
        
        initialize();
        dialogChanged();
        setControl(container);
    }

    /**
     * Dialog changed.
     */
    private void dialogChanged()
    {
        String key = getKey();
        String value = getValue();
        if (key.length() == 0)
        {
            updateStatus("Property key must be specified");
            return;
        }
        else if (value.length() == 0)
        {
            updateStatus("Property value must be specified");
            return;
        }
        else
        {
            updateStatus(null);
            return;
        }
    }


    /**
     * Gets the property key.
     *
     * @return the key
     */
    public String getKey()
    {
        return keyCombo.getText().trim();
    }

    /**
     * Gets the property value.
     *
     * @return the value
     */
    public String getValue()
    {
        return valueText.getText().trim();
    }


    /**
     * Initialize.
     */
    private void initialize()
    {
        try
        {
//            if (selection != null && !selection.isEmpty()
//                    && (selection instanceof IStructuredSelection))
//            {
//                IStructuredSelection ssel = (IStructuredSelection) selection;
//                if (ssel.size() > 1) return;
//                Object obj = ssel.getFirstElement();
//                if (obj instanceof IResource)
//                {
//                    IContainer container;
//                    if (obj instanceof IContainer) container = (IContainer) obj;
//                    else
//                        container = ((IResource) obj).getParent();
//                    String rawContainerString = container.getFullPath()
//                            .toString().substring(1);
//                    containerText.setText(rawContainerString.substring(0,
//                            rawContainerString.indexOf("/")));
//                }
//            }
        }

        catch (Exception exception)
        {
        }
    }

    /**
     * Update status.
     *
     * @param message the message
     */
    private void updateStatus(String message)
    {
        setErrorMessage(message);
        setPageComplete(message == null);
    }

}
