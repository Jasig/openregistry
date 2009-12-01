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

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.annotation.AllowedTypes;
import org.openregistry.core.domain.internal.Entity;
import org.openregistry.core.domain.jpa.JpaTypeImpl;
import org.openregistry.core.domain.sor.SorRole;
import org.openregistry.core.domain.sor.SorSponsor;
import org.springframework.util.Assert;

/**
 * Unique constraints assumes each sponsor and type combination only needs to appear once
 *
 * @author Dave Steiner
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@javax.persistence.Entity(name="sorSponsor")
@Table(name="prs_sponsors",uniqueConstraints = @UniqueConstraint(columnNames = {"sponsor_t","sponsor_id"}))
@Audited
public class JpaSorSponsorImpl extends Entity implements SorSponsor {

	private static final long serialVersionUID = 3547710151070428086L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prs_sponsors_seq")
    @SequenceGenerator(name="prs_sponsors_seq",sequenceName="prs_sponsors_seq",initialValue=1,allocationSize=50)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name="sponsor_t")
    @NotNull
    @AllowedTypes(property="sponsor.sponsorType")
    private JpaTypeImpl sponsorType;

    @Column(name="sponsor_id")
    @NotNull
    private Long sponsorId;

    @OneToMany(mappedBy = "sponsor", targetEntity = JpaSorRoleImpl.class)
    private Set<SorRole> roles = new HashSet<SorRole>();

	public JpaSorSponsorImpl() {
		// nothing to do
	}

	public JpaSorSponsorImpl(final JpaSorRoleImpl role) {
		this.roles.add(role);
	}

	protected Long getId() {
		return this.id;
	}

	public Long getSponsorId() {
		return this.sponsorId;
	}

	public Type getType() {
		return this.sponsorType;
	}

	public void setSponsorId(final Long id) {
		this.sponsorId = id;
	}


	public void setType(final Type type) {
        Assert.isInstanceOf(JpaTypeImpl.class, type);
	    this.sponsorType = (JpaTypeImpl)type;
	}

	public void addRole(final SorRole role) {
        Assert.isInstanceOf(JpaSorRoleImpl.class, role);
		this.roles.add(role);
	}

	public Set<SorRole> getRoles() {
		return this.roles;
	}

}
