package org.openregistry.core.domain.jpa;

import org.hibernate.annotations.Index;
import org.hibernate.envers.Audited;
import org.openregistry.core.domain.ContactPhone;
import org.openregistry.core.domain.Phone;
import org.openregistry.core.domain.Type;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.persistence.*;

/**
 * @version $Revision$ $Date$
 * @since 0.1
 */
@javax.persistence.Entity(name="contactPhone")

@Table(name="prc_contact_phones")
@Audited
@org.hibernate.annotations.Table(appliesTo = "prc_contact_phones", indexes = {@Index(name = "CONTACT_PHONE_INDEX", columnNames = {"country_code", "area_code", "phone_number"})})
public class JpaContactPhoneImpl implements ContactPhone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prc_contact_phones_seq")
    @SequenceGenerator(name="prc_contact_phones_seq",sequenceName="prc_contact_phones_seq",initialValue=1,allocationSize=50)
    private Long id;

    @ManyToOne(optional = true)
    @JoinColumn(name="address_t")
    private JpaTypeImpl addressType;

    @ManyToOne(optional = true)
    @JoinColumn(name="phone_t")
    private JpaTypeImpl phoneType;

    @Column(name="country_code",nullable=true,length=5)
    private String countryCode;

    @Column(name="area_code",nullable=true,length=5)
    private String areaCode;

    @Column(name="phone_number",nullable=true,length=10)
    private String number;

    @Column(name="extension", nullable=true,length=5)
    private String extension;

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
        if (addressType == null) {
            this.addressType = null;
            return;
        }

        Assert.isInstanceOf(JpaTypeImpl.class, addressType);
        this.addressType = (JpaTypeImpl) addressType;
    }

    public void setPhoneType(final Type phoneType) {
        if (phoneType == null) {
            this.phoneType = null;
            return;
        }
        
        Assert.isInstanceOf(JpaTypeImpl.class, phoneType);
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

    public String toString() {
        final StringBuilder builder = new StringBuilder();
        if (StringUtils.hasText(this.countryCode)) {
        	builder.append("+");
        	builder.append(this.countryCode);
        	builder.append(" ");
        }
        if (StringUtils.hasText(this.areaCode)) {
        	builder.append(this.areaCode);
        	builder.append(PHONE_SEP);
        }

        if (StringUtils.hasText(this.number)) {
            if (this.number.length() > 2 && this.number.charAt(3) != '-') {
                builder.append(this.number.substring(0, 3));
                builder.append(PHONE_SEP);
                builder.append(this.number.substring(3));
            } else {
                builder.append(this.number);
            }
        }
        
        if (StringUtils.hasText(this.extension)) {
        	builder.append(" x");
        	builder.append(this.extension);
        }
        return builder.toString();
    }

    @Override
    public void clear() {
        this.addressType = null;
        this.areaCode = null;
        this.countryCode = null;
        this.extension = null;
        this.number = null;
        this.phoneType = null;

    }

    @Override
    public void update(final Phone phone) {
        if (phone == null) {
            clear();
            return;
        }

        Assert.isInstanceOf(JpaTypeImpl.class, phone.getAddressType());

        this.addressType = (JpaTypeImpl) phone.getAddressType();
        this.areaCode = phone.getAreaCode();
        this.countryCode = phone.getCountryCode();
        this.extension = phone.getExtension();
        this.number = phone.getNumber();

        Assert.isInstanceOf(JpaTypeImpl.class, phone.getPhoneType());
        this.phoneType = (JpaTypeImpl) phone.getPhoneType();
    }
}
