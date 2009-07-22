package org.openregistry.security;

import java.util.Set;

/**
 * Represents a user in the system.  Exposes information about the user that may be needed within the system.
 *
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface User {

    /**
     * Returns the set of allowed System of Records.
     *
     * @return the allowed set of System of Records.  CANNOT be NULL.  CAN be EMPTY.
     */
    Set<SystemOfRecord> getSystemOfRecords();
            

}
