/**
 * Copyright (C) 2009 Jasig, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openregistry.core.domain.jpa;

import org.hibernate.envers.*;
import org.openregistry.core.domain.*;
import org.openregistry.core.domain.internal.Entity;

import javax.persistence.*;

/**
 * Unique constraints also assumes that one full phone number should not appear twice
 * Unique constraints assumes that each role has only one phone type per address
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@javax.persistence.Entity(name="phone")

// TODO should a phone number be able to appear twice?  Can someone choose to use their cell number as "mobile" and "office"?
@Table(name="prc_phones",
		uniqueConstraints= {
				@UniqueConstraint(columnNames={"country_code", "area_code", "phone_number"}),
				@UniqueConstraint(columnNames={"phone_t", "address_t", "role_record_id"})
		})
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

    public String toString() {
        final StringBuilder builder = new StringBuilder();
        if (this.countryCode != null && this.countryCode != "") {
        	builder.append("+");
        	builder.append(this.countryCode);
        	builder.append(" ");
        }
        if (this.areaCode != null && this.areaCode != "") {
        	builder.append(this.areaCode);
        	builder.append(PHONE_SEP);
        }
		if (this.number.length() > 2 && this.number.charAt(3) != '-') {
        	builder.append(this.number.substring(0, 3));
        	builder.append(PHONE_SEP);
        	builder.append(this.number.substring(3));
        } else {
        	builder.append(this.number);
        }
        if (this.extension != null && this.extension != "") {
        	builder.append(" x");
        	builder.append(this.extension);
        }
        return builder.toString();
    }
}
