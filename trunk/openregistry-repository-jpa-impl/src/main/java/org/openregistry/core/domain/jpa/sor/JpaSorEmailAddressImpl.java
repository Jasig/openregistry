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

import org.openregistry.core.domain.annotation.AllowedTypes;
import org.openregistry.core.domain.internal.Entity;
import org.openregistry.core.domain.EmailAddress;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.jpa.JpaTypeImpl;
import org.hibernate.envers.Audited;
import org.openregistry.core.domain.normalization.Capitalize;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Nancy Mond
 * @version $Revision$ $Date$
 * @since 0.1
 */

@javax.persistence.Entity(name="sorEmailAddress")
    @Table(name="prs_emails", uniqueConstraints= @UniqueConstraint(columnNames={"address_t", "role_record_id"}))
@Audited
public class JpaSorEmailAddressImpl extends Entity implements EmailAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prs_emails_seq")
    @SequenceGenerator(name="prs_emails_seq",sequenceName="prs_emails_seq",initialValue=1,allocationSize=50)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name="address_t")
    @NotNull
    @AllowedTypes(property = "emailAddress.addressType")
    private JpaTypeImpl addressType;

    @Column(name="address",nullable=false,length=100)
    @NotNull
    @Capitalize(property="emailAddress.address")
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

    public Long getId() {
        return this.id;
    }

    public Type getAddressType() {
        return this.addressType;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public void setAddressType(final Type type) {
        Assert.isInstanceOf(JpaTypeImpl.class, type);
        this.addressType = (JpaTypeImpl) type;
    }
}
