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

import org.openregistry.core.domain.Campus;
import org.openregistry.core.domain.internal.Entity;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.List;

/**
 * UniqueConstraint assumes that there is only one entry for each campus code/name combination
 *
 *  @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@javax.persistence.Entity(name="campus")

@Table(name="prd_campuses",
		uniqueConstraints= @UniqueConstraint(columnNames={"code", "name"}))
@Audited
public class JpaCampusImpl extends Entity implements Campus {

    @Id()
    @Column(name="id")
    @GeneratedValue(generator="prd_campuses_seq",strategy = GenerationType.AUTO)
    @SequenceGenerator(name="prd_campuses_seq",sequenceName="prd_campuses_seq",initialValue=1,allocationSize=50)
    private Long id;

    @Column(name="code", length=2, nullable = false)
    private String code;

    @Column(name="name", length=100, nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy="campus")
    private List<JpaRoleInfoImpl> roleInfos;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "campus")
    private List<JpaOrganizationalUnitImpl> organizationalUnits;

    public Long getId() {
        return this.id;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }
}
