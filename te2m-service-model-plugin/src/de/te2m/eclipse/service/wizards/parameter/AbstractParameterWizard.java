/*
* AbstractParameterWizard.java
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
import de.te2m.eclipse.service.model.tree.AttributedTreeParentNode;
import de.te2m.eclipse.service.model.tree.TreeNode;
import de.te2m.eclipse.service.model.tree.TreeParentNode;
import de.te2m.eclipse.service.model.tree.service.Parametrized;
import de.te2m.eclipse.service.model.tree.service.SimpleParameterNode;
import de.te2m.eclipse.service.views.ProjectModelProvider;

/**
 * The Class AbstractParameterWizard.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractParameterWizard extends Wizard implements INewWizard {

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
	protected Parametrized on;
	
	/**
	 * The tpn.
	 */
	protected SimpleParameterNode tpn;
	
	/**
	 * Instantiates a new manage parameter wizard.
	 *
	 * @param obj the obj
	 */
	public AbstractParameterWizard(TreeNode obj) {
		super();
		setNeedsProgressMonitor(true);
		if(obj instanceof Parametrized)
		{
			on=(Parametrized)obj;
			tpn = createNode();
		}
		else if(obj instanceof SimpleParameterNode)
		{
			on=null;
			tpn=(SimpleParameterNode)obj;
		}
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
	 * Creates the node.
	 *
	 * @return the simple parameter node
	 */
	protected abstract SimpleParameterNode createNode();

	/**
	 * Determines the parameter class.
	 *
	 * @param aTpn the a tpn
	 * @return the class info
	 */
	protected ClassInfo determineParameterClass( SimpleParameterNode aTpn) {
		
		ClassInfo ci = new ClassInfo();
		ci.setName(aTpn.getName());
		ci.setDescription(aTpn.getDescription());
		ci.setPkg(aTpn.getParamPkg());
		ci.setName(aTpn.getParamClass().getName());
		return ProjectModelProvider.getInstance().getProjectNode().validateClassInfo(ci);
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
	private void doFinish(String name, String desc, String type,String pkg,
			IProgressMonitor monitor) throws CoreException {
		monitor.beginTask("Update "+name, 1);

		
		
		if(tpn instanceof SimpleParameterNode)
		{
			SimpleParameterNode spn = (SimpleParameterNode)tpn;
		
			spn.setName(name);
			
			spn.setDescription(desc);
			
			spn.setParamPkg(pkg);
			
			spn.getParameterInfo().setParamClass(determineParameterClass(spn));
		}
		
		
		if(null!=on)
		{
			ParameterInfo param = new ParameterInfo();
			param.setName(name);
			param.setDescription(desc);
			param.setParamClass(type);
			ClassInfo ci = new ClassInfo();
			ci.setName(type);
			ci.setPkg(pkg);
			param.setParamClass(ProjectModelProvider.getInstance().getProjectNode().validateClassInfo(ci));
			on.addParameter(param);
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
					doFinish(name, desc, type, pkg,  monitor);
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
	
	/**
	 * Process parameter creation.
	 */
	protected void processParameterCreation() {
		((AttributedTreeParentNode)on).addChild(tpn);
	}

		
}