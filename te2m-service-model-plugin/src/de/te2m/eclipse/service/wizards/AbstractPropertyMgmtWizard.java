/*
* AbstractPropertyMgmtWizard.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.wizards;

import java.io.InputStream;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;

import de.te2m.eclipse.service.model.cfg.PropertyGroup;

/**
 * The Class AbstractPropertyMgmtWizard.
 * 
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractPropertyMgmtWizard extends Wizard {

	/**
	 * The Constant PG_OPERATION_JAXRS_XML.
	 */
	protected static final String PG_OPERATION_JAXRS_XML = "/operationJAXRS.xml";
	/**
	 * The Constant PG_OPERATION_JAXWS_XML.
	 */
	protected static final String PG_OPERATION_JAXWS_XML = "/operationJAXWS.xml";

	/**
	 * The Constant PG_SERVICE_JAXWS_XML.
	 */
	protected static final String PG_SERVICE_JAXWS_XML = "/serviceJAXWS.xml";

	/**
	 * The Constant PG_SERVICE_JAXRS_XML.
	 */
	protected static final String PG_SERVICE_JAXRS_XML = "/serviceJAXRS.xml";

	/**
	 * The page.
	 */
	protected MasterDataWizardPage page;

	/**
	 * The selection.
	 */
	protected ISelection selection;

	/**
	 * Instantiates a new abstract property mgmt wizard.
	 */
	public AbstractPropertyMgmtWizard() {
		super();
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
	 * Inits the property page.
	 * 
	 * @param groupID
	 *            the group id
	 * @param attributeMap
	 *            the p
	 * @return the property set wizard page
	 */
	protected PropertySetWizardPage initPropertyPage(String groupID,
			Map<String, String> attributeMap) {
		PropertySetWizardPage pswp = new PropertySetWizardPage(selection);
		PropertyGroup pg = preparePropertyGroup(groupID, attributeMap);
		if (null == pg)
			return null;
		pswp.setPropertyGroup(pg);
		addPage(pswp);
		return pswp;
	}

	/**
	 * Prepares the property group.
	 * 
	 * @param name
	 *            the name
	 * @param properties
	 *            the properties
	 * @return the property group
	 */
	protected PropertyGroup preparePropertyGroup(String name,
			Map<String, String> properties) {

		PropertyGroup propertyGroup = null;
		JAXBContext context;

		try {

			InputStream propsStream = this.getClass().getResourceAsStream(name);

			if (null == propsStream) {
				return null;
			}

			context = JAXBContext.newInstance(PropertyGroup.class);

			Unmarshaller um = context.createUnmarshaller();

			propertyGroup = (PropertyGroup) um.unmarshal(propsStream);

		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (null != propertyGroup) {
			propertyGroup.initValues(properties);
		}

		return propertyGroup;
	}

}