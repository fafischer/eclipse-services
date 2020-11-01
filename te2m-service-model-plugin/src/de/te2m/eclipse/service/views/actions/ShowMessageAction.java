/*
* ShowMessageAction.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.views.actions;

import org.eclipse.jface.viewers.TreeViewer;

/**
 * The Class ShowMessageAction.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class ShowMessageAction extends AbstractTreeAction
{
	
	/**
	 * The message.
	 */
	private String message;
	
	/**
	 * Instantiates a new show message action.
	 *
	 * @param v the v
	 */
	public ShowMessageAction(TreeViewer v)
	{
		super();
		myViewer=v;
		
	}
	
	/**
	 * Instantiates a new show message action.
	 *
	 * @param v the v
	 * @param msg the msg
	 */
	public ShowMessageAction(TreeViewer v, String msg)
	{
		super();
		myViewer=v;
		this.message=msg;
		
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run()
	{
			showMessage(message);			
	}
	
	/**
	 * Sets the message.
	 *
	 * @param msg the new message
	 */
	public void setMessage(String msg)
	{
		this.message=msg;
	}
}