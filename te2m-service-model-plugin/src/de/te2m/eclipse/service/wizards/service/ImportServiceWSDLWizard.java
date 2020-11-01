/*
* ImportServiceWSDLWizard.java
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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.INewWizard;

import de.te2m.api.ext.project.service.Service;
import de.te2m.eclipse.service.model.cfg.PropertyGroup;
import de.te2m.eclipse.service.model.tree.service.ServiceNode;
import de.te2m.eclipse.service.model.tree.service.SystemNode;
import de.te2m.eclipse.service.wizards.AbstractPropertyMgmtWizard;
import de.te2m.eclipse.service.wizards.MasterDataWizardPage;
import de.te2m.eclipse.service.wizards.PropertySetWizardPage;
import de.te2m.eclipse.service.wizards.impex.BasicImportWizardPage;


/**
 * The Class ImportServiceWSDLWizard.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class ImportServiceWSDLWizard extends AbstractPropertyMgmtWizard
		implements INewWizard {

	/**
	 * The import selection page.
	 */
	private BasicImportWizardPage selectWSDLpage;

	/**
	 * The page.
	 */
	private MasterDataWizardPage page;

	/**
	 * The soap property page.
	 */
	private PropertySetWizardPage soapPropertyPage;

	/**
	 * The tpn.
	 */
	private ServiceNode tpn;

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
	public ImportServiceWSDLWizard(SystemNode obj) throws CoreException {
		super();
		setNeedsProgressMonitor(true);

		//addPages();
		if (null != obj) {
			create = true;
			sn = (SystemNode) obj;
			tpn = new ServiceNode(new Service());
		}
	}

	/**
	 * Adding the page to the wizard.
	 */

	public void addPages() {
		selectWSDLpage= new BasicImportWizardPage("Import WSDL", selection);
		selectWSDLpage.setFileExt("*.wsdl");
		addPage(selectWSDLpage);
		page = new MasterDataWizardPage(selection);
		page.setSpn(tpn);
		addPage(page);
//		soapPropertyPage = initPropertyPage(PG_SERVICE_JAXWS_XML,
//				tpn.getProperties());
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

		tpn.setName(name);
		tpn.setDescription(desc);
		if (create) {
			sn.addChild(tpn);
		}
		//mergeProperties(tpn, pgs);
		monitor.worked(1);
	}

	/**
	 * Inits the property map.
	 * 
	 * @return the hashtable
	 */
	protected Hashtable<String, PropertyGroup> initPropertyMap() {
		final Hashtable<String, PropertyGroup> pgs = new Hashtable<>();
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