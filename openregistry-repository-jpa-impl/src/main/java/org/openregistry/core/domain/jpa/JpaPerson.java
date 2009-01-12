package org.openregistry.core.domain.jpa;

import org.openregistry.core.domain.internal.Entity;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.Role;
import org.openregistry.core.domain.PersonIdentifiers;

import java.util.Collection;

/**
 * Person entity mapped to a persistence store with JPA annotations.
 *
 * @since 1.0
 *        TODO: add all the properties, dependencies and map to OR DB with JPA annotations
 */
public class JpaPerson extends Entity implements Person {

    /**
     * @see org.openregistry.core.domain.Person#getRoles()
     */
    public Collection<Role> getRoles() {
        return null;
    }

    /**
     * @see org.openregistry.core.domain.Person#addRole(org.openregistry.core.domain.Role)
     */
    public void addRole(Role role) {

    }

    /**
     * @see org.openregistry.core.domain.Person#getIdentifiers()
     */
    public PersonIdentifiers getIdentifiers() {
        return null;
    }
}
