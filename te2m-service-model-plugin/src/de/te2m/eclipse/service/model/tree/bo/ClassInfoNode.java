/*
 * ClassInfoNode.java
 *   
 * Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
 *
 * This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
 * (http://temtools.sf.net).
 * 
 */
package de.te2m.eclipse.service.model.tree.bo;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import de.te2m.api.ext.project.bo.ClassInfo;
import de.te2m.api.ext.project.bo.Operation;
import de.te2m.api.ext.project.bo.ParameterInfo;
import de.te2m.eclipse.service.model.tree.AttributedTreeParentNode;
import de.te2m.eclipse.service.model.tree.TreeNode;
import de.te2m.eclipse.service.model.tree.service.HasOperations;
import de.te2m.eclipse.service.model.tree.service.OperationNode;
import de.te2m.eclipse.service.views.DefaultTreeViewLabelProvider;
import de.te2m.eclipse.service.views.actions.ManageBOAction;
import de.te2m.eclipse.service.views.actions.common.DeleteAction;
import de.te2m.eclipse.service.views.actions.parameter.AddConfigurationValueAction;
import de.te2m.eclipse.service.views.actions.project.SaveServiceTemplateAction;
import de.te2m.eclipse.service.views.actions.service.AddOperationAction;
import de.te2m.eclipse.service.views.actions.service.EditServiceAction;
import de.te2m.eclipse.service.views.actions.service.GenerateServiceCodeAction;
import de.te2m.project.util.ProjectUtils;

/**
 * The Class ClassInfoNode.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class ClassInfoNode extends AttributedTreeParentNode<ClassInfo>
		implements HasOperations {

	/**
	 * Instantiates a new class info node.
	 *
	 * @param ci
	 *            the ci
	 */
	public ClassInfoNode(ClassInfo ci) {
		super(ci);
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
		allowedActions.add(new ManageBOAction(viewer));
		allowedActions.add(new AddOperationAction(viewer));
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

		List<TreeNode> newList = new ArrayList<>(super.getChildrenAsList());

		if (!getClassInfo().getMembers().isEmpty()) {
			MemberListNode tpn = new MemberListNode("Attributes");

			for (Iterator iterator = getClassInfo().getMembers().iterator(); iterator
					.hasNext();) {
				ParameterInfo ci = (ParameterInfo) iterator.next();
				MemberNode cNode = new MemberNode(ci);
				tpn.addChild(cNode);

			}

			newList.add(tpn);
		}

		if (!getClassInfo().getOperations().isEmpty()) {
			MemberListNode tpn = new MemberListNode("Operations");

			for (Iterator iterator = getClassInfo().getOperations().iterator(); iterator
					.hasNext();) {
				Operation ci = (Operation) iterator.next();
				OperationNode cNode = new OperationNode(ci);
				tpn.addChild(cNode);

			}

			newList.add(tpn);
		}

		return newList;

	}

	/**
	 * Gets the class info.
	 *
	 * @return the class info
	 */
	public ClassInfo getClassInfo() {
		return getDelegatedObject();
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
				"/icons/class.png"), null);
		ImageDescriptor imageDcr = ImageDescriptor.createFromURL(url);
		return imageDcr.createImage();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.te2m.eclipse.service.model.tree.TreeNode#getName()
	 */
	@Override
	public String getName() {
		return getClassInfo().getName();
	}

	/* (non-Javadoc)
	 * @see de.te2m.eclipse.service.model.tree.service.HasOperations#getOperations()
	 */
	@Override
	public List<Operation> getOperations() {

		return getDelegatedObject().getOperations();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.te2m.eclipse.service.model.tree.TreeNode#setName(java.lang.String)
	 */
	@Override
	public void setName(String newName) {
		getClassInfo().setName(newName);
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
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see de.te2m.eclipse.service.model.tree.TreeNode#toString()
	 */
	@Override
	public String toString() {
		return getClassInfo().getName() + " ["
				+ ProjectUtils.getSignature(getClassInfo()) + "]";
	}

}
