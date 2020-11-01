/*
* ServiceWSDLImportProcessingWizard.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.wizards.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;

import de.te2m.api.ext.project.service.Service;
import de.te2m.eclipse.service.internal.engine.reader.WSDLCrawler;
import de.te2m.eclipse.service.model.tree.service.SystemNode;

/**
 * The Class ServiceWSDLImportProcessingWizard.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class ServiceWSDLImportProcessingWizard extends Wizard {

	
	/**
	 * The sys node.
	 */
	private SystemNode sysNode;

	
	/**
	 * The container hint.
	 */
	private String containerHint;
	
	/**
	 * The page.
	 */
	protected WSDLImportWizardPage page;
	/**
	 * The selection.
	 */
	private ISelection selection;

	/**
	 * Instantiates a new abstract imp ex processing wizard.
	 *
	 */
	public ServiceWSDLImportProcessingWizard() {
		super();
	}

	
	/**
	 * Instantiates a new abstract imp ex processing wizard.
	 *
	 * @param cName the c name
	 */
	public ServiceWSDLImportProcessingWizard(String cName) {
		super();
		containerHint=cName;
	}

	/**
	 * Instantiates a new service wsdl import processing wizard.
	 *
	 * @param pn the pn
	 */
	public ServiceWSDLImportProcessingWizard(SystemNode pn) {
		super();
		sysNode=pn;
	}

	/**
	 * Adding the page to the wizard.
	 */
	public void addPages() {
		page = new WSDLImportWizardPage(selection);;
		page.setContainerNameHint(containerHint);
		page.setTitle(getTitle());
		page.setDescription(getDescription());
		addPage(page);
	}

	/**
	 * Determines the target name.
	 *
	 * @return the string
	 */
	protected String determineTargetName() {
		if(null==sysNode)
		{
			return "";
		}
		return sysNode.getName();
	}

	/**
	 * Executes finish.
	 *
	 * @param fileName the file name
	 * @param monitor the monitor
	 * @throws CoreException the core exception
	 */
	protected void doFinish(String fileName, IProgressMonitor monitor)
			throws CoreException {
		monitor.beginTask("Parsing WSDL " + determineTargetName(), 2);
		
		WSDLCrawler wsdlCr = new WSDLCrawler("Test", fileName);
		
		List<Service> services = wsdlCr.parseServices();
		
		for(Service service:services)
		{
			sysNode.addService(service);
		}

		monitor.worked(1);
		monitor.setTaskName("Finishing import...");
		monitor.worked(1);
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	protected String getDescription() {
		return "Import a Service based on a WSDL";
	}



	/**
	 * Gets the sys node.
	 *
	 * @return the sys node
	 */
	public SystemNode getSysNode() {
		return sysNode;
	}


	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	protected String getTitle() {
		
		return "Import WSDL";
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
		final String containerName = page.getWSDLFileName();

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
	 * Sets the sys node.
	 *
	 * @param sysNode the new sys node
	 */
	public void setSysNode(SystemNode sysNode) {
		this.sysNode = sysNode;
	}


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