/*
* SaveServiceTemplateWizard.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.wizards.service;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

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

import de.te2m.api.ext.project.bo.ClassInfo;
import de.te2m.api.ext.project.bo.Operation;
import de.te2m.api.ext.project.bo.ParameterInfo;
import de.te2m.api.ext.project.service.Service;
import de.te2m.eclipse.service.ServiceModelPlugin;
import de.te2m.eclipse.service.model.tree.service.ServiceNode;
import de.te2m.eclipse.service.preferences.MainPreferenceConstants;
import de.te2m.eclipse.service.views.ProjectModelProvider;
import de.te2m.eclipse.service.wizards.MasterDataWizardPage;
import de.te2m.eclipse.service.wizards.service.template.ServiceTemplate;
import de.te2m.eclipse.service.wizards.service.template.ServiceTemplateUtils;
import de.te2m.project.util.ProjectUtils;

/**
 * The Class SaveServiceTemplateWizard.
 * This wizard exports the selected servica as a service template into the file system.
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class SaveServiceTemplateWizard extends Wizard implements INewWizard {

	/**
	 * The page.
	 */
	private MasterDataWizardPage page;
	/**
	 * The model.
	 */
	ServiceNode model;
	
	/**
	 * The selection.
	 */
	protected ISelection selection;

	/**
	 * Constructor for SaveProjectWizard.
	 */
	public SaveServiceTemplateWizard() {
		this(null);
		setNeedsProgressMonitor(true);

	}

	/**
	 * Instantiates a new save project wizard.
	 *
	 * @param serviceToSave the service to save
	 */
	public SaveServiceTemplateWizard(ServiceNode serviceToSave) {
		super();

		setNeedsProgressMonitor(true);

		model = serviceToSave;


	}

	/**
	 * Adding the page to the wizard.
	 */
	@Override
	public void addPages() {
		page = new MasterDataWizardPage(selection);
		//page.setSpn(model);
		addPage(page);
	}

	/**
	 * Executes finish.
	 *
	 * @param stpl the stpl
	 * @param monitor the monitor
	 * @throws CoreException the core exception
	 */
	protected void doFinish(ServiceTemplate stpl,IProgressMonitor monitor)
			throws CoreException {
		monitor.beginTask("Exporting Template"+stpl.getName(), 1);

		String dirName = ServiceModelPlugin
				.getDefault()
				.getPreferenceStore()
				.getString(
						MainPreferenceConstants.PREFERENCE_TEMPLATE_PATH);

		String fileName = dirName+File.separator+model.getId()+ServiceTemplate.TEMPLATE_EXTENSION;
		
		ServiceTemplateUtils.getInstance()
				.save(stpl, fileName);

		ServiceTemplateUtils.getInstance().getList();
		
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
		addPages();
	}



	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {

		final String name = page.getEnteredName();
		final String description = page.getEnteredDescription();
		
		final ServiceTemplate newTemplate = transformServiceNode(name, description, model);
		
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor)
					throws InvocationTargetException {
				try {
					doFinish(newTemplate, monitor);
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
	 * Process parameter object.
	 *
	 * @param template the template
	 * @param pInfo the info
	 */
	public void processParameterObject(ServiceTemplate template, ParameterInfo pInfo) {
		
		if(null==pInfo||null==template)
		{
			return;
		}
		
		// adjust parameter info ID

		pInfo.setId(UUID.randomUUID().toString());
		
		ClassInfo ci = (ClassInfo)pInfo.getParamClass();
		
		if (null!=ci && null == template.determineObjectByID(ci.getId())) {
			// parameter class must not be adjusted
			template.addObject(ci);
		}
	}
	
	/**
	 * Transform service node.
	 *
	 * @param templatename the templatename
	 * @param templatedescription the templatedescription
	 * @param snode the snode
	 * @return the service template
	 */
	public ServiceTemplate transformServiceNode(String templatename, String templatedescription, ServiceNode snode) {

		ServiceTemplate template = new ServiceTemplate();
		
		template.setName(templatename);

		template.setDescription(templatedescription);
		
		Service tmpService = snode.getService();

		template.setService(tmpService);

		tmpService.setId(UUID.randomUUID().toString());
		
		if(null!=tmpService.getConfigurations())
		{
			for (ParameterInfo pInfo : tmpService.getConfigurations()) {
				processParameterObject(template, pInfo);
			}			
		}
		
		for (Operation op : tmpService.getOperations()) {

			op.setId(UUID.randomUUID().toString());
			
			if (null != op.getParameters()) {

				for (ParameterInfo pInfo : op.getParameters()) {
					processParameterObject(template, pInfo);
				}
			}
			if (null != op.getErrors()) {

				for (ParameterInfo pInfo : op.getErrors()) {
					processParameterObject(template, pInfo);
				}
			}
			processParameterObject(template, op.getReturnValue());

		}
		
		return template;
	}




}