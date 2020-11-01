/*
* Service2CodeWizard.java
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
import java.util.HashMap;

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

import de.te2m.api.ext.project.service.Service;
import de.te2m.eclipse.service.ServiceModelPlugin;
import de.te2m.eclipse.service.model.tree.service.ServiceNode;
import de.te2m.eclipse.service.preferences.GeneratorPreferenceConstants;
import de.te2m.eclipse.service.views.ProjectModelProvider;
import de.te2m.report.api.model.Report;

/**
 * The Class CreatePropertyWizard.
 * 
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class Service2CodeWizard extends AbstractReportingWizard implements INewWizard {

	/**
	 * The page.
	 */
	private Service2CodeWizardPage page;

	/**
	 * The selection.
	 */
	private ISelection selection;

	/**
	 * The model.
	 */
	private Service model;

	/**
	 * Constructor for CreatePropertyWizard.
	 */
	public Service2CodeWizard() {
		super();
		setNeedsProgressMonitor(true);
	}

	/**
	 * Instantiates a new service2 code wizard.
	 *
	 * @param serviceNode the service node
	 */
	public Service2CodeWizard(ServiceNode serviceNode) {
		super();
		setNeedsProgressMonitor(true);
		model = serviceNode!=null?serviceNode.getService():null;
	}

	/**
	 * Adding the page to the wizard.
	 */

	public void addPages() {
		page = new Service2CodeWizardPage(selection);
		addPage(page);
	}

	/**
	 * Executes finish.
	 *
	 * @param containerName the container name
	 * @param functionName the function name
	 * @param packName the pack name
	 * @param target the target
	 * @param monitor the monitor
	 * @throws CoreException the core exception
	 */
	private void doFinish(String containerName, String functionName,
			String packName, String target, IProgressMonitor monitor)
			throws CoreException {
		monitor.beginTask("Creating java classes for service  " + functionName,
				2);
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

			// TODO Extend for other templates as defined in the target
			// selection

			if (GeneratorPreferenceConstants.TARGET_VALUE_CUSTOM
					.equals(target)) {
				String fileName = ServiceModelPlugin
						.getDefault()
						.getPreferenceStore()
						.getString(
								GeneratorPreferenceConstants.PREFERENCE_CUSTOM_SERVICE_TEMPLATE_PATH);

				is = new FileInputStream(fileName);

			} else if (GeneratorPreferenceConstants.TARGET_VALUE_JAX_WS_SERVER
					.equals(target)) {
				is = getClass().getResourceAsStream("/reports/JaxWSService.xml");
			}else if (GeneratorPreferenceConstants.TARGET_VALUE_JAX_RS_SERVER
					.equals(target)) {
				is = getClass().getResourceAsStream("/reports/JaxRSService.xml");
			}


			if (null == is) {
				throwCoreException("Not implemented!");

			}
			Unmarshaller um = context.createUnmarshaller();
			Report report = (Report) um.unmarshal(is);

			HashMap<String, Object> params = new HashMap<String, Object>();
			String deploymentUnit = containerName.replaceAll("/", "");
			prepareMetaData(params, deploymentUnit, packName);
			preparePOMInfo(params);
			params.put("bo", transformService(model, packName, deploymentUnit));
			
			params.put("project",ProjectModelProvider.getInstance().getProject());

			generate(container, packName, report, params);

			is.close();
			container.refreshLocal(2, monitor);
		} catch (Exception e) {

			throwCoreException(e);
		}
		monitor.worked(1);
		monitor.setTaskName("Opening file for editing...");
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
		final String targetName = page.getTarget();

		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor)
					throws InvocationTargetException {
				try {
					doFinish(containerName, model.getName(), packName,
							targetName, monitor);
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