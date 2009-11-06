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

import org.openregistry.core.domain.*;
import org.openregistry.core.domain.internal.Entity;
import org.hibernate.envers.Audited;
import org.springframework.util.Assert;

import javax.persistence.*;

/**
 * Unique Constraints assumes each calculated person can have only one entry for each identifier type
 * For example, a person can't have multiple SSN or NetIDs
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@javax.persistence.Entity(name="identifier")

@Table(name="prc_identifiers", uniqueConstraints= @UniqueConstraint(columnNames={"identifier_t", "identifier"}))
@Audited
public class JpaIdentifierImpl extends Entity implements Identifier {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prc_identifiers_seq")
    @SequenceGenerator(name="prc_identifiers_seq",sequenceName="prc_identifiers_seq",initialValue=1,allocationSize=50)
    private Long id;

    @ManyToOne(optional=false)
    @JoinColumn(name="person_id")
    private JpaPersonImpl person;

    @ManyToOne(optional=false)
    @JoinColumn(name="identifier_t")
    private JpaIdentifierTypeImpl type;

    @Column(name="identifier", length=100, nullable=false)
    private String value;

    @Column(name="is_primary", nullable=false)
    private Boolean primary = true;

    @Column(name="is_deleted", nullable=false)
    private Boolean deleted = false;

    public JpaIdentifierImpl() {
        // nothing to do
    }

    public JpaIdentifierImpl(final JpaPersonImpl person) {
        this.person = person;
    }

    public JpaIdentifierImpl(final JpaPersonImpl person, final JpaIdentifierTypeImpl jpaIdentifierType, final String value) {
        this(person);
        this.type = jpaIdentifierType;
        this.value = value;

        this.type.getIdentifiers().add(this);
    }

    protected Long getId() {
        return this.id;
    }

    public IdentifierType getType() {
        return this.type;
    }

    public String getValue() {
        return this.value;
    }

    public Boolean isPrimary() {
    	return this.primary;
    }

    public Boolean isDeleted() {
    	return this.deleted;
    }

    public void setType(final IdentifierType type) {
        Assert.isInstanceOf(JpaIdentifierTypeImpl.class, type, "Requires type JpaIdentifierTypeImpl.");
        this.type = (JpaIdentifierTypeImpl) type;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setPrimary(Boolean value) {
    	this.primary = value;
    }

    public void setDeleted(Boolean value) {
    	this.deleted = value;
    }

}
