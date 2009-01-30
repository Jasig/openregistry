package org.openregistry.core.domain.jpa;

import org.openregistry.core.domain.IdentifierType;
import org.openregistry.core.domain.internal.Entity;

import javax.persistence.*;
import java.util.List;

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
    @Column(name="identifier_t")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prd_identifier_type_seq")
    @SequenceGenerator(name="prd_identifier_type_seq",sequenceName="prd_identifier_type_seq",initialValue=1,allocationSize=50)
    private Long id;

    @Column(name="name",nullable = false,length = 100)
    private String name;

    @OneToMany(fetch=FetchType.LAZY, mappedBy="type")
    private List<JpaIdentifierImpl> identifiers; 

    protected Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}
