/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.openregistry.core.domain.jpa;

import org.hibernate.annotations.Index;
import org.openregistry.core.domain.internal.Entity;
import org.openregistry.core.domain.EmailAddress;
import org.openregistry.core.domain.Type;
import org.hibernate.envers.Audited;

import javax.persistence.*;

/**
 * Unique constraint assumes that each role only has one entry per address type
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */

@javax.persistence.Entity(name="emailAddress")
@Table(name="prc_emails") //,
//		uniqueConstraints= @UniqueConstraint(columnNames={"address_t", "role_record_id"}))
@Audited
@org.hibernate.annotations.Table(appliesTo = "prc_emails", indexes = @Index(name="EMAIL_ADDRESS_INDEX", columnNames = "address"))
public class JpaEmailAddressImpl extends Entity implements EmailAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prc_emails_seq")
    @SequenceGenerator(name="prc_emails_seq",sequenceName="prc_emails_seq",initialValue=1,allocationSize=50)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name="address_t")
    private JpaTypeImpl type;

    @Column(name="address",nullable=false,length=100)
    private String address;

    @ManyToOne(optional=false)
    @JoinColumn(name="role_record_id")
    private JpaRoleImpl role;

    public JpaEmailAddressImpl() {
        // nothing to do
    }

    public JpaEmailAddressImpl(final JpaRoleImpl role) {
        this.role = role;
    }

    public Long getId() {
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
