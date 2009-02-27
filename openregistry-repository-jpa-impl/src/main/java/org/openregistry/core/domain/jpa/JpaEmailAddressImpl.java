package org.openregistry.core.domain.jpa;

import org.openregistry.core.domain.internal.Entity;
import org.openregistry.core.domain.EmailAddress;
import org.openregistry.core.domain.Type;
import org.hibernate.envers.Audited;

import javax.persistence.*;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */

@javax.persistence.Entity(name="emailAddress")
@Table(name="prc_emails")
@Audited
public class JpaEmailAddressImpl extends Entity implements EmailAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prs_email_seq")
    @SequenceGenerator(name="prs_email_seq",sequenceName="prs_email_seq",initialValue=1,allocationSize=50)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name="address_t")
    private JpaTypeImpl type;

    @Column(name="address",nullable=false,length=100)
    private String address;
    
    @ManyToOne(optional=false)
    @JoinColumn(name="prc_role_record_id")
    private JpaRoleImpl role;

    public JpaEmailAddressImpl() {
        // nothing to do
    }

    public JpaEmailAddressImpl(final JpaRoleImpl role) {
        this.role = role;
    }

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
