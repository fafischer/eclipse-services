/*
* HasOperations.java
*   
* Copyright 2009 - 2015 Frank Fischer (email: frank@te2m.de)
*
* This file is part of the te2m-service-model-plugin project which is a sub project of temtools 
* (http://temtools.sf.net).
* 
*/
package de.te2m.eclipse.service.model.tree.service;

import java.util.List;

import de.te2m.api.ext.project.bo.Operation;

/**
 * The Interface HasOperations.
 *
 * @author ffischer
 */
public interface HasOperations {

   /**
    * Adds the operation.
    *
    * @param op
    *            the op
    */
   void addOperation(Operation op);

   /**
    * Gets the operations.
    *
    * @return the operations
    */
   List<Operation> getOperations();

   /**
    * Sets the operations.
    *
    * @param operations
    *            the new operations
    */
   void setOperations(List<Operation> operations);

}
