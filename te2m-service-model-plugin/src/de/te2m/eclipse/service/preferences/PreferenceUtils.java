/*
* PreferenceUtils.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.preferences;

import java.util.List;
import java.util.StringTokenizer;

import de.te2m.eclipse.service.ServiceModelPlugin;

/**
 * The Class PreferenceUtils.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class PreferenceUtils {
	
	/**
	 * Converts PREFERENCE_DELIMITER delimited String to a String array.
	 *
	 * @param preferenceValue the preference value
	 * @return the string[]
	 */
	private static String[] convert(String preferenceValue) {
		StringTokenizer tokenizer =
			new StringTokenizer(preferenceValue, MiscPreferenceConstants.PREFERENCE_DELIMITER);
		int tokenCount = tokenizer.countTokens();
		String[] elements = new String[tokenCount];
		for (int i = 0; i < tokenCount; i++) {
			elements[i] = tokenizer.nextToken();
		}

		return elements;
	}

	/**
	 * Return the bad words preference default.
	 *
	 * @param name the name
	 * @return the default string array preference
	 */
	public static String[] getDefaultStringArrayPreference(String name){

		return convert(ServiceModelPlugin.getDefault().getPreferenceStore().getDefaultString(name));
	}
		
	/**
	 * Returns the bad words preference.
	 *
	 * @param name the name
	 * @return the string array preference
	 */
	public static String[] getStringArrayPreference(String name) {
		String keys = ServiceModelPlugin.getDefault().getPreferenceStore().getString(name);
		
		if(null==keys||keys.trim().length()==0)
		{
			return getDefaultStringArrayPreference(name);
		}
		
		return convert(keys);
	}

	/**
	 * Sets the bad words preference.
	 *
	 * @param name the name
	 * @param elements the elements
	 */
	public static void setStringArrayPreference(String name, List<String> elements) {
		StringBuffer buffer = new StringBuffer();
		for (String key: elements) {
			buffer.append(key);
			buffer.append(MiscPreferenceConstants.PREFERENCE_DELIMITER);
		}
		ServiceModelPlugin.getDefault().getPreferenceStore().setValue(name, buffer.toString());
	}
	
	/**
	 * Sets the bad words preference.
	 *
	 * @param name the name
	 * @param elements the elements
	 */
	public static void setStringArrayPreference(String name, String[] elements) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < elements.length; i++) {
			buffer.append(elements[i]);
			buffer.append(MiscPreferenceConstants.PREFERENCE_DELIMITER);
		}
		ServiceModelPlugin.getDefault().getPreferenceStore().setValue(name, buffer.toString());
	}

}
