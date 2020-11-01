/*
* ManageReturnValueWizard.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.wizards.parameter;

import java.lang.reflect.InvocationTargetException;

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
import de.te2m.api.ext.project.bo.ParameterInfo;
import de.te2m.eclipse.service.model.tree.service.ReturnsErrors;
import de.te2m.eclipse.service.model.tree.service.ReturnsParameter;
import de.te2m.eclipse.service.model.tree.service.SimpleParameterNode;
import de.te2m.eclipse.service.views.ProjectModelProvider;

/**
 * The Class ManageReturnValueWizard.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class ManageReturnValueWizard extends Wizard implements INewWizard {

	/**
	 * The page.
	 */
	private ManageParameterWizardPage page;

	/**
	 * The selection.
	 */
	private ISelection selection;

	/**
	 * The on.
	 */
	protected ReturnsParameter on;

	/**
	 * The tpn.
	 */
	protected SimpleParameterNode tpn;

	/**
	 * Instantiates a new manage parameter wizard.
	 *
	 * @param obj the obj
	 * @param spn the spn
	 */
	public ManageReturnValueWizard(ReturnsParameter obj, SimpleParameterNode spn) {
		super();
		setNeedsProgressMonitor(true);
		on = obj;
		tpn = spn;
	}

	/**
	 * Adding the page to the wizard.
	 */

	public void addPages() {
		page = new ManageParameterWizardPage(selection);
		page.setSpn(tpn);
		addPage(page);
	}

	/**
	 * Executes finish.
	 *
	 * @param name the name
	 * @param desc the desc
	 * @param type the type
	 * @param pkg the pkg
	 * @param monitor the monitor
	 * @throws CoreException the core exception
	 */
	private void doFinish(String name, String desc, String type, String pkg,
			IProgressMonitor monitor) throws CoreException {
		monitor.beginTask("Update " + name, 1);

		ParameterInfo retVal= null;
		
		if(null!=tpn)
		{
			retVal = tpn.getParameterInfo();
		}		
		if(null==retVal)
		{
			retVal = new  ParameterInfo();
		}
		
		retVal.setName(name);
		retVal.setDescription(desc);
		ClassInfo ci = new ClassInfo();
		ci.setPkg(pkg);
		ci.setName(type);
		retVal.setParamClass(ProjectModelProvider.getInstance().getProjectNode()
				.validateClassInfo(ci));
		
		if(null!=on)
		{
			on.setReturnValue(retVal);
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
		final String name = page.getEnteredName();
		final String type = page.getType();
		final String pkg = page.getPackage();
		final String desc = page.getEnteredDescription();

		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor)
					throws InvocationTargetException {
				try {
					doFinish(name, desc, type, pkg, monitor);
				} catch (CoreException e) {
					e.printStackTrace();
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