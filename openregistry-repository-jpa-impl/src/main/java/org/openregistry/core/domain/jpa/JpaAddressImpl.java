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

import org.hibernate.annotations.*;
import org.openregistry.core.domain.Address;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.Region;
import org.openregistry.core.domain.Country;
import org.openregistry.core.domain.internal.Entity;
import org.hibernate.envers.Audited;
import org.springframework.util.StringUtils;
import java.util.Date;

import javax.persistence.*;
import javax.persistence.Table;

/**
 * UniqueConstraint assumes that there is only one entry for a given address type for each role
 *
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 *
 */
@javax.persistence.Entity(name="address")
@Table(	name="prc_addresses",
		uniqueConstraints= @UniqueConstraint(columnNames={"address_t", "role_record_id"}))
@Audited
@org.hibernate.annotations.Table(appliesTo = "prc_addresses", indexes = @Index(name = "ADDRESS_INDEX", columnNames = {"line1", "city", "postal_code"}))
public class JpaAddressImpl extends Entity implements Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prc_addresses_seq")
    @SequenceGenerator(name="prc_addresses_seq",sequenceName="prc_addresses_seq",initialValue=1,allocationSize=50)
    private Long id;

    @ManyToOne
    @JoinColumn(name="address_t")
    private JpaTypeImpl type;

    @Column(name="line1", nullable = true,length=100)
    private String line1;

    @Column(name="line2", nullable = true,length=100)
    private String line2;

    @Column(name="line3", nullable = true,length=100)
    private String line3;

    @Column(name="bldg_no", nullable = true,length=10)
    private String bldgNo;

    @Column(name="room_no", nullable = true,length=11)
    private String roomNo;

    @ManyToOne
    @JoinColumn(name="region_id")
    private JpaRegionImpl region;

    @ManyToOne
    @JoinColumn(name="country_id")
    private JpaCountryImpl country;

    @Column(name="city",length=100,nullable = false)
    private String city;

    @Column(name="postal_code",length=9, nullable = true)
    private String postalCode;

    @Column(name="update_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate = new Date();

    @ManyToOne(optional=false)
    @JoinColumn(name="role_record_id")
    private JpaRoleImpl role;

    public JpaAddressImpl() {
        // nothing to do
    }

    public JpaAddressImpl(final JpaRoleImpl jpaRole) {
        this.role = jpaRole;
    }

    public Long getId() {
        return this.id;
    }

    public Type getType() {
        return this.type;
    }

    public String getLine1() {
        return this.line1;
    }

    public String getLine2() {
        return this.line2;
    }

    public String getLine3() {
        return this.line3;
    }

    public String getBldgNo(){
        return this.bldgNo;
    }

    public String getRoomNo(){
        return this.roomNo;
    }

    public Region getRegion() {
        return this.region;
    }

    public Country getCountry() {
        return this.country;
    }

    public String getCity() {
        return this.city;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public Date getUpdateDate(){
        return this.updateDate;
    }

    public void setType(final Type type) {
        if (!(type instanceof JpaTypeImpl)) {
            throw new IllegalArgumentException("Requires type JpaTypeImpl");
        }

        this.type = (JpaTypeImpl) type;
    }

    public void setLine1(final String line1) {
        this.line1 = line1;
    }

    public void setLine2(final String line2) {
        this.line2 = line2;
    }

    public void setLine3(final String line3) {
        this.line3 = line3;
    }

    public void setBldgNo(String bldgNo){
        this.bldgNo = bldgNo;
    }

    public void setRoomNo(String roomNo){
        this.roomNo = roomNo;
    }

    public void setRegion(final Region region) {
        if (region != null && !(region instanceof JpaRegionImpl)) {
            throw new IllegalStateException("Region implementation must be of type JpaRegionImpl");
        }
        this.region = (JpaRegionImpl) region;
    }

    public void setCountry(final Country country) {
        if (country != null && !(country instanceof JpaCountryImpl)) {
            throw new IllegalStateException("Country implementation must be of type JpaCountryImpl");
        }
        this.country = (JpaCountryImpl) country;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public void setPostalCode(final String postalCode) {
        this.postalCode = postalCode;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updateDate = new Date();
    }

    public String getSingleLineAddress(){
        final StringBuilder builder = new StringBuilder();

        builder.append(getLine1());
        if (StringUtils.hasText(this.line2)) {
            builder.append(", ");
            builder.append(getLine2());
        }
        if (StringUtils.hasText(this.line3)) {
            builder.append(", ");
            builder.append(getLine3());
        }
        if (StringUtils.hasText(this.city)) {
            builder.append(", ");
            builder.append(getCity());
        }
        if (getRegion() != null) {
            builder.append(", ");
            builder.append(getRegion().getCode());
        }
        if (StringUtils.hasText(this.postalCode)) {
            builder.append(" ");
            builder.append(getPostalCode());
        }
        if (getCountry() != null) {
            builder.append(" ");
            builder.append(getCountry().getName());
        }

        return builder.toString();
    }
}
