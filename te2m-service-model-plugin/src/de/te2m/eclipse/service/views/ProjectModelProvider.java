/*
* ProjectModelProvider.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.views;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.Viewer;

import de.te2m.api.ext.project.Project;
import de.te2m.eclipse.service.model.tree.service.ProjectNode;
import de.te2m.project.util.ProjectUtils;

/**
 * The Class ProjectModelProvider.
 * 
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class ProjectModelProvider{

	/**
	 * The instance.
	 */
	private static ProjectModelProvider instance = new ProjectModelProvider();

	/**
	 * Gets the single instance of ProjectModelProvider.
	 *
	 * @return single instance of ProjectModelProvider
	 */
	public static ProjectModelProvider getInstance() {
		return instance;
	}
	
	/**
	 * The views.
	 */
	private List<Viewer> views;

	/**
	 * The project.
	 */
	private ProjectNode project;

	/**
	 * Instantiates a new project model provider.
	 */
	private ProjectModelProvider() {
		// do nothing;
	}

	/**
	 * Creates the project node.
	 *
	 * @param p the p
	 * @return the project node
	 */
	public ProjectNode createProjectNode(Project p) {
		project = new ProjectNode(p);

		return getProjectNode();

	}

	/**
	 * Determines the project file.
	 * 
	 * @param container
	 *            the container
	 * @return the i file
	 */
	protected IFile determineProjectFile(IContainer container) {
		IFile file;

		file = container.getFile(new Path("/project.t4p"));
		return file;
	}

	
	/**
	 * Distribute refresh.
	 */
	public void distributeRefresh()
	{
		if(null==views)
		{
			return;
		}
		
		for (Iterator viewIterator = views.iterator(); viewIterator.hasNext();) {
			( (Viewer) viewIterator.next()).refresh();		
		}
		
	}

	
	/**
	 * Gets the project.
	 *
	 * @return the project
	 */
	public Project getProject() {
		return project.getProject();
	}

	/**
	 * Gets the project node.
	 *
	 * @return the project node
	 */
	public ProjectNode getProjectNode() {
		return project;
	}

	/**
	 * Load from container.
	 *
	 * @param container the container
	 */
	public void loadFromContainer(IContainer container) {

		IFile projectFile = determineProjectFile(container);

		try {
			if (null != projectFile && projectFile.exists()
					&& projectFile.isAccessible()
					&& null != projectFile.getContents()) {

				Project storedProject = ProjectUtils.getInstance().load(
						projectFile.getContents());

				project = new ProjectNode(storedProject);
				project.setStorageLocationHint(container.getName());
			}
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Register view.
	 *
	 * @param view the view
	 */
	public void registerView(Viewer view)
	{
		if(null==views)
		{
			views = new ArrayList<>();
		}
		views.add(view);
	}

	/**
	 * Save project to container.
	 *
	 * @param container the container
	 * @throws CoreException the core exception
	 */
	public void saveProjectToContainer(IContainer container)
			throws CoreException {
		project.setStorageLocationHint(container.getName());

		String marshalled = ProjectUtils.getInstance().toString(project.getProject());

		IFile file = determineProjectFile(container);

		if (null != marshalled) {

			InputStream instream = new ByteArrayInputStream(
					marshalled.getBytes());

			try {
				if (!file.exists()) {

					file.create(instream, true, null);

				} else {
					file.setContents(instream, true, true, null);
				}
			} catch (CoreException e) {

				e.printStackTrace();
				throwCoreException(e);
			}

		} else {
			throwCoreException("Error occured while storing the project");
		}

	}

	
	/**
	 * Throw core exception.
	 * 
	 * @param e
	 *            the e
	 * @throws CoreException
	 *             the core exception
	 */
	protected void throwCoreException(Exception e) throws CoreException {
		IStatus status = new Status(IStatus.ERROR, "service2code", IStatus.OK,
				e.getMessage(), e);
		throw new CoreException(status);
	}
	
	/**
	 * Throw core exception.
	 * 
	 * @param message
	 *            the message
	 * @throws CoreException
	 *             the core exception
	 */
	protected void throwCoreException(String message) throws CoreException {
		IStatus status = new Status(IStatus.ERROR, "service2code", IStatus.OK,
				message, null);
		throw new CoreException(status);
	}
}
