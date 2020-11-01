/*
* ManageServiceWizard.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.wizards.service;

import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;
import java.util.UUID;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.INewWizard;

import de.te2m.api.ext.project.service.Service;
import de.te2m.eclipse.service.model.cfg.PropertyGroup;
import de.te2m.eclipse.service.model.tree.AttributedTreeParentNode;
import de.te2m.eclipse.service.model.tree.service.ServiceNode;
import de.te2m.eclipse.service.model.tree.service.SystemNode;
import de.te2m.eclipse.service.wizards.AbstractPropertyMgmtWizard;
import de.te2m.eclipse.service.wizards.MasterDataWizardPage;
import de.te2m.eclipse.service.wizards.PropertySetWizardPage;
import de.te2m.project.util.ProjectUtils;

/**
 * The Class ManageServiceWizard.
 * 
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class ManageServiceWizard extends AbstractPropertyMgmtWizard implements
		INewWizard {

	/**
	 * The page.
	 */
	private MasterDataWizardPage page;

	/**
	 * The rest property page.
	 */
	private PropertySetWizardPage restPropertyPage;

	/**
	 * The soap property page.
	 */
	private PropertySetWizardPage soapPropertyPage;

	/**
	 * The currentService.
	 */
	private Service currentService;

	/**
	 * The sn.
	 */
	private SystemNode sn;

	/**
	 * The create.
	 */
	boolean create;

	/**
	 * Instantiates a new manage operation wizard.
	 * 
	 * @param obj
	 *            the obj
	 * @throws CoreException
	 *             the core exception
	 */
	public ManageServiceWizard(AttributedTreeParentNode obj)
			throws CoreException {
		super();
		setNeedsProgressMonitor(true);

		if (null != obj) {
			if (obj instanceof SystemNode) {
				create = true;
				sn = (SystemNode) obj;
				currentService = new Service();
				currentService.setId(UUID.randomUUID().toString());
			} else if (obj instanceof ServiceNode) {
				create = false;
				currentService = ((ServiceNode) obj).getService();
			} else
				throw new CoreException(null);
		}
	}

	/**
	 * Adding the page to the wizard.
	 */

	public void addPages() {
		page = new MasterDataWizardPage(selection);
		if (null != currentService) {
			page.setDefaultDescription(currentService.getDescription());
			page.setDefaultName(currentService.getName());
		}
		addPage(page);
		restPropertyPage = initPropertyPage(PG_SERVICE_JAXRS_XML,
				ProjectUtils.mapAttributesToMap(currentService.getAttributes()));
		soapPropertyPage = initPropertyPage(PG_SERVICE_JAXWS_XML,
				ProjectUtils.mapAttributesToMap(currentService.getAttributes()));
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
	protected void doFinish(String name, String desc,
			Hashtable<String, PropertyGroup> pgs, IProgressMonitor monitor)
			throws CoreException {
		monitor.beginTask("Update parameter " + name, 1);

		currentService.setName(name);
		currentService.setDescription(desc);
		if (create) {
			sn.getServices().add(currentService);
		}

		// TODO Review Property/attribute handling
		// mergeProperties(currentService, pgs);
		monitor.worked(1);
	}

	/**
	 * Inits the property map.
	 * 
	 * @return the hashtable
	 */
	protected Hashtable<String, PropertyGroup> initPropertyMap() {
		final Hashtable<String, PropertyGroup> pgs = new Hashtable<>();
		if (null != restPropertyPage) {
			pgs.put(PG_SERVICE_JAXRS_XML, restPropertyPage.getPropertyGroup());
		}
		if (null != soapPropertyPage) {
			pgs.put(PG_SERVICE_JAXWS_XML, soapPropertyPage.getPropertyGroup());
		}
		return pgs;
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

		final Hashtable<String, PropertyGroup> pgs = initPropertyMap();
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor)
					throws InvocationTargetException {
				try {
					doFinish(name, description, pgs, monitor);
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