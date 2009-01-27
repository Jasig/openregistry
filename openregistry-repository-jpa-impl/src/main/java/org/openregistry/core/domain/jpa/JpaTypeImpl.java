package org.openregistry.core.domain.jpa;

import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.internal.Entity;

import javax.persistence.*;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@javax.persistence.Entity(name="type")
@Table(name="ctx_data_types")
public class JpaTypeImpl extends Entity implements Type {

    @Id
    @Column(name="type_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prs_type_seq")
    private Long id;

    @Column(name="data_type", nullable = false, length =100)
    private String dataType;

    @Column(name="description",nullable = false, length=100)
    private String description;

    public Long getId() {
        return this.id;
    }

    public String getDataType() {
        return this.dataType;
    }

    public String getDescription() {
        return this.description;
    }
}
