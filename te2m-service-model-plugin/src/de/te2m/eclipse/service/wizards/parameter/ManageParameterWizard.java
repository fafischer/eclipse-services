/*
* ManageParameterWizard.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.wizards.parameter;

import de.te2m.eclipse.service.model.tree.TreeNode;
import de.te2m.eclipse.service.model.tree.service.ParameterNode;
import de.te2m.eclipse.service.model.tree.service.SimpleParameterNode;

/**
 * The Class ManageParameterWizard.
 * 
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class ManageParameterWizard extends AbstractParameterWizard {

	
	/**
	 * Instantiates a new manage parameter wizard.
	 *
	 * @param obj the obj
	 */
	public ManageParameterWizard(TreeNode obj) {
		super(obj);
	}

	/* (non-Javadoc)
	 * @see de.te2m.eclipse.service.wizards.parameter.AbstractParameterWizard#createNode()
	 */
	protected SimpleParameterNode createNode() {
		return new ParameterNode(null);
	}
		
}