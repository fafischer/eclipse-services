/*
* SystemNode.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.model.tree.service;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import de.te2m.api.ext.project.service.Service;
import de.te2m.api.ext.project.service.ServiceBehavior;
import de.te2m.api.ext.project.service.SystemInfo;
import de.te2m.eclipse.service.model.tree.AttributedTreeParentNode;
import de.te2m.eclipse.service.model.tree.TreeNode;
import de.te2m.eclipse.service.views.DefaultTreeViewLabelProvider;
import de.te2m.eclipse.service.views.actions.EditSystemAction;
import de.te2m.eclipse.service.views.actions.common.DeleteAction;
import de.te2m.eclipse.service.views.actions.project.ImportWSDLAction;
import de.te2m.eclipse.service.views.actions.service.AddServiceAction;

/**
 * The Class ServiceNode.
 * 
 * 
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class SystemNode extends AttributedTreeParentNode<SystemInfo> {

	/**
	 * Instantiates a new system node.
	 * 
	 * @param sys
	 *            the sys
	 */
	public SystemNode(SystemInfo sys) {
		super(sys);
	}

	/**
	 * Adds the service.
	 * 
	 * @param newService
	 *            the new service
	 */
	public void addService(Service newService) {
		getServices().add(newService);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.te2m.eclipse.service.model.tree.TreeParentNode#getAllowedActions
	 * (org.eclipse.jface.viewers.TreeViewer)
	 */
	@Override
	public List<Action> getAllowedActions(TreeViewer viewer) {
		List<Action> allowedActions = new ArrayList<Action>();
		allowedActions.add(new EditSystemAction(viewer));
		allowedActions.add(new DeleteAction(viewer));
		allowedActions.add(new AddServiceAction(viewer));
		allowedActions.add(new ImportWSDLAction(viewer));
		allowedActions.addAll(super.getAllowedActions(viewer));
		return allowedActions;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.te2m.eclipse.service.model.tree.AbstractDocumentedTreeNode#
	 * getChildrenAsList()
	 */
	@Override
	public List<TreeNode> getChildrenAsList() {
		if (getServices().isEmpty()) {
			return super.getChildrenAsList();
		}

		List<TreeNode> newList = new ArrayList<>();

		ServiceListNode sln = new ServiceListNode("Services");

		ServiceClientListNode scln = new ServiceClientListNode(
				"Service-Clients");

		for (Service service : getServices()) {

			if (ServiceBehavior.CONSUMES == service.getBehavior()) {
				scln.addChild(new ServiceNode(service));
			} else {
				sln.addChild(new ServiceNode(service));
			}

		}

		newList.add(sln);
		
		newList.add(scln);
		
		newList.addAll(super.getChildrenAsList());


		return newList;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.te2m.eclipse.service.model.tree.TreeNode#getImage()
	 */
	@Override
	public Image getImage() {

		Bundle bundle = FrameworkUtil
				.getBundle(DefaultTreeViewLabelProvider.class);
		URL url = FileLocator.find(bundle, new org.eclipse.core.runtime.Path(
				"/icons/system.png"), null);
		ImageDescriptor imageDcr = ImageDescriptor.createFromURL(url);
		return imageDcr.createImage();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.te2m.eclipse.service.model.tree.TreeParentNode#getImageDescriptor()
	 */
	public String getImageDescriptor() {
		return ISharedImages.IMG_OBJ_ELEMENT;
	}

	/**
	 * Gets the services.
	 * 
	 * @return the services
	 */
	public List<Service> getServices() {

		return getDelegatedObject().getServices();
	}

	/**
	 * Gets the system.
	 * 
	 * @return the system
	 */
	public SystemInfo getSystem() {
		return getDelegatedObject();
	}

	/**
	 * Sets the services.
	 * 
	 * @param services
	 *            the new services
	 */
	public void setServices(List<Service> services) {
		getDelegatedObject().getServices().clear();
		getDelegatedObject().getServices().addAll(services);
	}

	/**
	 * Sets the system.
	 * 
	 * @param si
	 *            the new system
	 */
	public void setSystem(SystemInfo si) {
		setDelegatedObject(si);
	}

}
