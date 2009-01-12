package org.openregistry.core.domain;

import java.util.Set;

/**
 * Entity representing canonical persons and their identity in the Open Registry system
 *
 * @since 1.0
 *        TODO: define all the public APIs pertaining to Person's exposed properties and relationships with other entities
 */
public interface Person {

    /**
     * Get a collection of roles associated with this person
     *
     * @return a set of rolles the person currently has or an empty set.
     *         Note to implementers: this method should never return null.
     */
    Set<Role> getRoles();

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

}
