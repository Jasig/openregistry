package org.openregistry.core.domain.jpa;

import org.openregistry.core.domain.internal.Entity;
import org.openregistry.core.domain.EmailAddress;
import org.openregistry.core.domain.Type;

import javax.persistence.*;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */

@javax.persistence.Entity(name="emailAddress")
@Table(name="prs_emails")
public class JpaEmailAddressImpl extends Entity implements EmailAddress {

    @Id
    @Column(name="email_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prs_email_seq")
    private Long id;

    // TODO map
    private JpaTypeImpl type;

    @Column(name="address",nullable=false,length=100)
    private String address;
    
    @ManyToOne(optional=false)
    @JoinColumn(name="prs_sor_role_record_id")
    private JpaRoleImpl role;

    protected Long getId() {
        return this.id;
    }

    public Type getAddressType() {
        return this.type;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public void setAddressType(final Type type) {
        if (!(type instanceof JpaTypeImpl)) {
            throw new IllegalArgumentException("Requires type JpaTypeImpl");
        }

        this.type = (JpaTypeImpl) type;
    }
}
