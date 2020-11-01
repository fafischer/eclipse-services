/*
* ConnectionPreferenceInitializer.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import de.te2m.eclipse.service.ServiceModelPlugin;

/**
 * Class used to initialize default preference values.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class ConnectionPreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = ServiceModelPlugin.getDefault().getPreferenceStore();
		store.setDefault(ConnectionPreferenceConstants.PREFERENCE_URL, "");
		store.setDefault(ConnectionPreferenceConstants.PREFERENCE_USER, "");
		store.setDefault(ConnectionPreferenceConstants.PREFERENCE_PW,"");
	}

}
