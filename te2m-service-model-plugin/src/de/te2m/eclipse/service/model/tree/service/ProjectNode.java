/*
* ProjectNode.java
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

import de.te2m.api.ext.project.Project;
import de.te2m.api.ext.project.bo.ClassInfo;
import de.te2m.api.ext.project.service.SystemInfo;
import de.te2m.eclipse.service.model.tree.AttributedTreeParentNode;
import de.te2m.eclipse.service.model.tree.TreeNode;
import de.te2m.eclipse.service.views.DefaultTreeViewLabelProvider;
import de.te2m.eclipse.service.views.actions.AddSystemAction;
import de.te2m.eclipse.service.views.actions.common.EditDescriptionAction;
import de.te2m.eclipse.service.views.actions.common.RenameAction;
import de.te2m.eclipse.service.views.actions.project.GenerateProjectCodeAction;
import de.te2m.eclipse.service.views.actions.project.SaveProjectAction;
import de.te2m.project.util.ProjectUtils;

/**
 * The Class ProjectNode.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class ProjectNode extends AttributedTreeParentNode<Project>
{

    /**
     * The container hint.
     */
    private String containerHint;
    

    /**
     * Instantiates a new project node.
     *
     * @param p the p
     */
    public ProjectNode(Project p)
    {
        super(p);
        addAttribute("user.name", System.getProperty("user.name"));
    }

    


	/* (non-Javadoc)
	 * @see de.te2m.eclipse.service.model.tree.TreeParentNode#getAllowedActions(org.eclipse.jface.viewers.TreeViewer)
	 */
	@Override
	public List<Action> getAllowedActions(TreeViewer viewer) {
		List<Action> allowedActions = new ArrayList<Action>();
		allowedActions.add(new RenameAction(viewer));
		allowedActions.add(new EditDescriptionAction(viewer));
		allowedActions.add(new AddSystemAction(viewer));
		allowedActions.add(new SaveProjectAction(viewer));
		allowedActions.add(new GenerateProjectCodeAction(viewer));
		allowedActions.addAll(super.getAllowedActions(viewer));
		return allowedActions;
	}




	/* (non-Javadoc)
	 * @see de.te2m.eclipse.service.model.tree.AttributedTreeParentNode#getChildrenAsList()
	 */
	@Override
	public List<TreeNode> getChildrenAsList() {
		List<TreeNode> children = new ArrayList<TreeNode>();
		
		for(SystemInfo si: getDelegatedObject().getSystems())
		{
			children.add(new SystemNode(si));
		}
		
		children.addAll(super.getChildrenAsList());
		
		return children;
	}


	/**
	 * Gets the class info list.
	 *
	 * @return the class info list
	 */
	public List<ClassInfo> getClassInfoList() {

		return getDelegatedObject().getObjects();
	}




	/* (non-Javadoc)
	 * @see de.te2m.eclipse.service.model.tree.TreeNode#getImage()
	 */
	@Override
	public Image getImage() {

		Bundle bundle = FrameworkUtil
				.getBundle(DefaultTreeViewLabelProvider.class);
		URL url = FileLocator.find(bundle, new org.eclipse.core.runtime.Path(
				"/icons/logo.png"), null);
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




	/**
	 * Gets the project.
	 *
	 * @return the project
	 */
	public Project getProject()
	{
		return getDelegatedObject();
	}
	
	
	/**
	 * Gets the storage location hint.
	 *
	 * @return the storage location hint
	 */
	public String getStorageLocationHint() {

		return this.containerHint;
	}




	/**
	 * Sets the classes.
	 *
	 * @param classes the new classes
	 */
	public void setClasses(List<ClassInfo> classes) {
		
		getDelegatedObject().getObjects().clear();
		getDelegatedObject().getObjects().addAll(classes);
		
		
	}

	/**
	 * Sets the project.
	 *
	 * @param p the new project
	 */
	public void setProject(Project p)
	{
		setDelegatedObject(p);
	}
	
	
	/**
	 * Sets the storage location hint.
	 *
	 * @param containername the new storage location hint
	 */
	public void setStorageLocationHint(String containername) {

		this.containerHint=containername;
	}




	/**
	 * Validate class info.
	 *
	 * @param info the info
	 * @return the class info
	 */
	public ClassInfo validateClassInfo(ClassInfo info)
	{
		if(null==info){
			return null;
		}
		
		ClassInfo existing = ProjectUtils.determineObjectBySignature(getDelegatedObject(),ProjectUtils.getSignature(info));

		if(null==existing)
		{
			if(null==info.getId())
			{
				ProjectUtils.resetId(info);
			}
			getDelegatedObject().getObjects().add(info);
			return info;
		}
		else 
		{
			return existing;
		}
	}
	
	
}
