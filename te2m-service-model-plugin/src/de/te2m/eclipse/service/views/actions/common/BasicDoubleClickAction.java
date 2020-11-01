/*
* BasicDoubleClickAction.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.views.actions.common;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;

import de.te2m.eclipse.service.views.actions.AbstractTreeAction;

/**
 * The Class BasicDoubleClickAction.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class BasicDoubleClickAction extends AbstractTreeAction
{
	
	/**
	 * Instantiates a new basic double click action.
	 *
	 * @param v the v
	 */
	public BasicDoubleClickAction(TreeViewer v)
	{
		super();
		myViewer=v;
	}
	
	/**
	 * On double click.
	 *
	 * @param obj the obj
	 */
	public void onDoubleClick(Object obj)
	{
		showMessage("Double-click detected on "+obj.toString());	
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		ISelection selection = myViewer.getSelection();
		Object obj = ((IStructuredSelection)selection).getFirstElement();
		onDoubleClick(obj);
	}
}