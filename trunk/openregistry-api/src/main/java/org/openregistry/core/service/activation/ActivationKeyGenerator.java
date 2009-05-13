package org.openregistry.core.service.activation;

/**
 * Generates an activation key for an identifier.
 *
 * @author Nancy Mond
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface ActivationKeyGenerator {

    /**
     * Generates the next value as a String.
     *
     * @return the next value as a string.
     */
    String generateNextString();

}