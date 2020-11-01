/*
* WSDLCrawler.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.internal.engine.reader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.wsdl.Definition;
import javax.wsdl.Fault;
import javax.wsdl.Input;
import javax.wsdl.Message;
import javax.wsdl.Output;
import javax.wsdl.Port;
import javax.wsdl.WSDLException;
import javax.wsdl.factory.WSDLFactory;
import javax.wsdl.xml.WSDLReader;

import org.xml.sax.InputSource;

import de.te2m.api.ext.core.IdObject.Attributes.Entry;
import de.te2m.api.ext.project.bo.ClassInfo;
import de.te2m.api.ext.project.bo.Operation;
import de.te2m.api.ext.project.bo.ParameterInfo;
import de.te2m.api.ext.project.service.Service;
import de.te2m.eclipse.service.model.tree.service.ProjectNode;
import de.te2m.eclipse.service.views.ProjectModelProvider;
import de.te2m.project.util.ClassInfoConstants;
import de.te2m.project.util.ProjectUtils;


/**
 * The Class WSDLCrawler.
 * 
 * @author ffischer
 * @version 1.0
 * @since 1.0
 */
public class WSDLCrawler implements ServiceCrawler, ClassInfoConstants{



	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		if (args.length == 2) {
			String name = args[0];
			String fname = args[1];
			ServiceCrawler wCr = new WSDLCrawler(name, fname);
			wCr.parseServices();
		} else {
			System.out.println("Usage: WSDLCrawler <name> <file name>");
		}
	}

	/**
	 * The wsdl input stream.
	 */
	private InputStream wsdlInputStream;

	/**
	 * The name.
	 */
	private String name;

	/**
	 * Instantiates a new wSDL crawler.
	 * 
	 * @param sName
	 *            the s name
	 * @param inStream
	 *            the in stream
	 */
	public WSDLCrawler(String sName, InputStream inStream) {

		name = sName;

		wsdlInputStream = inStream;

	}

	/**
	 * Instantiates a new wSDL crawler.
	 * 
	 * @param sName
	 *            the s name
	 * @param fileName
	 *            the file name
	 */
	public WSDLCrawler(String sName, String fileName) {

		name = sName;
		if (null != fileName) {
			try {
				wsdlInputStream = new FileInputStream(fileName);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * Match a existing ClassInfo based on the QName of the provided message.
	 *
	 * @param msg the msg
	 * @return the class info
	 */
	private ClassInfo determineParamClass(Message msg) {
		
		String signature = msg.getQName().getLocalPart();
		
		ProjectNode currentProject = ProjectModelProvider.getInstance().getProjectNode();
		
		
		
		ClassInfo nfo = null;
		
		for(ClassInfo ci : currentProject.getClassInfoList())
		{
			String local= msg.getQName().getLocalPart();
			String namespace = msg.getQName().getNamespaceURI();
			if(null!=local&&local.equals(ProjectUtils.getAttribute(ci,ATTRIBUTE_NAME_Q_NAME_LOCAL_PART)))
			{
				if(null!=namespace)
				{
					if(namespace.equals(ProjectUtils.getAttribute(ci,ATTRIBUTE_NAME_Q_NAME_NAME_SPACE)))
					{
						return ci;
					}
				}
				else {
					if(null==ProjectUtils.getAttribute(ci,ATTRIBUTE_NAME_Q_NAME_NAME_SPACE))
					{
						return ci;
					}
				}
			}
		}
		
		if(null==nfo)
		{
			nfo = new ClassInfo();
			nfo.setId(UUID.randomUUID().toString());
			nfo.setName(signature);
			Entry entry =  new Entry();
			entry.setKey(ATTRIBUTE_NAME_Q_NAME_LOCAL_PART);
			entry.setValue(signature);
			nfo.getAttributes().getEntries().add(entry);
			
			entry =  new Entry();
			entry.setKey(ATTRIBUTE_NAME_Q_NAME_NAME_SPACE);
			entry.setValue(msg.getQName().getNamespaceURI());
			nfo.getAttributes().getEntries().add(entry);

			currentProject.validateClassInfo(nfo);
			
		}
		
		return nfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.te2m.eclipse.service.internal.engine.reader.ServiceCrawler#parseService
	 * ()
	 */
	@Override
	public List<Service> parseServices() {

		List<Service> serviceList = new ArrayList<Service>();

		try {
			Definition def = readWSDL();
			if (null != def) {
				System.out.println("BaseURI = " + def.getDocumentBaseURI());
				// sd.addAttribute(key, value);

				Map services = def.getServices();

				for (Object serviceObject : services.values()) {

					javax.wsdl.Service serv = (javax.wsdl.Service) serviceObject;

					Service sd = null;
					sd = new Service();
					sd.setId(UUID.randomUUID().toString());
					sd.setName(serv.getQName().getLocalPart());
					sd.setDescription(serv.getDocumentationElement() != null ? serv
							.getDocumentationElement().getNodeValue() : null);

					System.out.println("--- " + serv.getQName().getLocalPart());
					Map portMap = serv.getPorts();

					for (Object pto : portMap.values()) {
						List opl = ((Port) pto).getBinding().getPortType()
								.getOperations();
						for (Object opObject : opl) {
							Operation od = new Operation();
							javax.wsdl.Operation wsdlOp = (javax.wsdl.Operation) opObject;
							System.out.println("###> " + wsdlOp.getName());
							od.setName(wsdlOp.getName());
							od.setDescription(wsdlOp.getDocumentationElement() != null ? wsdlOp
									.getDocumentationElement().getTextContent()
									: null);
							od.setId(UUID.randomUUID().toString());
							
							Input inp = wsdlOp.getInput();
							if (null != inp) {

								ParameterInfo cd = new ParameterInfo();
								cd.setId(UUID.randomUUID().toString());
								Message msg = inp.getMessage();

								if (null != msg) {
									cd.setName(msg.getQName()
											.getLocalPart());
									ProjectUtils.setAttribute(cd,ATTRIBUTE_NAME_Q_NAME_NAME_SPACE, msg.getQName().getNamespaceURI());
									ProjectUtils.setAttribute(cd,ATTRIBUTE_NAME_Q_NAME_LOCAL_PART, msg.getQName().getLocalPart());
									cd.setDescription(msg
											.getDocumentationElement() != null ? msg
											.getDocumentationElement()
											.getTextContent() : null);
									
								} else {
									cd.setName(inp.getName());
									cd.setDescription(inp
											.getDocumentationElement() != null ? inp
											.getDocumentationElement()
											.getTextContent() : null);
								}
								cd.setParamClass(determineParamClass(msg));
								od.getParameters().add(cd);
							}
							Output outp = wsdlOp.getOutput();
							if (null != outp) {
								Message msg = outp.getMessage();
								
								ParameterInfo cd = new ParameterInfo();
								cd.setId(UUID.randomUUID().toString());
								

								if (null != msg) {
									ProjectUtils.setAttribute(cd,ATTRIBUTE_NAME_Q_NAME_NAME_SPACE, msg.getQName().getNamespaceURI());
									ProjectUtils.setAttribute(cd,ATTRIBUTE_NAME_Q_NAME_LOCAL_PART, msg.getQName().getLocalPart());
									cd.setName(msg.getQName()
											.getLocalPart());
									cd.setDescription(msg
											.getDocumentationElement() != null ? msg
											.getDocumentationElement()
											.getTextContent() : null);
								} else {
									cd.setName(outp.getName());

									cd.setDescription(outp
											.getDocumentationElement() != null ? inp
											.getDocumentationElement()
											.getTextContent() : null);
								}
								cd.setParamClass(determineParamClass(msg));
								od.setReturnValue(cd);
							}

							Map faults = wsdlOp.getFaults();
							{

								for (Object key : faults.keySet()) {
									
									System.out.println("<!> "
											+ key
											+ " = "
											+ ((Fault) faults.get(key))
													.toString());
								}
							}

							sd.getOperations().add(od);
						}
					}

					serviceList.add(sd);

				}

			}

		} catch (WSDLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return serviceList;
	}

	/**
	 * Read wsdl.
	 * 
	 * @return the definition
	 * @throws WSDLException
	 *             the wSDL exception
	 */
	public Definition readWSDL() throws WSDLException {
		if (null == wsdlInputStream) {
			return null;
		}
		WSDLReader reader = WSDLFactory.newInstance().newWSDLReader();

		Definition wsdlDefinition;

		InputSource inputSource = new InputSource(wsdlInputStream);
		wsdlDefinition = reader.readWSDL(null, inputSource);
		return wsdlDefinition;

	}

}
