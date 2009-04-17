package org.openregistry.core.service.identifier;

import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.sor.SorPerson;

/**
 * Assigns an identifier to a person based the data provided from the system of record.  Its the job of the identifier
 * assigner to determine if it needs to add an identifier for a person or not.
 *
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
     * @param sorPerson the original SoR person.
     * @param person the person to add the identifier to.
     */
    void addIdentifierTo(SorPerson sorPerson, Person person);

    /**
     * Returns the type of identifier that this assigner can assign.
     * @return identifer type
     */
    String getIdentifierType();
}
