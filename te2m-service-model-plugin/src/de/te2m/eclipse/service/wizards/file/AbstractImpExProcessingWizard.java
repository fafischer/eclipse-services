/*
* AbstractImpExProcessingWizard.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.wizards.file;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;

/**
 * The Class AbstractImpExProcessingWizard.
 * 
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractImpExProcessingWizard extends Wizard {

	/**
	 * The container hint.
	 */
	private String containerHint;
	
	/**
	 * The page.
	 */
	protected ProjectImpExWizardPage page;
	/**
	 * The selection.
	 */
	private ISelection selection;

	/**
	 * Instantiates a new abstract imp ex processing wizard.
	 *
	 * @param cName the c name
	 */
	public AbstractImpExProcessingWizard(String cName) {
		super();
		containerHint=cName;
	}

	/**
	 * Adding the page to the wizard.
	 */
	public void addPages() {
		page = new ProjectImpExWizardPage(selection);
		page.setContainerNameHint(containerHint);
		page.setTitle(getTitle());
		page.setDescription(getDescription());
		addPage(page);
	}

	/**
	 * Determines the container.
	 * 
	 * @param containerName
	 *            the container name
	 * @return the i container
	 * @throws CoreException
	 *             the core exception
	 */
	protected IContainer determineContainer(String containerName)
			throws CoreException {
		org.eclipse.core.resources.IWorkspaceRoot root = ResourcesPlugin
				.getWorkspace().getRoot();
		IResource resource = root.findMember(new Path(containerName));
		if (!resource.exists() || !(resource instanceof IContainer))
			throwCoreException("Container \"" + containerName
					+ "\" does not exist.");
		IContainer container = (IContainer) resource;
		return container;
	}

	/**
	 * Determines the target name.
	 * 
	 * @return the string
	 */
	protected abstract String determineTargetName();

	/**
	 * Executes finish.
	 * 
	 * @param containerName
	 *            the container name
	 * @param monitor
	 *            the monitor
	 * @throws CoreException
	 *             the core exception
	 */
	protected void doFinish(String containerName, IProgressMonitor monitor)
			throws CoreException {
		monitor.beginTask("Saving project " + determineTargetName(), 2);
		IContainer container = determineContainer(containerName);

		processTask(monitor, container);

		monitor.worked(1);
		monitor.setTaskName("Finishing export...");
		monitor.worked(1);
	}



	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	protected String getDescription() {
		return "Import or export a project file";
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	protected String getTitle() {
		
		return "Import / Export";
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
		final String containerName = page.getContainerName();

		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor)
					throws InvocationTargetException {
				try {
					doFinish(containerName, monitor);
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

	/**
	 * Process task.
	 * 
	 * @param monitor
	 *            the monitor
	 * @param container
	 *            the container
	 * @throws CoreException
	 *             the core exception
	 */
	protected abstract void processTask(IProgressMonitor monitor,
			IContainer container) throws CoreException;
	
	/**
	 * Throw core exception.
	 * 
	 * @param e
	 *            the e
	 * @throws CoreException
	 *             the core exception
	 */
	protected void throwCoreException(Exception e) throws CoreException {
		IStatus status = new Status(IStatus.ERROR, "service2code", IStatus.OK,
				e.getMessage(), e);
		throw new CoreException(status);
	}

	/**
	 * Throw core exception.
	 * 
	 * @param message
	 *            the message
	 * @throws CoreException
	 *             the core exception
	 */
	protected void throwCoreException(String message) throws CoreException {
		IStatus status = new Status(IStatus.ERROR, "service2code", IStatus.OK,
				message, null);
		throw new CoreException(status);
	}



}