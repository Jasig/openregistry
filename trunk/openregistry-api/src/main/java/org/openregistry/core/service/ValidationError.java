package org.openregistry.core.service;

/**
 * 
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface ValidationError {

    String getField();

    Object[] getArguments();

    String getCode();
}
