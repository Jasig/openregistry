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
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.internal.Entity;
import org.hibernate.envers.Audited;

import javax.persistence.*;

/**
 * Unique constraints assumes that each data type/description combination only appears once
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@javax.persistence.Entity(name="type")
@Table(name="ctx_data_types",
	uniqueConstraints = @UniqueConstraint(columnNames = {"data_type","description"})
)
@Audited
@Cacheable(true)
@org.hibernate.annotations.Table(indexes = {@Index(name="TYPE_INDEX", columnNames = "data_type"), @Index(name = "TYPE_DESCRIPTION_INDEX", columnNames = {"data_type", "description"})}, appliesTo = "ctx_data_types")
public class JpaTypeImpl extends Entity implements Type {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ctx_data_types_seq")
    @SequenceGenerator(name="ctx_data_types_seq",sequenceName="ctx_data_types_seq",initialValue=1,allocationSize=50)
    private Long id;

    @Column(name="data_type", nullable = false, length =100)
    private String dataType;
    
    @Column(name="description",nullable = false, length=100)
    private String description;

    public Long getId() {
        return this.id;
    }

    public String getDataType() {
        return this.dataType;
    }

    public String getDescription() {
        return this.description;
    }
}
