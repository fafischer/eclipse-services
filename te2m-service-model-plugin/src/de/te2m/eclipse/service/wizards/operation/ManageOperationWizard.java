/*
* ManageOperationWizard.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.wizards.operation;

import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.INewWizard;

import de.te2m.api.ext.core.IdObject.Attributes;
import de.te2m.api.ext.core.IdObject.Attributes.Entry;
import de.te2m.api.ext.project.bo.Operation;
import de.te2m.eclipse.service.model.cfg.PropertyCtnr;
import de.te2m.eclipse.service.model.cfg.PropertyGroup;
import de.te2m.eclipse.service.model.tree.AttributedTreeParentNode;
import de.te2m.eclipse.service.model.tree.service.HasOperations;
import de.te2m.eclipse.service.model.tree.service.OperationNode;
import de.te2m.eclipse.service.wizards.AbstractPropertyMgmtWizard;
import de.te2m.eclipse.service.wizards.MasterDataWizardPage;
import de.te2m.eclipse.service.wizards.PropertySetWizardPage;
import de.te2m.project.util.ProjectUtils;


/**
 * The Class ManageParameterWizard.
 * 
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class ManageOperationWizard extends AbstractPropertyMgmtWizard implements INewWizard {



	/**
	 * The rest property page.
	 */
	private PropertySetWizardPage restPropertyPage;
	
	/**
	 * The soap property page.
	 */
	private PropertySetWizardPage soapPropertyPage;
	
	// private ManageParameterWizardPage page;

	/**
	 * The operation.
	 */
	private Operation operation;

	/**
	 * The currentServiceNode.
	 */
	private HasOperations currentServiceNode;

	/**
	 * The create.
	 */
	boolean create;
	
	/**
	 * Instantiates a new manage operation wizard.
	 *
	 * @param obj the obj
	 * @throws CoreException the core exception
	 */
	public ManageOperationWizard(HasOperations obj) throws CoreException {
		super();
		setNeedsProgressMonitor(true);

		if (null != obj) {
			if (obj instanceof HasOperations) {
				create=true;
				currentServiceNode=(HasOperations)obj;
				operation = new Operation();
			}
			else
			throw new CoreException(null);
		}
	}
	
	/**
	 * Instantiates a new manage operation wizard.
	 *
	 * @param obj the obj
	 * @throws CoreException the core exception
	 */
	public ManageOperationWizard(OperationNode obj) throws CoreException {
		super();
		setNeedsProgressMonitor(true);

		if (null != obj) {
			if(obj instanceof OperationNode)
			{
				create = false;
				operation=((OperationNode)obj).getOperation();
			}
			else
			throw new CoreException(null);
		}
	}

	/**
	 * Adding the page to the wizard.
	 */

	public void addPages() {
		page = new MasterDataWizardPage(selection);
		if(create)
		{
			page.setTitle("New Operation");
			page.setDescription("Add a new operation to service "+ ((AttributedTreeParentNode)currentServiceNode).getName());
		}
		else
		{
			page.setTitle("Edit Operation");
			page.setDescription("Modify operation "+ operation.getName());
		}
		page.setDefaultName(operation.getName());
		page.setDefaultDescription(operation.getDescription());
		addPage(page);
		restPropertyPage = initPropertyPage( PG_OPERATION_JAXRS_XML, ProjectUtils.mapAttributesToMap(operation.getAttributes()));
		
		soapPropertyPage = initPropertyPage(PG_OPERATION_JAXWS_XML, ProjectUtils.mapAttributesToMap(operation.getAttributes()));

		
	}

	/**
	 * Executes finish.
	 *
	 * @param name the name
	 * @param desc the desc
	 * @param pgs the pgs
	 * @param monitor the monitor
	 * @throws CoreException the core exception
	 */
	private void doFinish(String name, String desc,Hashtable<String, PropertyGroup> pgs, IProgressMonitor monitor)
			throws CoreException {
		monitor.beginTask("Update Operation " + name, 1);

		operation.setName(name);
		operation.setDescription(desc);
		if(create)
		{
			currentServiceNode.addOperation(operation);
		}
		if(pgs!=null)
		{
			for (Iterator iterator = pgs.values().iterator(); iterator.hasNext();) {
				PropertyGroup pg = (PropertyGroup)iterator.next();
				
				if(null!=pg&&null!=pg.getProperties())
				{
					List<PropertyCtnr> props = pg.getProperties();
					
					for (Iterator iterator2 = props.iterator(); iterator2
							.hasNext();) {
						PropertyCtnr pC = (PropertyCtnr) iterator2
								.next();
			
						if(null!=pC&&null!=pC.getValue()&&pC.getValue().trim().length()>0)
						{
							Entry entry = new Entry();
							entry.setKey(pC.getName());
							entry.setValue(pC.getValue());
							if(null==operation.getAttributes()) {
								operation.setAttributes(new Attributes());
							}
							operation.getAttributes().getEntries().add(entry);
						}
					}
				}
				
			}
			
		}
		
		monitor.worked(1);
	}

	/**
	 * This method is called when 'Finish' button is pressed in the wizard. We
	 * will create an operation and run it using wizard as execution context.
	 * 
	 * @return true, if successful
	 */
	public boolean performFinish() {
		final String name = page.getEnteredName();
		final String description = page.getEnteredDescription();

		final Hashtable<String, PropertyGroup> propertyGroups = new Hashtable<>();
		
		if(null!=restPropertyPage)
		{
			propertyGroups.put(PG_OPERATION_JAXRS_XML, restPropertyPage.getPropertyGroup());
		}
		if(null!=soapPropertyPage)
		{
			propertyGroups.put(PG_OPERATION_JAXWS_XML, soapPropertyPage.getPropertyGroup());
		}
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor)
					throws InvocationTargetException {
				try {
					doFinish(name, description, propertyGroups,monitor);
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