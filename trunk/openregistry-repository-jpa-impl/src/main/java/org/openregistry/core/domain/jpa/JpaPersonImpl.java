package org.openregistry.core.domain.jpa;

import org.openregistry.core.domain.internal.Entity;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.Role;
import org.openregistry.core.domain.Identifier;

import java.util.Collection;
import java.util.List;

/**
 * Person entity mapped to a persistence store with JPA annotations.
 *
 * @since 1.0
 *        TODO: add all the properties, dependencies and map to OR DB with JPA annotations
 */
public class JpaPersonImpl extends Entity implements Person {

    private Collection<Role> roles;

    private List<Identifier> identifiers;

    private String firstName;

    private String lastName;

    private String middleName;

    public Role addRole() {
        final JpaRoleImpl jpaRole = new JpaRoleImpl();
        this.roles.add(jpaRole);

        return jpaRole;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    // TODO implement a better algorithm
    public String getFullName() {
        return getFirstName() + " " + getMiddleName() + " " + getLastName();
    }

    public Collection<Role> getRoles() {
        return this.roles;
    }

    public List<Identifier> getIdentifiers() {
        return this.identifiers;
    }
}
