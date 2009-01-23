package org.openregistry.core.domain.jpa;

import org.openregistry.core.domain.Identifier;
import org.openregistry.core.domain.IdentifierType;
import org.openregistry.core.domain.Person;

import javax.persistence.*;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@Entity
@Table(name="prc_identifiers")
public class JpaIdentifierImpl implements Identifier {

    private Person person;

    // TODO map this
    private JpaIdentifierTypeImpl type;

    @Column(name="identifier", length=100,nullable=false)
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
