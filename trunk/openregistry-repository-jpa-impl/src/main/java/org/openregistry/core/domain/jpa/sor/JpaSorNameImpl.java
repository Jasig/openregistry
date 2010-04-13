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

package org.openregistry.core.domain.jpa.sor;

import org.hibernate.annotations.Index;
import org.openregistry.core.domain.AbstractNameImpl;
import org.openregistry.core.domain.annotation.AllowedTypes;
import org.openregistry.core.domain.jpa.JpaTypeImpl;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.normalization.Capitalize;
import org.openregistry.core.domain.normalization.FirstName;
import org.openregistry.core.domain.normalization.LastName;
import org.openregistry.core.domain.normalization.NameSuffix;
import org.openregistry.core.domain.sor.SorName;
import org.springframework.util.Assert;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Implementation of the Name domain object that conforms to the tables for the Systems of Record
 *
 * TODO: add unique constraints with new type
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@javax.persistence.Entity(name="sorName")
@Table(name="prs_names")
@Audited
@org.hibernate.annotations.Table(appliesTo = "prs_names", indexes = @Index(name = "PRS_NAME_PERSON_INDEX", columnNames = "sor_person_id"))
public class JpaSorNameImpl implements SorName, Serializable {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prs_names_seq")
    @SequenceGenerator(name="prs_names_seq",sequenceName="prs_names_seq",initialValue=1,allocationSize=50)
    private Long id;
    
    @ManyToOne(optional=false)
    @JoinColumn(name="name_t", nullable=false)
    @NotNull
    @AllowedTypes(property = "name.type")
    private JpaTypeImpl type;

    @Column(name="prefix", nullable=true, length=5)
    @Capitalize(property = "name.prefix")
    private String prefix;

    @NotNull
    @Size(min=1)
    @Column(name="given_name",nullable=false,length=100)
    @Capitalize(property = "name.given")
    @FirstName
    private String given;

    @Column(name="middle_name",nullable=true,length=100)
    @Capitalize(property = "name.middle")
    private String middle;

    @Column(name="family_name",nullable=true,length=100)
    @Capitalize(property = "name.family")
    @LastName
    private String family;

    @Column(name="suffix",nullable=true,length=5)
    @Capitalize(property = "name.suffix")
    @NameSuffix
    private String suffix;

    @ManyToOne(optional = false)
    @JoinColumn(name="sor_person_id", nullable=false)
    private JpaSorPersonImpl person;

    public JpaSorNameImpl() {
        // nothing to do
    }

    public JpaSorNameImpl(final JpaSorPersonImpl person) {
        this.person = person;
    }

    public void setId(final Long id) {
        this.id = id;
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
    }

    public void setMiddle(final String middle) {
        this.middle = middle;
    }

    public void setFamily(final String family) {
        this.family = family;
    }

    public void setSuffix(final String suffix) {
        this.suffix = suffix;
    }

    public String getFormattedName(){
        final StringBuilder builder = new StringBuilder();

        construct(builder, "", this.family, ", ");
        construct(builder, "", this.given, "");

        return builder.toString();
    }

    public String getLongFormattedName(){
        final StringBuilder builder = new StringBuilder();

        if (this.prefix != null) construct(builder, "", this.prefix, " ");
        construct(builder, "", this.given, "");
        if (this.middle != null) construct(builder, " ", this.middle, "");
        if (this.family != null) construct(builder, " ", this.family, "");
        if (this.suffix != null && !this.suffix.isEmpty()) construct(builder, ", ", this.suffix, "");

        return builder.toString();
    }

    public String toString() {
        final StringBuilder builder = new StringBuilder();

        construct(builder, "", this.prefix, " ");
        construct(builder, "", this.given, " ");
        construct(builder, "", this.middle, " ");
        construct(builder, "", this.family, "");
        if (this.suffix != null && !this.suffix.isEmpty()) construct(builder, ", ", this.suffix, "");

        return builder.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof JpaSorNameImpl)) return false;

        final JpaSorNameImpl that = (JpaSorNameImpl) o;

        if (family != null ? !family.equals(that.family) : that.family != null) return false;
        if (given != null ? !given.equals(that.given) : that.given != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (middle != null ? !middle.equals(that.middle) : that.middle != null) return false;
        if (prefix != null ? !prefix.equals(that.prefix) : that.prefix != null) return false;
        if (suffix != null ? !suffix.equals(that.suffix) : that.suffix != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (prefix != null ? prefix.hashCode() : 0);
        result = 31 * result + (given != null ? given.hashCode() : 0);
        result = 31 * result + (middle != null ? middle.hashCode() : 0);
        result = 31 * result + (family != null ? family.hashCode() : 0);
        result = 31 * result + (suffix != null ? suffix.hashCode() : 0);
        return result;
    }

    protected void construct(final StringBuilder builder, final String prefix, final String string, final String delimiter) {
        if (string != null) {
            builder.append(prefix);
            builder.append(string);
            builder.append(delimiter);
        }
    }

    public void moveToPerson(JpaSorPersonImpl person){
        this.person = person;
    }
}
