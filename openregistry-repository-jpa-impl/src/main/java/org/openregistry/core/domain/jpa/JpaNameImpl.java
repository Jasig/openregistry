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

import org.hibernate.envers.*;
import org.openregistry.core.domain.*;
import org.springframework.util.*;

import javax.persistence.*;

/**
 * Unique Constraints assumes that only one official name and one preferred name per person id may exist
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@javax.persistence.Entity(name="name")
@Table(name="prc_names")

@Audited
public class JpaNameImpl extends AbstractNameImpl {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prc_names_seq")
    @SequenceGenerator(name="prc_names_seq",sequenceName="prc_names_seq",initialValue=1,allocationSize=50)
    private Long id;

    @ManyToOne(optional=false)
    @JoinColumn(name="name_t", nullable=false)
    private JpaTypeImpl type;

    @Column(name="prefix", nullable=true, length=5)
    private String prefix;

    @Column(name="given_name",nullable=false,length=100)
    private String given;

    @Column(name="middle_name",nullable=true,length=100)
    private String middle;

    @Column(name="family_name",nullable=true,length=100)
    private String family;

    @Column(name="suffix",nullable=true,length=5)
    private String suffix;

    @ManyToOne(optional=false)
    @JoinColumn(name="person_id", nullable=false)
    private JpaPersonImpl person;

    @Column(name="is_official_name", nullable=false)
    private Boolean officialName = false;

    @Column(name="is_preferred_name", nullable=false)
    private Boolean preferredName = false;

    @Column(name="name_source_id", nullable=false)
    private Long sourceId;

    @Column(name="comparison_value",nullable = false,length=201)
    private String comparisonValue;

    public JpaNameImpl() {
    	// nothing else to do
    }

    public JpaNameImpl(final JpaPersonImpl person) {
    	this.person = person;
    }

    public Long getId() {
        return this.id;
    }

    public Type getType() {
        return this.type;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getGiven() {
        return this.given;
    }

    public String getMiddle() {
        return this.middle;
    }

    public String getFamily() {
        return this.family;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public void setType(final Type type) {
        Assert.isInstanceOf(JpaTypeImpl.class, type);
        this.type = (JpaTypeImpl) type;
    }

    public void setPrefix(final String prefix) {
        this.prefix = prefix;
    }

    public void setGiven(final String given) {
        this.given = given;
        generateSoundEx();
    }

    public void setMiddle(final String middle) {
        this.middle = middle;
    }

    public void setFamily(final String family) {
        this.family = family;
        generateSoundEx();
    }

    public void setSuffix(final String suffix) {
        this.suffix = suffix;
    }

    @Override
    public Long getSourceNameId() {
        return this.sourceId;
    }

    @Override
    public void setSourceNameId(final Long sourceId) {
        this.sourceId = sourceId;
    }

    public void setOfficialName(final boolean officialName) {
        this.officialName = officialName;
    }

    public boolean isOfficialName() {
    	return this.officialName;
    }

    public void setPreferredName(final boolean preferredName) {
        this.preferredName = preferredName;
    }

    public boolean isPreferredName() {
    	return this.preferredName;
    }

    public String getFormattedName(){
        final StringBuilder builder = new StringBuilder();

        construct(builder, "", this.family, ",");
        construct(builder, "", this.given, "");

        return builder.toString();
    }

    public String getLongFormattedName(){
        final StringBuilder builder = new StringBuilder();

        if (this.prefix != null) construct(builder, "", this.prefix, " ");
        construct(builder, "", this.given, " ");
        if (this.middle != null) construct(builder, "", this.middle, " ");
        if (this.family != null) construct(builder, "", this.family, " ");
        if (this.suffix != null && !this.suffix.isEmpty()) construct(builder, ",", this.suffix, "");

        return builder.toString();
    }

    public String toString() {
        final StringBuilder builder = new StringBuilder();

        construct(builder, "", this.prefix, " ");
        construct(builder, "", this.given, " ");
        construct(builder, "", this.middle, " ");
        construct(builder, "", this.family, "");
        if (this.suffix != null && !this.suffix.isEmpty()) construct(builder, ",", this.suffix, "");

        return builder.toString();
    }

    protected void construct(final StringBuilder builder, final String prefix, final String string, final String delimiter) {
        if (string != null) {
            builder.append(prefix);
            builder.append(string);
            builder.append(delimiter);
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof JpaNameImpl)) return false;

        final JpaNameImpl that = (JpaNameImpl) o;

        if (family != null ? !family.equals(that.family) : that.family != null) return false;
        if (given != null ? !given.equals(that.given) : that.given != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (middle != null ? !middle.equals(that.middle) : that.middle != null) return false;
        if (prefix != null ? !prefix.equals(that.prefix) : that.prefix != null) return false;
        if (suffix != null ? !suffix.equals(that.suffix) : that.suffix != null) return false;

        return true;
    }

    @Override
    public String getComparisonValue() {
        return this.comparisonValue;
    }
}
