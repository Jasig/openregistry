package org.openregistry.core.domain.jpa;

import org.openregistry.core.domain.Identifier;
import org.openregistry.core.domain.IdentifierType;
import org.openregistry.core.domain.Person;

import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@Entity
@Table(name="prc_identifiers")
public class JpaIdentifierImpl implements Identifier {

    @ManyToOne
    @JoinColumn(name="person_id", nullable=false)
    private Person person;

    private JpaIdentifierTypeImpl type;

    private String value;

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
