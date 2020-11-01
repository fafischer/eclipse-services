/*
* NewServiceWizard.java
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
import java.util.UUID;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.INewWizard;

import de.te2m.api.ext.core.IdObject.Attributes.Entry;
import de.te2m.api.ext.project.bo.ClassInfo;
import de.te2m.api.ext.project.bo.Operation;
import de.te2m.api.ext.project.bo.ParameterInfo;
import de.te2m.api.ext.project.service.Service;
import de.te2m.api.ext.project.service.ServiceBehavior;
import de.te2m.eclipse.service.model.tree.AttributedTreeParentNode;
import de.te2m.eclipse.service.model.tree.service.SystemNode;
import de.te2m.eclipse.service.views.ProjectModelProvider;
import de.te2m.eclipse.service.wizards.AbstractPropertyMgmtWizard;
import de.te2m.eclipse.service.wizards.service.template.ServiceTemplate;
import de.te2m.project.util.ProjectUtils;

/**
 * The Class NewServiceWizard.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class NewServiceWizard extends AbstractPropertyMgmtWizard implements INewWizard {

	/**
	 * The page.
	 */
	private AbstractServiceMasterDataPage page;

	/**
	 * The sn.
	 */
	private SystemNode sn;

	/**
	 * Instantiates a new manage operation wizard.
	 *
	 * @param obj the obj
	 * @throws CoreException the core exception
	 */
	public NewServiceWizard(AttributedTreeParentNode obj) throws CoreException {
		super();
		setNeedsProgressMonitor(true);

		if (null != obj) {
			if (obj instanceof SystemNode) {
				sn=(SystemNode)obj;
			}
			else
			throw new CoreException(null);
		}
	}

	/**
	 * Adding the page to the wizard.
	 */

	public void addPages() {
		page = new NewServiceMasterDataWizardPage(selection);
		addPage(page);	
	}

	/**
	 * Executes finish.
	 *
	 * @param name the name
	 * @param description the description
	 * @param stmpl the stmpl
	 * @param isServiceClient the is service client
	 * @param monitor the monitor
	 * @throws CoreException the core exception
	 */
	protected void doFinish(String name, String description, ServiceTemplate stmpl, boolean isServiceClient, IProgressMonitor monitor)
			throws CoreException {
		monitor.beginTask("Creating service " + name, 1);

		Service service = null;
		
		if(stmpl!=null)
		{
			service=stmpl.getService();

			ProjectUtils.setAttribute(service, "service.template.name", stmpl.getName());
			ProjectUtils.setAttribute(service, "service.template.id", stmpl.getId());
		
		}
		else
		{
			service = new Service();
		}
		service.setId(UUID.randomUUID().toString());
		service.setName(name);
		service.setDescription(description);
		
		if(isServiceClient)
		{
			service.setBehavior(ServiceBehavior.CONSUMES);
		}
		else
		{
			service.setBehavior(ServiceBehavior.PROVIDES);
		}
		
		processParameterInfoList(service.getConfigurations());
		
		for(Operation op : service.getOperations())
		{
			op.setId(UUID.randomUUID().toString());

			processParameterInfoList(op.getErrors());
			
			processParameterInfoList(op.getParameters());
			
			processParameterInfo(op.getReturnValue());

		}
		
		sn.addService(service);
		monitor.worked(1);
	}


	/**
	 * This method is called when 'Finish' button is pressed in the wizard. We
	 * will create an operation and run it using wizard as execution context.
	 * 
	 * @return true, if successful
	 */
	public boolean performFinish() {
		
		final ServiceTemplate sTemplate = page.getServiceTemplate();
		
		final String name = page.getObjectName();
		
		final String desc = page.getObjectDescription();
		
		final boolean isServiceClient = page.isServiceClient();

		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor)
					throws InvocationTargetException {
				try {
					doFinish(name, desc,sTemplate,isServiceClient, monitor);
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
	 * Process parameter info.
	 *
	 * @param pInfo the info
	 */
	protected void processParameterInfo(ParameterInfo pInfo) {
		ProjectModelProvider pmp = ProjectModelProvider.getInstance();
		
		ClassInfo ci =  (ClassInfo)pInfo.getParamClass();
		if(null!=ci)
		{
			pInfo.setParamClass(pmp.getProjectNode().validateClassInfo(ci));
		}
		pInfo.setId(UUID.randomUUID().toString());
	}

	/**
	 * Process parameter info list.
	 *
	 * @param parameterInfoList the parameter info list
	 */
	protected void processParameterInfoList(
			List<ParameterInfo> parameterInfoList) {
		for(ParameterInfo pInfo : parameterInfoList)
		{
			processParameterInfo(pInfo);
		}
	}

}