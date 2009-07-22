package org.openregistry.security;

/**
 * Represents a System of Record that we may have permissions for.
 *
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface SystemOfRecord {

    /**
     * The name of the system of record.  Generally its unique identifier.
     *
     * @return the name. CANNOT be NULL.
     */
    String getName();
}
