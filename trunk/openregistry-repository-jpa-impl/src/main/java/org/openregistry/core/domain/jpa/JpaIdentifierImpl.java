package org.openregistry.core.domain.jpa;

import org.openregistry.core.domain.Identifier;
import org.openregistry.core.domain.IdentifierType;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.internal.Entity;

import javax.persistence.*;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@javax.persistence.Entity(name="identifier")
@Table(name="prc_identifiers")
public class JpaIdentifierImpl extends Entity implements Identifier {

    @Id
    @Column(name="identifier_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prs_identifier_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name="person_id", nullable=false)
    private JpaPersonImpl person;

    // TODO map this
    private JpaIdentifierTypeImpl type;

    @Column(name="identifier", length=100,nullable=false)
    private String value;

    protected Long getId() {
        return this.id;
    }

    public IdentifierType getType() {
        return this.type;
    }

    public String getValue() {
        return this.value;
    }

    public Person getPerson() {
        return this.person;
    }
}
