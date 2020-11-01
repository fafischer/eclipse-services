/*
* MiscWizard.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.wizards.emitter;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.operation.*;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import java.io.*;
import org.eclipse.ui.*;
import org.eclipse.ui.ide.IDE;

import de.te2m.report.api.model.GeneratorMetaData;
import de.te2m.report.api.model.Report;
import de.te2m.report.api.model.dev.java.JavaClassDescriptor;

/**
 * The Class MiscWizard.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class MiscWizard extends AbstractReportingWizard implements INewWizard {
	
	/**
	 * The page.
	 */
	private MiscWizardPage page;
	
	/**
	 * The selection.
	 */
	private ISelection selection;

	/**
	 * Constructor for MiscWizard.
	 */
	public MiscWizard() {
		super();
		setNeedsProgressMonitor(true);
	}
	
	/**
	 * Adding the page to the wizard.
	 */

	public void addPages() {
		page = new MiscWizardPage(selection);
		addPage(page);
	}

	/**
	 * The worker method. It will find the container, create the
	 * file if missing or just replace its contents, and open
	 * the editor on the newly created file.
	 *
	 * @param containerName the container name
	 * @param report the report
	 * @param params the params
	 * @param monitor the monitor
	 * @throws CoreException the core exception
	 */

	private void doFinish(
		String containerName,
		Report report,
		HashMap<String, Object> params,
		IProgressMonitor monitor)
		throws CoreException {
		// create a sample file
		monitor.beginTask("Creating " + report.getName(), 2);
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IResource resource = root.findMember(new Path(containerName));
		if (!resource.exists() || !(resource instanceof IContainer)) {
			throwCoreException("Container \"" + containerName + "\" does not exist.");
		}
		IContainer container = (IContainer) resource;

		generate(container, report, params);
		monitor.worked(1);

	}
	
	/**
	 * We will accept the selection in the workbench to see if
	 * we can initialize from it.
	 *
	 * @param workbench the workbench
	 * @param selection the selection
	 * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}
	
	/**
	 * This method is called when 'Finish' button is pressed in
	 * the wizard. We will create an operation and run it
	 * using wizard as execution context.
	 *
	 * @return true, if successful
	 */
	public boolean performFinish() {
		final String containerName = page.getContainerName();
		final String name = page.getNameText();
		final String desc = page.getDescriptionText();
		final String pkg = page.getPackageName();
		final Report report = page.getReportTemplate();
		
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		String deploymentUnit = containerName.replaceAll("/", "");
		prepareMetaData(params, deploymentUnit, pkg);
		preparePOMInfo(params);
		JavaClassDescriptor bo = JavaClassDescriptor
				.builder()
				.withDeploymentUnit(containerName)
				.withName(name)
				.withDescription(desc)
				.withPackage(pkg)
				.build();

		params.put("bo", bo);
		
		final HashMap<String, Object> generatorParams = params;
		
		//final String fileName = page.getFileName();
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				try {
					doFinish(containerName, report, generatorParams, monitor);
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
			MessageDialog.openError(getShell(), "Error", realException.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * Throw core exception.
	 *
	 * @param message the message
	 * @throws CoreException the core exception
	 */
	private void throwCoreException(String message) throws CoreException {
		IStatus status =
			new Status(IStatus.ERROR, "te2m-service-model-plugin", IStatus.OK, message, null);
		throw new CoreException(status);
	}
}