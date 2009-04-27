package org.openregistry.core.domain.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.openregistry.core.domain.Role;
import org.openregistry.core.domain.Sponsor;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.internal.Entity;

/**
 * @author Dave Steiner
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@javax.persistence.Entity(name="sponsor")
@Table(name="prc_sponsors")
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
    private List<Role> roles = new ArrayList<Role>();

	public JpaSponsorImpl() {
		// nothing to do
	}
	
	public JpaSponsorImpl(Role role) {
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
	    if (!(type instanceof JpaTypeImpl)) {
	    	throw new IllegalArgumentException("Requires type JpaTypeImpl");
	    }
	    this.sponsorType = (JpaTypeImpl)type;
	}

	public void addRole(final Role role) {
		if (!(role instanceof JpaRoleImpl)) {
			throw new IllegalArgumentException("role of type JpaRoleImpl required");
		}
		this.roles.add(role);
	}

	public List<Role> getRoles() {
		return this.roles;
	}
	
}
