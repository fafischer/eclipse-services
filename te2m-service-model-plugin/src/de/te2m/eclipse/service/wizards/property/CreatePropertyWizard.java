/*
* CreatePropertyWizard.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.wizards.property;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;

import de.te2m.eclipse.service.ServiceModelPlugin;
import de.te2m.eclipse.service.model.tree.AttributedTreeParentNode;
import de.te2m.eclipse.service.preferences.MainPreferenceConstants;
import de.te2m.eclipse.service.preferences.MiscPreferenceConstants;
import de.te2m.eclipse.service.preferences.PreferenceUtils;

/**
 * The Class CreatePropertyWizard.
 * 
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class CreatePropertyWizard extends Wizard implements INewWizard {




	/**
	 * The page.
	 */
	private CreatePropertyWizardPage page;

	
	/**
	 * The selection.
	 */
	private ISelection selection;


	/**
	 * The tpn.
	 */
	private AttributedTreeParentNode tpn;
	
	/**
	 * Constructor for CreatePropertyWizard.
	 *
	 * @param obj the obj
	 */
	public CreatePropertyWizard(AttributedTreeParentNode obj) {
		super();
		setNeedsProgressMonitor(true);
		tpn=obj;
	}

	/**
	 * Adding the page to the wizard.
	 */

	public void addPages() {
		page = new CreatePropertyWizardPage(selection);
		addPage(page);
	}

	/**
	 * Executes finish.
	 *
	 * @param key the key
	 * @param value the value
	 * @param monitor the monitor
	 * @throws CoreException the core exception
	 */
	private void doFinish(String key, String value,
			IProgressMonitor monitor) throws CoreException {
		monitor.beginTask("Creating Property "+key, 1);

		List<String> propKeys = new LinkedList<String>(Arrays.asList(PreferenceUtils.getStringArrayPreference(MiscPreferenceConstants.KNOWN_PROPERTIES_PREFERENCE)));
		
		if(!propKeys.contains(key))
		{
			propKeys.add(key);
			PreferenceUtils.setStringArrayPreference(MiscPreferenceConstants.KNOWN_PROPERTIES_PREFERENCE, propKeys);
		}
		
		tpn.addAttribute(key, value);
		
		monitor.worked(1);
	}

	/**
	 * Gets the key.
	 *
	 * @return the key
	 */
	public String getKey()
	{
		return page.getKey();
	}

	
	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue()
	{
		return page.getValue();
	}
	
	/**
	 * We will accept the selection in the workbench to see if we can initialize
	 * from it.
	 * 
	 * @param workbench
	 *            the workbench
	 * @param selection
	 *            the selection
	 * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}
	
	/**
	 * This method is called when 'Finish' button is pressed in the wizard. We
	 * will create an operation and run it using wizard as execution context.
	 * 
	 * @return true, if successful
	 */
	public boolean performFinish() {
		final String key = page.getKey();
		final String value = page.getValue();
		
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor)
					throws InvocationTargetException {
				try {
					doFinish(key, value,  monitor);
				} catch (CoreException e) {
					throw new InvocationTargetException(e);
				} finally {
					monitor.done();
				}
			}
		};
		try {
			getContainer().run(true, false, op);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			Throwable realException = e.getTargetException();
			e.printStackTrace();
			realException.printStackTrace();
			MessageDialog.openError(getShell(), "Error",
					realException.getMessage());
			return false;
		}
		return true;
	}
	
	
}