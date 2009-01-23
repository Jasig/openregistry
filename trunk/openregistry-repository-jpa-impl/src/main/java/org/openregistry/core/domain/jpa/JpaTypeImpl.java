package org.openregistry.core.domain.jpa;

import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.internal.Entity;

import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public class JpaTypeImpl extends Entity implements Type {

    @Id
    @Column(name="type_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prs_type_seq")
    private Long id;

    // TODO map this
    private String value;

    public Long getId() {
        return this.id;
    }

    public String getValue() {
        return this.value;
    }
}
