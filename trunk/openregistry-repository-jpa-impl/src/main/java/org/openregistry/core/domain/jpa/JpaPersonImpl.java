package org.openregistry.core.domain.jpa;

import org.openregistry.core.domain.internal.Entity;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.Role;
import org.openregistry.core.domain.Identifier;
import org.openregistry.core.domain.Name;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Date;

/**
 * Person entity mapped to a persistence store with JPA annotations.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@javax.persistence.Entity(name="person")
@Table(name="prc_persons")
@SecondaryTable(name="prs_biodem", pkJoinColumns=@PrimaryKeyJoinColumn(name="biodem_id"))
public class JpaPersonImpl extends Entity implements Person {

    @OneToMany(cascade=CascadeType.ALL, mappedBy="person")    
    private Collection<JpaRoleImpl> roles;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="person")
    private List<JpaIdentifierImpl> identifiers;

    @OneToOne(optional=false)
    @JoinColumn(name="name_id")
    private JpaNameImpl name;

    @Column(name="date_of_birth",nullable=false,table="prs_biodem")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Column(name="gender",length=1,nullable=false,table="prs_biodem")
    private String gender;

    public Name getName() {
        return this.name;
    }

    public String getGender() {
        return this.gender;
    }

    public Date getDateOfBirth() {
        return this.dateOfBirth;
    }

    public Role addRole() {
        final JpaRoleImpl jpaRole = new JpaRoleImpl();
        this.roles.add(jpaRole);

        return jpaRole;
    }

    public Collection<? extends Role> getRoles() {
        return this.roles;
    }

    public List<? extends Identifier> getIdentifiers() {
        return this.identifiers;
    }
}
