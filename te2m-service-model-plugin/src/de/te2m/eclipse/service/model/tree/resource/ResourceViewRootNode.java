/*
* ResourceViewRootNode.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.model.tree.resource;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.ui.ISharedImages;

import de.te2m.api.ext.project.Project;
import de.te2m.api.ext.project.bo.Operation;
import de.te2m.api.ext.project.service.Service;
import de.te2m.api.ext.project.service.SystemInfo;
import de.te2m.eclipse.service.model.tree.AbstractParentNode;
import de.te2m.eclipse.service.model.tree.ErrorNode;
import de.te2m.eclipse.service.model.tree.TreeNode;
import de.te2m.eclipse.service.model.tree.TreeParentNode;
import de.te2m.eclipse.service.views.ProjectModelProvider;
import de.te2m.project.util.ProjectUtils;


/**
 * The Class ResourceViewRootNode.
 * 
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class ResourceViewRootNode extends AbstractParentNode {

	/**
	 * The description.
	 */
	private String description;

	/**
	 * Instantiates a new root node.
	 * 
	 * @param name
	 *            the name
	 */
	public ResourceViewRootNode(String name) {
		super(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.te2m.eclipse.service.model.tree.AbstractParentNode#getChildrenAsList
	 * ()
	 */
	@Override
	public List<TreeNode> getChildrenAsList() {

		List<TreeNode> newList = new ArrayList<>();
		Project project = ProjectModelProvider.getInstance().getProject();
		if (null != project) {
			List<SystemInfo> systems = project.getSystems();

			for (Iterator iterator = systems.iterator(); iterator.hasNext();) {
				SystemInfo systemInfo = (SystemInfo) iterator.next();

				TreeParentNode tpn = new TreeParentNode(systemInfo.getName());
				for (Service service : systemInfo.getServices()) {

					TreeParentNode spn = new TreeParentNode(service.getName());

					List<Operation> ops = service.getOperations();

					String pathAttribute = ProjectUtils.getAttribute(service,"jaxrs.path");

					if (null == pathAttribute) {
						pathAttribute = "";
					}

					for (Iterator iterator3 = ops.iterator(); iterator3
							.hasNext();) {
						Operation operation = (Operation) iterator3.next();

						String operationPathAttribute = ProjectUtils.getAttribute(operation,"jaxrs.path");

						String methodAttribute = ProjectUtils.getAttribute(operation,"jaxrs.method");

						if (null == operationPathAttribute) {
							operationPathAttribute = "";
						}

						TreeNode resourceNode = null;

						if (operationPathAttribute.trim().length() > 0
								&& pathAttribute.trim().length() > 0) {

							resourceNode = new TreeNode(pathAttribute + "/"
									+ operationPathAttribute + "["
									+ methodAttribute + "]");

						} else {
							resourceNode = new ErrorNode("Incomplete data for "
									+ operation.getName());
						}

						spn.addChild(resourceNode);
					}

					tpn.addChild(spn);

				}

				newList.add(tpn);
			}
		}
		return newList;
	}

	/**
	 * Gets the description.
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
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
	 * Sets the description.
	 * 
	 * @param stext
	 *            the new description
	 */
	public void setDescription(String stext) {
		description = stext;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.te2m.eclipse.service.model.tree.TreeNode#toString()
	 */
	public String toString() {
		if (getDescription() == null || getDescription().trim().length() == 0)
			return getName();
		else

			return getName() + "(" + getDescription() + ")";
	}

}
