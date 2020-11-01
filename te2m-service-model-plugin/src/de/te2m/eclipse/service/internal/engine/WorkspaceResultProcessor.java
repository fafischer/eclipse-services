/*
* WorkspaceResultProcessor.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.internal.engine;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;

import de.te2m.tools.generator.engine.ELUtils;
import de.te2m.tools.generator.engine.GeneratorResultProcessor;
import de.te2m.report.api.model.GeneratorTarget;

/**
 * The Class WorkspaceResultProcessor.
 * 
 * @author ffischer
 */
public class WorkspaceResultProcessor implements GeneratorResultProcessor {

	/**
	 * The target container.
	 */
	IContainer targetContainer;

	/**
	 * Instantiates a new workspace result processor.
	 * 
	 * @param container
	 *            the container
	 */
	public WorkspaceResultProcessor(IContainer container) {

		targetContainer = container;
	}

	/**
	 * Creates the file.
	 * 
	 * @param fileNameToCreate
	 *            the file name to create
	 * @param instream
	 *            the instream
	 */
	public void createFile(String fileNameToCreate, InputStream instream) {

		String path = null;

		if (fileNameToCreate.lastIndexOf("/") != -1) {
			path=fileNameToCreate.substring(0, fileNameToCreate.lastIndexOf("/"));
			path2IFolder(path);
		}

		createFileInternal(instream, fileNameToCreate);

		//
	}

	/**
	 * Creates the file internal.
	 * 
	 * @param instream
	 *            the instream
	 * @param fileNameToCreate
	 *            the file name to create
	 */
	private void createFileInternal(InputStream instream,
			String fileNameToCreate) {
		IFile file;

		try {
			file = targetContainer.getFile(new Path(fileNameToCreate));

			if (!file.exists()) {

				file.create(instream, true, null);

			} else {
				file.setContents(instream, true, true, null);
			}
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Creates the source file.
	 * 
	 * @param filename
	 *            the filename
	 * @param instream
	 *            the instream
	 */
	public void createSourceFile(String filename, InputStream instream) {
		createSourceFile(filename, null, instream, null);
	}

	/**
	 * Creates the source file.
	 * 
	 * @param filename
	 *            the filename
	 * @param packagename
	 *            the packagename
	 * @param instream
	 *            the instream
	 * @param baseDir
	 *            the base dir
	 */
	public void createSourceFile(String filename, String packagename,
			InputStream instream, String baseDir) {

		package2IFolder(packagename, baseDir);

		String fileNameToCreate = baseDir + "/" + packagename.replace('.', '/')
				+ "/" + filename;

		createFileInternal(instream, fileNameToCreate);

		//
	}

	/**
	 * Package2 i folder.
	 * 
	 * @param pkgName
	 *            the pkg name
	 * @param baseDir
	 *            the base dir
	 */
	public void package2IFolder(String pkgName, String baseDir) {
		try {

			IFolder f1 = targetContainer.getFolder(new Path(baseDir));

			if (!f1.exists())
				f1.create(true, true, null);

			path2IFolder(baseDir + "/" + pkgName.replace('.', '/')); //$NON-NLS-1$
		} catch (CoreException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Path2 i folder.
	 * 
	 * @param path
	 *            the path
	 */
	public void path2IFolder(String path) {
		try {
			StringTokenizer st = new StringTokenizer(path, "/"); //$NON-NLS-1$

			String baseDir = null;

			while (st.hasMoreTokens()) {
				String dummy = st.nextToken();
				if (baseDir != null)
					baseDir = baseDir + "/" + dummy; //$NON-NLS-1$
				else
					baseDir = dummy;
				IFolder f2 = targetContainer.getFolder(new Path(baseDir));
				if (!f2.exists())
					f2.create(true, true, null);
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.te2m.tools.generator.engine.GeneratorResultProcessor#postProcess()
	 */
	public void postProcess() {
		//
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.te2m.tools.generator.engine.GeneratorResultProcessor#preProcess()
	 */
	public void preProcess() {
		//
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.te2m.tools.generator.engine.GeneratorResultProcessor#
	 * processGeneratorResult(byte[],
	 * de.te2m.tools.generator.model.GeneratorTarget, java.util.HashMap)
	 */
	public Object processGeneratorResult(byte[] generated,
			GeneratorTarget target, Map params) {
		// try {
		String name = target.getName();
		if (null == name || name.trim().length() == 0) {
			name = UUID.randomUUID().toString();

		}
		String fname = (String) ELUtils.evalExpression(params, name);

		System.out.println(name + " --> " + fname);
		ByteArrayInputStream inStream = new ByteArrayInputStream(generated);
		createFile(fname, inStream);

		// } catch (IOException ex) {
		// Logger.getLogger(WorkspaceResultProcessor.class.getName()).log(Level.SEVERE,
		// null, ex);
		// }
		return null;
	}



}
