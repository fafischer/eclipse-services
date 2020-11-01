/*
* AbstractTreeAction.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.views.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TreeViewer;

/**
 * The Class AbstractTreeAction.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractTreeAction extends Action {

	/**
	 * The my viewer.
	 */
	protected TreeViewer myViewer;
	
	/**
	 * The header.
	 */
	protected String header;

	/**
	 * Instantiates a new abstract tree action.
	 */
	public AbstractTreeAction() {
		super();
	}

	/**
	 * Instantiates a new abstract tree action.
	 *
	 * @param text the text
	 */
	public AbstractTreeAction(String text) {
		super(text);
	}

	/**
	 * Instantiates a new abstract tree action.
	 *
	 * @param text the text
	 * @param image the image
	 */
	public AbstractTreeAction(String text, ImageDescriptor image) {
		super(text, image);
	}

	/**
	 * Instantiates a new abstract tree action.
	 *
	 * @param text the text
	 * @param style the style
	 */
	public AbstractTreeAction(String text, int style) {
		super(text, style);
	}

	/**
	 * Sets the response dialog header.
	 *
	 * @param hdr the new response dialog header
	 */
	public void setResponseDialogHeader(String hdr) {
		this.header=hdr;
	}

	/**
	 * Show message.
	 *
	 * @param msg the msg
	 */
	protected void showMessage(String msg) {
		MessageDialog.openInformation(
			myViewer.getControl().getShell(),
			header,
			msg);
	}

}