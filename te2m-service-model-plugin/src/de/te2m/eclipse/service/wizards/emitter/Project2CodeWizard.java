/*
* Project2CodeWizard.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.wizards.emitter;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

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
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;

import de.te2m.eclipse.service.ServiceModelPlugin;
import de.te2m.eclipse.service.internal.engine.WorkspaceResultProcessor;
import de.te2m.eclipse.service.preferences.GeneratorPreferenceConstants;
import de.te2m.tools.generator.engine.SimpleGenerator;
import de.te2m.tools.generator.engine.impl.ChildReportProcessor;
import de.te2m.tools.generator.engine.impl.freemarker.FreeMarkerProcessor;
import de.te2m.report.api.model.Report;

/**
 * The Class CreatePropertyWizard.
 * 
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class Project2CodeWizard extends AbstractReportingWizard implements
		INewWizard {

	/**
	 * The page.
	 */
	private Project2CodeWizardPage page;

	/**
	 * The selection.
	 */
	private ISelection selection;

	/**
	 * The pkg.
	 */
	private String pkg;

	/**
	 * Constructor for CreatePropertyWizard.
	 */
	public Project2CodeWizard() {
		super();
		setNeedsProgressMonitor(true);
	}

	/**
	 * Adding the page to the wizard.
	 */

	public void addPages() {
		page = new Project2CodeWizardPage(selection);
		addPage(page);
	}

	/**
	 * Executes finish.
	 *
	 * @param containerName            the container name
	 * @param packName            the pack name
	 * @param monitor            the monitor
	 * @throws CoreException             the core exception
	 */
	private void doFinish(String containerName, String packName,
			IProgressMonitor monitor) throws CoreException {
		monitor.beginTask("Creating java classes for project  ", 2);
		org.eclipse.core.resources.IWorkspaceRoot root = ResourcesPlugin
				.getWorkspace().getRoot();
		IResource resource = root.findMember(new Path(containerName));
		if (!resource.exists() || !(resource instanceof IContainer))
			throwCoreException("Container \"" + containerName
					+ "\" does not exist.");
		IContainer container = (IContainer) resource;

		try {

			InputStream is = null;
			JAXBContext context = JAXBContext.newInstance(Report.class);

			String fileName = ServiceModelPlugin
					.getDefault()
					.getPreferenceStore()
					.getString(
							GeneratorPreferenceConstants.PREFERENCE_CUSTOM_PROJECT_TEMPLATE_PATH);

			ServiceModelPlugin
					.getDefault()
					.getPreferenceStore()
					.setValue(
							GeneratorPreferenceConstants.PREFERENCE_RECENTLY_USED_PACKAGE,
							packName);

			if (null != fileName && fileName.trim().length() > 0) {
				is = new FileInputStream(fileName);

				Unmarshaller um = context.createUnmarshaller();
				Report r = (Report) um.unmarshal(is);

				generate(container, packName, r, prepareReportGenerationParams(container.getName(), packName));

				is.close();
				container.refreshLocal(2, monitor);
			} else {
				throwCoreException("Report Template missing");
			}
		} catch (Exception e) {

			throwCoreException(e);
		}
		monitor.worked(1);
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
		final String packName = page.getPackageName();

		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor)
					throws InvocationTargetException {
				try {
					doFinish(containerName, packName, monitor);
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
	 * Throw core exception.
	 * 
	 * @param e
	 *            the e
	 * @throws CoreException
	 *             the core exception
	 */
	private void throwCoreException(Exception e) throws CoreException {
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
	private void throwCoreException(String message) throws CoreException {
		IStatus status = new Status(IStatus.ERROR, "service2code", IStatus.OK,
				message, null);
		throw new CoreException(status);
	}

}