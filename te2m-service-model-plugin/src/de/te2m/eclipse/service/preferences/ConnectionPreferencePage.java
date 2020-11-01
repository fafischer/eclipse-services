/*
* ConnectionPreferencePage.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
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

public class ConnectionPreferencePage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {

	/**
	 * Instantiates a new connection preference page.
	 */
	public ConnectionPreferencePage() {
		super(GRID);
		setPreferenceStore(ServiceModelPlugin.getDefault().getPreferenceStore());
		setDescription("Connection settings for the temtools(4)projects REST API");
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
		addField(
			new StringFieldEditor(
				ConnectionPreferenceConstants.PREFERENCE_URL,
				"&URL",
				getFieldEditorParent()));
		addField(
			new StringFieldEditor(ConnectionPreferenceConstants.PREFERENCE_USER, "U&ser:", getFieldEditorParent()));
		addField(
				new StringFieldEditor(ConnectionPreferenceConstants.PREFERENCE_PW, "&Password:", getFieldEditorParent()));	
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
	
}