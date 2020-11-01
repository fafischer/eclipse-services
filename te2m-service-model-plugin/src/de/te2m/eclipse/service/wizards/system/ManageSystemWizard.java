/*
* ManageSystemWizard.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.wizards.system;

import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.INewWizard;

import de.te2m.api.ext.project.service.SystemInfo;
import de.te2m.eclipse.service.model.cfg.PropertyCtnr;
import de.te2m.eclipse.service.model.cfg.PropertyGroup;
import de.te2m.eclipse.service.model.tree.AttributedTreeParentNode;
import de.te2m.eclipse.service.model.tree.service.ProjectNode;
import de.te2m.eclipse.service.model.tree.service.SystemNode;
import de.te2m.eclipse.service.wizards.AbstractPropertyMgmtWizard;
import de.te2m.eclipse.service.wizards.MasterDataWizardPage;
import de.te2m.eclipse.service.wizards.PropertySetWizardPage;
import de.te2m.project.util.ProjectUtils;


/**
 * The Class ManageSystemWizard.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class ManageSystemWizard extends AbstractPropertyMgmtWizard implements
		INewWizard {

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
	 * The sysInfo.
	 */
	private ProjectNode projectNode;

	/**
	 * The projectNode.
	 */
	private SystemInfo sysInfo;

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
	public ManageSystemWizard(AttributedTreeParentNode obj)
			throws CoreException {
		super();
		setNeedsProgressMonitor(true);

		if (null != obj) {
			if (obj instanceof ProjectNode) {
				create = true;
				projectNode = (ProjectNode) obj;
				sysInfo = new SystemInfo();
				sysInfo.setId(UUID.randomUUID().toString());
			} else if (obj instanceof SystemNode) {
				create = false;
				sysInfo = ((SystemNode) obj).getSystem();
			} else
				throw new CoreException(null);
		}
	}

	/**
	 * Adding the page to the wizard.
	 */

	public void addPages() {
		page = new MasterDataWizardPage(selection);
		if(!create)
		{
			page.setTitle("Manage system "+sysInfo.getName());
		}
		else
		{
			page.setTitle("Create new system");
		}
		page.setDefaultName(sysInfo.getName());
		page.setDefaultDescription(sysInfo.getDescription());
		addPage(page);
		/*
		restPropertyPage = initPropertyPage(PG_OPERATION_JAXRS_XML,
				sysInfo.getProperties());

		soapPropertyPage = initPropertyPage(PG_OPERATION_JAXWS_XML,
				sysInfo.getProperties());

^		*/
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
	private void doFinish(String name, String desc,
			Hashtable<String, PropertyGroup> pgs, IProgressMonitor monitor)
			throws CoreException {
		monitor.beginTask("Update parameter " + name, 1);

		sysInfo.setName(name);
		sysInfo.setDescription(desc);
		if (create) {
			projectNode.getProject().getSystems().add(sysInfo);
		}
		if (pgs != null) {
			for (Iterator iterator = pgs.values().iterator(); iterator
					.hasNext();) {
				PropertyGroup pg = (PropertyGroup) iterator.next();

				if (null != pg && null != pg.getProperties()) {
					List<PropertyCtnr> props = pg.getProperties();

					for (Iterator iterator2 = props.iterator(); iterator2
							.hasNext();) {
						PropertyCtnr pC = (PropertyCtnr) iterator2.next();

						if (null != pC && null != pC.getValue()
								&& pC.getValue().trim().length() > 0) {
							ProjectUtils.setAttribute(sysInfo, pC.getName(), pC.getValue());
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

		final Hashtable<String, PropertyGroup> pgs = new Hashtable<>();
		if (null != restPropertyPage) {
			pgs.put(PG_OPERATION_JAXRS_XML, restPropertyPage.getPropertyGroup());
		}
		if (null != soapPropertyPage) {
			pgs.put(PG_OPERATION_JAXWS_XML, soapPropertyPage.getPropertyGroup());
		}
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