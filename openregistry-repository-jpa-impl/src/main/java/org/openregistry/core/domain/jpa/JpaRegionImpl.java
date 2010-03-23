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

import org.openregistry.core.domain.Region;
import org.openregistry.core.domain.Country;
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
@javax.persistence.Entity(name="region")
@Table(name="ctd_regions", uniqueConstraints = {@UniqueConstraint(columnNames = {"country_id", "code"})})
@Audited
public class JpaRegionImpl extends Entity implements Region {

    @Id
    @SequenceGenerator(name="ctd_regions_seq",sequenceName="ctd_regions_seq",initialValue=1,allocationSize=50)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ctd_regions_seq")
    private Long id;

    @Column(name="name",nullable = false,length=100)
    private String name;

    @Column(name="code",nullable=false,length=3)
    private String code;

    @ManyToOne
    @JoinColumn(name="country_id", nullable=false)
    private JpaCountryImpl country;

    @OneToMany(mappedBy = "region", fetch = FetchType.LAZY)
    private List<JpaAddressImpl> addresses;

    protected Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getCode() {
        return this.code;
    }

    public Country getCountry() {
        return this.country;
    }

    public String toString(){
        return getCode();    
    }
}
