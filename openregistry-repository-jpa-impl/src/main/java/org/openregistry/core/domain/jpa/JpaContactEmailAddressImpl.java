package org.openregistry.core.domain.jpa;

import org.hibernate.annotations.Index;
import org.hibernate.envers.Audited;
import org.openregistry.core.domain.EmailAddress;
import org.openregistry.core.domain.Type;
import org.springframework.util.Assert;

import javax.persistence.*;

/**
 * Version of {@link org.openregistry.core.domain.EmailAddress} that writes to a different table for contact information.
 *
 * @version $Revision$ $Date$
 * @since 0.1
 */
@javax.persistence.Entity(name="contactEmailAddress")
@Table(name="prc_contact_emails")
@Audited
@org.hibernate.annotations.Table(appliesTo = "prc_contact_emails", indexes = @Index(name="CONTACT_EMAIL_ADDRESS_INDEX", columnNames = "address"))
public class JpaContactEmailAddressImpl implements EmailAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prc_contact_emails_seq")
    @SequenceGenerator(name="prc_contact_emails_seq",sequenceName="prc_contact_emails_seq",initialValue=1,allocationSize=50)
    private Long id;


    @ManyToOne(optional = true)
    @JoinColumn(name="email_address_t")
    private JpaTypeImpl type;

    @Column(name="address",nullable=true,length=100)
    private String address;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public Type getAddressType() {
        return this.type;
    }

    @Override
    public String getAddress() {
        return this.address;
    }

    @Override
    public void setAddress(final String address) {
        this.address = address;
    }

    @Override
    public void setAddressType(final Type type) {
        if (type == null) {
            this.type = null;
            return;
        }
        
        Assert.isInstanceOf(JpaTypeImpl.class, type);
        this.type = (JpaTypeImpl) type;
    }
}
