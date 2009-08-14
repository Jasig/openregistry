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

import org.openregistry.core.domain.IdentifierType;
import org.openregistry.core.domain.internal.Entity;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.List;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 *
 */
@javax.persistence.Entity(name="identifier_type")
@Table(name="prd_identifier_types")
@Audited
public class JpaIdentifierTypeImpl extends Entity implements IdentifierType {

    @Id
    @Column(name="identifier_t")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prd_identifier_types_seq")
    @SequenceGenerator(name="prd_identifier_types_seq",sequenceName="prd_identifier_types_seq",initialValue=1,allocationSize=50)
    private Long id;

    @Column(name="name",nullable = false,length = 100)
    private String name;

    @OneToMany(fetch=FetchType.LAZY, mappedBy="type")
    private List<JpaIdentifierImpl> identifiers; 

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return this.name;
    }
}
