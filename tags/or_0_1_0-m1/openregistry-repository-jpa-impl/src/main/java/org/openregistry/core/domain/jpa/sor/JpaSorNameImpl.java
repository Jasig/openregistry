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
package org.openregistry.core.domain.jpa.sor;

import org.openregistry.core.domain.internal.Entity;
import org.openregistry.core.domain.Name;
import org.openregistry.core.domain.Person;
import org.hibernate.envers.Audited;
import org.javalid.annotations.validation.NotEmpty;
import org.javalid.annotations.core.ValidateDefinition;

import javax.persistence.*;

/**
 * Implementation of the Name domain object that conforms to the tables for the Systems of Record
 *
 * TODO: Add Type field.
 * TODO: fix stubs
 * TODO: we want a type and not "is..." since those are our names
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@javax.persistence.Entity(name="sorName")
@Table(name="prs_names")
@Audited
@ValidateDefinition
public final class JpaSorNameImpl extends Entity implements Name {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prs_names_seq")
    @SequenceGenerator(name="prs_names_seq",sequenceName="prs_names_seq",initialValue=1,allocationSize=50)
    private Long id;

    @Column(name="prefix", nullable=true, length=5)
    private String prefix;

    @NotEmpty
    @Column(name="given_name",nullable=false,length=100)
    private String given;

    @Column(name="middle_name",nullable=true,length=100)
    private String middle;

    @Column(name="family_name",nullable=true,length=100)
    private String family;

    @Column(name="suffix",nullable=true,length=5)
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

    public Long getId() {
        return this.id;
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

    @PreRemove
	public void preRemove() {
        //need to remove association when remove a name from a person.
		this.person = null;
	}

    public String getFormattedName(){
        final StringBuilder builder = new StringBuilder();

        construct(builder, "", this.family, ",");
        construct(builder, "", this.given, "");

        return builder.toString();
    }

    public String toString() {
        final StringBuilder builder = new StringBuilder();

        construct(builder, "", this.prefix, " ");
        construct(builder, "", this.given, " ");
        construct(builder, "", this.middle, " ");
        construct(builder, "", this.family, "");
        construct(builder, ",", this.suffix, "");

        return builder.toString();
    }

    protected void construct(final StringBuilder builder, final String prefix, final String string, final String delimiter) {
        if (string != null) {
            builder.append(prefix);
            builder.append(string);
            builder.append(delimiter);
        }
    }

	public boolean isOfficialName() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isPreferredName() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setOfficialName() {
		// TODO Auto-generated method stub
		
	}

	public void setPreferredName() {
		// TODO Auto-generated method stub
		
	}

    public void moveToPerson(JpaSorPersonImpl person){
        this.person = person;
    }
}
