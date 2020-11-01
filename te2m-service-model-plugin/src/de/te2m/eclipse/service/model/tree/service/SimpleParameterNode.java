/*
* SimpleParameterNode.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.model.tree.service;

import org.eclipse.core.commands.ParameterizedCommand;

import de.te2m.api.ext.project.bo.ClassInfo;
import de.te2m.api.ext.project.bo.ParameterInfo;
import de.te2m.eclipse.service.model.tree.AttributedTreeParentNode;
import de.te2m.eclipse.service.views.ProjectModelProvider;
import de.te2m.project.util.ProjectUtils;


/**
 * The Class SimpleParameterNode.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class SimpleParameterNode extends AttributedTreeParentNode<ParameterInfo> {

	/**
	 * Instantiates a new simple parameter node.
	 *
	 * @param pi the pi
	 */
	public SimpleParameterNode(ParameterInfo pi) {
		super(pi);
	}


	/**
	 * Determines the class info.
	 *
	 * @return the class inf
	 */
	protected ClassInfo determineClassInfo() {
		ClassInfo ci = ProjectUtils.determineObjectByID(ProjectModelProvider.getInstance().getProject(),ProjectUtils.getSignature((ClassInfo)getParameterInfo().getParamClass()));
		return ci;
	}


	/* (non-Javadoc)
	 * @see de.te2m.eclipse.service.model.tree.AttributedTreeParentNode#getDelegatedObject()
	 */
	@Override
	public ParameterInfo getDelegatedObject() {
		if(null==super.getDelegatedObject())
		{
			setDelegatedObject(new ParameterInfo());
		}
		return super.getDelegatedObject();
	}


	/**
	 * Gets the param class.
	 *
	 * @return the param class
	 */
	public ClassInfo getParamClass() {
		if(null==getDelegatedObject().getParamClass())
		{
			getDelegatedObject().setParamClass(new ClassInfo());
		}
		return (ClassInfo)getDelegatedObject().getParamClass();

	}

	/**
     * Gets the parameter info.
     *
     * @return the parameter info
     */
    public ParameterInfo getParameterInfo()
    {
		
    	return getDelegatedObject();
    }

	/**
	 * Gets the param pkg.
	 *
	 * @return the param pkg
	 */
	public String getParamPkg() {
		ClassInfo ci = getParamClass();
		if(null!=ci)
		{
			return ci.getPkg();
		}
		return null;
	}

	/**
	 * Sets the param class.
	 *
	 * @param ci the new param class
	 */
	public void setParamClass(ClassInfo ci) {
		getDelegatedObject().setParamClass(ci);
	}
	
    /**
	 * Sets the param pkg.
	 *
	 * @param name the new param pkg
	 */
	public void setParamPkg(String name) {
		ClassInfo ci = getParamClass();
		if(null!=ci)
		{
			ci.setPkg(name);
		}
		
		
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.te2m.eclipse.service.model.tree.AbstractDocumentedTreeNode#toString
	 * ()
	 */
	@Override
	public String toString() {

		if (null != getParamClass()) {
			return getName() + "[" + ProjectUtils.getSignature(getParamClass()) + "]";
		}
		return getName();
	}
    
    

    
}