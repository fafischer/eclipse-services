/*
* GeneratorPreferenceConstants.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.preferences;

/**
 * Constant definitions for plug-in preferences.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class GeneratorPreferenceConstants {

	/**
	 * The Constant TARGET_VALUE_JAX_RS_CLIENT.
	 */
	public static final String TARGET_VALUE_JAX_RS_CLIENT = "JAX-RS Client";

	/**
	 * The Constant TARGET_VALUE_JAX_RS_SERVER.
	 */
	public static final String TARGET_VALUE_JAX_RS_SERVER = "JAX-RS Server";

	/**
	 * The Constant TARGET_VALUE_JAX_WS_CLIENT.
	 */
	public static final String TARGET_VALUE_JAX_WS_CLIENT = "JAX-WS Client";

	/**
	 * The Constant TARGET_VALUE_JAX_WS_SERVER.
	 */
	public static final String TARGET_VALUE_JAX_WS_SERVER = "JAX-WS Server";

	/**
	 * The Constant TARGET_VALUE_CUSTOM.
	 */
	public static final String TARGET_VALUE_CUSTOM = "Custom";

	/**
	 * The Constant PREFERENCE_CUSTOM_TEMPLATE_PATH.
	 */
	public static final String PREFERENCE_CUSTOM_SERVICE_TEMPLATE_PATH = "temtools.generator.custom_service_templatepath";

	/**
	 * The Constant PREFERENCE_CUSTOM_PROJECT_TEMPLATE_PATH.
	 */
	public static final String PREFERENCE_CUSTOM_PROJECT_TEMPLATE_PATH = "temtools.generator.custom_project_templatepath";

	
	/**
	 * The Constant PREFERENCE_TARGET.
	 */
	public static final String PREFERENCE_TARGET = "temtools.generator.target";

	
	/**
	 * The Constant PREFERENCE_BASE_PACKAGE.
	 */
	public static final String PREFERENCE_BASE_PACKAGE = "temtools.generator.base.package";
	
	/**
	 * The Constant GENERATOR_TARGET_VALUES.
	 */
	public static final String[][] GENERATOR_TARGET_VALUES = {
			{ TARGET_VALUE_JAX_RS_CLIENT, TARGET_VALUE_JAX_RS_CLIENT }, {TARGET_VALUE_JAX_RS_SERVER,TARGET_VALUE_JAX_RS_SERVER}, {TARGET_VALUE_JAX_WS_CLIENT,TARGET_VALUE_JAX_WS_CLIENT}, {TARGET_VALUE_JAX_WS_SERVER,TARGET_VALUE_JAX_WS_SERVER},{TARGET_VALUE_CUSTOM,TARGET_VALUE_CUSTOM}};

	/**
	 * The Constant DEFAULT_VERSION.
	 */
	public static final String PREFERENCE_DEFAULT_VERSION = "temtools.generator.default.version";
	
	
	/**
	 * The Constant GENERATOR_TARGET_VALUES_WIZARD.
	 */
	public static final String[] GENERATOR_TARGET_VALUES_WIZARD={TARGET_VALUE_JAX_RS_CLIENT,TARGET_VALUE_JAX_RS_SERVER,TARGET_VALUE_JAX_WS_CLIENT,TARGET_VALUE_JAX_WS_SERVER,TARGET_VALUE_CUSTOM};

	/**
	 * The Constant PREFERENCE_RECENTLY_USED_PACKAGE.
	 * Used for storing the last used package.
	 * Hidden configuration value, not changeable in the preferences menu
	 */
	public static final String PREFERENCE_RECENTLY_USED_PACKAGE = "temtools.generator.recently.used.package";

}
