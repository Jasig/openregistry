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

import org.openregistry.core.domain.IdentifierType;
import org.openregistry.core.domain.internal.Entity;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Unique Constraints assumes that the identifier type name must be unique
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 *
 */
@javax.persistence.Entity(name="identifier_type")
@Table(name="prd_identifier_types", uniqueConstraints= @UniqueConstraint(columnNames={"name"}))
@Audited
public class JpaIdentifierTypeImpl extends Entity implements IdentifierType {

    @Id
    @Column(name="identifier_t")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prd_identifier_types_seq")
    @SequenceGenerator(name="prd_identifier_types_seq",sequenceName="prd_identifier_types_seq",initialValue=1,allocationSize=50)
    private Long id;

    @Column(name="name",nullable = false, length = 100)
    private String name;

    @OneToMany(fetch=FetchType.LAZY, mappedBy="type")
    private List<JpaIdentifierImpl> identifiers = new ArrayList<JpaIdentifierImpl>();

    @Column(name="format", nullable = false, length=100)
    private String format;

    @Column(name="private", nullable = false)
    private boolean privateIdentifier = false;

    @Column(name="modifiable",nullable = false)
    private boolean modifiable = true;

    @Column(name="deleted", nullable = false)
    private boolean deleted = false;

    @Column(name="description",nullable = false, length=200)
    private String description;

    private transient Pattern pattern;

    @Override
    protected Long getId() {
        return this.id;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getFormatAsString() {
        return this.format;
    }

    @Override
    public Pattern getFormatAsPattern() {
        if (pattern == null) {
            this.pattern = Pattern.compile(this.format);
        }
                
        return this.pattern;
    }

    @Override
    public boolean isPrivate() {
        return this.privateIdentifier;
    }

    @Override
    public boolean isModifiable() {
        return this.modifiable;
    }

    @Override
    public boolean isDeleted() {
        return this.deleted;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return this.name;
    }

    public List<JpaIdentifierImpl> getIdentifiers() {
        return this.identifiers;
    }
}
