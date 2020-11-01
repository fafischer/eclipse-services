/*
* ServiceNode.java
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
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import de.te2m.api.ext.project.bo.Operation;
import de.te2m.api.ext.project.bo.ParameterInfo;
import de.te2m.api.ext.project.service.Service;
import de.te2m.eclipse.service.model.tree.AttributedTreeParentNode;
import de.te2m.eclipse.service.model.tree.TreeNode;
import de.te2m.eclipse.service.views.DefaultTreeViewLabelProvider;
import de.te2m.eclipse.service.views.actions.common.DeleteAction;
import de.te2m.eclipse.service.views.actions.parameter.AddConfigurationValueAction;
import de.te2m.eclipse.service.views.actions.project.SaveServiceTemplateAction;
import de.te2m.eclipse.service.views.actions.service.AddOperationAction;
import de.te2m.eclipse.service.views.actions.service.EditServiceAction;
import de.te2m.eclipse.service.views.actions.service.GenerateServiceCodeAction;

/**
 * The Class ServiceNode.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class ServiceNode extends AttributedTreeParentNode<Service> implements Configurable, HasOperations
{

    /**
     * Instantiates a new service node.
     *
     * @param ci the ci
     */
    public ServiceNode(Service ci) {
		super(ci);
	}

	/**
	 * Adds the config value.
	 *
	 * @param cfgVal the cfg val
	 */
	public void addConfigValue(ParameterInfo cfgVal)
	{
		getConfiguration().add(cfgVal);
	}

	/* (non-Javadoc)
	 * @see de.te2m.eclipse.service.model.tree.service.HasOperations#addOperation(de.te2m.api.ext.project.bo.Operation)
	 */
	@Override
	public void addOperation(Operation op) {
		if (null != op) {
			getDelegatedObject().getOperations().add(op);
		}
	}

	/* (non-Javadoc)
	 * @see de.te2m.eclipse.service.model.tree.TreeParentNode#getAllowedActions(org.eclipse.jface.viewers.TreeViewer)
	 */
	@Override
	public List<Action> getAllowedActions(TreeViewer viewer) {
		List<Action> allowedActions = new ArrayList<Action>();
		allowedActions.add(new EditServiceAction(viewer));
		allowedActions.add(new AddOperationAction(viewer));	
		allowedActions.add(new AddConfigurationValueAction(viewer));
		allowedActions.add(new DeleteAction(viewer));
		allowedActions.add(new SaveServiceTemplateAction(viewer));
		allowedActions.add(new GenerateServiceCodeAction(viewer));
		allowedActions.addAll(super.getAllowedActions(viewer));
		return allowedActions;
	}

	/* (non-Javadoc)
	 * @see de.te2m.eclipse.service.model.tree.AbstractDocumentedTreeNode#getChildrenAsList()
	 */
	@Override
	public List<TreeNode> getChildrenAsList() {
		
		List<TreeNode> newList = new ArrayList<>(super.getChildrenAsList());
		
		ConfigurationSetNode tpn = new ConfigurationSetNode("Configuration");
		
		tpn.setParent(this);
		
		for (Iterator iterator = getConfiguration().iterator(); iterator.hasNext();) {
			ParameterInfo ci = (ParameterInfo) iterator.next();
			ConfigurationNode cNode = new ConfigurationNode(ci);
			tpn.addChild(cNode);
			
		}
		
		newList.add(tpn);
		
		OperationListNode oln = new OperationListNode("Operations");
		
		for (Operation op: getService().getOperations()) {
			oln.addChild(new OperationNode(op));
			
		}
		
		newList.add(oln);
		
		return newList;

	}
    
	/**
	 * Gets the configuration.
	 *
	 * @return the configuration
	 */
	public List<ParameterInfo> getConfiguration() {
		return getService().getConfigurations();
	}

	
	

	/* (non-Javadoc)
	 * @see de.te2m.eclipse.service.model.tree.TreeNode#getImage()
	 */
	@Override
	public Image getImage() {

		Bundle bundle = FrameworkUtil
				.getBundle(DefaultTreeViewLabelProvider.class);
		URL url = FileLocator.find(bundle, new org.eclipse.core.runtime.Path(
				"/icons/bullet.png"), null);
		ImageDescriptor imageDcr = ImageDescriptor.createFromURL(url);
		return imageDcr.createImage();
	}
	
	/* (non-Javadoc)
     * @see de.te2m.eclipse.service.model.tree.TreeParentNode#getImageDescriptor()
     */
    public String getImageDescriptor()
    {

            return ISharedImages.IMG_OBJ_ELEMENT;
    }


	/* (non-Javadoc)
	 * @see de.te2m.eclipse.service.model.tree.service.HasOperations#getOperations()
	 */
	@Override
	public List<Operation> getOperations() {

		return getDelegatedObject().getOperations();
	}

	/**
	 * Gets the service.
	 *
	 * @return the service
	 */
	public Service getService() {

		return getDelegatedObject();
	}

	/**
	 * Sets the configuration.
	 *
	 * @param configuration the new configuration
	 */
	public void setConfiguration(List<ParameterInfo> configuration) {
		getService().getConfigurations().clear();
		getService().getConfigurations().addAll(configuration);
	}

	/* (non-Javadoc)
	 * @see de.te2m.eclipse.service.model.tree.service.HasOperations#setOperations(java.util.List)
	 */
	@Override
	public void setOperations(List<Operation> operations) {
		getDelegatedObject().getOperations().clear();
		if (null != operations) {
			getDelegatedObject().getOperations().addAll(operations);
		}
	}

	/**
	 * Sets the service.
	 *
	 * @param service the new service
	 */
	public void setService(Service service) {
		setDelegatedObject(service);
	}
	
}
