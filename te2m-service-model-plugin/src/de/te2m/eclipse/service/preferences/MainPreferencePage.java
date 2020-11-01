/*
* MainPreferencePage.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import de.te2m.eclipse.service.ServiceModelPlugin;


/**
 * The Class MainPreferencePage.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class MainPreferencePage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {

	/**
	 * Instantiates a new main preference page.
	 */
	public MainPreferencePage() {
		super(GRID);
		setPreferenceStore(ServiceModelPlugin.getDefault().getPreferenceStore());
		setDescription("Temtools Main Preference Page");
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors()
	 */
	public void createFieldEditors() {
		addField(
			new StringFieldEditor(MainPreferenceConstants.PREFERENCE_USER_NAME, "&User:", getFieldEditorParent()));
		addField(
				new StringFieldEditor(MainPreferenceConstants.PREFERENCE_COMPANY_NAME, "&Company:", getFieldEditorParent()));
		addField(
				new StringFieldEditor(MainPreferenceConstants.PREFERENCE_LEGAL_NOTICE, "&Copyright:", getFieldEditorParent()));	

		addField(new DirectoryFieldEditor(MainPreferenceConstants.PREFERENCE_TEMPLATE_PATH, 
				"&Template Path", getFieldEditorParent()));


		addField(new DirectoryFieldEditor(MainPreferenceConstants.PREFERENCE_REPORT_PATH, 
				"&Report Repository Path", getFieldEditorParent()));
		
		addField(new BooleanFieldEditor(MainPreferenceConstants.PREFERENCE_REMEMBER_PROJECT, 
				"&Remember last Project", getFieldEditorParent()));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
	
}