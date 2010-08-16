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
import org.openregistry.core.domain.Country;
import org.openregistry.core.domain.Region;
import org.openregistry.core.domain.internal.Entity;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 *
 */
@javax.persistence.Entity(name="country")
@Table(name="ctd_countries")
@Audited
@org.hibernate.annotations.Table(appliesTo = "ctd_countries", indexes = {
        @Index(name="COUNTRY_NAME_INDEX", columnNames = "name"),
        @Index(name="COUNTRY_CODE_INDEX", columnNames = "code")
})
public class JpaCountryImpl extends Entity implements Country {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ctd_countries_seq")
    @SequenceGenerator(name="ctd_countries_seq",sequenceName="ctd_countries_seq",initialValue=1,allocationSize=50)
    private Long id;

    @Column(name="code",nullable=false,length = 3)
    private String code;

    @Column(name="name",nullable = false, length=100)
    private String name;

    @OneToMany(cascade= CascadeType.ALL, mappedBy="country",targetEntity = JpaRegionImpl.class)
    private List<Region> regions;

    public Long getId() {
        return this.id;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public List<Region> getRegions() {
        return this.regions;
    }
}
