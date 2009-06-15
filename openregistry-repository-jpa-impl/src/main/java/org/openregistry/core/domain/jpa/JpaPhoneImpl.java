package org.openregistry.core.domain.jpa;

import org.openregistry.core.domain.internal.Entity;
import org.openregistry.core.domain.Phone;
import org.openregistry.core.domain.Type;
import org.hibernate.envers.Audited;

import javax.persistence.*;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@javax.persistence.Entity(name="phone")
@Table(name="prc_phones")
@Audited
public class JpaPhoneImpl extends Entity implements Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prc_phones_seq")
    @SequenceGenerator(name="prc_phones_seq",sequenceName="prc_phones_seq",initialValue=1,allocationSize=50)
    private Long id;
    
    @ManyToOne(optional = false)
    @JoinColumn(name="address_t")
    private JpaTypeImpl addressType;

    @ManyToOne(optional = false)
    @JoinColumn(name="phone_t")
    private JpaTypeImpl phoneType;

    @Column(name="country_code",nullable=false,length=5)
    private String countryCode;

    @Column(name="area_code",nullable=false,length=5)
    private String areaCode;

    @Column(name="phone_number",nullable=false,length=10)
    private String number;

    @Column(name="extension", nullable=true,length=5)
    private String extension;

    @ManyToOne(optional=false)
    @JoinColumn(name="role_record_id")
    private JpaRoleImpl role;

    public JpaPhoneImpl() {
        // nothing to do
    }

    public JpaPhoneImpl(JpaRoleImpl role) {
        this.role = role;
    }

    public Long getId() {
        return this.id;
    }

    public Type getAddressType() {
        return this.addressType;
    }

    public Type getPhoneType() {
        return this.phoneType;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public String getAreaCode() {
        return this.areaCode;
    }

    public String getNumber() {
        return this.number;
    }

    public String getExtension() {
        return this.extension;
    }

    public void setAddressType(final Type addressType) {
        if (!(addressType instanceof JpaTypeImpl)) {
            throw new IllegalArgumentException("Requires type JpaTypeImpl");
        }

        this.addressType = (JpaTypeImpl) addressType;
    }

    public void setPhoneType(final Type phoneType) {
        if (!(phoneType instanceof JpaTypeImpl)) {
            throw new IllegalArgumentException("Requires type JpaTypeImpl");
        }

        this.phoneType = (JpaTypeImpl) phoneType;
    }

    public void setCountryCode(final String countryCode) {
        this.countryCode = countryCode;
    }

    public void setAreaCode(final String areaCode) {
        this.areaCode = areaCode;
    }

    public void setNumber(final String number) {
        this.number = number;
    }

    public void setExtension(final String extension) {
        this.extension = extension;
    }
}
