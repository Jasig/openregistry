package org.openregistry.core.domain.jpa.sor;

import org.openregistry.core.domain.internal.Entity;
import org.openregistry.core.domain.EmailAddress;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.jpa.JpaTypeImpl;
import org.openregistry.core.domain.jpa.sor.JpaSorRoleImpl;
import org.hibernate.envers.Audited;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Nancy Mond
 * Date: Apr 7, 2009
 * Time: 2:28:38 PM
 * To change this template use File | Settings | File Templates.
 */

@javax.persistence.Entity(name="sorEmailAddress")
@Table(name="prs_emails")
public class JpaSorEmailAddressImpl extends Entity implements EmailAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prs_emails_seq")
    @SequenceGenerator(name="prs_emails_seq",sequenceName="prs_emails_seq",initialValue=1,allocationSize=50)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name="address_t")
    private JpaTypeImpl type;

    @Column(name="address",nullable=false,length=100)
    private String address;

    @ManyToOne(optional=false)
    @JoinColumn(name="role_record_id")
    private JpaSorRoleImpl sorRole;

    public JpaSorEmailAddressImpl() {
        // nothing to do
    }

    public JpaSorEmailAddressImpl(final JpaSorRoleImpl sorRole) {
        this.sorRole = sorRole;
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
