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

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.envers.Audited;
import org.openregistry.core.domain.Role;
import org.openregistry.core.domain.Sponsor;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.internal.Entity;
import org.springframework.util.Assert;

/**
 * Unique constraint assumes that a given sponsor has only one type entry
 *
 * @author Dave Steiner
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@javax.persistence.Entity(name="sponsor")
@Table(name="prc_sponsors", uniqueConstraints = @UniqueConstraint(columnNames = {"sponsor_t","sponsor_id"}))
@Audited
public class JpaSponsorImpl extends Entity implements Sponsor {

	private static final long serialVersionUID = -5134872278901259478L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prc_sponsors_seq")
    @SequenceGenerator(name="prc_sponsors_seq",sequenceName="prc_sponsors_seq",initialValue=1,allocationSize=50)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name="sponsor_t")
    private JpaTypeImpl sponsorType;

    @Column(name="sponsor_id")
    private Long sponsorId;

    @OneToMany(mappedBy = "sponsor", targetEntity = JpaRoleImpl.class)
    private Set<Role> roles = new HashSet<Role>();

	public JpaSponsorImpl() {
		// nothing to do
	}

	public JpaSponsorImpl(final JpaRoleImpl role) {
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

	public void addRole(final Role role) {
        Assert.isInstanceOf(JpaRoleImpl.class, role);
		this.roles.add(role);
	}

	public Set<Role> getRoles() {
		return this.roles;
	}
}
