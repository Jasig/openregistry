package org.openregistry.core.service.identifier;

import org.openregistry.core.domain.Person;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface IdentifierAssigner {

    /**
     * Adds the identifier to the particular person.  This is not necessarily saved until an explicit save call is made
     * so identifier creation systems should not rely on the identifier table in OR to check whether a value
     * was used or not.
     *
     * @param person the person to add the identifier to.
     */
    void addIdentifierTo(Person person);
}
