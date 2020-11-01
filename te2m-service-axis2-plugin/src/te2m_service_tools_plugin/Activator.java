/*
* Activator.java
*   
* Copyright 2009 - 2013 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-axis2-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package te2m_service_tools_plugin;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	/**
	 * The Constant PLUGIN_ID.
	 */
	public static final String PLUGIN_ID = "te2m-service-tools-plugin"; //$NON-NLS-1$

	// The shared instance
	/**
	 * The plugin.
	 */
	private static Activator plugin;
	
	/**
	 * The constructor.
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance.
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path.
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
}
