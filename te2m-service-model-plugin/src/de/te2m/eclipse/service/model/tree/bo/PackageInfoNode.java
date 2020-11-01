/*
* PackageInfoNode.java
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
import java.util.Hashtable;
import java.util.List;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import de.te2m.eclipse.service.model.tree.TreeNode;
import de.te2m.eclipse.service.model.tree.TreeParentNode;
import de.te2m.eclipse.service.views.DefaultTreeViewLabelProvider;


/**
 * The Class PackageInfoNode.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class PackageInfoNode extends TreeParentNode {

	
	/**
	 * The subpackages.
	 */
	private Hashtable<String, PackageInfoNode> subpackages;
	
	/**
	 * The package members.
	 */
	private List<ClassInfoNode> packageMembers;
	
	/**
	 * Instantiates a new class info node.
	 *
	 * @param name the name
	 */
	public PackageInfoNode(String name) {
		super(name);
		
	}

	/* (non-Javadoc)
	 * @see de.te2m.eclipse.service.model.tree.AbstractDocumentedTreeNode#getChildrenAsList()
	 */
	@Override
	public List<TreeNode> getChildrenAsList() {
		if(getSubpackages().isEmpty()&&getPackageMembers().isEmpty())
		{
			return super.getChildrenAsList();
	
		}
		
		List<TreeNode> newList = new ArrayList<>(super.getChildrenAsList());
		
		newList.addAll(getSubpackages().values());
		
		newList.addAll(getPackageMembers());
		
		return newList;
	}

	
	
	/* (non-Javadoc)
	 * @see de.te2m.eclipse.service.model.tree.TreeNode#getImage()
	 */
	@Override
	public Image getImage() {

		Bundle bundle = FrameworkUtil
				.getBundle(DefaultTreeViewLabelProvider.class);
		URL url = FileLocator.find(bundle, new org.eclipse.core.runtime.Path(
				"/icons/package.png"), null);
		ImageDescriptor imageDcr = ImageDescriptor.createFromURL(url);
		return imageDcr.createImage();
	}

	/**
	 * Gets the package members.
	 *
	 * @return the package members
	 */
	public List<ClassInfoNode> getPackageMembers() {
		
		if(null==packageMembers)
		{
			this.packageMembers= new ArrayList<ClassInfoNode>();
		}
		
		return packageMembers;
	}

	/**
	 * Gets the subpackages.
	 *
	 * @return the subpackages
	 */
	public Hashtable<String, PackageInfoNode> getSubpackages() {
		if(null==subpackages)
		{
			subpackages = new Hashtable<>();
		}
		
		return subpackages;
	}

	/**
	 * Sets the package members.
	 *
	 * @param packageMembers the new package members
	 */
	public void setPackageMembers(List<ClassInfoNode> packageMembers) {
		
		this.packageMembers = packageMembers;
	}

	/**
	 * Sets the subpackages.
	 *
	 * @param subpackages the subpackages
	 */
	public void setSubpackages(Hashtable<String, PackageInfoNode> subpackages) {
		this.subpackages = subpackages;
	}
	
}
