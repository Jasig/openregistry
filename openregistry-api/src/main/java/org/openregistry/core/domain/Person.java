package org.openregistry.core.domain;

import java.util.Set;
import java.util.Collection;
import java.io.Serializable;

/**
 * Entity representing canonical persons and their identity in the Open Registry system.
 *
 * @since 1.0
 *        TODO: define all the public APIs pertaining to Person's exposed properties and relationships with other entities
 */
public interface Person extends Serializable {

    Long getId();

    /**
     * Get a collection of roles associated with this person
     *
     * @return a collection of rolles the person currently has or an empty collection.
     *         Note to implementers: this method should never return null.
     */
    Collection<Role> getRoles();

    /**
     * Add a new role to a collection of role this person holds.
     * @param role new role to add to this person instance.
     */
    void addRole(Role role);

    /**
     * Get identifiers associated with this person.
     *
     * @return PersonIdentifiers. Please note that this method should never return null.
     */
    PersonIdentifiers getIdentifiers();

    String getFirstName();

    String getLastName();

    String getMiddleName();

    String getFullName();
}
