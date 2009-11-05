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
package org.openregistry.core.domain.jpa.sor;

import org.hibernate.envers.Audited;
import org.openregistry.core.domain.jpa.JpaTypeImpl;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.Phone;
import org.openregistry.core.domain.internal.Entity;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Unique constraints also assumes that one full phone number should not appear twice
 * Unique constraints assumes that each role has only one phone type per address
 *
 * Created by IntelliJ IDEA.
 * User: Nancy Mond
 * Date: Apr 7, 2009
 * Time: 2:49:19 PM
 * To change this template use File | Settings | File Templates.
 */
@javax.persistence.Entity(name="sorPhone")
@Table(name="prs_phones",
		uniqueConstraints= {
				@UniqueConstraint(columnNames={"country_code", "area_code", "phone_number"}),
				@UniqueConstraint(columnNames={"phone_t", "address_t", "role_record_id"})
		})
@Audited
public final class JpaSorPhoneImpl extends Entity implements Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prs_phones_seq")
    @SequenceGenerator(name="prs_phones_seq",sequenceName="prs_phones_seq",initialValue=1,allocationSize=50)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name="address_t")
    @NotNull
    private JpaTypeImpl addressType;

    @ManyToOne(optional = false)
    @JoinColumn(name="phone_t")
    @NotNull
    private JpaTypeImpl phoneType;

    @Column(name="country_code",nullable=false,length=5)
    @NotNull
    private String countryCode;

    @Column(name="area_code",nullable=false,length=5)
    @NotNull
    private String areaCode;

    @Column(name="phone_number",nullable=false,length=10)
    @NotNull
    private String number;

    @Column(name="extension", nullable=true,length=5)
    private String extension;

    @ManyToOne(optional=false)
    @JoinColumn(name="role_record_id")
    private JpaSorRoleImpl sorRole;

    public JpaSorPhoneImpl() {
        // nothing to do
    }

    public JpaSorPhoneImpl(final JpaSorRoleImpl sorRole) {
        this.sorRole = sorRole;
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
        Assert.isInstanceOf(JpaTypeImpl.class, addressType);
        this.addressType = (JpaTypeImpl) addressType;
    }

    public void setPhoneType(final Type phoneType) {
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
}
