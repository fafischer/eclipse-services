/*
* PropertyUtilsTest.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.model.cfg;

/**
 * The Class PropertyUtilsTest.
 *
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class PropertyUtilsTest {
	
	/**
	 * Creates the jaxws action property.
	 *
	 * @param pg the pg
	 */
	private static void createJAXWSActionProperty(PropertyGroup pg) {
		PropertyCtnr ctnr;
		ctnr = new PropertyCtnr();
		
		ctnr.setName("jaxws.method.name");
		
		ctnr.setDescription("Method name for this webservice operation");
		
		ctnr.setMandantory(false);

		pg.addProperty(ctnr);
		
	}

	/**
	 * Creates the jaxws operation name property.
	 *
	 * @param pg the pg
	 */
	private static void createJAXWSOperationNameProperty(PropertyGroup pg) {
		PropertyCtnr ctnr;
		ctnr = new PropertyCtnr();
		
		ctnr.setName("jaxws.action");
		
		ctnr.setDescription("Action for this webservice operation");
		
		ctnr.setMandantory(false);

		pg.addProperty(ctnr);
	}

	
	/**
	 * Creates the operation jaxws properties.
	 */
	protected static void createOperationJAXWSProperties() {
		PropertyGroup pg = new PropertyGroup("JAX-WS","jaxws.service.opeation.enabled");
		pg.setDescription("JAX-WS related properties for service operations");
		
		createJAXWSOperationNameProperty(pg);
		
		createJAXWSActionProperty(pg);

		PropertyUtils.getInstance().save(pg, "D:\\t4p_operationJAXWS.cfg.xml");
	}

	
	/**
	 * Creates the operation rest properties.
	 */
	protected static void createOperationRESTProperties() {
		PropertyGroup pg = new PropertyGroup("JAX-RS","jaxrs.service.operation.enabled");
		pg.setDescription("REST related properties for service operations");
		createRESTMethodProperty(pg);
		createRESTPathProperty(pg);
		createRESTConsumesProperty(pg);
		createRESTProducesProperty(pg);
		PropertyUtils.getInstance().save(pg, "D:\\t4p_operationJAXRS.cfg.xml");
	}

	
	/**
	 * Creates the rest consumes property.
	 *
	 * @param pg the pg
	 */
	protected static void createRESTConsumesProperty(PropertyGroup pg) {
		PropertyCtnr ctnr = new PropertyCtnr();
		
		ctnr.setName("jaxrs.consumes");
		
		ctnr.setDescription("Accepted media types for this method");
		
		ctnr.setMandantory(false);

		ctnr.setValueListValues(new String[]{"application/json","application/xml"});
		
		pg.addProperty(ctnr);
	}

	
	/**
	 * Creates the rest method property.
	 *
	 * @param pg the pg
	 */
	protected static void createRESTMethodProperty(PropertyGroup pg) {
		PropertyCtnr ctnr = new PropertyCtnr();
		
		ctnr.setName("jaxrs.method");
		
		ctnr.setDescription("REST method used for accessing this method");
		
		ctnr.setMandantory(true);

		ctnr.setValueListValues(new String[]{"@POST","@GET","@PUT", "@DELETE"});
		
		pg.addProperty(ctnr);
	}

	/**
	 * Creates the rest path property.
	 *
	 * @param pg the pg
	 */
	protected static void createRESTPathProperty(PropertyGroup pg) {
		PropertyCtnr ctnr;
		ctnr = new PropertyCtnr();
		
		ctnr.setName("jaxrs.path");
		
		ctnr.setDescription("Path definition used for REST");
		
		ctnr.setMandantory(true);

		pg.addProperty(ctnr);
	}

	/**
	 * Creates the rest produces property.
	 *
	 * @param pg the pg
	 */
	protected static void createRESTProducesProperty(PropertyGroup pg) {
		PropertyCtnr ctnr = new PropertyCtnr();
		
		ctnr.setName("jaxrs.produces");
		
		ctnr.setDescription("Returned media types for this method");
		
		ctnr.setMandantory(false);

		ctnr.setValueListValues(new String[]{"application/json","application/xml","text/plain"});
		
		pg.addProperty(ctnr);
	}

	/**
	 * Creates the service ejb properties.
	 */
	protected static void createServiceEJBProperties() {
		PropertyGroup pg = new PropertyGroup("EJB","ejb.enabled");
		pg.setDescription("EJB related properties for services");
		createRESTPathProperty(pg);
		createRESTConsumesProperty(pg);
		createRESTProducesProperty(pg);
		PropertyUtils.getInstance().save(pg, "D:\\t4p_serviceEJB.cfg.xml");
	}

	/**
	 * Creates the service rest properties.
	 */
	protected static void createServiceRESTProperties() {
		PropertyGroup pg = new PropertyGroup("JAX-RS","jaxrs.service.enabled");
		pg.setDescription("REST related properties for services");
		createRESTPathProperty(pg);
		createRESTConsumesProperty(pg);
		createRESTProducesProperty(pg);
		PropertyUtils.getInstance().save(pg, "D:\\t4p_serviceJAXRS.cfg.xml");
	}

	/**
	 * Creates the stateless session bean property.
	 *
	 * @param pg the pg
	 */
	protected static void createStatelessSessionBeanProperty(PropertyGroup pg) {
		PropertyCtnr ctnr = new PropertyCtnr();
		
		ctnr.setName("ejb.pattern");
		
		ctnr.setDescription("EJB bean pattern");
		
		ctnr.setMandantory(false);
		
		ctnr.setValueListValues(new String[]{"@stateless"});
		
		pg.addProperty(ctnr);
	}

	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		
		try {
			Thread.sleep(100000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		createOperationRESTProperties();
		createServiceRESTProperties();
		createOperationJAXWSProperties();
	}

}
