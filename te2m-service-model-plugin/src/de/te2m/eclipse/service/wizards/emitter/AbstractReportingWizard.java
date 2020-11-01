/*
* AbstractReportingWizard.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.wizards.emitter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.eclipse.core.resources.IContainer;
import org.eclipse.jface.wizard.Wizard;

import de.te2m.api.ext.project.Project;
import de.te2m.api.ext.project.bo.ClassInfo;
import de.te2m.api.ext.project.bo.Operation;
import de.te2m.api.ext.project.bo.ParameterInfo;
import de.te2m.api.ext.project.service.Service;
import de.te2m.api.ext.project.service.SystemInfo;
import de.te2m.eclipse.service.ServiceModelPlugin;
import de.te2m.eclipse.service.internal.engine.WorkspaceResultProcessor;
import de.te2m.eclipse.service.preferences.GeneratorPreferenceConstants;
import de.te2m.eclipse.service.preferences.MainPreferenceConstants;
import de.te2m.eclipse.service.views.ProjectModelProvider;
import de.te2m.project.util.ProjectUtils;
import de.te2m.tools.generator.engine.SimpleGenerator;
import de.te2m.tools.generator.engine.impl.ChildReportProcessor;
import de.te2m.tools.generator.engine.impl.freemarker.FreeMarkerProcessor;
import de.te2m.report.api.model.GeneratorMetaData;
import de.te2m.report.api.model.Report;
import de.te2m.report.api.model.dev.OperationDescriptor;
import de.te2m.report.api.model.dev.java.JavaClassDescriptor;
import de.te2m.report.api.model.dev.service.ServiceDescriptor;
import de.te2m.tools.templates.pom.PomInfo;

/**
 * The Class AbstractReportingWizard.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractReportingWizard extends Wizard {

	/**
	 * Instantiates a new abstract reporting wizard.
	 */
	public AbstractReportingWizard() {
		super();
	}

	/**
	 * Creates the java class descriptor.
	 *
	 * @param pi
	 *            the pi
	 * @return the java class descriptor
	 */
	protected JavaClassDescriptor createJavaClassDescriptor(ParameterInfo pi) {

		ClassInfo ci = null;

		if (null != pi) {
			ci = (ClassInfo) pi.getParamClass();
			
			if(null==ci)
			{
				ci = new ClassInfo();
			}
			
			Properties p = new Properties();

			if (null != ci.getAttributes()) {
				p=ProjectUtils.mapAttributesToProperties(ci.getAttributes());
			}
			
			JavaClassDescriptor paramCD = JavaClassDescriptor
					.builder()
					.withName(pi.getName())
					.withDescription(
							ci.getDescription() != null ? ci.getDescription() : "")
					.withType(
							ci != null && ci.getName() != null ? ci.getName() : "")
					.withPackage(
							ci != null && ci.getPkg() != null ? ci.getPkg() : "")
					.withProperties(p)
					.build();
			
			return paramCD;
		}
		return null;
	}

	/**
	 * Generate.
	 *
	 * @param container the container
	 * @param report the report
	 * @param params the params
	 */
	protected void generate(IContainer container, Report report, HashMap<String, Object> params) {
		WorkspaceResultProcessor wrp = new WorkspaceResultProcessor(container);
		FreeMarkerProcessor fmp = new FreeMarkerProcessor();
		SimpleGenerator g = new SimpleGenerator(report, fmp, wrp);
		g.registerProcessor(new ChildReportProcessor());


		g.generate(params);
	}

	/**
	 * Generate.
	 *
	 * @param container the container
	 * @param packName the pack name
	 * @param report the report
	 * @param params the params
	 */
	protected void generate(IContainer container, String packName,
			Report report, HashMap<String, Object> params) {
		generate(container, report, params);
		
		rememberPackageName(packName);
	}

	/**
	 * Prepares the meta data.
	 *
	 * @param params the params
	 * @param dUnit the d unit
	 * @param basePackage the base package
	 */
	protected void prepareMetaData(HashMap<String, Object> params, String dUnit, String basePackage) {
		GeneratorMetaData info = new GeneratorMetaData();
		String user = ServiceModelPlugin.getDefault().getPreferenceStore()
				.getString(MainPreferenceConstants.PREFERENCE_USER_NAME);
		info.setAuthor(user != null ? user : "");
		String cInfo = ServiceModelPlugin.getDefault().getPreferenceStore()
				.getString(MainPreferenceConstants.PREFERENCE_LEGAL_NOTICE);
		info.setCopyrightInfo(cInfo != null ? cInfo : "");
		info.setBasePackage(basePackage != null ? basePackage : "");
		String version = ServiceModelPlugin
				.getDefault()
				.getPreferenceStore()
				.getString(
						GeneratorPreferenceConstants.PREFERENCE_DEFAULT_VERSION);
		info.setVersion(version != null ? version : "");
		info.setDeploymentUnit(dUnit != null ? dUnit : "");
		params.put("info", info);
	}

	/**
	 * Prepares the pom info.
	 *
	 * @param params the params
	 */
	protected void preparePOMInfo(HashMap<String, Object> params) {
		PomInfo pi = new PomInfo();
		pi.setGroupID("group.id");
		pi.setArtifactID("arifactID");

		params.put("pomInfo", pi);
	}

	/**
	 * Prepares the project.
	 *
	 * @param params the params
	 * @param pkg the pkg
	 */
	protected void prepareProject(HashMap<String, Object> params, String pkg) {
		Project currentProject = ProjectModelProvider.getInstance()
				.getProject();
		List<ServiceDescriptor> boList = transformProjectServices(
				currentProject, pkg);
		List<JavaClassDescriptor> objList = transformProjectObjects(
				currentProject, pkg);
		params.put("boList", boList);
		params.put("objList", objList);
		params.put("project", ProjectModelProvider.getInstance().getProject());
	}

	/**
	 * Prepares the report generation params.
	 *
	 * @param dUnit the d unit
	 * @param pkg the pkg
	 * @return the hash map
	 */
	protected HashMap<String, Object> prepareReportGenerationParams(
			String dUnit, String pkg) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		prepareProject(params, pkg);
		prepareMetaData(params, dUnit, pkg);
		preparePOMInfo(params);

		return params;
	}

	/**
	 * Remember package name.
	 *
	 * @param packName the pack name
	 */
	protected void rememberPackageName(String packName) {
		ServiceModelPlugin
				.getDefault()
				.getPreferenceStore()
				.setValue(
						GeneratorPreferenceConstants.PREFERENCE_RECENTLY_USED_PACKAGE,
						packName);
	}

	/**
	 * Transform class info.
	 *
	 * @param ci the ci
	 * @return the java class descriptor
	 */
	protected JavaClassDescriptor transformClassInfo(ClassInfo ci) {

		Properties p = new Properties();

		if (null != ci.getAttributes()) {
			p=ProjectUtils.mapAttributesToProperties(ci.getAttributes());
		}
		
		JavaClassDescriptor paramCD = JavaClassDescriptor
				.builder()
				.withName(ci.getName())
				.withDescription(
						ci.getDescription() != null ? ci.getDescription() : "")
				.withType(
						ci != null && ci.getName() != null ? ci.getName() : "")
				.withPackage(
						ci != null && ci.getPkg() != null ? ci.getPkg() : "")
				.withProperties(p)
				.build();
		return paramCD;
	}

	/**
	 * Transform project objects.
	 *
	 * @param currentProject the current project
	 * @param pkg the pkg
	 * @return the list
	 */
	protected List<JavaClassDescriptor> transformProjectObjects(
			Project currentProject, String pkg) {
		List<JavaClassDescriptor> objList = new ArrayList<JavaClassDescriptor>();

		if (null != currentProject && null != currentProject.getObjects()) {
			for (ClassInfo ci : currentProject.getObjects()) {
				if (ci.isGenerate())
					objList.add(transformClassInfo(ci));
			}
		}
		return objList;
	}

	/**
	 * Transform project services.
	 *
	 * @param currentProject the current project
	 * @param pkg the pkg
	 * @return the list
	 */
	protected List<ServiceDescriptor> transformProjectServices(
			Project currentProject, String pkg) {
		List<ServiceDescriptor> boList = new ArrayList<ServiceDescriptor>();

		List<SystemInfo> systemList = currentProject.getSystems();

		for (SystemInfo si : systemList) {
			if (null != si.getServices()) {
				for (Service service : si.getServices()) {
					boList.add(transformService(service, pkg, null));
				}
			}
		}
		return boList;
	}

	/**
	 * Transform service.
	 *
	 * @param service the service
	 * @param pkg the pkg
	 * @param deploymentUnit the deployment unit
	 * @return the service descriptor
	 */
	protected ServiceDescriptor transformService(Service service, String pkg, String deploymentUnit) {

		List<JavaClassDescriptor> configs = new ArrayList<JavaClassDescriptor>();

		if (null != service.getConfigurations()) {

			for (ParameterInfo pi : service.getConfigurations()) {
				JavaClassDescriptor paramCD = JavaClassDescriptor
						.builder()
						.withName(pi.getName())						
						.withDescription(
								null != pi.getDescription() ? pi
										.getDescription() : "").build();
				configs.add(paramCD);
			}
		}

		List<OperationDescriptor> ops = new ArrayList<OperationDescriptor>();

		for (Operation op : service.getOperations()) {
			OperationDescriptor od = OperationDescriptor
					.builder()
					.withName(op.getName())
					.withDescription(
							null != op.getDescription() ? op.getDescription()
									: "").build();

			// parameter & Return values
			for (ParameterInfo pi : op.getParameters()) {

				JavaClassDescriptor paramCD = createJavaClassDescriptor(pi);

				// paramCD.setProperties(current.getProperties());
				od.addParameter(paramCD);

			}

			if (null != op.getReturnValue()) {
				od.setReturnValue(createJavaClassDescriptor(op.getReturnValue()));
			}

			ops.add(od);
		}

		ServiceDescriptor jcd = ServiceDescriptor
				.builder()
				.withName(service.getName())
				.withDescription(
						service.getDescription() != null ? service
								.getDescription() : "")
				.withDeploymentUnit(deploymentUnit)
				.withPackage(pkg != null ? pkg : "")
				.withProperties(
						ProjectUtils.mapAttributesToProperties(service
								.getAttributes())).withConfigurations(configs)
				.withOperations(ops).build();

		return jcd;
	}
}