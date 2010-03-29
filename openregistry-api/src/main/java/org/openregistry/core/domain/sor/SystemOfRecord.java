package org.openregistry.core.domain.sor;

/**
 * Internal representation of a System of Record.
 *
 * @version $Revision$ $Date$
 * @since 0.1
 */
public interface SystemOfRecord {

    /**
     * The string representation of this SystemOfRecord.  CANNOT be NULL.  Can change over time.
     *
     * @return the string id (i.e. "registrar").
     */
    String getSorId();
}
