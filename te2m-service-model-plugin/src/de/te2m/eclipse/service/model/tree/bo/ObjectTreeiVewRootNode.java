/*
* ObjectTreeiVewRootNode.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.model.tree.bo;

import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.ui.ISharedImages;

import de.te2m.api.ext.project.bo.ClassInfo;
import de.te2m.eclipse.service.model.tree.TreeNode;
import de.te2m.eclipse.service.model.tree.service.ProjectNode;
import de.te2m.eclipse.service.views.ProjectModelProvider;
import de.te2m.project.util.ProjectUtils;


/**
 * The Class ObjectTreeiVewRootNode.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class ObjectTreeiVewRootNode extends PackageInfoNode
{


    /**
     * Instantiates a new root node.
     *
     * @param name the name
     */
    public ObjectTreeiVewRootNode(String name)
    {
        super(name);
    }


    /* (non-Javadoc)
	 * @see de.te2m.eclipse.service.model.tree.AbstractParentNode#getChildrenAsList()
	 */
	@Override
	public List<TreeNode> getChildrenAsList() {

		ProjectNode pn = ProjectModelProvider.getInstance().getProjectNode();
		
		// reset
		
		setSubpackages(null);

		setPackageMembers(null);
		
		// rebuild
		
		if(null!=pn)
		{
			if(null!=pn.getClassInfoList())
			{
				for (Iterator iterator = pn.getClassInfoList().iterator(); iterator.hasNext();) {

					ClassInfo ci = (ClassInfo)iterator.next();
					
					if(ProjectUtils.getSignature(ci).contains("."))
					{
						
						PackageInfoNode currentParent = this;
						
						StringTokenizer st = new StringTokenizer(ProjectUtils.getSignature(ci), ".:");
						
						while(st.hasMoreTokens())
						{
							String newtToken = st.nextToken();
							
							if(st.hasMoreTokens())
							{
								PackageInfoNode pin = currentParent.getSubpackages().get(newtToken);
								
								if(null==pin)
								{
									pin=new PackageInfoNode(newtToken);
									currentParent.getSubpackages().put(newtToken, pin);
								}
								currentParent=pin;
							}
							else 
							{
								currentParent.getPackageMembers().add(new ClassInfoNode(ci));
							}
						}
					}
					else
					{
						getPackageMembers().add(new ClassInfoNode(ci));
					}
					
					
				}
				
			}
		}
		return super.getChildrenAsList();
	}

    /* (non-Javadoc)
     * @see de.te2m.eclipse.service.model.tree.TreeParentNode#getImageDescriptor()
     */
    public String getImageDescriptor()
    {

            return ISharedImages.IMG_OBJ_ELEMENT;
    }

	/* (non-Javadoc)
     * @see de.te2m.eclipse.service.model.tree.TreeNode#toString()
     */
    public String toString()
    {
        return getName();
    }
    
    

}
