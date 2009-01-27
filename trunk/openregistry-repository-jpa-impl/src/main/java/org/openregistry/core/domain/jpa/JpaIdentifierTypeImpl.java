package org.openregistry.core.domain.jpa;

import org.openregistry.core.domain.IdentifierType;
import org.openregistry.core.domain.internal.Entity;

import javax.persistence.*;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 *
 */
@javax.persistence.Entity(name="identifier_type")
@Table(name="prd_identifier_types")
public class JpaIdentifierTypeImpl extends Entity implements IdentifierType {

    @Id
    @Column(name="identifier_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prd_identifier_type_seq")
    private Long id;

    @Column(name="name",nullable = false,length = 100)
    private String name;

    protected Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}
