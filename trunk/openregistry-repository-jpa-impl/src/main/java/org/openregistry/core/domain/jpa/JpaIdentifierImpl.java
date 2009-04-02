package org.openregistry.core.domain.jpa;

import org.openregistry.core.domain.Identifier;
import org.openregistry.core.domain.IdentifierType;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.internal.Entity;
import org.hibernate.envers.Audited;

import javax.persistence.*;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@javax.persistence.Entity(name="identifier")
@Table(name="prc_identifiers")
@Audited
public class JpaIdentifierImpl extends Entity implements Identifier {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prc_identifiers_seq")
    @SequenceGenerator(name="prc_identifiers_seq",sequenceName="prc_identifiers_seq",initialValue=1,allocationSize=50)
    private Long id;

    @ManyToOne(optional=false)
    @JoinColumn(name="person_id")
    private JpaPersonImpl person;

    @ManyToOne(optional=false)
    @JoinColumn(name="identifier_t")
    private JpaIdentifierTypeImpl type;

    @Column(name="identifier", length=100, nullable=false)
    private String value;
    
    @Column(name="is_primary", nullable=false)
    private Boolean primary;
    
    @Column(name="is_deleted", nullable=false)
    private Boolean deleted;

    public JpaIdentifierImpl() {
        // nothing to do
    }

    public JpaIdentifierImpl(final JpaPersonImpl person) {
        this.person = person;
    }

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
    
    public Boolean isPrimary() {
    	return this.primary;
    }
    
    public Boolean isDeleted() {
    	return this.deleted;
    }

    public void setType(final IdentifierType type) {
        if (!(type instanceof JpaIdentifierTypeImpl)) {
            throw new IllegalArgumentException("Requires type JpaIdentifierTypeImpl");
        }

        this.type = (JpaIdentifierTypeImpl) type;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    public void setPrimary(Boolean value) {
    	this.primary = value;
    }
    
    public void setDeleted(Boolean value) {
    	this.deleted = value;
    }
}
