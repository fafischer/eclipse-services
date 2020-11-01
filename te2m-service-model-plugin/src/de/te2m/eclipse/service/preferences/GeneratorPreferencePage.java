/*
* GeneratorPreferencePage.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.preferences;

import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import de.te2m.eclipse.service.ServiceModelPlugin;

/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They
 * are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can
 * be accessed directly via the preference store.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */

public class GeneratorPreferencePage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {

	/**
	 * Instantiates a new generator preference page.
	 */
	public GeneratorPreferencePage() {
		super(GRID);
		setPreferenceStore(ServiceModelPlugin.getDefault().getPreferenceStore());
		setDescription("Code generator preferences");
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
		addField(new FileFieldEditor(GeneratorPreferenceConstants.PREFERENCE_CUSTOM_PROJECT_TEMPLATE_PATH, 
				"&Project Template Path", getFieldEditorParent()));
		
		addField(new FileFieldEditor(GeneratorPreferenceConstants.PREFERENCE_CUSTOM_SERVICE_TEMPLATE_PATH, 
				"&Custom Service Template Path", getFieldEditorParent()));

		addField(new ComboFieldEditor(
				GeneratorPreferenceConstants.PREFERENCE_TARGET,
			"&Default Template", GeneratorPreferenceConstants.GENERATOR_TARGET_VALUES, getFieldEditorParent()));
		addField(
				new StringFieldEditor(GeneratorPreferenceConstants.PREFERENCE_BASE_PACKAGE, "&Base Package:", getFieldEditorParent()));
		addField(
				new StringFieldEditor(GeneratorPreferenceConstants.PREFERENCE_DEFAULT_VERSION, "D&efault Version:", getFieldEditorParent()));


	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
	
}