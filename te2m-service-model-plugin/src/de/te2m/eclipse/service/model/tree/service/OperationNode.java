/*
* OperationNode.java
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
import de.te2m.eclipse.service.model.tree.AttributedTreeParentNode;
import de.te2m.eclipse.service.model.tree.TreeNode;
import de.te2m.eclipse.service.views.DefaultTreeViewLabelProvider;
import de.te2m.eclipse.service.views.actions.common.DeleteAction;
import de.te2m.eclipse.service.views.actions.parameter.AddDeclaredErrorAction;
import de.te2m.eclipse.service.views.actions.parameter.AddParameterAction;
import de.te2m.eclipse.service.views.actions.parameter.AddReturnValueAction;
import de.te2m.eclipse.service.views.actions.service.EditOperationAction;


/**
 * The Class OperationNode.
 * 
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class OperationNode extends AttributedTreeParentNode<Operation> implements Parametrized, ReturnsErrors, ReturnsParameter{

	/**
	 * Instantiates a new operation node.
	 * 
	 * @param op
	 *            the op
	 */
	public OperationNode(Operation op) {
		//
		super(op);

	}

	/* (non-Javadoc)
	 * @see de.te2m.eclipse.service.model.tree.service.ReturnsErrors#addDeclaredError(de.te2m.api.ext.project.bo.ParameterInfo)
	 */
	@Override
	public void addDeclaredError(ParameterInfo cfgVal) {
		getDeclaredErrors().add(cfgVal);
	}

	/* (non-Javadoc)
	 * @see de.te2m.eclipse.service.model.tree.service.Parametrized#addParameter(de.te2m.api.ext.project.bo.ParameterInfo)
	 */
	@Override
	public void addParameter(ParameterInfo param) {
		getParameter().add(param);
		
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
		allowedActions.add(new EditOperationAction(viewer));
		allowedActions.add(new AddParameterAction(viewer));
		allowedActions.add(new AddReturnValueAction(viewer));
		allowedActions.add(new AddDeclaredErrorAction(viewer));
		allowedActions.add(new DeleteAction(viewer));
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

		boolean hasParameter = !getOperation().getParameters().isEmpty();
		
		boolean hasReturnValue = getOperation().getReturnValue() != null;
		
		if (hasParameter || hasReturnValue) {

			ParameterListNode pln = new ParameterListNode("Parameter");

			for (ParameterInfo paramInfo : getOperation().getParameters()) {
				ParameterNode cNode = new ParameterNode(paramInfo);
				pln.addChild(cNode);
			}

			if (null != getOperation().getReturnValue()) {
				pln.addChild(new RetValNode(getOperation().getReturnValue()));
			}
			newList.add(pln);

			pln.setParent(this);

		}

		if (!getDeclaredErrors().isEmpty()) {
			DeclaredErrorListNode tpn = new DeclaredErrorListNode("Errors");

			for (Iterator iterator = getDeclaredErrors().iterator(); iterator
					.hasNext();) {
				ParameterInfo paramInfo = (ParameterInfo) iterator.next();
				DeclaredErrorNode cNode = new DeclaredErrorNode(paramInfo);
				tpn.addChild(cNode);

			}

			newList.add(tpn);
			tpn.setParent(this);
		}
		return newList;
	}

	/* (non-Javadoc)
	 * @see de.te2m.eclipse.service.model.tree.service.ReturnsErrors#getDeclaredErrors()
	 */
	@Override
	public List<ParameterInfo> getDeclaredErrors() {

		return getDelegatedObject().getErrors();
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
				"/icons/operation.png"), null);
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

		return ISharedImages.IMG_OBJS_BKMRK_TSK;
	}

	/**
	 * Gets the operation.
	 * 
	 * @return the operation
	 */
	public Operation getOperation() {

		return getDelegatedObject();
	}

	/* (non-Javadoc)
	 * @see de.te2m.eclipse.service.model.tree.service.Parametrized#getParameter()
	 */
	@Override
	public List<ParameterInfo> getParameter() {

		return getOperation().getParameters();
	}

	/* (non-Javadoc)
	 * @see de.te2m.eclipse.service.model.tree.service.ReturnsParameter#getReturnValue()
	 */
	@Override
	public ParameterInfo getReturnValue() {
		
		return getOperation().getReturnValue();
	}

	/* (non-Javadoc)
	 * @see de.te2m.eclipse.service.model.tree.service.ReturnsErrors#setDeclaredErrors(java.util.List)
	 */
	@Override
	public void setDeclaredErrors(List<ParameterInfo> declaredErrors) {
		getDelegatedObject().getErrors().clear();
		getDelegatedObject().getErrors().addAll(declaredErrors);
	}

	/**
	 * Sets the operation.
	 * 
	 * @param operation
	 *            the new operation
	 */
	public void setOperation(Operation operation) {
		setDelegatedObject(operation);
	}

	/* (non-Javadoc)
	 * @see de.te2m.eclipse.service.model.tree.service.Parametrized#setParameter(java.util.List)
	 */
	@Override
	public void setParameter(List<ParameterInfo> params) {

		getParameter().clear();
		getParameter().addAll(params);
		
	}

	/* (non-Javadoc)
	 * @see de.te2m.eclipse.service.model.tree.service.ReturnsParameter#setReturnValue(de.te2m.api.ext.project.bo.ParameterInfo)
	 */
	@Override
	public void setReturnValue(ParameterInfo retVal) {
		
		getOperation().setReturnValue(retVal);
	}
}
