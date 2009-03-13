package org.openregistry.core.service;

/**
 * Represents an error from when the system attempted to validate the object.
 * <p>
 * Exists so we're not tied to the JaValid or Spring APIs.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface ValidationError {

    /**
     * The field from which there was an error.  If its null, that means global error message.
     *
     * @return the field, or null, if its a global error message.
     */
    String getField();

    /**
     * The list of arguments to supply to the message for rendering purposes.
     * @return the list of arguments to apply to the rendered message.
     */
    Object[] getArguments();

    /**
     * The code description of the message.
     * @return the code of the message.  CANNOT be null.
     */
    String getCode();
}
